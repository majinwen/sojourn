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
        
        $('#J_next').click(function(){
        	var houseFid = $("#houseFid").val();
        	var area_title = $("#J_biaoti");
        	var area_desc = $("#J_miaoshu");
        	var area_around = $("#J_zhoubian");
        	
        	console.log(area_title.text());
        	console.log(area_title.val());
        	console.log(area_title.text().length);
            if(area_title.val().length>=10&&area_title.val().length<=30){
                area_title.siblings('.J_tishi').hide()
            }
            else{
                area_title.siblings('.J_tishi').show();
                return false;
            }

            if(area_desc.val().length>=50&&area_desc.val().length<=1000){
                area_desc.siblings('.J_tishi').hide()
            }
            else{
                area_desc.siblings('.J_tishi').show();
                return false;
            }

            if(area_around.val().length>=50&&area_around.val().length<=1000){
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
    					houseAround:area_around.val()
    			}
    			$.post("/houseIssue/saveOrUpDesc/"+houseFid,param,function(data){
    				if(data.code == 0){
    					window.location.href="/houseIssue/pic/"+data.data.houseBaseFid;
    				}else{
    					alert(data.msg);
    				}
    			},'json');
    		}

        });

    })();

});