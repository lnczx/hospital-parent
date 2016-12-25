$("#admin-form").validate({
	rules : {
		username : {
			required : true,
			minlength : 3,
			maxlength : 16
		},
		name : {
			required : true,
		},
		
		password:{
			required:true,
		},
		password2:{
			required:true,
			equalTo: "#password"
		},
		
		roleId : {
			required : true
		}

	
	},
	onkeyup : false,
	focusCleanup : true,
	success : "valid",
	submitHandler : function(form) {
		// $(form).ajaxSubmit();
		var index = parent.layer.getFrameIndex(window.name);
		// parent.$('.btn-refresh').click();
		parent.layer.close(index);
	}
});
