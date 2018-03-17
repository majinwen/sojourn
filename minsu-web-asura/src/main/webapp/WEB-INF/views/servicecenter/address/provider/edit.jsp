<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function editProviderAjaxDone(json) {
		$('#service_providerContainer').loadUrl('servicecenter/service/provider.do?search_service=${provider.service}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
	
	function addAddrServiceValue(target) {
		$("#address_service").val(target.value);
		var url = $("#address_url");
		
		var start = url.lastIndexOf('/');
		var end   = url.indexOf('?');
		
		$("#address_url").val(url.substring(0, start) + target.value + url.substr(end));
	}
</script>
<div class="pageContent">
	<form class="required-validate" action="servicecenter/service/provider/edit.do" method="post" onsubmit="return validateCallback(this, editProviderAjaxDone)">
		<input type="hidden" name="id" value="${provider.id}">
		<div class="pageFormContent" layoutH="56">
			<p style="width:100%;">
				<label>服务名：</label>
				<input type="text" id="address_service" name="service" class="required" size="50" />
				<select style="margin-left:5px;" onchange="addAddrServiceValue(this)">
					<option value="">请选择</option>
					<c:forEach var="service" items="${serviceMap}" varStatus="status">
						<option value="${service.value}">${service.key}</option>
					</c:forEach>
				</select>
			</p>
			<p style="width:100%;">
				<label>服务地址：</label>
				<span style="width:680px;">
				<c:if test="${provider.dynamic}">${provider.url}?${provider.parameters}</c:if>
				<c:if test="${not provider.dynamic}">${provider.url}?<input type="text" name="parameters" class="required" value="${provider.parameters}" size="90" alt="请输入字段取值键" /></c:if>
				</span>
			</p>
			<c:if test="${provider.dynamic}">
			<p style="width:100%;">
				<label>动态配置：</label>
				<input type="text" name="parameters" value="${provider.override.params}" class="required" size="60" />
				<span>动态URL不能直接修改，需通过动态配置进行覆盖，格式与URL参数相同。</span>
			</p>
			</c:if>
			<p style="width:100%;">
				<label>类型：</label>
				<c:if test="${provider.dynamic}"><span>动态</span></c:if>
				<c:if test="${not provider.dynamic}"><span>静态</span></c:if>
			</p>
			<p style="width:100%;">
				<label>状态：</label>
				<c:if test="${provider.enabled}"><span>已启用</span></c:if>
				<c:if test="${not provider.enabled}"><span>已禁用</span></c:if>
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