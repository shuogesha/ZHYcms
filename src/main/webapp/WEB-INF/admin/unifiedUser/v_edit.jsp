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
});
</script>
</head>
<body>
	<section>
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
			<li><span class="item_name" style="width: 120px;">用户名：</span> <input
				type="text" class="textbox textbox_295" name="username"
				placeholder="用户名" value="${bean.username}" readonly="readonly"/></li>
			<li><span class="item_name" style="width: 120px;">密码：</span> <input
				type="text" class="textbox textbox_295" name="password"
				placeholder="密码" /></li>
			<li><span class="item_name" style="width: 120px;">电子邮箱：</span> <input
				type="text" class="textbox textbox_295" name="email"
				placeholder="文本信息提示..." value="${bean.email}" /></li>
			<li><span class="item_name" style="width: 120px;">手机号：</span> <input
				type="text" class="textbox textbox_295" name="phone"
				placeholder="手机号" value="${bean.phone}"  /></li>
			<li><span class="item_name" style="width: 120px;">真实姓名：</span> <input
				type="text" class="textbox textbox_295" name="realname"
				placeholder="真实姓名"value="${bean.realname}"  /></li>
			<li><span class="item_name" style="width: 120px;">会员类型：</span>
				<select name="group" required>
					<option value="0">普通用户</option>
					<option value="1">会员用户</option>
				</select>
			</li>
			<li><span class="item_name" style="width: 120px;"></span> <input
				type="submit" class="link_btn" value="保存" /></li>
		</ul>
		</form>
	</section>
</body>
</html>
