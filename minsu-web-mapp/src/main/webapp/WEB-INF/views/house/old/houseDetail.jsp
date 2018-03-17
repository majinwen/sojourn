<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>发布房源</title>
<link rel="stylesheet" type="text/css"
	href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
<style type="text/css">
#addHouseFourForm, #addSupporting {
	height: 100%
}

#addHouseFourForm .bodys, #addSupporting .bodys {
	height: calc(100% - 2.45rem);
	overflow: auto;
	-webkit-overflow-scrolling: touch;
}
</style>
</head>
<body>

	<form id="addHouseFourForm" style="display: none;"
		onsubmit="return false;">
		<input type="hidden" name="priceLimit" id="priceLimit" value="${priceLimit }">
		<div class="bodys">
			<div class="main myCenterListNoneA">
				<header class="header"> <a
					href="${basePath}houseDeploy/${loginUnauth}/toLocationThree?houseBaseFid=${houseBaseFid}"
					id="goback" class="goback"></a>
				<h1>发布房源(4/4)</h1>
				</header>
				<ul class="myCenterList">
					<li class="clearfix bor_b "><a
						href="javascript:AddHouseFour.toAddHouseName();"> 房源名称 <input
							type="text" placeholder="房屋名字+商圈+房东特色+房源特色" id="houseName"
							value="${houseBaseDetailVo.houseName }" readonly="readonly">
							<span class="icon_r icon"></span>
					</a></li>

					<li class="clearfix bor_b ">
						<a href="javascript:;"
						onclick="submitHouse()"> 房源户型 <input type="text"
							placeholder="请填写房源户型" id="houseStyle"
							value="${houseBaseDetailVo.roomNum}室${houseBaseDetailVo.hallNum}厅${houseBaseDetailVo.kitchenNum}厨${houseBaseDetailVo.toiletNum}卫${houseBaseDetailVo.balconyNum}阳台"
							readonly="readonly"> <span class="icon_r icon"></span>
					</a>

					</li>
					<li class="clearfix bor_b ">房源面积 <c:choose>
							<c:when
								test="${not empty houseBaseDetailVo.houseArea && houseBaseDetailVo.houseArea>0 }">
								<input type="tel" placeholder="请填写整套房源面积" id="houseArea"
									value="${houseBaseDetailVo.houseArea }" maxlength="6">
							</c:when>
							<c:otherwise>
								<input type="tel" placeholder="请填写整套房源面积" id="houseArea"
									value="" maxlength="6">
							</c:otherwise>
						</c:choose> <span class="icon_r icon_noBg">㎡</span>

					</li>

					<li class="clearfix bor_b ">
					<a
						href="javascript:AddHouseFour.toSupporting();"> 配套设施 <input
							type="text" placeholder="请选择配套设施" id="supportList"
							name="supportList" value="" readonly="readonly"> <span
							class="icon_r icon"></span>
					</a>

					</li>
					<li class="clearfix bor_b ">出租期限 
						<input type="text"
						placeholder="请选择出租期限" id="rentTime" name="rentTime"
						value="${houseBaseDetailVo.tillDateStr }" pattern='yyyy-MM-dd' readonly="readonly" />
						<input id="rentDate" type="date"
						onchange="setValToInputDate(this,'rentTime');compareDate(this,'rentTime');"
						mins="${newDate}" max="2037-12-30"> 
						<span class="icon_r icon"></span>

					</li>
					<c:if test="${rentWay == 0 }">
						<li class="clearfix bor_b ">入住人数限制 <c:choose>
								<c:when
									test="${not empty houseBaseDetailVo.checkInLimit && houseBaseDetailVo.checkInLimit>0 }">
									<input type="text" placeholder="请选择入住人数限制" id="person"
										value="${houseBaseDetailVo.checkInLimit }人"
										readonly="readonly">
								</c:when>
								<c:when test="${not empty houseBaseDetailVo.checkInLimit && houseBaseDetailVo.checkInLimit == 0 }">
									<input type="text" placeholder="请选择入住人数限制" id="person" value="不限制" readonly="readonly">
								</c:when>
								<c:otherwise>
									<input type="text" placeholder="请选择入住人数限制" id="person" value=""
										readonly="readonly">
								</c:otherwise>
							</c:choose> 
							<select onchange="setValToInput(this,'person')" id="personList" name="personList">
								<c:forEach items="${limitList }" var="enumVo">
									<option value="${enumVo.key }"
										${houseBaseDetailVo.checkInLimit==enumVo.key ?'selected':''}>${enumVo.text }</option>
								</c:forEach>
							</select>
							<span class="icon_r icon"></span>

						</li>
					</c:if>
					<c:if test="${rentWay == 1 }">
						<!-- 是否与房东同住 -->
						<li class="clearfix bor_b ">与房东同住 <c:choose>
								<c:when
									test="${not empty houseBaseDetailVo.isTogetherLandlord && houseBaseDetailVo.isTogetherLandlord == 0 }">
									<input type="text" placeholder="请选择是否与房东同住"
										id="isTogetherLandlord" value="否" readonly="readonly">
								</c:when>
								<c:when
									test="${not empty houseBaseDetailVo.isTogetherLandlord && houseBaseDetailVo.isTogetherLandlord == 1 }">
									<input type="text" placeholder="请选择是否与房东同住"
										id="isTogetherLandlord" value="是" readonly="readonly">
								</c:when>
								<c:otherwise>
									<input type="text" placeholder="请选择是否与房东同住"
										id="isTogetherLandlord" value="" readonly="readonly">
								</c:otherwise>
							</c:choose> <select onchange="setValToInput(this,'isTogetherLandlord')"
							id="isTogetherLandlordList" name="isTogetherLandlordList">
								<option value="1">是</option>
								<option value="0" selected="selected">否</option>
						</select> <span class="icon_r icon"></span>

						</li>
					</c:if>
					<c:if test="${rentWay == 0 }">
						<li class="clearfix bor_b ">价格 
						  <c:if test="${houseBaseDetailVo.leasePrice >0 }">
						    <input type="tel" placeholder="请填写整套房屋价格" id="housePrice" value="${houseBaseDetailVo.leasePrice }" maxlength="6"/> 
						  </c:if>
						   <c:if test="${houseBaseDetailVo.leasePrice <=0 }">
						    <input type="tel" placeholder="请填写整套房屋价格" id="housePrice" value="" maxlength="6"/> 
						  </c:if>
						   <span class="icon_r icon_noBg">元</span>
						</li>
					</c:if>
					<li class="clearfix">
					<a href="javascript:AddHouseFour.toEnvironmen();"> 房源描述 <input
							type="text" placeholder="请描述房屋的基础信息，房屋内的配套设施与特色，以及能够充分展示出您的美屋的特征，例如：特色区域-花园，特色结构-loft，装修风格-北欧风等。文字优美，传递意境和场景感，中英双语为佳。50-1000字。" 
							id="houseMessage"
							value="${houseBaseDetailVo.houseDesc }" readonly="readonly">
							<span class="icon_r icon"></span>
					</a></li>

				</ul>
				<div class="title_classify pl20 ">
					<span onclick="AddHouseFour.clecked(this)"><span
						class="icon_check icon icon_l active"></span><span>同意</span></span><a
						href="${staticResourceUrl}/html/mapp_order_protocol.html"
						style="color: #4A90E2;font-size:0.7rem;line-height:2.2rem;">《服务协议》</a>
				</div>


			</div>
			<!--/main-->
		</div>
		<div class="box box_bottom">
			<input type="button" id="sentHouse" value="发布房源" class="org_btn">
		</div>
	</form>
	<!-- 添加房源名称 -->
	<form accept="" method="" style="display: none;"
		id="toAddHouseNameForm" onsubmit="return false;">
		<div class="main myCenterListNoneA">
			<header class="header">
			 <a href="javascript:AddHouseFour.refresPage();"  class="goback"></a>
			<h1>房源名称</h1>
			</header>
			<ul class="myCenterList">

				<li class="clearfix"><input type="text" id="addhouseName"
					class="ipt_lg" value="${houseBaseDetailVo.houseName }"
					placeholder="房屋名字+商圈+房东特色+房源特色" maxlength="30"></li>

			</ul>
		</div>
		<!--/main-->
		<div class="boxP075 mt85 mb85">
			<input type="button" id="submitBtn" value="完成"
				class="disabled_btn btn_radius">
		</div>
	</form>
	<input type="hidden" name="houseBaseFid" id="houseBaseFid"
		value="${houseBaseFid }" />
	<input type="hidden" name="rentWay" id="rentWay" value="${rentWay }" />
	<input type="hidden" name="rentWay" id="support" value="${support }" />
	<!-- 配套设施 -->
	<form accept="" method="" id="addSupporting" style="display: none;"
		onsubmit="return false;">
		<div class="bodys">
			<div class="main myCenterListNoneA " id="supportingList">
				<header class="header"> 
				<a href="javascript:AddHouseFour.refresPage();"class="goback" ></a>
				<h1>配套设施</h1>
				</header>
				<div class="title_classify">家电</div>
			</div>
			<!--/main-->
		</div>
		<div class="box box_bottom">
			<input type="button" id="supportingListBut" value="完成"
				class="org_btn">
		</div>
	</form>

	<!-- 周边情况 -->

	<form class="checkFormChild" id="peripheryEnvironmen"
		style="display: none;" onsubmit="return false;">
		<div class="main myCenterListNoneA">
			<header class="header"> 
			<a href="javascript:AddHouseFour.refresPage();"  class="goback" ></a>
			<h1>房源描述</h1>
			</header>
			<div class="area_box">
				<textarea id="arround" class="area_txt" maxlength="1000"
					placeholder="请描述房屋的基础信息，房屋内的配套设施与特色，以及能够充分展示出您的美屋的特征，例如：特色区域-花园，特色结构-loft，装修风格-北欧风等。文字优美，传递意境和场景感，中英双语为佳。50-1000字。">${houseBaseDetailVo.houseDesc }</textarea>
			</div>
		</div>
		<!--/main-->
		<div class="boxP075 mt85 mb85">
			<input type="button" id="submitBtnEnvironmen" value="确认"
				class="disabled_btn btn_radius" onclick="AddHouseFour.submitChose()" />
		</div>
	</form>

	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common/commonUtils.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/house/addHouseFour.js${VERSION}002"></script>
	<script type="text/javascript">
		function clecked(e) {
			if ($(e).find(".icon_check").hasClass("active")) {
				$(e).find(".icon_check").removeClass("active");
				$("#sentHouse").removeClass("org_btn").addClass("disabled_btn");
			} else {
				$(e).find(".icon_check").addClass("active");
				$("#sentHouse").addClass("org_btn").removeClass("disabled_btn");

			}
		}
		function clickLi(obj) {
			if ($(obj).find(".icon_check").hasClass("active")) {
				$(obj).find(".icon_check").removeClass("active");
			} else {
				$(obj).find(".icon_check").addClass("active");
			}
		}
		/*只能输入数字*/

		function keepFloat(obj, float, len) {
			//	debugger
			//	验证是否有字母
			var reg1 = new RegExp("[a-zA-Z]");
			//验证是否有多个.
			var reg = /^\\d+.\\d{2}/;
			//	特殊字符 
			var reg3 = /[`~!@#$%^&*()_+<>?:"{},\/;'[\]]/im;
			//验证是否有汉字
			var reg4 = /[^\u0000-\u00FF]/;
			var v = $.trim($(obj).val());

			if (reg.test(v)) {
				v = v.replace(/(^\s*)|(\s*$)/g, '')
				$(obj).val(v);
			}
			if (reg1.test(v)) {
				v = v.replace(/[a-zA-Z]/g, '')
				$(obj).val(v);
				return false;
			}
			if (reg3.test(v)) {
				v = v.replace(/[`~!@#$%^&*()_+<>?:"{},\/;'[\]]/g, '')
				$(obj).val(v);
				return false;
			}
			if (reg4.test(v)) {
				v = v.replace(/[^\u0000-\u00FF]/g, '')
				$(obj).val(v);
				return false;
			}
			if (float == 0) {
				//		保留为整数
				if (v.indexOf(".") != -1) {
					v = v.split(".")[0];
					$(obj).val(v);
					return false;
				}
			}
			if (len !== undefined) {
				if (v.indexOf(".") != -1) {
					var str = v.replace(".", "");
					if (str.length > len) {
						$(obj).val(v.slice(0, len));
						return false;
					}
				} else {
					if (v.length > len) {
						$(obj).val(v.slice(0, len));
						return false;
					}
				}
			}
			if (v.indexOf(".") != -1) {
				var str = v.split(".");
				$(obj).val(str[0] + "." + str[1].slice(0, float));
			}
			return true;
		}

		function compareDate(obj, id) {
			var beginDate = $(obj).attr('mins');
			var endDate = $(obj).val();
			var maxDate = $(obj).attr("max");
			var d1 = new Date(beginDate.replace(/\-/g, "\/"));
			var d2 = new Date(endDate.replace(/\-/g, "\/"));
			var max = new Date(maxDate.replace(/\-/g, "\/"));

			if (beginDate != "" && endDate != "" && d1 >= d2) {
				showShadedowTips("只能选择今天以后的日期！", 1);
				$(obj).val(beginDate);
				$('#' + id).val(beginDate);
				return false;
			}
			
			if(endDate != "" && d2 >= max ){
				showShadedowTips("最大时间为2037年！", 1);
				$(obj).val(maxDate);
				$('#' + id).val(maxDate);
				return false;
			}
		}
		$("#personList").change(function(){
			console.log($("#personList option:selected").val());
		});

		//跳转到户型设置提交保存房源信息
		function submitHouse() {
			window.location.href='${basePath }roomMgt/${loginUnauth }/houseApartment?houseBaseFid=${houseBaseFid}';

			var houseArea = $("#houseArea").val();//房源面积
			var rentTime = $("#rentTime").val();//出租期限
			var person = $("#personList option:selected").val();//入住人数限制
			var housePrice = $("#housePrice").val();//出租价格
			var isTogetherLandlord = $("#isTogetherLandlordList").val();
			var rentWay = $("#rentWay").val();

			if (houseArea == undefined || houseArea == null) {
				houseArea = "";
			}
			if (rentTime == undefined || rentTime == null) {
				rentTime = "";
			}
			if (person == undefined || person == null) {
				person = 0;
			}
			var postParam = {};
			if (rentWay == 0) {
				if (housePrice == "" || housePrice == null
						|| housePrice == undefined) {
					housePrice = 0;
				}
				housePrice = Math.round(parseFloat(housePrice) * 100);
				postParam = {
					houseArea : houseArea,
					isIssue : 0,
					houseBaseFid : $("#houseBaseFid").val(),
					rentWay : rentWay,
					tillDateStr : rentTime,
					checkInLimit : person,
					leasePrice : housePrice
				}
			}
			if (rentWay == 1) {
				var isTogetherLandlord = $("#isTogetherLandlordList").val();
				if (isTogetherLandlord == null || isTogetherLandlord == ""
						|| isTogetherLandlord == undefined) {
					showShadedowTips("请选择是否与房东同住", 1);
					return false;
				}
				postParam = {
					houseArea : houseArea,
					isIssue : 0,
					houseBaseFid : $("#houseBaseFid").val(),
					rentWay : rentWay,
					tillDateStr : rentTime,
					checkInLimit : person,
					leasePrice : housePrice
				}

			}

			$.ajax({
				url : "${basePath }houseInput/${loginUnauth }/issueHouse",
				data : postParam,
				dataType : "json",
				type : "post",
				async : true,
				success : function(result) {
				},
				error : function(result) {
					showShadedowTips("未知错误", 1);
				}
			});
		}

		// forAndroid
		function goMyHouse() {
			window.location.href = '${basePath}houseMgt/${loginUnauth}/myHouses';
		}
	</script>

</body>
</html>
