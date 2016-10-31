<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="perm-container">
<input type="hidden" name="perms" value="/index.do,/top.do"/>

<div class="perm-layout-1">
<label><input value="/work_main.do,/work_left.do,/work_right.do" type="checkbox" name="perms"/>我的工作</label>
	<div class="perm-layout-2">
		<label><input value="/user/v_chpwd.do" type="checkbox" name="perms"/>修改密码</label>
	</div>
</div>

<div class="perm-layout-1">
<label><input value="/channel_main.do,/channel_left.do,/channel_right.do" type="checkbox" name="perms"/>栏目</label>
	<div class="perm-layout-2">
		<label><input value="/channel/v_list.do" type="checkbox" name="perms"/>栏目管理</label>
	</div>
</div>

<div class="perm-layout-1">
<label><input value="/content_main.do,/content_left.do,/content_right.do" type="checkbox" name="perms"/>内容</label>
	<div class="perm-layout-2">
		<label><input value="/content/v_list.do" type="checkbox" name="perms"/>内容管理</label>
		<label><input value="/content/v_add.do" type="checkbox" name="perms"/>添加</label>
		<label><input value="/content/v_edit.do" type="checkbox" name="perms"/>修改</label>
		<label><input value="/content/o_delete.do" type="checkbox" name="perms"/>删除</label>
		<label><input value="/content/o_audit.do" type="checkbox" name="perms"/>提交审核</label>
		<label><input value="/content/v_audit.do" type="checkbox" name="perms"/>审核</label>
	</div>
</div>

<div class="perm-layout-1">
<label><input value="/user_main.do,/user_left.do,/user_right.do" type="checkbox" name="perms"/>用户</label>
	<div class="perm-layout-2">
		<label><input value="/member/v_list.do" type="checkbox" name="perms"/>会员管理</label>
	</div>
	<div class="perm-layout-2">
		<label><input value="/user/v_list.do" type="checkbox" name="perms"/>管理员</label>
	</div>
	<div class="perm-layout-2">
		<label><input value="/role/v_list.do" type="checkbox" name="perms"/>角色管理</label>
	</div>
</div>

<div class="perm-layout-1">
<label><input value="/config_main.do,/config_left.do,/config_right.do" type="checkbox" name="perms"/>配置</label>
	<div class="perm-layout-2">
		<label><input value="/site/v_config.do" type="checkbox" name="perms"/>本站设置</label>
	</div>
	<div class="perm-layout-2">
		<label><input value="/district/v_list.do" type="checkbox" name="perms"/>行政区划</label>
	</div>
	<div class="perm-layout-2">
		<label><input value="/app/v_list.do" type="checkbox" name="perms"/>应用管理</label>
	</div>
</div>

</div>
<script type="text/javascript">
$(function() {
	$(".perm-container input[type=checkbox]").bind("click",function() {
		parentCheck(this);
		childCheck(this);
	});
});
function parentCheck(chk) {
	var obj = $(chk).parent().parent().parent();
	while(obj && obj.attr("class").indexOf("perm-container")==-1) {
		if(chk.checked) {
			$(obj.children()[0]).children().attr("checked",true);
		}
		obj = obj.parent();
	}
}
function childCheck(chk) {
	$(chk).parent().next().children().find("input[type=checkbox]").each(function(){this.checked=chk.checked});
}
</script>
