<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title></title>
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
			<h2 class="fl">列表</h2>
			<a class="fr top_rt_btn" href="v_add.do" rel='load-content'>添加</a>
		</div>
		<form id="tableForm" action="v_list.do" method="post">
		<input type="submit" id="submitBtn" style="display: none;"/>
		<table class="table">
			<tr>
				<th>ID</th>
				<th>name</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${pagination.datas}" var="item">
			<tr>
				<td style="width: 265px;">${item.id}</td>
				<td>${item.name}</td>
				<td><a href="v_edit.do?id=${item.id}" class="inner_btn">修改</a> <a
					href="o_delete.do?ids=${item.id}" class="inner_btn" onclick="if(!confirm('确定要删除吗?')) {return false;}">删除</a></td>
			</tr>
			</c:forEach>
		</table>
		<%@include file="../common/page.jsp"%>
		</form>
	
</body>
</html>
