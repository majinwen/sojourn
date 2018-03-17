<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent j-resizeGrid">
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="55" rel="service_providerContainer">
			<thead>
				<tr>
					<th width="150">属性名</th>
					<th width="700">属性值</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="property" items="${properties}">
				<tr>
					<td><spring:message code="${property.key}"/></td>
					<td>${property.value}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>