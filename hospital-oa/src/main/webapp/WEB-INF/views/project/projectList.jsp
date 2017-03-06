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
		class="c-gray en">&gt;</span> 我的项目列表 <a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px"
		href="javascript:location.replace(location.href);" title="刷新">
		<i class="Hui-iconfont">&#xe68f;刷新</i>
	</a></nav>
	<div class="page-container">
		<div class="text-c">
			<form:form modelAttribute="searchModel" id="searchForm" action="/hospital-oa/project/list" method="GET">
			搜索条件：
			
			<span class="select-box inline">
					<selectYearTag:select selectedId="${searchModel.pYear }"/>
				</span>
				<span class="select-box inline">
					<selectOrgTag:select selectedId="0" sessionOrgId="${sessionScope.accountAuth.orgId}" />
				</span>
				<form:input path="name" class="input-text" style="width: 250px" placeholder="输入项目名称" />
				<form:input path="pNo" class="input-text" style="width: 250px" placeholder="输入项目编号" />
				<input type="hidden" name="linkType" value="${linkType }"/>
				<button type="submit" class="btn btn-primary radius" id="" name="">
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
							<th width="160">项目名称</th>
							<th width="35">负责人</th>
							<th width="20">学分</th>
							<th width="30">人数</th>
							<th width="20">期数</th>
							<th width="120">起止时间</th>
							<th width="200">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${contentModel.list}" var="item">
							<tr class="text-c">
								<td>${ item.pNo }</td>
								<td><strong><a href="javascript:;" onclick="btn_show_layer('查看项目','project/project-view?pId=${item.pId}','10001')" >${ item.name }</a></strong></td>
								<td>${ item.pHeader }</td>
								<td>${ item.credit }</td>
								<td>${ item.numRecruit }</td>
								<td>${item.numTerm }</td>
								<td>${item.dateRange }</td>
								<td class="td-manage">
								
									
								
									<c:if test="${linkType == 'project' || linkType == '' || linkType == null}">
										<c:if test="${accountAuth.accountRole.id == 1}">
											<a href="javascript:;" onclick="btn_add('project/project-form?pId=${item.pId}')"  class="btn btn-primary-outline size-S radius">修改</a> &nbsp; 
										</c:if>
										<c:if test="${item.briefingFilePath != '' }">
											<a href="javascript:;" onclick="btn_add_blank('project/attach-download?pId=${item.pId}')" class="btn btn-success size-S radius">会议通知</a>
										</c:if>
										
										<c:if test="${item.briefingFilePath == '' }">
											<a href="javascript:;" onclick="alert('提示：项目管理员还未上传本项目的会议通知。')" class="btn btn-primary-outline size-S radius">会议通知</a>&nbsp; 
										</c:if>
										
										<a href="javascript:;" onclick="btn_show_layer('查看会议日程','project/course/course-list?pId=${item.pId}','10001')"  
											<c:if test="${item.hasCourse == true }">
												class="btn btn-success size-S radius">
											</c:if>
											
											<c:if test="${item.hasCourse == false }">
												class="btn btn-primary-outline size-S radius">
											</c:if>
											
										会议日程</a> &nbsp; 
										
										
										
										<a href="javascript:;" onclick="btn_show_layer('查看学员列表','project/student/student-list?pId=${item.pId}','10001')" 
											<c:if test="${item.hasStudent == true }">
												class="btn btn-success size-S radius">
											</c:if>
											
											<c:if test="${item.hasStudent == false }">
												class="btn btn-primary-outline size-S radius">
											</c:if>
										
										
										学员</a>
									</c:if>
									<c:if test="${linkType == 'attach' }">
										<c:if test="${accountAuth.accountRole.id != 2}">
											<a href="javascript:;" onclick="btn_show_layer('导入会议通知','project/attach-import?pId=${item.pId}','10001')" class="btn btn-primary-outline size-S radius">导入会议通知</a> &nbsp;
										</c:if>
										
										
										<c:if test="${item.briefingFilePath != '' }">
											<a href="javascript:;" onclick="btn_add_blank('project/attach-download?pId=${item.pId}')" class="btn btn-success size-S radius">查看会议通知</a>
										</c:if>
										
										<c:if test="${item.briefingFilePath == '' }">
											<a href="javascript:;" onclick="alert('提示：项目管理员还未上传本项目的会议通知。')" class="btn btn-primary-outline size-S radius">查看会议通知</a>&nbsp; 
										</c:if>
										
									</c:if>
									
									<c:if test="${linkType == 'course' }">
										<c:if test="${accountAuth.accountRole.id != 2}">
											<a href="javascript:;" onclick="btn_show_layer('导入会议日程','project/course/course-import?pId=${item.pId}','10001')" class="btn btn-primary-outline size-S radius">导入会议日程</a> &nbsp;
										</c:if>
										<a href="javascript:;" onclick="btn_show_layer('查看会议日程','project/course/course-list?pId=${item.pId}','10001')" 
											
											<c:if test="${item.hasCourse == true }">
												class="btn btn-success size-S radius">
											</c:if>
											
											<c:if test="${item.hasCourse == false }">
												class="btn btn-primary-outline size-S radius">
											</c:if>
											
										查看会议日程</a>
									</c:if>
									
									<c:if test="${linkType == 'student' }">
										<c:if test="${accountAuth.accountRole.id != 2}">
											<a href="javascript:;" onclick="btn_show_layer('导入学员','project/student/student-import?pId=${item.pId}','10001')" class="btn btn-primary-outline size-S radius">导入学员</a> &nbsp;
										</c:if>
										<a href="javascript:;" onclick="btn_show_layer('查看学员','project/student/student-list?pId=${item.pId}','10001')" 
											<c:if test="${item.hasStudent == true }">
												class="btn btn-success size-S radius">
											</c:if>
											
											<c:if test="${item.hasStudent == false }">
												class="btn btn-primary-outline size-S radius">
											</c:if>
											查看学员</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<c:import url = "../shared/paging.jsp">
      				<c:param name="pageModelName" value="contentModel"/>
      				<c:param name="urlAddress" value="/project/list"/>
     			  </c:import>
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