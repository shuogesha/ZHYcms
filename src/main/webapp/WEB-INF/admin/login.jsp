<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>后台登录</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<style>
body {
	height: 100%;
	background: #16a085;
	overflow: hidden;
}

canvas {
	z-index: -1;
	position: absolute;
}
</style>
<script src="/js/jquery.js"></script>
<script src="/js/verificationNumbers.js"></script>
<script src="/js/Particleground.js"></script>
<script>
	$(document).ready(function() {
		//粒子背景特效
		$('body').particleground({
			dotColor : '#5cbdaa',
			lineColor : '#5cbdaa'
		});
		$(".submit_btn").click(function() {
			 
		});
	});
</script>
</head>
<body>
	<form id="jvForm" action="login.do" method="post">
	<c:if test="returnUrl??">
	<input type="hidden" name="returnUrl" value="${returnUrl}"/>
	</c:if>
	<c:if test="processUrl??">
	<input type="hidden" name="processUrl" value="${processUrl}"/>
	</c:if>
	<dl class="admin_login">
		<dt>
			<strong>后台管理系统</strong> <em>By Shuogesha</em>
		</dt>
		<dd class="user_icon">
			<input type="text" name="username" placeholder="账号" class="login_txtbx" />
		</dd>
		<dd class="pwd_icon">
			<input type="password" name="password"  placeholder="密码" class="login_txtbx" />
		</dd>
		<!-- <dd class="val_icon">
			<div class="checkcode">
				<input type="text" name="captcha" placeholder="验证码" maxlength="4"
					class="login_txtbx">
				<canvas class="J_codeimg" id="myCanvas" onclick="createCode()">对不起，您的浏览器不支持canvas，请下载最新版浏览器!</canvas>
			</div>
			<input type="button" value="验证码核验" class="ver_btn"
				onClick="validate();">
		</dd> -->
		<dd>
			<input type="submit" value="立即登陆" class="submit_btn" />
		</dd>
		<dd>
			<p>© 2015-2016 shuogesha.com 版权所有</p>
			<p>成都说个啥科技有限公司技术支持</p>
		</dd>
	</dl>
	</form>
</body>
</html>
