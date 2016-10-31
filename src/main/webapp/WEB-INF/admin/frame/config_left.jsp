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
					<dt>系统配置</dt>
					<shuogesha:buttonURL  url="/site/v_config.do">
					<dd>
						<a href="site/v_config.do" target="rightFrame">本站设置</a>
					</dd>
					</shuogesha:buttonURL>
					<shuogesha:buttonURL  url="/district/v_list.do">
					<dd>
						<a href="district/v_tree.do" target="rightFrame">行政区划</a>
					</dd>
					</shuogesha:buttonURL>
					<shuogesha:buttonURL  url="/app/v_list.do">
					<dd>
						<a href="app/v_list.do" target="rightFrame">应用管理</a>
					</dd>
					</shuogesha:buttonURL>
				</dl>
			</li>
		</ul>
	</div>
</body>
</html>

