/**
 * Created by mqzZoom on 16/7/21.
 */
$(function(){

    //男女交互
    (function(){
        var oSpan=$('.J_radio');
        oSpan.each(function(){
        	$(this).click(function(){
        		oSpan.removeClass('on');
	            $(this).addClass('on');
	            $(this).find('input[type=radio]').attr('checked',true);
        	});
        });
    })();

    //校验邮箱交互
    /*(function(){
        var oIpt=$('#J_email');
        var oTishi=$('#J_email_tishi');
        oIpt.blur(function(){
            if(checkEmail($(this).val())){
                oTishi.css('display','none');
            }
            else{
                oTishi.css('display','inline');
            }
        });
    })();*/

    //个人介绍交互
    (function(){
        var oIpt=$('#J_user_jieshao');
        var aTishi=$('.J_jieshao_tishi');

        oIpt.focus(function(){
            aTishi.css('display','block');
        });
        oIpt.blur(function(){
            aTishi.css('display','none');
        });
    })();

    //教育背景交互
    (function(){
        var oUl=$('#J_jiaoyu');
        var oIpt=$('#J_jiaoyu_ipt');

        oIpt.click(function(){
            oUl.css('display','block');
        });

        oUl.find('li').click(function(){
            oIpt.val($(this).html());
            oIpt.attr('data-xueli',$(this).attr('data-xueli'));
            oUl.css('display','none');
        });
    })();

    var maxDate=new Date();
    
    //日历选择插件
    $( "#J_birth_input" ).datepicker({
    	changeMonth: true,
        changeYear: true,
        maxDate:$("#currentDate").val(),
        yearRange:"1910:2050"
    });
    
    //统计字数
    (function(){
    	
    	var oIpt=$('#J_user_jieshao');
    	
    	//初始化字数
    	oIpt.siblings('.J_tongji').find('span').eq(0).html(oIpt.val().length);
    	
    	 //统计字数
        oIpt.bind('input propertychange',function(){

            $(this).siblings('.J_tongji').find('span').eq(0).html($(this).val().length);

            if($(this).val().length>500||$(this).val().length<20){
                $(this).siblings('.J_tongji').find('span').eq(0).addClass('on');
            }
            else{
                $(this).siblings('.J_tongji').find('span').eq(0).removeClass('on');
            }

        });
    })();
    
    

});

function checkEmail(str){
    return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(str);
}

Date.prototype.format = function(format){
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}

	if(/(y+)/.test(format)) {
	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}

	for(var k in o) {
	if(new RegExp("("+ k +")").test(format)) {
	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
	}
	}
	return format;
	} 
