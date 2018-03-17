<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="zh" style="font-size: 60px;">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
   	<meta name="applicable-device" content="mobile">
    <meta content="fullscreen=yes,preventMove=yes" name="ML-Config">    
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="initial-scale=0.3333333333333333, maximum-scale=0.3333333333333333, minimum-scale=0.3333333333333333, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
	<script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
	<title>日历详情</title> 
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
	
<link href="${staticResourceUrl }/js/layer/need/layer.css" type="text/css" rel="styleSheet" id="layermcss">
</head>
<body>
	<header class="header">
	
		<c:if test="${!empty sourceType && sourceType== 1 && sessionScope.versionCode}">
			<a href="javascript:;" onclick="window.WebViewFunc.popToParent();" class="goback"></a>
		</c:if>
		<c:if test="${!empty sourceType && sourceType== 2 && sessionScope.versionCode}">
			<a href="javascript:;" onclick="window.popToParent();" class="goback"></a>
		</c:if>
		<c:if test="${empty sourceType || ( sourceType!= 1 && sourceType!= 2 ) || (!empty sessionScope.versionCode && !sessionScope.versionCode)}">
			<c:if test="${backFalg == 1 }">
				<a href="${basePath }orderland/${loginUnauth}/showlist" class="goback"></a>
			</c:if>
			<c:if test="${backFalg != 1 }">
				<a href="${basePath }houseMgt/${loginUnauth}/myHouses" class="goback"></a>
			</c:if>		
		</c:if>
		
		<!-- <a href="javascript:;" id="setWeekPriceBtn" class="header_r">周末价格</a> -->
		<c:if test="${zkSysValue != 1}">
			<a href="javascript:;" id="setPriceBtn" class="header_r">价格设置</a>
		</c:if>

		<h1>日历详情</h1>
	</header>

	<div class="main">
		<div class="calendarTop clearfix">
			
				<div class="img"><img src="${tenantHouseDetailVo.defaultPic }"></div>
				<div class="txt">
					<h3>${tenantHouseDetailVo.houseName }</h3>
					<p class="star">
						<b id="myCalendarStar" data-val="${tenantHouseDetailVo.gradeStar}">
							<i class="s"></i><i class="s"></i><i class="s"></i><i class="s"></i><i class="s"></i>
						</b> 
						<c:if test="${tenantHouseDetailVo.evaluateCount > 0}">
						  <span>${tenantHouseDetailVo.gradeStar}</span>
						</c:if>
					</p>
					<p>地址：${tenantHouseDetailVo.houseAddr}</p>
				</div>
		</div><!--/calendarTop-->

		<div id="mainBox">	
			<div class="calendarBox" id="calendarBox">
			</div><!--/calendarBox-->
		
		</div>
	</div><!--/main-->



<ul class="stateTips clearfix" id="stateTips">
	<li>已出租</li>
	<li>设为已租</li>
	<li>系统屏蔽</li>
</ul>
<ul class="stateEvents clearfix" id="stateEvents">
	<li id="modifyBtn"><span class="icon icon_modify">修改价格</span></li>
	<li id="eventsBtn" style="display: list-item;"><span class="icon icon_events">修改房态</span></li>
</ul>

<input type="hidden" id="houseBaseFid" value="${houseBaseFid}">
<input type="hidden" id="houseRoomFid" value="${houseRoomFid}">
<input type="hidden" id="rentWay" value="${rentWay}">
<input type="hidden" id="startDate" value="${startDate}">
<input type="hidden" id="priceLow" value="${priceLow}">
<input type="hidden" id="priceHigh" value="${priceHigh}">
<input type="hidden" id="tillDate" value="${tillDate}">

<input type="hidden" id="setSpecialPrice" value="${basePathHttps}houseMgt/${loginUnauth }/setSpecialPrice">
<input type="hidden" id="lockHouse" value="${basePathHttps}houseMgt/${loginUnauth }/lockHouse">
<input type="hidden" id="unlockHouse" value="${basePathHttps}houseMgt/${loginUnauth }/unlockHouse">
<input type="hidden" id="leaseCalendar" value="${basePathHttps}houseMgt/${loginUnauth }/leaseCalendar">
<%-- <input type="hidden" id="setWeekSpecialPrice" value="${basePath}houseMgt/${loginUnauth }/setWeekSpecialPrice"> --%>
<input type="hidden" id="getWeekSpecialPrice" value="${basePathHttps}houseMgt/${loginUnauth }/getWeekPrice">

<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
<!-- TODO: -->
<script type="text/javascript" src="//static8.ziroom.com/fecommon/library/bscroll/bscroll.min.js"></script>
<script src="${staticResourceUrl}/js/calendar/calendarDetail.js${VERSION}002"></script>	
<script type="text/javascript">
	$("#setPriceBtn").click(function(){
		// flag 0:发布房源 1:修改房源 2:修改客户电话 3:修改可选信息 4:修改日历
		window.location.href = "${basePathHttps}houseIssue/${loginUnauth }/toPriceDetail?houseBaseFid=${houseBaseFid}&flag=4&houseRoomFid=${houseRoomFid}&rentWay=${rentWay}";
	});
</script>
</body></html>
