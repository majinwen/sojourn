<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent" style=" padding: 5px;">
	<div class="tabs" style="margin-top:2px;">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>
	                  		<li><a href="servicecenter/monitor/charts.do" target="ajax" rel="monitor_chartsContainer"><span>访问统计图表</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent">
				<div id="monitor_chartsContainer"><jsp:include flush="true" page="/servicecenter/monitor/charts.do"/>&nbsp;</div>
	      </div>
	      <div class="tabsFooter">
	            <div class="tabsFooterContent"></div>
	      </div>
	</div>
</div>