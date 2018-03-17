<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function dynamicReload(json) {
		var service = '${param.search_service}';
		$('#service_dynamicContainer').loadUrl('servicecenter/service/dynamic.do?search_service=' + service);
		alertMsgByJson(json);
	}
</script>
<div class="pageContent j-resizeGrid">
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<form id="pagerForm" onsubmit="return divSearch(this, 'service_dynamicContainer');" action="servicecenter/service/dynamic.do" method="post">
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							服务名：
							<input type="text" size="50" name="search_service" value="${param.search_service}">
						</td>
						<td><sf:button title="检索" url="servicecenter/service/dynamic.do"></sf:button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="panelBar" style="border-left: 1px solid #B8D0D6; border-right: 1px solid #B8D0D6;">
		<ul class="toolBar">
			<li><sf:link typeClass="iadd" url="servicecenter/service/dynamic/addinit.do" param="service=${param.search_service}" width="720" height="500" target="dialog" title="新增"></sf:link></li>
			<li><sf:link typeClass="idelete" url="servicecenter/service/dynamic/deletebatch.do" rel="rows" callback="dynamicReload" target="selectedTodo" title="批量删除"></sf:link></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="167" rel="service_dynamicContainer">
			<thead>
				<tr>
					<th width="22"><input type="checkbox" group="rows" class="checkboxCtrl"></th>
					<c:if test="${empty param.search_service}"><th width="350">服务名</th></c:if>
					<th width="200">应用名</th>
					<th width="130">机器IP</th>
					<th width="250">服务参数</th>
					<th width="130">状态</th>
					<th width="330">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="dynamic" items="${PAGING_RESULT.rows}" varStatus="status">
					<tr>
						<td><input name="rows" value="${dynamic.id}" type="checkbox"></td>
						<c:if test="${empty param.search_service}"><td>${dynamic.service}&nbsp;</td></c:if>
						<td>${dynamic.application}&nbsp;</td>
						<td>${dynamic.address}&nbsp;</td>
						<td>${URL_DECODE_MAP[dynamic.params]}&nbsp;</td>
						<td>${dynamic.enabled?'已启用':'已禁用'}&nbsp;</td>
						<td>
							<ul class="pageContent_opt">
								<li><sf:link typeClass="" url="servicecenter/service/dynamic/show.do" param="dynamicId=${dynamic.id}" width="840" height="500" target="dialog" title="查看"></sf:link></li>
								<li><sf:link typeClass="" url="servicecenter/service/dynamic/editinit.do" param="dynamicId=${dynamic.id}" width="720" height="500" target="dialog" title="编辑"></sf:link></li>
								<c:if test="${dynamic.enabled}"><li><sf:link typeClass="" url="servicecenter/service/dynamic/disable.do" param="dynamicId=${dynamic.id}" target="ajaxTodo" callback="dynamicReload" title="禁用"></sf:link></li></c:if>
								<c:if test="${not dynamic.enabled}"><li><sf:link typeClass="" url="servicecenter/service/dynamic/enable.do" param="dynamicId=${dynamic.id}" target="ajaxTodo" callback="dynamicReload" title="启用"></sf:link></li></c:if>
								<li><sf:link typeClass="" url="servicecenter/service/dynamic/delete.do" param="dynamicId=${dynamic.id}" target="ajaxTodo" callback="dynamicReload" title="删除"></sf:link></li>
							</ul>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sf:pager rel="service_dynamicContainer" totalCount="${PAGING_RESULT.total}" numPerPage="${searchModel.pageSize}" currentPage="${searchModel.page}"></sf:pager>
	</div>
</div>