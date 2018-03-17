$(function (){
	var page=1;
	var flag=true;
	var oViewPage=$('#pageView');
	var landlordUid = $("#landlordUid").val();

	var iWinHeight = $(window).height();
	var top = $(window).scrollTop()+iWinHeight;
	var curentMonth = $("#curMonth").val();

	
    var conHeight = $(window).height()-$('.header').height();
	$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
	$('#main > div').css({'position':'absolute','width':'100%','padding-bottom':'3.5rem'});
	myScroll = new IScroll('#main', { scrollbars: true, mouseWheel: true });
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
  


	//滚动加载	
	myScroll.on('scroll', updatePosition);
	
	function updatePosition () {
		var month = $("#curMonth").val();

		top = $(window).scrollTop()+iWinHeight;		
		var _top = oViewPage.offset().top;
		if(_top < top && oViewPage.html()!='没有更多房源了'){
			if(!flag){
				return;
			}
			page++;
			flag = false;
			$.getJSON(SERVER_CONTEXT+"profitMgt/"+LOGIN_UNAUTH+"/houseRoomList",{page:page,month:month},function(data){
				if(data.code == 0){
					var list = data.data.list;
					showList(list,page);
					flag = true;
				}else{
					//$("#orderList").empty();
					//$('#error-modal').modal("open");
				}
			});
		}
	}
	//滚动加载	
	/*myScroll.scroll(function(){

		var month = $("#curMonth").val();

		top = $(window).scrollTop()+iWinHeight;		
		var _top = oViewPage.offset().top;
		if(_top < top && oViewPage.html()!='没有更多房源了'){
			if(!flag){
				return;
			}
			page++;
			flag = false;
			$.getJSON(SERVER_CONTEXT+"profitMgt/"+LOGIN_UNAUTH+"/houseRoomList",{page:page,month:month},function(data){
				if(data.code == 0){
					var list = data.data.list;
					showList(list,page);
					flag = true;
				}else{
					//$("#orderList").empty();
					//$('#error-modal').modal("open");
				}
			});
		}
	});*/

	var initData = function(month){
		page = 1
		//第一次加载数据 加载待处理数据
		$.getJSON(SERVER_CONTEXT+"profitMgt/"+LOGIN_UNAUTH+"/houseRoomList",{page:page,month:month},function(data){

			if(data.code == 0){
				var list = data.data.list;
				showList(list,page);
				oViewPage.html('');
				if(page==1 && list.length==0){
					$('.mainList').empty();
					$('.mainList').html('<p class="nohouse">没有房源哦~</p>');
				}
			}else{
				$('.mainList').empty();
				$('.mainList').html('<p class="nohouse">没有房源哦~</p>');
			}
		});
	}

	initData(curentMonth);

	var getProfit = function(month){

		var month2 = $(".slick-active").find('input[name="month"]').val();

		//第一次加载数据 加载待处理数据
		$.getJSON(SERVER_CONTEXT+"profitMgt/"+LOGIN_UNAUTH+"/houseProfit",{month:month},function(data){
			if(data.code == 0){
				$(".slick-active").find('span[name="realMoney"]').text(data.data.houseRealMoney/100);
				$(".slick-active").find('span[name="predictMoney"]').text(data.data.housePredictMoney/100);
			}
		});
	}



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
				initData(month);
				getProfit(month);
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
			var index = $(".date:eq("+i+")").parent(".slick-slide").index()-1;
			$('#slider').slickGoTo(index);
		}
	}


	// 630新增 方法已放到git common.js
	setImgPosition();


})

//加载房源数据
function showList(list,page){
	var month = $("#curMonth").val();
	var oViewPage=$('#pageView');
	if(list.length==0){
		oViewPage.html('没有更多房源了');
		return;
	}
	var str='';

	$.each(list,function(i,n){
		var baseUrl = $("#picResourceUrl").val();
		var picSize = $("#picSize").val();
		var picUrl=n.defaultPic;
		var defaultPic=baseUrl+picUrl+picSize;
		if(picUrl==null){
			defaultPic="";
		}else{
			defaultPic=defaultPic+"."+picUrl.split(".")[1].toLowerCase(); 
		}

		if(n.roomStatus==41 || n.roomStatus==50){
			str+="<li class='end'>";
		}else{
			str+="<li>";
		}

		var houseName= n.houseName;

		if(!!houseName && houseName != undefined){
			houseName.replace("'s","");
		}

		var rentWay = parseInt(n.rentWay);
		if(rentWay == 1&&(n.houseRoomFid == null||n.houseRoomFid==""||n.houseRoomFid==undefined)){
			str +="<a href='javascript:roomTips();' class='bottom'>";
		}else{
			str +="<a href='"+SERVER_CONTEXT+"profitMgt/"+LOGIN_UNAUTH+"/toProfitOrderList?force=1&houseBaseFid="+n.houseBaseFid+"&houseRoomFid="+n.houseRoomFid+"&month="+month+"&houseName="+houseName+"&roomName="+n.roomName+"&rentWay="+n.rentWay+"' class='bottom'>";
		}
		str+="<div class='content_box clearfix'>";
		str+="<div class='img_box'>";
		str+="<img src='"+defaultPic+"' class='setImg'>";
		str+="<span class='status'>"+n.statusName+"</span>";
		str+="</div>";

		str+="<div class='text_box'>";

		str+="<h1>"+n.name+"</h1>";

		str+="<p>实际收益￥<span class='v'>"+n.actualMoney/100+"</span></p>";
		str+="<p>预计收益￥<span class='r'>"+n.predictMoney/100+"</span></p>";
		str+="<p>订单数量:<span class='v'>"+n.orderNum+"</span></p>";
		str+="<div>";
		str+="</div>";
		str += "</a>";   

	});
	if(page == 1){
		$("#hosueList").empty();
	}
	$("#hosueList").append(str);

	myScroll.refresh();
	setImgWidth();	
} 

/**
 * 无房间的提示
 */
function roomTips(){
	showShadedowTips("请完善房间信息",1);
}