function modalDo(pId) {
	$("#modal-pid").val(pId);
	$("#modal-do").modal("show");
	var modal = $("#modal-do");

}

function modalAttachView() {
	var pId = $("#modal-pid").val();

	btn_add_blank('project/attach-download?pId=' + pId);
}

function modalBtnPush() {
	var pId = $("#modal-pid").val();
	btn_push('确定要退回会议通知吗?', pId, 'statusAttach', 2)
}
