<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<div class="tabsContent">
	<div id="search_blackSmsListesContainer">
		<div class="pageContent j-resizeGrid">
			<div class="pageHeader" style="border: 1px #B8D0D6 solid">
				<form id="pagerForm"
					onsubmit="return divSearch(this, 'search_blackSmsListesContainer');"
					action="message/sms/black/blackListSms.do?resId=${resId }" method="post">
					<input type="hidden" name="page" value="1" />  <input type="hidden"
						name="pageSize" value="${searchModel.pageSize}" />
					<div class="searchBar">
						<table class="searchContent">
							<tr>
								<td>手机号：<input id="blackTelId" name="search_tel"
									value="${param.search_tel }" />
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li>
								<sf:button resId="${resId }" title="检索" url="message/sms/black/blackListSms.do"></sf:button>
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
							<sf:link resId="${resId }" url="message/sms/black/toAddBlackListSms.do" param="" typeClass="add" target="dialog"  title="添加" ></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" url="message/sms/black/delete.do" param="" promptMessage="确实要删除这些记录吗?" target="selectedTodo" rel="ids" typeClass="delete" title="批量删除"></sf:link>
						</li>
						<li class="line">line</li>
						<%-- <li>
						 	<sf:link resId="${resId }" url="message/mail/blackmail/toEditBlackMail.do" param="{object}" typeClass="edit" target="dialog" warn="请选择一个记录"  title="修改"></sf:link>
						</li> --%>
					</ul>
				</div>
				
				
				<table class="table" layoutH="140" rel="search_blackSmsListesContainer">
					<thead>
						<tr>
							<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
							<th width="200">手机号</th>
							<th width="150">黑名单类型</th>
							<th width="600">备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${PAGING_RESULT.rows}"
							varStatus="status">
							<tr target="object" rel="id=${item.id }&tel=${item.tel}&btype=${item.btype}&notes=${item.notes}">
								<td><input name="ids" value="${item.id }" type="checkbox"></td>
								<td>${item.tel}&nbsp;</td>
								<c:if test="${item.btype == 1}">
									<td>bug报警短信&nbsp;</td>
								</c:if>
								<c:if test="${item.btype == 2}">
									<td>广告短信&nbsp;</td>
								</c:if>
								<c:if test="${item.btype == 3}">
									<td>正常业务短信&nbsp;</td>
								</c:if>
								<td>${item.notes}&nbsp;</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<sf:pager rel="search_blackSmsListesContainer"
					totalCount="${PAGING_RESULT.total}"
					numPerPage="${searchModel.pageSize}"
					currentPage="${searchModel.page}"></sf:pager>
			</div>
		</div>
	</div>
</div>