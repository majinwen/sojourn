//拒绝按钮后弹出选择框
function setRefuseVal(obj,val){
	sRefuseVal = val;
	$(obj).addClass('active').siblings().removeClass();
}

(function ($) {

	var  makeSureBtnValue = 0;

	var orderSn = $("#orderSn").val();
	
	var orderDetail = {};
	var refuseHtml = '<ul class="layerList" id="layerList">'
		+'<li  onclick="setRefuseVal(this,0)"><span class="radio"></span>房客未认证</li>'
		+'<li  onclick="setRefuseVal(this,1)"><span class="radio"></span>房客评价较差</li>'
		+'<li  onclick="setRefuseVal(this,2)"><span class="radio"></span>个人原因</li>'
		+'</ul>';
	var sRefuseVal='';
	
	$('#refuseBtn').click(function(){
		
		window.location.href = SERVER_CONTEXT+"orderland/43e881/toRefuseOrder?orderSn="+$("#orderSn").val();
		
		/*layer.open({
			    title: ['请选择拒绝原因'],
			    content: refuseHtml,
			    btn:['确定','取消'],
			    yes: function(){
			    	var str = ""
			    	if(sRefuseVal == '0'){
			    		str = "房客未认证";
			    	}else if(sRefuseVal == '1'){
			    		str = "房客评价较差";
			    	}else if(sRefuseVal == '2'){
			    		str = "个人原因";
			    	}
			    	orderDetail.refuse(str);
		    	}, no: function(){
		    	}
			    
		});*/
	});
	
	$("#acceptBtn").click(function(){
		orderDetail.accept();
	});
	
	/**新增方法 开始*/
	 $("#makeSureBtn").click(function(){
		    if(makeSureBtnValue != 0){
				console.log("你双击了，不要着急。")
				return;
			}
			if($("#isOtherPrice").val() == 1){
				if($("#otherMoneyValue").val() == ""){
					showShadedowTips("请填写其它费用金额",1);
					return;
				}else if($("#otherMoneyDes").val() == ""){
					showShadedowTips("请输入其它费用明细",1);
					return;
				}
				// 之后的操作
				orderDetail.dosure();
			}else if($("#isOtherPrice").val() == 0){
				layer.open({
					title:"",
					content:"<p style='padding:1rem 0;'>您没有填写其他费用，是否提交确认订单？</p>",
					btn:['确定','取消'],
					yes:function(){
						// 确定之后的操作
						layer.closeAll();
						$("#otherMoneyValue").val(0);
						orderDetail.dosure();
					},
					no:function(){
						layer.closeAll();
					}
				})
			}
		})
    /**新增方法 结束*/
		
	/*$("#makeSureBtn").click(function(){
		if(makeSureBtnValue != 0){
			console.log("你双击了，不要着急。")
			return;
		}
		makeSureBtnValue = 1;
		orderDetail.dosure();
	});*/
	$("#addRemark").click(function(){
		orderDetail.addRemark();
	});
	$(".del_btn").each(function() {
		$(this).click(function() {
			$(this).parents("li").remove();
			$("#addBzBox").show();
			$.post( SERVER_CONTEXT+"orderland/"+LOGIN_UNAUTH+"/delRemark",{"fid":$(this).attr("data-fid")},function(data){},'json');
		})
	})
	$(".moveLi").each(function() {
		var h = $(this).height();
		$(this).find(".del_btn").css({
			"height" : h + "px",
			"line-height" : h + "px"
		})
	})
	
		// 新增方法start 
	// 其他费用
	$("#otherMoneyValue").on({

			focus:function(){
				var inputVal=$(this).val();
				if(inputVal&&inputVal.indexOf('￥')!=-1){
					inputVal = inputVal.replace("￥","");
				}
				$(this).val(inputVal);
			},
			afterpaste:function(){
				var inputVal=$(this).val();
				inputVal = inputVal.replace(/\D/g,'');
				$(this).val(inputVal);
				if(inputVal != ""){
					$(this).parents(".c_ipt_li").removeClass("c_ipt_li_none");
				}else{
					$(this).parents(".c_ipt_li").addClass("c_ipt_li_none");
				}
			},
			keyup:function(){
				var inputVal=$(this).val();
				inputVal = inputVal.replace(/\D/g,'');
				$(this).val(inputVal);
				if(inputVal != ""){
					$(this).parents(".c_ipt_li").removeClass("c_ipt_li_none");
				}else{
					$(this).parents(".c_ipt_li").addClass("c_ipt_li_none");
				}
			},
			blur:function(){
				var inputVal=$(this).val();
				if(inputVal&&inputVal.indexOf('￥')==-1){
					$(this).val('￥'+inputVal);
				}else{
					$(this).val(inputVal);
				}
			}
		})
		// 新增方法end 
	
	// 新增方法start
 	 orderDetail.choseIsOther = function(obj){
		if($(obj).hasClass("gray")){
			$(obj).removeClass("gray").addClass("org");
			// 是否有其他费用 用于提交判断 0 － 否 1 － 是
			$("#isOtherPrice").val("1");
			$("#otherMoney").show();
			$("#otherDes").show();
		}else{
			$(obj).removeClass("org").addClass("gray");
			// 是否有其他费用 用于提交判断 0 － 否 1 － 是
			$("#isOtherPrice").val("0");
			$("#otherMoney").hide();
			$("#otherDes").hide();
		}
	} 
	// 新增方法end
	
	//房东拒绝订单
	orderDetail.refuse = function(value){
		$.post(SERVER_CONTEXT+"orderland/43e881/refusedOrder",{"orderSn":orderSn,"paramValue":value},function(data){
			if(data.code == 0){
				window.location.href = SERVER_CONTEXT+"orderland/43e881/showDetail?orderSn="+orderSn;
			}else{
				showShadedowTips("操作失败",1);
			}
		},'json');
	}
	//房东接受订单
	orderDetail.accept = function(){
		$.post(SERVER_CONTEXT+"orderland/43e881/acceptOrder",{"orderSn":orderSn},function(data){
			if(data.code == 0){
				window.location.href = SERVER_CONTEXT+"orderland/43e881/showDetail?orderSn="+orderSn;
			}else{
				showShadedowTips("操作失败",1);
			}
		},'json');
	}
	//房东确认额外消费
	orderDetail.dosure = function(){
		var otherMoney = $.trim($("#otherMoneyValue").val());
		var paramValue = $.trim($("#otherMoneyDes").val());
		
		
		if(otherMoney != null && otherMoney != "" && otherMoney != undefined){
			otherMoney = otherMoney.replace("￥","");
			
			if(!isPInt(otherMoney) && parseInt(otherMoney) != 0){
				showShadedowTips("请输入整数",2);
				return;
			}
			//如果金额不为0  不校验金额
			if(parseInt(otherMoney) != 0 ){
				if(paramValue == null || paramValue == "" || paramValue == undefined){
					showShadedowTips("请填写原因",2);
					return;
				}else{
					if(otherMoney.length > 150){
						showShadedowTips("最大字数不超过150字",2);
						return;
					}
				}
			}
			
		}else{
			showShadedowTips("请输入金额",2);
			return;
		}
		makeSureBtnValue = 1;
		//过滤特殊字符
		paramValue = stripscript(paramValue);
		 //其他消费金额 和原因可以为空，如果填写金额需要填写原因
		$.post(SERVER_CONTEXT+"orderland/43e881/confirmOtherMoney",{"orderSn":orderSn,"otherMoney":otherMoney,"paramValue":paramValue},function(data){

			if(data.code == 0){
					//跳转刷新
					window.location.href = SERVER_CONTEXT+"orderland/43e881/showDetail?orderSn="+orderSn;
				}else{
					//金额超过限制
					showShadedowTips(data.msg,2);
				}
			makeSureBtnValue = 0;
		},'json');
	}
	
	//添加备注
	orderDetail.addRemark=function(){
		$.post(SERVER_CONTEXT+"orderland/43e881/checkRemarkCount",{"orderSn":orderSn},function(data){
			if(data.code == 0){
				window.location.href = SERVER_CONTEXT+"orderland/43e881/toOrderRemark?orderSn="+orderSn;
			}else{
				showShadedowTips(data.msg, 1);
			}
		},'json');
	}
	
	window.OrderDetail = orderDetail;
}(jQuery));
