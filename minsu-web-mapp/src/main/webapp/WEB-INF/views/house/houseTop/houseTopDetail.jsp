<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<title>${houseDetailVo.houseName }</title>
     <script>!function(N,M){function L(){var a=I.getBoundingClientRect().width;a/F>540&&(a=540*F);var d=a/10;I.style.fontSize=d+"px",D.rem=N.rem=d}var K,J=N.document,I=J.documentElement,H=J.querySelector('meta[name="viewport"]'),G=J.querySelector('meta[name="flexible"]'),F=0,E=0,D=M.flexible||(M.flexible={});if(H){console.warn("将根据已有的meta标签来设置缩放比例");var C=H.getAttribute("content").match(/initial\-scale=([\d\.]+)/);C&&(E=parseFloat(C[1]),F=parseInt(1/E))}else{if(G){var B=G.getAttribute("content");if(B){var A=B.match(/initial\-dpr=([\d\.]+)/),z=B.match(/maximum\-dpr=([\d\.]+)/);A&&(F=parseFloat(A[1]),E=parseFloat((1/F).toFixed(2))),z&&(F=parseFloat(z[1]),E=parseFloat((1/F).toFixed(2)))}}}if(!F&&!E){var y=N.navigator.userAgent,x=(!!y.match(/android/gi),!!y.match(/iphone/gi)),w=x&&!!y.match(/OS 9_3/),v=N.devicePixelRatio;F=x&&!w?v>=3&&(!F||F>=3)?3:v>=2&&(!F||F>=2)?2:1:1,E=1/F}if(I.setAttribute("data-dpr",F),!H){if(H=J.createElement("meta"),H.setAttribute("name","viewport"),H.setAttribute("content","initial-scale="+E+", maximum-scale="+E+", minimum-scale="+E+", user-scalable=no"),I.firstElementChild){I.firstElementChild.appendChild(H)}else{var u=J.createElement("div");u.appendChild(H),J.write(u.innerHTML)}}N.addEventListener("resize",function(){clearTimeout(K),K=setTimeout(L,300)},!1),N.addEventListener("pageshow",function(b){b.persisted&&(clearTimeout(K),K=setTimeout(L,300))},!1),"complete"===J.readyState?J.body.style.fontSize=12*F+"px":J.addEventListener("DOMContentLoaded",function(){J.body.style.fontSize=12*F+"px"},!1),L(),D.dpr=N.dpr=F,D.refreshRem=L,D.rem2px=function(d){var c=parseFloat(d)*this.rem;return"string"==typeof d&&d.match(/rem$/)&&(c+="px"),c},D.px2rem=function(d){var c=parseFloat(d)/this.rem;return"string"==typeof d&&d.match(/px$/)&&(c+="rem"),c}}(window,window.lib||(window.lib={}));</script>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/style.css">
    <link rel="stylesheet" href="${staticResourceUrl}/css/photoswipe.css">
    <link rel="stylesheet" href="${staticResourceUrl}/css/default-skin/default-skin.css">
    <script type="text/javascript" src="${staticResourceUrl}/js/zepto.min.js"></script>
    <script type="text/javascript" src="${staticResourceUrl}/js/photoswipe.min.js"></script>
    <script type="text/javascript" src="${staticResourceUrl}/js/photoswipe-ui-default.min.js"></script>
    <script type="text/javascript" src="${staticResourceUrl}/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="${staticResourceUrl}/js/main.js"></script>
    <script type="text/javascript" src="${staticResourceUrl}/js/iscroll.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
     <script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
    <script type="text/javascript">
    var myScroll;
    function loaded () {
        myScroll = new IScroll('#wrapper', {
            scrollX: true,
            scrollY: false,
            snapStepX: $('#scroller .slide').outerWidth(true),
            momentum: false,
            snap: true,
            snapSpeed: 300,
            keyBindings: true
        });
    }
</script>
</head>
<body>
<div class="bg" id="bgbg">
    <!-- banner -->
    <div class="bannerBox">
        <div class="banner" style="background:url(${houseDetailVo.defaultPic }) no-repeat;background-size: 100%; ">
        <!-- 相册 -->
        <div id="demo-test-gallery" class="demo-gallery" data-pswp-uid="1">
        <!-- 第一张是banner图，其他为相册中图 -->
          <%--  <a href="${houseDetailVo.defaultPic }" data-size="1600x1600" data-med="${houseDetailVo.defaultPic }" data-med-size="1024x1024" data-author="Folkert Gorter" class="demo-gallery__img--main">
                <img src="${houseDetailVo.defaultPic }" alt="">
            </a> --%>
            <c:if test="${fn:length(houseDetailVo.picDisList)>0 }" >
            <c:forEach items="${houseDetailVo.picDisList }" var="pic" >
				 <a href="${pic.eleValue }" data-size="1600x1600" data-med="${pic.eleValue }" data-med-size="1024x1024" data-author="Folkert Gorter" class="demo-gallery__img--main">
	                <img src="${pic.eleValue }" alt="">
	            </a>
            </c:forEach>
            </c:if>
        </div>
      </div>
      <div class="txt">
            <h3 class="name">${houseDetailVo.houseName }</h3>
            <ul class="tag">
                  <c:if test="${fn:length(houseDetailVo.houseTopInfoVo.labelTipsTopList)>0 }" >
                  <c:forEach items="${houseDetailVo.houseTopInfoVo.labelTipsTopList }" var="label" >
                       <li>${label.name } </li>
                  </c:forEach>
                </c:if>
            </ul>
        </div>
    </div>
   
    <!-- banner end -->

    <div class="title line">
        <span>${houseDetailVo.houseTopInfoVo.topTitle }</span>  <span class="faxian"><img src="${houseDetailVo.houseTopInfoVo.topTitlePic }" alt=""></span>
    </div>
    <input value="${houseDetailVo.houseTopInfoVo.shareContent}" type="hidden" id="shareContent"/>
    <div class="bigBox" id="bigBox">
    ${houseTopColumnVoStr }
       <%--  <div class="title con txtCenter txtRight txtLeft">亮点</div>

        <div class="con line">
            <p class="point">竹林梯田环绕</p>
            <p class="point">半小时车行山路十八弯至山顶的唯一民宿</p>
            <p class="point">百年木质结构吊脚楼</p>
        </div>

        <!-- 视频 -->
        <div class="S_video con">
            <iframe id="J_iframe" style="z-index:1;" src="" allowfullscreen="" frameborder="0"></iframe>
        </div>
        
        <div class="title con txtLeft">描述</div>
        <div class="con"><p class="bold mb18 fsz18">[投老归来终寄宝俭堂]</p></div>
        <div class="con"><p class="info">“栖于山水之间，生活在浅淡山田。”这是栖水主人梦
    想的生活，而带着他的梦想所塑造的这栋民宿，也是
    名副其实。一幢四层的小楼房，倚水静驻在号称“小千
    岛湖”的浦江前吴通济湖畔。栖水不大，由一栋DuLi的
    四层洋房和一个私密临湖院子组成。整个栖水只有四
    个房间并只做整栋预定。而几乎每位入住的客人都会
    在门开启的那刻，都会被掩映入眼的湖景惊艳到。</p></div>

        <div class="con smallTitle fsz16">1.倚水静驻在号称“小千岛湖”</div>
        <div class="con"><img src="${staticResourceUrl}/images/11.png" alt=""></div>
        <div class="con"><p class="picFrom">图片来自小橙子的美梦</p></div>
        <div class="con"><p class="info col666">无论是庭院、阳台，还是仅有的四间客房，都是面朝
    湖景。这栋房里子的每扇窗都是一幅活的静村山水
    画。湖畔庭院是客人最喜欢呆的地方。</p></div> --%>
    </div>



    <!-- 查看全文button -->
    <div class="goAllBtn" id="allBtn" onclick="targetToApp()">查看全文</div>
    <!-- top50 button -->
    <div class="goTop50Btn line" onclick="javascript:gotoTopList()"><img src="${houseDetailVo.houseTopInfoVo.topMiddlePic }"></div>

    <!-- 房东信息 -->
    <div class="fangdong">
        <p>您好~</p>
        <p>我是房东${houseDetailVo.landlordName }</p>
        <img src="${houseDetailVo.landlordIcon }" class="pic">
    </div>
    <div class="con">
        <div class="fdinfo">
          ${houseDetailVo.customerIntroduce}
        </div>
        <p class="point bold">价格：￥${fn:split(houseDetailVo.housePrice/100, ".")[0] }/晚</p>
        <p class="point bold">地址：${houseDetailVo.houseAddr }</p>
    </div>
    <div class="bar"></div>
    <!-- 评价 -->
    <c:if test="${!empty houseDetailVo.evaluateCount && houseDetailVo.evaluateCount >0 }" > 
	    <div class="con line">
	        <div class="pj">
	            <div class="ltitle">评价</div>
	            <div class="ckqb" onclick="targetToApp();">查看全部</div>
	            <div class="goal">${houseDetailVo.evaluateCount }条</div>
	        </div>
	        <div class="pjinfo">
	            <p>${houseDetailVo.tenantEvalVo.evalContent }</p>
	            <div class="pjer">${houseDetailVo.tenantEvalVo.evalDate } ${houseDetailVo.tenantEvalVo.customerName }</div>
	        </div>
	    </div>
    </c:if>
    <!-- 房屋信息 -->
    <div class="con line">
        <div class="ltitle">房屋信息</div>
        <ul class="pjtag tag">
             <c:forEach items="${houseDetailVo.houseNameInfoList }" var="houseNameInfo" >
               <li>${houseNameInfo }</li>
             </c:forEach>
        </ul>
    </div>
    <!-- 房源设施 -->
    <div class="con line">
        <div class="tit">
            <div class="ltitle">服务·设施</div>
            <div class="ckqb" onclick="targetToApp();">查看全部</div>   
        </div>
        <ul class="icon">
             <c:forEach items="${houseDetailVo.listHouseFacilities }" var="houseFac"> 
              <li><img src="${houseFac.iconTwoUrl}"><p>${houseFac.text}</p></li>
             </c:forEach>
        </ul>
    </div>
    <!-- 入住规则 -->
    <div class="con line"><div class="ltitle mb18">入住规则</div></div>
    <div class="pl18">
        <ul>
              <c:forEach items="${houseDetailVo.listCheckRuleVo }" var="ruleVo">
	            <li>
	                <span class="left">${ruleVo.name }</span>
	                <c:if test="${ruleVo.clickType =='1' }">
	                  <span class="right gold" onclick="targetToApp()" >${ruleVo.val }</span>
	                </c:if>
	                 <c:if test="${ruleVo.clickType !='1' }">
	                <span class="right">${ruleVo.val }</span>
	                </c:if>
	                
	            </li>
	          </c:forEach>
           <!--  <li>
                <span class="left">入住时间</span>
                <span class="right">下午2:00</span>
            </li>
            <li>
                <span class="left">离开时间</span>
                <span class="right">下午2:00</span>
            </li>
            <li>
                <span class="left">最短入住时间</span>
                <span class="right">下午2:00</span>
            </li>
            <li>
                <span class="left">入住时间</span>
                <span class="right">下午2:00</span>
            </li>
            <li>
                <span class="left">入住时间</span>
                <span class="right">下午2:00</span>
            </li> -->
        </ul>
    </div>
    <div class="more line" id="more">展开更多</div>

    <!-- 其他房间 -->
    <div class="con" id="ortherRoom" style="display: none;">
        <div class="ltitle mb18">其他房间</div>
    </div>
    <!-- 轮播图 -->
    <div id="viewport">
      <%--   <div id="wrapper">
            <div id="scroller">
                <div class="slide">
                    <img src="${staticResourceUrl}/images/pic1.png" alt="">
                    <p class="bold oneline fsz16">1王府井中式王府井新中式雅致胡同一览无余的...</p>
                    <p class="fsz16 mb18">￥9022/晚</p>
                    <div class="det shu"><span>整套出租</span><span>整套出租</span><span>可住2人</span></div>
                    <div class="det"><span>4.8分</span><span>评价12</span></div>
                </div>
                <div class="slide">
                    <img src="${staticResourceUrl}/images/pic1.png" alt="">
                    <p class="bold oneline fsz16">2王府井中式王府井新中式雅致胡同一览无余的...</p>
                    <p class="fsz16 mb18">￥9022/晚</p>
                    <div class="det shu"><span>整套出租</span><span>整套出租</span><span>可住2人</span></div>
                    <div class="det"><span>4.8分</span><span>评价12</span></div>
                </div>
                <div class="slide">
                    <img src="${staticResourceUrl}/images/pic1.png" alt="">
                    <p class="bold oneline fsz16">3王府井中式王府井新中式雅致胡同一览无余的...</p>
                    <p class="fsz16 mb18">￥9022/晚</p>
                    <div class="det shu"><span>整套出租</span><span>整套出租</span><span>可住2人</span></div>
                    <div class="det"><span>4.8分</span><span>评价12</span></div>
                </div>
            </div>
        </div> --%>
    </div>


<!-- 相册 -->
    <!-- Root element of PhotoSwipe. Must have class pswp. -->
<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">

    <!-- Background of PhotoSwipe. 
         It's a separate element as animating opacity is faster than rgba(). -->
    <div class="pswp__bg"></div>

    <!-- Slides wrapper with overflow:hidden. -->
    <div class="pswp__scroll-wrap">

        <!-- Container that holds slides. 
            PhotoSwipe keeps only 3 of them in the DOM to save memory.
            Don't modify these 3 pswp__item elements, data is added later on. -->
        <div class="pswp__container">
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
        </div>

        <!-- Default (PhotoSwipeUI_Default) interface on top of sliding area. Can be changed. -->
        <div class="pswp__ui pswp__ui--hidden">

            <div class="pswp__top-bar">

                <!--  Controls are self-explanatory. Order can be changed. -->

                <div class="pswp__counter"></div>

                <button class="pswp__button pswp__button--close" title="Close (Esc)"></button>

                <button class="pswp__button pswp__button--share" title="Share"></button>

                <button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button>

                <button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>

                <!-- Preloader demo http://codepen.io/dimsemenov/pen/yyBWoR -->
                <!-- element will get class pswp__preloader--active when preloader is running -->
                <div class="pswp__preloader">
                    <div class="pswp__preloader__icn">
                      <div class="pswp__preloader__cut">
                        <div class="pswp__preloader__donut"></div>
                      </div>
                    </div>
                </div>
            </div>

            <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
                <div class="pswp__share-tooltip"></div> 
            </div>

            <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">
            </button>

            <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">
            </button>

            <div class="pswp__caption">
                <div class="pswp__caption__center"></div>
            </div>

        </div>

    </div>

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
    
	
	function gotoAppLoad(){
		window.location.href="http://t.cn/RqOAHxW";
	}
	
   function gotoTopList(){
    	window.location.href="${TOP50_HOUSE_BANGDAN_URL}";
    }    
	
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
	    var unEncodeUrl = 'product=minsu&function=to_house_detail&istop50=1&android=&ios=&fid=${houseDetailVo.fid}&rentWay=${houseDetailVo.rentWay}';
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
</span>
<script>
    (function(){
        //视频
        //根据iframe实际高度计算里面链接的传参
      /*   var oIframe=$('#J_iframe');
        var w=Math.floor(oIframe.width());
        var h=Math.floor(oIframe.height());
        oIframe.attr('src','${houseDetailVo.houseTopInfoVo.topTitlePic };width='+w+'&amp;height='+h+'&amp;auto=0'); */

        // 查看更多
        if($('.pl18 ul li').length>3){
            $('#more').show();
        }
        if($('#article').outerHeight()>1200){
            $('#allBtn').show();
        }
        $('#more').on('tap', function(event) {
            // event.preventDefault();
            $('.pl18 ul').css('height', 'auto');
            $('#more').remove();
        });

        $('#allBtn').on('tap', function(event) {
            // event.preventDefault();
            $('#article').css('height', 'auto');
            $('#allBtn').remove();
        });

        // 其他房间轮播宽度
       /*  var width = $('#scroller .slide').length;
        $('#scroller').css('width', width*9.2+'rem'); */
        initSimilarHouse();
    	function initSimilarHouse(){
    		 var rentWay = "${houseDetailVo.rentWay}";
    		 $("#viewport").empty();
    		 if(rentWay == "1"){
    			 $.ajax({
     			    type: "POST",
     			    url: '${TOP50_HOUSE_SIMILAR_URL}',
     			    dataType:"json",
     			    data: {
     			    	landlordUid:"${houseDetailVo.landlordUid}",
     			    	fid:"${houseDetailVo.fid}",
     			    	rentWay:"${houseDetailVo.rentWay}",
     			    	isTop50Online:1
     				},
     			    success: function (result) {
     			    	if(result){
     			    		 var house = result.data.list;
     			    		var _html  = "";
     			    		if(house&&house.length>0){
     			    			_html  += "<div id='wrapper'>";
     	     			    		_html  += "<div id='scroller'>";
     	     			    		$("#ortherRoom").show();
     	     			    		 for(var i=0;i<house.length;i++){
     	     			    			 
     	     			    			var _url = SERVER_CONTEXT+"/houseTop/ee5f86/top50SimpleDetail?fid="+house[i].fid+"&rentWay="+house[i].rentWay;    	     			    			 
     	     			    			 
     	     			    			_html +="<div class='slide' onclick='javascript:gotoTop50Detail(\""+_url+"\")' >";
     	     			    			_html +="<img src='"+house[i].picUrl+"' alt=''>";
     	     			    			_html +="<p class='bold oneline fsz16'>"+house[i].houseName+"</p>";
     	     		                    _html +=" <p class='fsz16 mb18'>￥"+Math.floor(house[i].price) / 100  +"/晚</p>";
     	     		                    _html +="<div class='det shu'><span>"+house[i].cityName+"</span><span>"+house[i].rentWayName+"</span><span>可住"+house[i].personCount+"人</span></div>";
     	     		                    _html +=" <div class='det'><span>"+house[i].evaluateScore+"分</span><span>评价"+house[i].evaluateCount+"</span></div>";
     	     		                    _html +="</div>";
     	     			    		 }
     	     			    		 _html +="</div>";
     	     			    		 _html +="</div>";
     	     			             $("#viewport").html(_html);
     	     			            var width = $('#scroller .slide').length;
     	     			            $('#scroller').css('width', width*9.2+'rem'); 
     	     			            loaded();
     	     			            
     			    		}
     			    		
     			    		
     			    	}
     			    },
     			    error: function(result) {
     		        }
     		    });
    		 }
    	    	
    	 }
        
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
                           title: "${houseDetailVo.houseTopInfoVo.topShareTitle }", // 分享标题
                           desc:"${houseDetailVo.houseTopInfoVo.shareContent}", // 分享描述
                           link:window.location.href, // 分享链接
                           imgUrl:  "${houseDetailVo.defaultPic }", // 分享图标
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
                           title: "${houseDetailVo.houseTopInfoVo.topShareTitle }", // 分享标题
                           link:window.location.href, // 分享链接
                           imgUrl:"${houseDetailVo.defaultPic }", // 分享图标
                           success: function () { 
                               // 用户确认分享后执行的回调函数
                               
                           },
                           cancel: function () { 
                               // 用户取消分享后执行的回调函数
                           }
                       });
                       
                       wx.onMenuShareQQ({
                           title: "${houseDetailVo.houseTopInfoVo.topShareTitle }", // 分享标题
                           desc: "${houseDetailVo.houseTopInfoVo.shareContent}", // 分享描述
                           link:window.location.href, // 分享链接
                           imgUrl:  "${houseDetailVo.defaultPic }", // 分享图标
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
    })();

    
    
    function gotoTop50Detail(url){
    	window.location.href=url;
    }
    
   
</script>
<script type="text/javascript">
    (function() {

        var initPhotoSwipeFromDOM = function(gallerySelector) {

            var parseThumbnailElements = function(el) {
                var thumbElements = el.childNodes,
                        numNodes = thumbElements.length,
                        items = [],
                        el,
                        childElements,
                        thumbnailEl,
                        size,
                        item;

                for(var i = 0; i < numNodes; i++) {
                    el = thumbElements[i];

                    // include only element nodes
                    if(el.nodeType !== 1) {
                        continue;
                    }

                    childElements = el.children;

                    size = el.getAttribute('data-size').split('x');

                    // create slide object
                    item = {
                        src: el.getAttribute('href'),
                        w: parseInt(size[0], 10),
                        h: parseInt(size[1],9),
                        author: el.getAttribute('data-author')
                    };

                    item.el = el; // save link to element for getThumbBoundsFn

                    if(childElements.length > 0) {
                        item.msrc = childElements[0].getAttribute('src'); // thumbnail url
                        if(childElements.length > 1) {
                            item.title = childElements[1].innerHTML; // caption (contents of figure)
                        }
                    }


                    var mediumSrc = el.getAttribute('data-med');
                    if(mediumSrc) {
                        size = el.getAttribute('data-med-size').split('x');
                        // "medium-sized" image
                        item.m = {
                            src: mediumSrc,
                            w:'100%',
                            h:'auto'
                        };
                    }
                    // original image
                    item.o = {
                        src: item.src,
                        w: '100%',
                        h: 'auto'
                    };

                    items.push(item);
                }

                return items;
            };

            // find nearest parent element
            var closest = function closest(el, fn) {
                return el && ( fn(el) ? el : closest(el.parentNode, fn) );
            };

            var onThumbnailsClick = function(e) {

                e = e || window.event;
                e.preventDefault ? e.preventDefault() : e.returnValue = false;

                var eTarget = e.target || e.srcElement;

                var clickedListItem = closest(eTarget, function(el) {
                    return el.tagName === 'A';
                });

                if(!clickedListItem) {
                    return;
                }

                var clickedGallery = clickedListItem.parentNode;

                var childNodes = clickedListItem.parentNode.childNodes,
                        numChildNodes = childNodes.length,
                        nodeIndex = 0,
                        index;

                for (var i = 0; i < numChildNodes; i++) {
                    if(childNodes[i].nodeType !== 1) {
                        continue;
                    }

                    if(childNodes[i] === clickedListItem) {
                        index = nodeIndex;
                        break;
                    }

                    nodeIndex++;
                }

                if(index >= 0) {
                    openPhotoSwipe( index, clickedGallery );

                }
                return false;
            };

            var photoswipeParseHash = function() {
                var hash = window.location.hash.substring(1),
                        params = {};

                if(hash.length < 5) { // pid=1
                    return params;
                }

                var vars = hash.split('&');
                for (var i = 0; i < vars.length; i++) {
                    if(!vars[i]) {
                        continue;
                    }
                    var pair = vars[i].split('=');
                    if(pair.length < 2) {
                        continue;
                    }
                    params[pair[0]] = pair[1];
                }

                if(params.gid) {
                    params.gid = parseInt(params.gid, 10);
                }

                return params;
            };

            var openPhotoSwipe = function(index, galleryElement, disableAnimation, fromURL) {
                var pswpElement = document.querySelectorAll('.pswp')[0],
                        gallery,
                        options,
                        items;

                items = parseThumbnailElements(galleryElement);

                // define options (if needed)
                options = {

                    galleryUID: galleryElement.getAttribute('data-pswp-uid'),

                    getThumbBoundsFn: function(index) {
                        // See Options->getThumbBoundsFn section of docs for more info
                        var thumbnail = items[index].el.children[0],
                                pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
                                rect = thumbnail.getBoundingClientRect();

                        return {x:rect.left, y:rect.top + pageYScroll, w:rect.width};
                    },

                    addCaptionHTMLFn: function(item, captionEl, isFake) {
                        if(!item.title) {
                            captionEl.children[0].innerText = '';
                            return false;
                        }
                        captionEl.children[0].innerHTML = item.title +  '<br/><small>Photo: ' + item.author + '</small>';
                        return true;
                    }

                };

                // options for control bar
                options.shareEl = false;
                options.fullscreenEl = false;

                if(fromURL) {
                    if(options.galleryPIDs) {
                        // parse real index when custom PIDs are used
                        // http://photoswipe.com/documentation/faq.html#custom-pid-in-url
                        for(var j = 0; j < items.length; j++) {
                            if(items[j].pid == index) {
                                options.index = j;
                                break;
                            }
                        }
                    } else {
                        options.index = parseInt(index, 10) - 1;
                    }
                } else {
                    options.index = parseInt(index, 10);
                }

                // exit if index not found
                if( isNaN(options.index) ) {
                    return;
                }



                // Pass data to PhotoSwipe and initialize it
                gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);

                // see: http://photoswipe.com/documentation/responsive-images.html
                var realViewportWidth,
                        useLargeImages = false,
                        firstResize = true,
                        imageSrcWillChange;

                gallery.listen('beforeResize', function() {

                    var dpiRatio = window.devicePixelRatio ? window.devicePixelRatio : 1;
                    dpiRatio = Math.min(dpiRatio, 2.5);
                    realViewportWidth = gallery.viewportSize.x * dpiRatio;


                    if(realViewportWidth >= 1200 || (!gallery.likelyTouchDevice && realViewportWidth > 800) || screen.width > 1200 ) {
                        if(!useLargeImages) {
                            useLargeImages = true;
                            imageSrcWillChange = true;
                        }

                    } else {
                        if(useLargeImages) {
                            useLargeImages = false;
                            imageSrcWillChange = true;
                        }
                    }

                    if(imageSrcWillChange && !firstResize) {
                        gallery.invalidateCurrItems();
                    }

                    if(firstResize) {
                        firstResize = false;
                    }
$('.pswp__img').css({width:'100%',height:'auto'});
                    imageSrcWillChange = false;


                });

                gallery.listen('gettingData', function(index, item) {
                    console.log(1111);
                    // if( useLargeImages ) {
                    //     item.src = item.o.src;
                    //     item.w = item.o.w;
                    //     item.h = item.o.h;
                    // } else {
                    //     item.src = item.m.src;
                    //     item.w = item.m.w;
                    //     item.h = item.m.h;
                    // }
                });

                gallery.init();
            };
                setTimeout(function(){

                },1000)
            // select all gallery elements
            var galleryElements = document.querySelectorAll( gallerySelector );
            for(var i = 0, l = galleryElements.length; i < l; i++) {
                galleryElements[i].setAttribute('data-pswp-uid', i+1);
                galleryElements[i].onclick = onThumbnailsClick;
            }

            // Parse URL and open gallery if it contains #&pid=3&gid=1
            // var hashData = photoswipeParseHash();
            // if(hashData.pid && hashData.gid) {
            //     openPhotoSwipe( hashData.pid,  galleryElements[ hashData.gid - 1 ], true, true );
            // }
        };

        initPhotoSwipeFromDOM('.demo-gallery');

    })();

</script>

</body>

</html>
