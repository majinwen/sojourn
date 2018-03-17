<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	<title>我的收益</title>
	<link href="//cdn.bootcss.com/Swiper/3.4.2/css/swiper.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">
	
</head>
  
<body>


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
	<h1>我的收益</h1>
</header>

<div class="main">
    <div class="sliderBox">
    	<input type="hidden" id="curMonth" name="curMonth" value="${curMonth}">
		<div id="slider" class="swiper-container slick_box clearfix">
			<div class="swiper-wrapper">
			<c:forEach items="${mouthMap}" var="mymap" >
			<div class="swiper-slide">
				<div class="date" data-date="${mymap.key}"><fmt:formatDate value="${mymap.value}" type="both" pattern="yyyy年MM月收益"/></div>
				<div class="slick_item">
					<input type="hidden" name="month" value="${mymap.key}" />
					<ul class="slick_item_ul">
						<li><p>实际收益 <span>￥</span><span id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益 <span>￥</span><span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>
			</c:forEach>
		    <%--<div>
				<div class="date" data-date="1">2016年1月收益</div>
				<div class="slick_item">
				<input type="hidden" name="month" value="1" />
					<ul class="slick_item_ul">
						<li><p>实际收益 <span>￥</span><span id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益 <span>￥</span><span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>
			<div>
				<div class="date" data-date="2">2016年2月收益</div>
				<div class="slick_item">
				<input type="hidden" name="month" value="2" />
					<ul class="slick_item_ul">
						<li><p>实际收益 <span>￥</span><span id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益 <span>￥</span><span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>
			<div>
				<div class="date" data-date="3">2016年3月收益</div>
				<div class="slick_item">
				<input type="hidden" name="month" value="3" />
					<ul class="slick_item_ul">
						<li><p>实际收益 <span>￥</span><span id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益 <span>￥</span><span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>
			<div>
				<div class="date" data-date="4">2016年4月收益</div>
				<div class="slick_item">
				<input type="hidden" name="month" value="4" />
					<ul class="slick_item_ul">
						<li><p>实际收益<span>￥</span> <span  id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益 <span>￥</span><span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>
			<div>
				<div class="date" data-date="5">2016年5月收益</div>
				<div class="slick_item">
				<input type="hidden" name="month" value="5" />
					<ul class="slick_item_ul">
						<li><p>实际收益 <span>￥</span><span id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益<span>￥</span> <span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>
			<div>
				<div class="date" data-date="6">2016年6月收益</div>
				<div class="slick_item">
				<input type="hidden" name="month" value="6" />
					<ul class="slick_item_ul">
						<li><p>实际收益<span>￥</span> <span id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益 <span>￥</span><span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>
			<div>
				<div class="date" data-date="7">2016年7月收益</div>
				<div class="slick_item">
				<input type="hidden" name="month" value="7" />
					<ul class="slick_item_ul">
						<li><p>实际收益<span>￥</span> <span id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益 <span>￥</span><span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>
			<div>
				<div class="date" data-date="8">2016年8月收益</div>
				<div class="slick_item">
				<input type="hidden" name="month" value="8" />
					<ul class="slick_item_ul">
						<li><p>实际收益<span>￥</span> <span id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益 <span>￥</span><span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>
			<div>
				<div class="date" data-date="9">2016年9月收益</div>
				<div class="slick_item">
				<input type="hidden" name="month" value="9" />
					<ul class="slick_item_ul">
						<li><p>实际收益<span>￥</span> <span id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益 <span>￥</span><span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>
			<div>
				<div class="date" data-date="10">2016年10月收益</div>
				<div class="slick_item">
				<input type="hidden" name="month" value="10" />
					<ul class="slick_item_ul">
						<li><p>实际收益<span>￥</span> <span id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益 <span>￥</span><span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>
			<div>
				<div class="date" data-date="11">2016年11月收益</div>
				<div class="slick_item">
				<input type="hidden" name="month" value="11" />
					<ul class="slick_item_ul">
						<li><p>实际收益<span>￥</span> <span id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益 <span>￥</span><span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>
			<div>
				<div class="date" data-date="12">2016年12月收益</div>
				<div class="slick_item">
				<input type="hidden" name="month" value="12" />
					<ul class="slick_item_ul">
						<li><p>实际收益<span>￥</span> <span id="realMoney" name="realMoney" class="money"></span> </p></li>
						<li><p>预计收益 <span>￥</span><span id="predictMoney" name="predictMoney" class="money"></span> </p></li>
					</ul>
				</div>
			</div>--%>
			</div>
		</div>
	</div>
	<div class="mainList" id="main">
	    <div>
			<ul class="houseList" id="hosueList">
		
			</ul>
			<div id="pageView"></div>
		</div>
   </div>
</div><!--/main-->




<input type="hidden" id="landlordUid" value="${landlordUid}"/>
<input type="hidden" id="picResourceUrl" value="${picResourceUrl}">
<input type="hidden" id="picSize" value="${picSize}">
<input type="hidden" id="staticUrl" value="${staticResourceUrl}">

<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/slick.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
<script type="text/javascript" src="//cdn.bootcss.com/Swiper/3.4.2/js/swiper.min.js"></script>
<script type="text/javascript" src="//static8.ziroom.com/fecommon/library/bscroll/bscroll.min.js"></script>
<script src="${staticResourceUrl}/js/profit/profit.js${VERSION}0013" type="text/javascript"></script>


</body>
</html>
