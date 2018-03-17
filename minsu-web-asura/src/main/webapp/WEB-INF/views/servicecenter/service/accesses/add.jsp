<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function addAccessesAjaxDone(json) {
		$('#service_accessesContainer').loadUrl('servicecenter/service/accesses.do?search_service=${param.service}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
	
	function addAddressService(sel) {
		var $address = $("#address_service");
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
	<form class="required-validate" action="servicecenter/service/accesses/add.do" method="post" onsubmit="return validateCallback(this, addAccessesAjaxDone)">
		
		<div class="pageFormContent" layoutH="56">
			<p style="width:100%;">
				<label>服务名：</label>
				<c:if test="${empty param.service}">
					<textarea id="address_service" name="service" class="required" rows="8" cols="50"></textarea>
					<select style="margin:124px 0 0 5px;" onchange="addAddressService(this)">
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
				<label>消费者地址：</label>
				<textarea name="consumerAddress" class="required" rows="8" cols="50"></textarea>
				<span style="margin-left:130px;color:#00f;">多个地址用换行符分隔，地址必需是0.0.0.0到255.255.255.255的IP地址</span>
			</p>
			<p style="width:100%;">
				<label>状态：</label>
				<select name="allow">
					<option value="true">已启用</option>
					<option selected="selected" value="false">已禁用</option>
				</select>
				<span style="margin-left:5px;color:#00f;">白名单优先，只要有白名单，则白名单生效，否则黑名单生效</span>
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