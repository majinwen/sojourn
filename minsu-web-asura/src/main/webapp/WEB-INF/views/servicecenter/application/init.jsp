<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent" style=" padding: 5px;">
	<div class="tabs" style="margin-top:2px;">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>
	                        <li><a href="servicecenter/application/service.do?search_application=${param.search_application}" target="ajax" rel="application_serviceContainer"><span>服务</span></a></li>
							<li><a href="servicecenter/application/address.do?search_application=${param.search_application}" target="ajax" rel="application_addressContainer"><span>机器</span></a></li>
							<li><a href="servicecenter/application/dynamic.do?search_application=${param.search_application}" target="ajax" rel="application_dynamicContainer"><span>动态配置</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent">
	      		<div id="application_serviceContainer"><jsp:include flush="true" page="/servicecenter/application/service.do"/></div>
	            <div id="application_addressContainer">&nbsp;</div>
	            <div id="application_dynamicContainer">&nbsp;</div>
	      </div>
	      <div class="tabsFooter">
	            <div class="tabsFooterContent"></div>
	      </div>
	</div>
</div>