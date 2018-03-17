<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
function refreshStatus(clientName){
	$('#clientData').loadUrl('cachemonitor/cacheClientData.do?clientName='+clientName);
}
</script>
<div id="resourceTreeDivId" class="tabsContent">
	<div class="unitBox" style=" float:left; display:block; margin:10px; overflow:auto; width:300px; height:550px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
	    <ul class="tree treeFolder">
	        <li><a href='javascript:void(0);'  >mc服务器</a>
	           <ul>
	           <c:forEach items="${cacheClient}" var="mymap" >
	                <li><a href='javascript:void(0);' onclick="refreshStatus('${mymap.value}')">${mymap.value}</a></li>
	           </c:forEach>
	           </ul>
	        </li>
	    </ul>
	</div>
	<div id="clientData" class="unitBox" style="margin-left:20px;" rel="clientData">
	</div>	
</div>
