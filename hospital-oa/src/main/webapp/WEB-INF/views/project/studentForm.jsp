<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="selectOrgTag" uri="/WEB-INF/views/tags/selectOrg.tld"%>
<%@ taglib prefix="selectCityTag" uri="/WEB-INF/views/tags/selectCity.tld"%>
<%@ taglib prefix="selectTitleTag" uri="/WEB-INF/views/tags/selectTitle.tld"%>
<%@ taglib prefix="selectNationTag" uri="/WEB-INF/views/tags/selectNation.tld"%>
<%@ taglib prefix="selectEduTag" uri="/WEB-INF/views/tags/selectEdu.tld"%>
<%@ taglib prefix="selectDegreeTag" uri="/WEB-INF/views/tags/selectDegree.tld"%>
<%@ taglib prefix="selectIdTypeTag" uri="/WEB-INF/views/tags/selectIdType.tld"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link href="<c:url value='/lib/bootstrap-datepicker/dist/css/bootstrap-datepicker.standalone.min.css'/>" rel="stylesheet"
	type="text/css" />
	
</head>
<body>
	<article class="page-container"> 
	<form:form modelAttribute="formData"  id="student-form" action="/hospital-oa/project/student/student-form"
		class="form form-horizontal" method="POST">
		<form:hidden path="pId" />
		<form:hidden path="id" />
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>姓名：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<form:input path="name" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
			</div>
			
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>联系方式：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<form:input path="mobile" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
			</div>
			
		</div>
		
		
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>职称：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<selectTitleTag:select selectedId="${formData.titleId }"  />
			</div>
			
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>职务：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<form:input path="dutyName" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
			</div>
			
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>学历：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<selectEduTag:select selectedId="${formData.eduId }"  />
			</div>
			
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>单位：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<form:input path="orgName" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
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
	
	<script src="<c:url value='/lib/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js'/>" type="text/javascript" ></script>
	<script src="<c:url value='/lib/bootstrap-datepicker/dist/locales/bootstrap-datepicker.zh-CN.min.js'/>" type="text/javascript" ></script>

	
	<script src="<c:url value='/static/app/js/project/studentForm.js'/>" type="text/javascript"></script>
</body>
</html>
