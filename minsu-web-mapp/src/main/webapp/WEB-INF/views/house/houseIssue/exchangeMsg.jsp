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
	<title>交易信息</title>
	<link href="${staticResourceUrl}/css/mui.picker.min.css${VERSION}002" rel="stylesheet" />
	<link rel="stylesheet" type="text/css"
	href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
	
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/css_830.css${VERSION}001">
</head>
<body>
	<form id="form" >
		<header class="header">
		 <!-- <a href="javascript:history.go(-1);" id="goback" class="goback"></a> -->
		 <a href="${basePath }houseIssue/${loginUnauth }/showOptionalInfo?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}" id="goback" class="goback"></a>
			<h1>交易信息</h1>
		</header>
		<input name="houseBaseFid" value="${houseBaseFid }" type="hidden"/>
		<input name="rentWay" value="${rentWay }" type="hidden"/>
		<input name="houseRoomFid" value="${houseRoomFid }" type="hidden"/>
		<div class="bodys">
			<div class="main">
				<ul id="houseList" class="myCenterList">
					<li class="c_ipt_li bor_b "> 
						<a href="javascript:void(0);"> 
							<span class="c_span">预订类型</span>
							<input class="c_ipt j_s_ipt" type="text" id="orderType" value="申请预订" readonly="readonly">
							<select onchange="setValToInput(this,'orderType')" name="orderType">
								<c:forEach items="${orderTypeList }" var="obj">
	                        	<option value="${obj.key }" <c:if test="${obj.key == exchange.orderType }">selected</c:if>>${obj.text }</option>
	                        </c:forEach>
							</select>
							<span class="icon_r icon"></span>
						</a>
					</li>
					<%-- <li class="c_ipt_li bor_b "> 
						<a href="javascript:void(0);"> 
							<span class="c_span">民宿类型</span>
							<input class="c_ipt j_s_ipt" type="text" id="homestayType" value="通用" readonly="readonly">
							<select onchange="setValToInput(this,'homestayType')" name="homestayType">
								<c:forEach items="${homeStayList }" var="obj">
		                        	<option value="${obj.key }" <c:if test="${obj.key == exchange.homestayType }">selected</c:if>>${obj.text }</option>
		                        </c:forEach>
							</select>
							<span class="icon_r icon"></span>
						</a>
					</li> --%>
					<li id="rulerLi" class="c_ipt_li bor_b "> 
						<a href="javascript:void(0);"> 
							<span id="rulerSpan" class="c_span">押金</span>
							<input type="hidden" class="ipts" id="dicCode" name="houseConfList[0].dicCode"  value="${houseConfMsg.dicCode }">
							<input type="hidden" class="ipts" id="dicVal" name="houseConfList[0].dicVal" value="${houseConfMsg.dicVal }">
							<%-- <c:forEach items="${depositListObj }" var="obj">
								<c:if test="${obj.key == exchange.depositRulesCode }">
								 <c:forEach items="${obj.subEnumVals }" var="objChild">
								    	<c:if test="${objChild.key == houseConfMsg.dicVal }">
								    	  <input class="c_ipt" type="text" placeholder="请选择押金规则" id="depositRulesCode" name="depositRulesCode" value="${obj.text } ${objChild.text } " readonly="readonly">
								    	</c:if>
								 </c:forEach>
										
								</c:if>
		                    </c:forEach>
		                    <c:if test="${empty houseConfMsg }">
								   <input class="c_ipt" type="text" placeholder="请选择押金规则" id="depositRulesCode" name="depositRulesCode" value="${obj.text } ${objChild.text } " readonly="readonly">
		                    </c:if> --%>
		                    <input class="c_ipt"   maxlength="5" type="tel" placeholder="请填写押金" id="depositRulesCode" name="depositRulesCode" value="${fn:split(houseConfMsg.dicVal, '.')[0] }元" >
		                    
							<!-- <span class="icon_r icon"></span> -->
							<input id="depositRulesCodeDb" name="depositRulesCode" value="${exchange.depositRulesCode }" type="hidden"/>
							<input name="houseConfList[0].fid" value="${houseConfMsg.fid }" type="hidden"/>
						</a>
					</li>
					<li <c:if test="${empty exchange.checkOutRulesCode  }">class="c_ipt_li c_ipt_li_none"</c:if><c:if test="${!empty exchange.checkOutRulesCode  }">class="c_ipt_li "</c:if>>
						<a href="javascript:;" onclick="toCheckOutRules();">
						<c:if test="${!empty exchange.checkOutRulesCode  }">
							<span class="c_span">退房规则</span>
						</c:if>	
							<c:forEach items="${checkOutList }" var="obj">
								<c:if test="${obj.key == exchange.checkOutRulesCode }">
									<input  class="c_ipt" type="text" placeholder="请选择退房规则" id="checkOutRulesCode" name="checkOutRulesCode" value="${obj.text }" readonly="readonly"> 
								</c:if>
		                    </c:forEach>
								<c:if test="${empty exchange.checkOutRulesCode  }">
									<input  class="c_ipt" type="text" placeholder="请选择退房规则" id="checkOutRulesCode" name="checkOutRulesCode" value="" readonly="readonly"> 
								</c:if>
		                    <input  name="checkOutRulesCode" value="${exchange.checkOutRulesCode }" type="hidden"/>
							<span class="icon_r icon"></span>
						</a>

					</li>
				</ul>
				<!--/main-->
			</div>
			<div class="boxP080">
				<input type="button" id="submitBtn" value="确认" class="org_btn  btn_radius">
			</div>
			</div>
		</form>
		<!-- 添加房源名称 -->

		<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js?v=js.version.20160815001"
		type="text/javascript"></script>
		<script type="text/javascript"
		src="${staticResourceUrl}/js/layer/layer.js${VERSION}003"></script>
		<script src="${staticResourceUrl}/js/common2.js${VERSION}004"></script>
		<script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}001"></script>
		<script type="text/javascript" src="${staticResourceUrl}/js/mui.min.js${VERSION}002"></script>
		<script type="text/javascript" src="${staticResourceUrl}/js/mui.picker.min.js${VERSION}005"></script>
		<script type="text/javascript" src="${staticResourceUrl}/js/mui.poppicker.js${VERSION}006"></script>
		<script type="text/javascript" src="${staticResourceUrl}/js/common_830_2.js${VERSION}001"></script>
		<script type="text/javascript">
		var $$ = jQuery.noConflict();
		$$(function(){
				document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			}) 
		</script>
		<script>
		//解析遍历此json数据
		/* var depositJson = ${depositList };
		console.log(depositJson);
			(function($, doc) {
				$.init();
				$.ready(function() {
					var rulerData = [{
						value:depositJson[0].key,
						text:depositJson[0].text,
						children:[{
							value:depositJson[0].subEnumVals[0].key,
							text:depositJson[0].subEnumVals[0].text
						},{
							value:depositJson[0].subEnumVals[1].key,
							text:depositJson[0].subEnumVals[1].text
						}]
					},
					{
						value:depositJson[1].key,
						text:depositJson[1].text,
						children:[{
							value:depositJson[1].subEnumVals[0].key,
							text:depositJson[1].subEnumVals[0].text
						},{
							value:depositJson[1].subEnumVals[1].key,
							text:depositJson[1].subEnumVals[1].text
						},{
							value:depositJson[1].subEnumVals[2].key,
							text:depositJson[1].subEnumVals[2].text
						}]
					}];
					//级联示例
					var rulerPicker = new $.PopPicker({
						layer: 2
					});
					rulerPicker.setData(rulerData);
					var showRulerPickerButton = doc.getElementById('depositRulesCode');
					if(showRulerPickerButton.value != ''){
						for(var i = 0 ; i < rulerData.length ; i ++){
							var v1 = showRulerPickerButton.value.split(' ')[0];
							var v2 = showRulerPickerButton.value.split(' ')[1];
							var index1;
							var index2;
							if(rulerData[i].text == v1){
								index1 = i;
							}
							for(var j = 0 ; j < rulerData[i].children.length ; j ++){
								if(rulerData[i].children[j].text == v2){
									index2 = j;
								}
							}
							rulerPicker.pickers[0].setSelectedIndex(index1);
							rulerPicker.pickers[0].setSelectedValue(v1);
							rulerPicker.pickers[1].setSelectedIndex(index2);
							rulerPicker.pickers[1].setSelectedValue(v2);
						}
					}
					var dicCode = doc.getElementById('dicCode');
					var dicVal = doc.getElementById('dicVal');
					showRulerPickerButton.addEventListener('tap', function(event) {
						rulerPicker.show(function(items) {
							showRulerPickerButton.value = items[0].text + " " + items[1].text;
							dicCode.value = items[0].value;
							//console.log(dicCode.value);
							dicVal.value = items[1].value;
							//console.log(dicVal.value);
							$$("#depositRulesCodeDb").val(dicCode.value);
							$$("#dicVal").val(dicVal.value);
							$$("#dicCode").val(dicCode.value);
							$$("#rulerLi").removeClass("c_ipt_li_none");
						});
					}, false);
				});

			})(mui, document);
			 $("#submitBtn").click(function(){
				var _price = $("#cleanPrice").val();
				_price = _price.replace("元","");
				if(_price > 100){
					mui.confirm('您的清洁费太高了，最高可设置为100元！', '', ['关闭'], function(e) {
											});
				}
				
			}) */
		</script>
<!-- fenjie -->
	<script type="text/javascript">
		$$(function(){
			init();
		    
			loadVal();
			
			$$("#depositRulesCode").blur(function(){
				var depositFees = $$("#depositRulesCode").val();
				depositFees = depositFees.replace("元","");
				$$("#depositRulesCode").val(depositFees+"元");
			});
		})
		
		function init(){
		
			if(valid()){
				$$("#submitBtn").removeClass("disabled_btn").addClass("org_btn");
			}else{
				$$("#submitBtn").addClass("disabled_btn").removeClass("org_btn");
			}
		}
		
		function valid(){
			return $$.trim($$("select[name='orderType']").val()).length != 0 && $$.trim($$("#depositRulesCode").val()).length != 0; 
			/* return $$.trim($$("select[name='orderType']").val()).length != 0 && $$.trim($$("select[name='homestayType']").val()).length != 0 
					&& $$.trim($$("#depositRulesCode").val()).length != 0;  */
		}
		
		$$("select").keyup(function(){
			init();
		});
		
		function loadVal(){
			 $$("#orderType").val($$("select[name='orderType']").find("option:selected").text());
			 /* $$("#homestayType").val($$("select[name='homestayType']").find("option:selected").text()); */
			 /* $$("#dicCode").val($$("input[name='houseConfList[0].dicCode']").text());
			 $$("#dicVal").val($$("input[name='houseConfList[0].dicVal']").text()); */
		}
	
		function toCheckOutRules(){
			window.location.href='${basePath }houseIssue/${loginUnauth }/toCheckOutRules?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&checkOutRulesCode=${exchange.checkOutRulesCode }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}';
			updateExchangeMsg(false);
		}
		
		$$("#submitBtn").click(function(){
			if($$(this).hasClass("disabled_btn")){
				return false;
			}
			$$(this).addClass("disabled_btn").removeClass("org_btn");
			updateExchangeMsg(true);
			$$(this).removeClass("disabled_btn").addClass("org_btn");
		});
		
		function updateExchangeMsg(isShow){
			if(  $$.trim($$("#checkOutRulesCode").val()).length == 0 && isShow == true){
				showShadedowTips("请填写退房规则",1);
				return false;
			}
			  var regexp = new RegExp("^\[1-9][0-9]*$");
			
			//押金校验
			var depositMin = parseInt("${depositMin}");
			var depositMax = parseInt("${depositMax}");
			
			var curDeposit = $$("#depositRulesCode").val();
			curDeposit = curDeposit.replace("元","");
			curDeposit = curDeposit.replace(",","");
			
			if(curDeposit==undefined||curDeposit==null||curDeposit==""){
				showShadedowTips("请输入押金",1);
				return false;
			}
		
			if(curDeposit<depositMin||curDeposit>depositMax){
				showShadedowTips("押金范围在"+depositMin+"元到"+depositMax+"元",1);
				return false;
			}
			
			if(curDeposit!="0"){
			    var isPositiveInteger = regexp.test(curDeposit);
	            if(!isPositiveInteger){
	            	showShadedowTips("请填写正确押金", 1);
		            return false;
	            } 
			}
		   
			
			$$("#dicVal").val(curDeposit);
	    	$$.ajax({
				url:"${basePath }/houseIssue/${loginUnauth }/updateExchangeMsg",
				data:toJson($$("#form").serializeArray()),
				dataType:"json",
				type:"post",
				async: false,
				success:function(result) {
					if(!isShow){
						return;
					}
					if(result.code === 0){
						window.location.href='${basePath }houseIssue/${loginUnauth }/showOptionalInfo?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}';
					}else{
						showShadedowTips(result.msg,1);
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
			$$.each(array, function(i, n){
				var key = n.name;
				var value = n.value;
				if($$.trim(value).length != 0){
					json[key] = value;
				}
			});
			return json;
		}
		
		/* var temp_html = "",
			oDicCode = $$("select[name='houseConfList[0].dicCode']"),
			oDicVal = $$("select[name='houseConfList[0].dicVal']"),
			depositJson = ${depositList }; */
		
		/* //初始化配置项code
	    var dicCode = function(){
	        $$.each(depositJson,function(i,deposit){
	            temp_html+="<option value='" + deposit.key + "'>" + deposit.text + "</option>";
	        });
	        oDicCode.html(temp_html);
	        dicValue();
	    };
	    
	    //初始化配置项val
	    var dicValue = function(){
	    	temp_html = "";
	    	$$.each(oDicCode,function(i,obj){
				var n = oDicCode.get(i).selectedIndex;
				if (typeof (depositJson[n]) != "undefined" 
						&& typeof (depositJson[n].subEnumVals[0]) != "undefined") {
					$$.each(depositJson[n].subEnumVals, function(i, deposit) {
						temp_html += "<option value='" + deposit.key + "'>" + deposit.text + "</option>";
					});
				}
				$$(oDicVal[i]).html(temp_html);
		        $$("#dicVal").val(oDicVal.find("option:selected").text());
	    	});
	    };
	    
	    //code级联val
	    oDicCode.change(function(){
	    	//$$("input[name='depositRulesCode']").val($$(this).find("option:selected").val());
	       // $$("#dicCode").val($$(this).find("option:selected").text());
	    	dicValue();
	    });
	    
	    oDicVal.change(function(){
	        $$("#dicVal").val($$(this).find("option:selected").text());
	    });
	    
	    dicCode(); */
	    
	    // 押金code默认值
	    //var depositRulesCode = '${exchange.depositRulesCode}';
	    // 押金value默认值
	    //var depositRulesVal = '${houseConfMsg.dicVal}';
	    // 设置押金code默认值
	    /* if(depositRulesCode != undefined && $$.trim(depositRulesCode).length != 0) {
	    	oDicCode.val(depositRulesCode);
	    	$$("#dicCode").val(oDicCode.find("option:selected").text());
	    	dicValue();
	    }else{
	    	//$$("input[name='depositRulesCode']").val(oDicCode.find("option:selected").val());
	    } */
	    
	    /* // 设置押金value默认值
	    if(depositRulesVal != undefined && $$.trim(depositRulesVal).length != 0) {
	    	oDicVal.val(depositRulesVal);
	        $$("#dicVal").val(oDicVal.find("option:selected").text());
	    } */
	</script>
	
	
	</body>
	</html>

