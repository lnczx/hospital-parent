<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<title>帮助中心</title>
</head>
<body>
	<div class="page-container">
		<div class="text-l">
			<span class="select-box inline">
				<strong> 常用导入模板文件下载：<a href="/hospital-oa/attach/项目导入Excel模板.xls">《项目导入Excel模板》</a> | <a
						href="/hospital-oa/attach/会议日程导入Excel模板.xls">《会议日程导入Excel模板》</a> | <a href="/hospital-oa/attach/学员导入Excel模板.xls">《学员导入Excel模板》</a></strong>
			</span>
		</div>
		<br>
		<hr>
		<div class="mt-20">
			<p class="f-20 text-success text-c">系统使用帮助中心</p>
			<br>
			<p>
				<strong>[目录]</strong>
			</p>
			<p>1.项目导入Excel文件填写规范说明</p>
			<p>2.会议日程导入Excel文件填写规范说明</p>
			<p>3.学员导入Excel文件填写规范说明</p>
			<br>
			<hr>
		</div>
	</div>
	<%@ include file="../shared/importJs.jsp"%>
</body>
</html>