<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<div class="tabsContent">
	<div id="search_mailListsContainer">
		<div class="pageContent j-resizeGrid">
		    <div class="pageHeader" style="border: 1px #B8D0D6 solid">
				<form id="pagerForm"
					onsubmit="return divSearch(this, 'search_mailListsContainer');"
					action="message/mail/mailList.do?resId=${resId }" method="post">
					<input type="hidden" name="page" value="1" /> <input type="hidden"
						name="pageSize" value="${searchModel.pageSize}" />
					<div class="searchBar">
						<table class="searchContent">
							<tr>
								<td>邮件类型：</td>
								<td><select class="combox" name="search_mtype">
										<option value="">&nbsp;&nbsp;&nbsp;全部&nbsp;&nbsp;&nbsp;</option>
										<c:forEach items="${mtypeMap}" var="type">
											<c:if test="${param.search_mtype == type.key }">
												<option value="${type.key }" selected>${type.value}</option>
											</c:if>
											<c:if test="${param.search_mtype != type.key }">
												<option value="${type.key }">${type.value}</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>邮件状态：</td>
								<td><select class="combox" name="search_mailStatus">
										<option value="">&nbsp;&nbsp;&nbsp;全部&nbsp;&nbsp;&nbsp;</option>
										<c:forEach items="${statusMap}" var="st">
											<c:if test="${param.search_mailStatus == st.key }">
												<option value="${st.key }" selected>${st.value}</option>
											</c:if>
											<c:if test="${param.search_mailStatus != st.key }">
												<option value="${st.key }">${st.value}</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>邮箱地址：<input id="mailAddrId" name="search_mailAddr"
									value="${param.search_mailAddr }" />
								</td>
								<td>邮件主题：<input id="subjectId" name="search_subject"
									value="${param.search_subject }" />
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li>
								<sf:button resId="${resId }" title="检索" url="message/mail/mailList.do"></sf:button>
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
							<sf:link resId="${resId }" url="message/mail/repeatSend.do" param="" promptMessage="确实要重发吗?" target="selectedTodo" rel="ids" typeClass="delete" title="失败重发"></sf:link>
						</li>
					</ul>
				</div>

				<table class="table" layoutH="140" rel="search_mailListsContainer">
					<thead>
						<tr>
							<th width="250">邮箱地址</th>
							<th width="300">主题</th>
							<th width="150">邮件来源</th>
							<th width="150">邮件类型</th>
							<th width="150">状态</th>
							<th width="150">重发次数</th>
							<th width="150">发送时间</th>
							<th width="150">添加时间</th>
							<th width="200">操作</th>
						</tr>
					</thead>
					

					<tbody>
						<c:forEach var="item" items="${PAGING_RESULT.rows}"
							varStatus="status">
							<tr>
								<td>${item.mailAddr}&nbsp;</td>
								<td>${item.subject}&nbsp;</td>
								<td>${item.source}&nbsp;</td>
								<td>${item.mtypeName}&nbsp;</td>
								<td>${item.mailStatusName}&nbsp;</td>
								<td>${item.retryNum}&nbsp;</td>
								<td>${item.sendDate}&nbsp;</td>
								<td>${item.addDate}&nbsp;</td>
								<td>
									<ul class="pageContent_opt">
										<li>
											<sf:link resId="${resId }" url="message/mail/searchMailContent.do" param="id=${item.id }" typeClass="edit" width="900" height="620"  target="dialog"  title="查看内容"></sf:link>
										</li>
									</ul>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<sf:pager rel="search_mailListsContainer"
					totalCount="${PAGING_RESULT.total}"
					numPerPage="${searchModel.pageSize}"
					currentPage="${searchModel.page}"></sf:pager>
				
			</div>
		</div>
	</div>
</div>