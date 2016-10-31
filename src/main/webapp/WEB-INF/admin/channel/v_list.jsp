<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>栏目管理</title>
<%@ include file="../common.jsp"%>
<script type="text/javascript">
function getTableForm() {
	return document.getElementById('tableForm');
}
function optAdd() {
	if($('#siteId').val()==''||$('#siteId').val()==null){
		alert("请先选择站点!")
		return;
	}
	var f = getTableForm();
	f.action="v_add.do";
	$('#submitBtn').click();
}
</script>
<body>
	
		<h2>
			<strong style="color: grey;">
			<a href="#" rel='load-content'>${site.name}</a>
			<c:if test="${parent.parent!=null}"> > <a href="#" rel='load-content'>${parent.parent.name}</a></c:if>
			<c:if test="${parent.id!=null}"> > <a href="#" rel='load-content'>${parent.name}</a></c:if>
			</strong>
		</h2>
	    <div class="page_title">
			<h2 class="fl">
				栏目列表</h2>
			<a class="fr top_rt_btn" href="javascript:void(0)" onclick="optAdd()">添加栏目</a>
		</div>
		<form id="tableForm" action="v_list.do" method="post">
		<input type="submit" id="submitBtn" style="display: none;"/>
		<input type="hidden" name="pageNo" value="${pagination.pageNo}"/>
		<input type="hidden" id="pid" name="pid" value="${parent.id}"/>
		<input type="hidden" id="siteId" name="siteId" value="${site.id}"/>
		<table class="table">
			<tr>
				<th>栏目名称</th>
				<th>类型</th>
				<th>站点</th>
				<th>排序</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${pagination.datas}" var="item">
			<tr>
				<td>${item.name}</td>
				<td>${item.type}</td>
				<td>${item.site.name}</td>
				<td>${item.sort}</td>
				<td><a href="v_edit.do?id=${item.id}" class="inner_btn">修改</a> <a
					href="o_delete.do?ids=${item.id}&siteId=${site.id}&pid=${parent.id}" class="inner_btn" onclick="if(!confirm('确定要删除吗?')) {return false;}">删除</a></td>
			</tr>
			</c:forEach>
		</table>
		<%@include file="../common/page.jsp"%>
		</form>
	
</body>
</html>
