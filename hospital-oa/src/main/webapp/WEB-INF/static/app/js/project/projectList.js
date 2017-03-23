function modalDo(pId) {
	$("#modal-pid").val(pId);
	$("#modal-do").modal("show");
	var modal = $("#modal-do");

}

function modalDoRole3(pId) {
	$("#modal-pid").val(pId);
	$("#modal-do-role3").modal("show");
	var modal = $("#modal-do-role3");

}

function modalAttachView() {
	var pId = $("#modal-pid").val();

	btn_add_blank('project/attach-download?pId=' + pId);
}

function modalBtnPush() {
	var pId = $("#modal-pid").val();
	btn_push('确定要退回会议通知吗?', pId, 'statusAttach', 2)
}

function modalReImport() {
	var pId = $("#modal-pid").val();
	btn_link('project/attach-import?pId=' + pId);
}