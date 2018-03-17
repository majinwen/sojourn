(function ($) {
	var userAuth = {};
	var isSubmit=true;
	
	/**
	 * 证件照认证预览
	 */
	$("#frontPicUp").uploadPreview({ Img: "frontPic", Width: 140, Height: 104 });
	$("#backPicUp").uploadPreview({ Img: "backPic", Width: 140, Height: 104 });
	$("#handPicUp").uploadPreview({ Img: "handPic", Width: 140, Height: 104 });
	
	/**
	 * 保存用户认证信息
	 */
	userAuth.submitUserAuth=function (paramData){
		CommonUtils.ajaxPostSubmit("/customer/updateAuthData",paramData,function(data){
			if (data.code==0) {
				$("#frontPicStr").val("");
				$("#backPicStr").val("");
				$("#handPicStr").val("");
				$("#frontFid").val(data.data.picList[0].fid);
				$("#frontPicUuid").val(data.data.picList[0].picServerUuid);
				$("#backFid").val(data.data.picList[1].fid);
				$("#backPicUuid").val(data.data.picList[1].picServerUuid);
				$("#handFid").val(data.data.picList[2].fid);
				$("#handPicUuid").val(data.data.picList[2].picServerUuid);
				var oDiv3=$('#J_part_main3');
				if(oDiv3.length>0){
					oDiv3.css('display','block');
		            var h2=oDiv3.offset().top-100;
		            $('html,body').animate({scrollTop:h2+'px'});
				}
				isSubmit=true;
				showConfirm("保存成功","确定");
			} else {
				showConfirm(data.msg,"确定");
			}
		});
	}
	
	//更新用户昵称，用户头像 ，用户个人介绍
	userAuth.updateUserInfo = function(){
		
		var imgUploaded=$("#imgUploaded").val();
		var nickName=$("#nickName").val();
		var jieshao=$("#J_user_jieshao").val();
		
		if($.trim(imgUploaded).length == 0){
			$("#J_touxiang_tishi").show();
			return;
		}else{
			$("#J_touxiang_tishi").hide();
		}
		if($.trim(nickName).length == 0){
			$("#J_nicheng_tishi").show();
			return;
		} else if($.trim(nickName).length >30) {
			$("#J_nicheng_tishi").html("昵称过长").show();
			return;
		}else{
			$("#J_nicheng_tishi").hide();
		}
		
		if($.trim(jieshao).length == 0){
			$("#J_jieshaon_tishi").show();
			return;
		} else if($.trim(jieshao).length >500) {
			$("#J_jieshaon_tishi").html("个人介绍过长").show();
			return;
		}else if($.trim(jieshao).length>0&&$.trim(jieshao).length<20){
			$("#J_jieshaon_tishi").html("个人介绍过短").show();
			return;
		}else{
			$("#J_jieshaon_tishi").hide();
		}
		
		var formData = $("#customerInfoForm").serialize();
		$.post("/customer/updateNickNameAndExt",formData,function(data){
			if(data.code == 0){
				window.location.href = "/houseIssue/houseIssueOrAuth";
			}else{
				showConfirm(data.msg,"确定");
			}
		},'json');
	}
	
	/**
	 * 验证证件号码
	 */
	userAuth.checkIdNo=function (){
		var idType=$("#idType").val();
		var idNo=$("#idNo").val();
		var oTishi=$("#J_idno_tishi");
		if($.trim(idNo).length == 0){
            oTishi.css('display','inline');
            return false;
        }
		//判断身份证
		if(idType==1){
			if(!isCardNo(idNo)){
				oTishi.css('display','inline');
				return false;
			} else {
				oTishi.css('display','none');
				return true;
			}
		}
		//护照判断
		if(idType==2){
			if(!isPassport(idNo)){
				oTishi.css('display','inline');
				return false;
			} else {
				oTishi.css('display','none');
				return true;
			}
		}
		//港澳通行证
		if(idType==13){
			if(!isHKMacao(idNo)){
				oTishi.css('display','inline');
				return false;
			} else {
				oTishi.css('display','none');
				return true;
			}
		}
		//台湾通行证
		if(idType==6){
			if(!isTaiwan(idNo)){
				oTishi.css('display','inline');
				return false;
			} else {
				oTishi.css('display','none');
				return true;
			}
		}
	}
	
	// 验证身份证  
	function isCardNo(card) {  
	 var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	 return pattern.test(card);  
	}  
	
	// 验证护照
	function isPassport(card) {  
	 var re1 = /^[a-zA-Z]{5,17}$/;  
	 var re2 = /^[a-zA-Z0-9]{5,17}$/;    
	 return re1.test(card) || re2.test(card);  
	}  
	// 港澳通行证验证
	function isHKMacao(card) {  
	 var re = /^[HMhm]{1}([0-9]{10}|[0-9]{8})$/; 
	 return re.test(card);  
	}  
	
	// 台湾通行证验证
	function isTaiwan(card) {  
	 var re1 = /^[0-9]{8}$/;  
	 var re2 = /^[0-9]{10}$/; 
	 return re1.test(card) || re2.test(card);
	} 
	
	/**
	 * 绑定事件
	 */
	$("#authButton").click(function(){
		if(!isSubmit){
			return false;
		}
		var realName=$("#J_name_ipt").val();
		$("#idType").val($("#J_zhengjian_ipt").attr("data-type"));
		var idType=$("#idType").val();
		var frontFid=$("#frontFid").val();
		var frontPicUuid=$("#frontPicUuid").val();
		var frontPicStr=$("#frontPicStr").val();
		var backFid=$("#backFid").val();
		var backPicUuid=$("#backPicUuid").val();
		var backPicStr=$("#backPicStr").val();
		var handFid=$("#handFid").val();
		var handPicUuid=$("#handPicUuid").val();
		var handPicStr=$("#handPicStr").val();
		if($.trim(realName).length == 0){
			$('#J_name_tishi').show();
			return;
		}
		if($.trim(realName).length >= 30){
			$('#J_name_tishi').html("姓名过长").show();
			return;
		}
		if($.trim(idType).length == 0){
			$('#J_zhengjianType_tishi').show();
			return;
		}
		if(!userAuth.checkIdNo()){
			return;
		}
		if($.trim(frontPicStr).length == 0 && $.trim(frontFid).length == 0){
			$("#J_zhaopian_tishi").html("请上传证件正面照片").show();
			return;
		}
		if($.trim(backPicStr).length == 0 && $.trim(backFid).length == 0){
			$("#J_zhaopian_tishi").html("请上传证件反面照片").show();
			return;
		}
		if($.trim(handPicStr).length == 0 && $.trim(handFid).length == 0){
			$("#J_zhaopian_tishi").html("请上传本人手持证件照片").show();
			return;
		}
		userAuth.submitUserAuth($("#authForm").serialize());
		isSubmit=false;
	});
	
	/**
	 * 绑定验证证件事件
	 */
	/*$("#idNo").blur(function(){
		userAuth.checkIdNo();
	});*/
	
	//保存用户信息,跳转发布房源页面
	$("#J_step3_submit").click(function(){
		userAuth.updateUserInfo();
	});
}(jQuery));
(function ($) {
	var userAuth = {};
	var isSubmit=true;
	
	/**
	 * 证件照认证预览
	 */
	$("#frontPicUp").uploadPreview({ Img: "frontPic", Width: 140, Height: 104 });
	$("#backPicUp").uploadPreview({ Img: "backPic", Width: 140, Height: 104 });
	$("#handPicUp").uploadPreview({ Img: "handPic", Width: 140, Height: 104 });
	
	/**
	 * 保存用户认证信息
	 */
	userAuth.submitUserAuth=function (paramData){
		CommonUtils.ajaxPostSubmit("/customer/updateAuthData",paramData,function(data){
			if (data.code==0) {
				$("#frontPicStr").val("");
				$("#backPicStr").val("");
				$("#handPicStr").val("");
				$("#frontFid").val(data.data.picList[0].fid);
				$("#frontPicUuid").val(data.data.picList[0].picServerUuid);
				$("#backFid").val(data.data.picList[1].fid);
				$("#backPicUuid").val(data.data.picList[1].picServerUuid);
				$("#handFid").val(data.data.picList[2].fid);
				$("#handPicUuid").val(data.data.picList[2].picServerUuid);
				var oDiv3=$('#J_part_main3');
				if(oDiv3.length>0){
					oDiv3.css('display','block');
		            var h2=oDiv3.offset().top-100;
		            $('html,body').animate({scrollTop:h2+'px'});
				}
				isSubmit=true;
				showConfirm("保存成功","确定");
			} else {
				showConfirm(data.msg,"确定");
			}
		});
	}
	
	//更新用户昵称，用户头像 ，用户个人介绍
	userAuth.updateUserInfo = function(){
		
		var imgUploaded=$("#imgUploaded").val();
		var nickName=$("#nickName").val();
		var jieshao=$("#J_user_jieshao").val();
		
		if($.trim(imgUploaded).length == 0){
			$("#J_touxiang_tishi").show();
			return;
		}else{
			$("#J_touxiang_tishi").hide();
		}
		if($.trim(nickName).length == 0){
			$("#J_nicheng_tishi").show();
			return;
		} else if($.trim(nickName).length >30) {
			$("#J_nicheng_tishi").html("昵称过长").show();
			return;
		}else{
			$("#J_nicheng_tishi").hide();
		}
		
		if($.trim(jieshao).length == 0){
			$("#J_jieshaon_tishi").show();
			return;
		} else if($.trim(jieshao).length >500) {
			$("#J_jieshaon_tishi").html("个人介绍过长").show();
			return;
		}else if($.trim(jieshao).length>0&&$.trim(jieshao).length<20){
			$("#J_jieshaon_tishi").html("个人介绍过短").show();
			return;
		}else{
			$("#J_jieshaon_tishi").hide();
		}
		
		var formData = $("#customerInfoForm").serialize();
		$.post("/customer/updateNickNameAndExt",formData,function(data){
			if(data.code == 0){
				window.location.href = "/houseIssue/houseIssueOrAuth";
			}else{
				showConfirm(data.msg,"确定");
			}
		},'json');
	}
	
	/**
	 * 验证证件号码
	 */
	userAuth.checkIdNo=function (){
		var idType=$("#idType").val();
		var idNo=$("#idNo").val();
		var oTishi=$("#J_idno_tishi");
		if($.trim(idNo).length == 0){
            oTishi.css('display','inline');
            return false;
        }
		//判断身份证
		if(idType==1){
			if(!isCardNo(idNo)){
				oTishi.css('display','inline');
				return false;
			} else {
				oTishi.css('display','none');
				return true;
			}
		}
		//护照判断
		if(idType==2){
			if(!isPassport(idNo)){
				oTishi.css('display','inline');
				return false;
			} else {
				oTishi.css('display','none');
				return true;
			}
		}
		//港澳通行证
		if(idType==13){
			if(!isHKMacao(idNo)){
				oTishi.css('display','inline');
				return false;
			} else {
				oTishi.css('display','none');
				return true;
			}
		}
		//台湾通行证
		if(idType==6){
			if(!isTaiwan(idNo)){
				oTishi.css('display','inline');
				return false;
			} else {
				oTishi.css('display','none');
				return true;
			}
		}
	}
	
	// 验证身份证  
	function isCardNo(card) {  
	 var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	 return pattern.test(card);  
	}  
	
	// 验证护照
	function isPassport(card) {  
	 var re1 = /^[a-zA-Z]{5,17}$/;  
	 var re2 = /^[a-zA-Z0-9]{5,17}$/;    
	 return re1.test(card) || re2.test(card);  
	}  
	// 港澳通行证验证
	function isHKMacao(card) {  
	 var re = /^[HMhm]{1}([0-9]{10}|[0-9]{8})$/; 
	 return re.test(card);  
	}  
	
	// 台湾通行证验证
	function isTaiwan(card) {  
	 var re1 = /^[0-9]{8}$/;  
	 var re2 = /^[0-9]{10}$/; 
	 return re1.test(card) || re2.test(card);
	} 
	
	/**
	 * 绑定事件
	 */
	$("#authButton").click(function(){
		if(!isSubmit){
			return false;
		}
		var realName=$("#J_name_ipt").val();
		$("#idType").val($("#J_zhengjian_ipt").attr("data-type"));
		var idType=$("#idType").val();
		var frontFid=$("#frontFid").val();
		var frontPicUuid=$("#frontPicUuid").val();
		var frontPicStr=$("#frontPicStr").val();
		var backFid=$("#backFid").val();
		var backPicUuid=$("#backPicUuid").val();
		var backPicStr=$("#backPicStr").val();
		var handFid=$("#handFid").val();
		var handPicUuid=$("#handPicUuid").val();
		var handPicStr=$("#handPicStr").val();
		if($.trim(realName).length == 0){
			$('#J_name_tishi').show();
			return;
		}
		if($.trim(realName).length >= 30){
			$('#J_name_tishi').html("姓名过长").show();
			return;
		}
		if($.trim(idType).length == 0){
			$('#J_zhengjianType_tishi').show();
			return;
		}
		if(!userAuth.checkIdNo()){
			return;
		}
		if($.trim(frontPicStr).length == 0 && $.trim(frontFid).length == 0){
			$("#J_zhaopian_tishi").html("请上传证件正面照片").show();
			return;
		}
		if($.trim(backPicStr).length == 0 && $.trim(backFid).length == 0){
			$("#J_zhaopian_tishi").html("请上传证件反面照片").show();
			return;
		}
		if($.trim(handPicStr).length == 0 && $.trim(handFid).length == 0){
			$("#J_zhaopian_tishi").html("请上传本人手持证件照片").show();
			return;
		}
		userAuth.submitUserAuth($("#authForm").serialize());
		isSubmit=false;
	});
	
	/**
	 * 绑定验证证件事件
	 */
	/*$("#idNo").blur(function(){
		userAuth.checkIdNo();
	});*/
	
	//保存用户信息,跳转发布房源页面
	$("#J_step3_submit").click(function(){
		userAuth.updateUserInfo();
	});
}(jQuery));