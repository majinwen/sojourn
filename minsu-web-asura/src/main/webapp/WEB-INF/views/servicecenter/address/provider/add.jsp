<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function addProviderAjaxDone(json) {
		$('#address_providerContainer').loadUrl('servicecenter/address/provider.do?search_address=${address}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
	
	function addAddrServiceValue(target) {
		if(target.value) {
			$("#address_service").val(target.value);
			var url = $("#address_url").val();
			var start = url.lastIndexOf('/');
			var end   = url.indexOf('?');
			
			$("#address_url").val(url.substring(0, start + 1) + target.value + url.substr(end));
		}
	}
</script>
<div class="pageContent">
	<form class="required-validate" action="servicecenter/service/provider/add.do" method="post" onsubmit="return validateCallback(this, addProviderAjaxDone)">
		<div class="pageFormContent" layoutH="56">
			<p style="width:100%;">
				<label>服务名：</label>
				<input type="text" id="address_service" name="service" size="50" />
				<select style="margin-left:5px;" onchange="addAddrServiceValue(this)">
					<option value="">请选择</option>
					<c:forEach var="service" items="${serviceMap}" varStatus="status">
						<option value="${service.value}">${service.key}</option>
					</c:forEach>
				</select>
			</p>
			<p style="width:100%;">
				<label>服务地址：</label>
				<input type="text" id="address_url" name="url" class="required" value="${service_address}" size="90" alt="请输入字段取值键" />
			</p>
			<p style="width:100%;">
				<label>类型：</label>
				<span>静态</span>
			</p>
			<p style="width:100%;">
				<label>状态：</label>
				<select name="enabled">
					<option value="true">启用</option>
					<option selected="selected" value="false">禁用</option>
				</select>
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