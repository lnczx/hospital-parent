package com.hos.po.dao.admin;

import com.hos.po.model.admin.AdminAuthority;

public interface AdminAuthorityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminAuthority record);

    int insertSelective(AdminAuthority record);

    AdminAuthority selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminAuthority record);

    int updateByPrimaryKey(AdminAuthority record);
}