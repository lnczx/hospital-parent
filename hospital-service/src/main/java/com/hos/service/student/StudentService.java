package com.hos.service.student;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hos.po.model.student.Students;
import com.hos.vo.student.StudentSearchVo;



public interface StudentService {

	int insertSelective(Students record);

	int deleteByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(com.hos.po.model.student.Students record);

	Students initStudents();

	Students selectByPrimaryKey(Long id);

	List<Students> selectBySearchVo(StudentSearchVo searchVo);

	PageInfo selectByListPage(StudentSearchVo searchVo, int pageNum, int pageSize);

}
