package com.simi.oa.vo.account;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class AccountRegisterVo {
	
	private Long id;
	@NotEmpty(message = "{name.not.empty}")
	private String name;
	@NotEmpty(message = "{mobile.not.empty}")
	private String mobile;
	@NotEmpty(message = "{email.not.empty}")
	
	@Email(message = "{email.not.correct}")
	private String email;
	
	@NotEmpty(message = "{username.not.empty}")
	private String username;
	
	private String nickname;
	@Length(min = 8, message = "{password.length.error}")
	
	private String password;
	
	private String confirmPassword;
	
	@AssertTrue(message = "{agreement.must.agree}")
	private boolean agreement;
	
	private Long roleId;
	
	private Long orgId;
	
	public void setName(String name) {
		this.name = name;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setAgreement(boolean agreement) {
		this.agreement = agreement;
	}

	public String getName() {
		return this.name;
	}


	public String getMobile() {
		return mobile;
	}
	
	public String getEmail() {
		return this.email;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public boolean getAgreement() {
		return this.agreement;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}
