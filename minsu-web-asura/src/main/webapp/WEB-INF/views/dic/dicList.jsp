<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<div class="tabsContent">
	<div id="search_dicListsContainer">
		<div class="pageContent j-resizeGrid">
		    <div class="pageHeader" style="border: 1px #B8D0D6 solid">
				<form id="pagerForm"
					onsubmit="return divSearch(this, 'search_dicListsContainer');"
					action="dic/dicList.do?resId=${resId }" method="post">
					<input type="hidden" name="page" value="1" /> <input type="hidden"
						name="pageSize" value="${searchModel.pageSize}" />
					<div class="searchBar">
						<table class="searchContent">
							<tr>
							    <td>库名：<input id="search_dbName_id" name="search_dbName"
									value="${param.search_dbName }" />
								</td>
								<td>表名：<input id="search_tableName_id" name="search_tableName"
									value="${param.search_tableName }" />
								</td>
								<td>字段名：<input id="search_fieldName_id" name="search_fieldName"
									value="${param.search_fieldName }" />
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li>
								<sf:button resId="${resId }" title="检索" url="dic/dicList.do"></sf:button>
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
							<sf:link resId="${resId }" url="dic/toDicAdd.do" param="" typeClass="add" target="dialog" width="600" height="330" title="添加" ></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" url="dic/delete.do" param="" promptMessage="确实要删除这些记录吗?" target="selectedTodo" rel="ids" typeClass="delete" title="批量删除"></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" url="dic/toDicEdit.do" param="{object}" typeClass="edit" target="dialog" warn="请选择一个记录" width="600" height="330" title="修改"></sf:link>
						</li>
					</ul>
				</div>
				
				
				

				<table class="table" layoutH="140" rel="search_dicListsContainer">
					<thead>
						<tr>
							<th width="22"><input type="checkbox" group="ids"
								class="checkboxCtrl"></th>
							<th width="250">库名</th>
							<th width="300">表名</th>
							<th width="150">字段名</th>
							<th width="150">字段注解</th>
							<th width="150">字段值</th>
							<th width="150">值英文名</th>
							<th width="150">值显示名</th>
							<th width="150">添加时间</th>
							<th width="150">更新时间</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="item" items="${PAGING_RESULT.rows}"
							varStatus="status">
							<tr target="object"
								rel="id=${item.id }&dbName=${item.dbName}&tableName=${item.tableName}&fieldName=${item.fieldName}&fieldComment=${item.fieldComment}
								&fieldValue=${item.fieldValue}&valuePname=${item.valuePname}&valueShowname=${item.valueShowname}">
								<td><input name="ids" value="${item.id }" type="checkbox"></td>
								<td>${item.dbName}&nbsp;</td>
								<td>${item.tableName}&nbsp;</td>
								<td>${item.fieldName}&nbsp;</td>
								<td>${item.fieldComment}&nbsp;</td>
								<td>${item.fieldValue}&nbsp;</td>
								<td>${item.valuePname}&nbsp;</td>
								<td>${item.valueShowname}&nbsp;</td>
								<td>${item.addDate}&nbsp;</td>
								<td>${item.updateDate}&nbsp;</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<sf:pager rel="search_dicListsContainer"
					totalCount="${PAGING_RESULT.total}"
					numPerPage="${searchModel.pageSize}"
					currentPage="${searchModel.page}"></sf:pager>
				
			</div>
		</div>
	</div>
</div>