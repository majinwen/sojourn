<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<div class="tabsContent">
	<div id="search_roleListsContainer">
		<div class="pageContent j-resizeGrid">
			<div class="pageHeader" style="border: 1px #B8D0D6 solid">
				<form id="pagerForm"
					onsubmit="return divSearch(this, 'search_roleListsContainer');"
					action="authority/role/roleList.do?resId=${resId }" method="post">
					<input type="hidden" name="page" value="1" /> <input type="hidden"
						name="pageSize" value="${searchModel.pageSize}" />
					<div class="searchBar">
						<table class="searchContent">
							<tr>
								<td>角色名称：<input id="roleNameId" name="search_roleName"
									value="${param.search_roleName }" />
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li>
								<sf:button resId="${resId }" title="检索" url="authority/role/roleList.do"></sf:button>
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
							<sf:link resId="${resId }" url="authority/role/toRoleAdd.do"  param="" typeClass="add" target="dialog"  title="添加" ></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" url="authority/role/delete.do"  param="" promptMessage="确实要删除这些记录吗?" target="selectedTodo" rel="ids" typeClass="delete" title="批量删除"></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" url="authority/role/toRoleEdit.do" param="{object}" typeClass="edit" target="dialog" warn="请选择一个记录"  title="修改"></sf:link>
						</li>
					</ul>
				</div>


				<table class="table" layoutH="140" rel="search_roleListsContainer">
					<thead>
						<tr>
							<th width="22"><input type="checkbox" group="ids"
								class="checkboxCtrl"></th>
							<th width="250">角色名称</th>
							<th width="300">描述</th>
							<th width="200">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${PAGING_RESULT.rows}"
							varStatus="status">
							<tr target="object"
								rel="roleId=${item.roleId }&roleName=${item.roleName}&roleDesc=${item.roleDesc}">
								<td><input name="ids" value="${item.roleId }" type="checkbox"></td>
								<td>${item.roleName}&nbsp;</td>
								<td>${item.roleDesc}&nbsp;</td>
								<td>
									<ul class="pageContent_opt">
										<li>
											<sf:link resId="${resId }" url="authority/resource/allotResourceTree.do" param="roleId=${item.roleId}" typeClass="edit" width="350" height="500"  target="dialog"  title="分配资源"></sf:link>
										</li>
									</ul>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<sf:pager rel="search_roleListsContainer"
					totalCount="${PAGING_RESULT.total}"
					numPerPage="${searchModel.pageSize}"
					currentPage="${searchModel.page}"></sf:pager>
			</div>
		</div>
	</div>
</div>