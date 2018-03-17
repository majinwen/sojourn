<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script language="JavaScript" src='js/FusionCharts.js'></script>
<div class="pageFormContent nowrap" layoutH="0">
	<dl>
		<dt>服务器ip：</dt>
		<dd>
			${host.ip }
		</dd>
	</dl>
	<dl>
		<dt>cpu使用情况：</dt>
		<dd>
		    ${host.cpuUsage}
		</dd>
	</dl>
	<dl>
		<dt>内存使用情况：</dt>
		<dd>
			${host.memoryUsage }
		</dd>
	</dl>
	<dl>
		<dt>负载:</dt>
		<dd>
			${host.load }
		</dd>
	</dl>
	<dl>
		<dt>磁盘使用情况：</dt>
		<dd>
			<div id="chartdiv" align="center">
			<script type="text/javascript">
			var chart = new FusionCharts('fusionCharts/FCF_Column3D.swf', "ChartId", "600", "400");  
		    chart.setDataXML("${discXml}");   
		    chart.render('chartdiv');                          
			</script> 
			</div>
		</dd>
	</dl>
</div>
