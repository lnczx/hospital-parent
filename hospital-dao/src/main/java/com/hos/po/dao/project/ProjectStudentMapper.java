package com.hos.po.dao.project;

import java.util.List;

import com.hos.po.model.project.ProjectStudent;
import com.hos.vo.project.ProjectSearchVo;

public interface ProjectStudentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProjectStudent record);

    int insertSelective(ProjectStudent record);

    ProjectStudent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProjectStudent record);

    int updateByPrimaryKey(ProjectStudent record);
    
    List<ProjectStudent> selectByListPage(ProjectSearchVo searchVo);
    
    List<ProjectStudent> selectBySearchVo(ProjectSearchVo searchVo);
}