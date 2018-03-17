/**
 * Created by mqzZoom on 16/7/22.
 */
$(function(){

    //证件类型交互
    (function(){
        var oUl=$('#J_zhengjian');
        var oIpt=$('#J_zhengjian_ipt');
        var idType=$('#idType');

        oIpt.click(function(ev){
            oUl.css('display','block');
            ev.stopPropagation();
        });

        oUl.find('li').click(function(){
            oIpt.val($(this).html());
            oIpt.attr('data-type',$(this).attr('data-type'));
            idType.val($(this).attr('data-type'));
            if($(this).attr('data-type')==12){
   			   $("#identyFront").html("请上传法人身份证正面");
   		       $("#identyBack").html("请上传法人身份证反面");
   		       $("#identyOther").html("请上传营业执照");
   		       $("#J_zhaopian_tishi").html("请上传法人身份证正反面及营业执照");
 	  		}else{
 	  			  $("#identyFront").html("证件正面照片");
 	  		      $("#identyBack").html("证件反面照片");
 	  		      $("#identyOther").html("本人手持证件照片");
 	  		      $("#J_zhaopian_tishi").html("请上传证件照片");
 	  		 }
        });
        
        $('#J_all_box').click(function(){
        	oUl.hide();
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
    
    (function(){
    	var initIdType = $("#idType").val();
    	if(initIdType == 12){
    		$("#identyOther").html("营业执照照片");
    	}
    })();

});