<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent" style=" padding: 5px;">
	<div class="tabs" style="margin-top:2px;">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>
	                        <li><a href="servicecenter/search/service.do" target="ajax" rel="search_serviceContainer"><span>服务</span></a></li>
	                        <li><a href="servicecenter/search/application.do" target="ajax" rel="search_applicationContainer"><span>应用</span></a></li>
	                        <li><a href="servicecenter/search/address.do" target="ajax" rel="search_addressContainer"><span>机器IP</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent">
	      		<div id="search_serviceContainer"><jsp:include flush="true" page="/servicecenter/search/service.do"/></div>
	            <div id="search_applicationContainer">&nbsp;</div>
	            <div id="search_addressContainer">&nbsp;</div>
	      </div>
	      <div class="tabsFooter">
	            <div class="tabsFooterContent"></div>
	      </div>
	</div>
</div>