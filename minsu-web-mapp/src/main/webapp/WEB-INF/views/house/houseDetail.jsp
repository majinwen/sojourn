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
	<script type="text/javascript" src="${staticResourceUrl}/js/header.js${VERSION}002"></script>
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="yes" name="apple-touch-fullscreen" />
	<title>发布房源</title>
	<link rel="stylesheet" type="text/css"
	href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/css_830.css${VERSION}003">
	<style type="text/css">
	#addHouseFourForm, #addSupporting {
		height: 100%;
	}
	#addHouseFourForm .bodys, #addSupporting .bodys {
		height: calc(100% - 2.45rem);
		overflow: auto;
		-webkit-overflow-scrolling: touch;
	}
	
	.title_classify {
	    line-height: 2.4rem;
	}
	
	.title_classify, .myCenterList li {
	    font-size: .8rem;
	    margin: 0 .75rem;
	    position: relative;
	    height: 2.4rem;
	}
	.area_box{position: relative;background: #fff;padding-bottom: 1.8rem;}
	.area_num{position: absolute;bottom: 0.6rem;right: 0.6rem;font-size: 0.7rem;color: #666;}
	
	
	</style>
</head>
<body>



	<form id="addHouseFourForm" onsubmit="return false;" >
		<header class="header"> <a href="javascript:void(0);" id="goback" class="goback"></a>
			<h1>房源信息(4/6)</h1>
			<%-- 房东指南 --%>
			<a href="https://zhuanti.ziroom.com/zhuanti/minsu/zhinan/fabuliucheng2.html" class="toZhinan"></a>
		</header>
		<div class="bodys">
			<div id="main" class="main">
				<div class="box">
				<ul id="houseList" class="myCenterList">
					<c:if test="${rentWay == 0 }">
							<li <c:if test="${empty houseBaseDetailVo.houseName  }">class="c_ipt_li c_ipt_li_none bor_b"</c:if>
								<c:if test="${!empty houseBaseDetailVo.houseName  }">class="c_ipt_li  bor_b"</c:if> > 
								<a href="javascript:void(0);"> 
									<!-- TODO： 保存下一步时验证房间名称字数是否小于10-->
									<span class="c_span">房源名称</span>
									<textarea rows="1" class="c_ipt j_ipt" data-type="name" type="text" placeholder="请填写房源名称" id="houseName" maxlength="30">${houseBaseDetailVo.houseName }</textarea>
									<span class="icon_r icon icon_clear"></span>
								</a>
							</li>
							<li <c:if test="${!empty houseBaseDetailVo.houseArea && houseBaseDetailVo.houseArea>0  }">class="c_ipt_li bor_b"</c:if>
								<c:if test="${empty houseBaseDetailVo.houseArea || houseBaseDetailVo.houseArea<=0  }">class="c_ipt_li c_ipt_li_none bor_b"</c:if> > 
								<a href="javascript:void(0);"> 
									<span class="c_span">房源面积</span>
									
									<c:choose>
										<c:when	test="${not empty houseBaseDetailVo.houseArea && houseBaseDetailVo.houseArea>0 }"> 
											<input class="c_ipt j_ipt" data-type="area" type="tel" placeholder="请填写整套房源面积" id="houseArea" value="${houseBaseDetailVo.houseArea }m²" maxlength="6" >
										</c:when>
										<c:otherwise>
											<input class="c_ipt j_ipt" data-type="area" type="tel" placeholder="请填写整套房源面积" id="houseArea" value="" maxlength="6" >
										</c:otherwise>
									</c:choose>
									<span class="icon_r icon icon_clear"></span>
									
								</a>
							</li>
					</c:if>
					<c:if test="${rentWay == 1 }">
						<li class="c_ipt_li bor_b "> 
							<a href="javascript:void(0);"> 
								<span class="c_span">是否与房东同住</span>
								
								<c:set var="isTogetherLandlordShow" value="否"></c:set>
								<c:if test="${1==houseBaseDetailVo.isTogetherLandlord }"> 
									<c:set var="isTogetherLandlordShow" value="是"></c:set>
								</c:if>
								<input class="c_ipt j_s_ipt" type="text" placeholder="请选择是否与房东同住" id="liveTogether" value="${isTogetherLandlordShow }" readonly="readonly">
								<select onchange="setValToInput(this,'liveTogether')" id="liveTogetherList" name="liveTogetherList">
									<option value="0" <c:if test="${1!=houseBaseDetailVo.isTogetherLandlord }">selected="selected"</c:if> >否</option>
	
									<option value="1" <c:if test="${1==houseBaseDetailVo.isTogetherLandlord }">selected="selected"</c:if> >是</option>
	
								</select>
								<span class="icon_r icon"></span>
							</a>
						</li>
					</c:if>
					<c:if test="${rentWay == 0 }">
						<c:choose>
							<c:when test="${houseBaseDetailVo.leasePrice == 0}">
								<li class="c_ipt_li c_ipt_li_none bor_b ">
									<a href="javascript:void(0);" onclick="toPriceDetail()">
										<span class="c_span">价格</span>
										<input class="c_ipt" data-type="price" type="tel" placeholder="请设置价格"  name="" maxlength="5" value="" readonly="readonly" />
										<span class="icon_r icon"></span>
									</a>
								</li>
							</c:when>
							<c:otherwise>
								<li class="c_ipt_li bor_b ">
									<a href="javascript:void(0);" onclick="toPriceDetail()">
										<span class="c_span">价格</span>
										<input class="c_ipt"  type="tel" placeholder="请设置价格" id="" value="${priceSetting}" name="" maxlength="5" readonly="readonly"/>
										<span class="icon_r icon"></span>
									</a>
								</li>
							</c:otherwise>
						</c:choose>
						<li <c:if test="${houseBaseDetailVo.houseCleaningFees >=0  }">class="c_ipt_li bor_b"</c:if>
					<c:if test="${houseBaseDetailVo.houseCleaningFees <0  }">class="c_ipt_li c_ipt_li_none bor_b"</c:if> > 
							<a href="javascript:void(0);"> 
								<span class="c_span">清洁费</span>
								
								 <c:if test="${houseBaseDetailVo.houseCleaningFees >=0 }">
								    <input class="c_ipt j_ipt" data-type="price" type="tel" placeholder="请填写清洁费(0元表示不收取)" id="cleaningFees" maxlength="5" value="${cleaningFees }元"/> 
								  </c:if>
								   <c:if test="${houseBaseDetailVo.houseCleaningFees <0 }">
								    <input class="c_ipt j_ipt" data-type="price" type="tel" placeholder="请填写清洁费(0元表示不收取)" id="cleaningFees" maxlength="5" value=""/> 
								  </c:if>
								
								<span class="icon_r icon icon_clear"></span>
								
							</a>
						</li>
						
					</c:if>
					<li <c:if test="${empty houseBaseDetailVo.tillDateStr  }">class="c_ipt_li c_ipt_li_none bor_b"</c:if>
					<c:if test="${!empty houseBaseDetailVo.tillDateStr  }">class="c_ipt_li  bor_b"</c:if> > 
						<a href="javascript:void(0);">  
							<span class="c_span">可接受哪天之前的订单</span>
							<input class="c_ipt" type="text"
							placeholder="请选择可接受哪天之前的订单" id="rentTime" name="rentTime"
							value="${houseBaseDetailVo.tillDateStr }" pattern='yyyy-MM-dd' readonly="readonly"  />
							<input id="rentDate" type="date" onchange="setValToInputDate(this,'rentTime');" mins="${newDate}"  max="2037-12-30" > 
							<span class="icon_r icon"></span>
						</a>
					</li>
					<div class="c_message_div"><p class="c_message_p">建议选择1年后的日期</p></div>
					<c:if test="${rentWay == 0 }">
						<li class="c_ipt_li bor_b "> 
							<a href="javascript:void(0);"> 
								<span class="c_span">入住人数限制</span>
								
								<c:choose>
									<c:when
										test="${not empty houseBaseDetailVo.checkInLimit && houseBaseDetailVo.checkInLimit>0 }">
										<input class="c_ipt j_s_ipt" type="text" placeholder="请选择入住人数限制" id="person" value="${houseBaseDetailVo.checkInLimit }人" readonly="readonly">
									</c:when>
									<c:when test="${not empty houseBaseDetailVo.checkInLimit && houseBaseDetailVo.checkInLimit == 0 }">
										<input class="c_ipt j_s_ipt" type="text" placeholder="请选择入住人数限制" id="person" value="不限制" readonly="readonly">
									</c:when>
									<c:otherwise>
										<input class="c_ipt j_s_ipt" type="text" placeholder="请选择入住人数限制" id="person" value="" readonly="readonly">
									</c:otherwise>
								</c:choose> 
								
								<select onchange="setValToInput(this,'person')" id="personList" name="personList">
									<c:forEach items="${limitList }" var="enumVo">
										<option value="${enumVo.key }"
											${houseBaseDetailVo.checkInLimit==enumVo.key ?'selected':''}>${enumVo.text }</option>
									</c:forEach>
								</select>
								<span class="icon_r icon"></span>
							</a>
						</li>
					</c:if>
					<li class="c_ipt_li c_ipt_li_none bor_b ">
						<a href="javascript:AddHouseFour.toSupporting();">
							<span class="c_span">便利设施</span>
							<input  class="c_ipt" type="text" placeholder="请选择有哪些便利设施" id="supportList" name="supportList" value="" readonly="readonly"> 
							<span class="icon_r icon"></span>
						</a>

					</li>

					<li <c:if test="${empty houseBaseDetailVo.houseDesc }">class="c_ipt_li c_ipt_li_none bor_b"</c:if>
					<c:if test="${!empty houseBaseDetailVo.houseDesc }">class="c_ipt_li  bor_b"</c:if> >
						<a href="javascript:AddHouseFour.toEnvironmen();"> 
							<span class="c_span">房源描述</span>
							
							<c:if test="${!empty houseBaseDetailVo.houseDesc }"> 
								<input class="c_ipt" type="text" placeholder="请填写房源描述信息" id="houseMessageShow" value="已完成" readonly="readonly">
								<input class="c_ipt" type="hidden" placeholder="请填写房源描述信息" id="houseMessage" value="${houseBaseDetailVo.houseDesc }" readonly="readonly">
							</c:if>
							<c:if test="${empty houseBaseDetailVo.houseDesc }"> 
								<input class="c_ipt" type="hidden" placeholder="请填写房源描述信息" id="houseMessage" value="" readonly="readonly">
								<input class="c_ipt" type="text" placeholder="请填写房源描述信息" id="houseMessageShow" value="" readonly="readonly">
							</c:if>
							<!-- 
							<span id="houseDescCmplShow" class="${ houseDescCmplCss}"> <c:out value="${houseDescCmplShow }"></c:out> </span>
							 -->
							<span class="icon_r icon"></span>
						</a>
					</li>
					<li <c:if test="${empty houseBaseDetailVo.houseAroundDesc  }">class="c_ipt_li c_ipt_li_none bor_b"</c:if>
					<c:if test="${!empty houseBaseDetailVo.houseAroundDesc  }">class="c_ipt_li  bor_b"</c:if> >
						<a href="javascript:AddHouseFour.toArroundInfo();"> 
							<span class="c_span">周边情况</span> 
							<c:if test="${empty houseBaseDetailVo.houseAroundDesc || houseBaseDetailVo.houseAroundDesc==''  }"> 
								<input class="c_ipt" type="text" placeholder="请填写周边情况" id="arroundInfoShow" value="" readonly="readonly" >
								<input class="c_ipt" type="hidden" placeholder="请填写周边情况" id="arroundInfo" value="" readonly="readonly" >
							</c:if>
							<c:if test="${!empty houseBaseDetailVo.houseAroundDesc  }"> 
								<input class="c_ipt" type="text" placeholder="请填写周边情况" id="arroundInfoShow" value="已完成" readonly="readonly" >
								<input class="c_ipt" type="hidden" placeholder="请填写周边情况" id="arroundInfo" value="${houseBaseDetailVo.houseAroundDesc  }" readonly="readonly" >
							</c:if>
							<!-- 
							<span id="arroundInfoShow" class="${ arroundInfoCss }"> <c:out value="${arroundInfoShow }"></c:out> </span>
							 -->
							 <span class="icon_r icon"></span>
						</a>
					</li>

				</ul>
				</div>
				<!--/main-->
			</div>
		</div>
			<div class="box box_bottom">
				<!-- 
				<input type="button" id="sentHouse" value="下一步" class="org_btn">
				 -->
				<input id="nextBtn" type="button" value="下一步" class="org_btn">
			</div>
	</form>

 

	<input type="hidden" name="houseBaseFid" id="houseBaseFid"
		value="${houseBaseFid }" />
	<input type="hidden" name="rentWay" id="rentWay" value="${rentWay }" />
	<input type="hidden" name="support" id="support" value="${support }" />
	<input type="hidden" name="flag" id="flag" value="${flag }" />
	<input type="hidden" name="cleanPer" id="cleanPer" value="${cleanPer }" />
	<input type="hidden" name="priceLow" id="priceLow" value="${priceLow }">
	<input type="hidden" name="priceHigh" id="priceHigh" value="${priceHigh }">
	<input type="hidden" id="housePrice" value="${houseBaseDetailVo.leasePrice }">
	<input type="hidden" name="houseStatus" id="houseStatus" value="${houseBaseDetailVo.houseStatus }" />
	<!-- 配套设施 -->
	<form accept="" method="" id="addSupporting" style="display: none;"
		onsubmit="return false;">
		<div class="myCenterListNoneA " id="supportingList">
				 
			<!--/main-->
		</div>
		<div id="supportingbottomDiv" class="box box_bottom">
			<input type="button" id="supportingListBut" value="完成"
				class="org_btn">
		</div>
	</form>

	<!-- 房源描述 -->

	<form class="checkFormChild" id="peripheryEnvironmen"
		style="display: none;" onsubmit="return false;">
		<div class="main myCenterListNoneA">
			<header class="header"> 
			<a href="javascript:AddHouseFour.refresPage();"  class="goback" ></a>
			<h1>房源描述</h1>
			</header>
			<div class="area_box">
				<textarea id="arround" class="area_txt" maxlength="1000" style="overflow-y:auto"
					placeholder="请描述房屋的基础信息，房屋内的配套设施与特色，以及能够充分展示出您的美屋的特征，例如：特色区域-花园，特色结构-loft，装修风格-北欧风等。文字优美，传递意境和场景感，中英双语为佳。100-1000字。&#10;为保证您的房源呈现效果，请注意错别字、过多空行等格式问题。">${houseBaseDetailVo.houseDesc}</textarea>
					<span class="area_num"><i class="num">0</i>/1000</span>
			</div>
		</div>
		<!--/main-->
		<div class="boxP075 mt85 mb85">
			<input type="button" id="submitBtnEnvironmen" value="确认"
				class="btn_radius" onclick="AddHouseFour.submitChose()" />
		</div>
	</form>
	
		<!-- 周边情况 -->

	<form class="checkFormChild" id="arroundInfoForm"
		style="display: none;" onsubmit="return false;">
		<div class="main myCenterListNoneA">
			<header class="header"> 
			<a href="javascript:AddHouseFour.refresPage();"  class="goback" ></a>
			<h1>周边情况</h1>
			</header>
			<div class="area_box">
				<textarea id="arroundInfoArea" class="area_txt" maxlength="1000" style="overflow-y:auto"
					placeholder="请描述房子附近的景点，地标建筑的信息，周边吃喝玩乐生活配套设施，有哪些有趣好玩的推荐。有场景感和生活气息。中英文双语为佳，生活方式的推荐尤其佳。100-1000字。&#10;为保证您的房源呈现效果，请注意错别字、过多空行等格式问题。">${houseBaseDetailVo.houseAroundDesc}</textarea>
					<span class="area_num"><i class="num">0</i>/1000</span>
			</div>
		</div>
		<!--/main-->
		<div class="boxP075 mt85 mb85">
			<input type="button" id="submitBtnarroundInfo" value="确认"
				class="disabled_btn btn_radius" onclick="AddHouseFour.submitArroundInfo()" />
		</div>
	</form>

	
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script type="text/javascript" src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common/commonUtils.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}001"></script>
	<script type="text/javascript" src="${staticResourceUrl}/js/common_830.js${VERSION}003"></script>
	<script src="${staticResourceUrl}/js/house/addHouseFour.js${VERSION}007"></script>
	<script type="text/javascript">
		function toPriceDetail(){
			var houseBaseFid = '${houseBaseFid}';
			var houseRoomFid = '${houseRoomFid }';
			if(isNullOrBlank(houseBaseFid)){
				showShadedowTips("房源不存在",1);
				$("#nextBtn").removeAttr("disabled");
				return;
			}
			var url = "${basePath}houseIssue/${loginUnauth}/toPriceDetail?houseBaseFid=${houseBaseFid}&flag=0&houseRoomFid=${houseRoomFid }&rentWay=0";
			jumpPage(url);
		}
		
		function isNullOrBlank(obj){
			return obj == undefined || obj == null || $.trim(obj).length == 0;
		}
	
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
	
		// forAndroid
		function goMyHouse() {
			window.location.href = '${basePath}houseMgt/${loginUnauth}/myHouses';
		}
		</script>
		<script type="text/javascript">
	$(function(){

		var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });

		var conHeight = $(window).height()-$('.header').height()-$('.box_bottom').height();

		$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$('#main .box').css({'position':'absolute','width':'100%'});
		myScroll.refresh();
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		$("textarea").on('touchmove',function(e){
            e.stopPropagation();
        })
	})
	
	</script>
	<script type="text/javascript">
		//点击后退保存信息
		$("#goback").click(function(){
			var url = '${basePath}houseDeploy/${loginUnauth}/toLocationThree?houseBaseFid=${houseBaseFid}&rendWay=${rentWay}';
			jumpPage(url);
		})
		
		function jumpPage(url){
			var houseName = $("#houseName").val();//房源名称
			var rentTime = $("#rentTime").val();//出租期限
			var person = $("#personList option:selected").val();//入住人数限制
			var rentWay = '${rentWay}';
			var houseBaseFid = $("#houseBaseFid").val();
			if(rentWay ==  0){
				var houseArea = $("#houseArea").val();//房源面积
				if(houseArea.length > 2){
					houseArea = houseArea.replace("m²","");
				}
				var housePrice = $("#housePrice").val();//出租价格
				if(housePrice.length >0){
					housePrice = Math.round(parseFloat(housePrice)*100);
				}
				var cleaningFees = $("#cleaningFees").val();
				if(cleaningFees.length > 0){
					cleaningFees = cleaningFees.substring(0,cleaningFees.length-1);
					cleaningFees = Math.round(parseFloat(cleaningFees)*100);
				}
			}else if(rentWay == 1){
				var isTogetherLandlord = $("#liveTogetherList option:selected").val();
			}
			
			$.ajax({
			    type: "POST",
			    url: '${basePath}houseInput/${loginUnauth}/saveInfo',
			    dataType:"json",
			    data: {
					houseName:houseName,
					houseArea:houseArea,
					tillDateStr:rentTime,
					houseBaseFid:houseBaseFid,
					checkInLimit:person,
					leasePrice:housePrice,
					houseCleaningFees:cleaningFees,
					isTogetherLandlord:isTogetherLandlord,
					rentWay:rentWay
				},
			    success: function (result) {
			    	window.location.href = url;
			    },
			    error: function(result) {
		        }
		    });
		}
	
	</script>
		
	</body>
	</html>
