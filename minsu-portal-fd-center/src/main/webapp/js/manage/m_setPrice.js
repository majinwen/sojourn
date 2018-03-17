
$(function(){
	(function(){
		var oUl=$('#J_typeUl');
		var oIpt=$('#week');

		oIpt.click(function(e){
			oUl.css('display','block');
			e.stopPropagation();
		});

		oUl.find('li').click(function(){
			oIpt.val($(this).html());
			oIpt.attr('data-type',$(this).attr('data-type'));
			oUl.css('display','none');
		});
	})()

	$(".select_box").delegate("span","click",function(e){
		$(this).siblings(".selects_ul").show();
		$("body").addClass("hidden");
		e.stopPropagation();
	})
	$(".selects").delegate("li","click",function(){
		$(this).parents(".select_box").find(".select_span").html($(this).html());
		$(this).addClass("active").siblings().removeClass("active");
		$("body").removeClass("hidden");
		$(this).parents(".selects_ul").hide();
	})
	$(".j_round").each(function(){
		$(this).click(function(){
			if($(this).hasClass("org")){
				$(this).removeClass("org").addClass("gray");
				$(this).parents(".j_round_box").siblings(".j_round_s").hide();
			}else{
				$(this).removeClass("gray").addClass("org");
				$(this).parents(".j_round_box").siblings(".j_round_s").show();
				var seven = $("#seven").val();
				var thirty = $("#thirty").val();
				if(isNullOrBlank($("#qRebate").val())){
					if(seven != '-1' && !isNullOrBlank(seven)){
						$("#qRebate").val(seven);
					}
				}
				if(isNullOrBlank($("#ssRebate").val())){
					if(thirty != '-1' && !isNullOrBlank(thirty)){
						$("#ssRebate").val(thirty);
					}
				}
			}
		})
	})
	$(".j_price_ipt").each(function(){
		$(this).on('input',function(){
			if(!/^[0-9]+$/.test($(this).val())){
				$(this).val('');
			}
		})
	})
	// tap层
	$("#paryPriceHelp").hover(function(){
		var gap0 = $("#gap0").val();
		var gap1 = $("#gap1").val();
		var gap2 = $("#gap2").val();
		var str = "通过空置间夜自动折扣功能，您可以把房源价格根据用户需求及预订日期的变化自动调整，同时，在搜索排名中，根据综合因素我们优先推荐开启了空置间夜自动定价的房源，提升房源出租率。<br>开启空置间夜自动折扣功能，即表示您接受平台对于特定场景的房价自动调整。<br>空置间夜自动折扣场景：<br>1、可连续出租日期仅为1天时，预订价格可享受当日房价"+gap1+"折优惠<br>2、可连续出租日期仅为2天时，预订价格可享受当日房价"+gap2+"折优惠。";
		showTap(str,'paryPriceHelp','3000')
	},function(){
		layer.closeAll();
	})
	$("#rebateHelp").hover(function(){
		var id = $(this).attr("id");
		showTap('设置折扣，吸引房客预订更长时间的住宿','rebateHelp','2000')
	},function(){
		layer.closeAll();
	})
	//校验空的方法
	function isNullOrBlank(obj){
		return obj == undefined || obj == null || $.trim(obj).length == 0 || obj =='';
	}
	$("#submitSetPriceBtn").click(function(){
		var houseBaseFid = $("#houseFid").val();
		var houseRoomFid = $("#roomFid").val();
		var rentWay = $("#rentWay").val();
		var priceLow = $("#priceLow").val();
		var priceHigh = $("#priceHigh").val();
		//插入还是更新的标志
		var statFlag = $("#statFlag").val();
		//更新获取折扣率的fid
		var sevenFid = $("#sevenRateFid").val();
		if(isNullOrBlank(sevenFid)){
			sevenFid = $("#sevenFid").val();
		}
		var thirtyFid = $("#thirtyRateFid").val();
		if(isNullOrBlank(thirtyFid)){
			thirtyFid = $("#thirtyFid").val();
		}
		//固定价格
		var fixPrice = $("#fixPrice").val();
		if(fixPrice == ''){
			showError("请填写固定价格");
			return ;
		}
		if(parseInt(fixPrice,10) > parseInt(priceHigh,10)){
			showError("固定价格不能高于"+priceHigh+"元/晚");
			return ;
		}
		if(parseInt(fixPrice,10) < parseInt(priceLow,10)){
			showError("固定价格不能低于"+priceLow+"元/晚");
			return ;
		}

		/** 周末价格*/
		var week = $("#week").attr('data-type');
		var weekPrice = $("#weekPrice").val();
		//是否设置周末价格标示 0:未设置 1:已设置有效 2:已设置无效
		var weekendPriceFlag = $("#weekendPriceFlag").val();
		//周末价格开关
		var weekendPriceSwitch = 0;
		if($("#weekList").find(".j_round").hasClass("org")){
			if($("#week").val() == ''){
				showError("请选择日期");
				return ;
			}
			if($("#weekPrice").val() == ''){
				showError("请填写周末价格");
				return ;
			}
			if(parseInt($("#weekPrice").val(),10) > parseInt(priceHigh,10)){
				showError("周末价格不能高于"+priceHigh+"元/晚");
				return ;
			}
			if(parseInt(weekPrice,10) < parseInt(priceLow,10)){
				showError("周末价格不能低于"+priceLow+"元/晚");
				return ;
			}
			weekendPriceSwitch =1;
		}
		//控制空置间夜自动定价的开关
		var gapFlex = 0;
		if($("#gapList").find(".j_round").hasClass("org")){
			gapFlex = 1;
		}else{
			gapFlex = 0;
		}
		//今日特惠折扣开关
		var gapFlexT = 0;
		if($("#gapFlexT").find(".j_round").hasClass("org")){
			gapFlexT = 1;
		}else{
			gapFlexT = 0;
		}
		//控制折扣率的开关
		var disRateSwitch =0;
		var qRebate = $("#qRebate").val();
		var ssRebate = $("#ssRebate").val();
		if($("#rebateList").find(".j_round").hasClass("org")){
			disRateSwitch = 1;
			if(parseInt($("#qRebate").val(),10) > 100 || parseInt($("#qRebate").val(),10) < 0){
				showError("请填写正确入住满7天折扣");
				return ;
			}
			if(parseInt($("#ssRebate").val(),10) > 100 || parseInt($("#ssRebate").val(),10) < 0){
				showError("请填写正确入住满30天折扣");
				return ;
			}
			if(isNullOrBlank($("#qRebate").val()) && isNullOrBlank($("#ssRebate").val())){
				showError("请至少填写一个折扣，或者关闭开关");
				return ;
			}

		}else{
			disRateSwitch = 0;
		}
		//提交路径
		var requestUrl = '';
		if(statFlag == 0){
			requestUrl = "/house/savePriceSet";
		}else if(statFlag == 1){
			requestUrl = "/house/updatePriceSet";
		}
		// TODO:防止重复提交
		$(this).attr("disabled","disabled").removeClass("active").addClass("disabled_btn");
		// showPart('calendarPart');
		$.ajax({
			url: requestUrl,
			dataType: "json",
			async: false,
			data: {
				"houseBaseFid":houseBaseFid,
				"houseRoomFid":houseRoomFid,
				"rentWay" : rentWay,
				"leasePrice" : fixPrice,
				"roomPrice" : fixPrice,
				"gapAndFlexiblePrice" : gapFlex,
				"fullDayRate" : disRateSwitch,
				"dayDiscount" : gapFlexT,
				"flexDiscount" : gapFlex,
				"sevenFid" : sevenFid,
				"sevenDiscountRate" : qRebate,
				"thirtyFid" : thirtyFid,
				"thirtyDiscountRate" : ssRebate,
				"setTime" : week,
				"specialPrice" : weekPrice,
				"weekendPriceFlag" : weekendPriceFlag,
				"weekendPriceSwitch" : weekendPriceSwitch,
			},
			type: "POST",
			success: function (result) {
				if(result.code === 0){
					//跳转回日历页面
					if(rentWay == 1){
						window.location.href="/house/1/"+houseRoomFid+"/calendar";
					}else if(rentWay == 0){
						window.location.href="/house/0/"+houseBaseFid+"/calendar";
					}
				} else {
					showError("操作失败");
					$(this).removeAttr("disabled").removeClass("disabled_btn").addClass("org_btn");
				}
			},
			error:function(result){
				showError("未知错误");
				$(this).removeAttr("disabled").removeClass("disabled_btn").addClass("org_btn");
			}
		});

		// 请求成功或失败后
		// $$(this).removeAttr("disabled").removeClass("disabled_btn").addClass("org_btn");

	})
	//取消按钮事件
	$("#cancel").click(function(){
		var houseBaseFid = $("#houseFid").val();
		var houseRoomFid = $("#roomFid").val();
		var rentWay = $("#rentWay").val();
		//跳转回日历页面
		if(rentWay == 1){
			window.location.href="/house/1/"+houseRoomFid+"/calendar";
		}else if(rentWay == 0){
			window.location.href="/house/0/"+houseBaseFid+"/calendar";
		}
	})

})
function showError(str){
	$(".error").fadeIn().html(str);
	setTimeout(function(){
		$(".error").fadeOut().html('');
	},3000)
}
