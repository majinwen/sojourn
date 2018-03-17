var param = {};
var ms = {};
var flag;
(function($){
		
		ms.getCityList = function(){
			$.getJSON("/base/cityList",{},function(data){
				if(data.code == 0){
					var list = data.data.list;
					var liStr = "";
					var cityCode = $("#cityIpt").attr("data-citycode");
					$.each(list,function(i,n){
						if(cityCode == n.code){
							$("#cityIpt").val(n.name);
							$("#cityIpt").attr("data-citycode",n.code);
							liStr += '<li data-cityCode="'+n.code+'" class="current">'+n.name+'</li>';
						}else{
							liStr += '<li data-cityCode="'+n.code+'">'+n.name+'</li>';
						}
					});
					$("#cityList").append(liStr);
				}
			});
		}
		//获取参数
		ms.getParam = function(type){
			var cityCode = $("#cityIpt").attr("data-citycode");
			var startTime = $("#start").val();
			var endTime = $("#end").val();
			param.cityCode = cityCode;
			if(startTime != "" && startTime !="入住日期" && endTime != ""  && endTime != "离开日期"){
				param.startTime = startTime;
				param.endTime = endTime;
			}else{
				delete param.startTime;
				delete param.endTime;
			}
			if(type == 1){
				param.page = 1;
			}else{
				delete param.page;
			}
			
			var personCount = $("#personNumIpt").attr("data-person");
			var rentWay = $("#rent").val();
			var orderType = $("#preview").val();
			var startPrice = $("#startPrice").val();
			var endPrice = $("#endPrice").val();
			var lastPrice = $("#lastPrice").val();
			var q = $("#keywordIpt").val();
			if(personCount){
				param.personCount = personCount;
			}else{
				delete param.personCount;
			}
			if(rentWay && rentWay != -1){
				param.rentWay = rentWay;
			}else{
				delete param.rentWay;
			}
			if(orderType && orderType != -1){
				param.orderType = orderType;
			}else{
				delete param.orderType;
			}
			if(startPrice){
				param.priceStart = startPrice;
			}else{
				delete param.priceStart;
			}
			if(lastPrice){
				param.priceEnd = lastPrice;
			}else{
				delete param.priceEnd;
			}
			
			if($.trim(q) && q != "请输入商圈、景点、房间名、房东名等"){
				param.q = encodeURI(q);
			}else{
				delete param.q;
			}
			return param;
		}
		//第一次进入
		ms.init = function(){
			ms.getParam(1);
			addRequire();
			getHouseList(param);
		}
		//刷新房源
		ms.refreshSearchs = function(){
			ms.getParam(2);
			var str = "/search/rooms?"+$.param(param);
			window.location.href = str;
		}
		
		//显示搜索列表的标签
		ms.showIcon = function(item){
			var count = 0;
			var _li = "";
			if(item.isLock == 1){
				_li += "<li>智能锁</li>";
				count++;
			}
			_li += "<li>"+item.roomCount+"居</li>";
			count++;
			$.each(item.bedList,function(i,n){
				if(count == 4){
					return _li;
				}
				_li += "<li>"+n.num+n.name+"</li>";
				count++;
			});
			
			if(item.rentWay == 0){
				if(count >=4)return _li;
				if(item.balconyCount > 0){
					_li += "<li>"+item.balconyCount+"个阳台</li>";
					count++;
				}
			}
			if(item.rentWay == 1){
				if(count >=4) return _li;
				if(item.isBalcony ==1){
					_li += "<li>独立阳台</li>";
					count++;
				}
			}
			return _li;
		}
		
		//点击刷新刷新，重新请求房源
		$("#searchBtn").click(function(){
			ms.refreshSearchs();
		})
		
		ms.getCityList();
		
		ms.init();
		window.MS = ms;
	}(jQuery))
	
	
	function getHouseList(param){
		$.getJSON('/search/list',param,function(data){
			if(data.code == 0){
				var list = data.data.list;
				if(param.page ==1 && list.length == 0){
					$(".house_list").html('<p class="noMessage">对不起，没有找到合适房源，看看这些推荐吧~~</p>');
					flag = false;
					list = data.data.suggest;
				}
				if(list.length == 0){
						flag = false;
					}else{
						flag = true;
					}
				
				var str = "";
				$.each(list,function(i,n){
					 var durl = "/rooms/"+n.rentWay+"/"+n.fid;
					 str += '<div class="house_item clearfix">'
						    +'<a href="'+durl+'" class="disblock clearfix" target="_blank">'
		                    +'<div class="l clearfix">'
		                    +'<div class="img">'
		                    +  '<img src="'+n.picUrl+'">'
		                    +'</div>'
		                    +'<div class="txt">'
		                    +  '<h1>'+n.houseName+'</h1>'
		                    +  '<h3>'+n.houseAddr+'</h3>'
		                    +  '<div class="star_box clearfix">'
		                    +    '<span>'+n.rentWayName+'/'+(n.personCount == 0? '不限制人数':'可住'+n.personCount+'人')+'</span>'
//		                    +    '<ul class="stars clearfix" data-val='+n.evaluateScore+'>'
		                    +    '<ul class="stars clearfix" data-val='+n.realEvaluateScore+'>'
		                    +      '<li class="star"></li>'
		                    +      '<li class="star"></li>'
		                    +      '<li class="star"></li>'
		                    +      '<li class="star"></li>'
		                    +      '<li class="star"></li>'
		                    +    '</ul>'
//		                    +   ' <span class="score">'+n.evaluateScore+'分</span>'
		                    +   ' <span class="score">'+n.realEvaluateScore+'分</span>'
		                    +  '</div>'
		                    +  '<div class="price_box">'
		                    +    '<span>¥</span>'
		                    +    '<span class="price">'+Math.floor(n.price/100)+'</span>'
		                    +    '<span>/晚</span>'
		                    + ' </div>'
		                    +  '<ul class="tag_list">'
		                    +   MS.showIcon(n)
		                    +  '</ul>'
		                    +'</div>'
		                    +'</div>'
		                    +'<div class="r">'
		                    +  '<dt class="headimg">'
		                    +    '<img src='+n.landlordUrl+'>'
		                    +  '</dt>'
		                    +  '<dd>房东</dd>'
		                    +  '<dd class="name_dd">'+n.nickName+'</dd>'
		                    +'</div>'
		                    +'</a>'
		                  +'</div>';
				});
		          $(".house_list").append(str);
		          setStar();
			}
	});
}
