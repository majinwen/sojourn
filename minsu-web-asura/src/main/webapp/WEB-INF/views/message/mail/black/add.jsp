<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<script type="text/javascript">
	/**
		表单回调函数
		方法：validateCallback内部采用ajax提交方式, 参考2443行
	*/
	function addBlackListAjaxDone(json) {
		// 获得包含表单的div, 重新加载表单请求
		$('#search_blackMailListesContainer').loadUrl('message/mail/black/blackListMail.do?resId=${resId}');
		// 提示信息
		alertMsgByJson(json);
		// 关闭对话框
		$.pdialog.closeCurrent();
	}
</script>
<div class="pageContent">
	<form method="post" action="message/mail/black/addBlackListMail.do" class="pageForm required-validate" onsubmit="return validateCallback(this, addBlackListAjaxDone)">
		<div class="pageFormContent" layoutH="58">

			<div class="unit">
				<label>邮箱黑名单地址：</label>
				<textarea name="mailAddr" rows="3" cols="50" maxlength="500"></textarea>
			</div>
			<div class="unit">
				<label>黑名单类型：</label>
				<input type="radio" id="blackType0Id" name="btype" checked="checked" value="1"/>bug报警邮件
				<input type="radio" id="blackType1Id" name="btype" value="2"/>广告邮件
				<input type="radio" id="blackType1Id" name="btype" value="3"/>正常业务邮件
			</div>
			<div class="unit">
				<label>备注：</label>
				<textarea name="notes" rows="3" cols="50"></textarea>
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