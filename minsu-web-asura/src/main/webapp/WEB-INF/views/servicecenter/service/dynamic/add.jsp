<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function addDynamicAjaxDone(json) {
		$('#service_dynamicContainer').loadUrl('servicecenter/service/dynamic.do?search_service=${param.service}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
	
	function addAppServiceValue(target) {
		$("#application_service").val(target.value);
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
	<form class="required-validate" action="servicecenter/service/dynamic/add.do" method="post" onsubmit="return validateCallback(this, addDynamicAjaxDone)">
		
		<div class="pageFormContent" layoutH="56">
			<div class="panel" defH="130">
				<h1>推送配置信息</h1>
				<div>
					<p style="width:100%;">
						<label>服务名：</label>
						<c:if test="${empty param.service}">
							<input type="text" id="application_service" name="service" class="required" size="50" />
							<select style="margin-left:5px;" onchange="addAppServiceValue(this)">
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
						<label>消费者应用名：</label>
						<input type="text" name="application" class="required" size="30" />
					</p>
					<p style="width:100%;">
						<label>只推送的消费者地址：</label>
						<input type="text" name="address" size="30" />
						<span style="margin-left:10px;">不填表示对消费者应用的所有机器生效</span>
					</p>
					<p style="width:100%;">
						<label>状态：</label>
						<select name="enabled">
							<option value="true">启用</option>
							<option selected="selected" value="false">禁用</option>
						</select>
					</p>
				</div>
			</div>
			<div class="panel">
				<h1>动态配置  <span style="color:#f00">(方法级配置如：findPerson.timeout=1000)</span></h1>
				<div id="parameterDiv">
					<p style="width:100%;">
						<label>参数名：</label>
						<input type="text" style="width:200px;" name="overrideKey0" />
						<span style="margin:0 10px 0 30px;">参数值：</span>
						<input type="text" style="width:200px;" name="overrideValue0" />
					</p>
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
							<option value="fail">容错</option>
							<option value="force">屏蔽</option>
						</select>
						<span style="width:5px;">&nbsp;</span>
						<input type="text" style="width:200px;" name="mockDefaultMethodJson" />
					</p>
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