        var conHeight = $(window).height()-$('.header').height()-$('.bottomNav').height();
        
        
        $('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
        $('#main > div').css({'position':'absolute','height':conHeight,'width':'100%'});
        myScroll = new IScroll('#main > div', { scrollbars: true, mouseWheel: true , probeType: 3});
        document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		(function ($) {	
	
	var im = {
			flag :true,
			type:1,
			
	};
	var page = 1;
	/**
	 * IM初始化
	 */
	im.init = function(){

		var btnArray = ['确认', '取消'];
		//链接跳转
		$('#OA_task_1').on('tap','.msgList',function(){
			var elem = this;
			var _url=elem.getAttribute('data-url');
			window.location.href=_url;

		});


		//标记为已读
		$('#OA_task_1').on('tap','.mui-btn-grey',function(){
			var elem = this;
			var _id=document.getElementById('nums_'+elem.getAttribute('data-id'));
			var msgfid = elem.getAttribute('data-msgfid');
			var uid = $("#landlordUid_menu").val();
			//房客发的消息设置成已读
			$.post(SERVER_CONTEXT+"/im/"+LOGIN_UNAUTH+"/setLanMsgRead",{msgHouseFid:msgfid,msgSenderType:$("#msgType").val()},function(data){
				if(data.code == 0){
					var li = elem.parentNode.parentNode;
					mui.swipeoutClose(li);
					_id.parentNode.removeChild(_id);
					showAllUnReadMsgNum(uid);
				}else{
					showShadedowTips(data.msg,1);
				}
			},'json');
		});

		//删除
		$('#OA_task_1').on('tap', '.mui-btn-red', function(event) {
			var elem = this;
			var obj = $(this);
			var li = elem.parentNode.parentNode;
			mui.confirm('确认删除该条记录？', '提示', btnArray, function(e) {
				if (e.index == 0) {
					var msgHouseFid = obj.attr("msgHouseFid");
					CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"/im/"+LOGIN_UNAUTH+"/deleteIm", {"msgHouseFid":msgHouseFid,msgType:$("#msgType").val()}, function(data){
						if(data){
							if(data.code == 0){
								//im.loadData(1,page);
								li.parentNode.removeChild(li);
							}else{
								showShadedowTips(data.msg);
							}
						}
					})
					
				} else {
					setTimeout(function() {
						mui.swipeoutClose(li);
					}, 0);
				}
			});
		});

	
		

		myScroll.on('scroll', updatePosition);
			//myScroll.on('scrollEnd', updatePosition);

		function updatePosition () {
			
			conTop = this.y>>0;			
			if(Math.abs(conTop)>($('#OA_task_1').height()-conHeight) && im.flag){
				page++;
				im.loadData(im.type,page);
				
			}
			
		}
		
		im.loadData(1,1);
	}


	/**
	 * 加载评价数据
	 * @param imType  消息类型 （以后扩展用）
	 * @param page  当前页
	 */
	im.loadData = function(imType,page){

		if(page == null||page==""||page==undefined){
			showShadedowTips("页码错误",1);
			return false;
		}
		var version = $("#version").val();
		var imSourceList = $("#imSourceList").val();
		
		$.ajax({
		    type: "POST",
		    url: SERVER_CONTEXT+"im/"+LOGIN_UNAUTH+"/queryImList",
		    dataType:"json",
		    async: false,
		    data:  {"imType":imType,"page":page,"msgType":$("#msgType").val()},
		    success: function (data) {
		    	
		    	if(data){
					if(data.code == 0){
						var pageResult= data.data.listMsg;
						var _html="";
						var msgType  = parseInt($("#msgType").val());
						if(pageResult!=null&&pageResult!=undefined&&pageResult!=""&&pageResult.length>0){
							for (var i = 0; i < pageResult.length; i++) {

								 var url= $("#imUrl").val()+"/im/"+LOGIN_UNAUTH+"/queryMessageBase?msgSenderType="+msgType+"&msgHouseFid="+pageResult[i].msgHouseFid+"&uid="+pageResult[i].landlordUid+"&imSource=2&dSource=list&version="+version+"&imSourceList="+imSourceList;	
								_html +="<li class='mui-table-view-cell' style='z-index: 2;' id='im"+i+"' uid='"+pageResult[i].landlordUid+"' msgHouseFid='"+pageResult[i].msgHouseFid+"' >";
								_html +="<div class='mui-slider-right mui-disabled'>";
								_html +="<a class='mui-btn mui-btn-grey' data-msgfid='"+pageResult[i].msgHouseFid+"' data-id='"+i+"'>标为已读</a>";
								//_html +="<a class='mui-btn mui-btn-red'  msgHouseFid='"+pageResult[i].msgHouseFid+"' data-id='3'>删除</a>";
								_html +="</div>";
								_html +="<div class='mui-slider-handle'>";

								_html +="<div class='msgList clearfix' data-url='"+url+"'>";
								_html +="<div class='img'>";
								_html +="<img src='"+pageResult[i].tenantPicUrl+"'>";
								_html +="</div>";
								_html +="<div class='txt'>";
								_html +="<h3><span class='name'>"+pageResult[i].nickName+"</span> <span class='time'>"+CommonUtils.formateDay(pageResult[i].createTime)+"</span></h3>";
								if(pageResult[i].msgSenderType == 3){
									_html +="<p class='introduction'>咨询消息</p>";
								}else{
									_html +="<p class='introduction'>"+pageResult[i].msgContent+"</p>";
								}
								_html +="<p class='bottom clearfix'>";
								_html +="<span class='tag'>"+pageResult[i].houseName+"</span>";
								if(pageResult[i].unReadNum > 0){
									_html +="<span class='nums' id='nums_"+i+"'>"+pageResult[i].unReadNum+"</span>";
								}
								_html +="</p>";
								_html +="</div>";
								_html +="</div>";
								_html +="</div>";
								_html +="</li>";
							}
							$("#OA_task_1").append(_html);

							im.flag = true;
							myScroll.refresh();
						}else{
							
							if(data.data.listMsg==0 && page==1){
								$('#main').html('<div class="noxiaoxi">暂无消息</div>');
								$('body').removeClass('bgF');
							}
							im.flag = false;
							
						}
					}else{
						showShadedowTips(data.msg,1)
					}
				}
		    },
		    error: function(data) {
		       alert("error:"+data);
		        }
		     });
		

	}
	
	  //返回调用
	   im.goBack = function(){
		  var imSource = parseInt($("#imSource").val());
		  if(imSource == 1){
				window.WebViewFunc.popToParent();
			}else if(imSource == 2){
				popToParent();
			}
	  }
	im.init();
	window.Im = im;

}(jQuery));