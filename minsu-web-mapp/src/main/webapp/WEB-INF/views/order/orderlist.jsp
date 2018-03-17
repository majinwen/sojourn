<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
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
	<title>订单管理</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles.css${VERSION}001">
</head>
<body>


	<header class="header">
		<h1>订单管理</h1>
	</header>
	
	<div class="main">
		<div class="tabs">
			<ul class="tabTit clearfix" id="orderType">
				<li class="active" id="waiting">待处理</li>
				<li id="doing">进行中</li>
				<li id="done">已结束</li>
			</ul>
			<div class="tabCon">
				<div class="con"  id="conList" style="">
					<ul class="dingdanList" id="orderList">
						
					</ul>
					

				</div><!--/con-->
				
			</div><!--/tabCon-->
		</div><!--/tabs-->
	
	</div><!--/main-->

<%@ include file="../common/bottom.jsp" %>
<input type="hidden" value="${staticResourceUrl}" id="staticResourceUrl">
<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}006"></script>
<script src="${staticResourceUrl}/js/order/lanOrder.js${VERSION}006"></script>

</body>
</html>
