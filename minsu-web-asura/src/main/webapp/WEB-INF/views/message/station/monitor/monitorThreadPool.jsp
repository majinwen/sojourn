<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<div id="monitorSmsThreadPoolDivId" class="tabsContent">
    <table class="table" layoutH=4>
    	<tr>
    		<td width="200">线程保持活动时间(ms)：</td>
    		<td width="200">${stationInfoThreadPoolMBean.getThreadKeepAliveTime() }</td>
    	</tr>
    	
    	<tr>
    		<td>核心线程数量：</td>
    		<td>${stationInfoThreadPoolMBean.getCoreThreadPoolSize() }</td>
    	</tr>
    	
    	<tr>
    		<td>获取最大线程数：</td>
    		<td>${stationInfoThreadPoolMBean.getMaxThreadPoolSize() }</td>
    	</tr>
    	
    	<tr>
    		<td>完成任务数：</td>
    		<td>${stationInfoThreadPoolMBean.getCompletedTaskCount() }</td>
    	</tr>
    	
    	<tr>
    		<td>活动线程数 ：</td>
    		<td>${stationInfoThreadPoolMBean.getActiveThreadCount() }</td>
    	</tr>
    	
    	<tr>
    		<td>队列缓冲数：</td>
    		<td>${stationInfoThreadPoolMBean.getQueueSize() }</td>
    	</tr>
    </table>
</div>
