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
		<title>迪士尼</title>
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
				<img src="${staticResourceUrl}/img/disini/banner.jpg">
				<p>跟随着爱丽丝梦游仙境、让小熊维尼领着你上蹿下跳，或是跳上飞溅山的水上竹筏一跃而下，再或者坐进巨雷山的古老矿车穿梭于山谷之中。夜幕降临时，主城堡前的绚烂烟花耀眼夺目，为你美妙的迪士尼之行画上一个圆满的句号。给自己一趟奇幻旅程，体会肆无忌惮的孩童时光。</p>
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
		<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}005" type="text/javascript"></script>
		<script src="${staticResourceUrl}/js/common.js${VERSION}006"></script>
		<%--<script src="http://catcher.ziroom.com/assets/js/boomerang.min.js"></script>--%>
		<%--<script>--%>
		   <%--BOOMR.init({--%>
		   <%--beacon_url: "http://catcher.ziroom.com/beacon",--%>
		   <%--beacon_type: "POST",--%>
		   <%--page_key: "minsu_H5_hxdsn_160913"});--%>
		<%--</script>--%>
		<span style="display:none;">
	  <!--   <script type="text/javascript">
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