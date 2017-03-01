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
		<form class="importForm" method="post" action="/hospital-oa/project/course/course-import" enctype="multipart/form-data">
			<input type="hidden" name ="pId" id ="pId" value="${pId }"/>
			<div class="Huialert Huialert-success radius">
				<h3>使用提示：</h3>
				<ol>
					<li>
						1.第一次使用时，请点击下载：
						<a href="/hospital-oa/attach/会议日程导入Excel模板.xls" target="_blank"><font color="#FF0000">《会议日程导入Excel模板》</font></a>文件，并逐条填写。
						<br>
						2.在《会议日程导入Excel模板》文件中填写信息时，请注意内容和格式的规范性，详情可点击：<a href="/hospital-oa/home/help">
						<font color="#FF0000">《导入Excel文件填写说明》</font></a>查看。
					</li>
				</ol>
			</div>
			<div class="Huialert Huialert Huialert-danger radius">
				<h3>开始上传Excel进行导入操作</h3>
				<ol>
					<li>
						<span class="btn-upload form-group">
							<input class="input-text upload-url" type="text" name="file-list" id="file-list" readonly datatype="*"
								nullmsg="请添加附件！" style="width: 200px">
							<a href="javascript:void();" class="btn btn-primary upload-btn">
								<i class="Hui-iconfont">&#xe642;</i>
								选择 会议日程Excel文件
							</a>
							<input type="file" multiple name="course-file" class="input-file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"  data-rule-required="true">
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
	<script src="<c:url value='/static/app/js/project/courseImport.js'/>"></script>
</body>
</html>