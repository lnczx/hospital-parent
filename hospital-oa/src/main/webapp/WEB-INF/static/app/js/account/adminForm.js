$(function() {
	$("#admin-form").validate({
		rules : {
			username : {
				required : true,
				minlength : 3,
				maxlength : 16,
				remote : {
					url : appRootUrl + "/account/checkUsername.json",
					type : "get",
					dataType : "json",
					data : {
						username : function() {
							return $("#username").val();
						},
						id : function() {
							return $("#id").val();
						}
					},
					dataFilter : function(data, type) {
						var jsonStr = jQuery.parseJSON(data);
						var status = jsonStr.status;
						if (status == "0") {
							return true;
						}
						if (status == "999") {
							return false;
						}
						
					}
				
				}
			},
			name : {
				required : true,
			},
			
			password : {
				required : true,
			},
			
			confirmPassword : {
				required : true,
				equalTo : "#password"
			},
			
			orgId : {
				required : true
			},
			
			roleId : {
				required : true
			}
		},
		
		messages : {
			username : {
				required : "请输入用户名",
				remote : "用户名已经存在."
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
