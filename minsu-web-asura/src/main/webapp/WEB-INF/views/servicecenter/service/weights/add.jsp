<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function addWeightsAjaxDone(json) {
		$('#service_weightsContainer').loadUrl('servicecenter/service/weights.do?search_service=${param.service}');
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
		
		var weightaddress = document.getElementById('weightaddress');
		while(weightaddress.options.length > 1)
			weightaddress.options.remove(1);
		if(text) {
			$.ajax({
				url: "servicecenter/service/weights/serviceAddresses.do",
				cache: true,
				data: {'service' : text},
				success: function(data){
					for(var i=0; i < data.length; i++) {
						weightaddress.options.add(new Option(data[i], data[i])); //这个兼容IE与firefox
					}
				}
			});
		}
	}
	
	function addWeightAddress(sel) {
		var $address = $("#weight_address");
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
	<form class="required-validate" action="servicecenter/service/weights/add.do" method="post" onsubmit="return validateCallback(this, addWeightsAjaxDone)">
		<div class="pageFormContent" layoutH="56">
			<p style="width:100%;">
				<label>服务名：</label>
				<c:if test="${empty param.service}">
					<textarea id="address_weights" name="service" class="required" rows="8" cols="50"></textarea>
					<select style="margin:124px 0 0 5px;" onchange="addAddressWeightService(this)">
						<option value="">请选择</option>
						<c:forEach var="service" items="${serviceMap}" varStatus="status">
							<option value="${service.value}">${service.key}</option>
						</c:forEach>
					</select>
				</c:if>
				<c:if test="${not empty param.service}">
					<span>${param.service}</span>
					<input type="hidden" name="service" value="${param.service}">
				</c:if>
			</p>
			<p style="width:100%;">
				<label>提供者：</label>
				<textarea id="weight_address" name="address" class="required" rows="8" cols="50"></textarea>
				<select id="weightaddress" style="margin:124px 0 0 5px;" onchange="addWeightAddress(this)">
					<option value="">请选择</option>
					<c:forEach var="address" items="${addressList}" varStatus="status">
						<option value="${address}">${address}</option>
					</c:forEach>
				</select>
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