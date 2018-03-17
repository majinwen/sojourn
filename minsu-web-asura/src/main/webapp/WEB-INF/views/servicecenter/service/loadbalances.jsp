<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function loadbalancesReload(json) {
		var service = '${param.search_service}';
		$('#service_loadbalancesContainer').loadUrl('servicecenter/service/loadbalances.do?search_service=' + service);
		alertMsgByJson(json);
	}
</script>
<div class="pageContent j-resizeGrid">
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<form id="pagerForm" onsubmit="return divSearch(this, 'service_loadbalancesContainer');" action="servicecenter/service/loadbalances.do" method="post">
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							服务名：
							<input type="text" size="50" name="search_service" value="${param.search_service}">
						</td>
						<td><sf:button title="检索" url="servicecenter/service/loadbalances.do"></sf:button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="panelBar" style="border-left: 1px solid #B8D0D6; border-right: 1px solid #B8D0D6;">
		<ul class="toolBar">
			<li><sf:link typeClass="iadd" url="servicecenter/service/loadbalances/addinit.do" param="service=${param.search_service}" width="720" height="300" target="dialog" title="新增"></sf:link></li>
			<li><sf:link typeClass="idelete" url="servicecenter/service/loadbalances/deletebatch.do" rel="rows" callback="loadbalancesReload" target="selectedTodo" title="批量删除"></sf:link></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="167" rel="service_loadbalancesContainer">
			<thead>
				<tr>
					<th width="22"><input type="checkbox" group="rows" class="checkboxCtrl"></th>
					<th width="200">服务方法</th>
					<c:if test="${empty param.search_service}"><th width="350">服务名</th></c:if>
					<th width="130">负载均衡策略</th>
					<th width="330">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="loadbalance" items="${PAGING_RESULT.rows}" varStatus="status">
					<tr>
						<td><input name="rows" value="${loadbalance.id}" type="checkbox"></td>
						<td>${loadbalance.method}&nbsp;</td>
						<c:if test="${empty param.search_service}"><td>${loadbalance.service}&nbsp;</td></c:if>
						<td><spring:message code="${loadbalance.strategy}"/>&nbsp;</td>
						<td>
							<ul class="pageContent_opt">
								<li><sf:link typeClass="" url="servicecenter/service/loadbalances/editinit.do" param="loadbalancesId=${loadbalance.id}" width="720" height="300" target="dialog" title="编辑"></sf:link></li>
								<li><sf:link typeClass="" url="servicecenter/service/loadbalances/delete.do" param="loadbalancesId=${loadbalance.id}" target="ajaxTodo" callback="loadbalancesReload" title="删除"></sf:link></li>
							</ul>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sf:pager rel="service_loadbalancesContainer" totalCount="${PAGING_RESULT.total}" numPerPage="${searchModel.pageSize}" currentPage="${searchModel.page}"></sf:pager>
	</div>
</div>