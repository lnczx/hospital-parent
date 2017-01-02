package com.hos.po.model.project;

public class Projects {
    private Long pId;

    private Integer pYear;

    private String pNo;

    private String name;

    private Long orgId;

    private String orgName;

    private String pHeader;

    private String pHeaderTel;

    private String addr;

    private Short credit;

    private Integer numRecruit;

    private Short numTerm;

    private Short status;
    
    private String remarks;
    
    private String fileName;
    
    private Long adminId;

    private Long addTime;

    private Long updateTime;

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Integer getpYear() {
        return pYear;
    }

    public void setpYear(Integer pYear) {
        this.pYear = pYear;
    }

    public String getpNo() {
        return pNo;
    }

    public void setpNo(String pNo) {
        this.pNo = pNo == null ? null : pNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getpHeader() {
        return pHeader;
    }

    public void setpHeader(String pHeader) {
        this.pHeader = pHeader == null ? null : pHeader.trim();
    }

    public String getpHeaderTel() {
        return pHeaderTel;
    }

    public void setpHeaderTel(String pHeaderTel) {
        this.pHeaderTel = pHeaderTel == null ? null : pHeaderTel.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public Short getCredit() {
        return credit;
    }

    public void setCredit(Short credit) {
        this.credit = credit;
    }

    public Integer getNumRecruit() {
        return numRecruit;
    }

    public void setNumRecruit(Integer numRecruit) {
        this.numRecruit = numRecruit;
    }

    public Short getNumTerm() {
        return numTerm;
    }

    public void setNumTerm(Short numTerm) {
        this.numTerm = numTerm;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}