<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function addChartsServiceValue(target) {
		$("#charts_service").val(target.value);
		$("#charts_method").val('');
		
		var chartsmethod = document.getElementById('chartsmethod_select');
		while(chartsmethod.options.length > 1)
			chartsmethod.options.remove(1);
		
		if(target.value) {
			$.ajax({
				url: "servicecenter/monitor/charts/serviceMethods.do",
				cache: true,
				data: {'service' : target.value},
				success: function(data){
					for(var i=0; i < data.length; i++) {
						chartsmethod.options.add(new Option(data[i], data[i])); //这个兼容IE与firefox
					}
				}
			});
		}
	}
	
	function addChartsMethods(sel) {
		$("#charts_method").val(sel.value);
	}
	
	//var sf_chart_monitor = {};
	//sf_chart_monitor.errorCnt = 0;
	
	//function stopIntervalTime() {
	//	if(sf_chart_monitor.interval) clearInterval(sf_chart_monitor.interval);
	//}
	$(function () {
		var options = {
	        lines: { show: true },
	        points: { show: true },
	        // xaxis: { tickSize : [1, "minute"], mode: "time" }
	        xaxis: { mode: "time" }
	    };
		
		function createChart() {
			
			var cService = $("#charts_service").val();
			var cMethod = $("#charts_method").val();
			var cSType = $("#charts_statisticstype").val();
			var cStrTime = $("#charts_strTime").val();
			var cEndTime = $("#charts_endTime").val();
			
			if(!cService || !cMethod || !cSType || !cStrTime || !cEndTime) {
				//stopIntervalTime();
				// alert("输入数据不全！");
				return false;
			}
			
			$.ajax({
	            url: 'servicecenter/monitor/json.do',
	            method: 'GET',
	            dataType: 'json',
	            data: {'service' : cService,
	            	'method' : cMethod,
	            	'statisticstype' : cSType,
	            	'strTime' : cStrTime,
	            	'endTime' : cEndTime
	            },
	            success: function(data) {
	            	var jsondata = eval("(" + data + ")");
	            	
	            	var charts_ticksize = $('#charts_ticksize').val();
	            	var charts_ticksize_unit = $('#charts_ticksize_unit').val();
	            	if(charts_ticksize_unit && charts_ticksize) {
	            		options.xaxis.tickSize = [charts_ticksize, charts_ticksize_unit];
	            	}
	            	
	            	$.plot($("#placeholder"), [jsondata.flot], options);
	            },
	            error: function(xmlhttp) {
	            	if(xmlhttp.status == 500) { // 服务器内部错误
	            		stopIntervalTime();
	            	} else {
	            		//sf_chart_monitor.errorCnt++;
		            	//if(sf_chart_monitor.errorCnt > 5) {
		            	//	stopIntervalTime();
		            	//}
	            	}
	            }
	        });
			
			return true;
		}
		
		// stopIntervalTime();
		$("#viewCharts").click(function () {
			//if(createChart()) {
			//	sf_chart_monitor.interval = setInterval(createChart, 3000);
			//}
			createChart();
		});
	});
</script>
<style type="text/css">
	.floatleft {float:left;}
</style>
<div class="pageContent j-resizeGrid">
	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<input type="hidden" name="page" value="1" />
		<input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
		<div class="searchBar">
			<table id="charts_searchContent" class="searchContent">
				<tr>
					<td>
						<label>开始时间：</label>
						<input type="text" id="charts_strTime" class="date floatleft required" datefmt="yyyy-MM-dd HH:mm" value="<sf:date toFormat="yyyy-MM-dd HH:mm"/>" readonly="true"/>
						<a class="inputDateButton floatleft" href="javascript:;">选择</a>
					</td>
				</tr>
				<tr>
					<td>
						<label>结束时间：</label>
						<input type="text" id="charts_endTime" class="date floatleft required" datefmt="yyyy-MM-dd HH:mm" value="<sf:date toFormat="yyyy-MM-dd HH:mm"/>" readonly="true"/>
						<a class="inputDateButton floatleft" href="javascript:;">选择</a>
					</td>
				</tr>
				<tr>
					<td>
						<label>服务名：</label>
						<input type="text" id="charts_service" class="required" size="50" />
						<select style="margin-left:5px;" onchange="addChartsServiceValue(this)">
							<option value="">请选择</option>
							<c:forEach var="service" items="${serviceMap}" varStatus="status">
								<option value="${service.value}">${service.key}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label>方法名：</label>
						<input type="text" id="charts_method" class="required" value="${param.chartsmethod}" size="30" />
						<select id="chartsmethod_select" onchange="addChartsMethods(this)">
							<option value="">请选择</option>
							<c:forEach var="method" items="${methods}" varStatus="status">
								<option value="${method}">${method}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label>数据类型：</label>
						<select id="charts_statisticstype">
							<option value="success">成功</option>
							<option value="failure">失败</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label>时间间隔：</label>
						<input type="text" id="charts_ticksize" size="5" />
						<select id="charts_ticksize_unit">
							<option value="minute">分钟</option>
							<option value="hour">小时</option>
							<option value="day">天</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><sf:button id="viewCharts" title="查看访问图表" url="servicecenter/monitor/json.do"></sf:button></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="pageContent" layoutH="165" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid; padding:10px 5px 5px 10px;">
		<div id="placeholder" style="height:300px;"></div>
	</div>
</div>