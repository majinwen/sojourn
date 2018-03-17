<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function loginAjaxDone(json) {
		alertMsgByJson(json);
		
		if(json.statusCode == DWZ.statusCode.ok) {
			$("div.dialogHeader a.close").trigger("click");
			navTab.closeAllTab();
		}
	}
</script>
<div class="pageContent">
	<form action="logindialog/login.do" method="post" onsubmit="return validateCallback(this, loginAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>用户名：</label>
				<input type="text" name="username" size="20" value="root" class="login_input"/>
			</p>
			<p>
				<label>密码：</label>
				<input type="password" name="password" size="20" value="root" class="login_input"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button id="closebutton" type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>