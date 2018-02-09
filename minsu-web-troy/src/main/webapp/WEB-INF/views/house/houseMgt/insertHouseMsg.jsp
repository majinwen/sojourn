<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<base href="${basePath}">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">

    <link href="${staticResourceUrl}/favicon.ico${VERSION}" rel="shortcut icon">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.min.css${VERSION}" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
	<style>
		.lightBoxGallery img {
			margin: 5px;
			width: 160px;
		}
		
		.mtop {
			margin-top: 10px;
		}
		
		.help-block {
			margin-left: -10px;
		}
		
		.hide {
			display: none;
		}
	</style>
	<style>
		ul {
			width: 20%;
			text-align: center;
		}
		
		li {
			display: inline-block;
		}
		
		/* toggle switch css start */
		.switch-btn {
			position: relative;
			display: block;
			vertical-align: top;
			width: 80px;
			height: 30px;
			border-radius: 18px;
			cursor: pointer;
		}
		
		.checked-switch {
			position: absolute;
			top: 0;
			left: 0;
			opacity: 0;
		}
		
		.text-switch {
			background-color: #ed5b49;
			border: 1px solid #d2402e;
			border-radius: inherit;
			color: #fff;
			display: block;
			font-size: 15px;
			height: inherit;
			position: relative;
			text-transform: uppercase;
		}
		
		.text-switch:before, .text-switch:after {
			position: absolute;
			top: 50%;
			margin-top: -.5em;
			line-height: 1;
			-webkit-transition: inherit;
			-moz-transition: inherit;
			-o-transition: inherit;
			transition: inherit;
		}
		
		.text-switch:before {
			/* content: attr(data-no); */
			right: 11px;
		}
		
		.text-switch:after {
			/* content: attr(data-yes); */
			left: 11px;
			color: #FFF;
			opacity: 0;
		}
		
		.checked-switch:checked ~ .text-switch {
			background-color: #00af2c;
			border: 1px solid #068506;
		}
		
		.checked-switch:checked ~ .text-switch:before {
			opacity: 0;
		}
		
		.checked-switch:checked ~ .text-switch:after {
			opacity: 1;
		}
		
		.toggle-btn {
			background: linear-gradient(#eee, #fafafa);
			border-radius: 100%;
			height: 28px;
			left: 1px;
			position: absolute;
			top: 1px;
			width: 28px;
		}
		
		.toggle-btn::before {
			color: #aaaaaa;
			display: inline-block;
			font-size: 12px;
			letter-spacing: -2px;
			padding: 4px 0;
			vertical-align: middle;
		}
		
		.checked-switch:checked ~ .toggle-btn {
			left: 51px;
		}
		
		.text-switch, .toggle-btn {
			transition: All 0.3s ease;
			-webkit-transition: All 0.3s ease;
			-moz-transition: All 0.3s ease;
			-o-transition: All 0.3s ease;
		}
		/* toggle switch css end */
	</style>
</head>
  
  <body class="gray-bg">
	   <div id="blueimp-gallery" class="blueimp-gallery">
	       <div class="slides"></div>
	       <h3 class="title"></h3>
	       <a class="prev">‹</a>
	       <a class="next">›</a>
	       <a class="close">×</a>
	       <a class="play-pause"></a>
	       <ol class="indicator"></ol>
	   </div>
	   <div class="wrapper wrapper-content animated fadeInRight">
	   	<form action="house/houseMgt/saveHouseMsg" method="post" id="saveHouse">
	       <div class="row">
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				           	<div class="form-group">
		                       <div class="row">
	                               <div class="col-sm-1">
			                   	   <label class="control-label mtop">出租方式:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b m-b"  name="houseBase.rentWay" id="rentWay">
	                                   		<option value="0">整租</option>
	                                   		<option value="1">合租</option>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">房源类型:</label>
		                           <div class="col-sm-2">
										<select class="form-control m-b" name="houseBase.houseType" >
			                           		<c:forEach items="${homeTypeList }" var="stay">
			                           			<option value="${stay.key }">${stay.text }</option>
			                           		</c:forEach>
		                           		</select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">房源名称:</label>
		                           <div class="col-sm-2">
	                                  <input name="houseBase.houseName" value="" type="text" class="form-control m-b" required>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">小区名称:</label>
		                           <div class="col-sm-2">
										<input name="housePhy.communityName" value="" type="text" class="form-control m-b" required>
	                               </div>
		                       </div>
		                       <div class="row">
		                             <div class="col-sm-1">
			                   	   <label class="control-label mtop">国家/地区:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b m-b" name="housePhy.nationCode" id="nationCode" required>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">省:</label>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b " name="housePhy.provinceCode" id="provinceCode" required>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">市:</label>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b" name="housePhy.cityCode"  id="cityCode" required>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">区:</label>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b" name="housePhy.areaCode"  id="areaCode" required>
	                                   </select>
	                               </div>
		                       </div>
		                       
	                           <div class="hr-line-dashed"></div>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">街道:</label>
	                               </div>
		                           <div class="col-sm-3">
	                                   <input name="houseExt.houseStreet" value="" type="text" class="form-control m-b" required>
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">楼号:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input name="houseExt.buildingNum" value=""  type="text" class="form-control m-b">
	                               </div>
				                   <label class="col-sm-1 control-label mtop">单元号:</label>
		                           <div class="col-sm-1">
	                                   <input name="houseExt.unitNum" value="" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">楼层:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input  name="houseExt.floorNum" value="" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">门牌号:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input  name="houseExt.houseNum" value="" type="text" class="form-control m-b">
	                               </div>
		                           
		                       </div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">房东uid:</label>
		                           <div class="col-sm-2">
		                           	   <input name="houseBase.landlordUid" value="" type="text" class="form-control m-b" required>
	                               </div>
		                           <div class="col-sm-1">
		                           	   <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#landlordModel" onclick="resetForm();">选择房东</button>
	                               </div>
				                   <label class="col-sm-1 control-label entireRentPrice mtop">整租价格:</label>
		                           <div class="col-sm-1 entireRentPrice">
		                           	   <input id="housePrice" name="houseBase.leasePrice" value="" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">房源面积:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input name="houseBase.houseArea" value="" type="text" class="form-control m-b" required>
	                               </div>
	                               <div class="col-sm-1">
				                   	   <label class="control-label mtop">卧室:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input name="houseBase.roomNum" value="" type="text" class="form-control m-b" required>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">厨房:</label>
		                       		<div class="col-sm-1">
		                           	   <input name="houseBase.kitchenNum" value="" type="text" class="form-control m-b" required>
	                               </div>
		                       </div>
		                       <div class="row">
		                       <label class="col-sm-1 control-label mtop">客厅:</label>
		                           <div class="col-sm-1">
		                           	   <input name="houseBase.hallNum" value="" type="text" class="form-control m-b" required>
	                               </div>
	                                <div class="col-sm-1">
				                   	   <label class="control-label mtop">厕所:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input name="houseBase.toiletNum" value="" type="text" class="form-control m-b" required>
	                               </div>
		                       	   <label class="col-sm-1 control-label mtop">阳台:</label>
		                           <div class="col-sm-1">
		                           	   <input name="houseBase.balconyNum" value="" type="text" class="form-control m-b" required>
	                               </div>
		                       	   
	                               
	                               <label class="col-sm-1 control-label mtop">入住人数:</label>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b" name="houseExt.checkInLimit" >
		                           	   	  <c:forEach items="${limitList }" var="obj">
			                                  <option value="${obj.key }" >${obj.text }</option>
		                                  </c:forEach>
	                                   </select>
	                               </div>
	                               <label class="col-sm-2 control-label mtop">最少入住天数:</label>
		                           <div class="col-sm-1">
		                           	   <input name="houseExt.minDay" value="" type="text" class="form-control m-b" maxlength="1" required>
	                               </div>
	                               
		                       </div>
		                       <div class="row">
		                       	  <label class="col-sm-1 control-label mtop">被单更换:</label>
		                           <div class="col-sm-2">
			                           <select class="form-control m-b" name="houseExt.sheetsReplaceRules" >
			                           		<option value="0">每客一换</option>
			                           		<option value="1">每日一换</option>
			                           </select>
	                               </div>
	                               <!-- 可预订截止日期去掉  -->
		                       	  <!--  <label class="col-sm-2 control-label mtop">可预订截止日期:</label>
		                           <div class="col-sm-2">
		                           	   <input  id="tillDate" name="tillDate" class="laydate-icon form-control layer-date" required>
	                               </div> -->
		                       </div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">经度:</label>
		                           <div class="col-sm-2">
		                           	   <input name="housePhy.googleLongitude" value="" type="text" class="form-control m-b ">
	                               </div>
	                               <label class="col-sm-1 control-label mtop">纬度:</label>
		                           <div class="col-sm-2">
		                           	   <input name="housePhy.googleLatitude" value="" type="text" class="form-control m-b" >
	                               </div>
	                               <div class="col-sm-3">
							  	   	    <customtag:authtag authUrl="config/subway/toMap"><button class="btn btn-primary" type="button" onclick="toBaiduMap();">没有获取坐标？去地图获取坐标</button></customtag:authtag>
		                       	   </div>
		                       </div>
		                       <div class="row">
		                       	  <label class="col-sm-1 control-label mtop">房源描述:</label>
		                           <div class="col-sm-5">
		                           	   <textarea name="houseDesc.houseDesc" value="" type="text" class="form-control m-b" ></textarea>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">周边信息:</label>
		                           <div class="col-sm-5">
		                           	   <textarea name="houseDesc.houseAroundDesc" value="" type="text" class="form-control m-b"></textarea>
	                               </div>
							   </div>
							   <div class="row">
							   	   <label class="col-sm-1 control-label mtop">房屋守则:</label>
		                           <div class="col-sm-5">
		                           	   <textarea name="houseDesc.houseRules" value="" type="text" class="form-control m-b"></textarea>
	                               </div>
							   </div>
							   <!-- 灵活定价&折扣设置 start -->
                               <div class="hr-line-dashed set-price set-price-house-add set-price-house-remove"></div>
		                       <label class="help-block m-b-none set-price set-price-house-add set-price-house-remove" style="font-size:20px;">价格设置</label>
							   <div class="row set-price set-price-house-add set-price-house-remove">
							   	   <label class="col-sm-1 control-label mtop">灵活价格:</label>
							   	   <ul>
							   	   	   <li>
							   	   	   	   <label class="switch-btn">
							   	   	   	   	   <input type="hidden" class="set-price" name="sp.gapAndFlexiblePrice" default-value="0" value="0">
							   	   	   	   	   <input class="checked-switch set-price" type="checkbox" onclick="clickCheckbox(this)">
							   	   	   	   	   <span class="text-switch"></span>
							   	   	   	   	   <span class="toggle-btn"></span>
							   	   	   	   </label>
							   	   	   </li>
							   	   </ul>
							   </div>
							   <div class="row set-price-house-add hide">
							   	  <c:forEach items="${gapList}" var="gf">
			                           <c:if test="${gf.dicCode=='ProductRulesEnum020001'}">
			                        	   <label class="col-sm-2 control-label mtop">预订当日价格:</label>
			                        	   <div class="col-sm-1">
				                           	  <input type="text" class="form-control m-b" value="<fmt:parseNumber integerOnly='true' value='${gf.dicVal*100}'/>%" readonly="readonly">
			                               </div>
				                       </c:if>
			                           <c:if test="${gf.dicCode=='ProductRulesEnum020002'}">
			                        	   <label class="col-sm-2 control-label mtop">仅1日可连续预订价格:</label>
			                        	   <div class="col-sm-1">
				                           	  <input type="text" class="form-control m-b" value="<fmt:parseNumber integerOnly='true' value='${gf.dicVal*100}'/>%" readonly="readonly">
			                               </div>
				                       </c:if>
			                           <c:if test="${gf.dicCode=='ProductRulesEnum020003'}">
			                        	   <label class="col-sm-2 control-label mtop">仅2日可连续预订价格:</label>
			                        	   <div class="col-sm-1">
				                           	  <input type="text" class="form-control m-b" value="<fmt:parseNumber integerOnly='true' value='${gf.dicVal*100}'/>%" readonly="readonly">
			                               </div>
				                       </c:if>
			                        </c:forEach>
							   </div>
							   <div class="row set-price set-price-house-add set-price-house-remove">
							   	   <label class="col-sm-1 control-label mtop">折扣设置:</label>
							   	   <ul>
							   	   	   <li>
							   	   	   	   <label class="switch-btn">
							   	   	   	   	   <input type="hidden" name="sp.fullDayRate" default-value="0" value="0">
							   	   	   	   	   <input class="checked-switch set-price" type="checkbox" onclick="clickCheckbox(this)">
							   	   	   	   	   <span class="text-switch"></span>
							   	   	   	   	   <span class="toggle-btn"></span>
							   	   	   	   </label>
							   	   	   </li>
							   	   </ul>
							   </div>
							   <div class="row set-price-house-add hide">
	                        	   <label class="col-sm-2 control-label mtop">入住满7天折扣:</label>
	                        	   <div class="col-sm-1">
		                           	  <input type="hidden" class="set-price" name="sp.sevenDiscountRate" default-value="-1" value="-1">
		                           	  <input type="text" class="form-control m-b set-price" default-value="" onkeyup="validateInput(this)" onblur="validateBlur(this)" onfocus="inputOnFocus(this)">
	                               </div>
	                        	   <label class="col-sm-2 control-label mtop">入住满30天折扣:</label>
	                        	   <div class="col-sm-1">
		                           	  <input type="hidden" class="set-price" name="sp.thirtyDiscountRate" default-value="-1" value="-1">
		                           	  <input type="text" class="form-control m-b set-price" default-value="" onkeyup="validateInput(this)" onblur="validateBlur(this)" onfocus="inputOnFocus(this)">
	                               </div>
							   </div>
							   <!-- 灵活定价&折扣设置 end -->
							   
                               <div class="hr-line-dashed"></div>
		                       <label class="help-block m-b-none" style="font-size:20px;">房间信息</label>
		                       <div id="roomList">
		                       </div>
		                       <button id="addMenuButton" type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">添加房间</button>
		                       
                               <div class="hr-line-dashed"></div>
		                       <label class="help-block m-b-none" style="font-size:20px;">配套设施</label>
		                       <c:forEach items="${facilityList }" var="list">
			                       <div class="row">
			                           <div class="col-sm-1 mtop">
					                   	   <label class="control-label">${list.text }:</label>
		                               </div>
		                               <c:forEach items="${list.subEnumVals }" var="sub">
				                           <div class="col-sm-1">
				                           		<div class="checkbox i-checks">
		                                        <label><input type="checkbox" style="margin-top:2px;" name="facilityList" value="${list.key }-${sub.key }">${sub.text }</label>
		                                    	</div>
			                               </div>
		                               </c:forEach>
			                       </div>
		                       </c:forEach>
		                       	                
                               <div class="hr-line-dashed"></div>
		                       <label class="help-block m-b-none" style="font-size:20px;">服务</label>
								<div class="row">
			                       <c:forEach items="${serviceList }" var="list" varStatus="status">
												<div class="col-sm-2">
						                           	   <div class="checkbox i-checks">
					                                       <label><input type="checkbox" style="margin-top:2px;" name="serviceList" value="${list.key }">${list.text }</label>
					                                   </div>
			                               		</div>
			                       </c:forEach>
						  		</div>
		                       
                               <div class="hr-line-dashed"></div>
		                        <label class="help-block m-b-none mtop" style="font-size:20px;">交易信息</label>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">预订类型:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           		<select class="form-control m-b" name="houseExt.orderType" >
		                           			<option value="1">立即预订</option>
		                           			<option value="2" selected>申请预订</option>
		                           		</select>
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">民宿类型:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           		<select class="form-control m-b" name="houseExt.homestayType" >
			                           		<c:forEach items="${homestayList }" var="stay">
			                           			<option value="${stay.key }">${stay.text }</option>
			                           		</c:forEach>
		                           		</select>
	                               </div>
	                               <div class="col-sm-1">
				                   	   <label class="control-label mtop">入住时间:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           		<select class="form-control m-b" name="houseExt.checkInTime" >
			                           		<c:forEach items="${checkInTimeList }" var="stay">
			                           			<option value="${stay.key }">${stay.text }</option>
			                           		</c:forEach>
		                           		</select>
	                               </div>
	                               <div class="col-sm-1">
				                   	   <label class="control-label mtop">退房时间:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           		<select class="form-control m-b" name="houseExt.checkOutTime" >
			                           		<c:forEach items="${checkOutTimeList }" var="stay">
			                           			<option value="${stay.key }">${stay.text }</option>
			                           		</c:forEach>
		                           		</select>
	                               </div>
		                       </div>
		                       
	                           <div class="hr-line-dashed"></div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">优惠规则</label>
		                       <div class="row">
		                       <c:forEach items="${ discountList}" var="list" >
		                       	   <div class="col-sm-1">
				                   	   <label class="control-label mtop">${list.text }</label>
	                               </div>
		                           <div class="col-sm-2">
	                                    <select class="form-control m-b" name="discountList" >
			                           		<c:forEach items="${list.subEnumVals }" var="sub">
			                           			<option value="${list.key }-${sub.key }">${sub.text }</option>
			                           		</c:forEach>
		                           		</select>
	                               </div>
		                       </c:forEach>
		                       </div>	
		                       	                       
	                           <div class="hr-line-dashed"></div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">押金规则</label>
		                       <div class="row">
		                       		<!-- 
				                       <c:forEach items="${depositList }"  var="list" varStatus="status">
				                       			<div class="radio col-sm-1">
		                                        	<label class="control-label"><input type="radio" style="margin-top:2px;" value="${list.key }" name="houseExt.depositRulesCode"  ${status.index==0 ?'checked':''}> <i></i> ${list.text }</label>
		                                    	</div>
		                                    	<div class="col-sm-2">
				                                    <select class="form-control m-b" name="depositList" >
						                           		<c:forEach items="${list.subEnumVals }" var="sub">
						                           			<option value="${list.key }-${sub.key }">${sub.text }</option>
						                           		</c:forEach>
					                           		</select>
			                               		</div>
				                       </c:forEach>
		                        	-->
		                          <div class="col-sm-2">
		                           	   <input id="curDeposit" name="depositList" value="" type="text" class="form-control m-b" >
	                               </div>
		                        	
		                       </div>

	                           <div class="hr-line-dashed"></div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">退订政策</label>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">退订政策:</label>
	                               </div>
		                           <div class="col-sm-2">
										<select class="form-control m-b" name="houseExt.checkOutRulesCode" >
			                           		<c:forEach items="${checkOutList}" var="sub" varStatus="status">
			                           			<option value="${sub.key }" ${status.index==1 ?'selected':''}>${sub.text }</option>
			                           		</c:forEach>
		                           		</select>	                               
			                       </div>
			                       <label class="col-sm-2 control-label mtop">是否与房东同住:</label>
		                           <div class="col-sm-2">
		                           <select class="form-control m-b" name="houseExt.isTogetherLandlord" >
		                           		<option value="0">否</option>
		                           		<option value="1">是</option>
		                           </select>
	                               </div>
	                           </div>
			               </div>
			           </div>
	               </div>
       		       <div class="col-sm-4 col-sm-offset-2">
	                   <customtag:authtag authUrl="house/houseMgt/saveHouseMsg"><button class="btn btn-primary" id="btn_submit" type="submit" >保存内容</button></customtag:authtag>
	               </div>
	           </div>
	       </div>
	   </form>
	   </div>
		<!-- 添加 菜单  弹出框  添加房间-->
		<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content animated bounceInRight">
		       <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
		          </button>
		          <h4 class="modal-title">添加房间</h4>
		       </div>
		       <div class="modal-body">
			        <div class="wrapper wrapper-content animated fadeInRight">
				        <form id="roomFrom" class="row">
				          <div class="col-sm-14">
				               <div class="ibox float-e-margins">
				                   <div class="ibox-content" style="overflow:hidden;">
						                   <div class="form-group">
						                       <label class="col-sm-4 control-label mtop">房间名称：</label>
						                       <div class="col-sm-8">
						                           <input id="roomName" name="roomName" class="form-control" type="text" value="">
						                           <span class="help-block m-b-none"></span>
						                       </div>
						                   </div>
						                  <div class="form-group">
						                       <label class="col-sm-4 control-label mtop">房间面积：</label>
						                       <div class="col-sm-8">
						                           <input id="roomArea" name="roomArea" class="form-control" type="text" value="">
						                           <span class="help-block m-b-none"></span>
						                       </div>
						                 </div>
						                 <div class="form-group">
						                       <label class="col-sm-4 control-label mtop">房间日价格：</label>
						                       <div class="col-sm-8">
						                           <input id="roomPrice" name="roomPrice" class="form-control" type="text" value="">
						                           <span class="help-block m-b-none"></span>
						                       </div>
						                 </div>
						                 <div class="form-group">
						                       <label class="col-sm-4 control-label mtop">房间入住人数限制：</label>
						                       <div class="col-sm-8">
						                           <select class="form-control m-b" id="roomLimit">
					                           	   	  <c:forEach items="${limitList }" var="obj">
						                                  <option value="${obj.key }" >${obj.text }</option>
					                                  </c:forEach>
				                                   </select>
						                           <span class="help-block m-b-none"></span>
						                       </div>
						                 </div>
						                 <div class="form-group">
						                     <label class="col-sm-4 control-label mtop">是否独立卫生间：</label>
						                     <div class="col-sm-8">
						                          <select id="isToilet" class="form-control m-b" >
						                          		<option value="0">否</option>
						                          		<option value="1">是</option>
						                          </select>
						                     </div>
						                 </div>
										   <!-- 灵活定价&折扣设置 start -->
										   <div class="form-group set-price set-price-room-add set-price-room-remove hide">
										   	   <label class="col-sm-4 control-label mtop">灵活价格:</label>
										   	   <ul style="width: 60%">
										   	   	   <li>
										   	   	   	   <label class="switch-btn">
										   	   	   	   	   <input type="hidden" class="set-price" id="spList_gapAndFlexiblePrice" default-value="0" value="0">
										   	   	   	   	   <input class="checked-switch set-price" type="checkbox" onclick="clickCheckbox(this)">
										   	   	   	   	   <span class="text-switch"></span>
										   	   	   	   	   <span class="toggle-btn"></span>
										   	   	   	   </label>
										   	   	   </li>
										   	   </ul>
										   </div>
										   <div class="form-group set-price-room-add hide">
										   	  <c:forEach items="${gapList}" var="gf">
						                           <c:if test="${gf.dicCode=='ProductRulesEnum020001'}">
										   			   <div class="form-group">
							                        	   <label class="col-sm-4 control-label mtop">预订当日价格:</label>
							                        	   <div class="col-sm-8">
								                           	  <input type="text" id="productRulesEnum020001" class="form-control m-b" value="<fmt:parseNumber integerOnly='true' value='${gf.dicVal*100}'/>%" readonly="readonly">
							                               </div>
						                               </div>
							                       </c:if>
						                           <c:if test="${gf.dicCode=='ProductRulesEnum020002'}">
										   			   <div class="form-group">
							                        	   <label class="col-sm-4 control-label mtop">仅1日可连续预订价格:</label>
							                        	   <div class="col-sm-8">
								                           	  <input type="text" id="productRulesEnum020002" class="form-control m-b" value="<fmt:parseNumber integerOnly='true' value='${gf.dicVal*100}'/>%" readonly="readonly">
							                               </div>
						                               </div>
							                       </c:if>
						                           <c:if test="${gf.dicCode=='ProductRulesEnum020003'}">
										   			   <div class="form-group">
							                        	   <label class="col-sm-4 control-label mtop">仅2日可连续预订价格:</label>
							                        	   <div class="col-sm-8">
								                           	  <input type="text" id="productRulesEnum020003" class="form-control m-b" value="<fmt:parseNumber integerOnly='true' value='${gf.dicVal*100}'/>%" readonly="readonly">
							                               </div>
						                               </div>
							                       </c:if>
						                        </c:forEach>
										   </div>
										   <div class="form-group set-price set-price-room-add set-price-room-remove hide">
										   	   <label class="col-sm-4 control-label mtop">折扣设置:</label>
										   	   <ul style="width: 60%">
										   	   	   <li>
										   	   	   	   <label class="switch-btn">
										   	   	   	   	   <input type="hidden" class="set-price" id="spList_fullDayRate" default-value="0" value="0">
										   	   	   	   	   <input class="checked-switch set-price" type="checkbox" onclick="clickCheckbox(this)">
										   	   	   	   	   <span class="text-switch"></span>
										   	   	   	   	   <span class="toggle-btn"></span>
										   	   	   	   </label>
										   	   	   </li>
										   	   </ul>
										   </div>
										   <div class="form-group set-price-room-add hide">
								   			   <div class="form-group">
					                        	   <label class="col-sm-4 control-label mtop">入住满7天折扣:</label>
					                        	   <div class="col-sm-8">
						                           	  <input type="hidden" class="set-price" id="spList_sevenDiscountRate" default-value="-1" value="-1">
						                           	  <input type="text" class="form-control m-b set-price" default-value="" onkeyup="validateInput(this)" onblur="validateBlur(this)" onfocus="inputOnFocus(this)">
					                               </div>
				                               </div>
								   			   <div class="form-group">
					                        	   <label class="col-sm-4 control-label mtop">入住满30天折扣:</label>
					                        	   <div class="col-sm-8">
						                           	  <input type="hidden" class="set-price" id="spList_thirtyDiscountRate" default-value="-1" value="-1">
						                           	  <input type="text" class="form-control m-b set-price" default-value="" onkeyup="validateInput(this)" onblur="validateBlur(this)" onfocus="inputOnFocus(this)">
					                               </div>
				                               </div>
										   </div>
										   <!-- 灵活定价&折扣设置 end -->
						                 <input type="reset" style="display: none;" />   
					           </div>
					       </div>
				       </div>
			         </form>
		         </div>
		     </div>
		     <div class="modal-footer">
		         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
		         <button type="button" id="saveRoom" class="btn btn-primary" >保存</button>
		      </div>
		     </div>
		   </div>
		</div>
		
		<!-- 添加 菜单  弹出框 添加床-->
		<div class="modal inmodal" id="myModal1" tabindex="-1" role="dialog" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content animated bounceInRight">
		       <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
		          </button>
		          <h4 class="modal-title">添加床</h4>
		       </div>
		       <div class="modal-body">
			        <div class="wrapper wrapper-content animated fadeInRight">
			        <input type="hidden" id="roomBedName" value=""/>
				        <div class="row">
				          <div class="col-sm-14">
				               <div class="ibox float-e-margins">
				                   <div class="ibox-content" style="overflow:hidden;">
										  <div class="form-group">
						                       <label class="col-sm-3 control-label">床类型：</label>
						                       <div class="col-sm-8">
						                           <select class="form-control m-b" id="bedType">
					                           	   	  <c:forEach items="${bedTypeList }" var="obj">
						                                  <option value="${obj.key }" >${obj.text }</option>
					                                  </c:forEach>
				                                   </select>
						                           <span class="help-block m-b-none"></span>
						                       </div>
						                  </div>
						                  <div class="form-group">
						                       <label class="col-sm-3 control-label">床规格：</label>
						                       <div class="col-sm-8">
						                           <select class="form-control m-b" id="bedSize">
					                           	   	  <c:forEach items="${bedSizeList }" var="obj">
						                                  <option value="${obj.key }" >${obj.text }</option>
					                                  </c:forEach>
				                                   </select>
						                           <span class="help-block m-b-none"></span>
						                       </div>
						                 </div>
						                 <input type="reset" style="display: none;" />
					           </div>
					       </div>
				       </div>
			         </div>
		         </div>
		     </div>
		     <div class="modal-footer">
		         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
		         <button type="button"  class="btn btn-primary" onclick="saveBed();">保存</button>
		      </div>
		     </div>
		   </div>
		</div>
	   
	    <!-- 弹出框房东 -->
	    <div class="modal inmodal fade" id="landlordModel" tabindex="-2" role="dialog"  aria-hidden="true">
	         <div class="modal-dialog modal-lg">
	             <div class="modal-content">
	                 <div class="modal-header">
	                     <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	                     <h4 class="modal-title">房东信息</h4>
	                 </div>
	                 <form id="landlordForm" class="ibox-content">
		             	 <div class="row">
			                 <label class="col-sm-1 control-label mtop">房东手机:</label>
		                     <div class="col-sm-2">
		                    	 <input type="text" class="form-control m-b" id="mobile" name="mobile">
		                     </div>
		                     <div class="col-sm-1">
			               	 	 <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
			           		 </div>
			             </div>
		             	 <div class="row">
	                    	 <input type="hidden" id="landlordUid">
			                 <label class="col-sm-1 control-label mtop">房东姓名:</label>
		                     <div class="col-sm-2">
		                    	 <input type="text" class="form-control m-b" id="landlordName" readonly="readonly">
		                     </div>
			                 <label class="col-sm-1 control-label mtop">房东昵称:</label>
		                     <div class="col-sm-2">
		                    	 <input type="text" class="form-control m-b" id="landlordNickName" readonly="readonly">
		                     </div>
			             </div>
			             <!-- 用于 将表单缓存清空 -->
                         <input type="reset" style="display:none;" />
	             	</form>
	                 <div class="modal-footer">
	                     <button type="button" class="btn btn-primary" onclick="selectLandlord()" data-dismiss="modal">保存</button>
	                 </div>
	             </div>
	         </div>
	    </div>
	    
       <input type="hidden" id="priceLow" value="${priceLow}">
       <input type="hidden" id="priceHigh" value="${priceHigh}">
	   <!-- 全局js -->
	   <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	   
	   <!-- Bootstrap table -->
	   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	   <!-- 自定义js -->
	   <script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	
	   <!-- blueimp gallery -->
	   <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}"></script>
	   <!--  validate -->
	   <script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/Math.uuid.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/validate.ext.js${VERSION}001"></script>
	   
       <script type="text/javascript">
       		var priceLow=$("#priceLow").val();
       		var priceHigh=$("#priceHigh").val();
		  	$(function(){  
				//var BASE_CONTEXT = ${basePath};
				//外部js调用
			/*    laydate({
			      elem: '#tillDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			   });  */
				// 多级联动
	 			var areaJson = ${cityTreeList};
	 			var defaults = {
					geoJson	: areaJson
	 			};
	 			geoCascade(defaults);
			})
			
			$("#roomFrom").validate({
                rules: {
 		        	"roomName":{
 		        		 required: true,  
 		        		 maxlength: 30	
 		        	},
 		        	"roomArea":{
 		        		 required: true,  
 		        		 number: true	
 		        	},
 		        	"roomPrice":{
 		        		 required: true,  
 		        		 integer: true	
 		        	}
                }
	        });
		  	
			/*保存房间*/
			$("#saveRoom").click(function(){
				if(!$("#roomFrom").valid()){
					return;
				}
				//根据卧室数量限制添加的房间
				var roomNum = $("input[name='houseBase.roomNum']").val();
				var hasNum = 0;
				if(roomNum == '' || roomNum == undefined || roomNum == null){
					$("#myModal").modal('toggle');
					layer.alert("请填写卧室数量", {icon: 5,time: 2000, title:'提示'});
					return;
				}else{
					hasNum = $(".roomNumbers").length;
					if(roomNum == hasNum){
						$("#myModal").modal('toggle');
						layer.alert("添加房间已经够卧室数量了", {icon: 5,time: 2000, title:'提示'});
						return;
					}
				}
				
				var roomName=$("#roomName").val();
				var roomArea=$("#roomArea").val();
				var roomPrice=$("#roomPrice").val();
								
				if(parseInt(roomPrice)<parseInt(priceLow)){
					layer.alert("房间价格不能低于："+priceLow, {icon: 5,time: 2000, title:'提示'});
					return;
				}
								
				if(parseInt(roomPrice)>parseInt(priceHigh)){
					layer.alert("房间价格不能高于："+priceHigh, {icon: 5,time: 2000, title:'提示'});
					return;
				}
				var roomLimit=$("#roomLimit").val();
				var isToilet=$("#isToilet").val();
				var roomFid=Math.uuid(15);
				$("#roomList").append("<div class='row roomNumbers'>");
				$("#roomList").append("<div class='col-sm-1'><label class='control-label mtop'>房间名称:</label></div><div class='col-sm-2'><input  name='roomNameNick' value='"+roomName+"' readonly='readonly' type='text' class='form-control m-b'></div>");
				$("#roomList").append("<input  name='roomFid' value='"+roomFid+"' readonly='readonly' type='hidden'  class='form-control m-b' >");
				$("#roomList").append("<div class='col-sm-1'><label class='control-label mtop'>房间面积:</label></div><div class='col-sm-1'><input  name='roomArea' value='"+roomArea+"' readonly='readonly' type='text' class='form-control m-b'></div>");
				$("#roomList").append("<div class='col-sm-1'><label class='control-label mtop'>房间价格:</label></div><div class='col-sm-1'><input  name='roomPrice' value='"+roomPrice+"' readonly='readonly' type='text' class='form-control m-b'></div>");
				$("#roomList").append("<div class='col-sm-1'><label class='control-label mtop'>入住人数:</label></div><div class='col-sm-1'><input  name='roomLimit' value='"+roomLimit+"' readonly='readonly' type='text' class='form-control m-b'></div>");
				$("#roomList").append("<div class='col-sm-1'><label class='control-label mtop'>独立卫生间:</label></div><div class='col-sm-1'><input  name='isToilet' value='"+isToilet+"' readonly='readonly' type='text' class='form-control m-b'></div>");
				$("#roomList").append("</div>");
				//灵活定价 折扣设置 start
				appendPriceSet(hasNum);
				//灵活定价 折扣设置 end
				$("#roomList").append("<div class='row' id='"+roomFid+"'>")
				$("#roomList").append("</div>");
				$("#roomList").append("<div class='row'><div class='col-sm-2'><button type='button' style='margin-left:10px;'  class='btn btn-primary' data-target='#myModal1'  onclick=\"addBed('"+roomFid+"');\"><span>添加床</span></button></div><div class='row'>");
				$('#myModal').modal('hide');
				$("#roomName").val("");
				$("#roomArea").val("");
				$("#roomPrice").val("");
				$("input[type=reset]").trigger("click");
				//初始化房间灵活定价 折扣设置 start
				resetRoomPrice();
				//初始化房间灵活定价 折扣设置 end
			})
			
			function appendPriceSet(hasNum){
				//灵活定价
				$("#roomList").append("<div class='row'>")
				var gapAndFlexiblePrice = $("#spList_gapAndFlexiblePrice").val();
				$("#roomList").append("<input name='spList["+hasNum+"].gapAndFlexiblePrice' value='"+gapAndFlexiblePrice+"' type='hidden'>");
				if(gapAndFlexiblePrice == 1){
					var productRulesEnum020001 = $("#productRulesEnum020001").val();
					if(!checkNullOrBlank(productRulesEnum020001)){
						$("#roomList").append("<div class='col-sm-2'><label class='control-label mtop'>预订当日价格:</label></div><div class='col-sm-1'><input value='"+productRulesEnum020001+"' readonly='readonly' type='text' class='form-control m-b'></div>");
					}
					var productRulesEnum020002 = $("#productRulesEnum020002").val();
					if(!checkNullOrBlank(productRulesEnum020002)){
						$("#roomList").append("<div class='col-sm-2'><label class='control-label mtop'>仅1日可连续预订价格:</label></div><div class='col-sm-1'><input value='"+productRulesEnum020002+"' readonly='readonly' type='text' class='form-control m-b'></div>");
					}
					var productRulesEnum020003 = $("#productRulesEnum020003").val();
					if(!checkNullOrBlank(productRulesEnum020003)){
						$("#roomList").append("<div class='col-sm-2'><label class='control-label mtop'>仅2日可连续预订价格:</label></div><div class='col-sm-1'><input value='"+productRulesEnum020003+"' readonly='readonly' type='text' class='form-control m-b'></div>");
					}
				}
				$("#roomList").append("</div>");
				
				//折扣设置
				$("#roomList").append("<div class='row'>")
				var fullDayRate = $("#spList_fullDayRate").val();
				var sevenDiscountRate = $("#spList_sevenDiscountRate").val();
				var thirtyDiscountRate = $("#spList_thirtyDiscountRate").val();
				$("#roomList").append("<input name='spList["+hasNum+"].fullDayRate' value='"+fullDayRate+"' type='hidden'>");
				$("#roomList").append("<input name='spList["+hasNum+"].sevenDiscountRate' value='"+sevenDiscountRate+"' type='hidden'>");
				$("#roomList").append("<input name='spList["+hasNum+"].thirtyDiscountRate' value='"+thirtyDiscountRate+"' type='hidden'>");
				if(fullDayRate == 1){
					if(!checkNullOrBlank(sevenDiscountRate) && sevenDiscountRate != -1){
						$("#roomList").append("<div class='col-sm-2'><label class='control-label mtop'>入住满7天折扣:</label></div><div class='col-sm-1'><input value='"+sevenDiscountRate+"%' readonly='readonly' type='text' class='form-control m-b'></div>");
					}else{
						$("#roomList").append("<div class='col-sm-2'><label class='control-label mtop'>入住满7天折扣:</label></div><div class='col-sm-1'><input value='' readonly='readonly' type='text' class='form-control m-b'></div>");
					}
					if(!checkNullOrBlank(thirtyDiscountRate) && thirtyDiscountRate != -1){
						$("#roomList").append("<div class='col-sm-2'><label class='control-label mtop'>入住满7天折扣:</label></div><div class='col-sm-1'><input value='"+thirtyDiscountRate+"%' readonly='readonly' type='text' class='form-control m-b'></div>");
					}else{
						$("#roomList").append("<div class='col-sm-2'><label class='control-label mtop'>入住满30天折扣:</label></div><div class='col-sm-1'><input value='' readonly='readonly' type='text' class='form-control m-b'></div>");
					}
				}
				$("#roomList").append("</div>");
			}

		    function resetRoomPrice(){
		    	var rentWay = $("#rentWay option:selected").val();
		    	if(rentWay == 1){
			    	initPriceSet();
		    		$(".set-price-room-add").addClass('hide');
		    		$(".set-price-room-remove").removeClass('hide');
		    	}
		    }
			
			//选择合租的时候去掉整租价格显示
			$("select[name='houseBase.rentWay']").change(function(){
				//整租
				if($(this).val() == 0){
					$(".entireRentPrice").show();
				}else{
					$(".entireRentPrice").hide();
				}
			});
			
			/*添加床按钮*/
			function addBed(roomFid){
		  		$('#myModal1').modal('toggle');
		  		$("#roomBedName").val(roomFid);
		  	}
			
			/*添加床*/
			function saveBed(){
		  		var bedType=$("#bedType").val();
		  		var bedSize=$("#bedSize").val();
		  		var bedTypeText=$("#bedType").find("option:selected").text();
		  		var bedSizeText=$("#bedSize").find("option:selected").text();
		  		var roomBedName=$("#roomBedName").val();
		  		$("#"+roomBedName).append("<div class='row'>")
				$("#"+roomBedName).append("<div class='col-sm-1' style='margin-left:15px;'><label class='control-label mtop'>床类型：</label></div><div class='col-sm-1'><input  name='bedTypeText' value='"+bedTypeText+"' readonly='readonly' type='text' class='form-control m-b'><input  name='bedType' value='"+roomBedName+"-"+bedType+"' readonly='readonly' type='hidden' class='form-control m-b'></div>");
		  		$("#"+roomBedName).append("<div class='col-sm-1' style='margin-left:15px;'><label class='control-label mtop'>床规格：</label></div><div class='col-sm-2'><input  name='bedSizeText' value='"+bedSizeText+"' readonly='readonly' type='text' class='form-control m-b'><input  name='bedSize' value='"+roomBedName+"-"+bedSize+"' readonly='readonly' type='hidden' class='form-control m-b'></div>");
		  		$("#"+roomBedName).append("</div>")
		  		$('#myModal1').modal('toggle');
		  		$("#roomBedName").val("");
		  		$("input[type=reset]").trigger("click");
		  	}
			
			/*保存房源信息*/
				$("#saveHouse").validate({
					rules:{  
						"housePhy.googleLongitude": {
	 		        	    required: true,  
	 		        	    googleLongitude: true  
	 		        	},
	 		        	"housePhy.googleLatitude":{
	 		        		 required: true,  
	 		        		 googleLatitude: true  
	 		        	},
	 		        	"houseBase.houseName":{
	 		        		 required: true,  
	 		        		 maxlength: 30	
	 		        	},
	 		        	"houseDesc.houseDesc":{
	 		        		 required: true,  
	 		        		 maxlength: 1000,
	 		        		 minlength: 100
	 		        	},
	 		        	"houseDesc.houseAroundDesc":{
	 		        		 required: true,  
	 		        		 maxlength: 1000,
	 		        		 minlength: 100
	 		        	},
		 		       	"houseDesc.houseRules":{
			        		 required: true,  
			        		 maxlength: 1000	
			        	},
	 		        	"houseBase.leasePrice":{
	 		        		 required: true,  
	 		        		 integer: true	
	 		        	}
	 		        },
					submitHandler:function(){
						//如果包含禁用属性，则不能点击无效
						if($("#btn_submit").hasClass("btn-default")){
							return false;
						}
						var roomHtml=$.trim($("#roomList").html());
						if(roomHtml == '' || roomHtml == undefined || roomHtml == null){
							layer.alert("请最少添加一个房间！", {icon: 5,time: 2000, title:'提示'});
							return;
						}
						//判断价格是否小于限制值
						var housePrice=$("#housePrice").val();
						var rentWay=$("#rentWay").val();
						if(parseInt(housePrice)<parseInt(priceLow)&&rentWay==0){
							layer.alert("房间价格不能低于："+priceLow, {icon: 5,time: 2000, title:'提示'});
							return;
						}
						if(parseInt(housePrice)>parseInt(priceHigh)&&rentWay==0){
							layer.alert("房间价格不能高于："+priceHigh, {icon: 5,time: 2000, title:'提示'});
							return;
						}
						
						$("input[name='roomNameNick']").each(function(){
							var roomName = $(this).val();
							roomName = roomName+"minsu_se";
							$(this).val(roomName);
						});
						
						var regexp = new RegExp("^\[1-9][0-9]*$");						
						//押金校验
						var depositMin = parseInt('${depositMin}');
						var depositMax = parseInt('${depositMax}');
						
						var curDeposit = $("#curDeposit").val();
						
						if(curDeposit==undefined||curDeposit==null||curDeposit==""){
							layer.alert("请输入押金", {icon: 5,time: 2000, title:'提示'});
							return false;
						}
						if(curDeposit!="0"){
							var isPositiveInteger = regexp.test(curDeposit);
							if(!isPositiveInteger){
								layer.alert("请填写正确押金金额", {icon: 5,time: 2000, title:'提示'});
								return false;
							} 
						}
					
						if(curDeposit<depositMin||curDeposit>depositMax){
							layer.alert("押金范围在"+depositMin+"元到"+depositMax+"元", {icon: 5,time: 2000, title:'提示'});
							return false;
						}
						
						$("#btn_submit").removeClass("btn-primary").addClass("btn-default");
						$.ajax({
				            type: "POST",
				            url: "house/houseMgt/saveHouseMsg",
				            dataType:"json",
				            data: $("#saveHouse").serialize(),
				            success: function (result) {
				            	if(result.code==0){
				            		layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
									$.callBackParent("house/houseMgt/listHouseMsg",true,saveHouseCallback);
				            	}else{
				            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
				            		$("#btn_submit").removeClass("btn-default").addClass("btn-primary");
				            	}
				            },
				            error: function(result) {
				            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
				            	
				            	$("#btn_submit").removeClass("btn-default").addClass("btn-primary");
				            }
				      });
				     $("input[name='roomNameNick']").each(function(){
							var roomName = $(this).val();
							roomName = roomName.replace("minsu_se", "")
							$(this).val(roomName);
						});
					}});
			
			//审核房间刷新父页面数据
			function saveHouseCallback(panrent){
				panrent.refreshData("listTable");
			}
			
			function toBaiduMap() {
				var street = $.trim($("input[name='houseExt.houseStreet']").val());
				$.openNewTab(new Date().getTime(),'config/subway/toMap?stationName='+street+"&random="+Math.random(), "地图搜索");
			}
			
			function resetForm(){
				$("#landlordForm")[0].reset();
			}
			
		    function query(){
		    	var mobile = $('#mobile').val();
				if(checkNullOrBlank(mobile)){
					layer.alert("请填写房东手机号", {icon: 5,time: 2000, title:'提示'});
					return;
				}
				$.ajax({
		            type: "POST",
		            url: "customer/linkCustomerByMobile",
		            dataType:"json",
		            data: {"mobile":mobile},
		            success: function (result) {
		            	if(result.code==0){
		            		$("#landlordUid").val(result.data.customerBase.uid);
		            		$("#landlordName").val(result.data.customerBase.realName);
		            		$("#landlordNickName").val(result.data.customerBase.nickName);
		            	}else{
		            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
		            	}
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		      	});
		    }
		    
		    function checkNullOrBlank(param){
	    		return typeof(param) == 'undefined' || param == null || $.trim(param).length == 0;
	    	}
		    
		    function selectLandlord(){
	    		$("[name='houseBase.landlordUid']").val($("#landlordUid").val());
	    	}
		    
		    $("#rentWay").change(function(){
		    	var value = $(this).val();
		    	initPriceSet();
	    		$(".set-price-house-add").addClass('hide');
	    		$(".set-price-room-add").addClass('hide');
		    	if(value == 0){
		    		$(".set-price-house-remove").removeClass('hide');
		    	} else if(value == 1){
		    		$(".set-price-room-remove").removeClass('hide');
		    	}
		    });
		    
		    function initPriceSet(){
		    	var hiddenInputs = $(".set-price").filter("input[type='hidden']");
		    	$.each(hiddenInputs, function(i,n){
		    		var default_value = $(n).attr('default-value');
		    		$(n).val(default_value);
		    	});
		    	var textInputs = $(".set-price").filter("input[type='text']");
		    	$.each(textInputs, function(i,n){
		    		var default_value = $(n).attr('default-value');
		    		$(n).val(default_value);
		    	});
		    	$(".set-price").filter("input[type='checkbox']").attr('checked',false);
		    }
		    
		    function clickCheckbox(obj){
		    	if($(obj).is(':checked')){
		    		$(obj).prev().val('1');
		    		$(obj).parents().filter('.set-price').next().removeClass('hide');
		    	}else{
		    		$(obj).prev().val('0');
		    		$(obj).parents().filter('.set-price').next().addClass('hide');
		    	}
		    }
		    
		    function validateInput(obj){
		    	var value = $(obj).val();
		    	value = value.replace(/[^\d]/g,'');
		    	var regexp = new RegExp("^[1-9][0-9]?$");
		    	if(!checkNullOrBlank(value) && regexp.test(value)){
			    	$(obj).val(value);
		    		$(obj).prev().val(value);
		    	}else{
		    		$(obj).val('');
		    		$(obj).prev().val('-1');
		    	}
		    }
		    
		    function validateBlur(obj){
		    	var value = $(obj).val();
		    	value = value.replace(/[^\d]/g,'');
		    	var regexp = new RegExp("^[1-9][0-9]?$");
		    	if(!checkNullOrBlank(value) && regexp.test(value)){
		    		$(obj).val(value+'%');
		    		$(obj).prev().val(value);
		    	}else{
		    		$(obj).val('');
		    		$(obj).prev().val('-1');
		    	}
		    }
		    
		    function inputOnFocus(obj){
		    	var value = $(obj).val();
		    	value = value.replace('%','');
		    	$(obj).val(value);
		    }
	   </script>
	</body>
</html>
