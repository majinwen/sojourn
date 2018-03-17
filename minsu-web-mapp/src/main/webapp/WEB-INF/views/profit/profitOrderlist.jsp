<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/slick.css${VERSION}001">
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">
	  
</head>
<body style="height:auto;">
    <header class="header">

		<c:if test="${sourceOrg == 1 }">
			<a href="javascript:;" onclick="window.WebViewFunc.popToParent();" class="goback"></a>
		</c:if>
		<c:if test="${sourceOrg == 2 }">
			<a href="javascript:;" onclick="window.popToParent();" class="goback"></a>
		</c:if>
		<c:if test="${sourceOrg == 0 }">
			<a href="javascript:;" onclick="history.back();" class="goback"></a>
		</c:if>

		<h1>${houseRoomName}</h1>
	</header>
    <div class="main">
		<div class="profit_tab">
			<ul class="profit_tab_ul clearfix" id="orderType">
				<li class="active" id="waiting"><a href="javascript:;">实际收益</a></li>
				<li id="doing"><a href="javascript:;">预计收益</a></li>
			</ul>
			<div id="slider" class="slider slick_box2 clearfix">
				<c:forEach items="${mouthMap}" var="mymap" >
				<div>
					<input type="hidden" name="month" value="${mymap.key}" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney"  class="money"></span></p>
						<p> <span class="date" data-date="${mymap.key}">${mymap.key}</span>月收益 </p>
					</div>
				</div>
				</c:forEach>
			    <%--<div>
			        <input type="hidden" name="month" value="1" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney"  class="money"></span></p>
						<p> <span class="date" data-date="1">1</span>月收益 </p>
					</div>
				</div>
			    <div>
			        <input type="hidden" name="month" value="2" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney"  class="money"></span></p>
						<p> <span class="date" data-date="2">2</span>月收益 </p>
					</div>
				</div>
			    <div>
			        <input type="hidden" name="month" value="3" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney"  class="money"></span></p>
						<p> <span class="date" data-date="3">3</span>月收益 </p>
					</div>
				</div>
			    <div>
			        <input type="hidden" name="month" value="4" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney"  class="money"></span></p>
						<p> <span class="date" data-date="4">4</span>月收益 </p>
					</div>
				</div>
			    <div>
			        <input type="hidden" name="month" value="5" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney"  class="money"></span></p>
						<p> <span class="date" data-date="5">5</span>月收益 </p>
					</div>
				</div>
				<div>
				    <input type="hidden" name="month" value="6" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney" class="money"></span></p>
						<p> <span class="date" data-date="6">6</span> 月收益</p>
					</div>
				</div>
				<div>
				    <input type="hidden" name="month" value="7" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney" class="money"></span></p>
						<p> <span class="date" data-date="7">7</span> 月收益</p>
					</div>
				</div>
				<div>
				    <input type="hidden" name="month" value="8" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney" class="money"></span></p>
						<p> <span class="date" data-date="8">8</span> 月收益</p>
					</div>
				</div>
				<div>
				    <input type="hidden" name="month" value="9" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney" class="money"></span></p>
						<p> <span class="date" data-date="9">9</span> 月收益</p>
					</div>
				</div>
				<div>
				    <input type="hidden" name="month" value="10" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney" class="money"></span></p>
						<p> <span class="date" data-date="10">10</span> 月收益</p>
					</div>
				</div>
				<div>
				    <input type="hidden" name="month" value="11" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney" class="money"></span></p>
						<p> <span class="date" data-date="11">11</span> 月收益</p>
					</div>
				</div>
				<div>
				    <input type="hidden" name="month" value="12" />
					<div class="profit_tab_money">
						<p><span>￥</span><span name="realMoney" class="money"></span></p>
						<p> <span class="date" data-date="12">12</span> 月收益</p>
					</div>
				</div>--%>
			</div>
		</div><!--/tabs-->
		<div class="tabCon">
			<div class="con">
				<ul class="profitList" id="orderList">
				</ul>
				
			</div>
		 </div>
		 <div id="pageView">正在加载</div>
	</div><!--/main-->


<input type="hidden" value="${staticResourceUrl}" id="staticResourceUrl">
<input type="hidden" value="${houseFid}" id="houseFid">
<input type="hidden" value="${rentWay}" id="rentWay">
<input type="hidden" value="${roomFid}" id="roomFid">
<input type="hidden" value="${month}" id="curMonth">
    <input type="hidden" value="${versionCodeInit}" id="versionCode">
    <input type="hidden" value="${sourceType}" id="sourceType">

<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl}/js/slick.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/profit/profitOrder.js${VERSION}0014"></script>

</body>
</html>
