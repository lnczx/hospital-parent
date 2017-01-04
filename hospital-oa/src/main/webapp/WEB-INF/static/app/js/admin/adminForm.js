$(function() {
	$("#admin-form").validate({
		rules : {
			name : {
				required : true
			},

			username : {
				required : true
			},
			password : {
				required : true
			},
			confirmPassword : {
				required : true，
				equalTo:"#password",
			},
			orgId : {
				required : true
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