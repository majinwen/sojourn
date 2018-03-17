<%@page import="java.net.URLEncoder"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<div class="tabsContent">
	<div id="search_zKClusterListesContainer">
		<div class="pageContent j-resizeGrid">
			<div class="pageHeader" style="border: 1px #B8D0D6 solid">
				<form id="pagerForm"
					onsubmit="return divSearch(this, 'search_zKClusterListesContainer');"
					action="zkmoniter/config/searchZkCluster.do?resId=${resId }" method="post">
					<input type="hidden" name="page" value="1" />  <input type="hidden"
						name="pageSize" value="${searchModel.pageSize}" />
					<div class="searchBar">
						<div class="subBar">
							<ul>
								<li>
								<sf:button resId="${resId }" title="检索" url="zkmoniter/config/searchZkCluster.do"></sf:button>
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
							<sf:link resId="${resId }" url="zkmoniter/config/addZkClusterPage.do" param="" typeClass="add" width="950" height="400" target="dialog"  title="添加" ></sf:link>
						</li>
						<li class="line">line</li>
						<li>
							<sf:link resId="${resId }" url="zkmoniter/config/editZkClusterPage.do" param="{object}" typeClass="edit" warn="请选择一个记录"  width="950" height="400" target="dialog"  title="修改" ></sf:link>
						</li>
						<li class="line">line</li>
						<li>
						 	<sf:link resId="${resId }" url="zkmoniter/config/delZkCluster.do" param="" promptMessage="确实要删除这些记录吗?" target="selectedTodo" rel="ids" typeClass="delete" title="批量删除"></sf:link>
						</li>
					</ul>
				</div>
				
				
				<table class="table" layoutH="140" rel="search_zKClusterListesContainer">
					<thead>
						<tr>
						    <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
							<th width="100">集群名称</th>
							<th width="200">描述</th>
							<th width="500">zk服务明细</th>
							<th width="100">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${PAGING_RESULT.rows}"
							varStatus="status">
							<tr target="object" rel="id=${item.id}&name=${item.name}&desc=${item.desc}">
							    <td><input name="ids" value="${item.id }" type="checkbox"></td>
								<td>${item.name}&nbsp;</td>
								<td>${item.desc}&nbsp;</td>
								<td>${item.clusterContent}&nbsp;</td>
								<td>
								<ul class="pageContent_opt">
									<li><sf:link typeClass=""  resId="${resId }" url="zkmoniter/config/clusterAlarmPage.do" param="id=${item.id }" width="700" height="350" target="dialog"  title="报警设置" ></sf:link></li>
								</ul>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<sf:pager rel="search_zKClusterListesContainer"
					totalCount="${PAGING_RESULT.total}"
					numPerPage="${searchModel.pageSize}"
					currentPage="${searchModel.page}"></sf:pager>
			</div>
		</div>
	</div>
</div>