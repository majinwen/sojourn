<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <script>!function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));</script>

    <meta name="format-detection" content="telephone=no, email=no">
    <meta name="HandheldFriendly" content="true">
    <meta name="MobileOptimized" content="320">
    <meta http-equiv="Cache-Control" content="no-siteapp">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-title" content="">
    <meta name="apple-mobile-web-app-status-bar-style" content="white">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>天使房东</title>
    <link rel="stylesheet" href="https://zhuanti.ziroom.com/zhuanti/guifan/css/animate.css${VERSION}001"/>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/stylesSeed.css${VERSION}003">
</head>
<body>
<!-- 分享图片 -->
<!--<img src="img/share.jpg" class="share">-->

<!-- 滚动部分 -->
<img src="${staticResourceUrl}/img/seed/share.jpg" style="position: absolute; left: -300px; top: -300px; width: 300px; height: 300px;" alt=""/>
<div id="page-wrapper">

    <ul class="content">

        <li class="page page-1">
            <div class="container"></div>
        </li>

        <!-- 正式开始 -->
        <li class="page">
            <div class="container p1" data-ani="fadeIn">

                <!-- content -->
                <div class="p1_logo" data-ani="fadeInDown"><img src="${staticResourceUrl}/img/seed/logo.png" alt=""/></div>
                <div class="p1_tit" data-ani="fadeIn" data-delay="200"><img src="${staticResourceUrl}/img/seed/p1_tit.png" alt=""/></div>
                <div class="p1_txt1" data-ani="fadeIn" data-delay="400"><img src="${staticResourceUrl}/img/seed/p1_txt1.jpg" alt=""/></div>

                <ul class="p1_ul">
                    <li class="p1_txt2" data-ani="fadeInUp" data-delay="600"><img src="${staticResourceUrl}/img/seed/p1_txt2.png?1" alt=""/></li>
                </ul>


                <!-- 往下滚动图标 -->
                <div class="arrowUp">
                    <img src="${staticResourceUrl}/img/seed/arrow.png">
                </div>

            </div>
        </li>

        <li class="page">
            <div class="container p2" data-ani="fadeIn">

                <!-- content -->
                <div class="p2_none"></div>
                <div class="p2_tit" data-ani="pulse"><img src="${staticResourceUrl}/img/seed/p2_tit.png" alt=""/></div>
                <ul class="p2_ul">
                    <li class="p2_txt1" data-ani="fadeInUp" data-delay="400"><img src="${staticResourceUrl}/img/seed/p2_txt1.png" alt=""/></li>
                    <li class="p2_txt2" data-ani="fadeInUp" data-delay="600"><img src="${staticResourceUrl}/img/seed/p2_txt2.png" alt=""/></li>
                    <li class="p2_txt3" data-ani="fadeInUp" data-delay="800"><img src="${staticResourceUrl}/img/seed/p2_txt3.png" alt=""/></li>
                    <li class="p2_txt4" data-ani="fadeInUp" data-delay="1000"><img src="${staticResourceUrl}/img/seed/p2_txt4.png" alt=""/></li>
                    <li class="p2_txt5" data-ani="fadeInUp" data-delay="1200"><img src="${staticResourceUrl}/img/seed/p2_txt5.png" alt=""/></li>
                </ul>


                <!-- 往下滚动图标 -->
                <div class="arrowUp">
                    <img src="${staticResourceUrl}/img/seed/arrow.png">
                </div>

            </div>
        </li>

        <li class="page">
            <div class="container p3" data-ani="fadeIn">

                <!-- content -->
                <div class="p3_none"></div>
                <div class="p3_tit" data-ani="pulse"><img src="${staticResourceUrl}/img/seed/p3_tit.png" alt=""/></div>
                <ul class="p3_ul">
                    <li data-ani="fadeInUp" data-delay="400"><span>*</span>如果您拥有一套民宿，在其他平台评价量大于10条且评分4.5分以上</li>
                    <li data-ani="fadeInUp" data-delay="600"><span>*</span>如果您拥有多套民宿，总评价量大于50条且评分4.5分以上</li>
                    <li data-ani="fadeInUp" data-delay="800"><span>*</span>填写申请信息后我们会有专业的管家团队与您取得联系</li>
                    <li data-ani="fadeInUp" data-delay="1000"><span>*</span>仅限前200位报名且通过审核的房东</li>
                </ul>
                <div class="p3_bg"><img src="${staticResourceUrl}/img/seed/p3_bg.jpg" alt=""/></div>

                <!-- 往下滚动图标 -->
                <div class="arrowUp">
                    <img src="${staticResourceUrl}/img/seed/arrow.png">
                </div>

            </div>
        </li>

        <li class="page">
            <div class="container p4" data-ani="fadeIn">

                <!-- content -->
                <div class="p4_bg"><img src="${staticResourceUrl}/img/seed/p4_bg.jpg" alt=""/></div>
                <div class="p4_txt_box">
                    <div class="p4_txt1" data-ani="lightSpeedIn"><img src="${staticResourceUrl}/img/seed/p4_txt1.png" alt=""/></div>
                    <div class="p4_txt2" data-ani="fadeInUp" data-delay="400"><img src="${staticResourceUrl}/img/seed/p4_txt2.png" alt=""/></div>
                    <ul class="p4_ul clearfix">
                        <li class="p4_txt3" data-ani="fadeIn" data-delay="1000"><img src="${staticResourceUrl}/img/seed/p4_txt3.jpg" alt=""/></li>
                        <li class="p4_txt4" data-ani="zoomIn" data-delay="1300"><img src="${staticResourceUrl}/img/seed/p4_txt4.png" alt=""/></li>
                    </ul>
                </div>


                <!-- 往下滚动图标 -->
                <div class="arrowUp">
                    <img src="${staticResourceUrl}/img/seed/arrow.png">
                </div>

            </div>
        </li>

        <li class="page">
            <div class="container p5" data-ani="fadeIn">

                <!-- content -->
                <div class="p5_bg"><img src="${staticResourceUrl}/img/seed/p5_bg.jpg" alt=""/></div>
                <div class="p5_txt_box">
                    <div class="p5_txt1" data-ani="lightSpeedIn"><img src="${staticResourceUrl}/img/seed/p5_txt1.png" alt=""/></div>
                    <div class="p5_txt2" data-ani="fadeInUp" data-delay="400"><img src="${staticResourceUrl}/img/seed/p5_txt2.png" alt=""/></div>
                    <ul class="p5_ul clearfix">
                        <li class="p5_txt3" data-ani="fadeIn" data-delay="1000"><img src="${staticResourceUrl}/img/seed/p5_txt3.jpg" alt=""/></li>
                        <li class="p5_txt4" data-ani="zoomIn" data-delay="1300"><img src="${staticResourceUrl}/img/seed/p5_txt4.png" alt=""/></li>
                    </ul>
                </div>
                    <a href="${basePath}activityApply/toSeedApply" class="p5_link" data-ani="fadeInUp" data-delay="2000"> 申请成为天使房东</a>

            </div>
        </li>


    </ul>



    <div class="dialog-guide">
        <div class="guide-wrapper">
            <p class="icon-rotate-phone"></p>
            为了更好的体验，请使用竖屏浏览
        </div>
    </div>

    <div class="loading">
        <div class="progressbar-wrap">
            <div class="progressbar">
                <div class="bar-wrap"><div class="bar" style="width: 0%;"></div></div>
            </div>
            <p class="progress-text">努力加载中...</p>
        </div>
    </div>

</div>


<script src="${staticResourceUrl }/js/zepto.min.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/motion.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/index.js${VERSION}002"></script>
    <!-- <script type="text/javascript">
    $(function(){
    	console.log('${basePath}');
    });

    </script> -->
<div style="display:none">
   <!--  <script type="text/javascript">
        var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
        document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F038002b56790c097b74c818a80e3a68e' type='text/javascript'%3E%3C/script%3E"));
    </script> -->
    <!-- <script type="text/javascript">
        var _gaq = _gaq || [];
        _gaq.push(['_setAccount', 'UA-26597311-1']);
        _gaq.push(['_setLocalRemoteServerMode']);
        _gaq.push(['_trackPageview']);
        (function() {
            var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
        })();
    </script> -->
</div>
</body>
</html>