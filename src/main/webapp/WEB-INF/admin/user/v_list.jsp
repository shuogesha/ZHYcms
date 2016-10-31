<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户管理</title>
<%@ include file="../common.jsp"%>
<script type="text/javascript">
function getTableForm() {
	return document.getElementById('tableForm');
}
</script>
</head>
<body>
	
		<h2>
			<strong style="color: grey;"></strong>
		</h2>
		<div class="page_title">
			<h2 class="fl">管理员列表</h2>
			<a class="fr top_rt_btn" href="v_add.do" rel='load-content'>添加管理员</a>
		</div>
		<form id="tableForm" action="v_list.do" method="post">
		<input type="submit" id="submitBtn" style="display: none;"/>
		<input type="hidden" name="pageNo" value="${pagination.pageNo}"/>
		<table class="table">
			<tr>
				<th>用户名</th>
				<th>所属站点</th>
				<th>手机号</th>
				<th>邮箱</th>
				<th>是否禁用</th>
				<th>最后登录Ip</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${pagination.datas}" var="item">
			<tr>
				<td style="width: 265px;">${item.username}</td>
				<td>${item.site.name}</td>
				<td>${item.phone}</td>
				<td>${item.email}</td>
				<td>${item.unifiedUser.disabled}</td>
				<td>${item.unifiedUser.lastLoginIp}</td>
				<td><a href="v_edit.do?id=${item.id}" class="inner_btn">修改</a> <a
					href="o_delete.do?ids=${item.id}" class="inner_btn" onclick="if(!confirm('确定要删除吗?')) {return false;}">删除</a></td>
			</tr>
			</c:forEach>
		</table>
		<%@include file="../common/page.jsp"%>
		</form>
	
	<iframe style="width:0; height:0; margin-top:-10px;" name="submitFrame" src="about:blank"></iframe>
</body>
</html>
