<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>管理员添加</title>
<%@ include file="../common.jsp"%>
<script> 
$(function(){
    $("#jvForm").validate({
		rules: { 
			username: {
				required: true,
				minlength: 2,
				remote: {
				    url: "v_check_username.do",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				        username: function() {
				            return $("#username").val();
				        }
				    },
                    dataType: "html",
                    dataFilter: function(data, type) {
                        if (data == "true")
                            return true;
                        else
                            return false;
                    }
				}
			}
		},
		messages: { 
			username: {
				remote: "用户名已经存在",
				required: "请输入用户名",
				minlength: "格式错误"
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
			<h2 class="fl">添加管理员</h2>
			<a class="fr top_rt_btn" href="v_list.do" rel='load-content'>返回列表</a>
		</div>
		<form id="jvForm" action="o_save.do" method="post">
		<input type="hidden" name="admin" value="true">
		<ul class="ulColumn2">
			<li><span class="item_name" style="width: 120px;">用户名：</span> <input
				type="text" class="textbox textbox_295" name="username" id="username"
				placeholder="用户名" required /></li>
			<li><span class="item_name" style="width: 120px;">密码：</span> <input
				type="text" class="textbox textbox_295" name="password"
				placeholder="密码" required /></li>
			<li><span class="item_name" style="width: 120px;">电子邮箱：</span> <input
				type="text" class="textbox textbox_295" name="email"
				placeholder="email" /></li>
			<li><span class="item_name" style="width: 120px;">手机号：</span> <input
				type="text" class="textbox textbox_295" name="phone"
				placeholder="手机号" /></li>
			<li><span class="item_name" style="width: 120px;">真实姓名：</span> <input
				type="text" class="textbox textbox_295" name="realname"
				placeholder="真实姓名" /></li>
			<!-- <li><span class="item_name" style="width: 120px;">受限管理员：</span>
				<label>
				<input type="radio" name="selfAdmin" value="true">
				是
				</label>
				<label>
				<input type="radio" name="selfAdmin" checked="checked" value="false">
				否
				</label>
				<span class="pn-fhelp">只能管理自己的数据</span>
			
			</li> -->
			<li><span class="item_name" style="width: 120px;">角色：</span>
				<c:forEach items="${roleList}" var="role">
					<label><input value="${role.id}" type="checkbox" name="roleIds"/>${role.name}</label>
				</c:forEach>
			</li>
			<li><span class="item_name" style="width: 120px;">站点：</span>
				<select name="siteId" required>
					<c:forEach items="${siteList}" var="site">
						<option value="${site.id}">${site.name }</option>
					</c:forEach>
				</select>
			</li>
			<!-- <li><span class="item_name" style="width: 120px;">内容发布等级：</span> <input
				type="text" class="textbox textbox_295" name="step"
				placeholder="默认为0"  value="0"/></li> -->
			<li><span class="item_name" style="width: 120px;"></span> <input
				type="submit" class="link_btn" value="保存" /></li>
		</ul>
		</form>
	
</body>
</html>
