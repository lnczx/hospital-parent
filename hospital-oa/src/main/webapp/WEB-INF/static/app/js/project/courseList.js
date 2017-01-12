function searchSubmit() {
	var actionUrl = "/hospital-oa/project/course/course-list";
	$("#searchForm").attr("action", actionUrl);
	$("#searchForm").submit();
}


function exportCourse() {
	var exportUrl = "/hospital-oa/project/course/course-export";
	$("#searchForm").attr("action", exportUrl);
	$("#searchForm").submit();
}