/**
 * Created by mqzZoom on 16/6/29.
 */
$(function() {
    // 初始化资源加载器
    var resList = [
        /*'img/seed/arrow.png',
        'img/seed/arrow.png',
        'img/seed/item_add.png',
        'img/seed/item_chose.png',
        'img/seed/item_is.png',
        'img/seed/item_nochose.png',
        'img/seed/item_not.png',
        'img/seed/logo.png',
        'img/seed/no.png',
        'img/seed/p1_tit.png',
        'img/seed/p1_txt1.jpg',
        'img/seed/p1_txt2.png',
        'img/seed/p1_txt3.png',
        'img/seed/p1_txt4.png',
        'img/seed/p1_txt5.png',
        'img/seed/p2_tit.png',
        'img/seed/p2_txt1.png',
        'img/seed/p2_txt2.png',
        'img/seed/p2_txt3.png',
        'img/seed/p2_txt4.png',
        'img/seed/p2_txt5.png',
        'img/seed/p3_bg.jpg',
        'img/seed/p3_tit.png',
        'img/seed/p4_bg.jpg',
        'img/seed/p4_txt1.png',
        'img/seed/p4_txt2.png',
        'img/seed/p4_txt3.jpg',
        'img/seed/p4_txt4.png',
        'img/seed/p5_bg.jpg',
        'img/seed/p5_txt1.png',
        'img/seed/p5_txt2.png',
        'img/seed/p5_txt3.jpg',
        'img/seed/p5_txt4.png',
        'img/seed/rp.png',
        'img/seed/xiala.png',
        'img/seed/ye.png'*/
    ];


    // 初始化滑动组件
    slider = new mo.PageSlide({
        target: $('#page-wrapper .content > li')
    });

    slider.on('beforechange', function() {
        slider.touchMove(true);
    }).on('change', function(o,index){
        toAni($('.page').eq(index));
    });

    function toAni(obj){
        $(obj).siblings().find('[data-ani]').each(function(){
            var ani = $(this).attr('data-ani');
            $(this).removeClass(ani +' animated').css('opacity',0);
        });

        $(obj).find('[data-ani]').each(function(){

            var speed = parseInt($(this).attr('data-delay'));
            var ani = $(this).attr('data-ani');
            var _this = $(this);
            setTimeout(function(){
                _this.css('opacity',1).addClass(ani +' animated');
            },speed);
        });
    }

    loader = new mo.Loader(resList, {
        loadType : 1,
        minTime : 0,
        onLoading : function(count,total){
            var num = parseInt((count/total)*100);
            $('.loading .bar').width(num+'%');
        },
        onComplete : function(time){

            setTimeout(function() {
                $('.loading').animate({
                        'opacity': 0},
                    300, function() {
                        $('.parallax-arrow').fadeIn(800);
                        $('.loading').remove();
                    });
                slider.next();
                $('.icon-arrow').show();
            }, 500);
        }
    });

    // Android 2.3.x 动画退化处理
    function decideAndroid23(ua) {
        ua = (ua || navigator.userAgent).toLowerCase();
        return ua.match(/android.2\.3/) ? true : false;
    }

    if( decideAndroid23() ){
        $("#page-wrapper").addClass("android23");
    }

    // 翻转屏幕提示
    window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", function() {
        detectOrientation();
    }, false);

    (function() {
        detectOrientation();
    })();

    function detectOrientation() {
        if (window.orientation === 180 || window.orientation === 0) {
            $('.dialog-guide').hide();
        }
        if (window.orientation === 90 || window.orientation === -90 ){
            $('.dialog-guide').show();
        }
    }

});