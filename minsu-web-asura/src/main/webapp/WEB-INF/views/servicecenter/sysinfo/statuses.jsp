<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent j-resizeGrid">
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="55" rel="service_providerContainer">
			<thead>
				<tr>
					<th width="80">资源名称</th>
					<th width="100">状态</th>
					<th width="260">信息</th>
					<th width="700">描述</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="status" items="${statusList}">
				<tr>
					<td><spring:message code="status.${status.key}Status"/></td>
					<td><spring:message code="status.${status.value.level}"/></td>
					<td>${status.value.message}</td>
					<td><spring:message code="status.${status.key}StatusDesc"/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>