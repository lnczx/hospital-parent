package com.hos.service.project;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hos.po.model.project.ProjectCourse;
import com.hos.vo.project.ProjectCourseSearchVo;
import com.simi.vo.AppResultData;



public interface ProjectCourseService {

	int insertSelective(ProjectCourse record);

	int deleteByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ProjectCourse record);

	ProjectCourse initProjectCourse();

	ProjectCourse selectByPrimaryKey(Long id);

	List<ProjectCourse> selectBySearchVo(ProjectCourseSearchVo searchVo);

	PageInfo selectByListPage(ProjectCourseSearchVo searchVo, int pageNum, int pageSize);

	AppResultData<Object> validateProjectCourseImport(List<Object> excelDatas) throws Exception;

	List<Object> checkDuplication(Long pId, List<Object> excelDatas) throws Exception;

	AppResultData<Object> doProjectCousreImport(Long pId, List<Object> datas, String fileName, Long adminId) throws Exception;

}