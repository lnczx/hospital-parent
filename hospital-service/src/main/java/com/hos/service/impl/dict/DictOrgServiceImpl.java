package com.hos.service.impl.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hos.po.dao.dict.DictOrgsMapper;
import com.hos.po.model.dict.DictOrgs;
import com.hos.service.dict.DictOrgService;
import com.hos.service.dict.DictService;
import com.hos.vo.dict.DictOrgSearchVo;
import com.meijia.utils.TimeStampUtil;


@Service
public class DictOrgServiceImpl implements DictOrgService {

	@Autowired
	private DictOrgsMapper dictOrgMapper;

	@Autowired
	private DictService dictService;
	
	@Override
	public int insertSelective(DictOrgs record) {
		return dictOrgMapper.insert(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return dictOrgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DictOrgs record) {
		return dictOrgMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public DictOrgs initDictOrg() {
		DictOrgs record = new DictOrgs();
		record.setOrgId(0L);
		record.setOrgCode("");
		record.setParentCode("");
		record.setName("");
		record.setRemark("");
		record.setAdminId(0L);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public DictOrgs selectByPrimaryKey(Long id) {
		return dictOrgMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<DictOrgs> selectByAll() {
		return dictOrgMapper.selectByAll();
	}
	
	@Override
	public List<DictOrgs> selectBySearchVo(DictOrgSearchVo searchVo) {
		return dictOrgMapper.selectBySearchVo(searchVo);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(DictOrgSearchVo searchVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<DictOrgs> list = dictOrgMapper.selectByListPage(searchVo);
		PageInfo info = new PageInfo(list);
		return info;
	}
	
	@Override
	public DictOrgs findByOrgCode(String orgCode) {
		
		List<DictOrgs> list = dictService.LoadOrgData();
		
		for(DictOrgs item : list) {
			if (item.getOrgCode().equals(orgCode)) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public DictOrgs findByName(String name) {
		
		List<DictOrgs> list = dictService.LoadOrgData();
		
		for(DictOrgs item : list) {
			if (item.getName().equals(name)) {
				return item;
			}
		}
		return null;
	}

}
