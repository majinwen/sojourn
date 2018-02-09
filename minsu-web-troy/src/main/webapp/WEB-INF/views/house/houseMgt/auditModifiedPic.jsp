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
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				          <div class="form-group">
				           	<h2 class="title-font">基本信息</h2>
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
	                                   <input id="landlordName" name="landlordName" value="${landlord.realName }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
				                   <label class="col-sm-1 control-label">房东手机:</label>
		                           <div class="col-sm-2">
	                                   <input id="landlordMobile" name="landlordMobile" value="${landlord.customerMobile }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       
	                           <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">房源基本情况</h2>


							  <div class="row">
								  <c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.houseAddr}">
										  <div class="col-sm-1 houseAddr_Old">
											  <label class="control-label">
												  <span><font color="red">房源地址:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2 houseAddr_Old">
											  <label class="control-label">
												  <span><font color="red">${oldHouseBaseMsg.houseAddr}</font></span>
											  </label>
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.houseName}">
										  <div class="col-sm-1 houseName_Old">
											  <label class="control-label">
												  <span><font color="red">房源名称:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2 houseName_Old">
											  <label class="control-label">
												  <span id="houseName_Old"><font color="red">${oldHouseBaseMsg.houseName}</font></span>
											  </label>
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.roomNum || not empty oldHouseBaseMsg.hallNum || not empty oldHouseBaseMsg.toiletNum || not empty oldHouseBaseMsg.kitchenNum || not empty oldHouseBaseMsg.balconyNum}">
										  <div class="col-sm-1 houseModel_Old">
											  <label class="control-label">
												  <span><font color="red">房源户型:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2 houseModel_Old">
											  <label class="control-label">
												  <span><font color="red">${not empty oldHouseBaseMsg.roomNum ? oldHouseBaseMsg.roomNum : houseBaseMsg.roomNum}室${not empty oldHouseBaseMsg.hallNum ? oldHouseBaseMsg.hallNum : houseBaseMsg.hallNum}厅${not empty oldHouseBaseMsg.toiletNum ? oldHouseBaseMsg.toiletNum : houseBaseMsg.toiletNum}卫${not empty oldHouseBaseMsg.kitchenNum ? oldHouseBaseMsg.kitchenNum : houseBaseMsg.kitchenNum}厨${not empty oldHouseBaseMsg.balconyNum ? oldHouseBaseMsg.balconyNum : houseBaseMsg.balconyNum}阳台</font></span>
											  </label>
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>
							  </div>


		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源地址:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="houseAddr" name="houseAddr" title="${houseBaseMsg.houseAddr}" value="${houseBaseMsg.houseAddr}" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
								   <c:choose>
									   <c:when test="${not empty oldHouseBaseMsg.houseAddr}">
										   <div class="col-sm-1 houseAddr_Old">
											   <input id="houseAddrReject" value="${houseAddr_auditLogId}" style="display: none"/>
											   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#houseAddrReject').val(),$('#houseAddr'),$('#houseAddr_Old'),$('.houseAddr_Old'))">驳回</button>
										   </div>
									   </c:when>
									   <c:otherwise>
										   <div class="col-sm-1" style="visibility: hidden">
										   </div>
									   </c:otherwise>
								   </c:choose>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源名称:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="houseName" name="houseName" title="${houseBaseMsg.houseName}" value="${houseBaseMsg.houseName}" readonly="readonly" type="text" class="form-control m-b">
	                               </div>

								   <c:choose>
									   <c:when test="${not empty oldHouseBaseMsg.houseName}">
										   <div class="col-sm-1 houseName_Old">
											   <input id="houseNameReject" value="${houseName_auditLogId}" style="display: none"/>
											   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#houseNameReject').val(),$('#houseName'),$('#houseName_Old'),$('.houseName_Old'))">驳回</button>
										   </div>
									   </c:when>
									   <c:otherwise>
										   <div class="col-sm-1" style="visibility: hidden">
										   </div>
									   </c:otherwise>
								   </c:choose>

								   <label class="col-sm-1 control-label">房源户型:</label>
		                           <div class="col-sm-2">
	                                   <input id="houseModel" name="houseModel" value="${houseBaseMsg.roomNum }室${houseBaseMsg.hallNum }厅${houseBaseMsg.toiletNum }卫${houseBaseMsg.kitchenNum }厨${houseBaseMsg.balconyNum }阳台" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
								   <c:choose>
									   <c:when test="${not empty oldHouseBaseMsg.roomNum }||${not empty oldHouseBaseMsg.hallNum }||${not empty oldHouseBaseMsg.toiletNum }||${not empty oldHouseBaseMsg.kitchenNum }||${not empty oldHouseBaseMsg.balconyNum }">
										   <div class="col-sm-1 houseModel_Old">
											   <input id="houseModelReject" value="${houseModel_auditLogId}" style="display: none"/>
											   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#houseModelReject').val(),$('#houseModel'),$('#houseModel_Old'),$('.houseModel_Old'))">驳回</button>
										   </div>
									   </c:when>
									   <c:otherwise>
										   <div class="col-sm-1" style="visibility: hidden">
										   </div>
									   </c:otherwise>
								   </c:choose>
		                       </div>

							  <div class="row">
								  <c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.houseArea}">
										  <div class="col-sm-1">
											  <label class="control-label houseArea_Old">
												  <span><font color="red">房源面积:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2 houseArea_Old">
											  <label class="control-label">
												  <span id="houseArea_Old"><font color="red">${oldHouseBaseMsg.houseArea}</font></span>
											  </label>
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>
							  </div>

		                       <div class="row">
								   <div class="col-sm-1">
									   <label class="control-label">房源面积:</label>
								   </div>
								   <div class="col-sm-2">
									   <input id="houseArea" name="houseArea" value="${houseBaseMsg.houseArea }" readonly="readonly" type="text" class="form-control m-b">
								   </div>
								   <c:choose>
									   <c:when test="${not empty oldHouseBaseMsg.houseArea}">
										   <div class="col-sm-1 houseArea_Old">
											   <input id="houseAreaReject" value="${houseArea_auditLogId}" style="display: none"/>
											   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#houseAreaReject').val(),$('#houseArea'),$('#houseArea_Old'),$('.houseArea_Old'))">驳回</button>
										   </div>
									   </c:when>
									   <c:otherwise>
										   <div class="col-sm-1" style="visibility: hidden">
										   </div>
									   </c:otherwise>
								   </c:choose>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源坐标:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="houseLocation" name="houseLocation" value="${housePhyMsg.longitude },${housePhyMsg.latitude }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
								   <div class="col-sm-1" style="visibility: hidden">
								   </div>
				                   <label class="col-sm-1 control-label">房源类型:</label>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b" id="houseType" disabled="disabled">
		                                  <c:forEach items="${houseTypeList }" var="obj">
		                                   	 <option value="${obj.key }" <c:if test="${obj.key == houseBaseMsg.houseType }">selected</c:if>>${obj.text }</option>
		                                  </c:forEach>
	                                   </select>
	                               </div>
								   <div class="col-sm-1" style="visibility: hidden">
								   </div>
		                       </div>

							  <div class="row">
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
		                       
                               <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">租住描述</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">民宿类型:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b" id="homestayType" disabled="disabled">
		                           	   	  <c:forEach items="${homestayList }" var="obj">
			                                  <option value="${obj.key }" <c:if test="${obj.key == houseBaseExt.homestayType }">selected</c:if>>${obj.text }</option>
		                                  </c:forEach>
	                                   </select>
	                               </div>
								   <div class="col-sm-1" style="visibility: hidden">
								   </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">出租类型:</label>
	                               </div>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b" id="rentWay" disabled="disabled">
		                           	   	  <c:forEach items="${rentWayMap }" var="obj">
			                                  <option value="${obj.key }" <c:if test="${obj.key == houseBaseMsg.rentWay }">selected</c:if>>${obj.value }</option>
		                                  </c:forEach>
	                                   </select>
	                               </div>
								   <div class="col-sm-1" style="visibility: hidden">
								   </div>
				                   <label class="col-sm-1 control-label">被单更换:</label>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b" id="sheetsReplaceRules" disabled="disabled">
		                           	   	  <c:forEach items="${sheetReplaceList }" var="obj">
			                                  <option value="${obj.key }" <c:if test="${obj.key == houseBaseExt.sheetsReplaceRules}">selected</c:if>>${obj.text }</option>
		                                  </c:forEach>
	                                   </select>
	                               </div>
		                       </div>

							  <div class="row">
								  <c:choose>
									  <c:when test="${not empty oldHouseBaseExt.checkInLimit}">
										  <div class="col-sm-1 checkInLimit_Old">
											  <label class="control-label">
												  <span><font color="red">入住人数限制:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2 checkInLimit_Old">
											  <c:choose>
												  <c:when test="${oldHouseBaseExt.checkInLimit == 0 }">
													  <label class="control-label">
														  <span id="checkInLimit_Old"><font color="red">不限制</font></span>
													  </label>
												  </c:when>
												  <c:otherwise>
													  <label class="control-label">
														  <span id="checkInLimit_Old"><font color="red">${oldHouseBaseExt.checkInLimit}</font></span>
													  </label>
												  </c:otherwise>
											  </c:choose>
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseExt.isTogetherLandlord}">
										  <div class="col-sm-1 isTogetherLandlord_Old">
											  <label class="control-label">
												  <span><font color="red">是否与房东同住:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2 isTogetherLandlord_Old">
											  <c:if test="${oldHouseBaseExt.isTogetherLandlord == 0 || empty oldHouseBaseExt.isTogetherLandlord}">
												  <label class="control-label">
													  <span id="isTogetherLandlord_Old"><font color="red">否</font></span>
												  </label>
											  </c:if>
											  <c:if test="${oldHouseBaseExt.isTogetherLandlord == 1 }">
												  <label class="control-label">
													  <span id="isTogetherLandlord_Old"><font color="red">是</font></span>
												  </label>
											  </c:if>
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseExt.minDay}">
										  <div class="col-sm-1 minDay_Old">
											  <label class="control-label">
												  <span id="minDay_Old"><font color="red">最少入住天数:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2 minDay_Old">
											  <label class="control-label">
												  <span id="minDay_Old"><font color="red">${oldHouseBaseExt.minDay}</font></span>
											  </label>
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>
							  </div>

		                       <div class="row">
				                   <label class="col-sm-1 control-label">入住人数限制:</label>
		                           <div class="col-sm-2">
		                           	   <c:choose>
			                           	   <c:when test="${houseBaseExt.checkInLimit == 0 }">
			                                   <input id="checkInLimit" name="checkInLimit" value="不限制" readonly="readonly" type="text" class="form-control m-b">
			                           	   </c:when>
			                           	   <c:otherwise>
			                                   <input id="checkInLimit" name="checkInLimit" value="${houseBaseExt.checkInLimit }" readonly="readonly" type="text" class="form-control m-b">
			                           	   </c:otherwise>
		                           	   </c:choose>
	                               </div>
								   <c:choose>
									   <c:when test="${not empty oldHouseBaseExt.checkInLimit}">
										   <div class="col-sm-1 checkInLimit_Old">
											   <input id="checkInLimitReject" value="${checkInLimit_auditLogId}" style="display: none"/>
											   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#checkInLimitReject').val(),$('#checkInLimit'),$('#checkInLimit_Old'),$('.checkInLimit_Old'))">驳回</button>
										   </div>
									   </c:when>
									   <c:otherwise>
										   <div class="col-sm-1" style="visibility: hidden">
										   </div>
									   </c:otherwise>
								   </c:choose>
		                       	   <div class="col-sm-1">
					                   <label class="control-label">是否与房东同住:</label>
		                           </div>
			                       <div class="col-sm-2">
				                       <c:if test="${houseBaseExt.isTogetherLandlord == 0 || empty houseBaseExt.isTogetherLandlord}">
		                                  <input id="isToilet" name="isToilet" value="否" readonly="readonly" type="text" class="form-control m-b">
		                               </c:if>
				                       <c:if test="${houseBaseExt.isTogetherLandlord == 1 }">
		                                  <input id="isToilet" name="isToilet" value="是" readonly="readonly" type="text" class="form-control m-b">
		                               </c:if>
		                           </div>
								   <c:choose>
									   <c:when test="${not empty oldHouseBaseExt.isTogetherLandlord}">
										   <div class="col-sm-1 isTogetherLandlord_Old">
											   <input id="isTogetherLandlordReject" value="${isTogetherLandlord_auditLogId}" style="display: none"/>
											   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#isTogetherLandlordReject').val(),$('#isToilet'),$('#isTogetherLandlord_Old'),$('.isTogetherLandlord_Old'))">驳回</button>
										   </div>
									   </c:when>
									   <c:otherwise>
										   <div class="col-sm-1" style="visibility: hidden">
										   </div>
									   </c:otherwise>
								   </c:choose>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">最少入住天数:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="minDay" name="minDay" value="${houseBaseExt.minDay }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
								   <c:choose>
									   <c:when test="${not empty oldHouseBaseExt.minDay}">
										   <div class="col-sm-1 minDay_Old">
											   <input id="minDayReject" value="${minDay_auditLogId}" style="display: none"/>
											   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#minDayReject').val(),$('#minDay'),$('#minDay_Old'),$('.minDay_Old'))">驳回</button>
										   </div>
									   </c:when>
									   <c:otherwise>
										   <div class="col-sm-1" style="visibility: hidden">
										   </div>
									   </c:otherwise>
								   </c:choose>
		                       </div>

							  <div class="row">
								  <c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.tillDate}">
										  <div class="col-sm-1 tillDate_Old">
											  <label class="control-label">
												  <span><font color="red">可预订截止时间:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2 tillDate_Old">
											  <label class="control-label">
												  <span id="tillDate_Old"><font color="red"><fmt:formatDate value="${oldHouseBaseMsg.tillDate }" pattern="yyyy-MM-dd"/></font></span>
											  </label>
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseExt.checkInTime}">
										  <div class="col-sm-1 checkInTime_Old">
											  <label class="control-label">
												  <span><font color="red">入住时间:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2 checkInTime_Old">
											  <label class="control-label">
												  <span id="checkInTime_Old"><font color="red">${oldHouseBaseExt.checkInTime}</font></span>
											  </label>
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseExt.checkOutTime}">
										  <div class="col-sm-1 checkOutTime_Old">
											  <label class="control-label">
												  <span><font color="red">退房时间:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2 checkOutTime_Old">
											  <label class="control-label">
												  <span id="checkOutTime_Old"><font color="red">${oldHouseBaseExt.checkOutTime}</font></span>
											  </label>
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>
							  </div>

		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">可预订截止时间:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="tillDate" name="tillDate" value='<fmt:formatDate value="${houseBaseMsg.tillDate }" pattern="yyyy-MM-dd"/>' readonly="readonly" type="text" class="form-control m-b">
	                               </div>
								   <c:choose>
									   <c:when test="${not empty oldHouseBaseMsg.tillDate}">
										   <div class="col-sm-1 tillDate_Old">
											   <input id="tillDateReject" value="${tillDate_auditLogId}" style="display: none"/>
											   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#tillDateReject').val(),$('#tillDate'),$('#tillDate_Old'),$('.tillDate_Old'))">驳回</button>
										   </div>
									   </c:when>
									   <c:otherwise>
										   <div class="col-sm-1" style="visibility: hidden">
										   </div>
									   </c:otherwise>
								   </c:choose>
				                   <label class="col-sm-1 control-label">入住时间:</label>
		                           <div class="col-sm-2">
	                                   <input id="checkInTime" name="checkInTime" value="${houseBaseExt.checkInTime }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
								   <c:choose>
									   <c:when test="${not empty oldHouseBaseExt.checkInTime}">
										   <div class="col-sm-1 checkInTime_Old">
											   <input id="checkInTimeReject" value="${checkInTime_auditLogId}" style="display: none"/>
											   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#checkInTimeReject').val(),$('#checkInTime'),$('#checkInTime_Old'),$('.checkInTime_Old'))">驳回</button>
										   </div>
									   </c:when>
									   <c:otherwise>
										   <div class="col-sm-1" style="visibility: hidden">
										   </div>
									   </c:otherwise>
								   </c:choose>
				                   <label class="col-sm-1 control-label">退房时间:</label>
		                           <div class="col-sm-2">
	                                   <input id="checkOutTime" name="checkOutTime" value="${houseBaseExt.checkOutTime }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
								   <c:choose>
									   <c:when test="${not empty oldHouseBaseExt.checkOutTime}">
										   <div class="col-sm-1 checkOutTime_Old">
											   <input id="checkOutTimeReject" value="${checkOutTime_auditLogId}" style="display: none"/>
											   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#checkOutTimeReject').val(),$('#checkOutTime'),$('#checkOutTime_Old'),$('.checkOutTime_Old'))">驳回</button>
										   </div>
									   </c:when>
									   <c:otherwise>
										   <div class="col-sm-1" style="visibility: hidden">
										   </div>
									   </c:otherwise>
								   </c:choose>
		                       </div>
		                       
                               <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">房源描述</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">小区名称:</label>
	                               </div>
		                           <div class="col-sm-4">
	                                   <input id="communityName" name="communityName" title="${housePhyMsg.communityName}" value="${housePhyMsg.communityName}" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                       </div>

							  <div class="row">
								  <c:choose>
									  <c:when test="${not empty oldHouseDesc.houseDesc}">
										  <div class="col-sm-1 houseDesc_Old">
											  <label class="control-label">
												  <span><font color="red">房源描述:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-4 houseDesc_Old" style="word-wrap:break-word;word-break:break-all;">
											  <label class="control-label">
												  <span id="houseDesc_Old"><font color="red">${oldHouseDesc.houseDesc}</font></span>
											  </label>
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-4" style="visibility: hidden">
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseDesc.houseAroundDesc}">
										  <div class="col-sm-1 houseAroundDesc_Old">
											  <label class="control-label">
												  <span><font color="red">周边状况:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-4 houseAroundDesc_Old" style="word-wrap:break-word;word-break:break-all;">
											  <label class="control-label">
												  <span id="houseAroundDesc_Old"><font color="red">${oldHouseDesc.houseAroundDesc}</font></span>
											  </label>
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-4" style="visibility: hidden">
										  </div>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>
							  </div>

		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源描述:</label>
	                               </div>
		                           <div class="col-sm-4">
	                                   <textarea rows="8" id="houseDesc" name="houseDesc"  readonly="readonly"  class="form-control m-b">${houseDesc.houseDesc }</textarea>
	                               </div>
								   <c:choose>
									   <c:when test="${not empty oldHouseDesc.houseDesc}">
										   <div class="col-sm-1 houseDesc_Old">
											   <input id="houseDescReject" value="${houseDesc_auditLogId}" style="display: none"/>
											   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#houseDescReject').val(),$('#houseDesc'),$('#houseDesc_Old'),$('.houseDesc_Old'))">驳回</button>
										   </div>
									   </c:when>
									   <c:otherwise>
										   <div class="col-sm-1" style="visibility: hidden">
										   </div>
									   </c:otherwise>
								   </c:choose>

		                           <div class="col-sm-1">
				                   	   <label class="control-label">周边状况:</label>
	                               </div>
		                           <div class="col-sm-4">
	                                   <textarea rows="8" id="houseAroundDesc" name="houseAroundDesc"  readonly="readonly"  class="form-control m-b">${houseDesc.houseAroundDesc }</textarea>
	                               </div>
								   <c:choose>
									   <c:when test="${not empty oldHouseDesc.houseAroundDesc}">
										   <div class="col-sm-1 houseAroundDesc_Old">
											   <input id="houseAroundDescReject" value="${houseAroundDesc_auditLogId}" style="display: none"/>
											   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#houseAroundDescReject').val(),$('#houseAroundDesc'),$('#houseAroundDesc_Old'),$('.houseAroundDesc_Old'))">驳回</button>
										   </div>
									   </c:when>
									   <c:otherwise>
										   <div class="col-sm-1" style="visibility: hidden">
										   </div>
									   </c:otherwise>
								   </c:choose>
		                       	
		                       </div>
		                       <!-- 房屋守则 -->
		                       <c:if test="${rentWay==0 }">
			                       <div class="row">
									  <c:choose>
										  <c:when test="${not empty oldHouseDesc.houseRules}">
											  <div class="col-sm-1 houseRules_Old">
												  <label class="control-label">
													  <span><font color="red">房屋守则:（修改前）</font></span>
												  </label>
											  </div>
											  <div class="col-sm-4 houseRules_Old" style="word-wrap:break-word;word-break:break-all;">
												  <label class="control-label">
													  <span id="houseRules_Old"><font color="red">${oldHouseDesc.houseRules}</font></span>
												  </label>
											  </div>
											  <div class="col-sm-1" style="visibility: hidden">
											  </div>
										  </c:when>
										  <c:when test="${hasHouseRule==1 && empty oldHouseDesc.houseRules && not empty houseDesc.houseRules}">
											  <div class="col-sm-1 houseRules_Old">
												  <label class="control-label">
													  <span><font color="red">房屋守则:（修改前）</font></span>
												  </label>
											  </div>
											  <div class="col-sm-4 houseRules_Old" style="word-wrap:break-word;word-break:break-all;">
												  <label class="control-label">
													  <span id="houseRules_Old"><font color="red">未填写</font></span>
												  </label>
											  </div>
											  <div class="col-sm-1" style="visibility: hidden">
											  </div>
										  </c:when>
										  <c:otherwise>
											  <div class="col-sm-1" style="visibility: hidden">
											  </div>
											  <div class="col-sm-4" style="visibility: hidden">
											  </div>
											  <div class="col-sm-1" style="visibility: hidden">
											  </div>
										  </c:otherwise>
									  </c:choose>
								  </div>
	
			                       <div class="row">
			                           <div class="col-sm-1">
					                   	   <label class="control-label">房屋守则:</label>
		                               </div>
			                           <div class="col-sm-4">
		                                   <textarea rows="8" id="houseRules" name="houseRules"  readonly="readonly"  class="form-control m-b">${houseDesc.houseRules }</textarea>
		                               </div>
									   <c:choose>
										   <c:when test="${not empty oldHouseDesc.houseRules}">
											   <div class="col-sm-1 houseRules_Old">
												   <input id="houseRulesReject" value="${houseRules_auditLogId}" style="display: none"/>
												   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#houseRulesReject').val(),$('#houseRules'),$('#houseRules_Old'),$('.houseRules_Old'))">驳回</button>
											   </div>
										   </c:when>
										   <c:when test="${hasHouseRule==1 && empty oldHouseDesc.houseRules && not empty houseDesc.houseRules}">
											   <div class="col-sm-1 houseRules_Old">
												   <input id="houseRulesReject" value="${houseRules_auditLogId}" style="display: none"/>
												   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#houseRulesReject').val(),$('#houseRules'),$('#houseRules_Old'),$('.houseRules_Old'))">驳回</button>
											   </div>
										   </c:when>
										   <c:otherwise>
											   <div class="col-sm-1" style="visibility: hidden">
											   </div>
										   </c:otherwise>
									   </c:choose>
			                       </div>  
		                       </c:if>
		                       <c:if test="${rentWay==1 }">
			                       <div class="row">
									  <c:choose>
										  <c:when test="${not empty oldHouseRoomExt.roomRules}">
											  <div class="col-sm-1 roomRules_Old">
												  <label class="control-label">
													  <span><font color="red">房屋守则:（修改前）</font></span>
												  </label>
											  </div>
											  <div class="col-sm-4 roomRules_Old" style="word-wrap:break-word;word-break:break-all;">
												  <label class="control-label">
													  <span id="roomRules_Old"><font color="red">${oldHouseRoomExt.roomRules}</font></span>
												  </label>
											  </div>
											  <div class="col-sm-1" style="visibility: hidden">
											  </div>
										  </c:when>
										  <c:when test="${hasRoomRule==1 && empty oldHouseRoomExt.roomRules && not empty houseRoomExtEntity.roomRules}">
											  <div class="col-sm-1 houseRules_Old">
												  <label class="control-label">
													  <span><font color="red">房屋守则:（修改前）</font></span>
												  </label>
											  </div>
											  <div class="col-sm-4 houseRules_Old" style="word-wrap:break-word;word-break:break-all;">
												  <label class="control-label">
													  <span id="houseRules_Old"><font color="red">未填写</font></span>
												  </label>
											  </div>
											  <div class="col-sm-1" style="visibility: hidden">
											  </div>
										  </c:when>
										  <c:otherwise>
											  <div class="col-sm-1" style="visibility: hidden">
											  </div>
											  <div class="col-sm-4" style="visibility: hidden">
											  </div>
											  <div class="col-sm-1" style="visibility: hidden">
											  </div>
										  </c:otherwise>
									  </c:choose>
								  </div>
	
			                       <div class="row">
			                           <div class="col-sm-1">
					                   	   <label class="control-label">房屋守则:</label>
		                               </div>
			                           <div class="col-sm-4">
		                                   <textarea rows="8" id="roomRules" name="roomRules"  readonly="readonly"  class="form-control m-b">${houseRoomExtEntity.roomRules }</textarea>
		                               </div>
									   <c:choose>
										   <c:when test="${not empty oldHouseRoomExt.roomRules}">
											   <div class="col-sm-1 houseRules_Old">
												   <input id="roomRulesReject" value="${roomRules_auditLogId}" style="display: none"/>
												   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#roomRulesReject').val(),$('#roomRules'),$('#roomRules_Old'),$('.roomRules_Old'))">驳回</button>
											   </div>
										   </c:when>
										   <c:when test="${hasRoomRule==1 && empty oldHouseRoomExt.roomRules && not empty houseRoomExtEntity.roomRules}">
											   <div class="col-sm-1 houseRules_Old">
												   <input id="roomRulesReject" value="${roomRules_auditLogId}" style="display: none"/>
												   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#roomRulesReject').val(),$('#roomRules'),$('#roomRules_Old'),$('.roomRules_Old'))">驳回</button>
											   </div>
										   </c:when>
										   <c:otherwise>
											   <div class="col-sm-1" style="visibility: hidden">
											   </div>
										   </c:otherwise>
									   </c:choose>
			                       </div>  
		                       </c:if>
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
	                                   <input id="discount3" name="discount3" value="${discount3 }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">7天折扣率</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="discount7" name="discount7" value="${discount7 }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">30天折扣率</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="discount30" name="discount30" value="${discount30 }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">价格:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="leasePrice" name="leasePrice" value="${houseBaseMsg.leasePrice/100 }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">押金规则:</label>
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
	                                   <input id="checkOutRulesName" name="checkOutRulesName" value="${checkOutRulesName }" readonly="readonly"  type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       
	                           <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">退订政策</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">违约金计算方式:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="calculation" name="calculation" value="${calculation }" readonly="readonly"  type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">${unregName1 }:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="freeDays" name="freeDays" value="${unregVal1 }" readonly="readonly"  type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   	<label class="control-label">${unregName2 }:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="cancellRent" name="cancellRent" value="${unregVal2 }" readonly="readonly"  type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	  <label class="control-label">${unregName3 }:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="checkInRent" name="checkInRent" value="${unregVal3 }" readonly="readonly"  type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       
	                           <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">房间信息</h2>
		                       <c:forEach items="${roomList }" var="room">

								   <div class="row">
									   <c:choose>
										   <c:when test="${not empty oldHouseRoomMsg.roomName}">
											   <div class="col-sm-1 roomName_Old">
												   <label class="control-label">
													   <span><font color="red">房间名称:（修改前）</font></span>
												   </label>
											   </div>
											   <div class="col-sm-2 roomName_Old">
												   <label class="control-label">
													   <span id="roomName_Old"><font color="red">${oldHouseRoomMsg.roomName}</font></span>
												   </label>
											   </div>
											   <div class="col-sm-1" style="visibility: hidden">
											   </div>
										   </c:when>
										   <c:otherwise>
											   <div class="col-sm-1" style="visibility: hidden">
											   </div>
											   <div class="col-sm-2" style="visibility: hidden">
											   </div>
											   <div class="col-sm-1" style="visibility: hidden">
											   </div>
										   </c:otherwise>
									   </c:choose>
								   </div>


			                       <div class="row">
			                           <div class="col-sm-1">
					                   	   <label class="control-label">房间名称:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <input id="roomName" name="roomName" title="${room.roomName}" value="${room.roomName}" readonly="readonly" type="text" class="form-control m-b">
		                               </div>
									   <c:choose>
										   <c:when test="${not empty oldHouseRoomMsg.roomName}">
											   <div class="col-sm-1 roomName_Old">
												   <input id="roomNameReject" value="${roomName_auditLogId}" style="display: none"/>
												   <button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#roomNameReject').val(),$('#roomName'),$('#roomName_Old'),$('.roomName_Old'))">驳回</button>
											   </div>
										   </c:when>
										   <c:otherwise>
											   <div class="col-sm-1" style="visibility: hidden">
											   </div>
										   </c:otherwise>
									   </c:choose>

			                           <div class="col-sm-1">
					                   	   <label class="control-label">房间面积:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <input id="roomArea" name="roomArea" value="${room.roomArea }" readonly="readonly" type="text" class="form-control m-b">
		                               </div>
			                           <div class="col-sm-1">
					                   	   <label class="control-label">房间价格:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <input id=roomPrice name="roomPrice" value="${room.leasePrice }" readonly="readonly" type="text" class="form-control m-b">
		                               </div>
			                       </div>
			                       <div class="row">
			                           <div class="col-sm-1">
					                   	   <label class="control-label">房间类型:</label>
		                               </div>
			                           <div class="col-sm-2">
					                       <c:if test="${room.roomType == 0 || empty room.roomType}">
			                                  <input id="roomType" name="roomType" value="房间类型" readonly="readonly" type="text" class="form-control m-b">
			                               </c:if>
					                       <c:if test="${room.roomType == 1 }">
			                                  <input id="roomType" name="roomType" value="客厅" readonly="readonly" type="text" class="form-control m-b">
			                               </c:if>
		                               </div>
			                           <div class="col-sm-1">
					                   	   <label class="control-label">是否有独立卫生间:</label>
		                               </div>
			                           <div class="col-sm-2">
					                       <c:if test="${room.isToilet == 0 || empty room.isToilet}">
			                                  <input id="isToilet" name="isToilet" value="否" readonly="readonly" type="text" class="form-control m-b">
			                               </c:if>
					                       <c:if test="${room.isToilet == 1 }">
			                                  <input id="isToilet" name="isToilet" value="是" readonly="readonly" type="text" class="form-control m-b">
			                               </c:if>
		                               </div>
			                           <div class="col-sm-1">
					                   	   <label class="control-label">入住人数限制:</label>
		                               </div>
		                               <div class="col-sm-2">
			                           	   <c:choose>
				                           	   <c:when test="${room.checkInLimit == 0 }">
				                                   <input id="room.checkInLimit" name="room.checkInLimit" value="不限制" readonly="readonly" type="text" class="form-control m-b">
				                           	   </c:when>
				                           	   <c:otherwise>
				                                   <input id="room.checkInLimit" name="room.checkInLimit" value="${room.checkInLimit }" readonly="readonly" type="text" class="form-control m-b">
				                           	   </c:otherwise>
			                           	   </c:choose>
		                               </div>
			                       </div>
			                       <c:forEach items="${room.bedList }" var="bed">
				                       <div class="row">
				                           <div class="col-sm-1">
						                   	   <label class="control-label">床铺编号:</label>
			                               </div>
				                           <div class="col-sm-2">
			                                   <input id="bedSn" name="bedSn" value="${bed.bedSn }" readonly="readonly" type="text" class="form-control m-b">
			                               </div>
				                           <div class="col-sm-1">
						                   	   <label class="control-label">床铺类型:</label>
			                               </div>
				                           <div class="col-sm-2">
				                           	   <select class="form-control m-b" id="bedType" disabled="disabled">
					                           	   <c:forEach items="${bedTypeList }" var="obj">
					                                  <option value="${obj.key }" <c:if test="${obj.key == bed.bedType }">selected</c:if>>${obj.text }</option>
				                                   </c:forEach>
				                               </select>
			                               </div>
				                           <div class="col-sm-1">
						                   	   <label class="control-label">床铺尺寸:</label>
			                               </div>
				                           <div class="col-sm-2">
				                           	   <select class="form-control m-b" id="bedSize" disabled="disabled">
					                           	   <c:forEach items="${bedSizeList }" var="obj">
					                                  <option value="${obj.key }" <c:if test="${obj.key == bed.bedSize }">selected</c:if>>${obj.text }</option>
				                                   </c:forEach>
				                               </select>
			                               </div>
			                           </div>
			                       </c:forEach>
	                              <div class="hr-line-dashed"></div>
		                       </c:forEach>
		                       
		                      <h2 class="title-font"><c:if test="${houseBaseMsg.rentWay == 0 }">
							        <a href="javascript:uploadHousePic('${houseBaseMsg.fid}')">照片信息</a>
							       </c:if>
							        <c:if test="${houseBaseMsg.rentWay == 1 }">
							        <a href="javascript:uploadRoomPic('${houseFid}')">照片信息</a></c:if></h2>
							        
		                      <div class="row">
		                       	   <div class="form-group">
		                       	   <form id="form" class="form-group">
		                            	<label class="col-sm-1 control-label">房源照片</label>
									    <input type="hidden" name="landlordMobile" value="${landlord.customerMobile }">
									    <input type="hidden" name="landlordUid" value="${landlord.uid }">
									    <input type="hidden" name="houseName" value="${houseBaseMsg.houseName }">
									    <input type="hidden" name="houseSn" value="${houseBaseMsg.houseSn }">
									    <input type="hidden" name="houseOrRoomFid" value="${houseFid }">
									    <input type="hidden" name="rentWay" value="${rentWay }">

									    <input type="hidden" name="houseBaseFid" value="${houseBaseMsg.fid }">
									    <input type="hidden" id="remarkMsg" name="remarkMsg" value="">

		                                <div class="col-sm-9">
			                                <div class="lightBoxGallery">
				                                <c:forEach items="${noAuditPic }" var="pic">
				                                	<span id="${pic.fid }" style="display:inline-block">
				                                		<input type="hidden"  name="picFids"  value="${pic.fid }">
				                            			<a href="${picBaseAddr }${pic.picBaseUrl }${pic.picSuffix}" title="图片" data-gallery="blueimp-gallery${status.index }"><img src="${picBaseAddr }${pic.picBaseUrl }${pic.picSuffix}" style="height: 135px; width: 165px;"></a>
				                            			<button type='button'  class='btn btn-danger btn-xs' onclick="delPic('${pic.fid }','${pic.picServerUuid }');"><span>删除</span></button>
														<button type="button" class="btn btn-danger btn-xs" onclick="showRejectPic('${pic.fid }',$('#'+'${ pic.fid }'),'${pic.picType }')">驳回</button>
				                                	</span>
				                                </c:forEach>
					                        </div>
		                                </div>
		                           </form>     
		                           </div>
                               </div>

                              <c:if test="${not empty newHouseDefaultPicMsg}">
								  <div class="row">
									  <label class="col-sm-1 control-label">默认照片</label>
									  <div class="col-sm-3">
											  <div class="lightBoxGallery">
												<input id="defaultPicReject" value="${defaultPicFid_auditLogId}" style="display: none"/>
												<span id="${oldHouseDefaultPicMsg.fid }">
													<a href="${picBaseAddr }${oldHouseDefaultPicMsg.picBaseUrl }${oldHouseDefaultPicMsg.picSuffix}" title="图片" data-gallery="blueimp-gallery${status.index }"><img src="${picBaseAddr }${oldHouseDefaultPicMsg.picBaseUrl }${oldHouseDefaultPicMsg.picSuffix}" style="height: 135px; width: 165px;"></a>
												</span>
												<c:if test="${empty oldHouseDefaultPicMsg}">
										           <span><font color="red">老的默认图片已被删除</font></span>
										        </c:if>
											  </div>
									  </div>
									  <div class="col-sm-3 defaultPicMsg">
										  <div class="lightBoxGallery">
												<span id="${newHouseDefaultPicMsg.fid }">
													<a href="${picBaseAddr }${newHouseDefaultPicMsg.picBaseUrl }${newHouseDefaultPicMsg.picSuffix}" title="图片" data-gallery="blueimp-gallery${status.index }"><img src="${picBaseAddr }${newHouseDefaultPicMsg.picBaseUrl }${newHouseDefaultPicMsg.picSuffix}" style="height: 135px; width: 165px;"></a>
													<button type="button" class="btn btn-danger btn-xs" onclick="showRejectField($('#defaultPicReject').val(),null,null,$('.defaultPicMsg'))">驳回</button>
												</span>
										  </div>
									  </div>
								  </div>
							      <div class="row">
									  <div class="col-sm-1">
									  </div>
										  <div class="col-sm-3 defaultPicMsg">
											  <div class="lightBoxGallery">
												  <span>旧默认图</span>
											  </div>
										  </div>
									  <div class="col-sm-3 defaultPicMsg">
										  <div class="lightBoxGallery">
											  <span>新默认图</span>
										  </div>
									  </div>
								  </div>
							  </c:if>
                
	                           <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">摄影师</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">摄影师姓名:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="cameramanName" name="cameramanName" value="${houseBaseMsg.cameramanName }" readonly="readonly"  type="text" class="form-control m-b">
	                               </div>
				                   <label class="col-sm-1 control-label">摄影师手机:</label>
		                           <div class="col-sm-2">
	                                   <input id="cameramanMobile" name="cameramanMobile" value="${houseBaseMsg.cameramanMobile }" readonly="readonly"  type="text" class="form-control m-b">
	                               </div>
	                           </div>
		                       
                               <c:if test="${houseBaseMsg.rentWay == 0 }">
		                           <div class="hr-line-dashed"></div>
			                       <h2 class="title-font">房源状态</h2>
			                       <div class="row">
			                           <div class="col-sm-1">
					                   	   <label class="control-label">房源状态:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <select class="form-control m-b" id="houseStatus" disabled="disabled">
			                                   <c:forEach items="${houseStatusMap }" var="obj">
				                                   <option value="${obj.key }" <c:if test="${obj.key == houseBaseMsg.houseStatus }">selected</c:if>>${obj.value }</option>
			                                   </c:forEach>
		                                   </select>
		                               </div>
	                               </div>
                               </c:if>
                               <c:if test="${houseBaseMsg.rentWay == 1 }">
		                           <div class="hr-line-dashed"></div>
			                       <h2 class="title-font">房间状态</h2>
			                       <div class="row">
			                           <div class="col-sm-1">
					                   	   <label class="control-label">房间状态:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <select class="form-control m-b" id="houseStatus" disabled="disabled">
			                                   <c:forEach items="${houseStatusMap }" var="obj">
				                                   <option value="${obj.key }" <c:if test="${obj.key == roomList[0].roomStatus }">selected</c:if>>${obj.value }</option>
			                                   </c:forEach>
		                                   </select>
		                               </div>
	                               </div>
                               </c:if>

							  <div class="hr-line-dashed"></div>
							  <h2 class="title-font">备注</h2>
							  <form id="remarkform" class="form-group">
								  <div class="row">
									  <div class="form-group">
										  <label class="col-sm-1 control-label">驳回原因（短信告知房东）：</label>
										  <div class="col-sm-8">
											  <textarea id="remark" name="remark" disabled class="form-control" rows="5"></textarea>
										  </div>
									  </div>
								  </div>
							  </form>

								<div class="row">
									<div class="col-sm-12">
										<div class="ibox float-e-margins">
											<div class="ibox-content">
												<form class="form-group">
													<div class="row">
														<div class="form-group">
															<div class="col-sm-4 col-sm-offset-5">
																<customtag:authtag authUrl="house/houseMgt/approveModifiedPic">
																	<button type="button" onclick="auditHousePic();" class="btn btn-primary">完成</button>&nbsp;&nbsp;
																</customtag:authtag>	
																<customtag:authtag authUrl="house/houseMgt/showHouseOperateLogList">
																	<button type="button" class="btn btn-white" data-toggle="modal" data-target="#opLogModel">查看审核历史</button>
																</customtag:authtag>
															</div>
														</div>
													</div>
												</form>
											</div>
										</div>
									</div>
								</div>
	                           </div>
			               </div>
			           </div>
	               </div>
	           </div>
	       </div>
	   <!-- 审核历史弹出框 -->
	  <div class="modal inmodal fade" id="opLogModel" tabindex="-1" role="dialog"  aria-hidden="true">
	         <div class="modal-dialog modal-lg">
	             <div class="modal-content">
	                 <div class="modal-header">
	                     <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	                     <h4 class="modal-title">审核历史</h4>
	                 </div>
	                 <div class="ibox-content">
		                 <div class="row row-lg">
		                    <div class="col-sm-12">
		                        <div class="example-wrap">
		                            <div class="example">
					                    <table class="table table-bordered" id="historyListTable"
											data-click-to-select="true" data-toggle="table"
											data-side-pagination="server" data-pagination="true"
											data-page-list="[10,20,50]" 
											data-page-size="10" data-pagination-first-text="首页"
											data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
											data-pagination-last-text="末页"
											data-content-type="application/x-www-form-urlencoded"
											data-query-params="historyPaginationParam" data-method="post"
											data-height="300"
											data-single-select="true" data-url="house/houseMgt/showHouseOperateLogList">
											<thead>
												 <tr>
									                  <th data-field="fromStatus" data-width="25%" data-align="center" data-formatter="formateHouseStatus">初始状态</th>
									                  <th data-field="toStatus" data-width="25%" data-align="center" data-formatter="formateHouseStatus">结束状态</th>
									                  <th data-field="remark" data-width="25%" data-align="center">备注</th>
									                  <th data-field="createDate" data-width="25%" data-align="center" data-formatter="formateDate">操作日期</th>
									             </tr>
											</thead>
										</table>            
						            </div>
						        </div>
						    </div>
						 </div>
				     </div>      
	                 <div class="modal-footer">
	                     <button type="button" class="btn btn-primary"  data-dismiss="modal">关闭</button>
	                 </div>
	             </div>
	         </div>
	     </div>

	   <!--房源字段审核驳回弹出框 -->
	   <div class="modal inmodal fade" id="houseFieldReject"
			tabindex="-1" role="dialog" aria-hidden="true">
		   <div class="modal-dialog modal-lm">
			   <div class="modal-content">
				   <div style="padding:5px 10px 0;">
					   <button type="button" class="close" data-dismiss="modal">
						   <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					   </button>
				   </div>

				   <div class="ibox-content" style="border:none;padding-bottom:0;">
					   <div class="row row-lg">
						   <div class="col-sm-12">
							   <div class="example-wrap">
								   <div class="example">
									   <form id="remarkFieldform" class="form-group">
										   <div class="row">
											   <div class="col-sm-12">
												   <label class="col-sm-2 control-label left2">驳回原因：</label>
												   <div class="col-sm-8 left2">
													   <textarea id="fieldRemark" name="fieldRemark" class="form-control" rows="3"></textarea>
												   </div>
											   </div>
										   </div>
									   </form>
									   <%--<span><font color="red">例:</font>&nbsp;&nbsp;<font color="#da70d6">房源名称：名称中含有敏感字</font></span>--%>

									   <br/>

									   <div class="row" style="margin-bottom:10px;">
										   <div class="col-sm-6 text-right">
											   <customtag:authtag authUrl="house/houseMgt/rejectHouseField" ><button id="RejectFieldButton" type="button" class="btn btn-success btn-sm">驳回</button></customtag:authtag>
										   </div>
										   <div class="col-sm-6">
											   <customtag:authtag authUrl="house/houseMgt/rejectHouseField" ><button type="button" class="btn btn-danger btn-sm"
																											onclick="closeWindow(1)">取消</button></customtag:authtag>
										   </div>
									   </div>
								   </div>
							   </div>
						   </div>
					   </div>
				   </div>
			   </div>
		   </div>
	   </div>


	   <!--房源照片审核驳回弹出框 -->
	   <div class="modal inmodal fade" id="housePicReject"
			tabindex="-1" role="dialog" aria-hidden="true">
		   <div class="modal-dialog modal-lm">
			   <div class="modal-content">
				   <div style="padding:5px 10px 0;">
					   <button type="button" class="close" data-dismiss="modal">
						   <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					   </button>
				   </div>

				   <div class="ibox-content" style="border:none;padding-bottom:0;">
					   <div class="row row-lg">
						   <div class="col-sm-12">
							   <div class="example-wrap">
								   <div class="example">

									   <form id="remarkPicform" class="form-group">
										   <div class="row">
											   <div class="col-sm-12">
												   <label class="col-sm-2 control-label left2">驳回原因：</label>
												   <div class="col-sm-8 left2">
													   <textarea id="picRemark" name="picRemark" class="form-control" rows="3"></textarea>
												   </div>
											   </div>
										   </div>
									   </form>
									   <%--<span><font color="red">例:</font>&nbsp;&nbsp;<font color="#da70d6">房源图片：照片不清晰</font></span>--%>

									   <br/>

									   <div class="row" style="margin-bottom:10px;">
										   <div class="col-sm-6 text-right">
											   <customtag:authtag authUrl="house/houseMgt/rejectHousePic" ><button id="RejectPicButton" type="button" class="btn btn-success btn-sm">驳回</button></customtag:authtag>
										   </div>
										   <div class="col-sm-6">
											   <customtag:authtag authUrl="house/houseMgt/rejectHousePic" ><button type="button" class="btn btn-danger btn-sm"
																													 onclick="closeWindow(2)">取消</button></customtag:authtag>
										   </div>
									   </div>
								   </div>
							   </div>
						   </div>
					   </div>
				   </div>
			   </div>
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
	   <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>

	   <script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>

	   <script type="text/javascript">
		   $(function(){
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
		   });  
		   
		    function historyPaginationParam(params) {
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
		   
		   //删除未审核图片
		   function delPic(picFid,serverUUID){
			   $("#"+picFid).remove();
			   $.ajax({
					url:"${basePath}house/houseMgt/delNoAuditHousePic",
					data:{"fid":picFid,"serverUUID":serverUUID},
					dataType:"json",
					type:"post",
					success:function(result) {
						if(result.code === 0){
							layer.alert("删除成功", {icon: 6,time: 2000, title:'提示'});
						}else{
							layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
						}
					},
					error:function(result){
						layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
					}
				});
		   }
		   
		   //图片审核
		   function auditHousePic(){
               $("#remarkMsg").val($("#remark").val());
			   $.ajax({
                   	beforeSend : function(){
                       return $("#remarkform").valid();
                   	},
					url:"${basePath}house/houseMgt/auditNoHousePic",
					data:$('#form').serialize(),
					dataType:"json",
					type:"post",
					success:function(result) {
						if(result.code === 0){
							$.callBackParent("house/houseMgt/listModifiedPic",true,approveHouseInfoCallback);
						}else{
							layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
						}
					},
					error:function(result){
						layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
					}
				});
		   }
			//审核房源刷新父页面数据
			function approveHouseInfoCallback(parent){
				parent.refreshData("listTable");
				parent.refreshData("listTableS");
			}

			//弹出驳回房源审核字段输入框
			function showRejectField(id,obj1,obj2,obj3) {
                $("#houseFieldReject").modal("toggle");
                $("#remarkFieldform").validate({
                    rules: {
                        fieldRemark: {
                            required: true,
                            maxlength: 100
                        }
                    }
                });
                $("#RejectFieldButton").unbind("click").bind("click",function(){
                    rejectField(id,obj1,obj2,obj3);
                });
                //下面的这个赋予button点击函数，会重复赋予button点击函数，将会导致点击函数重复调用,慎用@lusp
//                $("#RejectFieldButton").click(function(){
//                    rejectField(id,obj1,obj2,obj3);
//                });
                $("#fieldRemark").val('');
            }


           //弹出驳回房源审核照片输入框
           function showRejectPic(fid,obj,picType) {
               $("#housePicReject").modal("toggle");
               $("#remarkPicform").validate({
                   rules: {
                       picRemark: {
                           required: true,
                           maxlength: 100
                       }
                   }
               });
               $("#RejectPicButton").unbind("click").bind("click",function(){
                   rejectPic(fid,obj,picType);
               });
               //下面的这个赋予button点击函数，会重复赋予button点击函数，将会导致点击函数重复调用,慎用@lusp
//               $("#RejectPicButton").click(function(){
//                   rejectPic(fid,obj);
//               });
               $("#picRemark").val('');
           }

           //关闭窗口
           function closeWindow(id) {
			    if(id == 1){
                    $("#houseFieldReject").modal("hide");
				}else if(id == 2){
                    $("#housePicReject").modal("hide");
				}
           }


		   //id:字段审核表（t_house_update_field_audit_newlog）id或图片id
		   //obj1:字段新值input元素    obj2:字段老值span元素     obj3：审核驳回后需要隐藏的div元素
		   //obj1.parent().prev().find("label:first-child").text() 来获取属性名称，例如"房源名称："
		   function rejectField(id,obj1,obj2,obj3) {
			   $.ajax({
                   beforeSend : function(){
                       return $("#remarkFieldform").valid();
                   },
				   url:"${basePath}house/houseMgt/rejectHouseField",
				   data:{id:id},
				   dataType:"json",
				   type:"post",
				   success:function (result) {
					   if(result.code === 0){
						   layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
						   if(obj1!=null&&obj2!=null){
							   obj1.val(obj2.text());
						   }
						   obj3.css({
							   visibility: 'hidden',
						   });
						   if(obj1==null||obj1==''){
                               var fieldName = "封面照片： ";
                           }else {
                               var fieldName = obj1.parent().prev().find("label:first-child").text();
                           }
                           if($("#remark").val()==null || $("#remark").val()==''){
                               $("#remark").val(fieldName+" "+$("#fieldRemark").val());
                           }else{
                               $("#remark").val($("#remark").val()+" ; "+fieldName+" "+$("#fieldRemark").val());
                           }
                           $("#remark").removeAttr('disabled');
						   $("#remarkform").validate({
							   rules: {
								   remark: {
									   required: true,
									   maxlength: 200
								   }
							   }
						   });
                           $("#houseFieldReject").modal("hide");
					   }else{
						   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
					   }
				   },
				   error:function(result){
					   layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
				   }
			   });
           }

           //fid : 照片fid    obj:成功后需要隐藏的元素
           function rejectPic(fid,obj,picType) {
               $.ajax({
                   beforeSend : function(){
                       return $("#remarkPicform").valid();
                   },
                   url:"${basePath}house/houseMgt/rejectHousePic",
                   data:{fid:fid},
                   dataType:"json",
                   type:"post",
                   success:function (result) {
                       if(result.code === 0){
                           layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
                           obj.remove();
                           /*obj.css({
                               display: 'none',
                           });*/
//                           var fieldName = obj.parent().parent().parent().find("label:first-child").text();
                           switch(picType){
                               case '0':
                                   var picDesc = '卧室照片: ';
                                   break;
                               case '1':
                                   var picDesc = '客厅照片: ';
                                   break;
							   case '2':
                                   var picDesc = '厨房照片: ';
                                   break;
                               case '3':
                                   var picDesc = '卫生间照片: ';
                                   break;
                               case '4':
                                   var picDesc = '室外照片: ';
                                   break;
                               default:
                                   var picDesc = '房源照片: ';
                           }

                           if($("#remark").val()==null || $("#remark").val()==''){
                               $("#remark").val(picDesc+$("#picRemark").val());
                           }else{
                               $("#remark").val($("#remark").val()+" ; "+picDesc+$("#picRemark").val());
                           }
                           $("#remark").removeAttr('disabled');
                           $("#remarkform").validate({
                               rules: {
                                   remark: {
                                       required: true,
                                       maxlength: 200
                                   }
                               }
                           });
                           $("#housePicReject").modal("hide");
                       }else{
                           layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
                       }
                   },
                   error:function(result){
                       layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
                   }
               });
           }
           
	       //上传房源图片
	       function uploadHousePic(houseBaseFid){
			   $.openNewTab(new Date().getTime(),'house/houseMgt/housePicAuditDetail?houseBaseFid='+houseBaseFid, "上传房源照片");
	       }
		    
		   //上传房间图片
		   function uploadRoomPic(houseRoomFid){
			   $.openNewTab(new Date().getTime(),'house/houseMgt/housePicAuditDetail?houseRoomFid='+houseRoomFid, "上传房间照片");
		   }
	   </script>
	</body>
</html>
