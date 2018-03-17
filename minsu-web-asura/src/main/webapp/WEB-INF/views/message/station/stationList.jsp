<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<div class="tabsContent">
	<div id="search_stationListsContainer">
		<div class="pageContent j-resizeGrid">
		    <div class="pageHeader" style="border: 1px #B8D0D6 solid">
				<form id="pagerForm"
					onsubmit="return divSearch(this, 'search_stationListsContainer');"
					action="message/station/stationList.do?resId=${resId }" method="post">
					<input type="hidden" name="page" value="1" /> <input type="hidden"
						name="pageSize" value="${searchModel.pageSize}" />
					<div class="searchBar">
						<table class="searchContent">
							<tr>
								<td>站内信状态：</td>
								<td><select class="combox" name="search_stationStatus">
										<option value="">&nbsp;&nbsp;&nbsp;全部&nbsp;&nbsp;&nbsp;</option>
										<c:forEach items="${statusMap}" var="st">
											<c:if test="${param.search_stationStatus == st.key }">
												<option value="${st.key }" selected>${st.value}</option>
											</c:if>
											<c:if test="${param.search_stationStatus != st.key }">
												<option value="${st.key }">${st.value}</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>接受者uid：<input id="touidId" name="search_touid"
									value="${param.search_touid }" />
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li>
								<sf:button resId="${resId }" title="检索" url="message/station/stationList.do"></sf:button>
								</li>
							</ul>
						</div>
					</div>
				</form>
			</div>
		
		
			<div class="pageContent"
				style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
				
				<div class="panelBar">
					<ul class="toolBar">
						<li>
							<sf:link resId="${resId }" url="message/station/repeatSend.do" param="" promptMessage="确实要重发吗?" target="selectedTodo" rel="ids" typeClass="delete" title="失败重发"></sf:link>
						</li>
					</ul>
				</div>

				<table class="table" layoutH="140" rel="search_stationListsContainer">
					<thead>
						<tr>
							<th width="250">发送人uid</th>
							<th width="300">接受人uid</th>
							<th width="150">内容</th>
							<th width="150">来源</th>
							<th width="150">状态</th>
							<th width="150">重发次数</th>
							<th width="150">发送时间</th>
							<th width="150">添加时间</th>
						</tr>
					</thead>
					

					<tbody>
						<c:forEach var="item" items="${PAGING_RESULT.rows}"
							varStatus="status">
							<tr>
								<td>${item.uid}&nbsp;</td>
								<td>${item.touid}&nbsp;</td>
								<td>${item.msg}&nbsp;</td>
								<td>${item.source}&nbsp;</td>
								<td>${item.stationStatusName}&nbsp;</td>
								<td>${item.retryNum}&nbsp;</td>
								<td>${item.sendDate}&nbsp;</td>
								<td>${item.addDate}&nbsp;</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<sf:pager rel="search_stationListsContainer"
					totalCount="${PAGING_RESULT.total}"
					numPerPage="${searchModel.pageSize}"
					currentPage="${searchModel.page}"></sf:pager>
				
			</div>
		</div>
	</div>
</div>