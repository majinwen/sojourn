<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	/**
		表单回调函数
		方法：validateCallback内部采用ajax提交方式, 参考2443行
	*/
	function allotResourceAjaxDone(json) {
		// 获得包含表单的div, 重新加载表单请求
		$('#search_roleListsContainer').loadUrl('authority/role/roleList.do?resId=${resId }');
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
		var extend = "&resIds="+paramStr;
		$(form).attr("action",action+extend)
		return validateCallback(form,callback);
	}
	
	/**
		获取选中的功能
	*/
	function checkCallback(){
		var param = new Array();
		// 获取class 为 indeterminate的checkbox的值, 特别注意class和: 之间有空格
		var indeterminatebox = $(".indeterminate :checkbox");
		$(indeterminatebox).each(function(index, obj){ 
	          param.push(obj.value);
		});
		// 获取class 为 checked的checkbox的值, 特别注意class和: 之间有空格
		var checkbox = $(".checked :checkbox");
		$(checkbox).each(function(index, obj){ 
			param.push(obj.value);
		});
		return param;
	}
	
	/**
		弃用： 获取选中的功能
	*/
	function checkCallback22(){
		var param = new Array();
		// 获取选中的checkbox, 特别注意id和: 之间有空格
		var chkboxs = $("#treecheckId :checkbox[checked]");
		$(chkboxs).each(function(index, obj){ 
	          var arr = obj.value.split("|");
	          if(arr.length != 1){
	        	  param.push(arr[0]);
	          } 
		});
		return param;
	}
	
	/**
		设置选中的资源信息
	*/
	function setCheckedBox(){
		var funResIds = '${funResIds}';
		var funResIdArray = funResIds.split("|");
		var chkboxs = $("#treecheckId a");
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
	<form method="post" action="authority/role/allotResource.do?roleId=${roleId }" class="pageForm required-validate" onsubmit="return definedValidateCallback(this,allotResourceAjaxDone)">
		<div id="allotResourceDivId" class="tabsContent">
			<div class="unitBox" style="float:left; display:block; overflow:auto; height:420px; width: 300px;">
				<sf:resourcetree resources="${resources }" treeCheck="true"></sf:resourcetree>
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
