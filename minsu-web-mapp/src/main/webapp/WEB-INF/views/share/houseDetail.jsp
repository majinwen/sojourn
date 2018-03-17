<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="applicable-device" content="mobile">
<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
<script type="text/javascript" src="${staticResourceUrl}/js/header.js${VERSION}001"></script>
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="yes" name="apple-touch-fullscreen" />
<meta name="name" content="淳美民居——${houseDetailVo.houseName }"/>
<meta name="image" content="${houseDetailVo.defaultPic }" />
<meta name="description" content="${houseDetailVo.houseDesc }" />
<title>淳美民居——${houseDetailVo.houseName }</title>
<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/share/css/styles.css${VERSION}010">
<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/share/js/slick/need/slick.css${VERSION}001">
<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/share/css/new.css${VERSION}001">
<link href="${staticResourceUrl}/share/js/photoswipe/photoswipe.css${VERSION}001" rel="stylesheet" />
<link href="${staticResourceUrl}/share/js/photoswipe/default-skin/default-skin.css${VERSION}001" rel="stylesheet" />
    
<script src="${staticResourceUrl}/share/js/photoswipe/photoswipe.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/share/js/photoswipe/photoswipe-ui-default.min.js${VERSION}001"></script>
<style type="text/css">
 

</style>
</head>
<body>
	<c:if test="${!empty sourceFrom && sourceFrom =='previewBtn' }">
		<header class="header">
			<a href="${basePathHttps}houseInput/${loginUnauth}/goToHouseUpdate?houseBaseFid=${houseBaseFid }&houseRoomFid=${houseRoomFid }&rentWay=${rentWay }" id="goback" class="goback" ></a>
			<h1 class="overflowstyleTitle">${houseDetailVo.houseName }</h1>
		</header>
	</c:if>
	<div class="msxqContainer">
		<div id="demo-test-gallery" class="block xiangqing demo-gallery">
			<div class="pr">
			<c:if test="${!empty houseDetailVo.picDisList &&  fn:length(houseDetailVo.picDisList)>0 }">
				<div id="slider" >
					<c:forEach items="${houseDetailVo.picDisList }" var="pic">
						<div class="bannerImg img">
							<a href="${pic.eleValue }" data-size="1600x1600" data-med="${pic.eleValue }" data-med-size="1024x1024" data-author="Folkert Gorter" class="demo-gallery__img--main">
								<img src="${pic.eleValue }" width="100%" alt="" class="setImg">
							</a>
						</div>
					</c:forEach>
				</div>
			</c:if>
	     <div class="parent">
	     <input type="hidden" value="${ houseDetailVo.toNightDiscountStartTime}" id="toNightDiscountStartTime">
	     <input type="hidden" value="${ houseDetailVo.toNightDiscountEndTime}" id="toNightDiscountEndTime">
	     <input type="hidden" value="${ houseDetailVo.toNightDiscountStatus}" id="toNightDiscountStatus">
	     <input type="hidden" value="${ houseDetailVo.toNightDiscount.openTime}" id="toNightDiscountOpenTime">
		 <div class="price"  id="tonightPrice">
			<span class="span_rel">￥<em>${fn:split(houseDetailVo.toNightDiscount.tonightPrice/100, ".")[0] }</em></span>
			<span class="tonight">今夜特价</span>
		 </div>
		 <div class="countdown">
			<span class="intime" id="intime">${houseDetailVo.toNightDiscount.openTime}准时开抢</span>
			<div class="time">
			    <span class="time_bg" id="t_h">00</span>&nbsp;:
			    <span class="time_bg" id="t_m">00</span>&nbsp;:
			    <span class="time_bg" id="t_s">00</span>
			</div>
		 </div>
		</div>
		</div><!-- /pr -->
		<div class="describe" id="describe">
			<p class="price_new"  id="priceNew">￥<em>${fn:split(houseDetailVo.housePrice/100, ".")[0] }</em>/晚</p>
			<p class="name" id="name">${houseDetailVo.houseName }</p>
			<p class="condition">${houseDetailVo.rentWayName }｜<c:choose><c:when test="${houseDetailVo.checkInLimit == 0 }">不限人数</c:when><c:otherwise>可住${houseDetailVo.checkInLimit}人</c:otherwise></c:choose>｜<c:if test="${houseDetailVo.isTogetherLandlord == 1 }">与房东同住｜</c:if>${houseDetailVo.houseArea }平米</p></p>
		</div>

            <!--优惠券展示逻辑-->
            <c:if test="${isCouponShow == 1}">
				<!-- 优惠券 -->
				<div id="ticket" onclick="lay()">
					<div class="cheapFr clearfix">
						<ul>
							<c:forEach items="${barList }" var="msg">
								<li class="li">${msg}</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<!-- 优惠券弹窗 -->
				<div class="lay" id="layBox">
					<div class="layWidth">
						<p class="layerT">自如民宿优惠券</p>
						<p class="layerDes">各档优惠每日限领1张，民宿平台通用</p>
						<c:forEach items="${couponList }" var="coupon">
							<div class="quanBg">
								<p class="quanD">￥<span class="quanP">${coupon.money}</span></p>
								<p class="quanDes">${coupon.desc}</p>
								<a href="javascript:;" onclick="targetToApp()" class="quanGet">领取</a>
							</div>
						</c:forEach>
					</div>
				</div>
            </c:if>

		</div>


		<div class="block peizhi">
			<ul class="ub">
				<c:forEach items="${mainMap }" var="main" begin="0" end="4">
					<li class="ub-f1">
						<img src="${staticResourceUrl}/share/img/peizhi/${main.key }.png" alt="">
						<p>${main.value }</p>
					</li>
				</c:forEach>
				<c:if test="${displaMore }">
					<li class="ub-f1">
						<a href="javascript:;" onclick="peizhiMore();">
							<img src="${staticResourceUrl}/share/img/more.png" alt="">
							<p>更多</p>
						</a>
					</li>
				</c:if>
			</ul>
		</div>
		<div class="block fangzhu">
			<dl>
				<dt onclick="toLandlordInfo();">
					<div class="fdxq">
						<p class="p1 overflowstyle">
							<span>${houseDetailVo.landlordName }</span><c:if test="${houseDetailVo.isAuth == 1 }"><img src="${staticResourceUrl}/share/img/renzheng.png" alt=""></c:if>
						</p>
						<p class="star p2 myCalendarStar" data-val="${houseDetailVo.gradeStar }"><i class="s"></i><i class="s"></i><i class="s"></i><i class="h"></i><i></i> <span>${houseDetailVo.gradeStar }分</span></p>
					</div>
					<div class="avator" style="background:url('${houseDetailVo.landlordIcon }') center no-repeat;background-size:cover;"></div>
					
				</dt>
				<c:if test="${not empty houseDetailVo.evaluateCount && houseDetailVo.evaluateCount != 0 }">
					<dd>
						<h3><span>${houseDetailVo.evaluateCount }个人评价了TA</span></h3>
						<p>${houseDetailVo.tenantEvalVo.evalContent }</p>
					</dd>
				</c:if>
			</dl>
		</div>
		<!-- 美居描述  start-->
		
			<div class="block zhoubian">
			<dl>
				<dt><img src="${staticResourceUrl}/share/img/line.png" alt=""><span>美居介绍</span><img src="${staticResourceUrl}/share/img/line.png" alt=""></dt>
				<dd>
					<p class="p1">
						 居住面积:${houseDetailVo.houseArea }平米</br>
						<c:if test="${!empty isTogetherLandlord }">
						     ${isTogetherLandlord} </br>
						</c:if>
						${houseDetailVo.houseDesc }
				   </p>
				</dd>
			</dl>
		</div>
		
		<!-- end -->
		
		
		<div class="block zhoubian">
			<dl>
				<dt><img src="${staticResourceUrl}/share/img/line.png" alt=""><span>周边情况</span><img src="${staticResourceUrl}/share/img/line.png" alt=""></dt>
				<dd>
					<p class="p1">${houseDetailVo.houseAroundDesc }</p>
					<div class="ditu">
						<img style="width:100%; height:auto;" src="http://api.map.baidu.com/staticimage/v2?ak=${baiduAk}&center=${houseDetailVo.longitude },${houseDetailVo.latitude }&markers=${houseDetailVo.longitude },${houseDetailVo.latitude }&width=900&height=600&zoom=17&markerStyles=m">
						<p><span><img src="${staticResourceUrl}/share/img/location.png" alt="">${houseDetailVo.houseAddr }</span></p>
					</div>
				</dd>
			</dl>
		</div>
		<div class="block yaoqiu">
			<dl>
				<dt><img src="${staticResourceUrl}/share/img/line.png" alt=""><span>入住要求</span><img src="${staticResourceUrl}/share/img/line.png" alt=""></dt>
				<dd>
					<ul>
						<li><p>下单类型<span>${houseDetailVo.orderTypeName }</span></p></li>
						<li><p>押金规则<span>${houseDetailVo.depositRulesName }</span></p></li>
						<li><p>退房规则<span>${houseDetailVo.checkOutRulesName }</span></p></li>
						<li><p>最少入住天数<span>${houseDetailVo.minDay }天</span></p></li>
					</ul>
				</dd>
			</dl>
		</div>
		<div id="fuwu" class="block fuwu">
			<dl>
				<dt><img src="${staticResourceUrl}/share/img/line.png" alt=""><span>服务</span><img src="${staticResourceUrl}/share/img/line.png" alt=""></dt>
				<dd>
					<ul class="clearfix">
						<li><img src="${staticResourceUrl}/share/img/peizhi/ruzhu.png" alt=""><span>${fn:substring(houseDetailVo.checkInTime, 0, 2)}点入住</span></li>
						<li><img src="${staticResourceUrl}/share/img/peizhi/tuifang.png" alt=""><span>${fn:substring(houseDetailVo.checkOutTime, 0, 2)}点退房</span></li>
						<c:forEach items="${houseDetailVo.serveList }" var="service">
							<c:forEach items="${serviceConfMap }" var="serviceConf">
								<c:if test="${service.dicPic == serviceConf.key }">
									<li><img src="${staticResourceUrl}/share/img/peizhi/${serviceConf.value }.png" alt=""><span>${service.dicName }</span></li>
								</c:if>
							</c:forEach>
						</c:forEach>
					</ul>
				</dd>
			</dl>
		</div>
		<%-- <div class="block fangyuan">
			<dl>
				<dt><img src="${staticResourceUrl}/share/img/line.png" alt=""><span>更多类似房源</span><img src="${staticResourceUrl}/share/img/line.png" alt=""></dt>
				<dd>
					<ul class="clearfix">
						<li>
							<c:forEach items="${list }" var="house">
								<a href="${basePath }tenantHouse/${noLoginAuth }/houseDetail?fid=${house.fid }&rentWay=${house.rentWay }">
									<div class="bannerImg">
										<img src="${house.picUrl }" width="100%" alt="">
										<span>￥ ${fn:split(house.price/100, ".")[0] }/晚</span>
									</div>
									<div class="describe">
										<p class="p1 overflowstyle">${fn:split(house.houseAddr, " ")[0] }</p>
										<p class="condition">${house.rentWayName }｜<c:choose><c:when test="${house.personCount == 0 }">不限人数</c:when><c:otherwise>可住${house.personCount}人</c:otherwise></c:choose></p>
										<p class="star p2 myCalendarStar" data-val="${house.evaluateScore }"><i class="s"></i><i class="s"></i><i class="s"></i><i class="h"></i><i></i> <span>${house.evaluateScore }分</span></p>
									</div>
								</a>
							</c:forEach>
						</li>
					</ul>
				</dd>
			</dl>
		</div> --%>
	</div>
	
	  <!-- Swiper -->
    <!-- <div class="swiper-container"> -->
        <!-- <div class="swiper-wrapper">
            <div class="swiper-slide">Slide 1</div>
            <div class="swiper-slide">Slide 2</div>
            <div class="swiper-slide">Slide 3</div>
            <div class="swiper-slide">Slide 4</div>
            <div class="swiper-slide">Slide 5</div>
            <div class="swiper-slide">Slide 6</div>
            <div class="swiper-slide">Slide 7</div>
            <div class="swiper-slide">Slide 8</div>
            <div class="swiper-slide">Slide 9</div>
            <div class="swiper-slide">Slide 10</div>
        </div> -->
        <!-- Add Pagination -->
        <!-- <div class="swiper-pagination"></div> -->
        <!-- Add Arrows -->
        <!-- <div class="swiper-button-next"></div>
        <div class="swiper-button-prev"></div> -->
    <!-- </div> -->
	 <a id="downloadapp" href="javascript:;" onclick="targetToApp()">
        <img src="${staticResourceUrl}/share/img/downloadapp.png">
    </a>
    <div class="weixinMaskTip" onclick="closeMask()"><img src="${staticResourceUrl}/share/img/weixinMaskTip.png" alt=""></div>
    
	<div id="gallery" class="pswp" tabindex="-1" role="dialog" aria-hidden="true">
	    <div class="pswp__bg"></div>
	    <div class="pswp__scroll-wrap">
	        <div class="pswp__container">
				<div class="pswp__item"></div>
				<div class="pswp__item"></div>
				<div class="pswp__item"></div>
	        </div>
	        <div class="pswp__ui pswp__ui--hidden">
	            <div class="pswp__top-bar">
					<div class="pswp__counter"></div>
					<button class="pswp__button pswp__button--close" title="Close (Esc)">
						<%-- <img alt="" src="${staticResourceUrl}/share/img/close.png"> --%>
					</button>
					<!--<button class="pswp__button pswp__button--share" title="Share"></button>
					<button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button>-->
					<button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>
					<div class="pswp__preloader">
						<div class="pswp__preloader__icn">
						  <div class="pswp__preloader__cut">
						    <div class="pswp__preloader__donut"></div>
						  </div>
						</div>
					</div>
	            </div>
		        <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
		            <div class="pswp__share-tooltip">
		            </div>
		      	</div>
		        <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)"></button>
		        <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)"></button>
		        <div class="pswp__caption">
		            <div class="pswp__caption__center">
		            </div>
		        </div>
		    </div>
		</div>
	</div>
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/share/js/index.js${VERSION}001"></script>
	<script type="text/javascript" src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/share/js/slick/slick.js${VERSION}001"></script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
		/*领取优惠券弹框*/
        function lay(){
            layer.open({
                type: 1
                ,content:$('#layBox').html()
                ,anim: 'up'
                ,style: 'position:fixed;bottom:0;left:0;width:100%;border:none;'
                ,btn: ['']
            });
        }

		$(function () { 
			var toNightDiscountStatus=$("#toNightDiscountStatus").val();
			var toNightDiscountOpenTime=$("#toNightDiscountOpenTime").val();
			if(isNullOrBlank(toNightDiscountStatus)){
				$("#tonightPrice").hide();
				$(".countdown").hide();
				$("#priceNew").attr("class","price_none");
			} else if (toNightDiscountStatus==0){
				$("#tonightPrice").show();
				$(".countdown").show();
				$("#tonightPrice").attr("class","price");
				$("#intime").html(toNightDiscountOpenTime+"准时开抢");
				$("#priceNew").attr("class","price_new");
			} else if (toNightDiscountStatus==1){
				$("#tonightPrice").show();
				$(".countdown").show();
				$("#tonightPrice").attr("class","tonight_color");
				$("#intime").html("距结束还剩");
				$("#priceNew").attr("class","price_middle");
			}
		});
		var i=0;
		function GetRTime(){
			var t;
			var toNightDiscountStatus=$("#toNightDiscountStatus").val();
			var toNightDiscountStartTime=$("#toNightDiscountStartTime").val();
			var toNightDiscountEndTime=$("#toNightDiscountEndTime").val();
		    var NowTime = new Date();    /*当前时间*/
		    if(isNullOrBlank(toNightDiscountStatus)){
		    	return;
		    }
		    if(toNightDiscountStatus==0){
		    	t=toNightDiscountStartTime-NowTime.getTime();
		    }else if(toNightDiscountStatus==1){
		    	t=toNightDiscountEndTime-NowTime.getTime();
		    }
		    console.log(t);
			/*时间戳里面+1表示多1毫秒     1493879935253这个时间戳就表示这么多毫秒*/
			/*3700000 毫秒   3700000/1000 =3700秒  */
		    // var d=0;
		    var h=0;
		    var m=0;
		    var s=0;
		    if(t>=0){
		      d=Math.floor(t/1000/60/60/24);
		      console.log(d);
		      h=Math.floor(t/1000/60/60%24+d*24);
		      m=Math.floor(t/1000/60%60);
		      s=Math.floor(t/1000%60);
		      if(h<10){h = "0" + h}
		      if(m<10){m = "0" + m}
		      if(s<10){s = "0" + s}
		    }else{
		    	h = "00";
		    	m = "00";
		    	s = "00";
				if(toNightDiscountStatus==0){
					$("#toNightDiscountStatus").val(1);
					$("#tonightPrice").show();
					$(".countdown").show();
					$("#tonightPrice").attr("class","tonight_color");
					$("#intime").html("距结束还剩");
					$("#priceNew").attr("class","price_middle");
				} else if(toNightDiscountStatus==1){
					$("#tonightPrice").hide();
					$(".countdown").hide();
					$("#priceNew").attr("class","price_none");
				}
		    }
			$("#t_h").html(h);
			$("#t_m").html(m);
			$("#t_s").html(s);
	  	}
	  	GetRTime();
		setInterval(GetRTime,1000);
		//判断空
	    function isNullOrBlank(obj){
            return obj == undefined || obj == null || $.trim(obj).length == 0;
        }
	</script>
	<script type="text/javascript">
		$.ajax({
		    type: "GET",
		    url: "https://minsuactivity.ziroom.com/topics/wxShare",
		    data:{"currentUrl":window.location.href},
		    dataType:"jsonp",
		    jsonp:"callback",
		    success: function(result){
		        wx.config({
		            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		            appId:result.data.wShareVo.appId, // 必填，公众号的唯一标识
		            timestamp:result.data.wShareVo.timestamp, // 必填，生成签名的时间戳
		            nonceStr:result.data.wShareVo.nonceStr, // 必填，生成签名的随机串
		            signature:result.data.wShareVo.signature,// 必填，签名，见附录1
		            jsApiList:['onMenuShareAppMessage','onMenuShareTimeline','onMenuShareQQ'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		        });
	
		        wx.ready(function () {
			        // 分享给朋友
	                wx.onMenuShareAppMessage({
	                    title: "淳美民居——${houseDetailVo.houseName }", // 分享标题
	                    desc: "${houseDetailVo.houseDesc }", // 分享描述
	                    link: window.location.href, // 分享链接
	                    imgUrl: "${houseDetailVo.defaultPic }", // 分享图标
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
		                
			        // 分享到朋友圈
	                wx.onMenuShareTimeline({
	                	title: "淳美民居——${houseDetailVo.houseName }", // 分享标题
	                    link: window.location.href, // 分享链接
	                    imgUrl: "${houseDetailVo.defaultPic }", // 分享图标
	                    success: function () { 
	                        // 用户确认分享后执行的回调函数
	                        
	                    },
	                    cancel: function () { 
	                        // 用户取消分享后执行的回调函数
	                    }
	                });
		               
	                // 分享到QQ
	                wx.onMenuShareQQ({
	                	title: "淳美民居——${houseDetailVo.houseName }", // 分享标题
	                    desc: "${houseDetailVo.houseDesc }", // 分享描述
	                    link: window.location.href, // 分享链接
	                    imgUrl: "${houseDetailVo.defaultPic }", // 分享图标
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
	     	}
		});
	</script>
	<script type="text/javascript">
		autoTitleHight();
		function autoTitleHight(){//预览页面有header需要调节高度
			if($("header").length>0){
				$("div.msxqContainer").css({'height': 'calc(100% - 4rem)'});
			}
		}
		
		function showBigImg(){
		  	var initPhotoSwipeFromDOM = function(gallerySelector) {
				var parseThumbnailElements = function(el) {
				    var thumbElements = $(el).parent().find('a.demo-gallery__img--main'),
				        numNodes = thumbElements.length,
				        items = [],
				        el,
				        childElements,
				        thumbnailEl,
				        size,
				        item;
	
				    for(var i = 0; i < numNodes; i++) {
				        el = thumbElements[i];
	
				        // include only element nodes 
				        if(el.nodeType !== 1) {
				          continue;
				        }
	
				        childElements = el.children;
				        var _this = $(childElements);
	
				        /*var newImg = new Image();
				        var imgH;
				        var imgW;
				        var dataSize;
				        var dataMedSize;
	
				        newImg.onload = function(){
				            imgH = _this.height();
				            imgW = _this.width();
				            dataSize = "1600x"+parseInt(1600/imgW*imgH);
				            dataMedSize = "1024x"+parseInt(1024/imgW*imgH);
				            $(childElements).parent().attr('data-size',dataSize);
				            $(childElements).parent().attr('data-med-size',dataMedSize);
				            console.log($(childElements).parent().attr('data-size'));
				        }
				        newImg.src = _this.attr('src');*/
				        //_this.attr('data-size') = dataSize;
				        //_this.attr('data-med-size') = dataMedSize;
	
				        size = el.getAttribute('data-size').split('x');
	
				        // create slide object
				        item = {
									src: el.getAttribute('href'),
									w: parseInt(size[0], 10),
									h: parseInt(size[1], 10),
									author: el.getAttribute('data-author')
				        };
	
				        item.el = el; // save link to element for getThumbBoundsFn
	
				        if(childElements.length > 0) {
				          item.msrc = childElements[0].getAttribute('src'); // thumbnail url
				          if(childElements.length > 1) {
				              item.title = childElements[1].innerHTML; // caption (contents of figure)
				          }
				        }
	
	
						var mediumSrc = el.getAttribute('data-med');
			          	if(mediumSrc) {
			            	size = el.getAttribute('data-med-size').split('x');
			            	// "medium-sized" image
			            	item.m = {
			              		src: mediumSrc,
			              		w: parseInt(size[0], 10),
			              		h: parseInt(size[1], 10)
			            	};
			          	}
			          	// original image
			          	item.o = {
			          		src: item.src,
			          		w: item.w,
			          		h: item.h
			          	};
	
				        items.push(item);
				    }
	
				    return items;
				};
	
				// find nearest parent element
				var closest = function closest(el, fn) {
				    return el && ( fn(el) ? el : closest(el.parentNode, fn) );
				};
	
				var onThumbnailsClick = function(e) {
				    e = e || window.event;
				    e.preventDefault ? e.preventDefault() : e.returnValue = false;
	
				    var eTarget = e.target || e.srcElement;
	
				    var clickedListItem = closest(eTarget, function(el) {
				        return el.tagName === 'A';
				    });
	
				    if(!clickedListItem) {
				        return;
				    }
	
				    var clickedGallery = clickedListItem.parentNode;
	
				    var childNodes = clickedGallery.parentNode.childNodes,
				        numChildNodes = childNodes.length,
				        nodeIndex = 0,
				        index;
	
				    for (var i = 0; i < numChildNodes; i++) {
				        if(childNodes[i].nodeType !== 1) { 
				            continue; 
				        }
	
				        if(childNodes[i] === clickedListItem.parentNode) {
				            index = nodeIndex;
				            break;
				        }
				        nodeIndex++;
				    }
	
				    if(index >= 0) {
				        openPhotoSwipe( index, clickedGallery );
				    }
				    return false;
				};
	
				var photoswipeParseHash = function() {
					var hash = window.location.hash.substring(1),
				    params = {};
	
				    if(hash.length < 5) { // pid=1
				        return params;
				    }
	
				    var vars = hash.split('&');
				    for (var i = 0; i < vars.length; i++) {
				        if(!vars[i]) {
				            continue;
				        }
				        var pair = vars[i].split('=');  
				        if(pair.length < 2) {
				            continue;
				        }           
				        params[pair[0]] = pair[1];
				    }
	
				    if(params.gid) {
				    	params.gid = parseInt(params.gid, 10);
				    }
	
				    return params;
				};
	
				var openPhotoSwipe = function(index, galleryElement, disableAnimation, fromURL) {
				    var pswpElement = document.querySelectorAll('.pswp')[0],
				        gallery,
				        options,
				        items;
	
					items = parseThumbnailElements(galleryElement);
	
				    // define options (if needed)
				    options = {
	
				        galleryUID: galleryElement.getAttribute('data-pswp-uid'),
	
				        getThumbBoundsFn: function(index) {
				            // See Options->getThumbBoundsFn section of docs for more info
				            var thumbnail = items[index].el.children[0],
				                pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
				                rect = thumbnail.getBoundingClientRect(); 
	
				            return {x:rect.left, y:rect.top + pageYScroll, w:rect.width};
				        },
	
				        addCaptionHTMLFn: function(item, captionEl, isFake) {
							if(!item.title) {
								captionEl.children[0].innerText = '';
								return false;
							}
							captionEl.children[0].innerHTML = item.title +  '<br/><small>Photo: ' + item.author + '</small>';
							return true;
				        },
						
				    };
	
	
				    if(fromURL) {
				    	if(options.galleryPIDs) {
				    		// parse real index when custom PIDs are used 
				    		// http://photoswipe.com/documentation/faq.html#custom-pid-in-url
				    		for(var j = 0; j < items.length; j++) {
				    			if(items[j].pid == index) {
				    				options.index = j;
				    				break;
				    			}
				    		}
					    } else {
					    	options.index = parseInt(index, 10) - 1;
					    }
				    } else {
				    	options.index = parseInt(index, 10);
				    }
	
				    // exit if index not found
				    if( isNaN(options.index) ) {
				    	return;
				    }
	
	
	
					
	
				    if(disableAnimation) {
				        options.showAnimationDuration = 0;
				    }
	
				    // Pass data to PhotoSwipe and initialize it
				    gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
	
				    // see: http://photoswipe.com/documentation/responsive-images.html
					var realViewportWidth,
					    useLargeImages = false,
					    firstResize = true,
					    imageSrcWillChange;
	
					gallery.listen('beforeResize', function() {
	
						var dpiRatio = window.devicePixelRatio ? window.devicePixelRatio : 1;
						dpiRatio = Math.min(dpiRatio, 2.5);
					    realViewportWidth = gallery.viewportSize.x * dpiRatio;
	
	
					    if(realViewportWidth >= 1200 || (!gallery.likelyTouchDevice && realViewportWidth > 800) || screen.width > 1200 ) {
					    	if(!useLargeImages) {
					    		useLargeImages = true;
					        	imageSrcWillChange = true;
					    	}
					        
					    } else {
					    	if(useLargeImages) {
					    		useLargeImages = false;
					        	imageSrcWillChange = true;
					    	}
					    }
	
					    if(imageSrcWillChange && !firstResize) {
					        gallery.invalidateCurrItems();
					    }
	
					    if(firstResize) {
					        firstResize = false;
					    }
	
					    imageSrcWillChange = false;
	
					});
	
					gallery.listen('gettingData', function(index, item) {
					    if( useLargeImages ) {
					        item.src = item.o.src;
					        item.w = item.o.w;
					        item.h = item.o.h;
					    } else {
					        item.src = item.m.src;
					        item.w = item.m.w;
					        item.h = item.m.h;
					    }
					});
	
				    gallery.init();
				};
	
				// select all gallery elements
				var galleryElements = document.querySelectorAll( gallerySelector );
				for(var i = 0, l = galleryElements.length; i < l; i++) {
					galleryElements[i].setAttribute('data-pswp-uid', i+1);
					galleryElements[i].onclick = onThumbnailsClick;
				}
	
				// Parse URL and open gallery if it contains #&pid=3&gid=1
				var hashData = photoswipeParseHash();
				if(hashData.pid && hashData.gid) {
					openPhotoSwipe( hashData.pid,  galleryElements[ hashData.gid - 1 ], true, true );
				}
			};
	
			initPhotoSwipeFromDOM('.demo-gallery');
		}
	</script>
	<script type="text/javascript">
		$('#slider').slick({
			autoplay:true,
	        dots: false,
	        arrows:false,
	        infinite: false,
	    	autoplay:true,
	    	autoplaySpeed:1000,
	        speed: 300,
	        slidesToShow: 1,
	        slidesToScroll: 1
	    });
	</script>
	<script type="text/javascript">
		$(function(){
			setStar();
			
			var sacle=2/3;
	
		    var height = $('.setImg:eq(0)').parents('.img').width()*sacle;
		    var num = 0;
		    var total = $('.setImg').length;
		    $('.setImg').each(function(){
	
	
		        
		        var _this = $(this);
	
		        var newImg = new Image();
	
		        newImg.onload = function(){
		            var imgH = _this.height();
		            var imgW = _this.width();
		            if(imgH<height){
		                //_this.css({'width':'auto','height':'100%','margin':'0 auto'});
		               // _this.css({'width':'auto','height':'100%'});
		                _this.css({'margin-top':(height-imgH)/2+'px'});
	
		            }
		            _this.parent().attr('data-size',"1600x"+parseInt(1600/imgW*imgH)) ;
		            _this.parent().attr('data-med-size',"1024x"+parseInt(1024/imgW*imgH)) ;
		            num++;
		            if(num==total){
		            	showBigImg();
		            }
		        }
		        newImg.src = _this.attr('src');       
		        
		        $(this).parents('.img').css({'height':height,'overflow':'hidden'});
	
		       
	
		    });
		})
		
		function setStar() {
	        $('.myCalendarStar').each(function(){
	            var iMyCalendarStar=$(this).attr('data-val').split('.');
	            
	            for(var i=0; i<iMyCalendarStar[0]; i++){
	                $(this).find('i').eq(i).addClass('s');
	            }

	            if(iMyCalendarStar[1]>0){
	                $(this).find('i').eq(iMyCalendarStar[0]).addClass('h');
	            }
	        });
		}
		
		function toLandlordInfo(){
			window.location.href = "${basePathHttps }landlord/${noLoginAuth }/landlordInfo?landlordUid=${houseDetailVo.landlordUid }&houseFid=${houseDetailVo.fid }&rentWay=${houseDetailVo.rentWay }&sourceFrom=${sourceFrom }";
		}
		
		function peizhiMore(){
			layer.open({
			    title: [
			        '',
			    ],
			    style: 'width:80%;height:17.5rem;max-height:100%',
			    content: assembleHtml()
			});
		}
		
		function assembleHtml(){
			var htmlString = '';
			htmlString += '<div class="layerpeizhi" style="height:15rem;overflow:auto;">\n';
			htmlString += '<table width="100%" border="0" class="table_tc table_th_bg table_td_bottomBor">\n';
			htmlString += '<tbody>\n';
			
			
			if("${not empty electricJson }" === "true"){
				var electricArray = $.parseJSON('${electricJson }');
				htmlString += '<tr class="tktitle"><td colspan="2">家电</td></tr>\n';
				$.each(electricArray,function(key,value){
					htmlString += '<tr><td class="text_left">'+value+'</td><td><img src="${staticResourceUrl}/share/img/peizhi/'+key+'.png" alt="" /></td></tr>\n';
				});
			}
			if("${not empty bathroomJson }" === "true"){
				var bathroomArray = $.parseJSON('${bathroomJson }');
				htmlString += '<tr class="tktitle"><td colspan="2">卫浴</td></tr>\n';
				$.each(bathroomArray,function(key,value){
					htmlString += '<tr><td class="text_left">'+value+'</td><td><img src="${staticResourceUrl}/share/img/peizhi/'+key+'.png" alt="" /></td></tr>\n';
				});
			}
			if("${not empty facilityJson }" === "true"){
				var facilityArray = $.parseJSON('${facilityJson }');
				htmlString += '<tr class="tktitle"><td colspan="2">设施</td></tr>\n';
				$.each(facilityArray,function(key,value){
					htmlString += '<tr><td class="text_left">'+value+'</td><td><img src="${staticResourceUrl}/share/img/peizhi/'+key+'.png" alt="" /></td></tr>\n';
				});
			}
			htmlString += '</tbody></table></div>';
			return htmlString;
		}
		
		
		var ua = navigator.userAgent.toLowerCase();
     
        
    	function targetToApp(){
    	    if(isIos()){
    	        openApp();
    	    }else{
    	    	layer.closeAll();
    	        if(isWeiXin1() || isWeibo() ){
    	            openMask();
    	           
    	        }else{
    	            openApp();
    	        }
    	    }
    	}

    	function openApp(){
    	    var unEncodeUrl = 'product=minsu&function=to_house_detail&android=&ios=&istop50=0&fid=${houseDetailVo.fid }&rentWay=${houseDetailVo.rentWay }';
    	    var base = new Base64();
    	    var EncodeUrl = base.encode(unEncodeUrl);
    	    if(isAndroid()){
    	        //android
    	        //此操作会调起app并阻止接下来的js执行
    	        $('body').append("<iframe src='ziroom://ziroom.app/openApp?p="+encodeURIComponent(EncodeUrl)+"' style='display:none' target='' ></iframe>");
    	        //没有安装应用会执行下面的语句
    	        setTimeout(function(){window.location.href='https://static8.ziroom.com/card_clean'},2000);
    	    }else{
    	        //ios
    	        window.location = 'https://static8.ziroom.com/openApp?p='+ encodeURIComponent(EncodeUrl);
    	    }
    	}
        
        
        function openMask(){
            $('.weixinMaskTip').show();
            $('.msxqContainer').css('overflow-y','hidden');
        }
        function closeMask(){
            $('.weixinMaskTip').hide();
            $('.msxqContainer').css('overflow-y','auto');
        }
        //判断微信内置浏览器
        function isWeiXin1(){
            if(ua.match(/MicroMessenger/i) == 'micromessenger'){
                return true;
            }else{
                return false;
            }
        }
        
        function isIos() {
            if (ua.match(/iPhone\sOS/i) == "iphone os") {
                return true;
            } else {
                return false;
            }
        }
        function isWeibo() {
        	//新浪微博
            if (ua.match(/WeiBo/i) == "weibo" ) {
                return true;
            } else {
                return false;
            }
        }
        
        function isAndroid() {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/Android/i) == "android") {
                return true;
            } else {
                return false;
            }
        }
        //加密、解密算法封装：
        function Base64() {
            // private property
            _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";  // public method for encoding
            this.encode = function (input) {
                var output = "";
                var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
                var i = 0;
                input = _utf8_encode(input);
                while (i < input.length) {
                    chr1 = input.charCodeAt(i++);
                    chr2 = input.charCodeAt(i++);
                    chr3 = input.charCodeAt(i++);
                    enc1 = chr1 >> 2;
                    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                    enc4 = chr3 & 63;
                    if (isNaN(chr2)) {
                        enc3 = enc4 = 64;
                    } else if (isNaN(chr3)) {
                        enc4 = 64;
                    }
                    output = output +    _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +    _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
                }
                return output;
            }
            _utf8_encode = function (string) {
                string = string.replace(/\r\n/g,"\n");
                var utftext = "";
                for (var n = 0; n < string.length; n++) {
                    var c = string.charCodeAt(n);
                    if (c < 128) {
                        utftext += String.fromCharCode(c);
                    } else if((c > 127) && (c < 2048)) {
                        utftext += String.fromCharCode((c >> 6) | 192);
                        utftext += String.fromCharCode((c & 63) | 128);
                    } else {
                        utftext += String.fromCharCode((c >> 12) | 224);
                        utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                        utftext += String.fromCharCode((c & 63) | 128);
                    }
                }
                return utftext;
            }
        }
		
		
        var browser = {
        	    versions: function() {
        	        var u = navigator.userAgent,
        	                app = navigator.appVersion;
        	        return {
        	            trident: u.indexOf('Trident') > -1, //IE内核
        	            presto: u.indexOf('Presto') > -1, //opera内核
        	            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
        	            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
        	            mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
        	            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
        	            android: u.indexOf('Android') > -1 || u.indexOf('Adr') > -1, //android终端
        	            iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
        	            iPad: u.indexOf('iPad') > -1, //是否iPad
        	            webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
        	            weixin: u.indexOf('MicroMessenger') > -1, //是否微信 （2015-01-22新增）
        	            qq: u.match(/QQ\//i) == "QQ/", //是否QQ
        	            weibo : u.match(/WeiBo/i) == "Weibo" //新浪微博
        	        };
        	    }(),
        	    language: (navigator.browserLanguage || navigator.language).toLowerCase()
        	} 

        	//是QQ
        	function isQQ() {
        	    return browser.versions.qq;
        	}
        
		
	</script>
	<script type="text/javascript">
		var url = {
		  iOS: 'appminsu://fid=${houseDetailVo.fid}&rentWay=${houseDetailVo.rentWay}',
		  andriod: 'appminsu://ziroom.app/openwith?fid=${houseDetailVo.fid}&rentWay=${houseDetailVo.rentWay}',
		  download: 'http://a.app.qq.com/o/simple.jsp?pkgname=com.ziroom.ziroomcustomer',
		  guide:"${basePathHttps }tenantHouse/${noLoginAuth }/guide"
		};
		
		//判断访问终端
		var browser = {
			versions: function() {
				var u = navigator.userAgent,
					app = navigator.appVersion;

				return {
					iOS: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
					iOS9 : !!u.match(/OS 9/ig), //iOS9
					android: u.indexOf('Android') > -1 || u.indexOf('Adr') > -1, //android终端
					weixin: u.indexOf('MicroMessenger') > -1, //是否微信 （2015-01-22新增）
				};
			}(),
			language: (navigator.browserLanguage || navigator.language).toLowerCase()
		}
		
		//是否android
		function isAndroid() {
			return browser.versions.android;
		}
		
		//是否ios
		function isIOS() {
			return browser.versions.iOS;
		}
		
		//是否ios9
		function isIOS9() {
			return browser.versions.iOS9;
		}
		
		//是否微信
		function isWeiXin() {
			return browser.versions.weixin;

		}

		$('a').click(function() {
			if(isWeiXin()){
				//提醒用户在浏览器中操作
				/* if(isAndroid()){
					window.location.href = url.guide;
				} */
			} else if(isIOS9()){
				/* window.location.href = url.iOS;
				setTimeout(function() {
					window.location.href = url.iOS;
			    }, 250);
			    setTimeout(function() {
			        location.reload();
			    }, 1000); */
			} else if(isIOS()){// iOS8及以下
				/* var ifr = document.createElement('iframe');
			    ifr.src = url.iOS;
			    ifr.style.display = 'none';
			    document.body.appendChild(ifr);
			    setTimeout(function(){
			        document.body.removeChild(ifr);
			    }, 3000); */
			} else if(isAndroid()){
				var ifr = document.createElement('iframe');
				ifr.src = url.andriod;
				ifr.style.display = 'none';
				document.body.appendChild(ifr);
				setTimeout(function(){
				    document.body.removeChild(ifr);
				    if(new Date() - openTime > 2500){
				        window.location.href = url.download;
				    }
				},2000)
			} else {
			   /*  window.location.href = url.download; */
			}
		});
	</script>
</body>
</html>