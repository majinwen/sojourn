<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="applicable-device" content="mobile">
	<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
	<script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="yes" name="apple-touch-fullscreen" />
	<title>房间价格</title>
	<link href="${staticResourceUrl }/css/mui.picker.min.css${VERSION}001" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">

	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/css_830.css${VERSION}0023">
</head>
<body>
<form id="mainForm" >
	<header class="header"> <a href="javascript:history.go(-1);" id="goback" class="goback"></a>
		<h1>价格</h1>
	</header>
    <c:if test="${empty redisSp}">
	<div id="main" class="main">
		<div class="box">
			<ul class="myCenterList">
				<c:if test="${rentWay ==0}">
					<c:if test="${empty housePrice || housePrice == 0}">
						<li class="c_ipt_li c_ipt_li_none">
							<a href="javascript:void(0);">
								<span class="c_span">固定价格</span>
								<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写固定价格" id="fixPrice" name="fixPrice" value="" maxlength="5" />
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
					</c:if>
					<c:if test="${!empty housePrice && housePrice != 0}">
						<li class="c_ipt_li">
							<a href="javascript:void(0);">
								<span class="c_span">固定价格</span>
								<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写固定价格" id="fixPrice" name="fixPrice" value="${fn:split(housePrice/100, '.')[0] }元/晚" maxlength="5" />
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
					</c:if>
				</c:if>
				<c:if test="${rentWay ==1}">
					<c:if test="${empty roomPrice || roomPrice == 0}">
						<li class="c_ipt_li c_ipt_li_none">
							<a href="javascript:void(0);">
								<span class="c_span">固定价格</span>
								<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写固定价格" id="fixPrice" name="fixPrice" value="" maxlength="5" />
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
					</c:if>
					<c:if test="${!empty roomPrice && roomPrice !=0}">
						<li class="c_ipt_li">
							<a href="javascript:void(0);">
								<span class="c_span">固定价格</span>
								<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写固定价格" id="fixPrice" name="fixPrice" value="${fn:split(roomPrice/100, '.')[0] }元/晚" maxlength="5" />
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
					</c:if>
				</c:if>
			</ul>
			<ul id="weekList" class="myCenterList">
				<c:if test="${weekendPriceFlag == 0 }">
					<input type="hidden" id="weekendPriceFlag" name="weekendPriceFlag" value="0">
					<li class="c_li bor_b">
						<a href="javascript:void(0);">
							周末价格
							<span class="c_li_r j_li_r gray"><i class="round"></i></span>
						</a>
					</li>
	
					<li id="weekLi" class="c_ipt_li bor_b c_ipt_li_none hide">
						<a href="javascript:void(0);">
							<span id="weekSpan" class="c_span">周末日期</span>
							<input class="c_ipt" type="text" placeholder="请选择日期" id="weekBtn" value="" readonly="readonly">
							<input type="hidden" name="setTime" id="week" value="">
							<span class="icon_r icon"></span>
						</a>
					</li>
	
					<li class="c_ipt_li c_ipt_li_none hide">
						<a href="javascript:void(0);">
							<span class="c_span">周末价格</span>
							<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写周末价格" id="weekPrice" name="specialPrice" value="" maxlength="5"/>
							<span class="icon_r icon icon_clear"></span>
						</a>
					</li>
				</c:if>
				<c:if test="${weekendPriceFlag == 1 }">
					<input type="hidden" id="weekendPriceFlag" name="weekendPriceFlag" value="1">
					<li class="c_li bor_b">
						<a href="javascript:void(0);">
							周末价格
							<span class="c_li_r j_li_r org"><i class="round"></i></span>
						</a>
					</li>
	
					<li id="weekLi" class="c_ipt_li bor_b">
						<a href="javascript:void(0);">
							<span id="weekSpan" class="c_span">周末日期</span>
							<input class="c_ipt" type="text" placeholder="请选择日期" id="weekBtn" value="${weekendPriceText }" readonly="readonly">
							<input type="hidden" name="setTime" id="week" value="${weekendPriceValue }">
							<span class="icon_r icon"></span>
						</a>
					</li>
	
					<li class="c_ipt_li">
						<a href="javascript:void(0);">
							<span class="c_span">周末价格</span>
							<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写周末价格" id="weekPrice" name="specialPrice" value="${fn:split(weekendPrice/100, '.')[0] }元/晚" maxlength="5"/>
							<span class="icon_r icon icon_clear"></span>
						</a>
					</li>
				</c:if>
				<c:if test="${weekendPriceFlag == 2 }">
					<input type="hidden" id="weekendPriceFlag" name="weekendPriceFlag" value="2">
					<li class="c_li bor_b">
						<a href="javascript:void(0);">
							周末价格
							<span class="c_li_r j_li_r gray"><i class="round"></i></span>
						</a>
					</li>
	
					<li id="weekLi" class="c_ipt_li bor_b hide">
						<a href="javascript:void(0);">
							<span id="weekSpan" class="c_span">周末日期</span>
							<input class="c_ipt" type="text" placeholder="请选择日期" id="weekBtn" value="${weekendPriceText }" readonly="readonly">
							<input type="hidden" name="setTime" id="week" value="${weekendPriceValue }">
							<span class="icon_r icon"></span>
						</a>
					</li>
	
					<li class="c_ipt_li hide">
						<a href="javascript:void(0);">
							<span class="c_span">周末价格</span>
							<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写周末价格" id="weekPrice" name="specialPrice" value="${fn:split(weekendPrice/100, '.')[0] }元/晚" maxlength="5"/>
							<span class="icon_r icon icon_clear"></span>
						</a>
					</li>
				</c:if>
			</ul>
		<!--
		<c:if test="${longTermLimit<500}">
			<ul id="gapFlexList" class="myCenterList noBm">
				<c:if test="${empty gapFlexList}">
				<li class="c_li">
					<a href="javascript:void(0);">		
						<span class="S_priceListBigTit">今日特惠折扣</span>
						<span class="S_priceListSmallTit">房客预订当日入住可享受的折扣</span>
						<span class="c_li_r j_li_r gray"><i class="round"></i></span>
					</a>
				</li>
				<c:forEach items="${gapList}" var="gf">
					<c:if test="${gf.dicCode=='ProductRulesEnum020001'}">
						<li class="c_ipt_li bor_b hide">
							<a href="javascript:void(0);">
								<span class="c_span">预订当日价格</span>
								<input class="c_ipt" value="<fmt:parseNumber  value='${gf.dicVal*10}'/>折" readonly="readonly"/>
							</a>
						</li>
					</c:if>
				</c:forEach>
				</c:if>
				<c:if test="${not empty gapFlexList}">
					<li class="c_li">
						<a href="javascript:void(0);">
							<span class="S_priceListBigTit">今日特惠折扣</span>
						    <span class="S_priceListSmallTit">房客预订当日入住可享受的折扣</span>
							<span class="c_li_r j_li_r org"><i class="round"></i></span>
						</a>
					</li>
					<c:forEach items="${gapFlexList}" var="gf">
						<c:if test="${gf.dicCode=='ProductRulesEnum020001'}">
							<li class="c_ipt_li bor_b ">
								<a href="javascript:void(0);">
									<span class="c_span">预订当日折扣</span>
									<input class="c_ipt" value="<fmt:parseNumber  value='${gf.dicVal*10}'/>折" readonly="readonly"/>
								</a>
							</li>
						</c:if>
					</c:forEach>
				</c:if>
			</ul>
		</c:if>-->	
		<%--<c:if test="${longTermLimit<500}">
			<ul id="gapFlexTList" class="myCenterList noBm">
				<c:if test="${empty gapFlexTList}">
				<li class="c_li">
					<a href="javascript:void(0);">
						空置间夜自动折扣
						<span class="c_li_r j_li_r gray"><i class="round"></i></span>
					</a>
				</li>
				<c:forEach items="${gapList}" var="gf">
					<c:if test="${gf.dicCode=='ProductRulesEnum020002'}">
						<li class="c_ipt_li bor_b hide">
							<a href="javascript:void(0);">
								<span class="c_span">仅1日可连续预订价格</span>
								<input class="c_ipt" value="<fmt:parseNumber  value='${gf.dicVal*10}'/>折" readonly="readonly"/>
							</a>
						</li>
					</c:if>
					<c:if test="${gf.dicCode=='ProductRulesEnum020003'}">
						<li class="c_ipt_li bor_b hide">
							<a href="javascript:void(0);">
								<span class="c_span">仅2日可连续预订价格</span>
								<input class="c_ipt" value="<fmt:parseNumber  value='${gf.dicVal*10}'/>折" readonly="readonly"/>
							</a>
						</li>
					</c:if>
				</c:forEach>
				</c:if>
				<c:if test="${not empty gapFlexTList}">
					<li class="c_li">
						<a href="javascript:void(0);">
							空置间夜自动折扣
							<span class="c_li_r j_li_r org"><i class="round"></i></span>
						</a>
					</li>
					<c:forEach items="${gapList}" var="gf">
						<c:if test="${gf.dicCode=='ProductRulesEnum020002'}">
							<li class="c_ipt_li bor_b ">
								<a href="javascript:void(0);">
									<span class="c_span">仅1日可连续预订价格</span>
									<input class="c_ipt" value="<fmt:parseNumber value='${gf.dicVal*10}'/>折" readonly="readonly"/>
								</a>
							</li>
						</c:if>
						<c:if test="${gf.dicCode=='ProductRulesEnum020003'}">
							<li class="c_ipt_li bor_b ">
								<a href="javascript:void(0);">
									<span class="c_span">仅2日可连续预订价格</span>
									<input class="c_ipt" value="<fmt:parseNumber  value='${gf.dicVal*10}'/>折" readonly="readonly"/>
								</a>
							</li>
						</c:if>
					</c:forEach>
				</c:if>
			</ul>
			<p class="konwMore">把房源价格设为适应需求，提升订单数量。<a href="javascript:tomorePriceInfo();" class="org">了解更多信息</a></p>
		</c:if>--%>
		<c:if test="${longTermLimit<500}">
			<ul id="rebateList" class="myCenterList noBm ">
				<c:if test="${not empty sevenRate}">
					<li class="c_li bor_b">
						<a href="javascript:void(0);">
							长期住宿折扣
							<span class="c_li_r j_li_r org"><i class="round"></i></span>
						</a>
					</li>
					<c:if test="${sevenRate.dicVal != -1}">
					<li class="c_ipt_li  bor_b ">
						<a href="javascript:void(0);">
							<span class="c_span">入住满7天折扣</span>
							<input class="c_ipt j_ipt" data-type="percent" type="text" placeholder="请填写入住满7天折扣（例如9折）" name="qfid" id="qRebate"  value="${sevenRate.dicVal/10}折" maxlength="3"/>
							<span class="icon_r icon icon_clear"></span>
						</a>
					</li>
					</c:if>
					<c:if test="${sevenRate.dicVal == -1}">
						<li class="c_ipt_li c_ipt_li_none bor_b ">
							<a href="javascript:void(0);">
								<span class="c_span">入住满7天折扣</span>
								<input class="c_ipt j_ipt" data-type="percent" type="text" placeholder="请填写入住满7天折扣（例如9折）" name="qfid" id="qRebate"  value="" maxlength="3"/>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
					</c:if>
					<c:if test="${thirtyRate.dicVal != -1}">
					<li class="c_ipt_li">
						<a href="javascript:void(0);">
							<span class="c_span">入住满30天折扣</span>
							<input class="c_ipt j_ipt" data-type="percent" type="text" placeholder="请填写入住满30天折扣（例如8.4折）" name="sfid" id="ssRebate"  value="${thirtyRate.dicVal/10}折" maxlength="3"/>
							<span class="icon_r icon icon_clear"></span>
						</a>
					</li>
					</c:if>
					<c:if test="${thirtyRate.dicVal == -1}">
						<li class="c_ipt_li c_ipt_li_none">
							<a href="javascript:void(0);">
								<span class="c_span">入住满30天折扣</span>
								<input class="c_ipt j_ipt" data-type="percent" type="text" placeholder="请填写入住满30天折扣（例如8.4折）" name="sfid" id="ssRebate"  value="" maxlength="3"/>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
					</c:if>
				</c:if>


				<c:if test="${empty sevenRate}">
					<li class="c_li bor_b">
						<a href="javascript:void(0);">
							长期住宿折扣
							<span class="c_li_r j_li_r gray"><i class="round"></i></span>
						</a>
					</li>
					<li class="c_ipt_li c_ipt_li_none bor_b hide">
						<a href="javascript:void(0);">
							<span class="c_span">入住满7天折扣</span>
							<input class="c_ipt j_ipt" data-type="percent" type="text" id="qRebate" placeholder="请填写入住满7天折扣（例如9折）" name="qfid"  value="" maxlength="3"/>
							<span class="icon_r icon icon_clear"></span>
						</a>
					</li>
					<li class="c_ipt_li c_ipt_li_none hide">
						<a href="javascript:void(0);">
							<span class="c_span">入住满30天折扣</span>
							<input class="c_ipt j_ipt" data-type="percent" type="text" id="ssRebate" placeholder="请填写入住满30天折扣（例如8.4折）" name="sfid" value="" maxlength="3"/>
							<span class="icon_r icon icon_clear"></span>
						</a>
					</li>
				</c:if>
			</ul>
		</c:if>
			
		</div>
		<!--/main-->
	</div>
	</c:if>
	<!-- redis的情况-->

	<c:if test="${!empty redisSp}">
		<div id="main" class="main">
			<div class="box">
				<ul class="myCenterList">
					<c:if test="${redisSp.rentWay ==0}">
						<c:if test="${empty redisSp.leasePrice}">
							<li class="c_ipt_li c_ipt_li_none">
								<a href="javascript:void(0);">
									<span class="c_span">固定价格</span>
									<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写固定价格" id="fixPrice" name="fixPrice" value="" maxlength="5" />
									<span class="icon_r icon icon_clear"></span>
								</a>
							</li>
						</c:if>
						<c:if test="${!empty redisSp.leasePrice}">
							<li class="c_ipt_li">
								<a href="javascript:void(0);">
									<span class="c_span">固定价格</span>
									<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写固定价格" id="fixPrice" name="fixPrice" value="${fn:split(housePrice/100, '.')[0] }元/晚" maxlength="5" />
									<span class="icon_r icon icon_clear"></span>
								</a>
							</li>
						</c:if>
					</c:if>
					<c:if test="${redisSp.rentWay ==1}">
						<c:if test="${empty redisSp.roomPrice}">
							<li class="c_ipt_li c_ipt_li_none">
								<a href="javascript:void(0);">
									<span class="c_span">固定价格</span>
									<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写固定价格" id="fixPrice" name="fixPrice" value="" maxlength="5" />
									<span class="icon_r icon icon_clear"></span>
								</a>
							</li>
						</c:if>
						<c:if test="${!empty redisSp.roomPrice}">
							<li class="c_ipt_li">
								<a href="javascript:void(0);">
									<span class="c_span">固定价格</span>
									<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写固定价格" id="fixPrice" name="fixPrice" value="${redisSp.roomPrice}元/晚" maxlength="5" />
									<span class="icon_r icon icon_clear"></span>
								</a>
							</li>
						</c:if>
					</c:if>
				</ul>
				<ul id="weekList" class="myCenterList">
					<c:if test="${redisSp.weekendPriceSwitch == 0 }">
						<input type="hidden" id="weekendPriceFlag" name="weekendPriceFlag" value="0">
						<li class="c_li bor_b">
							<a href="javascript:void(0);">
								周末价格
								<span class="c_li_r j_li_r gray"><i class="round"></i></span>
							</a>
						</li>

						<li id="weekLi" class="c_ipt_li bor_b c_ipt_li_none hide">
							<a href="javascript:void(0);">
								<span id="weekSpan" class="c_span">周末日期</span>
								<input class="c_ipt" type="text" placeholder="请选择日期" id="weekBtn" value="" readonly="readonly">
								<input type="hidden" name="setTime" id="week" value="">
								<span class="icon_r icon"></span>
							</a>
						</li>

						<li class="c_ipt_li c_ipt_li_none hide">
							<a href="javascript:void(0);">
								<span class="c_span">周末价格</span>
								<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写周末价格" id="weekPrice" name="specialPrice" value="" maxlength="5"/>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
					</c:if>
					<c:if test="${redisSp.weekendPriceSwitch == 1 }">
						<input type="hidden" id="weekendPriceFlag" name="weekendPriceFlag" value="1">
						<li class="c_li bor_b">
							<a href="javascript:void(0);">
								周末价格
								<span class="c_li_r j_li_r org"><i class="round"></i></span>
							</a>
						</li>

						<li id="weekLi" class="c_ipt_li bor_b">
							<a href="javascript:void(0);">
								<span id="weekSpan" class="c_span">周末日期</span>
								<input class="c_ipt" type="text" placeholder="请选择日期" id="weekBtn" value="${weekendPriceText }" readonly="readonly">
								<input type="hidden" name="setTime" id="week" value="${weekendPriceValue }">
								<span class="icon_r icon"></span>
							</a>
						</li>

						<li class="c_ipt_li">
							<a href="javascript:void(0);">
								<span class="c_span">周末价格</span>
								<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写周末价格" id="weekPrice" name="specialPrice" value="${redisSp.specialPrice }元/晚" maxlength="5"/>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
					</c:if>
					<c:if test="${redisSp.weekendPriceSwitch == 2 }">
						<input type="hidden" id="weekendPriceFlag" name="weekendPriceFlag" value="2">
						<li class="c_li bor_b">
							<a href="javascript:void(0);">
								周末价格
								<span class="c_li_r j_li_r gray"><i class="round"></i></span>
							</a>
						</li>

						<li id="weekLi" class="c_ipt_li bor_b hide">
							<a href="javascript:void(0);">
								<span id="weekSpan" class="c_span">周末日期</span>
								<input class="c_ipt" type="text" placeholder="请选择日期" id="weekBtn" value="${weekendPriceText }" readonly="readonly">
								<input type="hidden" name="setTime" id="week" value="${weekendPriceValue }">
								<span class="icon_r icon"></span>
							</a>
						</li>

						<li class="c_ipt_li hide">
							<a href="javascript:void(0);">
								<span class="c_span">周末价格</span>
								<input class="c_ipt j_ipt" data-type="unitPrice" type="tel" placeholder="请填写周末价格" id="weekPrice" name="specialPrice" value="${redisSp.specialPrice }元/晚" maxlength="5"/>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
					</c:if>
				</ul>
		        <c:if test="${longTermLimit<500}">
		        <!--
				<ul id="gapFlexList" class="myCenterList noBm">
					<c:if test="${ redisSp.dayDiscount == 0}">
						<li class="c_li">
							<a href="javascript:void(0);">
								<span class="S_priceListBigTit">今日特惠折扣</span>
						    	<span class="S_priceListSmallTit">房客预订当日入住可享受的折扣</span>
								<span class="c_li_r j_li_r gray"><i class="round"></i></span>
							</a>
						</li>
						<c:forEach items="${gapList}" var="gf">
							<c:if test="${gf.dicCode=='ProductRulesEnum020001'}">
								<li class="c_ipt_li bor_b hide">
									<a href="javascript:void(0);">
										<span class="c_span">预订当日价格</span>
										<input class="c_ipt" value="<fmt:parseNumber integerOnly='true' value='${gf.dicVal*10}'/>折" readonly="readonly"/>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${redisSp.dayDiscount == 1}">
						<li class="c_li">
							<a href="javascript:void(0);">
								<span class="S_priceListBigTit">今日特惠折扣</span>
						    	<span class="S_priceListSmallTit">房客预订当日入住可享受的折扣</span>
								<span class="c_li_r j_li_r org"><i class="round"></i></span>
							</a>
						</li>
						<c:forEach items="${gapList}" var="gf">
							<c:if test="${gf.dicCode=='ProductRulesEnum020001'}">
								<li class="c_ipt_li bor_b">
									<a href="javascript:void(0);">
										<span class="c_span">预订当日价格</span>
										<input class="c_ipt" value="<fmt:parseNumber integerOnly='true' value='${gf.dicVal*10}'/>折" readonly="readonly"/>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</c:if>
				</ul>
				  -->
				<%--<ul id="gapFlexTList" class="myCenterList noBm">
					<c:if test="${ redisSp.flexDiscount == 0}">
						<li class="c_li">
							<a href="javascript:void(0);">
								空置间夜自动折扣
								<span class="c_li_r j_li_r gray"><i class="round"></i></span>
							</a>
						</li>
						<c:forEach items="${gapList}" var="gf">
							<c:if test="${gf.dicCode=='ProductRulesEnum020002'}">
								<li class="c_ipt_li bor_b hide">
									<a href="javascript:void(0);">
										<span class="c_span">当可连续预订的日期仅有1天时价格</span>
										<input class="c_ipt" value="<fmt:parseNumber integerOnly='true' value='${gf.dicVal*10}'/>折" readonly="readonly"/>
									</a>
								</li>
							</c:if>
							<c:if test="${gf.dicCode=='ProductRulesEnum020003'}">
								<li class="c_ipt_li bor_b hide">
									<a href="javascript:void(0);">
										<span class="c_span">当可连续预订的日期仅有2天时价格</span>
										<input class="c_ipt" value="<fmt:parseNumber integerOnly='true' value='${gf.dicVal*10}'/>折" readonly="readonly"/>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${redisSp.flexDiscount ==1}">
						<li class="c_li">
							<a href="javascript:void(0);">
								空置间夜自动折扣
								<span class="c_li_r j_li_r org"><i class="round"></i></span>
							</a>
						</li>
						<c:forEach items="${gapList}" var="gf">
							<c:if test="${gf.dicCode=='ProductRulesEnum020002'}">
								<li class="c_ipt_li bor_b">
									<a href="javascript:void(0);">
										<span class="c_span">当可连续预订的日期仅有1天时价格</span>
										<input class="c_ipt" value="<fmt:parseNumber integerOnly='true' value='${gf.dicVal*10}'/>折" readonly="readonly"/>
									</a>
								</li>
							</c:if>
							<c:if test="${gf.dicCode=='ProductRulesEnum020003'}">
								<li class="c_ipt_li bor_b">
									<a href="javascript:void(0);">
										<span class="c_span">当可连续预订的日期仅有2天时价格</span>
										<input class="c_ipt" value="<fmt:parseNumber integerOnly='true' value='${gf.dicVal*10}'/>折" readonly="readonly"/>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</c:if>
				</ul>
				<p class="konwMore">把房源价格设为适应需求，提升订单数量。。<a href="javascript:tomorePriceInfo();"  class="org">了解更多信息</a></p>--%>
		</c:if>
		<c:if test="${longTermLimit<500}">
				<ul id="rebateList" class="myCenterList noBm ">
					<c:if test="${redisSp.fullDayRate==1}">
						<li class="c_li bor_b">
							<a href="javascript:void(0);">
								长期住宿折扣
								<span class="c_li_r j_li_r org"><i class="round"></i></span>
							</a>
						</li>
						<li class="c_ipt_li  bor_b ">
							<a href="javascript:void(0);">
								<span class="c_span">入住满7天折扣</span>
								<input class="c_ipt j_ipt" data-type="percent" type="text" placeholder="请填写入住满7天折扣（例如9折）" name="qfid" id="qRebate" <c:if test="${!empty redisSp.sevenDiscountRate && redisSp.sevenDiscountRate != ''}"> value="${redisSp.sevenDiscountRate/10 }折" </c:if> maxlength="3"/>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
						<li class="c_ipt_li">
							<a href="javascript:void(0);">
								<span class="c_span">入住满30天折扣</span>
								<input class="c_ipt j_ipt" data-type="percent" type="text" placeholder="请填写入住满30天折扣（例如8.4折）" name="sfid" id="ssRebate"<c:if test="${!empty redisSp.thirtyDiscountRate && redisSp.thirtyDiscountRate != ''}"> value="${redisSp.thirtyDiscountRate/10}折" </c:if> maxlength="3"/>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
					</c:if>


					<c:if test="${redisSp.fullDayRate==0}">
						<li class="c_li bor_b">
							<a href="javascript:void(0);">
								长期住宿折扣
								<span class="c_li_r j_li_r gray"><i class="round"></i></span>
							</a>
						</li>
						<li class="c_ipt_li c_ipt_li_none bor_b hide">
							<a href="javascript:void(0);">
								<span class="c_span">入住满7天折扣</span>
								<input class="c_ipt j_ipt" data-type="percent" type="text" id="qRebate" placeholder="请填写入住满7天折扣（例如9折）" name="qfid"  value="" maxlength="3"/>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
						<li class="c_ipt_li c_ipt_li_none hide">
							<a href="javascript:void(0);">
								<span class="c_span">入住满30天折扣</span>
								<input class="c_ipt j_ipt" data-type="percent" type="text" id="ssRebate" placeholder="请填写入住满30天折扣（例如8.4折）" name="sfid" value="" maxlength="3"/>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
					</c:if>
				</ul>
		</c:if>
				
			</div>
			<!--/main-->
		</div>
		
	</c:if>
	<div class="box box_bottom">
			<input type="button" id="submitBtn" value="确认" class="org_btn">
		</div>
</form>
<!-- 添加房源名称 -->

<div id="moreInfo" class="main " style="display: none;">
	<header class="header" style="border-bottom:none;"> <a href="javascript:offmorePriceInfo();" id="goClose"  class="goClose"></a>
		<%--<h1>空置间夜自动定价</h1>--%>
	</header>
<div id="moreInfoMainBox">
	<div class="box">
		<div class="knowMoreTxt">
			<div class="knowMoreTt">空置间夜自动定价</div>
			<p>通过空置间夜自动折扣功能，您可以把房源价格根据用户需求及预订日期的变化自动调整，同时，在搜索排名中，根据综合因素我们优先推荐开启了空置间夜自动定价的房源，提升房源出租率。</p>
			<p>开启空置间夜自动折扣功能，即表示您接受平台对于特定场景的房价自动调整。</p>
			<p>空置间夜自动折扣场景：</p>
			<c:forEach items="${gapList}" var="gf">
				<c:if test="${gf.dicCode=='ProductRulesEnum020002'}">
					<p>1、可连续出租日期仅为1天时，预订价格可享受当日房价<fmt:parseNumber integerOnly='true' value='${gf.dicVal*10}'/>折优惠</p>
				</c:if>
				<c:if test="${gf.dicCode=='ProductRulesEnum020003'}">
					<p>2、可连续出租日期仅为2天时，预订价格可享受当日房价<fmt:parseNumber integerOnly='true' value='${gf.dicVal*10}'/>折优惠</p>
				</c:if>
			</c:forEach>
		</div>
	</div>
</div>
	<!--/main-->
</div>
<script type="text/javascript" src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/layer/layer.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/common2.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/iscroll-probe.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/mui.min.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/mui.picker.min.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/mui.poppicker.js${VERSION}001"></script>
<!-- 修改  -->
<script type="text/javascript" src="${basePath }/js/common_830_2.js${VERSION}0013"></script>
<script>
	var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });

	var conHeight = $$(window).height()-$$('.header').height()-$$('.box_bottom').height();

	$$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
	$$('#main .box').css({'position':'absolute','width':'100%'});
	myScroll.refresh();
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

	var moreInfo = new IScroll('#moreInfoMainBox', { scrollbars: true, probeType: 3, mouseWheel: true });

	var conHeight2 = $$(window).height()-$$('.header').height() -48;

	$$('#moreInfoMainBox').css({'position':'relative','height':conHeight2,'overflow':'hidden'});
	$$('#moreInfoMainBox .box').css({'position':'absolute','width':'100%'});
	moreInfo.refresh();
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	//控制了解更多信息的开关
	function tomorePriceInfo(){

		$$("#mainForm").hide();
		$$("#moreInfo").show();
		moreInfo.refresh();
	}
	function offmorePriceInfo(){
		$$("#moreInfo").hide();
		$$("#mainForm").show();
	}
	(function($, doc) {
		$.init();
		$.ready(function() {
			var weekData = ${weekendData };
			//级联示例
			var weekPicker = new $.PopPicker({
				layer: 1
			});

			weekPicker.setData(weekData);
			var showWeekPickerButton = doc.getElementById('weekBtn');
			if(showWeekPickerButton.value != ''){
				var v1 = $$("#week").val();
				weekPicker.pickers[0].setSelectedValue(v1);
			}
			showWeekPickerButton.addEventListener('tap', function(event) {
				weekPicker.show(function(items) {
					jQuery.noConflict();
					showWeekPickerButton.value = items[0].text;
					$$("#week").val(items[0].value);
					jQuery("#weekLi").removeClass("c_ipt_li_none");
				});
			}, false);
		});

	})(mui, document);
	$$(function(){
		//初始化格式化小数
		if(!isNullOrBlank($$("#qRebate").val())){
			$$("#qRebate").val(parseFloat($$("#qRebate").val())+"折");
		}
		if(!isNullOrBlank($$("#ssRebate").val())){
			$$("#ssRebate").val(parseFloat($$("#ssRebate").val())+"折");
		}
		
		$$(".j_li_r").each(function(){
			$$(this).click(function(){
				if($$(this).hasClass("org")){
					$$(this).removeClass("org").addClass("gray");
					$$(this).parents("li").removeClass("bor_b").siblings("li").hide();
				}else{
					$$(this).removeClass("gray").addClass("org");
					$$(this).parents("li").addClass("bor_b").siblings("li").show();
					if(isNullOrBlank($$("#qRebate").val())){
						if('${seven.dicVal}' != '-1' && !isNullOrBlank('${seven.dicVal}')){
							var pv='${seven.dicVal/10}';
							$$("#qRebate").val(parseFloat(pv)+'折');
						}
					}
					if(isNullOrBlank($$("#ssRebate").val())){
						if('${thirty.dicVal}' != '-1' && !isNullOrBlank('${thirty.dicVal}')){
							var tv='${thirty.dicVal/10}';
							$$("#ssRebate").val(parseFloat(tv)+'折');
						}
					}

				}
				myScroll.refresh();
			})
		})
		function isNullOrBlank(obj){
			return obj == undefined || obj == null || $$.trim(obj).length == 0;
		}
		$$("#submitBtn").click(function(){
			var priceLow = '${priceLow}';
			var priceHigh = '${priceHigh}';
			//固定价格
			var fixPrice = $$("#fixPrice").val();
			if($$("#fixPrice").val() == ''){
				showShadedowTips("请填写固定价格",2);
				return ;
			}
			fixPrice = fixPrice.replace("元/晚","");
			if(parseInt(fixPrice,10) > parseInt(priceHigh,10)){
				showShadedowTips("固定价格不能高于"+priceHigh+"元/晚",2);
				return ;
			}
			if(parseInt($$("#fixPrice").val(),10) <parseInt(priceLow,10)){
				showShadedowTips("固定价格不能低于"+priceLow+"元/晚",2);
				return ;
			}

			var setTime = $$("#week").val();
			var specialPrice = $$("#weekPrice").val();
			specialPrice = specialPrice.replace("元/晚","");
			var weekendPriceFlag = $$("#weekendPriceFlag").val();
			var weekendPriceSwitch = 0;
			if($$("#weekList").find(".j_li_r").hasClass("org")){
				if(setTime == ''){
					showShadedowTips("请选择设置日期",2);
					return ;
				}
				
				if(specialPrice == ''){
					showShadedowTips("请填写周末价格",2);
					return ;
				}
				
		        var regexp = new RegExp("^\[1-9][0-9]*$");
		        var isPositiveInteger = regexp.test(specialPrice);
	            if(!isPositiveInteger){
					showShadedowTips("请填写正确价格",2);
		            return false;
	            }

		    	if(parseInt(specialPrice, 10) < parseInt(priceLow, 10)){
					showShadedowTips('周末价格不能低于'+priceLow+'元/晚',2);
		    		return;
		    	}
		    	
		    	if(parseInt(specialPrice, 10) > parseInt(priceHigh, 10)){
					showShadedowTips('周末价格不能高于'+priceHigh+'元/晚',2);
		    		return;
		    	}
		    	weekendPriceSwitch = 1;
			}
			var gapFlex = 0;
			var gapFlexT = 0;
			//灵活定价总开关
			var gapFlexZ=0;
			//是否设置 今日特惠折扣
			if($$("#gapFlexList").find(".j_li_r").hasClass("org")){
				gapFlex = 1;
			}else{
				gapFlex = 0;
			}
			//是否设置 空间自动折扣
			if($$("#gapFlexTList").find(".j_li_r").hasClass("org")){
				gapFlexT = 1;
			}else{
				gapFlexT = 0;
			}
			if(gapFlex==1||gapFlexT==1){
				gapFlexZ=1;
			}
			var disRateSwitch =0;
			var longTermLimit = '${longTermLimit}';
			var qRebate = '';
			var ssRebate = '';
			if(longTermLimit<500){
				qRebate = $$("#qRebate").val().replace("折","");
				ssRebate = $$("#ssRebate").val().replace("折","");
				qRebate=qRebate*10;
				ssRebate=ssRebate*10;
			}
			if($$("#rebateList").find(".j_li_r").hasClass("org")){
				disRateSwitch = 1;
				if(!isNullOrBlank($$("#qRebate").val())){
					if(qRebate > 100 || qRebate <= 0){
						showShadedowTips("请填写正确入住满7天折扣",2);
						return ;
					}
				}
				if(!isNullOrBlank($$("#ssRebate").val())){
					if(ssRebate > 100 || ssRebate <= 0){
						showShadedowTips("请填写正确入住满30天折扣",2);
						return ;
					}
				}
				if(isNullOrBlank($$("#qRebate").val()) && isNullOrBlank($$("#ssRebate").val())){
					showShadedowTips("请至少填写一个折扣，或者关闭开关",2);
					return ;
				}
			} else {
				disRateSwitch = 0;
			}
			var houseBaseFid = '${houseBaseFid}';
			var houseRoomFid = '${houseRoomFid }';
			var rentWay = '${rentWay }';
			var flag = "${flag}";
			var sevenFid = '${seven.fid}';
			if(sevenFid ==''){
				sevenFid = '${sevenRate.fid}';
			}
			var thirtyFid = '${thirty.fid}';
			if(thirtyFid == ''){
				thirtyFid = '${thirtyRate.fid}';
			}
			//插入还是更新的标志
			var statFlag = '${statFlag}';
			var requestUrl = '';
			if(statFlag == 0){
				requestUrl = "${basePath}houseIssue/${loginUnauth}/savePriceSet";
			}else if(statFlag == 1){
				requestUrl = "${basePath}houseIssue/${loginUnauth}/updatePriceSet";
			}
			// TODO:防止重复提交
			$$(this).attr("disabled","disabled").removeClass("org_btn").addClass("disabled_btn");
			// 请求成功或失败后
			//获得上一个的数据
			var roomName = '${houseRoomMsg.roomName}';
			var roomArea = '${houseRoomMsg.roomArea}';
			var cfee = '${houseRoomMsg.roomCleaningFees}';
			var limit = '${houseRoomMsg.checkInLimit}';
			var toilet = '${houseRoomMsg.isToilet}';
			if(ssRebate==0){
				ssRebate="";
			}
			if(qRebate==0){
				qRebate="";
			}
			$$.ajax({
			url: requestUrl,
			dataType: "json",
			async: false,
			data: {
				"houseBaseFid":houseBaseFid,
				"houseRoomFid":houseRoomFid,
				"rentWay" : rentWay,
				"leasePrice" : fixPrice,
				"roomPrice" : fixPrice,
				"gapAndFlexiblePrice" : gapFlexZ,
				"dayDiscount":gapFlex,
				"flexDiscount":gapFlexT,
				"fullDayRate" : disRateSwitch,
				"sevenFid" : sevenFid,
				"sevenDiscountRate" : qRebate,
				"thirtyFid" : thirtyFid,
				"thirtyDiscountRate" : ssRebate,
				"setTime" : setTime,
				"specialPrice" : specialPrice,
				"weekendPriceFlag" : weekendPriceFlag,
				"weekendPriceSwitch" : weekendPriceSwitch,
				"roomName" : roomName,
				"roomArea" : roomArea,
				"roomCleaningFees" : cfee,
				"checkInLimit" : limit,
				"isToilet" : toilet
			},
			type: "POST",
			success: function (result) {
				if(result.code === 0){
					var sp = result.data.specialPriceDto;
					if(flag == 3  && rentWay==1 && result.data.redis == 1){//分租租发布房源-首次
						//从redis中拿到的价格
						var orPrice = sp.roomPrice;
						var roomFid = sp.houseRoomFid;
						roomName = encodeURI(roomName);
						roomName = encodeURI(roomName);
						window.location.href = "${basePath}roomMgt/${loginUnauth}/toRoomDetail?houseBaseFid=${houseBaseFid}&flag=3&houseRoomFid="+roomFid+"&rentWay=1&priceFlag=1&orPrice="+orPrice+"&roomName="+roomName+"&roomArea="+roomArea+"&cfee="+cfee+"&isToilet="+toilet+"&limit="+limit;
					}else if(result.data.redis == 1 && flag ==0){//分租首次发布房源
						var orPrice = sp.roomPrice;
						var roomFid = sp.houseRoomFid;
						roomName = encodeURI(roomName);
						roomName = encodeURI(roomName);
						window.location.href = "${basePath}roomMgt/${loginUnauth}/toRoomDetail?houseBaseFid=${houseBaseFid}&flag=0&houseRoomFid="+roomFid+"&rentWay=1&priceFlag=1&orPrice="+orPrice+"&roomName="+roomName+"&roomArea="+roomArea+"&cfee="+cfee+"&isToilet="+toilet+"&limit="+limit;
					}else if(flag == 0 && rentWay == 0){//整租发布房源
						window.location.href = "${basePath}houseInput/${loginUnauth}/findHouseDetail?houseBaseFid=${houseBaseFid}&rentWay=0&roomFid=${houseRoomFid}";
					} else if(flag == 0 && rentWay == 1){//分租租发布房源-非首次
						window.location.href = "${basePath}roomMgt/${loginUnauth}/toRoomDetail?houseBaseFid=${houseBaseFid}&flag=${flag}&roomFid=${houseRoomFid}&rentWay=1";
					} else if(flag == 1 && rentWay == 0){//整租修改房源
						window.location.href = "${basePath}houseInput/${loginUnauth}/findHouseDetail?houseBaseFid=${houseBaseFid}&rentWay=0&flag=${flag}&houseRoomFid=${houseRoomFid}";
					}else if(flag==1 && rentWay==1){//分租修改房源
						window.location.href = "${basePath}roomMgt/${loginUnauth}/toRoomDetail?houseBaseFid=${houseBaseFid}&flag=${flag}&roomFid=${houseRoomFid}&rentWay=1";
					}else if(flag == 4){//日历价格设置
						window.location.href = "${basePath}houseMgt/${loginUnauth}/calendarDetail?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid}&rentWay=${rentWay}";
					}
				} else {
					showShadedowTips(result.msg,1);
					$$(this).removeAttr("disabled").removeClass("disabled_btn").addClass("org_btn");
				}
			},
			error:function(result){
				showShadedowTips("未知错误",1);
				$$(this).removeAttr("disabled").removeClass("disabled_btn").addClass("org_btn");
			}
			});
			// $$(this).removeAttr("disabled").removeClass("disabled_btn").addClass("org_btn");

		})
	})
</script>
</body>
</html>
