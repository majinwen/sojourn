<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<%
	String type = request.getParameter("type");
	int currentIndex = 0;
	
	if(type == null || type.isEmpty() || "provider".equals(type)) { currentIndex = 0;
	} else if("consumer".equals(type)) { currentIndex = 1;
	// } else if("application".equals(type)) { currentIndex = 2; // 服务治理菜单下选择进入，不会出现application
	} else if("routes".equals(type)) { currentIndex = 2;
	} else if("dynamic".equals(type)) { currentIndex = 3;
	} else if("accesses".equals(type)) { currentIndex = 4;
	} else if("weights".equals(type)) { currentIndex = 5;
	} else if("loadbalances".equals(type)) { currentIndex = 6;
	} else if("owners".equals(type)) { currentIndex = 7;}

	request.setAttribute("currentIndex", currentIndex);
	request.setAttribute("type", type);
%>
<div class="pageContent" style=" padding: 5px;">
	<div class="tabs" style="margin-top:2px;" currentIndex="${currentIndex}">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>
	                  		<li><a href="servicecenter/service/provider.do?search_service=${param.search_service}" target="ajax" rel="service_providerContainer"><span>提供者</span></a></li>
	                        <li><a href="servicecenter/service/consumer.do?search_service=${param.search_service}" target="ajax" rel="service_consumerContainer"><span>消费者</span></a></li>
							<c:if test="${empty type}"><li><a href="servicecenter/service/application.do?search_service=${param.search_service}" target="ajax" rel="service_applicationContainer"><span>应用</span></a></li></c:if>
							<li><a href="servicecenter/service/routes.do?search_service=${param.search_service}" target="ajax" rel="service_routesContainer"><span>路由规则</span></a></li>
							<li><a href="servicecenter/service/dynamic.do?search_service=${param.search_service}" target="ajax" rel="service_dynamicContainer"><span>动态配置</span></a></li>
							<li><a href="servicecenter/service/accesses.do?search_service=${param.search_service}" target="ajax" rel="service_accessesContainer"><span>访问控制</span></a></li>
							<li><a href="servicecenter/service/weights.do?search_service=${param.search_service}" target="ajax" rel="service_weightsContainer"><span>权重调节</span></a></li>
							<li><a href="servicecenter/service/loadbalances.do?search_service=${param.search_service}" target="ajax" rel="service_loadbalancesContainer"><span>负载均衡</span></a></li>
							<li><a href="servicecenter/service/owners.do?search_service=${param.search_service}" target="ajax" rel="service_ownersContainer"><span>负责人</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent">
	      		<div id="service_providerContainer"><c:if test="${empty type or type eq 'provider'}"><jsp:include flush="true" page="/servicecenter/service/provider.do"/></c:if>&nbsp;</div>
	            <div id="service_consumerContainer"><c:if test="${type eq 'consumer'}"><jsp:include flush="true" page="/servicecenter/service/consumer.do"/></c:if>&nbsp;</div>
	            <c:if test="${empty type}"><div id="service_applicationContainer">&nbsp;</div></c:if>
	      		<div id="service_routesContainer"><c:if test="${type eq 'routes'}"><jsp:include flush="true" page="/servicecenter/service/routes.do"/></c:if>&nbsp;</div>
	            <div id="service_dynamicContainer"><c:if test="${type eq 'dynamic'}"><jsp:include flush="true" page="/servicecenter/service/dynamic.do"/></c:if>&nbsp;</div>
	            <div id="service_accessesContainer"><c:if test="${type eq 'accesses'}"><jsp:include flush="true" page="/servicecenter/service/accesses.do"/></c:if>&nbsp;</div>
	      		<div id="service_weightsContainer"><c:if test="${type eq 'weights'}"><jsp:include flush="true" page="/servicecenter/service/weights.do"/></c:if>&nbsp;</div>
	            <div id="service_loadbalancesContainer"><c:if test="${type eq 'loadbalances'}"><jsp:include flush="true" page="/servicecenter/service/loadbalances.do"/></c:if>&nbsp;</div>
	            <div id="service_ownersContainer"><c:if test="${type eq 'owners'}"><jsp:include flush="true" page="/servicecenter/service/owners.do"/></c:if>&nbsp;</div>
	      </div>
	      <div class="tabsFooter">
	            <div class="tabsFooterContent"></div>
	      </div>
	</div>
</div>