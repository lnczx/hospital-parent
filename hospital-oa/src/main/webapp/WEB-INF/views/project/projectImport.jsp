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
		class="c-gray en">&gt;</span> 计划项目导入 <a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px"
		href="javascript:location.replace(location.href);" title="刷新">
		<i class="Hui-iconfont">&#xe68f;</i>
	</a></nav>
	<div class="page-container">
		<form class="importForm" method="post" action="/hospital-oa/project/project-import" enctype="multipart/form-data">
			<div class="Huialert Huialert-success radius">
				<h3>1.下载模板：</h3>
				<ol>
					<li>
						->
						<a href="/hospital-oa/attach/项目导入Excel模板.xls">项目导入Excel模板.xls</a>
					</li>
				</ol>
			</div>
			<div class="Huialert Huialert Huialert-danger radius">
				<h3>2.上传Excel</h3>
				<ol>
					<li>
						<span class="btn-upload form-group">
							<input class="input-text upload-url" type="text" name="file-list" id="file-list" readonly datatype="*"
								nullmsg="请添加附件！" style="width: 200px">
							<a href="javascript:void();" class="btn btn-primary upload-btn">
								<i class="Hui-iconfont">&#xe642;</i>
								选择 项目Excel文件
							</a>
							<input type="file" multiple name="project-file" class="input-file" data-rule-required="true">
						</span>
						<button type="submit" class="btn btn-success" id="" name="" onClick="">
							<i class="Hui-iconfont">&#xe600;</i>
							开始导入
						</button>
					</li>
				</ol>
			</div>
		</form>
	</div>
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/static/app/js/project/projectImport.js'/>"></script>
</body>
</html>