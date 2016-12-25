package com.simi.service.impl.admin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hos.po.model.dict.DictOrgs;
import com.hos.service.dict.DictOrgService;
import com.hos.service.dict.DictService;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.admin.AdminOrganizationService;
import com.simi.service.admin.AdminRoleService;
import com.simi.vo.admin.AccountSearchVo;
import com.simi.vo.admin.AdminAccountVo;
import com.simi.po.dao.admin.AdminAccountMapper;
import com.simi.po.dao.admin.AdminRoleMapper;
import com.simi.po.model.admin.AdminAccount;
import com.simi.po.model.admin.AdminRole;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;

@Service
public class AdminAccountServiceImpl implements AdminAccountService {

	@Autowired
	private AdminAccountMapper adminAccountMapper;

	@Autowired
	protected AdminRoleService adminRoleService;

	@Autowired
	protected AdminOrganizationService adminOrganizationService;

	@Autowired
	public AdminRoleMapper adminRoleMapper;
	
	@Autowired
	public DictOrgService dictOrgService;

	@Override
	public AdminAccount selectByPrimaryKey(Long id) {
		return adminAccountMapper.selectByPrimaryKey(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageInfo listPage(AccountSearchVo accountSearchVo, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		List<AdminAccount> list = adminAccountMapper.selectByListPage(accountSearchVo);
		if (list != null && list.size() > 0) {
			List<AdminAccountVo> adminAccountViewList = this.getAdminAccountViewList(list);
			for (int i = 0; i < list.size(); i++) {
				if (adminAccountViewList.get(i) != null) {
					list.set(i, adminAccountViewList.get(i));
				}
			}
		}
		PageInfo result = new PageInfo(list);
		return result;
	}

	@Override
	public Long insert(AdminAccount record) throws NoSuchAlgorithmException {
		record.setRegisterTime(DateUtil.getNowOfDate());
		return adminAccountMapper.insert(record);
	}

	@Override
	public AdminAccount login(String username, String password) throws NoSuchAlgorithmException {
		
		
		AccountSearchVo accountSearchVo = new AccountSearchVo();
		accountSearchVo.setUsername(username);
		accountSearchVo.setPassword(StringUtil.md5(password.trim()));
		accountSearchVo.setEnable((short) 1);
		List<AdminAccount> list = adminAccountMapper.selectBySearchVo(accountSearchVo);
		AdminAccount po = null;
		if (!list.isEmpty()) {
			po = list.get(0);
		}
		return po;
	}

	@Override
	public AdminAccount initAccount() {
		AdminAccount account = new AdminAccount();
		account.setId(0L);
		account.setName("");
		account.setUsername("");
		account.setPassword("");
		account.setEnable((short) 1);
		account.setRegisterTime(DateUtil.getNowOfDate());
		account.setVersion(0L);
		account.setRoleId(1L);
		account.setOrgId(0L);
		return account;
	}

	@Override
	public AdminAccount updateBind(Long id, Long roleId, Long orgId, String imUsername) {
		AdminAccount adminAccount = adminAccountMapper.selectByPrimaryKey(id);
		adminAccount.setRoleId(roleId);
		adminAccount.setOrgId(orgId);
		adminAccount.setImUsername(imUsername);
		adminAccountMapper.updateByPrimaryKeySelective(adminAccount);
		return adminAccount;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return adminAccountMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(AdminAccount record) {
		return adminAccountMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(AdminAccount record) {
		return adminAccountMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(AdminAccount record) {
		return adminAccountMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<AdminAccountVo> getAdminAccountViewList(List<AdminAccount> list) {
		List<Long> roleIds = new ArrayList<Long>();
		AdminAccount item = null;

		// 根据roleIds查询出对应的AdminRole
		List<AdminRole> roleList = adminRoleMapper.selectAll();
		List<AdminAccountVo> result = new ArrayList<AdminAccountVo>();
		Long roleId = 0L;
		// AdminAccount中的roleId和AdminRole中的Id进行比较，相同则为roleName赋值
		for (int i = 0; i < list.size(); i++) {
			item = list.get(i);
			roleId = item.getRoleId();

			AdminAccountVo vo = new AdminAccountVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);

			String roleName = "";
			for (AdminRole ar : roleList) {
				if (ar.getId().equals(roleId)) {
					roleName = ar.getName();
					break;
				}
			}
			vo.setRoleName(roleName);
			
			//机构
			String orgName = "";
			if (item.getOrgId() > 0L) {
				DictOrgs dictOrg = dictOrgService.findById(item.getOrgId());
				orgName = dictOrg.getName();
			}
			vo.setOrgName(orgName);
			
			result.add(vo);
		}
		return result;
	}

	@Override
	public int updateByPrimaryKeySelectives(AdminAccount record) {

		return adminAccountMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<AdminAccount> selectBySearchVo(AccountSearchVo searchVo) {
		return adminAccountMapper.selectBySearchVo(searchVo);
	}

}
