<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="zh">

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="applicable-device" content="mobile">
		<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
<script>!function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));</script>
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="yes" name="apple-touch-fullscreen" />
		<title>历史的重塑，建筑的呼吸</title>
		<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/zhuanti_news.css${VERSION}003">
		<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/pageStyles.css${VERSION}003">


	</head>

	<body>
	<style type="text/css">
		.banners{background: #fff; padding-bottom: 24px; text-align: left; margin-bottom:.6rem;}
		.banners .imgs{margin:.4rem;}
		.banners dl{margin: .4rem .4rem .5rem .4rem; font-size: .3rem;}
		.banners dl dt{font-size: .48rem; line-height:.9rem; color: #444; font-weight: bold;}
		.banners dl dd{font-size: .3733rem; color: #444; line-height:.55rem;}
		.share{position: absolute; left: -300px; top: -300px; width: 300px; height: 300px;}
		.jcList li{padding: .4rem 0;}

		.peoDetail{background: #fff; padding: .4rem; margin-bottom: .6rem}
		.peoDetail dl{padding: .5rem 0;}
		.peoDetail dt{height: 1.7rem; overflow: hidden; position: relative; font-weight: bold; font-size:.45rem; padding-left: 2rem;}
		.peoDetail dt img{width: 1.7rem; width: 1.7rem; position: absolute; left: 0; top:0;}
		.peoDetail dt h2{line-height: .8rem; margin-top: .1rem; display: inline-block; border-bottom: solid 1px #eec959; width: 1.9rem;}
		.peoDetail dt h3{line-height: .8rem;}
		.peoDetail dl:nth-child(2) dt h2{border-bottom: solid 1px #7ba7ea}
		.peoDetail dl:nth-child(3) dt h2{border-bottom: solid 1px #ed7a7a}
		.peoDetail dd{padding: .4rem 0;}
		.peoDetail dd p{font-size: .3733rem; color: #666; line-height:.55rem;}

		.jcList .txt h2{line-height: .8rem; font-size: .4rem; height: .8rem; width: 7.5rem;}
	</style>
	<img src="${staticResourceUrl}/img/sdf/share.jpg" class="share">
	<div class="jcBox">
		<div class="banners">
			<img src="${staticResourceUrl}/img/sdf/1.jpg">
			<div class="imgs"><img src="${staticResourceUrl}/img/sdf/2.jpg"></div>
			<div class="imgs"><img src="${staticResourceUrl}/img/sdf/3.jpg"></div>
			<dl>
				<dt>初遇树德坊</dt>
				<dd>
坐落于法租界的树德坊建于上世纪30年代末，是“老上海”的代表，也是现在旅行者心之向往的时髦生活方式。梧桐树掩映，充满浪漫情调及浓郁的文化气息，吸引众多文人雅士聚居于此。
或许是天生血液中流动着对美居的无限热爱，自如CEO熊林先生一直在寻觅中国真正的最美民宿。经好友推荐，熊林先生和自如上海团队得知树德坊内一栋小洋楼空置许久，欣喜不已。“这是中国的最美民宿，可以让更多人去深度了解和体会曾风花雪月、也饱经风霜的老上海。”</dd>
			</dl>

			<div class="imgs"><img src="${staticResourceUrl}/img/sdf/4.jpg"></div>
			<dl>
				<dt>重塑</dt>
				<dd>
为了让树德坊的这栋洋房焕发新生命，自如民宿从全国百位设计师中挑选了拥有独立民宿品牌，极具老房改造经验的优秀设计师—沈虹。同时也荣幸邀请到在公共空间设计领域拥有丰富经验的上海艺术家、上海视觉艺术学院的刘毅老师为其创作装置艺术品。自如CEO熊林先生、设计师沈虹和艺术家刘毅先生认为，这并非法租界的普通花园洋房，而是老上海人真正的居所，每一次改造，都是老建筑与人们生活方式的共同进化。重塑树德坊老洋房意味着重现年代建筑文明。而在民宿空间内创作艺术品，也是国内首次公共空间艺术与住宅空间的合作。</dd>
			</dl>
			<div class="imgs"><img src="${staticResourceUrl}/img/sdf/5.jpg"></div>
			<div class="imgs"><img src="${staticResourceUrl}/img/sdf/6.jpg"></div>
			<dl>
				<dt>树德坊的树</dt>
				<dd>
在这座焕然一新的洋房里，生长着一颗“会发光、会呼吸的树”，从一楼直至三楼屋顶。这件艺术品称为《树德坊的树》，灵感来源于艺术家刘毅先生对老上海生活片段的回忆，以及对于“树”这一生命体的敬意。作品的枝干生长在建筑的各个空间中，传统钩针编织工艺花布的绚丽图案丰富了树的表皮，透过会呼吸的光与空间呢喃对话。通过它，可以体现建筑与自然的共生关系。此次的跨界合作，也是国内首次突破与创新。</dd>
			</dl>
			<div class="imgs"><img src="${staticResourceUrl}/img/sdf/7.jpg"></div>
			<div class="imgs"><img src="${staticResourceUrl}/img/sdf/8.jpg"></div>
			<div class="imgs"><img src="${staticResourceUrl}/img/sdf/9.jpg"></div>
			<div class="imgs"><img src="${staticResourceUrl}/img/sdf/10.jpg"></div>
			<dl>
				<dt>小城（Hamlet）</dt>
				<dd>设计师沈虹初见这栋房子时激动的不断感叹，这才是真正老上海人的住所，将民宿做出家的味道也一直是其设计理念和灵感来源。房内的种种选择不是出于设计，而是源于生活-最大程度尊重和保留老洋房中独具韵味的部分-老梁、地板、楼梯面等。老房子虽面积小，但是内部错落有致，设计师将每层打通，形成流动的空间，卧房、客厅、露台都可以互相直通。路径之中现惊喜，空间感成为真正的奢侈品。</dd>
			</dl>
		</div>
		<div class="peoDetail">
			<dl>
				<dt>
					<img src="${staticResourceUrl}/img/sdf/p_xionglin.jpg" alt="">
					<h2>自如CEO</h2>
					<h3>熊林</h3>
				</dt>
				<dd>
					<p>非常幸运能让我遇到如此完整保存的老式洋房，也很开心能与生长于上海、深爱上海文化的艺术家刘毅先生，酷爱建筑与设计的90后设计师企鹅一起合作，希望延承她的美丽，更希望赋予她新的生机与可能性。</p>
					<p>第一次完工验收时，我愤怒地更换了工程队，找来与我合作的江苏籍老工长邱师傅，重新组织团队施工打磨，一切努力，只为让她重现于世时一如她当年般的雅致绰约！</p>
				</dd>
			</dl>
			<dl>
				<dt>
					<img src="${staticResourceUrl}/img/sdf/p_liuyi.jpg" alt="">
					<h2>艺术家</h2>
					<h3>刘毅</h3>
				</dt>
				<dd>
					<p>初到树德坊，这里的环境就立即让我想起了以前小时候的弄堂生活。这种老房子的内部结构往往给人感觉比较压抑，所能获得的光照量也很少。因此，我想为室内空间创造充足的光线，一方面可以大大提高居住体验，另一方面，光线是建筑的呼吸，是建筑与自然元素的交汇，通过光，体现人、建筑与自然的共生关系。当我再次充分考察了整个建筑的环境、历史文化，甚至细到一株小小的植物形态，不断在小区内来回兜转、走动，借以寻找灵感。</p>
<p>最后，还是决定采取灯光装置艺术作品来重新定制空间内的光线。除了作为大型室内灯光装置，它也成为了室内主要的光线来源之一。甚至还有一株枝桠伸进二楼的一间卧室内，变成房间里的照明灯。三层楼高的大树优雅地贯穿了室内狭长的空间，为居室带来了一种昂然“向上”的力量。这不是一个平面作品，我希望这个作品的每一个角度都能达到完美。每个角度都能与这个空间形成一个个完美的关系。</p>
				</dd>
			</dl>
			<dl>
				<dt>
					<img src="${staticResourceUrl}/img/sdf/p_shenhong.jpg" alt="">
					<h2>设计师</h2>
					<h3>沈虹</h3>
				</dt>
				<dd>
					<p>上海的老洋房有种独特的魅力，每一次改造变化，都是老建筑与人们的生活方式在共同进化。在老房子里感觉相当奇妙，因为内心与日常生活产生了有意思的反差。市井弄堂，生活气息，树影斑驳…这次设计最大程度的尊重和保留老洋房里原来的美丽的部位，老梁、地板、楼梯面等。老房子虽然面积很小，但是内部错落有致非常有趣味，我把整个四层空间打开，做成流动的空间，卧房、客厅、露台都可以互相直通，路径之中都有惊喜。空间感才是真正的奢侈品。</p>
<p>为了使裸露的原始老旧的部分散发最大的魅力，整个软硬装选择复古、简洁却极具设计感的手法，并且没有特别明显的地域风格。因为我认为那些所谓的潮流装饰，会束缚人们的生活，而住所应该让人感觉到自由自在。我踏过很多老法租界里的弄堂去找有趣的老物件，还有一些是我以前收藏的民国时期人们家中的器物。客人会被过去人讲究的生活方式所打动。这次的设计方向就是让大家回归到老房子这样以为老者到来的宁静、安全和气质中来。我并不想让房子变成明星和潮流，因为重点永远是房子里的人。</p>
				</dd>
			</dl>
		</div>
		
		<ul class="jcList">
			<c:forEach items="${list }"  var="obj">
					<li>
					<a href="javascript:void(0);" onclick="minsutuijianJump('${obj.fid }','${obj.rentWay }','${shareFlag }','${shareUrl }');">		
						<div class="img">
							<img src="${obj.picUrl } " class="setImg">
							<span class="t"><b>￥</b><fmt:parseNumber integerOnly="true" value="${obj.price/100 }" />元/天</span>
						</div>
						<div class="txt">
							<div class="ts">
								<img src="${obj.landlordUrl }">
							</div>
							<h2>${obj.houseName }</h2>
							<p class="gray">${obj.rentWayName } ｜ 可住${obj.personCount }人  </p>
							<p class="star">
								<b class="myCalendarStar" data-val="${obj.evaluateScore }">
									<i></i><i></i><i></i><i></i><i></i> 
								</b>
								<span>${obj.evaluateScore }分</span>
								<c:if test="${obj.evaluateCount != 0}">
									<b class="gray">评论${obj.evaluateCount }条</b>
								</c:if>
							</p>
		
						</div>
					</a>
					</li>
				 </c:forEach>
		</ul>
	</div>
<!--
转发文案如下：
标题：历史的重塑，建筑的呼吸
 
艺术家刘毅、自如CEO熊林、设计师沈虹
重塑上海树德坊1934老洋房
-->
		<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}003" type="text/javascript"></script>
		<script src="${staticResourceUrl}/js/common.js${VERSION}003"></script>
		<%--<script src="http://catcher.ziroom.com/assets/js/boomerang.min.js"></script>--%>
		<%--<script>--%>
		   <%--BOOMR.init({--%>
		   <%--beacon_url: "http://catcher.ziroom.com/beacon",--%>
		   <%--beacon_type: "POST",--%>
		   <%--page_key: "minsu_H5_shudefang_160913"});--%>
		<%--</script>--%>
		<!--统计代码-->
<div style="display:none"> 
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <!-- <script type="text/javascript">
    var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
    document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F038002b56790c097b74c818a80e3a68e' type='text/javascript'%3E%3C/script%3E"));
    </script> -->
    <script type="text/javascript">
  /*   var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-26597311-1']);
    _gaq.push(['_setLocalRemoteServerMode']);
    _gaq.push(['_trackPageview']);
    (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
    })(); */
    
    wx.config({
	    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId:'${appId}', // 必填，公众号的唯一标识
	    timestamp:'${timestamp}' , // 必填，生成签名的时间戳
	    nonceStr:'${nonceStr}', // 必填，生成签名的随机串
	    signature:'${signature}',// 必填，签名，见附录1
	    jsApiList:['onMenuShareAppMessage','onMenuShareTimeline','onMenuShareQQ'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.ready(function () {
		wx.onMenuShareAppMessage({
		    title: '艺术家刘毅、自如CEO熊林、设计师沈虹。', // 分享标题
		    desc: '重塑上海树德坊1934老洋房。', // 分享描述
		    link: '${basePath}topics/shudefang?paramJson=page_shudefang_param&shareFlag=2', // 分享链接
		    imgUrl: '${staticResourceUrl}/img/sdf/share.jpg', // 分享图标
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
		    title: '艺术家刘毅、自如CEO熊林、设计师沈虹重塑上海树德坊1934老洋房。', // 分享标题
		    link: '${basePath}topics/shudefang?paramJson=page_shudefang_param&shareFlag=2', // 分享链接
		    imgUrl: '${staticResourceUrl}/img/sdf/share.jpg', // 分享图标
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		
		wx.onMenuShareQQ({
			title: '艺术家刘毅、自如CEO熊林、设计师沈虹。', // 分享标题
		    desc: '重塑上海树德坊1934老洋房。', // 分享描述
		    link: '${basePath}topics/shudefang?paramJson=page_shudefang_param&shareFlag=2', // 分享链接
		    imgUrl: '${staticResourceUrl}/img/sdf/share.jpg', // 分享图标
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
    </script>
    
</div>
</body>

</html>