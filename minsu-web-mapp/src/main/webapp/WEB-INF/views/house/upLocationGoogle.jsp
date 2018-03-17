<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 


<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="applicable-device" content="mobile">
    <meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="yes" name="apple-touch-fullscreen" />
    <title>发布房源</title>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}002">
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/css_830.css${VERSION}002">
    
         <style>
            body{
                font-family: Circular, "Helvetica Neue", Helvetica, Arial, sans-serif

            }
            .ceter-icon{
                  width: 36px;
                  height: 36px;
                  position: absolute;
                  left: 50%;
                  top: 23%;
                  margin-left: -12px;
                  margin-top: -36px;
                  background: url(${staticResourceUrl}/images/marker_high.png) no-repeat;
                  background-size: 100% 100%;
              }
            .addressTag{
                font-size: 14px;
                color: #666666;
                line-height: 30px;
                position: absolute;
                background-color: white;
                left: 50%;
                top: 14%;
                margin-top: -36px;
                padding: 0 21px;
                box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.5);
                max-width: 90%;
                /*单行显示,多余字符显示省略号*/
                text-overflow:ellipsis; white-space:nowrap; overflow:hidden;
            }
            .addressArrow{
                width: 15.5px;
                height: 11px;
                position: absolute;
                left: 50%;
                top: 23%;
                margin-left: -5px;
                margin-top: -48px;
                background: url(${staticResourceUrl}/images/addressArrow.png) no-repeat;
                background-size: 100% 100%;
                display: none;
            }
        </style>
</head>
<body>
 
	   <form action="" method="get" id="form">
		<input type="hidden" name="operateSeq" value="3">
		<input type="hidden" name="houseBaseFid" value="${houseBaseFid }">
		<input type="hidden" name="houseType" value="${houseType }">
		<input type="hidden" name="rentWay" value="${rentWay }">
		<input type="hidden" name="nationCode" value="${nationCode }">
		<input type="hidden" name="provinceCode" value="${provinceCode }">
		<input type="hidden" name="cityCode" value="${cityCode }">
		<input type="hidden" name="areaCode" value="${areaCode }">
		<input type="hidden" name="longitude" id="longitude" value="${longitude }">
		<input type="hidden" name="latitude" id="latitude" value="${latitude }">
		<input type="hidden" name="flag" value="${flag }">
		<input type="hidden" name="houseRoomFid" value="${houseRoomFid }">
		<input type="hidden" name="housePhyFid" value="${housePhyFid }">
		
        <header class="header">
				<a href="${basePath }houseInput/${loginUnauth}/goToHouseUpdate?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid }&rentWay=${rentWay}&flag=${flag}" id="goback" class="goback"></a>
				<h1>修改房源地址</h1>
			</header>
        <div class="bodys">
            <div id="main" class="main">
            <div class="box">
                   <div id="allmap" class="mapBox"> 
                    <div class="addressTag"></div>
                  </div>
                  <div class="button-restoring" id="setCenter"></div>
			      <div class="ceter-icon"></div>
			      <div class="addressTag"></div>
			      <div class="addressArrow"></div>
                <ul id="addressList" class="myCenterList">
                    <!-- TODO:跳转到地区信息页面 -->
                    <li <c:if test="${empty locationName  }">class="c_ipt_li c_ipt_li_none bor_b"</c:if>
					<c:if test="${!empty locationName  }">class="c_ipt_li  bor_b"</c:if> > 
                        <a id="locationli" href="javascript:;"> 
                            <span class="c_span">地区</span>
                            <!-- 
                            <input type="radio" name="loationPage" value="1"/>
                             -->
                             <input type="hidden" name="loationPage" value="1" >
                            <input class="c_ipt" type="text" id="locationName" name="locationName" placeholder="请选择地区信息" value="${locationName}" readonly="readonly">
                            <span class="icon_r icon"></span>
                        </a>
                    </li>
                    <li <c:if test="${empty houseStreet  }">class="c_ipt_li c_ipt_li_none bor_b"</c:if>
					<c:if test="${!empty houseStreet  }">class="c_ipt_li  bor_b"</c:if> > 
                        <a href="javascript:void(0);"> 
                            <span class="c_span">街道</span>
                            <!-- 
                            <input type="radio" name="loationPage" value="2"/>
                             -->
                            <textarea class="c_ipt j_ipt" type="text" id="houseStreet" data-type="address" name="houseStreet" placeholder="请填写街道名称"  maxlength="30">${houseStreet}</textarea> 
                            <span class="icon_r icon icon_clear" ></span>
                        </a>
                    </li>
                    <li <c:if test="${empty communityName  }">class="c_ipt_li c_ipt_li_none bor_b"</c:if>
					<c:if test="${!empty communityName  }">class="c_ipt_li  bor_b"</c:if> > 
                        <a href="javascript:void(0);"> 
                            <span class="c_span">小区</span>
                            <!-- 
                            <input type="radio" name="loationPage" value="3"/>
                             -->
                            <textarea class="c_ipt j_ipt" type="text" id="communityName"  data-type="address" name="communityName" placeholder="请填写小区名称">${communityName}</textarea>
                            <span class="icon_r icon icon_clear" maxlength="30"></span>
                        </a>
                    </li>
                    <li <c:if test="${empty detailAddress  }">class="c_ipt_li c_ipt_li_none bor_b"</c:if>
					<c:if test="${!empty detailAddress  }">class="c_ipt_li  bor_b"</c:if> > 
                        <a href="javascript:void(0);">  
                            <span class="c_span">楼号－门牌号</span>
                            <!-- 
                            <input type="radio" name="loationPage" value="4"/>
                             -->
                            <textarea class="c_ipt j_ipt" type="text" id="detailAddress"  data-type="address" name="detailAddress" placeholder="请填写楼号－门牌号信息(用户支付后可见)" maxlength="30">${detailAddress}</textarea>
                            <span class="icon_r icon icon_clear" ></span>
                        </a>
                    </li>
                </ul>
                </div>
                <!--/main-->
            </div>
        </div>
        <div class="box box_bottom">
            <input id="nextBtn" type="button" value="确定" class="disabled_btn">
        </div> 
    </form>
	 
	 
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	 <script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}001"></script>	  
    <%-- <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=${baiduAk}&s=1"></script> --%>
	<script type="text/javascript" src="${staticResourceUrl}/js/common_830.js${VERSION}004"></script> 
	
	<!-- <script type="text/javascript" src="http://maps.google.cn/maps/api/js?sensor=false&key=AIzaSyCT54l5ah9i7q1AEClN_B7RlDzRghdcgEg"></script> -->
	<script type="text/javascript" src="${staticResourceUrl}/js/google.js?sensor=false&key=AIzaSyCT54l5ah9i7q1AEClN_B7RlDzRghdcgEg&v=${VERSION}003"></script> 
	<script type="text/javascript" src="${staticResourceUrl}/js/googlemap.js${VERSION}003"></script> 
	<script type="text/javascript">
	// 谷歌地图API功能 
	var latitude = $("#latitude").val();
	var longitude = $("#longitude").val();
	if(latitude!=null&&latitude!=undefined&&latitude!=""&&
		longitude!=null&&longitude!=undefined&&longitude!=""){
		setMap(latitude,longitude,18);
	}else{
		setMap(39.908715,116.39738899999998,15);
		getPoint();
	}

	function canNext(){
		if($.trim($("#locationName").val()).length != 0 && $.trim($("#houseStreet").val()).length != 0){
			
			if($.trim($("#houseStreet").val()).length >50){
				showShadedowTips("街道名称过长",1);
				return;
			}
			if($.trim($("#communityName").val()).length >50){
				showShadedowTips("小区名称过长",1);
				return;
			}
			$("#nextBtn").removeClass("disabled_btn").addClass("org_btn");
			return true;
		}else{
            $("#nextBtn").removeClass("org_btn").addClass("disabled_btn");
            return false;
        }
	}
	
    $("#locationli").click(function(e){
     	var path = "${basePath}houseDeploy/${loginUnauth}/toLocationOthers"; 
		$('#form').attr("action", path).submit();
    });
    
    $("#locationName").change(function(){
    	canNext();
    });
    
    $("#houseStreet").keyup(function(){
    	$("#houseStreet").val($.trim($("#houseStreet").val()));
    	canNext();
    });
    $("#communityName").keyup(function(){
    	$("#communityName").val($.trim($("#communityName").val()));
    	canNext();
    });
    $("#detailAddress").keyup(function(){
    	$("#detailAddress").val($.trim($("#detailAddress").val()));
    	canNext();
    });
    
    $(".icon_clear").click(function(){
    	$(this).prev().val("");
    	canNext();
    });
    
    
	</script>
	<script type="text/javascript">
	$(function(){
		
		
		/* $(".goback").click(function(event){
			var path = "${basePath}houseDeploy/${loginUnauth}/toRentWay"; 
			$('#form').attr("action", path).submit();
		}); */
		
		$(".loationPageLi").click(function(event){
			$(this).children("input").attr("checked","checked"); 
			var path = "${basePath}houseDeploy/${loginUnauth}/toLocationOthers"; 
			$('#form').attr("action", path).submit();
		});
		
		$("#nextBtn").click(function(e){
			if($(this).hasClass("disabled_btn")){
				return false;
			}else{

				if($.trim($("#locationName").val()).length ==0){
					showShadedowTips("请选择地区",1);
					return;
				}
				$("#houseStreet").val($.trim($("#houseStreet").val()));
				if($.trim($("#houseStreet").val()).length ==0){
					showShadedowTips("街道名称不能为空",1);
					return;
				}
				if($.trim($("#houseStreet").val()).length >50){
					showShadedowTips("街道名称过长",1);
					return;
				}
				
				$("#communityName").val($.trim($("#communityName").val())); 
				if($.trim($("#communityName").val()).length >50){
					showShadedowTips("小区名称过长",1);
					return;
				}
				$("#detailAddress").val($.trim($("#detailAddress").val()));
				if($.trim($("#detailAddress").val()).length >50){
					showShadedowTips("楼号－门牌号过长",1);
					return;
				}
				
				$.ajax({
					url:"${basePath}houseDeploy/${loginUnauth}/saveHouseFirst",
					data:$('#form').serialize(),
					dataType:"json",
					type:"post",
					async: false,
					success:function(result) {
						if(result.code === 0){
							var houseBaseFid = result.data.houseBaseFid;
							var rentWay = result.data.rentWay;
							var houseRoomFid=result.data.houseRoomFid;
							var flag=result.data.flag;
							window.location.href='${basePath}houseInput/${loginUnauth}/goToHouseUpdate?houseBaseFid='+houseBaseFid+"&rentWay="+rentWay+"&houseRoomFid="+houseRoomFid+"&flag="+flag;
						}else{
							showShadedowTips(result.msg,1);
						}
					},
					error:function(result){
						showShadedowTips("未知错误",1);
					}
				});
			}
		})
	})
	</script>
	<script type="text/javascript">
	$(function(){

		var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });

		var conHeight = $(window).height()-$('.header').height()-$('.box_bottom').height();

		$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$('#main .box').css({'position':'absolute','width':'100%'});
		myScroll.refresh();
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	})
	</script>

</body>
</html>