package com.hos.vo.dict;

public class DictOrgSearchVo {
	
	private Long orgId;
	
	private String orgCode;
	
	private String parentCode;
	
	private String name;
	
	private Short isParent;
	
	private Short isOrg;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getIsParent() {
		return isParent;
	}

	public void setIsParent(Short isParent) {
		this.isParent = isParent;
	}

	public Short getIsOrg() {
		return isOrg;
	}

	public void setIsOrg(Short isOrg) {
		this.isOrg = isOrg;
	}
	

}
