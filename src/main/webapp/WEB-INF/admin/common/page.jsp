<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="p" value="${pagination}"></c:set>
<c:if test="${p.totalCount gt 0}">
<div class="paging">
	<a onclick="gotoPage('${p.upPage}');">第一页</a>
	<c:forEach begin="1" end="${p.totalPage}" var="i">
		<c:if test="${(i <= 1)&&(p.pageNo > 5)}">
			<a onclick="gotoPage('1');">1</a>
		</c:if>
		<c:if test="${((p.pageNo-i) <= 4)&&((p.pageNo-i) >= -4)}">
			<a onclick="gotoPage('${i}');">${i}</a>
		</c:if>
		<c:if test="${(i >= p.totalPage)&&(((p.totalPage+1)-p.pageNo) > 5)}">
			<a onclick="gotoPage('${p.totalPage}');">${p.totalPage}</a>
		</c:if>
	</c:forEach>
	<!-- <a>1</a> <a>2</a> <a>3</a> <a>…</a> <a>1004</a>  -->
	<a onclick="gotoPage('${p.nextPage}');">最后一页</a>
</div>
</c:if>
<script type="text/javascript">
function gotoPage(pageNo){  
    $('#pageNo').val(pageNo);
	$('#submitBtn').click();
}  
</script>