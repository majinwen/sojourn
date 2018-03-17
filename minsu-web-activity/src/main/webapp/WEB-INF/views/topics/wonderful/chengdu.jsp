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
		<title>成都</title>
		<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/zhuanti_news.css">
		<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/pageStyles.css">


	</head>
<style>.jcList .txt h2 {
	    font-size: .4rem;
	    line-height: .6rem;
	}
	</style>
	<body>
	
	<div class="jcBox">
		<div class="banners">
			<img src="${staticResourceUrl}/img/chengdu/banner.jpg">
			<p>一座来了就不想走的城市，自古以来富足丰饶，拥“天府之国”之美誉。人生最闲情逸致的事，都可以在成都找到。成都人常挂在嘴边的“巴适”是舒服合适的意思，满城的茶馆正是这座城市悠闲的气质的最好体现。闲适的生活节奏是这座城市的名片，而满街的川菜馆、火锅店、小吃店更是让人流连忘返的罪魁祸首。成都，仿佛一张柔软的沙发，仓促的步履行到此处便放缓，不禁自拔的陷进温柔乡的怀抱。</p>
		</div>
		<c:forEach items="${list }"  var="obj">
		
			<c:if test="${obj.fid eq '8a9099775658507701565a19180d015a' }">
				<div class="boxs">
					<h2 class="title"><span>宽窄巷子</span></h2>
					<img src="${staticResourceUrl}/img/chengdu/kuanxiangzi.jpg">
					<p>成都是天府，窄巷子就是成都的“府”。 宅中有园，园里有屋，屋中有院，院中有树，树上有天，天上有月……这是中国式的院落梦想，也是窄巷子的生活梦想。</p>
				</div>
			</c:if>
			<c:if test="${obj.fid eq '8a90997756830b4b01568a171642022d' }">
				<div class="boxs">
					<h2 class="title"><span>太古里</span></h2>
					<img src="${staticResourceUrl}/img/chengdu/taiguli.jpg">
					<p>接壤于人潮涌动的春熙路，尽享优越交通和人流优势，比邻的千年古刹大慈寺更为其增添独特的历史和文化韵味。</p>
				</div>
			</c:if>
			<c:if test="${obj.fid eq '8a909978568d834801568ebc7b5a0101' }">
				<div class="boxs">
					<h2 class="title"><span>锦里 </span></h2>
					<img src="${staticResourceUrl}/img/chengdu/jinli.jpg">
					<p>锦里曾是西蜀历史上最古老、最具有商业气息的街道之一，早在秦汉、三国时期便闻名全国。蜿蜒曲折的院落、街巷与水岸、湖泊、荷塘、石桥相呼应，别有一番意境。</p>
				</div>
			</c:if>
		<ul class="jcList">
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
		</ul>
		</c:forEach>
	</div>	
		<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}004" type="text/javascript"></script>
		<script src="${staticResourceUrl}/js/common.js${VERSION}005"></script>
		<%--<script src="http://catcher.ziroom.com/assets/js/boomerang.min.js"></script>--%>
		<%--<script>--%>
		   <%--BOOMR.init({--%>
		   <%--beacon_url: "http://catcher.ziroom.com/beacon",--%>
		   <%--beacon_type: "POST",--%>
		   <%--page_key: "minsu_H5_hxcd_160913"});--%>
		<%--</script>--%>
		<span style="display:none;">
	    <!-- <script type="text/javascript">
	    var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
	    document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F038002b56790c097b74c818a80e3a68e' type='text/javascript'%3E%3C/script%3E"));
	    </script> -->
	    <!-- <script type="text/javascript">
	       (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	      })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
	    
	      ga('create', 'UA-26597311-1', 'auto');
	      ga('send', 'pageview');
	    </script> -->
	
	    </span>
	</body>

</html>