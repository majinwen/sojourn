<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script type="text/javascript">
	/**
		表单回调函数
		方法：validateCallback内部采用ajax提交方式, 参考2443行
	*/
	function resourceAjaxDone(json) {
		// 重新加载对应的div请求
		$('#resourceTreeDivId').loadUrl('authority/resource/resourceTree.do?resId=${resId }');
		// 提示信息
		alertMsgByJson(json);
	}
	
</script>
<div id="resourceTreeDivId" class="tabsContent">
	<div class="unitBox" style=" float:left; display:block; margin:10px; overflow:auto; width:300px; height:550px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
		<sf:resourcetree resources="${resources }" treeCheck="false"></sf:resourcetree>
	</div>
	<div id="jbsxBox3" class="unitBox" style="margin-left:20px;">
		<h2 class="contentTitle">资源信息</h2>
			<form method="post" action="authority/resource/execute.do" class="pageForm required-validate" onsubmit="return validateCallback(this,resourceAjaxDone)">
				<div class="pageFormContent nowrap" layoutH="97">
				    <input type="hidden" id="resIdId" name="resId" value=""/>
				    <input type="hidden" id="resLevelId" name="resLevel" value="1"/>
				    <input type="hidden" id="parentIdId" name="parentId" value="0"/>
					<dl>
						<dt>资源名称：</dt>
						<dd>
							<input type="text" id="resNameId" name="resName" style="width: 300px;" class="required"/>
						</dd>
					</dl>
					<dl>
						<dt>资源类型：</dt>
						<dd>
						    <span id="resType1DivId">
							<input type="radio" id="resType1Id" name="resType" value="01"  checked="checked"/>菜单
					        </span>
					        <span id="resType2DivId">
					        <input type="radio" id="resType2Id" name="resType" value="02" />功能
					        </span>
						</dd>
					</dl>
					<dl id="resUrlDLId">
						<dt>资源访问连接：</dt>
						<dd>
							<input type="text" id="resUrlId" name="resUrl" style="width: 300px;" class="required" value=""/>
						</dd>
					</dl>
					<dl id="isAdminMenuDLId">
						<dt>管理员默认：</dt>
						<dd>
							<input type="radio" id="isAdminMenu1Id" name="isAdminMenu" checked="checked" value="1"/>是
					        <input type="radio" id="isAdminMenu2Id" name="isAdminMenu" value="0"/>否
						</dd>
					</dl>
					<dl>
						<dt>显示顺序号：</dt>
						<dd>
							<input type="text" id="resOrderId" name="resOrder" style="width: 300px;" class="required"/>
						</dd>
					</dl>
					<dl>
						<dt>资源描述：</dt>
						<dd>
							<textarea id="resDescId" name="resDesc" style="width: 300px;"  rows="3" cols="27"></textarea>
						</dd>
					</dl>
				</div>
				<div class="formBar">
					<ul>
					    <li><span id="executeTypeId1DivId">
							<input type="radio" id="executeTypeId1Id" name="executeTypeName" value="add" checked="checked" onclick="addExecuteClick();"/>新增下级
							</span>
						</li>
					    <li>
					    <span id="executeTypeId2DivId" style="display: none">
					        <input type="radio" id="executeTypeId2Id" name="executeTypeName" value="update" onclick="updDelExecuteClick();"/>修改
					        </span>
					    </li>
					    <li>
					    <span id="executeTypeId3DivId" style="display: none">
					        <input type="radio" id="executeTypeId3Id" name="executeTypeName" value="delete" onclick="updDelExecuteClick();"/>删除
					        </span>
					    </li>
						<li>
						    <sf:button resId="${resId }" url="authority/resource/execute.do" title="提交"></sf:button>
 						</li>
					</ul>
				</div>
			</form>
			
		</div>
</div>
	

<script>


	/**
		修改删除单选按钮切换
	*/
	function updDelExecuteClick(){
		var resId = $("#resIdId").val();
		var array = ['resType','isAdminMenu','resUrlParam','resFunctionType'];// 这些字段需要特殊处理或者根本不需要
		var dataObjJson = $.parseJSON($("#tree"+resId).val());//转换为json对象 
		$.each(dataObjJson,function(name,value){
			if($.inArray(name,array) == -1){
				$("#"+name+"Id").attr("value",value);
			}else{
			   if('resType' == name){
				   if(value == '02'){
					   // 功能下面不能添加,没有类型选择, 只能修改和删除
					   $("#resType1DivId").css("display","none");// 隐藏菜单类型
					   $("#resType2DivId").css("display","");
					   $("#executeTypeId1DivId").css("display","none");
				   }else{
					   $("#resType2DivId").css("display","none");
					   $("#resType1DivId").css("display","");
					   $("#executeTypeId1DivId").css("display","");
				   }
				   for(var i=1; i<=2; i++){
					   var val = $("#resType"+i+"Id").val();
					   if(val == value){
						   $("#resType"+i+"Id").attr("checked",true);
					   }   
				   }
			   }
			   if('isAdminMenu' == name){
				   for(var i=1; i<=2; i++){
					   var val = $("#isAdminMenu"+i+"Id").val();
					   if(val == value){
						   $("#isAdminMenu"+i+"Id").attr("checked",true);
					   }   
				   }
			   }
			}
		});
		
	}

	/**
		点击新增事件, 初始化表单
	*/
	function addExecuteClick(){
		$("#resType2DivId").css("display","");
		$("#executeTypeId1Id").attr("checked",true);// 选中新增操作
		$("#resType1Id").attr("checked",true);
		$("#resType2Id").attr("checked",false);
		$("#resDescId").attr("value","");
		$("#resOrderId").attr("value","");
		$("#resNameId").attr("value","");
		var resLevel = new Number($("#resLevelId").val());
		$("#resLevelId").attr("value",resLevel+1);
		$("#resUrlId").attr("value","");
		$("#parentIdId").attr("value",$("#resIdId").val());
	}
	
	function root(){
		$("#resType1DivId").css("display","");
		$("#executeTypeId1DivId").css("display","");
		$("#resType2DivId").css("display","");
		$("#executeTypeId2DivId").css("display","none");
		$("#executeTypeId3DivId").css("display","none");
		$("#executeTypeId1Id").attr("checked",true);// 选中新增操作
		$("#resType1Id").attr("checked",true);
		$("#resType2Id").attr("checked",false);
		$("#resDescId").attr("value","");
		$("#resOrderId").attr("value","");
		$("#resUrlId").attr("value","");
		$("#resNameId").attr("value","");
		$("#parentIdId").attr("value",0);
		$("#resLevelId").attr("value",1);
	}



    /**
    	点击节点事件
    */
	function nodeClick(obj){
    	var id = obj.id;
    	if(id == 'tree0'){
    		root();
    		return false;
    	}
    	$("#executeTypeId2Id").attr("checked",true);// 选中修改操作
    	var array = ['resType','isAdminMenu','resUrlParam','resFunctionType'];// 这些字段需要特殊处理或者根本不需要
		var dataObjJson = $.parseJSON(id);//转换为json对象 
		var leaf = false; // 是不是叶子
		$.each(dataObjJson,function(name,value){
			if(name=="leaf" && value){
				leaf = value;
			}else{
				if($.inArray(name,array) == -1){
					$("#"+name+"Id").attr("value",value);
				}else{
				   if('resType' == name){
					   if(value == '02'){
						   // 功能下面不能添加,没有类型选择, 只能修改和删除
						   $("#resType1DivId").css("display","none");// 隐藏菜单类型
						   $("#resType2DivId").css("display","");
						   $("#executeTypeId1DivId").css("display","none");
					   }else{
						   $("#resType2DivId").css("display","none");
						   $("#resType1DivId").css("display","");
						   $("#executeTypeId1DivId").css("display","");
					   }
					   for(var i=1; i<=2; i++){
						   var val = $("#resType"+i+"Id").val();
						   if(val == value){
							   $("#resType"+i+"Id").attr("checked",true);
						   }
					   }
				   }
				   if('isAdminMenu' == name){
					   for(var i=1; i<=2; i++){
						   var val = $("#isAdminMenu"+i+"Id").val();
						   if(val == value){
							   $("#isAdminMenu"+i+"Id").attr("checked",true);
						   }
					   }
				   }
				}
			}
		});
		if(leaf){
			$("#executeTypeId3DivId").css("display","");
		}else{
			$("#executeTypeId3DivId").css("display","none");
		}
		$("#executeTypeId2DivId").css("display","");
		return false;
	}
</script>
