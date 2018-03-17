(function ($) {


	var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });
	var iWinHeight = $$(window).height();
	var top = $$(window).scrollTop()+iWinHeight;
	var conHeight = $$(window).height()-$$('.header').height()-$$('.box_bottom').height();
	var currRoomNum = $$('#sIpt').val();
	$$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
	$$('#main .box').css({'position':'absolute','width':'100%'});
	myScroll.refresh();
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

	var saveRoomInfoUrl = SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/saveRoomInfo";

	var urlParm={
		//户型切换
		updateHouseAndDelRoom : SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/deleteHouseRooms",
		roomBedZ:SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/roomBedZ",
		goToUpHousePic:SERVER_CONTEXT+"houseInput/"+LOGIN_UNAUTH+"/goToUpHousePic",
		checkHouseBed:SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/checkHouseBed",
		goToHouseUpdate:SERVER_CONTEXT+"houseInput/"+LOGIN_UNAUTH+"/goToHouseUpdate"
	}

	var roomInfoZ = {};

	var btnArray = ['确认', '取消'];


	/**
	 * 初始化
	 */
	roomInfoZ.init = function(){

		//roomInfoZ.loadData();
		var roomNum = $$("#sIpt").val();//卧室
		if(roomNum>0){
			$$('#baseHouseList').show();
			myScroll.refresh();
		}


		(function($) {
			$.init();
			var result = $('#houseBaseDataShow')[0];
			// var btns = $('.mui-btn');
			var btns = $('#houseBaseData');
			btns.each(function(i, btn) {
				btn.addEventListener('tap', function() {
					var oldS = Number($$("#sIpt").val());
					var optionsJson = this.getAttribute('data-options') || '{}';
					var options = JSON.parse(optionsJson);
					var id = this.getAttribute('id');
					var picker = new $.DtPicker(options);
					picker.show(function(rs) {
						var s = Number($$("#sIpt").val());
						if(oldS > s){ //当选择的房间数，小于上一步选择的房间数时
							layer.open({
								shade: true,
								shadeClose:true,
								content: '<div class="loginTips" style="line-height: 1.1rem">修改户型信息，会导致房间信息丢失，请确认是否继续修改？</div>',
								btn:['继续编辑', '放弃'],
								no: function(){
									$$("#sIpt").val(oldS);
									var text = $$("#sIpt").val()+'室'+$$("#tIpt").val()+'厅'+$$("#cIpt").val()+'厨'+$$("#wIpt").val()+'卫'+$$("#yIpt").val()+'阳台';
									$$('#houseBaseDataShow').val(text);
									$$('#houseBaseText').html(text);
								},
								yes: function(){
									roomInfoZ.loadData(picker,rs);
									layer.closeAll();
								}
							});
							/*mui.confirm('修改户型信息，会导致房间信息丢失，请确认是否继续修改？', '', btnArray, function(e) {
							 if (e.index == 1) { //这是取消
							 $$("#sIpt").val(oldS);
							 var text = $$("#sIpt").val()+'室'+$$("#tIpt").val()+'厅'+$$("#cIpt").val()+'厨'+$$("#wIpt").val()+'卫'+$$("#yIpt").val()+'阳台';
							 $$('#houseBaseDataShow').val(text);
							 $$('#houseBaseText').html(text);
							 }else {//这是确认
							 roomInfoZ.loadData(picker,rs);
							 }

							 });*/
						}else{
							roomInfoZ.loadData(picker,rs);
						}
					});
				}, false);
			});
		})(mui);

		//下一步 到第6步  说明：整租，1.必须要保证 有一个房间且有床，才能到下一步  2.并且 户型数量和房间数量必须保持一致
		$("#sentHouse").click(function(){
			var houseBaseFid = $("#fid").val();
			var flag = $("#flag").val();
			var houseRoomFid = $("#houseRoomFid").val();
			CommonUtils.ajaxPostSubmit(urlParm.checkHouseBed, {"houseBaseFid":houseBaseFid} ,function(data){

				if(data){
					var code = data.code;
					if(code == 0){
						var count = data.data.count;
						if(count>0){
							if(flag == 1){
								window.location.href = urlParm.goToHouseUpdate+"?houseBaseFid="+houseBaseFid+"&rentWay=0&houseRoomFid="+houseRoomFid
							}else{
								window.location.href = urlParm.goToUpHousePic+"?houseBaseFid="+houseBaseFid+"&rentWay=0&flag="+flag
							}

						}else{
							CommonUtils.showShadedowTips("请完善房间信息", 2)
						}
					}else{
						CommonUtils.showShadedowTips(data.msg, 2)
					}
				}
			})

		})
	}


	roomInfoZ.goToExt = function(){
		window.location.href=$("#toToExtUrl").val();
	}

	/**
	 * 加载房间数据
	 */
	roomInfoZ.loadData = function(picker,rs){


		var fid = $$("#fid").val();
		var roomNum = $$("#sIpt").val();//卧室
		var hallNum = $$("#tIpt").val();//客厅
		var toiletNum = $$("#wIpt").val();//卫生间
		var kitchenNum = $$("#cIpt").val();//厨房
		var balconyNum = $$("#yIpt").val();//阳台
		if(fid == null||fid==undefined||fid==""){
			return false;
		}
		var flag = $("#flag").val();
		CommonUtils.ajaxPostSubmit(urlParm.updateHouseAndDelRoom, {"fid":fid,"roomNum":roomNum,"hallNum":hallNum,"toiletNum":toiletNum,"kitchenNum":kitchenNum,"balconyNum":balconyNum}, function(data){

			if(data){
				var code = data.code;
				if(code == 0){
					var _html='';
					var roomList = data.data.HouseRoomInfoDto;
					var curRoomLen = roomList.length;
					var addNewRoomNum = roomNum - curRoomLen;

					for(var i=0;i<curRoomLen;i++){
						_html +="<li class='mui-table-view-cell'>";
						_html +="<div class='mui-slider-handle' onclick='window.location.href=&quot;"+urlParm.roomBedZ+"?roomFid="+roomList[i].fid+"&houseBaseFid="+fid+"&amp;index="+(i+1)+"&amp;flag="+flag+"&quot;'>";
						_html +="<span class='text'>";

						//+'<span class="c_span">房间 <span class="liIndex">'+(i+1)+'</span></span><span class="c_ipt">已完成</span>'
						if(roomList[i].roomFinishStatus == "已完成"){

							_html +="<span class='c_span'>房间 <span class='liIndex'>"+(i+1)+"</span></span><span class='c_ipt'>已完成</span>";
							//_html +="房间<span class='liIndex'>"+(i+1)+"</span>的信息</span>";
						}else{
							_html +="完善房间<span class='liIndex'>"+(i+1)+"</span>的信息</span>";
						}

						_html +="<span class='icon_r icon'></span>";
						_html +="</div>";
						_html +="</li>";
					}
					for(var i=curRoomLen;i<roomNum;i++){
						_html +="<li class='mui-table-view-cell'>";
						_html +="<div class='mui-slider-handle' onclick='window.location.href=&quot;"+urlParm.roomBedZ+"?houseBaseFid="+fid+"&amp;index="+(i+1)+"&amp;flag="+flag+"&quot;'>";
						_html +="<span class='text'>完善房间<span class='liIndex'>"+(i+1)+"</span>的信息</span>";
						_html +="<span class='icon_r icon'></span>";
						_html +="</div>";
						_html +="</li>";
					}

					$$('#baseHouseList').html('').append(_html).show();
					if(roomNum>0){
						$$('#baseHouseList').show();
					}

					myScroll.refresh();

					if(rs!=null&&rs!=""&&rs!=undefined){
						$$('#houseBaseDataShow').val(rs.text+'台');
						$$('#houseBaseText').html(rs.text+'台');
						$$('#houseBaseText').show();
						$$('.mui-btn').addClass('mui-btn-active');
					}
					if(picker!=null&&picker!=undefined&&picker!=""){
						picker.dispose();
					}

				}else{
					CommonUtils.showShadedowTips(data.msg, 2)
				}
			}
		})
	}

	roomInfoZ.init();


	window.RoomInfoZ = roomInfoZ;

}(jQuery));
