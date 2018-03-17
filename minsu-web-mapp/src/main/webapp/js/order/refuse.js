
$(function(){
	$("#reasonList li").each(function(){
		$(this).click(function(){
		// 出租日历冲突
		/*if($(this).attr("id") == 'conflictLi'){
			refuseOrderForCan($("#orderSn").val(),$(this).attr("value"),"");
		}*/
		if($(this).attr("value") == '1'){
			refuseOrderForCan($("#orderSn").val(),$(this).attr("value"),"");
			
		}else if($(this).attr("value") == '50'){
			$(".other_reason").show();
			$(".other_btn").show();
		}else{
			$(".other_reason").hide();
			$(".other_btn").hide();
			refuseOrder($("#orderSn").val(),$(this).attr("value"),"");
		}
		
		// 我对用户的安全、信用有疑问
		/*if($(this).attr("name") == 'commonLi'){
			refuseOrder($("#orderSn").val(),$(this).attr("value"),"");
		}*/
		
		// 其它原因
		/*if($(this).attr("id") == 'otherLi'){
			$(".other_reason").show();
			$(".other_btn").show();
		}else{
			$(".other_reason").hide();
			$(".other_btn").hide();
		}*/
		})
	})
	// 输入其他原因
	$("#reasonTxt").on('input',function(){
		if($(this).val() != ''){
			$("#submitBtn").removeClass("disabled_btn").addClass("org_btn");
		}else{
			$("#submitBtn").removeClass("org_btn").addClass("disabled_btn");
		}
	}).focus(function(){
		var top = $("#problemLi").offset().top;
		setTimeout(function(){
			$('body').css({'height':'2000px'});
			$('html,body').animate({scrollTop:top}, 200);
		},200)
	}).blur(function(){
		$('body').css({'height':'100%'});
		$('html').css({'height':'100%'});
	})
	// 保存其他原因 
	$("#submitBtn").click(function(){
		var reason = $("#reasonTxt").val();
		refuseOrder($("#orderSn").val(),50,reason);
	})
})


function dateConflict(start,ent){
	layer.open({
		    content:'<div class="conflict_message">请设置已出租日期的房屋日历！</div>	',
		    btn: ['去设置'],
		    shadeClose: false,
		    yes: function(){
		      	window.location.href = SERVER_CONTEXT+"houseMgt/43e881/calendarDetail?houseBaseFid="+$("#houseBaseFid").val()+"&houseRoomFid="+$("#houseRoomFid").val()+"&rentWay="+$("#rentWay").val();
		    }
		});
	/*$(".laymshade").click(function(){
		layer.closeAll();
	})*/
}
//去设置
$("#configBtn").click(function(){
	window.location.href = SERVER_CONTEXT+"houseMgt/43e881/calendarDetail?houseBaseFid="+$("#houseBaseFid").val()+"&houseRoomFid="+$("#houseRoomFid").val()+"&rentWay="+$("#rentWay").val()+"&backFalg=1";
	closeModalBox();
})
function refuseOrder(orderSn,value,refuseReason){
	$.post(SERVER_CONTEXT+"orderland/43e881/refusedOrder",{"orderSn":orderSn,"refuseCode":value,"refuseReason":refuseReason},function(data){
		if(data.code == 0){
			window.location.href = SERVER_CONTEXT+"orderland/43e881/showDetail?orderSn="+orderSn;
		}else{
			showShadedowTips(data.msg,1);
		}
	},'json');
}

function refuseOrderForCan(orderSn,value,refuseReason){
	$.post(SERVER_CONTEXT+"orderland/43e881/refusedOrder",{"orderSn":orderSn,"refuseCode":value,"refuseReason":refuseReason},function(data){
		if(data.code == 0){
			showModalBox('configModal');
		}else{
			showShadedowTips(data.msg,1);
		}
	},'json');
}
/*打开弹层*/
function showModalBox(id) {
	$('body').addClass('hidden');
	$('#modal').show();
	$('#' + id).show().siblings().hide();
}

/*关闭弹层*/
function closeModalBox() {
	$('body').removeClass('hidden');
	$('#modal').hide();
}