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
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
    <meta name="format-detection" content="telephone=no">
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
	<title>真实头像</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
		
</head>
<body>

<header class="header">
	<a href="${basePath}auth/${loginUnauth}/init?checkFlag=1" id="goback" class="goback" ></a>
	<h1>个人信息</h1>
</header>

	<div class="main " id="main">			
		<div id="mainScroll">	
			<c:if test="${sourceType != 1}">
					<div class="chooseImg" id="chooseImg">		
					<div id="clipArea" style="background: url('${headPicUrl}') no-repeat center center; background-size:500px 500px;"></div>
					<div class="files">	
						<input type="file" id="file">
					</div>
				</div> 
			</c:if>
			<!-- android默认头像，如果有显示 -->
			<c:if test="${sourceType == 1 }">
					<div id="headPic" onclick="upoladPicAndroid(3)" class="chooseImg"
					 <c:if test="${not empty  headPicUrl}"> style="background: #e7e7e7 url(${headPicUrl}) no-repeat center center;background-size: auto 100%;"</c:if> >	
					<div class="files">	
						<input type="file" >
					</div>
				</div> 
			</c:if>
			<div class="myCenterListNoneA">
			<!-- 上传头像提示 -->
			<div class="headImgDesc">
				<p>1、如您是个人房东，请上传本人真实生活照，且能露出五官<br>
					   2、如您是商户或品牌民宿，头像允许展示品牌LOGO或团队形象图案</p>
			</div>
			<ul class="myCenterList">
				<li class="clearfix bor_b">				
					<a href="javascript:;" id="toIntorduce">
						个人介绍<input type="text" <c:if test="${hasIntroduce == 1}">placeholder="已完成"</c:if> <c:if test="${hasIntroduce != 1}">placeholder="请输入您的个人介绍"</c:if>  readonly="readonly">
							  <span class="icon_r icon" ></span>
					</a>
				</li>
				<li class="clearfix">
					<a href="javascript:;" id="toNickName">
						昵称<input type="text" <c:if test="${hasNickName==1 }">placeholder="已完成"</c:if><c:if test="${hasNickName==0 }">placeholder="请输入您的昵称"</c:if> readonly="readonly"/>
						 <span class="icon_r icon" ></span>
					</a>				
				</li>
			</ul>
			</div>
		</div>	
	</div><!--/main-->
	<c:if test="${sourceType != 1}">
		<div class="box box_bottom clearfix" id="clipBtn_wrapper" style="display: none;">
			<input id="clipBtn" type="button" value="确认" class="org_btn btn_radius">
		</div>
		<div class="box box_bottom clearfix" id="mybtn_wrapper">
			<input id="mybtn" type="button" value="确认" class="org_btn btn_radius">
		</div>
	</c:if>

	<input type="hidden" id="sourceType" value="${sourceType }">
	<input type="hidden" id="isClip">
	<input type="hidden" id="btnType">
	
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}006"></script>
	
	
	<script src="${staticResourceUrl}/js/photoClip/iscroll-zoom.js"></script>
	<script src="${staticResourceUrl}/js/photoClip/hammer.js"></script>
	<script src="${staticResourceUrl}/js/photoClip/jquery.photoClip.js${VERSION}001"></script>
	<script>
	
		var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });
		var conHeight = $(window).height()-$('.header').height()-$('.box_bottom').height();
		
		$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$('#mainScroll').css({'position':'absolute','width':'100%'});
		
		myScroll.refresh();
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false); 
	
	
		$("#clipArea").photoClip({
			width:500,
			height:500,
			file: "#file",
			view: " ",
			ok: "#clipBtn",
			loadStart: function() {
				$('#clipArea').css({'background':'none'});
				
			},
			loadComplete: function() {
				$("#isClip").val("1");
				$("#mybtn_wrapper").hide();
				$("#clipBtn_wrapper").show();
			},
			clipFinish: function(dataURL) {
				$.post("${basePath }personal/${loginUnauth }/updateHeadPicBinary",{"picBin":dataURL},function(data){
					if(data.code == 0){
						window.location.href = "${basePath }auth/${loginUnauth }/init?checkFlag=1";
						var btnType = $("#btnType").val();
						if(btnType == 1){
							//个人介绍
							window.location.href="${basePath}personal/${loginUnauth}/introduce?type=2";
						}else if(btnType == 2){
							//昵称
							window.location.href = "${basePath}auth/${loginUnauth}/toNickNamePage";
						}else{
							window.location.href = "${basePath }auth/${loginUnauth }/init?checkFlag=1";
						}
					}
				},'json');
				
			},
			loadError:function(data){
				showShadedowTips('请检查是否开启相册权限',1);
			}
		});


	/* $('#file').click(function(){
		$('#clipArea').css({'background':'none'});
	}); */
	
	
	//调用android图片上传方法
	function upoladPicAndroid(type){
		var sourceType = $("#sourceType").val();
		//来自android
		if(sourceType == "1"){
			window.WebViewFunc.uploadUserPic(type);
			return;
		}
	}
	
	
	//android图片回调
	function handleAndroidPic(response){
		if(response == null || response == ""){
			showShadedowTips('图片上传失败请重新上传',1);
			return;
		}
		var resJson = $.parseJSON(response);
		var param = $.param(resJson.data);
		if(resJson.status == "0"){
			//照片信息入库
			$.post(SERVER_CONTEXT+"personal/"+LOGIN_UNAUTH+"/updateHeadPicAndroid",param,function(txt){
				if(txt.code == 0){
					$("#headPic").css({'background-image':"url("+resJson.data.url+")"});
				}else{
					showShadedowTips('图片上传失败请重新上传',1);
				}
			},'json');
			
		}
	}
	
	//跳转到个人介绍
	$("#toIntorduce").click(function(){
		
		if($("#isClip").val() == "1"){
			var clipBtn = document.getElementById("clipBtn");
			if(clipBtn.click){
				//clipBtn.click();
				var eventObj = new MouseEvent("click", {
				    bubbles: true,
				    cancelable: true
				});
				$("#btnType").val(1);
				clipBtn.dispatchEvent(eventObj);
				alert('头像上传成功');
			}
		}
		window.location.href="${basePath}personal/${loginUnauth}/introduce?type=2";
	});
	//跳转到个人介绍
	$("#toNickName").click(function(){
		if($("#isClip").val() == "1"){
			var clipBtn = document.getElementById("clipBtn");
			if(clipBtn.click){
				 var eventObj = new MouseEvent("click", {
				    bubbles: true,
				    cancelable: true
				});
				$("#btnType").val(2);
				clipBtn.dispatchEvent(eventObj);
				alert('头像上传成功');
			}
		}
		window.location.href = "${basePath}auth/${loginUnauth}/toNickNamePage";
	});
	
	$("#mybtn").click(function(event){
		window.location.href = "${basePath }auth/${loginUnauth }/init?checkFlag=1";
	});
	
	$("#clipBtn").click(function(){
		$(this).addClass("disabled_btn");
		$(this).attr("id","");
	});
	</script>
</body>
</html>