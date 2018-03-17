<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div id="navTab" class="tabsPage">
	<div class="tabsPageHeader">
		<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
			<ul class="navTab-tab">
				<li tabid="home"><a href="javascript:;"><span><span class="home_icon">首页</span></span></a></li>
			</ul>
		</div>
		<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
		<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
		<div class="tabsMore" style="display:none;">more</div>
	</div>
	<ul class="tabsMoreList">
		<li><a href="javascript:;">首页</a></li>
	</ul>
	<div class="navTab-panel tabsPageContent layoutBox">
		<div style="margin-right:230px"><div class="pageContent" style=" padding: 5px;">欢迎！</div></div>
	</div>
</div>