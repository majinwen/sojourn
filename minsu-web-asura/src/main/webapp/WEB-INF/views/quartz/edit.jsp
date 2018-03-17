<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<script type="text/javascript">
	/**
		表单回调函数
		方法：validateCallback内部采用ajax提交方式, 参考2443行
	*/
	function updateTriggerAjaxDone(json) {
		// 获得包含表单的div, 重新加载表单请求
		$('#search_triggerListsContainer').loadUrl('quartz/triggerList.do?resId=${resId }');
		// 提示信息
		alertMsgByJson(json);
		// 关闭对话框
		$.pdialog.closeCurrent();
	}
</script>
<div class="pageContent">
	<form method="post" action="quartz/edit.do" class="pageForm required-validate" onsubmit="return validateCallback(this, updateTriggerAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>触发器名称：</label>
				<input type="text" size="50" name="name" class="required" value="${triggerName }" readonly="readonly"/>
			</div>
			<div class="unit">
				<label>触发器组：</label>
				<input type="text" size="50" name="group" class="required" value="${triggerGroup }" readonly="readonly"/>
			</div>
			<div class="unit">
				<label>表达式：</label>
				<input type="text" size="50" name=cronExpression class="required" value="${cronExpression }"/>
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