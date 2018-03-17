<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<div class="tabsContent">
	<div id="search_triggerListsContainer">
		<div class="pageContent j-resizeGrid">
		    <div class="pageHeader" style="border: 1px #B8D0D6 solid">
				<form id="pagerForm"
					onsubmit="return divSearch(this, 'search_triggerListsContainer');"
					action="quartz/triggerList.do?resId=${resId }" method="post">
					<input type="hidden" name="page" value="1" /> <input type="hidden"
						name="pageSize" value="${searchModel.pageSize}" />
					<div class="searchBar">
						<table class="searchContent">
							<tr>
								<td>触发器名称：<input id="nameId" name="search_name"
									value="${param.search_name }" />
								</td>
								<td>触发器分组：<input id="groupId" name="search_group"
									value="${param.search_group }" />
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li>
								<sf:button resId="${resId }" title="检索" url="quartz/triggerList.do"></sf:button>
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
							<sf:link resId="${resId }" param="" url="quartz/toTriggerAdd.do" typeClass="add" target="dialog" width="600" height="330" title="添加" ></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" param=""  url="quartz/pauseTrigger.do" promptMessage="确实要暂停这些触发器吗?" target="selectedTodo" rel="ids" typeClass="delete" title="暂停触发器"></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" param=""  url="quartz/resumeTrigger.do" promptMessage="确实要恢复这些触发器吗?" target="selectedTodo" rel="ids" typeClass="delete" title="恢复触发器"></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" param=""  url="quartz/delete.do" promptMessage="确实要删除这些触发器吗?" target="selectedTodo" rel="ids" typeClass="delete" title="批量删除"></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" url="quartz/toTriggerEdit.do" param="{object}" typeClass="edit" target="dialog" warn="请选择一个记录" width="600" height="330" title="修改"></sf:link>
						</li>
					</ul>
				</div>
				
				
				<table class="table" layoutH="140" rel="search_triggerListsContainer">
					<thead>
						<tr>
							<th width="22"><input type="checkbox" group="ids"
								class="checkboxCtrl"></th>
							<th width="250">触发器名称</th>
							<th width="300">触发器分组</th>
							<th width="150">状态</th>
							<th width="100">类型</th>
							<th width="250">启动时间</th>
							<th width="250">前一次执行时间</th>
							<th width="250">下次执行时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${PAGING_RESULT.rows}"
							varStatus="status">
							<tr target="object"
								rel="name=${item.name }&group=${item.group}">
								<td><input name="ids" value="${item.name }|${item.group}" type="checkbox"></td>
								<td>${item.name}&nbsp;</td>
								<td>${item.group}&nbsp;</td>
								<td>${item.state}&nbsp;</td>
								<td>${item.type}&nbsp;</td>
								<td><sf:date toFormat="yyyy-MM-dd HH:mm:ss" dateLong="${item.startTime}"></sf:date>&nbsp;</td>
								<td><sf:date toFormat="yyyy-MM-dd HH:mm:ss" dateLong="${item.prevFireTime}"></sf:date>&nbsp;</td>
								<td><sf:date toFormat="yyyy-MM-dd HH:mm:ss" dateLong="${item.nextFireTime}"></sf:date>&nbsp;</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<sf:pager rel="search_triggerListsContainer"
					totalCount="${PAGING_RESULT.total}"
					numPerPage="${searchModel.pageSize}"
					currentPage="${searchModel.page}"></sf:pager>
				
			</div>
		</div>
	</div>
</div>