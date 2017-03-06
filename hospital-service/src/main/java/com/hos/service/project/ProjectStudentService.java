package com.hos.service.project;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hos.po.model.project.ProjectStudent;
import com.hos.vo.project.ProjectStudentSearchVo;
import com.hos.vo.project.ProjectStudentVo;
import com.simi.vo.AppResultData;



public interface ProjectStudentService {

	int insertSelective(ProjectStudent record);

	int deleteByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ProjectStudent record);

	ProjectStudent initProjectStudent();

	ProjectStudent selectByPrimaryKey(Long id);

	List<ProjectStudent> selectBySearchVo(ProjectStudentSearchVo searchVo);

	PageInfo selectByListPage(ProjectStudentSearchVo searchVo, int pageNum, int pageSize);

	AppResultData<Object> validateProjectStudentImport(List<Object> excelDatas) throws Exception;

	List<Object> checkDuplication(Long pId, List<Object> excelDatas) throws Exception;

	AppResultData<Object> doProjectStudentImport(Long pId, List<Object> datas, String fileName, Long adminId) throws Exception;

	ProjectStudentVo getVo(ProjectStudent record);

	

}
