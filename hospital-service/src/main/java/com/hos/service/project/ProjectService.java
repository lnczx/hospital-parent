package com.hos.service.project;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hos.po.model.project.Projects;
import com.hos.vo.project.ProjectSearchVo;



public interface ProjectService {

	int insertSelective(Projects record);

	int deleteByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Projects record);

	Projects initProject();

	Projects selectByPrimaryKey(Long id);

	List<Projects> selectBySearchVo(ProjectSearchVo searchVo);

	PageInfo selectByListPage(ProjectSearchVo searchVo, int pageNum, int pageSize);

}
