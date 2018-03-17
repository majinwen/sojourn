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
	<title>个人中心</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
	<style type="text/css">
		#header{opacity: 0; position:fixed; left: 0;  top: 0; width: 100%; z-index: 999}
	</style>
</head>
<body>
<div id="myListStep_1">	
	<header class="header" id="header">
		    <!-- 
		    <a href="javascript:;" onclick="history.back();" class="goback"></a>
		     -->
			<h1>个人中心</h1>
	</header>
	<div class="main" id="main">
		<div>	
		<div class="myBanner">
			<img src="${staticResourceUrl}/images/userbg.jpg" class="bg">
			<div class="avatar" <c:if test="${sourceType == 1}"> onclick="upoladPicAndroid(3)"</c:if> >
				<!-- 审核通过不能上传修改头像    如果头像为空，显示默认头像 -->
				<%-- <c:if test="${customer.auditStatus == 1}">
					<c:if test="${empty customerVo.userPicUrl}">
						<div class="img"  style="background:url('${staticResourceUrl}/images/USER@2x.png') no-repeat center center;background-size: auto 100%;"></div>
					</c:if>
					<c:if test="${not empty customerVo.userPicUrl}">
						<div class="img"  style="background:url('${customerVo.userPicUrl}') no-repeat center center;background-size: auto 100%;"></div>
					</c:if>
				</c:if> --%>
				<c:if test="${empty customerVo.userPicUrl}">
					<div class="img" id="myUserAvatar" style="background:url('${staticResourceUrl}/images/USER@2x.png') no-repeat center center;background-size: auto 100%;"></div>
				</c:if>
				<c:if test="${not empty customerVo.userPicUrl }">
					<div class="img" id="myUserAvatar" style="background:url('${customerVo.userPicUrl}') no-repeat center center;background-size: auto 100%;"></div>
				</c:if>
			</div><!--/avatr-->
			<p class="name">${customerVo.nickName }</p>
			<c:if test="${sourceType == 1 || sourceType == 2}">
				<a href="http://www.qiehuan.com" class="links">切换房客模式</a>
			</c:if>

		</div>
		<ul class="myCenterList">
			<li class="clearfix ">
				<a href="${basePath }personal/${loginUnauth }/showBasicDetail" >
					<span class="c_icon icon_ziliao"></span>
					个人资料
					<span class="icon_r icon"></span>
				</a>
			</li>
			<li class="clearfix bor_t">
				<c:if test="${authCode == 1}">
					<a href="${basePath }personal/${loginUnauth }/showAuthDetail" > 
						<span class="c_icon icon_renzheng"></span>
						认证信息
						<span class="gray">${isAuth }</span>
						<span class="icon_r icon"></span>
					</a>
				</c:if>
				<c:if test="${authCode == 0 || authCode ==2}">
					<a href="${basePath }personal/${loginUnauth }/toAuthDetail?authType=1" > 
						<span class="c_icon icon_renzheng"></span>
						认证信息
						<span class="gray">${isAuth }</span>
						<span class="icon_r icon"></span>
					</a>
				</c:if>
			</li>
		</ul>

		<ul class="myCenterList">
			<li class="clearfix">
				<a href="${basePath }personal/${loginUnauth }/toMyBankAcount" >
					<span class="c_icon icon_zhanghu"></span>
					我的账户
					<span class="icon_r icon"></span>
				</a>
			</li>
			<li class="clearfix bor_t">
				<a href="${basePath}profitMgt/43e881/profit" >
					<span class="c_icon icon_shouyi"></span>
					我的收益
					<span class="icon_r icon"></span>
				</a>
			</li>
		</ul>
		<ul class="myCenterList">
		   <li class="clearfix">
				<a href="${basePath }landlordEva/${loginUnauth }/index">
					<span class="c_icon icon_pingjia"></span>
					我的评价				
					<span class="icon_r icon"></span>
				</a>
		</ul>
		<ul class="myCenterList">
			<c:if test="${not empty sourceType }">
				<li class="clearfix bor_b">
					<a href="javascript:;" id="chooseKf">
						<span class="c_icon icon_kefu"></span>
						联系客服
						<span class="icon_r icon"></span>
					</a>
				</li>
			</c:if>
			<li class="clearfix bor_b">
				<a href="${basePath }personal/${loginUnauth }/complain" id="customerComplain">
					<span class="c_icon icon_tousu"></span>
					投诉建议
					<span class="icon_r icon"></span>
				</a>
			</li>
			<li class="clearfix">
				<a href="${staticResourceUrl}/html/order_service_protocol.html">
					<span class="c_icon icon_xieyi"></span>
					服务协议					
					<span class="icon_r icon"></span>
				</a>
			</li>
		</ul>

		
		<ul class="myCenterList">
			<li class="btnCen">
				<c:if test="${sourceType == 1 || sourceType == 2}">
					<a href="https://www.baidu.com">
						退出登录
					</a>
				</c:if>
				<c:if test="${empty sourceType}">
					<a href="${basePath }customer/${loginUnauth}/loginOut">
						退出登录
					</a>
				</c:if>
			</li>
		</ul>
		</div>
	</div><!--/main-->

<%@ include file="../common/bottom.jsp" %>
<input type="hidden" id="sourceType" value="${sourceType }">
<input type="hidden" id="phone400" value="${phone400}">
<input type="hidden" id="picBaseAddrMona" value="${picBaseAddrMona }">
<input type="hidden" id="default_head_size" value="${default_head_size }">
<input type="hidden" id="picSuffix" value="${picSuffix}">

</div><!--/myListStep_1-->
<div id="myListStep_2" style="display:none;">	
		<header class="header">
		<a href="javascript:;" id="goBack" class="goback"></a>
		<h1>修改头像</h1>
	</header>
	<div class="main">	
			<div class="chooseImg">	
				<div id="clipArea" style="background: url('${customerVo.userPicUrl}') no-repeat center center; background-size:400px 400px;">		
				<%-- <div id="clipArea" style="background:url('${customerVo.userPicUrl}') no-repeat center center;background-size: auto 100%;"></div> --%>
				<div class="files" style="z-index: 100">	
					<input type="file" id="file">
				</div>
				<!-- 是否剪切头像 -->
				<input type="hidden" id="isClip">
			</div>
		
		<!-- 修改头像提示 -->
		<div class="headImgDesc">
			<p>请务必上传本人真实生活照（上传五官清晰的照片可以提高审核通过率）</p>
		</div>
		<div class="boxP075 mt85" id="trueWarp">
			<input type="button" value="上传头像" class="org_btn" id="clipBtn">
		</div>
		<div class="boxP075 mt85" id="fakeWarp" style="display: none;">
			<input type="button" value="上传头像" class="org_btn disabled_btn">
		</div>	

	</div>	

</div><!--/myListStep_1-->
</div>
<div class="uhide" id="chooseKfBox">
	<ul id="chooseKfBoxList">
		<li><a href="javascript:;" onclick="callphone()">400电话客服</a></li>
		<li><a href="javascript:;"   onclick="easemobIM()">7*24小时在线客服</a></li>
	</ul>
</div>
	
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	
	
	<script src="${staticResourceUrl}/js/photoClip/iscroll-zoom.js"></script>
	<script src="${staticResourceUrl}/js/photoClip/hammer.js"></script>
	<script src="${staticResourceUrl}/js/photoClip/jquery.photoClip.js"></script>
	<script src="${staticResourceUrl}/js/iscroll-probe.js"></script>

	<script type="text/javascript">
	var conHeight = $(window).height()-$('.bottomNav').height();
	var conTop=0;
	var oHeader = $('#header');
	var opacity=0;
	$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
	$('#main > div').css({'position':'absolute','width':'100%'});
	myScroll = new IScroll('#main', { scrollbars: true, probeType: 3,bindToWrapper: true, mouseWheel: true });
	
	myScroll.on('scroll', updatePosition);

	function updatePosition () {
		
		conTop = this.y>>0;	
		if(conTop<0){
			opacity = (Math.abs(conTop)*15)/1000;	
			if(opacity>1){
				opacity=1;
			}
			oHeader.css({'opacity':opacity});
		}else{
			oHeader.css({'opacity':0});
		}


		
		
	}
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

		//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		$("#clipArea").photoClip({
			width: 400,
			height: 400,
			file: "#file",
			view: "#myUserAvatar",
			ok: "#clipBtn",
			loadStart: function() {
				$('#clipArea').css({'background':'none'});
			},
			loadComplete: function() {
				$("#isClip").val("1");
			},
			clipFinish: function(dataURL) {
				$.post("${basePath }personal/${loginUnauth }/updateHeadPicBinary",{"picBin":dataURL},function(data){
					if(data.code == 0){
						showShadedowTips('头像上传成功',1);
						$("#fakeWarp").hide();
						$("#trueWarp").show();
					}else{
						showShadedowTips('图片上传失败请重新上传',1);
						$("#fakeWarp").hide();
						$("#trueWarp").show();
					}	
				},'json');
			}
		});
		
		
		$("#clipBtn").click(function(){
			if($("#isClip").val() == 1){
				$("#trueWarp").hide();
				$("#fakeWarp").show();
			}
		});
		
		/* $('#file').click(function(){
			$('#clipArea').css({'background':'none'});
		}); */
		
		$('#myUserAvatar').click(function(){
			//如果是android不处理
			if($("#sourceType").val() == "1"){
				return;
			}
			
			$('#myListStep_2').show();
			$('#myListStep_1').hide();
		});

		$('#clipBtn,#goBack').click(function(){
			$('#myListStep_1').show();
			$('#myListStep_2').hide();
		});
	
		$('#chooseKf').click(function(){
			layer.open({
					title:'联系客服',
				    content: $('#chooseKfBox').html(),
				    offset:0,
				    btn:['取消'],
				    yes: function(){
			        	closeLayer();
			    	}, no: function(){
			        	
			    	}
				    
				});
		});
	
	function closeLayer(){
		layer.closeAll();
	}
	//在线客服
	function easemobIM(){
		closeLayer();
		var sourceType = $("#sourceType").val();
		if(sourceType == 1){
			window.WebViewFunc.contactIM(); //安卓
		}else if(sourceType == 2){
			contactIM();//ios
		}

	}
	//移动端拨号
	function callphone(){
		closeLayer();
		var sourceType = $("#sourceType").val();
		var phone400 = $("#phone400").val();
		if(phone400 == ""){
			return;
		}
		if(sourceType == "1"){
			window.WebViewFunc.callPhone(phone400);
		}else if(sourceType == "2"){
			callPhone(phone400);
		}
	}

	
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
		//图片后缀
		var picSuffix = $("#picSuffix").val();
		var resJson = $.parseJSON(response);
		var param = $.param(resJson.data);
		if(resJson.status == "0"){
			//照片信息入库
			$.post(SERVER_CONTEXT+"personal/"+LOGIN_UNAUTH+"/updateHeadPicAndroid",param,function(txt){
				if(txt.code == 0){
					var headSize =  $("#default_head_size").val();
					var picBaseAddrMona = $("#picBaseAddrMona").val();
					var picInfo = resJson.data;
					var URL = picBaseAddrMona + picInfo.urlBase + picInfo.urlExt + headSize + picSuffix;
					$("#myUserAvatar").css({'background-image':"url("+URL+")"});
				}else{
					showShadedowTips('图片上传失败请重新上传',1);
				}
			},'json');
			
		}
	}
	

		
	</script>
</body>
</html>
