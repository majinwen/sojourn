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
	<title>位置信息</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
	<style type="text/css">
		body,.listLi_box{overflow: hidden;}
		.listLi_box ul{overflow-y: auto;}
	</style>
</head>
<body style="height:100%;">
	<header class="header">
		<c:if test="${flag==1 }">
			<a href="${basePath }houseDeploy/${loginUnauth }/toLocationThree?houseBaseFid=${houseBaseFid}&rentWay=${rentWay}&flag=${flag }&houseRoomFid=${houseRoomFid}" id="goback" class="goback" ></a>
		</c:if>
		<c:if test="${flag!=1 }">
			<a href="${basePath }houseDeploy/${loginUnauth }/toLocation?houseBaseFid=${houseBaseFid}&rentWay=${rentWay}&houseType=${houseType }" id="goback" class="goback" ></a>
		</c:if>
		<h1>位置信息</h1>
	</header>
	
	<form action="${basePath}houseDeploy/${loginUnauth}/toLocationFromOthers" method="get" id="form">
		<input type="hidden" name="houseBaseFid" value="${houseBaseFid }">
		<input type="hidden" name="houseType" value="${houseType }">
		<input type="hidden" name="rentWay" value="${rentWay }">
		<input type="hidden" name="nationCode" value="${nationCode }">
		<input type="hidden" name="provinceCode" value="${provinceCode }">
		<input type="hidden" name="cityCode" value="${cityCode }">
		<input type="hidden" name="areaCode" value="${areaCode }">
		<input type="hidden" name="communityName" value="${communityName }">
		<input type="hidden" name="houseStreet" value="${houseStreet }">
		<input type="hidden" name="flag" value="${flag }">
		<input type="hidden" name="houseRoomFid" value="${houseRoomFid }">
		<input type="hidden" name="detailAddress" value="${detailAddress }">
		<input type="hidden" name="detailAddress" value="${detailAddress }">
		<input type="hidden" name="housePhyFid" value="${housePhyFid }">
		
		<div >
			<ul class="myCenterList myCenterList_no addressList clearfix">
				<li class="listLi_box">
					<ul class="county">
						<c:forEach items="${cityTreeList}" var="nation" varStatus="nationId">
							<c:if test="${nationCode == ''}">
							<li data-to="${nation.code}" class="<c:if test="${nationId.index == 0}">active</c:if> listLi">${nation.text}</li>
							</c:if>
							<c:if test="${nationCode != ''}">
							<li data-to="${nation.code}" class="<c:if test="${nation.code == nationCode}">active</c:if> listLi">${nation.text}</li>
							</c:if>
						</c:forEach>
					</ul>
				</li>
				<li class="listLi_box">
				 
				    <c:forEach items="${cityTreeList}" var="nation" varStatus="nationId">
				    
				       <c:if test="${nation.code =='100000'}">
				          <ul class="province ${nation.code}">
				          <c:forEach items="${nation.nodes}" var="province" varStatus="provinceId">
								<li data-to="${province.code}" class="<c:if test="${province.code == provinceCode}">active</c:if> listLi">${province.text}</li>
					  	 </c:forEach>
				       </c:if>
				         <c:if test="${nation.code !='100000'}">
				          <ul class="province ${nation.code} city_${nation.code}">
				           <c:forEach items="${nation.nodes}" var="province" varStatus="provinceId">
								<c:forEach items="${province.nodes}" var="city" varStatus="cityId">
								 <li data-to="${city.code}" data-to-province="${province.code}" class="<c:if test="${city.code == cityCode}">active</c:if> listLi">${city.text}</li>
								</c:forEach>
						</c:forEach>
				       </c:if>
					</ul>
					</c:forEach>
				 
				  
				</li>
				<li class="listLi_box">
					<c:forEach items="${cityTreeList}" var="nation" varStatus="nationId">
					<c:forEach items="${nation.nodes}" var="province" varStatus="provinceId">
						<ul class="city ${province.code}">
							<c:forEach items="${province.nodes}" var="city" varStatus="cityId">
							 <li data-to="${city.code}" class="<c:if test="${city.code == cityCode}">active</c:if> listLi">${city.text}</li>
							</c:forEach>
					   </ul>
					</c:forEach>
					</c:forEach>
				</li>
				<li class="listLi_box">
					<c:forEach items="${cityTreeList}" var="nation" varStatus="nationId">
					<c:forEach items="${nation.nodes}" var="province" varStatus="provinceId">
					<c:forEach items="${province.nodes}" var="city" varStatus="cityId">
						<ul class="area ${city.code}">
							<c:forEach items="${city.nodes}" var="area" varStatus="areaId">
							<li data-to="${area.code}" class="<c:if test="${area.code == areaCode}">active</c:if> listLi">${area.text}</li>
							</c:forEach>
						</ul>
					</c:forEach>
					</c:forEach>
					</c:forEach>
				</li>
			</ul>
		</div>
	</form>
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<script type="text/javascript">
	$(function(){
		$(".listLi_box ul.county li.listLi").each(function(){
			if($(this).hasClass('active')){
				var dataTo = $(this).attr("data-to");
				$("[name='nationCode']").val(dataTo); 
				selectCity($(this));
				$(this).addClass('active');
			}
		})
		 
		
		$(".listLi_box li.listLi").each(function(){
			$(this).click(function(){
				selectCity($(this));
			})
		})
		
		function selectCity(obj){
			var nationCode = $("[name='nationCode']").val();
			var index = obj.parents(".listLi_box").index()+1;
			var dataTo=obj.attr("data-to");
			if(index == 1){
				nationCode = dataTo;
			}
			obj.addClass("active").siblings().removeClass("active");
			if(nationCode=="100000"){
				
				$(".listLi_box:gt("+index+")").hide();
				$(".listLi_box:lt("+index+")").show();
				$(".listLi_box:eq("+index+")").show();
				$(".listLi_box:eq("+index+")").find(".listLi").each(function(){
					obj.removeClass("active");
				})
				$("."+dataTo).show();
				$("."+dataTo).siblings().hide();
				
				if(index==1){
					$("[name='nationCode']").val(dataTo);
					$("[name='provinceCode']").val('');
					$("[name='cityCode']").val('');
					$("[name='areaCode']").val('');
				}else if(index==2){
					$("[name='provinceCode']").val(dataTo);
					$("[name='cityCode']").val('');
					$("[name='areaCode']").val('');
				}else if(index==3){
					$("[name='cityCode']").val(dataTo);
					$("[name='areaCode']").val('');
				}else if(index==4){
					$("[name='areaCode']").val(dataTo);
				}
			}else{
				
			    $(".listLi_box:gt("+index+")").hide();
				$(".listLi_box:lt("+index+")").show();
				if(index==1){
					$(".listLi_box:eq("+index+")").show(); 
				}
				
				$(".listLi_box:eq("+index+")").find(".listLi").each(function(){
					obj.removeClass("active");
				})
				$("."+dataTo).show();
				$("."+dataTo).siblings().hide();
				var cls = "city_"+dataTo;
				$("."+cls).show();
				
				if(index==1){
					$("[name='nationCode']").val(dataTo);
					$("[name='provinceCode']").val('');
					$("[name='cityCode']").val('');
					$("[name='areaCode']").val('');
				}else if(index==2){
					var provinceCode =obj.attr("data-to-province");
					$("[name='provinceCode']").val(provinceCode);
					$("[name='cityCode']").val(dataTo);
					$("[name='areaCode']").val('');
					$('#form').submit();
				}
				
			}
			
			 if($("."+dataTo).children().length==0){
				$('#form').submit();
			}
			/* if($("."+dataTo).children().length==1){
				$("."+dataTo).children().click();
			} */ 
		}
		$(".area li.listLi").each(function(){
			$(this).click(function(){
				$('#form').submit();
			})
		})
		$(".listLi_box").css('height',$(window).height()-$(".header").height()+'px')
	})
	</script>
</body>
</html>
