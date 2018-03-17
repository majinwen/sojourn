<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<div class="tabsContent">
	<div id="search_logListsContainer">
		<div class="pageContent j-resizeGrid">
			<div class="pageHeader" style="border: 1px #B8D0D6 solid">
				<form id="pagerForm"
					onsubmit="return divSearch(this, 'search_logListsContainer');"
					action="authority/log/logList.do" method="post">
					<input type="hidden" name="page" value="1" /> <input type="hidden"
						name="pageSize" value="${searchModel.pageSize}" />
					<div class="searchBar">
						<table class="searchContent">
							<tr>
								<td>登录账号：<input id="logonNameId" name="search_logonName"
									value="${param.search_logonName }" />
								</td>
								<td>工号：<input id="jobNumId" name="search_jobNum"
									value="${param.search_jobNum }" />
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li>
								<sf:button resId="" title="检索" url="authority/log/logList.do"></sf:button>
								</li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="pageContent"
				style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">

				<table class="table" layoutH="140" rel="search_logListsContainer">
					<thead>
						<tr>
							<th width="200">登录账号</th>
							<th width="200">工号</th>
							<th width="500">操作地址</th>
							<th width="150">是否合法</th>
							<th width="200">日期</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${PAGING_RESULT.rows}"
							varStatus="status">
							<tr target="object">
								<td>&nbsp;&nbsp;${item.logonName}&nbsp;</td>
								<td>&nbsp;&nbsp;${item.jobNum}&nbsp;</td>
								<td>&nbsp;&nbsp;${item.operationUrl}&nbsp;</td>
								<c:if test="${item.isNormalOperation == 0}">
									<td>&nbsp;&nbsp;非法&nbsp;</td>
								</c:if>
								<c:if test="${item.isNormalOperation == 1}">
									<td>&nbsp;&nbsp;合法&nbsp;</td>
								</c:if>
								<td>&nbsp;&nbsp;<sf:date toFormat="yyyy-MM-dd HH:mm:ss" dateLong="${item.logonDate}"></sf:date>&nbsp;</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<sf:pager rel="search_logListsContainer"
					totalCount="${PAGING_RESULT.total}"
					numPerPage="${searchModel.pageSize}"
					currentPage="${searchModel.page}"></sf:pager>
			</div>
		</div>
	</div>
</div>