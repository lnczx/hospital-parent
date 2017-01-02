$(function() {
	$('#attachFile').on('change', function() {
		var fileNames = '';
		$.each(this.files, function() {
			if (this.size.toFixed(2) > (32*1024*1024)) {
				alert("招生简章文件大小为32M以下.");
			}
			
			fileNames += '<span class="am-badge">' + this.name + '</span> ';
		});
		$('#file-list').val(fileNames);
	});
});


$(function() {
	$("#attach-form").validate({
		rules : {
			attachFile : {
				required : true,
				checkFileSize: true,
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

