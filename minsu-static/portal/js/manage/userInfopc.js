(function ($) {
	
	/*邮箱验证*/
	function checkEmail(email){
	    var emailReg;
	    var pattern = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
	    if (pattern.test(email)) {
	        emailReg = true;
	    }else{
	        emailReg = false;
	    }
	    return emailReg;
	}
	
	var userInfo = {};
	
	/**
	 * 保存更新用户基本信息
	 */
	
	userInfo.submitUserInfo=function (userInfoParams){
		CommonUtils.ajaxPostSubmit("/customer/updatePersonData",userInfoParams,function(data){
			if (data.code==0) {
				showConfirm("更新成功","确定");
				$('#userInfoButton').removeAttr("disabled");
			} else {
				showConfirm(data.msg);
				$('#userInfoButton').removeAttr("disabled");
			}
		});
	}
	
	/**
	 * 绑定提交事件
	 */
	$("#userInfoButton").click(function(){
		var userInfoParams={
				"uid":$("#uid").val(),
				"nickName":$("#nickName").val(),
				"customerSex":$("input[name=customerSex]:checked").val(),
				"customerBirthdayStr": $("#J_birth_input").val(),
				"customerEmail": $("#J_email").val(),
				"resideAddr": $("#resideAddr").val(),
				"customerEdu":$("#J_jiaoyu_ipt").attr("data-xueli"),
				"customerJob":$("#customerJob").val(),
				"customerIntroduce":$("#J_user_jieshao").val()
		};
		
		if(userInfoParams.nickName.length>0 && userInfoParams.nickName.length>30){
			$('#J_nicheng_tishi').html("昵称过长").show();
			return;
		}else{
			$('#J_nicheng_tishi').hide();
		}
		
		if(userInfoParams.customerEmail.length>0 && !checkEmail(userInfoParams.customerEmail)){
			$('#J_email_tishi').show();
			return;
		}else{
			$('#J_email_tishi').hide();
		}
		
		if(userInfoParams.resideAddr.length>0 && userInfoParams.resideAddr.length>200){
			$('#J_dizhi_tishi').html("居住地址过长").show();
			return;
		}else{
			$('#J_dizhi_tishi').hide();
		}
		
		if(userInfoParams.customerJob.length>0 && userInfoParams.customerJob.length>80){
			$('#J_zhiye_tishi').html("职业描述过长").show();
			return;
		}else{
			$('#J_zhiye_tishi').hide();
		}
		
		if(userInfoParams.customerIntroduce.length>0 && userInfoParams.customerIntroduce.length>500){
			$('#J_jieshaon_tishi').html("个人介绍过长").show();
			return;
		}else if(userInfoParams.customerIntroduce.length<70){
			$('#J_jieshaon_tishi').html("个人介绍不能少于70字").show();
			return;
		}else{
			$('#J_jieshaon_tishi').hide();
		}
		//防重复点击处理
		$('#userInfoButton').attr("disabled","disabled");
		userInfo.submitUserInfo(userInfoParams);
	});
}(jQuery));
/*(function ($) {
	
	邮箱验证
	function checkEmail(email){
	    var emailReg;
	    var pattern = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
	    if (pattern.test(email)) {
	        emailReg = true;
	    }else{
	        emailReg = false;
	    }
	    return emailReg;
	}
	
	var userInfo = {};
	
	*//**
	 * 保存更新用户基本信息
	 *//*
	
	userInfo.submitUserInfo=function (userInfoParams){
		CommonUtils.ajaxPostSubmit("/customer/updatePersonData",userInfoParams,function(data){
			if (data.code==0) {
				showConfirm("更新成功","确定");
			} else {
				showConfirm(data.msg);
			}
		});
	}
	
	*//**
	 * 绑定提交事件
	 *//*
	$("#userInfoButton").click(function(){
		var userInfoParams={
				"uid":$("#uid").val(),
				"nickName":$("#nickName").val(),
				"customerSex":$("input[name=customerSex]:checked").val(),
				"customerBirthdayStr": $("#J_birth_input").val(),
				"customerEmail": $("#J_email").val(),
				"resideAddr": $("#resideAddr").val(),
				"customerEdu":$("#J_jiaoyu_ipt").attr("data-xueli"),
				"customerJob":$("#customerJob").val(),
				"customerIntroduce":$("#J_user_jieshao").val()
		};
		
		if(userInfoParams.nickName.length>0 && userInfoParams.nickName.length>30){
			$('#J_nicheng_tishi').html("昵称过长").show();
			return;
		}else{
			$('#J_nicheng_tishi').hide();
		}
		
		if(userInfoParams.customerEmail.length>0 && !checkEmail(userInfoParams.customerEmail)){
			$('#J_email_tishi').show();
			return;
		}else{
			$('#J_email_tishi').hide();
		}
		
		if(userInfoParams.resideAddr.length>0 && userInfoParams.resideAddr.length>200){
			$('#J_dizhi_tishi').html("居住地址过长").show();
			return;
		}else{
			$('#J_dizhi_tishi').hide();
		}
		
		if(userInfoParams.customerJob.length>0 && userInfoParams.customerJob.length>80){
			$('#J_zhiye_tishi').html("职业描述过长").show();
			return;
		}else{
			$('#J_zhiye_tishi').hide();
		}
		if(userInfoParams.customerIntroduce.length>0 && userInfoParams.customerIntroduce.length>500){
			$('#J_jieshaon_tishi').html("个人介绍过长").show();
			return;
		}else if(userInfoParams.customerIntroduce.length>0 && userInfoParams.customerIntroduce.length<100){
			$('#J_jieshaon_tishi').html("个人介绍不能少于100字").show();
			return;
		}else{
			$('#J_jieshaon_tishi').hide();
		}
		 
		userInfo.submitUserInfo(userInfoParams);
	});
}(jQuery));*/