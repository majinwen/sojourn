<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent j-resizeGrid">
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="55" rel="service_providerContainer">
			<thead>
				<tr>
					<th width="500">属性名</th>
					<th width="100">属性数量</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><spring:message code="NoProvider"/></td>
					<td>
						<a style="color:#00f;" href="servicecenter/sysinfo/dumps/show.do?type=noProvider" width="840" mask="true" target="dialog" title="没有提供者服务">
							<spring:message code="property.count"/>（${noProviderServicesCount}）
						</a>&nbsp;
					</td>
				</tr>
				<tr>
					<td><spring:message code="services"/></td>
					<td>
						<a style="color:#00f;" href="servicecenter/sysinfo/dumps/show.do?type=services" width="840" mask="true" target="dialog" title="所有服务">
							<spring:message code="property.count"/>（${servicesCount}）
						</a>&nbsp;
					</td>
				</tr>
				<tr>
					<td><spring:message code="providers"/></td>
					<td>
						<a style="color:#00f;" href="servicecenter/sysinfo/dumps/show.do?type=providers" width="840" mask="true" target="dialog" title="所有提供者">
							<spring:message code="property.count"/>（${providersCount}）
						</a>&nbsp;
					</td>
				</tr>
				<tr>
					<td><spring:message code="consumers"/></td>
					<td>
						<a style="color:#00f;" href="servicecenter/sysinfo/dumps/show.do?type=consumers" width="840" mask="true" target="dialog" title="所有消费者">
							<spring:message code="property.count"/>（${consumersCount}）
						</a>&nbsp;
					</td>
				</tr>
				<tr>
					<td><spring:message code="versions"/></td>
					<td>
						<a style="color:#00f;" href="servicecenter/sysinfo/dumps/show.do?type=versions" width="840" mask="true" target="dialog" title="Dubbo版本应用信息">
							<spring:message code="property.count"/>（${providersCount + consumersCount}）
						</a>&nbsp;
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>