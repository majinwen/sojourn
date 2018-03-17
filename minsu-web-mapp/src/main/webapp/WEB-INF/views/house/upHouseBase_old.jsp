<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE>
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
	<title>修改房源</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}002"> 
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/css_830.css${VERSION}001">
</head>
<body>

	<input type="hidden" id="staticUrl" value="${staticResourceUrl}">
	<div id="body" class="bodys">
		<header class="header">
			<a href="${basePath}houseMgt/${loginUnauth}/myHouses" id="goback" class="goback" ></a>
			<h1><c:if test="${rentWay==0 }">${houseBase.houseName }</c:if><c:if test="${rentWay==1 }">${houseRoom.roomName }</c:if></h1>
			<c:if test="${status ==40 }">
				<c:set var="houseDetailUrl" value="#"></c:set>
				<c:if test="${rentWay==0 ||(rentWay==1 && houseRoomFid !='null')  }">
					<c:if test='${rentWay==0 }'>
						<c:set var="houseDetailUrl" value="${basePath}tenantHouse/${noLoginAuth}/houseDetail?fid=${houseBase.fid }&rentWay=${rentWay }&houseBaseFid=${houseBase.fid}&houseRoomFid=${houseRoomFid}&sourceFrom=previewBtn"></c:set>
					</c:if>
					<c:if test='${rentWay==1 }'>	
						<c:set var="houseDetailUrl" value="${basePath}tenantHouse/${noLoginAuth}/houseDetail?fid=${houseRoomFid }&rentWay=${rentWay }&houseBaseFid=${houseBase.fid}&houseRoomFid=${houseRoomFid}&sourceFrom=previewBtn"></c:set>
					</c:if>
				</c:if>
				
				<a  href="${houseDetailUrl }" id="goHouseDetail" <c:if test="${rentWay==1 && houseRoomFid=='null'}">onclick="showCompleteRoomMsg();"</c:if> class="header_r">预览</a>
			</c:if>
		</header>
		<div class="main myCenterListNoneA">
			
			<div class="myBanner img">
				<c:choose>
				<c:when test="${rentWay==1&&(houseRoomFid=='' || houseRoomFid=='null') }">
					<img src="${defaultPic }" class="bg setImg">
				</c:when>
				<c:otherwise>
					<a href="${basePath }houseInput/${loginUnauth}/goToUpHousePic?houseBaseFid=${houseBase.fid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=1&pageType=1"><img src="${defaultPic }" class="bg setImg"></a>
				</c:otherwise>
				</c:choose>
				<!-- 显示审核未通过原因 -->
				<c:if test="${status == 21 }">
					<span  class="house_status house_status_ask">${houseStauts }
						<i id="askBtn"></i>
					</span>
				</c:if>
				<c:if test="${status != 21 }">
					<span  class="house_status">${houseStauts }</span>
				</c:if>
			</div>
			<ul class="myCenterList myCenterList_no">
				<li class="clearfix bor_b ">
					<a href="${basePath }houseInput/${loginUnauth }/findHouseDetail?houseBaseFid=${houseBase.fid}&rentWay=${rentWay}&flag=1&houseRoomFid=${houseRoomFid}">房源信息<input type="text" id="houseMessage" placeholder="修改您的房源信息" value="" readonly="readonly"></a>
					<span class="icon_r icon"></span>
				</li>
				<li class="clearfix bor_b ">
					
				    <c:choose>
				        <c:when test="${rentWay==1 && houseRoomFid!='null' && houseRoomFid!=''}">
				         <a href="${basePath }roomMgt/${loginUnauth }/roommateDetail?houseBaseFid=${houseBase.fid}&roomFid=${houseRoomFid}">房间信息<input type="text" id="houseMessage" placeholder="修改您的房间信息" value="" readonly="readonly"></a>
					     </c:when>
					     <c:when test="${rentWay==1 && (houseRoomFid=='null' || houseRoomFid=='')}">
				         <a href="${basePath }roomMgt/${loginUnauth }/houseApartment?houseBaseFid=${houseBase.fid}&flag=1&houseRoomFid=${houseRoomFid}">房间信息<input type="text" id="houseMessage" placeholder="修改您的房间信息" value="" readonly="readonly"></a>
					     </c:when>
					     <c:when test="${rentWay==0 && status != 40}">
				         <a href="${basePath }roomMgt/${loginUnauth }/houseApartment?houseBaseFid=${houseBase.fid}&flag=1&houseRoomFid=${houseRoomFid}">房间信息<input type="text" id="houseMessage" placeholder="修改您的房间信息" value="" readonly="readonly"></a>
					     </c:when>
				          <c:when test="${rentWay==0 && status == 40}">
				         <a href="${basePath }roomMgt/${loginUnauth }/roomList?houseBaseFid=${houseBase.fid}" >房间信息<input type="text" id="houseMessage" placeholder="修改您的房间信息" value="" readonly="readonly"></a>
				         </c:when>
				    </c:choose>		
				    <span class="icon_r icon"></span>		
                </li>
				<li class="clearfix">
					 <a href="${basePath }houseIssue/${loginUnauth }/showOptionalInfo?houseBaseFid=${houseBase.fid}&rentWay=${rentWay}&flag=1&houseRoomFid=${houseRoomFid}">补充信息<input type="text" id="addMessage" placeholder="默认为平台规则" value="" readonly="readonly"></a>
					<span class="icon_r icon"></span>			
				</li>
				
			</ul>
			<ul class="myCenterList">
				<li class="clearfix bor_b doubleLi">				
					<c:if test="${status ==40}">
						<h1>房源地址</h1>
						<p>${houseBase.houseAddr }</p>
					</c:if>
					<c:if test="${status !=40}">
						<a href="${basePath }houseDeploy/${loginUnauth }/toLocationThree?houseBaseFid=${houseBase.fid}&rentWay=${rentWay}&flag=1&houseRoomFid=${houseRoomFid}"><h1>房源地址</h1>
						<p>${houseBase.houseAddr }</p></a>
						<span class="icon_r icon" ></span>
					</c:if>	
				</li>

				<c:if test="${status == 40}">
					<li class="clearfix bor_b no_icon">				
						房源类型
						<input type="text" id="houseStyle" placeholder="" value="${houseStyle }" readonly="readonly">
					</li>
				</c:if>
				<c:if test="${status != 40}">
					 <li class="clearfix bor_b ">				
						房源类型
						<input type="text" id="checkInLimit" placeholder="请选择房源类型" value="${houseStyle }"  readonly="readonly">
						<select id="checkInLimitSelect"  name="checkInLimit">
							<c:forEach items="${houseTypeList }" var="stay">
								<option <c:if test="${houseTypeKey == stay.key}">selected="selected"</c:if> value="${stay.key }">${stay.text }</option>
							</c:forEach>
						</select>
						<span class="icon_r icon" ></span>
					 </li>
				</c:if>
					
				<%-- <li class="clearfix bor_b no_icon">
					出租类型
					<input type="text" id="rentStyle" placeholder="" value="${rentStyle}" readonly="readonly">
				</li> --%>
				
				<li class="c_ipt_li bor_b">
						<a href="javascript:;">
							<span class="c_span">出租类型</span>
							<!-- <input  class="c_ipt" type="text" id="rentStyle" name="rentStyle" value="独立房间" readonly="readonly"> -->
							<input  class="c_ipt" type="text" id="rentStyle" name="rentStyle" value="${rentStyle}" readonly="readonly">
							<span class="icon_r icon"></span>
						</a>

					</li>
					
					
				<li class="clearfix no_icon">
					房源编码
					<input type="text" id="rentStyle" placeholder="" value="${houseNo}" readonly="readonly">
				</li>
			</ul>
			<!-- 如果房源有智能锁显示入口 -->
			<c:if test="${houseBase.isLock == 1}">
				<ul class="myCenterList">
					<li class="clearfix bor_b">
						<a href="${basePath }smartLock/${loginUnauth }/showPanel?houseBaseFid=${houseBase.fid}&rentWay=${rentWay}&houseRoomFid=${houseRoomFid}"><h1>智能门锁</h1></a>
						<span class="icon_r icon" ></span>
					</li>
				</ul>
			</c:if>
		</div><!--/main-->
	</div>
		<%-- <c:if test="${status == 11 || status == 20 || status ==30}">
			<div class="box box_bottom">
				<input type="button" id="cancleHouse" value="取消发布" class="org_btn">
			</div>
		</c:if>
		<c:if test="${status == 40}">
			<div class="box box_bottom">
				<input type="button" id="downHouse" value="下架房源" class="org_btn">
			</div>
		</c:if>
		<c:if test="${status == 41 || status == 50 || status == 10 || status == 21}">
			<div class="box box_bottom clearfix">
				<input type="button" id="deleteHouse" value="删除" class="org_btn" style="width: 50%;float: left;background: #ddd;" >
				<input type="button" id="issueHouse" value="发布房源" class="org_btn" style="width: 50%;float: left;">
			</div>
		</c:if> --%>
		<c:if test="${status == 41 || status == 50 || status == 10 || status == 21}">
			<div class="box box_bottom clearfix">
				<input type="button" id="issueHouse" value="发布房源" class="org_btn">
			</div>
		</c:if>
	<!-- </form> -->
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script type="text/javascript" src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js"></script>
	<script type="text/javascript">
	
		$(function(){
			var flag = "${status == 41 || status == 50 || status == 10 || status == 21}";
			if(flag == "true"){
				$("#body").addClass("bodys");
			}else{
				$("#body").removeClass("bodys");
			}
			
			/* 命名空间方式分开 */
			var house={};
			house.houseMsg={
				landlordUid:'${houseBase.landlordUid}',
				houseFid:'${houseBase.fid}',
				roomFid:'${houseRoomFid}',
				rentWay:'${rentWay}',
				//delHouseMsg:'您确定要删除房源吗？',
				//downHouseMsg:'如果下架您的房源，您需要对已确认的预订负责，在下架时间段内，您的房源将不会出现在房源列表中。',
				refuseMsg:'很抱歉，您的房源未通过审核。自如民宿的管家会联系您并提供房屋升级的指导。期待早日相会，请继续加油哦！',
				//delHouseUrl:'${basePath }houseIssue/${loginUnauth }/delHouse',
				//downHouseUrl:'${basePath }houseIssue/${loginUnauth }/downHouse',
				issueHouseUrl:'${basePath }houseIssue/${loginUnauth }/issueHouseInDetail',
				//cancleHouseUrl:'${basePath }houseIssue/${loginUnauth }/cancleHouse',
				/* getRemark:'${basePath }houseIssue/${loginUnauth }/getHouseOperLog', */
				updateHouseTypeUrl:"${basePath }houseInput/${loginUnauth }/updateHouseType",
			};
			
			house.oper={
					/* showConfirm:function(msg,url,obj,data,type){
						var _this = this;
						 layer.open({
						    	content: '<div class="loginTips" style="line-height: 1.1rem">'+msg+'</div>',
						    	btn:['拒绝','同意'],
						    	no: function(){
						    		_this.ajaxPost(obj,url,data,type);
						        }, 
						    	yes: function(){
						        	layer.closeAll();
						        	$(obj).removeClass("disabled_btn").addClass("org_btn");
						        }
						   });
					}, */
					showAsk:function(){
						//弹出提示框
						layer.open({
					    	content: '<div class="box" >'+house.houseMsg.refuseMsg+'</div>',
					    	btn:['确定'],
					    	yes: function(){
					        	layer.closeAll();
					        }
						});
					},
					/* delHouse:function(obj){
						if($(obj).hasClass("disabled_btn")){
							return false;
						}
						$(obj).addClass("disabled_btn").removeClass("org_btn");
						var data = {};
						data.houseFid = house.houseMsg.houseFid;
						data.roomFid = house.houseMsg.roomFid;
						data.rentWay = house.houseMsg.rentWay;
						this.showConfirm(house.houseMsg.delHouseMsg,house.houseMsg.delHouseUrl,obj,data,1);
						
					},
					downHouse:function(obj){
						if($(obj).hasClass("disabled_btn")){
							return false;
						}
						$(obj).addClass("disabled_btn").removeClass("org_btn");
						var data = {};
						data.landlordUid = house.houseMsg.landlordUid;
						data.houseFid = this.getFid();
						data.rentWay = house.houseMsg.rentWay;
						this.showConfirm(house.houseMsg.downHouseMsg, house.houseMsg.downHouseUrl,obj,data);
					}, */
					issueHouse:function(obj){
						if($(obj).hasClass("disabled_btn")){
							return false;
						}
						$(obj).addClass("disabled_btn").removeClass("org_btn");
						var data = {};
						data.landlordUid = house.houseMsg.landlordUid;
						data.houseFid = house.houseMsg.houseFid;
						data.roomFid = house.houseMsg.roomFid;
						data.rentWay = house.houseMsg.rentWay;
						this.ajaxPost(obj,house.houseMsg.issueHouseUrl,data,2);
					},
					/* cancleHouse:function(obj){
						if($(obj).hasClass("disabled_btn")){
							return false;
						}
						$(obj).addClass("disabled_btn").removeClass("org_btn");
						var data = {};
						data.houseFid = house.houseMsg.houseFid;
						data.roomFid = house.houseMsg.roomFid;
						data.rentWay = house.houseMsg.rentWay;
						data.landlordUid = house.houseMsg.landlordUid;
						this.ajaxPost(obj,house.houseMsg.cancleHouseUrl,data);
					},
					getFid:function(){
						var houseFid = "";
						if(house.houseMsg.rentWay == 0){
							houseFid = house.houseMsg.houseFid;
						}else if(house.houseMsg.rentWay == 1){
							houseFid = house.houseMsg.roomFid;
						}
						return houseFid;
					}, */
					selectVal:function(obj,id){
						var val=$(obj).val();
					    var txt =  $(obj).find("option:selected").text();
					    $('#'+id).val(txt);
					    $.post(house.houseMsg.updateHouseTypeUrl,{fid:house.houseMsg.houseFid,houseType:val},function(){},'json');
					},
					ajaxPost:function(obj,url,data,type){
						$.ajax({
							url:url,
							data:data,
							dataType:"json",
							type:"post",
							success:function(result) {
								if(result.code === 0){
									if(type == 1){
										//如果删除则跳转到房源列表
										window.location.href = "${basePath }houseMgt/${loginUnauth }/myHouses";
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
									$(obj).removeClass("disabled_btn").addClass("org_btn");
								}
							},
							error:function(result){
								showShadedowTips("未知错误",1);
								$(obj).removeClass("disabled_btn").addClass("org_btn");
							}
						});
					}
				};
			
				/* $("#deleteHouse").click(function(){
					house.oper.delHouse(this);
				});
				
				$("#cancleHouse").click(function(){
					house.oper.cancleHouse(this);
				});
				
				$("#downHouse").click(function(){
					house.oper.downHouse(this);
				}); */
				
				$("#issueHouse").click(function(){
					house.oper.issueHouse(this);
				});
				
				$("#askBtn").click(function(){
					house.oper.showAsk();
				});
				
				$("#checkInLimitSelect").change(function(){
					house.oper.selectVal(this,"checkInLimit");
				});
				 
		})
					function showCompleteRoomMsg(){
						//弹出提示框
						layer.open({
					    	content: '<div class="box" >请完善房间信息！</div>',
					    	btn:['确定'],
					    	yes: function(){
					        	layer.closeAll();
					        }
						});
					}	
		
</script>

</body>
</html>
