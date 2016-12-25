//Global host+url
var host = window.location.host;
var appName = "/hospital-oa";

var appRootUrl = "http://" + host + "/" + appName + "/";

function btn_add_pop(title,url,id,w,h){
	url = appName + "/" + url;
	layer_show(title,url,w,h);
}

function btn_update_pop(title,url,id,w,h){
	url = appName + "/" + url;
	layer_show(title,url,w,h);
}

//新增事件
function btn_add_layer(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: appName + "/" + url
	});
	layer.full(index);
}


//修改按钮事件
function btn_update_layer(title,url) {
	var index = layer.open({
		type: 2,
		title: title,
		content: appName + "/" + url
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

