<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
function refreshStatus(){
	$('#cacheClient').loadUrl('cachemonitor/cacheClientStatus.do');
}
</script>
<div id="resourceTreeDivId" class="tabsContent">
	<div class="unitBox" style=" float:left; display:block; margin:10px; overflow:auto; width:300px; height:550px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
	    <ul class="tree treeFolder">
	        <li><a href='javascript:void(0);' onclick="refreshStatus()" >memcache数据分布</a></li>
	    </ul>
	</div>
	<div id="cacheClient" class="unitBox" style="margin-left:20px;" rel="cacheClient">
	</div>	
</div>
