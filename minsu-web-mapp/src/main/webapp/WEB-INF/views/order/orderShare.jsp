<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
    <meta name="format-detection" content="telephone=no">
    <!-- <script type="text/javascript" src="http://minsustatic.t.ziroom.com/mapp/js/header.js?v=js.version.20160802001"></script> -->
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
    <script type="text/javascript" src="${staticResourceUrl }/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="${staticResourceUrl }/js/slick.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <link rel="stylesheet" href="${staticResourceUrl }/css/slick.css"/>
    <title>这次去${cityName}，我住这儿</title>
    <style>
        *{
            padding: 0;
            margin: 0;
        }
        header{
            height: 3.4rem;
            padding-left: 1rem;
            line-height: 3.4rem;
        }
        h1{
            font-size: 1rem;
            color: #444;
        }
        .banner{
            padding: 0 1rem;
            overflow:hidden;
        }
        .banner img{
            width: 100%;
        }
        .info{
            padding: 0 1rem;
        }
        h2{
            line-height: 2.9rem;
            font-size: 0.9rem;
            color: #444;
        }
        .black_border{
            display: block;
            border: 1px solid #333;
            width: 1.7rem;
            height: 0;
        }
        .house_info{
            padding: 1rem 0;
            font-size: 0.7rem;
            line-height: 1.05rem;
            color: #999;
        }
        .go_home{
            display: block;
            margin: 0 auto 1rem;
            width: 7rem;
            height: 1.8rem;
            line-height: 1.8rem;
            text-decoration: none;
            border-radius: 0.15rem;
            color: #fff;
            background: #F9CA79;
            font-size: 0.7rem;
            text-align: center;
        }
        .user_info{
            padding: 0 0.8rem;
        }
        .user_info_box{
            position: relative;
            padding: 3rem 0.3rem 1rem;
            border-top: 1px solid #eee;
            border-bottom: 1px solid #eee;
        }
        .user_info_p{
            width: 40%;
            min-height:4.2rem;
            -webkit-min-height:4.2rem;
            border: 1px solid #eeeeee;
            padding: 1rem 3.85rem 1rem 1rem;
            font-size: 0.7rem;
            line-height: 1.05rem;
            color: #999;
        }
        .user_pic{
            position: absolute;
            left: 52%;
            top: 1rem;
            width: 46.65%;
            overflow:hidden;
        }
        .user_pic img{
            width: 100%;
        }
        footer{
            padding-top: 1rem;
        }
        .S_erwei{
            width: 6rem;
            height: 6rem;
            margin: 0 auto 0.75rem;
            opacity: 0.5;
        }
        .S_erwei img{
            width: 100%;
        }
        .footer_p{
            height: 0.7rem;
            line-height: 0.7rem;
            font-size: 0.7rem;
            text-align: center;
            color: #666;
            margin-bottom: 1.65rem;
        }
    </style>
    <script>
        $(function(){
        	
        	$('.user_pic').css('height',$('.user_pic').css('width'));
        	
        	var h=$('#J_slick').width()/3*2;
        	
        	$('#J_slick').css('height',h+'px');
        	
            $('#J_slick').slick({
                dots: true,
                arrows:false,
                infinite: true,
                speed: 300,
                slidesToShow: 1,
                slidesToScroll: 1,
               	autoplay:true,
                autoplaySpeed:2500
            });
        });
        
        //微信分享
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
	                    title: "淳美民居——${houseName}", // 分享标题
	                    desc: "${houseDesc}", // 分享描述
	                    link: window.location.href, // 分享链接
	                    imgUrl: "${defaultPic}", // 分享图标
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
	                	title: "淳美民居——${houseName}", // 分享标题
	                    link: window.location.href, // 分享链接
	                    imgUrl: "${defaultPic}", // 分享图标
	                    success: function () { 
	                        // 用户确认分享后执行的回调函数
	                        
	                    },
	                    cancel: function () { 
	                        // 用户取消分享后执行的回调函数
	                    }
	                });
		               
	                // 分享到QQ
	                wx.onMenuShareQQ({
	                	title: "淳美民居——${houseName}", // 分享标题
	                    desc: "自如民宿与你 开启一段美妙之旅", // 分享描述
	                    link: window.location.href, // 分享链接
	                    imgUrl: "${defaultPic}", // 分享图标
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
</head>
<body>
<header>
    <h1>想找高颜值美屋，上自如民宿呀～</h1>
</header>
<section>
    <div class="banner" id="J_slick">
		<c:forEach items="${picList}" var="pic">
			<div><img src="${pic}" alt=""/></div>
		</c:forEach>
		<%-- <div><img src="${staticResourceUrl }/images/image/banner1.png" alt=""/></div>
        <div><img src="${staticResourceUrl }/images/image/banner1.png" alt=""/></div>
        <div><img src="${staticResourceUrl }/images/image/banner1.png" alt=""/></div>
        <div><img src="${staticResourceUrl }/images/image/banner1.png" alt=""/></div> --%>
    </div>
    <div class="info">
        <h2>${houseName}</h2>
        <b class="black_border"></b>
        <p class="house_info">${houseAroundDesc}</p>
        <a href="${houseUrl}" class="go_home">查看他的小屋</a>
    </div>
    <div class="user_info">
        <div class="user_info_box">
            <p class="user_info_p">您好，我是${name}～${introduce}</p>
            <div class="user_pic">
                <img src="${headIcon}" alt=""/>
            </div>
        </div>
    </div>
</section>
<footer>
    <div class="S_erwei"><img src="${staticResourceUrl }/images/image/erwei.png" alt=""/></div>
    <p class="footer_p">下载APP，开启焕心之旅</p>
</footer>
</body>
</html>