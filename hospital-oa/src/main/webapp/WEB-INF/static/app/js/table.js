$(function(){
	var table = $('.table-sort').dataTable({
		"lengthChange": false,
		"searching" : false,
		"ordering" : false,

		"bProcessing": true,
	    "sAutoWidth": false,
	    "bDestroy":true,
	    "sPaginationType": "bootstrap", // full_numbers
	    "iDisplayStart ": 10,
	    "iDisplayLength": 10,
	    "bPaginate": false, //hide pagination
	    "bFilter": false, //hide Search bar
	    "bInfo": false, // hide showing entries
//		"aaSorting": [[ 1, "desc" ]],//默认第几个排序
//		"bStateSave": true,//状态保存
//		"aoColumnDefs": [
//		  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
//		  {"orderable":false,"aTargets":[0,8,9]}// 制定列不参与排序
//		]
	});
	$('.table-sort tbody').on( 'click', 'tr', function () {
		if ( $(this).hasClass('selected') ) {
			$(this).removeClass('selected');
		}
		else {
			table.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
		}
	});
});
