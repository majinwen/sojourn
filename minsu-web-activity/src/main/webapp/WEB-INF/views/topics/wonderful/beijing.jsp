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
		<title>北京</title>
		<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/zhuanti_news.css${VERSION}003">
		<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/pageStyles.css${VERSION}003">


	</head>
	<style>.jcList .txt h2 {
	    font-size: .4rem;
	    line-height: .6rem;
	}
	</style>
	<body>
	<div class="jcBox">
		<div class="banners">
			<img src="${staticResourceUrl}/img/beijing/1.jpg">
			<p>这是一座古老的城市，也是一座崭新的都市。帝都北京除了千年皇城和数不清的历史古迹之外，餐饮文化更是博大精深，深藏不露。许多百年老店，经典老字号，都隐藏在京城的各处。这些老字号多年的经营，积累了扎实的菜肴品质和原汁原味儿的北京味道，是一堂堂不能错过的京城饮食文化课。</p>
		</div>
		<c:forEach items="${list }"  var="obj">
			<c:if test="${obj.fid eq '8a909977560317640156036809dd0060' }">
				<div class="boxs">
				
					<h2 class="title"><span>簋街</span></h2>
					<img src="${staticResourceUrl}/img/beijing/2.jpg">
					<p>北京饮食文化的代表，也成为了人们心中的一个向往、一处欢乐，甚至是一段茶余饭后的谈资。</p>
				</div>
			</c:if>
			<c:if test="${obj.fid eq '8a90997755edb2cf0155fd54f7e704d8' }">
				<div class="boxs">
					<h2 class="title"><span>大栅栏</span></h2>
					<img src="${staticResourceUrl}/img/beijing/3.jpg">
					<p>许多著名的老字号就发源于此，比如全聚德烤鸭店、瑞蚨祥绸布店、同仁堂药铺、六必居酱菜园等，直到今天仍然长盛不衰。</p>
				</div>  
			</c:if>
			<c:if test="${obj.fid eq '8a909978564b0b3a015650a3a524011f' }">
				<div class="boxs">
					<h2 class="title"><span>烟袋斜街</span></h2>
					<img src="${staticResourceUrl}/img/beijing/4.jpg">
					<p>在人潮涌动的街道上，你时不时就会走到风格迥异的店铺面前，可能以前这些店面里面卖的就是名家字画，但现在更多的是独具特色的小吃。</p>
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
		<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}002" type="text/javascript"></script>
		<script src="${staticResourceUrl}/js/common.js${VERSION}003"></script>
		<%--<script src="http://catcher.ziroom.com/assets/js/boomerang.min.js"></script>--%>
	<%--<script>--%>
	   <%--BOOMR.init({--%>
	   <%--beacon_url: "http://catcher.ziroom.com/beacon",--%>
	   <%--beacon_type: "POST",--%>
	   <%--page_key: "minsu_H5_hxbj_160913"});--%>
	<%--</script>--%>
		
	<span style="display:none;">
 <!--    <script type="text/javascript">
    var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
    document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F038002b56790c097b74c818a80e3a68e' type='text/javascript'%3E%3C/script%3E"));
    </script> -->
   <!--  <script type="text/javascript">
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