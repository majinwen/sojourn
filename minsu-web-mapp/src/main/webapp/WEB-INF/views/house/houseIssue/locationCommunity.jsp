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
	<title>小区信息</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
	
</head>
<body>
	<form action="${basePath}houseDeploy/${loginUnauth}/toLocationFromOthers" method="get" id="form">
		<input type="hidden" name="houseBaseFid" value="${houseBaseFid }">
		<input type="hidden" name="houseType" value="${houseType }">
		<input type="hidden" name="rentWay" value="${rentWay }">
		<input type="hidden" name="nationCode" value="${nationCode }">
		<input type="hidden" name="provinceCode" value="${provinceCode }">
		<input type="hidden" name="cityCode" value="${cityCode }">
		<input type="hidden" name="areaCode" value="${areaCode }">
		<!-- <input type="hidden" name="communityName" value="${communityName }"> -->
		<input type="hidden" name="houseStreet" value="${houseStreet }">
		<input type="hidden" name="flag" value="${flag }">
		<input type="hidden" name="houseRoomFid" value="${houseRoomFid }">
		<input type="hidden" name="detailAddress" value="${detailAddress }">
	
		<div class="main myCenterListNoneA">
			<header class="header">
				<a href="javascript:;" id="goback" class="goback" onclick="history.go(-1)"></a>
				<h1>小区信息</h1>
			</header>
			<ul class="myCenterList">
				<li class="clearfix">
					<input type="text" id="communityName" placeholder="请输入小区信息" name="communityName" class="ipt_lg" value="${communityName}">
				</li>
			</ul>
		</div><!--/main-->
		<div class="boxP075 mt85 mb85">
			<input type="submit" id="submitBtn" value="完成" class="disabled_btn btn_radius">
		</div>
	</form>
	
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<script type="text/javascript">
		$(function(){
			if($.trim($("#communityName").val()).length != 0){
				$("#submitBtn").removeClass("disabled_btn").addClass("org_btn");
			}
			$("#communityName").on('keyup change', function(){
				var communityName = $.trim($("#communityName").val());
				if(communityName.length > 100 ){
					$("#communityName").val(communityName.slice(0, 100));
				}
				if(communityName.length != 0){
					$("#submitBtn").removeClass("disabled_btn").addClass("org_btn");
				}else{
					$("#submitBtn").addClass("disabled_btn").removeClass("org_btn");
				}
			})
			$("#submitBtn").click(function(){
				if($(this).hasClass("disabled_btn")){
					return false;
				}else{
					return true;
				}
			})
		})
	</script>
	
</body>
</html>
