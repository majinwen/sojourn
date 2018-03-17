<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function routesReload(json) {
		var service = '${param.search_service}';
		$('#service_routesContainer').loadUrl('servicecenter/service/routes.do?search_service=' + service);
		alertMsgByJson(json);
	}
</script>
<div class="pageContent j-resizeGrid">
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<form id="pagerForm" onsubmit="return divSearch(this, 'service_routesContainer');" action="servicecenter/service/routes.do" method="post">
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							服务名：
							<input type="text" size="50" name="search_service" value="${param.search_service}">
						</td>
						<td><sf:button title="检索" url="servicecenter/service/routes.do"></sf:button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="panelBar" style="border-left: 1px solid #B8D0D6; border-right: 1px solid #B8D0D6;">
		<ul class="toolBar">
			<li><sf:link typeClass="iadd" url="servicecenter/service/routes/addinit.do" param="service=${param.search_service}" width="720" height="500" target="dialog" title="添加"></sf:link></li>
			<li><sf:link typeClass="idelete" url="servicecenter/service/routes/deletebatch.do" rel="rows" callback="routesReload" target="selectedTodo" title="批量删除"></sf:link></li>
			<li><sf:link typeClass="idisable" url="servicecenter/service/routes/disablebatch.do" rel="rows" callback="routesReload" target="selectedTodo" title="批量禁用"></sf:link></li>
			<li><sf:link typeClass="ienable" url="servicecenter/service/routes/enablebatch.do" rel="rows" callback="routesReload" target="selectedTodo" title="批量启用"></sf:link></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="167" rel="service_routesContainer">
			<thead>
				<tr>
					<th width="22"><input type="checkbox" group="rows" class="checkboxCtrl"></th>
					<th width="230">路由名称</th>
					<c:if test="${empty param.search_service}"><th width="350">服务名</th></c:if>
					<th width="130">优先级</th>
					<th width="130">状态</th>
					<th width="280">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="routes" items="${PAGING_RESULT.rows}" varStatus="status">
					<tr>
						<td><input name="rows" value="${routes.id}" type="checkbox"></td>
						<td><a href="servicecenter/service/routes/show.do?routesId=${routes.id}" width="600" height="350" mask="true" target="dialog"><span style="color:#00f;">${routes.name}</span></a>&nbsp;</td>
						<c:if test="${empty param.search_service}"><td>${routes.service}&nbsp;</td></c:if>
						<td>${routes.priority}&nbsp;</td>
						<td>${routes.enabled ? '已启用':'已禁用'}&nbsp;</td>
						<td>
							<ul class="pageContent_opt">
								<li><sf:link typeClass="" url="servicecenter/service/routes/select.do" param="routesId=${routes.id}" width="840" height="500" target="dialog" title="预览"></sf:link></li>
								<li><sf:link typeClass="" url="servicecenter/service/routes/editinit.do" param="routesId=${routes.id}" width="720" height="500" target="dialog" title="编辑"></sf:link></li>
								<li><sf:link typeClass="" url="servicecenter/service/routes/addinit.do" param="routesId=${routes.id}" width="720" height="500" target="dialog" title="复制"></sf:link></li>
								<c:if test="${routes.enabled}"><li><sf:link typeClass="" url="servicecenter/service/routes/disable.do" param="routesId=${routes.id}" target="ajaxTodo" callback="routesReload" title="禁用"></sf:link></li></c:if>
								<c:if test="${not routes.enabled}"><li><sf:link typeClass="" url="servicecenter/service/routes/enable.do" param="routesId=${routes.id}" target="ajaxTodo" callback="routesReload" title="启用"></sf:link></li></c:if>
								<li><sf:link typeClass="" url="servicecenter/service/routes/delete.do" param="routesId=${routes.id}" target="ajaxTodo" callback="routesReload" title="删除"></sf:link></li>
							</ul>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sf:pager rel="service_routesContainer" totalCount="${PAGING_RESULT.total}" numPerPage="${searchModel.pageSize}" currentPage="${searchModel.page}"></sf:pager>
	</div>
</div>