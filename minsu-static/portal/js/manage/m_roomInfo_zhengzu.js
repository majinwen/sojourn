/**
 * Created by mqzZoom on 16/8/8.
 */

$(function(){
	
	var fidArr=[];
	
	
	//页面加载完成，床位信息回显，根据data-value生成对应的value
	(function(){
		$('.J_bed_type').each(function(){
			if($(this).attr('data-value')){
				$(this).val(bedJson[$(this).attr('data-value')]);
			}
		});

        /*$('.J_bed_guige').each(function(){
            if($(this).attr('data-value')){
                $(this).val(bedTypeJson[$(this).attr('data-value')]);
            }
        });*/
	})();

    //帮助信息
    (function(){

        var oBox=$('#J_helpInfo');
        var oWen=oBox.find('b');
        var oI=oBox.find('i');
        var oHelp=oBox.find('span').eq(1);

        oWen.mouseover(function(){
            oI.show();
            oHelp.show();
        });

        oWen.mouseout(function(){
            oI.hide();
            oHelp.hide()
        });

    })();

    //所有下拉
    (function(){

        var oBox=$('#J_delegate');

        oBox.delegate('.J_xiala input','click',function(e){
        	$('.J_xiala ul').hide();
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

    //添加删除床位
    (function(){

        $('#J_delegate').delegate('.J_add_bed','click',function(){
        	
        	if($(this).siblings('div').size()>=7){
        		return false;
        	}
        	
            $('<div class="S_bed_info clearfix">'
                +'<div class="S_bed_info_item J_xiala">'
                +'<input type="text" readonly="readonly" placeholder="床位类型" data-value="'+2+'" value="'+bedJson[2]+'" class="J_bed_type" />'
                +'<i class="S_bed_jiantou"></i>'
                +'<ul class="S_xiala S_xiala3">'
                +forStr(bedJson)
                +'</ul>'
                +'</div>'
                /*+'<div class="S_bed_info_item J_xiala">'
                +'<input type="text" readonly="readonly" placeholder="床位规格"data-value="'+4+'" value="'+bedTypeJson[4]+'" class="J_bed_guige"/>'
                +'<i class="S_bed_jiantou"></i>'
                +'<ul class="S_xiala S_xiala3">'
                +forStr(bedTypeJson)
                +'</ul>'
                +'</div>'*/
                +'<a href="javascript:;" class="J_remove_bed">删除</a>'
                +'</div>').insertBefore($(this));

        });

        $('#J_delegate').delegate('.J_remove_bed','click',function(){
        	var bedFid=$(this).parent().find(".J_bed_fid").val();
        	var paramData={};
        	var _this=this;
        	if(bedFid){
        		CommonUtils.ajaxPostSubmit("/houseIssue/delBed/"+bedFid,paramData,function(data){
                	if(data.code==0){
                		$(_this).parent().remove();
                	}else{
                		showConfirm(data.msg,"确定");
                	}
        		});
        	}else{
        		$(this).parent().remove();
        	}
        });

    })();

    //选择室交互
    var roomTotal=$('#J_room').prev().prev().val()||0;
    
    var roomNow=$('.J_room_item').size();
    
    if(roomTotal>roomNow){
		makeRoom(roomTotal-roomNow);
	}

    (function(){

        $('#J_room').find('li').click(function(){
        	//等于0的时候
        	if(roomTotal==0){
        		roomTotal=parseInt($(this).html());
                makeRoom(roomTotal);
        	}
        	//选择房间比当前房间多的情况
        	else if($(this).html()>roomNow){
        		roomTotal=parseInt($(this).html());
        		makeRoom(roomTotal-roomNow);
        	}
        	//选择房间比当前房间少的情况
        	else{
        		roomTotal=parseInt($(this).html());
        		if(roomTotal!=roomNow){
	        		var r=confirm('将会删除房间');
	        		if(!r){
	        			return false;
	        		}
	        		removeRoom(roomNow-roomTotal);
	        		//删除房间
	        		var paramData={"roomFids":fidArr.toString()};
	        		CommonUtils.ajaxPostSubmit("/houseIssue/delZroomByFid",paramData,function(data){
	                	if(data.code==0){
	                		fidArr=[];
	                	}else{
	                		showConfirm(data.msg,"确定");
	                	}
	        		});
        		}
        	}
        });

    })();
    
    //下一步校验信息
    (function(){
    	//点击下一步后变为true，不能多次
    	var isClick = false;
    	
        $('#J_next').click(function(){
        	if(isClick){
        		return false;
        	}
        	
            var houseInfo={};
            //房源信息
        	houseInfo.houseFid=$("#houseFid").val();
        	houseInfo.roomNum=parseInt($("#F_room").val());
        	houseInfo.hallNum=parseInt($("#F_hall").val());
        	houseInfo.toiletNum=parseInt($("#F_toilet").val());
        	houseInfo.kitchenNum=parseInt($("#F_kitchen").val());
        	houseInfo.balconyNum=parseInt($("#F_balcony").val());
        	
        	//console.log(roomNow);
        	var aRes=[];
        	for(var j=1;j<=roomNow;j++){
                json=getRoomInfo('J_roomBox_'+j);
                aRes.push(json);
            }
        	if($('.S_bed_info').size()==0){
        		showConfirm('请至少选择一个床位',"确定");
        		return false;
        	}
        	if(houseInfo.toiletNum==0){
        		showConfirm('请至少选择一个卫生间',"确定");
        		return false;
        	}
        	
        	
        	
        	isClick = true;
        	houseInfo.rooms=aRes;
    		var paramData={"houseInfo":JSON.stringify(houseInfo)};
    		CommonUtils.ajaxPostSubmit("/houseIssue/saveOrUpdateZroom",paramData,function(data){
            	if(data.code==0){
            		window.location.href="/houseReleaseExtras/initExtrasInfo/"+data.data.houseBaseFid;
            		//window.location.href="/houseIssue/pic/"+data.data.houseBaseFid;
            	}else if(data.code == 2){
            		window.location.href = SSO_USER_LOGIN_URL+window.location.href;
            	}else{
            		showConfirm(data.msg,"确定");
            		isClick = false;
            	}
    		});
        });
    })();

    function makeRoom(n){
    	
    	for(var i=0;i<n;i++){
    		$('<li class="S_item_content J_room_item" id="J_roomBox_'+(roomNow+1)+'">'
    	            +'<p class="S_content_tit">卧室'+(roomNow+1)+'</p>'
    	            +'<div class="S_bed_info clearfix">'
                    +'<div class="S_bed_info_item J_xiala">'
                    +'<input type="text" readonly="readonly" placeholder="床位类型" data-value="'+2+'" value="'+bedJson[2]+'" class="J_bed_type" />'
                    +'<i class="S_bed_jiantou"></i>'
                    +'<ul class="S_xiala S_xiala3">'
                    +forStr(bedJson) 
                    +'</ul>'
                    +'</div>'
                /*+'<div class="S_bed_info_item J_xiala">'
                +'<input type="text" readonly="readonly" placeholder="床位规格"data-value="'+4+'" value="'+bedTypeJson[4]+'" class="J_bed_guige"/>'
                +'<i class="S_bed_jiantou"></i>'
                +'<ul class="S_xiala S_xiala3">'
                +forStr(bedTypeJson)
                +'</ul>'
                +'</div>'*/
                    +'<a href="javascript:;" class="J_remove_bed">删除</a>'
                    +'</div>'
    	            +'<div class="S_add_bed J_add_bed">'
    	            +'<i class="S_item_add"></i>'
    	            +'<span>添加床位</span>'
    	            +'</div>'
    	            +'</li>').appendTo('#J_room_box');
    			roomNow++;
        }
    	
    	var aRoom=$('.J_room_item');
        aRoom.eq(aRoom.size()-1).addClass('on');
    }
    
    function removeRoom(n){
    	for(var i=0;i<n;i++){
    		var aRoom=$('.J_room_item');
    		var fid=aRoom.eq(aRoom.size()-1).find('.J_room_fid').val();
    		if(fid){
    			fidArr.push(fid);
    		}
            aRoom.eq(aRoom.size()-1).remove();
            roomNow--;
    	}
        aRoom.eq(aRoom.size()-1).addClass('on');
    }
    
    
    //获取房间每个房间信息
    function getRoomInfo(id){
    	var obj=$('#'+id);
        var oFid=obj.find('.J_room_fid');	
        
        var aType=obj.find('.J_bed_type');
        /*var aGuige=obj.find('.J_bed_guige');*/
        var abedFid=obj.find('.J_bed_fid');
        
        var aRes={};
        
        aRes.roomFid=oFid.val();
        aRes.bedNum=aType.size();
        
        
        aRes.beds=[];
        
        for(var i=0;i<aType.size();i++){
        	var jTmp={};
        	jTmp.bedFid = abedFid.eq(i).val();
        	jTmp.bedType=aType.eq(i).attr('data-value');
            /*jTmp.bedSize=aGuige.eq(i).attr('data-value');*/
        	
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

});




































































