<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="selectYearTag" uri="/WEB-INF/views/tags/selectYear.tld"%>
<%@ taglib prefix="selectOrgTag" uri="/WEB-INF/views/tags/selectOrg.tld"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link rel="stylesheet" href="<c:url value='/lib/icheck/icheck.css'/>" />
</head>
<body>
	<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 项目管理 <span
		class="c-gray en">&gt;</span> 我的项目一览 <a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px"
		href="javascript:location.replace(location.href);" title="刷新">
		<i class="Hui-iconfont">&#xe68f;</i>
	</a></nav>
	<div class="page-container">
		<div class="text-c">
			<form:form modelAttribute="searchModel" id="searchForm" action="/hospital-oa/account/list" method="GET">
			搜索条件：
			
			<span class="select-box inline">
					<selectYearTag:select />
				</span>
				<span class="select-box inline">
					<selectOrgTag:select selectedId="0" sessionOrgId="${sessionScope.accountAuth.orgId}" />
				</span>
				<form:input path="name" class="input-text" style="width: 250px" placeholder="输入项目名称" />
				<form:input path="pNo" class="input-text" style="width: 250px" placeholder="输入项目编号" />
				<button type="submit" class="btn btn-success radius" id="" name="">
					<i class="Hui-iconfont">&#xe665;</i>
					搜索
				</button>
			</form:form>
		</div>
		<div class="mt-20">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table id="DataTables_Table_0" class="table table-border table-bordered table-hover table-bg table-sort">
					<thead>
						<tr class="text-c">
							<th width="100">项目编号</th>
							<th width="260">项目名称</th>
							<th width="35">负责人</th>
							<th width="20">学分</th>
							<th width="30">人数</th>
							<th width="20">期数</th>
							<th width="50">起止时间</th>
							<th width="160">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${contentModel.list}" var="item">
							<tr class="text-c">
								<td>${ item.pNo }</td>
								<td>${ item.name }</td>
								<td>${ item.pHeader }</td>
								<td>${ item.credit }</td>
								<td>${ item.numRecruit }</td>
								<td>${item.numTerm }</td>
								<td>${item.dateRange }</td>
								<td class="td-manage"><a href="javascript:;" onclick="" class="btn btn-primary-outline size-S radius">招生简章</a>
									&nbsp; <a href="javascript:;" onclick="project_show('查看课表','schedule-list.html','10001')"
										class="btn btn-primary-outline size-S radius">课表</a> &nbsp; <a href="javascript:;"
										onclick="project_show('查看学员列表','student-list.html','10001')" class="btn btn-primary-outline size-S radius">学员</a>
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
		<script src="<c:url value='/static/app/js/project/projectList.js'/>"></script>
		<script src="<c:url value='/static/app/js/table.js'/>"></script>
</body>
</html>