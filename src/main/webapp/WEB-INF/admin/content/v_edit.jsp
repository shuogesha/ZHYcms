<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>修改</title>
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
			<h2 class="fl">修改</h2>
			<a class="fr top_rt_btn" href="v_list.do?siteId=${bean.site.id}&channelId=${bean.channel.id}" rel='load-content'>返回列表</a>
		</div>
		<form id="jvForm" action="o_update.do" method="post">
		<input type="hidden" name="id" value="${bean.id}">
		<input type="hidden" name="district" value="${bean.district}">
		<input type="hidden" name="status" value="0">
		<input type="hidden" name="audit" value="false">
		<input type="hidden" name="channelId" value="${bean.channel.id}">
		<input type="hidden" name="siteId" value="${bean.site.id}">
		<ul class="ulColumn2">
			<li><span class="item_name" style="width: 120px;">所属栏目：</span> <input
				type="text" class="textbox textbox_295"
				placeholder="所属栏目" value="${bean.channel.name}" readonly="readonly"/></li>
			<li><span class="item_name" style="width: 120px;">标题：</span> <input
				type="text" class="textbox textbox_295"
				placeholder="title" name="title" value="${bean.title}" required/></li>
			<li><span class="item_name" style="width: 120px;">内容：</span></li>
			<li>
				<textarea id="container" class="textbox textbox_295" name="content" >
				${bean.content}
				</textarea>
			</li>
			<li><span class="item_name" style="width: 120px;">来源：</span> <input
				type="text" class="textbox textbox_295" name="source"
				placeholder="来源" value="${bean.source}"/></li>
			<li><span class="item_name" style="width: 120px;"></span> <input
				type="submit" class="link_btn" value="保存" /></li>
		</ul>
		</form>
	
</body>
</html>
