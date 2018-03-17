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
    <title>房间</title>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/mui.picker.min.css${VERSION}001" />  
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/css_830.css${VERSION}001">
    <style type="text/css">
	#roomInfoForm, #addSupporting {
		height: 100%;
	}
	#roomInfoForm .bodys, #addSupporting .bodys {
		height: calc(100% - 2.45rem);
		overflow: auto;
		-webkit-overflow-scrolling: touch;
	}
	</style>
</head>
<body>
	<form id="roomInfoForm" >
		<!-- 租住类型 0 整租 1分租 整租只有床位信息 分租必须有床 -->
		<header class="header">
		     <a href="javascript:;" id="goback" class="goback" ></a>
			<h1>房间信息</h1>
		</header>
		<div class="bodys">
			<div id="main" class="main">
				<div class="box">
					<!-- TODO:还未添加床位信息时显示 -->
					<!-- <p class="noBedMessage">您还没有添加床位</p> -->
					<input type="hidden" name="houseBaseFid" value="${houseBaseFid}">
					<input type="hidden" name="fid" value="${houseRoomFid}">
					<input type="hidden" id="priceLow" value="${priceLow }">
					<input type="hidden" id="priceHigh" value="${priceHigh }">
					<input type="hidden" name="houseRoomFid" value="${roomFid}">
					<ul id="roomList" class="myCenterList">
						<li class="c_ipt_li bor_b c_ipt_li_none">
							<a href="javascript:void(0);"> 
								<!-- TODO： 保存下一步时验证房间名称字数是否小于10-->
								<span class="c_span">房间名称</span>
								<textarea rows="1" class="c_ipt j_ipt" data-type="name" type="text" placeholder="请填写房间名称" id="roomName"  name="roomName" minlength="10" maxlength="30">${houseRoomMsg.roomName }</textarea>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
						<c:choose>
							<c:when test="${houseRoomMsg.roomStatus eq 20 || houseRoomMsg.roomStatus eq 21 || houseRoomMsg.roomStatus eq 40 }">
								<li class="c_ipt_li bor_b"> 
									<a href="javascript:void(0);" onclick="showPromptMsg();"> 
										<span class="c_span">房间面积</span>
										<input type="hidden" id="roomArea" name="roomArea" value="${houseRoomMsg.roomArea }">
										<%-- <span class="c_ipt j_ipt">${houseRoomMsg.roomArea }m²</span> --%>
										<span class="c_ipt j_ipt c_disabled">${houseRoomMsg.roomArea }m²</span>
									</a>
								</li>
							</c:when>
							<c:otherwise>
								<li class="c_ipt_li bor_b c_ipt_li_none">
									<a href="javascript:void(0);"> 
										<span class="c_span">房间面积</span>
										<c:if test="${empty houseRoomMsg.roomArea}">
											<input class="c_ipt j_ipt" data-type="area" type="tel" placeholder="请填写房间面积" id="roomArea" name="roomArea" value="" maxlength="6">
										</c:if>
										<c:if test="${!empty houseRoomMsg.roomArea}">
											<input class="c_ipt j_ipt" data-type="area" type="tel" placeholder="请填写房间面积" id="roomArea" name="roomArea" value="${houseRoomMsg.roomArea }" maxlength="6">
										</c:if>
										<span class="icon_r icon icon_clear"></span>
									</a>
								</li>
							</c:otherwise>
						</c:choose>

						<li class="c_ipt_li c_ipt_li_none bor_b"> 
							<a href="javascript:void(0);"> 
								<span class="c_span">是否独卫</span>
								<input class="c_ipt j_ipt" type="text" placeholder="请选择是否独卫" id="toilet" value="" readonly="readonly">
								<select onchange="setValToInput(this,'toilet')" id="toiletList" name="isToilet">
									<option value="">请选择是否独卫</option>
									<option value="0" <c:if test="${houseRoomMsg.isToilet == 0 }">selected</c:if>>否</option>
									<option value="1" <c:if test="${houseRoomMsg.isToilet == 1 }">selected</c:if>>是</option>
								</select>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
						<li class="c_ipt_li c_ipt_li_none bor_b"> 
							<a href="javascript:void(0);"> 
								<span class="c_span">入住人数限制</span>
								<input class="c_ipt j_ipt" type="text" placeholder="请选择入住人数限制" id="person" value="" readonly="readonly">
								<select onchange="setValToInput(this,'person')" id="personList" name="checkInLimit">
									<option value="">请选择入住人数限制</option>
									<c:forEach items="${limitList }" var="obj">
										<option value="${obj.key }" <c:if test="${houseRoomMsg.checkInLimit == obj.key }">selected</c:if>>${obj.text }</option>
									</c:forEach>
								</select>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
						<c:if test="${empty roomPrice}"	>
							<c:choose>
								<c:when test="${empty houseRoomMsg.roomPrice}">
									<li class="c_ipt_li c_ipt_li_none bor_b ">
										<a href="javascript:void(0);" onclick="toPriceDetail()">
											<span class="c_span">价格</span>
											<input class="c_ipt j_ipt" data-type="price" type="tel" placeholder="请设置价格"   maxlength="5" value="" readonly="readonly" />
											<span class="icon_r icon "></span>
										</a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="c_ipt_li bor_b">
										<a href="javascript:void(0);" onclick="toPriceDetail()">
											<span class="c_span">价格</span>
											<input class="c_ipt j_ipt"  type="tel" placeholder="请设置价格" value="${priceSetting}" id="roomPrice"  maxlength="5" readonly="readonly"/>
											<span class="icon_r icon "></span>
										</a>
									</li>
								</c:otherwise>
							</c:choose>
						</c:if>
							<c:if test="${!empty roomPrice && !empty roomFid}">
								<li class="c_ipt_li bor_b">
									<a href="javascript:void(0);" onclick="toPriceDetail()">
										<span class="c_span">价格</span>
										<input class="c_ipt j_ipt"  type="tel" placeholder="请设置价格" value="${priceSetting}" id="roomPrice" maxlength="5" readonly="readonly"/>
										<span class="icon_r icon "></span>
									</a>
								</li>
							</c:if>
						<li class="c_ipt_li c_ipt_li_none bor_b">
							<a href="javascript:void(0);"> 
								<span class="c_span">清洁费</span>
								<input type="hidden" id="cleanPer" value="${cleanPer }">
								<c:choose>
									<c:when test="${empty houseRoomMsg.roomCleaningFees}">
										<input class="c_ipt j_ipt" data-type="price" type="tel" placeholder="请填写清洁费(0元表示不收取)" id="roomCleaningFees" name="roomCleaningFees" maxlength="5" value=""/> 
									</c:when>
									<c:otherwise>
										<input class="c_ipt j_ipt" data-type="price" type="tel" placeholder="请填写清洁费(0元表示不收取)" id="roomCleaningFees" name="roomCleaningFees" maxlength="5" value="${fn:split(houseRoomMsg.roomCleaningFees/100, '.')[0] }"/> 
									</c:otherwise>
								</c:choose>
								<span class="icon_r icon icon_clear"></span>
							</a>
						</li>
						<c:forEach items="${bedList }" var="bed" varStatus="no">
							<li id="li_bed${no.count }" class="c_ipt_li j_ipt_li bor_b mui-table-view-cell mui-transitioning"> 
								<div id="bedNum" class="mui-slider-right mui-disabled" data-roomfid=""><a class="mui-btn mui-btn-red" style="-webkit-transform: translate(0px, 0px);">删除</a></div>
								<a href="javascript:void(0);" class="">
									<div class="mui-slider-handle">
										<input class="fid" type="hidden" name="bedList[${no.index }].fid" value="${bed.fid }">
										<input class="bedType" type="hidden" name="bedList[${no.index }].bedType" value="${bed.bedType }">
										<input class="bedSize" type="hidden" name="bedList[${no.index }].bedSize" value="${bed.bedSize }">
										<span class="c_span j_span">床位${no.count }</span>
										<input class="c_ipt j_ipt" type="text" placeholder="请选择床位信息" id="bed${no.count }" value="${bed.bedTypeDis } ${bed.bedSizeDis }" readonly="readonly">
										<span class="icon_r icon icon_clear"></span>
									</div>
								</a>
						    </li>
					    </c:forEach>
					</ul>
				</div>
				<!--/main-->
			</div>
		</div>
		<ul class="bottomBtns clearfix">
			<li id="addBedBtn" class="addBtn">添加床位</li>
			<!-- <li id="submitBtn" class="submitBtn">确定</li> -->
			<li >
				<input type="button" id="submitBtn" class="submitBtn " value="确定">
			</li>
			
		</ul>
		</form>
		<!-- 添加房源名称 -->

		<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
		<script type="text/javascript" src="${staticResourceUrl }/js/layer/layer.js${VERSION}001"></script>
		<script src="${staticResourceUrl }/js/common2.js${VERSION}001"></script>
		<script src="${staticResourceUrl }/js/iscroll-probe.js${VERSION}001"></script>
		<script type="text/javascript" src="${staticResourceUrl }/js/mui.min.js${VERSION}001"></script>
		<script type="text/javascript" src="${staticResourceUrl }/js/mui.picker.min.js${VERSION}001"></script>
		<script type="text/javascript" src="${staticResourceUrl }/js/mui.poppicker.js${VERSION}001"></script>
		<script type="text/javascript" src="${staticResourceUrl }/js/common_830_2.js${VERSION}001"></script>

		<script type="text/javascript">
			var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });
			var conHeight = $$(window).height()-$$('.header').height()-$$('.bottomBtns').height();
			$$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
			$$('#main .box').css({'position':'absolute','width':'100%'});
			myScroll.refresh();
			document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		</script>
		<script type="text/javascript">
			
		</script>
		<script type="text/javascript">
			function isNullOrBlank(obj){
				return obj == undefined || obj == null || $$.trim(obj).length == 0;
			}
			function toPriceDetail(){
				var houseBaseFid = '${houseBaseFid}';
				var houseRoomFid = '${houseRoomFid }';
				var roomName = $$("#roomName").val();
				var roomArea = $$("#roomArea").val();
				var isToilet=$$("#toiletList").val();
				var cfee=$$("#roomCleaningFees").val();
				var checkInLimit=$$("#personList").val();
				if(isNullOrBlank(houseBaseFid)){
					showShadedowTips("房源不存在",1);
					$$("#submitBtn").removeAttr("disabled");
					return;
				}
				//分租房间第一次创建时，需要将房东之前填写的内容传递保存
				if(isNullOrBlank(houseRoomFid)){
					var redisRoomFid = '${roomFid}';
					roomName = encodeURI(roomName);
					roomName = encodeURI(roomName);
					roomArea = roomArea.replace("m²","");
					cfee = cfee.replace("元","");
					window.location.href = "${basePath}houseIssue/${loginUnauth}/toPriceDetail?houseBaseFid=${houseBaseFid}&flag=${flag}&houseRoomFid=${houseRoomFid }&rentWay=1&roomName="+roomName+"&roomArea="+roomArea+"&cfee="+cfee+"&isToilet="+isToilet+"&limit="+checkInLimit+"&redisRoomFid="+redisRoomFid;
				}else{
					window.location.href = "${basePath}houseIssue/${loginUnauth}/toPriceDetail?houseBaseFid=${houseBaseFid}&flag=${flag}&houseRoomFid=${houseRoomFid }&rentWay=1";
				}
			}
			$$(function(){
				$$("#submitBtn").click(function(){
					//校验是否填写了价格
					var priceSetting = '${priceSetting}';
					if(isNullOrBlank(priceSetting)){
						showShadedowTips("请设置价格",1);
						$$("#submitBtn").removeAttr("disabled");
						return;
					}
					$$(this).attr("disabled", "disabled");
					submitBed();
				})
				
				function submitBed(){
					if($$("#submitBtn").hasClass("submitBtn_disabled")){
						showShadedowTips("请先完善床位信息",1);
						$$("#submitBtn").removeAttr("disabled");
						return;
					}
					var beds = $$(".j_ipt_li").length;
					if(beds < 1){
						mui.confirm('您至少需要添加1个床位！', '', ['关闭'], function(e) {});
						return;
					}
					$$.ajax({
						beforeSend: function(){
							var valid = validateParams();
							if(!valid){
								$$("#submitBtn").removeAttr("disabled");
							}
							return valid;
						},
	            	    url: "${basePath}roomMgt/${loginUnauth}/saveRoomInfo",
	            	    dataType: "json",
	            	    async: false,
	            	    data: toJson($$("#roomInfoForm").serializeArray()),
	            	    type: "POST",
	            	    success: function (result) {
	            	    	if(result.code === 0){
	            	    		var flag;
	            	    		if("${empty flag}" === "false"){
	            	    			flag = '${flag }';
	            	    		}
	            	    		if(flag == 1){
	            	    			window.location.href = "${basePath}houseInput/${loginUnauth}/goToHouseUpdate?houseBaseFid=${houseBaseFid}&flag=${flag}&houseRoomFid=${houseRoomFid }&rentWay=1";
	            	    		}else{
	            	    			window.location.href = "${basePath}roomMgt/${loginUnauth}/houseApartment?houseBaseFid=${houseBaseFid}&flag=${flag}";
	            	    		}
	            	    	} else {
								showShadedowTips("操作失败",1);
								$$("#submitBtn").removeAttr("disabled");
	            	    	}
	            	    },
	            	    error:function(result){
							showShadedowTips("未知错误",1);
							$$("#submitBtn").removeAttr("disabled");
						}
	           	    });
				}
				
				function validateParams(){
					var roomName = $$("#roomName").val();
			        if(checkNullOrBlank(roomName)){
			            showShadedowTips("请填写房间名称",1);
			            $$("#roomName").val('');
			            $$("#roomName").focus();
			            return false;
			        }
			        if($$.trim(roomName).length < 10){
			            showShadedowTips("房间名称长度为10-30字",1);
			            $$("#roomName").focus();
			            return false;
			        }
			        var roomArea = $$("#roomArea").val();
			        if(checkNullOrBlank(roomArea)){
			            showShadedowTips("请填写房间面积",1);
			            $$("#roomArea").val('');
			            $$("#roomArea").focus();
			            return false;
			        }
			        roomArea = roomArea.replace("m²","");
			        var isPositiveNumber = new RegExp("^\[1-9][0-9]*(.[0-9])?$").test(roomArea);
			        if(!isPositiveNumber){
		            	showConfirm("请填写正确房间面积", $$("#roomArea"));
			            return false;
			        }
		        	/*var roomPrice = $$("#roomPrice").val();
		            if(checkNullOrBlank(roomPrice)){
		                showShadedowTips("请填写房间价格",1);
			            $$("#roomPrice").val('');
			            $$("#roomPrice").focus();
			            return false;
		            }
		            roomPrice = roomPrice.replace("元","");
			        var regexp = new RegExp("^\[1-9][0-9]*$");
			        var isPositiveInteger = regexp.test(roomPrice);
		            if(!isPositiveInteger){
		            	showConfirm("请填写正确房间价格", $$("#roomPrice"));
			            return false;
		            }
		            var priceLow=parseFloat($$("#priceLow").val());
		            //判断房间价格最低价格
		            if(parseFloat(roomPrice)<priceLow){
		            	var str = "房源价格不能低于"+priceLow+"元";
		            	showConfirm(str, $$("#roomPrice"));
			            return false;
		            }
		            var priceHigh=parseFloat($$("#priceHigh").val());
		            //判断房间价格最高价格
		            if(parseFloat(roomPrice)>priceHigh){
		            	var str = "房源价格不能高于"+priceHigh+"元";
		            	showConfirm(str, $$("#roomPrice"));
			            return false;
		            }*/
		            var isToilet=$$("#toiletList").val();
		            if(checkNullOrBlank(isToilet)){
		                showShadedowTips("请选择是否独卫",1);
			            $$("#toiletList").val('');
			            $$("#toiletList").focus();
			            return false;
		            }
		            var checkInLimit=$$("#personList").val();
		            if(checkNullOrBlank(checkInLimit)){
		                showShadedowTips("请选择入住人数限制",1);
			            $$("#personList").val('');
			            $$("#personList").focus();
			            return false;
		            }
		            var roomCleaningFees=$$("#roomCleaningFees").val();
		            if(checkNullOrBlank(roomCleaningFees)){
		                showShadedowTips("请填写清洁费",1);
			            $$("#roomCleaningFees").val('');
			            $$("#roomCleaningFees").focus();
			            return false;
		            }
		            roomCleaningFees = roomCleaningFees.replace("元","");
			        regexp  = new RegExp("^\[0-9][0-9]*$");
			        isPositiveNumber = regexp.test(roomCleaningFees);
		            if(!isPositiveNumber){
		            	showConfirm("请填写正确清洁费价格", $$("#roomCleaningFees"));
			            return false;
		            }
					var roomPrice = '${roomPrice}';
					if(isNullOrBlank(roomPrice)){
						roomPrice ='${houseRoomMsg.roomPrice}';
					}
		            var cleanPer=Number($$("#cleanPer").val());
		            //判断清洁费最高价格
		            if(parseFloat(roomCleaningFees)>parseFloat(roomPrice*cleanPer).toFixed(2)){
		            	var str = "清洁费不能高于"+parseFloat(roomPrice*cleanPer).toFixed(2)+"元";
		            	showConfirm(str, $$("#roomCleaningFees"));
			            return false;
		            }
			        
			        var listSize = $$(".j_ipt_li").length;
			        if(listSize == 0){
			            showShadedowTips("请添加床位",1);
			            return false;
			        }
			        
			        var valid = true;
			        $$('.j_ipt_li').each(function(){
			        	var bedType = $$(this).find(".bedType").val();
			        	var bedSize = $$(this).find(".bedSize").val();
			        	if(checkNullOrBlank(bedType) || checkNullOrBlank(bedSize)){
			        		valid = false;
				            showShadedowTips("请选择床位信息",1);
			        		return false;// 跳出循环
			        	}
			        });
			        return valid;
				}
				
				function toJson(array){
					var json = {};
					$$.each(array, function(i, n){
						var key = n.name;
						var value = n.value;
						if(key == "roomArea"){
							value = parseFloat(value);
						}
						if(key == "roomCleaningFees"){
							value = parseFloat(value)*100;
						}
						if($$.trim(value).length != 0){
							json[key] = value;
						}
					});
					return json;
				}	
			})
			
			function showConfirm(str,$obj){
		        layer.open({
		        	content: '<div class="loginTips" style="line-height: 1.1rem">'+str+'</div>',
			        btn:['确定'],
			        yes: function(){
			            //直接跳转到手机注册
			        	$obj.val('');
			        	$obj.focus();
			            layer.closeAll(); 
			        }
		        });
			}
		</script>
		<script>
			(function($, doc) {
				$.init();
				$.ready(function() {
					var rulerData = ${rulerData };
			
					//级联示例
					var bedPicker = new $.PopPicker({
						layer: 2
					});
					bedPicker.setData(rulerData);
					
					var showRulerPickerButton = doc.getElementById('addBedBtn');
					showRulerPickerButton.addEventListener('tap', function(event) {
						if($$("#addBedBtn").hasClass("addBtn_disabled")){
							showShadedowTips("请先完善床位信息",1);
						} else {
							bedPicker.show(function(items) {
								var beds = $$(".j_ipt_li").length;
								if(beds > 6){
									mui.confirm('您最多可添加7个床位！', '', ['关闭'], function(e) {});
									return;
								}
								var _str='';
								_str+='<li id="li_bed'+(beds+1)+'" class="c_ipt_li j_ipt_li bor_b mui-table-view-cell mui-transitioning unfinished_bed"> '
									+	'<div class="mui-slider-right mui-disabled" data-roomfid="">'
									+		'<a class="mui-btn mui-btn-red" style="-webkit-transform: translate(0px, 0px);">删除</a>'
									+	'</div>'
									+	'<a href="javascript:void(0);">'
									+		'<input class="fid" type="hidden" name="bedList['+beds+'].fid">'
									+		'<input class="bedType" type="hidden" name="bedList['+beds+'].bedType" value="'+items[0].value+'">'
									+		'<input class="bedSize" type="hidden" name="bedList['+beds+'].bedSize" value="'+items[1].value+'">'
									+		'<div class="mui-slider-handle"><span class="c_span j_span">床位'+(beds+1)+'</span>'
									+		'<input class="c_ipt j_b_ipt" type="text" placeholder="请选择床位信息" id="bed'+(beds+1)+'" value="'+items[0].text + " " + items[1].text+'" readonly="readonly"></div>'
									+		'<span class="icon_r icon"></span>'
									+	'</a>'
									+ '</li>';
								$$("#roomList").append(_str);
								myScroll.refresh();
								checkButtonAvailable();
							});
						}
					}, false);;
			
					$$("#roomList").on('tap','.j_b_ipt',function(){
						var liId = 'li_'+this.id;
						var iptId = this.id;
						var iptButton = doc.getElementById(iptId);
						bedPicker.show(function(items) {
							iptButton.value = items[0].text + " " + items[1].text;
							$$("#"+liId).find(".bedType").val(items[0].value);
							$$("#"+liId).find(".bedSize").val(items[1].value);
							$$("#"+liId).removeClass("c_ipt_li_none");
							$$("#"+liId).removeClass("unfinished_bed").addClass("finished_bed");
							checkButtonAvailable();
						});
					})
				});
			})(mui, document);
		</script>
		<script type="text/javascript">
	        mui.init();
			(function($) {
				$('#roomList').on('tap', '.mui-btn', function(event) {
					var elem = this;
					var li = elem.parentNode.parentNode;
					var liSize = $$('#roomList .j_ipt_li').size();
					if(liSize==1){
						mui.confirm('您至少保留1个床位！', '', ['关闭'], function(e) {
							$.swipeoutClose(li);
						});
					}else{
						mui.confirm('确认删除该条床位信息？', '', btnArray, function(e) {
							var liSize = $$('#roomList .j_ipt_li').size();
							if (e.index == 1) { //这是确认
								var bedFid = $$(li).find(".fid").val();
						        if(checkNullOrBlank(bedFid)){
									li.parentNode.removeChild(li);
									countLiIndex();
									checkButtonAvailable();
						        } else {
						        	$$.ajax({
					            	    url: "${basePath}roomMgt/${loginUnauth}/delBed",
					            	    dataType:"json",
					            	    data: {bedFid:bedFid},
					            	    type: "POST",
					            	    success: function (result) {
					            	    	if(result.code === 0){
												li.parentNode.removeChild(li);
												countLiIndex();
												checkButtonAvailable();
					            	    	} else {
												showShadedowTips(result.msg,1);
					            	    	}
					            	    },
					            	    error:function(result){
											showShadedowTips("未知错误",1);
										}
					           	    });
						        }
							} else {//这是取消
								setTimeout(function() {
									$.swipeoutClose(li);
								}, 0);
							}
						});
					}
				});
				var btnArray = ['取消', '确认'];
			})(mui);
	
	    	function countLiIndex(){
	    		var n = $$('.j_ipt_li').first().index();
				$$('.j_ipt_li').each(function(){
	    			$$(this).attr("id","li_bed"+($$(this).index()-n+1));
	    		});
	    		$$('.j_b_ipt').each(function(){
	    			$$(this).attr("id","bed"+($$(this).parents("li").index()-n+1));
	    			$$(this).attr("name","bed"+($$(this).parents("li").index()-n+1));
	    		});
	    		$$('.j_span').each(function(){
	    			$$(this).text("床位"+($$(this).parents("li").index()-n+1));
	    		});
	    		$$('.fid').each(function(){
	    			$$(this).attr("name","bedList["+($$(this).parents("li").index()-n)+"].fid");
	    		});
	    		$$('.bedType').each(function(){
	    			$$(this).attr("name","bedList["+($$(this).parents("li").index()-n)+"].bedType");
	    		});
	    		$$('.bedSize').each(function(){
	    			$$(this).attr("name","bedList["+($$(this).parents("li").index()-n)+"].bedSize");
	    		});
	    	}
			
			/**
			 * 校验按钮是否可用
			 */
	    	function checkButtonAvailable(){
	    		var listSize = $$("#roomList .j_ipt_li").size();
	    		var unsavedSize = $$('#roomList .unfinished_bed').size();//页面新增床铺数量
	    		if(listSize > 7){
	    			$$('#addBedBtn').addClass('addBtn_disabled');
	    		} else {
	    			$$('#addBedBtn').removeClass('addBtn_disabled');
	    		}
	    		if(listSize > 0 && listSize <= 7){
	    			$$('#submitBtn').removeClass('submitBtn_disabled');
	    		} else {
	    			$$('#submitBtn').addClass('submitBtn_disabled');
	    		}
	    	}
	    	
	    	function checkNullOrBlank(param){
	    		return typeof(param) == 'undefined' || param == null || $$.trim(param).length == 0;
	    	}
		</script>
		<script type="text/javascript">
			$$(function(){
				init();
			});
			
			function init(){
				var toiletElem = document.getElementById("toiletList");
				if(!checkNullOrBlank(toiletElem.value)){
					setValToInput(toiletElem, "toilet");
				}
				var limitElem = document.getElementById("personList");
				if(!checkNullOrBlank(limitElem.value)){
					setValToInput(limitElem, "person");
				}
				$$("#roomName").blur();
				$$("#roomArea").blur();
//				$$("#roomPrice").blur();
				$$("#roomCleaningFees").blur();
				checkButtonAvailable();
			}
			
			function showPromptMsg(){
				showShadedowTips("此状态下的房间面积不能修改",1);
			}
		</script>
		<script type="text/javascript">
		$$("#goback").click(function(){
			 var roomStatus = '${houseRoomMsg.roomStatus}';
			 var flag = '${flag}';
			 if(!(roomStatus == 20 || roomStatus == 21 || roomStatus == 40 )){
				 var roomName = '${houseRoomMsg.roomName }';
				 var roomArea = '${houseRoomMsg.roomArea }';
				 var roomPrice = '${houseRoomMsg.roomPrice}';
				 var roomCleaningFees = '${houseRoomMsg.roomCleaningFees}';
				 layer.open({
				 	shade: true,
				    shadeClose:true,
			    	content: '<div class="loginTips" style="line-height: 1.1rem">您填写的信息未保存，确认放弃此次编辑？</div>',
			    	btn:['继续编辑', '放弃'],
			    	no: function(){
			    		if(flag != 1){
			    			window.location.href = '${basePath}roomMgt/${loginUnauth}/houseApartment?houseBaseFid=${houseBaseFid}&flag=${flag}';
			    		}else if(flag == 1){
			    			window.location.href = '${basePath}houseInput/${loginUnauth}/goToHouseUpdate?houseBaseFid=${houseBaseFid}&flag=${flag}&houseRoomFid=${houseRoomFid }&rentWay=1';
			    		}
			        }, 
			    	yes: function(){
			    		layer.closeAll();
			        }
			  	 });
			 } else {
	    		if(flag != 1){
	    			window.location.href = '${basePath}roomMgt/${loginUnauth}/houseApartment?houseBaseFid=${houseBaseFid}&flag=${flag}';
	    		}else if(flag == 1){
	    			window.location.href = '${basePath}houseInput/${loginUnauth}/goToHouseUpdate?houseBaseFid=${houseBaseFid}&flag=${flag}&houseRoomFid=${houseRoomFid }&rentWay=1';
	    		}
			 }
		});
		</script>
</body>
</html>
