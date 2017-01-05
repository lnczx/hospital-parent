$('#courseDate').datepicker({
	format : "yyyy-mm-dd",
	language : "zh-CN",
	autoclose : true,
	startView : 0,
	todayBtn : true
});
var curFormDateTime = moment().format('YYYY-MM-DD');
$('.form_datetime').datetimepicker({
	language : 'zh-CN',
	startView : 'day',
	format : 'hh:ii',
	autoclose : true,
	todayBtn : false,
	startDate : curFormDateTime,
	minuteStep : 10,
	showButtonPanel : false,
});



$(function() {
	$("#course-form").validate({
		rules : {
			
			courseDate : {
				required : true,
			},
			
			startTime : {
				required : true,
			},
			
			endTime : {
				required : true,
				remote : {
					url : appRootUrl + "/project/course/checkDupName.json",
					type : "get",
					dataType : "json",
					data : {
						pId : function() {
							return $("#pId").val();
						},
						courseId : function() {
							return $("#courseId").val();
						},
						courseDate : function() {
							return $("#courseDate").val();
						},
						startTime : function() {
							return $("#startTime").val();
						},
						endTime : function() {
							return $("#endTime").val();
						},
						content : function() {
							return $("#content").val();
						},
						
					},
					dataFilter : function(data, type) {
						var jsonStr = jQuery.parseJSON(data);
						var status = jsonStr.status;
						if (status == "0") {
							return true;
						}
						if (status == "999") {
							alert(jsonStr.msg);
							return false;
						}
						
					}
				
				}
			},
			
			content : {
				required : true
			},
			
			teacher : {
				required : true
			},
			
			titleStr : {
				required : true
			},
			
			orgName : {
				required : true
			},
			
			courseType : {
				required : true
			},
			
//			credit : {
//				required : true,
//				isIntGteZero : true,
//				
//				remote : {
//					url : appRootUrl + "/project/course/checkCredit.json",
//					type : "get",
//					dataType : "json",
//					data : {
//						pId : function() {
//							return $("#pId").val();
//						},
//						courseId : function() {
//							return $("#courseId").val();
//						},
//						credit : function() {
//							return $("#credit").val();
//						},
//					},
//					dataFilter : function(data, type) {
//						var jsonStr = jQuery.parseJSON(data);
//						var status = jsonStr.status;
//						if (status == "0") {
//							return true;
//						}
//						if (status == "999") {
//							alert(jsonStr.msg);
//							return false;
//						}
//						
//					}
//				
//				}
//			},
		
		},
		
		messages : {
//			credit : {
//				required : "请输入学分",
//				remote : "学分超额."
//			},
			
			endTime : {
				required : "请选择结束时间",
				remote : "重复的课程，请检查课程内容和时间是否已经录入过."
			},
		},
				
		onkeyup : false,
		focusCleanup : true,
		success : "valid",
		submitHandler : function(form) {
			form.submit();
		}
	});
	
});
