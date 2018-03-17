(function ($) {
	var customerVOUid = $('#customerVOUid').val();
	var orderSn = $("#orderSn").val();
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
					return;
				}
				
				$.each(list,function(i,n){
					console.log(i);
					
					var itemstr = 	"<div class='calendarTop clearfix ydrDpInfo'>"					
									+"	<div class='img'><img src='"+n.picUrl+"'></div>"
									+"<div class='txt'>"
										+"<h3>"+n.houseName+"</h3>"
										+"<p>"+n.startTimeStr+"</p>"
									+"</div>"
								+"</div>"
								+"<div class='bgF'>"
									+"<div class='lineD75'></div>"
									+"<ul class='ydrDpList'>"
										+"<li>"
											+"<div class='img'><img src='"+n.userPicUrl+"'></div>"
											+"<h3 class='tit'>"+customer.nickName+"</h3>"
											+"<ul class='clearfix'>"
												+"<li><span>卫生环境</span> "+n.tenantEvaluateEntity.houseClean+"分</li>"
												+"<li><span>交通位置</span>  "+n.tenantEvaluateEntity.trafficPosition+"分</li>"
												+"<li><span>描述相符</span>  "+n.tenantEvaluateEntity.descriptionMatch+"分</li>"
												+"<li><span><i class='letter'>性</i><i class='letter'>价</i>比</span>  "+n.tenantEvaluateEntity.costPerformance+"分</li>"
												+"<li><span>安全程度</span>  "+n.tenantEvaluateEntity.safetyDegree+"分</li>"
											+"</ul>"
											+"<div class='lineD100'></div>"
											+"<p>"+n.tenantEvaluateEntity.content+"</p>"
											+"<p class='gray2'>"+n.tenantEvaluateEntity.lastModifyDate+"</p>"
										+"</li>"
										+"<li>"
											+"<div class='img'><img src='"+customer.userPicUrl+"'></div>"
											+"<h3 class='tit'>房东点评 <small>"+n.landlordSatisfied+"分</small></h3>"
											+"<div class='lineD100'></div>"
											+"<p>"+n.content+"</p>"
											+"<p class='gray2'>"+n.lastModifyDate+"</p>"
										+"</li>"
									+"</ul>"
								+"</div>";
						
					$("#evaHistoryList").append(itemstr);
					contactDetail.flag = true;
				});
				
			}else{
				showShadedowTips("操作失败",2);
			}
		});
	}
	contactDetail.init();
}(jQuery));