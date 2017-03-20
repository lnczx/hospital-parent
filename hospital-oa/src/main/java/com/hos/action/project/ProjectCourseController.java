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
import com.hos.po.model.project.ProjectCourse;
import com.hos.po.model.project.Projects;
import com.hos.service.project.ProjectCourseService;
import com.hos.service.project.ProjectService;
import com.hos.vo.project.ProjectCourseSearchVo;
import com.hos.vo.project.ProjectCourseVo;
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
@RequestMapping(value = "/project/course")
public class ProjectCourseController extends BaseController {
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectCourseService projectCourseService;
	
	@AuthPassport
	@RequestMapping(value = "/course-list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, ProjectCourseSearchVo searchVo) {

		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ConstantOa.DEFAULT_PAGE_SIZE;

		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		AccountRole accountRole = accountAuth.getAccountRole();
		
		Long pId = searchVo.getpId();
		Projects project = projectService.selectByPrimaryKey(pId);
		model.addAttribute("project", project);
		model.addAttribute("pId", pId);
		
		Short statusCourse = project.getStatusCourse();
		if (accountRole.getId().equals(2L) && !statusCourse.equals((short)1)) {
			List<ProjectCourse> list = new ArrayList<ProjectCourse>();
			PageInfo pageInfo = new PageInfo(list);
			model.addAttribute("contentModel", pageInfo);
		} else {
			PageInfo pageInfo = projectCourseService.selectByListPage(searchVo, pageNo, pageSize);
			List<ProjectCourse> list = pageInfo.getList();
			for (int i = 0 ; i < list.size(); i++) {
				ProjectCourse item = list.get(i);
				ProjectCourseVo vo = projectCourseService.getVo(item);
				list.set(i, vo);
			}
			pageInfo = new PageInfo(list);
			model.addAttribute("contentModel", pageInfo);
		}

		return "project/courseList";
	}
	
	@AuthPassport
	@RequestMapping(value = "/course-form", method = { RequestMethod.GET })
	public String courseForm(HttpServletRequest request, Model model, Long pId, Long courseId) {
		
		model.addAttribute("pId", pId);
		
		ProjectCourse record = projectCourseService.initProjectCourse();
		
		if (courseId > 0L) {
			record = projectCourseService.selectByPrimaryKey(courseId);
		}
		record.setpId(pId);
		
		ProjectCourseVo vo = projectCourseService.getVo(record);
		
		model.addAttribute("formData", vo);
		
		return "project/courseForm";

	}		
	
	
	
	@AuthPassport
	@RequestMapping(value = "/course-form", method = { RequestMethod.POST })
	public String courseForm(HttpServletRequest request, Model model, @ModelAttribute("formData") ProjectCourse formData) {
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long adminId = accountAuth.getId();
		
		Long courseId = Long.valueOf(request.getParameter("courseId"));
		Long pId = Long.valueOf(request.getParameter("pId"));
		ProjectCourse record = projectCourseService.initProjectCourse();
		
		if (courseId > 0L) {
			record = projectCourseService.selectByPrimaryKey(courseId);
		}
		
		BeanUtilsExp.copyPropertiesIgnoreNull(formData, record);
		
		record.setAdminId(adminId);
		if (courseId > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			projectCourseService.updateByPrimaryKeySelective(record);
		} else {
			projectCourseService.insertSelective(record);
		}
		
		model.addAttribute("formData", record);
		
		return "redirect:course-list?pId="+pId;

	}		
	
	@AuthPassport
	@RequestMapping(value = "/course-del", method = { RequestMethod.GET })
	public String courseDel(HttpServletRequest request, Model model) {
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long roleId = accountAuth.getAccountRole().getId();
		Long courseId = Long.valueOf(request.getParameter("courseId"));
		Long pId = Long.valueOf(request.getParameter("pId"));
		if (!roleId.equals(2L) && courseId > 0L) {
			projectCourseService.deleteByPrimaryKey(courseId);
		}

		return "redirect:course-list?pId="+pId;

	}	


	@AuthPassport
	@RequestMapping(value = "/course-import", method = { RequestMethod.GET })
	public String projectImportCheck(HttpServletRequest request, Model model, Long pId) {
		model.addAttribute("pId", pId);
		return "project/courseImport";
	}		
	
	@SuppressWarnings("unchecked")
	@AuthPassport
	@RequestMapping(value = "/course-import", method = { RequestMethod.POST })
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

			MultipartFile file = multiRequest.getFile("course-file");
			if (file != null && !file.isEmpty()) {
				// 存储的临时文件名 = 获取时间戳+随机六位数+"."图片扩展名
				String fileName = file.getOriginalFilename();
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				newFileName = "course-import-" + fileToken + "." + ext;
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, newFileName));
			}
		}

		List<Object> excelDatas = new ArrayList<Object>();
		if (StringUtil.isEmpty(newFileName)) {
			model.addAttribute("errors", "表格数据为空，请下载模板后填写.");
			model.addAttribute("tableDatas", "");
			return "project/courseImportError";
		}

		
		try {
		InputStream in = new FileInputStream(path + newFileName);
		excelDatas = ExcelUtil.readToList(path + newFileName, in, 0, 0);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			model.addAttribute("errors", "表格数据为空，请下载模板后填写.");
			model.addAttribute("tableDatas", "");
			return "project/courseImportError";
		}
		// 校验表格是否正确.
		AppResultData<Object> validateResult = projectCourseService.validateProjectCourseImport(pId, excelDatas);
		if (validateResult.getStatus() != Constants.SUCCESS_0) {
			model.addAttribute("errors", validateResult.getMsg());
			model.addAttribute("tableDatas", validateResult.getData());
			return "project/courseImportError";
		}
		
		//检测重复项
		List<Object> dupList = projectCourseService.checkDuplication(pId, excelDatas);

		int totalNews = 0;
		int totalUpdate = 0;;
//		model.addAttribute("tableDatas", "");
		
		for (int i = 0; i < dupList.size(); i++) {
			List<String> item = (List<String>) dupList.get(i);
			String status = item.get(9).toString();
			if (status.indexOf("新增") >= 0) totalNews++;
			if (status.indexOf("修改") >= 0) totalUpdate++;
		}
		model.addAttribute("tableDatas", dupList);
		model.addAttribute("totals", dupList.size());
		model.addAttribute("totalNews", totalNews);
		model.addAttribute("totalUpdate", totalUpdate);
		
		model.addAttribute("newFileName", newFileName);		
		
		return "project/courseImportConfirm";
	}
	
	@AuthPassport
	@RequestMapping(value = "/course-import-do", method = { RequestMethod.POST })
	public String doProjectImport(Model model, HttpServletRequest request) throws Exception {

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		if (accountAuth == null) {
			model.addAttribute("errors", "长时间未操作,请重新登录后进行导入.");
			return "project/projectImportError";
		}
		
		String pIdStr = request.getParameter("pId").toString();
		Long pId = Long.valueOf(pIdStr);
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

		AppResultData<Object> validateResult = projectCourseService.doProjectCousreImport(pId, excelDatas, newFileName, adminId);
		
		String totals = request.getParameter("totals").toString();
		String totalNews = request.getParameter("totalNews").toString();
		String totalUpdate = request.getParameter("totalUpdate").toString();
		
		model.addAttribute("totals", totals);
		model.addAttribute("totalNews", totalNews);
		model.addAttribute("totalUpdate", totalUpdate);
		
		return "/project/courseImportOk";
	}
	
	@RequestMapping(value = "/checkDupName", method = RequestMethod.GET)
	public AppResultData<Object> checkDupName(
			@RequestParam("pId") Long pId,
			@RequestParam("courseId") Long courseId, 
			@RequestParam("content") String content,
			@RequestParam("courseDate") String courseDate,
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		
		
		ProjectCourseSearchVo searchVo = new ProjectCourseSearchVo();
		searchVo.setpId(pId);
		searchVo.setContent(content);
		searchVo.setCourseDate(courseDate);
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		List<ProjectCourse> list = projectCourseService.selectBySearchVo(searchVo);
		
		if (courseId.equals(0L) && !list.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("重复的课程，请检查课程内容和时间是否已经录入过.");
		}
		for (ProjectCourse item: list) {
			if (!item.getCourseId().equals(courseId)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("重复的课程，请检查课程内容和时间是否已经录入过.");
			}
		}
		
		return result;
	}

	@RequestMapping(value = "/checkCredit", method = RequestMethod.GET)
	public AppResultData<Object> checkUserName(
			@RequestParam("pId") Long pId,
			@RequestParam("courseId") Long courseId, 
			@RequestParam("credit") int credit) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Projects project = projectService.selectByPrimaryKey(pId);
		
		int totalCredit = project.getCredit();
		
		ProjectCourseSearchVo searchVo = new ProjectCourseSearchVo();
		searchVo.setpId(pId);
		
		List<ProjectCourse> list = projectCourseService.selectBySearchVo(searchVo);
		
		int totalCourseCredit = 0;
		
		for (ProjectCourse item: list) {
			if (item.getCourseId().equals(courseId)) {
				totalCourseCredit = totalCourseCredit + credit;
			} else {
				totalCourseCredit = totalCourseCredit + item.getCredit();
			}
		}
		
		if (courseId.equals(0L)) {
			totalCourseCredit = totalCourseCredit + credit;
		}
		
		if (totalCourseCredit > totalCredit) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("项目学分为"+ totalCredit + ",当前课程学分总和为"+ totalCourseCredit +", 已超出.");
		}
		
		return result;
	}
	
	@AuthPassport
	@RequestMapping(value = "/course-export", method = { RequestMethod.GET })
	public String export(HttpServletRequest request, HttpServletResponse response, ProjectCourseSearchVo searchVo) throws IOException {

		Long pId = searchVo.getpId();
		Projects project = projectService.selectByPrimaryKey(pId);
		
		List<ProjectCourse> list = projectCourseService.selectBySearchVo(searchVo);

		
		String cpath = request.getSession().getServletContext().getRealPath("/WEB-INF") + "/attach/";
		String templateName = "会议日程导入Excel模板.xls";
		
		HssExcelTools excel = new HssExcelTools(cpath + templateName, 0);
		HSSFSheet sh = excel.getHssSheet();
		
		int rowNum = 1;
		
		for (int i = 0 ; i < list.size(); i++) {
			ProjectCourse item = list.get(i);
			ProjectCourseVo vo = projectCourseService.getVo(item);
			
			HSSFRow rowData = sh.createRow(rowNum);
			
			for(int j = 0; j <= 7; j++) {
				rowData.createCell(j);
				HSSFCell c = rowData.getCell(j);
				c.setCellType(Cell.CELL_TYPE_STRING);
			}
			
			//日期
			this.setCellValueForString(rowData, 0, vo.getCourseDate());
			
			//开始时间
			this.setCellValueForString(rowData, 1, vo.getStartTime());
			
			//结束时间
			this.setCellValueForString(rowData, 2, vo.getEndTime());
			
			//内容
			this.setCellValueForString(rowData, 3, vo.getContent());
			
			//教师
			this.setCellValueForString(rowData, 4, vo.getTeacher());
			
			//职称
			this.setCellValueForString(rowData, 5, vo.getTitleStr());
			
			//单位
			this.setCellValueForString(rowData, 6, vo.getOrgName());
			
			//类型
			this.setCellValueForString(rowData, 7, vo.getCourseType());
			
			rowNum++;
		}
		
		String fileName = project.getName() + "-会议日程.xls";
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
