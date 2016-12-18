package com.hos.service.project;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hos.po.model.project.ProjectStudent;
import com.hos.vo.project.ProjectSearchVo;



public interface ProjectStudentService {

	int insertSelective(ProjectStudent record);

	int deleteByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ProjectStudent record);

	ProjectStudent initProjectStudent();

	ProjectStudent selectByPrimaryKey(Long id);

	List<ProjectStudent> selectBySearchVo(ProjectSearchVo searchVo);

	PageInfo selectByListPage(ProjectSearchVo searchVo, int pageNum, int pageSize);

}
