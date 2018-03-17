$(function(){
    var iWinHeight = $(window).height();
    var str = '';
    if (!placeholderSupport()) { // 判断浏览器是否支持 placeholder
      $("input").each(function(){
        var input = $(this);
        if(input.attr('placeholder')){
            if (input.val() == '' || input.val() == input.attr('placeholder')) {
                input.addClass('placeholder');
                input.val(input.attr('placeholder'));
                input.css({"color":"#bbb"})
              }
        }
      })
    };
    $(window).scroll(function(){
        var top = $(window).scrollTop()+iWinHeight;
        var _top = $('#loadMore').offset().top;
        var iTop = $(window).scrollTop()
            // 返回顶部
            if(iTop >= iWinHeight){
                $("#goTop").show();
            }else{
                $("#goTop").hide();
            }
            if(flag){
                if(_top > 0 && _top < top){
                   param.page += 1;
                   flag = false;
                   getHouseList(param);
               }
            }
       })
    // tap层
    $(".ask").hover(function(){
        showTap('申请预订需房东先确认，再支付； 立即预订无需确认直接支付。','ask','3000')
    },function(){
        layer.closeAll();
    })
     // 筛选条件
     $(".td_c").each(function(){
        $(this).click(function(){

            if($(this).attr("data-type") == "rent"){
                $("#rent").val($(this).find(".tags").attr("data-rent"));
            }else if($(this).attr("data-type") == "preview"){
                $("#preview").val($(this).find(".tags").attr("data-preview"));
            }
            choseTags($(this));
        })
    })
   // 关键字搜索
   $("#keywordBtn").click(function(){
    ms.refreshSearchs();
})
   // 价格滑块
   $('.single-slider').jRange({
    from: 0,
    to: 1500,
    step: 10,
    scale: [0,300,600,900,1200,"1500+"],
    format: '¥%s',
    width: 600,
    showLabels: true,
    showScale: true
});
    $("#goTop").click(//定义返回顶部点击向上滚动的动画 
        function(){$('html,body').stop().animate({scrollTop:0},300); 
    }).html("TOP");
    $("#keywordIpt").on("keyup",function(e){
        if($(this).val() == ""){
            $("#keywordBtn").hide();
        }else{
            $("#keywordBtn").show();
            // 兼容FF和IE和Opera    
            var theEvent = e || window.event;    
            var code = theEvent.keyCode || theEvent.which || theEvent.charCode; 
            if (code == 13) {    
                //回车执行查询
                    ms.refreshSearchs();
                }    
        }
    })
})

function showKeywordBtn(){
    if($("#keywordIpt").val() == ""){
        $("#keywordBtn").hide();
    }else{
        $("#keywordBtn").show();
    }
}

// 删除筛选条件
function removeThis(obj){
    if($(obj).attr("data-type") == "data-money"){
        $("#price").val(0,1500);
        $("#startPrice").val("0");
        $("#endPrice").val("1500");
        $("#lastPrice").val("");
    }
    if($(obj).attr("data-type") == "data-td"){
      for(var i = 0; i < $(".td_c").length ; i ++){
        if($(".td_c:eq("+i+")").text() == $(obj).text()){
            $(".td_c:eq("+i+")").parents("tr").find(".td_c:eq(0)").addClass("active").siblings().removeClass("active");
            if($(".td_c:eq("+i+")").parents("tr").find(".td_c:eq(0)").attr("data-type") == "rent"){
                $("#rent").val("-1");
            }else if($(".td_c:eq("+i+")").parents("tr").find(".td_c:eq(0)").attr("data-type") == "preview"){
                $("#preview").val("-1");
            }
        }

    }

    
}
if($(obj).attr("data-type") == "data-keyword"){
    $("#keywordIpt").val("");
    $('#keywordBtn').css({
        "display":"none"
    })
}
$(obj).remove();
ms.refreshSearchs();
}
function choseTags(obj){
    $(obj).parents("tr").find(".td_c").removeClass("active");
    $(obj).addClass("active");
    ms.refreshSearchs();
}
// 收起筛选条件
function upRequirebox(obj){
    if($(obj).hasClass("down")){
        $("#searchList").hide();
        $(obj).addClass("up").removeClass("down").html("展开筛选条件");
    }else{
        $("#searchList").show();
        $(obj).addClass("down").removeClass("up").html("收起筛选条件");
    }
    
}
// 增加筛选条件
function addRequire(){
    var require_arr = [];
    var require_string = "";
    for(var i = 0 ; i < $(".td_c.active").length ; i ++){
        if($(".td_c.active:eq("+i+")").text() !== "不限"){
            require_arr.push($(".td_c.active:eq("+i+")").text());
        }
    }
    for(var ii = 0 ; ii< require_arr.length ; ii ++){
        require_string+="<li data-type='data-td' onclick='removeThis(this)'><a href='javascript:;'>"+require_arr[ii]+"</a></li>";
    }
    var startPrice = parseInt($("#startPrice").val());
    var endPrice = $("#endPrice").val();
    if(endPrice.indexOf(" " != -1)){
        endPrice = endPrice.split(" ")[0]+endPrice.split(" ")[1];
    }
    endPrice = parseInt(endPrice);
    if(startPrice != endPrice ){
        if(endPrice != 1500){
         require_string+="<li data-type='data-money' onclick='removeThis(this)'><a href='javascript:;'>"+" ¥ "+startPrice+" - "+endPrice+"</a></li>";
         }
         if(startPrice != 0 && endPrice == 1500){
            require_string+="<li data-type='data-money' onclick='removeThis(this)'><a href='javascript:;'>"+" ¥ "+startPrice+" + </a></li>";
        }
    }else{
        if(startPrice != "0"){
            require_string+="<li data-type='data-money' onclick='removeThis(this)'><a href='javascript:;'>"+" ¥ "+startPrice+"</a></li>";
        }
    }
if($("#keywordIpt").val().replace(" ","").length != 0 && $("#keywordIpt").val() != "请输入商圈、景点、房间名、房东名等"){
    require_string+="<li data-type='data-keyword' onclick='removeThis(this)'><a href='javascript:;'>"+$("#keywordIpt").val()+"</a></li>";
}
$("#requireList").html(require_string);
    // TODO：筛选条件加载数据
}
