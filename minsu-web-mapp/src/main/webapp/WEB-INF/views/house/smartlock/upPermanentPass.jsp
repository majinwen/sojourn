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
		<h1>管理密码</h1>
	</header>
	<form accept="" method="post" class="checkFormChild" id="form">	
	<input name="houseBaseFid" type="hidden" value="${houseBaseFid}"/>
	<div class="main myCenterListNoneA myCenterListGeren guanlimimaList">
		<ul class="myCenterList ">
			<li class="clearfix bor_b">
				管理密码
				<input type="tel" placeholder="请填写管理密码" name="slpass" id="pas_1" maxlength="6" value="">			
			</li>
			<li class="clearfix">
				确认管理密码
				<input type="tel" placeholder="请再次填写管理密码" name="reSlPass" id="pas_2" maxlength="6" value="">			
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
			var pas_1 = $("#pas_1").val();
	    	var pas_2 = $("#pas_2").val();
	    	if(pas_1!==pas_2){
	    		showShadedowTips("两次密码输入不一致，请重新输入",1);
	    		$(this).removeClass("disabled_btn");
	    		return;
	    	}
	    	$.post("${basePathHttps}smartLock/${loginUnauth}/savePermanentPass",$("#form").serialize(),function(response){
	    		if(response.code == 0){
	    			layer.open({
				    	content: '<div class="box" >您申请修改管理密码的请求已发送，密码修改成功后自动更新智能门锁页面，请注意查看</div>',
				    	btn:['确定'],
				    	yes: function(){
				        	layer.closeAll();
				        	window.location.href="${basePathHttps }smartLock/${loginUnauth }/showPanel?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid}&rentWay=${rentWay}";
				        }
					});
	    		}else if(response.code == 1){
	    			showShadedowTips(response.msg,1);
	    			$(this).removeClass("disabled_btn");
	    		}else{
	    			showShadedowTips("操作失败",1);
	    		}
	    	},'json');
			
		});
	}(jQuery));
	</script>
</body>
</html>
