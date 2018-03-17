<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>

<div id="applicationTreeDivId" class="tabsContent">
    <table class="table" layoutH=4>
    	<tr>
    		<td width="200">操作系统物理内存总数：</td>
    		<td width="200">${sysBean.getTotalPhysicalMemorySize() /1024 /1024 }</td>
    	</tr>
    	
    	<tr>
    		<td>操作系统空闲物理内存：</td>
    		<td>${sysBean.getFreePhysicalMemorySize() /1024 /1024 }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM实现名称：</td>
    		<td>${sysBean.getJvmName() }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM版本：</td>
    		<td>${sysBean.getJvmVersion() }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM启动时间 ：</td>
    		<td><sf:date toFormat="yyyy-MM-dd HH:mm:ss" dateLong="${sysBean.getJvmStartTime() }"></sf:date></td>
    	</tr>
    	
    	<tr>
    		<td>JVM最大能识别内存：</td>
    		<td>${sysBean.getMaxJvmMemorySize() /1024 /1024 }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM支持的最大堆内存：</td>
    		<td>${sysBean.getHeapMemoryMax() /1024 /1024 }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM总内存数：</td>
    		<td>${sysBean.getTotalJvmMemorySize() /1024 /1024 }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM空闲内存数：</td>
    		<td>${sysBean.getFreeJvmMemorySize() /1024 /1024 }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM初始堆大小：</td>
    		<td>${sysBean.getHeapMemoryInit() /1024 /1024 }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM启动后加载Class总数：</td>
    		<td>${sysBean.getTotalLoadedClassCount() }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM当前加载的Class数：</td>
    		<td>${sysBean.getLoadedClassCount() }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM启动后创建线程总数：</td>
    		<td>${sysBean.getTotalStartedThreadCount() }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM启动后活动线程的峰值：</td>
    		<td>${sysBean.getPeakThreadCount() }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM当前有效线程数(守、非守)：</td>
    		<td>${sysBean.getThreadCount() }</td>
    	</tr>
    	
    	<tr>
    		<td>JVM守护线程数：</td>
    		<td>${sysBean.getDaemonThreadCount() }</td>
    	</tr>
    </table>
</div>
