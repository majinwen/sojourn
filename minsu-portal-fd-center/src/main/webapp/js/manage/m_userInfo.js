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
    (function(){
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
    })();

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

    //教育背景交互
    (function(){
        var oUl=$('#J_jiaoyu');
        var oIpt=$('#J_jiaoyu_ipt');

        oIpt.click(function(ev){
            oUl.css('display','block');
            ev.stopPropagation();
        });

        oUl.find('li').click(function(){
            oIpt.val($(this).html());
            oIpt.attr('data-xueli',$(this).attr('data-xueli'));
        });
        
        $('#J_all_box').click(function(){
        	oUl.hide();
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

});

function checkEmail(str){
    return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(str);
}