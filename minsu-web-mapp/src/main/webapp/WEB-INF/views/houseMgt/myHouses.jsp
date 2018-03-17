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
	<title>我的房源</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}005">
	
</head>
  
<body>
<header class="header">
	<h1>我的房源</h1>
	<a href="${basePath}auth/${loginUnauth }/init" class="header_r" id="deploy">发布房源</a>
</header>

<div class="main" id="main">
	<div id="mainScroll">
		<ul class="houseList" id="houseList">
		</ul>
	</div>
			
			
</div>

<!--/bodys-->
<input type="hidden" id="landlordUid" value="${landlordUid}"/>
<input type="hidden" id="picResourceUrl" value="${picResourceUrl}">
<input type="hidden" id="picSize" value="${picSize}">
<input type="hidden" id="staticUrl" value="${staticResourceUrl}">
<input type="hidden" id="sourceType" value="${sourceType }">

<%@ include file="../common/bottom.jsp" %>
<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}006"></script>
<script src="${staticResourceUrl}/js/house/myHouses.js${VERSION}008" type="text/javascript"></script>
</body>
</html>
