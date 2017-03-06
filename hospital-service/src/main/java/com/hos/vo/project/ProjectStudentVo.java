package com.hos.vo.project;

import com.hos.po.model.project.ProjectStudent;;


public class ProjectStudentVo extends ProjectStudent {

	private String idTypeName;
	
	private String eduName;

	private String degreeName;
	
	private String cityName;

	public String getIdTypeName() {
		return idTypeName;
	}

	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
	}

	public String getEduName() {
		return eduName;
	}

	public void setEduName(String eduName) {
		this.eduName = eduName;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	@Override
	public String getCityName() {
		return cityName;
	}

	@Override
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	

}
