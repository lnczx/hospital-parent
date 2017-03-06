package com.hos.service.student;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hos.po.model.student.Students;
import com.hos.vo.project.ProjectStudentSearchVo;



public interface StudentService {

	int insertSelective(Students record);

	int deleteByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Students record);

	Students initStudents();

	Students selectByPrimaryKey(Long id);

	List<Students> selectBySearchVo(ProjectStudentSearchVo searchVo);

	PageInfo selectByListPage(ProjectStudentSearchVo searchVo, int pageNum, int pageSize);

}
