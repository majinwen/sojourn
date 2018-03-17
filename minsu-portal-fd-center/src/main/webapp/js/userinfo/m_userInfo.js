/**
 * Created by mqzZoom on 16/7/21.
 */
$(function(){

    //男女交互
    (function(){
        var oSpan=$('.J_radio');
        /*oSpan.click(function(){
            oSpan.removeClass('on');
            oSpan.find('input[type=radio]').attr('checked',false);
            $(this).addClass('on');
            $(this).find('input[type=radio]').attr('checked',true);
            alert($(this).find('input[type=radio]:checked').val());
        });*/
        
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