<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent" style=" padding: 5px;">
	<div class="tabs" style="margin-top:2px;">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>
	                        <li><a href="servicecenter/address/provider.do?search_address=${param.search_address}" target="ajax" rel="address_providerContainer"><span>提供者</span></a></li>
							<li><a href="servicecenter/address/consumer.do?search_address=${param.search_address}" target="ajax" rel="address_consumerContainer"><span>消费者</span></a></li>
							<li><a href="servicecenter/address/routes.do?search_address=${param.search_address}" target="ajax" rel="address_routesContainer"><span>路由规则</span></a></li>
							<li><a href="servicecenter/address/accesses.do?search_address=${param.search_address}" target="ajax" rel="address_accessesContainer"><span>访问控制</span></a></li>
							<li><a href="servicecenter/address/weights.do?search_address=${param.search_address}" target="ajax" rel="address_weightsContainer"><span>权重调节</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent">
	      		<div id="address_providerContainer"><jsp:include flush="true" page="/servicecenter/address/provider.do"/></div>
	            <div id="address_consumerContainer">&nbsp;</div>
	            <div id="address_routesContainer">&nbsp;</div>
	            <div id="address_accessesContainer">&nbsp;</div>
	            <div id="address_weightsContainer">&nbsp;</div>
	      </div>
	      <div class="tabsFooter">
	            <div class="tabsFooterContent"></div>
	      </div>
	</div>
</div>