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
		$(this).addClass("active").siblings().removeClass("active");
		$("#commentList").empty();
		if($(this).hasClass("all_rooms")){
			$("#p7Score").hide();
			page = 0;
			queryLanEva();
		}else{
			$("#p7Score").show();
			page = 0;
			queryHouseEva()
		}
	})
	
	
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
//百度地图定位
function map(longitude,latitude){
	// 百度地图API功能
	var map = new BMap.Map("allmap");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 13);  // 初始化地图,设置中心点坐标和地图级别
	map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
	// map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	// 用经纬度设置地图中心点
	map.clearOverlays(); 
	var new_point = new BMap.Point(longitude,latitude);
			var marker = new BMap.Marker(new_point);  // 创建标注
			map.addOverlay(marker);              // 将标注添加到地图中
			map.panTo(new_point);    
		}

//展示评价列表
function showComments(list){
	var _str = '';
	$.each(list,function(i,n){
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
		+	'</div>'
		+	'<div class="comment_item">'
		+		'<ul class="comment_tt clearfix">'
		+			'<li class="comment_tt_name c_4">房东回复：</li>'
		+		'</ul>'
		+		'<div class="comment_txt">'+n.lanContent+'</div>'
		+	'</div>'
		+'</div>'
		+'</li>'
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
			showComments(list);
			if(total <= page * 5){
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
			showComments(list);
		}
	});
}
//更多房源
function moreHouse(){
	var _str = '';
	$.getJSON("/search/landList",{landlordUid:$("#lanUid").val()},function(data){
		if(data.code == 0){
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
					+'					<ul class="stars" data-val="'+list[ii].evaluateScore+'">'
					+'						<li class="star"></li>'
					+'						<li class="star"></li>'
					+'						<li class="star"></li>'
					+'						<li class="star"></li>'
					+'						<li class="star"></li>'
					+'					</ul>'
					+'					<span>'+list[ii].evaluateScore+'分</span>'
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
			setStar();
			if(total == 0){
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
		$("#calendarBox").show();
		iptName = "startDate";
		$("#startDate").focus();
		$('html,body').animate({scrollTop:0}, 50);
		e.stopPropagation();
	})
	// 日历选择
	$(".expense_list").delegate('.ipt','click',function(e){
		
		iptName = $(this).attr("id");
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
    			if(endDate != "" && endDate != "结束时间"){
    				var endTime = endDate.replace(/-/g, '/');
    				var endTimes = Date.parse(endTime);
    				if(currentTimes >= endTimes){
    					$("#endDate").val("");
    				}
    				var _startDate = $(this).attr("id").replace("td_","");
    				var tempId_s = [];
    				getDateArr(_startDate,endDate);
    				tempId_s = list;
    				for(var i = 0 ; i < tempId_s.length; i ++){
    					if($("#"+tempId_s[i]).hasClass("have_rented") || $("#"+tempId_s[i]).hasClass("set_rented")){
    						$('#endDate').val("");
    					}
    				}
    			}


    		}else if(iptName =="endDate"){
    			if(startDate != "" && startDate != "开始时间"){
    				var startTime = startDate.replace(/-/g, '/');
    				var startTimes = Date.parse(startTime);
    				var _endDate = $(this).attr("id").replace("td_","");
    				var tempId_e = [];
    				getDateArr(startDate,_endDate);
    				tempId_e = list;
    				for(var i = 0 ; i < tempId_e.length; i ++){
    					if($("#"+tempId_e[i]).hasClass("have_rented") || $("#"+tempId_e[i]).hasClass("set_rented")){
    						showConfirm("所选时间区间内不可有已租或设为已租状态的日期！","确定");
    						return;
    					}
    				}
    				
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
	$("#"+iptName).val("");
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
function showService(){
	layer.tips('我们将收取房费的9%作为服务费', '#service', {
		tips: [1, '#fff'],
		area:"250px",
		time: 2000
	});
}