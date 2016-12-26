package com.hos.service.project;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hos.po.model.project.ProjectCourse;
import com.hos.vo.project.ProjectSearchVo;



public interface ProjectCourseService {

	int insertSelective(ProjectCourse record);

	int deleteByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ProjectCourse record);

	ProjectCourse initProjectCourse();

	ProjectCourse selectByPrimaryKey(Long id);

	List<ProjectCourse> selectBySearchVo(ProjectSearchVo searchVo);

	PageInfo selectByListPage(ProjectSearchVo searchVo, int pageNum, int pageSize);

}
