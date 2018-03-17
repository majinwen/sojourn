/**
 * Created by mqzZoom on 16/8/8.
 */

$(function(){
	
	//回显
	(function(){
        $('textarea').each(function(){
            $(this).siblings('.J_tongji').find('span').eq(0).html($(this).val().length);

            if($(this).val().length>$(this).attr('data-total')){
                $(this).siblings('.J_tongji').find('span').eq(0).addClass('on');
            }
            else{
                $(this).siblings('.J_tongji').find('span').eq(0).removeClass('on');
            }
        });
    })();

    //输入框
    (function(){

        $('textarea').each(function(){

            $(this).bind('input propertychange',function(){

                $(this).siblings('.J_tongji').find('span').eq(0).html($(this).val().length);

                if($(this).val().length>$(this).attr('data-total')){
                    $(this).siblings('.J_tongji').find('span').eq(0).addClass('on');
                }
                else{
                    $(this).siblings('.J_tongji').find('span').eq(0).removeClass('on');
                }

            });

        });

    })();

    //帮助信息
    (function(){

        var aBox=$('.J_helpInfo');

        aBox.each(function(){

            var oWen=$(this).find('b');
            var oI=$(this).find('i');
            var oHelp=$(this).find('span').eq(1);

            oWen.mouseover(function(){
                oI.show();
                oHelp.show();
            });

            oWen.mouseout(function(){
                oI.hide();
                oHelp.hide()
            });

        });

    })();

    //所有下拉
    (function(){

        var oBox=$('#J_delegate');

        oBox.delegate('.J_xiala input','click',function(e){
            e.stopPropagation();
            $(this).next().next().show();
        });

        oBox.delegate('.J_xiala ul li','click',function(){
            var oIpt=$(this).parent().prev().prev();
            oIpt.val($(this).html());
            oIpt.attr('data-value',$(this).attr('data-value'));
        });

        oBox.click(function(){
            $('.J_xiala ul').hide();
        });

    })();

});

/**
 * Created by zl
 */


$(function(){
	var isClick = false;
	
	function updateExchangeMsg(){
		
		var curDeposit = $("#curDeposit").val();
		if(curDeposit!="0"){
			curDeposit = curDeposit.replace(",","");
		}
		
		var paramData= {"houseBaseFid":$("#houseFid").val(),"buildingNum":$("#buildingNum").val(), "unitNum":$("#unitNum").val(),"floorNum":$("#floorNum").val(),"houseNum":$("#houseNum").val(),
				"orderType":$("#orderType").attr("data-value"),"homestayType":$("#homestayType").attr("data-value"),"depositRulesCode":$("#depositRules1").attr("data-value"),
				"checkOutRulesCode":$("#checkOutRulesCode").attr("data-value"),"minDay":$("#minDay").attr("data-value"),"checkInTime":$("#checkInTime").attr("data-value"),
				"checkOutTime":$("#checkOutTime").attr("data-value"),"sheetsReplaceRules":$("#sheetsReplaceRules").attr("data-value"),"houseDescEntity.houseBaseFid":$("#houseBaseFid").val(), "houseDescEntity.houseRules":$.trim($("#houseRules").val()),
				"houseConfMsgEntity.houseBaseFid":$("#houseBaseFid").val(),"houseConfMsgEntity.dicCode":$("#curDeposit").attr("data-value"),"houseConfMsgEntity.dicVal":curDeposit,"roomFid":$("#roomFid").val()};
		
		CommonUtils.ajaxPostSubmit("../saveExtrasInfo",paramData,function(data){
			if(data.code==0){
				//window.location.href="/house/lanHouseList";
				window.location.href="/houseIssue/pic/"+$("#houseFid").val()+"?roomFid="+$("#roomFid").val();
			}else if(data.code == 2){
        		window.location.href = SSO_USER_LOGIN_URL+window.location.href;
        	}else{
				showConfirm(data.msg,"确定");
				isClick = false;
			}
		});
	}
	
	$("#J_next").click(function(){
		if(isClick){
			return false;
		}
		if($(this).hasClass("disabled_btn")){
			return false;
		}
		
		var regexp = new RegExp("^\[1-9][0-9]*$");
		
		//押金校验
		var depositMin = parseInt($("#depositMin").val());
		var depositMax = parseInt($("#depositMax").val());
		
		var curDeposit = $("#curDeposit").val();
		
		if(curDeposit==undefined||curDeposit==null||curDeposit==""){
			$("#curDeposit").siblings('.J_tishi_txt').html("请输入押金").show();
			return false;
		}
		if(curDeposit!="0"){
			curDeposit = curDeposit.replace(",","");
			var isPositiveInteger = regexp.test(curDeposit);
			if(!isPositiveInteger){
				$("#curDeposit").siblings('.J_tishi_txt').html("请填写正确押金").show();
				return false;
			} 
		}
	
		if(curDeposit<depositMin||curDeposit>depositMax){
			$("#curDeposit").siblings('.J_tishi_txt').html("押金范围在"+depositMin+"元到"+depositMax+"元").show();
			return false;
		}
		
		
		var houseRules = $("#houseRules").val();
		if($.trim(houseRules).length>=0 && $.trim(houseRules).length<50){
			$("#J_shouze_tishi").show();
			return;
		}else{
			$("#J_shouze_tishi").hide();
		}
		if($.trim(houseRules).length>0 && $.trim(houseRules).length>1000){
			$("#J_shouze_tishi").show();
			return;
		}else{
			$("#J_shouze_tishi").hide();
		}
		
		isClick = true;
		updateExchangeMsg();
	});
	
//	var temp_html = "",
//	oDicCode = $("ul[name='houseConfList[0].dicCode']"),//押金规则 一级
//	oDicVal = $("ul[name='houseConfList[0].dicVal']"),//押金规则 二级
//	depositJson = depositRulesListJson ;
//
//
//	//初始化配置项code
//	var dicCode = function(){
//		var defDeposit1Val=$("#depositRules1").attr("data-value");
//		var defDeposit2Val=$("#depositRules2").attr("data-value");
//		var defDeposit1Htm="",defDeposit2Htm="";
//	    $.each(depositJson,function(i,deposit){
//	    	var li =$("<li data-value='" + deposit.key + "'>" + deposit.text + "</li>");
//	    	oDicCode.append(li);//添加一级列表
//	    	if(typeof(defDeposit1Val)!= "undefined"){
//		    	if(defDeposit1Val==deposit.key){
//		    		$("#depositRules1").val(deposit.text);//设置一级已存的初始值
//		    		
//		    		if(typeof(deposit.subEnumVals) != "undefined" ){
//		    			var depositRules2Html="";
//		    			$.each(deposit.subEnumVals, function(i, subdeposit) {//添加二级菜单
//		    				var subli =$("<li data-value='" + subdeposit.key + "'>" + subdeposit.text + "</li>");
//		    				oDicVal.append(subli);//添加二级列表
//		    				if(i==0){//选择一级时二级首项默认值
//		    			    	depositRules2Html=subdeposit.text;
//		    				}
//		    				if(typeof(defDeposit2Val)!= "undefined" && defDeposit2Val!=""){
//		    			    	if(defDeposit2Val==subdeposit.key){
//		    			    		depositRules2Html=subdeposit.text;//设置已存的二级初始值
//		    			    	} 
//		    			    }
//		    			});
//		    			$("#depositRules2").val(depositRules2Html);
//		    		}
//		    		
//		    	}
//		    }
//	    	
//	    	if(typeof(deposit.subEnumVals) != "undefined" ){
//	    		li.click(function(){//一级列表点击时添加二级菜单
//	    			oDicVal.html("");
//	    			$.each(deposit.subEnumVals, function(i, subdeposit) {
//	    				var subli =$("<li data-value='" + subdeposit.key + "'>" + subdeposit.text + "</li>");
//	    				if(i==0){//选择一级时二级首项默认值
//	    					$("#depositRules2").val(subdeposit.text);
//	    					$("#depositRules2").attr('data-value',subdeposit.key);
//	    				}
//	    				oDicVal.append(subli);//添加二级列表
//	    				
//	    				
//	    			});
//	    		});
//	    	}
//	    	
//	    });
//	  
//	    
//	    
//	};
//	
//	dicCode();
  
});


































































