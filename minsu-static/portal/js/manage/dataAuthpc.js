(function ($) {
	var dataAuth = {};
	
	/**
	 * 刷新验证码
	 */
	dataAuth.reloadImg=function (){
		var imgUrl = $("#imgUrlId").attr("imgUrl");
		var imgId = CommonUtils.uuid();
		imgId =  imgId.substring(0,32);
		imgUrl = imgUrl+imgId;
		$("#imgUrlId").attr("src",imgUrl);
		$("#imgUrlId").attr("imgId",imgId);
	}
	
	/**
	 * 绑定刷新验证码事件 
	 */
	$("#imgUrlId").click(function(){
		dataAuth.reloadImg();
	});
	$("#imgAId").click(function(){
		dataAuth.reloadImg();
	});
	
	/**
	 * 获取验证码
	 */
	dataAuth.sendSmsCode=function (){
		var mobile=$("#J_phone_ipt").val();
		var imgId=$("#imgUrlId").attr("imgId");
		var imgVal=$("#imgValue").val();
		var paramData={"mobile":mobile,"imgId":imgId,"imgVal":imgVal};
		CommonUtils.ajaxPostSubmit("/customer/sendAuthCode",paramData,function(data){
			if(data.code!=0){
				showConfirm(data.msg,"确定");
			} else {
				yzmAjax(); 
			}
		});
	}
	
	/**
	 * 保存联系方式
	 */
	dataAuth.submitMobile=function (paramData){
		CommonUtils.ajaxPostSubmit("/customer/saveMobile",paramData,function(data){
			if (data.code!=0) {
				showConfirm(data.msg,"确定");
			} else {
				var oDiv2=$('#J_part_main2');
				oDiv2.css('display','block');
		        var h1=oDiv2.offset().top-100;
		        $('html,body').animate({scrollTop:h1+'px'});
		        $('#J_mainBox').css('height','auto');
		        //重置图片验证码
		        dataAuth.reloadImg();
		        $("#smsCode").val("");
		        $("#imgValue").val("");
		        $('#J_getYzm').val('重新获取验证码').removeClass('disable');
			}
		});
	}
	
	/**
	 * 绑定保存联系人方式事件
	 */
	$("#J_step1_submit").click(function(){
		var mobile=$("#J_phone_ipt").val();
		var smsCode=$("#smsCode").val();
		var oTishi=$("#J_yzmTishi");
		var imgId=$("#imgUrlId").attr("imgId");
		var imgVal=$("#imgValue").val();		
		
		if(!validatemobile($.trim(mobile))){
			oTishi.html("请输入正确的手机号码");
			oTishi.css('display','block');
			return;
		} else {
			oTishi.css('display','none');
		}
		
		if($.trim(imgVal).length == 0){
			oTishi.html("请输入图片验证码");
			oTishi.css('display','block');
			return;
		} else {
			oTishi.css('display','none');
		}	
		
		if($.trim(smsCode).length == 0){
			oTishi.html("请输入手机验证码");
			oTishi.css('display','block');
			return;
		} else {
			oTishi.css('display','none');
		}
		var paramData={"mobile":mobile,"smsCode":smsCode,"imgId":imgId,"imgVal":imgVal};
		dataAuth.submitMobile(paramData);
	});
	
	/**
	 * 绑定获取验证码事件
	 */
	var clickFlag2=true;
	$("#J_getYzm").click(function (){
		if(!clickFlag2){
			return;
		}
		var mobile=$("#J_phone_ipt").val();
		var imgVal=$("#imgValue").val();
		var oTishi=$("#J_yzmTishi");
		if(!validatemobile($.trim(mobile))){
			oTishi.html("请输入正确的手机号码");
			oTishi.css('display','block');
			return;
		} else {
			oTishi.css('display','none');
		}
		if($.trim(imgVal).length == 0){
			oTishi.html("请输入图形验证码");
			oTishi.css('display','block');
			return;
		} else {
			oTishi.css('display','none');
		}

		dataAuth.sendSmsCode();
	});
	
	 /**
	  * 设置按钮读秒
	  */
    function yzmAjax(){
    	
    	clickFlag2=false;
        //发送ajax请求 同时冻结按钮
        //$.ajax()....;
    	var oBtn=$('#J_getYzm');
    	
        oBtn.addClass('disable');
        oBtn.val('60');
        var total=60;

        var timer=setInterval(function(){
            total--;
            oBtn.val(total);

            if(total<0){
                clearInterval(timer);
                clickFlag2=true;
                oBtn.removeClass('disable');
                oBtn.val('重新获取验证码');
            }
        },1000);
    }
}(jQuery));