var oRegUser =$('#reg_user');
var oRegPas =$('#reg_pas');
var oNextStep1=$('#nextStep_1');
var oNextStep2=$('#nextStep_2');

var imgId = $('#imgId').val();
var imgUrl = $('#imgUrl').val();
var imgSrc=imgUrl+''+imgId;

//第一步的按扭
oNextStep1.click(function(){
	if(!checkMobile(oRegUser.val())){   
		showShadedowTips('请输入正确的手机号！',1);      
		return;
	}

	//如果手机号没有被注册
	//是先弹出图片验证码进行验证
	layer.open({
		content: '<div class="regAuthCode"><input type="text" placeholder="请输入" id="imgVValue" onfocus="$(\'#rTips\').hide();"><img class="" src="'+imgSrc+'" imgId="'+imgId+'"  imgUrl="'+imgUrl+'"  id="imgUrlId" onclick="Customer.reloadImg()"><p id="rTips"></p></div>',
		btn:['确认','取消'],
		shadeClose: false,
		yes: function(){
			//验证验证码是否正确
			//如果对了，就跳转到第二步
			$('#yzTelVal').html(oRegUser.val());

			var imgVValue = $("#imgVValue").val();
			var imgId = $("#imgUrlId").attr("imgId");

			if(imgVValue == null ||imgVValue == undefined ||imgVValue ==""||imgId == null ||imgId == undefined ||imgId ==""){
				showShadedowTips('参数错误',1);   
				return false;
			}
			CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/checkImgCode", {"imgId":imgId,"imgVValue":imgVValue}, function(data){

				if(data){
					//正确跳转到第二步,显示第二块，进行发送验证码并开始倒计时
					if(data.code == 0){
						var phone =  oRegUser.val();
						var imgVid = data.data.imgVid;
						if(!checkMobile(phone)){  
							showShadedowTips('手机号错误！',1);      
							return;
						}
						CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/getMobileCode", {"phone":phone,"imgId":imgId,"imgVValue":imgVValue,"imgVid":imgVid}, function(data){

							if(data){
								if(data.code == 0){
									//测试
									if(data.data.vcode){
										//alert("测试验证码："+data.data.vcode)
									}
									$('#regStep_1').hide();
									$('#regStep_2').show();
									countDown();
									layer.closeAll();
								}else{

									if(data.msg=='用户已存在'){
										layer.open({
											content: '<div class="loginTips">该手机号已注册！</div>',
											btn:['直接登录','取消'],
											shadeClose: false,
											yes: function(){
												window.location.href="/customer/login";
											},
											no:function(){

											}
										});
									}else{
										showShadedowTips(data.msg,3);      
									}


								}
							}
						})
					}else{
						$('#rTips').html('请输入正确的验证码').show();
					}
				}
			})
			//layer.closeAll();
			$('#imgVValue').focus(function(){
				$('#rTips').html('');
			})
		}, no: function(){

		}

	});


});

//第二个下一步
oNextStep2.click(function(){
	//验证输入的手机验证码是否正确 

	var phone =  oRegUser.val(); 
	var vcode = $("#vcode").val();

	if(!checkMobile(phone)){ 
		showShadedowTips('手机号错误！',1);      
		return;
	}
	if(vcode.length != 6){
		showShadedowTips('验证码错误！',1);       
		return;
	}
	CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/verifiVcode", {"phone":phone,"vcode":vcode}, function(data){

		if(data){
			if(data.code == 0){
				$('#regStep_2').hide();
				$('#regStep_3').show();
				var vid = data.data.vid;
				$("#vid").val(vid);
				// layer.closeAll();
			}else{
				showShadedowTips(data.msg,1);    
			}
		}
	})
});


//密码blur时验证是否格式正确
oRegPas.blur(function(){
	var _val = $(this).val();
	if(!checkPas(_val)){
		showShadedowTips('请输入6-16位密码',1);   
	}
});



$('#regSubmit').click(function(){

	//手机号：reg_user
	//密码：reg_pas
	var _val = oRegPas.val();
	if(!checkPas(_val)){
		showShadedowTips('请输入6-16位密码',1);   
		return false;
	} 

	var phone =  oRegUser.val(); 
	var password =  _val;
	var vcode =  $("#vcode").val();

	if((phone == null||phone==""||phone==undefined)&&(vcode == null||vcode==""||vcode==undefined)){
		showShadedowTips("参数错误",1);
		return false;

	}
	password = "c"+password+"b";
	var password = $.md5(password);
	

	/**
	 * 去注册
	 */
	CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/userRegister", {"phone":phone,"vcode":vcode,"password":password,"vid":$("#vid").val()}, function(data){
		if(data){
			var code = data.code;
			if(code == 0){
				showShadedowTips('注册成功！');
				setTimeout(function(){
					window.location.href = SERVER_CONTEXT+"houseMgt/43e881/myHouses";
				},1000);

			}else{
				showShadedowTips(data.msg,1);   
			}
		}
	})

	//如果正确
	//showShadedowTips('注册成功！',1);  
	//setTimeout(fucntion(){window.location.href="index.html";},1500);



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