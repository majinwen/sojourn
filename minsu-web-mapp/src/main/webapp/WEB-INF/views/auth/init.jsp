<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="applicable-device" content="mobile">
    <meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <title>资质认证</title>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">

</head>

<body>

<!-- <form accept="" method="">
 -->	<div class="main myCenterListNoneA">
 <div class="bodys">
    <header class="header">
           <c:choose>
	    		<%-- Android --%>
	    		<c:when test="${sourceType eq 1 && versionCode ==true }">
					<a href="javascript:;" onclick="window.WebViewFunc.popToParent();" class="goback"></a>
	    		</c:when>
	    		<%-- iOS --%>
	    		<c:when test="${sourceType eq 2 && versionCode ==true}">
					<a href="javascript:;" onclick="window.popToParent();" class="goback"></a>
	    		</c:when>
	    		<%-- M站 --%>
	    		<c:otherwise>
	    		   <a href="${basePath}houseMgt/${loginUnauth}/myHouses" id="goback" class="goback" ></a>
	    		</c:otherwise>
	    	</c:choose>
        <h1>房东资质认证</h1>
    </header>
   
     <div class="myBanner">
        <h1>请填写您的认证信息</h1>
        <img src="${staticResourceUrl }/images/Group_32x.png" class="bg">
    </div>
    <ul class="myCenterList myCenterList_no">
        <li id="phoneLi" class="clearfix bor_b">
            <a href="${basePath}customer/${loginUnauth}/updateMobile?flag=2">
                    联系方式
                <span class="span_r">
                    <c:if test="${isContactAuth == 1 }">
                            已完成
                    </c:if>
                    <c:if test="${isContactAuth != 1 }">
                         未完成
                    </c:if>
                </span>
                <span class="icon_r icon" ></span>
            </a>
        </li>

        <li class="clearfix bor_b ">
            <%-- <a href="${basePath}auth/${loginUnauth}/cardInfo"> --%>
            <a href="${basePath }personal/${loginUnauth }/toAuthDetail?authType=2"> 
                  身份信息
                <span class="span_r ">
                    <c:if test="${isIdentityAuth == 1 }">
                          已完成
                    </c:if>
                    <c:if test="${isIdentityAuth != 1 }">
                           未完成
                    </c:if>
                </span>
                <span class="icon_r icon"></span>
            </a>
        </li>


        <li class="clearfix">
            <a href="${basePath}auth/${loginUnauth}/headPic">
                    个人信息
                <span class="span_r ">
                    <c:if test="${isFinishHead == 1 }">
                         已完成
                    </c:if>
                    <c:if test="${isFinishHead != 1 }">
                         未完成
                    </c:if>
                </span>
                <span class="icon_r icon"></span>
            </a>
        </li>
    </ul>

   </div>


</div><!--/main-->
<div class="box box_bottom">
    <input type="button" id="submitBtn" value="发布房源" class="disabled_btn">
</div>
<!-- </form> -->
<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>

<script type="text/javascript">
    $(function(){
        var flag = '${fullFlag}';
//        $("#phoneLi").click(function(){
//            if($(this).find(".span_r").hasClass("span_r_done")){
//                window.location.href="aptitude_phone_edit.html";
//                return false;
//            }else{
//                window.location.href="aptitude_phone.html";
//            }
//
//        });

        $("#submitBtn").click(function(){
            if($(this).hasClass("disabled_btn")){
                return false;
            }
            window.location.href='${basePath}houseDeploy/${loginUnauth}/toHouseType';
        });
        if(flag == 1){
            $("#submitBtn").removeClass("disabled_btn").addClass("org_btn")
        }

    })
</script>
</body>
</html>
