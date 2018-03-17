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
    <header class="header">
        <a href="${basePath}auth/${loginUnauth}/init?checkFlag=1" id="goback" class="goback" ></a>
        <h1>联系方式</h1>
    </header>
    <ul class="myCenterList">
        <li class="clearfix bor_b ">
            <input class="ipt_lg ipt_md" id="customerMobile" name="customerMobile"  value="${customerMobile}" placeholder="请输入联系方式" maxlength="11" />
            <span id="getCaptcha" class="yzm">获取验证码</span>
        </li>
        <li class="clearfix ">
            <input class="ipt_lg" id="yzmCode" placeholder="请输入动态验证码" maxlength="" />
        </li>

    </ul>
</div><!--/main-->
<div class="boxP075 mt85 mb85">
    <input type="button" id="submitBtn" value="确定" class="disabled_btn btn_radius">
</div>
<!-- </form> -->
<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/layer/layer.js${VERSION}001"></script>
<script type="text/javascript">
    $(function(){
        var oBtn = $('#getCaptcha');
        var regTel=/(^1[3|4|5|7|8][0-9]{9}$)/;//电话正则
        var phone = '';
        var start = 0 ;
        var end = 50;
        var timer = null;
        var caFlag = 0;
        $(".yzm").click(function(){
            if($(this).hasClass("disabled")){
                return false;
            }
            if(checkPhone()){
                caFlag = 1;
                findauthcode();
            }
        })
        function checkPhone(){
            phone = $("#customerMobile").val();
            if($(this).hasClass("disabled")){
                caFlag = 0;
                return false;
            }
            if($.trim(phone).length == 0){
                showShadedowTips("请输入联系方式",1);
                caFlag = 0;
                return false;
            }
            if(!regTel.test($.trim(phone))){
                showShadedowTips("请输入正确的联系方式",1);
                caFlag = 0;
                return false;
            }
            //发送验证码
            return true;
        }
        function findauthcode() {
            if(caFlag==1){
                caFlag=0;
                $("#yzmCode").val("");
                $("#submitBtn").addClass("disabled_btn").removeClass("org_btn");
                // $.ajax({
                // 	url: "code.json",
                // 	type: 'get',
                // 	data: {
                // 		phone:$("#phone").val()
                // 	},
                // 	data:"dataType",
                // 	success: function (result) {

                // if (result.status=="0") {
                timer = setInterval(function(){
                    end--;

                    if(end<=0){
                        end=60;
                        oBtn.html('获取验证码');
                        caFlag=1;
                        oBtn.removeClass("disabled");
                        clearInterval(timer);
                    }else{
                        oBtn.addClass("disabled").html('重新获取('+end+')s');
                    }

                },1000);
                // 			alert("获取成功！");

                // 		} else {
                // 			alert(result.message);
                // 		}
                // 	},
                // 	error:function(result){
                // 		alert("请稍后再试");
                // 	}
                // })
            }

        }
        $("#yzmCode").keyup(function(){
            if(checkPhone()){
                if($.trim($(this).val()).length != 0){
                    $("#submitBtn").removeClass("disabled_btn").addClass("org_btn");
                }
            }

        })
        $("#submitBtn").click(function(){
            if($(this).hasClass("disabled_btn")){
                return false;
            }
            var customerMobile = $("#customerMobile").val();
            $.ajax({
                url:"${basePath }auth/${loginUnauth}/saveTel?customerMobile="+customerMobile,
                data:{},
                dataType:"json",
                type:"post",
                async: false,
                success:function(result) {
                    if(result.code === 0){
                        showShadedowTips("修改成功",1);
                        window.location.href='${basePath}auth/${loginUnauth}/init?checkFlag=1';
                    }else{
                        showShadedowTips(result.msg,1);
                    }
                },
                error:function(result){
                    showShadedowTips("未知错误",1);
                }
            });

        })
    })
</script>
</body>
</html>