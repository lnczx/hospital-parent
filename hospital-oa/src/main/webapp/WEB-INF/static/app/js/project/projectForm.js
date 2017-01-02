
$(function() {
	$("#project-form").validate({
		rules : {
			
			pNo : {
				required : true,
			},
			
			name : {
				required : true,
			},
			
			orgId : {
				required : true,
			},
			
			numTerm : {
				required : true,
				isIntGteZero : true,
				remote : {
					url : appRootUrl + "/project/checkDupName.json",
					type : "get",
					dataType : "json",
					data : {
						pNo : function() {
							return $("#pNo").val();
						},
						pId : function() {
							return $("#pId").val();
						},
						numTerm : function() {
							return $("#numTerm").val();
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
			
			pHeader : {
				required : true
			},
			
			pHeaderTel : {
				required : true
			},
			
			addr : {
				required : true
			},
			
			credit : {
				required : true,
				isIntGteZero : true,
			},
			
			numRecruit : {
				required : true,
				isIntGteZero : true,
			},
			
		},
		
		messages : {
			numTerm : {
				required : "请输入项目期数",
				remote : "重复项"
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
