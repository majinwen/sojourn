<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="target-densitydpi=device-dpi,width=750,user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<script type="text/javascript">
	if (/Android (\d+\.\d+)/.test(navigator.userAgent)) {
	  var version = parseFloat(RegExp.$1);
	  if (version > 2.3) {
	  var phoneScale = parseInt(window.screen.width) / 750;
	  document.write('<meta name="viewport" content="width=750, minimum-scale = ' + phoneScale + ', maximum-scale = ' + phoneScale + ', target-densitydpi=device-dpi">');
	  } else {
	  document.write('<meta name="viewport" content="width=750, target-densitydpi=device-dpi">');
	  }
	  } else {
	  document.write('<meta name="viewport" content="width=750, user-scalable=no, target-densitydpi=device-dpi">');
	  }

	</script>
	<title>阿菠萝赏月计划</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/zhuanti/common/common.css${VERSION}003">
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/zhuanti/zhongqiu/css/zhongqiu.css${VERSION}003">
	<style>
	/* Slider */
	.slick-slider { position: relative; display: block; box-sizing: border-box; -moz-box-sizing: border-box; -webkit-touch-callout: none; -webkit-user-select: none; -khtml-user-select: none; -moz-user-select: none; -ms-user-select: none; user-select: none; -ms-touch-action: none; touch-action: none; -webkit-tap-highlight-color: transparent; }
	
	.slick-list { position: relative; overflow: hidden; display: block; margin: 0; padding: 0; }
	.slick-list:focus { outline: none; }
	.slick-loading .slick-list { }
	.slick-list.dragging { cursor: pointer; cursor: hand; }
	
	.slick-slider .slick-list, .slick-track, .slick-slide, .slick-slide img {}
	
	
	
	.slick-slide { float: left; height: 100%; min-height: 1px; display: none; }
	.slick-slide img { display: block; }
	.slick-slide.slick-loading img { display: none; }
	.slick-slide.dragging img { pointer-events: none; }
	.slick-initialized .slick-slide { display: block; }
	.slick-loading .slick-slide { visibility: hidden; }
	.slick-vertical .slick-slide { display: block; height: auto; border: 1px solid transparent; }
	
	.slick-dots { position: absolute; bottom: -45px; list-style: none; display: block; text-align: center; padding: 0px; width: 100%; }
	.slick-dots li { position: relative; display: inline-block; height: 20px; width: 20px; margin: 0px 5px; padding: 0px; cursor: pointer; }
	.slick-dots li button { border: 0; background: #fff; display: block; height: 10px; width: 10px; border-radius: 50%; outline: none; line-height: 0; 
		font-size: 0; color: transparent; padding: 5px; cursor: pointer; outline: none;  opacity: .25;}
	
	.slick-dots li.slick-active button { opacity:1; }
	.jcList > div {
	    margin: 40px 0 0px 0px;
	}
	</style>
	
</head>
<body>
<img src="${staticResourceUrl}/zhuanti/zhongqiu/img/share.jpg" class="share">
<div id="wrapper">
	<div class="main">
		<div class="banner">
			<img src="${staticResourceUrl}/zhuanti/zhongqiu/img/banner.jpg">
		</div>
		<div class="detail">
			<img src="${staticResourceUrl}/zhuanti/zhongqiu/img/detail.png">
			
			<div class="b">
				<input type="button" class="btn_xize" id="btnXize" />
				<input type="button" class="btn_to" id="btnToShare" />
			</div>
		</div>
		
		<div class="box">
			<img src="${staticResourceUrl}/zhuanti/zhongqiu/img/1.jpg">
			<div class="slick jcList">
				<c:forEach items="${list }"  var="obj">
					<c:if test="${obj.cityCode eq '110100'}">
					    <div onclick="setHouseIdFn(this,'${obj.fid }','${obj.rentWay }','${shareFlag }','${shareUrl }');" <c:if test="${obj.isAvailable == 0}">class ="end"</c:if>>  
					    	<div class="img">
					    		<c:if test="${obj.isAvailable == 0}"><span class="tips"><img src="${staticResourceUrl}/zhuanti/zhongqiu/img/close.png"></span></c:if>
					    		<img data-lazy="${obj.picUrl }" class="setImg">
					    		<span class="t"><b>￥</b><fmt:parseNumber integerOnly="true" value="${obj.price/100 }" />元/晚</span>
					    	</div>
					    	<div class="txt">
					    		<div class="ts">
					    			<img src="${obj.landlordUrl }">
					    		</div>
					    		<h2>${obj.houseName }</h2>
					    		<p class="gray">${obj.rentWayName } ｜ 可住${obj.personCount }人  </p>
					    	</div>
					    </div>
				    </c:if>
				 </c:forEach>   
			</div>
		</div>
		<div class="box">
			<img src="${staticResourceUrl}/zhuanti/zhongqiu/img/2.jpg">
			<div class="slick jcList">
			    <c:forEach items="${list }"  var="obj">
					<c:if test="${obj.cityCode eq '310100'}">
					    <div onclick="setHouseIdFn(this,'${obj.fid }','${obj.rentWay }','${shareFlag }','${shareUrl }');" <c:if test="${obj.isAvailable == 0}">class ="end"</c:if>>
					    	<div class="img">
								<c:if test="${obj.isAvailable == 0}"><span class="tips"><img src="${staticResourceUrl}/zhuanti/zhongqiu/img/close.png"></span></c:if>
								<img data-lazy="${obj.picUrl }" class="setImg">					    		<img src="${obj.picUrl }" class="setImg">
					    		<span class="t"><b>￥</b><fmt:parseNumber integerOnly="true" value="${obj.price/100 }" />元/晚</span>
					    	</div>
					    	<div class="txt">
					    		<div class="ts">
					    			<img src="${obj.landlordUrl }">
					    		</div>
					    		<h2>${obj.houseName }</h2>
					    		<p class="gray">${obj.rentWayName } ｜ 可住${obj.personCount }人  </p>
					    	</div>
					    </div>
				    </c:if>
				 </c:forEach>
			</div>
		</div>
		<div class="box">
			<img src="${staticResourceUrl}/zhuanti/zhongqiu/img/3.jpg">
			<div class="slick jcList">
			<c:forEach items="${list }"  var="obj">
					<c:if test="${obj.cityCode eq '440100'}">
					    <div onclick="setHouseIdFn(this,'${obj.fid }','${obj.rentWay }','${shareFlag }','${shareUrl }');" <c:if test="${obj.isAvailable == 0}">class ="end"</c:if>>
					    	<div class="img">
					    		<c:if test="${obj.isAvailable == 0}"><span class="tips"><img src="${staticResourceUrl}/zhuanti/zhongqiu/img/close.png"></span></c:if>
					    		<img data-lazy="${obj.picUrl }" class="setImg">
					    		<span class="t"><b>￥</b><fmt:parseNumber integerOnly="true" value="${obj.price/100 }" />元/晚</span>
					    	</div>
					    	<div class="txt">
					    		<div class="ts">
					    			<img src="${obj.landlordUrl }">
					    		</div>
					    		<h2>${obj.houseName }</h2>
					    		<p class="gray">${obj.rentWayName } ｜ 可住${obj.personCount }人  </p>
					    	</div>
					    </div>
				    </c:if>
				 </c:forEach>
				</div>
		</div>
		
		<div class="box">
			<img src="${staticResourceUrl}/zhuanti/zhongqiu/img/4.jpg">
			<div class="slick jcList">
			    <c:forEach items="${list }"  var="obj">
					<c:if test="${obj.cityCode eq '440300'}">
					    <div onclick="setHouseIdFn(this,'${obj.fid }','${obj.rentWay }','${shareFlag }','${shareUrl }');" <c:if test="${obj.isAvailable == 0}">class ="end"</c:if>>
					    	<div class="img">
					    		<c:if test="${obj.isAvailable == 0}"><span class="tips"><img src="${staticResourceUrl}/zhuanti/zhongqiu/img/close.png"></span></c:if>
					    		<img data-lazy="${obj.picUrl }" class="setImg">
					    		<span class="t"><b>￥</b><fmt:parseNumber integerOnly="true" value="${obj.price/100 }" />元/晚</span>
					    	</div>
					    	<div class="txt">
					    		<div class="ts">
					    			<img src="${obj.landlordUrl }">
					    		</div>
					    		<h2>${obj.houseName }</h2>
					    		<p class="gray">${obj.rentWayName } ｜ 可住${obj.personCount }人  </p>
					    	</div>
					    </div>
				    </c:if>
				 </c:forEach>
			</div>
		</div>
		<div class="box">
			<img src="${staticResourceUrl}/zhuanti/zhongqiu/img/5.jpg">
			<div class="slick jcList">
			    <c:forEach items="${list }"  var="obj">
					<c:if test="${obj.cityCode eq '320500'}">
					    <div onclick="setHouseIdFn(this,'${obj.fid }','${obj.rentWay }','${shareFlag }','${shareUrl }');" <c:if test="${obj.isAvailable == 0}">class ="end"</c:if>>
					    	<div class="img">
					    		<c:if test="${obj.isAvailable == 0}"><span class="tips"><img src="${staticResourceUrl}/zhuanti/zhongqiu/img/close.png"></span></c:if>
					    		<img data-lazy="${obj.picUrl }" class="setImg">
					    		<span class="t"><b>￥</b><fmt:parseNumber integerOnly="true" value="${obj.price/100 }" />元/晚</span>
					    	</div>
					    	<div class="txt">
					    		<div class="ts">
					    			<img src="${obj.landlordUrl }">
					    		</div>
					    		<h2>${obj.houseName }</h2>
					    		<p class="gray">${obj.rentWayName } ｜ 可住${obj.personCount }人  </p>
					    	</div>
					    </div>
				    </c:if>
				 </c:forEach>
			</div>
		</div>
		<div class="box">
			<img src="${staticResourceUrl}/zhuanti/zhongqiu/img/6.jpg">
			<div class="slick jcList">
			    <c:forEach items="${list }"  var="obj">
					<c:if test="${obj.cityCode eq '510100'}">
					    <div onclick="setHouseIdFn(this,'${obj.fid }','${obj.rentWay }','${shareFlag }','${shareUrl }');" <c:if test="${obj.isAvailable == 0}">class ="end"</c:if>>
					    	<div class="img">
					    		<c:if test="${obj.isAvailable == 0}"><span class="tips"><img src="${staticResourceUrl}/zhuanti/zhongqiu/img/close.png"></span></c:if>
					    		<img data-lazy="${obj.picUrl }" class="setImg">
					    		<span class="t"><b>￥</b><fmt:parseNumber integerOnly="true" value="${obj.price/100 }" />元/晚</span>
					    	</div>
					    	<div class="txt">
					    		<div class="ts">
					    			<img src="${obj.landlordUrl }">
					    		</div>
					    		<h2>${obj.houseName }</h2>
					    		<p class="gray">${obj.rentWayName } ｜ 可住${obj.personCount }人  </p>
					    	</div>
					    </div>
				    </c:if>
				 </c:forEach>
			</div>
		</div>
		<div class="box">
			<img src="${staticResourceUrl}/zhuanti/zhongqiu/img/7.jpg">
			<div class="slick jcList">
			    <c:forEach items="${list }"  var="obj">
					<c:if test="${obj.cityCode eq '370200'}">
					    <div onclick="setHouseIdFn(this,'${obj.fid }','${obj.rentWay }','${shareFlag }','${shareUrl }');" <c:if test="${obj.isAvailable == 0}">class ="end"</c:if>>
					    	<div class="img">
					    		<c:if test="${obj.isAvailable == 0}"><span class="tips"><img src="${staticResourceUrl}/zhuanti/zhongqiu/img/close.png"></span></c:if>
					    		<img data-lazy="${obj.picUrl }" class="setImg">
					    		<span class="t"><b>￥</b><fmt:parseNumber integerOnly="true" value="${obj.price/100 }" />元/晚</span>
					    	</div>
					    	<div class="txt">
					    		<div class="ts">
					    			<img src="${obj.landlordUrl }">
					    		</div>
					    		<h2>${obj.houseName }</h2>
					    		<p class="gray">${obj.rentWayName } ｜ 可住${obj.personCount }人  </p>
					    	</div>
					    </div>
				    </c:if>
				 </c:forEach>
			</div>
		</div>
		<div class="box">
			<img src="${staticResourceUrl}/zhuanti/zhongqiu/img/8.jpg">
			<div class="slick jcList">
			    <c:forEach items="${list }"  var="obj">
					<c:if test="${obj.cityCode eq '610100'}">
					    <div onclick="setHouseIdFn(this,'${obj.fid }','${obj.rentWay }','${shareFlag }','${shareUrl }');" <c:if test="${obj.isAvailable == 0}">class ="end"</c:if>>
					    	<div class="img">
					    		<c:if test="${obj.isAvailable == 0}"><span class="tips"><img src="${staticResourceUrl}/zhuanti/zhongqiu/img/close.png"></span></c:if>
					    		<img data-lazy="${obj.picUrl }" class="setImg">
					    		<span class="t"><b>￥</b><fmt:parseNumber integerOnly="true" value="${obj.price/100 }" />元/晚</span>
					    	</div>
					    	<div class="txt">
					    		<div class="ts">
					    			<img src="${obj.landlordUrl }">
					    		</div>
					    		<h2>${obj.houseName }</h2>
					    		<p class="gray">${obj.rentWayName } ｜ 可住${obj.personCount }人  </p>
					    	</div>
					    </div>
				    </c:if>
				 </c:forEach>
			</div>
		</div>
	</div>
</div>
<div id="xizeBox" class="dliog" style="display: none">
	<a href="javascript:;" class="close"></a>
	<div class="con">
		<h2>活动流程</h2>
		<ul>
			<li><b></b>即日起至2016年9月13日活动期间，进入活动页面即可浏览全国8个城市，总计50间精选房源。</li>
			<li><b></b>预订活动页面中任意一间房源连续三个中秋间夜，提交申请预订获得房东确认，支付完成即为预订成功。</li>
			<li><b></b>成功入住并提交评价后，即可获得200元现金返现。</li>		
		</ul>
		<h2>活动说明</h2>
		<ul>
			<li><b></b>200元现金返现将在入住成功并提交评价后入账，如您的登陆账号已绑定银行卡，将直接入账所绑定的银行卡，如无绑定则入账个人中心“我的钱包”，可在PC端操作提现。</li>
			<li><b></b>预订日期必须覆盖同一房源连续三个中秋间夜(9月14~16日)，否则将无法获得返现奖励。</li>
			<li><b></b>预订非活动页面房源不享受返现优惠。</li>
			<li><b></b>如有任何疑问可咨询客服电话：<br>4007711666 (9：00-21：00)</li>
		</ul>
	</div>
	
</div>
<div id="shareBox" class="dliog" style="display: none;"></div>
<div id="querenBox" class="dliog" style="display: none;">
	<img src="${staticResourceUrl}/zhuanti/zhongqiu/img/queren.png" alt="">
	<a href="javascript:;" onclick="toPage(this)" data-id='' id="setHouseId"></a>
</div>

<div id="dliogBg"></div>
<style type="text/css">
	html,body{height: auto; overflow-y: auto;}
	#wapper{height: auto;}
</style>
	<script type="text/javascript" src="${staticResourceUrl}/zhuanti/common/jquery-1.8.3.min.js${VERSION}001"></script>
	<!-- <script type="text/javascript" src="${staticResourceUrl}/zhuanti/common/iscroll/iscroll-probe.js${VERSION}002"></script> -->
	<script type="text/javascript" src="${staticResourceUrl}/zhuanti/common/slick/slick.js${VERSION}006"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}003"></script>
	<%--<script src="http://catcher.ziroom.com/assets/js/boomerang.min.js"></script>--%>
	<%--<script>--%>
	   <%--BOOMR.init({--%>
	   <%--beacon_url: "http://catcher.ziroom.com/beacon",--%>
	   <%--beacon_type: "POST",--%>
	   <%--page_key: "minsu_H5_appollo_160913"});--%>
	<%--</script>--%>
	
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript">
	
	$('.slick').slick({
		dots: true,
		 lazyLoad: 'ondemand',
		 slidesToShow: 1,
		 slidesToScroll: 1,
		 autoplay: true,
  		 autoplaySpeed: 3000
	}); 
	
	
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId:'${appId}', // 必填，公众号的唯一标识
	    timestamp:'${timestamp}' , // 必填，生成签名的时间戳
	    nonceStr:'${nonceStr}', // 必填，生成签名的随机串
	    signature:'${signature}',// 必填，签名，见附录1
	    jsApiList:['onMenuShareAppMessage','onMenuShareTimeline','onMenuShareQQ'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.ready(function () {
		wx.onMenuShareAppMessage({
		    title: '枕月而眠，赴一场月盈之约', // 分享标题
		    desc: '去一间看得见月亮的房间，邂逅一年中最美的时光', // 分享描述
		    link: '${basePath}topics/appolloMoon?paramJson=page_appollo_param&shareFlag=2', // 分享链接
		    imgUrl: '${staticResourceUrl}/zhuanti/zhongqiu/img/share.jpg', // 分享图标
		    type: 'link', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    trigger: function (res) {
		      },
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    },
	        fail: function (res) {
	        }
		});
		
		wx.onMenuShareTimeline({
		    title: '枕月而眠，赴一场月盈之约， 去一间看得见月亮的房间，邂逅一年中最美的时光', // 分享标题
		    link: '${basePath}topics/appolloMoon?paramJson=page_appollo_param&shareFlag=2', // 分享链接
		    imgUrl: '${staticResourceUrl}/zhuanti/zhongqiu/img/share.jpg', // 分享图标
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		
		wx.onMenuShareQQ({
		    title: '枕月而眠，赴一场月盈之约', // 分享标题
		    desc: '去一间看得见月亮的房间，邂逅一年中最美的时光', // 分享描述
		    link: '${basePath}topics/appolloMoon?paramJson=page_appollo_param&shareFlag=2', // 分享链接
		    imgUrl: '${staticResourceUrl}/zhuanti/zhongqiu/img/share.jpg', // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		       // 用户取消分享后执行的回调函数
		    }
		});
		
	});
		  
	wx.error(function(res){
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
     });
	
	var fid,rentWay,shareFlag,shareUrl;
	function setHouseIdFn(obj,_fid,_rentWay,_shareFlag,_shareUrl) {
		
		fid = _fid;
		rentWay = _rentWay;
		shareFlag = _shareFlag;
		shareUrl = _shareUrl;
		//跳转到APP详情页
		if($(obj).hasClass('end')){
			/* alert('该房源已被预定'); */
		}else{
			showDliog('#querenBox');
		}
		/* $('#setHouseId').attr({'data-id':fid}); */
	}

	$('#setHouseId').on('click',function(){		
		/* alert('跳转到APP详情页'+$(this).attr('data-id')); */
		minsutuijianJump(fid,rentWay,shareFlag,shareUrl);
		closeDliog();
	});

	
	function showDliog(id){
		$(id).show();
		$('#dliogBg').show();
	}
	function closeDliog(){
		$('.dliog').hide();
		$('#dliogBg').hide();
	}

	$('#btnXize').click(function(){
		showDliog('#xizeBox')
		
	});

	$('.dliog .close').on('touchend',function(){
		closeDliog();
	});


	$('#btnToShare').click(function(){
			showDliog('#shareBox');
	});

	$('#dliogBg').on('touchend',function(){
		closeDliog();
	})
	</script>
</body>
</html>
