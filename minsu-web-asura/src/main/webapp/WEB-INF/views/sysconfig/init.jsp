<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent" style=" padding: 5px;">
	<div class="tabs" style="margin-top:2px;">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>
	                  		<li><a href="sysconfig/config/search.do" target="ajax" rel="sysconfig_configContainer"><span>系统配置</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent">
				<div id="sysconfig_configContainer"><jsp:include flush="true" page="/sysconfig/config/search.do"/>&nbsp;</div>
	      </div>
	      <div class="tabsFooter">
	            <div class="tabsFooterContent"></div>
	      </div>
	</div>
</div>