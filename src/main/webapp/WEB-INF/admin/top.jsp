<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>后台管理系统</title>
<%@ include file="./common.jsp"%>
<style type="text/css">
body{margin:0;}
</style>
<script>
   function HoverLi(id){
		$("li[id^='tb_']").each(function(){
			$(this).removeClass("cur");
		});
		$("#tb_"+id).addClass("cur");
	}
   $(function(){
  	 $("#sites").val("${site.tplSolution}");
   }); 
</script> 
</head>
<body>
	<!--header-->
	<div class="header">
		<div class="head">
			<h1>
				<img src="/images/admin_logo.png" />
			</h1>
			<ul class="rt_nav">
				<li>
					<form action="index.do" target="_top" method="get">
						<select id="sites" class="website_icon" name="site_param" onchange="this.form.submit();">
							<option value="${user.site.tplSolution}">${user.site.name}</option>
							<c:forEach items="${user.sites}" var="i">
								<option value="${i.tplSolution}">${i.name}</option>
							</c:forEach>
						</select>
					</form>
				</li>
				<li><a href="/" target="_blank" class="website_icon">站点首页</a></li>
				<!-- <li><a href="#" class="admin_icon">DeathGhost</a></li> -->
				<!-- <li><a href="#" class="set_icon">账号设置</a></li> -->
				<li><a href="logout.do" target="_top" onclick="return confirm('确定要退出吗?');" class="quit_icon">安全退出</a></li>
			</ul>
		</div>
		<div style="clear: both;"></div>
		 <div id="Top">
		  <div class="toolbar1">
		    <div class="centreBox">
		      <div class="user">欢迎你,${user.username}</div>
		      <div class="menu">
		        <ul class="list">
		          <shuogesha:buttonURL  url="/work_main.do">
		          <li class="cur" id="tb_11" onclick="HoverLi(11);"><a target="mainFrame" href="work_main.do?source=top">我的工作</a></li>
		          </shuogesha:buttonURL>
		          <shuogesha:buttonURL  url="/channel_main.do">
		          <li id="tb_21" onclick="HoverLi(21);"><a target="mainFrame" href="channel_main.do?source=top">栏目管理</a></li>
		          </shuogesha:buttonURL>
		          <shuogesha:buttonURL  url="/content_main.do">
		          <li id="tb_31" onclick="HoverLi(31);"><a target="mainFrame" href="content_main.do?source=top">内容管理</a></li>
		          </shuogesha:buttonURL>
		          <shuogesha:buttonURL  url="/user_main.do">
		          <li id="tb_51" onclick="HoverLi(51);"><a target="mainFrame" href="user_main.do?source=top">用户</a></li>
		          </shuogesha:buttonURL>
		          <shuogesha:buttonURL  url="/config_main.do">
		          <li id="tb_61" onclick="HoverLi(61);"><a target="mainFrame" href="config_main.do?source=top">系统设置</a></li>
		          </shuogesha:buttonURL>
		        </ul>
		      </div>
		    </div>
		  </div>
		  <!-- <div class="toolbar2">
		    <div class="centreBox">
		      <div class="menu">
		        <ul>
		          <li class="cur"><a href="#" target="_blank">企业微博0</a></li>
		          <li><a href="#" target="_blank">企业微博0</a></li>
		          <li><a href="#" target="_blank">企业微博0</a></li>
		          <li><a href="#" target="_blank">企业微博0</a></li>
		          <li><a href="#" target="_blank">企业微博0</a></li>
		          <li><a href="#" target="_blank">企业微博0</a></li>
		          <li><a href="#" target="_blank">企业微博0</a></li>
		          <li><a href="#" target="_blank">企业微博0</a></li>
		          <li><a href="#" target="_blank">企业微博0</a></li>
		          <li><a href="#" target="_blank">企业微博0</a></li>
		          <li><a href="#" target="_blank">企业微博0</a></li>
		          <li><a href="#" target="_blank">企业微博0</a></li> 
		        </ul>
		      </div>
		    </div>
		  </div>-->
		</div>
	</div>
</body>
</html>

