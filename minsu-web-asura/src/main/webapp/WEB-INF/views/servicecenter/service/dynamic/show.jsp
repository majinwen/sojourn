<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<div class="panel" defH="130">
			<h1>推送配置信息</h1>
			<div>
				<p style="width:100%;">
					<label>服务名：</label>
					<span>${dynamic.service}&nbsp;</span>
				</p>
				<p style="width:100%;">
					<label>状态：</label>
					<span>${dynamic.enabled?'已启用':'已禁用'}&nbsp;</span>
				</p>
				<p style="width:100%;">
					<label>应用名：</label>
					<span>${dynamic.application}&nbsp;</span>
				</p>
				<p style="width:100%;">
					<label>客户端地址：</label>
					<span>${dynamic.address}&nbsp;</span>
				</p>
			</div>
		</div>
		<div class="panel">
			<h1>动态配置</h1>
			<div>
				<c:forEach var="parameter" items="${parameters}" varStatus="status">
				<p style="width:100%;">
					<label>参数名：</label>
					<span style="width:200px;">${parameter.key}&nbsp;</span>
					<span style="margin:0 5px;">参数值：</span>
					<span>${parameter.value}&nbsp;</span>
				</p>
				</c:forEach>
			</div>
		</div>
		<div class="panel">
			<h1>服务降级</h1>
			<div>
				<p style="width:100%;">
					<label>所有方法的Mock值：</label>
					<span>${mockDefaultMethodForce eq 'force' ? '屏蔽' : '容错'}：${mockDefaultMethodJson}</span>
				</p>
				<c:forEach var="m" items="${methodJsons}" varStatus="status">
				<p style="width:100%;">
					<label>方法<font color="orange"><b>${m.key}</b></font>的Mock值：</label>
					<span>${methodForces[m.key] eq 'force' ? '屏蔽' : '容错'}：${m.value}</span>
				</p>
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button"><div class="buttonContent"><button id="closebutton" type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</div>