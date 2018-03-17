<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="applicable-device" content="mobile">
	<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="yes" name="apple-mobile-web-app-capable"/>
	<meta content="yes" name="apple-touch-fullscreen"/>
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="format-detection" content="telephone=no">
	<script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
	<title>订单详情</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles.css${VERSION}003">

</head>
<body>

<header class="header">
	<c:if test="${sourceOrg == 1 }">
		<a href="javascript:;" onclick="window.WebViewFunc.popToParent();" class="goback"></a>
	</c:if>
	<c:if test="${sourceOrg == 2 }">
		<a href="javascript:;" onclick="window.popToParent();" class="goback"></a>
	</c:if>
	<c:if test="${sourceOrg == 0 }">
		<c:if test="${flag == 1}"><a href="javascript:;" onclick="history.back();" class="goback"></a></c:if>
		<c:if test="${flag != 1}"><a href="${basePath}orderland/43e881/showlist" class="goback"></a></c:if>
	</c:if>
	<h1>订单详情</h1>
</header>

<div class="main" id="main">
	<div>
		<div class="d_banner img">
			<img src="${detail.housePicUrl}" class="setImg">
			<div class="txt">
				<h3>${detail.orderStatusName }</h3>
				<p>${detail.orderSn }</p>
			</div>
		</div>

		<div class="bgF">
			<div class="detail_top">
				<p class="t">${detail.houseName }
					<span>预订时间：${detail.createTime }</span>
				</p>
				<p class="line2 fz7 boxP075"></p>
			</div>
			<div class="detail_center clearfix">
				<div class="c">
					<span class="icon icon_in">入住</span>
					<p>${detail.startTime }</p>
				</div>
				<div class="c">
					<span class="icon icon_out">退房</span>
					<p>${detail.endTime }</p>
				</div>
			</div>

		</div>

		<h2 class="tit">订单金额</h2>
		<div class="bgF">
			<div class="detail_top">
				<p class="t pr" onclick="shNew(this,'detail_center_1')">房客支付： <span class="org num">￥${detail.tenantNeedPay }</span><span class="icon icon_r icon_t"></span></p>
				<p class="line2 fz7 boxP075"></p>
			</div>
			<div class="detail_center uhide" id="detail_center_1">
				<div class="money_detail">
					<c:if test="${detail.bfRentalMoney != '0.00'}">
						<p class="d"><a>房费<span>￥${detail.bfRentalMoney }</span></a></p>
					</c:if>
					<c:if test="${detail.discountMoney != '0.00'}">
						<p class="d"><a>房费优惠金额<span>￥${detail.discountMoney }</span></a></p>
					</c:if>
					<c:if test="${detail.otherMoney != '0.00'}">
						<p class="d"><a>其他消费<span>￥${detail.otherMoney }</span></a></p>
					</c:if>
					<c:if test="${detail.depositMoney != '0.00'}">
						<p class="d"><a>押金<span>￥${detail.depositMoney }</span></a></p>
					</c:if>
					<c:if test="${detail.penaltyMoney != '0.00'}">
						<p class="d"><a>违约金<span>￥${detail.penaltyMoney }</span></a></p>
					</c:if>
					<c:if test="${detail.cleanMoney != '0.00'}">
						<p class="d"><a>清洁费<span>￥${detail.cleanMoney }</span></a></p>
					</c:if>
					<c:if test="${detail.userCommMoney != '0.00'}">
						<p class="d"><a>房客服务费<span>￥${detail.userCommMoney }</span></a></p>
					</c:if>
					<c:if test="${detail.refundMoney != '0.00'}">
						<p class="d"><a>退款金额<span>￥${detail.refundMoney }</span></a></p>
					</c:if>
				</div>
			</div>

			<%--<c:if test="${detail.payStatus == 0}">
				<!-- 未支付状态房东端收入显示0 -->
				<div class="detail_top">
					<p class="t bd0 pr" onclick="sh(this,'detail_center_2')">本单${detail.incomeName}： <span class="org num">￥0</span><span class="icon icon_r icon_t"></span></p>
					<p class="line2 fz7 boxP075"></p>
				</div>
			</c:if>
			<c:if test="${detail.payStatus == 1}">--%>
				<!-- 如果已经支付并且房东有收益 则显示明细，否则显示房东收益为0 -->
				<c:if test="${detail.landlordIncomeMoney != '0.00'}">
					<div class="detail_top">
						<p class="t bd0 pr" onclick="shNew(this,'detail_center_2')">本单${detail.incomeName}： <span class="org num">￥${detail.landlordIncomeMoney }</span><span class="icon icon_r icon_t"></span></p>
						<p class="line2 fz7 boxP075"></p>
					</div>
					<div class="detail_center uhide" id="detail_center_2">
						<div class="money_detail">
							<!-- 折扣房租 -->
							<c:if test="${detail.rentalMoney != '0.00'}">
								<p class="d"><a>房费<span>￥${detail.rentalMoney }</span></a></p>
							</c:if>
							<c:if test="${detail.otherMoney != '0.00'}">
								<p class="d"><a>其他消费<span>￥${detail.otherMoney }</span></a></p>
							</c:if>
							<c:if test="${detail.penaltyMoney != '0.00'}">
								<p class="d"><a>违约金<span>￥${detail.penaltyMoney }</span></a></p>
							</c:if>
							<c:if test="${detail.cleanMoney != '0.00'}">
								<p class="d"><a>清洁费<span>￥${detail.cleanMoney }</span></a></p>
							</c:if>
							<c:if test="${detail.lanCommMoney != '0.00'}">
								<p class="d"><a>房东服务费<span>-￥${detail.lanCommMoney }</span></a></p>
							</c:if>
							<c:if test="${detail.discountMoney != '0.00'}">
								<p class="d"><a>房费优惠金额<span>-￥${detail.discountMoney }</span></a></p>
							</c:if>
						</div>
					</div>
				</c:if>
				<!-- 如果房东收益为0则直接显示0 -->
				<c:if test="${detail.landlordIncomeMoney == '0.00'}">
					<div class="detail_top">
						<p class="t bd0 pr" onclick="shNew(this,'detail_center_2')">本单${detail.incomeName}： <span class="org num">￥0</span><span class="icon icon_r icon_t"></span></p>
						<p class="line2 fz7 boxP075"></p>
					</div>
				</c:if>
				
				<!-- 本单罚款 -->
				<c:if test="${!empty detail.financePenaltyVo and !empty detail.financePenaltyVo.penaltyFeeStr}">
					<div class="detail_top">
						<p class="t bd0 pr" >本单罚款： <span class="org num">-￥${detail.financePenaltyVo.penaltyFeeStr}</span></p>
						<p class="line2 fz7 boxP075"></p>
					</div>
				</c:if>
				
			 <!-- 被罚详情 -->
			 <c:if test="${!empty detail.listFinancePenaltyPayRelVo and  fn:length(detail.listFinancePenaltyPayRelVo)>0}">
					<div class="detail_top">
						<p class="t bd0 pr" onclick="shNew(this,'detail_center_3')">罚款明细 <span class="org num"></span><span class="icon icon_r icon_t"></span></p>
						<p class="line2 fz7 boxP075"></p>
					</div>
					<div class="detail_center uhide" id="detail_center_3">
						<div class="money_detail">
							<!-- 被罚明细-->
							<c:forEach var="financePenaltyPay" items="${detail.listFinancePenaltyPayRelVo}">
								<p class="d"><a>罚款订单:${financePenaltyPay.penaltyOrderSn }<span>-￥${financePenaltyPay.totalFeeStr }</span></a></p>
							</c:forEach>
						</div>
					</div>
				</c:if>
			<%--</c:if>
		</div>--%>

		<!-- 待房东确认额外消费 显示输入框让房东输入 -->
	<%-- 	<c:if test="${detail.orderStatus == 50 || detail.orderStatus == 51}">
			<h2 class="tit">其他费用</h2>
			<div class="bgF">
				<div class="detail_top">
					<p class="t bd0 pr">
						金额：<span class="org num">
								<input type="tel" id="otherMoneyValue" class="org" placeholder="请输入合计金额" onafterpaste="keyUpMon(this)" onkeyup = "keyUpMon(this)" maxlength="5">
							</span>
						<span class="icon icon_r icon_t"></span>
					</p>
					<p class="line2 fz7 boxP075"></p>
				</div>
				<div class="detail_center">
					<div class="input_detail">
						<textarea name="" id="otherMoneyDes" placeholder="请输入其它费用明细，例：&#13;&#10;矿泉水1瓶：￥5:00&#13;&#10;打碎玻璃杯一个：￥40.00"></textarea>
					</div>
				</div>
			</div>
		</c:if> --%>

		<!-- 结算中或者已完成状态会有其他费用展示，不过是只读的 -->
		<c:if test="${detail.orderStatus == 60 || detail.orderStatus == 61 || detail.orderStatus == 70 || detail.orderStatus == 71}">
			<!-- 如果其他消费为0不显示 -->
			<c:if test="${detail.otherMoney != '0.00'}">
				<h2 class="tit">其他费用</h2>
				<div class="bgF">
					<div class="detail_top">
						<p class="t bd0 pr">
							金额：<span class="org num">￥${detail.otherMoney }</span>
						</p>
						<p class="line2 fz7 boxP075"></p>
					</div>
					<div class="detail_center">
						<div class="input_detail">
							<p class="txt">${detail.otherMoneyDes }</p>
						</div>
					</div>
				</div>
			</c:if>
		</c:if>
	<!-- 页面调整 -->
		<!-- 其他费用 -->
<!-- 待房东确认额外消费 显示输入框让房东输入 -->
	<c:if test="${detail.orderStatus == 50 || detail.orderStatus == 51}">
		<div class="bgF">
			<ul class="myPriceList">
				<li class="c_li">
					<a href="javascript:void(0);">		
						<span class="S_priceListBigTit">是否有其他费用</span>
						<span class="S_priceListSmallTit">包括额外消费、赔偿等金额</span>
						<span class="c_li_r gray" onclick="OrderDetail.choseIsOther(this);"><i class="round"></i></span>
					</a>
				</li>
				<!-- TODO:
				是否有其他费用 隐藏域 用于提交和判断 0 － 否 1 － 是 -->
				<input id="isOtherPrice" type="hidden" value="0">
				<!-- 没有金额时 加上class c_ipt_li_none
				有金额时 去掉class c_ipt_li_none
				是否有其他费用 否增加 style="display:none;"
				是否有其他费用 是去掉 style="display:none;" -->
				<li id="otherMoney" class="c_ipt_li c_ipt_li_none" style="display:none;">
					<a href="javascript:void(0);">
						<span class="c_span">其它费用金额</span>
						<input type="tel" id="otherMoneyValue" class="c_ipt" placeholder="请填写其它费用金额" onafterpaste="keyUpMon(this)" onkeyup = "keyUpMon(this)" maxlength="5" >
					</a>
				</li>
			</ul>
			<div id="otherDes" class="detail_center" style="display:none;">
				<div class="input_detail">
					<textarea name="" id="otherMoneyDes" placeholder="请输入其它费用明细，例：&#13;&#10;矿泉水1瓶：￥5:00&#13;&#10;打碎玻璃杯一个：￥40.00"></textarea>
				</div>
			</div>
		</div>
		</c:if>
		<!-- /页面调整 -->
		
		
		<h2 class="tit">房客信息</h2>
		<div class="bgF">
			<div class="detail_top pr">
				<a href="${basePath }orderland/43e881/bookingDetail?orderSn=${detail.orderSn }&userUid=${detail.userUid}&orderStatus=${detail.orderStatus}&payStatus=${detail.payStatus}&checkOutTime=${detail.checkOutTime}" class="pr disblock">
					<ul class="fk_info clearfix">
						<li class="fk_info_img"><img src="${userPicUrl }" alt=""></li>
						<li class="fk_info_txt">
							<p>预订人：${detail.userName }</p>
							<p class="star">
								<!-- 星级显示计算 -->
								<c:forEach var="s" begin="1" end="${halfStar}">
									<i class="s"></i>
								</c:forEach>
								<c:if test="${satisStar%2 == 1 }">
									<i class="h"></i>
									<c:forEach var="s" begin="1" end="${4 - halfStar }">
										<i></i>
									</c:forEach>
								</c:if>
								<c:if test="${satisStar%2 == 0 }">
									<c:forEach var="s" begin="1" end="${5 - halfStar }">
										<i></i>
									</c:forEach>
								</c:if>
								<span>${detail.landSatisfAva }</span>
								<span>${detail.evaTotal }条评价</span></p>

						</li>
						<%--<span class="icon icon_r"></span>--%>
						<p class="line2 fz7 boxP075"></p>
						<a href="javascript:;" onclick="toIm()" class="icon_msg"></a>
					</ul>
				</a>
			</div>
			<div class="detail_top">
				<p class="bd0 fk_info">
					<a href="${basePath }orderland/43e881/contactlist?orderSn=${detail.orderSn }&orderStatus=${detail.orderStatus}&payStatus=${detail.payStatus}&checkOutTime=${detail.checkOutTime}" class="pr disblock">
						<span>${detail.contactsNum }位入住人</span>
						<span>
							<c:forEach var="item" items="${detail.contactList}">
								${item.conName }
							</c:forEach>
						</span>
						<span class="icon icon_r"></span>
					</a>
				</p>
				<p class="line2 fz7 boxP075"></p>
			</div>
		</div>
		<c:if test="${not empty detail.tripPurpose}">
			<h2 class="tit">TA此次出行的目的是什么</h2>
			<div class="bgF gray boxP8 mb75">
				<p class="fz6">${detail.tripPurpose}</p>
			</div>
		</c:if>

		<h2 class="tit" id="myRemark">备注</h2>
		<div class="bgF beizhuBox">

			<c:if test="${!empty remarkList}">
				<ul class="bzList">
					<c:forEach var="remark" items="${remarkList}">
						<li class="moveLi" id="">
							<div class="del_btn" data-fid="${remark.fid}">删除</div>
							<div class="list clearfix">
								<span class="icon_del"></span>
								<p class="list_txt">${remark.remarkContent}</p>
							</div>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			<div class="addBzBox" id="addBzBox"><a href="javascript:void(0);" id="addRemark"><span>添加备注</span></a></div>
		</div>
	</div>
</div><!--/main-->


<input type="hidden" id="orderSn" value="${detail.orderSn }"/>
<!-- 待房东确认订单 -->
<c:if test="${detail.orderStatus ==  10}">

	<!-- <ul class="stateEvents detailBtns clearfix" id=""> -->
	<ul class="okBtn detailBtns clearfix" id="">
		<li id="refuseBtn">拒绝订单</li>
		<li id="acceptBtn">接受订单</li>
	</ul>

</c:if>

<c:if test="${detail.orderStatus ==  50 || detail.orderStatus == 51}">

	<ul class="okBtn detailBtns clearfix" id="">
		<li><a id="makeSureBtn">确认订单</a></li>
	</ul>
</c:if>



<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/bed_style.js"></script>
<script src="${staticResourceUrl }/js/order/lanOrderDetail.js${VERSION}003" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/iscroll-probe.js${VERSION}003" type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		var conHeight = $(window).height()-$('.header').height()-$('.stateEvents').height();

		$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$('#main > div').css({'position':'absolute','width':'100%','padding-bottom':'3rem'});
		myScroll = new IScroll('#main', { scrollbars: true, mouseWheel: true });

		if('${empty remarkList || fn:length(remarkList) < 5}' == 'true'){
			$("#addBzBox").show();
		}else{
			$("#addBzBox").hide();
		}

		myScroll.refresh();
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		
		$("textarea").on('touchmove',function(e){
            e.stopPropagation();
        })
        
		var curUrl = window.location.href;
		if(curUrl.indexOf('myRemark')>=0){
			myScroll.scrollToElement(document.querySelector('#myRemark'), null, null, true);
			myScroll.refresh();
		}
		
		
		// 新增方法start
		// 是否有其他费用 0 － 否 1 － 是
		/*  $("#makeSureBtn").click(function(){
			if($("#isOtherPrice").val() == 1){
				if($("#otherMoneyValue").val() == ""){
					showShadedowTips("请填写其它费用金额",1);
				}else if($("#otherMoneyDes").val() == ""){
					showShadedowTips("请输入其它费用明细",1);
				}
				// 之后的操作

			}else if($("#isOtherPrice").val() == 0){
				layer.open({
					title:"",
					content:"<p style='padding:1rem 0;'>您没有填写其他费用，是否提交确认订单？</p>",
					btn:['确定','取消'],
					yes:function(){
						// 确定之后的操作
						layer.closeAll();
					},
					no:function(){
						layer.closeAll();
					}
				})
			}
		}) */
		

	})
	
	
		// 新增方法start
 /* 	function choseIsOther(obj){
		if($(obj).hasClass("gray")){
			$(obj).removeClass("gray").addClass("org");
			// 是否有其他费用 用于提交判断 0 － 否 1 － 是
			$("#isOtherPrice").val("1");
			$("#otherMoney").show();
			$("#otherDes").show();
		}else{
			$(obj).removeClass("org").addClass("gray");
			// 是否有其他费用 用于提交判断 0 － 否 1 － 是
			$("#isOtherPrice").val("0");
			$("#otherMoney").hide();
			$("#otherDes").hide();
		}
	}  */
	// 新增方法end
	//订单详情 收起打开
	function shNew(obj,id){
	    var obj1 = $(obj).find('span.icon');
	    var obj2 = $('#'+id);
	
	    if(obj1.hasClass('icon_t')){
	        obj2.show();
	        obj1.removeClass('icon_t').addClass('icon_b');
	    }else{
	        obj2.hide();
	        obj1.removeClass('icon_b').addClass('icon_t');
	    }
	    myScroll.refresh();
	    
	}

	var  toIm = function () {
		var sourceOrg = '${sourceOrg}';
		var rentWay = '${detail.rentWay}';
		var fid = "";
		if (rentWay == 0){
			fid = '${detail.houseFid}';
		}else{
			fid = '${detail.roomFid}';
		}
		var uid = '${detail.userUid}';

		if(sourceOrg == 1){
			window.WebViewFunc.toCustomerIm(uid, fid, rentWay);
		}else if(sourceOrg == 2){
			window.toCustomerIm(uid, fid, rentWay);
		}else {
			window.location.href='${imUrl}/im/43e881/queryMessageBase?msgSenderType=1&uid=${detail.landlordUid}&imSource=2&orderSn=${detail.orderSn}';
		}
	}
</script>

</body>
</html>
