package com.hos.po.dao.dict;

import java.util.List;

import com.hos.po.model.dict.Dicts;
import com.hos.vo.dict.DictSearchVo;

public interface DictsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dicts record);

    int insertSelective(Dicts record);

    Dicts selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dicts record);

    int updateByPrimaryKey(Dicts record);
    
    List<Dicts> selectBySearchVo(DictSearchVo searchVo);
}