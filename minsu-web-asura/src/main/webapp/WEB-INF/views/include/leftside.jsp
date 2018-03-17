<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div id="sidebar_s">
	<div class="collapse">
		<div class="toggleCollapse"><div></div></div>
	</div>
</div>
<div id="sidebar">
	<div class="toggleCollapse"><h2>主菜单</h2><div>collapse</div></div>
	<div class="accordion" fillSpace="sideBar">
		<sf:menutree pId="${pId }" resources="${SESSION_MENUS_RESOURCE }"></sf:menutree>
	</div>
</div>