<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function addAddrWeightsAjaxDone(json) {
		$('#address_weightsContainer').loadUrl('servicecenter/address/weights.do?search_address=${param.address}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
	
	function addAddressWeightService(sel) {
		var $address = $("#address_weights");
		var text = $address.val();
		if(sel.value && text.indexOf(sel.value) < 0) {
			if(text) {
				text += "\r\n";
			}
			text += sel.value;
		}
		
		$address.val(text);
	}
</script>
<div class="pageContent">
	<form class="required-validate" action="servicecenter/service/weights/add.do" method="post" onsubmit="return validateCallback(this, addAddrWeightsAjaxDone)">
		<input type="hidden" name="address" value="${address}">
		<div class="pageFormContent" layoutH="56">
			<p style="width:100%;">
				<label>服务名：</label>
				<textarea id="address_weights" name="service" class="required" rows="8" cols="50"></textarea>
				<select style="margin:124px 0 0 5px;" onchange="addAddressWeightService(this)">
					<option value="">请选择</option>
					<c:forEach var="service" items="${serviceMap}" varStatus="status">
						<option value="${service.value}">${service.key}</option>
					</c:forEach>
				</select>
			</p>
			<p style="width:100%;">
				<label>提供者：</label>
				<span style="color:#f00;">${address}</span>
				<span style="margin-left:130px;color:#00f;">多个地址用换行符分隔，地址必需是0.0.0.0到255.255.255.255的IP地址</span>
			</p>
			<p style="width:100%;">
				<label>权重调节：</label>
				<input type="text" name="weight" class="required" size="30" />
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