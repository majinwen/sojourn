<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function editLoadbalancesAjaxDone(json) {
		$('#service_loadbalancesContainer').loadUrl('servicecenter/service/loadbalances.do?search_service=${loadbalance.service}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
	
	function addLoadbalanceStrategy(sel) {
		var $target = $("#balance_strategy");
		$target.val(sel.value);
	}
</script>
<div class="pageContent">
	<form class="required-validate" action="servicecenter/service/loadbalances/edit.do" method="post" onsubmit="return validateCallback(this, editLoadbalancesAjaxDone)">
		<input type="hidden" name="service" value="${loadbalance.service}">
		<input type="hidden" name="balance_method" value="${loadbalance.method}">
		<input type="hidden" name="id" value="${loadbalance.id}">
		<div class="pageFormContent" layoutH="56">
			<p style="width:100%;">
				<label>服务名：</label>
				<span>${loadbalance.service}</span>
			</p>
			<p style="width:100%;">
				<label>方法名：</label>
				<span>${loadbalance.method}</span>
			</p>
			<p style="width:100%;">
				<label>负载均衡策略：</label>
				<input type="text" id="balance_strategy" name="strategy" value="${loadbalance.strategy}" class="required" size="30" />
				<select style="margin-left:5px;" onchange="addLoadbalanceStrategy(this)">
					<option value="">请选择</option>
					<option ${loadbalance.strategy eq 'random' ? 'selected="selected"':''} value="random"><spring:message code="random"/></option>
					<option ${loadbalance.strategy eq 'roundrobin' ? 'selected="selected"':''} value="roundrobin"><spring:message code="roundrobin"/></option>
					<option ${loadbalance.strategy eq 'leastactive' ? 'selected="selected"':''} value="leastactive"><spring:message code="leastactive"/></option>
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