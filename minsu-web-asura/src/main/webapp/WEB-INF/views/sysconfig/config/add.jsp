<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function addSysConfigAjaxDone(json) {
		$('#sysconfig_configContainer').loadUrl('sysconfig/config/search.do?resId=${resId }');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
</script>
<div class="pageContent">
	<form class="required-validate" action="sysconfig/config/add.do" method="post" onsubmit="return validateCallback(this, addSysConfigAjaxDone)">
		<div class="pageFormContent" layoutH="56">
			<p style="width:100%;">
				<label>类型：</label>
				<input type="text" name="type" class="required" size="50" alt="请输入类型" />
			</p>
			<p style="width:100%;">
				<label>CODE：</label>
				<input type="text" name="code" class="required" size="50" alt="请输入CODE" />
			</p>
			<p style="width:100%;">
				<label>名称：</label>
				<input type="text" name="name" size="50" alt="请输入名称" />
			</p>
			<p style="width:100%;">
				<label>值：</label>
				<input type="text" name="value" size="50" alt="请输入名称" />
			</p>
			<p style="width:100%;">
				<label>描述：</label>
				<textarea name="notes" style="width: 300px;" rows="3" cols="27"></textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button id="closebutton" type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>