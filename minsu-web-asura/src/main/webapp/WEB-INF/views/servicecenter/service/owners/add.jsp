<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function addOwnersAjaxDone(json) {
		$('#service_ownersContainer').loadUrl('servicecenter/service/owners.do?search_service=${param.service}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
	
	function addAddrServiceValue(target) {
		$("#owner_service").val(target.value);
	}
</script>
<div class="pageContent">
	<form class="required-validate" action="servicecenter/service/owners/add.do" method="post" onsubmit="return validateCallback(this, addOwnersAjaxDone)">
		<div class="pageFormContent" layoutH="56">
			<p style="width:100%;">
				<label>服务名：</label>
				<c:if test="${empty route.service and empty param.service}">
					<input type="text" id="owner_service" name="service" value="${empty param.service? route.service : param.service}" size="50" />
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
				<label>用户名：</label>
				<input type="text" name="username" value="${SESSION_KEY_USER.username}" class="required" size="30" />
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