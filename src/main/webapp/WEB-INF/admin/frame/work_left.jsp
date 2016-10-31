<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>后台管理系统</title>
<%@ include file="../common.jsp"%>
<style type="text/css">
html{overflow-y:hidden;}
</style>
</head>
<body>
	<!--aside nav-->
	<div class="lt_aside_nav content mCustomScrollbar">
		<ul>
			<li>
				<dl>
					<dt>常用操作</dt>
					<shuogesha:buttonURL  url="/user/v_chpwd.do">
					<dd>
						<a href="user/v_chpwd.do" target="rightFrame">修改密码</a>
					</dd>
					</shuogesha:buttonURL>
				</dl>
			</li>
		</ul>
	</div>
</body>
</html>

