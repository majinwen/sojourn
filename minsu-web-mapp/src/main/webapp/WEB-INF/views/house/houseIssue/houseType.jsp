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
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
    <meta name="format-detection" content="telephone=no"> 
     <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
	<title>房源类型</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/css_830.css${VERSION}001">
</head>
<body>
 
 <form action="${basePath}houseDeploy/${loginUnauth}/toRentWay" method="get" id="form">
		<input type="hidden" name="houseBaseFid" value="${houseBaseFid }">
		<header class="header">
			<c:choose>
	    		<%-- Android --%>
	    		<c:when test="${sourceType eq 1 && versionCode ==true}">
					<a href="javascript:;" onclick="window.WebViewFunc.popToParent();" class="goback"></a>
	    		</c:when>
	    		<%-- iOS --%>
	    		<c:when test="${sourceType eq 2 && versionCode ==true}">
					<a href="javascript:;" onclick="window.popToParent();" class="goback"></a>
	    		</c:when>
	    		<%-- M站 --%>
	    		<c:otherwise>
					<a href="javascript:;" id="goback" class="goback" onclick=""></a>
	    		</c:otherwise>
	    	</c:choose>
			<h1>房源类型(1/6)</h1>
    		<%-- 房东指南 --%>
			<a href="https://zhuanti.ziroom.com/zhuanti/minsu/zhinan/fabuliucheng2.html" class="toZhinan"></a>
		</header>
		<div class="main myCenterListNoneA">
			
				<div class="myBanner">
				<h1>选择您的房源类型</h1>
				<img src="${staticResourceUrl}/images/userbg.jpg" class="bg">
			</div>
			<div  id="main" >
				<div class="box">
			<ul class="myCenterList  myCenterList_no">
				<c:forEach items="${houseTypeList }" var="stay">
					<li class="clearfix bor_b houseTypeLi">				
						<input type="radio" name="houseType" value="${stay.key }"/>${stay.text }
						<span class="icon_r icon" ></span>
					</li>
				</c:forEach> 
				
			</ul>
			</div>
			</div>
		</div>
	</form>

	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}001"></script>

	<script type="text/javascript">
		$(function(){
			$(".goback").click(function(event){
				var path ="${basePath}houseMgt/${loginUnauth}/myHouses"; 
				$('#form').attr("action", path).submit();
			});
			
			$(".houseTypeLi").click(function(event){
				$(this).children("input").attr("checked","checked"); 
				var path = "${basePath}houseDeploy/${loginUnauth}/toRentWay"; 
				$('#form').attr("action", path).submit();
			});
		})
		
	</script>
	
	<script type="text/javascript">
	$(function(){

		var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });

		var conHeight = $(window).height()-$('.header').height()-$('.myBanner').height();

		$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$('#main .box').css({'position':'absolute','width':'100%'});
		myScroll.refresh();
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	})
	</script>
	
</body>
</html>
