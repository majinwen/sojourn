(function ($) {
	var cancleOrder = {};
	
	var cancleOrderParam={
			orderSn:$("#orderSn").val(),
			cancelReason:"",
			penaltyMoney:0,
			isTakeLandlordComm:0,
			isReturnCleanMoney:0,
			isReturnTonightRental:0
	};
	
	var curLastMoney = parseFloat($("#lastMoney").val());
	
	//初始化页面
	cancleOrder.init = function(){

		var orderSn = $("#orderSn").val();
		CommonUtils.ajaxPostSubmit("order/findCancleOrderInfo", {"orderSn":orderSn}, function(data){
			if(data.code == 0){
				var cancleOrderVo = data.data.cancleOrderVo;
				if(cancleOrderVo){
					var orderInfoVo = cancleOrderVo.orderInfoVo;
					$("#rentalMoneyConsume").val(Math.floor(cancleOrderVo.rentalMoneyConsume)/100);
					$("#userCommMoneyConsume").val(Math.floor(cancleOrderVo.userCommMoneyConsume)/100);
					$("#cleanMoneyConsume").val(Math.floor(cancleOrderVo.cleanMoneyConsume)/100);
					$("#discountMoney").val(Math.floor(cancleOrderVo.discountMoney)/100);
					
					//剩余金额
					$("#lastMoney").val(Math.floor(cancleOrderVo.lastMoney)/100);
					$("#realLastMoney").val(Math.floor(cancleOrderVo.realLastMoney)/100);
					$("#payMoney").val(Math.floor(cancleOrderVo.payMoney)/100);
					$("#couponMoneyConsume").val(Math.floor(cancleOrderVo.couponMoneyConsume)/100);
					if(orderInfoVo){
						$("#rentalMoneyAll").val(Math.floor(orderInfoVo.rentalMoneyAll)/100);
						$("#depositMoneyAll").val(Math.floor(orderInfoVo.depositMoneyAll)/100);
						$("#userCommMoney").val(Math.floor(orderInfoVo.userCommMoney)/100);
						$("#cleanMoney").val(Math.floor(orderInfoVo.cleanMoney)/100);
						$("#couponMoneyAll").val(Math.floor(orderInfoVo.couponMoneyAll)/100);
                        $("#actMoneyAll").val(Math.floor(orderInfoVo.actMoneyAll)/100);
						$("#realPayMoney").val(Math.floor(orderInfoVo.payMoney)/100);
					}
					$("#switchLanMoneyCur").val(cancleOrderVo.switchLanMoney);
					$("#switchTenCurDayCur").val(cancleOrderVo.switchTenCurDay);
					$("#switchCleanFeeCur").val(cancleOrderVo.switchCleanFee);
					$("#curDay").val(cancleOrderVo.curDay);
					$("#curDayMoney").val(Math.floor(cancleOrderVo.curDayMoney)/100);
					$("#curDayMoneyV").val(Math.floor(cancleOrderVo.curDayMoney)/100);
					$("#commissionRateLandlord").val(cancleOrderVo.commissionRateLandlord);
					var isHascurDayMoney = cancleOrderVo.isHascurDayMoney
					$("#isHascurDayMoney").val(isHascurDayMoney);
/*房东取消订单——首晚房费或者100元*/
					$("#switchFirstNightMoney").val(cancleOrderVo.switchFirstNightMoney);
					$("#switchOneHundred").val(cancleOrderVo.switchOneHundred);
					$("#checkInTime").val(cancleOrderVo.startTime);
/*房东取消订单——首晚房费或者100元*/
					
					var str = "是";
					if(isHascurDayMoney == 1){
						 str = "否";
					}
					$("#isHascurDayMoneyV").val(str);
					cancleOrder.setrefundSelect();
					
					
				}
			
			}else{
				alert(data.msg);
			}
		});
		
		
		//协商取消  功能方法
		cancleOrder.consultationCancel = function(){
            var cancelType = $("#cancelType").val();
			var cancelReason = $("#cancelReason").val();
			var penaltyMoney =$("#penaltyMoney").val();
			var isTakeLandlordComm = 0;
			var isReturnCleanMoney = 0;
			var isReturnTonightRental = 0;
			if(cancelReason == null||cancelReason==""||cancelReason==undefined){
				alert("请输入备注");
				return ;
			}
			if(cancelReason.length>450){
				alert("备注字数太长,最多450字");
				return ;
			}
			if(penaltyMoney == null||penaltyMoney==""||penaltyMoney==undefined){
				alert("违约金错误");
				return ;
			}
			if(penaltyMoney>curLastMoney){
				alert("违约金不能超过剩余客服可支配金额");
				return ;
			}
			 penaltyMoney = parseFloat(penaltyMoney);
			
			if ($("#isTakeLandlordComm").is(":checked")) { 
				 isTakeLandlordComm = 1; 
			} 
			if ($("#isReturnCleanMoney").is(":checked")) { 
				isReturnCleanMoney = 1; 
			} 
			if ($("#isReturnTonightRental").is(":checked")) { 
				isReturnTonightRental = 1; 
			} 
			
			cancleOrderParam.isReturnCleanMoney = isReturnCleanMoney;
			cancleOrderParam.isReturnTonightRental = isReturnTonightRental;
			cancleOrderParam.isTakeLandlordComm = isTakeLandlordComm;
			
			cancleOrderParam.penaltyMoney = parseInt(penaltyMoney); 
			cancleOrderParam.cancelReason = cancelReason;
			cancleOrderParam.cancelType = cancelType;
			CommonUtils.ajaxPostSubmit("order/cancelOrderNegotiate", cancleOrderParam, function(data){
				if(data.code == 0){
					$.callBackParent("order/toCancelOrderList",true,saveColumnCityCallback);
				}else{
					alert(data.msg);
				}
			})
			
		};
		
		//提交取消订单
		$("#cancleOrder").click(function(){
			var cancelType = $("#cancelType").val();
			if(cancelType==37){
				cancleOrder.consultationCancel();
				return;
			}
/*新增加  房东取消订单   参数     开始*/
			var cancelType = $("#cancelType").val();
			var cancelReasonCode = $("#cancelReasonCode").val();
			var isTakeFirstNightMoney=0;
			var isTakeOneHundred=0;
			var isCancelAngel=0;
			var isAddSystemEval=0;
			var isUpdateRankFactor=0;
			var isShieldCalendar=0;
			var isGiveCoupon=0;
/*新增加     房东取消订单    参数    结束*/
			
			var cancelReason = $("#cancelReason").val();
			var penaltyMoney =$("#penaltyMoney").val();
			var isTakeLandlordComm = 0;
			var isReturnCleanMoney = 0;
			var isReturnTonightRental = 0;
			if(cancelReason == null||cancelReason==""||cancelReason==undefined){
				alert("请输入备注");
				return ;
			}
			if(cancelReason.length>450){
				alert("备注字数太长,最多450字");
				return ;
			}
			if(penaltyMoney == null||penaltyMoney==""||penaltyMoney==undefined){
				alert("违约金错误");
				return ;
			}
			if(penaltyMoney>curLastMoney){
				alert("违约金不能超过剩余客服可支配金额");
				return ;
			}
			 penaltyMoney = parseFloat(penaltyMoney);
			
			if ($("#isTakeLandlordComm").is(":checked")) { 
				 isTakeLandlordComm = 1; 
			} 
			if ($("#isReturnCleanMoney").is(":checked")) { 
				isReturnCleanMoney = 1; 
			} 
			if ($("#isReturnTonightRental").is(":checked")) { 
				isReturnTonightRental = 1; 
			} 
			
/*新增加    房东取消订单       7项惩罚措施是否选中     开始*/
			if ($("#isTakeFirstNightMoney").is(":checked")) { 
				isTakeFirstNightMoney = 1; 
			} 
			if ($("#isTakeOneHundred").is(":checked")) { 
				isTakeOneHundred = 1; 
			} 
			if ($("#isCancelAngel").is(":checked")) { 
				isCancelAngel = 1; 
			} 
			if ($("#isAddSystemEval").is(":checked")) { 
				isAddSystemEval = 1; 
			} 
			if ($("#isUpdateRankFactor").is(":checked")) { 
				isUpdateRankFactor = 1; 
			} 
			if ($("#isShieldCalendar").is(":checked")) { 
				isShieldCalendar = 1; 
			} 
			if ($("#isGiveCoupon").is(":checked")) { 
				isGiveCoupon = 1; 
			} 
/*新增加  房东取消订单       惩罚措施是否选中  结束*/
			
			cancleOrderParam.isReturnCleanMoney = isReturnCleanMoney;
			cancleOrderParam.isReturnTonightRental = isReturnTonightRental;
			cancleOrderParam.isTakeLandlordComm = isTakeLandlordComm;
			
			cancleOrderParam.penaltyMoney = parseInt(penaltyMoney); 
			cancleOrderParam.cancelReason = cancelReason;
//新增加   房东取消订单   将惩罚措施赋值给请求参数中    开始
			cancleOrderParam.cancelType=cancelType;
			cancleOrderParam.cancelReasonCode=cancelReasonCode;
			cancleOrderParam.isTakeFirstNightMoney=isTakeFirstNightMoney;
			cancleOrderParam.isTakeOneHundred=isTakeOneHundred;
			cancleOrderParam.isCancelAngel=isCancelAngel;
			cancleOrderParam.isAddSystemEval=isAddSystemEval;
			cancleOrderParam.isUpdateRankFactor=isUpdateRankFactor;
			cancleOrderParam.isShieldCalendar=isShieldCalendar;
			cancleOrderParam.isGiveCoupon=isGiveCoupon;
			cancleOrderParam.isEdit=isEdit;
//新增加   房东取消订单   将惩罚措施赋值给请求参数中    结束


			CommonUtils.ajaxPostSubmit("order/cancelOrderNegotiate", cancleOrderParam, function(data){
				if(data.code == 0){
					$.callBackParent("order/toCancelOrderList",true,saveColumnCityCallback);
				}else{
					alert(data.msg);
				}
			})
			
		});
		//保存商机刷新父页列表
		function saveColumnCityCallback(panrent){
			panrent.refreshData("listTable");
		}
		//计算打款费用
		$("#jiSuanBut").click(function(){
			
			//协商违约金
			var penaltyMoney = $("#penaltyMoney").val();
			//剩余现金金额
			var lastMoney =$("#lastMoney").val();
			var realLastMoney =$("#realLastMoney").val();
			var isTakeLandlordComm = 0;
			var isReturnCleanMoney = 0;
			var isReturnTonightRental = 0;
			var toPlatform = 0;//平台佣金
			var toLandlordMoney = 0;//给房东打款
			var toTenlantMoney = 0;//给房客打款
			var commissionRateLandlord =  parseFloat($("#commissionRateLandlord").val());//房东佣金比例
			var curDayMoney = parseFloat($("#curDayMoney").val()); //当天价格
			
			var isHascurDayMoney = $("#isHascurDayMoney").val(); //剩余金额是否包含当天房租
			var cleanMoney = parseFloat($("#cleanMoney").val());
			if(penaltyMoney == null||penaltyMoney ==""|| penaltyMoney==undefined){
				alert("违约金错误");
				return ;
			}
		
			if(lastMoney == null||lastMoney ==""|| lastMoney==undefined){
				alert("剩余现金金额错误");
				return ;
			}
			if(realLastMoney == null||realLastMoney ==""|| realLastMoney==undefined){
				alert("剩余现金金额错误");
				return ;
			}
			penaltyMoney = parseFloat(penaltyMoney);
			lastMoney = parseFloat(lastMoney);
			realLastMoney = parseFloat(realLastMoney);
			
			if ($("#isTakeLandlordComm").is(":checked")) { 
				 isTakeLandlordComm = 1; 
			} 
			if ($("#isReturnCleanMoney").is(":checked")) { 
				isReturnCleanMoney = 1; 
			} 
			if ($("#isReturnTonightRental").is(":checked")) { 
				isReturnTonightRental = 1; 
			} 
			
			var  switchTenCurDay= $("#switchTenCurDayCur").val();
			var  switchCleanFeeCur= $("#switchCleanFeeCur").val();
			curLastMoney = lastMoney;
			if(penaltyMoney>lastMoney){
				alert("违约金不能超过剩余客服可支配金额");
				 $("#penaltyMoney").val("")
				return ;
			}
			if(switchTenCurDay == 110){
               //不包含 下午
               if(isHascurDayMoney == 1&&isReturnTonightRental== 1){ 
            	   realLastMoney = cancleOrder.accAdd(realLastMoney,curDayMoney);
               }
			}

			if(switchCleanFeeCur == 110){
        	   if(isReturnCleanMoney == 1){
        		   realLastMoney = cancleOrder.accAdd(realLastMoney,cleanMoney);
        	   }
			}
			
			
			toLandlordMoney =cancleOrder.accAdd(toLandlordMoney,penaltyMoney);
			if(isTakeLandlordComm == 1){
				toPlatform =Math.floor((toLandlordMoney*commissionRateLandlord) * 100) / 100 ;
				toLandlordMoney = toLandlordMoney - toPlatform;
			}
			toTenlantMoney =  cancleOrder.accSub(realLastMoney,penaltyMoney); 
			var realPayMoney = parseFloat($("#realPayMoney").val());
			if(toTenlantMoney>realPayMoney){
				toTenlantMoney = realPayMoney;
			}
			$("#toLandlordMoney").val(toLandlordMoney);
			$("#toPlatform").val(toPlatform);
			$("#toTenlantMoney").val(toTenlantMoney);
		})
		
		
	}
	
	//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。  
	//调用：accAdd(arg1,arg2)  
	//返回值：arg1加上arg2的精确结果  
	cancleOrder.accAdd = function (arg1,arg2){  
	    var r1,r2,m;  
	    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}  
	    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}  
	    m=Math.pow(10,Math.max(r1,r2))  
	    return (arg1*m+arg2*m)/m  
	}
	
	//说明：javascript的减法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的减法结果。  
	//调用：accSub(arg1,arg2)  
	//返回值：arg1减上arg2的精确结果  
	cancleOrder.accSub = function (arg1,arg2){      
	    return cancleOrder.accAdd(arg1,-arg2);  
	}
	
	//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。  
	//调用：accMul(arg1,arg2)  
	//返回值：arg1乘以arg2的精确结果  
	cancleOrder.accMul = function(arg1,arg2)  
	{  
	    var m=0,s1=arg1.toString(),s2=arg2.toString();  
	    try{m+=s1.split(".")[1].length}catch(e){}  
	    try{m+=s2.split(".")[1].length}catch(e){}  
	    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)  
	}
	
    /**
     * 初始化 退费选择选项
     */
	cancleOrder.setrefundSelect = function(){
		
		 var  switchLanMoney= $("#switchLanMoneyCur").val();
		 var  switchTenCurDay= $("#switchTenCurDayCur").val(); 
		 var  switchCleanFee= $("#switchCleanFeeCur").val();
		 var  curDay= $("#curDay").val();
		
		  var _html ="";
		  $("#refundSelect").empty();
		  
		  _html +="<label class='col-sm-1 control-label text-center'>退费选项:</label>";
		  _html +="<div class='col-sm-1'>";
		  _html +="<div class='checkbox i-checks'>";
		  _html +="<label>";
		  if(switchLanMoney == 100){
			  _html +="<input type='checkbox' id='isTakeLandlordComm' checked='checked' disabled='disabled' name='switchLanMoney' value=''> <i></i>收取房东违约金服务费";
		  }else if(switchLanMoney == 101){
			  _html +="<input type='checkbox'  id='isTakeLandlordComm'  readonly=disabled='disabled' name='switchLanMoney' value=''> <i></i>收取房东违约金服务费";
		  }else if(switchLanMoney == 111){
			  _html +="<input type='checkbox'  id='isTakeLandlordComm' checked='checked'  name='switchLanMoney' value=''> <i></i>收取房东违约金服务费";
		  }else{
			  _html +="<input type='checkbox'   id='isTakeLandlordComm' name='switchLanMoney' value=''> <i></i>收取房东违约金服务费";
		  }
			  
		  
		  _html +="</label>";
		  _html +="</div>";
		  _html +="</div>";
		  /*_html +="<span class='help-block m-b-none'></span>";*/
	      _html +="<div class='col-sm-3'>";
		  _html +="<div class='checkbox i-checks'>";
		  _html +="<label>";
		  
		  if(switchTenCurDay == 100){
			  _html +="<input type='checkbox' id='isReturnTonightRental' checked='checked' disabled='disabled' name='switchTenCurDay' value=''> <i></i>退还房客当天("+curDay+")房租 (注：包括退还当天服务费)";
		  }else if(switchTenCurDay == 101){
			  _html +="<input type='checkbox' id='isReturnTonightRental'  disabled='disabled' name='switchTenCurDay' value=''> <i></i>退还房客当天("+curDay+")房租 (注：包括退还当天服务费)";
		  }else if(switchTenCurDay == 111){
			  _html +="<input type='checkbox' id='isReturnTonightRental' checked='checked'  name='switchTenCurDay' value=''> <i></i>退还房客当天("+curDay+")房租 (注：包括退还当天服务费)";
		  }else{
			  _html +="<input type='checkbox' id='isReturnTonightRental'  name='switchTenCurDay' value=''> <i></i>退还房客当天("+curDay+")房租 (注：包括退还当天服务费)";
		  }
	      _html +="</label>";
		  _html +="</div>";
		  _html +=" </div>";
		/*  _html +="<span class='help-block m-b-none'></span>";*/
		  _html +=" <div class='col-sm-2'>";
		  _html +="<div class='checkbox i-checks'>";
		  _html +="<label>";
		  if(switchCleanFee == 100){
			  _html +="<input type='checkbox' id='isReturnCleanMoney' checked='checked' disabled='disabled' name='switchCleanFee' value=''> <i></i>退还清洁费";
		  }else if(switchCleanFee == 101){
			  _html +="<input type='checkbox' id='isReturnCleanMoney' disabled='disabled' name='switchCleanFee' value=''> <i></i>退还清洁费";
		  }else if(switchCleanFee == 111){
			  _html +="<input type='checkbox' id='isReturnCleanMoney' checked='checked'  name='switchCleanFee' value=''> <i></i>退还清洁费";
		  }else{
			  _html +="<input type='checkbox' id='isReturnCleanMoney' name='switchCleanFee' value=''> <i></i>退还清洁费";
		  }
		  _html +="</label>";
		  _html +="</div>";
		  _html +="</div>";
		  _html +="<span class='help-block m-b-none '></span>";
	
		  $("#refundSelect").html(_html);
		  $("#refundSelect").show();
		  $("#jiSuanBut").show();
	}
	
	$("#cancelType").change(function(){
		var val = $(this).val();
		if(val == 38){
			$("#penaltyMoney").val(0);
			document.getElementById("penaltyMoney").readOnly=true
			$("#five_div").show();
			//房东取消惩罚措施初始化
			cancleOrder.setlandlordPunishment();
			cancleOrder.setotherFivePunish();
		}else if(val == 37){
			$("#penaltyMoney").val(0);
			$("#five_div").hide();
			$("#landlordPunishment").empty();
			$("#otherFivePunish").empty();
			document.getElementById("penaltyMoney").readOnly=false	
		}
	});
	
	var isEdit = 0;
	$("#edit_btn").click(function(){
		isEdit = 1;
		$(".edit_dis").removeAttr("disabled");
	});

	/*房东取消订单惩罚措施*/
	cancleOrder.setlandlordPunishment = function(){ 
		
		 var  switchFirstNightMoney= $("#switchFirstNightMoney").val();
		 var  switchOneHundred= $("#switchOneHundred").val(); 
		
		  var _html ="";
		  $("#landlordPunishment").empty();
/* 0：首晚房费";*/		  
		  _html +="<label class='col-sm-1 control-label text-center'>房东处罚:</label>";
		  _html +="<div class='col-sm-3'>";
		  _html +="<div class='checkbox i-checks'>";
		  _html +="<label>";
		  if(switchFirstNightMoney == 1){
			  _html +="<input class='edit_dis' type='checkbox'  id='isTakeFirstNightMoney' checked='checked' disabled='disabled' name='isTakeFirstNightMoney' value=''> <i></i>在下笔订单中收取本订单首晚房费金额";
		  }else if(switchFirstNightMoney == 0){
			  _html +="<input class='edit_dis' type='checkbox'   id='isTakeFirstNightMoney' disabled='disabled' name='isTakeFirstNightMoney' value=''> <i></i>在下笔订单中收取本订单首晚房费金额";
		  }
		  _html +="</label>";
		  _html +="</div>";
		  _html +="</div>";
		  
/* 0：100元罚款";*/	
	      _html +="<div class='col-sm-3'>";
		  _html +="<div class='checkbox i-checks'>";
		  _html +="<label>";
		  
		  if(switchOneHundred == 1){
			  _html +="<input class='edit_dis' type='checkbox' id='isTakeOneHundred' checked='checked' disabled='disabled' name='isTakeOneHundred' value=''> <i></i>收取100元取消费用";
		  }else if(switchOneHundred == 0){
			  _html +="<input class='edit_dis' type='checkbox' id='isTakeOneHundred'  disabled='disabled' name='isTakeOneHundred' value=''> <i></i>收取100元取消费用";
		  }
	      _html +="</label>";
		  _html +="</div>";
		  _html +=" </div>";
		  _html +="<span class='help-block m-b-none '></span>";
			
			
		  $("#landlordPunishment").html(_html);
		  $("#landlordPunishment").show();
		  

		$(".edit_dis").click(function(){
				var name = $(this).attr("name");
				if( name == "isTakeOneHundred" || name=="isTakeFirstNightMoney"){
					if(name == "isTakeOneHundred"){
						$("#isTakeFirstNightMoney").attr("checked", false);  
					}
					if(name == "isTakeFirstNightMoney"){
						$("#isTakeOneHundred").attr("checked", false);  
					}
				}
			});
	}
	
	cancleOrder.setotherFivePunish = function(){ 
		  var  switchFirstNightMoney= $("#switchFirstNightMoney").val();
		  var  switchOneHundred= $("#switchOneHundred").val();
		  var  switchCancelAngel= $("#switchCancelAngel").val();
		  var _html ="";
		  $("#otherFivePunish").empty();
/* 1：取消天使房东";   自己单独一行*/
		  //_html +="<label class='col-sm-1 control-label text-center' id='editId'>编辑:</label>";
		  _html +="<div class='row'>";
		  _html +=" <div class='col-sm-6'>";
		  _html +="<div class='checkbox i-checks'>";
		  _html +="<label>";
		  if(switchCancelAngel == 1){
		      _html +="<input class='edit_dis' type='checkbox' id='isCancelAngel' checked='checked' disabled='disabled' name='isCancelAngel' value=''> <i></i>取消天使房东资格(已申请通过的取消资格，未申请通过的不予通过)";
		  }else if(switchCancelAngel == 0){
		     _html +="<input class='edit_dis' type='checkbox' id='isCancelAngel' disabled='disabled' name='isCancelAngel' value=''> <i></i>取消天使房东资格(已申请通过的取消资格，未申请通过的不予通过)";
		  }
		  _html +="</label>";
		  _html +="</div>";
		  _html +="</div>";
		  _html +="</div>";
		  
/*2：房源列表页增加“房东取消订单记录”    自己单独一行;*/
		  /*_html +="<div class='row'>";
		  _html +=" <div class='col-sm-2'>";
		  _html +="<div class='checkbox i-checks'>";
		  _html +="<label>";
		  _html +="<input class='edit_dis' type='checkbox' id='isAddSystemEval' checked='checked' disabled='disabled' name='isAddSystemEval' value=''> <i></i>房源列表页增加“房东取消订单记录”";

		  _html +="</label>";
		  _html +="</div>";
		  _html +="</div>";
		  _html +="</div>";*/
	
/* 3：更细排序因子" 以下三项在一行;*/
		  _html +="<div class='row'>";//更新排序因子一行结束
		  _html +=" <div class='col-sm-1'>";
		  _html +="<div class='checkbox i-checks'>";
		  _html +="<label>";
		  _html +="<input class='edit_dis' type='checkbox' id='isUpdateRankFactor' checked='checked' disabled='disabled' name='isUpdateRankFactor' value=''> <i></i>更细排序因子";
		 
		  _html +="</label>";
		  _html +="</div>";
		  _html +="</div>";
	
/* 4：屏蔽日期";*/
		  _html +="<label class='col-sm-1 control-label text-center'></label>";
		  _html +=" <div class='col-sm-1'>";
		  _html +="<div class='checkbox i-checks'>";
		  _html +="<label>";
		   //点击编辑按钮，判断权限  TODO
		  _html +="<input class='edit_dis' type='checkbox' id='isShieldCalendar' checked='checked' disabled='disabled' name='isShieldCalendar' value=''> <i></i>屏蔽日期";
		 
		  _html +="</label>";
		  _html +="</div>";
		  _html +="</div>";
	
/* 5：给房客赠送取消优惠券";*/
		  _html +="<label class='col-sm-1 control-label text-center'></label>";
		  _html +=" <div class='col-sm-1'>";
		  _html +="<div class='checkbox i-checks'>";
		  _html +="<label>";
		   //点击编辑按钮，判断权限  TODO
		  _html +="<input class='edit_dis' type='checkbox' id='isGiveCoupon' checked='checked' disabled='disabled' name='isGiveCoupon' value=''> <i></i>给房客赠送取消优惠券";
		 
		  _html +="</label>";
		  _html +="</div>";
		  _html +="</div>";
          _html +="</div>";//更新排序因子一行结束
          _html +="<span class='help-block m-b-none '></span>";
	
		 $("#otherFivePunish").html(_html);
		 $("#otherFivePunish").show();
}	
	

	
	cancleOrder.init();
	window.CancleOrder = cancleOrder;
}(jQuery));
