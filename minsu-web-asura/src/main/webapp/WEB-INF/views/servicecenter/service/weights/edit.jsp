<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function editWeightsAjaxDone(json) {
		$('#service_weightsContainer').loadUrl('servicecenter/service/weights.do?search_service=${weight.service}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
</script>
<div class="pageContent">
	<form class="required-validate" action="servicecenter/service/weights/edit.do" method="post" onsubmit="return validateCallback(this, editWeightsAjaxDone)">
		<input type="hidden" name="service" value="${weight.service}">
		<input type="hidden" name="address" value="${weight.address}">
		<input type="hidden" name="id" value="${weight.id}">
		<div class="pageFormContent" layoutH="56">
			<p style="width:100%;">
				<label>服务名：</label>
				<span>${weight.service}</span>
			</p>
			<p style="width:100%;">
				<label>提供者：</label>
				<span>${weight.address}</span>
			</p>
			<p style="width:100%;">
				<label>权重调节：</label>
				<input type="text" name="weight" value="${weight.weight}" class="required" size="30" />
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