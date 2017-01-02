package com.hos.service.impl.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hos.common.ConstantMsg;
import com.hos.common.Constants;
import com.hos.po.dao.project.ProjectCourseMapper;
import com.hos.po.model.dict.DictOrgs;
import com.hos.po.model.dict.Dicts;
import com.hos.po.model.project.ProjectCourse;
import com.hos.service.dict.DictOrgService;
import com.hos.service.dict.DictService;
import com.hos.service.project.ProjectCourseService;
import com.hos.vo.project.ProjectCourseSearchVo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Service
public class ProjectCourseServiceImpl implements ProjectCourseService {

	@Autowired
	private ProjectCourseMapper projectCourseMapper;

	@Autowired
	private DictService dictService;
	
	@Autowired
	private DictOrgService dictOrgService;

	@Override
	public int insertSelective(ProjectCourse record) {
		return projectCourseMapper.insert(record);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return projectCourseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ProjectCourse record) {
		return projectCourseMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public ProjectCourse initProjectCourse() {
		ProjectCourse record = new ProjectCourse();
		record.setCourseId(0L);
		record.setpId(0L);
		record.setCourseDate("");
		record.setStartTime("");
		record.setEndTime("");
		record.setContent("");
		record.setTeacher("");
		record.setTitleStr("");
		record.setTitleId(0L);
		record.setOrgName("");
		record.setOrgId(0L);
		record.setCourseType("");
		record.setCredit((short) 0);
		record.setFileName("");
		record.setAdminId(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public ProjectCourse selectByPrimaryKey(Long id) {
		return projectCourseMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ProjectCourse> selectBySearchVo(ProjectCourseSearchVo searchVo) {
		return projectCourseMapper.selectBySearchVo(searchVo);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(ProjectCourseSearchVo searchVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ProjectCourse> list = projectCourseMapper.selectByListPage(searchVo);
		PageInfo info = new PageInfo(list);
		return info;
	}

	// 导入项目excel, 校验excel表格情况
	//
	@SuppressWarnings("unchecked")
	@Override
	public AppResultData<Object> validateProjectCourseImport(List<Object> excelDatas) throws Exception {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		// 1. 检测数据是否为空
		if (excelDatas.isEmpty() || excelDatas.size() == 1) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("Excel表格数据为空，请下载模板后填写.");
			return result;
		}

		// 2. 检测表头是否正确
		List<String> tableHeaderDatas = (List<String>) excelDatas.get(0);
		String tableHeaderError = this.validateTableHeader(tableHeaderDatas);
		if (!StringUtil.isEmpty(tableHeaderError)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(tableHeaderError);
			return result;
		}

		// 3. 检测表格数据是否正确.
		List<Object> validateDatas = this.validateDatas(excelDatas);
		if (!validateDatas.isEmpty() && validateDatas.size() > 0) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("表格数据填写有误，请查看");
			result.setData(validateDatas);
			return result;
		}

		return result;
	}

	// 导入项目excel，检测有重复的数据
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> checkDuplication(Long pId, List<Object> excelDatas) throws Exception {

		
		List<Object> result = new ArrayList<Object>();
		for (int i = 1; i < excelDatas.size(); i++) {
			List<String> item = (List<String>) excelDatas.get(i);

			int s = item.size();
			item.add(s, String.valueOf(i + 1));
			item.add(s + 1, "<font color='green'>新增</font>");
			String courseDateStr = item.get(0).trim();
			Date courseDateObj = DateUtil.parse(courseDateStr);
			String courseDate = DateUtil.format(courseDateObj, "yyyy-MM-dd");
			String startTime = item.get(1).trim();
			String endTime = item.get(2).trim();
			ProjectCourseSearchVo searchVo = new ProjectCourseSearchVo();
			searchVo.setpId(pId);
			searchVo.setCourseDate(courseDate);
			searchVo.setStartTime(startTime);
			searchVo.setEndTime(endTime);
			
			List<ProjectCourse> list = this.selectBySearchVo(searchVo);

			if (!list.isEmpty()) {
				item.set(s + 1, "<font color='red'>修改</font>");
			}
			result.add(item);
		}

		return result;
	}

	// 校验是否为空或者是否为正确的格式。
	@SuppressWarnings("unchecked")
	private List<Object> validateDatas(List<Object> datas) {
		List<Object> result = new ArrayList<Object>();

		// int totalNum = 0;
		for (int i = 1; i < datas.size(); i++) {
			List<String> item = (List<String>) datas.get(i);
			int errorNum = 0;
			
			String courseDate = item.get(0).trim();
			if (StringUtil.isEmpty(courseDate)) {
				item.set(0, "<font color='red'>日期为必填项</font>");
				errorNum++;
			} else {
				if (!DateUtil.isDate(courseDate)) {
					item.set(0, "<font color='red'>日期格式为YYYY-MM-DD</font>");
					errorNum++;
				}
			}
			
			String startTime = item.get(1).trim();
			if (StringUtil.isEmpty(startTime)) {
				item.set(1, "<font color='red'>开始时间为必填项</font>");
				errorNum++;
			} else {
				if (!DateUtil.isDate(startTime, "HH:mm")) {
					item.set(1, "<font color='red'>开始时间格式为HH:mm</font>");
					errorNum++;
				}
			}
			
			String endTime = item.get(2).trim();
			if (StringUtil.isEmpty(endTime)) {
				item.set(2, "<font color='red'>结束时间为必填项</font>");
				errorNum++;
			} else {
				if (!DateUtil.isDate(endTime, "HH:mm")) {
					item.set(2, "<font color='red'>结束时间格式为HH:mm</font>");
					errorNum++;
				}
			}
			if (StringUtil.isEmpty(item.get(3).trim())) {
				item.set(3, "<font color='red'>内容为必填项</font>");
				errorNum++;
			}
			if (StringUtil.isEmpty(item.get(4).trim())) {
				item.set(4, "<font color='red'>教师为必填项</font>");
				errorNum++;
			}
			if (StringUtil.isEmpty(item.get(7).trim())) {
				item.set(7, "<font color='red'>类型为必填项</font>");
				errorNum++;
			}

			if (errorNum > 0) {
				item.add(8, String.valueOf(i + 1));
				result.add(item);
			}

		}

		return result;
	}

	private String validateTableHeader(List<String> datas) {
		String error = "";

		Boolean tableHeaderFalg = true;

		if (datas.isEmpty() || datas.size() < 7) {
			tableHeaderFalg = false;
			// System.out.println("表格表头不对，请按照模板的格式填写.");
			error = "表格表头不对，请按照模板的格式填写.";
			return error;
		}

		if (!datas.get(0).equals("日期(必填)"))
			tableHeaderFalg = false;
		if (!datas.get(1).equals("开始时间(必填)"))
			tableHeaderFalg = false;
		if (!datas.get(2).equals("结束时间(必填)"))
			tableHeaderFalg = false;
		if (!datas.get(3).equals("内容(必填)"))
			tableHeaderFalg = false;
		if (!datas.get(4).equals("教师(必填)"))
			tableHeaderFalg = false;
		if (!datas.get(5).equals("职称"))
			tableHeaderFalg = false;
		if (!datas.get(6).equals("单位"))
			tableHeaderFalg = false;
		if (!datas.get(7).equals("类型(必填)"))
			tableHeaderFalg = false;


		if (!tableHeaderFalg) {
			System.out.println("表格表头不对，请按照模板的格式填写.");
			error = "表格表头不对，请按照模板的格式填写.";
		}

		return error;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AppResultData<Object> doProjectCousreImport(Long pId, List<Object> datas, String fileName, Long adminId) throws Exception {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		

		for (int i = 1; i < datas.size(); i++) {
			List<String> item = (List<String>) datas.get(i);
			
			String courseDateStr = item.get(0).trim();
			Date courseDateObj = DateUtil.parse(courseDateStr);
			String courseDate = DateUtil.format(courseDateObj, "yyyy-MM-dd");
			
			
			String startTime = item.get(1).trim();
			String endTime = item.get(2).trim();
			String content = item.get(3).trim();
			String teacher = item.get(4).trim();
			String titleStr = item.get(5).trim();
			Long titleId = 0L;
			Dicts dictTitle = dictService.findTitleByName(titleStr);
			if (dictTitle != null) titleId = dictTitle.getId();
			
			String orgName = item.get(6).trim();
			DictOrgs org = dictOrgService.findByName(orgName);
			Long orgId = 0L;
			if (org != null) orgId = org.getOrgId();
			
			String courseType = item.get(7).trim();

			ProjectCourseSearchVo searchVo = new ProjectCourseSearchVo();
			searchVo.setpId(pId);
			searchVo.setCourseDate(courseDate);
			searchVo.setStartTime(startTime);
			searchVo.setEndTime(endTime);

			ProjectCourse record = this.initProjectCourse();
			List<ProjectCourse> list = this.selectBySearchVo(searchVo);

			if (!list.isEmpty()) {
				record = list.get(0);
			}

			record.setpId(pId);
			record.setCourseDate(courseDate);
			record.setStartTime(startTime);
			record.setEndTime(endTime);
			record.setContent(content);
			record.setTeacher(teacher);
			record.setTitleStr(titleStr);
			record.setTitleId(titleId);
			record.setOrgName(orgName);
			record.setOrgId(orgId);
			record.setCourseType(courseType);
			record.setFileName(fileName);
			record.setAdminId(adminId);

			if (record.getCourseId().equals(0L)) {
				this.insertSelective(record);
			} else {
				record.setUpdateTime(TimeStampUtil.getNowSecond());
				this.updateByPrimaryKeySelective(record);
			}
		}

		return result;
	}

}
