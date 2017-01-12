function searchSubmit() {
	var actionUrl = "/hospital-oa/project/student/student-list";
	$("#searchForm").attr("action", actionUrl);
	$("#searchForm").submit();
}


function exportStudent() {
	var exportUrl = "/hospital-oa/project/student/student-export";
	$("#searchForm").attr("action", exportUrl);
	$("#searchForm").submit();
}
