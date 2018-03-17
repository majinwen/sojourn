<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function editConsumerAjaxDone(json) {
		$('#service_consumerContainer').loadUrl('servicecenter/service/consumer.do?search_service=${consumer.service}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
	
	function editAddrConsumerAjaxDone(json) {
		$('#address_consumerContainer').loadUrl('servicecenter/address/consumer.do?search_address=${consumer.address}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
</script>
<div class="pageContent">
	<form class="required-validate" action="servicecenter/service/consumer/edit.do" method="post" onsubmit="return validateCallback(this, ${empty param.edittype ? 'editConsumerAjaxDone' : 'editAddrConsumerAjaxDone'})">
		<input type="hidden" name="id" value="${consumer.id}">
		<div class="pageFormContent" layoutH="56">
			<p style="width:100%;">
				<label>服务名：</label>
				<span>${consumer.service}&nbsp;</span>
			</p>
			<p style="width:100%;">
				<label>消费者地址：</label>
				<span style="width:680px;">consumer://${consumer.address}/${consumer.service}?${consumer.parameters}</span>
			</p>
			<p style="width:100%;">
				<label>动态配置：</label>
				<input type="text" name="parameters" value="${consumer.override.params}" class="required" size="60" />
				<span>动态URL不能直接修改，需通过动态配置进行覆盖，格式与URL参数相同。</span>
			</p>
			<p style="width:100%;">
				<label>降级：</label>
				<span>${consumer.mockType}</span>
			</p>
			<p style="width:100%;">
				<label>状态：</label>
				<span>${consumer.allowed ? '已允许' : '已禁止'}</span>
			</p>
			<p style="width:100%;">
				<label>路由：</label>
				<span>${consumer.routeCount > 0? '已路由' : '未路由'}</span>
			</p>
			<p style="width:100%;">
				<label>通知：</label>
				<span><c:if test="${consumer.providerCount > 0}">已通知（${consumer.providerCount}）</c:if><c:if test="${consumer.providerCount eq 0}">没有提供者</c:if></span>
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