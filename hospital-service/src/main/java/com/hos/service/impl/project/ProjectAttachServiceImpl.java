package com.hos.service.impl.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hos.po.dao.project.ProjectAttachMapper;
import com.hos.po.model.project.ProjectAttach;
import com.hos.service.dict.DictService;
import com.hos.service.project.ProjectAttachService;
import com.hos.vo.project.ProjectSearchVo;
import com.meijia.utils.TimeStampUtil;


@Service
public class ProjectAttachServiceImpl implements ProjectAttachService {

	@Autowired
	private ProjectAttachMapper projectAttachMapper;

	@Autowired
	private DictService dictService;
	
	@Override
	public int insertSelective(ProjectAttach record) {
		return projectAttachMapper.insert(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return projectAttachMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ProjectAttach record) {
		return projectAttachMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public ProjectAttach initProjectAttach() {
		ProjectAttach record = new ProjectAttach();
		record.setId(0L);
		record.setpId(0L);
		record.setAttachType("");
		record.setFileName("");
		record.setFileType("");
		record.setFileSize(0);
		record.setAdminId(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public ProjectAttach selectByPrimaryKey(Long id) {
		return projectAttachMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<ProjectAttach> selectBySearchVo(ProjectSearchVo searchVo) {
		return projectAttachMapper.selectBySearchVo(searchVo);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(ProjectSearchVo searchVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ProjectAttach> list = projectAttachMapper.selectByListPage(searchVo);
		PageInfo info = new PageInfo(list);
		return info;
	}
	
}
