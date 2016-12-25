<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="selectOrgTag" uri="/WEB-INF/views/tags/selectOrg.tld"%>
<%@ taglib prefix="selectRoleTag" uri="/WEB-INF/views/tags/selectRole.tld"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link rel="stylesheet" href="<c:url value='/lib/icheck/icheck.css'/>" />
</head>
<body>
	<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span
		class="c-gray en">&gt;</span> 用户管理 <a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px"
		href="javascript:location.replace(location.href);" title="刷新">
		<i class="Hui-iconfont">&#xe68f;</i>
	</a></nav>
	<div class="page-container">
		<div class="text-c">
			<form:form modelAttribute="searchModel" id="searchForm" action="/hospital-oa/account/list" method="GET">
			搜索条件：
			<span class="select-box inline">
					<selectOrgTag:select selectedId="0" sessionOrgId="${sessionScope.accountAuth.orgId}" />
				</span>
				<span class="select-box inline">
					<selectRoleTag:select selectedId="0" />
				</span>
				<form:input path="username" class="input-text" style="width: 250px" placeholder="输入用户名" />
				<button type="submit" class="btn btn-success radius" id="" name="">
					<i class="Hui-iconfont">&#xe665;</i>
					搜用户
				</button>
			</form:form>
		</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l">
				<a href="javascript:;" onClick="btn_add('/account/adminForm?id=0')" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe600;</i>
					添加用户
				</a>
			</span>
		</div>
		<div class="mt-20">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table id="DataTables_Table_0" class="table table-border table-bordered table-hover table-bg table-sort">
					<thead>
						<tr class="text-c">
							<th width="30">ID</th>
							<th width="60">用户名</th>
							<th width="80">昵称</th>
							<th width="130">二级单位</th>
							<th width="70">用户类型</th>
							<th width="120">联系电话</th>
							<th width="120">邮箱</th>
							<th width="40">状态</th>
							<th width="120">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${contentModel.list}" var="item">
							<tr class="text-c">
								<td>${ item.id }</td>
								<td>${ item.username }</td>
								<td>${ item.name }</td>
								<td>${ item.orgName }</td>
								<td>${ item.roleName }</td>
								<td class="text-l">${item.mobile }</td>
								<td>${item.email }</td>
								<td class="td-status"><c:if test="${item.enable == 1 }">
										<span class="label label-success radius">正常</span>
									</c:if> <c:if test="${item.enable == 0 }">
										<span class="label label-danger radius">停用</span>
									</c:if></td>
								<td class="td-manage">
									<a style="text-decoration: none" onClick="btn_add('/account/adminForm?id=${item.id}')" href="javascript:;"
										title="修改">
										<i class="Hui-iconfont f-18">&#xe60c;</i>
									</a> |
									<a style="text-decoration: none" class="ml-5"
										onClick="btn_add_pop('修改密码','/account/adminPasswordForm?id=${item.id}','600','270')" href="javascript:;" title="修改密码">
										<i class="Hui-iconfont f-18">&#xe63f;</i>
									</a> 
									
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
		<script src="<c:url value='/static/app/js/account/list.js'/>"></script>
		<script src="<c:url value='/static/app/js/table.js'/>"></script>
</body>
</html>