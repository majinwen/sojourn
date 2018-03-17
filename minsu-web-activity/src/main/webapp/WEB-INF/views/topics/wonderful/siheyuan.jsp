<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="zh">

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="applicable-device" content="mobile">
		<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
		<meta name="viewport" content="width=750, user-scalable=no, target-densitydpi=device-dpi">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="yes" name="apple-touch-fullscreen" />
		<title>胡同</title>
		<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
		<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/pageStyles.css${VERSION}001">


	</head>

	<body>
	
	<div class="jcBox">
		<div class="banners">
			<img src="${staticResourceUrl}/images/hutong.jpg"><img src="${staticResourceUrl}/images/hutong2.jpg">
		</div>
		<ul class="jcList">
		<c:forEach items="${list }"  var="obj">
			<li>
			<a href="javascript:void(0);" onclick="minsutuijianJump('${obj.fid }','${obj.rentWay }','${shareFlag }','${shareUrl }');">		
				<div class="img">
					<img src="${obj.picUrl }" class="setImg">
					<span class="t"><b>￥</b><fmt:parseNumber integerOnly="true" value="${obj.price/100 }" />元/天</span>
				</div>
				<div class="txt">
					<div class="ts">
						<img src="${obj.landlordUrl }">
					</div>
					<h2>${obj.houseName }</h2>
					<p class="gray">${obj.rentWayName } ｜ 可住${obj.personCount }人  </p>
					<p class="star">
						<b class="myCalendarStar" data-val="${obj.evaluateScore }">
							<i></i><i></i><i></i><i></i><i></i>
						</b>
						<span>${obj.evaluateScore }分</span>
						<c:if test="${obj.evaluateCount != 0}">
							<b class="gray">评论${obj.evaluateCount }条</b>
						</c:if>
					</p>

				</div>
			</a>
			</li>
		</c:forEach>
		</ul>
	</div>
		
		<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
		<script src="${staticResourceUrl}/js/common.js${VERSION}002"></script>
		<%--<script src="http://catcher.ziroom.com/assets/js/boomerang.min.js"></script>--%>
		<%--<script>--%>
		   <%--BOOMR.init({--%>
		   <%--beacon_url: "http://catcher.ziroom.com/beacon",--%>
		   <%--beacon_type: "POST",--%>
		   <%--page_key: "minsu_H5_siheyuan_160913"});--%>
		<%--</script>--%>
	</body>

</html>