<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link href="<c:url value='/lib/bootstrap-datepicker/dist/css/bootstrap-datepicker.standalone.min.css'/>" rel="stylesheet"
	type="text/css" />

<link href="<c:url value='/lib/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css'/>" rel="stylesheet"
	type="text/css" />	
<link href="<c:url value='/lib/bootstrap-datetimepicker/css/bootstrap-datetimepicker-standalone.css'/>" rel="stylesheet"
	type="text/css" />
	
</head>
<body>
	<article class="page-container"> 
	<form:form modelAttribute="formData"  id="course-form" action="/hospital-oa/project/course/course-form"
		class="form form-horizontal" method="POST">
		<form:hidden path="pId" />
		<form:hidden path="courseId" />
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				<span class="c-red">*</span>
				日期：
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				<form:input path="courseDate" class="input-text form_datetime"  readonly="true" />
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				<span class="c-red">*</span>
				开始时间：
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				<form:input path="startTime" class="input-text form_datetime"  readonly="true" />
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				<span class="c-red">*</span>
				结束时间：
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				<form:input path="endTime" class="input-text form_datetime" readonly="true" />
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"> 学时(分钟)： </label>
			<div class="formControls col-xs-8 col-sm-9">
				<label id="courseMin">${formData.courseMin }</label>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				<span class="c-red">*</span>
				课程内容：
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				<form:input path="content" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				<span class="c-red">*</span>
			 	教师： 
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				<form:input path="teacher" class="input-text" autocomplete="off" maxLength="255"  />
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"> 
				<span class="c-red">*</span>
				职称： 
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				<form:input path="titleStr" class="input-text" autocomplete="off" maxLength="255"  />
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"> 
				<span class="c-red">*</span>
				单位： 
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				<form:input path="orgName" class="input-text" autocomplete="off" maxLength="255"  />
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"> 类型： </label>
			<div class="formControls col-xs-8 col-sm-9">
				<form:select path="courseType" class="select-box">
					<form:option value="理论">理论</form:option>
					<form:option value="实践">实践</form:option>
				</form:select>
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

	<script src="<c:url value='/lib/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js'/>" type="text/javascript" ></script>
	<script src="<c:url value='/lib/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js'/>" type="text/javascript" ></script>
	
	
	<script src="<c:url value='/static/app/js/project/courseForm.js'/>" type="text/javascript"></script>
</body>
</html>
