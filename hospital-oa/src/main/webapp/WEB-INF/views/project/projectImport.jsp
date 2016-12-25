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
		<div class="text-c">
			<div class="text-c">
				<form class="Huiform" method="post" action="" target="_self">
				
					<span class="btn-upload form-group">
						<input class="input-text upload-url" type="text" name="uploadfile-2" id="uploadfile-2" readonly datatype="*"
							nullmsg="请添加附件！" style="width: 200px">
						<a href="javascript:void();" class="btn btn-primary upload-btn">
							<i class="Hui-iconfont">&#xe642;</i>
							选择 项目Excel文件
						</a>
						<input type="file" multiple name="file-2" class="input-file">
					</span>
				
					<span class="btn-upload form-group">
						<input class="input-text upload-url" type="text" name="uploadfile-2" id="uploadfile-2" readonly datatype="*"
							nullmsg="请添加附件！" style="width: 200px">
						<a href="javascript:void();" class="btn btn-primary upload-btn">
							<i class="Hui-iconfont">&#xe642;</i>
							选择 项目Excel文件
						</a>
						<input type="file" multiple name="file-2" class="input-file">
					</span>
					<button type="button" class="btn btn-success" id="" name="" onClick="picture_colume_add(this);">
						<i class="Hui-iconfont">&#xe600;</i>
						开始导入
					</button>
				</form>
			</div>
		</div>
		
		<!-- js placed at the end of the document so the pages load faster -->
		<!--common script for all pages-->
		<%@ include file="../shared/importJs.jsp"%>
		<!--script for this page-->
		<script src="<c:url value='/static/app/js/project/projectImport.js'/>"></script>

</body>
</html>