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
        .room-pic{float:left;}
        .room-pic p{text-align:center;}
        .blueimp-gallery>.title{left:0;bottom:45px;top:auto;width:100%;text-align:center;}
        .house-desc-color{position:absolute;right:20px; bottom:6px;color:cc3366;font-size:6px}
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
	                               <div class="col-sm-1" style="color: red;">
				                   	   <label class="control-label">房东是否审核通过:</label>
	                               </div>
	                               <div class="col-sm-2" style="color: red;">
		                               <c:if test="${landlord.auditStatus==1}">
			                              <input id="auditStatus" name="auditStatus" value="审核通过" readonly="readonly" type="text" class="form-control m-b">
		                               </c:if>
		                               <c:if test="${landlord.auditStatus==0}">
			                              <input id="auditStatus" name="auditStatus" value="未审核" readonly="readonly" type="text" class="form-control m-b">
		                               </c:if>
		                               <c:if test="${landlord.auditStatus==2}">
			                              <input id="auditStatus" name="auditStatus" value="审核未通过" readonly="readonly" type="text" class="form-control m-b">
		                               </c:if>
		                                <c:if test="${landlord.auditStatus==4}">
			                              <input id="auditStatus" name="auditStatus" value="已认证信息修改待审核" readonly="readonly" type="text" class="form-control m-b">
		                               </c:if>
                                   </div>
	                               <c:if test="${auditFlag == 1 && (landlord.auditStatus == 0 || landlord.auditStatus == 2)}">
		                               <customtag:authtag authUrl="customer/listCheckCustomerMsg">
				                           <div class="col-sm-2">
			                                   <button class="btn btn-primary" type="button" onclick="toAudit('${landlord.auditStatus }');"><i class="fa fa-link"></i>&nbsp;审核房东</button>
			                               </div>
		                               </customtag:authtag>
	                               </c:if>
		                       </div>
		                       
	                           <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">房源基本情况</h2>

							  <div class="row">
								  <c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.houseAddr}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">房源地址:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2">
											  <label class="control-label">
												  <span><font color="red">${oldHouseBaseMsg.houseAddr}</font></span>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.houseName}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">房源名称:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2">
											  <label class="control-label">
												  <span><font color="red">${oldHouseBaseMsg.houseName}</font></span>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.roomNum || not empty oldHouseBaseMsg.hallNum || not empty oldHouseBaseMsg.toiletNum || not empty oldHouseBaseMsg.kitchenNum || not empty oldHouseBaseMsg.balconyNum}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red" class="sensitiv-words-validate">房源户型:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2">
											  <label class="control-label">
												  <span><font color="red">${not empty oldHouseBaseMsg.roomNum ? oldHouseBaseMsg.roomNum : houseBaseMsg.roomNum}室${not empty oldHouseBaseMsg.hallNum ? oldHouseBaseMsg.hallNum : houseBaseMsg.hallNum}厅${not empty oldHouseBaseMsg.toiletNum ? oldHouseBaseMsg.toiletNum : houseBaseMsg.toiletNum}卫${not empty oldHouseBaseMsg.kitchenNum ? oldHouseBaseMsg.kitchenNum : houseBaseMsg.kitchenNum}厨${not empty oldHouseBaseMsg.balconyNum ? oldHouseBaseMsg.balconyNum : houseBaseMsg.balconyNum}阳台</font></span>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.houseArea}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">房源面积:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2">
											  <label class="control-label">
												  <span><font color="red">${oldHouseBaseMsg.houseArea}</font></span>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
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
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源名称:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="houseName" name="houseName" title='<c:out value="${houseBaseMsg.houseName}" escapeXml="true"/>' value='<c:out value="${houseBaseMsg.houseName}" escapeXml="true"/>' readonly="readonly" type="text" class="form-control m-b sensitiv-words-validate">
	                               </div>
				                   <label class="col-sm-1 control-label">房源户型:</label>
		                           <div class="col-sm-2">
	                                   <input id="houseModel" name="houseModel" value="${houseBaseMsg.roomNum }室${houseBaseMsg.hallNum }厅${houseBaseMsg.toiletNum }卫${houseBaseMsg.kitchenNum }厨${houseBaseMsg.balconyNum }阳台" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源面积:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="houseArea" name="houseArea" value="${houseBaseMsg.houseArea }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                       </div>

							  <div class="row">
								  <c:choose>
									  <c:when test="${not empty oldHousePhyMsg.longitude || not empty oldHousePhyMsg.latitude}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">房源坐标:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2">
											  <label class="control-label">
												  <span><font color="red">${oldHousePhyMsg.longitude },${oldHousePhyMsg.latitude }</font></span>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.houseType}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">房源类型:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2">
											  <label class="control-label">
												  <select class="form-control m-b" disabled="disabled">
													  <c:forEach items="${houseTypeList }" var="obj">
														  <option value="${obj.key }" <c:if test="${obj.key == oldHouseBaseMsg.houseType }">selected</c:if>>${obj.text }</option>
													  </c:forEach>
												  </select>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <%--<c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.roomNum || not empty oldHouseBaseMsg.hallNum || not empty oldHouseBaseMsg.toiletNum || not empty oldHouseBaseMsg.kitchenNum || not empty oldHouseBaseMsg.balconyNum}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">房源户型:</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2">
											  <label class="control-label">
												  <span><font color="red">${oldHouseBaseMsg.roomNum }室${oldHouseBaseMsg.hallNum }厅${oldHouseBaseMsg.toiletNum }卫${oldHouseBaseMsg.kitchenNum }厨${oldHouseBaseMsg.balconyNum }阳台</font></span>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.houseArea}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">房源面积:</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2">
											  <label class="control-label">
												  <span><font color="red">${oldHouseBaseMsg.houseArea}</font></span>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>--%>
							  </div>

		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源坐标:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="houseLocation" name="houseLocation" value="${housePhyMsg.longitude },${housePhyMsg.latitude }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
				                   <label class="col-sm-1 control-label">房源类型:</label>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b" id="houseType" disabled="disabled">
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
	                                 	  <input id="roomSn" name="roomSn" value="${roomList[0].roomSn}" readonly="readonly" type="text" class="form-control m-b">
		                               </c:if>
	                               </div>
	                               <c:if test="${houseBaseMsg.rentWay == 0}">
				                   		<label class="col-sm-1 control-label">清洁费:</label>
				                   		<div class="col-sm-2">
		                                   <input id="houseCleaningFees" name="houseCleaningFees" value="${(houseBaseMsg.houseCleaningFees)/100}" readonly="readonly" type="text" class="form-control m-b">
	                               		</div>
	                               </c:if>
		                       </div>
		                       <div class="row">
	                               <div class="col-sm-1">
				                   	   <label class="control-label mtop">楼号:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input name="houseExt.buildingNum" value="${houseBaseExt.buildingNum }"  readonly="readonly" type="text" class="form-control m-b"  >
	                               </div>
				                   <label class="col-sm-1 control-label mtop">单元号:</label>
		                           <div class="col-sm-1">
	                                   <input name="houseExt.unitNum" value="${houseBaseExt.unitNum }"  readonly="readonly" type="text" class="form-control m-b" >
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">楼层:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input  name="houseExt.floorNum" value="${houseBaseExt.floorNum }"   readonly="readonly" type="text" class="form-control m-b" >
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">门牌号:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input  name="houseExt.houseNum" value="${houseBaseExt.houseNum }"   readonly="readonly" type="text" class="form-control m-b" >
	                               </div>
		                       </div>
		                       
                               <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">租住描述</h2>
		                       <%-- <div class="row">
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
				                   <label class="col-sm-1 control-label">被单更换:</label>
		                           <div class="col-sm-2">
		                           	   <select class="form-control m-b" id="sheetsReplaceRules" disabled="disabled">
		                           	   	  <c:forEach items="${sheetReplaceList }" var="obj">
			                                  <option value="${obj.key }" <c:if test="${obj.key == houseBaseExt.sheetsReplaceRules}">selected</c:if>>${obj.text }</option>
		                                  </c:forEach>
	                                   </select>
	                               </div> 
		                       </div> --%>

							  <div class="row">
								  <c:choose>
									  <c:when test="${not empty oldHouseBaseExt.checkInLimit}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">入住人数限制: （修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2">
											  <c:choose>
												  <c:when test="${oldHouseBaseExt.checkInLimit == 0 }">
													  <label class="control-label">
														  <span><font color="red">不限制</font></span>
													  </label>
												  </c:when>
												  <c:otherwise>
													  <label class="control-label">
														  <span><font color="red">${oldHouseBaseExt.checkInLimit}</font></span>
													  </label>
												  </c:otherwise>
											  </c:choose>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseExt.isTogetherLandlord}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">是否与房东同住: （修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2">
											  <c:if test="${oldHouseBaseExt.isTogetherLandlord == 0 || empty oldHouseBaseExt.isTogetherLandlord}">
												  <label class="control-label">
													  <span><font color="red">否</font></span>
												  </label>
											  </c:if>
											  <c:if test="${oldHouseBaseExt.isTogetherLandlord == 1 }">
												  <label class="control-label">
													  <span><font color="red">是</font></span>
												  </label>
											  </c:if>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseExt.minDay}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">最少入住天数: （修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2">
											  <label class="control-label">
												  <span><font color="red">${oldHouseBaseExt.minDay}</font></span>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
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
		                           <div class="col-sm-1">
				                   	   <label class="control-label">最少入住天数:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="minDay" name="minDay" value="${houseBaseExt.minDay }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       <div class="row">
		                           <%-- <div class="col-sm-1">
				                   	   <label class="control-label">可预订截止时间:</label>
	                               </div> 
		                           <div class="col-sm-2">
	                                   <input id="tillDate" name="tillDate" value='<fmt:formatDate value="${houseBaseMsg.tillDate }" pattern="yyyy-MM-dd"/>' readonly="readonly" type="text" class="form-control m-b">
	                               </div>  --%>
				                   <label class="col-sm-1 control-label">入住时间:</label>
		                           <div class="col-sm-2">
	                                   <input id="checkInTime" name="checkInTime" value="${houseBaseExt.checkInTime }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
				                   <label class="col-sm-1 control-label">退房时间:</label>
		                           <div class="col-sm-2">
	                                   <input id="checkOutTime" name="checkOutTime" value="${houseBaseExt.checkOutTime }" readonly="readonly" type="text" class="form-control m-b">
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
		                       </div>
		                       
                               <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">房源描述</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">小区名称:</label>
	                               </div>
		                           <div class="col-sm-5">
	                                   <input id="communityName" name="communityName" title="${housePhyMsg.communityName}" value="${housePhyMsg.communityName}" readonly="readonly" type="text" class="form-control m-b sensitiv-words-validate">
	                               </div>
		                       </div>

							  <div class="row">
								  <c:choose>
									  <c:when test="${not empty oldHouseDesc.houseDesc}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">房源描述:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-5">
											  <label class="control-label">
												  <span><font color="red">${oldHouseDesc.houseDesc}</font></span>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-5" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>

								  <c:choose>
									  <c:when test="${not empty oldHouseDesc.houseAroundDesc}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">周边状况:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-5">
											  <label class="control-label">
												  <span><font color="red">${oldHouseDesc.houseAroundDesc}</font></span>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-5" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>
							  </div>

		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源描述:</label>
	                               </div>
		                           <div class="col-sm-5">
	                                   <textarea rows="8" id="houseDesc" name="houseDesc"  readonly="readonly"  class="form-control m-b sensitiv-words-validate">${houseDesc.houseDesc }
	                                   </textarea>
	                                   <p class="house-desc-color">字数：${houseDescNum }</p>
	                               </div>
	                               
		                           <div class="col-sm-1">
				                   	   <label class="control-label">周边状况:</label>
	                               </div>
		                           <div class="col-sm-5">
	                                   <textarea rows="8" id="houseAroundDesc" name="houseAroundDesc"  readonly="readonly"  class="form-control m-b sensitiv-words-validate">${houseDesc.houseAroundDesc }</textarea>
	                                     <p class="house-desc-color">字数：${houseAroundDescNum }</p>
	                               </div>
		                       </div>

							  <div class="row">
								  <c:choose>
									  <c:when test="${not empty oldHouseDesc.houseRules}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">房屋守则:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-5">
											  <label class="control-label">
												  <span><font color="red">${oldHouseDesc.houseRules}</font></span>
											  </label>
										  </div>
									  </c:when>
							          <c:when test="${empty oldHouseDesc.houseRules && not empty houseDesc.houseRules}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">房屋守则:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-5">
											  <label class="control-label">
												  <span><font color="red">未填写</font></span>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-5" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>
							  </div>

		                       <div class="row">
		                       	   <div class="col-sm-1">
				                   	   <label class="control-label">房屋守则:</label>
	                               </div>
		                           <div class="col-sm-5">
	                                   <textarea rows="8"  class="form-control m-b" readonly="readonly">${houseDesc.houseRules }</textarea>
	                                     <p class="house-desc-color sensitiv-words-validate">字数：${houseRulesNum }</p>
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
				                   	   <label class="control-label">今日特惠折扣</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="ProductRulesEnum020001" name="ProductRulesEnum020001" value="${ProductRulesEnum020001 }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">一天夹心折扣</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="ProductRulesEnum020002" name="ProductRulesEnum020002" value="${ProductRulesEnum020002 }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">二天夹心折扣</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="ProductRulesEnum020003" name="ProductRulesEnum020003" value="${ProductRulesEnum020003 }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>

								   <div class="col-sm-1">
									   <label class="control-label">七天长租折扣率</label>
								   </div>
								   <div class="col-sm-2">
									   <input id="ProductRulesEnum0019001" name="ProductRulesEnum0019001" value="${ProductRulesEnum0019001 }" readonly="readonly" type="text" class="form-control m-b">
								   </div>
							   </div>

							  <div class="row">

								  <div class="col-sm-1" style="visibility: hidden">
								  </div>
								  <div class="col-sm-2" style="visibility: hidden">
								  </div>

								  <c:choose>
									  <c:when test="${not empty oldHouseBaseMsg.leasePrice}">
										  <div class="col-sm-1">
											  <label class="control-label">
												  <span><font color="red">价格:（修改前）</font></span>
											  </label>
										  </div>
										  <div class="col-sm-2">
											  <label class="control-label">
												  <span><font color="red">${oldHouseBaseMsg.leasePrice/100}</font></span>
											  </label>
										  </div>
									  </c:when>
									  <c:otherwise>
										  <div class="col-sm-1" style="visibility: hidden">
										  </div>
										  <div class="col-sm-2" style="visibility: hidden">
										  </div>
									  </c:otherwise>
								  </c:choose>
							  </div>

							  <div class="row">

								   <div class="col-sm-1">
									   <label class="control-label">30天长租折扣率</label>
								   </div>
								   <div class="col-sm-2">
									   <input id="ProductRulesEnum0019002" name="ProductRulesEnum0019002" value="${ProductRulesEnum0019002 }" readonly="readonly" type="text" class="form-control m-b">
								   </div>

		                           <div class="col-sm-1">
				                   	   <label class="control-label">价格:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="leasePrice" name="leasePrice" value="${houseBaseMsg.leasePrice/100 }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
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
											   <div class="col-sm-1">
												   <label class="control-label">
													   <span><font color="red">房间名称:（修改前）</font></span>
												   </label>
											   </div>
											   <div class="col-sm-2">
												   <label class="control-label">
													   <span><font color="red">${oldHouseRoomMsg.roomName}</font></span>
												   </label>
											   </div>
										   </c:when>
										   <c:otherwise>
											   <div class="col-sm-1" style="visibility: hidden">
											   </div>
											   <div class="col-sm-2" style="visibility: hidden">
											   </div>
										   </c:otherwise>
									   </c:choose>

									   <c:choose>
										   <c:when test="${not empty oldHouseRoomMsg.roomArea}">
											   <div class="col-sm-1">
												   <label class="control-label">
													   <span><font color="red">房间面积:（修改前）</font></span>
												   </label>
											   </div>
											   <div class="col-sm-2">
												   <label class="control-label">
													   <span><font color="red">${oldHouseRoomMsg.roomArea}</font></span>
												   </label>
											   </div>
										   </c:when>
										   <c:otherwise>
											   <div class="col-sm-1" style="visibility: hidden">
											   </div>
											   <div class="col-sm-2" style="visibility: hidden">
											   </div>
										   </c:otherwise>
									   </c:choose>

									   <c:choose>
										   <c:when test="${not empty oldHouseRoomMsg.roomPrice}">
											   <div class="col-sm-1">
												   <label class="control-label">
													   <span><font color="red">房间价格:（修改前）</font></span>
												   </label>
											   </div>
											   <div class="col-sm-2">
												   <label class="control-label">
													   <span><font color="red">${oldHouseRoomMsg.roomPrice/100}</font></span>
												   </label>
											   </div>
										   </c:when>
										   <c:otherwise>
											   <div class="col-sm-1" style="visibility: hidden">
											   </div>
											   <div class="col-sm-2" style="visibility: hidden">
											   </div>
										   </c:otherwise>
									   </c:choose>
								   </div>

			                       <div class="row">
			                           <div class="col-sm-1">
					                   	   <label class="control-label">房间名称:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <input id="roomName" name="roomName" title="<c:out value="${room.roomName}" escapeXml="true"/>" value="<c:out value="${room.roomName}" escapeXml="true"/>" readonly="readonly" type="text" class="form-control m-b">
		                               </div>
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
		                                   <input id=roomPrice name="roomPrice" value="${room.roomPrice/100}" readonly="readonly" type="text" class="form-control m-b">
		                               </div>
			                       </div>
			                       <div class="row">
			                          <div class="col-sm-1">
					                   	   <label class="control-label">房间类型:</label>
		                               </div>
			                           <div class="col-sm-2">
					                       <c:if test="${room.roomType == 0 || empty room.roomType}">
			                                  <input id="roomType" name="roomType" value="房间" readonly="readonly" type="text" class="form-control m-b">
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
		                       </c:forEach>
		                       <div class="row">
		                       	   <div class="col-sm-1" style="color: red;">
				                   	   <label class="control-label">审核未通过次数:</label>
	                               </div>
	                               <div class="col-sm-2" style="color: red;">
	                               		<input id="findHouseAuditNoLogTime" name="findHouseAuditNoLogTime" value="${findHouseAuditNoLogTime}次" readonly="readonly"  type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       <div class="hr-line-dashed"></div>
		                       <div class="row">
		                       	   <c:if test="${houseBaseMsg.rentWay == 1}">
				                   		<label class="col-sm-1 control-label">清洁费:</label>
				                   		<div class="col-sm-2">
		                                   <input id="houseCleaningFees" name="houseCleaningFees" value="${(roomList[0].roomCleaningFees)/100}" readonly="readonly" type="text" class="form-control m-b">
	                               		</div>
	                               </c:if>
		                       </div>
		                       <c:choose>
							   <c:when test="${auditFlag ==1 || auditModifiedFlag==1}">
							      <h2 class="title-font">
							       <c:if test="${houseBaseMsg.rentWay == 0 }">
							        <a href="javascript:uploadHousePic('${houseBaseMsg.fid}')">照片信息</a>
							       </c:if>
							        <c:if test="${houseBaseMsg.rentWay == 1 }">
							        <a href="javascript:uploadRoomPic('${houseFid}')">照片信息</a>
							       </c:if>
							       
							      </h2>
							   </c:when>
							   
							   <c:otherwise>
							     <h2 class="title-font">照片信息</h2>
							   </c:otherwise>
							  </c:choose>
		                       
		                      <%-- <div class="row">
		                       	   <div class="form-group">
		                            	<label class="col-sm-1 control-label">房源照片</label>
		                                <div class="col-sm-9">
			                                <div class="lightBoxGallery">
				                                <c:forEach items="${housePicList }" var="pic">
				                                	<a href="${picBaseAddr }${pic.picBaseUrl }${pic.picSuffix}" title="图片" data-gallery="blueimp-gallery${status.index }"><img src="${picBaseAddr }${pic.picBaseUrl }${pic.picSuffix}" style="height: 135px; width: 165px;"></a>
				                                </c:forEach>
					                        </div>
		                                </div>
		                           </div>
                               </div> --%>
                               <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">当前预约摄影师状态:</label>
	                               </div>
	                               <div class="col-sm-2 ">
										<c:choose>
										<c:when test="${bookOrderStatuResult == null}">
											<input id="bookOrderStatu" name="bookOrderStatu" value="无预约订单" readonly="readonly"  type="text" class="form-control m-b">
											
										</c:when>
										<c:otherwise>
											<c:forEach items="${bookOrderStatusMap}" var="obj">
												<c:if test="${obj.key == bookOrderStatuResult }">
													<input id="bookOrderStatu" name="bookOrderStatu" value="${obj.value}" readonly="readonly"  type="text" class="form-control m-b">
												</c:if>
											</c:forEach> 
										</c:otherwise>
										</c:choose>
								   </div> 
	                           </div>
                               <c:forEach items="${housePicAuditVo.roomPicList }" var="room" varStatus="status">
                            	<div class="row">
		                            <div class="form-group">
		                                <label class="col-sm-2 control-label">房间名称</label>
		                                <div class="col-sm-2">
		                                   <span>${room.picTypeName }</span>
		                                </div>
		                            </div>
	                            </div>
	                            <div class="row">
		                            <div class="form-group">
		                            	<label class="col-sm-2 control-label">房间图片</label>
		                                <div class="col-sm-9">
			                                <div class="lightBoxGallery">
			                                	<div id="roomPicBox${room.roomFid }" class="clearfix">
					                                <c:forEach items="${room.picList }" var="pic">
						                                <a class="room-pic" href="${picBaseAddrMona}${pic.picBaseUrl }${pic.picSuffix}${picSize}.jpg" 
						                                <c:if test="${!empty pic.widthPixel and !empty pic.heightPixel and !empty pic.picSize and !empty pic.widthDpi and !empty pic.heightDpi }">title="${pic.widthPixel }*${pic.heightPixel } W:${pic.widthDpi }DPI H:${pic.heightDpi }DPI ${pic.picSize }KB"</c:if> 
						                                data-gallery="blueimp-gallery${room.roomFid }">
						                                	<img src="${picBaseAddrMona}${pic.picBaseUrl }${pic.picSuffix}${picSize}.jpg" style="height: 135px; width: 165px;">
						                                	<c:if test="${!empty pic.widthPixel and !empty pic.heightPixel and !empty pic.picSize }">
						                                		<p>${pic.widthPixel }*${pic.heightPixel } ${pic.picSize }KB</p>
						                                	</c:if>
						                                </a>
					                                </c:forEach>
				                                </div>
					                        </div>
		                                </div>
		                            </div>
	                            </div>
                               </c:forEach>
                               <c:forEach items="${housePicAuditVo.housePicList }"  var="house" varStatus="status">
                            	<div class="row">
		                            <div class="form-group">
		                            	<label class="col-sm-2 control-label">${house.picTypeName }</label>
		                                <div class="col-sm-9">
			                                <div class="lightBoxGallery">
			                                	<div id="picTypeBox${house.picType }" class="clearfix">
					                                <c:forEach items="${house.picList }" var="pic">
				                                		<a class="room-pic" href="${picBaseAddrMona}${pic.picBaseUrl }${pic.picSuffix}${picSize}.jpg" 
						                                <c:if test="${!empty pic.widthPixel and !empty pic.heightPixel and !empty pic.picSize and !empty pic.widthDpi and !empty pic.heightDpi }">title="${pic.widthPixel }*${pic.heightPixel } W:${pic.widthDpi }DPI H:${pic.heightDpi }DPI ${pic.picSize }KB"</c:if> 
				                                		data-gallery="blueimp-gallery${house.picType }">
				                                			<img src="${picBaseAddrMona}${pic.picBaseUrl }${pic.picSuffix}${picSize}.jpg" style="height: 135px; width: 165px;">
						                                	<c:if test="${!empty pic.widthPixel and !empty pic.heightPixel and !empty pic.picSize }">
						                                		<p>${pic.widthPixel }*${pic.heightPixel } ${pic.picSize }KB</p>
						                                	</c:if>
				                                		</a>
					                                </c:forEach>
				                                </div>
					                        </div>
		                                </div>
		                            </div>
	                            </div>
                               </c:forEach>

							  <c:if test="${not empty oldHouseDefaultPicMsg && not empty newHouseDefaultPicMsg}">
								  <div class="row">
									  <label class="col-sm-1 control-label">默认照片</label>
									  <div class="col-sm-3">
										  <div class="lightBoxGallery">
											  <input id="defaultPicReject" value="${defaultPicFid_auditLogId}" style="display: none"/>
											  <span id="${oldHouseDefaultPicMsg.fid }">
													<a href="${picBaseAddr }${oldHouseDefaultPicMsg.picBaseUrl }${oldHouseDefaultPicMsg.picSuffix}" title="图片" data-gallery="blueimp-gallery${status.index }"><img src="${picBaseAddr }${oldHouseDefaultPicMsg.picBaseUrl }${oldHouseDefaultPicMsg.picSuffix}" style="height: 135px; width: 165px;"></a>
												</span>
										  </div>
									  </div>
									  <div class="col-sm-3 defaultPicMsg">
										  <div class="lightBoxGallery">
												<span id="${newHouseDefaultPicMsg.fid }">
													<a href="${picBaseAddr }${newHouseDefaultPicMsg.picBaseUrl }${newHouseDefaultPicMsg.picSuffix}" title="图片" data-gallery="blueimp-gallery${status.index }"><img src="${picBaseAddr }${newHouseDefaultPicMsg.picBaseUrl }${newHouseDefaultPicMsg.picSuffix}" style="height: 135px; width: 165px;"></a>
													<%--<button id="defaultPicId" type="button" class="btn btn-danger btn-xs" onclick="rejectField($('#defaultPicReject').val(),null,null,$('.defaultPicMsg'))">驳回</button>--%>
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
		                               <div class="col-sm-1">
					                   	   <label class="control-label">房源渠道:</label>
		                               </div>
			                           <div class="col-sm-2">
			                           	   <c:if test="${!empty houseBaseMsg.houseChannel && houseBaseMsg.houseChannel==3}"> 
			                           	   		<input  value="地推" readonly="readonly"  type="text" class="form-control m-b">
			                           	   </c:if>		
			                           	   <c:if test="${empty houseBaseMsg.houseChannel || houseBaseMsg.houseChannel!=3}"> 
				                           	   <select class="form-control m-b" id="houseChannel" >
				                           	   	  <option value="">请选择</option>
				                                   <c:forEach items="${houseChannel }" var="obj">
					                                   <option value="${obj.key }" <c:if test="${(empty houseBaseMsg.houseChannel && obj.key == 2) || obj.key == houseBaseMsg.houseChannel }">selected</c:if> >${obj.value }</option>
				                                   </c:forEach>
			                                   </select>
			                           	   </c:if>
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
		                                <div class="col-sm-1">
					                   	   <label class="control-label">房源渠道:</label>
		                               </div>
			                           <div class="col-sm-2">
			                           	   <c:if test="${!empty houseBaseMsg.houseChannel && houseBaseMsg.houseChannel==3 }"> 
			                           	   		<input id="houseChannel"  value="地推" readonly="readonly"  type="text" class="form-control m-b">
			                           	   </c:if>		
			                           	   <c:if test="${empty houseBaseMsg.houseChannel || houseBaseMsg.houseChannel!=3}"> 
				                           	   <select class="form-control m-b" id="houseChannel" >
				                           	   	  <option value="">请选择</option>
				                                   <c:forEach items="${houseChannel }" var="obj">
					                                   <option value="${obj.key }" <c:if test="${(empty houseBaseMsg.houseChannel && obj.key == 2) || obj.key == houseBaseMsg.houseChannel }">selected</c:if> >${obj.value }</option>
				                                   </c:forEach>
			                                   </select>
			                           	   </c:if>
		                               </div>
	                               </div>
                               </c:if>
							   <c:if test="${isShow ne false}">
							  		<div class="row">
									  <div class="col-sm-1">
										  <label class="control-label">房源品质等级:</label>
									  </div>
									  <div class="col-sm-2">
										  <select class="form-control m-b" id="houseQualityGrade">
											  <option value="">请选择</option>
											  <option value="TOP">塔尖</option>
											  <option value="A">A级</option>
											  <option value="B-">B-级</option>
											  <option value="B">B级</option>
											  <option value="B+">B+级</option>
											  <option value="C">C级</option>
										  </select>
									  </div>
								  </div>
							   </c:if>
                                  <div class="hr-line-dashed"></div>
			                       <h2 class="title-font">运营专员信息</h2>
			                       <%-- <div class="row">
			                           <div class="col-sm-1">
					                   	   <label class="control-label">地推管家编号:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <input  name="empPushCode" value="${houseGuardRel.empPushCode }" readonly="readonly"  type="text" class="form-control m-b">
		                               </div>
		                                 <div class="col-sm-1">
					                   	   <label class="control-label">地推管家姓名:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <input  name="empPushName" value="${houseGuardRel.empPushName }" readonly="readonly"  type="text" class="form-control m-b">
		                               </div>
	                               </div> --%>
	                               <div class="row">
			                           <div class="col-sm-1">
					                   	   <label class="control-label">运营专员编号:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <input  name="empGuardCode" value="${houseGuardRel.empGuardCode }" readonly="readonly"  type="text" class="form-control m-b">
		                               </div>
		                                 <div class="col-sm-1">
					                   	   <label class="control-label">运营专员姓名:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <input  name="empGuardName" value="${houseGuardRel.empGuardName }" readonly="readonly"  type="text" class="form-control m-b">
		                               </div>
	                               </div>
                               
							   <c:if test="${houseSurveyMsg.recordStatus == 1 }">
		                           <div class="hr-line-dashed"></div>
			                       <h2 class="title-font">实勘信息</h2>
			                       <div class="row">
			                           <div class="col-sm-1">
					                   	   <label class="control-label">实勘编号:</label>
		                               </div>
			                           <div class="col-sm-2">
		                                   <input name="surveySn" value="${houseSurveyMsg.surveySn }" readonly="readonly"  type="text" class="form-control m-b">
		                               </div>
		                               <c:choose>
		                                   <c:when test="${auditFlag == 1 && houseSurveyMsg.isAudit == 0}">
				                               <customtag:authtag authUrl="house/houseSurvey/auditHouseSurvey">
						                           <div class="col-sm-2">
					                                   <button class="btn btn-primary" type="button" onclick="toAuditHouseSurvey('${houseSurveyMsg.houseBaseFid }');"><i class="fa fa-link"></i>&nbsp;审阅</button>
					                               </div>
				                               </customtag:authtag>
		                                   </c:when>
		                                   <c:otherwise>
				                               <customtag:authtag authUrl="house/houseSurvey/surveyDetail">
						                           <div class="col-sm-2">
					                                   <button class="btn btn-primary" type="button" onclick="toSurveyDetail('${houseSurveyMsg.houseBaseFid }');"><i class="fa fa-link"></i>&nbsp;查看详情</button>
					                               </div>
				                               </customtag:authtag>
		                                   </c:otherwise>
		                               </c:choose>
		                           </div>
			                   </c:if> 
			                   
							   <c:if test="${isShow ne false}">
		                           <div class="hr-line-dashed"></div>
			                       <h2 class="title-font">补充信息</h2>
			                       <div class="row">
			                       	   <div class="col-sm-1">
										   <label class="control-label">补充信息：</label>
		                               </div>
			                           <div class="col-sm-5">
			                           	   <!-- id不能命名为addtionalInfo会与管家审核页面补充信息字段冲突 -->
		                                   <textarea rows="8" name="addtionalInfo" readonly="readonly"  class="form-control m-b">${houseDesc.addtionalInfo }</textarea>
		                                    <p class="house-desc-color">字数：${addtionalInfoNum }</p>
		                               </div>
		                           </div>
							   </c:if>
	                           
							   <c:if test="${isDisplay }">
								   <div class="row">
									   <div class="form-group">
										   <div class="col-sm-4">
											   <customtag:authtag authUrl="house/houseMgt/showHouseOperateLogList">
											       <button type="button" class="btn btn-white" data-toggle="modal" data-target="#opLogModel">查看审核历史</button>
											   </customtag:authtag>
										   </div>
									   </div>
								   </div>
							   </c:if>

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
			             <div class="row">
			             	 <c:if test="${houseBaseMsg.rentWay == 0 }">
				                 <label class="col-sm-1 control-label m-b">房源编号:</label>	
			                     <div class="col-sm-2">
			                         <input type="text" class="form-control m-b" value="${houseBaseMsg.houseSn }"/>
			                     </div>
			                     <label class="col-sm-1 control-label m-b">房源名称:</label>	
			                     <div class="col-sm-4">
			                         <input type="text" class="form-control m-b" value="<c:out value="${houseBaseMsg.houseName}" escapeXml="true"/>"/>
			                     </div>
			             	 </c:if>
			             	 <c:if test="${houseBaseMsg.rentWay == 1 }">
				                 <label class="col-sm-1 control-label m-b">房间编号:</label>	
			                     <div class="col-sm-2">
			                         <input type="text" class="form-control m-b" value="${roomList[0].roomSn }"/>
			                     </div>
			                     <label class="col-sm-1 control-label m-b">房间名称:</label>	
			                     <div class="col-sm-4">
			                         <input type="text" class="form-control m-b" value="<c:out value="${roomList[0].roomName }" escapeXml="true"/>"/>
			                     </div>
			             	 </c:if>
			            </div>
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
									                  <th data-field="fromStatus" data-width="20%" data-align="center" data-formatter="formateHouseStatus">初始状态</th>
									                  <th data-field="toStatus" data-width="20%" data-align="center" data-formatter="formateHouseStatus">结束状态</th>
									                  <th data-field="auditCause" data-width="20%" data-align="center">审核说明</th>
									                  <th data-field="remark" data-width="20%" data-align="center">备注</th>
									                  <th data-field="createDate" data-width="20%" data-align="center" data-formatter="formateDate">操作日期</th>
									                  <th data-field="createName" data-width="20%" data-align="center">审核人</th>
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
	   <!-- 欧诺弥亚 -->
	   <input type="hidden" class="_access_token_" value="${access_token}">
	   <input type="hidden" class="_access_token_" value="${sensitiveUrl}">
	   <script src="${staticResourceUrl}/../eunomia/js/minsu/common/minsu.eunomia.validate.js"></script> 
	   <script type="text/javascript">
	   <!-- 欧诺弥亚 -->
	   $('.sensitiv-words-validate').sensitivWords({
           auto : true, 
           url: '${sensitiveUrl}'
       });
	   
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



			   chooseSelect("houseQualityGrade","${houseBaseExt.houseQualityGrade}");
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
			   var result = '';
			   $.each(houseStatusJson,function(i,n){
				   if(value == i){
					   result = n;
					   return false;
				   }
			   });
			   return result;
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

		   function chooseSelect(id,value){
			   if (!value){
				   value = "";
			   }
			   $("#"+id).find("option[value='"+value+"']").attr("selected",true);
		   }
		   
	       //上传房源图片
	       function uploadHousePic(houseBaseFid){
			   $.openNewTab(new Date().getTime(),'house/houseMgt/housePicAuditDetail?houseBaseFid='+houseBaseFid, "上传房源照片");
	       }
		    
		   //上传房间图片
		   function uploadRoomPic(houseRoomFid){
			   $.openNewTab(new Date().getTime(),'house/houseMgt/housePicAuditDetail?houseRoomFid='+houseRoomFid, "上传房间照片");
		   }
		   
		   //审核房东
		   function toAudit(auditStatus){
			   var landlordName = $("#landlordName").val();
			   var landlordMobile = $("#landlordMobile").val();
			   $.openNewTab(new Date().getTime(),'customer/listCheckCustomerMsg?auditStatus='
					   +auditStatus+'&realName='+landlordName+'&customerMobile='+landlordMobile, "客户信息审核");
		   }
		   
		   
		   function toSurveyDetail(houseBaseFid){
			   $.openNewTab(new Date().getTime(),'house/houseSurvey/surveyDetail?houseBaseFid='+houseBaseFid, "实勘详情");
		   }
		   
		   //审核房源实勘信息
		   function toAuditHouseSurvey(houseBaseFid){
			   $.openNewTab(new Date().getTime(),'house/houseSurvey/toAuditHouseSurvey?houseBaseFid='+houseBaseFid, "审阅实勘");
		   }



	   </script>
	</body>
</html>
