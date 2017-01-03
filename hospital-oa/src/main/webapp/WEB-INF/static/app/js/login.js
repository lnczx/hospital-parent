$('#drag').drag();

$(function() {
	$("#loginform").validate({
		rules : {
			username : {
				required : true,
			},
			
			password : {
				required : true,
			},
		},
		
		messages : {
			username : {
				required : "请输入用户名",
			},
			
			password : {
				required : "请输入密码",
			},
		},
				
		onkeyup : false,
		focusCleanup : true,
		success : "valid",
		submitHandler : function(form) {
			var dragText = $(".drag_text").html();
			if (dragText != '验证通过') {
				console.log("drag fail");
				return false;
			}
			form.submit();
		}
	});
	
});



if(window !=top){  
    top.location.href=location.href;  
}