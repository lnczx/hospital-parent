package com.hos.po.dao.project;

import java.util.List;
import java.util.Map;

import com.hos.po.model.project.ProjectCourse;
import com.hos.vo.project.ProjectCourseSearchVo;

public interface ProjectCourseMapper {
	
    int deleteByPrimaryKey(Long courseId);

    int insert(ProjectCourse record);

    int insertSelective(ProjectCourse record);

    ProjectCourse selectByPrimaryKey(Long courseId);

    int updateByPrimaryKeySelective(ProjectCourse record);

    int updateByPrimaryKey(ProjectCourse record);
    
    List<ProjectCourse> selectByListPage(ProjectCourseSearchVo searchVo);
    
    List<ProjectCourse> selectBySearchVo(ProjectCourseSearchVo searchVo);

	Map<String, String> selectByDateRange(Long pId);
}