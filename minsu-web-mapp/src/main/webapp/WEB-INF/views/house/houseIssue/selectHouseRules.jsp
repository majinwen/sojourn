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
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
	<title>房屋守则</title>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles.css${VERSION}001">
	<link rel="stylesheet" href="${staticResourceUrl}/css/styleAdd.css${VERSION}001"/>
    <script src="${staticResourceUrl}/js/jquery-2.1.1.min.js"></script>
</head>
<body style="background-color: #fafafa">
    <header class="header">
        <a href="${basePath }houseIssue/${loginUnauth }/showOptionalInfo?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}" class="goback"></a>
        <h1>房屋守则</h1>
    </header>
	<input type="hidden" id="houseBaseFid" value="${houseBaseFid}">
	<input type="hidden" id="houseRoomFid" value="${houseRoomFid}">
    <div class="S_ruleSelectBox">
        <ul class="S_ruleSelectUl">
        	<c:forEach items="${selectHouseRulesList}" var="houseRules">  
        		<li class="J_ruleSelect" data-isSelect="0" data-value="${houseRules.key}">
                	<span>${houseRules.text}</span>
               		<i class="S_ruleIs"></i>
            	</li>
			</c:forEach>   
        </ul>         
    </div>

    <div class="S_elseRule">
       	<a href="javascript:;" onclick="toHouseRules()">
            <span>其它守则</span>
            <i></i>
       	</a>
    </div>
	
    <input type="button" id="submitBtn" value="确认" class="S_ruleSelectSubmit"/>
</body>

<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
<script>
	$(function(){
		var select='${listFindByFidAndCode}';
		$('.J_ruleSelect').each(function(){
			for(var i=0;i<select.length;i++){
	   			if($(this).attr("data-value")==select[i]){
	   				$(this).attr('data-isSelect','1');
	          	     	$(this).find('i').addClass('on');
	   			}
			}
   		});
	});
	
    $('.J_ruleSelect').click(function(){
        if($(this).attr('data-isSelect')=='0'){
            $(this).attr('data-isSelect','1');
            $(this).find('i').addClass('on');
        }
        else{
            $(this).attr('data-isSelect','0');
            $(this).find('i').removeClass('on');
        }
    });
    
    var arr1 = [];
    function toHouseRules(){
    	$('.J_ruleSelect').each(function(){
    		if($(this).attr("data-isSelect")==1){
    			arr1.push($(this).attr("data-value"));
    		}
    	});
    	$.ajax({
    		url:"${basePath }/houseIssue/${loginUnauth }/updateSelectHouseRules",
    		data:{"selectRules":arr1.toString(),"houseBaseFid":$("#houseBaseFid").val(),"houseRoomFid":$("#houseRoomFid").val()},
    		dataType:"json",
    		type:"post",
    		async: false,
    		success:function(result){
    			if(result.code === 0){
    				window.location.href='${basePath }houseIssue/${loginUnauth }/toHouseRules?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}';
    				updateCheckInMsg(false);  
				}else{
					showShadedowTips("操作失败",1); 
				}
    		},
    		error:function(result){
				showShadedowTips("未知错误",1);
			}
    	}); 
		/* window.location.href='${basePath }houseIssue/${loginUnauth }/toHouseRules?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}';
		updateCheckInMsg(false);  */
	}
    
    var arr = [];
    $("#submitBtn").click(function(){
    	$('.J_ruleSelect').each(function(){
    		if($(this).attr("data-isSelect")==1){
    			arr.push($(this).attr("data-value"));
    		}
    	});
    	updateSelectHouseRules();
    });
    
    function updateSelectHouseRules(){
    	$.ajax({
    		url:"${basePath }/houseIssue/${loginUnauth }/updateSelectHouseRules",
    		data:{"selectRules":arr.toString(),"houseBaseFid":$("#houseBaseFid").val(),"houseRoomFid":$("#houseRoomFid").val()},
    		dataType:"json",
    		type:"post",
    		async: true,
    		success:function(result){
    			if(result.code === 0){
					window.location.href = "${basePath }houseIssue/${loginUnauth }/toCheckInMsg?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}";
				}else{
					/* window.location.href = "${basePath }houseIssue/${loginUnauth }/toCheckInMsg?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}"; */
					showShadedowTips("操作失败",1); 
				}
    		},
    		error:function(result){
				showShadedowTips("未知错误",1);
			}
    	}); 	
    }
</script>
</html>