<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
function refreshStatus(ip){
	$('#zookeeperStatus').loadUrl('zkmoniter/moniter/zookeeperStatus.do?ip='+ip);
}
</script>
<div id="resourceTreeDivId" class="tabsContent">
	<div class="unitBox" style=" float:left; display:block; margin:10px; overflow:auto; width:300px; height:550px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
	    <ul class="tree treeFolder">
	        <li><a href='javascript:void(0);'  >zookeeper集群</a>
	        <c:forEach var="item" items="${zkClusters}" varStatus="status">
	           <ul>
	              <li><a href='javascript:void(0);'>${item.name }</a>
	               <c:forEach var="item1" items="${item.zkInfos}" varStatus="status">
	                  <ul>
	                     <li><a href='javascript:void(0);' onclick="refreshStatus('${item1.ip}')">${item1.ip }</a></li>
	                  </ul>
	               </c:forEach>
	              </li>
	           </ul>
	        </c:forEach>
	        </li>
	    </ul>
	</div>
	<div id="zookeeperStatus" class="unitBox" style="margin-left:20px;" rel="zookeeperStatus">
	</div>	
</div>
