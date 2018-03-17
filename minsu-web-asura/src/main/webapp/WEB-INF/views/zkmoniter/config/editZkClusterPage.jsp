<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<script type="text/javascript">
	/**
		表单回调函数
		方法：validateCallback内部采用ajax提交方式, 参考2443行
	*/
	function editZkClusterAjaxDone(json) {
		// 获得包含表单的div, 重新加载表单请求
		$('#search_zKClusterListesContainer').loadUrl('zkmoniter/config/searchZkCluster.do?resId=${resId}');
		// 提示信息
		alertMsgByJson(json);
		// 关闭对话框
		$.pdialog.closeCurrent();
	}
	function addZkServer(){
		$("#zkServer").append("<p style='width:100%;'><label>服务器名称：</label><input type='text' class='required textInput' name='zkname'/><label>服务器ip：</label><input type='text' class='required textInput' name='zkip'/><label>服务器端口：</label><input type='text' class='required textInput' name='zkport'/>&nbsp;<input type='button' value='删除服务器' onclick='delZkServer()'/></p>");
	}
	function delZkServer(){
		$("#zkServer").find("p:last").remove();
	}
</script>
<div class="pageContent">
	<form method="post" action="zkmoniter/config/editZkCluster.do" class="pageForm required-validate" onsubmit="return validateCallback(this, editZkClusterAjaxDone)">
		<div class="pageFormContent" layoutH="58" id="zkServer">
			<input type="hidden" name="id" value="${zkCluster.id }" /> 
			<p style="width:100%;">
				<label>集群名称：</label>
				<input type="text" name="name" class="required" size="50" value="${zkCluster.name} "/>
			</p>
			<p style="width:100%;">
				<label>集群描述：</label>
				<textarea name="desc" style="width: 300px;" rows="3" cols="27">${zkCluster.desc }</textarea>
			</p>
			<p style="width:100%;">
			    <input type="button" value="添加服务器" onclick="addZkServer()"/>
			</p>
			<c:forEach var="item" items="${zkInfos}"
							varStatus="status">
				<p style="width:100%;">
			    <label>服务器名称：</label>
			    <input type="text" name="zkname" class="required" value="${item.name}"/>
			    <label>服务器ip：</label>
			    <input type="text" name="zkip" class="required" value="${item.ip}"/>
			    <label>服务器端口：</label>
			    <input type="text" name="zkport" class="required" value="${item.port}"/>
			    &nbsp;<input type='button' value='删除服务器' onclick='delZkServer()'/>
			</p>	
			</c:forEach>	
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>