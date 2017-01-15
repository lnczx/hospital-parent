package com.hos.service.dict;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hos.po.model.dict.DictOrgs;
import com.hos.vo.dict.DictOrgSearchVo;


public interface DictOrgService {

	int insertSelective(DictOrgs record);

	int deleteByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(DictOrgs record);

	DictOrgs initDictOrg();

	DictOrgs selectByPrimaryKey(Long id);

	List<DictOrgs> selectByAll();

	List<DictOrgs> selectBySearchVo(DictOrgSearchVo searchVo);

	PageInfo selectByListPage(DictOrgSearchVo searchVo, int pageNum, int pageSize);

	DictOrgs findByName(String name);

	DictOrgs findByOrgCode(String orgCode);

	DictOrgs findById(Long orgId);

	List<DictOrgs> findByMatchName(String name);

}
