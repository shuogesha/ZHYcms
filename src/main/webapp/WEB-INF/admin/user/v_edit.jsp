<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>管理员修改</title>
<%@ include file="../common.jsp"%>
<script> 
var roleIds = [<c:forEach items="${bean.roles}" var="i">"${i.id}",</c:forEach>];
var siteIds = [<c:forEach items="${bean.sites}" var="i">"${i.id}",</c:forEach>];
$(function(){
    $("#jvForm").validate();
    $("input[name=roleIds]").each(function() {
		var roleId = $(this).val();
		for(var i=0,len=roleIds.length;i<len;i++) {
			if(roleIds[i]==roleId) {
				$(this).attr("checked",true);
				break;
			}
		}
	});
    $("input[name=siteIds]").each(function() {
		var siteId = $(this).val();
		for(var i=0,len=siteIds.length;i<len;i++) {
			if(siteIds[i]==siteId) {
				$(this).attr("checked",true);
				break;
			}
		}
	});
});
</script>
</head>
<body>
	
		<h2>
			<strong style="color: grey;"></strong>
		</h2>
		<div class="page_title">
			<h2 class="fl">修改管理员</h2>
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
			<li><span class="item_name" style="width: 120px;">角色：</span>
				<c:forEach items="${roleList}" var="role">
					<label><input value="${role.id}" type="checkbox" name="roleIds"/>${role.name}</label>
				</c:forEach>
			</li>	
			<li><span class="item_name" style="width: 120px;">站点：</span>
				<input type="text" readonly="readonly" value="${bean.site.name}">
			</li>
			<li><span class="item_name" style="width: 120px;">管理其它站点：</span>
				<c:forEach items="${siteList}" var="site">
					<c:if test="${site.id!=bean.site.id}">
						<label><input value="${site.id}" type="checkbox" name="siteIds"/>${site.name}</label>
					</c:if>
				</c:forEach>
			</li>
			<%-- <li><span class="item_name" style="width: 120px;">内容发布等级：</span> <input
				type="text" class="textbox textbox_295" name="step"
				placeholder="默认为0"  value="${bean.step}"/></li> --%>
			<li><span class="item_name" style="width: 120px;"></span> <input
				type="submit" class="link_btn" value="保存" /></li>
		</ul>
		</form>
	
</body>
</html>
