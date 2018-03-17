<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html style="height: auto;">
<head lang="zh">
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
	<title>预订人信息</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles.css${VERSION}001"> 
<body  style="height: auto;" >
	<header class="header">

		<c:if test="${sourceOrg == 1}">
			<a href="javascript:;" onclick="window.WebViewFunc.popToParent();" class="goback"></a>
		</c:if>
		<c:if test="${sourceOrg == 2}">
			<a href="javascript:;" onclick="window.popToParent();" class="goback"></a>
		</c:if>
		<c:if test="${sourceOrg == 0}">
			<a href="javascript:;" onclick="history.back();" class="goback"></a>
		</c:if>
		<h1>预订人信息</h1>
	</header>
	
	<div class="main">
		<div class="bgF boxP075">
			<div class="ydrTop clearfix">
				<div class="img"><img src="${customerVo.userPicUrl}"></div>
				<p>${customerVo.nickName }</p>
			</div>
			<div class="ydrInfoList">
				<ul class="clearfix">
					<li><span>真实姓名：</span><em>${empty customerVo ? "":customerVo.realName}</em></li>
					<li><span class="ws">性</span>别：<em>${empty customerVo ? "" : customerVo.customerSex}</em></li>
					<li><span class="ws">学</span>历：<em>${empty education ? "" : education}</em></li>
					<li><span class="ws">职</span>业：<em>${empty customerVo ? "":customerVo.customerJob}</em></li>
					<li><span class="ws">城</span>市：<em>${empty cityName ? "":cityName}</em></li>
					<c:if test="${isShowMes eq true}"><li><span>联系方式：</span><em>${empty customerVo ? "":customerVo.showMobile}</em></li></c:if>
				</ul>
			</div>
		</div>
		<!-- 新调整 -->
		<div id="evaHistoryList" class="s_evaHistoryList">
			<h2 class="s_evaItemTt">点评记录</h2>
			<!-- 点评内容 -->
			
		</div>
		<!-- /新调整 -->
		<div id="pageView">
			正在加载
		</div>
	</div><!--/main-->
	
	<input type="hidden" value="${customerVo.uid}" id="customerVOUid">
	<input type="hidden" value="${orderSn}" id="orderSn">
	<input type="hidden" value="${versionCodeInit}" id="versionCodeInit">	
	<input type="hidden" value="${sourceType}" id="sourceType">		
	
<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/order/contactDetail.js${VERSION}001"></script>
</body>
</html>
