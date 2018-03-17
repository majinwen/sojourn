(function ($$) {
	var houseBaseSaveUrl = SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/saveHouseBaseInfo"
	var delRoomResUrl = SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/delRoomInfo";
	var toRoomDetailUrl = SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/toRoomDetail"
	var apartInfo = {};
	
	/**初始化*/
	apartInfo.init = function(){
		//添加房间按钮
		$$("#addRoomBtn").click(function(){
			apartInfo.addRoomBtnClick();
		});
		//删除按钮
		$$("span[name^='del_btn']").click(function(){
			var roomFid = $$(this).attr("roomFid");
			apartInfo.delBtnClick(roomFid)
		});
	}
	
	/** 点击删除房间按钮*/
	apartInfo.delBtnClick = function(fid){
		var params = {'roomFid':fid};
		CommonUtils.ajaxPostSubmit(delRoomResUrl,params,apartInfo.delBtnCallBack);
	}
	
	/** 点击删除房间按钮  回调函数*/
	apartInfo.delBtnCallBack = function(result){
		if(result.code == 0){
			showShadedowTips("删除成功",1);
		}else{
			showShadedowTips(result.msg,1);
		}
	}
	/** 点击添加房间按钮 */
	apartInfo.addRoomBtnClick = function(){
		var actualRoomNum = apartInfo.countNum('roomInfoList','li');
		var setRoomNum = $$("input[name='roomNum']").val();
		var mesg = '';
		if(!apartInfo.checkParam(setRoomNum)){
			mesg = '请选择房源户型后添加房间!';
			showShadedowTips(mesg,2);
			return;
		}
		if(!apartInfo.compare(actualRoomNum+1,setRoomNum)){
			mesg = '房间数量不能多于户型中室的数量!';
			showShadedowTips(mesg,2);
			return;
		}
		//跳转到房间详情页面
		var houseBaseFid = $$("input[name='houseBaseFid']").val();
		window.location.href = toRoomDetailUrl+"?houseBaseFid="+houseBaseFid;
	}
	
	/** 校验参数方法*/
	apartInfo.checkParam = function(param){
		if(param == "" || typeof(param) == undefined){
			return false;
		}else{
			return true;
		}
	}
	
	/** 触发校验方法*/
	apartInfo.complateBtnClick = function(roomNumber){
		//1先校验卧室数量与实际房间数量是否一致
		var actualRoomNum = apartInfo.countNum('roomInfoList','li');
		var setRoomNum = roomNumber;
		if(!apartInfo.compare(actualRoomNum,setRoomNum)){
			var mesg = '户型中室的数量小于房间数量，请删除房间后修改户型!';
			showShadedowTips(mesg,2);
			return false;
		}else{
			return true;
		}
		
	}
	
	/** 保存房型方法*/
	apartInfo.saveRoomInfo = function(params){
		CommonUtils.ajaxPostSubmit(houseBaseSaveUrl,params,apartInfo.complateBtnCallBack);
	}
	
	/** 保存房源基本信息后 回调函数*/
	apartInfo.complateBtnCallBack = function(result){
		var mesg = '';
		if(typeof(result) != undefined && result.code == 0){
			mesg = '保存成功';
			showShadedowTips(mesg,1);
			//2 修改隐藏域
			$$("input[name='roomNum']").val(result.data.houseBaseMsg.roomNum);
		}else{
			mesg = result.msg;
			showShadedowTips(mesg,1);
		}
	}
	
	/** 比较大小*/
	apartInfo.compare = function(first,second){
		var result = false;
		if(typeof(first) == undefined){
			return result;
		}
		if(typeof(second) == undefined){
			return result;
		}
		if(first <= second){
			result = true;
		}
		return result;
	}
	
	/** 计算 一个节点下元素的数目*/
	apartInfo.countNum = function(parentName,elementName){
		var elementSize = $$("#"+parentName).find(elementName).length;
		return elementSize;
	}
	
	apartInfo.init();
	window.ApartInfo = apartInfo;
	
}(jQuery));

function showShadedowTips(str,time){
    if(time){
        layer.open({
            content: str,
            shade:false,
            style: 'background:rgba(0,0,0,.5); color:#fff; border:none;line-height:1rem',
            time:time   
        });
    }else{
        layer.open({
            content: str,
             shade:false,
            style: 'background:rgba(0,0,0,.5); color:#fff; border:none;'

        });
    }
    
}


