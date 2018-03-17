<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	/**
		表单回调函数
		方法：validateCallback内部采用ajax提交方式, 参考2443行
	*/
	function allotRoleAjaxDone(json) {
		// 获得包含表单的div, 重新加载表单请求
		$('#search_operatorListsContainer').loadUrl('authority/operator/operatorList.do?resId=${resId }');
		// 提示信息
		alertMsgByJson(json);
		// 关闭对话框
		$.pdialog.closeCurrent();
	}
	
	/**
		自定义表单提交方法, 可以在提交前拦截
	*/
	function definedValidateCallback(form,callback){
		var paramArr = checkCallback();
		var paramStr = paramArr.join(",");
		var action = $(form).attr("action");
		var extend = "&roleIds="+paramStr;
		$(form).attr("action",action+extend);
		return validateCallback(form,callback);
	}
	
	/**
		获取选中的角色id
	*/
	function checkCallback(){
		var param = new Array();
		// 获取class 为 checked的checkbox的值, 特别注意class和: 之间有空格
		var checkbox = $(".checked :checkbox");
		$(checkbox).each(function(index, obj){
			var value = obj.value;
			if(value != '0'){
				param.push(value);
			}
		});
		return param;
	}
	
	/**
		设置选中的资源信息
	*/
	function setCheckedBox(){
		var funResIds = '${roleIds}';
		var funResIdArray = funResIds.split("|");
		var chkboxs = $("#roleTreeCheckId a");
		$(chkboxs).each(function(index, obj){ 
			var tvalue = $(obj).attr('tvalue');
			var exist = $.inArray(tvalue,funResIdArray);
			if(exist>=0){
				$(obj).attr('checked',true);
			}
		});
	}
	
	$(document).ready(function(){
		
		setCheckedBox();
	});
	
</script>
<div class="pageContent">
	<form method="post" action="authority/operator/allotRoles.do?operatorId=${operatorId }" class="pageForm required-validate" onsubmit="return definedValidateCallback(this,allotRoleAjaxDone)">
		<div id="allotRolesDivId" class="tabsContent">
			<div class="unitBox" style="float:left; display:block; overflow:auto; height:420px; width: 330px;">
				<sf:roletree roles="${roles }"></sf:roletree>
			</div>
		</div>

		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
