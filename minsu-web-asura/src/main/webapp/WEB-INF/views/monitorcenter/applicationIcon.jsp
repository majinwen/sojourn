<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageFormContent" layoutH="60">
	<sf:sfexplore
				dataFgroup="${providerNodes}"
				dataSgroup="${consumerNodes}"
				fgroupName="提供者"
				sgroupName="消费者"
				title="ip;applicationName"
				status="alive"
				params="ip"
				url="monitorcenter/applicationNodeInfo.do"
				enableIcon="./images/enable.gif"
				disableIcon="./images/disable.gif"
			/> 
</div>
