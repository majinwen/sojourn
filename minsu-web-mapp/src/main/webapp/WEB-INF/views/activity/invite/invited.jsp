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

        <div class="topBoxQc">
            <div class="img"><img src="${staticResourceUrl}/activity/invite/images/errorTxt.png"></div>

        </div>
        <div class="bottomBox">
            <div class="con">
                <div class="txt">
                    <p>每个用户仅可接受一次好友邀请，打开APP分享你自己的邀请码同样可以获得感谢优惠劵哦！</p>
                    <%--<p>开启你的焕心之旅吧！ </p>--%>
                </div>

                <div class="btns">
                    <a href="javascript:;" class="btnss" id="toBower" ><img src="${staticResourceUrl}/activity/invite/images/btns_go.png" alt="打开APP"></a>
                    <a href="javascript:;" class="btnss" id="toDown" ><img src="${staticResourceUrl}/activity/invite/images/btns_download.png" alt="下载APP"></a>
                </div>
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
<input type="hidden" name="isLogin" id="isLogin" value="yes"/>
<!-- 是否点击去下单 -->
<input type="hidden" name="isToMakeOrder" id="isToMakeOrder">
<script type="text/javascript" src="${staticResourceUrl}/activity/invite/js/js.js${VERSION}001"></script>

<script type="text/javascript" >
    //判断访问终端
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
    // 是否android
    function isAndroid() {
        return browser.versions.android;
    }
    // 是否ios
    function isIos() {
        return browser.versions.ios;
    }
    // 是否微信
    function isWeiXin() {
        return browser.versions.weixin;
    }
    // 是QQ
    function isQQ() {
        return browser.versions.qq;
    }
    // 是微博
    function isWeibo(){
        return browser.versions.weibo;
    }

    $(function(){
        if(!isWeiXin() && !isQQ()){
            if(isAndroid()){
                window.location.href="appminsu://ziroom.app/openApp?isMinsu=true";
            }else if(isIos()){
                window.location.href="appminsu://isMinsu=true";
            }
        }
    })


    $('#toBower').click(function(){
        if(isWeibo()){
            $('#boserShow').show();
        }else if(isWeiXin()){
            $('#boserShow').show();
        }else if(isQQ() && isIos()){
            $('#boserShow').show();
        }else if(isAndroid()){
            window.location.href="appminsu://ziroom.app/openApp?isMinsu=true";
        }else if(isIos()){
            window.location.href="appminsu://isMinsu=true";
        }
    });

    $('#boserShow').click(function(){
        $('#boserShow').hide();
    });

    $('#toDown').click(function(){
        if(isWeiXin()){
            $('#boserShow').show();
        }else if(isWeibo() && isIos()){
            $('#boserShow').show();
        }else if(isAndroid()){
            window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.ziroom.ziroomcustomer";
        }else if(isIos()){
            window.location.href="https://itunes.apple.com/us/app/zi-ru-chuang-zao-pin-zhi-zu/id685872176?l=zh&amp;ls=1&amp;mt=8";
        }
    });
</script>
<script src="http://catcher.ziroom.com/assets/js/boomerang.min.js"></script>
<script>
    BOOMR.init({
        beacon_url: "http://catcher.ziroom.com/beacon",
        beacon_type: "POST",
        page_key: "minsu_app_lvxingjijin"
    });
</script>
<!--统计代码-->
<!-- <span style="display: none">
	<script type="text/javascript">
        var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
        document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F038002b56790c097b74c818a80e3a68e' type='text/javascript'%3E%3C/script%3E"));
    </script>
</span> -->
<script type='text/javascript'>
    $('.btnss,.btn').click(function(){
        var _this = $(this);
        _this.css({'opacity':'.9'});
        setTimeout(function(){
            _this.css({'opacity':'1'});
        },30);
    });
</script>
<script type='text/javascript'>
    var _vds = _vds || [];
    window._vds = _vds;
    (function(){
        _vds.push(['setAccountId', '8da2730aaedd7628']);
        (function() {
            var vds = document.createElement('script');
            vds.type='text/javascript';
            vds.async = true;
            vds.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'dn-growing.qbox.me/vds.js';
            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(vds, s);
        })();
    })();
</script>
</body>
</html>