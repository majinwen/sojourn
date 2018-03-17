<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function applicationServiceReload(json) {
		var application = '${param.search_application}';
		$('#application_serviceContainer').loadUrl('servicecenter/application/service.do?search_application=' + application);
		alertMsgByJson(json);
	}
</script>
<div class="pageContent j-resizeGrid">
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<form id="pagerForm" onsubmit="return divSearch(this, 'application_serviceContainer');" action="servicecenter/application/service.do" method="post">
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							应用名：
							<input type="text" size="35" name="search_application" value="${param.search_application}">
						</td>
						<td><sf:button title="检索" url="servicecenter/application/service.do"></sf:button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="panelBar" style="border-left: 1px solid #B8D0D6; border-right: 1px solid #B8D0D6;">
		<ul class="toolBar">
			<li><sf:link typeClass="ishield" url="servicecenter/service/application/shieldbatch.do" rel="rows" callback="applicationServiceReload" target="selectedTodo" title="批量屏蔽"></sf:link></li>
			<li><sf:link typeClass="itolerant" url="servicecenter/service/application/tolerantbatch.do" rel="rows" callback="applicationServiceReload" target="selectedTodo" title="批量容错"></sf:link></li>
			<li><sf:link typeClass="ienable" url="servicecenter/service/application/recoverbatch.do" rel="rows" callback="applicationServiceReload" target="selectedTodo" title="批量恢复"></sf:link></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="140" rel="application_serviceContainer">
			<thead>
				<tr>
					<th width="22"><input type="checkbox" group="rows" class="checkboxCtrl"></th>
					<th width="550">服务名</th>
					<th width="130">状态</th>
					<th width="130">降级</th>
					<th width="130">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="service" items="${PAGING_RESULT.rows}" varStatus="status">
					<tr>
						<td><input name="rows" value="${param.search_application},${service.name}" type="checkbox"></td>
						<td><a style="color:#00F;" title="服务" rel="service" target="navTab" href="servicecenter/service/init.do?search_service=${service.name}">${service.name}&nbsp;</a></td>
						<td>${service.description}&nbsp;</td>
						<td><c:if test="${service.description eq '消费者'}">${service.mock}</c:if>&nbsp;</td>
						<td>
							<c:if test="${service.description eq '消费者'}">
							<ul class="pageContent_opt">
								<c:if test="${service.mock eq '已屏蔽'}"><li><sf:link typeClass="" url="servicecenter/service/application/recover.do" param="service=${service.name}&application=${param.search_application}" target="ajaxTodo" callback="applicationServiceReload" title="恢复"></sf:link></li></c:if>
								<c:if test="${service.mock ne '已屏蔽'}"><li><sf:link typeClass="" url="servicecenter/service/application/shield.do" param="service=${service.name}&application=${param.search_application}" target="ajaxTodo" callback="applicationServiceReload" title="屏蔽"></sf:link></li></c:if>
								<c:if test="${service.mock eq '已容错'}"><li><sf:link typeClass="" url="servicecenter/service/application/recover.do" param="service=${service.name}&application=${param.search_application}" target="ajaxTodo" callback="applicationServiceReload" title="恢复"></sf:link></li></c:if>
								<c:if test="${service.mock ne '已容错'}"><li><sf:link typeClass="" url="servicecenter/service/application/tolerant.do" param="service=${service.name}&application=${param.search_application}" target="ajaxTodo" callback="applicationServiceReload" title="容错"></sf:link></li></c:if>
							</ul>
							</c:if>
							&nbsp;
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sf:pager rel="search_serviceContainer" totalCount="${PAGING_RESULT.total}" numPerPage="${searchModel.pageSize}" currentPage="${searchModel.page}"></sf:pager>
	</div>
</div>