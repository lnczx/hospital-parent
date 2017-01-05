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
	<div class="page-container">
		<form class="importForm" id="attach-form" method="post" action="/hospital-oa/project/attach-import" enctype="multipart/form-data">
			<div class="Huialert Huialert-success radius">
			<input type="hidden" name ="pId" id ="pId" value="${pId }"/>
				<h3>使用提示：</h3>
				<ol>
					<li>
						招生简章文件可以是Word、PDF或各类图片文件，上传的文件大小请不要超过32M。
					</li>
				</ol>
			</div>
			<div class="Huialert Huialert Huialert-danger radius">
				<h3>开始上传招生简章文件</h3>
				<ol>
					<li>
						<span class="btn-upload form-group">
							<input class="input-text upload-url" type="text" name="file-list" id="file-list" readonly datatype="*"
								nullmsg="请添加附件！" style="width: 200px">
								
							<a href="javascript:void();" class="btn btn-primary upload-btn">
								<i class="Hui-iconfont">&#xe642;</i>
								选择 招生简章文件
							</a>
							<input type="file" multiple id="attachFile" name="attachFile" class="input-file"  data-rule-required="true">
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
	<script src="<c:url value='/lib/jquery.validation/1.14.0/jquery.validate.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery.validation/1.14.0/validate-methods.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/lib/jquery.validation/1.14.0/messages_zh.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/static/app/js/project/attachImport.js'/>"></script>
</body>
</html>