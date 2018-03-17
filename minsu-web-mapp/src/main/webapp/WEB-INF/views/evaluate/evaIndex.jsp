<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
	<title>评价管理</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles.css${VERSION}001">
	
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
			<a href="${basePath }personal/${loginUnauth }/initPersonalCenter" class="goback"></a>
		</c:if>
		
		<h1>评价管理</h1>
	</header>
	
	<div class="main">
		
		<div class="tabs" id="tabs">
			<ul class="tabTit clearfix pingjiaTit" id="pingjiaTit">
				<li class="active">待评价</li>
				<li>已评价</li>
			</ul>
			<div class="tabCon" id="tabCon">
			

				<div class="con" id="con">
					<ul class="dingdanList pingjiaList" id="evaList">
						
					</ul>
				</div><!--/con-->
				
				
			</div><!--/tabCon-->
		</div><!--/tabs-->
		
		
	</div><!--/main-->

<%-- <%@ include file="../common/bottom.jsp" %> --%>
<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}006"></script>
<script src="${staticResourceUrl}/js/evaluate/evaLan.js${VERSION}002"></script>
<script type="text/javascript">
/* $(function() {
	EvaLan.init();
}) */
</script>
</body>
</html>