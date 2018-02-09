<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="applicable-device" content="mobile">
	<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">        
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="yes" name="apple-mobile-web-app-capable"/>
	<meta content="yes" name="apple-touch-fullscreen"/>
	<title>我的消息</title>
	<link rel="stylesheet" type="text/css" href="${basePath }css/styles_new.css${VERSION}003">
	<style type="text/css">
		.header,
		.goback{height: 44px;line-height: 44px;}
		.header h1{font-size: 18px;line-height: 44px;}
		textarea{word-break: break-all; word-wrap:break-word;}
		.c_bodys {
			height: calc(100% - 4.85rem);
			overflow: auto;
			-webkit-overflow-scrolling: touch;
			padding-bottom: 4.85rem;
		}
		#house{background: #fff;height: 92px;padding: 16px;border-bottom: 0.5px solid #eee;}
		.house_img{width: 90px;height: 60px;float: left;overflow: hidden;}
		.house_img img{width: 100%;}
		.house_txt{margin-left: 106px;}
		.house_txt .house_address{width: 100%;height: 40px;line-height: 20px;font-size: 14px;color: #444;
		word-wrap:break-word;display: -webkit-box;-webkit-box-orient; text-overflow: ellipsis; overflow : hidden;
		display: -webkit-box;-webkit-line-clamp: 2;-webkit-box-orient: vertical;margin-bottom: 8px;}
		.house_txt .house_renttype{height: 12px;line-height: 12px;font-size: 12px;color: #999;}
		.c_btn{width:100%;height:48px;line-height:48px;text-align: center;color: #fff;font-size: 16px;background: #ffa000;}
		.come{height: 36px;line-height: 36px;color: #ffa000;font-size: 12px;text-align: center;background: #F8E5C5;}
  </style>
</head>
<body>
		<header class="header">
			<a href="javascript:void(0);" id="goback" class="goback"  ></a>
			<h1>
			<c:if test="${msgSenderType ==1 }">
			   ${tenantName }
			</c:if>
			<c:if test="${msgSenderType ==2 }">
			    ${lanlordName }
			</c:if>
			
			</h1>
		</header>
		<c:choose>
		   <c:when test="${msgSenderType ==2 }">  
		        <div class="c_bodys" id="scrolldIV">    
		   </c:when>
		   <c:otherwise> 
		      <div class="bodys" id="scrolldIV">
		   </c:otherwise>
		  </c:choose>
		
			<div>
			    <div id="house" class="clearfix">
					<div class="house_img">
						<img src="${housePicUrl }"/>
					</div>
					<div class="house_txt">
						<div class="house_address">${houseName }</div>
						<div class="house_renttype">${rentWayStr }</div>
					</div>
				</div>
				<div class="come">
					欢迎！在自如民宿，每间美屋都由房东自主运营。
				</div>
				<div id="loadMore" class="loadMoreBtn">加载更多</div>
				<div class="item_box" id="messageBox"></div>
			</div>
	    </div>
    <input type="hidden" id ="msgHouseFid" value="${msgHouseFid }"/>
    <input type="hidden" id ="tenantUrl" value="${tenantUrl }"/>
    <input type="hidden" id ="lanlordUrl" value="${lanlordUrl }"/>
    <input type="hidden" id ="nowTime" value="${nowTime }"/>
    <input type="hidden" id ="msgSenderType" value="${msgSenderType }"/>
    <input type="hidden" id ="imSource" value="${imSource }"/>
    <input type="hidden" id ="sendNum" value="${sendNum }"/>
    <input type="hidden" id ="jpushFlag" value="${jpushFlag }"/>
    <input type="hidden" id="dSource" value="${dSource }">
    <input type="hidden" id="MAPP_DOMAIN" value="${MAPP_DOMAIN }">
    <input type="hidden" id ="fid" value="${fid }"/>
    <input type="hidden" id ="rentWay" value="${rentWay }"/>
    <input type="hidden" id ="lanlordName" value="${lanlordName }"/>
    <input type="hidden" id ="version" value="${version }"/>
    <input type="hidden" id ="imSourceList" value="${imSourceList }"/>
    <input type="hidden" id ="uid" value="${uid }"/>
    <input type="hidden" id="isImOpen" value="${isOpen }">
    <div class="box box_bottom_new" id="box_bottom_new" style="height: auto;">
			<div class="ipt_box clearfix">
			<textarea id="txtIpt" class="ipt" rows="1" style="height: auto; padding: 8px 0;" rows="1"></textarea>
			 <input id="sentBtn"  class="btn btn_org"  type="button"   value="发送"  msgSentType ="${msgSenderType}" style="position: absolute; right:8px; bottom: 6px; height: 32px;" />
		</div>
		<c:if test="${msgSenderType == 2 && version!=-1 }">
		  <div id="bookComplate" class="c_btn" onclick="finishBook();">完成预订</div>
		</c:if>
	</div> 
	 
	<script src="${basePath }js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${basePath }layer/layer.js${VERSION}001" type="text/javascript"></script>
	<script src="${basePath }js/common/common.js${VERSION}001" type="text/javascript"></script>
	<script src="${basePath }js/common/commonUtils.js${VERSION}001" type="text/javascript"></script>
	<script src="${basePath }js/iscroll-probe.js${VERSION}006"></script>
	<script src="${basePath }js/chat.js${VERSION}010" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		
		if($("#isImOpen").val() == 1){
			layer.open({
			  type: 1, shadeClose:false,
			  content: '<div style="padding:1rem .5rem; width:15rem; text-align:center; font-size:.6rem; border-radius:1rem; line-height:.8rem;"><img src="http://www.ziroom.com/static/201401/mapp/images/logo.png" style="width:200px; margin:0 auto .5rem auto;"><br>Hi！自如民宿已发布新的版本，新消息提醒更醒目，聊天功能更流畅，请立即更新以免影响聊天功能的正常使用哦！<br>'
			  			+'<a href="javascript:;" onclick="javascript:window.location=\'http://a.app.qq.com/o/simple.jsp?pkgname=com.ziroom.ziroomcustomer\'" style="background:#ffa000; color#fff; padding:.0rem 3rem; display:inline-block; line-height:2rem; margin-top:.5rem; color:#fff;border-radius:1rem;">下载APP</a></div>'
			});
		}
		
		
		hiddenBookBtn();
		
		$(document).on("click", "*", function(){ 
			hiddenKeyBord();
			return false;
		});
		$("#txtIpt").click(function(){return false});
		
	}); 
	function hiddenBookBtn(){//没有预定完成按钮调节式样
		if($("#msgSenderType").val()!=1){
			$("#box_bottom_new").css({"bottom":"0" });
			$("#sentBtn").css({"bottom":"6px" });
		}
	}
	function hiddenKeyBord(){
		var imSource = parseInt($("#imSource").val());
		if(imSource == 1){
		  
		}else if(imSource == 4){//隐藏IOS输入键盘
			endEditing();
		}
	}	
	$('#txtIpt').on('input',function(){
        autoResize();
	});
		autoResize();
		var minRows = 1;
		   // 最大高度，超过则出现滚动条
		   var maxRows = 5;
		   function autoResize(){
		       var t = document.getElementById('txtIpt');
		       if (t.scrollTop == 0) t.scrollTop=1;
		       while (t.scrollTop == 0){
		           if (t.rows > minRows)
		               t.rows--;
		           else
		               break;
		           t.scrollTop = 1;
		           if (t.rows < maxRows)
		               t.style.overflowY = "hidden";
		           if (t.scrollTop > 0){
		               t.rows++;
		               break;
		           }
		       }
		       while(t.scrollTop > 0){
		           if (t.rows < maxRows){
		               t.rows++;
		               if (t.scrollTop == 0) t.scrollTop=1;
		           }
		           else{
		               t.style.overflowY = "auto";
		               break;
		           }
		       }
		   }
	</script>
	
</body>
</html>