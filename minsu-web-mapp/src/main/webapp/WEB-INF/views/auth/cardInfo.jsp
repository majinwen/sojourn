<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="applicable-device" content="mobile">
	<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">        
	<script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="yes" name="apple-mobile-web-app-capable"/>
	<meta content="yes" name="apple-touch-fullscreen"/>
	<title>资质认证</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">
	
</head>
<body>

<form id="customerBaseInfo" accept="" method="">	
	<div class="main myCenterListNoneA">
<header class="header">
	<a href="javascript:;" id="goback" class="goback" onclick="history.go(-1)"></a>
	<h1>身份信息</h1>
</header>
<ul class="myCenterList">
	<li class="clearfix bor_b no_icon">
		姓名
		<input id="realName"  name="realName" value="${customer.realName}" placeholder="请与证件姓名保持一致" class="ipt"  />
	</li>
	<li class="clearfix  bor_b">
		证件类型
		<input type="text" placeholder="请选择" id="idStyle"  value="" readonly="readonly">
		<select onchange="setValToInput(this,'idStyle')" id="idStyleList" name="idType">
			<option value="" selected="selected">请选择</option>
			<c:forEach var="idType" items="${idTypeList}">
			   <option value="${ idType.code}">${idType.name}</option>
			</c:forEach>
			
		</select>
		<span class="icon_r icon" ></span>
	</li>
	<li class="clearfix no_icon">
		证件号码
		<input id="idCode" name="idNo" value="${customer.idNo}" placeholder="请填写证件号码" class="ipt"  />
	</li>

</ul>

</div><!--/main-->
<div class="boxP075 mt85 mb85">
	<input type="button" id="submitBtn" value="确定" class="disabled_btn btn_radius">
</div>
</form> 
<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/layer/layer.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script type="text/javascript">
	$(function(){
		
		var selectedDefault = function(selectName,value){
			$("#"+selectName+" option[value='"+value+"']").attr("selected","selected");
			$("#idStyle").val($("#"+selectName+" option[value='"+value+"']").html());
		}
		
		selectedDefault('idStyleList','${customer.idType}');
		
		$(".ipt").each(function(){
			$(this).keyup(function(){
				checkIpt();
			})
		})
		$("#idStyleList").change(function(){
			checkIpt();
		})
		function checkIpt(){
			for(var i = 0 ; i < $("input").length ; i ++){
					if($.trim($("input").eq(i).val()).length == 0){
						$("#submitBtn").addClass("disabled_btn").removeClass("org_btn");
						return false;
					}else{
						if($("#idStyle").val() == "请选择"){
							$("#submitBtn").addClass("disabled_btn").removeClass("org_btn");
							return false;
						}else{
							$("#submitBtn").removeClass("disabled_btn").addClass("org_btn");
						}
					}
					
			}
		}
		$("#submitBtn").click(function(){
			if($(this).hasClass("disabled_btn")){
				return false;
			}
			
			if($("#idStyleList option:selected").val() == 1){
				if(!isCardNo($("#idCode").val())){
					showShadedowTips("身份证号格式不正确",1);
					return false;
				}
			}
			if($("#idStyleList option:selected").val() == 2){
				if(!isPassport($("#idCode").val())){
					showShadedowTips("护照格式不正确",1);
					return false;
				}
			}
			
            if($("#idStyleList option:selected").val() == 13){
            	if(!isHKMacao($("#idCode").val())){
					showShadedowTips("港澳通行证格式不正确",1);
					return false;
				}
			}
            if($("#idStyleList option:selected").val() == 6){
            	if(!isTaiwan($("#idCode").val())){
					showShadedowTips("台湾通行证格式不正确",1);
					return false;
				}
			}
            
			if($("#realName").val() == "" || $("#realName").val() == undefined || $("#realName").val() == null){
				showShadedowTips("请填写真实姓名",1);
				return false;
			}
			
			/** 保存后回调函数 */
			var saveCusInfoCallBack = function(result){
				if(result.code == 0 ){
					showShadedowTips("保存成功",1);
					window.location.href ='${basePath}'+ "auth/43e881/init?checkFlag=1";	
				}else{
					showShadedowTips("保存失败",1);
				}
				
			}
			
			/** 保存后路径  */
			var saveCusInfoUrl= SERVER_CONTEXT+"auth\/"+LOGIN_UNAUTH+"/saveBaseInf";
			/** 保存后参数  */
			var params = $("#customerBaseInfo").serialize();
			/** 保存方法 */
			CommonUtils.ajaxPostSubmit(saveCusInfoUrl,params,saveCusInfoCallBack);
			
		})
		
	})
</script>
</body>
</html>