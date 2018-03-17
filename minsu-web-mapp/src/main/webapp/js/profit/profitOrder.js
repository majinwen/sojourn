(function ($) {
	
	var staticResourceUrl = $('#staticResourceUrl').val();
    var curMonth = $("#curMonth").val();
    
    var houseFid = $("#houseFid").val();
	var roomFid =  $("#roomFid").val();
	var rentWay =  $("#rentWay").val();
	//定义对象
	var lanOrder = {
		flag:true,
		type:1,
		noneImg:staticResourceUrl+'/images/user.png'
	}
	
	lanOrder.initData = function(curMonth,type){
		var initPage = 1;
		//第一次加载数据 加载待处理数据
 		$.getJSON(SERVER_CONTEXT+"profitMgt/"+LOGIN_UNAUTH+"/profitOrderDataList",{month:curMonth,page:initPage,type:type,houseFid:houseFid,roomFid:roomFid,rentWay:rentWay},function(data){
 			if(data.code == 0){
 				var list = data.data.orderList;
 				lanOrder.showList(list,initPage);
 				$('#pageView').html('');
 			}else{
 				$("#orderList").empty();
 				$('#pageView').html(data.msg);
 			}
 		});
 		lanOrder.getProfit(curMonth,type);
	}
	
	lanOrder.getProfit = function(month,type){
		//第一次加载数据 加载待处理数据
		$.getJSON(SERVER_CONTEXT+"profitMgt/"+LOGIN_UNAUTH+"/houseMonthProfit",{rentWay:rentWay,roomFid:roomFid,houseFid:houseFid,month:month,type:type},function(data){
			if(data.code == 0){
				$(".slick-active").find('span[name="realMoney"]').text(data.data.money/100);
			}
		});
	}
	
	/**
	 * 初始化
	 */
	lanOrder.init = function(){
		var page=1;
		var oViewPage=$('#pageView');
		var iWinHeight = $(window).height();
		var top = $(window).scrollTop()+iWinHeight;
		
       // lanOrder.initData($("#curMonth").val(),lanOrder.type);
		
		$('#orderType li').click(function(event){
 			if($(this).hasClass('active')){
 				return;
 			}else{
 				$(this).addClass('active').siblings().removeClass("active");
 			}
 			
 			$("#orderList").empty();
 			oViewPage.html('正在加载');

 			var id = $(this).attr('id'); 			
 			if(id == "waiting"){
 				lanOrder.dolist($("#curMonth").val(),1,1);
 				lanOrder.type=1;
 				lanOrder.getProfit($("#curMonth").val(),lanOrder.type);
 			}else if(id == "doing"){
 				lanOrder.dolist($("#curMonth").val(),1,2);
 				lanOrder.type=2;
 				lanOrder.getProfit($("#curMonth").val(),lanOrder.type);
 			}
 		});

 		//滚动加载	
 		$(window).scroll(function(){
 			top = $(window).scrollTop()+iWinHeight;		
 			var _top = oViewPage.offset().top;

 			if(_top < top && oViewPage.html()!='没有数据'){
 				if(!lanOrder.flag){
 					return;
 				}
 				page++;
 				lanOrder.flag = false;
 				
 				lanOrder.dolist($("#curMonth").val(),page,lanOrder.type);

 			}
 		});
 		
 		$('#slider').slick({
 		    dots: false,
 		    arrows:false,
 		    infinite: true,
 		    speed: 1,
 		    slidesToShow: 1,
 		    slidesToScroll: 1,
 		    onBeforeChange:function(){
 		    	touchMove:true
 		    },
 		    onAfterChange:function(o,index){
 		    	setTimeout(function(){
 		    		var month = $(".slick-active").find('input[name="month"]').val();
 	 		    	lanOrder.initData(month,lanOrder.type);
 	 		    	lanOrder.getProfit(month,lanOrder.type);
 	 		    	$("#curMonth").val(month);
 	        	},1)
 		    }
 		});
 	   // 只有一条数据
 		if($(".slick-slide").length == 1){
 		    $(".slick-slide").addClass("slick-active")
 		}
 		var curentMonth = $("#curMonth").val();
 		// 滚动到具体月份
 		for(var i = 0 ; i < $(".date").length ; i ++){
 			if($(".date:eq("+i+")").attr("data-date") == curentMonth){
 				var index = $(".date:eq("+i+")").parents(".slick-slide").index()-1;
 				$('#slider').slickGoTo(index);
 			}
 		}
 		
 		
	}
	
	/**
	 * 获取订单列表的list
	 */
	lanOrder.dolist = function(month,page,type){
	
		$.getJSON(SERVER_CONTEXT+"profitMgt/"+LOGIN_UNAUTH+"/profitOrderDataList",{page:page,type:type,month:month,houseFid:houseFid,roomFid:roomFid,rentWay:rentWay},function(data){
			if(data.code == 0){
				var list = data.data.orderList;
				var oViewPage=$('#pageView');
				oViewPage.html('');
				if(list.length==0 && oViewPage.html()!='服务错误'){
					oViewPage.html('没有更多 数据');
					lanOrder.flag= false;
					if(page == 1){
						var _html = "<p class='noprofit'>您暂时还没有收益</p>";
						$("#orderList").append(_html);
					}
					return;
				}else{
					lanOrder.showList(list,page);
					lanOrder.flag= true;
				}
				
			}

			if(data.code == 1){
				$("#orderList").empty();
				oViewPage.html(data.msg);
			}


		});
	}
	/**
	 * 展示列表
	 */
	lanOrder.showList = function (list,page){
            var _html="";
          
 			$.each(list,function(i,n){
 				_html +="<li>";
	 			_html +="<div class='t clearfix'><span class='fl'>"+n.houseName+"</span> <span class='fr'>"+n.orderStatusShowName+"</span></div>";
	 			_html +="<a href='"+SERVER_CONTEXT+"orderland/43e881/showDetail?orderSn="+n.orderSn+"&flag=1"+"' class='clearfix' >";
	 			
	 			_html +="<div class='c clearfix'>";
	 			_html +="<div class='img'>";
	 			_html +="<img src='"+n.housePicUrl+"' alt=''>";
	 			_html +="</div>";
	 			
	 			_html +="<div class='txt'>";
				_html +="<p>"+n.startTimeStr+"至"+n.endTimeStr+"</p>";
				_html +="<p>预定人："+n.userName+"&nbsp;&nbsp;&nbsp; 共"+n.contactsNum+"位入住</p>";
				_html +="<p>订单总额：<span class='org'>¥"+n.needMoney+"元</span></p>";
				_html +="</div>";
				_html +="<span class='icon icon_r'></span>";
				_html +="</div>";
				_html +="</a>";
				_html +="<div class='b'>";
				 _html +="<a class='b' href='"+SERVER_CONTEXT+"orderland/"+LOGIN_UNAUTH+"/toOrderRemark?orderSn="+n.orderSn+"'><span class='icon'>添加备注</span></a>";
				 _html +="</div>";
			 
			   _html +="</li>";
			});
 			
 			if(page == 1){
 				$("#orderList").empty();
 				if(list == null || list == undefined || list.length <= 0){
 	 				_html += "<p class='noprofit'>您暂时还没有收益</p>";
 	 			}
 			}
 			
 			$("#orderList").append(_html);
 	}
	
	lanOrder.init();
	window.LanOrder = lanOrder;
	
}(jQuery));
