(function ($$) {
	var houseBaseSaveUrl = SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/deleteHouseRooms"
	var toRoomDetailUrl = SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/toRoomDetail"
	var apartInfo = {};
	
	/**初始化*/
	apartInfo.init = function(){
		// 添加房间按钮
		$$("#addRoomBtn").click(function(){
			apartInfo.addRoomBtnClick();
		});
		
		$$("#roomInfoList").find("li").each(function(){
			$$(this).click(function(){
				apartInfo.toRoomDetail(this);
			});
		});
	}
	
	/** 点击添加房间按钮 */
	apartInfo.addRoomBtnClick = function(){
		var actualRoomNum = apartInfo.countNum('roomInfoList','li');
		var setRoomNum = $$("input[name='roomNum']").val();
		var mesg = '';
		if(apartInfo.checkParam(setRoomNum)){
			mesg = '请先选择房源户型后再添加房间!';
			showShadedowTips(mesg,2);
			return;
		}
		if(!apartInfo.compare(actualRoomNum+1,setRoomNum)){
			mesg = '房间数量不能多于户型中室的数量!';
			showShadedowTips(mesg,2);
			return;
		}
		
		apartInfo.addRoomDom(1);
	}
	
	/**
	 * 跳转添加房间页面
	 */
	apartInfo.toRoomDetail = function(obj){
		var houseBaseFid = $$("input[name='houseBaseFid']").val();
		var rentWay = $$("input[name='rentWay']").val();
		var flag = $$("input[name='flag']").val();
		var roomFid = $$(this).val();
		window.location.href = toRoomDetailUrl+"?houseBaseFid="+houseBaseFid+"&rentWay="+rentWay+"&roomFid="+roomFid+"&flag="+flag;
	}
	
	
	/**
	 * 校验参数是否为空
	 * @param param
	 * @return true:参数为空 false:参数非空
	 */
	apartInfo.checkParam = function(param){
		return typeof(param) == 'undefined' || param == null || $$.trim(param).length == 0;
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
	
	/**
	 * 计算父元素下子元素数量
	 * 
	 * @param parentName 父元素名称
	 * @param elementName 子元素名称
	 * @return 子元素数量
	 */
	apartInfo.countNum = function(parentName,elementName){
		var elementSize = $$("#"+parentName).find(elementName).length;
		return elementSize;
	}
	
	/**
     * 减法数学运算
     * 
     * @param num1
     * @param num2
     * @return 
     */
	apartInfo.subtract = function(num1, num2){
    	return num1 - num2;
    }
    
	/**
     * 保存房源户型
     * 
     * @param params ajax提交参数
     * @param callbackParams 回调参数
     * @return
     */
	apartInfo.saveHouseTypeInFo = function(params, funcParam){
    	$$.ajax({
    	    url: houseBaseSaveUrl,
    	    dataType:"json",
    	    data: params,
    	    type: "POST",
    	    success: function (result) {
    	    	if(result.code === 0){
    	    		func(num);
				}else{
					
				}
    	    },
   	    });
    }
	
	/**
	 * 保存房源户型
	 * 
	 * @param params ajax提交参数
	 * @param func ajax成功执行方法
	 * @param num ajax成功执行方法参数
	 * @return
	 */
	apartInfo.saveHouseTypeCallBack = function(params, func, num){
		$$.ajax({
			url: houseBaseSaveUrl,
			dataType:"json",
			data: params,
			type: "POST",
			success: function (result) {
				if(result.code === 0){
					func(num);
				}else{
					showShadedowTips(result.msg,1);
				}
			},
		});
	}
    
    /**
     * 页面增加房间dom元素
     * 
     * @param num 需要增加的dom数量
     * @return
     */
	apartInfo.addRoomDom = function(num){
    	if(num <= 0){
    		return;
    	}
    	var size = ApartInfo.countNum("baseHouseList", "li");
    	var htmlString = "";
    	for (var int = 0; int < num; int++) {
    		size++;
    		htmlString += '<li class="mui-table-view-cell unsaved_mark">';
    		htmlString += 	'<div class="mui-slider-handle" onclick="">';
    		htmlString += 		'<span class="text">完善房间<span class="liIndex">'+size+'</span>的信息</span>';
    		htmlString += 		'<span class="icon_r icon"></span>';
    		htmlString += 	'</div>';
    		htmlString += '</li>';
		}
    	$$("#baseHouseList").append(htmlString).show();
    }
    
    /**
     * 页面删除房间dom元素
     * 
     * @param num 需要删除的dom数量
     * @return
     */
	apartInfo.delRoomDom = function(num){
    	if(num <= 0){
    		return;
    	}
    	var liObj = $$("#baseHouseList").find("li").last();
    	for (var int = 0; int < num; int++) {
			var tempObj = liObj.prev();
			liObj.remove();
			liObj = tempObj;
		}
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