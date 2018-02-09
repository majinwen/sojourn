
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/iCheck/custom.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
	<style>
        .lightBoxGallery img {margin: 5px;width: 160px;}
        label{font-weight:500;margin-top:8px;}
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
	       <div class="row">
	       <form action="house/houseMgt/upHouseMsg" method="post" id="upHouse">
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				          <div class="form-group">
				           	<h2 class="title-font">基本信息</h2>
							  <customtag:authtag authUrl="house/houseCalendar/toHouseCalendar">
								  <c:if test="${isSj == true}">
									  <div class="row">
										  <div class="col-sm-4">
											  <button type="button" class="btn btn-info" id="setHouseCalendar" onclick="toHouseCalendar()">设置房源出租日历</button>
										  </div>
									  </div>
								  </c:if>
							  </customtag:authtag>
		                       <div class="row">
	                               <div class="col-sm-1">
			                   	   <label class="control-label">国家:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b m-b" id="nationCode" disabled="disabled">
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label">省份:</label>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b " id="provinceCode" disabled="disabled">
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label">城市:</label>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b" id="cityCode" disabled="disabled">
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label">区域:</label>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b" id="areaCode" disabled="disabled">
	                                   </select>
	                               </div>
		                       </div>
		                       
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房东姓名:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input value="${landlord.realName }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
				                   <label class="col-sm-1 control-label">房东手机:</label>
		                           <div class="col-sm-2">
	                                   <input value="${landlord.customerMobile }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       
	                           <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">房源基本情况</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源地址:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input  title="${houseBaseMsg.houseAddr}" value="${houseBaseMsg.houseAddr}"  name="houseBase.houseAddr" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源名称:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           	   <input type="hidden" name="houseBase.fid"  value="${houseBaseMsg.fid }">
	                                   <input id="houseName" name="houseBase.houseName" title="${houseBaseMsg.houseName}" value="${houseBaseMsg.houseName}"  type="text" class="form-control m-b">
	                               </div>
				                   <label class="col-sm-1 control-label">房源户型:</label>
		                           <div class="col-sm-2">
	                                   <input value="${houseBaseMsg.roomNum }室${houseBaseMsg.hallNum }厅${houseBaseMsg.toiletNum }卫${houseBaseMsg.kitchenNum }厨${houseBaseMsg.balconyNum }阳台" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源面积:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input  value="${houseBaseMsg.houseArea }" id="houseArea" name="houseBase.houseArea"  type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源坐标:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input value="${housePhyMsg.longitude },${housePhyMsg.latitude }" type="text" class="form-control m-b" id="coordinate" name="coordinate">
	                                   <button onclick="javascript:toMap();" type="button" class="btn btn-primary">没有获取坐标？去地图获取坐标</button>
	                               </div>
				                   <label class="col-sm-1 control-label">房源类型:</label>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b"  disabled="disabled">
		                                  <c:forEach items="${houseTypeList }" var="obj">
		                                   	 <option value="${obj.key }" <c:if test="${obj.key == houseBaseMsg.houseType }">selected</c:if>>${obj.text }</option>
		                                  </c:forEach>
	                                   </select>
	                               </div>
	                               <c:if test="${houseBaseMsg.rentWay == 0}">
				                   		<label class="col-sm-1 control-label">房源编号:</label>
	                               </c:if>
	                               <c:if test="${houseBaseMsg.rentWay == 1}">
				                  		 <label class="col-sm-1 control-label">房间编号:</label>
	                               </c:if>
	                               <div class="col-sm-2">
	                               
		                               <c:if test="${houseBaseMsg.rentWay == 0}">
		                                   <input id="houseSn" name="houseSn" value="${houseBaseMsg.houseSn}" readonly="readonly" type="text" class="form-control m-b">
		                               </c:if>
		                               <c:if test="${houseBaseMsg.rentWay == 1}">
	                                 	  <input id="houseSn" name="houseSn" value="${roomList[0].roomSn}" readonly="readonly" type="text" class="form-control m-b">
		                               </c:if>
	                               </div>
		                       </div>
		                       <div class="row">
		                       		<input type="hidden" name="houseExt.fid" value="${houseBaseExt.fid }">
	                               <label class="col-sm-1 control-label mtop">楼号-门牌号信息:</label>
		                          <div class="col-sm-2">
	                                   <input name="houseExt.detailAddress" value="${houseBaseExt.detailAddress }"  type="text" class="form-control m-b" >
	                               </div>
				                    <%-- <label class="col-sm-1 control-label mtop">单元号:</label>
		                           <div class="col-sm-1">
	                                   <input name="houseExt.unitNum" value="${houseBaseExt.unitNum }" type="text" class="form-control m-b" >
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">楼层:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input  name="houseExt.floorNum" value="${houseBaseExt.floorNum }" type="text" class="form-control m-b" >
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">门牌号:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input  name="houseExt.houseNum" value="${houseBaseExt.houseNum }" type="text" class="form-control m-b" >
	                               </div> --%>
		                       </div>
		                       
                               <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">租住描述</h2>
		                       <%-- <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">民宿类型:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b"  disabled="disabled">
		                           	   	  <c:forEach items="${homestayList }" var="obj">
			                                  <option value="${obj.key }" <c:if test="${obj.key == houseBaseExt.homestayType }">selected</c:if>>${obj.text }</option>
		                                  </c:forEach>
	                                   </select>
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">出租类型:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b"  disabled="disabled">
		                           	   	  <c:forEach items="${rentWayMap }" var="obj">
			                                  <option value="${obj.key }" <c:if test="${obj.key == houseBaseMsg.rentWay }">selected</c:if>>${obj.value }</option>
		                                  </c:forEach>
	                                   </select>
	                               </div>
				                   <label class="col-sm-1 control-label">被单更换:</label>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b"  disabled="disabled">
		                           	   	  <c:forEach items="${sheetReplaceList }" var="obj">
			                                  <option value="${obj.key }" <c:if test="${obj.key == houseBaseExt.sheetsReplaceRules}">selected</c:if>>${obj.text }</option>
		                                  </c:forEach>
	                                   </select>
	                               </div>
		                       </div> --%>
		                       <div class="row">
				                   <label class="col-sm-1 control-label">入住人数限制:</label>
		                           <div class="col-sm-2">
		                           	   <c:choose>
			                           	   <c:when test="${houseBaseExt.checkInLimit == 0 }">
			                                   <input  value="不限制" readonly="readonly" type="text" class="form-control m-b">
			                           	   </c:when>
			                           	   <c:otherwise>
			                                   <input  value="${houseBaseExt.checkInLimit }" readonly="readonly" type="text" class="form-control m-b">
			                           	   </c:otherwise>
		                           	   </c:choose>
	                               </div>
		                       	   <div class="col-sm-1">
					                   <label class="control-label">是否与房东同住:</label>
		                           </div>
			                       <div class="col-sm-2">
				                       <c:if test="${houseBaseExt.isTogetherLandlord == 0 || empty houseBaseExt.isTogetherLandlord}">
		                                  <input id="isToilet" value="否" readonly="readonly" type="text" class="form-control m-b">
		                               </c:if>
				                       <c:if test="${houseBaseExt.isTogetherLandlord == 1 }">
		                                  <input id="isToilet" value="是" readonly="readonly" type="text" class="form-control m-b">
		                               </c:if>
		                           </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">最少入住天数:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input  value="${houseBaseExt.minDay }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">可预订截止时间:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="tillDate" name="tillDate" value='<fmt:formatDate value="${houseBaseMsg.tillDate }" pattern="yyyy-MM-dd"/>'  class="laydate-icon form-control layer-date">
	                               </div>
				                   <label class="col-sm-1 control-label">入住时间:</label>
		                           <div class="col-sm-2">
	                                   <input  value="${houseBaseExt.checkInTime }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
				                   <label class="col-sm-1 control-label">退房时间:</label>
		                           <div class="col-sm-2">
	                                   <input value="${houseBaseExt.checkOutTime }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
	                               <div class="col-sm-1">
				                   	   <label class="control-label">出租类型:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b"  disabled="disabled">
		                           	   	  <c:forEach items="${rentWayMap }" var="obj">
			                                  <option value="${obj.key }" <c:if test="${obj.key == houseBaseMsg.rentWay }">selected</c:if>>${obj.value }</option>
		                                  </c:forEach>
	                                   </select>
	                               </div>
		                       </div>
		                       
                               <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">房源描述</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">小区名称:</label>
	                               </div>
		                           <div class="col-sm-5">
		                           	   <input name="housePhy.fid" type="hidden"  value="${housePhyMsg.fid }">	
	                                   <input id="communityName" name="housePhy.communityName" title="${housePhyMsg.communityName}" value="${housePhyMsg.communityName}" type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源描述:</label>
				                   	   <input name="houseDesc.fid" type="hidden"  value="${houseDesc.fid }">	
	                               </div>
		                           <div class="col-sm-5">
	                                   <textarea rows="8" id="houseDesc" name="houseDesc.houseDesc"   class="form-control m-b">${houseDesc.houseDesc }</textarea>
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">周边状况:</label>
	                               </div>
		                           <div class="col-sm-5">
	                                   <textarea rows="8" id="houseAroundDesc" name="houseDesc.houseAroundDesc"  class="form-control m-b">${houseDesc.houseAroundDesc }</textarea>
	                               </div>
		                       	
		                       </div>
		                       <div class="row">
		                       	   <div class="col-sm-1">
				                   	   <label class="control-label">房屋守则:</label>
	                               </div>
		                           <div class="col-sm-5">
	                                   <textarea rows="8"  name="houseDesc.houseRules" class="form-control m-b" id="houseRules">${houseDesc.houseRules }</textarea>
	                               </div>
		                       </div>
		                       
                               <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">配套设施</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">电器:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           		<c:forEach items="${electricList }" var="obj">
			                               ${obj}&nbsp;
		                                </c:forEach>
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">卫浴:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           		<c:forEach items="${bathroomList }" var="obj">
			                               ${obj}&nbsp;
		                                </c:forEach>
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">设施:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           		<c:forEach items="${facilityList }" var="obj">
			                               ${obj}&nbsp;
		                                </c:forEach>
	                               </div>
		                       </div>
		                       
                               <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">服务</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">服务:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           		<c:forEach items="${serviceList }" var="obj">
			                               ${obj}&nbsp;
		                                </c:forEach>
	                               </div>
		                       </div>
		                       
	                           <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">租金规则</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">3天折扣率</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input value="${discount3 }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">7天折扣率</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input  value="${discount7 }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">30天折扣率</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input  value="${discount30 }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">价格:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input value="${houseBaseMsg.leasePrice/100 }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">押金:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="depositRules" name="depositRules" value="${depositConfMsg.dicVal }" readonly="readonly"  type="text" class="form-control m-b">
		                           	   <!-- 
		                           	   <select class="form-control m-b" id="depositRules" disabled="disabled">
		                           	   	  <c:forEach items="${depositList }" var="obj">
		                           			  <option value="${obj.key }" <c:if test="${obj.key == depositRulesValue }">selected</c:if>>${depositRulesName }:${obj.text }</option>
		                                  </c:forEach>
	                                   </select>
		                           	    -->
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">下单类型:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b" id="orderType" disabled="disabled">
		                           	   	  <c:forEach items="${orderTypeList }" var="obj">
			                                  <option value="${obj.key }" <c:if test="${obj.key == houseBaseExt.orderType }">selected</c:if>>${obj.text }</option>
		                                  </c:forEach>
	                                   </select>
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">退房规则:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input value="${checkOutRulesName }" readonly="readonly"  type="text" class="form-control m-b">
	                                   <input name="houseExt.checkOutRulesCode" type="hidden"  value="${checkOutRulesCode }">
	                               </div>
		                       </div>
		                       
	                           <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">退订政策</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">违约金计算方式:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input  value="${calculation }" readonly="readonly"  type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">${unregName1 }:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input  value="${unregVal1 }" readonly="readonly"  type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   	<label class="control-label">${unregName2 }:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input value="${unregVal2 }" readonly="readonly"  type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	  <label class="control-label">${unregName3 }:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input  value="${unregVal3 }" readonly="readonly"  type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       
	                           <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">房间信息</h2>
		                       <c:forEach items="${roomList }" var="room" varStatus="index" >
			                       <div class="row">
			                           <div class="col-sm-1">
					                   	   <label class="control-label">房间名称:</label>
		                               </div>
			                           <div class="col-sm-2">
			                           	   <input type="hidden" name="roomFid"  value="${room.fid}">
		                                   <input id="roomName" name="roomName" title="${room.roomName}" value="${room.roomName}"  type="text" class="form-control m-b">
		                               </div>
			                           <div class="col-sm-1">
					                   	   <label class="control-label">房间面积:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <input  value="${room.roomArea }" name="roomArea"  type="text" class="form-control m-b">
		                               </div>
			                           <div class="col-sm-1">
					                   	   <label class="control-label">房间价格:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <input  value="${room.leasePrice }" readonly="readonly" type="text" class="form-control m-b">
		                               </div>
			                       </div>
			                       <div class="row">
			                           <div class="col-sm-1">
					                   	   <label class="control-label">房间类型:</label>
		                               </div>
		                               <div class="col-sm-2">
			                           	   <c:if test="${room.roomType == 0 || empty room.roomType}">
			                                  <input value="房间" readonly="readonly" type="text" class="form-control m-b">
			                               </c:if>
					                       <c:if test="${room.roomType == 1 }">
			                                  <input value="客厅" readonly="readonly" type="text" class="form-control m-b">
			                               </c:if>
		                               </div>
			                           <div class="col-sm-1">
					                   	   <label class="control-label">是否有独立卫生间:</label>
		                               </div>
			                           <div class="col-sm-2">
					                       <c:if test="${room.isToilet == 0 || empty room.isToilet}">
			                                  <input value="否" readonly="readonly" type="text" class="form-control m-b">
			                               </c:if>
					                       <c:if test="${room.isToilet == 1 }">
			                                  <input value="是" readonly="readonly" type="text" class="form-control m-b">
			                               </c:if>
		                               </div>
			                           <div class="col-sm-1">
					                   	   <label class="control-label">入住人数限制:</label>
		                               </div>
		                               <div class="col-sm-2">
			                           	   <c:choose>
				                           	   <c:when test="${room.checkInLimit == 0 }">
				                                   <input value="不限制" readonly="readonly" type="text" class="form-control m-b">
				                           	   </c:when>
				                           	   <c:otherwise>
				                                   <input  value="${room.checkInLimit }" readonly="readonly" type="text" class="form-control m-b">
				                           	   </c:otherwise>
			                           	   </c:choose>
		                               </div>
			                       </div>
			                       <c:forEach items="${room.bedList }" var="bed">
			                       <input type="hidden"  name="bedFidList"  value="${bed.fid }">
				                       <div class="row">
				                           <div class="col-sm-1">
						                   	   <label class="control-label">床铺编号:</label>
			                               </div>
				                           <div class="col-sm-2">
			                                   <input  value="${bed.bedSn }" readonly="readonly" type="text" class="form-control m-b">
			                               </div>
				                           <div class="col-sm-1">
						                   	   <label class="control-label">床铺类型:</label>
			                               </div>
				                           <div class="col-sm-2">
				                           	   <select class="form-control m-b"  name="bedType">
					                           	   <c:forEach items="${bedTypeList }" var="obj">
					                                  <option value="${obj.key }" <c:if test="${obj.key == bed.bedType }">selected</c:if>>${obj.text }</option>
				                                   </c:forEach>
				                               </select>
			                               </div>
				                           <div class="col-sm-1">
						                   	   <label class="control-label">床铺尺寸:</label>
			                               </div>
				                           <div class="col-sm-2">
				                           	   <select class="form-control m-b" name="bedSize">
					                           	   <c:forEach items="${bedSizeList }" var="obj">
					                                  <option value="${obj.key }" <c:if test="${obj.key == bed.bedSize }">selected</c:if>>${obj.text }</option>
				                                   </c:forEach>
				                               </select>
			                               </div>
			                           </div>
			                       </c:forEach>
	                              
	                              <c:if test="${!index.last}"> <div class="hr-line-dashed"></div></c:if>
		                       </c:forEach>
		                       
		                       <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">房源状态</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源渠道:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           	   <c:if test="${!empty houseBaseMsg.houseChannel && houseBaseMsg.houseChannel==3 }"> 
		                           	   		<input value="地推" readonly="readonly"  type="text" class="form-control m-b">
		                           	   </c:if>		
		                           	   <c:if test="${empty houseBaseMsg.houseChannel || houseBaseMsg.houseChannel!=3 }"> 
			                           	   <%--<select class="form-control m-b" id="houseChannel" name="houseBase.houseChannel" >--%>
											<select class="form-control m-b" id="houseChannel" >
			                           	   	  <option value="">请选择</option>
			                                   <c:forEach items="${houseChannel }" var="obj">
				                                   <option value="${obj.key }" <c:if test="${(empty houseBaseMsg.houseChannel && obj.key==2) ||(obj.key == houseBaseMsg.houseChannel) }">selected</c:if>>${obj.value }</option>
			                                   </c:forEach>
		                                   </select>
		                           	   </c:if>
	                               </div>
									<customtag:authtag authUrl='house/houseMgt/upHouseChannel'>
										<c:if test="${empty houseBaseMsg.houseChannel || houseBaseMsg.houseChannel!=3 }">
											<div class="col-sm-2">
												<button type="button" id="houseChannelSaveBtn"  class="btn btn-info">保存渠道信息</button>
											</div>
										</c:if>
									</customtag:authtag>
		                       </div>

							  <div class="row">
								  <div class="col-sm-1">
									  <label class="control-label">房源品质等级:</label>
								  </div>
								  <div class="col-sm-2">
									  <select class="form-control m-b" id="houseQualityGrade" >
											  <option value="">请选择</option>
											  <option value="TOP">塔尖</option>
											  <option value="A">A级</option>
											  <option value="B-">B-级</option>
											  <option value="B">B级</option>
											  <option value="B+">B+级</option>
											  <option value="C">C级</option>
									  </select>
								  </div>
								  <customtag:authtag authUrl='house/houseMgt/upHouseQualityGrade'>
									  <div class="col-sm-2">
										  <button type="button" id="houseQualityGradeSaveBtn"  class="btn btn-info">保存房源品质</button>
									  </div>
								  </customtag:authtag>
							  </div>

		                       <div class="hr-line-dashed"></div>
							   <div class="row">
								    <div class="form-group">
										 <div class="col-sm-4 col-sm-offset-5">
				                     		<button type="button"  class="btn btn-primary"  data-dismiss="modal" onclick="upHouseMsg();">保存修改</button>
				                   		</div>
				                   </div>
							   </div>
			               </div>
			           </div>
	               </div>
	           </div>
	           </form>
	       </div>
	   </div>
	   
	   <!-- 全局js -->
	   <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	   
	   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
 	   
	   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	   <!-- blueimp gallery -->
	   <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	   
	   <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
	   
	   <script type="text/javascript">
		   $(function(){
			    //外部js调用
		        laydate({
		            elem: '#tillDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus', //响应事件。如果没有传入event，则按照默认的click
		            min: laydate.now()
		        });
	     	   // 多级联动
			   var geoJson = ${cityTreeList};
			   var options = {
				   geoJson	: geoJson,
				   nationCode: '${housePhyMsg.nationCode}',
				   provinceCode: '${housePhyMsg.provinceCode}',
				   cityCode: '${housePhyMsg.cityCode}',
				   areaCode: '${housePhyMsg.areaCode}'
			   };
			   geoCascade(options);

			   chooseSelect("houseQualityGrade","${houseBaseExt.houseQualityGrade}");

		   });  

		   //更新房源渠道
			$("#houseChannelSaveBtn").click(function () {
				var houseChannel = $("#houseChannel").val();
				if(houseChannel){
					var houseFid = "${houseBaseMsg.fid}";
					$.post("${basePath}house/houseMgt/upHouseChannel",{"fid":houseFid,"houseChannel":houseChannel},function(data){
						if (data.code == 0){
							layer.alert("保存成功", {icon: 1,time: 2000, title:'提示'});
						}else{
							layer.alert("保存失败", {icon: 5,time: 2000, title:'提示'});
						}
					},'json');
				}
			});
		   //保存房源品质
		   $("#houseQualityGradeSaveBtn").click(function(){
			   var houseQualityGrade = $("#houseQualityGrade").val();
			   if (houseQualityGrade){
				   var houseFid = "${houseBaseMsg.fid}";
			   }
			   $.post("${basePath}house/houseMgt/upHouseQualityGrade",{"houseFid":houseFid,"qualityGrade":houseQualityGrade},function(data){
				   if (data.code == 0){
					   layer.alert("保存成功", {icon: 1,time: 2000, title:'提示'});
				   }else{
					   layer.alert("保存失败", {icon: 5,time: 2000, title:'提示'});
				   }
			   },'json');
		   })

		    function historyPaginationParam(params) {
		    	console.log(params);
		    	/* console.log($('#historyListTable').bootstrapTable('getOptions').pageNumber); */
			    return {
			    	limit:params.limit,
			        page:params.pageNumber,
			        houseFid:'${houseFid}',
			        rentWay:'${rentWay}'
		    	};
		   }
		   
		   // 房源状态
		   function formateHouseStatus(value, row, index){
			   
			   var houseStatusJson = ${houseStatusJson };
			   if(houseStatusJson!=undefined&&houseStatusJson!=null){
				   var result = '';
				   $.each(houseStatusJson,function(i,n){
					   if(value == i){
						   result = n;
						   return false;
					   }
				   });
				   return result;
			   }
		   }
		   
		   // 格式化时间
		   function formateDate(value, row, index) {
			   if (value != null) {
				   var vDate = new Date(value);
				   return vDate.format("yyyy-MM-dd HH:mm:ss");
			   } else {
				   return "";
			   }
		   }
		   //更新房源信息
		   function upHouseMsg(){
			   
			  var houseDesc = $("#houseDesc").val();
			  var houseAroundDesc = $("#houseAroundDesc").val();
			  var tillDate= $("#tillDate").val();
			  var houseRules= $("#houseRules").val();
			  var coordinate= $("#coordinate").val();
			  
			  var rentWay = ${houseBaseMsg.rentWay};
			  
			  if(rentWay==0){
				  
				  if( ! /^[1-9][0-9]*(\.[0-9])?$/.test($('#houseArea').val())){
	                    layer.alert("请填写正确的房源面积，最多只能保留一位小数 ", {icon: 5,time: 2000, title:'提示'});
	                    return false;
	                } 
				  
				  $("[name='roomArea']").each(function(i,e){
					  $(this).val(0); 
				  });
				  
				  
			  }else if(rentWay==1){
				  
				  $("[name='roomArea']").each(function(i,e){
					  if( ! /^[1-9][0-9]*(\.[0-9])?$/.test($(this).val())){
		                    layer.alert("请填写正确的房间面积，最多只能保留一位小数 ", {icon: 5,time: 2000, title:'提示'});
		                    return false;
		                } 
				  });
				  
				  $('#houseArea').val(0); 
				  
			  } 
			  
			  if(houseDesc.length < 100){
					layer.alert("请至少填写100字的房源描述", {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				if(houseDesc.length > 1000){
					layer.alert("房源描述不能超过1000字", {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				if(houseAroundDesc.length < 100){
					layer.alert("请至少填写100字的周边情况", {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				if(houseAroundDesc.length > 1000){
					layer.alert("周边情况不能超过1000字", {icon: 5,time: 2000, title:'提示'});
					return false;
				}
			   
				if(tillDate==''){
					layer.alert("出租截止日期不能为空", {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				
				if(houseRules!='' && (houseRules.length<50 ||houseRules.length>1000)){
					layer.alert("房屋守则字数应在50-1000之间", {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				
				if(coordinate!=''){
					var ctes=coordinate.split(",");
					console.log(ctes[0]);
					if(!/^[\-\+]?(0?\d{1,2}\.\d{1,6}|1[0-7]?\d{1}\.\d{1,6}|180\.0{1,6})$/.test(ctes[0])){
						layer.alert("经纬度错误1", {icon: 5,time: 2000, title:'提示'});
						return false;
					}
					if(!/^[\-\+]?([0-8]?\d{1}\.\d{1,6}|90\.0{1,6})$/.test(ctes[1])){
						layer.alert("经纬度错误", {icon: 5,time: 2000, title:'提示'});
						return false;
					}
				} else {
					layer.alert("经纬度不能为空", {icon: 5,time: 2000, title:'提示'});
					return false;
				}
			   
			   $.ajax({
					url:"${basePath}house/houseMgt/upHouseMsg",
					data:$('#upHouse').serialize(),
					dataType:"json",
					type:"post",
					async: true,
					success:function(result) {
						if(result.code === 0){
							$.callBackParent("house/houseMgt/upHouseMsgList",true,approveHouseInfoCallback);
						}else{
							layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
						}
					},
					error:function(result){
						layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
					}
				});
		   }
		   function chooseSelect(id,value){
			   if (!value){
				   value = "";
			   }
			   $("#"+id).find("option[value='"+value+"']").attr("selected",true);
		   }
			//审核房源刷新父页面数据
			function approveHouseInfoCallback(parent){
				parent.refreshData("listTable");
				parent.refreshData("listTableS");
			}

		   //跳转到设置房源日历页
		   function toHouseCalendar() {
			   $.openNewTab(new Date().getTime(),'house/houseCalendar/toHouseCalendar?fid=${houseFid}&rentWay=${rentWay}', "设置房源出租日历");
		   }
		   //跳转到百度坐标页
		   function toMap(){
			   $.openNewTab(new Date().getTime(),'http://api.map.baidu.com/lbsapi/getpoint/index.html', "百度地图");
		   }
	   </script>
	</body>
</html>
