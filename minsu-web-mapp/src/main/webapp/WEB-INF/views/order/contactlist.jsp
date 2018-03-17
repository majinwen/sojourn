<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
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
	<title>入住人信息</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles.css${VERSION}001">
<body>
  	<header class="header">
  		<a href="javascript:;" onclick="history.back();" class="goback"></a>
		<h1>入住人信息</h1>
	</header>
	<div class="main">
		<div class="bgF rzrCon">
			<c:forEach var="item" items="${contactList}">
				<ul>
					<h3>${item.conName }</h3>
					<li><span><i>性</i>别：</span><c:if test="${item.conSex == 1 }">男</c:if><c:if test="${item.conSex == 2 }">女</c:if></li>
					<li><span><i>年</i>龄：</span>${item.conAge }</li>
					<c:if test="${isShowMes eq true}"><li><span>联系方式：</span>${item.conTel }</li></c:if>
				</ul>
			</c:forEach>
		</div>
	</div><!--/main-->
	
</body>
</html>
