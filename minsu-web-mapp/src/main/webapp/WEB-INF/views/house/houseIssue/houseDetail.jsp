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
	<title>房源详情</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
	
</head>
<body>
<header class="header">
		<h1>房源详情</h1>
	</header>
	<form accept="" method="post" id="form">	
		<div class="main myCenterListNoneA myCenterListGeren">
			<ul class="myCenterList ">
				<li class="clearfix no_icon">
					房源名称
					<input type="text" placeholder="房屋名字+商圈+房东特色+房源特色" name="houseName" value="${houseBaseDetailVo.houseName }">			
				</li>
			</ul>
			<ul class="myCenterList ">
				<li class="clearfix no_icon">
					房屋户型
					<input type="text" placeholder="请选择整套房屋户型" value="${houseBaseDetailVo.roomNum }室${houseBaseDetailVo.hallNum }厅${houseBaseDetailVo.toiletNum }卫${houseBaseDetailVo.kitchenNum }厨${houseBaseDetailVo.balconyNum }阳台">			
				</li>
			</ul>
			<ul class="myCenterList ">
				<li class="clearfix no_icon">
					房源面积
					<input type="text" placeholder="请填写整套房源面积" name="houseArea" value="${houseBaseDetailVo.houseArea }" maxlength="6">			
				</li>
			</ul>
			<ul class="myCenterList">
				<li class="clearfix bor_b no_icon">
					入住人数限制
					<input type="text" placeholder="请选择最多入住人数" id="sex" value="${houseBaseDetailVo.checkInLimit }">
					<select onchange="setValToInput(this,'sex')" id="customerSex" name="checkInLimit">
						<option value="" selected="selected">请选择性别</option>
						<c:forEach items="${limitList }" var="limit">
							<option value="${limit.key }" >${limit.text}</option>
						</c:forEach>
					</select>
					<span class="icon_r icon"></span>
				</li>
			</ul>
			<ul class="myCenterList ">
				<li class="clearfix no_icon">
					价格
					<input type="text" placeholder="请设置整套房屋价格" name="leasePrice" value="${houseBaseDetailVo.leasePrice }" maxlength="6">			
				</li>
			</ul>
		</div><!--/main-->
		<div class="boxP075 mt85 mb85">
			<input type="button" value="提交" class="org_btn" id="saveBtn">
		</div>
	</form>

	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
</body>
</html>
