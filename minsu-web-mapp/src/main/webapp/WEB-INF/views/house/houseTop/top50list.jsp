<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<meta charset="utf-8" />
	<title>2017自如民宿年度发现TOP50</title>
     <script>!function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));</script>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/style.css">
    <script type="text/javascript" src="${staticResourceUrl}/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="${staticResourceUrl}/js/main.js"></script>
    <script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" src="${staticResourceUrl}/js/iscroll.js"></script>
</head>
<style type="text/css">
* {
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

html {
	-ms-touch-action: none;
}

body,ul,li {
	padding: 0;
	margin: 0;
	border: 0;
}

body {
	overflow: hidden; /* this is important to prevent the whole page to bounce */
}

/* #wrapper1 {
	position: absolute;
	z-index: 1;
	top: 0;
	bottom: 48px;
	left: 0;
	width: 100%;
	overflow: hidden;
} */

#scroller {
	/* position: absolute;
 */	z-index: 1;
	-webkit-tap-highlight-color: rgba(0,0,0,0);
	width: 100%;
	/* -webkit-transform: translateZ(0);
	-moz-transform: translateZ(0);
	-ms-transform: translateZ(0);
	-o-transform: translateZ(0);
	transform: translateZ(0);
	-webkit-touch-callout: none;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	-webkit-text-size-adjust: none;
	-moz-text-size-adjust: none;
	-ms-text-size-adjust: none;
	-o-text-size-adjust: none;
	text-size-adjust: none; */
	height: auto!important;
}

.listbanner{width: 100%;overflow: hidden;}
.listbanner img{height:auto; width:100%;}

</style>
<body>


<div id="wrapper1">

    <div id="scroller">
	    <!-- banner -->
		<div class="listbanner">
		    <c:if test="${!empty top50ListTitleBackground }">
    			<img src="${top50ListTitleBackground }" />
	    	</c:if>
	    	<input type="hidden" id="TOP50HouseListUrl" value="${TOP50_HOUSE_LIST_URL }" >
	    	<input type="hidden" id="news_title" value="${top50ListShareTitle }" >
	        <input type="hidden" id="new_the" value="${ top50ListShareContent}" >
	        <input type="hidden" id="shareImg" value="${top50ListShareImgSrc }" >  
		</div>
		<!-- banner end -->
		<ul id="conList">
		    
		</ul>
		<div id="toshowMore" class="txtCenter" style="display:none;">滑动加载更多...</div>
	</div>
</div>

<!-- 底部按钮 -->
<div class="fixed">
    <a id="downloadapp" onclick="targetToApp()">
        <img src="https://zhuanti.ziroom.com/zhuanti/minsu/template/img/downloadapp.png">
    </a>
</div>
<div class="weixinMaskTip" style="display:none;" onclick="closeMask();" >
    <img src="${staticResourceUrl}/images/guard.png">
</div>

<span style="display: none;">
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
</span>
<script type="text/javascript">

var myScroll;

$(window).scroll(function () {  
     //已经滚动到上面的页面高度  
    var scrollTop = $(this).scrollTop();  
     //页面高度  
    var scrollHeight = $(document).height();  
      //浏览器窗口高度  
    var windowHeight = $(this).height();  
     //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作  
     if (scrollTop + windowHeight == scrollHeight && lanOrder.flag) {  
    	 lanOrder.page++;
    	 lanOrder.dolist(lanOrder.page); 
      }  
});


function targetToApp(){
    if(isIos()){
        openApp();
    }else{
        if(isWeiXin() || isWeibo() ){
            openMask();
        }else{
            openApp();
        }
    }
}

function openApp(){
    var unEncodeUrl = 'product=minsu&function=to_top50_house_list';
    var base = new Base64();
    var EncodeUrl = base.encode(unEncodeUrl);
    if(isAndroid()){
        //android
        //此操作会调起app并阻止接下来的js执行
        $('body').append("<iframe src='ziroom://ziroom.app/openApp?p="+encodeURIComponent(EncodeUrl)+"' style='display:none' target='' ></iframe>");
        //没有安装应用会执行下面的语句
        setTimeout(function(){window.location.href='https://static8.ziroom.com/card_clean'},2000);
    }else{
        //ios
        window.location = 'https://static8.ziroom.com/openApp?p='+ encodeURIComponent(EncodeUrl);
    }
}



</script>
<script>
 
 
(function ($) {

	//定义对象
	var lanOrder = {
		flag:true, 
		page:1,
		limit:10
	}

	
	/**
	 * 初始化
	 */
	lanOrder.init = function(){ 
 		
		lanOrder.dolist(1);
		
	}
	
	/**
	 * 获取列表的list
	 */
	lanOrder.dolist = function(page){
		 lanOrder.flag= false; 	
		$.getJSON($("#TOP50HouseListUrl").val(),{limit:lanOrder.limit,page:page},function(data){
			if(data.status == 0){
				var list = data.data.list;
				var total = data.data.total;

				if(lanOrder.page==1 && list.length==0){
 					$('#conList').html('<p class="txtCenter fsz16">没有数据哦~</p>');
 				}
				if(list.length==0){			
					
					return;
				}else{
					lanOrder.showList(list);					
				}

			}else{
				$('#conList').html('<p class="txtCenter fsz16">没有数据哦~</p>');
			}

		});
	}

	/**
	 * 展示列表
	 */
	lanOrder.showList = function (list){

 			var _html="";

 			if(list.length==0){
 				$('#conList').html('<p class="txtCenter fsz16">没有数据哦~</p>');
 			}
 			var startNum = (lanOrder.page-1)*lanOrder.limit+1;
 			var endNum = lanOrder.page*lanOrder.limit;
 			_html +='<div class="title line bgf9">'+startNum+'-'+endNum+'</div>';
 			
 			$.each(list,function(i,n){
 				var _url = SERVER_CONTEXT+"/houseTop/ee5f86/houseDetail?fid="+n.fid+"&rentWay="+n.rentWay;
 				
 				_html +='<li class="con line" onclick="javascript:gotoTop50SimpleDetail(\''+_url+'\')" >';
 				_html +=	'<img src="'+n.picUrl+'" >';
 				_html +=	'<p class="bold oneline fsz16">'+n.houseName+'</p>';
 				
 				if(n.indivLabelTipsList.length>0){
 					_html +=	'<ul class="pjtag tag">';
 					$.each(n.indivLabelTipsList,function(j,e){
 						_html +='<li>'+e+'</li>';
 					}); 					
 					_html +=	'</ul>';
 				}
 				
 				_html +='</li>';
		     
			});
 			$("#conList").append(_html);
 			lanOrder.flag= true;
 	}
	
	lanOrder.init();
	
	window.lanOrder = lanOrder;
	
	
	$.ajax({
        type: "GET",
        url: "https://minsuactivity.ziroom.com/topics/wxShare",
        data:{"currentUrl":window.location.href},
        dataType:"jsonp",
        jsonp:"callback",
        success: function(data){

          wx.config({
               debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
               appId:data.data.wShareVo.appId, // 必填，公众号的唯一标识
               timestamp:data.data.wShareVo.timestamp, // 必填，生成签名的时间戳
               nonceStr:data.data.wShareVo.nonceStr, // 必填，生成签名的随机串
               signature:data.data.wShareVo.signature,// 必填，签名，见附录1
               jsApiList:['onMenuShareAppMessage','onMenuShareTimeline','onMenuShareQQ'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
           });

           wx.ready(function () {
                   
                   wx.onMenuShareAppMessage({
                       title: "${top50ListShareTitle }", // 分享标题
                       desc: "${ top50ListShareContent}", // 分享描述
                       link:window.location.href, // 分享链接
                       imgUrl: "${top50ListShareImgSrc }", // 分享图标
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
                       title: "${top50ListShareTitle }", // 分享标题
                       link:window.location.href, // 分享链接
                       imgUrl:  "${top50ListShareImgSrc }", // 分享图标
                       success: function () { 
                           // 用户确认分享后执行的回调函数
                           
                       },
                       cancel: function () { 
                           // 用户取消分享后执行的回调函数
                       }
                   });
                   
                   wx.onMenuShareQQ({
                       title: "${top50ListShareTitle }", // 分享标题
                       desc: "${ top50ListShareContent}", // 分享描述
                       link:window.location.href, // 分享链接
                       imgUrl:  "${top50ListShareImgSrc }", // 分享图标
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
	
	
}(jQuery));


function gotoTop50SimpleDetail(url){
	window.location.href=url;
}



</script>
</body>
</html>


