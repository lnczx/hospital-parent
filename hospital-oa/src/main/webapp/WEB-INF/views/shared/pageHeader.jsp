<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--header start-->
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="index.html">继续教育项目管理平台</a> <a class="logo navbar-logo-m f-l mr-10 visible-xs" href="index.html">H-ui</a> <span class="logo navbar-slogan f-l mr-10 hidden-xs"></span> <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
			<nav class="nav navbar-nav">
				<ul class="cl">
					<li class="dropDown dropDown_hover"><a href="javascript:;" class="dropDown_A"><i class="Hui-iconfont">&#xe600;</i> 常用功能 <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="http://jjxmglxt.pumc.edu.cn/app/login.do" target="_blank"><i class="Hui-iconfont">&#xe616;</i> 进入老版系统</a></li>
							<li><a href="javascript:;" onclick="btn_add_blank('home/help')"><i class="Hui-iconfont">&#xe613;</i> 使用帮助中心</a></li>
						</ul>
					</li>
				</ul>
			</nav>
			<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
				<ul class="cl">
					<li>${accountAuth.name}</li>
					<li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A">${accountAuth.username} <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							
							<li><a href="#" onclick="btn_show_pop('个人信息', 'home/myForm', '600','500')" >个人信息</a></li>
							<li><a href="/hospital-oa/account/logout">退出</a></li>
						</ul>
					</li>
					<!-- 消息  留着备用
					<li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
					-->
					<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" data-val="default" title="默认（蓝色）">默认（蓝色）</a></li>
							<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
							<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
							<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
							<li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
							<li><a href="javascript:;" data-val="black" title="黑色">黑色</a></li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>
	</div>
</header>
