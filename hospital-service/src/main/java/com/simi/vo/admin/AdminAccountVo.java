package com.simi.vo.admin;

import com.simi.po.model.admin.AdminAccount;

public class AdminAccountVo extends AdminAccount {

	private String roleName;
	
	private String orgName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}



}
