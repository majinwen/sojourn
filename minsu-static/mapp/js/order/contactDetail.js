(function ($) {
	var customerVOUid = $('#customerVOUid').val();
	var orderSn = $("#orderSn").val();
	var sourceType = $("#sourceType").val();
	var versionCodeInit = $("#versionCodeInit").val();
	var contactDetail = {};
	contactDetail.flag=true;
	contactDetail.init = function(){
		var page=1;
		
		var oViewPage=$('#pageView');
		var iWinHeight = $(window).height();
		var top = $(window).scrollTop()+iWinHeight;
		contactDetail.evaList(1,5);

		//滚动加载	
		$(window).scroll(function(){
			top = $(window).scrollTop()+iWinHeight;		
			var _top = oViewPage.offset().top;
			if(_top < top && oViewPage.html()!=''){
				if(!contactDetail.flag){
					return;
				}
				page++;
				contactDetail.flag = false;
				
				contactDetail.evaList(page,5);
			}
		});
	}
	
	//房客对客户评价列表
	contactDetail.evaList = function(p,s){
		CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"landlordEva/43e881/tenEvaRecord",{page:p,size:s,ratedUserUid:customerVOUid,"orderSn":orderSn},function(data){
			if(data&&data.code == 0){
				data = data.data;
				var list = data.listEvaluateVos;
				var customer = data.customerVo;
				if(data.total == 0){
					$('#evaHistoryList').html('<p class="nodinping">暂无点评记录</p>');
					return;
				}
				

				$.each(list,function(i,n){

//					
//					var itemstr = 	"<div class='calendarTop clearfix ydrDpInfo'>"					
//									+"	<div class='img'><img src='"+n.picUrl+"'></div>"
//									+"<div class='txt'>"
//										+"<h3>"+n.houseName+"</h3>"
//										+"<p>"+n.startTimeStr+"</p>"
//									+"</div>"
//								+"</div>"
//								+"<div class='bgF'>"
//									+"<div class='lineD75'></div>"
//									+"<ul class='ydrDpList'>"
//										+"<li>"
//											+"<div class='img'><img src='"+n.userPicUrl+"'></div>"
//											+"<h3 class='tit'>"+customer.nickName+"</h3>"
//											+"<ul class='clearfix'>"
//												+"<li><span><i class='letter'>清</i><i class='letter'>洁</i>度</span> "+n.tenantEvaluateEntity.houseClean+"分</li>"
//												+"<li><span>周边环境</span>  "+n.tenantEvaluateEntity.trafficPosition+"分</li>"
//												+"<li><span>描述相符</span>  "+n.tenantEvaluateEntity.descriptionMatch+"分</li>"
//												+"<li><span><i class='letter'>性</i><i class='letter'>价</i>比</span>  "+n.tenantEvaluateEntity.costPerformance+"分</li>"
//												+"<li><span>房东印象</span>  "+n.tenantEvaluateEntity.safetyDegree+"分</li>"
//											+"</ul>"
//											+"<div class='lineD100'></div>"
//											+"<p>"+n.tenantEvaluateEntity.content+"</p>"
//											+"<p class='gray2'>"+CommonUtils.formateDate(n.tenantEvaluateEntity.lastModifyDate)+"</p>"
//										+"</li>"
//										+"<li>"
//											+"<div class='img'><img src='"+n.landlordPicUrl+"'></div>"
//											+"<h3 class='tit'>房东点评 <small>"+n.landlordSatisfied+"分</small></h3>"
//											+"<div class='lineD100'></div>"
//											+"<p>"+n.content+"</p>"
//											+"<p class='gray2'>"+CommonUtils.formateDate(n.lastModifyDate)+"</p>"
//										+"</li>"
//									+"</ul>"
//								+"</div>";
						
					var itemstr ='<div class="s_evaItem">'
									+'<div class="s_evaItemHead clearfix">'
										+'<div class="s_evaItemImg"><img src='+n.landlordPicUrl+' /></div>'
										+'<dl class="s_ItemDl">'
											+'<dt>'+n.landlordNickName+'</dt>'
											+'<dd>'+contactDetail.formateDate(n.lastModifyDate)+'</dd>'
										+'</dl>'
//										<!-- 根据得分设置class s_orgStar or s_grayStar -->
										+'<ul class="s_evaItemStar clearfix" data-val="'+n.landlordSatisfied+'"></ul>'
									+'</div>'
									
									+'<div class="s_evaItemMain clearfix">'+n.content+'</div>';
					
//					<!-- TODO:链接跳转 -->
					itemstr =itemstr+'<a class="s_evaItemLink" ';
					var rentWay =parseInt(n.rentWay);
					if(contactDetail.checkNullOrBlank(versionCodeInit)||isNaN(versionCodeInit) || versionCodeInit <100004){
						var houseDetailUrl ='#';
						if(rentWay==0 ||(rentWay==1 && !contactDetail.checkNullOrBlank(n.roomFid))  ){
							if(rentWay==0 ){
								houseDetailUrl = SERVER_CONTEXT+"tenantHouse/"+NO_LOGIN_AUTH+"/houseDetail?fid="+n.houseFid+"&rentWay="+rentWay+"&houseBaseFid="+n.houseFid+"&houseRoomFid="+n.roomFid;
							}else if(rentWay==1){
								houseDetailUrl = SERVER_CONTEXT+"tenantHouse/"+NO_LOGIN_AUTH+"/houseDetail?fid="+n.roomFid+"&rentWay="+rentWay+"&houseBaseFid="+n.houseFid+"&houseRoomFid="+n.roomFid;
							}
							
							itemstr =itemstr+' href="'+houseDetailUrl+'"';
						}
						
					}else{
						var onclick="";
						if(parseInt(sourceType) == 1){
							if(rentWay==0 ){
								onclick="window.WebViewFunc.toHouseDetail('"+n.houseFid+"','"+rentWay+"');"
							}else if(rentWay==1){
								onclick="window.WebViewFunc.toHouseDetail('"+n.roomFid+"','"+rentWay+"');"
							}
						}else if(parseInt(sourceType) == 2){
							if(rentWay==0 ){
								onclick="window.toHouseDetail('"+n.houseFid+"','"+rentWay+"');"
							}else if(rentWay==1){
								onclick="window.toHouseDetail('"+n.roomFid+"','"+rentWay+"');"
							}
						}
						
						itemstr =itemstr+' onclick="'+onclick+'"';
					}
					
					itemstr =itemstr+'  >'+n.houseName+'</a>'					
							+'</div>';
					
					$("#evaHistoryList").append(itemstr);
					contactDetail.flag = true;
				});
				contactDetail.setEvaStar();
			}else{
				$('#evaHistoryList').html('<p class="nodinping">暂无点评记录</p>');
				showShadedowTips("操作失败",2);
			}
		});
	}
	 
	contactDetail.checkNullOrBlank = function(param){
		return typeof(param) == 'undefined' || param == null || $.trim(param).length == 0;
	}
	
	contactDetail.setEvaStar = function () {
		$(".s_evaItemStar").each(function(){
			var num = 5;
			var starStr = '';
			var val = parseInt($(this).attr("data-val"),10);
			var _val = parseFloat($(this).attr("data-val"));
			for(var i = 0 ; i < val; i ++){
				starStr+='<li class="s_orgStar"></li>';
			}
			if(num != val){
				// 是否存在小数
				if(val < _val){starStr+='<li class="s_halfStar"></li>';}
				for(var j = 0 ; j < num - val;j++){
					starStr+='<li class="s_grayStar"></li>';
				}
			}
			$(this).html(starStr);
			
		})
	}
	
	contactDetail.formateDate =  function(value, row, index) {
		
		  if(value==null||value == ""||value==undefined){
			  return "-";
		  }
		  var date = new Date(value);
		  var month = date.getMonth()+1;
		  var day = date.getDate();
		  var hours = date.getHours();
		  var minutes = date.getMinutes();
		  var seconds = date.getSeconds();
		  month = month<10?'0'+month:month;
		  day = day<10?'0'+day:day;
		  hours = hours<10?'0'+hours:hours;
		  minutes = minutes<10?'0'+minutes:minutes;
		  seconds = seconds<10?'0'+seconds:seconds;
//		  return date.getFullYear()+"-"+month+"-"+day+"  "+hours+":"+minutes+":"+seconds;
		  return date.getFullYear()+"年"+month+"月";
	}
	
	
	contactDetail.init();
}(jQuery));
