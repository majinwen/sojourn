$(function(){
    
	//回显
	(function(){
        $('textarea').each(function(){
            $(this).siblings('.J_tongji').find('span').eq(0).html($(this).val().length);

            if($(this).val().length>$(this).attr('data-total')){
                $(this).siblings('.J_tongji').find('span').eq(0).addClass('on');
            }
            else{
                $(this).siblings('.J_tongji').find('span').eq(0).removeClass('on');
            }
        });
    })();

    //输入框
    (function(){

        $('textarea').each(function(){

            $(this).bind('input propertychange',function(){

                $(this).siblings('.J_tongji').find('span').eq(0).html($(this).val().length);

                if($(this).val().length>$(this).attr('data-total')){
                    $(this).siblings('.J_tongji').find('span').eq(0).addClass('on');
                }
                else{
                    $(this).siblings('.J_tongji').find('span').eq(0).removeClass('on');
                }

            });

        });

    })();

    //点击下一步校验
    (function(){
    	//用户为点击的时候是false，点击后为true，返回结果后变回false
    	var isClick=false;
    	
        $('#J_next').click(function(){
        	
        	if(isClick){
        		return false;
        	}
        	var roomFid = $("#roomFid").val();
        	var houseFid = $("#houseFid").val();
        	var area_title = $("#J_biaoti");
        	var area_desc = $("#J_miaoshu");
        	var area_around = $("#J_zhoubian");
        	
        	//console.log(area_title.text());
        	//console.log(area_title.val());
        	//console.log(area_title.text().length);
            if(area_title.val().length>=10&&area_title.val().length<=30){
                area_title.siblings('.J_tishi').hide()
            }
            else{
                area_title.siblings('.J_tishi').show();
                return false;
            }

            if(area_desc.val().length>=100&&area_desc.val().length<=1000){
                area_desc.siblings('.J_tishi').hide()
            }
            else{
                area_desc.siblings('.J_tishi').show();
                return false;
            }

            if(area_around.val().length>=100&&area_around.val().length<=1000){
                area_around.siblings('.J_tishi').hide()
            }
            else{
                area_around.siblings('.J_tishi').show();
                return false;
            }
            
    		if(houseFid){
    			var param = {
    					housefid:houseFid,
    					houseName:area_title.val(),
    					houseDesc:area_desc.val(),
    					houseAround:area_around.val(),
    					roomFid:roomFid
    			}
    			
    			isClick = true;
    			$.post("/houseIssue/saveOrUpDesc/"+houseFid,param,function(data){
    				if(data.code == 0){
    					 if(roomFid==null||roomFid==""||roomFid==undefined){
    						 window.location.href="/houseIssue/toWholeOrSublet/"+data.data.houseBaseFid;
    					 }else{
    						 window.location.href="/houseIssue/toWholeOrSublet/"+data.data.houseBaseFid+"?roomFid="+roomFid;
    					 }
    					
    				}else if(data.code == 2){
                		window.location.href = SSO_USER_LOGIN_URL+window.location.href;
                	}else{
                		showConfirm(data.msg,"确定");
                		isClick = false;
    				}
    			},'json');
    		}

        });

    })();
    
    //得到焦点显示提示文字
    (function(){
    	
    	$('#J_biaoti').focus(function(){
    		$(this).next().show();
    	});
    	
    	$('#J_biaoti').blur(function(){
    		$(this).next().hide();
    	});
    	
    	$('#J_miaoshu').focus(function(){
    		$(this).next().show();
    	});
    	
    	$('#J_miaoshu').blur(function(){
    		$(this).next().hide();
    	});
    	
    	$('#J_zhoubian').focus(function(){
    		$(this).next().show();
    	});
    	
    	$('#J_zhoubian').blur(function(){
    		$(this).next().hide();
    	});
    	
    })();

});