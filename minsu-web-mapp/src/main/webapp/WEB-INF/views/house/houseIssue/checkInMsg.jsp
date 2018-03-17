<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="applicable-device" content="mobile">
	<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
	<script type="text/javascript" src="${staticResourceUrl}/js/header.js${VERSION}001"></script>
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="yes" name="apple-touch-fullscreen" />
	<title>入住信息</title>
	<link href="${staticResourceUrl}/css/mui.picker.min.css${VERSION}001" rel="stylesheet" />
	<link rel="stylesheet" type="text/css"
	href="${staticResourceUrl}//css/styles_new.css${VERSION}001">
	
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/css_830.css${VERSION}001">
</head>
<body>
	<form id="form" >
		<header class="header"> <a href="${basePath }houseIssue/${loginUnauth }/showOptionalInfo?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}" class="goback"></a>
			<h1>入住信息</h1>
		</header>
		<input name="houseBaseFid" value="${houseBaseFid }" type="hidden"/>
		<div class="bodys">
			<div class="main">

				<ul id="houseList" class="myCenterList">
					
					<li class="c_ipt_li  bor_b "> 
						<a href="javascript:void(0);"> 
							<span class="c_span">最小入住天数</span>
							<input class="c_ipt j_s_ipt" type="text" placeholder="请选择最小入住天数" id="minDay" value="" readonly="readonly">
					<select onchange="setValToInput(this,'minDay')" name="minDay">
						<c:forEach items="${minDayList }" var="obj">
                        	<option value="${obj.key }" <c:if test="${obj.key == houseBaseExt.minDay }">selected</c:if>>${obj.text }</option>
                        </c:forEach>
					</select>
							<span class="icon_r icon"></span>
						</a>
					</li>
					<li class="c_ipt_li  bor_b "> 
						<a href="javascript:void(0);"> 
							<span class="c_span">入住时间</span>
							<input class="c_ipt j_s_ipt" type="text" id="checkInTime" placeholder="请选择入住时间" value="" readonly="readonly">
					<select onchange="setTextToInput(this,'checkInTime','后')" name="checkInTime">
						<c:forEach items="${checkInTimeList }" var="obj">
                        	<option value="${obj.key }" <c:if test="${obj.key == houseBaseExt.checkInTime }">selected</c:if>>${obj.text }</option>
                        </c:forEach>
					</select>
							<span class="icon_r icon"></span>
						</a>
					</li>
					<li class="c_ipt_li  bor_b "> 
						<a href="javascript:void(0);"> 
							<span class="c_span">退房时间</span>
							<input class="c_ipt j_s_ipt" type="text" id="checkOutTime" placeholder="请选择退房时间" value="" readonly="readonly">
					<select onchange="setTextToInput(this,'checkOutTime','前')" name="checkOutTime">
						<c:forEach items="${checkOutTimeList }" var="obj">
                        	<option value="${obj.key }" <c:if test="${obj.key == houseBaseExt.checkOutTime }">selected</c:if>>${obj.text }</option>
                        </c:forEach>
					</select>
							<span class="icon_r icon"></span>
						</a>
					</li>
					<li class="c_ipt_li  bor_b "> 
						<a href="javascript:void(0);"> 
							<span class="c_span">被单更换规则</span>
							<input class="c_ipt j_s_ipt" type="text" id="sheetsReplaceRules" placeholder="请选择被单更换规则" value="" readonly="readonly">
					
					<select onchange="setValToInput(this,'sheetsReplaceRules')" name="sheetsReplaceRules">
						<c:forEach items="${sheetReplaceList }" var="obj">
                        	<option value="${obj.key }" <c:if test="${obj.key == houseBaseExt.sheetsReplaceRules }">selected</c:if>>${obj.text }</option>
                        </c:forEach>
					</select>
							<span class="icon_r icon"></span>
						</a>
					</li>
					<li <c:if test="${isComplete == 1}">class="c_ipt_li "</c:if><c:if test="${isComplete != 1}">class="c_ipt_li c_ipt_li_none"</c:if>>
						<a href="javascript:;" onclick="toHouseRules()">
							<c:if test="${isComplete == 1}"><span class="c_span">房屋守则</span></c:if>
							<input  class="c_ipt" type="text" placeholder="请填写房屋守则" id="houseRuler" name="houseRuler" value="<c:if test="${isComplete == 1}">已完成</c:if>" readonly="readonly"> 
							
							<span class="icon_r icon"></span>
						</a>
					</li>

				</ul>
				<!--/main-->
			</div>
			<div class="boxP080">
				<input type="button" id="submitBtn" value="确认" class="org_btn  btn_radius">
			</div>
		</form>
		<!-- 添加房源名称 -->

		<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001"
		type="text/javascript"></script>
		<script type="text/javascript"
		src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
		<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
		<script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}001"></script>
		<script type="text/javascript" src="${staticResourceUrl}/js/mui.min.js"></script>
		<script type="text/javascript" src="${staticResourceUrl}/js/mui.picker.min.js"></script>
		<script type="text/javascript" src="${staticResourceUrl}/js/mui.poppicker.js"></script>
		<script type="text/javascript" src="${staticResourceUrl}/js/common_830.js"></script>
		<script type="text/javascript">
			$(function(){
				document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			})
		</script>
		<script type="text/javascript">
		$(function(){
			init();
			loadVal();
		})
		
		function init(){
			if(valid()){
				$("#submitBtn").removeClass("disabled_btn").addClass("org_btn");
			}else{
				$("#submitBtn").addClass("disabled_btn").removeClass("org_btn");
			}
		}
		
		function valid(){
			return $.trim($("select[name='minDay']").val()).length != 0 && $.trim($("select[name='checkInTime']").val()).length != 0 
					&& $.trim($("select[name='checkOutTime']").val()).length != 0 && $.trim($("select[name='sheetsReplaceRules']").val()).length != 0;
		}
		
		$("select").keyup(function(){
			init();
		});
		
		function setTextToInput(obj,id,text){
		    var txt = $(obj).find("option:selected").text()+text;
		    $('#'+id).val(txt);
		}
		
		function loadVal(){
			$("#minDay").val($("select[name='minDay']").find("option:selected").text());
			$("#checkInTime").val($("select[name='checkInTime']").find("option:selected").text()+"后");
			$("#checkOutTime").val($("select[name='checkOutTime']").find("option:selected").text()+"前");
			$("#sheetsReplaceRules").val($("select[name='sheetsReplaceRules']").find("option:selected").text());
		}
		
		function toHouseRules(){
			window.location.href='${basePath }houseIssue/${loginUnauth }/toSelectHouseRules?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}';
			/* window.location.href='${basePath }houseIssue/${loginUnauth }/totHouseRules?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}'; */
			updateCheckInMsg(false);
		}
		
		$("#submitBtn").click(function(){
			if($(this).hasClass("disabled_btn")){
				return false;
			}
	    	$(this).addClass("disabled_btn").removeClass("org_btn");
	    	updateCheckInMsg(true);
	    	$(this).removeClass("disabled_btn").addClass("org_btn");
		});
		
		function updateCheckInMsg(isShow){
	    	$.ajax({
				url:"${basePath }/houseIssue/${loginUnauth }/updateCheckInMsg",
				data:toJson($("#form").serializeArray()),
				dataType:"json",
				type:"post",
				async: true,
				success:function(result) {
					if(!isShow){
						return;
					}
					if(result.code === 0){
						window.location.href='${basePath }houseIssue/${loginUnauth }/showOptionalInfo?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}';
					}else{
						showShadedowTips("操作失败",1);
					}
				},
				error:function(result){
					if(!isShow){
						return;
					}
					showShadedowTips("未知错误",1);
				}
			});
		}
		
		function toJson(array){
			var json = {};
			$.each(array, function(i, n){
				var key = n.name;
				var value = n.value;
				if($.trim(value).length != 0){
					json[key] = value;
				}
			});
			return json;
		}	
	</script>

	</body>
	</html>
