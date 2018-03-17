 var conHeight = $(window).height()-$('.header').height()-$('.stateTips').height()-$('.calendarTop').height();

 $('#mainBox').css({'position':'relative','height':conHeight,'overflow':'hidden','margin-top':'.75rem'});
 $('#mainBox > div').css({'position':'absolute','width':'100%','padding-bottom':'100px'});

 myScroll = new BScroll('#mainBox',{click:true});
 document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);



 var oStateEvents=$('#stateEvents');
 var oModifyBtn=$('#modifyBtn');
 var oEventsBtn=$('#eventsBtn');
 var oEventsKeBtn=$('#eventsKeBtn');
 var oCalendarBox=$('#calendarBox');
 /*var setWeekPriceBtn=$("#setWeekPriceBtn");*/
 var tempId=[];
 var iPrice=0;

 var houseBaseFid = $('#houseBaseFid').val();
 var houseRoomFid = $('#houseRoomFid').val();
 var setSpecialPrice = $('#setSpecialPrice').val();
 var lockHouse = $('#lockHouse').val();
 var unlockHouse = $('#unlockHouse').val();
 var leaseCalendar = $('#leaseCalendar').val();
 var rentWay = $('#rentWay').val();
 var startDate = $('#startDate').val();
 var tillDate = $('#tillDate').val();
 /*var setWeekSpecialPrice = $("#setWeekSpecialPrice").val();*/

 var oMyCalendarStar=$('#myCalendarStar');
 var iMyCalendarStar=oMyCalendarStar.attr('data-val').split('.');

 for(var i=0; i<iMyCalendarStar[0]; i++){
 	oMyCalendarStar.find('i').eq(i).addClass('s');
 }

 if(iMyCalendarStar[1]>0){
 	oMyCalendarStar.find('i').eq(iMyCalendarStar[0]).addClass('h');
 }

// 交互修改
$('#calendarBox').delegate('td','click',function(){
	if($(this).text() == ""){
		return;
	}
	if(!$(this).hasClass('deprecated') && !$(this).hasClass('disabled') && !$(this).hasClass('pb')){
		if($('#calendarBox').find(".active").length != 0){
			checkCalendarTd();
			var calendarsIndex,trIndex ,tdIndex;
			var thisCalendarsIndex = $(this).parents(".calendars").index();
			var thisTrIndex = $(this).parents("tr").index();
			var thisTdIndex = $(this).index();
			var thisTime = Date.parse($(this).attr("id").replace('td_',''));
			var eTime = Date.parse(tempId[tempId.length -1].replace('td_',''));
			var sTime = Date.parse(tempId[0].replace('td_',''));

			var canChose = true;
			if(tempId.length > 1){
				if(thisTime < sTime){
					calendarsIndex = $('#'+tempId[tempId.length -1]).parents(".calendars").index();
					trIndex = $('#'+tempId[tempId.length -1]).parents("tr").index();
					tdIndex = $('#'+tempId[tempId.length -1]).index();
					canChose = true;
				}else if(sTime < thisTime && thisTime < eTime ){
					calendarsIndex = $('#'+tempId[0]).parents(".calendars").index();
					trIndex = $('#'+tempId[0]).parents("tr").index();
					tdIndex = $('#'+tempId[0]).index();
				}else if(sTime < thisTime && thisTime > eTime){
					calendarsIndex = $('#'+tempId[0]).parents(".calendars").index();
					trIndex = $('#'+tempId[0]).parents("tr").index();
					tdIndex = $('#'+tempId[0]).index();
					canChose = true;
				}else{
					canChose = false;
				}
			}else{
				if(sTime != thisTime){
					calendarsIndex = $('#'+tempId[0]).parents(".calendars").index();
					trIndex = $('#'+tempId[0]).parents("tr").index();
					tdIndex = $('#'+tempId[0]).index();
					canChose = true;
				}else{
					canChose = false;
				}
			}
			if(thisTime == sTime || thisTime == eTime){
				$(this).removeClass('active');
			}else{
				$(this).addClass('active');
			}

			if(canChose){
				if(calendarsIndex < thisCalendarsIndex){
					activeCalendarTd(calendarsIndex,thisCalendarsIndex,trIndex,thisTrIndex,tdIndex,thisTdIndex);
				}else if(calendarsIndex == thisCalendarsIndex){
					if(trIndex < thisTrIndex){
						activeCalendarTd(calendarsIndex,thisCalendarsIndex,trIndex,thisTrIndex,tdIndex,thisTdIndex);
					}else if(trIndex == thisTrIndex){
						if(tdIndex < thisTdIndex){
							activeCalendarTd(calendarsIndex,thisCalendarsIndex,trIndex,thisTrIndex,tdIndex,thisTdIndex);
						}else if(tdIndex > thisTdIndex){
							activeCalendarTd(thisCalendarsIndex,calendarsIndex,thisTrIndex,trIndex,thisTdIndex,tdIndex);
						}
					}else{
						activeCalendarTd(thisCalendarsIndex,calendarsIndex,thisTrIndex,trIndex,thisTdIndex,tdIndex);
					}
				}else{
					activeCalendarTd(thisCalendarsIndex,calendarsIndex,thisTrIndex,trIndex,thisTdIndex,tdIndex);
				}
			}
		}else{
			$(this).addClass('active');
		}

		if(!oStateEvents.is(':visible')){

			oStateEvents.show();
		}

			setCalendarTd(); //设置td样式
			checkCalendarTd();//统计选中的td--ID
		}
	});



	//layer.closeAll();


var submited = false;

	//修改价格的按扭
	oModifyBtn.click(function(){
		var tempIDtd = [];
		var hasRented=false;
		for(var i=0; i<tempId.length; i++){
			if(!$("#"+tempId[i]).hasClass("rented")){
				tempIDtd.push(tempId[i].replace('td_',''));
			}else{
				//$('#'+tempId[i]).removeClass('active');
				hasRented=true;
			}
		}
		if(tempIDtd.length==0){
			if(hasRented){
				showShadedowTips("已出租、设为已租的日期无法修改价格",2);
			}
			return;
		}
		var title ='<div class="setPrice"><div class="title">'+tempIDtd[0]+' 至 '+tempIDtd[tempIDtd.length-1]+'</div><div class="priceIptBox"><input id="priceIpt" type="tel" class="priceIpt" maxlength="5"><label>元/晚</label></div><div id="error"></div></div>';
		if(tempId.length==1){
			title ='<div class="setPrice"><div class="title">'+tempIDtd[0]+'</div><div class="priceIptBox"><input id="priceIpt" type="tel" class="priceIpt" maxlength="5"><label>元/晚</label></div><div id="error"></div></div>';
		}
		layer.open({
			content:title,
			btn: ['确认', '取消'],
			shadeClose: false,
			yes: function(){

				var iPrice = $('#priceIpt').val();

/*				if(iPrice==0){
					$('#rTips2').html('价格不能少于1元').show();
					return;
				}*/
				
				var reg = /^\d+?$/ ; 
				if (!reg.test(iPrice) || iPrice <=0) {
					showShadedowTips("请填写正确的价格，只能是整数",1);
					$('#priceIpt').val("");
					return false;
				}			
				
				var priceLow=$("#priceLow").val();
		      	if(parseInt(iPrice)<parseInt(priceLow)){
					$('#error').html('价格不能低于'+priceLow+'元').show();
					return false;
				}
		      	
		      	var priceHigh=$("#priceHigh").val();
		      	if(parseInt(iPrice)>parseInt(priceHigh)){
		      		$('#error').html('价格不能高于'+priceHigh+'元').show();
		      		return false;
		      	}

				var tempIDtd = [];
				var tipType="";
				for(var i=0; i<tempId.length; i++){
					if(!$("#"+tempId[i]).hasClass("rented")){
						tempIDtd.push(tempId[i].replace('td_',''));
					}else{
						tipType="已出租、设为已租的日期无法修改价格,";
					}
					
				}
				
				if(submited){
					showShadedowTips('正在提交，请稍后！',2);
					return;
				}
				
				submited = true;
				/*setTimeout(function(){
					submited = false;
				}, 5000);*/
				
				
//				showShadedowTips(tipType+'正在提交修改……',2);
					$.getJSON(setSpecialPrice, {
						houseBaseFid:houseBaseFid,
						houseRoomFid:houseRoomFid,
						rentWay:rentWay,
						setTime:tempIDtd.join(','),
						specialPrice: iPrice,
						startDate:calendar.iStartDate,
						endDate:calendar.iEndDate
							},function(data){
								//$('#rTips2').html('').hide();
								if(data.code == 0){ //修改成功了显示的
									if(tipType.length==0){
										showShadedowTips('修改成功！',2);
									}else{
										showShadedowTips(tipType+'其余已经修改成功！',2);
									}
									for(var i=0; i<tempId.length; i++){
										$('#'+tempId[i]).removeClass('active');
										if(!$('#'+tempId[i]).hasClass("rented")){
											$('#'+tempId[i]).find('i').html('¥'+iPrice);
										}
										
									}
									
									tempId=[];//将容器清空
									setTimeout(function(){
										window.location.reload(); 
									},1500);
								}else{
									showShadedowTips(data.msg,2);
								}
								
								if(data.status == 1){//修改失败的时候显示的
									showShadedowTips(data.message,2);
								}
                        		submited = false;
							});
                oStateEvents.hide();
		        //iPrice=0;
		    }, no: function(){
		        //layer.open({content: '你选择了取消', time: 1});
		        //iPrice=0;
		    }
		});
		
		
});



	
	// 出租状态
	oEventsBtn.click(function(){
		var tempIDtd = [];
		for(var i=0; i<tempId.length; i++){
			tempIDtd.push(tempId[i].replace('td_',''));
		} 
		if(tempIDtd.length==0){
			return;
		}
		var title='<div class="setEvents"><div class="title">'+tempIDtd[0]+' 至 '+tempIDtd[tempIDtd.length-1]+'</div><div id="error"></div></div>';
		if(tempId.length==1){
			title='<div class="setEvents"><div class="title">'+tempIDtd[0]+'</div><div id="error"></div></div>';
		}
		layer.open({
			content:title,
			btn: ['设为已租','设为可租'],
			title:' ',
		    closeBtn:1,
			shadeClose: false,
			yes:function(){
				
				//重新赋值，已租的不添加
				tempIDtd = [];
				for(var i=0; i<tempId.length; i++){
					if(!$("#"+tempId[i]).hasClass("rented")){
						tempIDtd.push(tempId[i].replace('td_',''));
					}else{
						$('#'+tempId[i]).removeClass('active');
					}
				}
				if(tempIDtd.length==0){
					showShadedowTips('当前日期已是设为已租状态！',2);
					return;
				}
				
				if(submited){
					showShadedowTips('正在提交，请稍后！',2);
					return;
				}
				
				submited = true;
				setTimeout(function(){
					submited = false;
				}, 5000);
				
		        //设为已租
		        $.getJSON(lockHouse,
		        {
		        	houseBaseFid:houseBaseFid,
		        	houseRoomFid:houseRoomFid,
		        	rentWay:rentWay,
		        	lockDateList:tempIDtd.join(','),
		        	specialPrice: iPrice
		
		        },function(data){
		        	if(data.code == 0){ //修改成功了显示的
		        		showShadedowTips('修改成功！',2);
		        		for(var i=0; i<tempId.length; i++){
		        			$('#'+tempId[i]).removeClass('active').addClass('rented');        					
		        		}
		        		oStateEvents.hide();
		        		tempId=[];//将容器清空
		        		setTimeout(function(){
		        			window.location.reload(); 
		        		},2000);
		        	}else{
		        		
		        		showShadedowTips(data.msg,2);
		        	}
		
		        	if(data.status == 1){//修改失败的时候显示的
		        		showShadedowTips(data.message,2);
		        	}
		        });
		        submited = false;
		    },
		    no:function(){
		    	
		    	//重新赋值，未出租的不添加
				tempIDtd = [];
				for(var i=0; i<tempId.length; i++){
					if($("#"+tempId[i]).hasClass("rented")){
						tempIDtd.push(tempId[i].replace('td_',''));
					}else{
						$('#'+tempId[i]).removeClass('active');
					}
				}
				if(tempIDtd.length==0){
					showShadedowTips('当前日期已是可租状态！',2);
					return;
				}
				
				if(submited){
					showShadedowTips('正在提交，请稍后！',2);
					return;
				}
				
				submited = true;
				setTimeout(function(){
					submited = false;
				}, 5000);
		    	
		    	$.getJSON(unlockHouse,
		    	{
		    		houseBaseFid:houseBaseFid,
		    		houseRoomFid:houseRoomFid,
		    		rentWay:rentWay,
		    		lockDateList:tempIDtd.join(','),
		    		specialPrice: iPrice
		
		    	},function(data){
					        	if(data.code == 0){ //修改成功了显示的
					        		showShadedowTips('修改成功！',2);
					        		for(var i=0; i<tempId.length; i++){
					        			$('#'+tempId[i]).removeClass('active').removeClass('rented');        					
					        		}
					        		oStateEvents.hide();
					        		tempId=[];//将容器清空
					        		setTimeout(function(){
					        			window.location.reload(); 
					        		},2000);
					        	}else{
					        		showShadedowTips(data.msg,2);
					        	}
		
					        	if(data.status == 1){//修改失败的时候显示的
					        		showShadedowTips(data.message,1);
					        	}
					        	
					        });
		    	submited = false;
		    }
})

});
 // 设置周末价格
 /*setWeekPriceBtn.click(function(){
	var s = getTimes();
	var e = tillDate;
	layer.open({
		content:'<div class="setWeekPrice"><div class="title">'+s+' 至 '+e+'</div><ul class="clearfix weekUl"><li><a><input type="hidden" value="5,6">周五/六</a></li><li><a><input type="hidden" value="6,7">周六/日</a></li><li><a><input type="hidden" value="5,6,7">周五/六/日</a></li></ul><div class="weekPriceIptBox"><input id="weekPriceIpt" type="tel" class="weekPriceIpt" maxlength="5"><label>元/晚</label></div><div id="error"></div></div>',
		btn: ['保存修改','取消'],
		shadeClose: false,
		yes:function(){
		    	// 保存修改
		    	// TODO:保存周末价格
			
				var weekLi = $(".clearfix.weekUl").find(".active");
		    	if(weekLi.length==0){
		    		$("#error").show().html('请选择星期 ！');
		    		return;
		    	}
		    	var weekInput = $(weekLi).find("a").find("input");
		    	if(!weekInput || weekInput.length==0){
		    		$("#error").show().html('请选择星期 ！');
		    		return;
		    	}
			
		    	var price = $("#weekPriceIpt").val();
		    	if(price == ""){
		    		$("#error").show().html('请先输入价格！');
		    		return;
		    	}
		    	
		    	var reg = /^\d+?$/ ; 
				if (!reg.test(price) || price <=0) {
					showShadedowTips("请填写正确的价格，只能是整数",1);
					$('#weekPriceIpt').val("");
					return false;
				}	
		    	
		    	var priceLow = $("#priceLow").val(); 
		    	if(parseInt(price) < parseInt(priceLow)){
		    		$("#error").show().html('价格不能低于'+priceLow+'元！');
		    		return;
		    	}
		    	
		    	var priceHigh = $("#priceHigh").val(); 
		    	if(parseInt(price) > parseInt(priceHigh)){
		    		$("#error").show().html('价格不能高于'+priceHigh+'元！');
		    		return;
		    	}
		    	$("#error").hide().html('');
		    	
		    	var weekPriceParam={};
		    	var rentWay=$("#rentWay").val();
		    	 
	    		weekPriceParam={ 
	    				houseBaseFid:$("#houseBaseFid").val(), 
	    				houseRoomFid:$("#houseRoomFid").val(),
	    				rentWay:$("#rentWay").val(),
	    				setTime:weekInput.val(),
	    				specialPrice:price,
	    				startDate:calendar.iStartDate,
	    				endDate:calendar.iEndDate
		    	}
	    		$("#weekPriceIpt").blur();
				$.getJSON($("#setWeekSpecialPrice").val(), weekPriceParam,function(data){
		        	//$('#rTips2').html('').hide();
		        	if(data.code == 0){ //修改成功了显示的
		        		showShadedowTips('修改成功！',3);
		        		setTimeout(function(){
		        			oStateEvents.hide();
		        			window.location.reload(); 
		        		},3000);
		        	}else{
		        		showShadedowTips(data.msg,2);
		        	}
 
		        }); 
		    },
		    no:function(){
		    	
		    }
		})
		//点击设置标签,查询周末特殊价格
		$('.weekUl li').each(function(){
			$(this).click(function(){ 
				$(this).addClass("active").siblings().removeClass("active");
				var i = $(this).index();
				var getPriceUrl = $("#getWeekSpecialPrice").val();
				$.getJSON(getPriceUrl,{"houseFid":houseBaseFid,"roomFid":houseRoomFid},function(data){
					if(data.code == 0){
						var tab = data.data.tab;
						if(i == tab){
							$("#weekPriceIpt").val(data.data.price);
						}else{
							$("#weekPriceIpt").val("");
						}
					}
				});
			})
		})
		$("#weekPriceIpt").on('input',function(){
			$("#error").hide().html('');
		})
 })*/

 function checkCalendarTd(){
 	tempId=[];
 	oCalendarBox.find('td').each(function(){
 		if($(this).hasClass('active')){
 			tempId.push($(this).attr('id'));
 		}
 	});
 	if(tempId.length==0){
 		oStateEvents.hide();
 	}
 }

 function getTimes(){
 	var now = new Date();
 	var m = now.getMonth()+1
 	m = m <10?'0'+m:m;
 	var d = now.getDate();
 	d = d <10?'0'+d:d;
 	var s = now.getFullYear()+'/'+m+'/'+d;
 	return s;
 }

 function activeCalendarTd(i1,i2,n1,n2,t1,t2){
 	var j1,j2;
 	var m1,m2;
 	var i = i1;
 	var flag = true;
 	$(".calendars td").each(function(){
 		$(this).removeClass("active")
 	})
 	do{
 		if(i1 == i2){
 			j1 = n1;
 			j2 = n2;
 		}else{
 			if(i == i1){
 				j1 = n1;
 				j2 = $(".calendars:eq("+i+")").find("tr").length;
 			}else if(i == i2){
 				j1 = 1,
 				j2 = n2;
 			}else{
 				j1 = 1;
 				j2 = $(".calendars:eq("+i+")").find("tr").length;
 			}
 		}
 		for(var j = j1; j <= j2; j ++){
 			if(i1 != i2 ){
 				if(flag){
 					m1 = t1;
 					m2 = $(".calendars:eq("+i+") tr:eq("+j+")").find("td").length;
 				}else{
 					m1 = 0;
 					if(j == j2){
 						m2 = t2;
 					}else{
 						m2 = $(".calendars:eq("+i+") tr:eq("+j+")").find("td").length;
 					}

 				}
 			}else{
 				if(j == n1){
 					m1 = t1;
 				}else{
 					m1 = 0;
 				}
 				if(j == n2){
 					m2 = t2;
 				}else{
 					m2 = $(".calendars:eq("+i+") tr:eq("+j+")").find("td").length;
 				}
 			}
 			for(var m = m1; m <= m2; m ++){
 				var obj = $(".calendars:eq("+i+") tr:eq("+j+")").find("td:eq("+m+")");
 				if(obj.text() != "" && !obj.hasClass("disabled") && !obj.hasClass("pb")){
 					obj.addClass("active");
 				}

 			}
 			flag = false;
 		}
 		do{
 			j++;
 		}while(j < j2)
 		i ++;


 	}
 	while(i <= i2)
 }


(function ($) {
	
	//定义对象
	var calendar = {
		iStartDate:startDate,
		iEndDate:"",
		iMyEndDate:"",
		iPage:0
	}

	
	/**
	 * 初始化
	 */
	 calendar.init=function(){

	 	calendar.iEndDate = calendar.checNextMonthDay(calendar.iStartDate,6);
	 	calendar.showCalendar(calendar.iStartDate,calendar.iEndDate);

	 },

	 calendar.showCalendar = function(start,end){
		//第一次加载数据 加载待处理数据
		$.getJSON(leaseCalendar,{
			houseBaseFid:houseBaseFid,
			houseRoomFid:houseRoomFid,
			rentWay:rentWay,
			startDate:start,
			endDate:end

		},function(data){
			if(data.code == 0){
				var list = data.data.list;
				calendar.iMyEndDate=data.data.tillDate;
				calendar.wirteDate(list);

			}else{

			}
		});
	}

	/**
	 * 计算当月1号是星期几
	 */
	 calendar.checWeek = function(date){		
	 	return new Date(date).getDay();
	 }

	/**
	 * 取得下一个月的最后一天
	 */
	 calendar.checNextMonthDay = function(date,n){
	 	var year = new Date(date).getFullYear();
	 	var month = new Date(date).getMonth()+1;
	 	var nextMonth = month+n;
	 	var nextDate='';
	 	var day=01;

	 	if(nextMonth==1 || nextMonth==3 || nextMonth==5 || nextMonth==7){
	 		day=31;
	 	}
	 	if( nextMonth==4 || nextMonth==6 || nextMonth==8){

	 		day=30;
	 	}

	 	if(nextMonth==2 ){
	 		if(year%4){
	 			day=29;
	 		}else{
	 			day=28;	
	 		}

	 	}
	 	if(nextMonth>12){
	 		nextDate = (year+1)+'-'+(nextMonth%12+1)+'-'+day;	
	 	}else{
	 		nextDate = year+'-'+nextMonth+'-'+day;
	 	}
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

	 	$.each(list,function(i,n){
	 		var week=calendar.checWeek(n.monthStr);
	 		var tempTd=[];
	 		var iDate=0;
			var forDateVal=''; //用于存判断日期大小的值
			var forDateValEnd=''; //用于存判断日期大小的值
			var pirce=0;
			_html += '<div class="calendars"><h2>'+n.monthStr+'</h2>'
			+'<table>'
			+'<tr>'
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

			$.each(n.calendarList,function(i,a){

				iDate = a.date.slice(8);
				forDateVal = calendar.comptime(a.date,myCurDay);
				forDateValEnd = calendar.comptime(a.date,calendar.iMyEndDate);


				if(forDateVal=='C'){
					iDate='今天';
				}

				pirce = a.price/100;

				if(forDateVal=='B' || forDateValEnd=='A'){
					tempTd.push('<td class="deprecated">'+iDate+'</i></td>');
				}else{

							if(a.status==0){//可租
								tempTd.push('<td id="td_'+a.date+'">'+iDate+'<i><s>¥</s>'+pirce+'</i></td>');
							}
							if(a.status==1){//已租
								tempTd.push('<td class="disabled" id="td_'+a.date+'">'+iDate+'<i><s>¥</s>'+pirce+'</i></td>');
							}
							if(a.status==2){//不可租
								tempTd.push('<td class="rented" id="td_'+a.date+'">'+iDate+'<i><s>¥</s>'+pirce+'</i></td>');
							}	
							if(a.status==3){//系统屏蔽
								tempTd.push('<td class="pb" id="td_'+a.date+'">'+iDate+'<i><s>¥</s>'+pirce+'</i></td>');
							}	
						}




					});


			for(var i=0; i<tempTd.length; i++){
				_html+=tempTd[i];

				if(i%7==6){
					_html+='</tr><tr>';
				}
			}



			_html+='</tr>'
			+'</table>'				
			+'</div>'







		});
 $("#calendarBox").append(_html);

 setTimeout(function () {
 	myScroll.refresh();
 },10)

 setCalendarTd();
}

calendar.comptime = function(start,end) {
	var beginTime = start;
	var endTime = end;
	var beginTimes = beginTime.substring(0, 10).split('-');
	var endTimes = endTime.substring(0, 10).split('-');


	beginTime = beginTimes[0] + '-' + beginTimes[1] + '-' + beginTimes[2];
	endTime = endTimes[0] + '-' + endTimes[1] + '-' + endTimes[2];
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