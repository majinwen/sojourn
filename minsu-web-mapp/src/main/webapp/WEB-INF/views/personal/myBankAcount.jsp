<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
	String path = request.getContextPath(); 
	request.setAttribute("path", path);
%>
<!doctype html>
<html lang="zh">
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
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
	<title>我的账户</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
	
</head>
<body>
<header class="header">

    <c:if test="${sourceOrg == 1}">
        <a href="javascript:;" onclick="window.WebViewFunc.popToParent();" class="goback"></a>
    </c:if>
    <c:if test="${sourceOrg == 2}">
        <a href="javascript:;" onclick="window.popToParent();" class="goback"></a>
    </c:if>
    <c:if test="${sourceOrg == 0}">
        <a href="${basePathHttps }personal/${loginUnauth }/initPersonalCenter" class="goback"></a>
    </c:if>


    <h1>我的账户</h1>
</header>
<div class="main myCenterListNoneA myCenterListGeren" id="main">
	<div>
    <div class="zhanghuTop">
        <h2>我的余额</h2>
        <p><span>￥</span>${balance }</p>
    </div><!--/zhanghuTop-->


    <c:if test="${haveBank == 0}">
        <div class="bgF boxP075 pr">
            <a href="${basePathHttps }personal/${loginUnauth }/addBank">
                <ul class="myBankInfo boxPTB5">
                    <li class="clearfix letter2"><span class="gray">开户人姓名：</span>${bankcardHolder }</li>
                    <li class="clearfix letter"><span class="gray">开户银行：</span>${bankName }</li>
                    <li class="clearfix letter"><span class="gray">开户账号：</span>${bankcardNo }</li>
                    <li class="clearfix letter"><span class="gray">开户城市：</span>${province }</li>
                </ul>
                <span class="icon icon_r" style="right: .75rem;"></span>
            </a>

        </div>
    </c:if>
    <c:if test="${haveBank == 1}">
        <ul class="myCenterList myCenterListZhanghu mt75">
            <li class="disblock pr clearfix">
                <a href="${basePathHttps }personal/${loginUnauth }/addBank">
                    <span class="fl fz8">绑定银行卡</span>
                    <span class="fr fz7 gray">未绑定</span>
                    <span class="icon icon_r"></span>
                </a>
            </li>
        </ul>
    </c:if>

    <ul class="myCenterList myCenterListZhanghu mt75">
        <li class="disblock pr clearfix bor_b">
            <a href="javascript:;" id="payDetail">
                <span class="fl fz8">结算方式</span>
					<span class="fr fz7 gray">

						<c:if test="${clearingCode == 1}">
                            按订单结算
                        </c:if>
						<c:if test="${clearingCode == 2}">
                            按天结算
                        </c:if>
					</span>
                <span class="icon icon_r"></span>
            </a>
        </li>
        <li class="disblock pr clearfix">
            <a href="javascript:;" id="rentPayment">
                <span class="fl fz8">收款方式</span>
					<span class="fr fz7 gray">

						<c:if test="${rentPayment == 0}">
                            账户空间
                        </c:if>

						<c:if test="${rentPayment == 1}">
                            银行卡
                        </c:if>
					</span>
                <span class="icon icon_r"></span>
            </a>
        </li>

    </ul>
    <p class="boxP075 gray fz6 mt75">提示：提现请切换至房客端个人中心->钱包页面</p>
    </div>
</div><!--/main-->

	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
    <script type="text/javascript" src="//static8.ziroom.com/fecommon/library/bscroll/bscroll.min.js"></script>
	<script type="text/javascript">
	
	
		$(function(){
			    var conHeight = $(window).height()-$('.header').height();
		        
		        $('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		        $('#main > div').css({'position':'absolute','width':'100%','padding-bottom':'1rem'});
		        myScroll = new BScroll('#main', { click:true});
		        document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		        
		        
			$("#payDetail").click(function(){
				window.location.href="${basePathHttps }personal/${loginUnauth }/updatePayInfo";
			});
		
            $("#rentPayment").click(function(){
                window.location.href="${basePathHttps }personal/${loginUnauth }/updatePaymentInfo";
            });
        })
	</script>
</body>
</html>
