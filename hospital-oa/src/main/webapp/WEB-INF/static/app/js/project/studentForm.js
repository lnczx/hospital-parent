$('#birthDate').datepicker({
	format : "yyyy-mm-dd",
	language : "zh-CN",
	autoclose : true,
	startView : 0,
	todayBtn : true,
	defaultViewDate : {
		year : 1980,
		month : 0,
		day : 1
	}
});

$(function() {
	$("#student-form").validate({
		rules : {
			
			name : {
				required : true,
			},
			
			mobile : {
				required : true,
				isMobile : true,
				remote : {
					url : appRootUrl + "/project/student/checkDupName.json",
					type : "get",
					dataType : "json",
					data : {
						pId : function() {
							return $("#pId").val();
						},
						id : function() {
							return $("#id").val();
						},
						name : function() {
							return $("#name").val();
						},
						mobile : function() {
							return $("#mobile").val();
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
			
			mobile : {
				required : "请输入手机号",
				isMobile : "手机号格式不正确",
				remote : "重复学员"
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
