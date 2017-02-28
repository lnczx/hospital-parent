<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<link rel="stylesheet" href="<c:url value='/static/h-ui.admin/css/H-ui.login.css'/>" />
<link rel="stylesheet" href="<c:url value='/lib/drag/drag.css'/>" />
</head>
<body>
	<input type="hidden" id="TenantId" name="TenantId" value="" />
	<div class="header"></div>
	<div class="loginWraper">
		<div  class="loginBox">
			<form:form modelAttribute="contentModel" id="loginform" class="form form-horizontal" method="POST">
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">
						<i class="Hui-iconfont">&#xe60d;</i>
					</label>
					<div class="formControls col-xs-8 col-sm-7">
						 <form:input path="username" class="input-text size-L" autocomplete="off" placeholder="用户名"/>
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">
						<i class="Hui-iconfont">&#xe60e;</i>
					</label>
					<div class="formControls col-xs-8 col-sm-7">
						<form:password path="password" class="input-text size-L" autocomplete="off" placeholder="密码"/>
					</div>
				</div>
				<div class="row cl">
					<div class="formControls col-xs-8 col-xs-offset-3">
						<div id="drag" style="width: 360px;"></div>
					</div>
				</div>
				
				<div class="row cl">
					<div class="formControls col-xs-8 col-xs-offset-3">
						<input name="" type="submit" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
						<input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">

						<a href="http://jjxmglxt.pumc.edu.cn/app/login.do" target="_blank" class="btn btn-warning radius size-L">升级前页面</a>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	<%@ include file="../shared/pageFooter.jsp"%>
	
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<script src="<c:url value='/lib/jquery.validation/1.14.0/jquery.validate.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/lib/drag/drag.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/static/app/js/login.js'/>" type="text/javascript"></script>
	
	
</body>
</html>