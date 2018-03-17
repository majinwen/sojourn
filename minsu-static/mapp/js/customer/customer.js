(function ($) {
	
	//$.md5("c123456b") = a53c9868827ee7df457110549547fbb0  
	var customer = {};
	
	//公用header参数
	var headerMap = {
			"client-version":"1.0",
			"client-type":3
	};
	
	var oLoginUser =$('#login_user');
	var oLoginPas = $('#login_pas');
	var oLoginCode = $('#login_code');
	/**
	 * 用户js初始化
	 */
	customer.init = function(){
		
		/**
		 * 手机注册，
		 * 1.点击下一步
		 */
		$("#mobileCheck").bind("click",function(){
			
			var phone = $("#phone").val();
			var imgId = $("#imgUrlId").attr("imgId");
			
			if(phone == null ||phone == undefined ||phone ==""||imgId == null ||imgId == undefined ||imgId ==""){
				showShadedowTips("参数错误",1);
				return false;
			}
			CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/getMobileCode", {"phone":phone,"imgId":imgId}, function(data){
				
				if(data){
					if(data.code == 0){
						$("#vcode").html(data.data.vcode);
						$("#mobileCheck").hide();
						$("#passwordBut").show();
						showShadedowTips("获取验证码成功",1);
					}else{
						showShadedowTips(data.msg,1);
					}
				}
			})
			
			
		});
		
		/**
		 * 校验 图像验证码
		 */
	   $("#imgButton").bind("click",function(){
			
			var imgVValue = $("#imgVValue").val();
			var imgId = $("#imgUrlId").attr("imgId");
			
			if(imgVValue == null ||imgVValue == undefined ||imgVValue ==""||imgId == null ||imgId == undefined ||imgId ==""){
				showShadedowTips("参数错误",1);
				return false;
			}
			CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/checkImgCode", {"imgId":imgId,"imgVValue":imgVValue}, function(data){
				
				if(data){
					if(data.code == 0){
						$("#imgCheck").hide();
						$("#mobileCheck").show();
						showShadedowTips("验证成功",1);
					}else{
						showShadedowTips(data.msg,1);
					}
				}
			})
			
			
		});
		
       // 用户登录
	   $("#userLogin").bind("click",function(){
	 
	    
	    var oSubmitBtn=$('#userLogin');
		var password = oLoginPas.val();
		$("#phone").val("");
		$("#email").val("");
		$("#username").val("");

		if(oSubmitBtn.hasClass('disabled_btn')){
		    return false;
		}
		if(!checkMobile(oLoginUser.val()) && !checkEmail(oLoginUser.val()) ){
			showShadedowTips('请输入正确的手机号或邮箱！',1);	   
		    return false;
		}
		if(password==null||password==""||password==undefined){
			showShadedowTips('密码错误',1);	   
		    return false;
		}
		$("#password").val(password);
		//手机正确
		if(checkMobile(oLoginUser.val())){
			var phone = oLoginUser.val();
			$("#phone").val(phone)
		}
		//邮箱正确
		if(checkEmail(oLoginUser.val())){
			var email = oLoginUser.val();
			$("#email").val(email)
		}
	    
		customer.userLogin();
		});
	   //用户注册 第一步 获取验证码
	   $("#getMobileCode").bind("click",function(){
			customer.getMobileCode();
		});
	   
	/*   $('#login_user').blur(function(){
			if(!checkMobile(oLoginUser.val()) && !checkEmail(oLoginUser.val()) ){	
				showShadedowTips('请输入正确的手机号或邮箱！',1);	   
			    return;
			}
			if(checkMobile(oLoginUser.val())){//手机号输入正确进行验证是否是已注册用户
				//如果是没有注册过的用户显示提示
				console.log(111);
				layer.open({
					    content: '<div class="loginTips">该手机号未注册</div>',
					    btn:['直接注册','知道了'],
					    yes: function(){
				        	//直接跳转到手机注册
				    		window.location.href="reg.html";
				    	}, no: function(){
				        	
				    	}
					    
					});
			}

			
		});*/
	}
	
	/**
	 * 用户注册
	 */
	customer.userRigster = function(phone,password,vcode){
		var phone = $("#phone").val();
		var password =  $("#password").val();
		var vcode =  $("#vcode").val();
		
		
		if((phone == null||phone==""||phone==undefined)&&(vcode == null||vcode==""||vcode==undefined)){
			showShadedowTips("参数错误",1);
			return false;
			
		}
		if(password == null||password==""||password==undefined){
			showShadedowTips("密码错误");
			return false;
		}
		password = "c"+password+"b";
		var password = $.md5(password);
		/**
		 * 去注册
		 */
		CommonUtils.ajaxPostHeaderSubmit(SERVER_CONTEXT+"customer/userLogin", {"phone":phone,"vcode":vcode,"password":password},headerMap, function(data){
			if(data){
				var code = data.code;
				if(code == 0){
					showShadedowTips("注册成功",1);
					window.location.href = SERVER_CONTEXT+"houseMgt/"+LOGIN_UNAUTH+"/myHouses?landlordUid="+data.data.uid;
				}else{
					showShadedowTips(data.msg,1);
				}
			}
		})
		
	}
	/**
	 * 用户登录
	 */
	customer.userLogin = function(){
		var phone = $("#phone").val();
		var email = $("#email").val();
		var username = $("#username").val();
		var password =  $("#password").val();
		var currImgId =  $("#imgUrlId").attr("imgId");
		var currImgVValue =  $("#imgVValue").val();
		var flag = 0;
		
		if((phone == null||phone==""||phone==undefined)&&(email == null||email==""||email==undefined)&&(username == null||username==""||username==undefined)){
			showShadedowTips("参数错误",1);
			return false;
			
		}
		if(password == null||password==""||password==undefined){
			showShadedowTips("密码错误",1);
			return false;
		}
		password = "c"+password+"b";
		var password = $.md5(password)
		
		/**
		 * 去登录
		 */
		CommonUtils.ajaxPostHeaderSubmit(SERVER_CONTEXT+"customer/userLogin", {"phone":phone,"email":email,"username":username,"password":password,"imgId":currImgId,"imgVValue":currImgVValue},headerMap, function(data){
			if(data){
				var code = data.code;
				if(code == 0){
					var reg = new RegExp("(^|&)session_return_url_key=([^&]*)(&|$)");
					var r = window.location.search.substr(1).match(reg);
					if(r != null){
						window.location.href = SERVER_CONTEXT+unescape(r[2]);
					}else{
						window.location.href = SERVER_CONTEXT+"orderland/"+LOGIN_UNAUTH+"/showlist";
					}

				}else{
					if(data.data.etTimes == "40001"){
						//密码错误5次，让输入图形验证码
						$("#userVcode").show();
						customer.reloadImg();
						//验证验证码是否正确
				    	var imgVValue = $("#imgVValue").val();
						var imgId = $("#imgUrlId").attr("imgId");
						$("#currImgId").val(imgId);
					}else{
						showShadedowTips(data.msg,1);
					}
					
				}
			}
		})
	}
	/**
	 * 获取验证码
	 */
	customer.getMobileCode = function(){
		var phone = $("#reg_user").val();
		var imgId = $("#imgUrlId").attr("imgId");
		if(phone == null||phone==""||phone==undefined){
			showShadedowTips("手机号不存在");
			return false;
		}
		CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/getMobileCode", {"phone":phone,"imgId":imgId}, function(data){
			if(data){
				var code = data.code;
				if(code != 0){
					showShadedowTips(data.msg,1);
				}
			}
		})
	}
	/**
	 * 刷新图形验证码
	 */
	customer.reloadImg = function(){
		 var imgUrl = $("#imgUrlId").attr("imgUrl");
		 var imgId = CommonUtils.uuid();
		 imgId =  imgId.substring(0,32);
		 imgUrl = imgUrl+imgId;
		 $("#imgUrlId").attr("src",imgUrl);
		 $("#imgUrlId").attr("imgId",imgId);
	}
	customer.init();
	window.Customer = customer;
}(jQuery));