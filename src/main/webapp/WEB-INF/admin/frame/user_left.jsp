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
					<dt>功能菜单</dt>
					<shuogesha:buttonURL  url="/fupin/v_list.do">
					<dd>
						<a href="unifiedUser/v_list.do" target="rightFrame">会员管理</a>
					</dd>
					</shuogesha:buttonURL>
					<dd>
						<a href="user/v_list.do" target="rightFrame">管理员</a>
					</dd>
					<shuogesha:buttonURL  url="/role/v_list.do">
					<dd>
						<a href="role/v_list.do" target="rightFrame">角色管理</a>
					</dd>
					</shuogesha:buttonURL>
				</dl>
			</li>
		</ul>
	</div>
</body>
</html>

