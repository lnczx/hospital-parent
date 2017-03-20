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
				<button type="button" class="btn btn-primary radius" onclick="searchSubmit()">
					<i class="Hui-iconfont">&#xe665;</i>
					搜索
				</button>
			</form:form>
		</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l">
				<c:if test="${accountAuth.accountRole.id != 2}">
					<a href="javascript:;" onclick="btn_add('project/course/course-form?pId=${pId}&courseId=0')"
						<c:if test="${project.statusCourse != 1 }">
						class="btn btn-primary radius">
						</c:if>
						<c:if test="${project.statusCourse == 1 }">
						class="btn disabled radius">
						</c:if>
						<i class="Hui-iconfont">&#xe600;</i> 逐条添加课程</a>
					<a href="javascript:;" onclick="btn_add('project/course/course-import?pId=${pId}')"

						<c:if test="${project.statusCourse != 1 }">
						class="btn btn-secondary radius">
						</c:if>
						<c:if test="${project.statusCourse == 1 }">
						class="btn disabled radius">
						</c:if>
						<i class="Hui-iconfont">&#xe645;</i>批量导入</a>
				</c:if>
				<a href="javascript:;" onclick="exportCourse()" class="btn btn-success radius">
					<i class="Hui-iconfont">&#xe644;</i>
					导出Excel
				</a>
				<c:if test="${accountAuth.accountRole.id != 2 && project.statusCourse != 1}">
					<a href="#" onclick="btn_push('确定要提交会议日程吗?', ${pId}, 'statusCourse', 1)" class="btn btn-danger radius">
						<i class="Hui-iconfont">&#xe645;</i>
						提交
					</a>
				</c:if>
				<c:if test="${accountAuth.accountRole.id == 2 && project.statusCourse == 1}">
					<a href="#" onclick="btn_push('确定要退回会议日程吗?', ${pId}, 'statusCourse', 2)" class="btn btn-danger radius">
						<i class="Hui-iconfont">&#xe645;</i>
						退回
					</a>
				</c:if>
			</span>
			<span class="r">
				<small>说明：下方按钮为绿色时表示已提交，红色表示退回，蓝色表示有数据但未提交，灰色表示提交后不能操作。</small>
			</span>
		</div>
		<div class="mt-20">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table id="DataTables_Table_0" class="table table-border table-bordered table-hover table-bg table-sort">
					<thead>
						<tr class="text-c">
							<th width="60">日期</th>
							<th width="55">开始时间</th>
							<th width="55">结束时间</th>
							<th width="55">学时(分钟)</th>
							<th width="200">课程内容</th>
							<th width="40">教师</th>
							<th width="30">职称</th>
							<th width="150">单位</th>
							<th width="40">类型</th>
							<c:if test="${accountAuth.accountRole.id != 2 && project.statusCourse != 1}">
								<th width="110">操作</th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${contentModel.list}" var="item">
							<tr class="text-c">
								<td>${ item.courseDate }</td>
								<td>${ item.startTime }</td>
								<td>${ item.endTime }</td>
								<td>${ item.courseMin }</td>
								<td><strong>${ item.content }</strong></td>
								<td>${ item.teacher }</td>
								<td>${ item.titleStr }</td>
								<td>${ item.orgName }</td>
								<td>${ item.courseType }</td>
								<c:if test="${accountAuth.accountRole.id != 2 && project.statusCourse != 1 }">
									<td class="td-manage"><a href="javascript:;"
											onclick="btn_add('project/course/course-form?pId=${pId}&courseId=${item.courseId }')"
											class="btn btn-primary-outline size-S radius">修改</a> &nbsp; <a href="javascript:;"
											onclick="btn_del('project/course/course-del?pId=${pId}&courseId=${item.courseId }')"
											class="btn btn-primary-outline size-S radius">删除</a> &nbsp;</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:import url="../shared/paging.jsp">
					<c:param name="pageModelName" value="contentModel" />
					<c:param name="urlAddress" value="/project/course/course-list" />
				</c:import>
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