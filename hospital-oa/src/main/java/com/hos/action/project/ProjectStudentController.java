package com.hos.action.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
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
import com.hos.po.model.dict.Dicts;
import com.hos.po.model.project.ProjectCourse;
import com.hos.po.model.project.ProjectStudent;
import com.hos.po.model.project.Projects;
import com.hos.po.model.student.Students;
import com.hos.service.dict.DictOrgService;
import com.hos.service.dict.DictService;
import com.hos.service.project.ProjectStudentService;
import com.hos.service.project.ProjectService;
import com.hos.service.student.StudentService;
import com.hos.vo.project.ProjectStudentSearchVo;
import com.hos.vo.project.ProjectStudentVo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.ExcelUtil;
import com.meijia.utils.RandomUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.poi.HssExcelTools;
import com.simi.action.BaseController;
import com.simi.oa.auth.AccountAuth;
import com.simi.oa.auth.AccountRole;
import com.simi.oa.auth.AuthHelper;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.vo.AppResultData;

@Controller
@RequestMapping(value = "/project/student")
public class ProjectStudentController extends BaseController {
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectStudentService projectStudentService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private DictService dictService;
		
	@Autowired
	private DictOrgService dictOrgService;
	
	@AuthPassport
	@RequestMapping(value = "/student-list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, ProjectStudentSearchVo searchVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		
		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ConstantOa.DEFAULT_PAGE_SIZE;
		
		Long pId = searchVo.getpId();
		Projects project = projectService.selectByPrimaryKey(pId);
		model.addAttribute("project", project);
		model.addAttribute("pId", pId);
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		AccountRole accountRole = accountAuth.getAccountRole();
		
		Short statusCourse = project.getStatusCourse();
		if (accountRole.getId().equals(2L) && !statusCourse.equals((short)1)) {
			List<ProjectStudent> list = new ArrayList<ProjectStudent>();
			PageInfo pageInfo = new PageInfo(list);
			model.addAttribute("contentModel", pageInfo);
		} else {
			PageInfo pageInfo = projectStudentService.selectByListPage(searchVo, pageNo, pageSize);		
			model.addAttribute("contentModel", pageInfo);
		}
		
		return "project/studentList";
	}
	
	@AuthPassport
	@RequestMapping(value = "/student-view", method = { RequestMethod.GET })
	public String courseForm(HttpServletRequest request, Model model, Long id) {
		
		ProjectStudent record = projectStudentService.selectByPrimaryKey(id);
		ProjectStudentVo vo = projectStudentService.getVo(record);
		
		model.addAttribute("formData", vo);
		
		return "project/studentView";

	}		
	
	@AuthPassport
	@RequestMapping(value = "/student-form", method = { RequestMethod.GET })
	public String courseForm(HttpServletRequest request, Model model, Long pId, Long id) {
		
		model.addAttribute("pId", pId);
		
		ProjectStudent record = projectStudentService.initProjectStudent();
		
		if (id > 0L) {
			record = projectStudentService.selectByPrimaryKey(id);
		}
		record.setpId(pId);
		model.addAttribute("formData", record);
		
		return "project/studentForm";

	}		
	
	
	
	@AuthPassport
	@RequestMapping(value = "/student-form", method = { RequestMethod.POST })
	public String courseForm(HttpServletRequest request, Model model, @ModelAttribute("formData") ProjectStudent formData) {
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long adminId = accountAuth.getId();
		
		Long id = Long.valueOf(request.getParameter("id"));
		Long pId = Long.valueOf(request.getParameter("pId"));
		
		String orgName = formData.getOrgName();
		if (!StringUtil.isEmpty(orgName)) {
			DictOrgs org = dictOrgService.findByName(orgName);
			if (org != null) formData.setOrgId(org.getOrgId());;
		}

		
		Long titleId = formData.getTitleId();
		if (titleId != null && titleId > 0L) {
			Dicts titleObj = dictService.findById(titleId, Constants.DICT_TITLE);
			if (titleObj != null) formData.setTitleStr(titleObj.getName());
		}
		
		Long eduId = formData.getEduId();
		if (eduId != null && eduId > 0L) {
			Dicts eduObject = dictService.findById(eduId, Constants.DICT_EDU);
			if (eduObject != null) formData.setEduName(eduObject.getName());
		}
		
		
		//检测student表，是否需要插入此表。
		ProjectStudentSearchVo searchVo = new ProjectStudentSearchVo();
		searchVo.setName(formData.getName().trim());
		searchVo.setMobile(formData.getMobile().trim());
		
		Students student = studentService.initStudents();
		List<Students> list = studentService.selectBySearchVo(searchVo);

		if (!list.isEmpty()) {
			student = list.get(0);
		}
		BeanUtilsExp.copyPropertiesIgnoreNull(formData, student);
		student.setAdminId(adminId);
		if (student.getStuId().equals(0L)) {
			studentService.insertSelective(student);
		} else {
			student.setUpdateTime(TimeStampUtil.getNowSecond());
			studentService.updateByPrimaryKeySelective(student);
		}
		
		Long stuId = student.getStuId();
				
		ProjectStudent record = projectStudentService.initProjectStudent();
		
		if (id > 0L) {
			record = projectStudentService.selectByPrimaryKey(id);
		}
		
		BeanUtilsExp.copyPropertiesIgnoreNull(formData, record);
		record.setStuId(stuId);
		record.setAdminId(adminId);
		if (id > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			projectStudentService.updateByPrimaryKeySelective(record);
		} else {
			projectStudentService.insertSelective(record);
		}

		model.addAttribute("formData", record);
		
		return "redirect:student-list?pId="+pId;

	}		
	
	@AuthPassport
	@RequestMapping(value = "/student-del", method = { RequestMethod.GET })
	public String courseDel(HttpServletRequest request, Model model) {
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long roleId = accountAuth.getAccountRole().getId();
		Long id = Long.valueOf(request.getParameter("id"));
		Long pId = Long.valueOf(request.getParameter("pId"));
		if (!roleId.equals(2L) && id > 0L) {
			projectStudentService.deleteByPrimaryKey(id);
		}

		return "redirect:student-list?pId="+pId;

	}	


	@AuthPassport
	@RequestMapping(value = "/student-import", method = { RequestMethod.GET })
	public String projectImportCheck(HttpServletRequest request, Model model, Long pId) {
		model.addAttribute("pId", pId);
		return "project/studentImport";
	}		
	
	@SuppressWarnings("unchecked")
	@AuthPassport
	@RequestMapping(value = "/student-import", method = { RequestMethod.POST })
	public String projectImport(HttpServletRequest request, Model model) throws Exception {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		String pIdStr = request.getParameter("pId");
		Long pId = Long.valueOf(pIdStr);
		model.addAttribute("pId", pId);
		// 创建一个通用的多部分解析器.
		String path = Constants.IMPORT_PATH;
		String fileToken = TimeStampUtil.getNowSecond().toString() + RandomUtil.randomNumber(6);
		String newFileName = "";
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

		if (multipartResolver.isMultipart(request)) {
			// 判断 request 是否有文件上传,即多部分请求...
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);

			MultipartFile file = multiRequest.getFile("student-file");
			if (file != null && !file.isEmpty()) {
				// 存储的临时文件名 = 获取时间戳+随机六位数+"."图片扩展名
				String fileName = file.getOriginalFilename();
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				newFileName = "student-import-" + fileToken + "." + ext;
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, newFileName));
			}
		}

		List<Object> excelDatas = new ArrayList<Object>();
		if (StringUtil.isEmpty(newFileName)) {
			model.addAttribute("errors", "表格数据为空，请下载模板后填写.");
			model.addAttribute("tableDatas", "");
			return "project/studentImportError";
		}

		
		try {
		InputStream in = new FileInputStream(path + newFileName);
		excelDatas = ExcelUtil.readToList(path + newFileName, in, 0, 0);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			model.addAttribute("errors", "表格数据为空，请下载模板后填写.");
			model.addAttribute("tableDatas", "");
			return "project/studentImportError";
		}
		// 校验表格是否正确.
		AppResultData<Object> validateResult = projectStudentService.validateProjectStudentImport(excelDatas);
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			model.addAttribute("errors", validateResult.getMsg());
			model.addAttribute("tableDatas", validateResult.getData());
			return "project/studentImportError";
		}
		
		//检测重复项
		List<Object> dupList = projectStudentService.checkDuplication(pId, excelDatas);

		int totalNews = 0;
		int totalUpdate = 0;;
//		model.addAttribute("tableDatas", "");
		
		for (int i = 0; i < dupList.size(); i++) {
			List<String> item = (List<String>) dupList.get(i);
			String status = item.get(7).toString();
			if (status.indexOf("新增") >= 0) totalNews++;
			if (status.indexOf("修改") >= 0) totalUpdate++;
		}
		model.addAttribute("tableDatas", dupList);
		model.addAttribute("totals", dupList.size());
		model.addAttribute("totalNews", totalNews);
		model.addAttribute("totalUpdate", totalUpdate);
		
		model.addAttribute("newFileName", newFileName);		
		
		return "project/studentImportConfirm";
	}
	
	@AuthPassport
	@RequestMapping(value = "/student-import-do", method = { RequestMethod.POST })
	public String doProjectImport(Model model, HttpServletRequest request) throws Exception {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		if (accountAuth == null) {
			model.addAttribute("errors", "长时间未操作,请重新登录后进行导入.");
			return "project/projectImportError";
		}
		
		Long pId = Long.valueOf(request.getParameter("pId").toString());
		model.addAttribute("pId", pId);
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

		AppResultData<Object> validateResult = projectStudentService.doProjectStudentImport(pId, excelDatas, newFileName, adminId);
		
		String totals = request.getParameter("totals").toString();
		String totalNews = request.getParameter("totalNews").toString();
		String totalUpdate = request.getParameter("totalUpdate").toString();
		
		model.addAttribute("totals", totals);
		model.addAttribute("totalNews", totalNews);
		model.addAttribute("totalUpdate", totalUpdate);
		
		return "/project/studentImportOk";
	}
	
	@RequestMapping(value = "/checkDupName", method = RequestMethod.GET)
	public AppResultData<Object> checkDupName(
			@RequestParam("pId") Long pId,
			@RequestParam("id") Long id,
			@RequestParam("name") String name,
			@RequestParam("mobile") String mobile
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		ProjectStudentSearchVo searchVo = new ProjectStudentSearchVo();
		searchVo.setpId(pId);
		searchVo.setName(name);
		searchVo.setMobile(mobile);
		List<ProjectStudent> list = projectStudentService.selectBySearchVo(searchVo);
		
		if (id.equals(0L) && !list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("重复的学员,请检查学员姓名和手机号是否已经录入过.");
		}
		for (ProjectStudent item: list) {
			if (!item.getId().equals(id)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("重复的学员,请检查学员姓名和手机号是否已经录入过.");
			}
		}
		
		return result;
	}
	
	@AuthPassport
	@RequestMapping(value = "/student-export", method = { RequestMethod.GET })
	public String export(HttpServletRequest request, HttpServletResponse response, ProjectStudentSearchVo searchVo) throws IOException {

		Long pId = searchVo.getpId();
		Projects project = projectService.selectByPrimaryKey(pId);
		
		List<ProjectStudent> list = projectStudentService.selectBySearchVo(searchVo);

		
		String cpath = request.getSession().getServletContext().getRealPath("/WEB-INF") + "/attach/";
		String templateName = "学员导入Excel模板.xls";
		
		HssExcelTools excel = new HssExcelTools(cpath + templateName, 0);
		HSSFSheet sh = excel.getHssSheet();
		
		int rowNum = 1;
		
		for (int i = 0 ; i < list.size(); i++) {
			ProjectStudent item = list.get(i);
			
			HSSFRow rowData = sh.createRow(rowNum);
			
			for(int j = 0; j <= 6; j++) {
				rowData.createCell(j);
				HSSFCell c = rowData.getCell(j);
				c.setCellType(Cell.CELL_TYPE_STRING);
			}
			
			//姓名
			this.setCellValueForString(rowData, 0, item.getName());
			
			//职称
			this.setCellValueForString(rowData, 1, item.getTitleStr());
			
			//职务
			this.setCellValueForString(rowData, 2, item.getDutyName());
			
			//学历
			this.setCellValueForString(rowData, 3, item.getEduName());
			
			//所在单位
			this.setCellValueForString(rowData, 4, item.getOrgName());
			
			
			//手机号
			this.setCellValueForString(rowData, 5, item.getMobile());
			
			
			rowNum++;
		}
		
		String fileName = project.getName() + "-学员列表.xls";
		excel.downloadExcel(request, response, fileName);
		
		return null;
	}

	private  void setCellValueForString(HSSFRow rowData, int rowNum, String v) {
		HSSFCell c = rowData.getCell(rowNum);
		c.setCellType(Cell.CELL_TYPE_STRING);
		c.setCellValue(v);
	}
	
	private  void setCellValueForDouble(HSSFRow rowData, int cellNum, Double v) {
		HSSFCell c = rowData.getCell(cellNum);
		c.setCellType(Cell.CELL_TYPE_NUMERIC);
		c.setCellValue(v);
	}	
}
