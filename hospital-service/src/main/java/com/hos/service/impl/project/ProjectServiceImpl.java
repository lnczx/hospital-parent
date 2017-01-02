package com.hos.service.impl.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hos.common.ConstantMsg;
import com.hos.common.Constants;
import com.hos.po.dao.project.ProjectsMapper;
import com.hos.po.model.dict.DictOrgs;
import com.hos.po.model.project.Projects;
import com.hos.service.dict.DictOrgService;
import com.hos.service.dict.DictService;
import com.hos.service.project.ProjectCourseService;
import com.hos.service.project.ProjectService;
import com.hos.vo.project.ProjectSearchVo;
import com.hos.vo.project.ProjectVo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.RegexUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.vo.AppResultData;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectsMapper projectsMapper;
	
	@Autowired
	private ProjectCourseService projectCourseService;

	@Autowired
	private DictService dictService;
	
	@Autowired
	private DictOrgService dictOrgService;
	
	
	@Override
	public int insertSelective(Projects record) {
		return projectsMapper.insert(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return projectsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Projects record) {
		return projectsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Projects initProject() {
		Projects record = new Projects();
		
		record.setpId(0L);
		record.setpYear(DateUtil.getNowOfDate().getYear());
		record.setpNo("");
		record.setName("");
		record.setOrgId(0L);
		record.setOrgName("");
		record.setpHeader("");
		record.setpHeaderTel("");
		record.setAddr("");
		record.setCredit((short) 0);
		record.setNumRecruit(0);
		record.setNumTerm((short) 0);
		record.setStatus((short) 0);		
		record.setFileName("");
		record.setAdminId(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public Projects selectByPrimaryKey(Long id) {
		return projectsMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<Projects> selectBySearchVo(ProjectSearchVo searchVo) {
		return projectsMapper.selectBySearchVo(searchVo);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(ProjectSearchVo searchVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Projects> list = projectsMapper.selectByListPage(searchVo);
		PageInfo info = new PageInfo(list);
		return info;
	}
	
	@Override
	public ProjectVo getVo(Projects item) {
		ProjectVo vo = new ProjectVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
		
		//得到起止时间.
		String dateRange = "";
		
		Map<String, String> dataRanges = projectCourseService.selectByDateRange(item.getpId());
		
		if (dataRanges != null) {
			if ( dataRanges.get("minDate") != null) {
				dateRange+= dataRanges.get("minDate");
			}
			
			if ( dataRanges.get("maxDate") != null) {
				dateRange+= "至" + dataRanges.get("maxDate");
			}
		}
		
		vo.setDateRange(dateRange);
		return vo;
	}
	
	
	// 导入项目excel, 校验excel表格情况
	//
	@SuppressWarnings("unchecked")
	@Override
	public AppResultData<Object> validateProjectImport(List<Object> excelDatas) throws Exception {
		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		//1. 检测数据是否为空
		if (excelDatas.isEmpty() || excelDatas.size() == 1) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("Excel表格数据为空，请下载模板后填写.");
			return result;
		}

		//2. 检测表头是否正确
		List<String> tableHeaderDatas = (List<String>) excelDatas.get(0);
		String tableHeaderError = this.validateTableHeader(tableHeaderDatas);
		if (!StringUtil.isEmpty(tableHeaderError)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(tableHeaderError);
			return result;
		}
		
		//3. 检测表格数据是否正确.
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
	public List<Object> checkDuplication(List<Object> excelDatas) throws Exception {
		
		ProjectSearchVo searchVo = new ProjectSearchVo();

		List<Object> result = new ArrayList<Object>();
		for (int i = 1; i < excelDatas.size(); i++) {
			List<String> item = (List<String>) excelDatas.get(i);
			
			int s = item.size();
			item.add(s, String.valueOf(i+1));
			item.add(s + 1, "<font color='green'>新增</font>");
			String pNo = item.get(0).toString().trim();
			String numTermStr = item.get(8).toString().trim();
			Short numTerm = Short.valueOf(numTermStr);
			searchVo.setpNo(pNo);
			searchVo.setNumTerm(numTerm);
			
			List<Projects> list = this.selectBySearchVo(searchVo);
			
			if (!list.isEmpty()) {
				item.set(s + 1, "<font color='red'>修改</font>");
			}
			result.add(item);
		}			
		
		return result;
	}	
	
	
	//校验是否为空或者是否为正确的格式。
	@SuppressWarnings("unchecked")
	private List<Object> validateDatas(List<Object> datas) {
		List<Object> result = new ArrayList<Object>();
		
//		int totalNum = 0;
		for (int i = 1; i < datas.size(); i++) {
			List<String> item = (List<String>) datas.get(i);
			int errorNum = 0;
			
			if (StringUtil.isEmpty(item.get(0).trim())) {
				item.set(0, "<font color='red'>项目编号为必填项</font>");
				errorNum++;
			}
			if (StringUtil.isEmpty(item.get(1).trim())) {
				item.set(1, "<font color='red'>项目名称为必填项</font>");
				errorNum++;
			}
			if (StringUtil.isEmpty(item.get(2).trim())) {
				item.set(2, "<font color='red'>申办单位为必填项</font>");
				errorNum++;
			} else {
				String orgName = item.get(2).trim();
				DictOrgs org = dictOrgService.findByName(orgName);
				if (org == null) {
					item.set(2, "<font color='red'>申办单位不存在</font>");
					errorNum++;
				}
			}
			if (StringUtil.isEmpty(item.get(3).trim())) {
				item.set(3, "<font color='red'>项目负责人为必填项</font>");
				errorNum++;
			}
			if (StringUtil.isEmpty(item.get(4).trim())) {
				item.set(4, "<font color='red'>负责人电话为必填项</font>");
				errorNum++;
			}
			if (StringUtil.isEmpty(item.get(5).trim())) {
				item.set(5, "<font color='red'>举办地点为必填项</font>");
				errorNum++;
			}
			
			String creditStr = item.get(6).trim();
			if (StringUtil.isEmpty(creditStr)) {
				item.set(6, "<font color='red'>授予学分为必填项</font>");
				errorNum++;	
			} else {
				if (!RegexUtil.isInteger(creditStr)) {
					item.set(6, "<font color='red'>授予学分必须为数字</font>");
					errorNum++;	
				}
			}
			
			String numRecruitStr = item.get(7).trim();
			if (StringUtil.isEmpty(numRecruitStr)) {
				item.set(7, "<font color='red'>授予学分必须为数字</font>");
				errorNum++;		
			} else {
				if (!RegexUtil.isInteger(numRecruitStr)) {
					item.set(7, "<font color='red'>拟招人数必须为数字</font>");
					errorNum++;		
				}
			}
			
			String numTermStr = item.get(8).trim();
			if (StringUtil.isEmpty(numTermStr)) {
				item.set(8, "<font color='red'>项目期数为必填项</font>");
				errorNum++;		
			} else {
				if (!RegexUtil.isInteger(numTermStr)) {
					item.set(8, "<font color='red'>项目期数必须为数字</font>");
					errorNum++;		
				}
			}
			
			if (errorNum > 0) {
				item.add(9, String.valueOf(i+1));
				result.add(item);
			}
			
		}		
		
		return result;
	}	
	
	private String validateTableHeader(List<String> datas) {
		String error = "";

		Boolean tableHeaderFalg = true;
		
		if (datas.isEmpty() || datas.size() < 9) {
			tableHeaderFalg = false;
//			System.out.println("表格表头不对，请按照模板的格式填写.");
			error = "表格表头不对，请按照模板的格式填写.";
			return error;
		}
				
		if (!datas.get(0).equals("项目编号")) tableHeaderFalg = false;
		if (!datas.get(1).equals("项目名称")) tableHeaderFalg = false;
		if (!datas.get(2).equals("申办单位")) tableHeaderFalg = false;
		if (!datas.get(3).equals("项目负责人")) tableHeaderFalg = false;
		if (!datas.get(4).equals("负责人电话")) tableHeaderFalg = false;
		if (!datas.get(5).equals("举办地点")) tableHeaderFalg = false;
		if (!datas.get(6).equals("授予学分")) tableHeaderFalg = false;
		if (!datas.get(7).equals("拟招人数")) tableHeaderFalg = false;
		if (!datas.get(8).equals("项目期数")) tableHeaderFalg = false;
		
		if (!tableHeaderFalg) {
			System.out.println("表格表头不对，请按照模板的格式填写.");
			error = "表格表头不对，请按照模板的格式填写.";
		}
		
		return error;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public AppResultData<Object> doProjectImport(List<Object> datas, String fileName, Long adminId) throws Exception {

		AppResultData<Object> result = new AppResultData<Object>( Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		ProjectSearchVo searchVo = new ProjectSearchVo();
		
		for (int i = 1; i < datas.size(); i++) {
			List<String> item = (List<String>) datas.get(i);
			String pNo = item.get(0).toString().trim();
			int pYear = Integer.valueOf(pNo.substring(0, 4)).intValue();
			String name = item.get(1).toString().trim();
			String orgName = item.get(2).toString().trim();
			
			DictOrgs org = dictOrgService.findByName(orgName);
			Long orgId = 0L;
			if (org !=null) orgId = org.getOrgId();
			
			
			String pHeader = item.get(3).toString().trim();
			String pHeaderTel = item.get(4).toString().trim();
			String addr = item.get(5).toString().trim();
			String creditStr = item.get(6).toString().trim();
			Short credit = Short.valueOf(creditStr);
			String numRecruitStr = item.get(7).toString().trim();
			Integer numRecruit = Integer.valueOf(numRecruitStr);
			String numTermStr = item.get(8).toString().trim();
			Short numTerm = Short.valueOf(numTermStr);
			
			
			Projects record = this.initProject();
			
			searchVo.setpNo(pNo);
			searchVo.setNumTerm(numTerm);
			
			List<Projects> list = this.selectBySearchVo(searchVo);
			
			if (!list.isEmpty()) {
				record = list.get(0);
			}
			
			record.setpNo(pNo);
			record.setpYear(pYear);
			record.setName(name);
			record.setOrgName(orgName);
			record.setOrgId(orgId);
			record.setpHeader(pHeader);
			record.setpHeaderTel(pHeaderTel);
			record.setAddr(addr);
			record.setCredit(credit);
			record.setNumRecruit(numRecruit);
			record.setNumTerm(numTerm);
			record.setFileName(fileName);
			record.setAdminId(adminId);
			if (record.getpId().equals(0L)) {
				this.insertSelective(record);
			} else {
				record.setUpdateTime(TimeStampUtil.getNowSecond());
				this.updateByPrimaryKeySelective(record);
			}
		}
		
		return result;
	}
	
}
