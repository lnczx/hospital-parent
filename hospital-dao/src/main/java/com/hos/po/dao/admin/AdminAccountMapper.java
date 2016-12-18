package com.hos.po.dao.admin;

import com.hos.po.model.admin.AdminAccount;

public interface AdminAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminAccount record);

    int insertSelective(AdminAccount record);

    AdminAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminAccount record);

    int updateByPrimaryKey(AdminAccount record);
}