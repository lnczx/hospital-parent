package com.hos.vo.project;

import com.hos.po.model.project.Projects;


public class ProjectVo extends Projects {

	private String dateRange;
	
	private String startDate;
	
	private String endDate;
	
	private String briefingFilePath;
	
	private Boolean hasCourse;
	
	private Boolean hasAttach;
	
	private Boolean hasStudent;

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public String getBriefingFilePath() {
		return briefingFilePath;
	}

	public void setBriefingFilePath(String briefingFilePath) {
		this.briefingFilePath = briefingFilePath;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Boolean getHasCourse() {
		return hasCourse;
	}

	public void setHasCourse(Boolean hasCourse) {
		this.hasCourse = hasCourse;
	}

	public Boolean getHasAttach() {
		return hasAttach;
	}

	public void setHasAttach(Boolean hasAttach) {
		this.hasAttach = hasAttach;
	}

	public Boolean getHasStudent() {
		return hasStudent;
	}

	public void setHasStudent(Boolean hasStudent) {
		this.hasStudent = hasStudent;
	}
	
}
