<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function consumerReload(json) {
		var service = '${param.search_service}';
		$('#service_consumerContainer').loadUrl('servicecenter/service/consumer.do?search_service=' + service);
		alertMsgByJson(json);
	}
</script>
<div class="pageContent j-resizeGrid">
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<form id="pagerForm" onsubmit="return divSearch(this, 'service_consumerContainer');" action="servicecenter/service/consumer.do" method="post">
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							服务名：
							<input type="text" size="50" name="search_service" value="${param.search_service}">
						</td>
						<td><sf:button title="检索" url="servicecenter/service/consumer.do"></sf:button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="panelBar" style="border-left: 1px solid #B8D0D6; border-right: 1px solid #B8D0D6;">
		<ul class="toolBar">
			<li><sf:link typeClass="idisable" url="servicecenter/service/consumer/forbidbatch.do" rel="rows" callback="consumerReload" target="selectedTodo" title="批量禁止"></sf:link></li>
			<li><sf:link typeClass="ienable" url="servicecenter/service/consumer/allowbatch.do" rel="rows" callback="consumerReload" target="selectedTodo" title="批量允许"></sf:link></li>
			<li><sf:link typeClass="idisable" url="servicecenter/service/consumer/forbidonly.do" rel="rows" callback="consumerReload" target="selectedTodo" title="只禁止"></sf:link></li>
			<li><sf:link typeClass="ienable" url="servicecenter/service/consumer/allowonly.do" rel="rows" callback="consumerReload" target="selectedTodo" title="只允许"></sf:link></li>
			<li><sf:link typeClass="ishield" url="servicecenter/service/consumer/shieldbatch.do" rel="rows" callback="consumerReload" target="selectedTodo" title="批量屏蔽"></sf:link></li>
			<li><sf:link typeClass="itolerant" url="servicecenter/service/consumer/tolerantbatch.do" rel="rows" callback="consumerReload" target="selectedTodo" title="批量容错"></sf:link></li>
			<li><sf:link typeClass="ienable" url="servicecenter/service/consumer/recoverbatch.do" rel="rows" callback="consumerReload" target="selectedTodo" title="批量恢复"></sf:link></li>
			<li><sf:link typeClass="ishield" url="servicecenter/service/consumer/shieldall.do" param="service=${param.search_service}" target="ajaxTodo" callback="consumerReload" title="缺省屏蔽"></sf:link></li>
			<li><sf:link typeClass="itolerant" url="servicecenter/service/consumer/tolerantall.do" param="service=${param.search_service}" target="ajaxTodo" callback="consumerReload" title="缺省容错"></sf:link></li>
			<li><sf:link typeClass="ienable" url="servicecenter/service/consumer/recoverall.do" param="service=${param.search_service}" target="ajaxTodo" callback="consumerReload" title="批量删除"></sf:link></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="167" rel="service_consumerContainer">
			<thead>
				<tr>
					<th width="22"><input type="checkbox" group="rows" class="checkboxCtrl"></th>
					<th width="150">机器IP</th>
					<c:if test="${empty param.search_service}"><th width="350">服务名</th></c:if>
					<th width="230">应用名</th>
					<th width="100">访问</th>
					<th width="100">降级</th>
					<th width="100">路由</th>
					<th width="100">通知</th>
					<th width="280">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="consumer" items="${PAGING_RESULT.rows}" varStatus="status">
					<tr>
						<td><input name="rows" value="${consumer.id}" type="checkbox"></td>
						<td><a href="servicecenter/service/consumer/show.do?consumerId=${consumer.id}" width="840" mask="true" max="true" target="dialog"><span style="color:#00f;">${consumer.address}</span></a>&nbsp;</td>
						<c:if test="${empty param.search_service}"><td>${consumer.service}&nbsp;</td></c:if>
						<td>${consumer.application}&nbsp;</td>
						<td>${consumer.allowed ? '已允许' : '已禁止'}&nbsp;</td>
						<td>${consumer.mockType}&nbsp;</td>
						<td><c:if test="${consumer.routeCount > 0}">已路由（${consumer.routeCount}）</c:if><c:if test="${consumer.routeCount eq 0}">未路由</c:if>&nbsp;</td>
						<td><c:if test="${consumer.providerCount > 0}">
							<a href="servicecenter/service/consumer/notify.do?consumerId=${consumer.id}" width="840" mask="true" max="true" target="dialog"><span style="color:#00f;">
							已通知（${consumer.providerCount}）
							</span></a>
						</c:if><c:if test="${consumer.providerCount eq 0}">没有提供者</c:if>&nbsp;</td>
						<td>
							<ul class="pageContent_opt">
								<li><sf:link typeClass="" url="servicecenter/service/consumer/editinit.do" param="consumerId=${consumer.id}" width="840" height="350" target="dialog" title="编辑"></sf:link></li>
								<c:if test="${consumer.allowed}"><li><sf:link typeClass="" url="servicecenter/service/consumer/forbid.do" param="consumerId=${consumer.id}" target="ajaxTodo" callback="consumerReload" title="禁止"></sf:link></li></c:if>
								<c:if test="${not consumer.allowed}"><li><sf:link typeClass="" url="servicecenter/service/consumer/allow.do" param="consumerId=${consumer.id}" target="ajaxTodo" callback="consumerReload" title="允许"></sf:link></li></c:if>
								<c:if test="${consumer.mockType eq '已屏蔽'}"><li><sf:link typeClass="" url="servicecenter/service/consumer/recover.do" param="consumerId=${consumer.id}" target="ajaxTodo" callback="consumerReload" title="恢复"></sf:link></li></c:if>
								<c:if test="${consumer.mockType ne '已屏蔽'}"><li><sf:link typeClass="" url="servicecenter/service/consumer/shield.do" param="consumerId=${consumer.id}" target="ajaxTodo" callback="consumerReload" title="屏蔽"></sf:link></li></c:if>
								<c:if test="${consumer.mockType eq '已容错'}"><li><sf:link typeClass="" url="servicecenter/service/consumer/recover.do" param="consumerId=${consumer.id}" target="ajaxTodo" callback="consumerReload" title="恢复"></sf:link></li></c:if>
								<c:if test="${consumer.mockType ne '已容错'}"><li><sf:link typeClass="" url="servicecenter/service/consumer/tolerant.do" param="consumerId=${consumer.id}" target="ajaxTodo" callback="consumerReload" title="容错"></sf:link></li></c:if>
							</ul>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sf:pager rel="service_consumerContainer" totalCount="${PAGING_RESULT.total}" numPerPage="${searchModel.pageSize}" currentPage="${searchModel.page}"></sf:pager>
	</div>
</div>