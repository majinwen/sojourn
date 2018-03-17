<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script language="JavaScript" src='js/FusionCharts.js'></script>
<div class="pageFormContent nowrap" layoutH="0">
	<dl>
		<dt>业务数据：</dt>
		<dd>
			<div id="mastdiv" align="center">
			<script type="text/javascript">
			var chart = new FusionCharts('fusionCharts/FCF_Pie3D.swf', "ChartId", "600", "400");  
		    chart.setDataXML("${mastxml}");   
		    chart.render('mastdiv');                          
			</script> 
			</div>
		</dd>
	</dl>
	<dl>
		<dt>session数据：</dt>
		<dd>
		    <div id="sessiondiv" align="center">
			<script type="text/javascript">
			var chart = new FusionCharts('fusionCharts/FCF_Pie3D.swf', "ChartId", "600", "400");  
		    chart.setDataXML("${sessionxml}");   
		    chart.render('sessiondiv');                          
			</script> 
			</div>
		</dd>
	</dl>
</div>
