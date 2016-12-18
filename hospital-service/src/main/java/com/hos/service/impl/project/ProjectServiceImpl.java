package com.hos.service.impl.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hos.po.dao.project.ProjectsMapper;
import com.hos.po.model.project.Projects;
import com.hos.service.dict.DictService;
import com.hos.service.project.ProjectService;
import com.hos.vo.project.ProjectSearchVo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;


@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectsMapper projectsMapper;

	@Autowired
	private DictService dictService;
	
	@Override
	public int insertSelective(Projects record) {
		return projectsMapper.insert(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return projectsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Projects record) {
		return projectsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Projects initProject() {
		Projects record = new Projects();
		
		record.setpId(0L);
		record.setpYear(DateUtil.getNowOfDate());
		record.setpNo("");
		record.setName("");
		record.setOrgId(0L);
		record.setOrgName("");
		record.setpHeader("");
		record.setpHeaderTel("");
		record.setAddr("");
		record.setCredit((short) 0);
		record.setNumRecruit(0);
		record.setNumTerm((short) 0);
		record.setStatus((short) 0);		
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public Projects selectByPrimaryKey(Long id) {
		return projectsMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<Projects> selectBySearchVo(ProjectSearchVo searchVo) {
		return projectsMapper.selectBySearchVo(searchVo);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(ProjectSearchVo searchVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Projects> list = projectsMapper.selectByListPage(searchVo);
		PageInfo info = new PageInfo(list);
		return info;
	}
	
}
