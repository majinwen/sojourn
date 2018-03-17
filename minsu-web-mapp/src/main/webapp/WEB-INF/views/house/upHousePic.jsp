<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE >
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="applicable-device" content="mobile">
	<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">        
	<script type="text/javascript" src="${staticResourceUrl}/js/header.js${VERSION}001"></script>
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="yes" name="apple-mobile-web-app-capable"/>
	<meta content="yes" name="apple-touch-fullscreen"/>
	<title>添加照片</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
	
</head>
<body>

	<!-- <form accept="" method="">	 -->
	<div class="bodys">
	<header class="header">
			<c:if test="${flag == 1}">
				<a href="${basePath }houseInput/${loginUnauth }/goToHouseUpdate?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }"  class="goback"></a>
			</c:if>
			<c:if test="${flag != 1 }">
				<a href="${basePath }roomMgt/${loginUnauth }/houseApartment?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}"  class="goback"></a>
			</c:if>
			<h1>添加照片 <c:if test="${flag != 1}">(6/6)</c:if>
			</h1>
			<a href="javascript:;" class="header_r header_r_2" onclick="setDefaultPic('${houseBaseFid}','${rentWay}');">设置封面照片</a>
			<%-- 房东指南 --%>
			<a href="https://zhuanti.ziroom.com/zhuanti/minsu/zhinan/fabuliucheng2.html" class="toZhinan"></a>
		</header>
	
		<div id="main" class="main myCenterListNoneA">
			<div class="box">
			<p class="photomessage">
				1、每张照片请上传至对应区域，例如卧室的所有照片上传至”卧室“区域，卫生间的所有照片上传至”卫生间“区域；
				<br>2、每间卧室、卫生间至少1张照片，客厅至少2张照片，上传照片总数不得少于10张；
				<br>3、请上传清晰无水印的照片，请勿加边框，各区域至少有一张可以看清房间全景的照片；
				<br>4、照片大于300K，格式支持jpg、png；
				<br>5、建议上传横图。首图需选择横图，首图可选择卧室、客厅、室外等区域。
			</p>
			<ul class="hosueList">
				<c:forEach items="${roomPicList }" var="room" varStatus="no">
					<c:if test="${fn:length(room.picList)>0 }">
						<li class="imgli" onclick="toUpPic('${houseBaseFid}','${room.roomFid }',${room.picType },${room.picMaxNum });">
							<img class="setImg2" src="${picBaseAddrMona}${room.picList.get(0).picBaseUrl }${room.picList.get(0).picSuffix}${picSize}${picSuffix}">
							<span class="state">共 <i>${fn:length(room.picList)}</i> 张</span>
							<p class="title">${room.picTypeName }</p>
						</li>
					</c:if>
					<c:if test="${fn:length(room.picList)==0 }">
						<li class="noimgli" onclick="toUpPic('${houseBaseFid}','${room.roomFid }',${room.picType },${room.picMaxNum });">
							
							<div class="upload_photo">
								<div class="uploadBtn"></div>
								<c:if test="${empty room.picTypeName  ||  room.picTypeName ==''}">
								   <h2>上传卧室${no.index + 1 }照片</h2>
								</c:if>
								<c:if test="${not empty room.picTypeName}">
								   <h2>上传${room.picTypeName }照片</h2>
								</c:if>
							</div>
							
							<p class="title hide"></p>
						</li>
					</c:if>
				</c:forEach>
				<c:forEach items="${picTypeList }"  var="type">
					<c:if test="${fn:length(type.picList)>0 }">
						<li class="imgli" onclick="toUpPic('${houseBaseFid}','${houseRoomFid }',${type.picType },${type.picMaxNum });">
							<img class="setImg2" src="${picBaseAddrMona}${type.picList.get(0).picBaseUrl }${type.picList.get(0).picSuffix}${picSize}${picSuffix}">
							<span class="state">共 <i>${fn:length(type.picList)}</i> 张</span>
							<p class="title">${type.picTypeName }</p>
						</li>
					</c:if>
					<c:if test="${fn:length(type.picList)==0 }">
						<li class="noimgli" onclick="toUpPic('${houseBaseFid}','${houseRoomFid }',${type.picType },${type.picMaxNum });">
							
							<div class="upload_photo">
								<div class="uploadBtn"></div>
								<h2>上传${type.picTypeName }照片</h2>
							</div>
							
							<p class="title hide"></p>
						</li>
					</c:if>
				</c:forEach>
			</ul>
			
				<div class="title_classify pl20 ">
					<span onclick="clecked(this)"><span class="icon_check icon icon_l active"></span><span>同意</span></span>
					<a 	href="${staticResourceUrl}/html/order_service_protocol.html" style="color: #4A90E2;font-size:0.7rem;line-height:2.2rem;">《服务协议》</a>
				</div>
			</div>
		</div><!--/main-->
	</div>
	<%-- <div class="box box_bottom">
		<a href="${basePath }houseIssue/${loginUnauth }/showOptionalInfo?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid }&flag=${flag}&rentWay=${rentWay}"><input type="button" id="nextBtn" value="发布房源" class="org_btn" ></a>
	</div> --%>
	
	 <div class="box box_bottom">
	    <c:if test="${flag != 1}">
		  <a href="javascript:releaseHouse('${houseBaseFid}');"><input type="button" id="nextBtn" value="发布房源" class="org_btn" ></a>
		</c:if>
		<c:if test="${flag == 1}">
		  <a href="${basePath }houseInput/${loginUnauth }/goToHouseUpdate?houseBaseFid=${houseBaseFid}&rentWay=${rentWay}&flag=${flag}&houseRoomFid=${houseRoomFid}"><input type="button" id="nextBtn" value="确认" class="org_btn" ></a>
		</c:if>
	</div>
	
	<input type="hidden" id="houseAllPicCount" value="${houseAllPicCount }">
	<input type="hidden" id="sourceType" value="${sourceType }">
	<input type="hidden" id="houseStatus" value="${houseStatus }">
	<c:forEach items="${roomPicList }" var="room">
		<input type="hidden" id="rooms" value="${room.roomFid}">
		<input type="hidden" id="count" value="${fn:length(room.picList)}">
	</c:forEach>


	<!-- </form> -->
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script type="text/javascript" src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<script type="text/javascript" src="${staticResourceUrl}/js/common/commonUtils.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}006"></script>
	<script type="text/javascript">
		

		var source= $("#sourceType").val();
		var houseAllPicCount = $("#houseAllPicCount").val();
		var houseStatus = $("#houseStatus").val();
		/*询问弹层*/
		function showConfirm(str){
		    //信息框-例2
		    layer.open({
		    	content: '<div class="loginTips" style="line-height: 1.1rem">'+str+'</div>',
		    	btn:['拒绝','同意'],
		    	yes: function(){
		              //直接跳转到手机注册
		              layer.closeAll();              
		          }, no: function(){
		            //alert("已下架")
		            showShadedowTips("已下架",2);
		        }
	
		    });
		}
		
		
		//选择房间属性 值
		var sRoomFid = "";
		var sRoomPicCount = 0;
		function setVal(obj,val){
			sRoomFid = $(obj).attr('data-id');
			sRoomPicCount = $(obj).attr('data-count');
			$(obj).addClass('active').siblings().removeClass();
		}
		var rentWay = '${rentWay}';
		var roomNum = '${roomNum}';
		//选择需要设置默认图片的房间
		function chooseSetRoom(houseBaseFid){
			var roomList = '<ul class="layerList" id="layerList">'
							<c:forEach items="${roomPicList }" var="room">
								+'<li data-count="${fn:length(room.picList)}" data-id="${room.roomFid}" onclick="setVal(this)"><span class="radio"></span>${room.picTypeName }</li>'
							</c:forEach>
							+'</ul>';
//			console.log(roomList);
			if(rentWay ==1 && roomNum ==1){
				sRoomFid = $("#rooms").val();
				sRoomPicCount = $("#count").val();
				/*console.log(sRoomFid);
				console.log(sRoomPicCount);*/
				if(sRoomFid == ""){
					showShadedowTips("请选择房间",1);
					return;
				}
				if(sRoomPicCount == 0){
					showShadedowTips("请先添加照片",1);
					return;
				}
				if(source == "1"){
					window.WebViewFunc.setHouseDefaultPic(houseBaseFid,sRoomFid);
					sRoomFid = "";
					sRoomPicCount = 0;
				}else if(source == "2"){
					setHouseDefaultPic(houseBaseFid,sRoomFid);
					sRoomFid = "";
					sRoomPicCount = 0;
				}
			}else{
				
				document.body.removeEventListener('touchmove',bodyScroll,false); 
				layer.open({
					title: ['请选择房间'],
					content: roomList,
					btn:['确定','取消'],
					yes: function(){
						if(sRoomFid == ""){
							showShadedowTips("请选择房间",1);
							return;
						}
						if(sRoomPicCount == 0){
							showShadedowTips("请先添加照片",1);
							return;
						}
						layer.closeAll();
						if(source == "1"){
							window.WebViewFunc.setHouseDefaultPic(houseBaseFid,sRoomFid);
							sRoomFid = "";
							sRoomPicCount = 0;
						}else if(source == "2"){
							setHouseDefaultPic(houseBaseFid,sRoomFid);
							sRoomFid = "";
							sRoomPicCount = 0;
						}
						document.body.addEventListener('touchmove',bodyScroll,false); 
					}, no: function(){
						sRoomFid = "";
						sRoomPicCount = 0;
						document.body.addEventListener('touchmove',bodyScroll,false); 
					}

				});
			}

		}
		
		
		//调用原生
		function toUpPic(houseBaseFid,houseRoomFid,picType,picMaxNum){
			if(source == "1"){
				//兼容之前版本处理(多加一个参数)
				window.WebViewFunc.uploadHousePic(houseBaseFid,houseRoomFid,picType);
				window.WebViewFunc.uploadHousePicNew(houseBaseFid,houseRoomFid,picType,parseInt(houseStatus));
				window.WebViewFunc.uploadHousePicV3(houseBaseFid,houseRoomFid,picType,parseInt(houseStatus),picMaxNum);
			}else if(source == "2"){
				uploadHousePic(houseBaseFid,houseRoomFid,picType);
				uploadHousePicNew(houseBaseFid,houseRoomFid,picType,parseInt(houseStatus));
				uploadHousePicV3(houseBaseFid,houseRoomFid,picType,parseInt(houseStatus),picMaxNum);
			}
		}
		 //设置默认图片
		function setDefaultPic(houseBaseFid,rentWay){
			console.log(houseAllPicCount);
			if(rentWay == 0){
				if(houseAllPicCount == 0){
					showShadedowTips("请先添加照片",1);
					return;
				}
				if(source == "1"){
					window.WebViewFunc.setHouseDefaultPic(houseBaseFid,'');
				}else if(source == "2"){
					setHouseDefaultPic(houseBaseFid,'');
				}
			}else if(rentWay == 1){
				chooseSetRoom(houseBaseFid);
			}
		 }
		 
		 //发布房源
		 function releaseHouse(houseBaseFid){
			 CommonUtils.ajaxPostSubmit('${basePath }houseInput/${loginUnauth }/releaseHouse', {"houseBaseFid":houseBaseFid,"rentWay":"${rentWay }","houseRoomFid":$("#houseRoomFid").val()}, function(data){
				 if(data.code == 0){
					 var flag = ${flag};
					 if(flag !=1){
						 window.location.href = '${basePath }houseInput/${loginUnauth }/goToFinished?houseBaseFid=${houseBaseFid}&rentWay=${rentWay}&flag=${flag}';
					 }else{
						 window.location.href = '${basePath }houseInput/${loginUnauth }/goToHouseUpdate?houseBaseFid=${houseBaseFid}&rentWay=${rentWay}&flag=${flag}&houseRoomFid=${houseRoomFid}';
					 }
				 } else {
		            showShadedowTips(data.msg,2);
				 }
			 })
		 }
	</script>
	<script type="text/javascript">
	$(function(){

		var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });

		var conHeight = $(window).height()-$('.header').height()- $(".box_bottom").height();

		$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$('#main .box').css({'position':'absolute','width':'100%'});
		myScroll.refresh();
		document.body.addEventListener('touchmove',bodyScroll,false); 
	})
	
	
	function bodyScroll(event){
        event.preventDefault();
    }    



	
	</script>
</body>
</html>
