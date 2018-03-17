<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function addLoadbalancesAjaxDone(json) {
		$('#service_loadbalancesContainer').loadUrl('servicecenter/service/loadbalances.do?search_service=${param.service}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
	
	function addAddrServiceValue(target) {
		$("#balance_service").val(target.value);
		$("#balance_method").val('');
		
		var routemethod = document.getElementById('balance_method_select');
		while(routemethod.options.length > 1)
			routemethod.options.remove(1);
		
		if(target.value) {
			$.ajax({
				url: "servicecenter/address/routes/serviceMethods.do",
				cache: true,
				data: {'service' : target.value},
				success: function(data){
					for(var i=0; i < data.length; i++) {
						routemethod.options.add(new Option(data[i], data[i])); //这个兼容IE与firefox
					}
				}
			});
		}
	}
	
	function addLoadbalanceMethod(sel) {
		var $target = $("#balance_method");
		$target.val(sel.value);
	}
	
	function addLoadbalanceStrategy(sel) {
		var $target = $("#balance_strategy");
		$target.val(sel.value);
	}
</script>
<div class="pageContent">
	<form class="required-validate" action="servicecenter/service/loadbalances/add.do" method="post" onsubmit="return validateCallback(this, addLoadbalancesAjaxDone)">
		<div class="pageFormContent" layoutH="56">
			<p style="width:100%;">
				<label>服务名：</label>
				<c:if test="${empty route.service and empty param.service}">
					<input type="text" id="balance_service" name="service" value="${empty param.service? route.service : param.service}" size="50" />
					<select style="margin-left:5px;" onchange="addAddrServiceValue(this)">
						<option value="">请选择</option>
						<c:forEach var="service" items="${serviceMap}" varStatus="status">
							<option value="${service.value}" ${service.value eq route.service or service.value eq param.service ? 'selected="selected"':''}>${service.key}</option>
						</c:forEach>
					</select>
				</c:if>
				<c:if test="${not empty route.service or not empty param.service}">
					<span>${empty param.service? route.service : param.service}&nbsp;</span>
					<input type="hidden" name="service" value="${empty param.service? route.service : param.service}">
				</c:if>
			</p>
			<p style="width:100%;">
				<label>方法名：</label>
				<input type="text" id="balance_method" name="balance_method" class="required" size="30" />
				<select id="balance_method_select" style="margin-left:5px;" onchange="addLoadbalanceMethod(this)">
					<option value="">请选择</option>
					<c:forEach var="method" items="${methods}" varStatus="status">
						<option value="${method}">${method}</option>
					</c:forEach>
				</select>
				<span style="margin-left:5px;color:#00f;">“*”代表所有方法</span>
			</p>
			<p style="width:100%;">
				<label>负载均衡策略：</label>
				<input type="text" id="balance_strategy" name="strategy" class="required" size="30" />
				<select style="margin-left:5px;" onchange="addLoadbalanceStrategy(this)">
					<option value="">请选择</option>
					<option value="random"><spring:message code="random"/></option>
					<option value="roundrobin"><spring:message code="roundrobin"/></option>
					<option value="leastactive"><spring:message code="leastactive"/></option>
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