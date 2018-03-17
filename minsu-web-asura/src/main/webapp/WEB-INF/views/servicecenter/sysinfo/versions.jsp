<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent j-resizeGrid">
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="55" rel="service_providerContainer">
			<thead>
				<tr>
					<th width="500">Dubbo版本</th>
					<th width="100">应用</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="version" items="${PAGING_RESULT.rows}" varStatus="status">
					<tr>
						<td><a style="color:#00f;" href="servicecenter/sysinfo/versions/show.do?version=${version.name}" width="840" mask="true" target="dialog" title="Dubbo版本应用信息">${version.name}</a></td>
						<td>${version.appCount}&nbsp;</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sf:pager rel="service_accessesContainer" totalCount="${PAGING_RESULT.total}" numPerPage="${searchModel.pageSize}" currentPage="${searchModel.page}"></sf:pager>
	</div>
</div>