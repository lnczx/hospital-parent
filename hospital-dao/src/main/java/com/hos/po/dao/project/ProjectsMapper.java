package com.hos.po.dao.project;

import java.util.List;

import com.hos.po.model.project.Projects;
import com.hos.vo.project.ProjectSearchVo;

public interface ProjectsMapper {
    int deleteByPrimaryKey(Long pId);

    int insert(Projects record);

    int insertSelective(Projects record);

    Projects selectByPrimaryKey(Long pId);

    int updateByPrimaryKeySelective(Projects record);

    int updateByPrimaryKey(Projects record);
    
    List<Projects> selectByListPage(ProjectSearchVo searchVo);
    
    List<Projects> selectBySearchVo(ProjectSearchVo searchVo);
}