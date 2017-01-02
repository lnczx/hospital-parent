<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->	
</head>
<body>
	<article class="page-container"> 
	<form:form modelAttribute="formData"  id="project-view" class="form form-horizontal" method="POST">
		<form:hidden path="pId" />
		<div class="row cl">
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>项目编号：</label>
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>${formData.pNo }</label>
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>项目名称：</label>
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>${formData.name }</label>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>申办单位：</label>
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>${formData.orgName }</label>
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>期数：</label>
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>${formData.numTerm }</label>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>负责人：</label>
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>${formData.pHeader }</label>
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>联系电话：</label>
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>${formData.pHeaderTel }</label>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>开始时间：</label>
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>${formData.startDate }</label>
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>结束时间：</label>
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>${formData.endDate }</label>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>举办地点：</label>
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>${formData.addr }</label>
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>授予学分：</label>
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>${formData.credit }</label>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>拟招人数：</label>
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>${formData.numRecruit }</label>
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>备注：</label>
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span></label>
        </div>
	</form:form> </article>
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
</body>
</html>
