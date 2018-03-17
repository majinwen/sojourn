(function ($) {

	var staticResourceUrl = $('#staticResourceUrl').val();

	//定义对象
	var lanOrder = {
		flag:true,
		type:0,
		noneImg:staticResourceUrl+'/images/user.png'
	}

	
	/**
	 * 初始化
	 */
	lanOrder.init = function(){
		var page=1;
		
		var oViewPage=$('#pageView');
		var iWinHeight = $(window).height();
		var top = $('.bodys').scrollTop()+iWinHeight;


		//第一次加载数据 加载待处理数据
 		$.getJSON(SERVER_CONTEXT+"orderland/"+LOGIN_UNAUTH+"/dataList",{page:1,lanOrderType:1},function(data){
 			if(data.code == 0){
 				var list = data.data.orderHouseList;
 				lanOrder.showList(list);
 				$('#pageView').html('');
 				if(page==1 && list.length==0){
 					$('#orderList').html('<p class="nodingdan">您还没有订单哦~</p>');
 				}
 			}else{
 				$('#orderList').html('<p class="nodingdan">您还没有订单哦~</p>');
 			}
 		});
 		
 		$('#orderType li').click(function(event){
 			if($(this).hasClass('active')){
 				return;
 			}
 			$("#orderList").empty();
 			page=1;
 			oViewPage.html('正在加载');

 			var id = $(this).attr('id'); 			
 			if(id == "waiting"){
 				lanOrder.dolist(page,1);
 				lanOrder.type=1;
 			}else if(id == "doing"){
 				lanOrder.dolist(page,2);
 				lanOrder.type=2;
 			}else if(id == "done"){
 				lanOrder.dolist(page,3);
 				lanOrder.type=3;
 			}
 		});

 		//滚动加载	
 		$('.bodys').scroll(function(){
 			top = $(window).scrollTop()+iWinHeight;		
 			var _top = oViewPage.offset().top;

 			if(_top < top && oViewPage.html()!='没有数据'){
 				if(!lanOrder.flag){
 					return;
 				}
 				page++;
 				lanOrder.flag = false;
 				
 				lanOrder.dolist(page,lanOrder.type);

 			}
 		});

 		
	}
	
	/**
	 * 获取订单列表的list
	 */
	lanOrder.dolist = function(page,type){
		
		$.getJSON(SERVER_CONTEXT+"orderland/"+LOGIN_UNAUTH+"/dataList",{page:page,lanOrderType:type},function(data){
			if(data.code == 0){
				var list = data.data.orderHouseList;
				var oViewPage=$('#pageView');
				oViewPage.html('');
				if(page==1 && list.length==0){
 					$('#orderList').html('<p class="nodingdan">您还没有订单哦~</p>');
 				}
				if(list.length==0 && oViewPage.html()!='服务错误'){
					oViewPage.html('没有数据');
					lanOrder.flag= false;
					return;
				}else{
					lanOrder.showList(list);
					lanOrder.flag= true;
				}

			}

			if(data.code == 1){
				$('#orderList').html('<p class="nodingdan">您还没有订单哦~</p>');
			}


		});
	}
	
	
	/**
	 * 添加备注
	 */
	lanOrder.addRemark=function(orderSn){
		$.post(SERVER_CONTEXT+"orderland/43e881/checkRemarkCount",{"orderSn":orderSn},function(data){
			if(data.code == 0){
				window.location.href = SERVER_CONTEXT+"orderland/43e881/toOrderRemark?orderSn="+orderSn;
			}else{
				showShadedowTips(data.msg, 1);
			}
		},'json');
	}

	/**
	 * 展示列表
	 */
	lanOrder.showList = function (list){
 			//$("#orderList").empty();

 			var _html="";

 			if(list.length==0){
 				$('#orderList').html('<p class="nodingdan">您还没有订单哦~</p>');
 			}
 			$.each(list,function(i,n){
	 			_html +="<li>";
	 			_html +="<div class='t clearfix'><p class='fl'>"+n.houseName+"</p> <span class='fr'>"+n.orderStatusShowName+"</span></div>";
	 			_html +="<div class='c'><a href='"+SERVER_CONTEXT+"orderland/43e881/showDetail?orderSn="+n.orderSn+"&flag=2"+"' class='clearfix' >";
	 			_html +="<div class='img'><img src='"+n.housePicUrl+"' alt=''></div>";
				_html +="<p>"+n.startTimeStr+"至"+n.endTimeStr+"</p>";
				_html +="<p>预订人："+n.userName+"&nbsp;&nbsp;&nbsp; 共"+n.contactsNum+"位入住</p>";
				_html +="<p>订单总额：<span class='org'>¥"+n.needMoney+"元</span></p>";
				_html +="<span class='icon icon_r'></span>";
				_html +="</a></div>";
				
				_html +="<div class='b clearfix br'>";
				_html +="<a class='add addRemark' href='javascript:void(0);' data-sn='"+n.orderSn+"'>添加备注</a>";
				if(n.evaStatus == 0){
					_html +="<a class='write' href='"+SERVER_CONTEXT+"landlordEva/"+LOGIN_UNAUTH+"/queryEvaluateInfo?orderSn="+n.orderSn+"'>去点评</a>";
				}
				_html +="</div></li>";
			});
 			$("#orderList").append(_html);
 			$(".addRemark").click(function(){
 				lanOrder.addRemark($(this).attr("data-sn"))
 			});
 			imgCenter();
 	}
	
	lanOrder.init();
	window.LanOrder = lanOrder;
	
}(jQuery));
