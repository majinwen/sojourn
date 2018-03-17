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
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
    <meta name="format-detection" content="telephone=no">
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}002"></script>
    <title>发布房源</title>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}002">
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/css_830.css${VERSION}002">

</head>
<body>
 
     <form action="" method="get" id="form">
        <input type="hidden" name="houseBaseFid" value="${houseBaseFid }">
		<input type="hidden" name="houseType" value="${houseType }">
    
        <header class="header">
                <a href="javascript:;" id="goback" class="goback" onclick=""></a>
                <h1>出租类型(2/6)</h1>
                <%-- 房东指南 --%>
				<a href="https://zhuanti.ziroom.com/zhuanti/minsu/zhinan/fabuliucheng2.html" class="toZhinan"></a>
            </header>
            <div class="myBanner">
                <h1>选择您的出租方式</h1>
                <img src="${staticResourceUrl}/images/userbg.jpg" class="bg">
            </div>
            <div class="main">
                <ul class="myCenterList myCenterList_no">
                    <li class="c_ipt_li bor_b rentWayLi">
                    	<!-- <a href="javascript:void(0);">-->
                        <a href="${basePath}houseDeploy/${loginUnauth}/toLocation?houseBaseFid=${houseBaseFid }&houseType=${houseType }&rentWay=0"> 
                            <!--  <input type="radio" name="rentWay" value="0"/>-->
                            <span class="c_span">整套出租</span>
                            <input class="c_ipt" value="您的房子整套出租" readonly="readonly">
                            <span class="icon_r icon"></span>
                        </a>
                    </li>
                    <li class="c_ipt_li bor_b rentWayLi">
                        <a href="${basePath}houseDeploy/${loginUnauth}/toLocation?houseBaseFid=${houseBaseFid }&houseType=${houseType }&rentWay=1">  
                            <span class="c_span">独立房间</span>
                            <input class="c_ipt" value="您的一套房子可按多个独立房间出租" readonly="readonly">
                            <span class="icon_r icon"></span>
                        </a>
                    </li>
                   
                </ul>
                <!--/main-->
            </div>

    </form>
  
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>    
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
    <script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}001"></script>
	<script type="text/javascript">
		$(function(){
			$(".goback").click(function(event){
				var path ="${basePath}houseDeploy/${loginUnauth}/toHouseType"; 
				$('#form').attr("action", path).submit();
			});
			
            //document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			 /* $(".rentWayLi").click(function(event){
				$(this).find("input").attr("checked","checked");
				var path ="${basePath}houseDeploy/${loginUnauth}/toLocation"; 
				$('#form').attr("action", path).submit();
			});  */
			 
		})
	</script>
</body>
</html>
