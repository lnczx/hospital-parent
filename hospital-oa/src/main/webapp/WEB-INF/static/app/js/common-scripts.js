//Global host+url
var host = window.location.host;
var appName = "hospital-oa";

var appRootUrl = "http://" + host + "/" + appName + "/";


function btn_add(path) {
	location.href = appRootUrl + path;
}

function btn_add_blank(path) {
	window.open(appRootUrl + path, "_blank");
}

//按钮时间
function btn_link(path) {
	// location.href = appRootUrl + "account/register?id=" + id;
//	alert(appRootUrl + path);
	 window.location.replace(appRootUrl + path);
}

function btn_show_pop(title,url,w,h){
	url =appRootUrl + url;
	layer_show(title,url,w,h);
}

function btn_show_layer(title,url) {
//	alert(appRootUrl + url);
	var index = layer.open({
		type: 2,
		title: title,
		content: appRootUrl + url
	});
	layer.full(index);
}

//删除按钮事件
function btn_del(path) {
	var statu = confirm("确定要删除吗?");
    if(!statu){
        return false;
    }
//    location.href = appRootUrl + "account/delete/" + id;
    location.href = appRootUrl + path;
}

function btn_push(confirmMsg, pId, statusType, status) {
	if (confirm(confirmMsg)) {
		var count = $("#count").val();
		
		if (count == 0) {
			alert("你还未导入信息，请导入后再提交.");
			return false;
		}
		
		var params = {};
		params.pId = pId;
		params.statusType = statusType;
		params.status = status;
		$.ajax({
			type: 'POST',
			url: appRootUrl +'/project/project-status-push',
			dataType: 'json',
			async : false,
			cache: false,
			data:params,
			success:function(result){
				var status = result.status;
				if (status == "999") {
					alert(result.msg);
					return false;
				}
				
				alert("操作成功");
				location.replace(location.href);
			},
			error:function(){
				
			}
		});
		
		
		location.replace(location.href);
	}
}


//菜单点击展开
function setSubMenuId(menuId) {
//	console.log("setSubMenuId ==" + menuId)
	$.cookie("menu-sub-id", menuId, {
		path : "/"
	});
	menuHighLight();
}

function menuHighLight() {
//	console.log("menuHighLight");
	var menuId = $.cookie('menu-sub-id');
	
	if (menuId == undefined) return false;
	if (menuId == "") return false;
	
	$(".sub").each(function() {
		$(this).find('li').each(function() {
			var tmenuId = $(this).attr("id");
			
//			console.log("tmenuId = " + tmenuId + "=== menuId = " + menuId);
			
			if (tmenuId == menuId) {


				$("#" + tmenuId).addClass("active");
//				$("#" + tmenuId).attr("style",""); 
			} else {

				$("#" + tmenuId).removeClass("active");
//				$("#" + tmenuId).attr("style","height: 0px;"); 
				
			}
		});
	});
}

//menuHighLight();

