<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	function editRoutesAjaxDone(json) {
		$('#service_routesContainer').loadUrl('servicecenter/service/routes.do?search_service=${route.service}');
		alertMsgByJson(json);
		$.pdialog.closeCurrent();
	}
</script>
<div class="pageContent">
	<form class="required-validate" action="servicecenter/service/routes/edit.do" method="post" onsubmit="return validateCallback(this, editRoutesAjaxDone)">
		<input type="hidden" name="id" value="${route.id}">
		<div class="pageFormContent" layoutH="56">
			<p style="width:100%;">
				<label>路由名称：</label>
				<input type="text" name="name" class="required" value="${route.name}" size="30" />
			</p>
			<p style="width:100%;">
				<label>优先级：</label>
				<input type="text" name="priority" value="${route.priority}" class="required" size="30" />
			</p>
			<p style="width:100%;">
				<label>服务名：</label>
				<span>${route.service}&nbsp;</span>
			</p>
			<p style="width:100%;">
				<label>方法名：</label>
				<input type="text" name="routemethod" value="${routemethod}" class="required" size="30" />
				<span style="width:5px;">&nbsp;</span>
				<select>
					<option value="">请选择</option>
					<c:forEach var="method" items="${methods}" varStatus="status">
						<option value="${method}">${method}</option>
					</c:forEach>
				</select>
				<span style="margin-left:130px;color:#00f;">只有Dubbo2.0.0以上版本的服务消费端支持按方法路由，多个方法名用逗号分隔，“*”代表所有方法。</span>
			</p>
			<p style="width:100%;">
				<label>匹配条件：</label>
				<span style="width:235px;">匹配</span>
				<span style="width:100px;">不匹配</span>
			</p>
			<p style="width:100%;">
				<label>消费者IP地址：</label>
				<input type="text" style="width:200px;" name="consumerHost" value="${consumerHost}" />
				<span style="width:30px;">&nbsp;</span>
				<input type="text" style="width:200px;" name="unconsumerHost" value="${unconsumerHost}" />
			</p>
			<p style="width:100%;">
				<label>消费者应用名称：</label>
				<input type="text" style="width:200px;" name="consumerApplication" value="${consumerApplication}" />
				<span style="width:30px;">&nbsp;</span>
				<input type="text" style="width:200px;" name="unconsumerApplication" value="${unconsumerApplication}" />
			</p>
			<p style="width:100%;">
				<label>消费者集群：</label>
				<input type="text" style="width:200px;" name="consumerCluster" value="${consumerCluster}" />
				<span style="width:30px;">&nbsp;</span>
				<input type="text" style="width:200px;" name="unconsumerCluster" value="${unconsumerCluster}" />
			</p>
			<p style="width:100%;">
				<label>过滤规则：</label>
				<span style="width:235px;">匹配</span>
				<span style="width:100px;">不匹配</span>
			</p>
			<p style="width:100%;">
				<label>提供者IP地址：</label>
				<input type="text" style="width:200px;" name="providerHost" value="${providerHost}" />
				<span style="width:30px;">&nbsp;</span>
				<input type="text" style="width:200px;" name="unproviderHost" value="${unproviderHost}" />
			</p>
			<p style="width:100%;">
				<label>提供者集群：</label>
				<input type="text" style="width:200px;" name="providerCluster" value="${providerCluster}" />
				<span style="width:30px;">&nbsp;</span>
				<input type="text" style="width:200px;" name="unproviderCluster" value="${unproviderCluster}" />
			</p>
			<p style="width:100%;">
				<label>提供者协议：</label>
				<input type="text" style="width:200px;" name="providerProtocol" value="${providerProtocol}" />
				<span style="width:30px;">&nbsp;</span>
				<input type="text" style="width:200px;" name="unproviderProtocol" value="${unproviderProtocol} "/>
			</p>
			<p style="width:100%;">
				<label>提供者端口：</label>
				<input type="text" style="width:200px;" name="providerPort" value="${providerPort}" />
				<span style="width:30px;">&nbsp;</span>
				<input type="text" style="width:200px;" name="unproviderPort" value="${unproviderPort}" />
			</p>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button id="closebutton" type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>