package com.hos.po.dao.student;

import java.util.List;

import com.hos.po.model.student.Students;
import com.hos.vo.project.ProjectStudentSearchVo;

public interface StudentsMapper {
    int deleteByPrimaryKey(Long stuId);

    int insert(Students record);

    int insertSelective(Students record);

    Students selectByPrimaryKey(Long stuId);

    int updateByPrimaryKeySelective(Students record);

    int updateByPrimaryKey(Students record);
    
    List<Students> selectBySearchVo(ProjectStudentSearchVo searchVo);
    
    List<Students> selectByListPage(ProjectStudentSearchVo searchVo);
}