<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="selectOrgTag" uri="/WEB-INF/views/tags/selectOrg.tld"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
	
</head>
<body>
	<article class="page-container"> 
	<form:form modelAttribute="formData"  id="project-form" action="/hospital-oa/project/project-form"
		class="form form-horizontal" method="POST">
		<form:hidden path="pId" />
		<div class="row cl">
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>项目编号：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <form:input path="pNo" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
            </div>
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>项目名称：</label>
            <div class="formControls col-xs-4 col-sm-4">
                 <form:input path="name" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>申办单位：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <selectOrgTag:select selectedId="${formData.orgId }" sessionOrgId="${sessionScope.accountAuth.orgId}" />
            </div>
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>期数：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <form:input path="numTerm" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>负责人：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <form:input path="pHeader" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
            </div>
        
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>联系电话：</label>
            <div class="formControls col-xs-4 col-sm-4">
                 <form:input path="pHeaderTel" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
            </div>
        </div>
        
        <div class="row cl">
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>举办地点：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <form:input path="addr" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
            </div>
        
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>授予学分：</label>
            <div class="formControls col-xs-4 col-sm-4">
                 <form:input path="credit" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>拟招人数：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <form:input path="numRecruit" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
            </div>
        
            <label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>备注：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <form:textarea path="remarks" class="form-control"  rows="5" cols="50"/>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;保存并提交&nbsp;&nbsp;">
            </div>
        </div>
	</form:form> </article>
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/lib/jquery.validation/1.14.0/jquery.validate.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery.validation/1.14.0/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery.validation/1.14.0/messages_zh.min.js'/>" type="text/javascript"></script>
		
	<script src="<c:url value='/static/app/js/project/projectForm.js'/>" type="text/javascript"></script>
</body>
</html>
