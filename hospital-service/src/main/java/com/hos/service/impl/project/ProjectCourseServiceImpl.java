package com.hos.service.impl.project;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hos.po.dao.project.ProjectCourseMapper;
import com.hos.po.model.project.ProjectCourse;
import com.hos.service.dict.DictService;
import com.hos.service.project.ProjectCourseService;
import com.hos.vo.project.ProjectSearchVo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;


@Service
public class ProjectCourseServiceImpl implements ProjectCourseService {

	@Autowired
	private ProjectCourseMapper projectCourseMapper;

	@Autowired
	private DictService dictService;
	
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
		
		Date nowDate = DateUtil.getNowOfDate();
		
		record.setCourseDate(nowDate);
		record.setStartTime(nowDate);
		record.setEndTime(nowDate);
		record.setCourseDatetime(0L);
		record.setContent("");
		record.setTeacher("");
		record.setTitleId(0L);
		record.setOrgId(0L);
		record.setCourseType(0L);
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
	public List<ProjectCourse> selectBySearchVo(ProjectSearchVo searchVo) {
		return projectCourseMapper.selectBySearchVo(searchVo);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(ProjectSearchVo searchVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ProjectCourse> list = projectCourseMapper.selectByListPage(searchVo);
		PageInfo info = new PageInfo(list);
		return info;
	}
	
}
