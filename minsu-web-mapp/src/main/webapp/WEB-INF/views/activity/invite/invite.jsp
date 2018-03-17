<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="description" content="自如民宿全国美宿可用，一起探索世界之美。" />
    <title>${name}送给你${money}元自如民宿旅行优惠劵，快开启你的焕心之旅吧！</title>
    <script>!function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));
    </script>
    <script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl }/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/activity/invite/css/resetMobile.css${VERSION}002">
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/activity/invite/css/style.css${VERSION}003">
</head>
<body>
<img src="${staticResourceUrl}/activity/invite/images/share.jpg" class="share">
<div class="main">
    <div id="appBox" class="mainBox">

        <div class="topBox">
            <div class="yaoqingTxt"><img src="${staticResourceUrl}/activity/invite/images/yaoqingTxt.png">
                <p>${money}</p>
            </div>

            <p class="yqmTxt">邀请码：${inviteCode}</p>
        </div>
        <div class="bottomBox">
            <div class="con">
                <div class="txt">
                    <p>${name}  送给你${money}元自如民宿旅行优惠劵 </p>
                    <p>开启你的焕心之旅吧！ </p>
                </div>
                <div class="btns">
                    <a href="javascript:;" class="btn" id="" onclick="accept()" ><img src="${staticResourceUrl}/activity/invite/images/btn_jieshou.png"></a>

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
<!-- 是否登录 -->
<input type="hidden" name="isLogin" id="isLogin" value="${isLogin}"/>
<!-- 是否点击去下单 -->
<input type="hidden" name="isToMakeOrder" id="isToMakeOrder">
<script type="text/javascript" src="${staticResourceUrl}/activity/invite/js/js.js${VERSION}001"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
    var title = "${name}送给你${money}元自如民宿旅行优惠劵，快开启你的焕心之旅吧！";
    var desc = "自如民宿全国美宿可用，一起探索世界之美。";
    var imgUrl = "${staticResourceUrl}/activity/invite/images/share.jpg";
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
                    title: title, // 分享标题
                    desc: desc, // 分享描述
                    link: window.location.href, // 分享链接
                    imgUrl: imgUrl, // 分享图标
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
                    title: title, // 分享标题
                    link: window.location.href, // 分享链接
                    imgUrl: imgUrl, // 分享图标
                    success: function () {
                        // 用户确认分享后执行的回调函数

                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                    }
                });

                // 分享到QQ
                wx.onMenuShareQQ({
                    title: title, // 分享标题
                    desc: desc, // 分享描述
                    link: window.location.href, // 分享链接
                    imgUrl: imgUrl, // 分享图标
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
<script type="text/javascript" >
    function accept(){
        var isLogin = $("#isLogin").val();
        if(isLogin == 'yes'){
            window.location.href="${basePath}invite/43e881/accept?code=${inviteCode}";
        } else {
            window.location.href="${basePath}customer/login?session_return_url_key=invite/43e881/accept?code=${inviteCode}";
        }
    }
</script>
<script src="http://catcher.ziroom.com/assets/js/boomerang.min.js${VERSION}001"></script>
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