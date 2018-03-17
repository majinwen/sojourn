/**
 * Created by mqzZoom on 16/7/22.
 */
$(function(){

    //证件类型交互
    (function(){
        var oUl=$('#J_zhengjian');
        var oIpt=$('#J_zhengjian_ipt');
        var idType=$('#idType');

        oIpt.click(function(){
            oUl.css('display','block');
        });

        oUl.find('li').click(function(){
            oIpt.val($(this).html());
            oIpt.attr('data-type',$(this).attr('data-type'));
            idType.val($(this).attr('data-type'));
            oUl.css('display','none');
        });
    })();

    //判断姓名不为空
    (function(){
        var oIpt=$('#J_name_ipt');
        var oTishi=$('#J_name_tishi');

        oIpt.blur(function(){
            if(/^\s*$/.test($(this).val())){
                oTishi.css('display','inline');
            }
            else{
                oTishi.css('display','none');
            }
        });

    })();

});