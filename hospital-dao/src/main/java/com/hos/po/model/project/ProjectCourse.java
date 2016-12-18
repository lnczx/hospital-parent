package com.hos.po.model.project;

import java.util.Date;

public class ProjectCourse {
	
    private Long courseId;

    private Long pId;

    private Date courseDate;

    private Date startTime;

    private Date endTime;

    private Long courseDatetime;

    private String content;

    private String teacher;

    private Long titleId;

    private Long orgId;

    private Long courseType;

    private Long addTime;

    private Long updateTime;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Date getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Date courseDate) {
        this.courseDate = courseDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getCourseDatetime() {
        return courseDatetime;
    }

    public void setCourseDatetime(Long courseDatetime) {
        this.courseDatetime = courseDatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher == null ? null : teacher.trim();
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCourseType() {
        return courseType;
    }

    public void setCourseType(Long courseType) {
        this.courseType = courseType;
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
}