<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>

<div id="stationApplicationTreeDivId" class="tabsContent">
	<div class="unitBox"
		style="float: left; display: block; margin: 10px; overflow: auto; width: 300px; height: 560px; border: solid 1px #CCC; line-height: 21px; background: #FFF;">
		<sf:applicationTree applicationNodes="${messageServiceProviderNodes }"
			url="message/station/monitor/monitorThreadPool.do" rel="monitorstationThreadPoolId"></sf:applicationTree>
	</div>

</div>
<div id="monitorstationThreadPoolId" class="unitBox" style="margin-left: 246px;">
</div>
