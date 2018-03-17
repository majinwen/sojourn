<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
 	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
	<title>自如民宿 点十成金</title>
    <style type="text/css">
	*{margin: 0;padding: 0;}
	img{width: 100%;vertical-align: top;}
	.pr{position: relative;}
	.share{position: absolute;top: -500px;left: -500px;}
	.loadImg{opacity: 0;
    -webkit-transition:opacity .5s ease-in;
    -moz-transition:opacity .5s ease-in;
    -o-transition:opacity .5s ease-in;
    transition:opacity .5s ease-in; }    
	.loadImgShow{opacity: 1;}
	.ewm{position: absolute;left: 0;bottom: 0;}
    </style>
</head>

<body>
	<img src="${staticResourceUrl}/img/dscj/share.jpg" class="share">

	<img src="${staticResourceUrl}/img/dscj/pic1.jpg">
	<img src="${staticResourceUrl}/img/dscj/pic2.jpg" class="loadImg">
	<img src="${staticResourceUrl}/img/dscj/pic3.jpg" class="loadImg">
	<img src="${staticResourceUrl}/img/dscj/pic4.jpg" class="loadImg">
	<img src="${staticResourceUrl}/img/dscj/pic5.jpg" class="loadImg">
	<div class="pr">
		<img src="${staticResourceUrl}/img/dscj/pic6.jpg" class="loadImg">
		<img src="${staticResourceUrl}/img/dscj/ewm.png" class="loadImg ewm">
	</div>
	<img src="${staticResourceUrl}/img/dscj/pic7.jpg" class="loadImg">
	<img src="${staticResourceUrl}/img/dscj/pic8.jpg" class="loadImg">
	<img src="${staticResourceUrl}/img/dscj/pic9.jpg" class="loadImg">
	<img src="${staticResourceUrl}/img/dscj/pic10.jpg" class="loadImg">
	<img src="${staticResourceUrl}/img/dscj/pic11.jpg" class="loadImg">
	<img src="${staticResourceUrl}/img/dscj/pic12.jpg" class="loadImg">
	<img src="${staticResourceUrl}/img/dscj/pic13.jpg" class="loadImg">
	<img src="${staticResourceUrl}/img/dscj/pic14.jpg" class="loadImg">
	<img src="${staticResourceUrl}/img/dscj/pic15.jpg" class="loadImg">

<!--
	分享文案
标题文案：
十座城市的十种秋光，一起踏上旅程吧。
小文案：
在最美的旅行季节，勿虚度好时光。
-->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}003"></script>
<script type="text/javascript">
$(function(){
	showImg();   
    $(window).scroll(function(){
        showImg(); 
    });
    function showImg(){
		var iWinHeight = $(window).height();
		var iTop = $(window).scrollTop() + iWinHeight;
		
		$('.loadImg').each(function(){
				var _top = $(this).offset().top;
				if(_top < iTop){
					var dataAni ='';
	        		var dataDelay ='';
	        		if($(this).attr('data-ani'))
	        		{
	        			dataAni =$(this).attr('data-ani');
	        		}
	        		if($(this).attr('data-delay'))
	        		{
	        			dataDelay=$(this).attr('data-delay');
	        		}

	        		$(this).addClass(dataAni+' animated loadImgShow '+dataDelay);
				}
			});	
	}
});
</script>
	<%--<script src="http://catcher.ziroom.com/assets/js/boomerang.min.js"></script>--%>
	<%--<script>--%>
	   <%--BOOMR.init({--%>
	   <%--beacon_url: "http://catcher.ziroom.com/beacon",--%>
	   <%--beacon_type: "POST",--%>
	   <%--page_key: "minsu_H5_dscj_160913"});--%>
	<%--</script>--%>

<!--统计代码-->
<div style="display:none"> 
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
 <!--    <script type="text/javascript">
    var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
    document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F038002b56790c097b74c818a80e3a68e' type='text/javascript'%3E%3C/script%3E"));
    </script> -->
    <script type="text/javascript">
    /*  var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-26597311-1']);
    _gaq.push(['_setLocalRemoteServerMode']);
    _gaq.push(['_trackPageview']);
    (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
    })();  */
    
    wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId:'${appId}', // 必填，公众号的唯一标识
	    timestamp:'${timestamp}' , // 必填，生成签名的时间戳
	    nonceStr:'${nonceStr}', // 必填，生成签名的随机串
	    signature:'${signature}',// 必填，签名，见附录1
	    jsApiList:['onMenuShareAppMessage','onMenuShareTimeline','onMenuShareQQ'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});

	wx.ready(function () {
		wx.onMenuShareAppMessage({
		    title: ' 十座城市的十种秋光，一起踏上旅程吧。', // 分享标题
		    desc: '在最美的旅行季节，勿虚度好时光。', // 分享描述
		    link: '${basePath}topics/nationalDay_dscj?shareFlag=2', // 分享链接
		    imgUrl: '${staticResourceUrl}/img/dscj/share.jpg', // 分享图标
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
		    title: '十座城市的十种秋光，十足心意的我们想跟你一起踏上旅程。', // 分享标题
		    link: '${basePath}topics/nationalDay_dscj?shareFlag=2', // 分享链接
		    imgUrl: '${staticResourceUrl}/img/dscj/share.jpg', // 分享图标
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		
		wx.onMenuShareQQ({
			title: '十座城市的十种秋光，十足心意的我们想跟你一起踏上旅程。', // 分享标题
		    desc: '在最美的旅行季节，勿虚度好时光。',// 分享描述
		    link: '${basePath}topics/nationalDay_dscj?shareFlag=2', // 分享链接
		    imgUrl: '${staticResourceUrl}/img/dscj/share.jpg', // 分享图标
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