<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>站点设置</title>
<%@ include file="../common.jsp"%>
<script> 
$(function(){
    $("#jvForm").validate();
});
</script>
</head>
<body>
	
      <h2><strong style="color:grey;"></strong></h2>
      <form id="jvForm" action="o_config_update.do" method="post" target="submitFrame">
      <input type="hidden" name="id" value="${bean.id}">
      <ul class="ulColumn2">
       <li>
        <span class="item_name" style="width:120px;">站点名称：</span>
        <input type="text" name="name" value="${bean.name}" class="textbox textbox_295" placeholder="" required/>
       
       </li>
       <li>
        <span class="item_name" style="width:120px;">状态：</span>
        <select class="select" name="status">
         <option value="0">开放</option>
         <option value="1">关闭</option>
        </select>
       
       </li>
        <!--  <li>
        <span class="item_name" style="width:120px;">图片上传路径：</span>
        <input type="text" name="imageUrl" value="${bean.imageUrl}" class="textbox textbox_295" placeholder=""/>
       	</li>
      	<li>
        <span class="item_name" style="width:120px;">站点路径：</span>
        <input type="text" name="tplSolution" value="${bean.tplSolution}" class="textbox textbox_295" placeholder=""/>
       </li> -->
       <li>
        <span class="item_name" style="width:120px;">网站描述：</span>
        <textarea placeholder="网站描述" class="textarea" name="description" style="width:500px;height:100px;">${bean.description}</textarea>
       </li>
       <!-- <li>
        <span class="item_name" style="width:120px;">上传Logo：</span>
        <label class="uploadImg">
         <input type="file"/>
         <span>上传图片</span>
        </label>
       </li> -->
       <li>
        <span class="item_name" style="width:120px;"></span>
        <input type="submit" class="link_btn" value="保存"/>
       </li>
      </ul>
      </form>
     	 
     <iframe style="width:0; height:0; margin-top:-10px;" name="submitFrame" src="about:blank"></iframe>
</body>
</html>
