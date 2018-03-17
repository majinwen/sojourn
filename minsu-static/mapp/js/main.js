var countdown=60;
var time60;
var shareFlag = 0;
var times = 0;

function showImg(){
    var iWinHeight = $(window).height();
    var iTop = $(window).scrollTop() + iWinHeight;
    $('.loadImg').each(function(){
        var _top = $(this).offset().top;
        if(_top < iTop){
            var dataAni ='';
            var dataDelay ='';
            if($(this).attr('data-ani')){
                dataAni =$(this).attr('data-ani');
            }
            if($(this).attr('data-delay')){
                dataDelay=$(this).attr('data-delay');
            }
            $(this).addClass(dataAni+' animated loadImgShow '+dataDelay);
            $(this).css('animation-delay',dataDelay/10+'s');
            $(this).css('-o-animation-delay',dataDelay/10+'s');
            $(this).css('-moz-animation-delay',dataDelay/10+'s');
            $(this).css('-ms-animation-delay',dataDelay/10+'s');
            $(this).css('-webkit-animation-delay',dataDelay/10+'s');
        }
    });
}

//获取验证码
function getCode(){
    $('.yanzhengBtn').css({
       background: '#d9d9d9',
       color: '#fff'
    });
    //获取用户输入内容
    var mobile = $('#mobile').val();
    var phoneReg=/^1[34578]\d{9}$/;
    var tel=$.trim(mobile);
    if( !phoneReg.test(tel) ){
        $('#mobile').val('');
        $('.yanzhengBtn').css('background', 'url(../img/code.jpg) no-repeat');
        showTip('请输入正确的手机号码');
        
        return;
    }else if(mobile==''){
        $('.yanzhengBtn').css('background', 'url(../img/code.jpg) no-repeat');
        showTip('请输入手机号码');
        return;
    }else{
        $.ajax({
            type:"get",
            url:"https://minsuactivity.ziroom.com/mobile/getMobileCode",
            //url:"http://minsu.activity.d.ziroom.com/mobile/getMobileCode",
            //url:"https://tminsuactivity.ziroom.com/mobile/getMobileCode",
            data:{'mobile':mobile,'mobileCode':'QMQM01'},
            dataType:"jsonp",
            jsonp: 'callback',
            success:function(data){
                if(data.code==0){
                    //倒计时
                    setTime();
                }else{
                    //error
                } 
            },
            error:function(){
               showTip('获取失败');
            }
        })
    } 
}
//倒计时
function setTime(){
    if (countdown == 0) { 
        $('#yanzheng').removeAttr('disabled');
        $('.yanzhengBtn').css('background', 'url(../img/code.jpg) no-repeat');    
        $('#yanzheng').val("获取验证码"); 
        countdown = 60; 
        return;
    } else { 
        $('#yanzheng').attr('disabled', 'disabled');
        $('#yanzheng').val("重新发送(" + countdown + ")"); 
        countdown--; 
    } 
    time60 = setTimeout(function() { 
        setTime() }
    ,1000) 
}
//领取优惠券
var bFlag=true;
function getHuaYu(){
    if(bFlag){
        bFlag=false;
        var mobile = $('#mobile').val();
        var code = $('#yzm').val();
        if(mobile==''){
            showTip('请输入手机号');
            bFlag=true;
        }else if(code==''){
            showTip('请输入验证码');
            bFlag=true;
        }else{
            $.ajax({
                type:"get",
                url:"https://minsuactivity.ziroom.com/coupon/pullActCoupon",
                //url:"http://minsu.activity.d.ziroom.com/coupon/pullActCoupon",
                //url:"https://tminsuactivity.ziroom.com/coupon/pullActCoupon",
                data:{'mobile':mobile,'code':code,'actSn':'QMQM01'},
                dataType:"jsonp",
                jsonp: 'callback',
                success:function(data){
                    $('#te').val(mobile);
                    if(data.code != 0){
                        //error
                        if(data.code=='111000'){
                            showTip(data.msg);//错误提示
                            $('.yanzhengBtn').css('background', 'url(../img/code.jpg) no-repeat'); 
                            bFlag=true;
                        }else if(data.code=='111004'){
                            showTip('活动已经结束咯！');
                            setTimeout(function(){
                                closeLayer();//关闭弹窗
                            },3000);
                        }else if(data.msg=='验证码异常'){
                            showTip('呀, 验证码输入不正确, 换个手指再来一次');
                            $('#yzm').val('');
                            $('.yanzhengBtn').css('background', 'url(../img/code.jpg) no-repeat'); 
                            bFlag=true;
                        }else if(data.code=='111005'){
                            showTip('哈！你已经领取过咯，别贪心哦！');
                            bFlag=true;
                            setTimeout(function(){
                                closeLayer();//关闭弹窗
                                $('.pop').hide();
                            },3000);
                        }else{
                            showTip(data.msg);
                            $('.yanzhengBtn').css('background', 'url(../img/code.jpg) no-repeat'); 
                            bFlag=true;
                        }
  
                    }else{
                        //success
                        closeLayer();//关闭弹窗
                        showHy();
                    } 
                },
                error:function(){
                    showTip('请稍后再试');
                }
            }) 
        }
    }
}
var str='<div class="con">'
            +'<input class="phoneBox" type="tel" placeholder="请输入手机号码" maxlength="11" id="mobile">'
            +'<div class="codeBox">'
                +'<input type="tel" class="code" id="yzm" placeholder="请输入验证码">'
                +'<input type="text" class="yanzhengBtn" id="yanzheng" value="获取验证码" unselectable="on" onclick="getCode();" readonly />'
            +'</div>'
            +'<div class="lqbtn" id="btn2" onclick="getHuaYu()">点击领取</div>'
        +'</div>'

//显示弹窗
function showLayer(str){
    closeLayer();
    layer.open({
        content: str,
        shadeClose: false,
    });
}
//关闭弹窗
function closeLayer(){
    layer.closeAllNew();
}
//显示提示
function showTip(str){
    // layer.open({
    //     content: '<div class="layerBox tipBox"><div class="con2">'+str+'</div></div>',
    //     shadeClose: false,
    // });
    // setTimeout(function(){
    //     $('.tipBox').parents('.layui-m-layer').remove();
    // },2000)
    $('#tip .str p').html(str);
    $('#tip').show();
    $('.closeBtn').click(function(){
        $('#tip').hide();
    })
}




//去预订
function gotoLlq(){
    closeLayer();
    var curUrl=window.location.href;
    if(curUrl.indexOf('shareFlag=2')>0){
        //app外
        window.location.href="http://t.cn/RqOAHxW";//下载app
    }
}
//点分享按钮
function goclickShare(){
    var curUrl=window.location.href;
    if(curUrl.indexOf('shareFlag=2')>0){
        //appwai
        closeLayer();
        $('.shareMask').show();
        $('.shareMask').click(function(){
            $('.shareMask').hide();
        })
    }else{
        //app里的分享
        toShare();
    }
}

//获取url地址中的参数
function request(strParame){   //传的是参数的key 返回的是参数的value 
    var args = new Object( ); 
    var query = location.search.substring(1);  //设置或取得当前URL的查询字串 ?id=2&name=123
    var pairs = query.split("&"); //将key=value
    for(var i = 0; i < pairs.length; i++) { 
    var pos = pairs[i].indexOf('='); 
    if (pos == -1) continue; 
    var argname = pairs[i].substring(0,pos); 
    var value = pairs[i].substring(pos+1); 
    value = decodeURIComponent(value); 
    args[argname] = value; 
    } 
    return args[strParame]; 
}


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
// 是否微信
function isWeiXin() {
    return browser.versions.weixin;
}

//是QQ
function isQQ() {
    return browser.versions.qq;
}

function openMask(){
    $('.weixinMaskTip').show();
}
function closeMask(){
    $('.weixinMaskTip').hide();
}
 
var ua = navigator.userAgent.toLowerCase();

function isIos() {
	
    if (ua.match(/iPhone\sOS/i) == "iphone os") {
        return true;
    } else {
        return false;
    }
}

function isAndroid() {
    if (ua.match(/Android/i) == "android") {
        return true;
    } else {
        return false;
    }
}
//是微博
function isWeibo(){
    return browser.versions.weibo;
}


//2.加密、解密算法封装：
function Base64() {    
    // private property 
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";  
    // public method for encoding  
    this.encode = function (input) { 
        var output = "";  
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4; 
        var i = 0; 
        input = _utf8_encode(input);
        while (i < input.length) { 
            chr1 = input.charCodeAt(i++);  
            chr2 = input.charCodeAt(i++);   
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;    
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);    
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);    
            enc4 = chr3 & 63;    
            if (isNaN(chr2)) {     
                enc3 = enc4 = 64;    
            } else if (isNaN(chr3)) {     
                enc4 = 64;    
            }    
            output = output +    _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +    _keyStr.charAt(enc3) + _keyStr.charAt(enc4);   
        }   
        return output;  
    }    
    // public method for decoding  
    _utf8_encode = function (string) {   
            string = string.replace(/\r\n/g,"\n");   
            var utftext = "";   
            for (var n = 0; n < string.length; n++) {    
                var c = string.charCodeAt(n);    
                if (c < 128) {     
                    utftext += String.fromCharCode(c);    
                } else if((c > 127) && (c < 2048)) {     
                utftext += String.fromCharCode((c >> 6) | 192);     
                utftext += String.fromCharCode((c & 63) | 128);    
            } else {     
                utftext += String.fromCharCode((c >> 12) | 224);     
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);     
                utftext += String.fromCharCode((c & 63) | 128);    
            }     
    }   
    return utftext;  
    }    
}  



