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
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="yes" name="apple-touch-fullscreen" />
	<title>可选信息</title>
	<link href="${staticResourceUrl}/css/mui.picker.min.css${VERSION}001" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/css_830.css">
	
</head>
<body>
	<!-- <form id="form" accept="" method="">	 -->
	<div class="bodys">
		<div class="main myCenterListNoneA">
			
			<header class="header">
			<c:if test="${houseFive !=2}">
			    <c:if test="${flag==1 }">
				   <a href="${basePath }houseInput/${loginUnauth}/goToHouseUpdate?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid }&rentWay=${rentWay}" class="goback"></a>
				</c:if>
				<c:if test="${flag!=1 }">
					<c:choose>
			    		<%-- Android --%>
			    		<c:when test="${sourceType eq 1 && versionCode ==true}">
							<a href="javascript:;" onclick="window.WebViewFunc.popToParent();" class="goback"></a>
			    		</c:when>
			    		<%-- iOS --%>
			    		<c:when test="${sourceType eq 2 && versionCode ==true}">
							<a href="javascript:;" onclick="window.popToParent();" class="goback"></a>
			    		</c:when>
			    		<%-- M站 --%>
			    		<c:otherwise>
							<a href="${basePath }houseMgt/${loginUnauth }/myHouses" class="goback"></a>
			    		</c:otherwise>
			    	</c:choose>
				</c:if>
			</c:if>
			<c:if test="${houseFive ==2}">
			     <a href="${basePath }roomMgt/${loginUnauth}/houseApartment?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid }&rentWay=${rentWay}&flag=${flag }" class="goback"></a>
			</c:if>
				
				<h1>可选信息</h1>
			</header>
			<%-- <div class="myBanner img">
				<c:if test="${defaultPic !='' }">
					<img src="${defaultPic }" class="bg setImg">
				</c:if>
				<c:if test="${defaultPic =='' }">
					<div class="upload_photo">
						<div class="uploadBtn"></div>
						<h2>上传房源照片</h2>
					</div>
				</c:if>
			</div> --%>
			<ul class="myCenterList ">
				<li class="c_ipt_li bor_b " id="exchangeMsg">
				<a href="javascript:;">
					<span class="c_tt c_9">交易信息</span>
					<span class="icon_r icon"></span>
					</a>
				</li>
				
				<li class="c_ipt_li bor_b " id="checkInMsg">	
				<a href="javascript:;">		
					<span class="c_tt c_9">入住信息</span>
					<span class="icon_r icon"></span>
					</a>
				</li>
			</ul>
		</div>
		<c:if test="${status == 11 || status == 20}">
			<c:if test="${rentWay == 0 || (rentWay == 1 && not empty houseRoomFid && houseRoomFid != 'null')}">
				<div class="box">
					<a href="javascript:;" class="delHouse" id="cancleHouse" >取消发布</a>
				</div>
			</c:if>
		</c:if>
		<c:if test="${status == 40}">
			<div class="box">
				<a href="javascript:;" class="delHouse" id="downHouse" >下架房源</a>
			</div>
		</c:if>
		<c:if test="${status == 10 || status == 21 || status == 30 || status == 41 || status == 50}">
		   <div class="box">
				<a href="javascript:;" class="delHouse" id="deleteHouse" >删除房源</a>
		   </div>
		</c:if>
	</div><!--/main-->
		<!-- <div class="box box_bottom ">
			<input type="button" id="submitBtn" value="确认" class="org_btn">
		</div> -->
	<!-- </form> -->
	<input type="hidden" id="sourceType" value="${sourceType }"/>
	<input type="hidden" id="versionCode" value="${versionCode }"/>
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<script type="text/javascript">
		$(function(){
			/* 命名空间方式分开 */
			var house={};
			house.houseMsg={
				landlordUid:'${houseBase.landlordUid}',
				houseFid:'${houseBaseFid}',
				roomFid:'${houseRoomFid}',
				rentWay:'${rentWay}',
				delHouseMsg:'您确定要删除房源吗？',
				downHouseMsg:'如果下架您的房源，您需要对已确认的预订负责，在下架时间段内，您的房源将不会出现在房源列表中。',
				delHouseUrl:'${basePath }houseIssue/${loginUnauth }/delHouse',
				downHouseUrl:'${basePath }houseIssue/${loginUnauth }/downHouse',
				cancleHouseUrl:'${basePath }houseIssue/${loginUnauth }/cancleHouse',
			};
			
			house.oper={
					showConfirm:function(msg,url,obj,data,type){
						var _this = this;
						 layer.open({
							 	shade: true,
							    shadeClose:true,
						    	content: '<div class="loginTips" style="line-height: 1.1rem">'+msg+'</div>',
						    	btn:['确认','取消'],
						    	no: function(){
						        	layer.closeAll();
						        }, 
						    	yes: function(){
						    		_this.ajaxPost(obj,url,data,type);
						        	//$(obj).removeClass("disabled_btn").addClass("org_btn");
						        }
						   });
					},
					delHouse:function(obj){
						if($(obj).hasClass("disabled_btn")){
							return false;
						}
						//$(obj).addClass("disabled_btn").removeClass("org_btn");
						var data = {};
						data.houseFid = house.houseMsg.houseFid;
                        data.landlordUid = house.houseMsg.landlordUid;
						data.roomFid = house.houseMsg.roomFid;
						data.rentWay = house.houseMsg.rentWay;
						this.showConfirm(house.houseMsg.delHouseMsg,house.houseMsg.delHouseUrl,obj,data,1);
					},
					downHouse:function(obj){
						if($(obj).hasClass("disabled_btn")){
							return false;
						}
						//$(obj).addClass("disabled_btn").removeClass("org_btn");
						var data = {};
						data.landlordUid = house.houseMsg.landlordUid;
						data.houseFid = this.getFid();
						data.rentWay = house.houseMsg.rentWay;
						this.showConfirm(house.houseMsg.downHouseMsg, house.houseMsg.downHouseUrl,obj,data);
					},
					cancleHouse:function(obj){
						if($(obj).hasClass("disabled_btn")){
							return false;
						}
						//$(obj).addClass("disabled_btn").removeClass("org_btn");
						
						var data = {};
						data.houseFid = house.houseMsg.houseFid;
						data.roomFid = house.houseMsg.roomFid;
						data.rentWay = house.houseMsg.rentWay;
						data.landlordUid = house.houseMsg.landlordUid;
						
						layer.open({
							 	shade: true,
							    shadeClose:true,
						    	content: '<div class="loginTips" style="line-height: 1.1rem">确认要取消发布吗？</div>',
						    	btn:['确认','取消'],
						    	no: function(){
						        	layer.closeAll();
						        }, 
						    	yes: function(){
						        	//$(obj).removeClass("disabled_btn").addClass("org_btn");
						    		//_this.ajaxPost(obj,url,data,type);
									house.oper.ajaxPost(obj,house.houseMsg.cancleHouseUrl,data);
						        }
						   });
						
					},
					getFid:function(){
						var houseFid = "";
						if(house.houseMsg.rentWay == 0){
							houseFid = house.houseMsg.houseFid;
						}else if(house.houseMsg.rentWay == 1){
							houseFid = house.houseMsg.roomFid;
						}
						return houseFid;
					},
					ajaxPost:function(obj,url,data,type){
						$.ajax({
							url:url,
							data:data,
							dataType:"json",
							type:"post",
							success:function(result) {
								
								if(result.code === 0){
									var sourceType = $("#sourceType").val();
									var versionCode = $("#versionCode").val();
									if(type == 1){
										if(sourceType == 1 && (versionCode ==true || versionCode =='true')){
											window.WebViewFunc.popToParent();
										}else if(sourceType == 2 && (versionCode ==true || versionCode =='true')){
											window.popToParent();
										}else{
											//如果删除则跳转到房源列表
											window.location.href = "${basePath }houseMgt/${loginUnauth }/myHouses";
										}
									}else{
										//否则刷新当前页面
										window.location.reload(true);
									}
									
								}else{
									if(type == 2){
										showShadedowTips(result.msg,1);
									}else{
										showShadedowTips("操作失败",1);
									}
									//$(obj).removeClass("disabled_btn").addClass("org_btn");
								}
							},
							error:function(result){
								showShadedowTips("未知错误",1);
								//$(obj).removeClass("disabled_btn").addClass("org_btn");
							}
						});
					}
				};
			
				$("#deleteHouse").click(function(){
					house.oper.delHouse(this);
				});
				
				$("#cancleHouse").click(function(){
					house.oper.cancleHouse(this);
				});
				
				$("#downHouse").click(function(){
					house.oper.downHouse(this);
				});
		})
		
		// 跳转上传图片页面
		$(".myBanner").click(function(){
			window.location.href = "${basePath }houseInput/${loginUnauth }/goToUpHousePic?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}";	
		});
		// 跳转位置信息页面
		$("#locationMsg").click(function(){
			window.location.href = "${basePath }houseIssue/${loginUnauth }/toLocationMsg?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}";
		});
		// 跳转交易信息页面
		$("#exchangeMsg").click(function(){
			var status='${status}';
			if(status==11){
				showShadedowTips('无法修改审核中房源信息，可预览',1);
			}else{
				window.location.href = "${basePath }houseIssue/${loginUnauth }/toExchangeMsg?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}";
			}
		});
		// 跳转入住信息页面
		$("#checkInMsg").click(function(){
			var status='${status}';
			if(status==11){
				showShadedowTips('无法修改审核中房源信息，可预览',1);
			}else{
				window.location.href = "${basePath }houseIssue/${loginUnauth }/toCheckInMsg?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}";
			}
		});
		// 跳转我的房源页面
		$("#submitBtn").click(function(){
			var flag=${(flag ==null||flag=='')?0:flag};
			if(flag==1){
				window.location.href ="${basePath }/houseInput/${loginUnauth}/goToHouseUpdate?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid }&rentWay=${rentWay}";
				return;
			}
			window.location.href = "${basePath }houseMgt/${loginUnauth }/myHouses";
		});
	</script>
</body>
</html>
