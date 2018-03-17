/**
 * Created by mqzZoom on 16/8/8.
 */

$(function(){
	//
	var cleanFeeRate = $("#cleaningFeeRate").val();
	//页面加载完成，床位信息回显，根据data-value生成对应的value
	(function(){
		$('.J_bed_type').each(function(){
			if($(this).attr('data-value')){
				$(this).val(bedJson[$(this).attr('data-value')]);
			}
		});
		
		$('.J_bed_guige').each(function(){
			if($(this).attr('data-value')){
				$(this).val(bedTypeJson[$(this).attr('data-value')]);
			}
		});
		
		//是否独立卫生间
		if($(".J_isWc").val() == 1){
			$(".J_radio_no").removeClass("on");
			$(".J_radio_yes").addClass("on");
		}
		if($(".J_isWc").val() == 0){
			$(".J_radio_yes").removeClass("on");
			$(".J_radio_no").addClass("on");
		}
		
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

    //所有下拉
    (function(){

        var oBox=$('#J_delegate');

        oBox.delegate('.J_xiala input','click',function(e){
            e.stopPropagation();
            $(this).next().next().show();
        });

        oBox.delegate('.J_xiala ul li','click',function(){
            var oIpt=$(this).parent().prev().prev();
            oIpt.val($(this).html());
            oIpt.attr('data-value',$(this).attr('data-value'));
        });

        oBox.click(function(){
            $('.J_xiala ul').hide();
        });

    })();

    //是否
    (function(){

        $('#J_delegate').delegate('.J_radio_yes','click',function(){
            $(this).addClass('on');
            $(this).next().removeClass('on');
            $(this).prev().val('1');
        });

        $('#J_delegate').delegate('.J_radio_no','click',function(){
            $(this).addClass('on');
            $(this).prev().removeClass('on');
            $(this).prev().prev().val('0');
        });

    })();

    //床位操作
    (function(){
    	
    	//添加床位
        $('#J_delegate').delegate('.J_add_bed','click',function(){

        	if($(this).siblings('div').size()>=7){
        		return false;
        	}
        	
            $('<div class="S_bed_info clearfix">'
            	+'<input type="hidden" class="J_bed_fid"/>'
                +'<div class="S_bed_info_item J_xiala">'
                +'<input type="text" readonly="readonly" placeholder="床位类型" class="J_bed_type" data-value="1" value="'+bedJson[1]+'"/>'
                +'<i class="S_bed_jiantou"></i>'
                +'<ul class="S_xiala S_xiala3">'
                +forStr(bedJson)
                +'</ul>'
                +'</div>'
                +'<div class="S_bed_info_item J_xiala">'
                +'<input type="text" readonly="readonly" placeholder="床位规格" class="J_bed_guige" data-value="1" value="'+bedTypeJson[1]+'"/>'
                +'<i class="S_bed_jiantou"></i>'
                +'<ul class="S_xiala S_xiala3">'
                +forStr(bedTypeJson)
                +'</ul>'
                +'</div>'
                +'<a href="javascript:;" class="J_remove_bed">删除</a>'
                +'</div>').insertBefore($(this));

        });
        
        //删除床位信息
       $('#J_delegate').delegate('.J_remove_bed','click',function(){
    	   var _this = this;
        	var fid = $(this).parent().children(".J_bed_fid").val();
        	if(fid){
        		$.post("/houseIssue/delBed/"+fid,function(data){
        			if(data.code == 0){
                		$(_this).parent().remove();
        			}
        		},'json');
        	}else{
        		$(this).parent().remove();
        	}
        
       }) 		
    })();

    //房间总数
    var roomTotal=$('#J_room').prev().prev().val()||0;
    var roomNow=$('.J_roomBox').size();
    
    isShowRemove();

    //添加房间
    (function(){
        //选择房间数量，如果房间总数小于现有房间数量，需要删除
        $('#J_room').find('li').click(function(){
        	var oldTotal=roomTotal;
            roomTotal=$(this).html();

            if(roomTotal<roomNow){
            	
            	var r=confirm('是否删除多余的卧室？');
            	
            	if(!r){
            		roomTota=oldTotal;
            		return false;
            	}
            	
                while(roomTotal<roomNow){
                    roomNow--;
                    
                    //选择户型删除房间    fid
                    var roomFid = $('.J_roomBox').eq(roomNow).find('.J_room_fid').val();
                    if(delRoomFid(roomFid)){
                    	$('.J_roomBox').eq(roomNow).remove();
                    }else{
                    	alert("删除失败");
                    }
                }
            }

            isShowRemove();

        });

        var addButton=$('#J_add_room');
        var domPos=addButton.parent();

        addButton.click(function(){

            if(roomNow<roomTotal){
                $('<div class="S_part2 J_roomBox" id="J_roomBox_'+(roomNow+1)+'">'+
                    '<div class="S_part2_item">'+
                    '<input type="hidden" class="J_room_fid"/>'+
                    '<h2 class="S_small_tit clearfix">\
                    <p>卧室<span class="J_small_tit">'+(roomNow+1)+'</span></p>\
                    <input type="button" value="删除房间" class="J_remove_room"/>\
                    </h2>'+
                    '<ul class="S_item_content">'+

                    '<li>'+
                    '<p class="S_content_tit">房间名称</p>'+
                    '<input type="text" class="S_long_ipt J_room_name" placeholder="房子名字（选填）+商圈+房东特色+房源特色，限10-30字。"/>'+
                    '<span class="S_tishi_2 J_tishi2">请填写正确的房间名称</span>'+
                    '</li>'+

                    '<li>'+
                    '<p class="S_content_tit">您的房源的面积</p>'+
                    '<input type="text" maxlength="6" class="S_normal_ipt J_room_mianji"/>'+
                    '<i class="S_str_right">平米</i>'+
                    '<span class="S_tishi_2 J_tishi2">请填写正确的房间面积</span>'+
                    '</li>'+

                    '<li>'+
                    '<p class="S_content_tit">是否独立卫生间</p>'+
                    '<div class="S_radio_box clearfix">'+
                    '<input type="hidden" value="0" class="J_isWc"/>'+
                    '<p class="S_radio J_radio_yes">是</p>'+
                    '<p class="S_radio on J_radio_no">否</p>'+
                    '</div>'+
                    '</li>'+

                    '<li class="J_xiala">'+
                    '<p class="S_content_tit">可以接待多少位房客</p>'+
                    '<input type="text" class="S_normal_ipt J_how_many" readonly="readonly" placeholder="请选择"/>'+
                    '<i class="S_jiantou_right"></i>'+
                    '<ul class="S_xiala S_xiala2">'+
                    forStr(limitJson)+
                    '</ul>'+
                    '<span class="S_tishi_2 J_tishi2">请选择可接待的房客数</span>'+
                    '</li>'+

                    '<li>'+
                    '<p class="S_content_tit">您的房间价格</p>'+
                    '<input type="text"  maxlength="6" class="S_normal_ipt J_how_much"/>'+
                    '<i class="S_str_right">每晚</i>'+
                    '<span class="S_tishi_2 J_tishi2">请填写正确的房源价格</span>'+
                    '</li>'+
                    '<li>'+
                    	'<p class="S_helpInfo2 J_helpInfo" >'+
	                        '<span>清洁费</span>'+
	                        '<b></b>'+
	                        '<i></i>'+
	                        '<span class="S_helpTxt2">清洁费默认为0元，表示不收取清洁费；清洁费最高上限为每晚房费的'+cleanFeeRate*100+'。</span>'+
                        '</p>'+
                        '<input type="text" maxlength="6" class="S_normal_ipt J_cleanFee" value="0"/>'+
                        '<span class="S_tishi_2 J_tishi2"></span>'+
                    '</li>'+
                    '<li>'+
                    '<p class="S_content_tit">您的床位信息</p>'+

                    '<div class="S_bed_info clearfix">'+
                    '<input type="hidden" class="J_bed_fid"/>'+
                    '<div class="S_bed_info_item J_xiala">'+
                    '<input type="text" readonly="readonly" placeholder="床位类型" class="J_bed_type" data-value="1" value="'+bedJson[1]+'"/>'+
                    '<i class="S_bed_jiantou"></i>'+
                    '<ul class="S_xiala S_xiala3">'+
                    forStr(bedJson)+
                    '</ul>'+
                    '</div>'+
                    '<div class="S_bed_info_item J_xiala">'+
                    '<input type="text" readonly="readonly" placeholder="床位规格" class="J_bed_guige" data-value="1" value="'+bedTypeJson[1]+'"/>'+
                    '<i class="S_bed_jiantou"></i>'+
                    '<ul class="S_xiala S_xiala3">'+
                    forStr(bedTypeJson)+
                    '</ul>'+
                    '</div>'+
                    '</div>'+

                    '<div class="S_add_bed J_add_bed">'+
                    '<i class="S_item_add"></i>'+
                    '<span>添加床位</span>'+
                    '</div>'+

                    '</li>'+

                    '</ul>'+
                    '</div>'+
                    '</div>').insertBefore(domPos);

                roomNow++;
                isShowRemove();
            }

        });

    })();

    //删除房间
    (function(){

        var oPos=$('#J_add_room').parent();

        $('#J_delegate').delegate('.J_remove_room','click',function(){
        	
        	var r=confirm('是否删除多余的卧室？');
        	
        	if(!r){
        		return false;
        	}

            if(roomNow==1){
                return false;
            }

            //点击删除的时候获取fid
            var roomFid = $(this).parent().parent().parent().find('.J_room_fid').val();
            
            //删除房间 dom操作
            if(delRoomFid(roomFid)){
            	$(this).parent().parent().parent().nextUntil(oPos).each(function(){
            		
            		var num=$(this).attr('id').charAt(10);
            		$(this).attr('id','J_roomBox_'+(num-1));
            		$(this).find('.J_small_tit').html((num-1));
            		
            	});
            	
            	$(this).parent().parent().parent().remove();
            	roomNow--;
            	isShowRemove();
            }

        });

    })();

    //下一步校验信息
    (function(){
        $('#J_next').click(function(){

            //成功标志
            var flag=false;

            //校验户型信息
            var oUl=$('#J_huxing_info');
            var oTishi1=$('#J_tishi_1');

            oUl.find('input').each(function(){
                if($(this).val()==''){
                    oTishi1.show();
                    $('html,body').animate({scrollTop:'0px'});
                    flag=false;
                    return false;
                }
                else{
                    oTishi1.hide();
                    flag=true;
                }
            });

            //如果不正确不进行下一步
            if(! flag){
                return false;
            }

            //循环验证每一个卧室信息
            for(var i=1;i<=roomNow;i++){
                flag=checkRoom('J_roomBox_'+i);
                if(!flag){
                    break;
                }
            }

            if(flag){
                var houseInfo={};
                
            	var aRes=[];
            	for(var j=1;j<=roomNow;j++){
                    json=getRoomInfo('J_roomBox_'+j);
                    aRes.push(json);
                }
            	//房源信息
            	houseInfo.houseFid=$("#houseFid").val();
            	houseInfo.roomNum=parseInt($("#F_room").val());
            	houseInfo.hallNum=parseInt($("#F_hall").val());
            	houseInfo.toiletNum=parseInt($("#F_toilet").val());
            	houseInfo.kitchenNum=parseInt($("#F_kitchen").val());
            	
            	//房间信息
            	houseInfo.rooms=aRes;
            	
            	$.post("/houseIssue/saveOrUpdateRoom",{param:JSON.stringify(houseInfo)},function(data){
            		console.log(data)
            		if(data.code == 0){
            			window.location.href="/houseIssue/desc/"+data.data.houseBaseFid;
            		}else{
            			alert(data.msg);
            		}
            	},"json");
            	
            }

        });
    })();

    //判断现不现实房间删除按钮函数
    function isShowRemove(){
        if(roomNow>1){
            $('.J_remove_room').show();
        }
        else{
            $('.J_remove_room').hide();
        }
    }
    
    //根据id依次校验卧室信息的函数
    function checkRoom(id){
        var obj=$('#'+id);
        var ipt1=obj.find('.J_room_name');
        var ipt2=obj.find('.J_room_mianji');
        var ipt3=obj.find('.J_how_many');
        var ipt4=obj.find('.J_how_much');
        var ipt5=obj.find('.J_cleanFee');
        
        if( (ipt1.val().length>=10) && (ipt1.val().length<=30) ){
            ipt1.siblings('.J_tishi2').hide();
        }
        else{
            ipt1.siblings('.J_tishi2').show();
            $('html,body').animate({scrollTop:ipt1.offset().top-250+'px'});
            return false;
        }

        if( ! /^[1-9][0-9]*(\.[0-9])?$/.test(ipt2.val())){
            ipt2.siblings('.J_tishi2').show();
            $('html,body').animate({scrollTop:ipt2.offset().top-250+'px'});
            return false;
        }
        else{
            ipt2.siblings('.J_tishi2').hide();
        }

        if(ipt3.val()==''){
            ipt3.siblings('.J_tishi2').show();
            $('html,body').animate({scrollTop:ipt3.offset().top-250+'px'});
            return false;
        }
        else{
            ipt3.siblings('.J_tishi2').hide();
        }

        if( ! /^[1-9][0-9]*(\.[0-9])?$/.test(ipt4.val())){
        	ipt4.siblings('.J_tishi2').text("请填写正确的房间价格");
            ipt4.siblings('.J_tishi2').show();
            $('html,body').animate({scrollTop:ipt4.offset().top-250+'px'});
            return false;
        }else if(parseInt(ipt4.val()) < 60){
        	ipt4.siblings('.J_tishi2').text("房间价格不能低于60元");
            ipt4.siblings('.J_tishi2').show();
            $('html,body').animate({scrollTop:ipt4.offset().top-250+'px'});
            return false;
        }else{
            ipt4.siblings('.J_tishi2').hide();
        }
        
        var price = ipt4.val();
        var upPrice = Math.ceil(price * parseFloat(cleanFeeRate));
        if(! /^[0-9][0-9]*(\.[0-9])?$/.test(ipt5.val())){
        	ipt5.siblings('.J_tishi2').text("请填写正确的清洁费");
        	ipt5.siblings('.J_tishi2').show();
        	$('html,body').animate({scrollTop:ipt4.offset().top-250+'px'});
        	return false;
        }else if(ipt5.val() > upPrice){
        	ipt5.siblings('.J_tishi2').text("您的清洁费太高了，最高可设置为于"+upPrice+"元");
        	ipt5.siblings('.J_tishi2').show();
        	$('html,body').animate({scrollTop:ipt4.offset().top-250+'px'});
        	return false;
        }else{
        	ipt5.siblings('.J_tishi2').hide();
        }
        
        return true;

    }
    //获取房间每个房间信息
    function getRoomInfo(id){
    	var obj=$('#'+id);
        var oName=obj.find('.J_room_name');
        var oMianji=obj.find('.J_room_mianji');
        var oMany=obj.find('.J_how_many');
        var oMuch=obj.find('.J_how_much');
        var oClean=obj.find('.J_cleanFee');
        var oWc=obj.find('.J_isWc');
        var oFid=obj.find('.J_room_fid');	
        
        var aType=obj.find('.J_bed_type');
        var aGuige=obj.find('.J_bed_guige');
        var abedFid=obj.find('.J_bed_fid');
        
        var aRes={};
        
        aRes.roomName=oName.val();
        aRes.roomArea=parseFloat(oMianji.val());
        aRes.checkInLimit=parseInt(oMany.attr('data-value'));
        aRes.roomPrice=parseInt(oMuch.val());
        aRes.cleanFee=parseInt(oClean.val());
        aRes.isToilet=parseInt(oWc.val());
        aRes.roomFid=oFid.val();
        aRes.bedNum=aType.size();
        
        
        aRes.beds=[];
        
        for(var i=0;i<aType.size();i++){
        	var jTmp={};
        	jTmp.bedFid = abedFid.eq(i).val();
        	jTmp.bedType=aType.eq(i).attr('data-value');
        	jTmp.bedSize=aGuige.eq(i).attr('data-value');
        	
        	aRes.beds.push(jTmp);
        }
        
        return aRes;
        
    }
    
    //复现用，遍历选项函数
    function forStr(json){
    	var res='';
    	for(var key in json){
    		res+='<li data-value="'+key+'">'+json[key]+'</li>';
    	}
    	return res;
    };
    
    //删除房间图片
    function delRoomFid(roomFid){
    	var isDel = true;
    	$.post("/houseIssue/delRoom/"+roomFid,function(data){
    		if(data.code != 0){
    			alert("房间删除成功");
    		}else{
    			isDel = false;
    		}
    	},'json');
    	return isDel;
    }
    
});


