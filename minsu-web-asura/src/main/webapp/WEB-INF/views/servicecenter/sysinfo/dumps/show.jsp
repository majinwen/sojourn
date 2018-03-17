<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<table class="list" width="98%">
		<thead>
			<tr>
				<th>${dumpsTitle}</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="dump" items="${dumpsInfo}" varStatus="status">
			<tr>
				<td>${dump}</td>
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