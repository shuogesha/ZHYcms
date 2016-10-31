<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>添加</title>
<%@ include file="../common.jsp"%>
<script> 
$(function(){
    $("#jvForm").validate();
});
</script>
</head>
<body>
	
		<h2>
			<strong style="color: grey;"></strong>
		</h2>
		<div class="page_title">
			<h2 class="fl">添加</h2>
			<a class="fr top_rt_btn" href="v_list.do" rel='load-content'>返回列表</a>
		</div>
		<form id="jvForm" action="o_save.do" method="post">
		<ul class="ulColumn2">
			<li><span class="item_name" style="width: 120px;">站点名称：</span> <input
				type="text" class="textbox textbox_295" name="name"
				placeholder="站点名称" required /></li>
			<li><span class="item_name" style="width: 120px;"></span> <input
				type="submit" class="link_btn" value="保存" /></li>
		</ul>
		</form>
	
</body>
</html>
