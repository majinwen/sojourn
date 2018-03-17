<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<script type="text/javascript">
	/**
		表单回调函数
		方法：validateCallback内部采用ajax提交方式, 参考2443行
	*/
	function editOperatorAjaxDone(json) {
		// 获得包含表单的div, 重新加载表单请求
		$('#search_operatorListsContainer').loadUrl('authority/operator/operatorList.do?resId=${resId }');
		// 提示信息
		alertMsgByJson(json);
		// 关闭对话框
		$.pdialog.closeCurrent();
	}
</script>
<div class="pageContent">
	<form method="post" action="authority/operator/edit.do" class="pageForm required-validate" onsubmit="return validateCallback(this, editOperatorAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="operatorId" value="${operator.operatorId }"/>
			<div class="unit">
				<label>姓名：</label>
				<input type="text" name="operatorName" value="${operator.operatorName }" class="required"/>
			</div>
			<div class="unit">
				<label>登录账号：</label>
				<input type="text" name="loginName" value="${operator.loginName }" class="required"></input>
			</div>
			<div class="unit">
				<label>工号：</label>
				<input type="text" name="jobNum" value="${operator.jobNum }" class="required"></input>
			</div>
			<div class="unit">
				<label>手机号：</label>
				<input type="text" name="telephone" value="${operator.telephone }" class="required"></input>
			</div>
			<div class="unit">
				<label>邮件：</label>
				<input type="text" name="operatorEmail" value="${operator.operatorEmail }" class="required email"></input>
			</div>
			<div class="unit">
				<label>所属部门：</label>
				<input type="radio" id="operatorDepartment5Id" name="operatorDepartment" checked="checked" value="5"/>技术部
			</div>
			<div class="unit">
				<label>类型：</label>
				<c:if test="${operator.operatorType == 1 }">
					<input type="radio" id="operatorType0Id" name="operatorType" checked="checked" value="1"/>操作员
					<input type="radio" id="operatorType1Id" name="operatorType" value="2"/>管理员
				</c:if>
				<c:if test="${operator.operatorType == 2 }">
					<input type="radio" id="operatorType0Id" name="operatorType" value="1"/>操作员
					<input type="radio" id="operatorType1Id" name="operatorType" checked="checked" value="2"/>管理员
				</c:if>
			</div>
			<div class="unit">
				<label>状态：</label>
				<c:if test="${operator.operatorStatus == 0 }">
					<input type="radio" id="operatorStatus0Id" name="operatorStatus" checked="checked" value="0"/>失效
					<input type="radio" id="operatorStatus1Id" name="operatorStatus" value="1"/>正常
					<input type="radio" id="operatorStatus2Id" name="operatorStatus" value="2"/>删除
				</c:if>
				<c:if test="${operator.operatorStatus == 1 }">
					<input type="radio" id="operatorStatus0Id" name="operatorStatus" value="0"/>失效
					<input type="radio" id="operatorStatus1Id" name="operatorStatus" checked="checked" value="1"/>正常
					<input type="radio" id="operatorStatus2Id" name="operatorStatus" value="2"/>删除
				</c:if>
				<c:if test="${operator.operatorStatus == 2 }">
					<input type="radio" id="operatorStatus0Id" name="operatorStatus" value="0"/>失效
					<input type="radio" id="operatorStatus1Id" name="operatorStatus" value="1"/>正常
					<input type="radio" id="operatorStatus2Id" name="operatorStatus" checked="checked" value="2"/>删除
				</c:if>
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