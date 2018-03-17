<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
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
			<a href="${basePath }landlordEva/43e881/index" class="goback"></a>
		</c:if>

		<h1>评价详情</h1>
	</header>
	
	<input type="hidden" id="orderSn" name="orderSn" value="${orderSn }"/>
	<div class="main">
		<div class="calendarTop clearfix pingjiaTop">
			<a href="javascript:;" class="clearfix">
				<div class="img"><img src="${evaluateInfoVo.picUrl }"></div>
				<div class="txt">
					<h3 class="gray4">${evaluateInfoVo.houseName }</h3>
					<p class="gray6">${evaluateInfoVo.startTimeStr }至${evaluateInfoVo.endTimeStr } &nbsp;
					&nbsp;共${evaluateInfoVo.housingDay }晚</p>					
					<p class="gray6">
					预订人：${evaluateInfoVo.userName }  
					&nbsp;
					&nbsp;共${evaluateInfoVo.contactsNum }位入住</p>
				</div>
				<!--span class="icon_r icon"></span-->

			</a>	
		</div>
		<c:if test="${evaluateInfoVo.evaStatus == 100 || evaluateInfoVo.evaStatus == 101}">
			<div class="bgF mt75 pt10">
			<ul class="ydrDpList">
				<li>
					<div class="img"><img src="${evaluateInfoVo.userPicUrl }"></div>
						
					<h3 class="tit">${evaluateInfoVo.userName }</h3>
					
					<p class="gray">您还未评价，暂时看不到房客对您的评价。</p>
				</li>
			</ul>
		</div>
		
		<div class="bgF mt75 boxP075">
			<div class="boxPTB5 pingjiaBox">
				<div class="t clearfix">
					<span class="fl fz7">为您的房客评分</span>
					<p class="starBig fr">
						<i class=""></i>
						<i class=""></i>
						<i class=""></i>
						<i class=""></i>
						<i class=""></i>
						<input type="hidden" value="0" id="stars">
					</p>
				</div>

				<textarea placeholder="请输入对房客的评价" id="evaContent"></textarea>


			</div>
			
			
		</div>
		</c:if>
		
		<c:if test="${evaluateInfoVo.evaStatus == 110}">
		<div class="bgF mt75 pt10">
			<ul class="ydrDpList">
				<li>
					<div class="img"><img src="${evaluateInfoVo.userPicUrl }"></div>
						
					<h3 class="tit">${evaluateInfoVo.userName }</h3>
					
					<p class="gray">房客暂未发表点评。</p>
				</li>
			</ul>
		</div>
		<div class="bgF mt75 pt10">
			<ul class="ydrDpList">		
				<li>
					<div class="img"><img src="${evaluateInfoVo.landlordPicUrl }"></div>
					<h3 class="tit">房东点评 <small>${evaluateInfoVo.landlordEvaluate.landlordSatisfied }分</small></h3>
					<div class="lineD100"></div>
					<p>${evaluateInfoVo.landlordEvaluate.content }</p>
					<p class="gray2">${evaluateInfoVo.lanTimeStr }</p>
				</li>
			</ul>
			
		</div>
		</c:if>
		
		<c:if test="${evaluateInfoVo.evaStatus == 111}">
		
		<c:if test="${not empty evaluateInfoVo.tenantEvaluate}">
			<div class="bgF mt75 pt10">
			<ul class="ydrDpList">
				<li>
					<div class="img"><img src="${evaluateInfoVo.userPicUrl }"></div>
						
					<h3 class="tit">${evaluateInfoVo.userName }</h3>
					<ul class="clearfix">
						<li><span>清洁度</span> ${evaluateInfoVo.tenantEvaluate.houseClean }分</li>
						<li><span>描述相符</span>  ${evaluateInfoVo.tenantEvaluate.descriptionMatch }分</li>
						<li><span>周边环境</span>  ${evaluateInfoVo.tenantEvaluate.trafficPosition }分</li>
						<li><span>房东印象</span> ${evaluateInfoVo.tenantEvaluate.safetyDegree }分</li>
						<li><span><i class="letter">性</i><i class="letter">价</i>比</span>  ${evaluateInfoVo.tenantEvaluate.costPerformance }分</li>
					</ul>
					<div class="lineD100"></div>
					<p>${evaluateInfoVo.tenantEvaluate.content }</p>
					<p class="gray2">${evaluateInfoVo.tenTimeStr }</p>
				</li>
			</ul>
		</div>
		</c:if>
		<c:if test="${empty evaluateInfoVo.tenantEvaluate}">
			<div class="bgF mt75 pt10">
				<ul class="ydrDpList">
					<li>
						<div class="img"><img src="${evaluateInfoVo.userPicUrl }"></div>
							
						<h3 class="tit">${evaluateInfoVo.userName }</h3>
						
						<p class="gray">房客暂未发表点评。</p>
					</li>
				</ul>
			</div>
		</c:if>
		<div class="bgF mt75 pt10">
			<ul class="ydrDpList">		
				<li>
					<div class="img"><img src="${evaluateInfoVo.landlordPicUrl }"></div>
					<h3 class="tit">房东点评 <small>${evaluateInfoVo.landlordEvaluate.landlordSatisfied }分</small></h3>
					<div class="lineD100"></div>
					<p>${evaluateInfoVo.landlordEvaluate.content }</p>
					<p class="gray2">${evaluateInfoVo.lanTimeStr }</p>
				</li>
			</ul>
			
		</div>
		</c:if>
		
	</div><!--/main-->

<c:if test="${evaluateInfoVo.evaStatus == 100 || evaluateInfoVo.evaStatus == 101}">
<!--ul class="stateEvents detailBtns clearfix" id="submitStar">
	<li>提交</li>
</ul-->
<div class="boxP075 mt85 pt10">
	<input type="button" class="org_btn " value="提交" id="submitStar">
</div>
</c:if>	

<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/evaluate/evaLan.js${VERSION}005"></script>
</body>
</html>