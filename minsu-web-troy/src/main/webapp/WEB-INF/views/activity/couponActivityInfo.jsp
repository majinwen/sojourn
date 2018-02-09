<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="${basePath}">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/jsTree/style.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
</head>
 <body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-sm-22">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
			           <form id="activityForm" action="#" class="form-horizontal" onsubmit="return false" method="post" >
			           		<div class="form-group">
			                   <div class="row">
	                               <div class="col-sm-1 control-label mtop">
			                   	       <label class="control-label mtop">活动码:</label>
	                               </div>

								   <div class="col-sm-2">
									   <input id="actSn" name="actSn" value="${activity.actSn }" disabled="disabled" type="text" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" onblur="value=value.replace(/[^\w\.\/]/ig,'')" class="form-control m-b"  required maxlength="6" readonly="readonly" />
								   </div>

	                               <label class="col-sm-2 control-label mtop">活动名称:</label>
		                           <div class="col-sm-2">
	                                  <input name="actName" value="${activity.actName }" type="text" disabled="disabled"  class="form-control m-b" required>
	                               </div>
	                           </div>
		                       <div class="hr-line-dashed"></div>
		                       <div id="actTypeRow" class="row actTypeRow">

								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">活动形式:</label>
								   </div>
		                           <div class="col-sm-2">
	                                   <select id="actTypeSelect"  disabled="disabled"  class="form-control m-b m-b" title="请选择活动形式"  name="actType" required>
	                                   		<option value="">请选择</option>
	                                   		<option value="1">优惠券抵现金</option>
										    <option value="2">折扣券</option>
	                                   		<option value="4">免天优惠券</option>
	                                   		<option value="5">优惠定额活动</option>
	                                   </select>
	                               </div>
	                              
	                               <div id="showDiv">
	                               
	                               </div>
	                             </div>
	                             <div id="newRow">
	                             	
	                             </div>
	                             <div id="addRow" style="display:none;" class="row">
		                           <div class="col-sm-4"></div> <button id="addBtn" class="btn btn-primary" type="button" onclick="addRow()">+</button></div>
	                           <div class="hr-line-dashed"></div>

								<div class="row">

									<div class="col-sm-1 control-label mtop">
										<label class="control-label mtop">活动组:</label>
									</div>

									<div class="col-sm-2">
										<select id="groupSnSelect"  disabled="disabled"  name="groupSn" class="form-control">
											<option value="">不限</option>
											<c:forEach items="${groupList}" var="group">
												<option value="${group.groupSn}">${group.groupName}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-1 control-label mtop">

									</div>
								</div>

								<div class="hr-line-dashed"></div>

	                           <div class="row">

								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">活动开始日期:</label>
								   </div>

		                           <div class="col-sm-2">
		                           	   <input id="actStartTime" name="sActStartTime"  disabled="disabled"  value="" class="laydate-icon form-control layer-date" required>
	                               </div>
		                      
		                           <label class="col-sm-2 control-label mtop">活动结束日期:</label>
		                           <div class="col-sm-2">
		                           	   <input id="actEndTime" name="sActEndTime" value=""  disabled="disabled"  class="laydate-icon form-control layer-date" required>
	                               </div>
	                           </div>

	                           <div class="hr-line-dashed"></div>
								<div class="row">
									<div class="col-sm-1 ">
										<div id="allCheckBox" class="checkbox i-checks">
											<label>
												<input id="allCityCheckBox" disabled="disabled"  name="allCityCode" type="checkbox" value="${all}" > <i></i>全部
											</label>
										</div>
									</div>
									<div id="cityCheckBox" class="col-sm-11" >
										<c:forEach items="${cityList }" var="obj" varStatus="status">
											<div class="col-sm-1 ">
											<div class="checkbox i-checks">
												<label>
													<input type="checkbox" disabled="disabled"  name="cityCodeArray" value="${obj.code }"> <i></i>${obj.name }
												</label>
											</div>
											</div>
										</c:forEach>
										<input type="hidden" name="check_city">
										<span class="help-block m-b-none"></span>
									</div>
								</div>

	                           <%--<div class="row">--%>
								   <%--<div class="col-sm-1 control-label mtop">--%>
									   <%--<label class="control-label mtop">发放优惠券数量:</label>--%>
								   <%--</div>--%>

		                           <%--<div class="col-sm-2">--%>
	                                   <%--<input name="couponNum" value="${activity.couponNum }" placeholder="友好提示:请输入100的倍数(特殊除外)" type="text" class="form-control m-b " >--%>
	                               <%--</div>--%>

	                               <%--<label class="col-sm-2 control-label mtop">活动城市:</label>--%>
		                           <%--<div class="col-sm-2">--%>
		                               <%--<div id="allCheckBox" class="checkbox i-checks">--%>
	                                        <%--<label>--%>
	                                            <%--<input id="allCityCheckBox" name="allCityCode" type="checkbox" value="0" > <i></i>全部--%>
	                                        <%--</label>--%>
	                                   <%--</div>--%>
	                                   <%--<div id="cityCheckBox">--%>
			                            <%--<c:forEach items="${cityList }" var="obj" varStatus="status">--%>
		                                  <%--<div class="checkbox i-checks">--%>
		                                     <%--<label>--%>
		                                          <%--<input type="checkbox" name="cityCodeArray" value="${obj.code }"> <i></i>${obj.name }--%>
		                                      <%--</label>--%>
		                                   <%--</div>--%>
		                                  <%--</c:forEach>--%>
		                                  <%--<input type="hidden" name="check_city">--%>
		                               <%--<span class="help-block m-b-none"></span>--%>
								       <%--</div>--%>
                               <%--</div>--%>
		                   </div>
		                   <div class="hr-line-dashed"></div>
			               <div class="form-group">
			                   <div class="row">


								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">优惠券名称:</label>
								   </div>

		                           <div class="col-sm-2">
	                                   <input name="couponName"  disabled="disabled"  value="${activity.couponName }" type="text" class="form-control m-b" required>
	                               </div>
								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">发放优惠券数量:</label>
								   </div>

								   <div class="col-sm-2">
									   <input name="couponNum" disabled="disabled"  value="${activity.couponNum }" placeholder="友好提示:请输入100的倍数(特殊除外)" type="text" class="form-control m-b " >
								   </div>
	                           </div>
	                           <div class="hr-line-dashed"></div>


							   <div class="row">
								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">是否限制领取次数:</label>
								   </div>
								   <div class="col-sm-2">
									   <select id="limitTime" class="form-control m-b m-b" disabled="disabled"  onchange="changTimes(this)"  title="是否限制领取次数" name="limitTime" required>
										   <option value="0">不限制</option>
										   <option value="1">限制</option>
									   </select>
								   </div>
								   <div class="col-sm-2" id="timeDiv" style="display: none">
									   <input name="times"  disabled="disabled"  value="${activity.times}" type="text" class="form-control m-b" required>
								   </div>
								   <div class="col-sm-1" id="timeDivShow" style="display: none">
									   <label class="control-label mtop">次</label>
								   </div>
							   </div>

							   <div class="hr-line-dashed"></div>
							   <div class="row">
								   <div class="col-sm-1 text-right">
									   <label class="control-label">是否限制房源:</label>
								   </div>
								   <div class="col-sm-2">
									   <select id="limitHouse" class="form-control m-b m-b" disabled  name="isLimitHouse" required>
										   <option value="0">不限制</option>
										   <option value="1">限制</option>
									   </select>
								   </div>
								   <div class="col-sm-6 limitHouseDiv" style="display: none">
									   <input name="limitHouseSns" id="limitHouseSns" disabled value="${houseSns}" placeholder="多个编号使用 ,(英文)隔开"  type="text" class="form-control" required>
								   </div>
							   </div>

							   <div class="hr-line-dashed"></div>
							   <div class="row">
								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">限制时间类型:</label>
								   </div>
								   <div class="col-sm-2">
									   <select id="couponTimeType" disabled="disabled" class="form-control m-b m-b" onchange="changTimeType(this)"   title="请选择优惠券限制时间类型" name="couponTimeType" required>
										   <option value="1" selected>固定时间</option>
										   <option value="2">有效天数</option>
									   </select>
								   </div>
								   <div id="couponTimeLastDiv"  style="display: none" >
									   <label class="col-sm-2 control-label mtop">有效天数（天）:</label>
									   <div class="col-sm-2">
										   <input name="couponTimeLast" disabled="disabled" id="couponTimeLast"  value="${activity.couponTimeLast}" type="text" class="form-control m-b" required>
									   </div>
								   </div>

								   <div id="couponTimeDiv">
									   <div class="col-sm-1 control-label mtop">
										   <label class="control-label mtop">优惠券使用开始日期:</label>
									   </div>
									   <div class="col-sm-2">
										   <input id="couponStartTime" disabled="disabled" name="sCouponStartTime" value="" class="laydate-icon form-control layer-date" required>
									   </div>
									   <label class="col-sm-2 control-label mtop">优惠券使用结束日期:</label>
									   <div class="col-sm-2">
										   <input id="couponEndTime" disabled="disabled" name="sCouponEndTime"  value="" class="laydate-icon form-control layer-date" required>
									   </div>
								   </div>
							   </div>


							   <div class="hr-line-dashed"></div>



                               <div class="row">
								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">订单入住时间限制:</label>
								   </div>
                                   <div class="col-sm-2">
	                                   <select id="isCheckTimeSelect" disabled="disabled"  class="form-control m-b m-b"  title="请选择是否限制入住时间" name="isCheckTime" required>
	                                   		<option value="">请选择</option>
	                                   		<option value="0">不限制</option>
	                                   		<option value="1">限制</option>
	                                   </select>
	                               </div>
	                               <div id="checkInLimitDiv" style="display: none" >
			                           <label class="col-sm-2 control-label mtop">入住日期:</label>
			                           <div class="col-sm-2">
			                                <input id="checkInTime"  disabled="disabled"  name="sCheckInTime" value="" class="laydate-icon form-control layer-date" required>
			                           </div>
			                           <label class="col-sm-2 control-label mtop">离开日期:</label>
			                           <div class="col-sm-2">
			                                <input id="checkOutTime" disabled="disabled"  name="sCheckOutTime" value="" class="laydate-icon form-control layer-date" required>
	                                   </div>
	                               </div> 
                               </div>
                               <div class="hr-line-dashed"></div>
								
								<div class="row">
									<div class="col-sm-1 text-right">
										<label class="control-label">优惠券数量报警配置:</label>
									</div>
									<div class="col-sm-2">
										<select id="isWarn" class="form-control m-b m-b" disabled="disabled"
											title="请选择优惠券数量报警配置" name="isWarn">
											<option value="0">不报警</option>
											<option value="1">报警</option>
										</select>
									</div>
								</div>
								<div id="warnPhones" class="row" style="display: none">
									<div class="col-sm-1 text-right">
								    	<label class="control-label">接受报警手机号:</label>
								    </div>
								   	<div class="col-sm-2">
								   		<%-- <label class="control-label">${warnPhone}</label> --%>
									   <input name="warnPhone"  disabled="disabled"  value="${warnPhone}" type="text" class="form-control m-b">
								   	</div> 
							   </div>
							</div>

                           </div>
                           </div>
                        </form>
			        </div>
                </div>
            </div>
        </div>
    </div>
	

    <!-- 全局js -->
    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>

    <!-- jsTree plugin javascript -->
    <script src="${staticResourceUrl}/js/plugins/jsTree/jstree.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
    <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
    <script src="${basePath}js/minsu/common/commonUtils.js${VERSION}008" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js"></script>
	<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>


	<script>

		/**校验活动码*/
		$("#saveAc").click(function(){
			$.ajax({
				beforeSend : function(){
					return $("#activityForm").valid();
				},
				url:"activity/updateActCoupon",
				dataType:"json",
				data: $("#activityForm").serialize(),
				type:"post",
				async: false,
				success:function(data) {
					if(data.code === 0){
						$.callBackParent("activity/activityList",true,callBack);
					}else{
						layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
					}
				},
				error:function(result){
					layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
				}
			});
		});



		var couponTimeTypeOper = function(value) {
			if(value == 1){
				$("#couponTimeLastDiv").hide();
				$("#couponTimeDiv").show();
			}else {
				$("#couponTimeLastDiv").show();
				$("#couponTimeDiv").hide();
			}
		}
		

        $(function (){
        	//初始化日期
			CommonUtils.datePickerFormat('actStartTime');
			CommonUtils.datePickerFormat('actEndTime');
			CommonUtils.datePickerFormat('couponStartTime');
			CommonUtils.datePickerFormat('couponEndTime');
			CommonUtils.datePickerFormat('checkInTime');
			CommonUtils.datePickerFormat('checkOutTime');
        	
       		$("#couponStartTime").val(CommonUtils.formatCSTDate('${activity.couponStartTime }'));
           	$("#couponEndTime").val(CommonUtils.formatCSTDate('${activity.couponEndTime }'));
           	$("#actStartTime").val(CommonUtils.formatCSTDate('${activity.actStartTime }'));
           	$("#actEndTime").val(CommonUtils.formatCSTDate('${activity.actEndTime }'));
           	$("#checkInTime").val(CommonUtils.formatCSTDate('${activity.checkInTime }'));
           	$("#checkOutTime").val(CommonUtils.formatCSTDate('${activity.checkOutTime }'));
           	
        	
        	//select设置默认选中 
        	CommonUtils.selectedDefault('actTypeSelect','${activity.actType}',activityTypeOper);
			CommonUtils.selectedDefault('limitTime','${activity.limitTime}','');
			CommonUtils.selectedDefault('groupSnSelect','${activity.groupSn}','');
			CommonUtils.selectedDefault('couponTimeType','${activity.couponTimeType}',couponTimeTypeOper);
			CommonUtils.selectedDefault('limitHouse','${activity.isLimitHouse}','');

			limitHouseShow();

			//限制房源选择切换
			$("#limitHouse").change(function(){
				limitHouseShow();
			});

			function limitHouseShow() {
				var val = $("#limitHouse").val();
				if(val == 1){
					$(".limitHouseDiv").show();
				}else{
					$(".limitHouseDiv").hide();
				}
			}

			var limitTime = '${activity.limitTime}';

			if(limitTime == 1){
				$("#timeDiv").show();
				$("#timeDivShow").show();
			}
			
			
        	$("#actTypeSelect").change(function(){
        		activityTypeOper($(this).val());
        	});
        	
        	//select设置默认选中 
        	CommonUtils.selectedDefault('isCheckTimeSelect','${activity.isCheckTime}',activityLimitOper);
        	
        	//isWarn设置默认选中 
        	CommonUtils.selectedDefault('isWarn','${activity.isWarn}',warnOper);
        	
        	$("#isCheckTimeSelect").change(function(){
        		activityLimitOper($(this).val());
        	});

        	/** 设置优惠券默认选中 */
        	initCheckBox();
        	
        	/**复选框点击事件*/
        	$("#allCheckBox").click(function(){
        		setCheckBoxSelected('allCityCheckBox','cityCheckBox','cityCodeArray');
        	});
        	

        	
        	$("#activityForm").validate({
        	    rules: {
        	    	"couponNum" : {
						required : true,
						digits : true,
						min:1
					},
					"actMax" : {
						required : true,
						digits : true,
						max:10000
					},
					"actLimit" : {
						required : true,
						digits : true,
						min:0
					},
					"actCut" : {
						required : true,
						digits : true,
						min:1
					},
					"ratio" : {
						required : true,
						digits : true,
						min:1
					}
        	
        	    },
        	    messages: {
        	    	"couponNum" : {required : "输入优惠券数量！", digits : "必须是整数！", min:"最小值1！"},
        	    	"actMax" : {required : "限制消费金额最大值！", digits : "必须是整数！", max:"最大值10000！"},
        	    	"actLimit" : {required : "输入满减限制！", digits : "必须是整数！", min:"最小值0！"},
        	    	"actCut" : {required : "请输入减免天数！", digits : "必须是整数！", min:"最小值0！"},
        	    	
        	    }
        	});
        	
        	
        });


		//限制次数
		function changTimes(value) {
			if(value.value == 0){
				$("#times").val(0);
				$("#timeDiv").hide();
				$("#timeDivShow").hide();
			}else {
				$("#times").val(1);
				$("#timeDiv").show();
				$("#timeDivShow").show();
			}
		}

        /** 初始化 checkBox*/
        var initCheckBox= function(){
        	var cityCodeArray = "${cityCodeStr}".split(",");
        	for(var cityCode in cityCodeArray){
				console.log(cityCode);
        		if(!!cityCodeArray[cityCode]){
					console.log($("input:checkbox[value="+cityCodeArray[cityCode]+"]"));
        			$("input:checkbox[value="+cityCodeArray[cityCode]+"]").prop('checked','true');
        		}
        	}
			if($('#allCityCheckBox').val() == 1){
//				setCheckBoxSelected('allCityCheckBox','cityCheckBox','cityCodeArray');

				$("#cityCheckBox").find("input[name='cityCodeArray']").attr("disabled","disabled");
				$("#cityCheckBox").find("input[name='cityCodeArray']").prop("checked",true);
			}
        }


		/** 设置checkBox使用情况*/
		var setCheckBoxSelectedDisabled = function(selectedCheckBox,setCheckBox,checkBoxName){
			if($('#'+selectedCheckBox).is(':checked')) {
				$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").attr("disabled","disabled");
			}else{
				$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").removeAttr("disabled");
			}
		};


		/** 设置checkBox使用情况*/
		var setCheckBoxSelected = function(selectedCheckBox,setCheckBox,checkBoxName){
			if($('#'+selectedCheckBox).is(':checked')) {
				$("#allCityCheckBox").val(1);
				$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").prop("checked",true);
				$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").attr("disabled","disabled");
			}else{
				$("#allCityCheckBox").val(0);
				$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").removeAttr("checked");
				$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").removeAttr("disabled");
			}
		};

		
        var activityLimitOper = function(type){
    		if(typeof(type) == undefined && typeof(type) != 'number'){
    			return ;
    		}
    		var html = '';
    		if(type == 0){
    			$("#checkInLimitDiv").hide();
    			$("#checkInTime").val(0);
    			$("#checkOutTime").val(0);
    			
    		}else if(type == 1){
    			$("#checkInTime").val(CommonUtils.formatCSTDate('${activity.checkInTime }'));
               	$("#checkOutTime").val(CommonUtils.formatCSTDate('${activity.checkOutTime }'));
    			$("#checkInLimitDiv").show();
    		}
    	 }
        
        var warnOper = function(type){
    		if(type == 1){
            	$("#warnPhones").show();
    		}
    	 }
        
        
        var activityTypeOper = function(type){
        	$("#addRow").hide();
        	$("#newRow").hide();
    		if(typeof(type) == ""||typeof(type) == undefined){
    			$("#showDiv").children().remove();
    			return ;
    		}
    		
    		var html = '';
    		if(type == 1){
    			html +='<label class="col-sm-2 control-label mtop">满:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actLimit" disabled value="${activity.actLimit }" type="text" class="form-control m-b" required>';
    			html +='</div>'
    			html +='<label class="col-sm-2 control-label mtop">减:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actCut"   disabled value="${activity.actCut }" type="text" class="form-control m-b" required>';
    			html +='</div>';
    		}else if(type == 2){
				html +=	'<label class="col-sm-1 control-label">限制消费金额最大值:</label>';
				html +='<div class="col-sm-1">';
				html +='<input name="actMax" value="${activity.actMax /100.0}" disabled type="text" class="form-control m-b" required>';
				html +='</div>';
				html +='<div class="col-sm-1">';
				html +='<input name="actCut" value="${activity.actCut }" disabled type="text" class="form-control m-b" required>';
				html +='</div>'
				html +=	'<label class="col-sm-1 control-label" disabled style="text-align: left">%</label>';
				html +='<div class="col-sm-2 control-label" style="text-align: left">(填写范围：1<=折扣<=100 百分比计算)</div>';
			}else if(type == 4){
    			/* html +='<label class="col-sm-2 control-label mtop">限制消费金额最小值:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actLimit" value="${activity.actLimit }" type="text" class="form-control m-b" required>';
    			html +='</div>' */
    			html +='<label class="col-sm-2 control-label mtop">限制消费金额最大值:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actMax"  disabled  value="${activity.actMax }" type="text" class="form-control m-b" required>';
    			html +='</div>';
    			html += '<label class="col-sm-2 control-label mtop">免几天房租(单位:天):</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actCut"  disabled  value="${activity.actCut }" type="text" class="form-control m-b" required>';
    			html +='</div>';
    		}else if(type == 5){
    			$("#addRow").show();
    			$("#newRow").show();
    			html +='<label class="col-sm-2 control-label mtop">满:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actLimit" value="${activity.actLimit }"  disabled type="text" class="form-control m-b" required>';
    			html +='</div>'
    			html +='<label class="col-sm-2 control-label mtop">减:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actCut" value="${activity.actCut }"  disabled type="text" class="form-control m-b" required>';
    			html +='</div>';
    			html +='<label class="col-sm-2 control-label mtop">占比:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="ratio" value="${activity.actCut }"  disabled type="text" class="form-control m-b" required>';
    			html +='</div>';
    		}
    		$("#showDiv").children().remove();
			$("#showDiv").append(html);
    	}
        function addRow(){
        	var str = '';
        	str+='<div class="row actTypeRow"><div class="col-sm-3"></div>'
        	+'<label class="col-sm-2 control-label mtop">满:</label>'
        	+'<div class="col-sm-2"><input name="actLimit" value=""  disabled type="text" class="form-control m-b" required=""></div>'
        	+'<label class="col-sm-2 control-label mtop">减:</label>'
        	+'<div class="col-sm-2"><input name="actCut" value="" type="text"  disabled class="form-control m-b" required=""></div>'
        	+'<label class="col-sm-2 control-label mtop">占比:</label>'
        	+'<div class="col-sm-2"><input name="ratio" value=""  disabled type="text" class="form-control m-b" required=""></div>'
        	+'</div>'
        	$("#newRow").append(str);
        }



		function callBack(parent){
			parent.refreshData("listTable");
		}


    </script>
</body>
</html>