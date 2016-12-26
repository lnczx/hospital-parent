package com.hos.action.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.github.pagehelper.PageInfo;
import com.hos.common.Constants;
import com.hos.po.model.project.Projects;
import com.hos.service.project.ProjectService;
import com.hos.vo.project.ProjectSearchVo;
import com.hos.vo.project.ProjectVo;
import com.meijia.utils.ExcelUtil;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.BaseController;
import com.simi.oa.auth.AccountAuth;
import com.simi.oa.auth.AuthHelper;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/project")
public class ProjectController extends BaseController {
	@Autowired
	private ProjectService projectService;
	
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, ProjectSearchVo searchVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ConstantOa.DEFAULT_PAGE_SIZE;

		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long orgId = accountAuth.getOrgId();
		if (orgId > 0L) {
			searchVo.setOrgId(orgId);
		}

		@SuppressWarnings("rawtypes")
		PageInfo pageInfo = projectService.selectByListPage(searchVo, pageNo, pageSize);
		List<Projects> list = pageInfo.getList();
		for (int i =0; i < list.size(); i++) {
			Projects item = list.get(i);
			ProjectVo vo = projectService.getVo(item);
			list.set(i, vo);
		}
		pageInfo = new PageInfo(list);
		
		model.addAttribute("contentModel", pageInfo);

		return "project/projectList";
	}


	@AuthPassport
	@RequestMapping(value = "/project-import", method = { RequestMethod.GET })
	public String projectImportCheck(HttpServletRequest request, Model model) {

		return "project/projectImport";
	}		
	
	@SuppressWarnings("unchecked")
	@AuthPassport
	@RequestMapping(value = "/project-import", method = { RequestMethod.POST })
	public String projectImport(HttpServletRequest request, Model model) throws Exception {

		// 获取登录的用户
//		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		// 创建一个通用的多部分解析器.
		String path = Constants.IMPORT_PATH;
		String fileToken = TimeStampUtil.getNowSecond().toString() + RandomUtil.randomNumber(6);
		String newFileName = "";
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

		if (multipartResolver.isMultipart(request)) {
			// 判断 request 是否有文件上传,即多部分请求...
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);

			MultipartFile file = multiRequest.getFile("project-file");
			if (file != null && !file.isEmpty()) {
				// 存储的临时文件名 = 获取时间戳+随机六位数+"."图片扩展名
				String fileName = file.getOriginalFilename();
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				newFileName = "project-import-" + fileToken + "." + ext;
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, newFileName));
			}
		}

		List<Object> excelDatas = new ArrayList<Object>();
		if (StringUtil.isEmpty(newFileName)) {
			model.addAttribute("errors", "表格数据为空，请下载模板后填写.");
			model.addAttribute("tableDatas", "");
			return "project/projectImportError";
		}

		
		try {
		InputStream in = new FileInputStream(path + newFileName);
		excelDatas = ExcelUtil.readToList(path + newFileName, in, 0, 0);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			model.addAttribute("errors", "表格数据为空，请下载模板后填写.");
			model.addAttribute("tableDatas", "");
			return "project/projectImportError";
		}
		// 校验表格是否正确.
		AppResultData<Object> validateResult = projectService.validateProjectImport(excelDatas);
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			model.addAttribute("errors", validateResult.getMsg());
			model.addAttribute("tableDatas", validateResult.getData());
			return "project/projectImportError";
		}
		
		//检测重复项
		List<Object> dupList = projectService.checkDuplication(excelDatas);

		int totalNews = 0;
		int totalUpdate = 0;;
//		model.addAttribute("tableDatas", "");
		
		for (int i = 0; i < dupList.size(); i++) {
			List<String> item = (List<String>) dupList.get(i);
			String status = item.get(10).toString();
			if (status.indexOf("新增") >= 0) totalNews++;
			if (status.indexOf("修改") >= 0) totalUpdate++;
		}
		model.addAttribute("tableDatas", dupList);
		model.addAttribute("totals", dupList.size());
		model.addAttribute("totalNews", totalNews);
		model.addAttribute("totalUpdate", totalUpdate);
		
		model.addAttribute("newFileName", newFileName);		
		
		return "project/projectImportConfirm";
	}
	
	@AuthPassport
	@RequestMapping(value = "/project-import-error", method = { RequestMethod.GET })
	public String projectImportError(HttpServletRequest request, Model model) {

		return "project/projectImportError";
	}	
	
	@AuthPassport
	@RequestMapping(value = "/project-import-do", method = { RequestMethod.POST })
	public String doProjectImport(Model model, HttpServletRequest request) throws Exception {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		if (accountAuth == null) {
			model.addAttribute("errors", "长时间未操作,请重新登录后进行导入.");
			return "project/projectImportError";
		}
		
		Long adminId = accountAuth.getId();
		// 创建一个通用的多部分解析器.
		String path = Constants.IMPORT_PATH;
		String newFileName = request.getParameter("newFileName").toString();

		if (StringUtil.isEmpty(newFileName)) {
			model.addAttribute("errors", "表格数据为空，请下载模板后填写.");
			return "project/projectImportError";
		}

		List<Object> excelDatas = new ArrayList<Object>();

		InputStream in = new FileInputStream(path + newFileName);
		excelDatas = ExcelUtil.readToList(path + newFileName, in, 0, 0);

		AppResultData<Object> validateResult = projectService.doProjectImport(excelDatas, newFileName, adminId);
		
		String totals = request.getParameter("totals").toString();
		String totalNews = request.getParameter("totalNews").toString();
		String totalUpdate = request.getParameter("totalUpdate").toString();
		
		model.addAttribute("totals", totals);
		model.addAttribute("totalNews", totalNews);
		model.addAttribute("totalUpdate", totalUpdate);
		
		return "/project/projectImportOk";
	}



}
