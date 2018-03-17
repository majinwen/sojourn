/**
 * Created by mqzZoom on 16/8/4.
 */
$(function(){
	
	//后退回显
	(function(){
		$('.J_tab_content input[type=hidden]').each(function(){
			if($(this).val()==1){
				$(this).parent().addClass('on');
			}
			else{
				$(this).parent().removeClass('on');
			}
		});
	})();
	
    //选择配置物品交互
    (function(){
    	//用户为点击的时候是false，点击后为true，返回结果后变回false
    	var isClick=false;
    	
        var oDiv=$('.J_tab_content');
        oDiv.click(function(){
            if($(this).hasClass('on')){
                $(this).removeClass('on');
                $(this).find('input[type=hidden]').val('0');
            }
            else{
                $(this).addClass('on');
                $(this).find('input[type=hidden]').val('1');
            }
        });

        //取消span点击默认事件
        $('span').click(function(e){
            e.preventDefault();
        });
        
        //下一步提交数据
        $('#J_next').click(function(){
        	var roomFid=$("#roomFid").val()
        	if(isClick){
        		return false;
        	}
        	var oDiv=$('.J_tab_content');
        	var oCode=[];
        	oDiv.each(function(){
        		var oVal=$(this).find('input[type=hidden]');
        		if(oVal.val()==1){
        			oCode[oCode.length]=oVal.attr("data-code");
        		}
        	}); 
        	
        	var paramData={"confCode":oCode.toString(),"fid":$("#houseFid").val()};
        	if(oCode.length == 0){
        		showConfirm("请选择配套设施","确定");
        		return;
        	}
        	
        	
        	isClick = true;
        	
        	CommonUtils.ajaxPostSubmit("/houseIssue/insertOrUpHouseConf",paramData,function(data){
             	if(data.code==0){
             		//console.log(data.data.confCode);
             		$("#confCodeList").val(data.data.confCode);
             		 if(roomFid==null||roomFid==""||roomFid==undefined){
             			window.location.href="/houseIssue/desc/"+$("#houseFid").val();
             		 }else{
             			window.location.href="/houseIssue/desc/"+$("#houseFid").val()+"?roomFid="+roomFid;
             		 }
             		
             	}else if(data.code == 2){
            		window.location.href = SSO_USER_LOGIN_URL+window.location.href;
            	}else{
             		showConfirm(data.msg,"确定");
             		isClick = false;
             	}
     		});
        });
    })();

});