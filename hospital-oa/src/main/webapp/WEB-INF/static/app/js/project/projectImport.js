$(function() {
	$('#project-file').on('change', function() {
		var fileNames = '';
		$.each(this.files, function() {
			fileNames += '<span class="am-badge">' + this.name + '</span> ';
		});
		$('#file-list').val(fileNames);
	});
});
