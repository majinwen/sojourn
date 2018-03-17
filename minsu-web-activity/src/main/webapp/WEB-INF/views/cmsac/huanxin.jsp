<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
    <title>焕新之旅【自如民宿】</title>
    <link rel="stylesheet" type="text/css" href="https://bnbstatics.ziroom.com/act/css/zhuanti_news.css?v=js.version.20160913003">
    <link rel="stylesheet" type="text/css" href="https://bnbstatics.ziroom.com/act/css/pageStyles.css?v=js.version.20160913003">
    <script type="text/javascript" src="https://static8.ziroom.com/fecommon/library/layer/laytpl-v1.1/laytpl.js"></script>
    <script src="../js/md5.js"></script>
    <style type="text/css">
      .jcList .txt h2{line-height: .6rem; font-size: .4rem;}
      .jcList .gray{line-height: .4rem;}
      #main{padding:0; height: auto;}
      .jcList li .img .t .price{font-size: .6rem;}
      .banners p.tc,
      .boxs p.tc{text-align: center;}
.viewMore{border:solid 1px #ddd; width: 3.5rem; height:1.2rem; text-align: center; font-size: .4rem; line-height: 1.2rem; display: block; margin: .64rem auto .64rem auto; color: #444; border-radius:5px; display: none; background: #fff;}
    </style>
   
  </head>
 <body>

<div id="main" class="jcBox">${bodyHtml }</div>

<script id="houseListTpl" type="text/html">
<ul class="jcList">
{{# for(var i = 0, len = d.data.list.length; i < len; i++){ }}
 <li class="toDetail">
        <a href="javascript:void(0);" data-fid="{{d.data.list[i].fid}}" data-rentway="{{d.data.list[i].rentWay}}">   
          <div class="img">
            <img src="{{d.data.list[i].picUrl}} " class="setImg">
            <span class="t"><b>￥</b><i class="price" data-val="{{d.data.list[i].price}}"></i>元/天</span>
          </div>
          <div class="txt"> 
            <div class="ts">
              <img src="{{d.data.list[i].landlordUrl}}">
            </div>
            <h2 data-val="{{d.data.list[i].houseName}}"></h2>
            <p class="gray">{{d.data.list[i].rentWayName}} ｜
             {{# if(d.data.list[i].personCount==0){ }}
              不限人数 

            {{#}else{}}
   可住{{d.data.list[i].personCount}}人  

{{# } }}
            
             </p>
            <p class="star">
              <b class="myCalendarStar" data-val="{{d.data.list[i].evaluateScore}}">
                <i></i><i></i><i></i><i></i><i></i> 
              </b>
              <span>{{d.data.list[i].evaluateScore}}分</span>
              
            </p>
  
          </div>
        </a>
        </li>
{{# } }}
</ul>

</script>
  <a href="javascript:;" id="houseT" class="viewMore">查看房源详情</a>
<input id="news_title" type="hidden"/>
<input id="new_the" type="hidden"/>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

    <script src="https://bnbstatics.ziroom.com/act/js/jquery-2.1.1.min.js?v=js.version.20160913002" type="text/javascript"></script>
    <script src="https://bnbstatics.ziroom.com/act/js/common.js?v=js.version.20160913003"></script>
   <!--  <script src="http://catcher.ziroom.com/assets/js/boomerang.min.js"></script> -->
  <script>
 
//微信分享开始
  setShare();
  
  var gettpl = document.getElementById('houseListTpl').innerHTML;
  var shareFlag = 0;
  var curUrl=window.location.href;
  if(curUrl.indexOf('shareFlag=2')>0){
    shareFlag=2
  }
  
 setHosueList();
 toMore();
$('.title').each(function(){
 $(this).html('<span>'+$(this).html()+'</span>');   
});
setIframeHeight();

 function setShare(){
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
                 title:'${newsTitle}', // 分享标题
                 desc:'${newThe}', // 分享描述
                 link:window.location.href, // 分享链接
                 imgUrl:  $('#shareImg').val(), // 分享图标
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
                 title: '${newsTitle}', // 分享标题
                 link:window.location.href, // 分享链接
                 imgUrl:  $('#shareImg').val(), // 分享图标
                 success: function () { 
                     // 用户确认分享后执行的回调函数
                 },
                 cancel: function () { 
                     // 用户取消分享后执行的回调函数
                 }
             });
             
             wx.onMenuShareQQ({
                 title: '${newsTitle}', // 分享标题
                 desc: '${newThe}', // 分享描述
                 link:window.location.href, // 分享链接
                 imgUrl:  $('#shareImg').val(), // 分享图标
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
   


     function toMore(){
      if($('#fid').size()>0){
        var fid = $('#fid').val() ? $('#fid').val() : 0;
        var rentWay = rentWay = $('#rentWay').val() ? $('#rentWay').val() : 0;
        $("#houseT").css({display:'block'});
        $("#houseT").click(function(){
          minsutuijianJump(fid,rentWay,shareFlag,'https://bnbm.ziroom.com/');
        }); 
      }
     }
     function GetRequest() {
        var url = location.search; //获取url中"?"符后的字串
        if (url.indexOf("?") != -1) {    //判断是否有参数
           var str = url.substr(1); //从第一个字符开始 因为第0个是?号 获取所有除问号的所有符串
           strs = str.split("=");   //用等号进行分隔 （因为知道只有一个参数 所以直接用等号进分隔 如果有多个参数 要用&号分隔 再用等号进行分隔）
           return strs[1];
        }
     }

     function SetStar(){
         $('.star').each(function(){
             var _val = $(this).find('b').attr('data-val');           
             if(_val.length==1){
                 _val = $(this).find('b').attr('data-val')+'.0';
             }
             var tmp = _val.split('.');
             var aI = $(this).find('i');
             for(var i=0; i<tmp[0]; i++){
                 aI.eq(i).addClass('active');
             }
             if(tmp[2]!=0){
                 aI.eq(tmp[0]).addClass('half');
             }
         });

         $('.price').each(function(){
             var _val = $(this).attr('data-val');
            
             $(this).html(_val/100);
         });
         $('.txt h2').each(function(){
             var _val = $(this).attr('data-val');           
             if(_val.indexOf('【')>=0){
                  _val = _val.replace('【','[');

             }
             if(_val.indexOf('】')>=0){
                  _val = _val.replace('】',']');
             }
             $(this).html(_val);
         });


     }
        
     function setHosueList(){
       var houseData = '';
       var index=0;
       $('.houseData').each(function(){
           var _this = $(this);          

           houseData = encodeURIComponent($(this).val().split('|'));
           $.ajax({
             type: "POST",
             url: 'https://bnbsearch.ziroom.com/searchInit/jsonp/list?par={"houseList":['+houseData+'],"picSize":"_Z_720_480"}',            
             dataType:"jsonp",
             jsonp:"callback",
             success: function(data){
             
                  laytpl(gettpl).render(data, function(html){
                     document.getElementById('view_'+_this.attr('data-index')).innerHTML = html;                     
                     SetStar();
                     setIframeHeight();
                  });
             },
             error:function(){
                  console.log('没成功');
             }
                      
           });    
       });
          
         
     }

$("body").delegate(".toDetail","click",function(){
  minsutuijianJump($(this).find('a').attr('data-fid'),$(this).find('a').attr('data-rentway'),shareFlag,'https://bnbm.ziroom.com/');
});


function GetQueryString(name){
  var reg = new RegExp("(^|&)"+name +"=([^&]*)(&|$)");
  var r=window.location.search.substr(1).match(reg);
  if(r!=null)return unescape(r[2]);return null;
}

function setIframeHeight(){
  var agent = navigator.userAgent;
  var h=500;
  var dpr=$('html').attr('data-dpr');
  h=215*dpr;
  $('iframe').each(function(){
    $(this).css('height',h+'px');
  });
}
function toUrl(url){
    window.location.href=url;
}


  </script>
    
  </body>
</html>