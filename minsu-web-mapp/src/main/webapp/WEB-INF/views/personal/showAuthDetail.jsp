<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>认证信息</title>
<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
</head>
<body>
	<header class="header">
		<a href="javascript:;" onclick="history.back();" class="goback"></a>
		<h1>认证信息</h1>
	</header>
	<div class="main myCenterListNoneIcon myCenterListGeren" id="main">
	<div>
		<ul class="myCenterList ">
			<li class="clearfix">				
				真实姓名
				<span class="gray">${customerEntity.realName }</span>			
			</li>
		</ul>
		<ul class="myCenterList">
			<li class="clearfix bor_b ">				
				证件类型
				<span class="gray">${idName }</span>
				
			</li>
			<li class="clearfix bor_b ">				
				证件号码
				<span class="gray">${customerEntity.idNo }</span>				
			</li>
		
			<li class="clearfix">
					证件照
					<span class="gray">${hasIdentity }</span>	
			</li>
		</ul>
		<ul class="myPicList clearfix">
			<li>
				<div class="img"><img src="${cardPicUrl1 }"></div>				
				<p>正面</p>
			</li>
			<li>
				<div class="img"><img src="${cardPicUrl2 }"></div>	
				<p>反面</p>
			</li>
			<li>
				<div class="img"><img src="${cardPicUrl3 }"></div>	
				<p>本人手持</p>
			</li>
		</ul>
	</div>
	</div><!--/main-->
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/jquery.form.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/iscroll.js${VERSION}006"></script>
	
	<script type="text/javascript">
		var conHeight = $(window).height()-$('.header').height();
		
		$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$('#main > div').css({'position':'absolute','width':'100%'});
		myScroll = new IScroll('#main', { scrollbars: true, mouseWheel: true });
	</script>
</body>
</html>