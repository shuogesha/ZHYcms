<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>修改</title>
<%@ include file="../common.jsp"%>
<script> 
var perms = [<c:forEach items="${bean.perms}" var="i">"${i}",</c:forEach>];
function disablePerms() {
	if($("input[name=allPerms]:checked").val()=="true") {
		$(".perm-container input[type=checkbox]").attr("disabled","disabeld");
	} else {
		$(".perm-container input[type=checkbox]").removeAttr("disabled");
	}
}
$(function(){
    $("#jvForm").validate();
    $(".perm-container input[type=checkbox]").each(function() {
		var perm = $(this).val();
		var index = perm.indexOf(",");		
		if(index!=-1) {
			perm = perm.substring(0,index);
		}
		for(var i=0,len=perms.length;i<len;i++) {
			if(perms[i]==perm) {
				$(this).attr("checked",true);
				break;
			}
		}
	});
    $(":radio[name=allPerms][value=${bean.allPerms}]").attr("checked","true");
    $("input[name=allPerms]").bind("click",function(){
		disablePerms();
	});
}); 
</script>
</head>
<style type="text/css">
.perm-container{padding-left: 130px;}
.perm-layout-1{padding:5px 0;}
.perm-layout-2{padding:5px 0px 5px 30px;}
.perm-container label{color: #364f56;font-size: 12px;}
</style>
<body>
	
		<h2>
			<strong style="color: grey;"></strong>
		</h2>
		<div class="page_title">
			<h2 class="fl">修改</h2>
			<a class="fr top_rt_btn" href="v_list.do" rel='load-content'>返回列表</a>
		</div>
		<form id="jvForm" action="o_update.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${bean.id}">
		<ul class="ulColumn2">
			<li><span class="item_name" style="width: 120px;">角色名：</span> <input
				type="text" class="textbox textbox_295" name="name"
				placeholder="name" value="${bean.name}" required/></li>
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
