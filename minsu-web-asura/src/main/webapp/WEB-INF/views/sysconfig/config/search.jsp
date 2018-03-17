<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function sysconfigReload(json) {
		$('#sysconfig_configContainer').loadUrl('sysconfig/config/search.do?resId=${resId }');
		alertMsgByJson(json);
	}
</script>
<div class="pageContent j-resizeGrid">
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<form id="pagerForm" onsubmit="return divSearch(this, 'sysconfig_configContainer');" action="sysconfig/config/search.do?resId=${resId }" method="post">
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							类型：
							<input type="text" size="20" name="search_type" value="${param.search_type}">
						</td>
						<td>
							CODE：
							<input type="text" size="20" name="search_code" value="${param.search_code}">
						</td>
						<td><sf:button resId="${resId }" title="检索" url="sysconfig/config/search.do"></sf:button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="panelBar" style="border-left: 1px solid #B8D0D6; border-right: 1px solid #B8D0D6;">
		<ul class="toolBar">
			<li><sf:link resId="${resId }" typeClass="iadd" url="sysconfig/config/addinit.do" param="" width="550" height="330" target="dialog" title="添加"></sf:link></li>
			<li><sf:link resId="${resId }" typeClass="ienable" url="sysconfig/config/rebuild.do" param="" callback="sysconfigReload" target="ajaxTodo" title="重建全部系统配置数据"></sf:link></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="140" rel="sysconfig_configContainer">
			<thead>
				<tr>
					<th width="50">类型</th>
					<th width="100">CODE</th>
					<th width="200">名称</th>
					<th width="200">值</th>
					<th width="200">ZK值</th>
					<th width="200">描述</th>
					<th width="200">更新时间</th>
					<th width="200">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="sysconfig" items="${PAGING_RESULT.rows}" varStatus="status">
					<tr>
						<td>${sysconfig.type}&nbsp;</td>
						<td>${sysconfig.code}&nbsp;</td>
						<td>${sysconfig.name}&nbsp;</td>
						<td>${sysconfig.value}&nbsp;</td>
						<td>${sysconfig.zkValue}&nbsp;</td>
						<td>${sysconfig.notes}&nbsp;</td>
						<td>${sysconfig.updateDate}&nbsp;</td>
						<td>
							<ul class="pageContent_opt">
								<li><sf:link typeClass="" url="sysconfig/config/editinit.do" param="type=${sysconfig.type}&code=${sysconfig.code}&name=${sysconfig.name}&value=${sysconfig.value}&notes=${sysconfig.notes}" width="550" height="330" target="dialog" title="编辑"></sf:link></li>
								<li><sf:link typeClass="" url="sysconfig/config/delete.do" param="type=${sysconfig.type}&code=${sysconfig.code}" target="ajaxTodo" callback="sysconfigReload" title="删除"></sf:link></li>
							</ul>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sf:pager rel="sysconfig_configContainer"
					totalCount="${PAGING_RESULT.total}"
					numPerPage="${searchModel.pageSize}"
					currentPage="${searchModel.page}"></sf:pager>
	</div>
</div>