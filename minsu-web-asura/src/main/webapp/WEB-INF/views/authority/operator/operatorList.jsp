<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<div class="tabsContent">
	<div id="search_operatorListsContainer">
		<div class="pageContent j-resizeGrid">
			<div class="pageHeader" style="border: 1px #B8D0D6 solid">
				<form id="pagerForm"
					onsubmit="return divSearch(this, 'search_operatorListsContainer');"
					action="authority/operator/operatorList.do?resId=${resId }" method="post">
					<input type="hidden" name="page" value="1" /> <input type="hidden"
						name="pageSize" value="${searchModel.pageSize}" />
					<div class="searchBar">
						<table class="searchContent">
							<tr>
								<td>姓名：<input id="operatorNameId" name="search_operatorName"
									value="${param.search_operatorName }" />
								</td>
								<td>登录账号：<input id="loginNameId" name="search_loginName"
									value="${param.search_loginName }" />
								</td>
								<td>工号：<input id="jobNumId" name="search_jobNum"
									value="${param.search_jobNum }" />
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li>
								<sf:button resId="${resId }" title="检索" url="authority/operator/operatorList.do"></sf:button>
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
							<sf:link resId="${resId }" url="authority/operator/toOperatorAdd.do" param="" typeClass="add" target="dialog" width="600" height="330" title="添加" ></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" url="authority/operator/delete.do" param="" promptMessage="确实要删除这些记录吗?" target="selectedTodo" rel="ids" typeClass="delete" title="批量删除"></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" url="authority/operator/toOperatorEdit.do" param="{object}" typeClass="edit" target="dialog" warn="请选择一个记录" width="600" height="330" title="修改"></sf:link>
						</li>
					</ul>
				</div>


				<table class="table" layoutH="140" rel="search_operatorListsContainer">
					<thead>
						<tr>
							<th width="22"><input type="checkbox" group="ids"
								class="checkboxCtrl"></th>
							<th width="200">名字</th>
							<th width="200">登录账号</th>
							<th width="150">工号</th>
							<th width="200">手机号</th>
							<th width="100">状态</th>
							<th width="100">类型</th>
							<th width="200">邮箱</th>
							<th width="200">所属部门</th>
							<th width="200">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${PAGING_RESULT.rows}"
							varStatus="status">
							<tr target="object"
								rel="operatorId=${item.operatorId }&operatorName=${item.operatorName}&loginName=${item.loginName}&jobNum=${item.jobNum}&telephone=${item.telephone}&operatorStatus=${item.operatorStatus}&operatorType=${item.operatorType}&operatorEmail=${item.operatorEmail}&operatorDepartment=${item.operatorDepartment}">
								<td><input name="ids" value="${item.operatorId }" type="checkbox"></td>
								<td>${item.operatorName}&nbsp;</td>
								<td>${item.loginName}&nbsp;</td>
								<td>${item.jobNum}&nbsp;</td>
								<td>${item.telephone}&nbsp;</td>
								<c:if test="${item.operatorStatus == 0}">
									<td>失效&nbsp;</td>
								</c:if>
								<c:if test="${item.operatorStatus == 1}">
									<td>正常&nbsp;</td>
								</c:if>
								<c:if test="${item.operatorStatus == 2}">
									<td>删除&nbsp;</td>
								</c:if>
								<c:if test="${item.operatorType == 1}">
									<td>操作员&nbsp;</td>
								</c:if>
								<c:if test="${item.operatorType == 2}">
									<td>管理员&nbsp;</td>
								</c:if>
								<td>${item.operatorEmail}&nbsp;</td>
								<td>技术部&nbsp;</td>
								<td>
									<ul class="pageContent_opt">
										<li>
										<c:if test="${item.operatorStatus == 1}">
											<sf:link resId="${resId }" url="authority/role/allotRoleTree.do" param="operatorId=${item.operatorId }" typeClass="edit" width="350" height="500"  target="dialog"  title="分配角色"></sf:link>
										</c:if>
										</li>
									</ul>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<sf:pager rel="search_operatorListsContainer"
					totalCount="${PAGING_RESULT.total}"
					numPerPage="${searchModel.pageSize}"
					currentPage="${searchModel.page}"></sf:pager>
			</div>
		</div>
	</div>
</div>