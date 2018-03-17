<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
   	<meta name="applicable-device" content="mobile">
    <meta content="fullscreen=yes,preventMove=yes" name="ML-Config">        
    <script type="text/javascript" src="${staticResourceUrl}/js/header.js${VERSION}001"></script>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
	<title>发布房源</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
	
</head>
<body class="bgF">
<header class="header">
	
	<c:choose>
   		<%-- Android --%>
   		<c:when test="${sourceType eq 1 && versionCode ==true }">
			<a href="javascript:;" onclick="window.WebViewFunc.popToParent();" class="goback"></a>
   		</c:when>
   		<%-- iOS --%>
   		<c:when test="${sourceType eq 2 && versionCode ==true}">
			<a href="javascript:;" onclick="window.popToParent();" class="goback"></a>
   		</c:when>
   		<%-- M站 --%>
   		<c:otherwise>
			 <a href="${basePath }houseMgt/${loginUnauth }/myHouses" class="goback"></a>
   		</c:otherwise>
   	</c:choose>
	
	
	<h1>发布成功</h1>
</header>
<form accept="" method="">	
	<div class="main myCenterListNoneA">
		<div class="myBanner">
			<h1 class="top_h1">恭喜您房源发布申请已成功提交</h1>
			<%--<p>距离房源上架 <span>72</span> 小时</p>--%>
			<img src="${staticResourceUrl}/images/userbg.jpg" class="bg">
		</div>
		<div class="txt_box">您还有部分信息需要完善，有助于提升您的房源订单，如果未填写将按照平台默认规则执行。</div>
		
	</div><!--/main-->
	<div class="boxP075 mt85 mb85">
		<a href="${basePath }houseIssue/${loginUnauth }/showOptionalInfo?houseBaseFid=${houseBaseFid}&rentWay=${rentWay}&flag=${flag}"><input type="button" id="infoBtn" value="完善信息" class="org_btn btn_radius"></a>
	</div>
</form>
<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>

</body>
</html>