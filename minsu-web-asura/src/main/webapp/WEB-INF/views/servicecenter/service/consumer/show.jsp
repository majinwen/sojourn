<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<p style="width:100%;">
			<label>消费者地址：</label>
			<span style="width:90%">consumer://${consumer.address}/${consumer.service}?${consumer.parameters}</span>
		</p>
		<p style="width:100%;">
			<label>动态配置：</label>
			<span>${consumer.override.params}</span>
		</p>
		<p style="width:100%;">
			<label>主机名：</label>
			<span>${consumer.address}</span>
		</p>
		<c:forEach var="parameter" items="${parameterMap}" varStatus="status">
			<p style="width:100%;">
				<label><spring:message code="${parameter.key}"/>：</label>
				<span>${parameter.value}</span>
			</p>
		</c:forEach>
		<p style="width:100%;">
			<label>降级：</label>
			<span>${consumer.mockType}&nbsp;</span>
		</p>
		<p style="width:100%;">
			<label>状态：</label>
			<span>${consumer.allowed ? '已允许' : '已禁止'}&nbsp;</span>
		</p>
		<p style="width:100%;">
			<label>路由：</label>
			<span><c:if test="${consumer.routeCount > 0}">已路由（${consumer.routeCount}）</c:if><c:if test="${consumer.routeCount eq 0}">未路由</c:if>&nbsp;</span>
		</p>
		<p style="width:100%;">
			<label>通知：</label>
			<span><c:if test="${consumer.providerCount > 0}">已通知（${consumer.providerCount}）</c:if><c:if test="${consumer.providerCount eq 0}">没有提供者</c:if>&nbsp;</span>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button"><div class="buttonContent"><button id="closebutton" type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</div>