<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent j-resizeGrid">
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<form id="pagerForm" onsubmit="return divSearch(this, 'search_applicationContainer');" action="servicecenter/search/application.do" method="post">
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							关键词：
							<input type="text" size="50" name="search_keyword" value="${param.search_keyword}">
						</td>
						<td><sf:button title="检索" url="servicecenter/search/application.do"></sf:button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		<table class="table" layoutH="140" rel="search_applicationContainer">
			<thead>
				<tr>
					<th width="550">应用名</th>
					<th width="130">角色</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="application" items="${PAGING_RESULT.rows}" varStatus="status">
					<tr>
						<td><a style="color:#00F;" title="应用" rel="application" target="navTab" href="servicecenter/application/init.do?search_application=${application.name}">${application.name}&nbsp;</a>&nbsp;</td>
						<td>${application.description}&nbsp;</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<sf:pager rel="search_applicationContainer" totalCount="${PAGING_RESULT.total}" numPerPage="${searchModel.pageSize}" currentPage="${searchModel.page}"></sf:pager>
	</div>
</div>