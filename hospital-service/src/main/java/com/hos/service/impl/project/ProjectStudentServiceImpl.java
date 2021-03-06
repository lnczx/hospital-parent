package com.hos.service.impl.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hos.common.ConstantMsg;
import com.hos.common.Constants;
import com.hos.po.dao.project.ProjectStudentMapper;
import com.hos.po.model.dict.DictOrgs;
import com.hos.po.model.dict.Dicts;
import com.hos.po.model.project.ProjectStudent;
import com.hos.po.model.student.Students;
import com.hos.service.dict.DictOrgService;
import com.hos.service.dict.DictService;
import com.hos.service.project.ProjectStudentService;
import com.hos.service.student.StudentService;
import com.hos.vo.project.ProjectStudentSearchVo;
import com.hos.vo.project.ProjectStudentVo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.RegexUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;


@Service
public class ProjectStudentServiceImpl implements ProjectStudentService {

	@Autowired
	private ProjectStudentMapper projectStudentMapper;

	@Autowired
	private DictService dictService;
		
	@Autowired
	private DictOrgService dictOrgService;
	
	@Autowired
	private StudentService studentService;
	
	@Override
	public int insertSelective(ProjectStudent record) {
		return projectStudentMapper.insert(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return projectStudentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ProjectStudent record) {
		return projectStudentMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public ProjectStudent initProjectStudent() {
		ProjectStudent record = new ProjectStudent();

		record.setId(0L);
		record.setpId(0L);
		record.setStuId(0L);
		record.setName("");
		record.setMobile("");
		record.setSex("");
		record.setNationId(0L);
		record.setBirthDate("");
		record.setEmail("");
		record.setIdType(0L);
		record.setIdCard("");
		record.setTitleId(0L);
		record.setTitleStr("");
		record.setOrgId(0L);
		record.setOrgName("");
		record.setCityId(0L);
		record.setCityName("");
		record.setDutyName("");
		record.setDegreeName("");
		record.setDegreeId(0L);
		record.setEduId(0L);
		record.setAddr("");		
		record.setZipCode("");
		record.setAdminId(0L);
		record.setFileName("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public ProjectStudent selectByPrimaryKey(Long id) {
		return projectStudentMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<ProjectStudent> selectBySearchVo(ProjectStudentSearchVo searchVo) {
		return projectStudentMapper.selectBySearchVo(searchVo);
	}
	
	@Override
	public ProjectStudentVo getVo(ProjectStudent item) {
		ProjectStudentVo vo = new ProjectStudentVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
		
		String idTypeName = "";
		if (vo.getIdType() != null && vo.getIdType() > 0L) {
			Dicts dict = dictService.findById(vo.getIdType(), Constants.DICT_ID_TYPE);
			if (dict == null) idTypeName = dict.getName();
		}
		vo.setIdTypeName(idTypeName);
		
		String eduName = "";
		if (vo.getEduId() != null && vo.getEduId() > 0L) {
			Dicts dict = dictService.findById(vo.getIdType(), Constants.DICT_EDU);
			if (dict == null) eduName = dict.getName();
		}
		vo.setEduName(eduName);
		
		String degreeName = "";
		if (vo.getDegreeId() != null && vo.getDegreeId() > 0L) {
			Dicts dict = dictService.findById(vo.getIdType(), Constants.DICT_DEGREE);
			if (dict == null) degreeName = dict.getName();
		}
		vo.setDegreeName(degreeName);
		
		String cityName = "";
		if (vo.getDegreeId() != null && vo.getDegreeId() > 0L) {
			Dicts dict = dictService.findById(vo.getIdType(), Constants.DICT_AREA);
			if (dict == null) cityName = dict.getName();
		}
		vo.setCityName(cityName);
		
		return vo;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(ProjectStudentSearchVo searchVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ProjectStudent> list = projectStudentMapper.selectByListPage(searchVo);
		PageInfo info = new PageInfo(list);
		return info;
	}
	
	// 导入项目excel, 校验excel表格情况
	//
	@SuppressWarnings("unchecked")
	@Override
	public AppResultData<Object> validateProjectStudentImport(List<Object> excelDatas) throws Exception {
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
			item.add(6, String.valueOf(i + 1));
			item.add(7, "<font color='green'>新增</font>");
			String name = item.get(0).trim();
			String mobile = item.get(5).trim();
			ProjectStudentSearchVo searchVo = new ProjectStudentSearchVo();
			searchVo.setpId(pId);
			searchVo.setName(name);
			searchVo.setMobile(mobile);
			List<ProjectStudent> list = this.selectBySearchVo(searchVo);

			if (!list.isEmpty()) {
				item.set(7, "<font color='red'>修改</font>");
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
			
			if (StringUtil.isEmpty(item.get(0).trim())) {
				item.set(0, "<font color='red'>姓名为必填项</font>");
				errorNum++;
			}
			
			String sex = item.get(1).trim();
			if (StringUtil.isEmpty(sex)) {
				item.set(1, "<font color='red'>职称为必填项</font>");
				errorNum++;
			} 
			
			if (StringUtil.isEmpty(item.get(2).trim())) {
				item.set(2, "<font color='red'>职务为必填项</font>");
				errorNum++;
			}
			
			if (StringUtil.isEmpty(item.get(3).trim())) {
				item.set(3, "<font color='red'>学历为必填项</font>");
				errorNum++;
			}
			
			if (StringUtil.isEmpty(item.get(4).trim())) {
				item.set(4, "<font color='red'>单位为必填项</font>");
				errorNum++;
			}
			

			
			if (StringUtil.isEmpty(item.get(5).trim())) {
				item.set(5, "<font color='red'>联系电话为必填项</font>");
				errorNum++;
			} else {
				String mobile = item.get(5).trim();
				if (!RegexUtil.isMobile(mobile)) {
					item.set(5, "<font color='red'>手机号格式不正确</font>");
					errorNum++;
				}
			}
			
			if (errorNum > 0) {
				item.add(6, String.valueOf(i + 1));
				result.add(item);
			}

		}

		return result;
	}

	private String validateTableHeader(List<String> datas) {
		String error = "";

		Boolean tableHeaderFalg = true;

		if (datas.isEmpty() || datas.size() < 6 ) {
			tableHeaderFalg = false;
			// System.out.println("表格表头不对，请按照模板的格式填写.");
			error = "表格表头不对，请按照模板的格式填写.";
			return error;
		}

		if (datas.get(0).indexOf("姓名") < 0)
			tableHeaderFalg = false;
		if (datas.get(1).indexOf("职称") < 0)
			tableHeaderFalg = false;
		if (datas.get(2).indexOf("职务") < 0)
			tableHeaderFalg = false;
		if (datas.get(3).indexOf("学历") < 0)
			tableHeaderFalg = false;
		if (datas.get(4).indexOf("单位") < 0)
			tableHeaderFalg = false;
		if (datas.get(5).indexOf("联系电话") < 0)
			tableHeaderFalg = false;
		
		if (!tableHeaderFalg) {
			System.out.println("表格表头不对，请按照模板的格式填写.");
			error = "表格表头不对，请按照模板的格式填写.";
		}

		return error;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AppResultData<Object> doProjectStudentImport(Long pId, List<Object> datas, String fileName, Long adminId) throws Exception {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());

		for (int i = 1; i < datas.size(); i++) {
			List<String> item = (List<String>) datas.get(i);
			
			String name = item.get(0).trim();
			String titleStr = item.get(1).trim();
			Long titleId = 0L;
			Dicts dictTitle = dictService.findByName(titleStr, Constants.DICT_TITLE);
			if (dictTitle != null) titleId = dictTitle.getId();
			
			String dutyName = item.get(2).trim();
			
			String eduStr = item.get(3).trim();
			Long eduId = 0L;
			Dicts dictEdu = dictService.findByName(eduStr, Constants.DICT_EDU);
			if (dictEdu != null) eduId = dictEdu.getId();
			
			String orgName = item.get(4).trim();
			DictOrgs org = dictOrgService.findByName(orgName);
			Long orgId = 0L;
			if (org != null) orgId = org.getOrgId();

			String mobile = item.get(5).trim();			

			ProjectStudentSearchVo searchVo = new ProjectStudentSearchVo();
			searchVo.setName(name);
			searchVo.setMobile(mobile);
			
			//先插入学员表，再插入项目学员表
			Students student = studentService.initStudents();
			List<Students> list = studentService.selectBySearchVo(searchVo);

			if (!list.isEmpty()) {
				student = list.get(0);
			}

			student.setName(name);
			student.setTitleId(titleId);
			student.setTitleStr(titleStr);
			student.setDutyName(dutyName);
			student.setEduName(eduStr);
			student.setEduId(eduId);
			student.setOrgId(orgId);
			student.setOrgName(orgName);
			student.setMobile(mobile);
			student.setAdminId(adminId);
			if (student.getStuId().equals(0L)) {
				studentService.insertSelective(student);
			} else {
				student.setUpdateTime(TimeStampUtil.getNowSecond());
				studentService.updateByPrimaryKeySelective(student);
			}
			
			
			Long stuId = student.getStuId();
			searchVo = new ProjectStudentSearchVo();
			searchVo.setpId(pId);
			searchVo.setName(name);
			searchVo.setMobile(mobile);
			
			ProjectStudent record = this.initProjectStudent();
			List<ProjectStudent> ps = this.selectBySearchVo(searchVo);

			if (!ps.isEmpty()) {
				record = ps.get(0);
			}
			
			record.setpId(pId);
			record.setStuId(stuId);
			record.setName(name);
			record.setTitleId(titleId);
			record.setTitleStr(titleStr);
			record.setDutyName(dutyName);
			record.setEduName(eduStr);
			record.setEduId(eduId);
			
			record.setOrgId(orgId);
			record.setOrgName(orgName);
			record.setMobile(mobile);
			record.setFileName(fileName);
			if (record.getId().equals(0L)) {
				this.insertSelective(record);
			} else {
				record.setUpdateTime(TimeStampUtil.getNowSecond());
				this.updateByPrimaryKeySelective(record);
			}
			
		}

		return result;
	}
	
}
