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
</head>
<body>
 
	   <form action="" method="get" id="form">
		<input type="hidden" name="operateSeq" value="1">
		<input type="hidden" name="houseBaseFid" value="${houseBaseFid }">
		<input type="hidden" name="houseType" value="${houseType }">
		<input type="hidden" name="rentWay" value="${rentWay }">
		<input type="hidden" name="nationCode" value="${nationCode }">
		<input type="hidden" name="provinceCode" value="${provinceCode }">
		<input type="hidden" name="cityCode" value="${cityCode }">
		<input type="hidden" name="areaCode" value="${areaCode }">
		<input type="hidden" name="longitude" value="">
		<input type="hidden" name="latitude" value="">
		<input type="hidden" name="flag" value="${flag }">
		<input type="hidden" name="houseRoomFid" value="${houseRoomFid }">
		<input type="hidden" name="housePhyFid" value="${housePhyFid }">
		
        <header class="header"> <a href="javascript:;" id="goback" class="goback"></a>
            <h1>位置信息(3/6)</h1>
            <a href="https://zhuanti.ziroom.com/zhuanti/minsu/zhinan/fabuliucheng2.html" class="toZhinan"></a>
        </header>
        <div class="bodys">
            <div id="main" class="main">
            <div class="box">
                <div id="allmap" class="mapBox"></div>
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
            <input type="button" id="nextBtn" value="下一步" class="disabled_btn">
        </div> 
    </form>
	 
	 
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	 <script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}001"></script>	  
<%-- 	<script type="text/javascript" src="http://api.map.baidu.com/api?type=quick&ak=${baiduAk}&v=1.0"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/getscript?v=2.0&amp;ak=${baiduAk}&amp;services=&amp;t=20160513110936"></script> --%>
	<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=${baiduAk}&s=1"></script>
	<script type="text/javascript" src="${staticResourceUrl}/js/common_830.js${VERSION}003"></script> 
	<script type="text/javascript">

if (typeof(console) == "undefined") {
    console = {};
    console.log = function() {}
}
window.onerror = function() {}; (function() {
    var i = window.DomReady = {};
    var h = navigator.userAgent.toLowerCase();
    var c = {
        version: (h.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [])[1],
        safari: /webkit/.test(h),
        opera: /opera/.test(h),
        msie: (/msie/.test(h)) && (!/opera/.test(h)),
        mozilla: (/mozilla/.test(h)) && (!/(compatible|webkit)/.test(h))
    };
    var d = false;
    var e = false;
    var g = [];
    function a() {
        if (!e) {
            e = true;
            if (g) {
                for (var j = 0; j < g.length; j++) {
                    g[j].call(window, [])
                }
                g = []
            }
        }
    }
    function f(j) {
        var k = window.onload;
        if (typeof window.onload != "function") {
            window.onload = j
        } else {
            window.onload = function() {
                if (k) {
                    k()
                }
                j()
            }
        }
    }
    function b() {
        if (d) {
            return
        }
        d = true;
        if (document.addEventListener && !c.opera) {
            document.addEventListener("DOMContentLoaded", a, false)
        }
        if (c.msie && window == top) { (function() {
                if (e) {
                    return
                }
                try {
                    document.documentElement.doScroll("left")
                } catch(k) {
                    setTimeout(arguments.callee, 0);
                    return
                }
                a()
            })()
        }
        if (c.opera) {
            document.addEventListener("DOMContentLoaded",
            function() {
                if (e) {
                    return
                }
                for (var k = 0; k < document.styleSheets.length; k++) {
                    if (document.styleSheets[k].disabled) {
                        setTimeout(arguments.callee, 0);
                        return
                    }
                }
                a()
            },
            false)
        }
        if (c.safari) {
            var j; (function() {
                if (e) {
                    return
                }
                if (document.readyState != "loaded" && document.readyState != "complete") {
                    setTimeout(arguments.callee, 0);
                    return
                }
                if (j === undefined) {
                    var l = document.getElementsByTagName("link");
                    for (var m = 0; m < l.length; m++) {
                        if (l[m].getAttribute("rel") == "stylesheet") {
                            j++
                        }
                    }
                    var k = document.getElementsByTagName("style");
                    j += k.length
                }
                if (document.styleSheets.length != j) {
                	
                    setTimeout(arguments.callee, 0);
                    return
                }
                a()
            })()
        }
        f(a)
    }
    i.ready = function(k, j) {
        b();
        if (e) {
            k.call(window, [])
        } else {
            g.push(function() {
                return k.call(window, [])
            })
        }
    };
    b()
})();
	var Fe = Fe || {
	    version: "20080809",
	    emptyFn: function() {}
	};
	Fe.G = function() {
	    for (var b = [], c = arguments.length - 1; c > -1; c--) {
	        var d = arguments[c];
	        b[c] = null;
	        if (typeof d == "object" && d && d.dom) {
	            b[c] = d.dom
	        } else {
	            if ((typeof d == "object" && d && d.tagName) || d == window || d == document) {
	                b[c] = d
	            } else {
	                if (typeof d == "string" && (d = document.getElementById(d))) {
	                    b[c] = d
	                }
	            }
	        }
	    }
	    return b.length < 2 ? b[0] : b
	};
	
	//标注点数组
	var markerArr = [{title:"我的标记",content:"我的备注",point:"121.331698|24.864415",isOpen:0,icon:{w:21,h:21,l:0,t:0,x:6,lb:5}}
		 ];

</script>
	<script type="text/javascript">
	// 百度地图API功能
/* 	var map = new BMap.Map("allmap");
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,12);
	map.addControl(new BMap.ZoomControl()); */
	
/* 	var map = new BMap.Map("allmap", {enableMapClick:false}); // 创建地图实例  ，关闭底图可点功能
	var point = new BMap.Point(116.404, 39.915);  // 创建点坐标  
	map.centerAndZoom(point, 12);                 // 初始化地图，设置中心点坐标和地图级别   */
	//map.enableScrollWheelZoom(true);
	var mapInfo = {
    	    cityName: "",
    	    cityCode: "",
    	    centerPoint: null
    	};
	initMap(116.404, 39.915);
	// 创建地址解析器实例
/* 	var myGeo = new BMap.Geocoder();
	var geolocation = new BMap.Geolocation(); */
	
	getPoint();
	function getPoint(){
		var locationName = $.trim($("#locationName").val());
		if(locationName.length != 0 || $.trim($("#communityName").val()).length != 0 || $.trim($("#houseStreet").val()).length != 0){
			//国内
			var i = locationName.indexOf("中国");
			if(locationName.indexOf("中国") > -1 ){
				canNext();
				try {
					myGeo.getPoint($("#locationName").val()+$("#houseStreet").val()+$("#communityName").val(), function(point){
						if (point) {
							/* map.clearOverlays(); 
							map.centerAndZoom(point, 16);
							map.addOverlay(new BMap.Marker(point)); */
							createCityMap(point, 16);
						/* 	$("[name='longitude']").val(point.lng);
				            $("[name='latitude']").val(point.lat); */
						}
					}, $("#locationName").val());			
				} catch (e) {
					//console.log(e);
				}
			}else{ 	
				//国外  先定位到县 然后到具体街道  如果返回多个位置给出多个位置标记
				var locationNameArr = locationName.split(" ");
				var curCityName = locationNameArr[locationNameArr.length-3];
				var i=0;
				goCurCity(curCityName);
			/* 	for(i=0;i<locationNameArr.length;i++){
					goCurCity(locationNameArr[i]);
				}
				 */
				if($.trim($("#communityName").val()).length != 0 || $.trim($("#houseStreet").val()).length != 0){
					curCityName = $("#houseStreet").val()+$("#communityName").val();
					getCurrentCityName(curCityName);
				}
				
			}
			
		}else{
            var myCity = new BMap.LocalCity();
            myCity.get(function (result) {
                var cityName = result.name;
                myGeo.getPoint(cityName, function(point){
                    if (point) {
                    	createCityMap(point, 16);
                       /*  $("[name='longitude']").val(point.lng);
                        $("[name='latitude']").val(point.lat); */
                    }
                });
            });

        }
	}
	function canNext(){
		if($.trim($("#locationName").val()).length != 0 && $.trim($("#houseStreet").val()).length != 0 ){
			
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
    
    

    //创建和初始化地图函数：
    function initMap(x,y){
        createMap(x,y);//创建地图
        setMapEvent();//设置地图事件
       // addMapControl();//向地图添加控件
       // addMarker();//向地图中添加marker
    }
    
    //创建地图函数：
    function createMap(x,y){
    	var myGeo = new BMap.Geocoder();
    	var geolocation = new BMap.Geolocation();
        var map = new BMap.Map("allmap", {enableMapClick:false});//在百度地图容器中创建一个地图 关闭底图可点功能
        window.map = map;//将map变量存储在全局
        window.projection = new BMap.MercatorProjection();
        window.myGeo = myGeo;
        window.geolocation = geolocation;
        var point = new BMap.Point(x,y);//定义一个中心点坐标
	    mapInfo.centerPoint = point;
	    map.centerAndZoom(point,12);//设定地图的中心点和坐标并将地图显示在地图容器中
	    map.addOverlay(new BMap.Marker(point));
	    setMapEvent();//设置地图事件
		$("[name='longitude']").val(point.lng);
        $("[name='latitude']").val(point.lat);
    }
    
    //创建地图函数
    function createMapSize(x,y,size){
    	var myGeo = new BMap.Geocoder();
    	var geolocation = new BMap.Geolocation();
        var map = new BMap.Map("allmap", {enableMapClick:false});//在百度地图容器中创建一个地图 关闭底图可点功能
        window.map = map;//将map变量存储在全局
        window.projection = new BMap.MercatorProjection();
        window.myGeo = myGeo;
        window.geolocation = geolocation;
        var point = new BMap.Point(x,y);//定义一个中心点坐标
	    mapInfo.centerPoint = point;
	    map.centerAndZoom(point,size);//设定地图的中心点和坐标并将地图显示在地图容器中
	    map.addOverlay(new BMap.Marker(point));
	    setMapEvent();//设置地图事件
		$("[name='longitude']").val(point.lng);
        $("[name='latitude']").val(point.lat);
    }
    
    function createCityMap(point,size){
    	
    	map.clearOverlays(); 
    	mapInfo.centerPoint = point;
		map.centerAndZoom(point, size);
		map.addOverlay(new BMap.Marker(point));
		setMapEvent();//设置地图事件
		$("[name='longitude']").val(point.lng);
        $("[name='latitude']").val(point.lat);
    }
    //地图事件设置函数：
    function setMapEvent(){
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
        map.enableScrollWheelZoom(true);
    }
    
    function goCurCity(cityName){
    	var  a = encodeURIComponent(cityName);
	    a = "http://map.baidu.com/?newmap=1&qt=cur&callback=setCurrentCity&coor=bd09ll&ie=utf-8&wd=" + a + "&oue=1&res=jc";
	    scriptRequest(a, "null");
	} 
	function setCurrentCity(d) {
	    if (!d.content || d.content.error == 0) {
	        setTimeout(function() {
	        	getCurrentCityName();
	        },
	        0)
	    } else {
	    	try {
		        var b = (((d.content.geo).split("|")[2]).split(";")[0]).split(",")[0];
		        var a = (((d.content.geo).split("|")[2]).split(";")[0]).split(",")[1];
		        var c = new BMap.MercatorProjection().pointToLngLat(new BMap.Pixel(b, a));
		        createMapSize(c.lng,c.lat,12);
	    	}catch(e){
	    	}
	      
	    }
	}
	
	//script 请求
	function scriptRequest(url, echo, id, charset) {
	    var isIe = /msie/i.test(window.navigator.userAgent);
	    if (isIe && Fe.G("_script_" + id)) {
	        var script = Fe.G("_script_" + id)
	    } else {
	        if (Fe.G("_script_" + id)) {
	            Fe.G("_script_" + id).parentNode.removeChild(Fe.G("_script_" + id))
	        }
	        var script = document.createElement("script");
	        if (charset != null) {
	            script.charset = charset
	        }
	        if (id != null && id != "") {
	            script.setAttribute("id", "_script_" + id)
	        }
	        script.setAttribute("type", "text/javascript");
	        document.body.appendChild(script)
	    }
	    var t = new Date();
	    if (url.indexOf("?") > -1) {
	        url += "&t=" + t.getTime()
	    } else {
	        url += "?t=" + t.getTime()
	    }
	    var _complete = function() {
	        if (!script.readyState || script.readyState == "loaded" || script.readyState == "complete") {
	            if (echo == "null") {
	                return
	            } else {
	                if (typeof(echo) == "function") {
	                    try {
	                        echo()
	                    } catch(e) {}
	                } else {
	                    eval(echo)
	                }
	            }
	        }
	    };
	    if (isIe) {
	        script.onreadystatechange = _complete
	    } else {
	        script.onload = _complete
	    }
	    script.setAttribute("src", url)
	}
	
	  function getCurrentCityName(curCityName) {
	        var a = map.getZoom();
	        var c;
	        var h = 10000;
	        if (a <= 7) {
	            c = a;
	            setCurrentMapInfo("全国");
	            return
	        }
	        var i = function() {
	            var m = map.getBounds();
	            var j = projection.lngLatToPoint(m.getSouthWest());
	            var l = projection.lngLatToPoint(m.getNorthEast());
	            var k = function(n) {
	                return parseInt(n / 1000) * 1000
	            };
	            return k(j.x) + "," + k(j.y) + ";" + k(l.x) + "," + k(l.y)
	        };
	        var e = mapInfo.centerPoint;
	        var d = map.getCenter();
	        var f = Math.sqrt((e.lng - d.lng) * (e.lng - d.lng) + (e.lat - d.lat) * (e.lat - d.lat));
	        if (f > h || a != c) {
	            c = a;
	            var b = i();
	            var wd=encodeURIComponent(curCityName);
	            var b = "http://map.baidu.com/?newmap=1&callback=setCurrentArea&reqflag=pcmap&biz=1&from=webmap&da_par=direct&pcevaname=pc4.1&qt=s&da_src=searchBox.button&wd="+wd+"&c=60732&src=0&wd2=&sug=0&l=14&"+i()+"&from=webmap&biz_forward={%22scaler%22:1,%22styles%22:%22pl%22}&sug_forward=&tn=B_NORMAL_MAP&nn=0&u_loc=12969403,4835282&ie=utf-8";
	            scriptRequest(b)
	        }
	        function g() {
	            if (typeof _mapCenter == "undefined") {
	                return
	            }
	            var j = _mapCenter;
	            var k = j.content;
	            if (!k) {
	                return
	            }
	            setCurrentMapInfo(_mapCenter.content.name, _mapCenter.content.uid)
	        }
	    }
	    function setCurrentMapInfo(c, d) {
	        var a = mapInfo;
	        var b = map.getZoom();
	        a.cityName = c;
	        a.cityCode = d;
	        a.centerPoint = map.getCenter();
	        Fe.G("curCity").innerHTML = c;
	        Fe.G("ZoomNum").innerHTML = b
	    }
	    
		function setCurrentArea(d) {
		    if (!d.content) {
		        setTimeout(function() {
		        },
		        0)
		    } else {
		    	try {
		            var ary = d.content[0];
			        var b = (((ary.geo).split("|")[2]).split(";")[0]).split(",")[0];
			        var a = (((ary.geo).split("|")[2]).split(";")[0]).split(",")[1];
			        var c = new BMap.MercatorProjection().pointToLngLat(new BMap.Pixel(b, a));
	        	    createMapSize(c.lng,c.lat,15);
		    	}catch(e){
		    		
		    	}
		    }
		}
	</script>
	<script type="text/javascript">
	$(function(){
		
		
		$(".goback").click(function(event){
			var path = "${basePath}houseDeploy/${loginUnauth}/toRentWay"; 
			$('#form').attr("action", path).submit();
		});
		
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
				
				$(this).addClass("disabled_btn");
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
							window.location.href='${basePath}houseInput/${loginUnauth}/findHouseDetail?houseBaseFid='+houseBaseFid+"&rentWay="+rentWay;
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
