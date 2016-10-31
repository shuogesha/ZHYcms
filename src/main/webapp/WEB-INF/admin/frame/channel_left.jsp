<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>后台管理系统</title>
<%@ include file="../common.jsp"%>
<link rel="stylesheet" href="/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/js/jquery.ztree.core.js"></script>
<style type="text/css">
html{overflow-y:hidden;}
</style>
<SCRIPT type="text/javascript">
		<!--
		var siteId= '${site.id}';
		var setting = {
			rootPID : 0, 
			view: {
				selectedMulti: false
			},
			callback: {
				beforeClick: beforeClick,
				beforeAsync: beforeAsync,
				onClick: onClick
			},
			async: {
				enable: true,
				url:"channel/getNodeList.do",
				autoParam:["id", "name=n", "level=lv"],
				otherParam:{"siteId":siteId},
				dataFilter: filter
			}
		};
		
		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
		

		var log, className = "dark";
		function beforeClick(treeId, treeNode, clickFlag) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeClick ]&nbsp;&nbsp;" + treeNode.name );
			return (treeNode.click != false);
		}
		function beforeAsync(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeAsync ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
			return true;
		}
		function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
			showLog("[ "+getTime()+" onAsyncError ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
		}
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			showLog("[ "+getTime()+" onAsyncSuccess ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
		}
		function onClick(event, treeId, treeNode, clickFlag) {
			//alert("[ "+getTime()+" onClick ]&nbsp;&nbsp;clickFlag = " + clickFlag + " (" + (clickFlag===1 ? "普通选中": (clickFlag===0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")");
			var id=treeNode.id;
			if(treeNode.id=="0"){
				id="";
			}
			parent.rightFrame.location.href="channel/v_list.do?pid="+id+"&siteId="+treeNode.siteId;
		}		
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds();
			return (h+":"+m+":"+s);
		}
		
		function refreshNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			type = e.data.type,
			silent = e.data.silent,
			nodes = zTree.getSelectedNodes();
			if (nodes.length == 0) {
				alert("请先选择一个父节点");
			}
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.reAsyncChildNodes(nodes[i], type, silent);
				if (!silent) zTree.selectNode(nodes[i]);
			}
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting);
			parent.rightFrame.location.href="channel/v_list.do?siteId="+siteId;
			$("#refreshNode").bind("click", {type:"refresh", silent:false}, refreshNode);
		});
		//-->
	</SCRIPT>
</head>
<body>
	<!--aside nav-->
	<div class="lt_aside_nav content mCustomScrollbar">
		<ul style="margin-bottom:0px;">
			<li>
				<dl>
					<dt>${site.name}</dt>
				</dl>
			</li>
		</ul>
		<shuogesha:buttonURL  url="/channel/v_list.do">
			<!-- <a href="channel/v_list.do" target="rightFrame">栏目管理</a> -->
		<ul id="treeDemo" class="ztree"></ul>
		</shuogesha:buttonURL>
	</div>
</body>
</html>

