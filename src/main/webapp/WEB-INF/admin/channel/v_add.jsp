<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>添加</title>
<%@ include file="../common.jsp"%>
<script type="text/javascript" charset="utf-8" src="/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#jvForm").validate();
	var oEditor = CKEDITOR.replace('container');
	oEditor.on("blur",function(){
		$("#container").val(oEditor.getData());
	});
});
</script>
</head>
<body>
	
		<h2>
			<strong style="color: grey;"></strong>
		</h2>
		<div class="page_title">
			<h2 class="fl">添加</h2>
			<a class="fr top_rt_btn" href="v_list.do?siteId=${site.id}&pid=${parent.id}" rel='load-content'>返回列表</a>
		</div>
		<form id="jvForm" action="o_save.do" method="post">
		<input type="hidden" name="pid" value="${parent.id}">
		<input type="hidden" name="siteId" value="${site.id}">
		<ul class="ulColumn2">
			<li><span class="item_name" style="width: 120px;">栏目名称：</span> <input
				type="text" class="textbox textbox_295" name="name"
				placeholder="name" required /></li>
			<li><span class="item_name" style="width: 120px;">上级目录：</span> <input
			type="text" class="textbox textbox_295"
			placeholder="上级目录" value="${parent.name}" readonly="readonly"/></li>
			<li>
			<span class="item_name" style="width: 120px;">栏目类型：</span> 
				<select id="type" class="textbox" name="type">
					<option value="content">内容</option>
					<option value="alone">单页</option>
				</select>
			</li>
			<li><span class="item_name" style="width: 120px;">排序号：</span> <input
			type="text" class="textbox textbox_295" name="sort"  value="0" 
			placeholder="如sort" /></li>
			<li><span class="item_name" style="width: 120px;">内容：</span></li>
			<li>
				<textarea id="container" class="textbox textbox_295" name="content" >
				</textarea>
			</li>
			<li><span class="item_name" style="width: 120px;"></span> <input
				type="submit" class="link_btn" value="保存" /></li>
		</ul>
		</form>
	
</body>
</html>
