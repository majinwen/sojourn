<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function ownersReload(json) {
		var service = '${param.search_service}';
		$('#service_ownersContainer').loadUrl('servicecenter/service/owners.do?search_service=' + service);
		alertMsgByJson(json);
	}
</script>
<div class="pageContent j-resizeGrid">
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<form id="pagerForm" onsubmit="return divSearch(this, 'service_ownersContainer');" action="servicecenter/service/owners.do" method="post">
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							服务名：
							<input type="text" size="50" name="search_service" value="${param.search_service}">
						</td>
						<td><sf:button title="检索" url="servicecenter/service/owners.do"></sf:button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="panelBar" style="border-left: 1px solid #B8D0D6; border-right: 1px solid #B8D0D6;">
		<ul class="toolBar">
			<li><sf:link typeClass="iadd" url="servicecenter/service/owners/addinit.do" param="service=${param.search_service}" width="720" height="300" target="dialog" title="新增"></sf:link></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="167" rel="service_ownersContainer">
			<thead>
				<tr>
					<th width="300">用户名</th>
					<c:if test="${empty param.search_service}"><th width="350">服务名</th></c:if>
					<th width="100">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="owner" items="${PAGING_RESULT.rows}" varStatus="status">
					<tr>
						<td>${owner.username}&nbsp;</td>
						<c:if test="${empty param.search_service}"><td>${owner.service}&nbsp;</td></c:if>
						<td>
							<ul class="pageContent_opt">
								<li><sf:link typeClass="" url="servicecenter/service/owners/delete.do" param="username=${owner.username}&service=${param.search_service}" target="ajaxTodo" callback="ownersReload" title="删除"></sf:link></li>
							</ul>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sf:pager rel="service_ownersContainer" totalCount="${PAGING_RESULT.total}" numPerPage="${searchModel.pageSize}" currentPage="${searchModel.page}"></sf:pager>
	</div>
</div>