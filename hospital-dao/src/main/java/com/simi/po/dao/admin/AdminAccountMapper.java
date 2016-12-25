package com.simi.po.dao.admin;

import java.util.List;

import com.simi.po.model.admin.AdminAccount;
import com.simi.vo.admin.AccountSearchVo;

public interface AdminAccountMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(AdminAccount record);

    int insertSelective(AdminAccount record);

    AdminAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminAccount record);

    int updateByPrimaryKey(AdminAccount record);

    List<AdminAccount> selectByListPage(AccountSearchVo accountSearchVo);

    List<AdminAccount> selectBySearchVo(AccountSearchVo accountSearchVo);
    
}