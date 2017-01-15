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
			<form:form  id="matchForm" action="" method="GET">

				<input type="text" id="matchName" name="matchName" style="width: 250px" placeholder="输入匹配单位名称" />
				
				<button type="button" class="btn btn-success radius" onClick="matchTest()">
					<i class="Hui-iconfont">&#xe665;</i>
					匹配测试
				</button>
				
				<label id="matchResult"></label>
				
			</form:form>
		</div>
		<div class="mt-20">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table id="DataTables_Table_0" class="table table-border table-bordered table-hover table-bg table-sort">
					<thead>
						<tr class="text-c">
							<th width="100">单位名称</th>
							<th width="160">单位简称</th>
							<th width="35">简称匹配数</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${contentModel}" var="item">
							<tr class="text-c">
								<td>${ item.name }</td>
								<td>${ item.shortName }</td>
								<td>${ item.mc }</td>
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
		<script src="<c:url value='/static/app/js/table.js'/>"></script>
		
		<script>
			function matchTest() {
				var matchName = $("#matchName").val();
				if (matchName == undefined || matchName == '') {
					alert("需要输入匹配单位名称.");
					return false;s
				}
				$("#matchResult").html("");
				var params = {};
				params.matchName = matchName;
				$.ajax({
					type: 'POST',
					url: appRootUrl +'/project/shortname-match.json',
					dataType: 'json',
					cache: false,
					data:params,
					success:function(result){
						var status = result.status;
						if (status == "999") {
							alert(result.msg);
							return false;
						}
						
						var datas = result.data;
						if (datas == undefined || datas == '' || datas == null) {
							$("#matchResult").html("未找到匹配单位");
						} else {
							
							var matchResultHtml = '<br>匹配结果:' + datas.length + "个.<br>";
							$.each(datas, function(i, item) {
								matchResultHtml+="---单位全称" + item.name + ", 单位简称: " + item.short_name + "<br>";
							});
							$("#matchResult").html(matchResultHtml);
						}
						
						
					},
					error:function(){
						
					}
				});
			}
		
		</script>
</body>
</html>