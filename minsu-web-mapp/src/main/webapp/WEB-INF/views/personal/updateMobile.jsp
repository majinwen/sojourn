<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>修改联系方式</title>
  	<script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles.css${VERSION}005">
    <script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl }/js/customer/customer.js${VERSION}002"></script>
    <script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
    <script src="${staticResourceUrl }/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl }/js/jquery.form.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styleAdd.css${VERSION}006">
</head>
<body>
	<header class="header" id="J_newHeader">
        <a href="javascript:;" onclick="history.back();" class="goback"></a>
		<h1>修改联系方式</h1>
    </header>
	<form action="" onsubmit="return false;" class="checkFormChild">
    <div class="S_contactBox" id="J_contactBox">
        <ul class="S_contactList">
        <c:forEach items="${nationCodeList }" var="nation">
        <c:if test="${ nation.countryCode==countryCode}">
            <li id="J_toCountryPage">
                <span>国家／地区</span>
                <p id="J_selectCountryName">${nation.nationName }</p>
            </li>
            <li class="S_contactLiTel">
                <span id="J_selectCountryNum">+${nation.countryCode }</span>
                <input type="tel" placeholder="请输入联系方式" id="regUser" onkeyup="inputVal(this,'#forRegUser')" value="${customerMobile }"/>
            </li>
        </c:if>
        </c:forEach>
        </ul>

        <a href="javascript:;" class="S_contactNext" id="nextStep_1">下一步</a>
    </div>
	</form>
    <div class="S_contactCountryBox" id="J_contactCountryBox">

        <div class="S_contactCountryTit">
            <i id="J_closeCountryBtn"></i>
            <p>选择国家／地区</p>
        </div>
        <ul class="S_countryList">
        <c:forEach items="${nationCodeList }" var="nation">
        	<li class="clearfix J_itemCountry">
                <p class="S_itemCountry J_itemCountryName">${nation.nationName }</p>
                <p class="S_itemCountryTelNum J_itemCountryNum">+${nation.countryCode }</p>
            </li>
        </c:forEach>
        </ul>

    </div>
    <input id="flag" type="hidden" name="flag" value="${flag }" />
	<form action="" onsubmit="return false;" class="checkFormChild">
		<div id="regStep_2" style="display: none;" class="bgF"> 
			<header class="header">
		        <a href="javascript:;" onclick="history.back();" class="goback"></a>
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
	</form>
    <script>

        //选择国家交互
        (function(){

            $('#J_toCountryPage').click(function(){
                $('#J_contactBox').hide();
                $('#J_contactCountryBox').show();
            });

            $('#J_closeCountryBtn').click(function(){
                $('#J_contactBox').show();
                $('#J_contactCountryBox').hide();
            });

            $('.J_itemCountry').click(function(){

                var name=$(this).find('.J_itemCountryName').html();
                var num=$(this).find('.J_itemCountryNum').html();

                $('#J_contactBox').show();
                $('#J_contactCountryBox').hide();

                $('#J_selectCountryName').html(name);
                $('#J_selectCountryNum').html(num);
                $('#regUser').val("");
            });

        })();
        //验证码 
        var imgId = $('#imgId').val();
		var imgUrl = $('#imgUrl').val();
		var imgSrc=imgUrl+''+imgId;
		
		var oRegUser=$('#regUser');
		var oSubmitBtn=$('#submitBtn');
		var oGetCaptcha=$('#getCaptcha');
    	$('#nextStep_1').click(function(){
    		
    		//如果是日本 验证手机号码
    		var country_code=$("#J_selectCountryNum").html();
    		var countryCode=country_code.substring(1,country_code.length);
    		if(country_code!="+86"){
    			if(validateNum(oRegUser.val())){
    		          CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/${loginUnauth}/updatePho?falg=1", {"phone":oRegUser.val(),"countryCode":countryCode}, function(data){
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
    		            });
    			} else {
    				showShadedowTips('请输入正确的手机号！',1);  
    			}
    			return;
    		}
	
	       	if(!checkMobile(oRegUser.val())){
	          showShadedowTips('请输入正确的手机号！',1);            
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
	                    showShadedowTips('请输入验证码',1);   
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
	
	                                          $('#J_contactBox').hide();
	                                          $('#J_newHeader').hide();
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
	          CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"customer/${loginUnauth}/updatePho?falg=1", {"phone":phone,"countryCode":"86"}, function(data){
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
		
		//验证必须是10为数字     
   		function validateNum(str){
			if(str.length >20){
				return false;       
			} else {
				for(var i=0; i<str.length; i++) {
					if(str.charAt(i)<'0' || str.charAt(i)>'9') {
						return false;           
					}         
				}       
			}       
			return true;     
		}    
		</script>

</body>
</html>
