<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>修改密码</title>
<%@ include file="../common.jsp"%>
<script> 
$(function(){
    $("#jvForm").validate();
});
</script>
</head>
<body>
	
      <h2><strong style="color:grey;"></strong></h2>
      <form id="jvForm" action="o_chpwd.do" method="post" target="submitFrame">
      <ul class="ulColumn2">
       <li>
        <span class="item_name" style="width:120px;">旧密码：</span>
        <input type="password" name="name" value="" class="textbox textbox_295" placeholder="" required/>
       </li>
       <li>
        <span class="item_name" style="width:120px;">新密码：</span>
        <input type="password" name="name" value="" class="textbox textbox_295" placeholder="" required/>
       </li>
       <li>
        <span class="item_name" style="width:120px;"></span>
        <input type="submit" class="link_btn" value="功能暂未开放"/>
       </li>
      </ul>
      </form>
     	 
     <iframe style="width:0; height:0; margin-top:-10px;" name="submitFrame" src="about:blank"></iframe>
</body>
</html>
