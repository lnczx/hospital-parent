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
		<div class="text-c">
			<form:form modelAttribute="searchModel" id="searchForm" action="/hospital-oa/project/student/student-list" method="GET">
			搜索条件：
				<form:hidden path="pId" />
				<form:input path="name" class="input-text" style="width: 250px" placeholder="输入学员姓名" />
				<form:input path="mobile" class="input-text" style="width: 250px" placeholder="输入学员手机号" />
				<button type="submit" class="btn btn-success radius" id="" name="">
					<i class="Hui-iconfont">&#xe665;</i>
					搜索
				</button>
			</form:form>
		</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l">
				<a href="javascript:;" onclick="btn_add('project/student/student-form?pId=${pId}&id=0')" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe600;</i>
					逐条添加学员
				</a>
		</div>
		<div class="mt-20">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table id="DataTables_Table_0" class="table table-border table-bordered table-hover table-bg table-sort">
					<thead>
						<tr class="text-c">
							<th width="25">ID</th>
							<th width="55">姓名</th>
							<th width="30">性别</th>
							<th width="80">手机号</th>
							<th width="120">身份证</th>
							<th width="160">地址</th>
							<th width="160">所在单位</th>
							<th width="40">职称</th>
							<th width="120">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${contentModel.list}" var="item">
							<tr class="text-c">
								<td>${ item.stuId }</td>
								<td>${ item.name }</td>
								<td>${ item.sex }</td>
								<td>${ item.mobile }</td>
								<td>${ item.idCard }</td>
								<td>${ item.addr }</td>
								<td>${ item.orgName }</td>
								<td>${ item.titleStr }</td>
								
								<td class="td-manage">
								
									<a href="javascript:;" onclick="btn_add('project/student/student-form?pId=${pId}&id=${item.id }')"  class="btn btn-primary-outline size-S radius">修改</a> &nbsp; 
									
									<a href="javascript:;" onclick="btn_del('project/student/student-del?pId=${pId}&id=${item.id }')"  class="btn btn-primary-outline size-S radius">删除</a> &nbsp; 
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!-- js placed at the end of the document so the pages load faster -->
		<!--common script for all pages-->
		<%@ include file="../shared/importJs.jsp"%>
		<!--script for this page-->
		<script type="text/javascript" src="<c:url value='/lib/datatables/1.10.0/jquery.dataTables.min.js'/>"></script>
		<script src="<c:url value='/static/app/js/project/studentList.js'/>"></script>
		<script src="<c:url value='/static/app/js/table.js'/>"></script>
</body>
</html>