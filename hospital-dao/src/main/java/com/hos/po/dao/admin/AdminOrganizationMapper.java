package com.hos.po.dao.admin;

import com.hos.po.model.admin.AdminOrganization;

public interface AdminOrganizationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminOrganization record);

    int insertSelective(AdminOrganization record);

    AdminOrganization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminOrganization record);

    int updateByPrimaryKey(AdminOrganization record);
}