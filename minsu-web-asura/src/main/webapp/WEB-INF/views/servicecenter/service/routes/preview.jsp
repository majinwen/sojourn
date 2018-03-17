<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<table class="list" width="100%">
			<thead>
				<tr>
					<th width="22">序号</th>
					<th>消费者地址</th>
				</tr>
			</thead>
			<tbody style="border-top: 1px solid #EDEDED; border-left: 1px solid #EDEDED;">
				<c:forEach var="urlMap" items="${result}" varStatus="status">
				<tr>
					<td style="text-align:center;">
						${status.index + 1}
					</td>
					<td>
						${urlMap.key}?${urlMap.value}
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button"><div class="buttonContent"><button id="closebutton" type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</div>