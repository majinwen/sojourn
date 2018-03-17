var startDate = $('#startDate').val();
var endDate = $('#startDate').val();
var inDate = $('#inDate').val();
var outDate = $('#outDate').val();
var iprice = 0;
var tempId=[];

var isRent = $("#isRent").val();// 0 设为已租 1 可租
var monthStr = $('#monthStr').val();
var start = $("#start").val();

var month = monthStr.split("-")[0];
$(function(){
    // 设置弹层高度
    $(window).resize(function() {
        countModalHeight();
    });
    // 关闭弹层
    $('.modal-content .close').click(function() {
        closeModalBox();
    });
    $("#modal").on('click', function() {
        $(this).hide();
    })
    $('.modal-content').on('click', function(event) {
        event.stopPropagation();
    })
    $(".set-box").delegate("li","click",function(){
        if(!$(this).hasClass("current")){
            $(".btn-right").removeClass("disabled");
        }
        $(this).addClass("current").siblings().removeClass("current");
        isRent = $(this).attr("data-rent");
        if(isRent == 0){
            $(".ipt-box-md").hide();
        }else if(isRent == 1){
            $(".ipt-box-md").show();
        }

    })
    selectScroll('houseSelect');
    selectScroll('monthSelect');
    $(".btn-group").delegate(".btn-right","click",function(){
        if($(this).hasClass("disabled")){
            return;
        }else{
            $(this).addClass("disabled");
        }
    })
    // 日历
    $('#calendarBox').delegate('td','click',function(){
        if(!$(this).hasClass("un_rented") && !$(this).hasClass("have_rented")){
            showModalBox("rentBox");
            var _id = $(this).attr("id");
            $('#calendarBox td').each(function(){
                $(this).removeClass("active");
            })
            $(this).addClass("active");
            if($(this).hasClass("can_rented")){
                // 可租 －设为已租
                $(".set-box").find(".have_rented_li").addClass("current").siblings().removeClass("current");
                isRent = 0;// 出租方式 －－可租
                $(".ipt-box-md").hide();
            }else if($(this).hasClass("set_rented")){
                // 设为已租 －可租
                $(".set-box").find(".can_rented_li").addClass("current").siblings().removeClass("current");
                isRent = 1;// 出租方式 －－设为已租
                $(".ipt-box-md").show();
            }
            $("#price").val("");
            infoDate(_id);
        }

    });
    // 月份房源下拉框
    $(document).click(function(){
        $('.selects_ul').hide();
    });
    $(".select_box").delegate("span","click",function(e){
        $(this).siblings(".selects_ul").show();
        $("body").addClass("hidden");
        e.stopPropagation();
    })
    $(".selects").delegate("li","click",function(){
        $(this).parents(".select_box").find(".select_span").html($(this).html());
        $(this).addClass("active").siblings().removeClass("active");
        $("body").removeClass("hidden");
        var id = $(this).parents(".selects").attr("id");
        if(id == 'monthSelect'){
            $("#monthStr").val($(this).attr("data-val"));
            $("#start").val($(this).attr("data-val")+'-01');
        }else if(id == 'houseSelect'){
            //更换房源直接更换url，刷新页面
            var r = $(this).attr("data-rent");
            var url = localhostPaht+"/house/"+r+"/";
            if(r == 0){
                url += $(this).attr("data-hfid")+"/calendar";
            }else if(r == 1){
                url += $(this).attr("data-rfid")+"/calendar";
            }
            window.location.href = url;
        }
        selectScroll(id);
        loadCalendar();
        iconDefault($("#start").val());
    })

    $(".calendars_bar").delegate('.icon','click',function(){
        var _y = parseInt($("#monthStr").val().split("-")[0],10);
        var _m = parseInt($("#monthStr").val().split("-")[1],10);
        if(!$(this).hasClass("disabled")){
            if($(this).hasClass("icon_prev")){
                if(_m > 1){
                    _m--;
                }else{
                    _m = 12;
                    _y--;
                }
            }else if($(this).hasClass("icon_next")){
                if(_m < 12){
                    _m++;
                }else{
                    _m =1;
                    _y++;
                }
            }
            // _m = _m<10?'0'+_m:_m;
            console.log();
            $("#monthBox").find(".select_span").html(_y+'年'+_m+'月');
            var _str = '';
            _str = _y+'-'+_m;
            $("#monthStr").val(_str);
            $("#start").val(_str+'-01');
            for(var m = 0 ; m < $("#monthSelect").find("li").length ; m ++){
                if($("#monthSelect li:eq("+m+")").attr("data-val") == _str){
                    $("#monthSelect li:eq("+m+")").addClass("active").siblings().removeClass("active");
                }
            }
            selectScroll('monthSelect');
            loadCalendar();
            iconDefault($("#start").val());
        }

    })
})
/* 打开弹层 */
function showModalBox(id) {
    $('body').addClass('hidden');
    $('#modal').show();
    $('#' + id).show().siblings().hide();
    $('#' + id).find(".btn-right").removeClass("disabled");
    countModalHeight();
}

/* 关闭弹层 */
function closeModalBox() {
    $('body').removeClass('hidden');
    $('#modal').hide();
}

/* 计算弹层的高度 */
function countModalHeight() {
    var t = ($(window).height() - 200) / 2 ;
    t = t < 0 ? 0 : t;
    $('.modal-content').css({
        'margin-top': t + 'px'
    });
}
// 时间带入
function infoDate(id){
    var date = id.replace('td_','');
    $( "#startDate" ).val(date);
    $( "#endDate" ).val(date);
    outDate = $('#outDate').val();
    $( "#startDate" ).datepicker({"minDate":date,"maxDate":outDate,onSelect:function(){
        compareDate();
    }});
    $( "#endDate" ).datepicker({"minDate":date,"maxDate":outDate,onSelect:function(){
        compareDate();
    }});
}
// 价格区间
function compareDate(){
    endDate = $( "#endDate" ).val();
    startDate = $( "#startDate" ).val();
    if(startDate != "" && endDate != ""){
        $( "#endDate" ).datepicker("option","minDate",startDate);
        if(startDate == endDate){
            $("#price").attr("placeholder","当晚价格");
        }else if(startDate != endDate){
            $("#price").attr("placeholder","每晚价格");
        }
    }
}
function timestampDate(date){
    date = date.replace(/-/g,'/');
    return new Date(date).getTime()                                   ;
}
//校验空的方法
function isNullOrBlank(obj){
    return obj == undefined || obj == null || $.trim(obj).length == 0 || obj =='';
}
//去价格设置页面
function toPriceDetail(){
    var houseBaseFid=$("#houseFid").val();
    var houseRoomFid=$("#roomFid").val();
    var	rentWay=$("#rentWay").val();
    if(rentWay == 0){
        if(isNullOrBlank(houseBaseFid)){
            showShadedowTips("房源不存在，参数错误","1000");
            return;
        }
    }else if(rentWay == 1){
        if(isNullOrBlank(houseBaseFid) && isNullOrBlank(houseRoomFid)){
            showShadedowTips("房间不存在，参数错误","1000");
            return;
        }
    }
    window.location.href = "/house/toPriceDetail?houseBaseFid="+houseBaseFid+"&houseRoomFid="+houseRoomFid+"&rentWay="+rentWay;
}
//保存价格
function savePrice(){
    iPrice = $("#price").val();
    if(iPrice != ""){
        iPrice = parseInt(iPrice,10);
        if(iPrice <  60){
            showConfirm("价格不得低于60元","确定");
            return;
        }
    }
    endDate = $( "#endDate" ).val();
    startDate = $( "#startDate" ).val();
    var param = {
        startDate:startDate,
        endDate:endDate,
        rentWay:$("#rentWay").val(),
        houseFid:$("#houseFid").val(),
        roomFid:$("#roomFid").val()
    }
    var tempId = [];
    if(isRent == 0){
        $.post("/house/lockHouse",param,function(data){

            if(data.code == 0){
                tempId = data.data.lockDayList;

                setPrice(tempId)
            }else{
                $(".btn-right").removeClass("disabled");
            }

        },'json');
    }else if(isRent == 1){
        param.specialPrice = iPrice;
        $.post("/house/unlockHouse",param,function(data){

            if(data.code == 0){
                tempId = data.data.lockDayList;
                setPrice(tempId)
            }else{
                $(".btn-right").removeClass("disabled");
            }
        },'json');
    }


}
function setPrice(tempId){
    iPrice = $("#price").val();
    for(var i=0; i<tempId.length; i++){
        if($('#'+tempId[i]).hasClass("have_rented")){
            showShadedowTips("已出租房源价格将不会被修改","1000");
        }else{
            if(isRent == 1){// 可租
                $('#'+tempId[i]).removeClass("set_rented").addClass("can_rented");
                if(iPrice != "" && iPrice != " "){
                    $('#'+tempId[i]).find('i').html('¥'+iPrice);
                }

            }else if(isRent == 0){// 设为已租
                $('#'+tempId[i]).removeClass("can_rented").addClass("set_rented");
            }
        }

        $('#'+tempId[i]).removeClass('active');
    }
    closeModalBox();
}

// 选择房源滚动
function selectScroll(id){
    var index = $("#"+id).find("li.active").index();
    var top = $("#"+id).offset().top;
    $("#"+id).stop().animate({scrollTop:200},200);
    if(index >= 10){
        var _t = (index-5)*30;
        $("#"+id).scrollTop(_t);
    }else{
        $("#"+id).scrollTop(0);
    }
}
// 加载日历
function loadCalendar(){
    var year = $("#monthStr").val().split("-")[0];
    var month = $("#monthStr").val().split("-")[1];
    month = month<10?'0'+month:month;
    var s = year+'-'+month+'-01';
    var e = calendar.checNextMonthDay(s,0);
    calendar.showCalendar(s,e);
}
// 页面加载完成判断左右按钮状态
function iconDefault(date){
    var _date = timestampDate(date);
    inDate = inDate.split('-')[0]+'-'+inDate.split('-')[1]+'-01';
    var _info = timestampDate(inDate);
    outDate = outDate.split('-')[0]+'-'+outDate.split('-')[1]+'-01';
    var _out = timestampDate(outDate);
    // console.log(_date,_info,_out);
    if(_date <= _info){
        $(".icon_prev").addClass("disabled");
    }else{
        $(".icon_prev").removeClass("disabled");
    }
    if(_date >= _out){
        $(".icon_next").addClass("disabled");
    }else{
        $(".icon_next").removeClass("disabled");
    }

}
// 加载月份
loadMonth(inDate,outDate)
function loadMonth(start,end){
    var start_y = parseInt(start.split('-')[0],10);
    var start_m = parseInt(start.split('-')[1],10);
    var end_y = parseInt(end.split('-')[0],10);
    var end_m = parseInt(end.split('-')[1],10);
    var monthArr = [];
    var monthLen = (end_y-start_y-1)*12;
    var e_m = 12;
    var now = new Date();
    var now_y = now.getFullYear();
    var now_m = now.getMonth()+1;
    monthLen = monthLen + end_m - start_m;
    for(var _y = start_y ; _y <= end_y ; _y++){
        if(_y == end_y){
            e_m = end_m;
        }
        for(var _m = start_m ; _m <= e_m; _m++){
            if(_m == 12){
                start_m = 1;
            }
            if(_y == now_y && _m == now_m){
                _m = _m<10?'0'+_m:_m;
                monthArr.push('<li data-val="'+_y+'-'+_m+'" class="active"><a href="javascript:;">'+_y+'年'+_m+'月'+'</a></li>');
            }else{
                _m = _m<10?'0'+_m:_m;
                monthArr.push('<li data-val="'+_y+'-'+_m+'"><a href="javascript:;">'+_y+'年'+_m+'月'+'</a></li>');
            }
        }
    }
    var _monthStr = '';
    for(var _s = 0 ; _s < monthArr.length ; _s++){
        _monthStr+=monthArr[_s];
    }
    $("#monthSelect").html(_monthStr);
    now_m = now_m<10?'0'+now_m:now_m;
    $("#monthBox").find(".select_span").html(now_y+'年'+now_m+'月');
}
(function ($) {

    // 定义对象
    var calendar = {
        iStartDate:"",
        iEndDate:"",
        iMyEndDate:"",
        iPage:0
    }
    /**
     * 初始化
     */
    calendar.init = function(){
        var _n = new Date();
        var _n_y = _n.getFullYear();
        var _n_m = _n.getMonth()+1;
        _n_m = _n_m<10?'0'+_n_m:_n_m;
        $('#monthStr').val(_n_y+'-'+ _n_m);
        calendar.iStartDate =_n_y+'-'+ _n_m + '-01';
        iconDefault(calendar.iStartDate);
        calendar.iEndDate = calendar.checNextMonthDay(calendar.iStartDate,0);
        //alert(calendar.iEndDate);
        calendar.showCalendar(calendar.iStartDate,calendar.iEndDate);
    }

    calendar.showCalendar = function(start,end){
        start = start.replace(/-/g, '/');
        end = end.replace(/-/g, '/');
        var data = {
            houseBaseFid:$("#houseFid").val(),
            houseRoomFid:$("#roomFid").val(),
            rentWay:$("#rentWay").val(),
            startDate:start,
            endDate:end
        };

        $.getJSON("/house/leaseCalendar",data,function(data){
            if(data.code == 0){
                var list = data.data.list;
                calendar.wirteDate(list);
            }
        });
    }

    /**
     * 计算当月1号是星期几
     */
    calendar.checWeek = function(date){
        var _date = new Date(Date.parse(date.replace(/-/g, '/')));
        return _date.getDay();
    }

    /**
     * 取得下一个月的最后一天
     */
    calendar.checNextMonthDay = function(date,n){
        date = date.replace(/-/g, '/');
        var year = new Date(date).getFullYear();
        var month = new Date(date).getMonth()+1;
        var nextMonth = month+n;
        var nextDate='';
        var day=0;

        if(nextMonth==1 || nextMonth==3 || nextMonth==5 || nextMonth==7 || nextMonth==8 || nextMonth==10 || nextMonth==12){
            day=31;
        }
        if( nextMonth==4 || nextMonth==6 || nextMonth==9 || nextMonth== 11){

            day=30;
        }

        if(nextMonth==2 ){
            if(year%4){
                day=29;
            }else{
                day=28;
            }

        }
        nextMonth = nextMonth<10?'0'+nextMonth:nextMonth;
        nextDate = year+'-'+nextMonth+'-'+day;
        return nextDate;
    }
    /**
     * 展示列表
     */
    calendar.wirteDate = function (list){
        var _html="";
        var myDate = new Date();
        var myYear = myDate.getFullYear();
        var myMoth = (myDate.getMonth()+1) < 10 ? '0'+(myDate.getMonth()+1) : (myDate.getMonth()+1);
        var myDate = myDate.getDate()< 10 ? '0'+myDate.getDate() : myDate.getDate();
        var myCurDay =myYear+'-'+myMoth+'-'+myDate;
        var week=calendar.checWeek(list[0].monthStr+'-01');
        var tempTd=[];
        var iDate=0;
        var forDateVal=''; // 用于存判断日期大小的值
        var forDateValEnd=''; // 用于存判断日期大小的值
        var pirce=0;

        _html +='<tr>'
            +'<th>日</th>'
            +'<th>一</th>'
            +'<th>二</th>'
            +'<th>三</th>'
            +'<th>四</th>'
            +'<th>五</th>'
            +'<th>六</th>'
            +'</tr>'
            +'<tr>';
        for(var i=0; i<week; i++){
            tempTd.push('<td></td>');
        }
        for(var i = 0 ; i < list[0].calendarList.length; i ++ ){
            var a = list[0].calendarList[i];

            iDate = a.date.slice(8);
            forDateVal = calendar.comptime(a.date,myCurDay);
            calendar.iMyEndDate = $("#outDate").val();
            forDateValEnd = calendar.comptime(a.date,calendar.iMyEndDate);
            pirce = a.price/100;

            if(forDateVal=='C'){
                if(a.status==0){// 可租
                    tempTd.push('<td class="can_rented" id="td_'+a.date+'"><div class="dates"><span class="date">'+iDate+'</span> 今天<p><i>¥'+pirce+'</i></p></td>');
                }
                if(a.status==1){// 已租
                    tempTd.push('<td class="have_rented" id="td_'+a.date+'"><div class="dates"><span class="date">'+iDate+'</span> 今天<p><i>¥'+pirce+'</i></p></td>');
                }
                if(a.status==2){// 设为已租
                    tempTd.push('<td class="set_rented" id="td_'+a.date+'"><div class="dates"><span class="date">'+iDate+'</span> 今天<p><i>¥'+pirce+'</i></p></td>');
                }

            }else if(forDateVal=='B' || forDateValEnd=='A'){
                tempTd.push('<td class="un_rented" id="td_'+a.date+'"><div class="dates"><span class="date">'+iDate+'</span><p><i>¥'+pirce+'</i></p></td>');

            }else{

                if(a.status==0){// 可租
                    tempTd.push('<td class="can_rented" id="td_'+a.date+'"><div class="dates"><span class="date">'+iDate+'</span><p><i>¥'+pirce+'</i></p></td>');
                }
                if(a.status==1){// 已租
                    tempTd.push('<td class="have_rented" id="td_'+a.date+'"><div class="dates"><span class="date">'+iDate+'</span><p><i>¥'+pirce+'</i></p></td>');
                }
                if(a.status==2){// 不可租
                    tempTd.push('<td class="set_rented" id="td_'+a.date+'"><div class="dates"><span class="date">'+iDate+'</span><p><i>¥'+pirce+'</i></p></td>');
                }
            }




        }


        for(var i=0; i<tempTd.length; i++){
            _html+=tempTd[i];

            if(i%7==6){
                _html+='</tr><tr>';
            }
        }



        _html+='</tr>'

        $("#calendarTab").html(_html);
    }

    calendar.comptime = function(start,end) {
        var beginTime = start.replace(/-/g, '/');
        var endTime = end.replace(/-/g, '/');
        var a = (Date.parse(endTime) - Date.parse(beginTime))/3600/1000;
        if (a < 0) {
            return 'A';
        } else if (a > 0) {
            return 'B';
        } else if (a == 0) {
            return 'C';
        } else {
            return 'exception'
        }
    }

    calendar.init();
    window.calendar = calendar;

}(jQuery));


function toExportDate () {
    showModalBox('exportBox');
    //是否已同步
    var isBind=$("#relateStatus").val();
    // 信息提示
    setMsg();
    if(isBind && Number(isBind)==0){//已同步日历链接不可编辑
        $("#link").attr("disabled","disabled");
        $("#toExport").hide();$("#stopExport").show();
    }else{//未同步日历链接可编辑
        $("#link").removeAttr("disabled");
        $("#toExport").show();$("#stopExport").hide();
    }

}
function setMsg(){
    $("#exportMsg").html("保存并同步日历成功，我们每2个小时会为您同步一次日历");
}
function toExport(){
    // 防止多次点击
    if($("#toExport").hasClass("disabled")){
        return;
    }

    var link = $("#link").val();
    if(link.length <= 0 || !isValidURL(link)){
        $("#exportMsg").html("您的日历链接为无效链 接,请检查链接地址或稍后再次尝试 。");
        return;
    }

    var oldCalendarUrl = $("#relateUrl").val();
    var fid = $.trim($("#relateFid").val());
    if(oldCalendarUrl && oldCalendarUrl==link){//恢复
        $.ajax({
            type: "POST",
            url: "/house/updateHouseRelate",
            dataType:"json",
            data: {"fid":fid, "isDel":0},
            success: function (data) {
                if(data){
                    if (data.code == 0){
                        // 交互成功后
                        $("#toExport").hide();$("#stopExport").show();
                        $("#link").attr("disabled","disabled");
                        $("#relateStatus").val(0);
                    }else{
                        // 交互成功或失败后
                        $("#toExport").removeClass("disabled");
                    }
                }else{
                    // 交互成功或失败后
                    $("#toExport").removeClass("disabled");
                }
            },
            error: function(result) {
                // 交互成功或失败后
                $("#toExport").removeClass("disabled");
            }
        });
	}else{

        $("#toExport").addClass("disabled");

        var houseFid = $.trim($("#houseFid").val());
        var roomFid = $.trim($("#roomFid").val());
        var rentWay = $("#rentWay").val();
        if (houseFid == "" || link == "" || rentWay == ""){
            return;
        }
        if (rentWay == "1" && roomFid == ""){
            return;
        }
        $.post("/house/saveHouseRelate",{"houseFid":houseFid,"roomFid":roomFid,"rentWay":rentWay,"calendarUrl":link},function (data) {
            if (data.code == 0){
                // 交互成功后
                $("#toExport").hide();$("#stopExport").show();
                $("#link").attr("disabled","disabled");
                window.location.reload();
            }else{
                // 交互成功或失败后
                $("#toExport").removeClass("disabled");
            }

        },"json");

    }

}


function stopExport(){
    // 防止多次点击
    if($("#stopExport").hasClass("disabled")){
        return;
    }

    var fid = $.trim($("#relateFid").val());
    layer.confirm("确认停止同步？", function(){
        $("#stopExport").addClass("disabled");
        $.ajax({
            type: "POST",
            url: "/house/updateHouseRelate",
            dataType:"json",
            data: {"fid":fid, "isDel":1},
            success: function (data) {
                if(data){
                    if (data.code == 0){
                        // 交互成功后
                        $("#toExport").show();$("#stopExport").hide();
                        $("#link").removeAttr("disabled");
                        $("#relateStatus").val(1);
                    }else{
                        // 交互成功或失败后
                        $("#stopExport").removeClass("disabled");
                    }
                }else{
                    // 交互成功或失败后
                    $("#stopExport").removeClass("disabled");
                }
            },
            error: function(result) {
                // 交互成功或失败后
                $("#stopExport").removeClass("disabled");
            }
        });
    });


}
// 验证链接的有效性
function isValidURL(url){
    var urlRegExp=/^((https|http|ftp|rtsp|mms)?:\/\/)+[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
    if(urlRegExp.test(url)){
        return true;
    }else{
        return false;
    }
}

