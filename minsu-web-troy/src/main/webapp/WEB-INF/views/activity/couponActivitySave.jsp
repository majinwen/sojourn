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
	  <style type="text/css">

	  </style>
</head>
 <body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-sm-22">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
			           <form id="activityForm"  class="form-horizontal" onsubmit="return false" method="post" >
			           		<div class="form-group">
			                   <div class="row">
	                               <div class="col-sm-1 text-right">
			                   	       <label class="control-label">活动码:</label>
	                               </div>
								   <div class="col-sm-2 col-sm-2">
									   <input id="actSn" name="actSn" value="${activity.actSn }" type="text" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" onblur="value=value.replace(/[^\w\.\/]/ig,'')" class="form-control m-b"  required maxlength="6">
									   <span>(数字、字母、下划线,最长6位)</span></br>

									   <button id="checkCode" class="btn btn-primary" type="button">校验活动码</button>
								   </div>
								   <div class="col-sm-1 text-right">
									   <label class="control-label">活动名称:</label>
								   </div>
		                           <div class="col-sm-2">
	                                  <input name="actName" value="${activity.actName }" type="text" class="form-control m-b" required>
	                               </div>
	                           </div>
		                       <div class="hr-line-dashed"></div>
		                       <div id="actTypeRow" class="row actTypeRow">

								   <div class="col-sm-1 text-right">
									   <label class="control-label">活动形式:</label>
								   </div>
		                           <div class="col-sm-2">
	                                   <select id="actTypeSelect" class="form-control m-b m-b" title="请选择活动形式"  name="actType" required>
	                                   		<option value="">请选择</option>
	                                   		<option value="1">优惠券抵现金</option>
	                                   		<option value="2">折扣券</option>
	                                   		<option value="4">免天优惠券</option>
	                                   		<%--<option value="5">优惠定额活动</option>--%>
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
										<select id="groupSn" name="groupSn" class="form-control">
											<option value="">不限</option>
											<c:forEach items="${groupList}" var="group">
												<option value="${group.groupSn}">${group.groupName}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-1 control-label">

									</div>
								</div>

								<div class="hr-line-dashed"></div>

	                           <div class="row">

								   <div class="col-sm-1 text-right">
									   <label class="control-label">活动开始日期:</label>
								   </div>

		                           <div class="col-sm-2">
		                           	   <input id="actStartTime" name="sActStartTime" value="" class="laydate-icon form-control layer-date" required>
	                               </div>
								   <div class="col-sm-1 text-right">
									   <label class="control-label">活动结束日期:</label>
								   </div>
		                           <div class="col-sm-2">
		                           	   <input id="actEndTime" name="sActEndTime" value=""  class="laydate-icon form-control layer-date" required>
	                               </div>
	                           </div>
	                           <div class="hr-line-dashed"></div>
	                           <div class="row">
								   <div class="col-sm-1 text-right">
									   <label class="control-label">发放优惠券数量:</label>
								   </div>

		                           <div class="col-sm-2">
	                                   <input name="couponNum" value="${activity.couponNum }" placeholder="友好提示:请输入100的倍数(特殊除外)" type="text" class="form-control m-b " >
	                               </div>
                               </div>
								<div class="row">
									<div class="col-sm-1 text-right">
										<label class="control-label">活动城市:</label>
									</div>
									<div class="col-sm-1">
										<div id="allCheckBox" class="checkbox i-checks">
											<label>
												<input id="allCityCheckBox" name="allCityCode" type="checkbox" value="0" > <i></i>全部
											</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-1"></div>
								   	<div class="col-sm-10" id="cityCheckBox">
										<c:forEach items="${cityList }" var="obj" varStatus="status">
									  		<div class="col-sm-1" class="checkbox i-checks">
											  <input type="checkbox" name="cityCodeArray" value="${obj.code }"> <i></i>${obj.name }
									   		</div>
									  	</c:forEach>
									  <input type="hidden" name="check_city">
								   	</div>
		                   </div>
		                   <div class="hr-line-dashed"></div>
						   <div class="row">
							   <div class="col-sm-1 text-right">
								   <label class="control-label">优惠券名称:</label>
							   </div>
							   <div class="col-sm-2">
								   <input name="couponName" value="${activity.couponName }" type="text" class="form-control m-b" required>
							   </div>
							   <!-- <label class="col-sm-2 control-label mtop">优惠券使用对象:</label>
							   <div class="col-sm-2">
								   <select id="actUserSelect" class="form-control m-b m-b"  name="actUser">
										<option value="">请选择</option>
										<option value="0">全部</option>
										<option value="1">新用户</option>
										<option value="2">老用户</option>
								   </select>
							   </div> -->
						   </div>
							<div class="hr-line-dashed"></div>
						   <div class="row">
							   <div class="col-sm-1 text-right">
								   <label class="control-label">是否限制领取次数:</label>
							   </div>
							   <div class="col-sm-2">
								   <select id="limitTime" class="form-control m-b m-b" onchange="changTimes(this)"  title="是否限制领取次数" name="limitTime" required>
									   <option value="0">不限制</option>
									   <option value="1">限制</option>
								   </select>
							   </div>
							   <div class="col-sm-2" id="timeDiv" style="display: none">
								   <input name="times" id="times"  value="0" type="text" class="form-control m-b" required>
							   </div>
							   <div class="col-sm-1" id="timeDivShow" style="display: none">
								   <label class="control-label">次</label>
							   </div>
						   </div>
							<div class="hr-line-dashed"></div>
							<div class="row">
								<div class="col-sm-1 text-right">
									<label class="control-label">是否限制房源:</label>
								</div>
								<div class="col-sm-2">
									<select id="limitHouse" class="form-control m-b m-b"  name="isLimitHouse" required>
										<option value="0">不限制</option>
										<option value="1">限制</option>
									</select>
								</div>
								<div class="col-sm-6 limitHouseDiv" style="display: none">
									<input name="limitHouseSns" id="limitHouseSns" placeholder="多个编号使用 ,(英文)隔开"  type="text" class="form-control" required>
								</div>
								<div class="col-sm-1 limitHouseDiv" style="display: none">
									<button id="checkLimitHouse" type="button" class="btn btn-primary">校验格式</button>
								</div>
							</div>

						   <div class="hr-line-dashed"></div>
						   <div class="row">
							   <div class="col-sm-1 text-right">
								   <label class="control-label">限制时间类型:</label>
							   </div>
							   <div class="col-sm-2">
								   <select id="couponTimeType" class="form-control m-b m-b" onchange="changTimeType(this)"   title="请选择优惠券限制时间类型" name="couponTimeType" required>
									   <option value="1" selected>固定时间</option>
									   <option value="2">有效天数</option>
								   </select>
							   </div>
							   <div id="couponTimeLastDiv"  style="display: none" >
								   <label class="col-sm-2 control-label mtop">有效天数（天）:</label>
								   <div class="col-sm-2">
									   <input name="couponTimeLast" id="couponTimeLast"  value="0" type="text" class="form-control m-b" required>
								   </div>
							   </div>

							   <div id="couponTimeDiv">
								   <div class="col-sm-1 text-right">
									   <label class="control-label">优惠券使用开始日期:</label>
								   </div>
								   <div class="col-sm-2">
									   <input id="couponStartTime" name="sCouponStartTime" value="" class="laydate-icon form-control layer-date" required>
								   </div>
								   <div class="col-sm-1 text-right">
									   <label class="control-label ">优惠券使用结束日期:</label>
								   </div>
								   <div class="col-sm-2">
									   <input id="couponEndTime" name="sCouponEndTime"  value="" class="laydate-icon form-control layer-date" required>
								   </div>
							   </div>
						   </div>


							   <div class="hr-line-dashed"></div>

                               <div class="row">
								   <div class="col-sm-1 text-right">
									   <label class="control-label ">订单入住时间限制:</label>
								   </div>
                                   <div class="col-sm-2">
	                                   <select id="isCheckTimeSelect" class="form-control m-b m-b"  title="请选择是否限制入住时间" name="isCheckTime" required>
	                                   		<option value="">请选择</option>
	                                   		<option value="0">不限制</option>
	                                   		<option value="1">限制</option>
	                                   </select>
	                               </div>
	                               <div id="checkInLimitDiv" style="display: none" >
			                           <label class="col-sm-2 control-label">入住日期:</label>
			                           <div class="col-sm-2">
			                                <input id="checkInTime" name="sCheckInTime" value="" class="laydate-icon form-control layer-date" required>
			                           </div>
			                           <label class="col-sm-2 control-label">离开日期:</label>
			                           <div class="col-sm-2">
			                                <input id="checkOutTime" name="sCheckOutTime" value="" class="laydate-icon form-control layer-date" required>
	                                   </div>
	                               </div> 
                               </div>


								<div class="hr-line-dashed"></div>
								
								<div class="row">
									<div class="col-sm-1 text-right">
										<label class="control-label">优惠券数量报警配置:</label>
									</div>
									<div class="col-sm-2">
										<select id="isWarn" class="form-control m-b m-b"
											title="请选择优惠券数量报警配置" name="isWarn">
											<option value="0">不报警</option>
											<option value="1">报警</option>
										</select>
									</div>
								</div>
								<div id="warnPhones" class="row hide">
									<div class="col-sm-1 text-right">
								    	<label class="control-label">接受报警手机号:</label>
								    </div>
								   	<div class="col-sm-2">
								   		<%-- <label class="control-label">${warnPhone}</label> --%>
									   <input name="warnPhone"  disabled="disabled"  value="${warnPhone}" type="text" class="form-control m-b">
								   	</div> 
							   </div>
							</div>

						   <div class="form-group">
							   <div class="col-sm-4 col-sm-offset-2">
								   <button id="saveAc" class="btn btn-primary" >保存内容</button>
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

		//限制活动时间类型
		function changTimeType(value) {
			$("#couponTimeLast").val(0);
			if(value.value == 1){
				$("#couponTimeLastDiv").hide();
				$("#couponTimeDiv").show();
			}else {
				$("#couponTimeLastDiv").show();
				$("#couponTimeDiv").hide();
			}
		}



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




        $(function (){

			CommonUtils.datePickerFormat('actStartTime');
			CommonUtils.datePickerFormat('actEndTime');
			CommonUtils.datePickerFormat('couponStartTime');
			CommonUtils.datePickerFormat('couponEndTime');
			CommonUtils.datePickerFormat('checkInTime');
			CommonUtils.datePickerFormat('checkOutTime');
			
			checkInitTime('${activity.couponStartTime }');
			checkInitTime('${activity.couponEndTime }');
			checkInitTime('${activity.actStartTime }');
			checkInitTime('${activity.actEndTime }');
			checkInitTime('${activity.checkInTime }');
			checkInitTime('${activity.checkOutTime }');
        	
        	//select设置默认选中 
        	CommonUtils.selectedDefault('actTypeSelect','${activity.actType}',activityTypeOper);

			//限制房源选择切换
			$("#limitHouse").change(function(){
				var val = $(this).val();
				if(val == 1){
					$(".limitHouseDiv").show();
				}else{
					$(".limitHouseDiv").hide();
				}
			});

			//校验房源信息
			$("#checkLimitHouse").click(function(){
				var houseSnStr = $("#limitHouseSns").val();
				if (houseSnStr){
					$.getJSON("activity/checkLimitHouse",{"houseSns":houseSnStr},function(data){
						if (data.code == 0){
							layer.alert("校验通过", {icon: 6,time: 2000, title:'提示'});
						}else{
							layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
						}
					});
				}
			});

        	
        	$("#actTypeSelect").change(function(){
        		activityTypeOper($(this).val());
        	});
        	
        	//select设置默认选中 
        	CommonUtils.selectedDefault('isCheckTimeSelect','${activity.isCheckTime}',activityLimitOper);
        	
        	$("#isCheckTimeSelect").change(function(){
        		activityLimitOper($(this).val());
        	});

        	
        	/** 设置优惠券默认选中 */
        	initCheckBox();
        	
        	/**复选框点击事件*/
        	$("#allCheckBox").click(function(){
        		setCheckBoxSelected('allCityCheckBox','cityCheckBox','cityCodeArray');
        	});
        	
        	/**校验活动码*/
        	$("#checkCode").click(function(){
                 var actSn = $("#actSn").val();
                 var dealCallBack = function(result){
        			if(result.code == "0"){
        				layer.alert("这个码可以使用！", {icon: 6,time: 2000, title:'提示'});
        			}else{
        				layer.alert(result.msg, {icon: 5,time: 3000, title:'提示'});
        			}
        		} 
        		CommonUtils.ajaxPostSubmit("activity/checkActivityCode",{'code':actSn},dealCallBack);
        	});


			/**校验活动码*/
			$("#saveAc").click(function(){
				$.ajax({
					beforeSend : function(){
						return $("#activityForm").valid();
					},
					url:"activity/saveActivityInfo",
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
			
			//选择报警提醒后显示报警手机号
			$("#isWarn").change(function(){
				var isWarn = $(this).val();
				if(isWarn == "1"){
					$("#warnPhones").removeClass('hide');
				}else if (isWarn == "0"){
					$("#warnPhones").addClass('hide');
				}
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
					"times" : {
						required : true,
						digits : true,
						min:0
					},
					"couponTimeLast" : {
						required : true,
						digits : true,
						min:0
					},

					"couponTimeType" : {
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
					"times" : {required : "请出入领取次数！", digits : "必须是整数！", min:"最小值0！"},
					"couponTimeLast" : {required : "异常的持续天数！", digits : "必须是整数！", min:"最小值0！"},
					"couponTimeType" : {required : "请选择优惠券限制时间类型！", digits : "必须是整数！", min:"最小值0！"},
        	    }
        	});
        	
        	
        });
        
        /** 初始化 checkBox*/
        var initCheckBox= function(){
        	var cityCodeArray = "${cityCodeStr}".split(",");
        	for(var cityCode in cityCodeArray){
        		if(!!cityCodeArray[cityCode]){
        			$("input:checkbox[value="+cityCodeArray[cityCode]+"]").attr('checked','true');
        		}

        	}
        }



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
    			$("#checkInTime").val(CommonUtils.formateDate('${activity.checkInTime }'));
               	$("#checkOutTime").val(CommonUtils.formateDate('${activity.checkOutTime }'));
    			$("#checkInLimitDiv").show();
    		}
    	 }
        
        /* 校验时间参数 */
        function checkInitTime(timeParam){
        	if(timeParam != null && timeParam != undefined && timeParam != ''){
        		CommonUtils.formateDate(timeParam);
        	}
        }

		function callBack(parent){
			parent.refreshData("listTable");
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
    			html +='<label class="col-sm-1 control-label">满:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actLimit" value="${activity.actLimit }" type="text" class="form-control m-b" required>';
    			html +='</div>'
    			html +='<label class="col-sm-1 control-label">减:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actCut" value="${activity.actCut }" type="text" class="form-control m-b" required>';
    			html +='</div>';
    		}else if(type == 2){
                html += '<label class="col-sm-1 control-label">可抵扣最大金额:</label>';
				html +='<div class="col-sm-1">';
				html +='<input name="actMax" value="${activity.actMax }" type="text" class="form-control m-b" required>';
				html +='</div>';
				html +='<div class="col-sm-1">';
				html +='<input name="actCut" value="${activity.actCut }" type="text" class="form-control m-b" required>';
				html +='</div>'
				html +=	'<label class="col-sm-1 control-label" style="text-align: left">%</label>';
				html +='<div class="col-sm-2 control-label" style="text-align: left">(填写范围：1<=折扣<=100 百分比计算)</div>';
			}else if(type == 4){
    			/* html +='<label class="col-sm-2 control-label mtop">限制消费金额最小值:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actLimit" value="${activity.actLimit }" type="text" class="form-control m-b" required>';
    			html +='</div>' */
    			html +='<label class="col-sm-1 control-label">限制消费金额最大值:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actMax" value="${activity.actMax }" type="text" class="form-control m-b" required>';
    			html +='</div>';
    			html += '<label class="col-sm-1 control-label">免几天房租(单位:天):</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actCut" value="${activity.actCut }" type="text" class="form-control m-b" required>';
    			html +='</div>';
    		}else if(type == 5){
    			$("#addRow").show();
    			$("#newRow").show();
    			html +='<label class="col-sm-1 control-label">满:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actLimit" value="${activity.actLimit }" type="text" class="form-control m-b" required>';
    			html +='</div>'
    			html +='<label class="col-sm-1 control-label">减:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actCut" value="${activity.actCut }" type="text" class="form-control m-b" required>';
    			html +='</div>';
    			html +='<label class="col-sm-1 control-label">占比:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="ratio" value="${activity.actCut }" type="text" class="form-control m-b" required>';
    			html +='</div>';
    		}
    		$("#showDiv").children().remove();
			$("#showDiv").append(html);
    	}
        function addRow(){
        	var str = '';
        	str+='<div class="row actTypeRow"><div class="col-sm-3"></div>'
        	+'<label class="col-sm-1 control-label">满:</label>'
        	+'<div class="col-sm-2"><input name="actLimit" value="" type="text" class="form-control m-b" required=""></div>'
        	+'<label class="col-sm-1 control-label">减:</label>'
        	+'<div class="col-sm-2"><input name="actCut" value="" type="text" class="form-control m-b" required=""></div>'
        	+'<label class="col-sm-1 control-label">占比:</label>'
        	+'<div class="col-sm-2"><input name="ratio" value="" type="text" class="form-control m-b" required=""></div>'
        	+'</div>'
        	$("#newRow").append(str);
        }
    </script>
</body>
</html>