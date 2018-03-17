<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function applicationReload(json) {
		var service = '${param.search_service}';
		$('#service_applicationContainer').loadUrl('servicecenter/service/application.do?search_service=' + service);
		alertMsgByJson(json);
	}
</script>
<div class="pageContent j-resizeGrid">
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<form id="pagerForm" onsubmit="return divSearch(this, 'service_applicationContainer');" action="servicecenter/service/application.do" method="post">
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							服务名：
							<input type="text" size="50" name="search_service" value="${param.search_service}">
						</td>
						<td><sf:button title="检索" url="servicecenter/service/application.do"></sf:button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="panelBar" style="border-left: 1px solid #B8D0D6; border-right: 1px solid #B8D0D6;">
		<ul class="toolBar">
			<li><sf:link typeClass="ishield" url="servicecenter/service/application/shieldbatch.do" param="service=${param.search_service}" rel="rows" callback="applicationReload" target="selectedTodo" title="批量屏蔽"></sf:link></li>
			<li><sf:link typeClass="itolerant" url="servicecenter/service/application/tolerantbatch.do" param="service=${param.search_service}" rel="rows" callback="applicationReload" target="selectedTodo" title="批量容错"></sf:link></li>
			<li><sf:link typeClass="ienable" url="servicecenter/service/application/recoverbatch.do" param="service=${param.search_service}" rel="rows" callback="applicationReload" target="selectedTodo" title="批量恢复"></sf:link></li>
			<li><sf:link typeClass="ishield" url="servicecenter/service/application/shieldall.do" param="service=${param.search_service}" target="ajaxTodo" callback="applicationReload" title="缺省屏蔽"></sf:link></li>
			<li><sf:link typeClass="itolerant" url="servicecenter/service/application/tolerantall.do" param="service=${param.search_service}" target="ajaxTodo" callback="applicationReload" title="缺省容错"></sf:link></li>
			<li><sf:link typeClass="ienable" url="servicecenter/service/application/recoverall.do" param="service=${param.search_service}" target="ajaxTodo" callback="applicationReload" title="缺省恢复"></sf:link></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="167" rel="service_applicationContainer">
			<thead>
				<tr>
					<th width="22"><input type="checkbox" group="rows" class="checkboxCtrl"></th>
					<th width="230">应用名</th>
					<th width="130">角色</th>
					<th width="130">降级</th>
					<th width="330">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="application" items="${PAGING_RESULT.rows}" varStatus="status">
					<tr>
						<td><input name="rows" value="${application.name}" type="checkbox"></td>
						<td>${application.name}&nbsp;</td>
						<td>${application.status}&nbsp;</td>
						<td><c:if test="${application.comsumer}">${application.mockType}</c:if>&nbsp;</td>
						<td>
							<c:if test="${application.comsumer}">
								<ul class="pageContent_opt">
									<c:if test="${application.mockType eq '已屏蔽'}"><li><sf:link typeClass="" url="servicecenter/service/application/recover.do" param="service=${param.search_service}&appName=${application.name}" target="ajaxTodo" callback="applicationReload" title="恢复"></sf:link></li></c:if>
									<c:if test="${application.mockType ne '已屏蔽'}"><li><sf:link typeClass="" url="servicecenter/service/application/shield.do" param="service=${param.search_service}&appName=${application.name}" target="ajaxTodo" callback="applicationReload" title="屏蔽"></sf:link></li></c:if>
									<c:if test="${application.mockType eq '已容错'}"><li><sf:link typeClass="" url="servicecenter/service/application/recover.do" param="service=${param.search_service}&appName=${application.name}" target="ajaxTodo" callback="applicationReload" title="恢复"></sf:link></li></c:if>
									<c:if test="${application.mockType ne '已容错'}"><li><sf:link typeClass="" url="servicecenter/service/application/tolerant.do" param="service=${param.search_service}&appName=${application.name}" target="ajaxTodo" callback="applicationReload" title="容错"></sf:link></li></c:if>
								</ul>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sf:pager rel="service_applicationContainer" totalCount="${PAGING_RESULT.total}" numPerPage="${searchModel.pageSize}" currentPage="${searchModel.page}"></sf:pager>
	</div>
</div>