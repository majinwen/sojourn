<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>

<div id="applicationTreeDivId" class="tabsContent">
	<div class="unitBox"
		style="float: left; display: block; margin: 10px; overflow: auto; width: 300px; height: 560px; border: solid 1px #CCC; line-height: 21px; background: #FFF;">
		<sf:applicationTree applicationNodes="${applicationNodes }"
			url="monitorcenter/applicationFileLogInfo.do" rel="nodeInfoId"></sf:applicationTree>
	</div>

</div>
<div id="nodeInfoId" class="unitBox" style="margin-left: 246px;">
</div>
