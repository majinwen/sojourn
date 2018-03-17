$(function(){
	var iWinHeight = $(window).height();
	    var str = '';
	    $(window).scroll(function(){
	    	var top = $(window).scrollTop()
	        var i_top = top +iWinHeight;
	        var _top = $('#loadMore').offset().top;
	        // 返回顶部
	        if(top >= iWinHeight){
	        	$("#goTop").show();
	        }else{
	        	$("#goTop").hide();
	        }
	        if(_top < i_top){
	          // TODO：ajax加载更多
	            str += '<li class="clearfix">'
					+			'<div class="img_box">'
					+				'<img class="setImg" src="images/house/house.jpg">'
					+				'<span class="state">待发布</span>'
					+			'</div>'
					+			'<div class="txt_box">'
					+				'<h1>王府井新中式雅致胡同风格美屋</h1>'
					+				'<h4>房源编码：2016fkdjfsdfa40080</h4>'
					+				'<div class="bar">'
					+					'<div class="bar_active" style="width:50%;"></div>'
					+				'</div>'
					+				'<p class="m">信息完整度：50%</p>'
					+				'<p>浏览量：30</p>'
					+				'<p>未来30天出租率：50％</p>'
					+				'<p>房客评分：5.0分</p>'
					+				'<div class="btn_box clearfix">'
					+					'<div class="btns"><a class="btn active">继续发布</a></div>'
					+					'<div class="btns"><a class="btn">预览</a></div>'
					+				'</div>'
					+			'</div>'
					+			'<div class="del_icon"></div>'
					+			'<div class="del_box">'
					+				'<div class="del_txt">'
					+					'<h1>删除房源</h1>'
					+					'<h3>确认要删除此房源么?删除后房源信息将无法恢复。</h3>'
					+					'<div class="btn_box">'
					+						'<div class="btns"><a class="btn active del_btn">删除</a></div>'
					+						'<div class="btns"><a class="btn back_btn">返回</a></div>'
					+					'</div>'
					+				'</div>'
					+			'</div>'
					+'</li>';
	                  $("#houseList").append(str);
	        }
	    })

			$(".part_r_tt_box li").each(function(){
				$(this).click(function(){
					$(this).addClass("current").siblings().removeClass("current");
				})
			})
			$(".ask").bind('click',function(){
				// TODO：审核未通过原因 调用方法showTap('str','id','time')
				showTap("审核未通过原因：信息填写不完整，请填写完整信息。","pro1","2000");
			})

			$("#houseList").delegate(".del_icon","click",function(){
				$(this).siblings(".del_box").show().stop().animate({"top":"0","height":"100%"},300);
			})
			$("#houseList").delegate(".back_btn","click",function(){
				$(this).parents(".del_box").hide().stop().animate({"top":"300px","height":"0"},300);
			})
			$("#houseList").delegate(".cancle_btn","click",function(){
				console.log($(this).parents("li").find(".del_box"));
				$(this).parents("li").find(".del_box").show().stop().animate({"top":"0","height":"100%"},300);
			})

			$("#houseList").delegate(".del_btn","click",function(){
				$(this).parents("li").remove();
			})
			$("#goTop").click(//定义返回顶部点击向上滚动的动画 
				function(){$('html,body').stop().animate({scrollTop:0},300); 
			}).html("TOP")
})