<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<div class="tabsContent">
	<div id="search_blackMailListesContainer">
		<div class="pageContent j-resizeGrid">
			<div class="pageHeader" style="border: 1px #B8D0D6 solid">
				<form id="pagerForm"
					onsubmit="return divSearch(this, 'search_blackMailListesContainer');"
					action="message/mail/black/blackListMail.do?resId=${resId }" method="post">
					<input type="hidden" name="page" value="1" />  <input type="hidden"
						name="pageSize" value="${searchModel.pageSize}" />
					<div class="searchBar">
						<table class="searchContent">
							<tr>
								<td>邮箱地址：<input id="blackTelId" name="search_mailAddr"
									value="${param.search_mailAddr }" />
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li>
								<sf:button resId="${resId }" title="检索" url="message/mail/black/blackListMail.do"></sf:button>
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
							<sf:link resId="${resId }" url="message/mail/black/toAddBlackListMail.do" param="" typeClass="add" target="dialog"  title="添加" ></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" url="message/mail/black/delete.do" param="" promptMessage="确实要删除这些记录吗?" target="selectedTodo" rel="ids" typeClass="delete" title="批量删除"></sf:link>
						</li>
						<li class="line">line</li>
						<%-- <li>
						 	<sf:link resId="${resId }" url="message/mail/blackmail/toEditBlackMail.do" param="{object}" typeClass="edit" target="dialog" warn="请选择一个记录"  title="修改"></sf:link>
						</li> --%>
					</ul>
				</div>
				
				
				<table class="table" layoutH="140" rel="search_blackListesContainer">
					<thead>
						<tr>
							<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
							<th width="200">黑名单邮箱地址</th>
							<th width="150">黑名单类型</th>
							<th width="600">备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${PAGING_RESULT.rows}"
							varStatus="status">
							<tr target="object" rel="id=${item.id }&mailAddr=${item.mailAddr}&btype=${item.btype}&notes=${item.notes}">
								<td><input name="ids" value="${item.id }" type="checkbox"></td>
								<td>${item.mailAddr}&nbsp;</td>
								<c:if test="${item.btype == 1}">
									<td>bug报警邮件&nbsp;</td>
								</c:if>
								<c:if test="${item.btype == 2}">
									<td>广告邮件&nbsp;</td>
								</c:if>
								<c:if test="${item.btype == 3}">
									<td>正常业务邮件&nbsp;</td>
								</c:if>
								<td>${item.notes}&nbsp;</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<sf:pager rel="search_blackMailListesContainer"
					totalCount="${PAGING_RESULT.total}"
					numPerPage="${searchModel.pageSize}"
					currentPage="${searchModel.page}"></sf:pager>
			</div>
		</div>
	</div>
</div>