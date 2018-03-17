<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function addrProviderReload(json) {
		$('#address_providerContainer').loadUrl('servicecenter/address/provider.do?search_address=${param.search_address}');
		alertMsgByJson(json);
	}
</script>
<div class="pageContent j-resizeGrid">
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<form id="pagerForm" onsubmit="return divSearch(this, 'address_providerContainer');" action="servicecenter/address/provider.do" method="post">
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							机器地址：
							<input type="text" size="50" name="search_address" value="${param.search_address}">
						</td>
						<td><sf:button title="检索" url="servicecenter/address/provider.do"></sf:button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="panelBar" style="border-left: 1px solid #B8D0D6; border-right: 1px solid #B8D0D6;">
		<ul class="toolBar">
			<li><sf:link typeClass="iadd" url="servicecenter/address/provider/addinit.do" param="address=${param.search_address}" width="840" height="300" target="dialog" title="添加"></sf:link></li>
			<li><sf:link typeClass="idoubling" url="servicecenter/service/provider/doublingbatch.do" rel="rows" callback="addrProviderReload" target="selectedTodo" title="批量倍权"></sf:link></li>
			<li><sf:link typeClass="ihalving" url="servicecenter/service/provider/halvingbatch.do" rel="rows" callback="addrProviderReload" target="selectedTodo" title="批量半权"></sf:link></li>
			<li><sf:link typeClass="idisable" url="servicecenter/service/provider/disablebatch.do" rel="rows" callback="addrProviderReload" target="selectedTodo" title="批量禁用"></sf:link></li>
			<li><sf:link typeClass="ienable" url="servicecenter/service/provider/enablebatch.do" rel="rows" callback="addrProviderReload" target="selectedTodo" title="批量启用"></sf:link></li>
			<li><sf:link typeClass="idelete" url="servicecenter/service/provider/deletebatch.do" rel="rows" callback="addrProviderReload" target="selectedTodo" title="批量删除"></sf:link></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="167" rel="address_providerContainer">
			<thead>
				<tr>
					<th width="22"><input type="checkbox" group="rows" class="checkboxCtrl"></th>
					<th width="300">服务名</th>
					<th width="130">权重</th>
					<th width="130">类型</th>
					<th width="130">状态</th>
					<th width="130">检查</th>
					<th width="250">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="provider" items="${PAGING_RESULT.rows}" varStatus="status">
					<tr>
						<td><input name="rows" value="${provider.id}" type="checkbox"></td>
						<td><a href="servicecenter/service/provider/show.do?providerId=${provider.id}" width="840" height="300" mask="true" max="true" target="dialog"><span style="color:#00f;">${provider.service}</span></a>&nbsp;</td>
						<td>${provider.weight}&nbsp;</td>
						<td>${provider.dynamic ? '动态':'静态'}&nbsp;</td>
						<td>${provider.enabled ? '已启用':'已禁止'}&nbsp;</td>
						<td>正常&nbsp;</td>
						<td>
							<ul class="pageContent_opt">
								<li><sf:link typeClass="" url="servicecenter/address/provider/editinit.do" param="providerId=${provider.id}" width="840" height="300" target="dialog" title="编辑"></sf:link></li>
								<li><sf:link typeClass="" url="servicecenter/address/provider/addinit.do" param="providerId=${provider.id}" width="840" height="300" target="dialog" title="复制"></sf:link></li>
								<li><sf:link typeClass="" url="servicecenter/service/provider/doubling.do" param="providerId=${provider.id}" target="ajaxTodo" callback="addrProviderReload" title="倍权"></sf:link></li>
								<li><sf:link typeClass="" url="servicecenter/service/provider/halving.do" param="providerId=${provider.id}" target="ajaxTodo" callback="addrProviderReload" title="半权"></sf:link></li>
								<c:if test="${provider.enabled}"><li><sf:link typeClass="" url="servicecenter/service/provider/disable.do" param="providerId=${provider.id}" target="ajaxTodo" callback="addrProviderReload" title="禁用"></sf:link></li></c:if>
								<c:if test="${not provider.enabled}"><li><sf:link typeClass="" url="servicecenter/service/provider/enable.do" param="providerId=${provider.id}" target="ajaxTodo" callback="addrProviderReload" title="启用"></sf:link></li></c:if>
								<c:if test="${not provider.dynamic}"><li><sf:link typeClass="" url="servicecenter/service/provider/delete.do" param="providerId=${provider.id}" target="ajaxTodo" callback="addrProviderReload" title="删除"></sf:link></li></c:if>
							</ul>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sf:pager rel="service_providerContainer" totalCount="${PAGING_RESULT.total}" numPerPage="${searchModel.pageSize}" currentPage="${searchModel.page}"></sf:pager>
	</div>
</div>