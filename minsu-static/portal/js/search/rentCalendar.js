(function ($) {
// 定义对象
	var calendar = {
			iStartDate:"",
			iEndDate:"",
			iMyEndDate:"",
			iPage:0
	}
	/**
	 * 初始化
	 */
	 calendar.init = function(){
		var _n = new Date();
	 	var _n_y = _n.getFullYear();
	 	var _n_m = _n.getMonth()+1;
	 	_n_m = _n_m<10?'0'+_n_m:_n_m;
	 	$("#monthBox").attr("data-month",_n_y+'-'+ _n_m).html(_n_y+'年'+ _n_m+'月');
	 	calendar.iStartDate =_n_y+'-'+ _n_m + '-01';
	 	
	 	calendar.iEndDate = calendar.checNextMonthDay(calendar.iStartDate,0);
	 	calendar.showCalendar(calendar.iStartDate,calendar.iEndDate);
	 }

	calendar.showCalendar = function(start,end){
		var data = {
		 		rentWay:$("#rentWay").val(),
		 		startDate:start,
		 		endDate:end,
		 		fid:$("#fid").val()
		 	};	
		 	$.getJSON("/house/leaseCalendar",data,function(data){
		 		if(data.code == 0){
		 			var list = data.data.list;
		 			var tillDate = data.data.tillDate;
		 			$("#outDate").val(tillDate);
		 			calendar.iMyEndDate = tillDate;
		 			var _n = new Date();
				 	var _n_y = _n.getFullYear();
				 	var _n_m = _n.getMonth()+1;
				 	var time = _n.getTime();
				 	var endTime = Date.parse(tillDate.replace(/-/g,"/"));
				 	if(time > endTime){
				 		showConfirm("该房源已超出出租期限，去其他房源看看吧！","确定");
				 		return;
				 	}
		 			iconDisabled(_n_y,_n_m);
		 			calendar.wirteDate(list);
		 		}
		 	});
	}

	/**
	 * 计算当月1号是星期几
	 */
	 calendar.checWeek = function(date){
	 	var _date = new Date(Date.parse(date.replace(/-/g, '/')));
	 	return _date.getDay();
	 }

	/**
	 * 取得下一个月的最后一天
	 */
	 calendar.checNextMonthDay = function(date,n){
		 date= date.replace(/-/g, '/');
	 	var year = new Date(date).getFullYear();
	 	var month = new Date(date).getMonth()+1;
	 	var nextMonth = month+n;
	 	var nextDate='';
	 	var day=0;

	 	if(nextMonth==1 || nextMonth==3 || nextMonth==5 || nextMonth==7 || nextMonth==8 || nextMonth==10 || nextMonth==12){
	 		day=31;
	 	}
	 	if( nextMonth==4 || nextMonth==6 || nextMonth==9 || nextMonth== 11){

	 		day=30;
	 	}
	 	if(nextMonth==2 ){
	 		if(year%4){
	 			day=29;
	 		}else{
	 			day=28;	
	 		}
	 	}
	 	nextMonth = nextMonth<10?'0'+nextMonth:nextMonth;
	 	nextDate = year+'-'+nextMonth+'-'+day;
	 	return nextDate;
	 }
	/**
	 * 展示列表
	 */
	 calendar.wirteDate = function (list){
	 	var _html="";
	 	var myDate = new Date();
	 	var myYear = myDate.getFullYear();
	 	var myMoth = (myDate.getMonth()+1) < 10 ? '0'+(myDate.getMonth()+1) : (myDate.getMonth()+1);
	 	var myDate = myDate.getDate()< 10 ? '0'+myDate.getDate() : myDate.getDate();		  
	 	var myCurDay =myYear+'-'+myMoth+'-'+myDate;
	 	var week=calendar.checWeek(list[0].monthStr+'-01');
	 	var tempTd=[];
	 	var iDate=0;
			var forDateVal=''; // 用于存判断日期大小的值
			var forDateValEnd=''; // 用于存判断日期大小的值
			var pirce=0;
			
			_html +='<tr>'
			+'<th>日</th>'
			+'<th>一</th>'
			+'<th>二</th>'
			+'<th>三</th>'
			+'<th>四</th>'
			+'<th>五</th>'
			+'<th>六</th>'
			+'</tr>'
			+'<tr>';
			for(var i=0; i<week; i++){
				tempTd.push('<td></td>');
			}
			for(var i = 0 ; i < list[0].calendarList.length; i ++ ){
				var a = list[0].calendarList[i];
				
				iDate = a.date.slice(8);
				forDateVal = calendar.comptime(a.date,myCurDay);
				forDateValEnd = calendar.comptime(a.date,calendar.iMyEndDate);

				pirce = a.price/100;
				if(forDateVal=='C'){
					if(a.status==0){// 可租
						tempTd.push('<td class="can_rented current_day" id="td_'+a.date+'" data-price="'+pirce+'"><div class="dates">'+iDate+'</div></td>');
					}
					if(a.status==1){// 已租
						tempTd.push('<td class="set_rented current_day" id="td_'+a.date+'" data-price="'+pirce+'"><div class="dates">'+iDate+'</div></td>');
					}
					if(a.status==2){// 设为已租
						tempTd.push('<td class="set_rented current_day" id="td_'+a.date+'" data-price="'+pirce+'"><div class="dates">'+iDate+'</div></td>');
					}
				}else if(forDateVal=='B' || forDateValEnd=='A'){
					tempTd.push('<td class="un_rented" id="td_'+a.date+'" data-price ="'+pirce+'"><div class="dates">'+iDate+'</div></td>');
	
				}else{
					if(a.status==0){// 可租
						tempTd.push('<td class="can_rented" id="td_'+a.date+'" data-price ="'+pirce+'"><div class="dates">'+iDate+'</div></td>');
					}
					if(a.status==1){// 已租
						tempTd.push('<td class="set_rented" id="td_'+a.date+'" data-price ="'+pirce+'"><div class="dates">'+iDate+'</div></td>');
					}
					if(a.status==2){// 设为已租
						tempTd.push('<td class="set_rented" id="td_'+a.date+'" data-price ="'+pirce+'"><div class="dates">'+iDate+'</div></td>');
					}
				}
			}

				for(var i=0; i<tempTd.length; i++){
					_html+=tempTd[i];

					if(i%7==6){
						_html+='</tr><tr>';
					}
				}
					_html+='</tr>'
					
					$("#calendarTab").html(_html);
				}

				calendar.comptime = function(start,end) {
					var beginTime = start.replace(/-/g, '/');
					var endTime = end.replace(/-/g, '/');
					var a = (Date.parse(endTime) - Date.parse(beginTime))/3600/1000;
					if (a < 0) {
						return 'A';
					} else if (a > 0) {
						return 'B';
					} else if (a == 0) {
						return 'C';
					} else {
						return 'exception'
					}
				}

				calendar.init();
				window.calendar = calendar;

	}(jQuery));



