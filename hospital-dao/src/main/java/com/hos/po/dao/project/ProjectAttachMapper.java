package com.hos.po.dao.project;

import java.util.List;

import com.hos.po.model.project.ProjectAttach;
import com.hos.vo.project.ProjectSearchVo;

public interface ProjectAttachMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProjectAttach record);

    int insertSelective(ProjectAttach record);

    ProjectAttach selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProjectAttach record);

    int updateByPrimaryKey(ProjectAttach record);
    
    List<ProjectAttach> selectBySearchVo(ProjectSearchVo searchVo);
    
    List<ProjectAttach> selectByListPage(ProjectSearchVo searchVo);
}