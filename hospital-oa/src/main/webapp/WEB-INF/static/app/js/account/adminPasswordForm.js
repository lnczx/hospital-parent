$(function() {
	$("#admin-password-form").validate({
		rules : {
			password : {
				required : true,
			},
			
			confirmPassword : {
				required : true,
				equalTo : "#password"
			},
		},
				
		onkeyup : false,
		focusCleanup : true,
		success : "valid",
		submitHandler : function(form) {
			var params = {};
			params.id = $("#id").val();
			params.password = $("#password").val();
			$.ajax({
				type: 'POST',
				url: appRootUrl +'/account/changePassword.json',
				dataType: 'json',
				cache: false,
				data:params,
				success:function(result){
					var status = result.status;
					if (status == "999") {
						alert(result.msg);
						return false;
					}
					
					alert("修改成功");
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				},
				error:function(){
					
				}
			});
		}
	});
	
});
