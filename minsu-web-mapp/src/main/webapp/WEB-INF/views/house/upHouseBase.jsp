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
	<form id="addHouseFourForm" >
		<header class="header">
			<h1><c:if test="${rentWay==0 }">${houseBase.houseName }</c:if><c:if test="${rentWay==1 }">${houseRoom.roomName }</c:if></h1>

			<c:choose>
	    		<%-- Android --%>
	    		<c:when test="${sourceType eq 1 && versionCode}">
					<a href="javascript:;" onclick="window.WebViewFunc.popToParent();" class="goback"></a>
					<c:if test="${(status ==40 ||status==11) && versionCodeInit >=100004 }">
						<c:if test='${rentWay==0 }'>
							<a  href="#" id="goHouseDetail" onclick="window.WebViewFunc.toHouseDetail('${houseBase.fid }','${rentWay}');" class="header_r">预览</a>
						</c:if>
						<c:if test='${rentWay==1 }'>
							<a  href="#" id="goHouseDetail" onclick="window.WebViewFunc.toHouseDetail('${houseRoomFid}','${rentWay}');" class="header_r">预览</a>
						</c:if>
					</c:if>
                    <c:if test="${(status ==40 ||status==11) && versionCodeInit <100004 }">
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


	    		</c:when>
	    		<%-- iOS --%>
	    		<c:when test="${sourceType eq 2 && versionCode}">
					<a href="javascript:;" onclick="window.popToParent();" class="goback"></a>

                    <c:if test="${(status ==40 ||status==11) && versionCodeInit >=100004 }">
                        <c:if test='${rentWay==0 }'>
                            <a  href="#" id="goHouseDetail" onclick="window.toHouseDetail('${houseBase.fid }','${rentWay}');" class="header_r">预览</a>
                        </c:if>
                        <c:if test='${rentWay==1 }'>
                            <a  href="#" id="goHouseDetail" onclick="window.toHouseDetail('${houseRoomFid}','${rentWay}');" class="header_r">预览</a>
                        </c:if>
                    </c:if>
                    <c:if test="${(status ==40 ||status==11) && versionCodeInit <100004 }">
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
	    		</c:when>
	    		<%-- M站 --%>
	    		<c:otherwise>
	    		    <a href="${basePath }houseMgt/43e881/myHouses" class="goback"></a>
					<c:if test="${status ==40 || status==11}">
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
	    		</c:otherwise>
	    	</c:choose>
		</header>
		<input type="hidden" id="staticUrl" value="${staticResourceUrl}">
		<input name="houseBaseFid" value="${houseBase.fid }" type="hidden"/>
		<div id="main" class="main">
			<div class="box">
			   
				<!-- <div class="scrollDiv" style="position:absolute;top:0;left:0;width: 100%;"> -->
				<div class="myBanner img">
				
				
				
                <c:choose>
				<c:when test="${status == 11 }">
					<img src="${defaultPic }" class="bg setImg">
				</c:when>
				<c:when test="${rentWay==1&&(houseRoomFid=='' || houseRoomFid=='null' || houseRoomFid==null ) }">
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
				
				<ul class="myCenterList" style="margin-bottom:0;">
					<c:if test="${status == 11}">
				     <li class="c_ipt_li bor_b">
						<a href="javascript:showShadedowTips('无法修改审核中房源信息，可预览',1);">
							<span class="c_span">房源信息</span>
							<input  class="c_ipt" type="text" placeholder="请填写房源信息" id="houseMessage" name="houseMessage" value="<c:if test="${houseInfoStaSit.houseStatusSit==1 }">已完成，点击编辑房源信息</c:if><c:if test="${houseInfoStaSit.houseStatusSit==0 }">未完成，点击继续填写房源信息</c:if>" readonly="readonly">
						</a>
					 </li>
					</c:if>
					<c:if test="${status != 11}">
					<li class="c_ipt_li bor_b">
						<a href="${basePath }houseInput/${loginUnauth }/findHouseDetail?houseBaseFid=${houseBase.fid}&rentWay=${rentWay}&flag=1&houseRoomFid=${houseRoomFid}">
							<span class="c_span">房源信息</span>
							<input  class="c_ipt" type="text" placeholder="请填写房源信息" id="houseMessage" name="houseMessage" value="<c:if test="${houseInfoStaSit.houseStatusSit==1 }">已完成，点击编辑房源信息</c:if><c:if test="${houseInfoStaSit.houseStatusSit==0 }">未完成，点击继续填写房源信息</c:if>" readonly="readonly">
							<span class="icon_r icon"></span>
						</a>
					</li>
					</c:if>
					<c:if test="${status != 11}">
					<li class="c_ipt_li bor_b">
						<c:choose>
							<c:when test="${rentWay==1 }">
							    <a href="javascript:gotoHouseApartment();">
							  <%--  <a href="${basePath }roomMgt/${loginUnauth }/toRoomDetail?houseBaseFid=${houseBase.fid}&rentWay=${rentWay}&flag=1&roomFid=${houseRoomFid}&flag=1"> --%>
							</c:when>
							<c:otherwise>
							    <a href="${basePath }roomMgt/${loginUnauth }/houseApartment?houseBaseFid=${houseBase.fid}&rentWay=${rentWay}&flag=1&houseRoomFid=${houseRoomFid}">
							</c:otherwise>
						</c:choose>
						            <span class="c_span">房间信息</span>
								    <input  class="c_ipt" type="text"  id="houseMessage" placeholder="请填写房间信息" id="houseMessage" name="roomMessage" value="<c:if test="${houseInfoStaSit.roomStatusSit==1 }">已完成，点击编辑房间信息</c:if><c:if test="${houseInfoStaSit.roomStatusSit==0 }">未完成，点击继续填写房间信息</c:if>" readonly="readonly">
								    <span class="icon_r icon"></span>
						        </a>
					</li>
					</c:if>
					<c:if test="${status == 11}">
					<li class="c_ipt_li bor_b">
					    <a href="javascript:showShadedowTips('无法修改审核中房源信息，可预览',1);">
				            <span class="c_span">房间信息</span>
						    <input  class="c_ipt" type="text"  id="houseMessage" placeholder="请填写房间信息" id="houseMessage" name="roomMessage" value="<c:if test="${houseInfoStaSit.roomStatusSit==1 }">已完成，点击编辑房间信息</c:if><c:if test="${houseInfoStaSit.roomStatusSit==0 }">未完成，点击继续填写房间信息</c:if>" readonly="readonly">
				        </a>
					</li>
					</c:if>
					<li class="c_ipt_li bor_b">
						<a href="${basePath }houseIssue/${loginUnauth }/showOptionalInfo?houseBaseFid=${houseBase.fid}&rentWay=${rentWay}&flag=1&houseRoomFid=${houseRoomFid}">
							<span class="c_span">可选信息</span>
							<input  class="c_ipt" type="text" id="addMessage" placeholder="请填写可选信息" id="houseMessage" name="addMessage" value="<c:if test="${houseInfoStaSit.extStatusSit==1 }">已完成，点击编辑可选信息</c:if><c:if test="${houseInfoStaSit.extStatusSit==0 }">未完成，点击继续填写可选信息</c:if>" readonly="readonly">
							<span class="icon_r icon"></span>
						</a>
					</li>
					<c:if test="${status != 11}">
				    <li class="c_ipt_li bor_b">
						<a href="javascript:void(0);">
							<span class="c_span">立即预订</span>
							<input  class="c_ipt" type="text" id="addMessage" placeholder="请选择是否立即预订" id="houseMessage" name="addMessage" value="房客可直接预订，无须发送申请" readonly="readonly">
							<span class="c_li_r j_li_r  ${houseBaseExt.orderType==1?'org':'gray' }"><i class="round"></i></span>
						</a>
					</li>
					</c:if>
					<c:if test="${status == 11}">
					<li class="c_ipt_li bor_b">
						<a href="javascript:showShadedowTips('无法修改审核中房源信息，可预览',1);">
							<span class="c_span">立即预订</span>
							<input  class="c_ipt" type="text" id="addMessage" placeholder="请选择是否立即预订" id="houseMessage" name="addMessage" value="房客可直接预订，无须发送申请" readonly="readonly">
							<span class="c_li_r j_li_r  ${houseBaseExt.orderType==1?'org':'gray' }"><i class="round"></i></span>
						</a>
					</li>
					</c:if>
				</ul>
				<ul class="myCenterList"  style="margin-bottom:0;">
					<li class="c_ipt_li bor_b">
	                   <c:if test="${addressStatus == 0}">
	                        <a href="javascript:showShadedowTips('此状态下的房源位置信息不能修改',1);">
								<span class="c_span">房源地址</span>
								<input  class="c_ipt" type="text" placeholder="请填写房源地址" id="houseMessage" value="${houseBase.houseAddr }" name="houseMessage" value="北京市朝阳区酒仙桥中路" readonly="readonly">
							</a>
						</c:if>
					    <c:if test="${addressStatus == 2}">
	                        <a href="javascript:showShadedowTips('无法修改审核中房源信息，可预览',1);">
								<span class="c_span">房源地址</span>
								<input  class="c_ipt" type="text" placeholder="请填写房源地址" id="houseMessage" value="${houseBase.houseAddr }" name="houseMessage" value="北京市朝阳区酒仙桥中路" readonly="readonly">
							</a>
						</c:if>
						<c:if test="${addressStatus == 1}">
						    <a href="${basePath }houseDeploy/${loginUnauth }/toLocationThree?houseBaseFid=${houseBase.fid}&rentWay=${rentWay}&flag=1&houseRoomFid=${houseRoomFid}">
								<span class="c_span">房源地址</span>
								<input  class="c_ipt" type="text" placeholder="请填写房源地址" id="houseMessage" value="${houseBase.houseAddr }" name="houseMessage" value="北京市朝阳区酒仙桥中路" readonly="readonly">
								<span class="icon_r icon"></span>
							</a>
						</c:if>	
					</li>
				  <c:if test="${status == 40}">
					<li class="c_ipt_li ">
						<a href="javascript:showShadedowTips('此状态下的房源类型不能修改',1);">
							<span class="c_span">房源类型</span>
							<input  class="c_ipt j_s_ipt" type="text" placeholder="请选择房源类型" id="houseStyle" value="${houseStyle }" name="checkInLimit" readonly="readonly">
							<!-- <span class="icon_r icon"></span> -->
						</a>

					</li>
					</c:if>
			        <c:if test="${status == 11}">
					<li class="c_ipt_li ">
						<a href="javascript:showShadedowTips('无法修改审核中房源信息，可预览',1);">
							<span class="c_span">房源类型</span>
							<input  class="c_ipt j_s_ipt" type="text" placeholder="请选择房源类型" id="houseStyle" value="${houseStyle }" name="checkInLimit" readonly="readonly">
							<!-- <span class="icon_r icon"></span> -->
						</a>

					</li>
					</c:if>
					<c:if test="${status != 11 && status != 40}">
						<li class="c_ipt_li ">
							<a href="javascript:;">
								<span class="c_span">房源类型</span>
								<input  class="c_ipt j_s_ipt" type="text" placeholder="请选择房源类型" id="checkInLimit" value="${houseStyle }" name="checkInLimit"  readonly="readonly">
							 <select onchange="setValToInput(this,'checkInLimit')"  id="checkInLimitSelect"  name="checkInLimit">
								<c:forEach items="${houseTypeList }" var="stay">
									<option <c:if test="${houseTypeKey == stay.key}">selected="selected"</c:if> value="${stay.key }">${stay.text }</option>
								</c:forEach>
							</select>
								<span class="icon_r icon"></span>
							</a>
	
						</li>
					</c:if>
				</ul>
				<ul class="myCenterList">
					
					<!-- 可修改 出租类型-->
					<li class="c_ipt_li bor_b">
						<a href="javascript:;">
							<span class="c_span">出租类型</span>
							<input  class="c_ipt j_s_ipt" type="text"  id="rentStyle" name="rentStyle" value="${rentStyle}" readonly="readonly">
						</a>

					</li>
					<!-- 如果房源有智能锁显示入口 -->
				<c:if test="${houseBase.isLock == 1}">
					<li class="c_ipt_li">
							<a href="${basePath }smartLock/${loginUnauth }/showPanel?houseBaseFid=${houseBase.fid}&rentWay=${rentWay}&houseRoomFid=${houseRoomFid}">
								<span class="c_tt">智能门锁</span>
							</a>
	                      <span class="icon_r icon"></span>
					</li>
				</c:if>
				<li class="c_ipt_li">
					<a href="javascript:;">
					  <c:choose>
				    	<c:when test="${rentWay == 0||(rentWay == 1&&(houseRoomFid == null || houseRoomFid=='' || houseRoomFid=='null'))}">
				    	<span class="c_tt">房源编码</span>
				    	</c:when>
				    	<c:otherwise>
				    	  <span class="c_tt">房间编码</span>
				    	</c:otherwise>
				       </c:choose>
					<span class="c_sub_tt" id="houseCode">${houseNo}</span>
					</a>
				</li>
				</ul>
				
				<!-- </div> -->
			</div>
			<!--/main-->
		</div>
		
		<c:if test="${status == 10 || status == 21 || status == 30 || status == 41 || status == 50 }">
			<div class="box box_bottom">
				<input type="button" id="issueHouse" value="发布房源" class="org_btn">
			</div>
		</c:if>
	</form>
	<!-- 添加房源名称 -->

	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js?v=js.version.20160815001"
	type="text/javascript"></script>
	<script type="text/javascript"
	src="${staticResourceUrl}/js/layer/layer.js?v=js.version.20160815001"></script>
	<script src="${staticResourceUrl}/js/common.js?v=js.version.20160815001"></script>
	<script src="${staticResourceUrl}/js/iscroll-probe.js?v=js.version.201608168006"></script>
	<script type="text/javascript" src="${staticResourceUrl}/js/common_830.js"></script>
	
	<script type="text/javascript">
	$(function(){

		var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });

		var conHeight = $(window).height()-$('.header').height()-$('.box_bottom').height();

		$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$('#main .box').css({'position':'absolute','width':'100%'});
		myScroll.refresh();
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		
	})
	</script>
	
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
				refuseMsg:'很抱歉，您的房源未通过审核。自如民宿的管家会联系您并提供房屋升级的指导。期待早日相会，请继续加油哦！',
				
				issueHouseUrl:'${basePath }houseIssue/${loginUnauth }/issueHouseInDetail',
				updateHouseTypeUrl:"${basePath }houseInput/${loginUnauth }/updateHouseType",
			};
			
			house.oper={
					
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
										if("${sourceType eq 1 && versionCode ==true }" == "true"){
											window.location.href = window.WebViewFunc.popToParent();;
										} else if("${sourceType eq 2 && versionCode ==true}" == "true"){
											window.location.href = window.popToParent();;
										} else {
											window.location.href = "${basePath }houseMgt/${loginUnauth }/myHouses";
										}
									}else{
										if(	data.rentWay == 1&&(data.roomFid==null||data.roomFid==''||data.roomFid==undefined)){
											if("${sourceType eq 1 && versionCode ==true }" == "true"){
												window.location.href = window.WebViewFunc.popToParent();;
											} else if("${sourceType eq 2 && versionCode ==true}" == "true"){
												window.location.href = window.popToParent();;
											} else {
												window.location.href = "${basePath }houseMgt/${loginUnauth }/myHouses";
											}
										}else{
											//否则刷新当前页面
											window.location.reload(true);
										}
										
									}
								}else{
									if(type == 2){
										showShadedowTips(result.msg,2);
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
			
			
				
				$("#issueHouse").click(function(){
					house.oper.issueHouse(this);
				});
				
				$("#askBtn").click(function(){
					house.oper.showAsk();
				});
				
				$("#checkInLimitSelect").change(function(){
					house.oper.selectVal(this,"checkInLimit");
				});
				$(".j_li_r").each(function(){
					var orderType;
					var houseStatus='${status}';
					if(houseStatus!=11){
						$(this).click(function(){
							if($(this).hasClass("org")){
								$(this).removeClass("org").addClass("gray");
								orderType=2;
							}else{
								$(this).removeClass("gray").addClass("org");
								orderType=1;
							}
							//更新下单类型
							$.ajax({
								url:'${basePath }houseIssue/${loginUnauth }/updateHouseBaseExtByHouseBaseFid',
								data:{"houseBaseFid":'${houseBase.fid}',"orderType":orderType},
								dataType:"json",
								type:"post",
								success:function(result) {
									if(result.code == 0){
	
									}else{
									    showShadedowTips(result.msg,2);
									}
								},
								error:function(result){
									showShadedowTips("未知错误",1);
									$(obj).removeClass("disabled_btn").addClass("org_btn");
								}
							});
						});
					}
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
		
		//分租房间处理 1. 房间roomFid 不为null 直接去房间页面  2. 若为null 去分租户型页面，返回此页面发布房源，发布后，直接到列表页面
		function gotoHouseApartment(){
			var rentWay = '${rentWay}';
			var houseRoomFid = '${houseRoomFid}';
			
			if(rentWay==null||rentWay==""||rentWay==undefined){
				showShadedowTips("参数错误，请联系客服", 2)
				return false;
			}
			
			var urlF = "${basePath }roomMgt/${loginUnauth }/toRoomDetail?houseBaseFid=${houseBase.fid}&rentWay=${rentWay}&flag=1&roomFid=${houseRoomFid}&flag=1";
			if(houseRoomFid==null||houseRoomFid==''||houseRoomFid==undefined||houseRoomFid=='null'||houseRoomFid=='undefined'){
				urlF = "${basePath }roomMgt/${loginUnauth }/houseApartment?houseBaseFid=${houseBase.fid}&rentWay=${rentWay}&flag=3&houseRoomFid=${houseRoomFid}";
			}
			window.location.href = urlF;
		}
		
</script>

</body>
</html>
