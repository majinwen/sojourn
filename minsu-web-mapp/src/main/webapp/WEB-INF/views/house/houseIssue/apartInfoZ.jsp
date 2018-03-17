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
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <title>房间信息</title>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/mui.picker.min.css${VERSION}002" />  
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/css_830.css${VERSION}001">
</head>
<body>
	<header class="header"> 
       	<c:if test="${flag==1 }">
       		<a href="${basePath}houseInput/43e881/goToHouseUpdate?houseBaseFid=${houseBaseMsg.fid}&rentWay=${houseBaseMsg.rentWay}&flag=${flag}&houseRoomFid=${houseRoomFid}" id="goback" class="goback" ></a>
       	</c:if>
       	<c:if test="${flag!=1 }">
           	<a href="${basePath}houseInput/43e881/findHouseDetail?houseBaseFid=${houseBaseMsg.fid}&rentWay=${houseBaseMsg.rentWay}" id="goback" class="goback" ></a>
       	</c:if>
		<h1>房间信息
		<c:if test="${flag != 1 }">
		    (5/6)
		</c:if>
		</h1>
		<%-- 房东指南 --%>
		<a href="https://zhuanti.ziroom.com/zhuanti/minsu/zhinan/fabuliucheng2.html" class="toZhinan"></a>
	</header>
		<div class="main" id="main">
			<div class="box z_scrollBox">
				<form id="addHouseFourForm" >
				    <input type="hidden" name="houseBaseFid" id="houseBaseFid" value="${houseBaseMsg.fid}">
				    <input type="hidden" name="rentWay" id="rentWay" value="${houseBaseMsg.rentWay}">
				    <input type="hidden" name="roomNum" id="roomNum" value="${houseBaseMsg.roomNum}">
				    <input type="hidden" name="flag" id="flag" value="${flag }">
				    <input type="hidden" name="houseRoomFid" id="houseRoomFid" value="${houseRoomFid}">
				    <input type="hidden" name="toToExtUrl" id="toToExtUrl" value="${basePath}houseIssue/43e881/showOptionalInfo?houseBaseFid=${houseBaseMsg.fid}&rentWay=${houseBaseMsg.rentWay}&flag=${flag }&houseFive=2">
					<ul class="z_list">
						<c:if test="${houseBaseMsg.houseStatus == 11 || houseBaseMsg.houseStatus == 40 }">
							<li class="s disabled-mui-btn">
								<input id="houseBaseDataShow" type="text" placeholder="请选择整套房屋的户型" value="${houseBaseMsg.roomNum}室${houseBaseMsg.hallNum}厅${houseBaseMsg.kitchenNum}厨${houseBaseMsg.toiletNum}卫${houseBaseMsg.balconyNum}阳台" readonly="readonly" class="c_disabled text">
								<span class="c_span">房源户型</span>
								<span class="c_ipt c_disabled"  style="display: none;" id="houseBaseText">${houseBaseMsg.roomNum}室${houseBaseMsg.hallNum}厅${houseBaseMsg.kitchenNum}厨${houseBaseMsg.toiletNum}卫${houseBaseMsg.balconyNum}阳台</span>
							</li>
						</c:if>
						<c:if test="${houseBaseMsg.houseStatus != 11 && houseBaseMsg.houseStatus != 40 }">
							<li class="mui-btn s mui-btn-active" id="houseBaseData"> 
								<input id="houseBaseDataShow" type="text" placeholder="请选择整套房屋的户型" value="${houseBaseMsg.roomNum}室${houseBaseMsg.hallNum}厅${houseBaseMsg.kitchenNum}厨${houseBaseMsg.toiletNum}卫${houseBaseMsg.balconyNum}阳台" readonly="readonly" class=" text">
								<span class="c_span">房源户型</span>
								<span class="c_ipt" id="houseBaseText">${houseBaseMsg.roomNum}室${houseBaseMsg.hallNum}厅${houseBaseMsg.kitchenNum}厨${houseBaseMsg.toiletNum}卫${houseBaseMsg.balconyNum}阳台</span>
								<span class="icon_r icon"></span>
							</li>
						</c:if>
						<li class="z_fx_tips s">
							1、无独立客厅，例如loft、开间等房型，请选择0厅
							<br /> 2、发布独立房间时，需要选择整套房源的户型，并至少完善1间房间信息
							<br /> 3、发布整套房源时，至少为其中1个卧室添加床位信息						
						</li>
					</ul>
					<ul id="baseHouseList" class="z_list hide">
			           <c:set  var="len"  value="${houseBaseMsg.roomNum - roomList.size() }"></c:set>
			             <c:forEach var="roomInfo" items="${roomList}" varStatus="no" >
			             <c:if test="${not empty roomInfo }">
			                <li class="mui-table-view-cell">
          							
          							<div class="mui-slider-handle" onclick="window.location.href=&quot;${basePath}roomMgt/43e881/roomBedZ?houseBaseFid=${houseBaseMsg.fid }&amp;roomFid=${roomInfo.fid }&amp;rentWay=0&amp;index=${no.index+1 }&amp;flag=${flag }&quot;">
	          							<c:choose>
									    	<c:when test="${roomInfo.isComplete }">
											    <span class="c_span">房间 <span class="liIndex">${no.index+1 }</span></span><span class="c_ipt">已完成</span>
									    	</c:when>
									    	<c:otherwise>
									    		<span class="text">完善房间<span class="liIndex">${no.index+1 }</span>的信息</span>
									    	</c:otherwise>
								       </c:choose>
	          						   <span class="icon_r icon"></span>
          						  </div>
            		         </li>
            		       
			             </c:if>
            		   </c:forEach>
            		     <c:if test="${len > 0 }">
            		      <c:set  var="lenIndex"  value="${roomList.size() }"></c:set>
            		      <c:forEach begin="0" end="${len - 1 }" varStatus="no" >
            		       <c:set  var="lenIndex"  value="${roomList.size()+no.index }"></c:set>
            		        <li class="mui-table-view-cell">
          							<div class="mui-slider-handle" onclick="window.location.href=&quot;${basePath}roomMgt/43e881/roomBedZ?houseBaseFid=${houseBaseMsg.fid }&amp;rentWay=0&amp;index=${lenIndex+1 }&amp;flag=${flag }&quot;">
	          							<span class="text">完善房间<span class="liIndex">${lenIndex+1 }</span>的信息</span>
	          						   <span class="icon_r icon"></span>
          						  </div>
            		         </li>
            		    </c:forEach>
			           </c:if>
		            </ul>
		            <c:if test="${flag!=1 }">
						<ul class="z_list"  onclick="RoomInfoZ.goToExt();" >
							<li class=" "> 
								<a href="javascript:#;" >			
									<span class="text">可选信息</span>
									<span class="icon_r icon"></span>
								</a>
							</li>
						</ul>
					</c:if>
				</form>
			</div>
		</div><!--/main-->
		
	<div class="box box_bottom">
	   	<c:if test="${flag!=1 }">
	   	  <input type="button" id="sentHouse" value="下一步" class="org_btn">
	   	</c:if>
		<c:if test="${flag==1 }">
		  <input type="button" id="sentHouse" value="确认" class="org_btn">
		</c:if>
	</div>
	<input type="hidden" name="fid" id="fid" value="${houseBaseMsg.fid}" >
	<input type="hidden" name="sIpt" id="sIpt" value="${houseBaseMsg.roomNum}" >
	<input type="hidden" name="tIpt" id="tIpt" value="${houseBaseMsg.hallNum}" >
	<input type="hidden" name="cIpt" id="cIpt" value="${houseBaseMsg.kitchenNum}" >
	<input type="hidden" name="wIpt" id="wIpt" value="${houseBaseMsg.toiletNum}" >
	<input type="hidden" name="yIpt" id="yIpt" value="${houseBaseMsg.balconyNum}" >
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script type="text/javascript" src="${staticResourceUrl }/js/layer/layer.js${VERSION}001"></script>
	<script type="text/javascript" src="${staticResourceUrl }/js/common/commonUtils2.js${VERSION}001"></script>
	<script type="text/javascript" src="${staticResourceUrl }/js/bed_style2.js${VERSION}003"></script>
	
	<script type="text/javascript" src="${staticResourceUrl }/js/mui.min.js${VERSION}001"></script>
	<script type="text/javascript" src="${staticResourceUrl }/js/mui.picker.min.js${VERSION}002"></script>
	<script type="text/javascript" src="${staticResourceUrl }/js/iscroll-probe.js${VERSION}001"></script>
	<script type="text/javascript" src="${staticResourceUrl }/js/house/roomInfoZ.js${VERSION}001"></script>
	<script>
	$$(function(){
		
		$$(".disabled-mui-btn").click(function(){
			CommonUtils.showShadedowTips("此状态下的房源户型不能修改", 2);
		})
	})
	</script>
	
</body>
</html>
