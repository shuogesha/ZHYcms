<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>修改</title>
<%@ include file="../common.jsp"%>
<script> 
$(function(){
    $("#jvForm").validate();
	var districtIds = [<c:forEach items="${bean.districts}" var="i">"${i.id}",</c:forEach>];
    $("#jvForm").validate();
    $("input[name=districtIds]").each(function() {
		var districtId = $(this).val();
		for(var i=0,len=districtIds.length;i<len;i++) {
			if(districtIds[i]==districtId) {
				$(this).attr("checked",true);
				break;
			}
		}
	});
});
</script>
</script>
</head>
<body>
	
		<h2>
			<strong style="color: grey;"></strong>
		</h2>
		<div class="page_title">
			<h2 class="fl">修改</h2>
			<a class="fr top_rt_btn" href="v_list.do" rel='load-content'>返回列表</a>
		</div>
		<form id="jvForm" action="o_update.do" method="post">
		<input type="hidden" name="id" value="${bean.id}">
		<ul class="ulColumn2">
			<li><span class="item_name" style="width: 120px;">站点名称：</span> <input
				type="text" class="textbox textbox_295" name="name"
				placeholder="站点名称" value="${bean.name}" required/></li>
			<li><span class="item_name" style="width: 120px;">选择开通城市：</span> 
				<c:forEach items="${districtList}" var="district">
					<label style="padding: 5px;line-height: 38px;"><input value="${district.id}" type="checkbox" name="districtIds"/>${district.name}</label>
				</c:forEach>
			
			</li>
			<li><span class="item_name" style="width: 120px;"></span> <input
				type="submit" class="link_btn" value="保存" /></li>
		</ul>
		</form>
	
</body>
</html>
