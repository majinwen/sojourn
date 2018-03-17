<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<div class="panel" defH="250">
			<h1>消费者地址</h1>
			<div>
			<table class="list" width="100%">
				<thead>
					<tr>
						<th width="200">消费者地址</th>
						<th width="80">注册用户名</th>
						<th width="80">注册时间</th>
						<th width="200">注册中心地址</th>
						<th width="80">预览</th>
					</tr>
				</thead>
				<tbody style="border-top: 1px solid #EDEDED; border-left: 1px solid #EDEDED;">
					<c:forEach var="consumer" items="${consumers}" varStatus="status">
					<tr>
						<td class="tablefirsttd">${consumer.address}</td>
						<td>${consumer.username}</td>
						<td>${consumer.created}</td>
						<td>${consumer.registry}</td>
						<td>
							<c:if test="${matchRoute[consumer.address]}">
								<font color="blue">本Route不匹配此服务消费者（会返回所有的服务提供者）</font>
								<a href="servicecenter/service/routes/preview.do?routesId=${route.id}&consumerId=${consumer.id}">预览</a>
							</c:if>
							<c:if test="${not matchRoute[consumer.address]}">
								<font color="blue">本Route不匹配此服务消费者（会返回所有的服务提供者）</font>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
		
		<form class="required-validate" method="post" action="servicecenter/service/routes/preview.do" onsubmit="return dialogSearch(this);">
		<div class="panel" defH="70">
			<h1>自定义Consumer</h1>
			<div>
				<p style="width:100%;">
					<label>消费者地址：</label>
					<input type="text" name="address" class="required" size="30" alt="请输入字段取值键" />
			        <input type="hidden" name="service" value="${route.service}">
			        <input type="hidden" name="routesId" value="${route.id}">
				</p>
				<div class="buttonActive"><div class="buttonContent"><button type="submit">预览</button></div></div>
			</div>
		</div>
		</form>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button"><div class="buttonContent"><button id="closebutton" type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</div>