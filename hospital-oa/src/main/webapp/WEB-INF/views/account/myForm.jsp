<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="selectOrgTag" uri="/WEB-INF/views/tags/selectOrg.tld"%>
<%@ taglib prefix="selectRoleTag" uri="/WEB-INF/views/tags/selectRole.tld"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
</head>
<body>
	<article class="page-container"> 
	<form:form modelAttribute="formData"  id="my-form" action=""
		class="form form-horizontal" method="POST">
		<form:hidden path="id" />
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				<span class="c-red">*</span>
				用户名：
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				${formData.username }
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				<span class="c-red">*</span>
				密码：
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input-text" autocomplete="off" value="" placeholder="密码" id="password" name="password">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				<span class="c-red">*</span>
				确认密码：
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input-text" autocomplete="off" placeholder="确认新密码" id="confirmPassword" name="confirmPassword">
			</div>
		</div>

		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				<span class="c-red">*</span>
				昵称：
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				<form:input path="name" class="input-text" autocomplete="off" placeholder="昵称" />
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				<span class="c-red">*</span>
				二级单位：
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box">
					<selectOrgTag:select selectedId="${formData.orgId }" sessionOrgId="${sessionScope.accountAuth.orgId}" />
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				<span class="c-red">*</span>
				用户类型：
			</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box">
					<selectRoleTag:select selectedId="${formData.roleId }" />
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"> 联系方式： </label>
			<div class="formControls col-xs-8 col-sm-9">
				<form:input path="mobile" class="input-text" autocomplete="off" placeholder="手机号/座机" />
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"> 邮箱： </label>
			<div class="formControls col-xs-8 col-sm-9">
				<form:input path="email" class="input-text" autocomplete="off" placeholder="@" />
			</div>
		</div>
		
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form:form> </article>
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/lib/jquery.validation/1.14.0/jquery.validate.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery.validation/1.14.0/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery.validation/1.14.0/messages_zh.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/static/app/js/admin/myForm.js'/>" type="text/javascript"></script>
</body>
</html>
