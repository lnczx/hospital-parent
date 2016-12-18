package com.hos.po.dao.project;

import java.util.List;

import com.hos.po.model.project.ProjectCourse;
import com.hos.vo.project.ProjectSearchVo;

public interface ProjectCourseMapper {
	
    int deleteByPrimaryKey(Long courseId);

    int insert(ProjectCourse record);

    int insertSelective(ProjectCourse record);

    ProjectCourse selectByPrimaryKey(Long courseId);

    int updateByPrimaryKeySelective(ProjectCourse record);

    int updateByPrimaryKey(ProjectCourse record);
    
    List<ProjectCourse> selectByListPage(ProjectSearchVo searchVo);
    
    List<ProjectCourse> selectBySearchVo(ProjectSearchVo searchVo);
}