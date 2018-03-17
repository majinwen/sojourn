var move = null,         //触摸能滑动页面
start   = true,         //控制动画开始
startM  = null,         //开始移动
position = null;         //方向值
            
var dir=0;

            //绑定事件
           
                           //绑定事件
                   function changeOpen(){
                       $('.moveLi').on('mousedown touchstart',page_touchstart);
                       $('.moveLi').on('mousemove touchmove',function(e){
                                page_touchmove(e,$(this));
                       });
                       $('.moveLi').on('mouseup touchend mouseout',page_touchend);
                   };





            //开启事件绑定滑动
            changeOpen();
            //触摸（鼠标按下）开始函数
            function page_touchstart(e){
                if (e.type == "touchstart") {
                    initP = window.event.touches[0].pageX;
                }
                
                
            };
            

            //触摸移动（鼠标移动）开始函数
            var l=0;
            var w = 0;
            var moveP;
            function page_touchmove(e,obj){
                e.preventDefault();
                e.stopPropagation();
                if (e.type == "touchmove") {
                    moveP = window.event.touches[0].pageX;
                }
                
                l = moveP-initP;
                var index = obj.index();
                
                var w = 0 - $('.moveLi').eq(index).find(".del_btn").width();
                if(l>0){
                    //向右
                    obj.stop().animate({'left':'0px'},200);

                }

                if(l<0){
                    //向左
                    obj.stop().animate({'left':w+'px'},200);

                }
            };
            //触摸结束（鼠标起来或者离开元素）开始函数
            function page_touchend(e){
                
               // initP=0;
               // moveP=0;
            };

            
            $(".icon_del").each(function(){
                $(this).click(function(){
                    var w = 0 - $(this).parents("li").find(".del_btn").width();
                    $(this).parents("li").stop().animate({'left':w+'px'},200);
                })
            })
             // 删除事件
            //$(".del_btn").each(function(){
               // $(this).click(function(){
                 //   $(this).parents("li").remove();
                //})
            //})
            