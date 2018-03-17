<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<script type="text/javascript">
	/**
		表单回调函数
		方法：validateCallback内部采用ajax提交方式, 参考2443行
	*/
	function addRoleAjaxDone(json) {
		// 获得包含表单的div, 重新加载表单请求
		$('#search_roleListsContainer').loadUrl('authority/role/roleList.do?resId=${resId }');
		// 提示信息
		alertMsgByJson(json);
		// 关闭对话框
		$.pdialog.closeCurrent();
	}
</script>
<div class="pageContent">
	<form method="post" action="authority/role/add.do" class="pageForm required-validate" onsubmit="return validateCallback(this, addRoleAjaxDone)">
		<div class="pageFormContent" layoutH="58">

			<div class="unit">
				<label>角色名称：</label>
				<input type="text" name="roleName" class="required"/>
			</div>
			<div class="unit">
				<label>描述：</label>
				<textarea name="roleDesc" rows="3" cols="27"></textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>