<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>内容管理</title>
<%@ include file="../common.jsp"%>
<script type="text/javascript">
function getTableForm() {
	return document.getElementById('tableForm');
}
function optAdd() {
	if($('#siteId').val()==''){
		alert("请先选择站点!")
		return;
	}
	if('${channel.level}'>0||$('#channelId').val()==''){
		alert("请先子栏目!")
		return;
	}
	var f = getTableForm();
	f.action="v_add.do";
	$('#submitBtn').click();
}
</script>
</head>
<body>
	
		<h2>
			<strong style="color: grey;">
			<a href="#" rel='load-content'>${site.name}</a>
			<c:if test="${channel.parent!=null&&channel.parent.parent!=null}"> > <a href="#" rel='load-content'>${channel.parent.parent.name}</a></c:if>
			<c:if test="${channel.parent!=null}"> > <a href="#" rel='load-content'>${channel.parent.name}</a></c:if>
			<c:if test="${channel.id!=null}"> > <a href="#" rel='load-content'>${channel.name}</a></c:if>
			</strong>
		</h2>
		<div class="page_title">
			<h2 class="fl">内容列表</h2>
			<shuogesha:buttonURL  url="/content/v_add.do">
			<a class="fr top_rt_btn" href="javascript:void(0)" onclick="optAdd()">添加内容</a>
			</shuogesha:buttonURL>
		</div>
		<form id="tableForm" action="v_list.do" method="post">
		<input
		type="submit" id="submitBtn" style="display: none;"/>
		<input type="hidden" name="pageNo" value="${pagination.pageNo}"/>
		<input type="hidden" id="channelId" name="channelId" value="${channel.id}"/>
		<input type="hidden" id="siteId" name="siteId" value="${site.id}"/>
		<table class="table">
			<tr>
				<th>标题</th>
				<th>所属栏目</th>
				<th>所属站点</th>
				<th>是否审核</th>
				<th>发布时间</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${pagination.datas}" var="item">
			<tr>
				<td>${item.title}</td>
				<td>${item.channel.name}</td>
				<td>${item.site.name}</td>
				<td>
					<c:if test="${item.status=='1'}">审核通过</c:if>
					<c:if test="${item.status=='0'}">待审核</c:if>
					<c:if test="${item.status=='-1'}">审核不通过</c:if>
					<%-- -${item.audit} --%>
				</td>
				<td>${item.dateline}</td>
				<td>
				<c:if test="${fn:startsWith(item.district, district)}">
					  <c:if test="${!item.audit}">
					  	<shuogesha:buttonURL  url="/content/o_audit.do">
					  	<a href="o_audit.do?id=${item.id}&channelId=${channel.id}&siteId=${site.id}" onclick="if(!confirm('确定要提交审核吗?')) {return false;}" class="inner_btn">提交审核</a>
					  	</shuogesha:buttonURL>
					  </c:if>
					  <c:if test="${item.audit&&(item.status=='0')}">
					  	<shuogesha:buttonURL  url="/content/v_audit.do">
					  	<a href="v_audit.do?id=${item.id}" class="inner_btn">审核</a>
					  	</shuogesha:buttonURL>
					  </c:if>
					  <shuogesha:buttonURL  url="/content/v_edit.do">
					  <a href="v_edit.do?id=${item.id}" class="inner_btn">修改</a>
					  </shuogesha:buttonURL>
					  <shuogesha:buttonURL  url="/content/o_delete.do">
					  <a href="o_delete.do?ids=${item.id}&channelId=${channel.id}&siteId=${site.id}" class="inner_btn" onclick="if(!confirm('确定要删除吗?')) {return false;}">删除</a>
					  </shuogesha:buttonURL>
				  </c:if>
				</td>
				
			</tr>
			</c:forEach>
		</table>
		<%@include file="../common/page.jsp"%>
		</form>
	
</body>
</html>
