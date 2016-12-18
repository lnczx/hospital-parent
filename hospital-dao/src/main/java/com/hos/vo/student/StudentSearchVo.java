package com.hos.vo.student;

public class StudentSearchVo {
	private Long stuId;
	
	private String name;
	
	private String mobile;
		
	private Long cityId;

	public Long getStuId() {
		return stuId;
	}

	public void setStuId(Long stuId) {
		this.stuId = stuId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
