var oPwUser =$('#pw_user');
var oRegPas =$('#pw_pas');
var oNextStep1=$('#nextStep_1');
var oNextStep2=$('#nextStep_2');
var oNextStep3=$('#nextStep_3');
//flag=1,手机
//flag=2,邮箱
var flag=0;
var imgId = $('#imgId').val();
var imgUrl = $('#imgUrl').val();
var imgSrc=imgUrl+''+imgId;

//第一步的按扭
oNextStep1.click(function(){
	flag=0;
	if(!checkMobile(oPwUser.val())&&!checkEmail(oPwUser.val())){	
		showShadedowTips('请输入正确的手机号或邮箱！',1);	   
	    return;
	}
	if(checkMobile(oPwUser.val())){
		flag=1;
	}
	if(checkEmail(oPwUser.val())){
		flag=2;
	}

	//手机号
	if(flag==1){
		//如果手机号已经注册过的提示是
		/*		
		layer.open({
		    content: '<div class="loginTips">该手机号未注册</div>',
		    btn:['知道了','直接注册'],
		    yes: function(){
	        	//直接跳转到手机注册
	    		window.location.href="reg.html";
	    	}, no: function(){
	        	
	    	}
		    
		});*/
	
		//如果手机号没有被注册
		//是先弹出图片验证码进行验证
		layer.open({
			  content: '<div class="regAuthCode"><input type="text"  onfocus="$(\'#rTips\').hide();" placeholder="请输入" id="imgVValue"><img class="" src="'+imgSrc+'" imgId="'+imgId+'"  imgUrl="'+imgUrl+'"  id="imgUrlId" onclick="Customer.reloadImg()"><p id="rTips"></p></div>',
		    btn:['确认','取消'],
		    yes: function(){
	        	//验证验证码是否正确
	        	//如果对了，就跳转到第二步
	    		
		    	//验证验证码是否正确
	        	//如果对了，就跳转到第二步
	    		
		    	var imgVValue = $.trim($("#imgVValue").val());
				var imgId = $("#imgUrlId").attr("imgId");
				
				if(imgVValue == null ||imgVValue == undefined ||imgVValue ==""||imgId == null ||imgId == undefined ||imgId ==""){
					showShadedowTips('参数错误',1);	  
					return false;
				}
				CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/checkImgCode", {"imgId":imgId,"imgVValue":imgVValue}, function(data){
					
					if(data){
						//正确跳转到第二步,显示第二块，进行发送验证码并开始倒计时
						if(data.code == 0){
				    		  var phone =  oPwUser.val();
				    		  var imgVid = data.data.imgVid;
				    		  if(!checkMobile(phone)){	
				    				showShadedowTips('手机号错误！',1);	   
				    			    return;
				    		}

				 			CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/getMobileCode", {"phone":phone,"imgId":imgId,"imgVid":imgVid,"type":3}, function(data){
				 				
				 				if(data){
				 					if(data.code == 0){
				 						    //测试
				 						    if(data.data.vcode){
				 						    	//alert("测试验证码："+data.data.vcode)
				 						    }
											$('#pwStep_1').hide();
								    		$('#pwStep_2').show();
								    		$('#yzm_mothed').html("验证码已发送到手机");
								    		$('#yzm_user').html(oPwUser.val());
								    		countDown();
								    		layer.closeAll();
				 					}else{
				 						if(data.msg=='用户不存在'){
				 							layer.open({
				 							    content: '<div class="loginTips">该手机号未注册！</div>',
				 							    btn:['直接注册','取消'],
				 							    shadeClose: false,
				 							    yes: function(){
				 							        window.location.href="/customer/toRegister";
				 							    },
				 							    no:function(){

				 							    }
				 							});
				 						}else{
				 							showShadedowTips(data.msg,1);	 
				 						}
				 						
				 					}
				 				}
				 			})
						}else{
							$('#rTips').html('请输入正确的验证码').show();
						}
					}
				})
	
	    		//$('#pwAuthCodeVal').val()  这是取得验证码的值，判断是否正确
	    		//正确跳转到第二步,显示第二块，进行发送验证码并开始倒计时
	    		/*
	    		
	    		    $('#pwStep_1').hide();
	    			$('#pwStep_2').show();
					countDown();
	
	    		*/
	    		//不正确显示提示
	    		/*
	    		
	    			$('#rTips').html('请输入正确的验证码');
	
					$('#pwAuthCodeVal').foucs(function(){
						$('#rTips').html('');
					});
	    		*/
	    	
	    	
	 			
	    	}, no: function(){
	        	
	    	}
		    
		});
	}
	
	//邮箱
	if(flag==2){
		//如果邮箱已经注册过的提示是
		/*		
		layer.open({
		    content: '<div class="loginTips">该邮箱未注册</div>',
		    btn:['知道了','直接注册'],
		    yes: function(){
	        	//直接跳转到手机注册
	    		window.location.href="reg.html";
	    	}, no: function(){
	        	
	    	}
		    
		});*/
	
		//如果邮箱没有被注册
		//是先弹出图片验证码进行验证
		layer.open({
		    content: '<div class="regAuthCode"><input type="text" placeholder="请输入" id="pwAuthCodeVal"><img class="" src="images/1.jpg"><p id="rTips"></p></div>',
		    btn:['确认','取消'],
		    shadeClose: false,
		    yes: function(){
	        	//验证验证码是否正确
	        	//如果对了，就跳转到第二步
	    		
	
	    		//$('#pwAuthCodeVal').val()  这是取得验证码的值，判断是否正确
	    		//正确跳转到第二步,显示第二块，进行发送验证码并开始倒计时
	    		/*
	    		
	    		    $('#pwStep_1').hide();
	    			$('#pwStep_2').show();
					countDown();
	
	    		*/
	    		//不正确显示提示
	    		/*
	    		
	    			$('#rTips').html('请输入正确的验证码');
	
					$('#pwAuthCodeVal').foucs(function(){
						$('#rTips').html('');
					});
	    		*/
	    	
	    		$('#pwStep_1').hide();
	    		$('#pwStep_2').show();
	    		$('#yzm_mothed').html("验证码已发送到邮箱");
	    		$('#yzm_user').html(oPwUser.val());
	    		countDown();
	    		layer.closeAll();
	 			
	    	}, no: function(){
	        	
	    	}
		    
		});
	}
	
});

//第二个下一步
oNextStep2.click(function(){
	//验证输入的手机验证码是否正确 
	//如果不正确
	//showShadedowTips('请输入正确的验证码！',1);
	//return;
	//如果正确跳转到第三步设置密码
	//$('#pwStep_2').hide();
    //$('#pwStep_3').show();
    
    var phone =  oPwUser.val(); 
	var vcode = $("#vcode").val();
	
   if(!checkMobile(phone)){	
		showShadedowTips('手机号错误！',1);	   
 	    return;
   }
   if(vcode.length != 6){
	   showShadedowTips('验证码错误！',1);	   
	   return;
   }
   $('#pwStep_1').hide();
   $('#pwStep_2').hide();
   $('#pwStep_3').show();
/*	CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/verifiVcode", {"phone":phone,"vcode":vcode,"type":3}, function(data){
			
			if(data){
				if(data.code == 0){
					$('#pwStep_1').hide();
				    $('#pwStep_2').hide();
				    $('#pwStep_3').show();
				}else{
					showShadedowTips(data.msg,1);	 
				}
			}
		})*/
});


//密码blur时验证是否格式正确
oRegPas.blur(function(){
	var _val = $(this).val();

	if(!checkPas(_val)){
		showShadedowTips('请输入6-16位密码',1);	
	}
});



$('#pwSubmit').click(function(){

	//用户名（手机号或邮箱）：pw_user
	//密码：pw_pas
	var _val = oRegPas.val();
	if(!checkPas(_val)){
		showShadedowTips('请输入6-16位密码',1);	
	}
	//如果正确
	//showShadedowTips('注册成功！',1);	
	//setTimeout(fucntion(){window.location.href="index.html";},1500);
	
	var phone =  oPwUser.val(); 
	var password =  _val;
	var vcode =  $("#vcode").val();
	
	if((phone == null||phone==""||phone==undefined)&&(vcode == null||vcode==""||vcode==undefined)){
		showShadedowTips("参数错误",1);
		return false;
		
	}
	password = "c"+password+"b";
	var password = $.md5(password);
	/**
	 * 重置密码
	 */
	CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/resetPassword", {"phone":phone,"vcode":vcode,"password":password}, function(data){
		if(data){
			var code = data.code;
			if(code == 0){
				showShadedowTips('修改成功',1);	
				window.location.href = SERVER_CONTEXT+"customer/login";
			}else{
				showShadedowTips(data.msg,1);	
			}
		}
	})
	
});


function countDown(){
	var oCountDownTxt =$('#countDownTxt');
	if(oCountDownTxt.html()=='点击重新获取验证码'){
		var n=59;
		oCountDownTxt.html(n+' 秒后重新获取验证码');

		var timer = setInterval(function(){
			n--;
			oCountDownTxt.html(n+' 秒后重新获取验证码');
			if(n==0){
				clearInterval(timer);
				oCountDownTxt.html('点击重新获取验证码');
			}
		},1000);
	}
	
}