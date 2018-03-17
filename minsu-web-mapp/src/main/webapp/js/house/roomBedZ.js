(function ($) {  	

	
	

	/**
	 * 整租添加房间初始化
	 */
	roomBedZ.init = function(){
		
		var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });

		var conHeight = $(window).height()-$('.header').height()-$('.bottomBtns').height();

		$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$('#main .box').css({'position':'absolute','width':'100%'});
		myScroll.refresh();
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		$("#submitBtn").click(function(){
			var rentType = $("#rentType").val();
			var beds = $(".j_ipt_li").length;
			if(rentType == 0){//整租

			}else if(rentType == 1){//分租
				if(beds < 1){
					mui.confirm('您至少可添加1个床位！', '', ['关闭'], function(e) {});
				}
			}
		})

	}
	roomBedZ.init();


	window.RoomBedZ = roomBedZ;

}(jQuery));