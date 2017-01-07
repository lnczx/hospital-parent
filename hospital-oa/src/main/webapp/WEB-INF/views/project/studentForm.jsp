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
			
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>手机号：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<form:input path="mobile" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
			</div>
			
		</div>
		
		
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>性别：</label>
			<div class="formControls col-xs-4 col-sm-4 skin-minimal">
				<form:select path="sex" class="select">>
					<option value=""/> 请选择性别
					<form:option value="男">男</form:option>
					<form:option value="女">女</form:option>
				</form:select> 
			</div>
			
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>所在省市：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<span class="select-box">
					<selectCityTag:select selectedId="${formData.cityId }" />
				</span>
			</div>
			
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>所在单位：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<form:input path="orgName" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
			</div>
			
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>职称：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<selectTitleTag:select selectedId="${formData.titleId }"  />
			</div>
		</div>
		<div class="row cl">
			
		
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>通讯地址：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<form:input path="addr" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
			</div>
			
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>电子邮箱：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<form:input path="email" class="input-text" autocomplete="off" maxLength="255" placeholder="@" />
			</div>
		</div>
		
		
		
		<%-- <div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>证件类型：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<selectIdTypeTag:select selectedId="${formData.idType }"  />
			</div>
		
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>证件号码：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<form:input path="idCard" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
			</div>
		</div> 
		
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>最高学历：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<selectEduTag:select selectedId="${formData.eduId }"  />
			</div>
		
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>最高学位：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<selectDegreeTag:select selectedId="${formData.degreeId }"  />
			</div>
		</div> 
		
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>民族：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<selectNationTag:select selectedId="${formData.nationId }"  />
			</div>
		
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>出生日期：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<form:input path="birthDate" class="input-text form_datetime"  readonly="true" />
			</div>
		</div> --%>
		
		
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
