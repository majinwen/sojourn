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
	<title>智能门锁</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
	
</head>
<body class="bgF">
			<header class="header">
                <c:choose>
                    <c:when test="${sourceType eq 1 && versionCode}">
                        <a href="javascript:;" onclick="window.WebViewFunc.popToParent();" class="goback"></a>
                    </c:when>
                    <c:when test="${sourceType eq 2 && versionCode}">
                        <a href="javascript:;" onclick="window.popToParent();" class="goback"></a>
                    </c:when>
                    <c:otherwise>
<<<<<<< HEAD
                        <a href="${basePath }houseInput/43e881/goToHouseUpdate?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid}&rentWay=${rentWay}"
=======
                        <a href="${basePathHttps }houseInput/43e881/goToHouseUpdate?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid}&rentWay=${rentWay}"
>>>>>>> refs/remotes/origin/test
                           id="goback" class="goback"></a>
                    </c:otherwise>
                </c:choose>
				<h1>智能门锁</h1>
			</header>
			<div class="main">	
				<div class="mima">
					<div class="tips">
						<p>	智能门锁状态离线，请尽快联系智能门锁厂商维修</p>
						<p>	400-641-4123</p>	
					</div>	
					<div class="rBox">
						<div id="refreshStatus" class="c lixian">	
							<h2>离线</h2>
							<p>电池电量${power}%</p>
							<span class="r"></span>
						</div>
						
					</div>
					<div class="bottomBtns">	
<<<<<<< HEAD
						<a href="${basePath }smartLock/${loginUnauth }/getDynamicPass?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid}&rentWay=${rentWay}" class="s">动态密码</a>
						<a href="http://minsustatic.ziroom.com/mapp/html/door_lock_instructions.html" class="w">智能锁说明</a>
=======
						<a href="${basePathHttps }smartLock/${loginUnauth }/getDynamicPass?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid}&rentWay=${rentWay}" class="s">动态密码</a>
						<a href="${staticResourceUrl}/mapp/html/door_lock_instructions.html" class="w">智能锁说明</a>
>>>>>>> refs/remotes/origin/test
					</div>
				</div>
			</div>
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	
	<script type="text/javascript">
		(function($){
			$("#refreshStatus").click(function(){
				window.location.reload();
			});
		}(jQuery))
	</script>
</body>
</html>
