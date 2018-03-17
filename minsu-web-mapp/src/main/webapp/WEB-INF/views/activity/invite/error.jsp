<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>邀请好友下单赚旅行优惠劵</title>
    <script>!function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));
    </script>
    <script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl }/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/activity/invite/css/resetMobile.css${VERSION}002">
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/activity/invite/css/style.css${VERSION}002">

</head>
<body>
<img src="${staticResourceUrl}/activity/invite/images/share.jpg" class="share">
<div class="main">
    <div id="appBox" class="mainBox">

        <div class="topBoxQcError">
            <div class="txt">
                <h2>${error}</h2>
            </div>

        </div>


    </div>
</div>
<!--横竖屏提示代码-->
<div class="dialog-guide">
    <div class="guide-wrapper">
        <p class="icon-rotate-phone"></p>
        为了更好的体验,请使用竖屏浏览
    </div>
</div>
<div id="boserShow">

</div>
<!-- 是否登录 -->
<input type="hidden" name="isLogin" id="isLogin" value=""/>
<!-- 是否点击去下单 -->
<input type="hidden" name="isToMakeOrder" id="isToMakeOrder">
<script type="text/javascript" src="${staticResourceUrl}/activity/invite/js/js.js${VERSION}001"></script>

<script type="text/javascript" >
    $('#toBower').click(function(){
        $('#boserShow').show();
    });
    $('#boserShow').click(function(){
        $('#boserShow').hide();
    })
</script>
<script src="http://catcher.ziroom.com/assets/js/boomerang.min.js"></script>
<script>
    BOOMR.init({
        beacon_url: "http://catcher.ziroom.com/beacon",
        beacon_type: "POST",
        page_key: "minsu_app_lvxingjijin"
    });
</script>
<script type='text/javascript'>
    $('.btnss,.btn').click(function(){
        var _this = $(this);
        _this.css({'opacity':'.9'});
        setTimeout(function(){
            _this.css({'opacity':'1'});
        },30);
    });
</script>
<!--统计代码-->
<!-- <span style="display: none">
	<script type="text/javascript">
        var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
        document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F038002b56790c097b74c818a80e3a68e' type='text/javascript'%3E%3C/script%3E"));
    </script>
</span> -->
</body>
</html>