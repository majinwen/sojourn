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
	<title>拒绝订单信息</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">
</head>
<body >
	<header class="header">
		<a href="javascript:;" onclick="history.back();" class="goback"></a>
		<h1>拒绝订单信息</h1>
	</header>
	
	<div class="main">
		<div id="house" class="clearfix">
			<div class="house_img">
				<img src="${orderDetail.housePicUrl}" class="setImg">
			</div>
			<div class="house_txt">
			    <div class="house_address">${orderDetail.houseName }</div>
				<div class="house_renttype"> ${orderDetail.rentWayName } </div>
			</div>
		</div>
		<div class="reason_tt">请选择拒绝原因</div>
		<div class="reasons">
			<ul id="reasonList" class="reason_list">
				<c:forEach items="${nodeList }" var="stay">
              		<li name="commonLi" value="${stay.key }">${stay.text }</li>
              	</c:forEach>								
			</ul>
			<div class="other_reason">
				<textarea id="reasonTxt" row="5" placeholder="请输入原因,100字以内" maxlength="100"></textarea>
			</div>
			<div class="other_btn pt32 pb24">
				<!-- 禁止按钮状态 -->
				<input type="button" id="submitBtn" value="完成" class="btn_radius disabled_btn">
			</div>
		</div>
	</div>
	<div class="modal" id="modal">
		<div class="modal-content" id="configModal">
			<div class="modal-txt"><div class="modal-message"> <span id="startDate"></span>请设置已出租日期的房屋日历</div></div>
			<div id="configBtn" class="modal-btn">去设置</div>
		</div>
	</div>
	<input type="hidden" id="orderSn" value="${orderSn }">
	<input type="hidden" id="houseBaseFid" value="${orderDetail.houseFid }">
	<input type="hidden" id="houseRoomFid" value="${orderDetail.roomFid  }">
	<input type="hidden" id="rentWay" value="${orderDetail.rentWay  }">
	
<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/order/refuse.js${VERSION}001"></script>
</body>
</html>
