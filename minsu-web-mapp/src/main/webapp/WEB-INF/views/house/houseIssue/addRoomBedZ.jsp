<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="applicable-device" content="mobile"> 
	<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
	    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="yes" name="apple-touch-fullscreen" />
	<title>房间</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/mui.picker.min.css${VERSION}001" />  
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/css_830.css${VERSION}001">
	<style type="text/css">
	#addHouseFourForm, #addSupporting {
		height: 100%;
	}
	#addHouseFourForm .bodys, #addSupporting .bodys {
		height: calc(100% - 2.45rem);
		overflow: auto;
		-webkit-overflow-scrolling: touch;
	}
	</style>
</head>
<body>

	<form id="addHouseFourForm" >
		<input id="rentType" type="hidden" value="0">
		<!-- 租住类型 0 整租 1分租 整租只有床位信息 分租必须有床 -->
		
		<header class="header">  <a href="javascript:;" id="goback" class="goback"></a>
			<h1>房间${index }</h1>
		</header>
		<div class="bodys">
			<div id="main" class="main">
				<div class="box">
					<!-- TODO:还未添加床位信息时显示 -->
					   <p class="noBedMessage" id="emptyImg" style="display:none;">您还没有添加床位</p>
					    <ul id="roomList" class="myCenterList">
					 <c:if test="${bedList.size()>0}">
					  
					    <c:forEach var="bedList" items="${bedList}" varStatus="no" >
							 <li id="li_bed" class="c_ipt_li j_ipt_li bor_b mui-table-view-cell mui-transitioning" bedFid='${bedList.fid }'>
								<div class="mui-slider-right mui-disabled" data-roomfid="">
							        <a class="mui-btn mui-btn-red" bedFid='${bedList.fid }' style="-webkit-transform: translate(0px, 0px);">删除</a>
								</div>
								<a href="javascript:void(0);">
								<div id="bedNum" class="mui-slider-handle">
								<span class="c_span j_span">床位 ${no.index+1 }</span>
								<input class="c_ipt j_b_ipt" type="text" placeholder="请选择床位信息"  typeSize="${bedList.bedType }_${bedList.bedSize }_${bedList.fid }" id="bed${no.index+1 }" name="bed${no.index+1 }" value="${bedList.bedTypeDis } ${bedList.bedSizeDis }" readonly="readonly">
								</div>
								<span class="icon_r icon"></span>
							</a>
						</li>
			            </c:forEach>
					 </c:if>
					</ul>
					</div>
					<!--/main-->
				</div>
			</div>
			<ul class="bottomBtns clearfix">
				<li id="addBedBtn" class="addBtn">添加床位</li>
				<li id="submitBtn" class="submitBtn submitBtn_disabled">确定</li>
			</ul>
		</form>
		<input type="hidden"  name="rulerData" id="rulerData" value="${rulerData }"/> 
		<input type="hidden"  name="houseBaseFid" id="houseBaseFid" value="${houseBaseFid }"/> 
		<input type="hidden"  name="roomFid" id="roomFid" value="${roomFid }"/> 
		<!-- 添加房源名称 -->

		<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
		<script type="text/javascript" src="${staticResourceUrl }/js/layer/layer.js${VERSION}001"></script>
		<script src="${staticResourceUrl }/js/common2.js${VERSION}001"></script>
		<script type="text/javascript" src="${staticResourceUrl}/js/common/commonUtils.js${VERSION}001"></script>
		<script src="${staticResourceUrl }/js/iscroll-probe.js${VERSION}001"></script>
		<script type="text/javascript" src="${staticResourceUrl }/js/mui.min.js${VERSION}001"></script>
		<script type="text/javascript" src="${staticResourceUrl }/js/mui.picker.min.js${VERSION}001"></script>
		<script type="text/javascript" src="${staticResourceUrl }/js/mui.poppicker.js"></script>
		<script type="text/javascript" src="${staticResourceUrl }/js/common_830_2.js"></script>
<script>
(function($, doc) {
	$.init();
	$.ready(function() {
		
		var rulerData = ${rulerData };
		console.log(rulerData);
		var curBeds = $$(".j_ipt_li").length;
		
		if(curBeds == 0){
			$$("#emptyImg").show();
			$$("#roomList").addClass("noList");
			$$("#submitBtn").addClass("submitBtn_disabled");
		}else{
			$$("#submitBtn").removeClass("submitBtn_disabled");
		}
		//级联示例
		var bedPicker = new $.PopPicker({
			layer: 2
		});
		bedPicker.setData(rulerData);
		var showRulerPickerButton = doc.getElementById('addBedBtn');
		showRulerPickerButton.addEventListener('tap', function(event) {
			bedPicker.show(function(items) {
				var beds = $$(".j_ipt_li").length;
				if(beds > 6){
					mui.confirm('您最多可添加7个床位！', '', ['关闭'], function(e) {});
					return;
				}
				$$("#emptyImg").hide();
				$$("#roomList").removeClass("noList");
				$$("#submitBtn").removeClass("submitBtn_disabled");
				var _str='';
				_str+='<li id="li_bed'+(beds+1)+'" class="c_ipt_li j_ipt_li bor_b mui-table-view-cell mui-transitioning"> '
				+	'<div class="mui-slider-right mui-disabled" data-roomfid="">'
				+		'<a class="mui-btn mui-btn-red" style="-webkit-transform: translate(0px, 0px);">删除</a>'
				+	'</div>'
				+	'<a href="javascript:void(0);">'
				+		'<div class="mui-slider-handle">'
				+			'<span class="c_span j_span">床位'+(beds+1)+'</span>'
				
				+			'<input class="c_ipt j_b_ipt" type="text" placeholder="请选择床位信息" typeSize="'+items[0].value+'_'+items[1].value+'_0" id="bed'+(beds+1)+'" name="bed'+(beds+1)+'" value="'+items[0].text + " " + items[1].text+'" readonly="readonly"></div>'
				+		'<span class="icon_r icon"></span>'

				+	'</a>'
				+'</li>'

				$$("#roomList").append(_str);
			});
		}, false);
		
		var saveRoomInfoUrl = SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/saveRoomBedZ";
		$$("#submitBtn").click(function(){
			var typeSizeStr ="";
			 $('input[id^=bed]').each(function(){//遍历
				 var typeSize = $$(this).attr("typeSize");
				 typeSizeStr = typeSizeStr+typeSize+",";
		          
		      })
		      
		      var houseBaseFid = $$("#houseBaseFid").val();
			  if(houseBaseFid==null||houseBaseFid==""){
				  //alert("房源信息错误");
				  showShadedowTips("房源信息错误",2);
				  return false;
			  }
		      if(typeSizeStr!=""){
		    	  CommonUtils.ajaxPostSubmit(saveRoomInfoUrl, {"houseBaseFid":houseBaseFid,"typeSize":typeSizeStr,"roomFid":$$("#roomFid").val()}, function(data){
		        	   
		    		  if(data){
		    			  var code = data.code;
		    			  if(code == 0){
		    				  window.location.href=  SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/houseApartment?houseBaseFid="+houseBaseFid+"&flag="+${flag};
		    			  }else{
		    				  //alert(data.msg);
		    				  showShadedowTips(data.msg,2);
		    			  }
		    		  }
		           })
		      }
		      
		})
		
		
		
	});
})(mui, document);

</script>
<script type="text/javascript">

        mui.init();
		(function($) {
			$('#roomList').on('tap', '.mui-btn', function(event) {
				var elem = this;
				var li = elem.parentNode.parentNode;
				var liSize = $$('#roomList .j_ipt_li').size();
				var rentType = $$("#rentType").val();
				var bedFid =  $$(this).attr("bedFid");
				mui.confirm('确认删除该条床位信息？', '', btnArray, function(e) {
					var liSize = $$('#roomList .j_ipt_li').size();
					if (e.index == 1) { //这是确认
						if(bedFid==null||bedFid==""){
							li.parentNode.removeChild(li);
							var curBedsNum = $$(".j_ipt_li").length;
	    					if(curBedsNum == 0){
	    						$$("#emptyImg").show();
	    						$$("#roomList").addClass("noList");
	    						$$("#submitBtn").addClass("submitBtn_disabled");
	    					}
							return false;
						}
						CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/delBed", {"bedFid":bedFid}, function(data){
				        	   
				    		  if(data){
				    			  var code = data.code;
				    			  if(code == 0){
				    					li.parentNode.removeChild(li);
				    					var curBedsNum = $$(".j_ipt_li").length;
				    					if(curBedsNum == 0){
				    						$$("#emptyImg").show();
				    						$$("#roomList").addClass("noList");
				    						$$("#submitBtn").addClass("submitBtn_disabled");
				    					}
				    			  }else{
				    				  //alert(data.msg);
				    				  showShadedowTips(data.msg,2);
				    			  }
				    		  }
				           })
						
						
					} else {//这是取消
						setTimeout(function() {
							$.swipeoutClose(li);
						}, 0);
					}
				});
				
			});
			var btnArray = ['取消', '确认'];
			
		})(mui);
	   
	</script>
	<script type="text/javascript">
	
		$$("#goback").click(function(){

			//alert("单击事件触发");
			var bedListlength = '${bedList.size()}';
			var tmp = document.getElementById("roomList").getElementsByTagName("li");
			if(tmp.length > bedListlength){
				layer.open({
				 	shade: true,
				    shadeClose:true,
			    	content: '<div class="loginTips" style="line-height: 1.1rem">您填写的信息未保存，确认放弃此次编辑？</div>',
			    	btn:['继续编辑', '放弃'],
			    	no: function(){
			    		window.location.href = '${basePath }roomMgt/${loginUnauth}/houseApartment?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid }&rentWay=${rentWay}&flag=${flag}';
			        }, 
			    	yes: function(){
			    		layer.closeAll();
			        }
			  	 });
			}else{
			   window.location.href = '${basePath }roomMgt/${loginUnauth}/houseApartment?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid }&rentWay=${rentWay}&flag=${flag}';
			}
		});
	</script>

</body>
</html>