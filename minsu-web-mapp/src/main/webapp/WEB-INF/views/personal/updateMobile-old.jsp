<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="applicable-device" content="mobile">
<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="yes" name="apple-touch-fullscreen" />
<meta name="viewport"
	content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no">
<script type="text/javascript"
	src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
<title>修改联系方式</title>
<link rel="stylesheet" type="text/css"
	href="${staticResourceUrl }/css/styles.css${VERSION}001">

</head>
<body>

	<div class="loginBox">


		<form action="" onsubmit="return false;" class="checkFormChild">
			<div id="regStep_1" class="bgF">
				<header class="header"> <a href="javascript:;"
					onclick="history.back();" class="goback"></a>
				<h1>修改联系方式</h1>
				</header>
				<div class="main myCenterListNoneA">
					<ul class="myCenterList myCenterListLianxi pt75">
						<li class="clearfix bor_b" id="step_1"><span
							class="c_icon icon_tel"></span> <input type="tel"
							placeholder="请输入联系方式" id="regUser"
							onkeyup="inputVal(this,'#forRegUser')"> <span
							class="l_icon icon_close" id="forRegUser"
							onclick="clearInp(this,'#regUser')"></span></li>
					</ul>



				</div>
				<!--/main-->
				<div class="boxP075 mt85 mb85">
					<input type="button" value="下一步" class="org_btn disabled_btn"
						id="nextStep_1">
				</div>
			</div>
		</form>
		<input id="flag" type="hidden" name="flag" value="${flag }" />
		<form action="" onsubmit="return false;" class="checkFormChild">
			<div id="regStep_2" style="display: none;" class="bgF">
				<header class="header">
				<h1>填写验证码</h1>
				</header>
				<div class="content ">

					<h2>验证码已发送到手机</h2>
					<h3 id="yzTelVal"></h3>
					<div class="inputs">
						<input type="text" placeholder="" id="vcode" name="captcha"
							maxlength="6" class="val">
					</div>
					<p class="org" id="countDownTxt" onclick="countDown()">点击重新获取验证码</p>
					<p class="gray">若无法收到验证码，请拨打客服电话：400-100-1111</p>
					<div class="boxP075 mt85 mb85">
						<input type="button" onclick="checkLianxiForm();" value="确认"
							class="org_btn disabled_btn">
					</div>

				</div>
			</div>
			<!--/发送验证码regStep_2-->

		</form>

	</div>
	<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001"
		type="text/javascript"></script>
	<script src="${staticResourceUrl }/js/jquery.form.js${VERSION}001"
		type="text/javascript"></script>
	<script src="${staticResourceUrl }/js/layer/layer.js${VERSION}001"
		type="text/javascript"></script>
	<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl }/js/customer/customer.js${VERSION}001"></script>
	<script type="text/javascript">

var imgId = $('#imgId').val();
var imgUrl = $('#imgUrl').val();
var imgSrc=imgUrl+''+imgId;

var oRegUser=$('#regUser');
var oSubmitBtn=$('#submitBtn');
var oGetCaptcha=$('#getCaptcha');
    $('#nextStep_1').click(function(){  

       if(!checkMobile(oRegUser.val())){
          showShadedowTips('请输入正确的手机号！','1');            
            return;
        }

        layer.open({
           content: '<div class="regAuthCode"><input type="text" placeholder="请输入" id="imgVValue"><img class="" src="${imgUrl }${imgId }" imgId="${imgId }"  imgUrl="${imgUrl }"  id="imgUrlId" onclick="Customer.reloadImg()"><p id="rTips"></p></div>',
            btn:['确认','取消'],
            yes: function(){
                //验证验证码是否正确
                

                var imgVValue = $("#imgVValue").val();
                var imgId = $("#imgUrlId").attr("imgId");
                
                if(imgVValue == null ||imgVValue == undefined ||imgVValue ==""||imgId == null ||imgId == undefined ||imgId ==""){
                    showShadedowTips('参数错误',1);   
                    return false;
                }
                $('#yzTelVal').html(oRegUser.val());
                CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/checkImgCode", {"imgId":imgId,"imgVValue":imgVValue}, function(data){
                    
                    if(data){
                        //正确跳转到第二步,显示第二块，进行发送验证码并开始倒计时
                        if(data.code == 0){
                              var phone =  oRegUser.val();
                              if(!checkMobile(phone)){  
                                    showShadedowTips('手机号错误！',1);      
                                    return;
                            }

                            CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/${loginUnauth}/getMobileCodeByToken", {"phone":phone}, function(data){
                                
                                if(data){
                                    if(data.code == 0){
                                          countDown();
                                      	  //测试
	     								  if(data.data.vcode){
	     									  //alert("测试验证码："+data.data.vcode)
	     								   }
                                          layer.closeAll();

                                          $('#regStep_1').hide();
                                          $('#regStep_2').show();
                                    }else{
                                        showShadedowTips(data.msg,3);    
                                    }
                                }
                            })
                        }else{
                            $('#rTips').html('请输入正确的验证码');
                        }
                    }
                })
                //layer.closeAll();
                $('#imgVValue').focus(function(){
                    $('#rTips').html('');
                })
            }, no: function(){
                
            }
            
        });

    });

    function checkLianxiForm() {
       if(oSubmitBtn.hasClass('disabled_btn')){
            return false;
        }
        var phone =  oRegUser.val(); 
        var vcode = $("#vcode").val();
      
       if(!checkMobile(phone)){ 
        showShadedowTips('手机号错误！',1);    
          return false;
       }
       if(vcode.length != 6){
         showShadedowTips('验证码错误！',1);     
         return false;
       }
   
      CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/${loginUnauth}/verifiVcodeByToken", {"phone":phone,"vcode":vcode}, function(data){
        if(data){
        if(data.code == 0){
          CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/${loginUnauth}/updatePho?falg=1", {"phone":phone}, function(data){
              if(data&&data.code == 0){
            	  showShadedowTips('修改成功',2);
            	  var flag = $("#flag").val();
            	  if(flag =="" ||flag == undefined||flag==null){
            		  flag = 1;
            	  }
            	  flag = parseInt(flag);
            	  var url = SERVER_CONTEXT+"personal/${loginUnauth}/showBasicDetail";
            	  if(flag == 2){
            		  url = SERVER_CONTEXT+"auth/${loginUnauth}/init";//联系方式认证
            	  }
                  window.location.href = url;
              }else{
                showShadedowTips(data.msg,1); 
              }
            })
          }else{
            showShadedowTips(data.msg,1);  
          }
          }
        })
    }

function countDown(){
    var oCountDownTxt =$('#countDownTxt');
    if(oCountDownTxt.html()=='点击重新获取验证码'){
        var n=59;
        oCountDownTxt.html(n+' 秒后重新获取验证码');

        var timer = setInterval(function(){
            n--;
            oCountDownTxt.html(n+' 秒后重新获取验证码');
            if(n==0){
                clearInterval(timer);
                oCountDownTxt.html('点击重新获取验证码');
            }
        },1000);
    }
    
}
</script>
</body>
</html>
