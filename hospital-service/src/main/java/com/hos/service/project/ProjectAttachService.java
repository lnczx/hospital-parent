package com.hos.service.project;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hos.po.model.project.ProjectAttach;
import com.hos.vo.project.ProjectSearchVo;



public interface ProjectAttachService {

	int insertSelective(ProjectAttach record);

	int deleteByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ProjectAttach record);

	ProjectAttach initProjectAttach();

	ProjectAttach selectByPrimaryKey(Long id);

	List<ProjectAttach> selectBySearchVo(ProjectSearchVo searchVo);

	PageInfo selectByListPage(ProjectSearchVo searchVo, int pageNum, int pageSize);

}
