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
	<title>个人资料</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
</head>
<body>
	<header class="header">
		
		<h1>提示信息</h1>
	</header>
	<div class="main">
		<div class="pt75">
			<div class="tac mt85">
				<span class="c_icon icon_ok">提交成功</span>
			</div>
			<p class="tac fz6">${msg }</p>
		</div>
	</div><!--/main-->

	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<script type="text/javascript">
		(function($){
			setTimeout(backToHome,1000);
			//跳转到我的房源
			function backToHome(){
				window.location.href = "${basePathHttps }personal/${loginUnauth }/initPersonalCenter";	
			}
		}(jQuery))			
	</script>
</body>
</html>