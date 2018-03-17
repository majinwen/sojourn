<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<div class="tabsContent">
	<div id="search_cacheDataContainer">
		<div class="pageContent j-resizeGrid">
			<div class="pageHeader" style="border: 1px #B8D0D6 solid">
				<form id="pagerForm"
					onsubmit="return divSearch(this, 'search_cacheDataContainer');"
					action="cachemonitor/searchDataByKey.do?resId=${resId }" method="post">
					<div class="searchBar">
						<table class="searchContent">
							<tr>
								<td>缓存类型：</td>
								<td><select class="combox" name="cacheType">
									<option value="SFBEST">SFBEST</option>
									<option value="MAPP">MAPP</option>
									<option value="CORP">CORP</option>
								</select></td>
								
								<td>key：<input name="cacheKey"
									value="${param.cacheKey }" />
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li>
								<sf:button resId="${resId }" title="查询" url="cachemonitor/searchDataByKey.do"></sf:button>
								</li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="pageContent"
				style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
				<table  layoutH="140" rel="search_cacheDataContainer"  style="word-break:break-all;word-wrap:break-word" border="1">
					<thead>
						<tr>
							<th>mc服务器：</th>
							<th>value：</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="mymap" items="${dataMap}"
							varStatus="status">
							<tr>
								<td>${mymap.key}&nbsp;</td>
								<td>${mymap.value}&nbsp;</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>