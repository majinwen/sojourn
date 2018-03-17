/**
 * Created by mqzZoom on 16/8/2.
 */
$(function(){

    //当前状态
    var nowType='whole';

    //房源类型交互
    (function(){
        var oUl=$('#J_typeUl');
        var oIpt=$('#J_typeIpt');

        oIpt.click(function(e){
            oUl.css('display','block');
            e.stopPropagation();
        });

        oUl.find('li').click(function(){
            oIpt.val($(this).html());
            oIpt.attr('data-type',$(this).attr('data-type'));
        });

        $('#J_bigger').click(function(){
            oUl.css('display','none');
        });
    })();

    //出租类型交互
    (function(){
        var oHidden=$('#J_chuzuType');
        var oIsNone=$('#J_isNone');
        var oWhole=$('#J_whole');
        var oOne=$('#J_one');

        var oWhole1=$('#J_whole1');
        var oWhole2=$('#J_whole2');
        var oWhole3=$('#J_whole3');
        var oWhole4=$('#J_whole4');

        var oOne1=$('#J_one1');
        //console.log(oIsNone.val());
        if(oIsNone.val()>=2&&oHidden.val()==0){
        	oOne.hide();
        }
        else if(oIsNone.val()>=2&&oHidden.val()==1){
        	oWhole.hide();
        }
        
        //初始化出租方式
        if(oHidden.val()==1){
        	oWhole.removeClass('on');
        	oOne.addClass('on');
            oHidden.val(1);

            if(nowType=='one'){
                return false;
            }

            oWhole1.hide();
            oWhole2.hide();
            oWhole3.hide();
            oWhole4.hide();
            oOne1.show();
            nowType='one';
        }

        //插入dom位置
        var oDomPos=$('#J_public');

        oWhole.click(function(){
            oOne.removeClass('on');
            $(this).addClass('on');
            oHidden.val(0);

            if(nowType=='whole'){
                return false;
            }

            oWhole1.show();
            oWhole2.show();
            oWhole3.show();
            oWhole4.show();
            oOne1.hide();

            nowType='whole';
        });

        oOne.click(function(){
            oWhole.removeClass('on');
            $(this).addClass('on');
            oHidden.val(1);

            if(nowType=='one'){
                return false;
            }

            oWhole1.hide();
            oWhole2.hide();
            oWhole3.hide();
            oWhole4.hide();
            oOne1.show();

            nowType='one';
        });
    })();

    //接待访客交互
    (function(){
        var oBox=$('#J_bigger');

        oBox.delegate('#J_numIpt','click',function(e){
            $('#J_numUl').css('display','block');
            e.stopPropagation();
        });

        oBox.delegate('#J_numUl li','click',function(){
            $('#J_numIpt').val($(this).html());
            $('#J_numIpt').attr('data-num',$(this).attr('data-num'));
        });

        oBox.click(function(){
            $('#J_numUl').css('display','none');
        });
    })();

    //是否与房东同住
    (function(){

        var oBox=$('#J_bigger');

        oBox.delegate('#J_togetherIpt','click',function(e){
            $('#J_togetherUl').css('display','block');
            e.stopPropagation();
        });

        oBox.delegate('#J_togetherUl li','click',function(){
            $('#J_togetherIpt').val($(this).html());
            $('#J_togetherIpt').attr('data-together',$(this).attr('data-together'));
        });

        oBox.click(function(){
            $('#J_togetherUl').css('display','none');
        });

    })();

    //帮助信息
    (function(){

        var aBox=$('.J_helpInfo');

        aBox.each(function(){

            var oWen=$(this).find('b');
            var oI=$(this).find('i');
            var oHelp=$(this).find('span').eq(1);

            oWen.mouseover(function(){
                oI.show();
                oHelp.show();
            });

            oWen.mouseout(function(){
                oI.hide();
                oHelp.hide()
            });

        });

    })();
    
    
    //提交信息验证
    (function(){

        $('#J_next').click(function(){

            if(nowType=='whole'){

                //房屋整租校验
                if($('#J_typeIpt').val()==''){
                    $('#J_typeIpt').siblings('.J_tishi_txt').show();
                    return false;
                }
                else{
                    $('#J_typeIpt').siblings('.J_tishi_txt').hide();
                }

                if($('#J_numIpt').val()==''){
                    $('#J_numIpt').siblings('.J_tishi_txt').show();
                    return false;
                }
                else{
                    $('#J_numIpt').siblings('.J_tishi_txt').hide();
                }
                
                if($('#J_whole2 input').val()==''){
                	$('#J_whole2 input').siblings('.J_tishi_txt').show();
                }else{
                    $('#J_whole2 input').siblings('.J_tishi_txt').hide();
                }
                
                if( ! /^[1-9][0-9]*(\.[0-9])?$/.test($('#J_whole2 input').val())){
                    $('#J_whole2 input').siblings('.J_tishi_txt').html("请填写正确的房源面积，最多只能保留一位小数 ").show();
                    return false;
                }
                else{
                    $('#J_whole2 input').siblings('.J_tishi_txt').hide();
                }

                if($('#J_qixian').val()==''){
                    $('#J_qixian').siblings('.J_tishi_txt').show();
                    return false;
                }
                else{
                    $('#J_qixian').siblings('.J_tishi_txt').hide();
                }

                if($.trim($('#J_whole3 input').val())==''){
                	$('#J_whole3 input').siblings('.J_tishi_txt').text("请填写房源价格").show();
                }else{
                    $('#J_whole3 input').siblings('.J_tishi_txt').hide();
                }
                
                if( ! /^[1-9][0-9]*$/.test($('#J_whole3 input').val())){
                	$('#J_whole3 input').siblings('.J_tishi_txt').text("请填写正确的房源价格，只能输入整数");
                    $('#J_whole3 input').siblings('.J_tishi_txt').show();
                    return false;
                }else if(parseInt($('#J_whole3 input').val()) < 60){
                	$('#J_whole3 input').siblings('.J_tishi_txt').text("房源价格不能低于60元");
                    $('#J_whole3 input').siblings('.J_tishi_txt').show();
                    return false;
                }else{
                    $('#J_whole3 input').siblings('.J_tishi_txt').hide();
                }
                var clef=parseInt(Math.round($('#J_whole3 input').val()*$("#cleaningFees").val()));
                if( ! /^[0-9][0-9]*$/.test($('#J_whole4 input').val())&&$.trim($('#J_whole4 input').val())!=''){
                	$('#J_whole4 input').siblings('.J_tishi_txt').text("请填写正确的清洁费，只能输入整数");
                    $('#J_whole4 input').siblings('.J_tishi_txt').show();
                    return false;
                }else if($('#J_whole4 input').val()>clef){
                	$('#J_whole4 input').siblings('.J_tishi_txt').text("您的清洁费太高了，最高可设置为"+clef+"元");
                    $('#J_whole4 input').siblings('.J_tishi_txt').show();
                    return false;
                }else{
                    $('#J_whole4 input').siblings('.J_tishi_txt').hide();
                }
                //提交更新整租房源信息
                var paramData={"fid":$("#houseFid").val(),"houseType":$("#J_typeIpt").attr("data-type"),"rentWay":$("#J_chuzuType").val(),"checkInLimit":$("#J_numIpt").attr("data-num"),
                		"houseArea":$("#houseArea").val(),"dayPrice":$("#housePrice").val(),"dateLimit":$("#J_qixian").val(),"operateSeq":$("#operateSeq").val(),"clearPrice":$('#J_whole4 input').val()
                		,"cleaningFees":$("#cleaningFees").val()};
                CommonUtils.ajaxPostSubmit("/houseIssue/updateHouseBaseAndExt",paramData,function(data){
                	if(data.code==0){
                        var oHidden=$('#J_chuzuType');
                        if(oHidden.val()==0){
                        	$('#J_one').hide();
                        }
                        else if(oHidden.val()==1){
                        	$('#J_whole').hide();
                        }
                        $('#J_isNone').val(2);
                		window.location.href="/houseIssue/configMsg/"+$("#houseFid").val();
                	}else if(data.code == 2){
                		window.location.href = SSO_USER_LOGIN_URL+window.location.href;
                	}else{
                		showConfirm(data.msg,"确定");
                	}
        		});
            }
            else{

                //独立房间校验
                if($('#J_togetherIpt').val()==''){
                    $('#J_togetherIpt').siblings('.J_tishi_txt').show();
                    return false;
                }
                else{
                    $('#J_togetherIpt').siblings('.J_tishi_txt').hide();
                }

                if($('#J_qixian').val()==''){
                    $('#J_qixian').siblings('.J_tishi_txt').show();
                    return false;
                }
                else{
                    $('#J_qixian').siblings('.J_tishi_txt').hide();
                }
                
                //提交更新合租房源信息
                var paramData={"fid":$("#houseFid").val(),"houseType":$("#J_typeIpt").attr("data-type"),"rentWay":$("#J_chuzuType").val(),
                		"dateLimit":$("#J_qixian").val(),"isTogetherLandlord":$("#J_togetherIpt").attr("data-together"),"operateSeq":$("#operateSeq").val()};
                CommonUtils.ajaxPostSubmit("/houseIssue/updateHouseBaseAndExt",paramData,function(data){
                	if(data.code==0){
                        var oHidden=$('#J_chuzuType');
                        if(oHidden.val()==0){
                        	$('#J_one').hide();
                        }
                        else if(oHidden.val()==1){
                        	$('#J_whole').hide();
                        }
                        $('#J_isNone').val(2);
                		window.location.href="/houseIssue/configMsg/"+$("#houseFid").val();
                	}else{
                		showShadedowTips(data.msg,2000);
                	}
        		});
            }
        });

    })();

    //出租期限
    var y,m,d;
    var now = new Date();

    y = now.getFullYear();
    m = now.getMonth()+1;
    d = now.getDate();
    new MINSU.DatePicker(y+'-'+m+'-'+d, '#J_qixian');
    
    //焦点事件
    //面积
    $('#houseArea').blur(function (){ 
    	if( ! /^[1-9][0-9]*(\.[0-9])?$/.test($('#J_whole2 input').val())){

            $('#J_whole2 input').siblings('.J_tishi_txt').show();
            return false;
        }
        else{
            $('#J_whole2 input').siblings('.J_tishi_txt').hide();
        }
    }) ;
    
    //房源价格
    $('#housePrice').blur(function (){ 
        if( ! /^[1-9][0-9]*$/.test($('#J_whole3 input').val())){
        	$('#J_whole3 input').siblings('.J_tishi_txt').text("请填写正确的房源价格，只能输入整数");
            $('#J_whole3 input').siblings('.J_tishi_txt').show();
            return false;
        }else if(parseInt($('#J_whole3 input').val()) < 60){
        	$('#J_whole3 input').siblings('.J_tishi_txt').text("房源价格不能低于60元");
            $('#J_whole3 input').siblings('.J_tishi_txt').show();
            return false;
        }else{
            $('#J_whole3 input').siblings('.J_tishi_txt').hide();
        }
    }) ;
    
    //清洁费
    $('#houseCleaningFees').blur(function (){ 
        var clef=parseInt(Math.round($('#J_whole3 input').val()*$("#cleaningFees").val()));
        if( ! /^[0-9][0-9]*$/.test($('#J_whole4 input').val())&&$.trim($('#J_whole4 input').val())!=''){
        	$('#J_whole4 input').siblings('.J_tishi_txt').text("请填写正确的清洁费，只能输入整数");
            $('#J_whole4 input').siblings('.J_tishi_txt').show();
            return false;
        }else if($('#J_whole4 input').val()>clef){
        	$('#J_whole4 input').siblings('.J_tishi_txt').text("您的清洁费太高了，最高可设置为"+clef+"元");
            $('#J_whole4 input').siblings('.J_tishi_txt').show();
            return false;
        }else{
            $('#J_whole4 input').siblings('.J_tishi_txt').hide();
        }
    }) ;
});
