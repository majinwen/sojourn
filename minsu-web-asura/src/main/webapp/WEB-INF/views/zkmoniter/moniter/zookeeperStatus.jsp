<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageFormContent nowrap" layoutH="0">
	<dl>
		<dt>服务器ip：</dt>
		<dd>
			${zkStatus.ip }
		</dd>
	</dl>
	<dl>
		<dt>集群模式：</dt>
		<dd>
		    ${zkStatus.mode}
		</dd>
	</dl>
	<dl>
		<dt>连接数：</dt>
		<dd>
			${zkStatus.connectCount }
		</dd>
	</dl>
	<dl>
		<dt>Watch数:</dt>
		<dd>
			${zkStatus.watches }
		</dd>
	</dl>
	<dl>
		<dt>Watched/Totalpath：</dt>
		<dd>
			${zkStatus.watchedPaths }/${zkStatus.nodeCount }
		</dd>
	</dl>
	<dl>
		<dt>sent/Received：</dt>
		<dd>
			${zkStatus.sent }/ ${zkStatus.received}
		</dd>
	</dl>
	<dl>
		<dt>自检状态：</dt>
		<dd>
			<c:if test="${zkStatus.statusType==1 }">OK</c:if>
			<c:if test="${zkStatus.statusType==0 }">Checking</c:if>
			<c:if test="${zkStatus.statusType==-1 }">Initializing</c:if>
			<c:if test="${zkStatus.statusType==2 }">error</c:if>
		</dd>
	</dl>
	<dl>
		<dt>状态：</dt>
		<dd>
			${zkStatus.statContent }
		</dd>
	</dl>
</div>
