(function ($) {
	var saveRoomInfoUrl = SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/saveRoomInfo";
	var saveTmpRoomInfoUrl = SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/saveTmpInf";
	var toBedListUrl=SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/toListBed";
	var toApartInfoUrl=SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/houseApartment";
	var toListInfoUrl = SERVER_CONTEXT +"houseInput/"+LOGIN_UNAUTH+"/goToHouseUpdate"
	var roomInfo = {};
	/**
	 * 初始化
	 */
	roomInfo.init = function(){
		//===========================================================================//
		//保存按钮
		$("#saveRoomInfoBtn").click(function(){
			if(roomInfo.checkFormParam()){
				roomInfo.saveRoomInfo();	
			}
			
		});
		//添加床铺按钮
		$("#toBedListBtn").click(function(){
			if(roomInfo.checkToParam()){
				roomInfo.toBedListBtn();
			}
		});
		
		//========================修改进来页面使用的方法================================//
		
		//保存按钮
		$("#roommateInfoBtn").click(function(){
			if(roomInfo.roommateCheckSaveParam()){
				roomInfo.roommateInfo();	
			}
			
		});
		
		//添加床铺按钮
		$("#roommateToBedListBtn").click(function(){
			if(roomInfo.roommateCheckToParam()){
				roomInfo.roommateToBedListBtn();
			}
		});
		
		
	}
	//=============================================================================//
	/** 设置select选择框 为value的值选中*/
	roomInfo.selectedDefault = function(selectName,value,showName){
		$("#"+selectName+" option[value='"+value+"']").attr("selected","selected");
		$("#"+showName).val($("#"+selectName+" option[value='"+value+"']").html());
	}
	
	/**跳转到床铺列表页面 触发函数*/
	roomInfo.toBedListBtn = function(){
		var params = $("#roomInfoDetail").serialize();
		CommonUtils.ajaxPostSubmit(saveTmpRoomInfoUrl,params,roomInfo.toBedListBtnCallBack);
	}
	
	/**跳转到床铺列表页面 回调函数*/
	roomInfo.toBedListBtnCallBack = function(result){
		if(result.code == 0){
			 var houseBaseFid = $("#houseBaseFid").val();
			 var roomFid = result.data.houseRoomMsg.fid;
			 var pageType = 1;
			 var flag=$("#flag").val();
			 window.location.href = toBedListUrl+"?houseBaseFid="+houseBaseFid+"&roomFid="+roomFid+"&pageType="+pageType+"&flag="+flag;
		 }else{
			 showShadedowTips(result.msg,1);
		 }
	}
	
	/**保存房间基本信息到数据库*/
	roomInfo.saveRoomInfo = function(){
		var params = $("#roomInfoDetail").serialize();
		CommonUtils.ajaxPostSubmit(saveRoomInfoUrl,params,roomInfo.saveRoomInfoCallBack);
	}
	
	/**保存房间信息 回调函数*/
	roomInfo.saveRoomInfoCallBack = function(result){
	 if(result.code == 0){
	     showShadedowTips("保存成功",1);
	     var houseBaseFid = $("#houseBaseFid").val();
	     var flag=$("#flag").val();
	     window.location.href = toApartInfoUrl+"?houseBaseFid="+houseBaseFid+"&flag="+flag;
	 }else{
		 showShadedowTips(result.msg,1);
	 }
	}
	
	/**表单提交 验证*/
	roomInfo.checkFormParam = function(){
		var result = false;
		var roomName = $("#roomName").val();
        if(!roomInfo.checkParam(roomName)){
            showShadedowTips("请填写房间名称",1);
            return result;
        }
        var roomArea = $("#roomArea").val();
        if(!roomInfo.checkParam(roomArea)){
            showShadedowTips("请填写房间面积",1);
            return result;
        }
        if(isNaN(roomArea)){
        	showShadedowTips("房间面积只能为数字",1);
        	return result
        }
        
       if(rentWay == 1){
        	var isToilet = $('#isToiletSelect option:selected').val();
            if(!roomInfo.checkParam(isToilet)){
                showShadedowTips("请选择是否独立卫生间",1);
                return result;
            }
        	var roomPrice = $("#roomPrice").val();
            if(!roomInfo.checkParam(roomPrice)){
                showShadedowTips("请输入房间价格",1);
                return result;
            }
            if(isNaN(roomPrice)){
            	showShadedowTips("房间价格只能为数字",1);
            	return result
            }
            var priceLimit=$("#priceLimit").val();
            //判断最低价格
            if(parseInt(roomPrice)<parseInt(priceLimit)){
            	showShadedowTips("房源价格不能低于"+priceLimit+"元",1);
                return result;
            }
            var checkInLimit = $('#checkInLimitSelect option:selected').val();
            if(!roomInfo.checkParam(checkInLimit)){
                showShadedowTips("请选择最大入住人数",1);
                return result;
            }
        }
        
       var isSetBed = $("#isSetBed").val();
        if(isSetBed == 0){
            showShadedowTips("请添加床铺",1);
            return result;
        }
       
        result = true;
        return result;
    }



    /**表单提交 验证*/
    roomInfo.checkToParam = function(){
        var result = false;
        var roomName = $("#roomName").val();
        if(!roomInfo.checkParam(roomName)){
            showShadedowTips("请填写房间名称",1);
            return result;
        }
        var roomArea = $("#roomArea").val();
        if(!roomInfo.checkParam(roomArea)){
            showShadedowTips("请填写房间面积",1);
            return result;
        }
        if(isNaN(roomArea)){
            showShadedowTips("房间面积只能为数字",1);
            return result
        }

        if(rentWay == 1){
            //var isToilet = $('#isToiletSelect option:selected').val();
            var isToilet = $('#isBathroomList').val();
            if(!roomInfo.checkParam(isToilet)){
                showShadedowTips("请选择是否独立卫生间",1);
                return result;
            }
            var roomPrice = $("#roomPrice").val();
            if(!roomInfo.checkParam(roomPrice)){
                showShadedowTips("请输入房间价格",1);
                return result;
            }
            if(isNaN(roomPrice)){
                showShadedowTips("房间价格只能为数字",1);
                return result
            }
            var priceLimit=$("#priceLimit").val();
            //判断最低价格
            if(parseInt(roomPrice)<parseInt(priceLimit)){
            	showShadedowTips("房源价格不能低于"+priceLimit+"元",1);
                return result;
            }
            //var checkInLimit = $('#checkInLimitSelect option:selected').val();
            var checkInLimit = $('#checkInLimit').val();
            if(!roomInfo.checkParam(checkInLimit)){
                showShadedowTips("请选择最大入住人数",1);
                return result;
            }
        }
        result = true;
        return result;
    }
    
	//=============================================================================//
	/**roommate跳转到床铺列表页面 触发函数*/
	roomInfo.roommateToBedListBtn = function(){
		var params = $("#roomInfoDetail").serialize();
		
		CommonUtils.ajaxPostSubmit(saveTmpRoomInfoUrl,params,roomInfo.roommateToBedListBtnCallBack);
	}
	
	roomInfo.roommateToBedListBtnCallBack = function(result){
		
		if(result.code == 0){
			 var houseBaseFid = $("#houseBaseFid").val();
			 var roomFid = result.data.houseRoomMsg.fid;
			 var pageType = 2;
			 var flag=$("#flag").val();
			 window.location.href = toBedListUrl+"?houseBaseFid="+houseBaseFid+"&roomFid="+roomFid+"&pageType="+pageType+"&flag="+flag;	
		 }else{
			 showShadedowTips(result.msg,1);
		 }
	}
	
	/**保存房间基本信息到数据库*/
	roomInfo.roommateInfo = function(){
		var params = $("#roomInfoDetail").serialize();
		CommonUtils.ajaxPostSubmit(saveRoomInfoUrl,params,roomInfo.roommateInfoCallBack);
	}
	
	/**保存房间信息 回调函数*/
	roomInfo.roommateInfoCallBack = function(result){
	 if(result.code == 0){
	     showShadedowTips("保存成功",1);
	     var rentWay = $("#rentWay").val();
	     var houseBaseFid = $("#houseBaseFid").val();
	     var roomFid = $("#roomFid").val();
	    window.location.href = toListInfoUrl+"?houseBaseFid="+houseBaseFid+"&houseRoomFid="+roomFid+"&rentWay="+rentWay;
	 }else{
		 showShadedowTips(result.msg,1);
	 }
	}
	
    /**表单提交 验证*/
    roomInfo.roommateCheckToParam = function(){
        var result = false;
        var roomName = $("#roomName").val();
        if(!roomInfo.checkParam(roomName)){
            showShadedowTips("请填写房间名称",1);
            return result;
        }
        var roomArea = $("#roomArea").val();
        if(!roomInfo.checkParam(roomArea)){
            showShadedowTips("请填写房间面积",1);
            return result;
        }
        if(isNaN(roomArea)){
            showShadedowTips("房间面积只能为数字",1);
            return result
        }

        //var isToilet = $('#isToiletSelect option:selected').val();
        var isToilet = $('#isBathroomList').val();
        if(!roomInfo.checkParam(isToilet)){
            showShadedowTips("请选择是否独立卫生间",1);
            return result;
        }
        var roomPrice = $("#roomPrice").val();
        if(!roomInfo.checkParam(roomPrice)){
            showShadedowTips("请输入房间价格",1);
            return result;
        }
        if(isNaN(roomPrice)){
            showShadedowTips("房间价格只能为数字",1);
            return result
        }
        var priceLimit=$("#priceLimit").val();
        //判断最低价格
        if(parseInt(roomPrice)<parseInt(priceLimit)){
        	showShadedowTips("房源价格不能低于"+priceLimit+"元",1);
            return result;
        }
        //var checkInLimit = $('#checkInLimitSelect option:selected').val();
        var checkInLimit = $('#checkInLimit').val();
        if(!roomInfo.checkParam(checkInLimit)){
            showShadedowTips("请选择最大入住人数",1);
            return result;
        }
        
       result = true;
        return result;
    }
    
    /**表单提交 验证*/
    roomInfo.roommateCheckSaveParam = function(){
        var result = false;
        var roomName = $("#roomName").val();
        if(!roomInfo.checkParam(roomName)){
            showShadedowTips("请填写房间名称",1);
            return result;
        }
        var roomArea = $("#roomArea").val();
        if(!roomInfo.checkParam(roomArea)){
            showShadedowTips("请填写房间面积",1);
            return result;
        }
        if(isNaN(roomArea)){
            showShadedowTips("房间面积只能为数字",1);
            return result
        }

        var isToilet = $('#isToiletSelect option:selected').val();
        if(!roomInfo.checkParam(isToilet)){
            showShadedowTips("请选择是否独立卫生间",1);
            return result;
        }
        var roomPrice = $("#roomPrice").val();
        if(!roomInfo.checkParam(roomPrice)){
            showShadedowTips("请输入房间价格",1);
            return result;
        }
        if(isNaN(roomPrice)){
            showShadedowTips("房间价格只能为数字",1);
            return result
        }
        var priceLimit=$("#priceLimit").val();
        //判断最低价格
        if(parseInt(roomPrice)<parseInt(priceLimit)){
        	showShadedowTips("房源价格不能低于"+priceLimit+"元",1);
            return result;
        }
        var checkInLimit = $('#checkInLimitSelect option:selected').val();
        if(!roomInfo.checkParam(checkInLimit)){
            showShadedowTips("请选择最大入住人数",1);
            return result;
        }
        
        var isSetBed = $("#roommateIsSetBed").val();
        if(isSetBed == 0){
            showShadedowTips("请添加床铺",1);
            return result;
        }
        
        result = true;
        return result;
    }
    
  //======================================================================//  
    /**校验参数为空*/
	roomInfo.checkParam = function(param){
		if(param == "" || typeof(param) == undefined){
			return false;
		}else{
			return true;
		}
	}
	roomInfo.init();
	window.RoomInfo = roomInfo;
	
}(jQuery));
