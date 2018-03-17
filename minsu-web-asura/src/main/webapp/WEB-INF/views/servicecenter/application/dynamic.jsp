<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function applicaitonDynamicReload(json) {
		var application = '${param.search_application}';
		$('#application_dynamicContainer').loadUrl('servicecenter/application/dynamic.do?search_application=' + application);
		alertMsgByJson(json);
	}
</script>
<div class="pageContent j-resizeGrid">
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<form id="pagerForm" onsubmit="return divSearch(this, 'application_dynamicContainer');" action="servicecenter/application/dynamic.do" method="post">
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							应用名：
							<input type="text" size="35" name="search_application" value="${param.search_application}">
						</td>
						<td><sf:button title="检索" url="servicecenter/application/dynamic.do"></sf:button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="panelBar" style="border-left: 1px solid #B8D0D6; border-right: 1px solid #B8D0D6;">
		<ul class="toolBar">
			<li><a class="iadd" href="servicecenter/application/dynamic/addinit.do?application=${param.search_application}" width="720" height="500" mask="true" target="dialog" title="添加动态配置"><span>新增</span></a></li>
			<li><a class="idelete" href="servicecenter/service/dynamic/deletebatch.do" rel="rows" target="selectedTodo" callback="applicaitonDynamicReload" title="确定要删除这些服务吗?"><span>批量删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="167" rel="service_dynamicContainer">
			<thead>
				<tr>
					<th width="22"><input type="checkbox" group="rows" class="checkboxCtrl"></th>
					<th width="300">服务名</th>
					<th width="180">应用名</th>
					<th width="130">机器IP</th>
					<th width="300">服务参数</th>
					<th width="130">状态</th>
					<th width="200">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="dynamic" items="${PAGING_RESULT.rows}" varStatus="status">
					<tr>
						<td><input name="rows" value="${dynamic.id}" type="checkbox"></td>
						<td><a style="color:#00F;" title="服务" rel="service" target="navTab" href="servicecenter/service/init.do?search_service=${dynamic.service}">${dynamic.service}</a>&nbsp;</td>
						<td>${dynamic.application}&nbsp;</td>
						<td>${dynamic.address}&nbsp;</td>
						<td>${URL_DECODE_MAP[dynamic.params]}&nbsp;</td>
						<td>${dynamic.enabled?'已启用':'已禁用'}&nbsp;</td>
						<td>
							<ul class="pageContent_opt">
								<li><a href="servicecenter/service/dynamic/show.do?dynamicId=${dynamic.id}" width="840" height="500" mask="true" target="dialog"><span>查看</span></a></li>
								<li><a href="servicecenter/application/dynamic/editinit.do?dynamicId=${dynamic.id}" width="720" height="500" mask="true" target="dialog"><span>编辑</span></a></li>
								<c:if test="${dynamic.enabled}"><li><a href="servicecenter/service/dynamic/disable.do?dynamicId=${dynamic.id}" target="ajaxTodo" title="确认禁用吗？" callback="applicaitonDynamicReload"><span>禁用</span></a></li></c:if>
								<c:if test="${not dynamic.enabled}"><li><a href="servicecenter/service/dynamic/enable.do?dynamicId=${dynamic.id}" target="ajaxTodo" title="确认启用吗？" callback="applicaitonDynamicReload"><span>启用</span></a></li></c:if>
								<li><a href="servicecenter/service/dynamic/delete.do?dynamicId=${dynamic.id}" target="ajaxTodo" title="确认删除吗？" callback="applicaitonDynamicReload"><span>删除</span></a></li>
							</ul>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sf:pager rel="service_dynamicContainer" totalCount="${PAGING_RESULT.total}" numPerPage="${searchModel.pageSize}" currentPage="${searchModel.page}"></sf:pager>
	</div>
</div>