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
import com.hos.vo.student.StudentSearchVo;
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
		record.setMobile("");
		record.setSex("");
		record.setEmail("");
		record.setCityId(0L);
		record.setOrgId(0L);
		record.setTitleId(0L);
		record.setAddr("");			
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public Students selectByPrimaryKey(Long id) {
		return studentMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<Students> selectBySearchVo(StudentSearchVo searchVo) {
		return studentMapper.selectBySearchVo(searchVo);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(StudentSearchVo searchVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Students> list = studentMapper.selectByListPage(searchVo);
		PageInfo info = new PageInfo(list);
		return info;
	}
	
}
