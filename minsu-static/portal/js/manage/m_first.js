/**
 * Created by mqzZoom on 16/7/26.
 */
$(function(){

    //获取验证码
    /*(function(){

        var oIpt=$('#J_phone_ipt');
        var oTishi=$('#J_yzmTishi');
        var oSubmit=$('#J_step1_submit');


        oIpt.blur(function(){
            if(checkPhone($(this).val())){
                oTishi.css('display','none');
            }
            else{
                oTishi.html('请输入正确的手机号码');
                oTishi.css('display','block');
            }
        });  

        function checkPhone(str){
            return /^1[3|4|5|7|8]\d{9}$/.test(str);
        }

    })();*/
	
	
	
	//证件类型交互
    (function(){
        var oUl=$('#J_zhengjian');
        var oIpt=$('#J_zhengjian_ipt');

        oIpt.click(function(e){
            oUl.css('display','block');
            e.stopPropagation();
        });

        oUl.find('li').click(function(){
            oIpt.val($(this).html()).attr("data-type",$(this).attr("data-type"));
            if($(this).attr("data-type")==12){
    			   $("#identyFront").html("请上传法人身份证正面");
    		       $("#identyBack").html("请上传法人身份证反面");
    		       $("#identyOther").html("请上传营业执照");
  	  		}else{
  	  			  $("#identyFront").html("证件正面照片");
  	  		      $("#identyBack").html("证件反面照片");
  	  		      $("#identyOther").html("本人手持证件照片");
  	  		 }
        });

        $('#J_part_main2').click(function(){
            oUl.css('display','none');
        });
    })();

    /*//判断姓名不为空
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

    })();*/

    //页面加载计算高度
    (function(){
        $('#J_mainBox').css('height',$(window).height()-384+'px');
    })();
    
    //已经有手机号，直接第二步
	(function(){
		if($('#J_phone_ipt').val()!=''){
			var oDiv2=$('#J_part_main2');
			oDiv2.css('display','block');
	        var h1=oDiv2.offset().top-100;
	        $('html,body').animate({scrollTop:h1+'px'});
	        $('#J_mainBox').css('height','auto');
		}
	})();
	
	
	//直接到第三步
	(function(){
		if($('#authStep').val()==2  ){
			var oDiv3=$('#J_part_main3');
			if(oDiv3.length>0){
				oDiv3.css('display','block');
	            var h2=oDiv3.offset().top-100;
	            $('html,body').animate({scrollTop:h2+'px'});
			}
		}
	})();
    
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
    
    //显示隐藏案例
    (function(){
    	
    	var oIpt=$('#J_user_jieshao');
    	var aTishi=$('.J_jieshao_tishi');
    	
    	oIpt.focus(function(){
    		aTishi.show();
    	});
    	
    	oIpt.blur(function(){
    		aTishi.hide();
    	});
    	
    })();
});