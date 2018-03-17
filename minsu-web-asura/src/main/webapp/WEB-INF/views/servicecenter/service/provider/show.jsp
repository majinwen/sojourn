<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<p style="width:100%;">
			<label>服务名称：</label>
			<span style="width:90%">${provider.service}</span>
		</p>
		<p style="width:100%;">
			<label>服务地址：</label>
			<span style="width:90%">${provider.url}?${provider.parameters}</span>
		</p>
		<c:if test="${provider.dynamic}">
		<p style="width:100%;">
			<label>动态配置：</label>
			<span>${provider.override.params}</span>
		</p>
		</c:if>
		<p style="width:100%;">
			<label>主机名：</label>
			<span>${provider.address}</span>
		</p>
		<c:forEach var="parameter" items="${parameterMap}" varStatus="status">
			<p style="width:100%;">
				<label><spring:message code="${parameter.key}"/>：</label>
				<span>${parameter.value}</span>
			</p>
		</c:forEach>
		<p style="width:100%;">
			<label>类型：</label>
			<span>${provider.dynamic ? '动态':'静态'}</span>
		</p>
		<p style="width:100%;">
			<label>状态：</label>
			<span>${provider.enabled ? '已启用':'已禁止'}</span>
		</p>
		<p style="width:100%;">
			<label>检查：</label>
			<span>正常</span>
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