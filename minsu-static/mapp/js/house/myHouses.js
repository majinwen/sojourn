	var myScroll = new IScroll('#mainScroll', { scrollbars: true, probeType: 3, mouseWheel: true });

	var page=1;
	var flag=true;
	var oViewPage=$('#pageView');
	var landlordUid = $("#landlordUid").val();
	
	var iWinHeight = $(window).height();
	var top = $(window).scrollTop()+iWinHeight;

	//第一次加载数据 加载待处理数据
	
	var conHeight = $(window).height()-$('.header').height()-$('.bottomNav').height();
	var conTop=0;
	var conFlag=false;
	
	$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
	$('#mainScroll').css({'position':'absolute','height':conHeight,'width':'100%'});
	

	myScroll.on('scroll', updatePosition);
		

	function updatePosition () {
		conTop = this.y>>0;	
			
		if(Math.abs(conTop)>($('#houseList').height()-conHeight) && flag){
			
			flag = false;
			page++;
			$.getJSON(SERVER_CONTEXT+"houseMgt/"+LOGIN_UNAUTH+"/houseRoomList",{landlordUid:landlordUid,page:page},function(data){
				if(data.code == 0){
					var list = data.data.list;
					showList(list);					
				}
			});
		}
		
	}
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	

	$.getJSON(SERVER_CONTEXT+"houseMgt/"+LOGIN_UNAUTH+"/houseRoomList",{landlordUid:landlordUid,page:page},function(data){
		if(data.code == 0){
			var list = data.data.list;
			showList(list);
			oViewPage.html('');
			if(page==1 && list.length==0){
				$('#main').empty();
				$('#main').html('<p class="nohouse">您还没有发布房源哦~</p>');
			}
		}else{
			$('#main').html('<p class="nohouse">您还没有发布房源哦~</p>');
		}
	});



var noneImg = $('#staticUrl').val()+'/images/base1.jpg';
var noneImgSrc = 'this.src='+noneImg;
var source= $("#sourceType").val();//1:Android 2:iOS
//加载房源数据
function showList(list){
	var oViewPage=$('#pageView');
	if(list.length==0){
		oViewPage.html('没有更多房源了');
		return;
	}
	var str='';
	var date = new Date();
	var currentMonth = date.getMonth() + 1;
	$.each(list,function(i,n){
		var baseUrl = $("#picResourceUrl").val();
		var picSize = $("#picSize").val();
		var picUrl=n.defaultPic;
		var defaultPic=baseUrl+picUrl+picSize;
		var _url = SERVER_CONTEXT+"houseInput/"+LOGIN_UNAUTH+"/goToHouseUpdate?houseBaseFid="+n.houseBaseFid+"&houseRoomFid="+n.houseRoomFid+"&rentWay="+n.rentWay;
		if(picUrl==null){
			defaultPic=noneImg;
		}else{
			defaultPic=defaultPic+"."+picUrl.split(".")[1].toLowerCase(); 
		}
		
		if(n.roomStatus==41 || n.roomStatus==50){
			str+="<li class='end'>";
		}else{
			str+="<li>";
		}
		
		//过滤特殊字符，可能导致url截断
		var roomName = "";
		var houseName = "";
		var status = parseInt(n.status);
		
		if(n.roomName){
			roomName = n.roomName.replace("'","");
		}
		if(n.houseName){
			houseName = n.houseName.replace("'","")
		}
		str	+="<div class='content_box clearfix' onclick='window.location.href=\""+_url+"\"'>";
		str	+="<div class='img_box'>";
		str	+="<img src='"+defaultPic+"'  onerror='"+noneImgSrc+"'>";
		str	+="<span class='status'>"+n.statusName+"</span>";
		str	+="</div>";
		str	+="<div class='text_box'>";
		str	+="<h1>"+n.name+"</h1>";
		str	+="<p>浏览量：<span class='v'>"+n.housePv+"</span> 次</p>";
		str	+="<p>未来30天出租率：<span class='r'>"+n.houseBookRate+"%</span></p>";
		str	+="<p>客户评分：<span class='s'>"+n.houseEvaScore+"</span></p>";
		str	+="</div></div>";
		
		var rentWay = parseInt(n.rentWay);
		if(n.zoMobile !== undefined && n.zoMobile !== null && $.trim(n.zoMobile).length !== 0){
			var liObjString = "<li>";
			if(source == "1"){
				//liObjString += "<a href='javascript:;' onclick='callPhone("+n.zoMobile+");'>联系专员:"+n.zoName+"</a></li>";
				var tel = "4007711666";
				liObjString += "<a href='javascript:;' onclick='callPhone("+tel+");'>联系客服</a></li>";
	    	} else {
	    		/*var tel = "tel://"+n.zoMobile;
	    		liObjString += "<a href='"+tel+"' mce_href='"+tel+"'>联系专员:"+n.zoName+"</a></li>";*/
	    		var tel = "tel://4007711666";
	    		liObjString += "<a href='"+tel+"' mce_href='"+tel+"'>联系客服</a></li>";
	    	}
			str += "<ul class='btn_box add clearfix'>"
				
				if(status==10 || (rentWay == 1&&(n.houseRoomFid == null||n.houseRoomFid==""||n.houseRoomFid==undefined)) ){
					str += "<li><a href='javascript:roomTips();'>收益明细</a></li>";
					str += "<li><a href='javascript:roomTips();'>出租日历</a></li>";
				}else{
					str += "<li><a href='"+SERVER_CONTEXT+"profitMgt/"+LOGIN_UNAUTH+"/toProfitOrderList?force=1&houseBaseFid="+n.houseBaseFid+"&houseRoomFid="+n.houseRoomFid+"&month="+currentMonth+"&houseName="+houseName+"&roomName="+roomName+"&rentWay="+n.rentWay+"'>收益明细</a></li>";
					str += "<li><a href='"+SERVER_CONTEXT+"houseMgt/"+LOGIN_UNAUTH+"/calendarDetail?houseBaseFid="+n.houseBaseFid+"&houseRoomFid="+n.houseRoomFid+"&rentWay="+n.rentWay+"'>出租日历</a></li>";
				}
			    
			str +=liObjString;
		}else{
			str += "<ul class='btn_box clearfix'>";
				
			
			if(status==10 || (rentWay == 1&&(n.houseRoomFid == null||n.houseRoomFid==""||n.houseRoomFid==undefined))){
				str +="<li><a href='javascript:roomTips();'></i>收益明细</a></li>";
				str +="<li><a href='javascript:roomTips();'>出租日历</a></li>";
			}else{
				str +="<li><a href='"+SERVER_CONTEXT+"profitMgt/"+LOGIN_UNAUTH+"/toProfitOrderList?force=1&houseBaseFid="+n.houseBaseFid+"&houseRoomFid="+n.houseRoomFid+"&month="+currentMonth+"&houseName="+houseName+"&roomName="+roomName+"&rentWay="+n.rentWay+"'></i>收益明细</a></li>";
				str +="<li><a href='"+SERVER_CONTEXT+"houseMgt/"+LOGIN_UNAUTH+"/calendarDetail?houseBaseFid="+n.houseBaseFid+"&houseRoomFid="+n.houseRoomFid+"&rentWay="+n.rentWay+"'></i>出租日历</a></li>";
			}
		}
		str +="</ul></li>";
	});
	$("#houseList").append(str);
	myScroll.refresh();
	flag = true;
	setImgWidth();	
}

/**
 * 无房间的提示
 */
function roomTips(){
	showShadedowTips("请先发布房源",1);
}
// 安卓APP端调用打电话
function callPhone(phoneNum){
	window.WebViewFunc.callPhone(phoneNum.toString());
}
