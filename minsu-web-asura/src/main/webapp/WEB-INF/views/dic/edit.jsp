<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<script type="text/javascript">
	/**
		表单回调函数
		方法：validateCallback内部采用ajax提交方式, 参考2443行
	*/
	function updateDicAjaxDone(json) {
		// 获得包含表单的div, 重新加载表单请求
		$('#search_dicListsContainer').loadUrl('dic/dicList.do?resId=${resId }');
		// 提示信息
		alertMsgByJson(json);
		// 关闭对话框
		$.pdialog.closeCurrent();
	}
</script>
<div class="pageContent">
	<form method="post" action="dic/edit.do" class="pageForm required-validate" onsubmit="return validateCallback(this, updateDicAjaxDone)">
		<div class="pageFormContent" layoutH="58">
		    <input type="hidden" name="id" value="${dicEntity.id }"/>
		    
			<div class="unit">
				<label>库名：</label>
				<input type="text" size="50" name="dbName" class="required" value="${dicEntity.dbName }" readonly="readonly"/>
			</div>
			<div class="unit">
				<label>表名：</label>
				<input type="text" size="50" name="tableName" class="required" value="${dicEntity.tableName }" readonly="readonly"/>
			</div>
			<div class="unit">
				<label>字段名：</label>
				<input type="text" size="50" name="fieldName" class="required" value="${dicEntity.fieldName }"/>
			</div>
			<div class="unit">
				<label>字段注解：</label>
				<input type="text" size="50" name="fieldComment" class="required" value="${dicEntity.fieldComment }"/>
			</div>
			<div class="unit">
				<label>字段值：</label>
				<input type="text" size="50" name="fieldValue" class="required" value="${dicEntity.fieldValue }"/>
			</div>
			<div class="unit">
				<label>值英文名：</label>
				<input type="text" size="50" name="valuePname" class="required" value="${dicEntity.valuePname }"/>
			</div>
			<div class="unit">
				<label>值显示名：</label>
				<input type="text" size="50" name="valueShowname" class="required" value="${dicEntity.valueShowname }"/>
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