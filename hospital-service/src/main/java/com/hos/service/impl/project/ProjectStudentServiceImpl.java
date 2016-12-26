package com.hos.service.impl.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hos.po.dao.project.ProjectStudentMapper;
import com.hos.po.model.project.ProjectStudent;
import com.hos.service.dict.DictService;
import com.hos.service.project.ProjectStudentService;
import com.hos.vo.project.ProjectSearchVo;
import com.meijia.utils.TimeStampUtil;


@Service
public class ProjectStudentServiceImpl implements ProjectStudentService {

	@Autowired
	private ProjectStudentMapper projectStudentMapper;

	@Autowired
	private DictService dictService;
	
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
		record.setMobile("");
		record.setSex("");
		record.setEmail("");
		record.setTitleId(0L);
		record.setOrgId(0L);
		record.setCityId(0L);
		record.setAddr("");		
		record.setAdminId(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public ProjectStudent selectByPrimaryKey(Long id) {
		return projectStudentMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<ProjectStudent> selectBySearchVo(ProjectSearchVo searchVo) {
		return projectStudentMapper.selectBySearchVo(searchVo);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(ProjectSearchVo searchVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ProjectStudent> list = projectStudentMapper.selectByListPage(searchVo);
		PageInfo info = new PageInfo(list);
		return info;
	}
	
}
