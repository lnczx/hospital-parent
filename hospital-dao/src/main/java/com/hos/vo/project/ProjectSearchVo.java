package com.hos.vo.project;

import java.util.Date;

public class ProjectSearchVo {
	private Long pId;
	
	private Date pYear;
	
	private String pNo;
	
	private String name;
	
	private Long orgId;

	public String getpNo() {
		return pNo;
	}

	public void setpNo(String pNo) {
		this.pNo = pNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getpYear() {
		return pYear;
	}

	public void setpYear(Date pYear) {
		this.pYear = pYear;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}
	
}
