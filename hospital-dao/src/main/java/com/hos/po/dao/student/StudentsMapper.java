package com.hos.po.dao.student;

import java.util.List;

import com.hos.po.model.student.Students;
import com.hos.vo.student.StudentSearchVo;

public interface StudentsMapper {
    int deleteByPrimaryKey(Long stuId);

    int insert(Students record);

    int insertSelective(Students record);

    Students selectByPrimaryKey(Long stuId);

    int updateByPrimaryKeySelective(Students record);

    int updateByPrimaryKey(Students record);
    
    List<Students> selectByListPage(StudentSearchVo searchVo);
    
    List<Students> selectBySearchVo(StudentSearchVo searchVo);
}