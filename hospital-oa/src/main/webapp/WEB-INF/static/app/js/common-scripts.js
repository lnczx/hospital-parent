//Global host+url
var host = window.location.host;
var appName = "hospital-oa";

var appRootUrl = "http://" + host + "/" + appName + "/";

//新增事件

function btn_add(path) {
	location.href = appRootUrl + path;
}


//修改按钮事件
function btn_update(path) {
//	location.href = appRootUrl + "account/register?id=" + id;
	location.href = appRootUrl + path;
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

