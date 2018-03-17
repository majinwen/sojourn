Date.prototype.Format = function (fmt) { //author: meizz 
	var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    	if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//分页信息
var page = 0;

$(function(){
	setStar();
	$(".slider").slide({
		mainCell:".bd",
		switchLoad:"_src",
		prevCell:".prev",
		nextCell:".next"
	})
	$('.outslide').slide({
		mainCell:".bd",
		titCell:".hd li",
		switchLoad:"_src",
		prevCell:".prev",
		nextCell:".next",
		trigger:"click",
		pnLoop:false,
		startFun:function(i,c){
			var w = c*150;
			$('.outslide .hd').css({"width":w+"px"})
		},
		endFun:function(i,c){
			var _w = 0 - (i-2)*150;
			if(i > 1 && i < (c-2)){
				$('.outslide .hd').stop().animate({"left":+_w+"px"},200)
			}
		}
	});
	
	$(".slider").delegate('.slide_item','click',function(){
		$(".outslidebox").show();
		countSlideHeight();
	})
	$(".outslide .hd").delegate('.slide_item','click',function(){
		var index = $(this).index();
		var len = $(".outslide .hd").find("li").length;
		$(this).addClass("on").siblings().removeClass("on");
		var l = 0 - (index-2)*150;
		if(index > 2 && index < (len-2)){
			$('.outslide .hd').stop().animate({"left":+l+"px"},200)
		}
	})
	$(".close").click(function(){
		$(".outslidebox").hide();
	})
	// 锚点
	$(".point").delegate('a','click',function(){
		var id = $(this).attr("href");
		var top = $(id).offset().top - 110;
		$(this).addClass("active").siblings().removeClass("active");
		if(top > 0){
			$('html,body').animate({scrollTop:top}, 50);
		}
	})
	// 地图 初始化地图当前位置
	// var city = $("#position").attr("data-city");
	// var address = $("#position").text();
	// map(city,address);
	// 地图 初始化地图当前位置
	var longitude = $("#position").attr("data-longitude");
	var latitude = $("#position").attr("data-latitude");
	map(longitude,latitude);

	$(window).scroll(function(){
		var top = $(window).scrollTop();
		var _top1 = $('#part2').offset().top - 110;

		if(top < _top1){
			$("#subNav").hide();
		}else{
			$("#subNav").show();
		}
		$(".part").each(function(){
			var _top = $(this).offset().top -110;
			var _class = $(this).attr("id");
			if(top >= _top){
				$("#subNav").find("."+_class).addClass("active").siblings().removeClass("active");
			}
		})
	})
	// 文字显示
	$(".show_more_btn").delegate('.overflowBtn','click',function(){
		$(this).parents(".part").find(".detail_txt").removeClass("overflow").css({"height":"auto"});
		$(this).parents(".show_more_btn").hide();
	})
	// 评论切换
	$(".part7_tt").delegate('li','click',function(){
		if($(this).hasClass("active")){
			return;
		}
		$(this).addClass("active").siblings().removeClass("active");
		$("#commentList").empty();
		if($(this).hasClass("all_rooms")){
			page = 0;
			queryLanEva();
		}else{
			page = 0;
			queryHouseEva()
		}
	})
	
	//评论加载更多
	$("#commentBtn").click(function(){
		if($("#part7").find(".part7_tt li.active").hasClass("all_rooms")){
			queryLanEva();
		}else{
			queryHouseEva();
		}
		
	})
	
	//初始化数据
	queryHouseEva();
	moreHouse();
})
/*计算弹层的高度*/
function countSlideHeight() {
	var t = ($(window).height() - 580) / 2 ;
	t = t < 0 ? 0 : t;
	$('.outslide').css({
		'top': t + 'px'
	});
}
// 政策
function showPolicy(obj){
	if($(obj).find("i").hasClass("up")){
		$(".policy").show();
		$(obj).find("i").removeClass("up").addClass("down");
	}else if($(obj).find("i").hasClass("down")){
		$(".policy").hide();
		$(obj).find("i").removeClass("down").addClass("up");
	}
}
// 时间间隔对比－调整政策
function compareTimes(sTime,eTime){
	var day = (eTime - sTime)/(86400*1000);
	var cDay = $("#cDay").val();
	if(day >= cDay){
		$(".lReserve").each(function () {
				$(this).show();
			})
			$(".sReserve").each(function () {
				$(this).hide();
			})
		}else{
			$(".lReserve").each(function () {
				$(this).hide();
			});
			$(".sReserve").each(function () {
				$(this).show();
			});
		}

}
//百度地图定位
function map(longitude,latitude){
	// 百度地图API功能
	var map = new BMap.Map("allmap");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 13);  // 初始化地图,设置中心点坐标和地图级别
	map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
	// map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	// 用经纬度设置地图中心点
	map.clearOverlays();
	map.enableScrollWheelZoom(true);
	var new_point = new BMap.Point(longitude,latitude);
			var marker = new BMap.Marker(new_point);  // 创建标注
			map.addOverlay(marker);              // 将标注添加到地图中
			map.panTo(new_point);    
		}

//展示评价列表
function showComments(list){
	var _str = '';
	$.each(list,function(i,n){
		
		n.userName = n.userName==null?"":n.userName;
		n.tentContent = n.tentContent==null?"":n.tentContent;
		n.lanContent = n.lanContent==null?"":n.lanContent;
		n.lanReplyContent = n.lanReplyContent==null?"":n.lanReplyContent;
		if(n.tentContent!=""){
			_str += '<li class="comment_li clearfix">'
				+'<div class="comment_left">'
				+	'<div class="headimg">'
				+		'<img src="'+n.userPic+'">'
				+	'</div>'
				+'</div>'
				+'<div class="comment_right">'
				+	'<div class="comment_item">'
				+		'<ul class="comment_tt clearfix">'
				+			'<li class="comment_tt_name c_g">'+n.userName+'</li>'
				+			'<li class="comment_tt_date c_6">入住时间：'+new Date(n.inDate).Format('yyyy-MM')+'</li>'
				+		'</ul>'
				+		'<div class="comment_txt">'+n.tentContent+'</div>'
				+	'</div>';
				
				if(n.lanReplyContent!=""){
					_str +=	'<div class="comment_item">'
					+		'<ul class="comment_tt clearfix">'
					+			'<li class="comment_tt_name c_4">房东回复：</li>'
					+		'</ul>'
					+		'<div class="comment_txt">'+n.lanReplyContent+'</div>'
					+	'</div>';
				}
			
			_str +='</div>'
				+'</li>';
		}
		
	});
	$("#commentList").append(_str);
	
}
//查询房源 房客与房东互评列表
function queryHouseEva(){
	page++;
	var rentWay = $("#rentWay").val();
	var fid = $("#fid").val();
	var param = {
		page:page,
		rentWay:rentWay
	}
	if(rentWay == 0){
		param.houseFid = fid;
	}
	if(rentWay == 1){
		param.roomFid = fid;
	}
	$.getJSON("/house/evaList",param,function(data){
		if(data.code == 0){
			var list = data.data.evaList;
			var total = data.data.total;
			total= total==undefined?0:total;
			if(total>0){
				$("#part7").find(".noMessage").hide();
				$("#part7").find("li").eq(0).html("本房源评价("+total+")");
				$("#p7Score").show();
				showComments(list);
				if(total <= page * 5){
					$("#part7").find(".show_more_btn").hide();
				}else{
					$("#part7").find(".show_more_btn").show();
				}
			}else{
				$("#part7").find(".noMessage").show();
				$("#part7").find(".show_more_btn").hide();
			}
			
		}
	});
	
}
//查询房客对该房源的房东的所有评价
function queryLanEva(){
	page++;
	var param = {
		page:page,
		landlordUid:$("#lanUid").val()
	}
	$.getJSON("/house/tolanList",param,function(data){
		if(data.code == 0){
			var list = data.data.lanEvaList;
			var total = data.data.total;
			total= total==undefined?0:total;
			if(total>0){
				$("#part7").find(".noMessage").hide();
				$("#part7").find("li").eq(1).html("房东的所有评价("+total+")");
				showComments(list);
				if(total <= page * 5){
					$("#part7").find(".show_more_btn").hide();
				}else{
					$("#part7").find(".show_more_btn").show();
				}
			}else{
				$("#part7").find(".noMessage").show();
				$("#part7").find(".show_more_btn").hide();
			}
			
		}
	});
}

//更多房源
function moreHouse(){
	var _str = '';
	$.getJSON("/search/landList",{landlordUid:$("#lanUid").val()},function(data){
		if(data.code == 0){
			var total =0;
			if(data.data.list!=undefined){
				var list = data.data.list;
				var total = data.data.total;
				
				for(var ii = 0;ii<list.length;ii++){
					if(list[ii].fid == $("#fid").val()){
						continue;
					}
					_str += '<li class="con">'
						+' <a href="/rooms/'+list[ii].rentWay+'/'+list[ii].fid+'" class="house">'
						+'		<div class="img_box">'
						+'			<img class="setImg" src="'+list[ii].picUrl+'">'
						+'		</div>'
						+'		<div class="txt">'
						+'			<h1>'+list[ii].houseName+'</h1>'
						+'			<h3>'+list[ii].rentWayName+'/可住'+list[ii].personCount+'人</h3>'
						+'			<div class="clearfix">'
						+'				<div class="star_box clearfix">'
//						+'					<ul class="stars" data-val="'+list[ii].evaluateScore+'">'
						+'					<ul class="stars" data-val="'+list[ii].realEvaluateScore+'">'
						+'						<li class="star"></li>'
						+'						<li class="star"></li>'
						+'						<li class="star"></li>'
						+'						<li class="star"></li>'
						+'						<li class="star"></li>'
						+'					</ul>'
//						+'					<span>'+list[ii].evaluateScore+'分</span>'
						+'					<span>'+list[ii].realEvaluateScore+'分</span>'
						+'				</div>'
						+'				<div class="price">'
						+'				¥<span>'+list[ii].price/100+'</span>/晚'
						+'				</div>'
						+'			</div>'
						+'		</div>'
						+'		</a>'
						+'	</li>'
						
				}
				$(".more_house_list").append(_str);
				$(".more_house_box").show();
				setStar();
			}
			
			if(total == 1){
				$(".more_house_box").hide();
			}
		}
	});

}

// 日历

var startDate = $('#startDate').val();
var endDate = $('#endDate').val();
var iprice = 0;
var tempId=[];


var iptName;
$(function(){
	$(document).click(function(){
		$("#calendarBox").hide();
	})
	$(".reserve_list").delegate('#calendarBoxBtn','click',function(e){
		var _n = new Date();
		var _time = _n.getTime();
		var _endTime = Date.parse($("#outDate").val().replace(/-/g,"/"));
		if(_time > _endTime){
			showConfirm("该房源已超出出租期限，去其他房源看看吧！","确定")
			return;
		}
		$("#calendarBox").show();
		iptName = "startDate";
		$("#startDate").focus();
		$('html,body').animate({scrollTop:0}, 50);
		e.stopPropagation();
	})
	// 日历选择
	$(".expense_list").delegate('.ipt','click',function(e){
		
		iptName = $(this).attr("id");
		var _n = new Date();

		var _time = _n.getTime();
		var _endTime = Date.parse($("#outDate").val().replace(/-/g,"/"));
		if(_time > _endTime){
				 		showConfirm("该房源已超出出租期限，去其他房源看看吧！","确定")
				 		return;
				 	}
		if($(this).val() != ""){
			var y = $(this).val().split("-")[0];
			var m = $(this).val().split("-")[1];
			$("#monthBox").attr("data-month",y+'-'+m).html(y+'年'+m+'月');
			iconDisabled(y,m);
			showDateList();
		}else{
			unRented();
		}
		$("#calendarBox").show();
		e.stopPropagation();
	})
	$(".calendars_bar").delegate('.icon','click',function(e){
		var y = $("#monthBox").attr("data-month").split("-")[0];
		var m = $("#monthBox").attr("data-month").split("-")[1];
		if(!$(this).hasClass("disabled")){
			if($(this).hasClass("icon_next")){
				if(m == 12){
					m =1;
					y++;
				}else{
					m++;
				}
			}
			if($(this).hasClass("icon_prev")){
				if(m == 1){
					m =12;
					y--;
				}else{
					m--;
				}
			}
			m = m<10?'0'+m:m;
			$("#monthBox").attr("data-month",y+'-'+m).html(y+'年'+m+'月');
			iconDisabled(y,m);
			showDateList();
			
		}
		e.stopPropagation();
	})
    // 日历
    var flag = true;
    $('#calendarBox').delegate('td','click',function(e){
    	if(!$(this).hasClass("un_rented") && !$(this).hasClass("have_rented") && !$(this).hasClass("set_rented")){

    		var currentTime = $(this).attr("id").replace('td_','');
    		var currentTimes = Date.parse(currentTime.replace(/-/g, '/'))
    		startDate = $('#startDate').val();
    		endDate = $('#endDate').val();
    		if(iptName == "startDate"){
    			if(endDate != "" && endDate != "离开日期"){
    				var endTime = endDate.replace(/-/g, '/');
    				var endTimes = Date.parse(endTime);
    				if(currentTimes >= endTimes){
    					$("#endDate").val("");
    					$("#deposit").text(0);//押金
						$("#serviceCharge").text(0);
						$("#roomCharge").text(0);//房费0
						$("#discountMoney").text(0);
						$("#totalCharge").text(0);//总费用
    				}
    				var _startDate = $(this).attr("id").replace("td_","");
    				var tempId_s = [];
    				getDateArr(_startDate,endDate);
    				tempId_s = list;
    				for(var i = 0 ; i < tempId_s.length; i ++){
    					if($("#"+tempId_s[i]).hasClass("have_rented") || $("#"+tempId_s[i]).hasClass("set_rented")){
    						$('#endDate').val("");
    						$("#deposit").text(0);//押金
							$("#serviceCharge").text(0);
							$("#roomCharge").text(0);//房费0
							$("#discountMoney").text(0);
							$("#totalCharge").text(0);//总费用
    					}
    				}
    				compareTimes(currentTimes,endTimes);
    			}


    		}else if(iptName =="endDate"){
    			if(startDate != "" && startDate != "入住日期"){
    				var startTime = startDate.replace(/-/g, '/');
    				var startTimes = Date.parse(startTime);
    				var _endDate = $(this).attr("id").replace("td_","");
    				var tempId_e = [];
    				getDateArr(startDate,_endDate);
    				tempId_e = list;
    				for(var i = 0 ; i < tempId_e.length; i ++){
    					if($("#"+tempId_e[i]).hasClass("have_rented") || $("#"+tempId_e[i]).hasClass("set_rented")){
    						showConfirm("所选时间内暂时无房可住，请重新选择日期！","确定");
    						return;
    					}
    				}
    				compareTimes(startTimes,currentTimes);
    				
    			}else{
    				if($(this).hasClass("current_day")){
    					showConfirm("租住时间至少一天","确定");
    					return;
    				}
    			}
    		}
    		setDateIpt(currentTime);
    		$("#calendarBox td.active").removeClass("active");
    		$(this).addClass("active");
    		setPrice();
    	}else{
    		e.stopPropagation();
    	}

    });
$('#calendarBox').delegate('td','mouseenter',function(){
	if(!$(this).hasClass("un_rented")){
		showTap('¥'+$(this).attr("data-price"),$(this).attr("id"),3000);
	}

});
$('#calendarBox').delegate('td','mouseleave',function(){
	if(!$(this).hasClass("un_rented")){
		layer.closeAll('tips');
	}

});
$(".clear_box").delegate(".clearBtn","click",function(){
	$("#startDate").val("");
	$("#endDate").val("");
	if($("#calendarTab").find("td.active").length != 0){
		$("#calendarTab").find("td.active").removeClass("active");
	}
})
$("#service").hover(function(){
	showService();
},function(){
	layer.closeAll();
})
	// 显示更多按钮
	overTxt("part3");
	overTxt("part5");
})
function overTxt(id){
	if($("#"+id).find(".overflow").height() < 72 || $("#"+id).find(".overflow").height() == 75){
			$("#"+id).find(".show_more_btn").hide();
		}else{
			$("#"+id).find(".overflow").css({"height":"72px"})
			$("#"+id).find(".show_more_btn").show();
		}
}
var list = [];
//获取日期数组列表
function getDateArr(startDate,endDate){
	startDate = startDate.replace("/","-");
	endDate = endDate.replace("/","-")
	$.ajaxSettings.async = false;
	$.getJSON("/house/dateArr",{start:startDate,end:endDate},function(data){
		if(data.code == 0){
			list = data.data.dateList;
		}
	});
}
function showService(){
	layer.tips('我们将收取房费的9%作为服务费', '#service', {
		tips: [1, '#fff'],
		area:"250px",
		time: 2000
	});
}
// 日期左右icon disabled
function iconDisabled(y,m){
	
	// 开始日期
	var now = new Date();
	var _y = now.getFullYear();
	var _m = now.getMonth()+1;
	if(y < _y){
		$(".icon_prev").addClass("disabled");
	}else{
		if(y == _y){
			if(m <= _m){
				$(".icon_prev").addClass("disabled");
			}else{
				$(".icon_prev").removeClass("disabled");
			}
		}else{
			$(".icon_prev").removeClass("disabled");
		}
	}
	// 结束日期
	var outDate = $('#outDate').val();
	var o_y = outDate.split('-')[0];
	var o_m = outDate.split('-')[1];
	if(y > o_y){
		$(".icon_next").addClass("disabled");
	}else{
		if(y == o_y){
			if(m >= o_m){
				$(".icon_next").addClass("disabled");
			}else{
				$(".icon_next").removeClass("disabled");
			}
		}else{
			$(".icon_next").removeClass("disabled");
		}
	}
}
function unRented(){
	for(var r= 1 ; r< $("#calendarTab").find("tr").length;r++){

		for(var d = 0 ; d < $("#calendarTab tr:eq("+r+")").find("td").length;d++){

			var _td = $("#calendarTab tr:eq("+r+") td:eq("+d+")");
			if(_td.hasClass("can_rented")){
				_td.removeClass("un_rented");
			}
		}
	}
	if($("#calendarTab").find("td.active").length != 0){
		$("#calendarTab").find("td.active").removeClass("active");
	}
	if(iptName == "startDate" && $("#startDate").val() != ""){
		$("#td_"+$("#startDate").val()).addClass("active")
	}else if(iptName == "endDate" && $("#endDate").val() != ""){
		$("#td_"+$("#endDate").val()).addClass("active")
	}
	if(iptName == "endDate" && $("#startDate").val() != ""){
		var td_len = $("#td_"+$("#startDate").val()).index();
		var tr_len = $("#td_"+$("#startDate").val()).parents("tr").index();
		if(td_len != -1 && tr_len != -1){
			for(var i = 1 ; i < tr_len ; i++){
				var _td_len = $("#calendarTab tr:eq("+i+") td").length;
				for(var j = 0 ; j < _td_len ; j ++){
					var s_td = $("#calendarTab tr:eq("+i+") td:eq("+j+")");
					if(s_td.hasClass("can_rented")){
						s_td.addClass("un_rented");
					}
				}
			}
			for(var j = 0 ; j < td_len+1; j++){
		
						var s_td2 = $("#td_"+$("#startDate").val()).parents("tr").find("td:eq("+j+")");
						if(s_td2.hasClass("can_rented")){
							s_td2.addClass("un_rented");
						}
					}
		}else{
			var nowD = $("#monthBox").attr("data-month")+'-01';
			var nowT = Date.parse(nowD.replace(/-/g,'/'));
			var starD = $("#startDate").val();
			var starT = Date.parse(starD.replace(/-/g,'/'));
			if(nowT < starT){
				for(var r= 1 ; r< $("#calendarTab").find("tr").length;r++){

					for(var d = 0 ; d < $("#calendarTab tr:eq("+r+")").find("td").length;d++){

						var _td = $("#calendarTab tr:eq("+r+") td:eq("+d+")");
						if(_td.hasClass("can_rented")){
							_td.addClass("un_rented");
						}
					}
				}
			}
		}
		
	}
}
function showDateList(){
	var _start = $("#monthBox").attr("data-month")+'-01';
	var _end = calendar.checNextMonthDay(_start,0);
	var data = {
		rentWay:$("#rentWay").val(),
		startDate:_start,
		endDate:_end,
		fid:$("#fid").val()
	};	
	$.getJSON("/house/leaseCalendar",data,function(data){
		if(data.code == 0){
			var list = data.data.list;
			calendar.wirteDate(list);
			unRented();
		}
	});
}
// 设置ipt日期
function setDateIpt(date){
	$("#"+iptName).val(date);
}
// 设置价格
function setPrice(){
	startDate = $("#startDate").val();
	endDate = $("#endDate").val();
	if(startDate == "" || startDate == "入住日期" || endDate == "" || endDate == "离开日期"){
		return;
	}
	var param = {
		startTime:startDate,
		endTime:endDate,
		fid:$("#fid").val(),
		rentWay:$("#rentWay").val()
	}
	$.post("/house/needPay",param,function(data){
		if(data.code == 0){
			data.data.cost/100;//折扣后房租 +折扣金额
			$("#deposit").text(data.data.depositMoney/100);//押金
			$("#serviceCharge").text(data.data.userCommission/100);
			$("#roomCharge").text(data.data.cost/100);//房费
			$("#cleanMoney").text(data.data.cleanMoney/100);//清洁费
			var discountMoney = data.data.discountMoney;//折扣金额
			if(discountMoney != 0){
				$("#discountMoneyWrapper").show();
				$("#discountMoney").text(discountMoney/100);
			}
			$("#totalCharge").text(data.data.needPay/100);//总费用
		}else{
			data.msg = data.msg == "服务错误"?"网络异常，请稍后重试":data.msg;
			showConfirm(data.msg,"确定");
		}
	},'json');
}
