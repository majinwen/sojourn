<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<p style="width:100%;">
			<label>路由名称：</label>
			<span>${route.name}&nbsp;</span>
		</p>
		<p style="width:100%;">
			<label>匹配规则：</label>
			<span>${route.matchRule}&nbsp;</span>
		</p>
		<p style="width:100%;">
			<label>过滤规则：</label>
			<span>${route.filterRule}&nbsp;</span>
		</p>
		<p style="width:100%;">
			<label>优先级：</label>
			<span>${route.priority}&nbsp;</span>
		</p>
		<p style="width:100%;">
			<label>用户名：</label>
			<span>${route.username}&nbsp;</span>
		</p>
		<p style="width:100%;">
			<label>创建时间：</label>
			<span>${route.created}&nbsp;</span>
		</p>
		<p style="width:100%;">
			<label>修改时间：</label>
			<span>${route.modified}&nbsp;</span>
		</p>
		<p style="width:100%;">
			<label>状态：</label>
			<span>${route.enabled ? '已启用':'已禁用'}&nbsp;</span>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button"><div class="buttonContent"><button id="closebutton" type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</div>