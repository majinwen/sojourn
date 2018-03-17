/**
 * Created by zl
 */


$(function(){

	function updateExchangeMsg(){
		 var paramData= {"houseBaseFid":$("#houseFid").val(),"buildingNum":$("#buildingNum").val(), "unitNum":$("#unitNum").val(),"floorNum":$("#floorNum").val(),"houseNum":$("#houseNum").val(),
				 "orderType":$("#orderType").attr("data-value"),"homestayType":$("#homestayType").attr("data-value"),"depositRulesCode":$("#depositRules1").attr("data-value"),
				 "checkOutRulesCode":$("#checkOutRulesCode").attr("data-value"),"minDay":$("#minDay").attr("data-value"),"checkInTime":$("#checkInTime").attr("data-value"),
				 "checkOutTime":$("#checkOutTime").attr("data-value"),"sheetsReplaceRules":$("#sheetsReplaceRules").attr("data-value"),"houseDescEntity.houseBaseFid":$("#houseBaseFid").val(), "houseDescEntity.houseRules":$("#houseRules").val(),
				 "houseConfMsgEntity.houseBaseFid":$("#houseBaseFid").val(),"houseConfMsgEntity.dicCode":$("#depositRules1").attr("data-value"),"houseConfMsgEntity.dicVal":$("#depositRules2").attr("data-value")};
		 
       CommonUtils.ajaxPostSubmit("../saveExtrasInfo",paramData,function(data){
    	if(data.code==0){
    		window.location.href="/house/lanHouseList";
    	}else{
    		showConfirm(data.msg,"确定");
    	}
		});
	}
	
	$("#J_next").click(function(){
		if($(this).hasClass("disabled_btn")){
			return false;
		}
		updateExchangeMsg();
	});
	
	var temp_html = "",
	oDicCode = $("ul[name='houseConfList[0].dicCode']"),//押金规则 一级
	oDicVal = $("ul[name='houseConfList[0].dicVal']"),//押金规则 二级
	depositJson = depositRulesListJson ;


	//初始化配置项code
	var dicCode = function(){
		var defDeposit1Val=$("#depositRules1").attr("data-value");
		var defDeposit2Val=$("#depositRules2").attr("data-value");
		var defDeposit1Htm="",defDeposit2Htm="";
	    $.each(depositJson,function(i,deposit){
	    	var li =$("<li data-value='" + deposit.key + "'>" + deposit.text + "</li>");
	    	oDicCode.append(li);//添加一级列表
	    	if(typeof(defDeposit1Val)!= "undefined"){
		    	if(defDeposit1Val==deposit.key){
		    		$("#depositRules1").val(deposit.text);//设置一级已存的初始值
		    		
		    		if(typeof(deposit.subEnumVals) != "undefined" ){
		    			var depositRules2Html="";
		    			$.each(deposit.subEnumVals, function(i, subdeposit) {//添加二级菜单
		    				var subli =$("<li data-value='" + subdeposit.key + "'>" + subdeposit.text + "</li>");
		    				oDicVal.append(subli);//添加二级列表
		    				if(i==0){//选择一级时二级首项默认值
		    			    	depositRules2Html=subdeposit.text;
		    				}
		    				if(typeof(defDeposit2Val)!= "undefined" && defDeposit2Val!=""){
		    			    	if(defDeposit2Val==subdeposit.key){
		    			    		depositRules2Html=subdeposit.text;//设置已存的二级初始值
		    			    	} 
		    			    }
		    			});
		    			$("#depositRules2").val(depositRules2Html);
		    		}
		    		
		    	}
		    }
	    	
	    	if(typeof(deposit.subEnumVals) != "undefined" ){
	    		li.click(function(){//一级列表点击时添加二级菜单
	    			oDicVal.html("");
	    			$.each(deposit.subEnumVals, function(i, subdeposit) {
	    				var subli =$("<li data-value='" + subdeposit.key + "'>" + subdeposit.text + "</li>");
	    				if(i==0){//选择一级时二级首项默认值
	    					$("#depositRules2").val(subdeposit.text);
	    				}
	    				oDicVal.append(subli);//添加二级列表
	    				
	    				
	    			});
	    		});
	    	}
	    	
	    });
	  
	    
	    
	};
	
	dicCode();
  
});