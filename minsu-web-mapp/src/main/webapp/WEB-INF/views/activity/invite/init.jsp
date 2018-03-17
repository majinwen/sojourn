<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="applicable-device" content="mobile">
    <meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta name="format-detection" content="telephone=no">
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

        <div class="topBox">
            <div class="img"><img src="${staticResourceUrl}/activity/invite/images/txt.png"></div>
        </div>
        <div class="bottomBox">
            <a href="javascript:;" id="toViewMore" onclick="showDetail()" class="more">了解详情</a>
            <div class="con">
                <div class="txt">
                    <p>送上好友${money}元自如民宿旅行优惠劵</p>
                    <p>当好友开启旅程时,您可获得${inviterMoney}元奖励优惠劵 </p>
                </div>

                <div class="btns">

                    <c:if test="${initStatus == 1 }">
                        <a href="javascript:;" class="btn" onclick="goLogin()" id="toLoginBtn" ><img src="${staticResourceUrl}/activity/invite/images/btn_denglv.png"></a>
                    </c:if>

                    <!-- 已登录 -->
                    <c:if test="${initStatus == 2 }">
                        <div id="toShareBtn" onclick="toShare()" class="toShare">
                            <p>邀请码：${inviteCode} </p>
                        </div>
                    </c:if>

                    <!-- 已登录 但APP没更新最新版本-->
                    <c:if test="${initStatus == 3 }">
                        <div class="toShare toShareDis"></div>
                    </c:if>
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
<input type="hidden" name="isLogin" id="isLogin" value="yes"/>
<!-- 是否点击去下单 -->
<input type="hidden" name="isToMakeOrder" id="isToMakeOrder">

<script src="http://catcher.ziroom.com/assets/js/boomerang.min.js"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script type="text/javascript" >

//    // 弹层
    function layerModal(obj){
        if(!obj.fn){
            obj.fn = function(){
                layer.close();
            }
        }
        layer.open({
            type: 1,
            title:obj.title || '',
            skin: 'layui-layer-demo',
            area: ['7rem'],
            closeBtn:obj.isClose || 0,
            shadeClose:obj.shadeClose || false,
            content: obj.str || '',
            btn:obj.btn || '',
            success:function(){
                obj.fn();
            }

        });
    }
    var  initStatus  = '${initStatus}';
    var  sourceType  = '${sourceType}';
    var  shareUrl  = '${shareUrl}';
    var  money  = '${money}';
    var  actLimit  = '${actLimit}';
    var  inviterMoney  = '${inviterMoney}';
    var  nickName  = '${nickName}';

    var  sharePic  = '${staticResourceUrl}' + "/activity/invite/images/share.jpg";

    function goLogin() {
        if (sourceType == 1){
            <%-- 安卓 --%>
            window.WebViewFunc.toLoginPage();
        }else if(sourceType == 2){
            <%-- iOS --%>
            window.toLoginPage();
        }
    }

    function toShare() {
        var title= nickName + "送给你"+money+"元自如民宿旅行优惠劵，快开启你的焕心之旅吧！";
        var content= "自如民宿全国美宿可用，一起探索世界之美。";
        if (sourceType == 1){
            <%-- 安卓 --%>
            window.WebViewFunc.share(shareUrl,title,content,sharePic);
        }else if(sourceType == 2){
            <%-- iOS --%>
            window.share(shareUrl,title,content,sharePic);
        }
    }

    var detailHtmlStr = '<div class="detailMore"><h3>活动详情</h3><ul>'
            +'<li>· 每个用户获得专属邀请码，点击分享按钮即可邀请好友下单。</li>'
            +'<li>· 受邀用户在下单时填写邀请码信息即可减免'+money+'元，订单金额满'+actLimit+'元可用。</li>';
    if(inviterMoney != 0){
        detailHtmlStr += '<li>· 受邀用户入住后，您将获得'+inviterMoney+'元奖励优惠劵，可在个人中心优惠券查看。</li>';
    }
    detailHtmlStr += '</ul></div>';

    var appNoUpdate='<div class="appNoUpdate">'
            +'<p>您的应用未更新到最新版本<br />无法获取邀请码哦！</p>'
            +'<div class="btn"><a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.ziroom.ziroomcustomer" ><img src="${staticResourceUrl}/activity/invite/images/btn_update.png" alt="立即更新APP，获取邀请码"></a></div>'
            +'</div>';

    function  showDetail() {
        layerModal({str:detailHtmlStr,shadeClose:true});
    }

    if (initStatus == 3) {
        layerModal({str: appNoUpdate, shadeClose: false});
    } else if (initStatus == 2) {
        //已经登录
    }

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

<style type="text/css">
    .layermbox1 .layermchild{border-radius: .2rem;}
    .layermmain .section{margin: 0 auto;}
</style>

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