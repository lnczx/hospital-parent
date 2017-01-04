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
			
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>手机号：</label>
			<div class="formControls col-xs-4 col-sm-4">
				
				${formData.mobile }
			</div>
			
		</div>
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>证件类型：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.idTypeName }
			</div>
		
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>证件号码：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.idCard }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>性别：</label>
			<div class="formControls col-xs-4 col-sm-4 skin-minimal">
				${formData.sex }
			</div>
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>出生日期：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.birthDate }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>最高学历：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.eduName }
			</div>
		
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>最高学位：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.degreeName }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>所在省市：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.cityName }
			</div>
		
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>所在单位：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.orgName }
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>职称：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.titleStr }
			</div>
		
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>通讯地址：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.addr }
				<form:input path="addr" class="input-text" autocomplete="off" maxLength="255" placeholder="" />
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>民族：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<selectNationTag:select selectedId="${formData.nationId }"  />
			</div>
		
			<label class="form-label col-xs-2 col-sm-2"><span class="c-red"></span>电子邮箱：</label>
			<div class="formControls col-xs-4 col-sm-4">
				${formData.addr }
			</div>
		</div>

	</form:form> </article>
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
</body>
</html>
