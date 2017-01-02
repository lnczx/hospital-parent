package com.hos.service.impl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hos.po.dao.student.StudentsMapper;
import com.hos.po.model.student.Students;
import com.hos.service.dict.DictService;
import com.hos.service.student.StudentService;
import com.hos.vo.project.ProjectStudentSearchVo;
import com.meijia.utils.TimeStampUtil;


@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentsMapper studentMapper;

	@Autowired
	private DictService dictService;
	
	@Override
	public int insertSelective(Students record) {
		return studentMapper.insert(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return studentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Students record) {
		return studentMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Students initStudents() {
		Students record = new Students();
		
		record.setStuId(0L);
		record.setName("");
		record.setMobile("");
		record.setSex("");
		record.setNationId(0L);
		record.setBirthDate("");
		record.setEmail("");
		record.setIdType(0L);
		record.setIdCard("");
		record.setTitleId(0L);
		record.setTitleStr("");
		record.setOrgId(0L);
		record.setOrgName("");
		record.setCityId(0L);
		record.setCityName("");
		record.setDutyName("");
		record.setDegreeId(0L);
		record.setEduId(0L);
		record.setAddr("");		
		record.setZipCode("");
		record.setAdminId(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public Students selectByPrimaryKey(Long id) {
		return studentMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<Students> selectBySearchVo(ProjectStudentSearchVo searchVo) {
		return studentMapper.selectBySearchVo(searchVo);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(ProjectStudentSearchVo searchVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Students> list = studentMapper.selectByListPage(searchVo);
		PageInfo info = new PageInfo(list);
		return info;
	}
	
}
