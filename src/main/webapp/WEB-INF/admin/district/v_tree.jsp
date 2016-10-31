<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title></title>
<%@ include file="../common.jsp"%>
 <link rel="stylesheet" href="/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/js/jquery.ztree.core.js"></script>
<style type="text/css">
html{overflow-y:hidden;}
</style>
<SCRIPT type="text/javascript">
		<!--
		var pcode= '51';
		var level= 2;
		var setting = {
			rootPID : pcode, 
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
				url:"getNodeList.do",
				autoParam:["id", "name=n", "level=lv"],
				otherParam:{"pcode":pcode},
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
			document.getElementById('contentFrame').src="v_list.do?pcode="+id+"&level="+(treeNode.level+2);
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
			document.getElementById('contentFrame').src="v_list.do?pcode="+pcode+"&level="+level;
			$("#refreshNode").bind("click", {type:"refresh", silent:false}, refreshNode);
		});
		//-->
	</SCRIPT>
</head>
<body>
	<div style="width: 200px;float: left;height: 100%;overflow:scroll;">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div style="margin-left: 200px;min-height: 700px;">
		<iframe id="contentFrame" width="100%" height="100%" style="border: 0px;"></iframe>
	</div>
</body>
</html>
