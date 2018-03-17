var height = $(window).height();

$('#swiper-slide1').css({'height':height});

var index =0;       
            /*var mySwiper = new Swiper ('.swiper-container', {
                direction: 'vertical',
                loop: false,
                speed:300,
                onSlideChangeEnd: function(swiper){
                    index = mySwiper.activeIndex;
                    
                    if(index==0){
                        $('#swiper-slide2').addClass('swiper-no-swiping');
                    }
                }
                
                
               
              });*/

            var move            = null,         //触摸能滑动页面
                start           = true,         //控制动画开始
                startM          = null,         //开始移动
                position        = null;         //方向值
                
                var dir=0;
            //绑定事件
            function changeOpen(e){
                $('#swiper-slide1').on('mousedown touchstart',page_touchstart);
                $('#swiper-slide1').on('mousemove touchmove',page_touchmove);
                $('#swiper-slide1').on('mouseup touchend mouseout',page_touchend);
            };

 //绑定事件
            function changeOpen2(e){
                $('#swiper-slide2').on('mousedown touchstart',page_touchstart2);
                $('#swiper-slide2').on('mousemove touchmove',page_touchmove2);
                $('#swiper-slide2').on('mouseup touchend mouseout',page_touchend2);
            };
          
                
                //开启事件绑定滑动
            changeOpen();
changeOpen2();
            //触摸（鼠标按下）开始函数
            function page_touchstart(e){
                if (e.type == "touchstart") {
                    initP = window.event.touches[0].pageY;
                }
                //console.log('initP='+initP);
                
            };
            

            //触摸移动（鼠标移动）开始函数
            var t=0;
            function page_touchmove(e){
                e.preventDefault();
                e.stopPropagation();  
                if (e.type == "touchmove") {
                    moveP = window.event.touches[0].pageY;
                }
                
                t = moveP-initP;

                if(t>0){
                    t=0
                }
                $('#swiper-slide1').css({'top':t+'px'});

               
                
                
            };

            //触摸结束（鼠标起来或者离开元素）开始函数
            function page_touchend(e){
                if(Math.abs(t)>100){
                     $('#swiper-slide1').animate({'top':-height+'px'},400);
                     $('.main').scrollTop(0);
                }else{
                    $('#swiper-slide1').animate({'top':'0px'},400);
                }
                if((initP-moveP)>0){
                    dir=0;
                }else{
                    dir=1;
                }
            };


         //触摸（鼠标按下）开始函数
         function page_touchstart2(e){
             if (e.type == "touchstart") {
                 initP = window.event.touches[0].pageY;
             }
         };
         


         //触摸移动（鼠标移动）开始函数
         function page_touchmove2(e){
               
             if (e.type == "touchmove") {
                 moveP = window.event.touches[0].pageY;
             }
             
             
           t = (moveP-initP-height);
            if($('.main').scrollTop()==0){
                 $('#swiper-slide1').css({'top':t});
            }
         };

         //触摸结束（鼠标起来或者离开元素）开始函数
         function page_touchend2(e){
             if((initP-moveP)>0){
                 dir=0;
             }else{
                 dir=1;
             }

           if($('.main').scrollTop()==0 && dir==1 && Math.abs(t)>200){
                $('#swiper-slide1').animate({'top':'0px'},300);
           }

             
           
         };
/*$('.main').scroll(function(){
     if(Math.abs(t)>200 && dir==1 && $('.main').scrollTop()>10){
       //$('#swiper-slide1').animate({'top':'0px'},300);
    }
});*/

