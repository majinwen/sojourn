<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
   	<meta name="applicable-device" content="mobile">
    <meta content="fullscreen=yes,preventMove=yes" name="ML-Config">    
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
    <meta name="format-detection" content="telephone=no">
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
	<title>管理密码</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
	
</head>
<body>
	<header class="header">
		<a href="${basePathHttps }smartLock/${loginUnauth }/showPanel?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid}&rentWay=${rentWay}" class="goback"></a>	
		<h1>动态密码</h1>
	</header>
	<form accept="" method="post" class="checkFormChild" id="form">	
		<input name="houseBaseFid" type="hidden" value="${houseBaseFid}"/>
		<div class="main myCenterListNoneA myCenterListGeren guanlimimaList2">
		<ul class="myCenterList ">
			<li class="clearfix">
				接收动态密码手机号
				<input type="tel" placeholder="请填写接收手机号" id="tel" name="phoneNum" value="" maxlength="11">			
			</li>
		</ul>
		</div>
		<div class="boxP075 mt85 mb85">
			<input type="button" value="确定" class="org_btn disabled_btn" id="saveBtn">
		</div>
	</form>
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<script type="text/javascript">
	(function($){
		$("input").keyup(function(){
			keepFloat(this,0);
		})
		$("#saveBtn").click(function(){
			if($(this).hasClass("disabled_btn")){
				return;
			}
			$(this).addClass("disabled_btn");
			var telVal = $("#tel").val();
	    	if(!checkMobile(telVal)){
	    		showShadedowTips("请输入正确的手机号",1);
	    		$(this).removeClass("disabled_btn");
	    		return;
	    	}
	    	$.post("${basePathHttps}smartLock/${loginUnauth}/sendDynamicPass",$("#form").serialize(),function(response){
	    		if(response.code == 0){
	    			layer.open({
				    	content: '<div class="box" >您申请的态密码已发送至您填写的接收者手机，请注意查看。</div>',
				    	btn:['确定'],
				    	yes: function(){
				        	layer.closeAll();
				        	window.location.href="${basePathHttps }smartLock/${loginUnauth }/showPanel?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid}&rentWay=${rentWay}";
				        }
					});
	    		}else{
	    			layer.open({
				    	content: '<div class="box" >您申请的动态密码发送失败，请重新申请。</div>',
				    	btn:['确定'],
				    	yes: function(){
				        	layer.closeAll();
				        	$(this).removeClass("disabled_btn");
				        }
					});
	    		}
	    	},'json');
		});
	}(jQuery));
	</script>
</body>
</html>
