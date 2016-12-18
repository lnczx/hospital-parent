package com.hos.po.dao.dict;

import java.util.List;

import com.hos.po.model.dict.DictOrgs;
import com.hos.vo.dict.DictOrgSearchVo;

public interface DictOrgsMapper {
    int deleteByPrimaryKey(Long orgId);

    int insert(DictOrgs record);

    int insertSelective(DictOrgs record);

    DictOrgs selectByPrimaryKey(Long orgId);

    int updateByPrimaryKeySelective(DictOrgs record);

    int updateByPrimaryKey(DictOrgs record);
    
    List<DictOrgs> selectByAll();
    
    List<DictOrgs> selectBySearchVo(DictOrgSearchVo searchVo);
    
    List<DictOrgs> selectByListPage(DictOrgSearchVo searchVo);
}