package com.hos.action.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.github.pagehelper.PageInfo;
import com.hos.common.ConstantMsg;
import com.hos.common.Constants;
import com.hos.po.model.dict.DictOrgs;
import com.hos.po.model.project.ProjectAttach;
import com.hos.po.model.project.Projects;
import com.hos.service.dict.DictOrgService;
import com.hos.service.project.ProjectAttachService;
import com.hos.service.project.ProjectService;
import com.hos.vo.dict.DictOrgSearchVo;
import com.hos.vo.project.ProjectSearchVo;
import com.hos.vo.project.ProjectVo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.BoyerMooreUtil;
import com.meijia.utils.DateUtil;
import com.meijia.utils.ExcelUtil;
import com.meijia.utils.FileUtil;
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
	
	@Autowired
	private DictOrgService dictOrgService;
	
	@Autowired
	private ProjectAttachService projectAttachService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @param searchVo
	 * @param linkType   根据不同的linkType展现不同的按钮
	 *        linkType == 'project' or ''    
	 *           会议通知  会议日程   学员
	 *        linkType == 'courseType'
	 *           导入会议日程  查看会议日程
	 *        linkType == 'attach'
	 *           导入会议通知  查看会议通知
	 *        linkType == 'student'
	 *           导入学员  查看学员
	 *         
	 *          
	 *        
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, ProjectSearchVo searchVo, String linkType) {

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
		
		if (searchVo.getpYear() == null) {
			searchVo.setpYear(DateUtil.getYear());
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
		model.addAttribute("linkType", linkType);
		return "project/projectList";
	}
	
	@AuthPassport
	@RequestMapping(value = "/project-view", method = { RequestMethod.GET })
	public String projectView(HttpServletRequest request, Model model, Long pId) {
		
		Projects record = projectService.initProject();
		if (pId > 0L) {
			record = projectService.selectByPrimaryKey(pId);
		}
		
		ProjectVo vo = projectService.getVo(record);
		
		model.addAttribute("formData", vo);
		return "project/projectView";
	}

	@AuthPassport
	@RequestMapping(value = "/project-form", method = { RequestMethod.GET })
	public String projectForm(HttpServletRequest request, Model model, Long pId) {
		
		Projects record = projectService.initProject();
		if (pId > 0L) {
			record = projectService.selectByPrimaryKey(pId);
		}
		model.addAttribute("formData", record);
		return "project/projectForm";
	}
	
	@AuthPassport
	@RequestMapping(value = "/project-form", method = { RequestMethod.POST })
	public String doProjectForm(HttpServletRequest request, Model model, @ModelAttribute("formData") Projects formData) {
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long adminId = accountAuth.getId();
		
		Long pId = Long.valueOf(request.getParameter("pId"));
		Projects record = projectService.initProject();
		
		if (pId > 0L) {
			record = projectService.selectByPrimaryKey(pId);
		}
		
		BeanUtilsExp.copyPropertiesIgnoreNull(formData, record);
		
		record.setAdminId(adminId);
		
		//单位
		Long orgId = record.getOrgId();
		DictOrgs org = dictOrgService.findById(orgId);
		record.setOrgName(org.getName());
		
		
		if (pId > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			projectService.updateByPrimaryKeySelective(record);
		} else {
			projectService.insertSelective(record);
		}
		
		model.addAttribute("formData", record);
		
		return "redirect:list";

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
			String status = item.get(11).toString();
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
	
	
	@RequestMapping(value = "/checkDupName", method = RequestMethod.GET)
	public AppResultData<Object> checkDupName(
			@RequestParam("pId") Long pId,
			@RequestParam("pNo") String pNo, 
			@RequestParam("numTerm") Short numTerm
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		ProjectSearchVo searchVo = new ProjectSearchVo();
		
		searchVo.setpNo(pNo);
		
		searchVo.setNumTerm(numTerm);
		
		List<Projects> list = projectService.selectBySearchVo(searchVo);
		
		if (!list.isEmpty() && pId.equals(0L)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("项目重复，请检查项目编号和期数是否已经录入过.");
		}
		
		for (Projects item: list) {
			if (!item.getpId().equals(pId)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("项目重复，请检查项目编号和期数是否已经录入过.");
			}
		}
		
		return result;
	}
	
	
	@AuthPassport
	@RequestMapping(value = "/attach-import", method = { RequestMethod.GET })
	public String projectAttach(HttpServletRequest request, Model model) {
		
		Long pId = Long.valueOf(request.getParameter("pId"));
		
		model.addAttribute("pId", pId);
		
		return "project/attachImport";
	}		
	
	@AuthPassport
	@RequestMapping(value = "/attach-import", method = { RequestMethod.POST })
	public String doAttach(HttpServletRequest request, Model model) throws Exception {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long adminId = accountAuth.getId();
		Long pId = Long.valueOf(request.getParameter("pId"));
		
		model.addAttribute("pId", pId);
		
		// 创建一个通用的多部分解析器.
		String path = Constants.IMPORT_PATH;
		String fileToken = TimeStampUtil.getNowSecond().toString() + RandomUtil.randomNumber(6);
		String newFileName = "";
		Long fileSize = 0L;
		String fileType = "";
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

		if (multipartResolver.isMultipart(request)) {
			// 判断 request 是否有文件上传,即多部分请求...
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);

			MultipartFile file = multiRequest.getFile("attachFile");
			if (file != null && !file.isEmpty()) {
				// 存储的临时文件名 = 获取时间戳+随机六位数+"."图片扩展名
				String fileName = file.getOriginalFilename();
				fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
				fileSize = file.getSize();
				newFileName = "project-attach-" + fileToken + "." + fileType;
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, newFileName));
			}
		}
		
		if (StringUtil.isEmpty(newFileName)) {
			model.addAttribute("errors", "上传文件为空.");
			return "project/attachImportError";
		}
		
		if (fileSize > 33554432L) {
			model.addAttribute("errors", "上传文件超出32M.");
			return "project/attachImportError";
		}
		
		ProjectSearchVo searchVo = new ProjectSearchVo();
		searchVo.setpId(pId);
		searchVo.setAttachType("briefing");
		List<ProjectAttach> list = projectAttachService.selectBySearchVo(searchVo);
		ProjectAttach record = projectAttachService.initProjectAttach();
		
		if (!list.isEmpty()) {
			record = list.get(0);
		}
		
		record.setpId(pId);
		record.setAttachType("briefing");
		record.setFileName(newFileName);
		record.setFileType(fileType);
		record.setFileSize(fileSize);
		record.setAdminId(adminId);
		
		if (record.getId() > 0L) {
			projectAttachService.updateByPrimaryKeySelective(record);
		} else {
			projectAttachService.insertSelective(record);
			
			
		}
		
		//设定为project status_attach = 1
		Projects project = projectService.selectByPrimaryKey(pId);
		project.setStatusAttach(Constants.STATUS_1);
		project.setUpdateTime(TimeStampUtil.getNowSecond());
		projectService.updateByPrimaryKeySelective(project);
		
		
		return "project/attachImportOk";
	}


	@AuthPassport
	@RequestMapping(value = "/attach-download", method = { RequestMethod.GET }) 
	public String attachDownload(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Long pId = Long.valueOf(request.getParameter("pId"));
		
		Projects project = projectService.selectByPrimaryKey(pId);
		
		ProjectSearchVo searchVo = new ProjectSearchVo();
		searchVo.setpId(pId);
		searchVo.setAttachType("briefing");
		List<ProjectAttach> list = projectAttachService.selectBySearchVo(searchVo);
		
		if (!list.isEmpty()) {
			ProjectAttach item = list.get(0);
			String fileName = item.getFileName();
			String filePath = Constants.IMPORT_PATH + fileName;
			
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
			String viewName = project.getpNo() + project.getName() + "." + ext;
			viewName = FileUtil.processFileName(request, viewName);
			FileUtil.fileDownload(request, response, viewName, filePath);
		}
		
		
		return null;
	}
	
	
	@RequestMapping(value = "/shortname-test", method = { RequestMethod.GET }) 
	public String shortNameTest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		DictOrgSearchVo searchVo = new DictOrgSearchVo();
		
		searchVo.setIsOrg((short) 1);
		List<DictOrgs> list = dictOrgService.selectBySearchVo(searchVo);
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		BoyerMooreUtil bm = new BoyerMooreUtil();
		
		for (DictOrgs item : list) {
			Map<String, Object> vo = new HashMap<String, Object>();
			vo.put("name", item.getName());
			vo.put("shortName", item.getShortName());
			vo.put("mc", bm.boyerMoore(item.getShortName(), item.getName()));
			
			result.add(vo);
		}
		
		model.addAttribute("contentModel", result);
		return "project/shortNameTest";
	}
	
	@RequestMapping(value = "/shortname-match", method = RequestMethod.POST)
	public AppResultData<Object> shortNameMatch(@RequestParam("matchName") String matchName) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		List<DictOrgs> resultData = dictOrgService.findByMatchName(matchName);
		result.setData(resultData);
		return result;
	}
	
	@RequestMapping(value = "/shortname-match-like", method = RequestMethod.POST)
	public AppResultData<Object> shortNameMatchLike(@RequestParam("matchName") String matchName) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		List<DictOrgs> resultData = dictOrgService.findByMatchNameLike(matchName);
		result.setData(resultData);
		return result;
	}
	
	@RequestMapping(value = "/project-status-push", method = RequestMethod.POST)
	public AppResultData<Object> projectStatusPush(
			@RequestParam("pId") Long pId,
			@RequestParam("statusType") String statusType, 
			@RequestParam("status") Short status
			) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		ProjectSearchVo searchVo = new ProjectSearchVo();
		
		searchVo.setpId(pId);
		
		List<Projects> list = projectService.selectBySearchVo(searchVo);
		
		if (list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("项目重复，请检查项目编号和期数是否已经录入过.");
		}
		
		Projects project = list.get(0);
		
		if (statusType.equals("statusAttach")) {
			project.setStatusAttach(status);
		}
		
		if (statusType.equals("statusCourse")) {
			project.setStatusCourse(status);
		}
		
		if (statusType.equals("statusStudent")) {
			project.setStatusStudent(status);
		}
		
		project.setUpdateTime(TimeStampUtil.getNowSecond());
		projectService.updateByPrimaryKeySelective(project);
		
		return result;
	}
}
