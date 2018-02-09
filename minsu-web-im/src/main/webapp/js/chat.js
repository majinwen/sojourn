var page=0;
var sendNum = parseInt($("#sendNum").val());	
$(function(){
	
	   var oViewPage=$('#pageView');
		var type=1;
		// 粘贴事件监控
		$.fn.pasteEvents = function( delay ) {
			if (delay == undefined) delay = 10;
			return $(this).each(function() {
				var $el = $(this);
				$el.on("paste", function() {
					$el.trigger("prepaste");
					setTimeout(function() { $el.trigger("postpaste"); }, delay);
				});
			});
		};
		// 使用
		$("input[type='text']").on("postpaste", function() { 
		    // code...
		    checkIpt();
		}).pasteEvents();
		
		//点击返回按钮 设置消息为已读
		$("#goback").click(function(){
			chatPhyReturn();
		});
		
		
		
		var now,now_y,now_m,now_d,now_time_h,now_time_m,date ,time;
		  
	     $("#sentBtn").click(function(){
	    	/* if($("#sentBtn").hasClass("btn_gray")){
	 			return false;
	 		}*/
	    	  var msgHouseFid = $("#msgHouseFid").val();
	    	  var msgContent =  $("#txtIpt").val();
	    	  
	    	  if(msgContent == ""  ||msgContent == null ||msgContent==undefined||msgContent.length>500){
				  return false
			  }
			  
			  if(msgHouseFid!=null && msgHouseFid !=""){
				  sendNum++;
				  var params={
						  msgContent:msgContent,
						  msgSentType:$(this).attr("msgSentType"),
						  msgHouseFid: msgHouseFid,
						  sendNum:sendNum,
						  uid:$("#uid").val(),
						  jpushFlag:$("#jpushFlag").val()
				  }
				  $("#txtIpt").val("");
				  CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"im/"+LOGIN_UNAUTH+"/saveMsgBase",params, function(data){
					  if(data){
						    $("#txtIpt").attr("style","height: auto; padding: 8px 0;");
						    $("#txtIpt").attr("rows",1);
						  CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"im/"+LOGIN_UNAUTH+"/listMsgBaseByTime", {"msgHouseFid":msgHouseFid,"page":page,"uid":$("#uid").val()}, listMsgBase);
					  }
				  });
			  }
		  });
	    
	     $("#loadMore").click(function(){
	    	 showList();
	     })
	    if($("#isImOpen").val() == 0){
	    	showList();
	    }
		function showList(){
			page++;
			CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"im/"+LOGIN_UNAUTH+"/listMsgBaseByTime", {"msgHouseFid":$("#msgHouseFid").val(),"page":page,"uid":$("#uid").val()}, listMsgBase);
			
		}
	})
var appFlag = 0;//定时任务标示
function checkIpt(){
	var value = $("#txtIpt").val();
	if(value == " " || value == ""){
		$("#sentBtn").removeClass("btn_org").addClass("btn_gray");
	}else{
		$("#sentBtn").addClass("btn_org").removeClass("btn_gray");
	}
}
//for andorid 物理键返回	
function chatPhyReturn(){
	var imSource = $("#imSource").val();
	var dSource = $("#dSource").val();
	var MAPP_DOMAIN = $("#MAPP_DOMAIN").val();
	//设置消息已读标志
	$.post(SERVER_CONTEXT+"im/"+LOGIN_UNAUTH+"/setMsgRead",{"msgHouseFid":$("#msgHouseFid").val(),"msgSenderType":$("#msgSenderType").val(),"uid":$("#uid").val()},function(){
	});
	if(dSource == 'list'){
	
		var version = $("#version").val();
		var imSourceList = $("#imSourceList").val();
		var msg  = parseInt($("#msgSenderType").val());
		if(msg == 1){
			window.location.href = MAPP_DOMAIN+"/im/43e881/imIndex";
		}else{
			window.location.href = MAPP_DOMAIN+"/im/43e881/imIndex?msgType=2&version="+version+"&imSourceList="+imSourceList;
		}
		
	}else {
		if(imSource == 1){
			window.WebViewFunc.popToParent();
		}else if(imSource ==2){
			history.go(-1);
		}else if(imSource == 4){
			popToParent();
		}
	}
	
}

	// 输入对话后滚到对话最底部
	function scrollBottom(){
		var div = document.getElementById('scrolldIV');
		div.scrollTop = div.scrollHeight;
	}
	// 显示时间
	function showTime(){
		var len,odd;
		var nowTimes = date+' '+time;
		len = $("#messageBox").find(".item").length;
		if(len == 0){
			addTime(date,time);
			return false;
		}
		odd = $("#messageBox").find(".item").eq(len-1);
		
		var oddTimes = odd.attr("data-time");
		console.log(oddTimes,nowTimes);
		var time_difference = transdate(nowTimes) - transdate(oddTimes);
		if(time_difference > 60){
			addTime(date,time);
		}
	}
	function addTime(date,time){
		var time_string = '';
		time_string+='<div class="item_time" >'
		+	'<span class="date">'
		+		date
		+	'</span>'
		+	'<span class="time">'
		+		time
		+	'</span>'
		+'</div>';
		$("#messageBox").append(time_string);
	}
	function getNowTime(){
		now = new Date();
		now_y = now.getFullYear();
		now_m = checkTen(now.getMonth()+1);
		now_d = checkTen(now.getDate());
		now_time_h = checkTen(now.getHours());
		now_time_m = checkTen(now.getMinutes());
		date = now_y+'-'+now_m+'-'+now_d;
		time = now_time_h+':'+now_time_m;
	}
	
	function transdate(endTime){ 
		var date=new Date(); 
		date.setFullYear(endTime.substring(0,4)); 
		date.setMonth(endTime.substring(5,7)-1); 
		date.setDate(endTime.substring(8,10)); 
		date.setHours(endTime.substring(11,13)); 
		date.setMinutes(endTime.substring(14,16)); 
		date.setSeconds(endTime.substring(17,19)); 
		return Date.parse(date)/1000; 
	} 
	function checkTen(obj){
		if(obj< 10){
			return '0'+obj;
		}else{
			return obj;
		}
	}
	// 错误提示方法
	function showShadedowTips(str,time){
		if(time){
			layer.open({
				content: str,
				shade:false,
				style: 'background:rgba(0,0,0,.6); color:#fff; border:none; margin-top:50px;',
				time:time   
			});
		}else{
			layer.open({
				content: str,
				shade:false,
				style: 'background:rgba(0,0,0,.6); color:#fff; border:none; margin-top:50px;'

			});
		}

	}
	  //封装数据
	  function listMsgBase(data){
		  if(data){
			  var lisBaseEntities = data;
			  var msgSenderType = parseInt($("#msgSenderType").val());
			  if(lisBaseEntities.length>0){
				  $("#messageBox").empty();
				  var _html="";
				  for (var i = 0; i < lisBaseEntities.length; i++) {
					  
					if(lisBaseEntities[i].msgSenderType != 10 && lisBaseEntities[i].msgSenderType != 20){
					  
					  if(lisBaseEntities[i].msgSenderType != 3){//普通消息
						  if(i == 0){
							 // _html +="<div class='item_time' ><span class='date'>"+CommonUtils.formateDate(lisBaseEntities[i].createTime)+"</div>";
							  _html +="<div class='item_time' ><span class='date'></div>";
						  }
						  _html +="<div class='item clearfix' data-time ='2016-02-03 12:00'>";
						  
						  //消息发送人类型（1=房东 2=房客） 
						  if(msgSenderType == 1){
							  if(lisBaseEntities[i].msgSenderType == 1){
								  _html +="<div class='right'>";
							  }else{
								  _html +="<div class='left'>";
								 
							  }
						  }else {
							  if(lisBaseEntities[i].msgSenderType == 1){
								  _html +="<div class='left'>";
							  }else{
								  _html +="<div class='right'>";
								 
							  }
						  } 
						 _html +="<div class='headImg'>";
						 if(lisBaseEntities[i].msgSenderType == 1){
							 _html +="<img src='"+$("#lanlordUrl").val()+"'>";
						 }else{
							 _html +="<img src='"+$("#tenantUrl").val()+"'>"; 
						 }
						
					     _html +="</div>";
						 _html +="<div class='message'>"+lisBaseEntities[i].msgContent+"</div>";
						 _html +="</div>";
						 _html +="</div>";
				  }else if(lisBaseEntities[i].msgSenderType == 3){
					  var bookAdviceMsg=lisBaseEntities[i];
					  var bookAdviceContent = eval("("+bookAdviceMsg.msgContent+")");
					  _html +="<div class='item_time's>";
					  _html +="咨询已发送";
					  _html +="<br>";
					  _html +=""+bookAdviceContent.startTime+"&nbsp;-&nbsp;"+bookAdviceContent.endTime+","+bookAdviceContent.peopleNum+"位房客 ";
					  _html +="</div>";
					  
					  if(bookAdviceContent.tripPurpose !=null&&bookAdviceContent.tripPurpose!=""&&bookAdviceContent.tripPurpose!=undefined){
						  _html +="<div class='item clearfix' data-time ='2016-02-03 12:00'>";
						  if(msgSenderType == 1){
							  _html +="<div class='left'>";
						  }else {
							  _html +="<div class='right'>";
						  } 
						  _html +="<div class='headImg'>";
						  _html +="<img src='"+$("#tenantUrl").val()+"'>"; 
						  _html +="</div>";
						  _html +="<div class='message'>"+bookAdviceContent.tripPurpose+"</div>";
						 _html +="</div>";
						 _html +="</div>";
					  }
					  
				  }
				 }  
				}
			    $("#messageBox").html(_html); 
			    scrollBottom();
			    
			  }
			 
		  }
	  }
	  function myrefresh(){ 
		  var msgHouseFid = $("#msgHouseFid").val();
		  
		  if(msgHouseFid !=null&&msgHouseFid !=""){
			  if(appFlag==0){
				  CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"im/"+LOGIN_UNAUTH+"/listMsgBaseByTime",{"msgHouseFid":msgHouseFid,"page":page,"uid":$("#uid").val()}, listMsgBase);
			  }
		  }
		
	  } 
	  //返回调用
	  function goBack(){
		  var imSource = parseInt($("#imSource").val());
		  if(imSource == 1){
				window.WebViewFunc.popToParent();
			}else if(imSource == 4){
				popToParent();
			}
	  }
	  //appflag 标示
	  function setAppFlag(flag){
		  appFlag = flag;
	  }
	  //校验 msgContent 
	  function checkMsgContent(msgContent){
		  
		  if(msgContent == ""  ||msgContent == null ||msgContent==undefined){
			  return false
		  }
		  
		  
		  var contentArr = ["手机号","微信","wenxin","wx","字母","数字"];
		  for (var i = 0; i < contentArr.length; i++) {
			if(contentArr[i] ==msgContent ){
				return false;
			}
		}
		  
		 if(checkMobile(msgContent)){
			    return false;
		 }
		 var b = /^[0-9a-zA-Z]*$/g;
		 if(b.test(msgContent)){
			 return false;
		 }
		  return true;
	  }
	//完成预定按钮回调  1.  fid   2. rentWay 3. startTime  4. endTime 5. lName        //房东名   6. tripPurpose     //出行目的  
	  function finishBook(){
		  var msgHouseFid = $("#msgHouseFid").val();
		  var fid =  $("#fid").val();
		  var rentWay =  $("#rentWay").val();
		  var lName =  $("#lanlordName").val()==null?"error":$("#lanlordName").val();
		  if(msgHouseFid !=null&&msgHouseFid !=""){
			  CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"im/"+LOGIN_UNAUTH+"/queryMsgBook",{"msgHouseFid":msgHouseFid,"uid":$("#uid").val()}, function(data){
				  if(data){
					  var imSource = parseInt($("#imSource").val());
					  var imSourceList = parseInt($("#imSourceList").val());
					  var startTime = data.startTime==null?"error":data.startTime;
					  var endTime = data.endTime==null?"error":data.endTime;
					  var tripPurpose = data.tripPurpose==null?"error":data.tripPurpose;
					  if(imSource == 1 ){
						  window.WebViewFunc.toCreateOrderPage(fid,rentWay,startTime,endTime,lName,tripPurpose);
						}else if(imSource == 4){
							toCreateOrderPage(fid,rentWay,startTime,endTime,lName,tripPurpose);
						}else{
							if(imSourceList == 1 ){
								  window.WebViewFunc.toCreateOrderPage(fid,rentWay,startTime,endTime,lName,tripPurpose);
							}else if(imSourceList == 4){
								toCreateOrderPage(fid,rentWay,startTime,endTime,lName,tripPurpose);
							}
						}
					  
				  }
			  });
		  }
	  }  
	//完成预定按钮回调  1.  fid   2. rentWay 3. startTime  4. endTime 5. lName        //房东名   6. tripPurpose     //出行目的  
/*	$("#bookComplate").click(function(){
		
		  var msgHouseFid = $("#msgHouseFid").val();
		  var fid =  $("#fid").val();
		  var rentWay =  $("#rentWay").val();
		  var lanlordName =  $("#lanlordName").val();
		  alert(msgHouseFid);
		  if(msgHouseFid !=null&&msgHouseFid !=""){
			  CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"im/"+LOGIN_UNAUTH+"/queryMsgBook",{"msgHouseFid":msgHouseFid}, function(data){
				  if(data){
					  var imSource = parseInt($("#imSource").val());
					  var imSourceList = parseInt($("#imSourceList").val());
					  alert(imSourceList);
					  if(imSource == 1 ){
						  window.WebViewFunc.toCreateOrderPage(fid,rentWay,data.startTime,data.endTime,lanlordName,data.tripPurpose);
						}else if(imSource == 4){
							toCreateOrderPage(fid,rentWay,data.startTime,data.endTime,lanlordName,data.tripPurpose);
						}else{
							if(imSourceList == 1 ){
								  window.WebViewFunc.toCreateOrderPage(fid,rentWay,data.startTime,data.endTime,lanlordName,data.tripPurpose);
							}else if(imSourceList == 4){
								toCreateOrderPage(fid,rentWay,data.startTime,data.endTime,lanlordName,data.tripPurpose);
							}
						}
					  
				  }
			  });
		  }
		 
		
	});*/
	  
	 //setInterval(myrefresh,5000);
