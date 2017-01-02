$('#courseDate').datepicker({
	format : "yyyy-mm-dd",
	language : "zh-CN",
	autoclose : true,
	startView : 0,
	todayBtn : true
});
var curFormDateTime = moment().format('YYYY-MM-DD');

$('#startTime').datetimepicker({
	language : 'zh-CN',
	startView : 'day',
	format : 'hh:ii',
	autoclose : true,
	todayBtn : false,
	startDate : curFormDateTime,
	minuteStep : 10,
	showButtonPanel : false,
});

$('#endTime').datetimepicker({
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
			
			credit : {
				required : true,
				isIntGteZero : true,
				
				remote : {
					url : appRootUrl + "/project/course/checkCredit.json",
					type : "get",
					dataType : "json",
					data : {
						pId : function() {
							return $("#pId").val();
						},
						courseId : function() {
							return $("#courseId").val();
						},
						credit : function() {
							return $("#credit").val();
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
		
		},
		
		messages : {
			username : {
				required : "请输入学分",
				remote : "学分超额."
			}
		},
				
		onkeyup : false,
		focusCleanup : true,
		success : "valid",
		submitHandler : function(form) {
			form.submit();
		}
	});
	
});
