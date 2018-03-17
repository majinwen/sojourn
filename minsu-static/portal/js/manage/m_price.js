
var inDate = $('#inDate').val();
var outDate = $('#outDate').val();
var isRent;
var isFirst = true;
$(function(){
	var iWinHeight = $(window).height();
	var str = '';
	$(window).scroll(function(){
		var top = $(window).scrollTop()
		var i_top = top +iWinHeight;
		var _top = $('#loadMore').offset().top;
	        // 返回顶部
	        if(top >= iWinHeight){
	        	$("#goTop").show();
	        }else{
	        	$("#goTop").hide();
	        }
	        if(_top < i_top){
	        	var s_time = $("#startTimeSpan").attr("data-start");
	        	var e_time = $("#startTimeSpan").attr("data-end");
	        	isFirst = false;
	        	calendar.showCalendar(s_time,e_time);
	        }
	    })
$("#goTop").click(//定义返回顶部点击向上滚动的动画 
	function(){$('html,body').stop().animate({scrollTop:0},300); 
}).html("TOP");
     //设置弹层高度
     $(window).resize(function() {
     	countModalHeight();
     });
    //关闭弹层
    $('.modal-content .close').click(function() {
    	closeModalBox();
    });
    $("#modal").on('click', function() {
    	$(this).hide();
    })
    $('.modal-content').on('click', function(event) {
    	event.stopPropagation();
    })
    
    iconDefault();
    $("#startTime").datepicker({"minDate":inDate,"maxDate":outDate,onSelect:function(){
    	var time_str = $(this).val();
    	var _str = calendar.checNextWeekDay(time_str,7,'next');
    	var spanTime_str = time_str.split("-")[0]+"年"+time_str.split("-")[1]+"月"+time_str.split("-")[2]+"日 ～ "+_str.split("-")[0]+"年"+_str.split("-")[1]+"月"+_str.split("-")[2]+"日";
    	$("#startTimeSpan").html(spanTime_str);
    	$("#startTimeSpan").attr("data-start",time_str);
    	$("#startTimeSpan").attr("data-end",_str);
    	isFirst = true;
    	calendar.showCalendar(time_str,_str);
    	iconDefault();
    }});
    $(".prices_bar").delegate('.icon','click',function(){
    	var s_time,e_time;
    	var _time;
    	if(!$(this).hasClass("disabled")){
    		if($(this).hasClass("icon_prev")){
    			s_time= $("#startTimeSpan").attr("data-start");
    			_time =calendar.checNextWeekDay (s_time,7,'prev');
    			
    			if(timestampDate(_time) < timestampDate(inDate)){
    				s_time = inDate;
    				e_time = calendar.checNextWeekDay (inDate,7,'next');
    				
    			}else{
    				e_time = s_time;
    				s_time = _time;
    			}
    		}
    		if($(this).hasClass("icon_next")){
    			s_time= $("#startTimeSpan").attr("data-end");
    			_time =calendar.checNextWeekDay(s_time,7,'next');
    			
    			if(timestampDate(_time) > timestampDate(outDate)){
    				s_time = calendar.checNextWeekDay (outDate,7,'prev');;
    				e_time = outDate;
    			}else{
    				s_time = s_time;
    				e_time = _time;
    			}

    		}
    		$("#startTime").val(s_time);
    		var spanTime_str = s_time.split("-")[0]+"年"+s_time.split("-")[1]+"月"+s_time.split("-")[2]+"日 ～ "+e_time.split("-")[0]+"年"+e_time.split("-")[1]+"月"+e_time.split("-")[2]+"日";
    		$("#startTimeSpan").html(spanTime_str);
    		$("#startTimeSpan").attr("data-start",s_time);
    		$("#startTimeSpan").attr("data-end",e_time);
    		isFirst = true;
    		calendar.showCalendar(s_time,e_time);

    		iconDefault();
    	}
    })
$(".set-box").delegate("li","click",function(){
	$(this).addClass("current").siblings().removeClass("current");
	isRent = $(this).attr("data-rent");
	if(isRent == 0){
		$(".ipt-box-md").hide();
	}else if(isRent == 1){
		$(".ipt-box-md").show();
	}
})
// 日历
$('#priceTab').delegate('td','click',function(){
	if(!$(this).hasClass("un_rented") && !$(this).hasClass("have_rented")){
		showModalBox("rentBox");
		var _id = $(this).attr("id");
		$('#priceTab td').each(function(){
			$(this).removeClass("active");
		})
		$(this).addClass("active");
		if($(this).hasClass("can_rented")){
    			//可租 －设为已租
    			$(".set-box").find(".have_rented_li").addClass("current").siblings().removeClass("current");
    			isRent = 0;//出租方式 －－可租
    			$(".ipt-box-md").hide();
    		}else if($(this).hasClass("set_rented")){
				//设为已租 －可租
				$(".set-box").find(".can_rented_li").addClass("current").siblings().removeClass("current");
				isRent = 1;//出租方式 －－设为已租
				$(".ipt-box-md").show();
			}
			$("#price").val("");
			$("#strId").val(_id.split('_')[0]+'_'+_id.split('_')[1]+'_');
			infoDate(_id);
		}
		
	});
})

// 时间带入
function infoDate(id){
	var date = id.split("_")[2];
	$( "#startDate" ).val(date);
	$( "#endDate" ).val(date);
	$( "#startDate" ).datepicker({"minDate":inDate,"maxDate":outDate,onSelect:function(){
		compareDate();
	}});
	$( "#endDate" ).datepicker({"minDate":inDate,"maxDate":outDate,onSelect:function(){
		compareDate();
	}});
}
// 价格区间
function compareDate(){
	var endDate = $( "#endDate" ).val();
	var startDate = $( "#startDate" ).val();
	if(startDate != "" && endDate != ""){
		$( "#endDate" ).datepicker("option","minDate",startDate);
		if(startDate == endDate){
			$("#price").attr("placeholder","当晚价格");
		}else if(startDate != endDate){
			$("#price").attr("placeholder","每晚价格");
		}
	}
}
function timestampDate(date){  
	date = date.replace(/-/g,'/'); 
	return new Date(date).getTime()                                   ;
}
function savePrice(){
	iPrice = $("#price").val();
	if(iPrice != ""){
		iPrice = parseInt(iPrice,10);
		if(iPrice <=  60){
			showShadedowTips("价格不得低于60","3000");
			return;
		}
	}
	var endDate = $( "#endDate" ).val();
	var startDate = $( "#startDate" ).val();
	var strId = $("#strId").val();//id字符串拼接前缀strId_日期
	// isRent租住方式
	// TODO:ajax请求返回  tempId
	var tempId = ['td_0_2016-08-28','td_0_2016-08-29','td_0_2016-08-30'];
	for(var i=0; i<tempId.length; i++){
		if($('#'+tempId[i]).hasClass("have_rented")){
			showShadedowTips("已出租房源价格将不会被修改","1000");
		}else{
		    	if(isRent == 1){//可租
		    		$('#'+tempId[i]).removeClass("set_rented").addClass("can_rented");
		    		$('#'+tempId[i]).find('.price').html('¥'+iPrice);
				}else if(isRent == 0){//设为已租
					$('#'+tempId[i]).removeClass("can_rented").addClass("set_rented");
				}
			}

			$('#'+tempId[i]).removeClass('active');
		}
		closeModalBox();

	}
	// 页面加载完成判断左右按钮状态
	function iconDefault(){
		var s_date = $("#startTimeSpan").attr("data-start");
		var e_date = $("#startTimeSpan").attr("data-end");
		s_date = timestampDate(s_date);
		e_date = timestampDate(e_date);
		var _info = timestampDate(inDate);
		var _out = timestampDate(outDate);
		if(s_date <= _info){
			$(".icon_prev").addClass("disabled");
		}else{
			$(".icon_prev").removeClass("disabled");
		}
		if(e_date >= _out){
			$(".icon_next").addClass("disabled");
		}else{
			$(".icon_next").removeClass("disabled");
		}
		
	}
	
	(function ($) {
		
	//定义对象
	var calendar = {
		iStartDate:"",
		iEndDate:"",
		iPage:0
	}
	/**
	 * 初始化
	 */
	 calendar.init = function(){
	 	var myDate = new Date();
	 	var myYear = myDate.getFullYear();
	 	var myMoth = (myDate.getMonth()+1) < 10 ? '0'+(myDate.getMonth()+1) : (myDate.getMonth()+1);
	 	var myDate = myDate.getDate()< 10 ? '0'+myDate.getDate() : myDate.getDate();		  
	 	var myCurDay =myYear+'-'+myMoth+'-'+myDate;
	 	
	 	calendar.iStartDate = myCurDay;                                                                                                                                                                                                  
	 	calendar.iEndDate = calendar.checNextWeekDay(calendar.iStartDate,7,'next');
	 	var spanTime_str = calendar.iStartDate.split("-")[0]+"年"+calendar.iStartDate.split("-")[1]+"月"+calendar.iStartDate.split("-")[2]+'日 ~ '+calendar.iEndDate.split("-")[0]+"年"+calendar.iEndDate.split("-")[1]+"月"+calendar.iEndDate.split("-")[2]+"日";
	 	$("#startTimeSpan").html(spanTime_str)
	 	$("#startTimeSpan").attr("data-start",calendar.iStartDate);
	 	$("#startTimeSpan").attr("data-end",calendar.iEndDate);
	 	$("#startTime").val(calendar.iStartDate);
	 	calendar.showCalendar(calendar.iStartDate,calendar.iEndDate);
	 }

	 calendar.showCalendar = function(start,end){
		//第一次加载数据 加载待处理数据
		// $.ajax({
		// 	type:"post",
		// 	data:{houseBaseFid:houseBaseFid,monthStr:monthStr},
		// 	dataType:"json"
		// 	success:function(data){
		// 		if(data.code == 0){
				// calendar.iMyEndDate=data.data.tillDate;
				var list = [
				{
					"calendarList": [
					"2016-08-28",
					"2016-08-29",
					"2016-08-30",
					"2016-08-31",
					"2016-09-01",
					"2016-09-02",
					"2016-09-03",
					"2016-09-04"
					]
				},
				{
					"priceList": [
					{
						"houseFid": "",
						"houseAddress": "十里堡复式无印良品风单人间",
						"housePriceList": [
						{
							"price": "1200",
							"status": "0",
							"date": "2016-08-28"
						},
						{
							"price": "1200",
							"status": "1",
							"date": "2016-08-29"
						},
						{
							"price": "1200",
							"status": "2",
							"date": "2016-08-30"
						},
						{
							"price": "1200",
							"status": "0",
							"date": "2016-08-31"
						},
						{
							"price": "1200",
							"status": "0",
							"date": "2016-09-01"
						},
						{
							"price": "1100",
							"status": "0",
							"date": "2016-09-02"
						},
						{
							"price": "1100",
							"status": "0",
							"date": "2016-09-03"
						},
						{
							"price": "1100",
							"status": "0",
							"date": "2016-09-04"
						}
						]
					},
					{
						"houseFid": "",
						"houseAddress": "十里堡复式无印良品风单人间",
						"housePriceList": [
						{
							"price": "1200",
							"status": "0",
							"date": "2016-08-28"
						},
						{
							"price": "1200",
							"status": "1",
							"date": "2016-08-29"
						},
						{
							"price": "1200",
							"status": "2",
							"date": "2016-08-30"
						},
						{
							"price": "1200",
							"status": "1",
							"date": "2016-08-31"
						},
						{
							"price": "1200",
							"status": "0",
							"date": "2016-09-01"
						},
						{
							"price": "1100",
							"status": "0",
							"date": "2016-09-02"
						},
						{
							"price": "1100",
							"status": "0",
							"date": "2016-09-03"
						},
						{
							"price": "600",
							"status": "2",
							"date": "2016-09-04"
						}
						]
					},{
						"houseFid": "",
						"houseAddress": "十里堡复式无印良品风单人间",
						"housePriceList": [
						{
							"price": "1200",
							"status": "0",
							"date": "2016-08-28"
						},
						{
							"price": "1200",
							"status": "1",
							"date": "2016-08-29"
						},
						{
							"price": "1200",
							"status": "2",
							"date": "2016-08-30"
						},
						{
							"price": "1200",
							"status": "0",
							"date": "2016-08-31"
						},
						{
							"price": "1200",
							"status": "0",
							"date": "2016-09-01"
						},
						{
							"price": "1100",
							"status": "0",
							"date": "2016-09-02"
						},
						{
							"price": "1100",
							"status": "0",
							"date": "2016-09-03"
						},
						{
							"price": "600",
							"status": "0",
							"date": "2016-09-04"
						}
						]
					}
					]
				}
				]
				calendar.wirteDate(list);
			// }else{
				
			// }
		// })
}

	/**
	 * 计算星期几
	 */
	 calendar.checWeek = function(date){
	 	var _date = new Date(Date.parse(date.replace(/-/g, '/')));
	 	var _week = _date.getDay();
	 	var week = '';
	 	switch (_week){ 
	 		case 1 : 
	 		week = '周一';
	 		break; 
	 		case 2 : 
	 		week = '周二';
	 		break; 
	 		case 3 : 
	 		week = '周三';
	 		break; 
	 		case 4 : 
	 		week = '周四';
	 		break; 
	 		case 5 : 
	 		week = '周五';
	 		break; 
	 		case 6 : 
	 		week = '周六';
	 		break; 
	 		case 0 : 
	 		week = '周日';
	 		break; 
	 		default : break; 
	 	} 
	 	return week;
	 }

	/**
	 * 取得一周后的日期
	 */
	 calendar.checNextWeekDay = function(date,n,type){
	 	var year = new Date(date).getFullYear();
	 	var month = new Date(date).getMonth();
	 	var _date = new Date(date).getDate();
	 	var nextMonth = month+1;
	 	var _nextDate;
	 	var nextDate='';
	 	var day=0;
	 	var _day = 1;

	 	

	 	if(nextMonth==1 || nextMonth==3 || nextMonth==5 || nextMonth==7 || nextMonth==8 || nextMonth == 10 || nextMonth == 12){
	 		day=31;
	 	}
	 	if( nextMonth==4 || nextMonth==6 || nextMonth==9 || nextMonth == 11){

	 		day=30;
	 	}

	 	if(nextMonth==2 ){
	 		if(year%4){
	 			day=29;
	 		}else{
	 			day=28;	
	 		}

	 	}
	 	if(type == 'prev'){
	 		_nextDate = _date - n;
	 		if(_nextDate < 0){
	 			_day = (day + _nextDate);
	 			nextMonth--;

	 		}else{
	 			_day = _nextDate;
	 		}
	 	}
	 	if(type == 'next'){
	 		_nextDate = _date + n;
	 		if(_nextDate > day){
	 			_day = (_nextDate - day);
	 			nextMonth++;

	 		}else{
	 			_day = _nextDate;
	 		}
	 		
	 	}
	 	if(_day == 0){
	 		_day = 1;
	 	}
	 	_day = _day<10?'0'+_day:_day;
	 	if(nextMonth>12){
	 		nextDate = (year+1)+'-01-'+_day;	
	 	}else if (nextMonth <= 0){
	 		nextDate = (year-1)+'-12-'+_day;	
	 	}else{
	 		nextMonth = nextMonth<10?'0'+nextMonth:nextMonth;
	 		nextDate = year+'-'+nextMonth+'-'+_day;
	 	}
	 	return nextDate;
	 }
	/**
	 * 展示列表
	 */
	 calendar.wirteDate = function (list){
	 	var _html="";
	 	var tempTd=[];
	 	var iDate=0;
	 	var pirce=0;

	 	_html +='<tr>';
	 	if(isFirst){
	 		tempTd.push('<th>房间名称</th>');
		 	for(var i = 0 ; i < list[0].calendarList.length; i ++ ){
		 		var iDate = list[0].calendarList[i];
		 		var mDate = list[0].calendarList[i].split('-')[1]
		 		var dDate = list[0].calendarList[i].split('-')[2]
		 		tempTd.push('<th><dt>'+mDate+' - '+dDate+'</dt><dd>'+calendar.checWeek(iDate)+'</dd></th>');

		 	}
	 	}
	 	for(var i = 0 ; i < list[1].priceList.length; i ++){
	 		tempTd.push('<td><div class="title">'+list[1].priceList[i].houseAddress+'</div></td>');
	 		for(var j = 0 ; j < list[1].priceList[i].housePriceList.length ; j++){
	 			var id = list[1].priceList[i].housePriceList[j].date;
	 			var price = list[1].priceList[i].housePriceList[j].price;
	 			if(list[1].priceList[i].housePriceList[j].status == 0){//可租
	 				tempTd.push('<td id="td_'+i+'_'+id+'" class="can_rented"><div class="price">¥'+price+'</div></td>');
	 			}
	 			if(list[1].priceList[i].housePriceList[j].status == 1){//已租
	 				tempTd.push('<td id="td_'+i+'_'+id+'" class="have_rented"><div class="price">¥'+price+'</div></td>');
	 			}
	 			if(list[1].priceList[i].housePriceList[j].status == 2){//设为可租
	 				tempTd.push('<td id="td_'+i+'_'+id+'" class="set_rented"><div class="price">¥'+price+'</div></td>');
	 			}
	 			
	 		}
	 	}
	 	for(var i=0; i<tempTd.length; i++){
	 		_html+=tempTd[i];

	 		if(i%9==8){
	 			_html+='</tr><tr>';
	 		}
	 	}



	 	_html+='</tr>'

	 	if(isFirst){
	 		$("#priceTab").html(_html);
	 	}else{
	 		$("#priceTab").append(_html);
	 	}
	 	isFirst = false;
	 }


	 calendar.init();
	 window.calendar = calendar;

	}(jQuery));




