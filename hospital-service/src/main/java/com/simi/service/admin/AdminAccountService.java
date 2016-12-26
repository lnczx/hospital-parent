package com.simi.service.admin;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.vo.admin.AccountSearchVo;
import com.simi.vo.admin.AdminAccountVo;
import com.simi.po.model.admin.AdminAccount;


public interface AdminAccountService {

		AdminAccount selectByPrimaryKey(Long id);

		Long insert(AdminAccount record) throws NoSuchAlgorithmException;

		AdminAccount login(String username, String password)
				throws NoSuchAlgorithmException;

		PageInfo listPage(AccountSearchVo accountSearchVo, int pageNo, int pageSize);

		AdminAccount initAccount();

		public AdminAccount updateBind(Long id, Long roleId, Long organizationId,String imUsername);

		int deleteByPrimaryKey(Long id);

		int insertSelective(AdminAccount record);

		int updateByPrimaryKeySelective(AdminAccount record);

	    int updateByPrimaryKey(AdminAccount record);

		List<AdminAccountVo> getAdminAccountViewList(List<AdminAccount> list);

		int updateByPrimaryKeySelectives(AdminAccount record);
		
		List<AdminAccount> selectBySearchVo(AccountSearchVo searchVo);

}	