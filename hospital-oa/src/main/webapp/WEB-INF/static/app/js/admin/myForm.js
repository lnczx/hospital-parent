$("#orgId").attr("disabled","disabled");  
$("#roleId").attr("disabled","disabled");  

$(function() {
	$("#my-form").validate({
		rules : {
			name : {
				required : true
			},

			confirmPassword : {
				equalTo:"#password",
			},

		
		},
		
		onkeyup : false,
		focusCleanup : true,
		success : "valid",
		submitHandler : function(form) {
			
			var formData = {};
			formData.naem = $("#name").val();
			formData.password = $("#password").val();
			formData.mobile = $("#mobile").val();
			formData.email = $("#email").val();
			console.log(formData);
			$.ajax({
				url : appRootUrl + "home/myForm.json",
				type : "POST",
				data: formData, 
				async : false,
				success : function(result) { 
					var index = parent.layer.getFrameIndex(window.name);
		            parent.layer.close(index);
				}
				
			});
		},
	});
	
});
