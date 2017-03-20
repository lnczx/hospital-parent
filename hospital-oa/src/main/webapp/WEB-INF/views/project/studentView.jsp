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
	<form:form modelAttribute="formData"  id="student-form" action="/hospital-oa/project/student/student-form"
		class="form form-horizontal" method="POST">

		<form:hidden path="id" />
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>姓名：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.name }
			</div>
			
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>联系方式：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.mobile }
			</div>
			
		</div>
		
		
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>职称：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.titleName }
			</div>
		
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>职务：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.dutyName }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>学历：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.eduName }
			</div>
		
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>单位：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.orgName }
			</div>
		</div>

	</form:form> </article>
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
</body>
</html>
