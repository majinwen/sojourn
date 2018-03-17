/**
 * Created by mqzZoom on 16/8/4.
 */
$(function(){

    //选择配置物品交互
    (function(){

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
        	CommonUtils.ajaxPostSubmit("/houseIssue/insertOrUpHouseConf",paramData,function(data){
             	if(data.code==0){
             		window.location.href="/houseIssue/toWholeOrSublet/"+$("#houseFid").val();
             	}else{
             		showConfirm("服务错误","确定");
             	}
     		});
        });
    })();

});