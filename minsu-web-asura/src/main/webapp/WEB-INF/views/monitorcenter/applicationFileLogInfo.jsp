<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>

<div id="applicationLogDivId" class="tabsContent">
	<div class="tabsContent">
		<div id="search_triggerListsContainer">
			<div class="pageContent j-resizeGrid">
				<table class="table" layoutH="45">
					<thead>
						<tr>
						    <th width="50">序号</th>
							<th width="550">日志文件名</th>
							<th width="300">文件大小</th>
							<th width="150">下载操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${fileNames}" varStatus="status">
							<tr>
							    <td>
							    	${status.index+1}
							    </td>
								<td>
								    ${item.fileName}
								</td>
								<td>
									${item.fileSize}
								</td>
								<td>
								    <ul class="pageContent_opt">
										<li>
											<a href="monitorcenter/download.do?fileName=${item.fileName}&ip=${item.ip}" class="edit" target="blank">下载</a>
										</li>
										<li>
											<a href="log/logview.do?fileName=${item.fileName}" class="edit" target="blank">查看日志</a>
										</li>
									</ul>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
