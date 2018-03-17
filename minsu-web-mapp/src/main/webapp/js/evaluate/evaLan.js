(function ($) {
	
	var evaLan = {
		falg :true
	};
	//默认可以点击
	var canClick = true;
	/**
	 * 房东点评
	 */
	$("#submitStar").click(function(){
		if (!canClick){
			return;
		}
		canClick = false;
		var orderSn = $("#orderSn").val();
		var content = $("#evaContent").val();
		var landlordSatisfied = $("#stars").val();
		
		if((orderSn==null||orderSn==""||orderSn==undefined)||(content==null||content==""||content==undefined)||(landlordSatisfied==null||landlordSatisfied==""||landlordSatisfied==undefined)){
			showShadedowTips("提交参数错误",1);
			canClick = true;
			return false;
		}
		if(landlordSatisfied == 0){
			showShadedowTips("请选择星级",1);
			canClick = true;
			return false;
		}
		CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"landlordEva/"+LOGIN_UNAUTH+"/lanEvaluate", {"orderSn":orderSn,"content":content,"landlordSatisfied":landlordSatisfied}, function(data){
			if(data){
				if(data.code == 0){
					  showShadedowTips("评价成功",1);
					  window.location.reload();
				}else{
					showShadedowTips(data.msg,1);
				}
				canClick = true;
			}
		})
		
	});
	/**
	 * 评价初始化
	 */
	evaLan.init = function(){
		
		var conHeight = $(window).height()-$('.header').height()-$('.tabTit').height();
		$('#tabCon').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$('#con').css({'position':'absolute','height':conHeight,'width':'100%'});
		myScroll = new IScroll('#con', { scrollbars: true, mouseWheel: true });
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

		var page=1;
		
		var type=1;
		var iWinHeight = $(window).height();//浏览器当前窗口可视区域高度
		var top=0;
		var oViewPage = $('#pageView');


		$('#pingjiaTit li').click(function(){
			page=1;
			type = $(this).index()+1;
			$("#evaList").empty();
			evaLan.loadData(type,page);
		});	


		//滚动加载	
		myScroll.on('scroll', updatePosition);
			//myScroll.on('scrollEnd', updatePosition);

		function updatePosition () {
			conTop = this.y>>0;			
			if(Math.abs(conTop)>($('#tabCon').height()-conHeight) && !evaLan.flag){
				
				page++;
				evaLan.flag = false;
				evaLan.loadData(type,page);
				
			}
			
		}

		

		

	}
	/**
	 * 加载评价数据
	 * @param evaType  评价类型  1=待评价  2=已评价
	 * @param page  当前页
	 */
	evaLan.loadData = function(evaType,page){
		
		if((evaType == null||evaType==""||evaType==undefined) ||(page == null||page==""||page==undefined)){
			showShadedowTips("查询类型错误或者页码错误",1);
			return false;
		}
		
		CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"landlordEva/"+LOGIN_UNAUTH+"/queryEvaluate", {"evaType":evaType,"page":page}, function(data){
			if(data){
				if(data.code == 0){
					var pageResult= data.data.pageResult.rows;
					
					  var _html="";
					if(pageResult&&pageResult.length>0){
						for (var i = 0; i < pageResult.length; i++) {
							  _html +="<li orderSn="+pageResult[i].orderSn+" id='evaLan"+i+"'>";
							  _html +="<div class='t clearfix'><p class='fl'>"+pageResult[i].houseName+"</p> ";
							  
							  _html +="<span class='fr'>"+pageResult[i].evaTips+"</span></div>";
							 /* var evaStatus = pageResult[i].evaStatus;
							  if(evaStatus == 100 || evaStatus == 101){
								  _html +="<span class='fr'>房东未评价</span></div>";
							  }else if(evaStatus == 110){
								  _html +="<span class='fr'>房东已评价</span></div>";
							  }else{
								  _html +="<span class='fr'>房东、房客均已评价</span></div>";
							  }*/
							  _html +="<div class='c'>";
							 // _html +="<a href='' class='clearfix'>";
							  _html +="<div class='img'><img src='"+pageResult[i].userPicUrl+"' alt=''></div>";
							  _html +="<p class='mt'>"+pageResult[i].startTimeStr+"至"+pageResult[i].endTimeStr+"</p>";
							  _html +="<p>预定人："+pageResult[i].userName+" 共"+pageResult[i].peopleNum+"位入住</p>";
							  _html +="<span class='icon icon_r'></span>";
							 // _html +="</a>";
						      _html +="</div>";
						      _html +="</li>";
						}
						$("#evaList").append(_html);
						myScroll.refresh();	
						evaLan.flag = true;
					}else{
						
						if(page==1 && pageResult.length==0){
							$('#evaList').html('<p class="nopingjia">您还没有评价哦~</p>');
						}
						evaLan.flag = false;
					}
					imgCenter();
					$("*[id*='evaLan']").click(function(){
						window.location.href = SERVER_CONTEXT+"landlordEva/"+LOGIN_UNAUTH+"/queryEvaluateInfo?force=1&orderSn="+$(this).attr("orderSn");
					})
				}else{
					showShadedowTips(data.msg,1)
				}
			}
		});
		
	}
	evaLan.init();
	window.EvaLan = evaLan;
	
}(jQuery));