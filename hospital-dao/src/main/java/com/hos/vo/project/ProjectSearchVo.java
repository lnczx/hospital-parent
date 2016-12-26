package com.hos.vo.project;

public class ProjectSearchVo {
	private Long pId;
	
	private Integer pYear;
	
	private String pNo;
	
	private String name;
	
	private Short numTerm;
	
	private Long orgId;
	
	private String attachType;

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

	public Integer getpYear() {
		return pYear;
	}

	public void setpYear(Integer pYear) {
		this.pYear = pYear;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public Short getNumTerm() {
		return numTerm;
	}

	public void setNumTerm(Short numTerm) {
		this.numTerm = numTerm;
	}

	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}
	
}
