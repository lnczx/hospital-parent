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
			<form:form modelAttribute="searchModel" id="searchForm" action="/hospital-oa/project/student/student-list"
				method="GET">
			搜索条件：
				<form:hidden path="pId" />
				<form:input path="name" class="input-text" style="width: 250px" placeholder="输入学员姓名" />
				<form:input path="mobile" class="input-text" style="width: 250px" placeholder="输入学员手机号" />
				<button type="button" class="btn btn-primary radius" onclick="searchSubmit()">
					<i class="Hui-iconfont">&#xe665;</i>
					搜索
				</button>
			</form:form>
		</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l">
				<c:if test="${accountAuth.accountRole.id != 2}">
					<a href="javascript:;" onclick="btn_add('project/student/student-form?pId=${pId}&id=0')"
						<c:if test="${project.statusStudent != 1 }">
						class="btn btn-primary radius">
						</c:if>
						<c:if test="${project.statusStudent == 1 }">
						class="btn disabled radius">
						</c:if>
						<i class="Hui-iconfont">&#xe600;</i> 逐条添加学员</a>
					<a href="javascript:;" onclick="btn_add('project/student/student-import?pId=${pId}')"
						<c:if test="${project.statusStudent != 1 }">
						class="btn btn-secondary radius">
						</c:if>
						<c:if test="${project.statusStudent == 1 }">
						class="btn disabled radius">
						</c:if>
						<i class="Hui-iconfont">&#xe645;</i> 批量导入</a>
				</c:if>
				<a href="javascript:;" onclick="exportStudent()" class="btn btn-success radius">
					<i class="Hui-iconfont">&#xe644;</i>
					导出Excel
				</a>
				<c:if test="${accountAuth.accountRole.id != 2}">
					<a href="#" onclick="btn_push('点击提交后，将无法修改，请确定是否提交？?', ${pId}, 'statusStudent', 1)" 
						<c:if test="${project.statusStudent != 1 }">
						class="btn btn-danger radius">
						</c:if>
						<c:if test="${project.statusStudent == 1 }">
						class="btn disabled radius">
						</c:if>
						<i class="Hui-iconfont">&#xe645;</i>
						提交
					</a>
				</c:if>
				<c:if test="${accountAuth.accountRole.id == 2}">
					<a href="#" onclick="btn_push('确定要退回项目学员吗?', ${pId}, 'statusStudent', 2)" 
					    <c:if test="${project.statusStudent == 1 }">
						class="btn btn-danger radius">
						</c:if>
						<c:if test="${project.statusStudent != 1 }">
						class="btn disabled radius">
						</c:if>
						<i class="Hui-iconfont">&#xe645;</i>
						退回
					</a>
				</c:if>
			</span>
			<span class="r">
				<small class=" f-16 c-red">说明：下方按钮为绿色时表示已提交，红色表示退回，蓝色表示有数据但未提交。</small>
			</span>
		</div>
		<input type="hidden" id="count" value="${count }"/>
		<div class="mt-20">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table id="DataTables_Table_0" class="table table-border table-bordered table-hover table-bg table-sort">
					<thead>
						<tr class="text-c">
							<th width="35">学员ID</th>
							<th width="55">姓名</th>
							<th width="80">联系方式</th>
							<th width="40">职称</th>
							<th width="40">职务</th>
							<th width="40">学历</th>
							<th width="150">单位</th>
							<c:if test="${accountAuth.accountRole.id != 2 && project.statusStudent != 1}">
								<th width="120">操作</th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${contentModel.list}" var="item">
							<tr class="text-c">
								<td>${ item.stuId }</td>
								<td><strong><a href="javascript:;"
											onclick="btn_show_layer('查看学员','project/student/student-view?id=${item.id}','10001')">${ item.name }</a></strong></td>
								<td>${ item.mobile }</td>
								<td>${ item.titleStr }</td>
								<td>${ item.dutyName }</td>
								<td>${ item.eduName }</td>
								<td>${ item.orgName }</td>
								<c:if test="${accountAuth.accountRole.id != 2 && project.statusStudent != 1 }">
									<td class="td-manage"><a href="javascript:;"
											onclick="btn_add('project/student/student-form?pId=${pId}&id=${item.id }')"
											class="btn btn-primary-outline size-S radius">修改</a> &nbsp; <a href="javascript:;"
											onclick="btn_del('project/student/student-del?pId=${pId}&id=${item.id }')"
											class="btn btn-primary-outline size-S radius">删除</a> &nbsp;</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:import url="../shared/paging.jsp">
					<c:param name="pageModelName" value="contentModel" />
					<c:param name="urlAddress" value="/project/student/student-list" />
				</c:import>
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