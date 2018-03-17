(function ($) {
	var flag = true;
	var houseMan = {};	
	var currPage = 0;
	var urlParam = {
		queryLanHouse:localhostPaht+"/house/queryLanHouse"
	}
	
	var optionFlag =true;
	 

	/**
	 * 房源管理初始化
	 */
	houseMan.init = function(){
		currPage++;
		houseMan.queryLanHouse(0,currPage);
		var iWinHeight = $(window).height();
	    var str = '';
	    $(window).scroll(function(){
	    	var top = $(window).scrollTop();
	        var i_top = top +iWinHeight;
	        var _top = $('#loadMore').offset().top;
	        // var rentWay =  $("#rentWay").val();
	        var rentWay = $(".part_r_tt_box").find("li.current").attr("rentWay");
	        // 返回顶部
	        if(top >= iWinHeight){
	        	$("#goTop").show();
	        }else{
	        	$("#goTop").hide();
	        }
	        //console.log(flag);
	        if(top > 0 && _top < i_top){

	        	if(flag){
	        		currPage++;
	        		flag = false;
	        		houseMan.queryLanHouse(rentWay, currPage);
	        	}
	        	
	        }
	    })
		
		$("#houseList").delegate(".state","mouseover",function(){
			if($(this).find(".ask").length !=0){
				var id = $(this).find(".ask").attr("id");
				showTap("很抱歉，您的房源未通过审核。自如民宿的管家会联系您并提供房屋升级的指导。期待早日相会，请继续加油哦！",id,"50000");
			}
		})
		$("#houseList").delegate(".state","mouseout",function(){
			if($(this).find(".ask").length !=0){
				layer.closeAll();
			}
		})
		$("#houseList").delegate(".del_icon","click",function(){
			$(this).siblings(".del_box").show().stop().animate({"top":"0","height":"100%"},300);
		})
		$("#houseList").delegate(".back_btn","click",function(){
			$(this).parents(".del_box").hide().stop().animate({"top":"300px","height":"0"},300);
		})
		$("#houseList").delegate(".cancle_btn","click",function(){
			$(this).parents("li").find(".del_box").show().stop().animate({"top":"0","height":"100%"},300);
		})
		$("#houseList").delegate(".del_btn","click",function(){
			$(this).parents("li").remove();
		})
		$(".part_r_tt_box").delegate("li","click" ,function(){
		   var rentWay = $(this).attr("rentWay");
		   $(this).addClass("current").siblings().removeClass("current");
		   flag = true;
		   currPage = 1;
		   $(".noHouse").remove();
		   $("#rentWay").val(rentWay);
		   $("#houseList").empty();
		   houseMan.queryLanHouse(rentWay, 1);
		})
		$("body").delegate(".reason_show","click",function(){
          	if($(this).hasClass("on")){
		    	$(this).removeClass("on").siblings(".reason").addClass("reason_overflow");
		    	$(this).find("a").html("展开");
		    	return;
		    }else{
		    	$(this).addClass("on").siblings(".reason").removeClass("reason_overflow");
		    	$(this).find("a").html("收起");
		    	return;
		    }
         });
		
	}
	
	houseMan.unfold=function(){
         $(".reason").each(function(){
         	if($(this).html().length > 170){
         		$(this).siblings(".reason_show").show();
         	}else{
         		$(this).removeClass("reason_overflow");
         		$(this).siblings(".reason_show").hide();
         	}
         });
	}
	//删除房源
	houseMan.del = function(houseFid,roomFid,rentWay,i){
		
		if(!optionFlag){
			showConfirm("您的操作过于频繁！","确定");
		}		
		optionFlag = false;
		
		var param = {};
		if(roomFid != null && roomFid !="" && roomFid !="null"){
			param.roomFid = roomFid;
		}
		param.houseFid = houseFid;
		param.rentWay = rentWay;
		$.post("/house/del",param,function(data){
			if(data.code == 0){
				var _id = "li_"+i;
				$("li").remove("#"+_id);
				$(".part_r_tt_box .current").find(".num").text($(".part_r_tt_box .current").find(".num").text()-1)
			}else{
				showConfirm(data.msg,"确定");
			}
			optionFlag = true;
		},'json');
	}
	//下架房源
	houseMan.down = function(houseFid,roomFid,rentWay){
		
		if(!optionFlag){
			showConfirm("您的操作过于频繁！","确定");
		}		
		optionFlag = false;
		
		$.post("/house/down",{rentWay:rentWay,houseFid:houseFid,roomFid:roomFid},function(data){
			if(data.code == 0){
				 $("#houseList").empty();
				//操作之后刷新
				currPage = 1;
				HouseMan.queryLanHouse(rentWay,currPage);
			}else{
				showConfirm(data.msg,"确定");
			}
			optionFlag = true;
		},'json');
	}
	//取消发布
	houseMan.cancle = function(houseFid,roomFid,rentWay){
		
		if(!optionFlag){
			showConfirm("您的操作过于频繁！","确定");
		}		
		optionFlag = false;
		
		$.post("/house/cancle",{houseFid:houseFid,roomFid:roomFid,rentWay:rentWay},function(data){
				if(data.code == 0){
					 $("#houseList").empty();
					//操作之后刷新
					currPage = 1;
					HouseMan.queryLanHouse(rentWay,currPage);
				}else{
					showConfirm(data.msg,"确定");
				}
				optionFlag = true;
			},'json');
		
	}
	//发布房源
	houseMan.release=function(houseFid,roomFid,rentWay){
		
		if(!optionFlag){
			showConfirm("您的操作过于频繁！","确定");
		}		
		optionFlag = false;
		
		$.post("/house/release",{houseFid:houseFid,roomFid:roomFid,rentWay:rentWay},function(data){
			if(data.code == 0){
				 $("#houseList").empty();
				//操作之后刷新
				currPage = 1;
				HouseMan.queryLanHouse(rentWay,currPage);
			}else{
				showConfirm(data.msg,"确定");
			}
			optionFlag = true;
		},'json');
	}
	/**
	 * 条件查询房东房源
	 */
	houseMan.queryLanHouse = function(rentWay,page){
		CommonUtils.ajaxPostSubmit(urlParam.queryLanHouse, {"rentWay":rentWay,"page":page},function(data){
			if(data){
				if(data.code == 0){
					var houseList = data.data.houseList;
					var total = data.data.total;
					var _html ="";
					var baseUrl = $("#picBaseAddrMona").val();
					var noneImg = $('#staticUrl').val()+'/images/common/default_house.jpg';
					var noneImgSrc = 'this.src='+noneImg;
					if(houseList.length == 0){
						flag = false;
						if(page == 1){
							$("#houseList").before("<div class='noHouse'>还没有发布房源哦</div>")
						}
						return;
					}else{
						flag = true;
					}
					for (var i = 0; i < houseList.length; i++) {
						var picUrl=houseList[i].defaultPic;
						var rentWay = houseList[i].rentWay;
						var houseFid = houseList[i].houseBaseFid;
						var roomFid = houseList[i].houseRoomFid;
                        if(typeof roomFid == "undefined" || roomFid == null){
                            roomFid = "";
                        }
						var status = houseList[i].houseStatus;
						var fid = "";
						if(rentWay == 1){
							fid= roomFid;
						}
						if(rentWay == 0){
							fid = houseFid;
						}
						//合租 房间没填完整 还没有房间
						if(rentWay == 1 && houseList[i].roomStatus == null){
							status = houseList[i].houseStatus;
						}
						if(rentWay == 1 && houseList[i].roomStatus != null){
							status = houseList[i].roomStatus;
						}
						
						var defaultPic = "";
						
						if(picUrl==null){
							defaultPic=noneImg;
						}else{
							defaultPic=baseUrl+picUrl+"_Z_720_480.jpg"; 
						}
						_html +="<li class='clearfix unsend'>";
						//审核中房源不能修改
						if(status ==11){
							_html +="<a onclick=\"showConfirm('无法修改审核中房源信息，可预览','确定');\"><div class='img_box'>";
						}else{
							if(rentWay == 0){
								_html +="<a href='"+localhostPaht+"/house/"+houseFid+"/goIssue\'><div class='img_box'>";
							}else{
								_html +="<a href='"+localhostPaht+"/house/"+houseFid+"/goIssue?roomFid="+roomFid+"\'>"+"<div class='img_box'>";
							}
						}
						_html +="<img class='setImg' src='"+defaultPic+"'>";
						// _html +="<span class='state'>"+houseMan.getHouseStatuName(status)+"</span>";
						if(status == 21){
							_html +="<span class='state'>"+houseMan.getHouseStatuName(status)+"<i id='ask"+i+"_"+page+"' class='icon ask'></i>"+"</span>";
						}else{
							_html +="<span class='state'>"+houseMan.getHouseStatuName(status)+"</span>";
						}
						_html +="</div></a>";
						_html +="<div class='txt_box'>";
						//审核中房源不能修改
						if(status ==11){
							_html +="<a href='javascript:;'><h1>"+(houseList[i].name == null ? "未完成发布的美居":houseList[i].name)+"</h1></a>";
						}else{
							_html +="<a href='"+localhostPaht+"/house/"+houseFid+"/goIssue?roomFid="+roomFid+"'><h1>"+(houseList[i].name == null ? "未完成发布的美居":houseList[i].name)+"</h1></a>";
						}
						_html +="<h4>房源编码："+houseList[i].houseSn+"</h4>";
						_html +="<div class='bar'>";
						_html +="<div class='bar_active' style='width:"+houseList[i].intactRate*100+"%;'></div>";
						_html +="</div>";
						_html +="<p class='m'>信息完整度："+houseList[i].intactRate*100+"%</p>";
						_html +="<p>浏览量："+houseList[i].housePv+"</p>";
						_html +="<p>未来30天出租率："+houseList[i].houseBookRate+"％</p>";
						_html +="<p>房客评分："+houseList[i].houseEvaScore+"</p>";
						_html +="<div class='btn_box clearfix'>";
						_html +=houseMan.getOptionByHouseStatu(status,houseFid,roomFid,rentWay);
						if(fid != null && fid != ""){
							_html +="<div class='btns'><a target= '_blank' href='/preview/"+rentWay+"/"+fid+"' class='btn'>预览</a></div>";
						}
						_html +="</div>";
						_html +="</div>";
						//待发布、已下架、强制下架、审核未通过房源可删除
						if(status==10 || status ==21 || status ==30 || status==41 || status==50){
							_html +="<div class='del_icon'></div>";
							_html +="<div class='del_box'>";
							_html +="<div class='del_txt'>";
							_html +="<h1>删除房源</h1>";
							_html +="<h3>确认要删除此房源么？删除后房源信息将无法恢复。</h3>";
							_html +="<div class='btn_box'>";
							_html +="<div class='btns'><a href='javascript:;' onclick='HouseMan.del(\""+houseFid+"\",\""+roomFid+"\","+rentWay+","+i+")' class='btn active del_btn'>删除</a></div>";
							_html +="<div class='btns'><a class='btn back_btn'>返回</a></div>";
							_html +="</div>";
							_html +="</div>";
							_html +="</div>";
							if(status==30){
								_html+="<div class='reason_box'><div class='reason reason_overflow'><span>审核未通过原因：</span>"+houseList[i].refuseReason;
								_html+="</div><div class='reason_show'><a href='javascript:;'>展开</a></div></div>"
							}
						}
						if(status == 40){
							_html +="<div class='del_icon'></div>";
							_html +="<div class='del_box'>";
							_html +="<div class='del_txt'>";
							_html +="<h1>下架房源</h1>";
							_html +="<h3>确认下架此房源么？</h3>";
							_html +="<div class='btn_box'>";
							_html +="<div class='btns'><a href='javascript:;' onclick='HouseMan.down(\""+houseFid+"\",\""+roomFid+"\","+rentWay+")' class='btn active del_btn'>下架</a></div>";
							_html +="<div class='btns'><a class='btn back_btn'>返回</a></div>";
							_html +="</div>";
							_html +="</div>";
							_html +="</div>";
						}
						if(status == 11 || status == 20){
							_html +="<div class='del_box'>";
							_html +="<div class='del_txt'>";
							_html +="<h1>取消发布房源</h1>";
							_html +="<h3>确认要取消发布房源申请么？取消后房源无法进行审核上架！</h3>";
							_html +="<div class='btn_box'>";
							_html +="<div class='btns'><a href='javascript:;' onclick='HouseMan.cancle(\""+houseFid+"\",\""+roomFid+"\","+rentWay+")' class='btn active del_btn'>取消发布</a></div>";
							_html +="<div class='btns'><a class='btn back_btn'>返回</a></div>";
							_html +="</div>";
							_html +="</div>";
							_html +="</div>";
						}
					   //待发布、已下架、强制下架、审核未通过、房源 独有代码 
					    _html +="</li>";
					}
					$("#houseList").append(_html);
					houseMan.unfold();
				}else if(data.code == 2){
            		window.location.href = SSO_USER_LOGIN_URL+window.location.href;
            	}else{
					showConfirm(data.msg,"确定");
				}
			}
		})
			
	}
	
	/**
	 * 根据房源状态展示状态展示
	 * 房源状态 10：待发布，11：已发布，30：品质审核未通过，40：上架，41：下架，50：强制下架
	 * 1、房源状态：待发布、审核中、审核未通过（品质审核未通过）、已上架（品质审核通过）、已下架、强制下架
	 */
	houseMan.getHouseStatuName = function(houseVal){
		
		if(houseVal==null||houseVal==""||houseVal==undefined){
			return "";
		}
		var houseValInt = parseInt(houseVal);
		var hosueName = "";
		switch (houseValInt) {
		case 10:
			hosueName = "待发布"
			break;
	    case 11:
	    	hosueName = "审核中"
			break;
	    case 20:
	    	hosueName = "审核中"
		   break;
	    case 21:
		   hosueName = "审核未通过"
			break;
	    case 30:
		   hosueName = "审核未通过"
			break;
	    case 40:
	    	hosueName = "已上架"
		   break;
	    case 41:
		   hosueName = "已下架"
			break;
	    case 50:
		   hosueName = "强制下架"
			break;
		default:
			break;
		}
		return hosueName;
	}
	//根据房源状态获取操作
	houseMan.getOptionByHouseStatu =  function(status,houseFid,roomFid,rentWay){
		if(status==null||status==""||status==undefined){
			return "";
		}
		var houseValInt = parseInt(status);
		var hosueHtml= "";
		switch (houseValInt) {
		
	   case 11:
	   case 20:
		   var url = localhostPaht+"/house/"+rentWay+"/";
		   if(rentWay == 0){
			   url += houseFid+"/calendar";
		   }else if(rentWay == 1){
			   url += roomFid+"/calendar";
		   }
		   hosueHtml = "<div class='btns'><a class='btn active cancle_btn' >取消发布</a></div>";
		   hosueHtml += "<div class='btns'><a class='btn active' href='"+url+"'>出租日历</a></div>";
		   break;
	   case 40:
		   var url = localhostPaht+"/house/"+rentWay+"/";
		   if(rentWay == 0){
			   url += houseFid+"/calendar";
		   }else if(rentWay == 1){
			   url += roomFid+"/calendar";
		   }
		   hosueHtml = "<div class='btns'><a class='btn active' href='"+url+"'>出租日历</a></div>";
		   break;
	   case 10:
		   if(roomFid==null||roomFid==""||roomFid==undefined){
			   var _url = localhostPaht+"/house/"+houseFid+"/goIssue";
		   }else{
			   var _url = localhostPaht+"/house/"+houseFid+"/goIssue"+"?roomFid="+roomFid;
		   }
		   hosueHtml ="<div class='btns'><a class='btn active' href='"+_url+"'>继续发布</a></div>";
		   break;
	   case 21:
	   case 30:
	   case 41:
	   case 50:
		   if(roomFid==null||roomFid==""||roomFid==undefined){
			   var _url = localhostPaht+"/house/"+houseFid+"/goIssue";
		   }else{
			   var _url = localhostPaht+"/house/"+houseFid+"/goIssue"+"?roomFid="+roomFid;
		   }
		   hosueHtml ="<div class='btns'><a class='btn active' href='"+_url+"'>继续发布</a></div>";
		   var url = localhostPaht+"/house/"+rentWay+"/";
		   if(rentWay == 0){
			   url += houseFid+"/calendar";
		   }else if(rentWay == 1){
			   url += roomFid+"/calendar";
		   }
		   hosueHtml += "<div class='btns'><a class='btn active' href='"+url+"'>出租日历</a></div>";
		   break;
		default:
			break;
		}
		return hosueHtml;
	}
	
	houseMan.init();
	window.HouseMan = houseMan;
	
}(jQuery));