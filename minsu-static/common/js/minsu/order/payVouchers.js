(function ($) {
	
	var payVouchers = {};
	
	/**
	 * 账单初始化
	 */
	payVouchers.init = function() {
		// 外部js调用
		laydate({
			elem : '#runTimeStart', // 目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式
			event : 'focus' // 响应事件。如果没有传入event，则按照默认的click
		});
		laydate({
			elem : '#runTimeEnd', // 目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式
			event : 'focus' // 响应事件。如果没有传入event，则按照默认的click
		});
	}
	

	/**
	 * 付款单查询参数
	 */
	payVouchers.payVouchersParam = function (params) {
		
		var runTimeStart  = $.trim($('#runTimeStart').val());
		var runTimeEnd =$.trim($('#runTimeEnd').val());
		if(runTimeStart!=null&&runTimeStart!=""&&runTimeStart!=undefined){
			runTimeStart = runTimeStart+" 00:00:00"
		}
		if(runTimeEnd!=null&&runTimeEnd!=""&&runTimeEnd!=undefined){
			runTimeEnd = runTimeEnd+" 23:59:59"
		}
	    return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
	        receiveName:$.trim($('#receiveName').val()),
	        receiveTel:$.trim($('#receiveTel').val()),
	        userName:$.trim($('#userName').val()),
	        userTel:$.trim($('#userTel').val()),
	        userUid:$.trim($('#userUid').val()),
	        pvSn:$.trim($('#pvSn').val()),
	        paymentStatus:$.trim($('#paymentStatus').val()),
	        orderSn:$.trim($('#orderSn').val()),
	        runTimeStart:runTimeStart,
	        runTimeEnd:runTimeEnd
    	};
	}
	
	/**
	 * 收款人角色
	 */
	payVouchers.receiveType = function(value, row, index){
        var receiveTypeStr= "*";
        if(value == 1){
        	receiveTypeStr= "房东";
        }
        if(value == 2){
        	receiveTypeStr= "租客";
        }
        return receiveTypeStr;
    }


	payVouchers.paymentType = function(value, row, index){
		var str= "*";
		if(value == 'yhfk'){
			str= "银行付款";
		}
		if(value == 'ylfh'){
			str= "原路返回";
		}
		if(value == 'account'){
			str= "账户空间";
		}
		return str;
	}
	
	/**
	 * 处理长文本显示
	 */
	payVouchers.formatBigContent = function(value, row, index){
		if(value==null){
			return "<p title='"+value+"'>-</p>";
		}
		if(value.length > 10){
		    return "<p title='"+value+"'>"+value.substring(0,10)+"...</p>";
		}
		return "<p title='"+value+"'>"+value+"</p>";
	}
	
	payVouchers.init();
	window.PayVouchers = payVouchers;
}(jQuery));