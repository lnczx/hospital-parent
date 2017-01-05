<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
</head>
<body>
	<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 项目管理 <span
		class="c-gray en">&gt;</span> 计划项目导入 
	</nav>
	<div class="page-container">
		<div class="Huialert Huialert-success radius">
			<form class="importOkForm" method="post" action="/hospital-oa/project/project-import-do" enctype="multipart/form-data">
			
			<h3>导入成功：</h3>
			<ol>
				<li>本次共导入<font color="red">${totals}</font>个项目，其中新增<font color="red">${totalNews}</font>个项目, 更新已存在的<font color="red">${totalUpdate}</font>个项目。</li>
				<li>
					<a href="/hospital-oa/project/list?linkType=project" class="btn btn-success">我的项目一览</a>
				</li>
			</ol>
			</form>
		</div>
	</div>
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
</body>
</html>