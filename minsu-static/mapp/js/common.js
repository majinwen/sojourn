$(function(){

    var oBody=$('body');
    if(oBody.find('.box_bottom').length != 0){
        oBody.css({'overflow':'hidden'})
    }
    

/*
    var winHeight = $(window).height();
    var bodysHeight = winHeight;

    if($('.bottomNav').size()!=0){
        bodysHeight = winHeight - $('.bottomNav').height();
    }

    if($('.box_bottom').size()!=0){
         bodysHeight = winHeight - $('.box_bottom').height();
    }
    if($('.stateTips').size()!=0){
         bodysHeight = winHeight - $('.stateTips').height();
    }
    $('.bodys').css({'height':bodysHeight+'px'});*/
   /* $('.myCenterList').each(function(){
        var aLi = $(this).find('li');

        aLi.each(function(){
            if($(this).find('.icon_r').size()==0){
                $(this).css({'margin-right':'0'})
            }
        })
    })*/

    

    setCalendarTd();

    $(".tabs").tab({tabNavObj:".tabTit",tabContentObj:".tabCon",tabContent:".con",eventType:"click"}); 


    $(window).on("orientationchange",function(){ 

        if(window.orientation == 0)// Portrait
        { 

         setCalendarTd();
         setImgWidth();
         setImgWidth2();
           // window.location.href =window.location.href;

        }else// Landscape

        { 
            setCalendarTd();
            setImgWidth();
            setImgWidth2();
            //Landscape相关操作
             // window.location.href =window.location.href;

         }

     });


    $('.starBig').each(function(){
        var aI=$(this).find('i');
        var _index=0;
        var oInp=$(this).find('input');

        aI.each(function(){
            $(this).click(function(){
             _index = $(this).index();
             oInp.val(_index+1);
             aI.each(function(){
               var i = $(this).index();
               if(i<_index+1){
                   $(this).addClass('s');
               }else{
                   $(this).removeClass('s');
               }
           });
         });
        });
    });



    $(".checkFormChild").linghtBtn(); 

    

    setImgWidth();
    setImgWidth2();

    imgCenter();

    $('#otherMoneyDes,#otherMoneyDes,#otherMoneyValue,#evaContent').focus(function(){
        $('.stateEvents ').hide();
    }).blur(function(){
        $('.stateEvents ').show();
    });

    $('#evaContent').focus(function(){
        //$('.pingjiaTop').hide();
    }).blur(function(){
       // $('.pingjiaTop').show();
    })


    //设置星级
    setStar();

});


function imgCenter(){
    $('.ydrDpList,.dingdanList,.ydrTop,.msgList').each(function(){
        $(this).find('.img').each(function(){
            var w=0;
            var h=0;
            var oImg = $(this).find('img');
            var newImg = new Image();

            newImg.onload = function(){
                w = newImg.width;
                h = newImg.height;
                
                if(w>h){
                    oImg.css({'width':'auto','height':'100%'});                    
                   }else if(h>w){
                    oImg.css({'height':'auto','width':'100%'});                   
                   }else{
                    oImg.css({'height':'auto','width':'100%'});
                }
            }
           
            newImg.src = oImg.attr('src');
        });
    });
   
    
    $('.fk_info').each(function(){
        $(this).find('.fk_info_img').each(function(){
            var w=0;
            var h=0;
            var oImg = $(this).find('img');
            var newImg = new Image();

            newImg.onload = function(){
                w = newImg.width;
                h = newImg.height;
                
                if(w>h){
                    oImg.css({'width':'auto','height':'100%'});                    
                   }else if(h>w){
                    oImg.css({'height':'auto','width':'100%'});                   
                   }else{
                    oImg.css({'height':'auto','width':'100%'});
                }
            }
           
            newImg.src = oImg.attr('src');
        });
       
    });
    
}

function setImgCenter(obj){
    var w=0;
    var h=0;

    //$(obj).load(function(){
        w = $(obj).width();
        h = $(obj).height();
        if(w>h){
            $(obj).css({'width':'auto','height':'100%'});
            w = $(obj).width();
            $(obj).css({'margin-left':-w/2,'left':'50%','position':'relative'});
        }else if(h>w){
            $(obj).css({'height':'auto','width':'100%'});
            h = $(obj).outerHeight();
        }else{
            $(obj).css({'height':'auto','width':'100%'});
        }


   // });

$(obj).load(function(){
    w = $(obj).width();
    h = $(obj).height();
    if(w>h){
        $(obj).css({'width':'auto','height':'100%'});
        w = $(obj).width();
        $(obj).css({'margin-left':-w/2,'left':'50%','position':'relative'});
    }else if(h>w){
        $(obj).css({'height':'auto','width':'100%'});
        h = $(obj).outerHeight();
    }else{
        $(obj).css({'height':'auto','width':'100%'});
    }
});
}

function setValToInput(obj,id){
    var val=$(obj).val();
    var txt =  $(obj).find("option:selected").text();
    $('#'+id).val(txt);
}
function setValToInputDate(obj,id){
    var val=$(obj).val();

    $('#'+id).val(val);
}
function setCalendarTd(){
    var bigW=$('#calendarBox').outerWidth()*.96;

    $('.calendars table').each(function(){
        var w=bigW/7;

        $(this).find('td').each(function(){
            $(this).css({width:w,height:w});
            $(this).find('p').css({width:w+'px',height:w});
        });

        
    });
}
//订单详情 收起打开
function sh(obj,id){
    var obj1 = $(obj).find('span.icon');
    var obj2 = $('#'+id);

    if(obj1.hasClass('icon_t')){
        obj2.show();
        obj1.removeClass('icon_t').addClass('icon_b');
    }else{
        obj2.hide();
        obj1.removeClass('icon_b').addClass('icon_t');
    }

    
}
//订单详情 输入其他金额中的合计金额改变样式
function keyUpMon(obj){
    var inputVal=$(obj).val(); 
    inputVal = inputVal.replace(/\D/g,'');
    if(inputVal&&inputVal.indexOf('￥')==-1){
        $(obj).val('￥'+inputVal);
    }else{
        $(obj).val(inputVal);
    }
}
function inputVal(obj,id){
    if($(obj).val()!==''){
        $(id).show();
    }else{
        $(id).hide();
    }
}

function clearInp(obj,id){
  $(id).val('');
  $(obj).hide();  
}


/*手机正则验证*/
function checkMobile(tel){
    var telReg;
    var pattern = /^1[34578]\d{9}$/;
    if (pattern.test(tel)) {
        telReg = true;
    }else{
        telReg = false;
    }
    return telReg;
}
/*邮箱验证*/
function checkEmail(email){
    var emailReg;
    var pattern = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
    if (pattern.test(email)) {
        emailReg = true;
    }else{
        emailReg = false;
    }
    return emailReg;
}

/*密码6-16位*/
function checkPas(val){
    var emailReg;
    var _val=$.trim(val);
    if (_val.length>=6 && val.length<=16) {
        emailReg = true;
    }else{
        emailReg = false;
    }
    return emailReg;
}


/*是否为空*/
function checkEmpty(val) {
    if(val.length==0){
        return false;
    }else{
        return true;
    }
}

/*只能输入数字*/

    function keepFloat(obj,float,len){
//  debugger
//  验证是否有字母
var reg1 = new RegExp("[a-zA-Z]");
//验证是否有多个.
var reg = /^\\d+.\\d{2}/;
//  特殊字符 
var reg3 = /[`~!@#$%^&*()_+<>?:"{},\/;'[\]]/im;
//验证是否有汉字
var reg4 = /[^\u0000-\u00FF]/;
var v = $.trim($(obj).val());

if(reg.test(v)){
    v = v.replace(/(^\s*)|(\s*$)/g, '')
    $(obj).val(v);
}
if (reg1.test(v)) {
    v = v.replace(/[a-zA-Z]/g, '')
    $(obj).val(v);
    return false;
}
if (reg3.test(v)) {
    v = v.replace(/[`~!@#$%^&*()_+<>?:"{},\/;'[\]]/g, '')
    $(obj).val(v);
    return false;
}
if (reg4.test(v)) {
    v = v.replace(/[^\u0000-\u00FF]/g, '')
    $(obj).val(v);
    return false;
}
if(float == 0){
//      保留为整数
if (v.indexOf(".") != -1){
    v = v.split(".")[0];
    $(obj).val(v);
    return false;
}
}
if(len !== undefined){
    if (v.indexOf(".") != -1) {
        var str = v.replace(".","");
        if(str.length > len){
            $(obj).val(v.slice(0, len));
            return false;
        }
    }else{
        if(v.length > len){
            $(obj).val(v.slice(0, len));
            return false;
        }
    }
}
if (v.indexOf(".") != -1) {
    var str = v.split(".");
    $(obj).val(str[0] + "." + str[1].slice(0, float));
}
return true;
}
function goBack(){
    self.location=document.referrer;
}


/*提示弹层*/
function showTips(str){
    layer.open({
        content:str,
        shade:false,
        success:function(){
            STimeout(function(){
                layer.closeAll();
            },1000)
        }
    });
}



function showShadedowTips(str,time){
    if(time){
        layer.open({
            content: str,
            shade:false,
            style: 'background:rgba(0,0,0,.6); color:#fff; border:none; margin-top:50px;',
            time:time   
        });
    }else{
        layer.open({
            content: str,
            shade:false,
            style: 'background:rgba(0,0,0,.6); color:#fff; border:none; margin-top:50px;'

        });
    }

}

function setImgWidth(){
    var staticUrl = $('#staticUrl').val();
    var sacle=2/3;

    var height = $('.setImg:eq(0)').parents('.img').width()*sacle;

    $('.setImg').each(function(){


        
        var _this = $(this);

        var newImg = new Image();

        newImg.onload = function(){
            var imgH = _this.height();
            if(imgH<height){
                //_this.css({'width':'auto','height':'100%','margin':'0 auto'});
               // _this.css({'width':'auto','height':'100%'});
                _this.css({'margin-top':(height-imgH)/2+'px'});
            }
            
        }
        newImg.src = _this.attr('src');

        newImg.onerror = function(){
            _this.attr('src',staticUrl+'/images/base1.jpg');
        }
        //$(this).css({'height':w*sacle});
        
        $(this).parents('.img').css({'height':height,'overflow':'hidden'});

       

    });

}
function setImgWidth2(){
        var sacle=2/3;
        $('.setImg2').each(function(){
            var w = $(this).width();
            var h = w*sacle;
            var _this = $(this);

            var newImg = new Image();

            newImg.onload = function(){
                var imgH = newImg.height;

                if(imgH<h){
                    _this.css({'width':'100%','height':'auto'});
                }
            }
            $(this).parents('.imgli').css({'height':h,'overflow':'hidden'});
        });
        $('.noimgli').each(function(){
            var w = $(this).width();
            var h = w*sacle;
            $(this).css({'height':h,'overflow':'hidden'});
        });


    }



function setStar() {
        $('.myCalendarStar').each(function(){
            var iMyCalendarStar=$(this).attr('data-val').split('.');
            
            for(var i=0; i<iMyCalendarStar[0]; i++){
                $(this).find('i').eq(i).addClass('s');
            }

            if(iMyCalendarStar[1]>0){
                $(this).find('i').eq(iMyCalendarStar[0]).addClass('h');
            }
        });
}



//校验特殊字符，如果有的话替换为空字符串直接返回
function stripscript(s) {
    //var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]")
    var pattern = new RegExp("[~'!@#$%^&*()-+_=:]");
    var rs = "";
    for (var i = 0; i < s.length; i++) {
        rs = rs + s.substr(i, 1).replace(pattern, '');
    }
    return rs;
}

//校验是否是正整数
function isPInt(str) {
    var g = /^[0-9]*[1-9][0-9]*$/;
    return g.test(str);
}

//是否是整数
function isInt(str)
{
    var g=/^-?\d+$/;
    return g.test(str);
}
//身份证校验
function isCardNo(card){
// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
var reg =/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;//身份证号验证  
return reg.test(card);
}
//护照格式校验
function isPassport(value){
    var re1 = /^[a-zA-Z]{5,17}$/;
    var re2 = /^[a-zA-Z0-9]{5,17}$/;
    return (re2.test(value) || re1.test(value));
}
//港澳通行证验证
function isHKMacao(value){
    var re = /^[HMhm]{1}([0-9]{10}|[0-9]{8})$/;
    return re.test(value);
}
//台湾通行证验证
function isTaiwan(value){
    var re1 = /^[0-9]{8}$/;
    var re2 = /^[0-9]{10}$/;
    return re1.test(value) || re2.test(value);
}
// 630新增
function setImgPosition(){
    $(".img_box img").each(function(){

            var img_box_h = $(this).parent(".img_box").height();
            var img_box_w = $(this).parent(".img_box").width();
            var _this = new Image();
            var h ;
            var self = $(this);
            _this.onload = function(){
                _this.height = img_box_w/_this.width*_this.height;
                if(_this.height < img_box_h){
                    h = (img_box_h - _this.height)/2;
                    self.css({"margin-top":h});
                }
            }
            _this.src = $(this).attr('src');

        })
}

// 创建一个闭包     
(function($) {     
    //插件主要内容 判断表单元素是否全部有值，有值的话那就让提交按扭可点     
    $.fn.linghtBtn = function(options) {     
    // 处理默认参数   
    var opts = $.extend({}, $.fn.linghtBtn.defaults, options);     
    return this.each(function() {  
        var $this=$(this),
        $formInp=$('input:not([type="hidden"])',$this),  
       // $formInp=$('input:not([type="hidden"])',$this),              
       $formSlect=$('select',$this),
       $formTextarea=$('textarea',$this),
       $btn=$(opts.btn,$this),
       $close=$(opts.close,$this);

       var flag=0;
       checkAll();

       $formInp.keyup(function(){
        checkAll();
    });
       $formInp.change(function(){
        checkAll();
    });
       $formSlect.change(function(){
        checkAll();
    });
       $formTextarea.keyup(function(){
        checkAll();
    });
       $formTextarea.change(function(){
        checkAll();
    });
       $close.click(function(){
        checkAll();
    });





       function checkAll(){

        flag = 1;
        $formInp.each(function(){
            if($(this).val()==''){
                if($(this).is(':visible')|| $(this).attr('id')=='reg_pas' || $(this).attr('id')=='pw_pas' || $(this).attr('id')=='vcode'){
                    flag=0;
                }

            }

        });
        $formSlect.each(function(){
            if($(this).val()==''){
                flag=0;
            }
        });
        $formTextarea.each(function(){
            if($(this).val()==''){
                flag=0;
            }
        });


        if(flag==1){
            $btn.removeClass('disabled_btn');

        }else{
            $btn.addClass('disabled_btn');
        }


    }

});
    // 保存JQ的连贯操作结束
};
    //插件主要内容结束
    
    // 插件的defaults     
    $.fn.linghtBtn.defaults = {     
        form:'.checkFormChild',
        btn:'.org_btn',
        close:'.icon_close'
    };  
    //linghtBtn 用法例：$(".checkFormChild").linghtBtn({form:".checkFormChild",btn:".org_btn"}); 
})(jQuery);    
(function($) {     
    //插件主要内容 tab切换     
    $.fn.tab = function(options) {     
    // 处理默认参数   
    var opts = $.extend({}, $.fn.tab.defaults, options);     
    return this.each(function() {  
        var $this=$(this),
        $tabNavObj=$(opts.tabNavObj,$this),
        $tabContentObj=$(opts.tabContentObj,$this),
        $tabNavBtns=$(opts.tabNavBtn,$tabNavObj),
        $tabContentBlocks=$(opts.tabContent,$tabContentObj);
        $tabNavBtns.bind(opts.eventType,function(){
            var $that=$(this),
            _index=$tabNavBtns.index($that);
            
            if(!$that.hasClass('more')){
                $that.addClass(opts.currentClass).siblings(opts.tabNavBtn).removeClass(opts.currentClass);
                $tabContentBlocks.eq(_index).show().siblings(opts.tabContent).hide();
            }
            
        }).eq(0).trigger(opts.eventType);
    });
    // 保存JQ的连贯操作结束
};
    //插件主要内容结束
    
    // 插件的defaults     
    $.fn.tab.defaults = {     
        tabNavObj:'.tabNav',
        tabNavBtn:'li',
        tabContentObj:'.tabContent',
        tabContent:'.list',
        currentClass:'active',
        eventType:'click'
    };  
    //tab 用法例：$(".index-tabWrap").tab({tabNavObj:".tabNav",tabContentObj:".tabContent",tabContent:".con",eventType:"click"}); 
})(jQuery);
