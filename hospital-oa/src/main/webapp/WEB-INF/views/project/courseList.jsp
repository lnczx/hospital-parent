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
</head>
<body>
	<div class="page-container">
		<div class="text-c">
			<form:form modelAttribute="searchModel" id="searchForm" action="/hospital-oa/project/course/course-list" method="GET">
			搜索条件：
				<form:hidden path="pId" />
				<form:input path="content" class="input-text" style="width: 250px" placeholder="输入课程名称" />
				<form:input path="teacher" class="input-text" style="width: 250px" placeholder="输入教师名称" />
				<button type="submit" class="btn btn-success radius" id="" name="">
					<i class="Hui-iconfont">&#xe665;</i>
					搜索
				</button>
			</form:form>
		</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l">
				<c:if test="${accountAuth.accountRole.id != 2}">
				<a href="javascript:;" onclick="btn_add('project/course/course-form?pId=${pId}&courseId=0')" class="btn btn-primary radius">
					<i class="Hui-iconfont">&#xe600;</i>
					逐条添加课程
				</a>
				</c:if>
		</div>
		<div class="mt-20">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table id="DataTables_Table_0" class="table table-border table-bordered table-hover table-bg table-sort">
					<thead>
						<tr class="text-c">
							<th width="60">日期</th>
							<th width="55">开始时间</th>
							<th width="55">结束时间</th>
							<th width="200">课程内容</th>
							<th width="40">教师</th>
							<th width="30">职称</th>
							<th width="150">单位</th>
							<th width="30">类型</th>
							<th width="30">学时(分钟)</th>
							<th width="120">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${contentModel.list}" var="item">
							<tr class="text-c">
								<td>${ item.courseDate }</td>
								<td>${ item.startTime }</td>
								<td>${ item.endTime }</td>
								<td><strong>${ item.content }</strong></td>
								<td>${ item.teacher }</td>
								<td>${ item.titleStr }</td>
								<td>${ item.orgName }</td>
								<td>${ item.courseType }</td>
								<td>${ item.credit }</td>
								<td class="td-manage">
									<c:if test="${accountAuth.accountRole.id != 2}">
									<a href="javascript:;" onclick="btn_add('project/course/course-form?pId=${pId}&courseId=${item.courseId }')"  class="btn btn-primary-outline size-S radius">修改</a> &nbsp; 
									
									<a href="javascript:;" onclick="btn_del('project/course/course-del?pId=${pId}&courseId=${item.courseId }')"  class="btn btn-primary-outline size-S radius">删除</a> &nbsp; 
									</c:if>
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
		<script src="<c:url value='/static/app/js/project/courseList.js'/>"></script>
		<script src="<c:url value='/static/app/js/table.js'/>"></script>
</body>
</html>