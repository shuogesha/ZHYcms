<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>添加</title>
<%@ include file="../common.jsp"%>
<script> 
function disablePerms() {
	if($("input[name=allPerms]:checked").val()=="true") {
		$(".perm-container input[type=checkbox]").attr("disabled","disabeld");
	} else {
		$(".perm-container input[type=checkbox]").removeAttr("disabled");
	}
}
$(function(){
    $("#jvForm").validate();
    $(":radio[name=allPerms][value=false]").attr("checked","true");
    $("input[name=allPerms]").bind("click",function(){
		disablePerms();
	});
});
</script>
<style type="text/css">
.perm-container{padding-left: 130px;}
.perm-layout-1{padding:5px 0;}
.perm-layout-2{padding:5px 0px 5px 30px;}
.perm-container label{color: #364f56;font-size: 12px;}
</style>
</head>
<body>
	
		<h2>
			<strong style="color: grey;"></strong>
		</h2>
		<div class="page_title">
			<h2 class="fl">添加</h2>
			<a class="fr top_rt_btn" href="v_list.do" rel='load-content'>返回列表</a>
		</div>
		<form id="jvForm" action="o_save.do" method="post" enctype="multipart/form-data">
		<ul class="ulColumn2">
			<li><span class="item_name" style="width: 120px;">角色名：</span> <input
				type="text" class="textbox textbox_295" name="name"
				placeholder="name" required /></li>
			<li>
				<span class="item_name" style="width: 120px;">所有权限：</span>
				<label>
				<input type="radio" name="allPerms" value="true">
				是
				</label>
				<label>
				<input type="radio" name="allPerms" value="false">
				否
				</label>
			</li>
			<li>
				<span class="item_name" style="width: 120px;">权限：</span>
				<%@include file="./perms.jsp"%>	
			</li>
			<li><span class="item_name" style="width: 120px;"></span> <input
				type="submit" class="link_btn" value="保存" /></li>
		</ul>
		</form>
	
</body>
</html>
