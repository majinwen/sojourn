<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function editDynamicAjaxDone(json) {
		$('#service_dynamicContainer').loadUrl('servicecenter/service/dynamic.do?search_service=${override.service}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
	
	function addParameters() {
		var length = $("input[name^='overrideKey']", '#parameterDiv').length;
		
		var parameterHtml = '<p style="width:100%;">'
							+ '<label>参数名：</label>'
							+ '<input class="textInput" type="text" style="width:200px;" name="overrideKey' + length + '" />'
							+ '<span style="margin:0 10px 0 30px;">参数值：</span>'
							+ '<input class="textInput" type="text" style="width:200px;" name="overrideValue' + length + '" />'
							+ '</p>';
		
		$("#parameterButton").before(parameterHtml);
	}
	
	function addMethods() {
		var length = $("select", '#methodDiv').length;
		
		var parameterHtml = '<p style="width:100%;">'
							+ '<label><input class="textInput" type="text" style="width:50px;" name="mockMethodName' + length + '" />的Mock值：</label>'
							+ '<select name="mockMethodForce' + length + '">'
							+ '<option value="fail">容错</option>'
							+ '<option value="force">屏蔽</option>'
							+ '</select>'
							+ '<span style="width:5px;">&nbsp;</span>'
							+ '<input class="textInput" type="text" style="width:200px;" name="mockMethodJson' + length + '" />'
							+ '</p>';
		
		$("#methodButton").before(parameterHtml);
	}
</script>
<div class="pageContent">
	<form class="required-validate" action="servicecenter/service/dynamic/edit.do" method="post" onsubmit="return validateCallback(this, editDynamicAjaxDone)">
		<input type="hidden" name="id" value="${override.id}">
		<input type="hidden" name="service" value="${override.service}">
		<div class="pageFormContent" layoutH="56">
			<div class="panel" defH="130">
				<h1>推送配置信息</h1>
				<div>
					<p style="width:100%;">
						<label>服务名：</label>
						<span>${override.service}</span>
					</p>
					<p style="width:100%;">
						<label>消费者应用名：</label>
						<input type="text" name="application" value="${override.application}" class="required" size="30" />
					</p>
					<p style="width:100%;">
						<label>只推送的消费者地址：</label>
						<input type="text" name="address" value="${override.address}" size="30" />
						<span style="margin-left:10px;">不填表示对消费者应用的所有机器生效</span>
					</p>
					<p style="width:100%;">
						<label>状态：</label>
						<select name="enabled">
							<option ${override.enabled?"selected='selected'":""} value="true">启用</option>
							<option ${override.enabled?"":"selected='selected'"} value="false">禁用</option>
						</select>
					</p>
				</div>
			</div>
			<div class="panel">
				<h1>动态配置  <span style="color:#f00">(方法级配置如：findPerson.timeout=1000)</span></h1>
				<div id="parameterDiv">
					<c:forEach var="parameter" items="${parameters}" varStatus="status">
					<p style="width:100%;">
						<label>参数名：</label>
						<input type="text" style="width:200px;" name="overrideKey${status.index}" value="${parameter.key}" />
						<span style="margin:0 10px 0 30px;">参数值：</span>
						<input type="text" style="width:200px;" name="overrideValue${status.index}" value="${parameter.value}" />
					</p>
					</c:forEach>
					<p style="width:100%;" id="parameterButton">
						<div class="button"><div class="buttonContent"><button type="button" onclick="addParameters()">新增参数</button></div></div>
					</p>
				</div>
			</div>
			<div class="panel">
				<h1>服务降级 <span style="color:#f00">(示例：return null/empty/JSON或throw com.foo.BarException)</span></h1>
				<div id="methodDiv">
					<p style="width:100%;">
						<label>所有方法的Mock值：</label>
						<select name="mockDefaultMethodForce">
							<option ${mockDefaultMethodForce eq 'fail' ? 'selected="selected"' : ''} value="fail">容错</option>
							<option ${mockDefaultMethodForce eq 'force' ? 'selected="selected"' : ''} value="force">屏蔽</option>
						</select>
						<span style="width:5px;">&nbsp;</span>
						<input type="text" style="width:200px;" name="mockDefaultMethodJson" value="${mockDefaultMethodJson}" />
					</p>
					<c:forEach var="m" items="${methodJsons}" varStatus="status">
						<p style="width:100%;">
							<label><input type="text" style="width:50px;" name="mockMethodName${status.index}" value="${m.key}" />的Mock值：</label>
							<select name="mockMethodForce${status.index}">
								<option ${methodForces[m.key] eq 'fail' ? 'selected="selected"' : ''} value="fail">容错</option>
								<option ${methodForces[m.key] eq 'force' ? 'selected="selected"' : ''} value="force">屏蔽</option>
							</select>
							<span style="width:5px;">&nbsp;</span>
							<input type="text" style="width:200px;" name="mockMethodJson${status.index}" value="${m.value}" />
						</p>
					</c:forEach>
					<p style="width:100%;" id="methodButton">
						<div class="button"><div class="buttonContent"><button type="button" onclick="addMethods()">新增方法</button></div></div>
					</p>
				</div>
			</div>
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