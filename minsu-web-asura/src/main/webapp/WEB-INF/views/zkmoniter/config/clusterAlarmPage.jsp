<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>
<script type="text/javascript">
	/**
		表单回调函数
		方法：validateCallback内部采用ajax提交方式, 参考2443行
	*/
	function addZkClusterAjaxDone(json) {
		// 获得包含表单的div, 重新加载表单请求
		$('#search_zKClusterListesContainer').loadUrl('zkmoniter/config/searchZkCluster.do?resId=${resId}');
		// 提示信息
		alertMsgByJson(json);
		// 关闭对话框
		$.pdialog.closeCurrent();
	}
</script>
<div class="pageContent">
	<form method="post" action="zkmoniter/config/clusterAlarm.do" class="pageForm required-validate" onsubmit="return validateCallback(this, addZkClusterAjaxDone)">
	<input type="hidden" name="id" value="${alarm.id }">
	<input type="hidden" name="zkClusterId" value="${ alarm.zkClusterId}">
		<div class="pageFormContent" layoutH="58" id="zkServer">
			<p style="width:100%;">
				<label style="width:30%;" >zookeeper节点生命检测最大延时：</label>
				<input type="text" name="maxDelayOfCheck" class="required" size="10" value="${alarm.maxDelayOfCheck }"/>s
			</p>
			<p style="width:100%;">
				<label style="width:30%;">cpu峰值报警：</label>
				<input type="text" name="maxCpuUsage" class="required" size="10" value="${alarm.maxCpuUsage }"/>%
			</p>
			<p style="width:100%;">
				<label style="width:30%;">Memory峰值报警：</label>
				<input type="text" name="maxMemoryUsage" class="required" size="10" value="${alarm.maxMemoryUsage }"/>%
			</p>
			<p style="width:100%;">
				<label style="width:30%;">Load峰值报警：</label>
				<input type="text" name="maxLoad" class="required" size="10" value="${alarm.maxLoad }"/>
			</p>
			<p style="width:100%;">
				<label style="width:30%;">单机连接数 峰值报警：</label>
				<input type="text" name="maxConnection" class="required" size="10" value="${alarm.maxConnection }"/>
			</p>	
			<p style="width:100%;">
				<label style="width:30%;">单机watcher数 峰值报警：</label>
				<input type="text" name="maxWatch" class="required" size="10" value="${alarm.maxWatch }"/>
			</p>
			<p style="width:100%;">
				<label style="width:30%;">设置报警手机号：</label>
				<input type="text" name="phoneList" class="required" size="50" value="${alarm.phoneList }"/>&nbsp;(多个用“;”隔开)
			</p>
			<p style="width:100%;">
				<label style="width:30%;">设置报警邮箱地址：</label>
				<input type="text" name="mailList" class="required" size="50" value="${alarm.mailList }"/>&nbsp;(多个用“;”隔开)
			</p>	
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>