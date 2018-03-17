/**
 * Created by mqzZoom on 16/8/4.
 */
$(function(){

    // 地图 初始化地图当前位置
    var lg = $("#longitude").val();
    var la = $("#latitude").val();
    if(lg=="" || la==""){
        /*if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                var coords = position.coords;
                lg = coords.longitude;
                la = coords.latitude;
                alert(lg + "--" + la);
            });
        }else{*/
            lg = 116.3975780499;
            la = 39.9087677478;
        //}
    }
    setMap(lg,la,15)


    //当前状态
    var nowType='whole';

    /*function setMap(){
        var map = new BMap.Map("allmap");
        var point = new BMap.Point(116.331398,39.897445);
        map.centerAndZoom(point,12);
        map.addControl(new BMap.ZoomControl());
        // 创建地址解析器实例
        var myGeo = new BMap.Geocoder();
        var geolocation = new BMap.Geolocation();

        if($.trim($("#J_select_ipt").val()).length != 0 || $.trim($("#J_enter_ipt1").val()).length != 0 || $.trim($("#J_enter_ipt2").val()).length != 0){
            myGeo.getPoint($("#J_select_ipt").val()+$("#J_enter_ipt2").val()+$("#J_enter_ipt1").val(), function(point){
                if (point) {
                    map.centerAndZoom(point, 16);
                    map.addOverlay(new BMap.Marker(point));
                    $("#longitude").val(point.lng);
                    $("#latitude").val(point.lat);
                }
            }, $("#J_select_ipt").val());
        }else{
            geolocation.getCurrentPosition(function(r){
                if(this.getStatus() == BMAP_STATUS_SUCCESS){
                    var mk = new BMap.Marker(r.point);
                    map.addOverlay(mk);
                    map.panTo(r.point);

                    var pt = r.point;
                    $("#longitude").val(point.lng);
                    $("#latitude").val(point.lat);
                }
            },{enableHighAccuracy: true})
        }
    }*/

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

        $('#J_all_in').click(function(){
            oUl.css('display','none');
        });
    })();

    //出租类型交互
    (function(){
        var oHidden=$('#J_chuzuType');
        var oIsNone=$('#J_isNone');
        var oWhole=$('#J_whole');
        var oOne=$('#J_one');

        if(oIsNone.val()>=1&&oHidden.val()==0){
            oOne.hide();
        }
        else if(oIsNone.val()>=1&&oHidden.val()==1){
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
            nowType='one';
        }

        oWhole.click(function(){
            oOne.removeClass('on');
            $(this).addClass('on');
            oHidden.val(0);

            if(nowType=='whole'){
                return false;
            }

            nowType='whole';
        });

        oOne.click(function(){
            oWhole.removeClass('on');
            $(this).addClass('on');
            oHidden.val(1);

            if(nowType=='one'){
                return false;
            }

            nowType='one';
        });
    })();

    //点击下一步判空
    (function(){
        var oIpt1=$('#J_select_ipt');
        var oIpt2=$('#J_enter_ipt1');
        var oIpt3=$('#J_enter_ipt2');
        var oIpt4=$('#J_menpai');
        var oTishi=$('#J_red_tishi');
        var reg=/^\s*$/;

        //用户为点击的时候是false，点击后为true，返回结果后变回false
        var isClick=false;

        oIpt2.blur(function(){
            getGoogleLocationByKeyword();//重置地图
        });

        oIpt3.blur(function(){
            getGoogleLocationByKeyword();//重置地图
        });

        $('#J_next').click(function(){
            //判断是否已经点击过了
            if(isClick){
                return false;
            }

            //房源类型校验
            if($('#J_typeIpt').val()==''){
                $('#J_typeIpt').siblings('.J_tishi_txt').show();
                return false;
            }
            else{
                $('#J_typeIpt').siblings('.J_tishi_txt').hide();
            }

            if(nowCodeArr[0]=="100000" && (reg.test(oIpt1.val())||oIpt1.val().split(",").length<3)){
                oIpt1.addClass('on');
                oIpt2.removeClass('on');
                oIpt3.removeClass('on');
                oIpt4.removeClass('on');
                oTishi.html('请选择国家、城市、行政区');
                return false;
            }else if(reg.test(oIpt1.val())||oIpt1.val().split(",").length<2){
                oIpt1.addClass('on');
                oIpt2.removeClass('on');
                oIpt3.removeClass('on');
                oIpt4.removeClass('on');
                oTishi.html('请选择国家、城市');
                return false;
            }
//            else if(reg.test(oIpt2.val())){
//                oIpt2.addClass('on');
//                oIpt1.removeClass('on');
//                oIpt3.removeClass('on');
//                oIpt4.removeClass('on');
//                oTishi.html('请填写房屋所在小区名称');
//                return false;
//            }
            else if(reg.test(oIpt3.val())){
                oIpt3.addClass('on');
                oIpt1.removeClass('on');
                oIpt2.removeClass('on');
                oIpt4.removeClass('on');
                oTishi.html('请填写房屋所在街道／路');
                return false;
            }
            else if(oIpt4.val().length>20){
                oIpt3.removeClass('on');
                oIpt4.addClass('on');
                oIpt1.removeClass('on');
                oIpt2.removeClass('on');
                oTishi.html('楼号-门牌号过长');
                return false;
            }else if($("#latitude").val() == "" || $("#longitude").val() == ""){
                oTishi.html('经纬度没有定位，请重新输入街道地址');
                return false;
            }else{
                oIpt3.removeClass('on');
                oIpt1.removeClass('on');
                oIpt2.removeClass('on');
                oIpt4.removeClass('on');
                oTishi.html('');


                isClick=true;

                var paramData={"houseType":$("#J_typeIpt").attr("data-type"),"rentWay":$("#J_chuzuType").val(),"operateSeq":$("#operateSeq").val(),
                    "nationCode":nowCodeArr[0],"cityCode":nowCodeArr[1],"areaCode":nowCodeArr[2],"communityName":oIpt2.val(),"googleLongitude":$("#longitude").val(),
                    "googleLatitude":$("#latitude").val(),"houseStreet":oIpt3.val(),"detailAddress":oIpt4.val(),"houseBaseFid":$("#houseFid").val(),
                    "cityName":nowStrArr[1],"areaName":nowStrArr[2],"fid":$("#housePhyFid").val()};
                CommonUtils.ajaxPostSubmit("/houseIssue/insertHouseLocationMsg",paramData,function(data){
                    if(data.code==0){
                        if($("#J_chuzuType").val()==0){
                            $('#J_one').hide();
                        }
                        if($("#J_chuzuType").val()==1){
                            $('#J_whole').hide();
                        }
                        $('#J_isNone').val(1);

                        $("#houseFid").val(data.data.houseBaseFid);
                        $("#housePhyFid").val(data.data.housePhyFid);
                        window.location.href="/houseIssue/basicMsg/"+data.data.houseBaseFid;
                    }else if(data.code == 2){
                        window.location.href = SSO_USER_LOGIN_URL+window.location.href;
                    }else{
                        showShadedowTips(data.msg,2000);
                        isClick=false;
                    }
                });
            }
        });

        CommonUtils.ajaxPostSubmit("/houseIssue/getOpenNationLandlord",{},function(data){
            //循环生成选项
            makeLi(data,'J_select_guojia');
        });

    })();



    //选择区域
    var oIpt=$('#J_select_ipt');
    var oBox=$('#J_position_select');
    var codeH=$("#nowCodeArr");

    oIpt.click(function(e){
        oBox.show();
        e.stopPropagation();
    });

    $('#J_all_in').click(function(){
        oBox.hide();
    });


    //信息例子
    //var guojiaData=[{code:"100000",name:"中国"}];
    //makeLi(guojiaData,'J_select_guojia');

    //选择联动
    var oTitUl=$('#J_select_tit');
    var oGuojiaUl=$('#J_select_guojia');
    var oCityUl=$('#J_select_city');
    var oQuUl=$('#J_select_qu');

    var nowStrArr=[];
    var nowCodeArr=[];

    if(oIpt.val()!=""){
        nowStrArr=oIpt.val().split(",");
        nowCodeArr=codeH.val().split(",");
    }

    oGuojiaUl.delegate('li','click',function(e){
    	CommonUtils.ajaxPostSubmit("/houseIssue/getSubAreaByParentCode",{code:$(this).attr('data-code'),level:1},function(data){
            //循环生成选项
            makeLi(data,'J_select_city');
        });

        //改变顶部样式
        oTitUl.find('li').eq(0).removeClass('on');
        oTitUl.find('li').eq(1).addClass('on');

        //改变显示的选择框
        oGuojiaUl.hide();
        oCityUl.show();

        //当前项选中样式
        $(this).siblings('li').removeClass('on');
        $(this).addClass('on');

        //改变存储的字符串
        nowStrArr.length=0;
        nowCodeArr.length=0;
        nowStrArr[0]=$(this).html();
        nowCodeArr[0]=$(this).attr('data-code');
        oIpt.val(nowStrArr.join(','));
        codeH.val(nowCodeArr.join(','));
        oIpt.attr('data-code',nowCodeArr.join(','));
        e.stopPropagation();
    });

    oCityUl.delegate('li','click',function(e){

        CommonUtils.ajaxPostSubmit("/houseIssue/getSubAreaByParentCode",{code:$(this).attr('data-code')},function(data){
            //循环生成选项
            makeLi(data,'J_select_qu');
        });

        if(nowCodeArr[0] == "100000"){
            //改变顶部样式
            oTitUl.find('li').eq(1).removeClass('on');
            oTitUl.find('li').eq(2).addClass('on');

            //改变显示的选择框
            oCityUl.hide();
            oQuUl.show();

            //当前项选中样式
            $(this).siblings('li').removeClass('on');
            $(this).addClass('on');
        }else {
            oBox.hide();
        }


        //改变存储的字符串
        nowStrArr.length=1;
        nowCodeArr.length=1;
        nowStrArr[1]=$(this).html();
        nowCodeArr[1]=$(this).attr('data-code');
        oIpt.val(nowStrArr.join(','));
        codeH.val(nowCodeArr.join(','));
        oIpt.attr('data-code',nowCodeArr.join(','));
        e.stopPropagation();
        getGoogleLocationByKeyword();//重置地图
    });

    oQuUl.delegate('li','click',function(e){

        //改变存储的字符串
        nowStrArr.length=2;
        nowCodeArr.length=2;
        nowStrArr[2]=$(this).html();
        nowCodeArr[2]=$(this).attr('data-code');
        oIpt.val(nowStrArr.join(','));
        codeH.val(nowCodeArr.join(','));
        oIpt.attr('data-code',nowCodeArr.join(','));
        e.stopPropagation();
        getGoogleLocationByKeyword();//重置地图
        oBox.hide();
    });

    //点击国家选项
    oTitUl.find('li').eq(0).click(function(ev){

        $(this).siblings('li').removeClass('on');
        $(this).addClass('on');
        oGuojiaUl.show();
        oQuUl.hide();
        oCityUl.hide();

        ev.stopPropagation();

    });

    //点击城市选项
    oTitUl.find('li').eq(1).click(function(ev){

        ev.stopPropagation();
        if(nowStrArr.length<1){
            return false;
        }

        oGuojiaUl.hide();
        oQuUl.hide();
        oCityUl.show();

        $(this).siblings('li').removeClass('on');
        $(this).addClass('on');

    });

    //点击区域选项
    oTitUl.find('li').eq(2).click(function(ev){
        ev.stopPropagation();
        if(nowStrArr.length<2){
            return false;
        }

        oGuojiaUl.hide();
        oCityUl.hide();
        oQuUl.show();

        $(this).siblings('li').removeClass('on');
        $(this).addClass('on');

    });

    function makeLi(data,id){
        $('#'+id).html('');
        for(var i=0;i<data.length;i++){
            $("<li data-code='"+data[i].code+"'>"+data[i].name+"</li>").appendTo('#'+id);
        }
    }
});