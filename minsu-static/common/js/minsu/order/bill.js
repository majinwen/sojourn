(function ($) {
	
	var bill = {};
	
	/**
	 * 账单初始化
	 */
	bill.init = function(){
		//外部js调用
		   laydate({
		      elem: '#actualStartTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		  }); 
		   laydate({
			      elem: '#actualEndTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			  }); 
	}
	
	/**
	 * 收款单查询参数
	 */
	bill.paymentParam= function (params) {
		
		var actualStartTime  = $.trim($('#actualStartTime').val());
		var actualEndTime =$.trim($('#actualEndTime').val());
		
		if(actualStartTime!=null&&actualStartTime!=""&&actualStartTime!=undefined){
			actualStartTime = actualStartTime+" 00:00:00"
		}
		if(actualEndTime!=null&&actualEndTime!=""&&actualEndTime!=undefined){
			actualEndTime = actualEndTime+" 23:59:59"
		}
	    return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
	        userName:$.trim($('#userName').val()),
	        userTel:$.trim($('#userTel').val()),
	        userUid:$.trim($('#userUid').val()),
	        fid:$.trim($('#fid').val()),
	        paymentStatus:$.trim($('#paymentStatus').val()),
	        paymentType:$.trim($('#paymentType').val()),
	        sourceType:$.trim($('#sourceType').val()),
	        orderSn:$.trim($('#orderSn').val()),
	        actualStartTime:actualStartTime,
	        actualEndTime:actualEndTime
    	};
	}

	/**
	 * 收入查询参数
	 */
	bill.incomeParam = function (params) {
		
		var actualStartTime  = $.trim($('#actualStartTime').val());
		var actualEndTime =$.trim($('#actualEndTime').val());
		
		if(actualStartTime!=null&&actualStartTime!=""&&actualStartTime!=undefined){
			actualStartTime = actualStartTime+" 00:00:00"
		}
		if(actualEndTime!=null&&actualEndTime!=""&&actualEndTime!=undefined){
			actualEndTime = actualEndTime+" 23:59:59"
		}
	    return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
	        userName:$.trim($('#userName').val()),
	        userTel:$.trim($('#userTel').val()),
	        userUid:$.trim($('#userUid').val()),
	        fid:$.trim($('#fid').val()),
	        incomeType:$.trim($('#incomeType').val()),
	        orderSn:$.trim($('#orderSn').val()),
	        actualStartTime:actualStartTime,
	        actualEndTime:actualEndTime
    	};
	}
	
	bill.init();
	window.Bill = bill;
}(jQuery));