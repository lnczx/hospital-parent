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
		<div class="Huialert Huialert-danger radius">
			<h3>导入错误提示：</h3>
			<ol>
				<li>${errors }</li>
				<li>
					<a href="/hospital-oa/project/project-import" class="btn btn-success">返回</a>
				</li>
			</ol>
		</div>
		<c:if test="${tableDatas != ''}">
			<div class="mt-20">
				<table id="error-table" class="table table-border table-bordered table-hover table-bg table-sort">
					<thead>
						<tr class="text-c">
							<th width="20">行数</th>
							<th width="60">项目编号</th>
							<th width="80">项目名称</th>
							<th width="80">申办单位</th>
							<th width="40">项目负责人</th>
							<th width="50">负责人电话</th>
							<th width="60">举办地点</th>
							<th width="30">授予学分</th>
							<th width="30">拟招人数</th>
							<th width="30">项目期数</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${tableDatas}" var="item">
						<tr>
							<td>${item[9]}</td>
							<td>${item[0]}</td>
							<td>${item[1]}</td>
							<td>${item[2]}</td>
							<td>${item[3]}</td>
							<td>${item[4]}</td>
							<td>${item[5]}</td>
							<td>${item[6]}</td>
							<td>${item[7]}</td>
							<td>${item[8]}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</div>
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script type="text/javascript" src="<c:url value='/lib/datatables/1.10.0/jquery.dataTables.min.js'/>"></script>
	<c:if test="${tableDatas != ''}">
		<script>
			$(function() {
				
				
				
				$('#error-table').dataTable({
					"lengthChange" : false,
					searching : false,
					ordering : false,

				});
			});
		</script>
	</c:if>
</body>
</html>