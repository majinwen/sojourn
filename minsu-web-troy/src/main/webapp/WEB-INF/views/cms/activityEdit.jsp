<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
			           <form id="activityForm" action="cms/addActivityInfo" class="form-horizontal" onsubmit="document.charset='utf-8'" method="post" >
			           		<input type="hidden" name="editFlag" value="${editFlag}" type="text" class="form-control m-b">
			           		<div class="form-group">
			                   <div class="row">
	                               <div class="col-sm-1">
			                   	       <label class="control-label mtop">活动码:</label>
	                               </div>
	                               <c:choose>
	                               <c:when test="${editFlag eq 'new'}">
		                               <div class="col-sm-2">
			                               <input id="actSn" name="actSn" value="${activity.actSn }" type="text" onkeyup="var reg = /^[\d\_a-zA-Z]+$/;if(!reg.test(this.value)) this.value='';" class="form-control m-b"  required maxlength="6">
			                               <span>(数字、字母、下划线,最长6位)</span></br>
			                               <button id="checkCode" class="btn btn-primary" type="button">校验活动码</button>
		                               </div>
	                               </c:when>
	                               <c:otherwise>
	                                   <div class="col-sm-2">
			                               <input id="actSn" name="actSn" value="${activity.actSn }" type="text" onkeyup="var reg = /^[\d\_a-zA-Z]+$/;if(!reg.test(this.value)) this.value='';" class="form-control m-b"  required maxlength="6" readonly="readonly" />
		                                   <span>(数字、字母、下划线,最长6位)</span>
		                               </div>
	                               </c:otherwise>
	                               </c:choose>
		                           
	                               
	                               <label class="col-sm-1 control-label mtop">活动名称:</label>
		                           <div class="col-sm-2">
	                                  <input name="actName" value="${activity.actName }" type="text" class="form-control m-b" required>
	                               </div>
	                           </div>
		                       <div class="hr-line-dashed"></div>
		                       <div class="row">
		                           <div class="col-sm-1">
		                              <label class="control-label mtop">活动形式:</label>
		                           </div>
		                           <div class="col-sm-2">
	                                   <select id="actTypeSelect" class="form-control m-b m-b"  name="actType" class="{required:true}">
	                                   		<option value="">请选择</option>
	                                   		<option value="1">现金券</option>
	                                   		<option value="4">免单</option>
	                                   </select>
	                               </div>
	                               <div id="showDiv" >
	                               </div>
	                             </div>
	                           <div class="hr-line-dashed"></div>
	                           <div class="row">
	                               <div class="col-sm-1">
		                               <label class="control-label mtop">活动开始日期:</label>
		                           </div>
		                           <div class="col-sm-2">
		                           	   <input id="actStartTime" name="sActStartTime" value="" class="laydate-icon form-control layer-date" required>
	                               </div>
		                      
		                           <label class="col-sm-1 control-label mtop">活动结束日期:</label>
		                           <div class="col-sm-2">
		                           	   <input id="actEndTime" name="sActEndTime" value=""  class="laydate-icon form-control layer-date" required>
	                               </div>
	                           </div>
	                           <div class="hr-line-dashed"></div>
	                           <div class="row">
	                               <div class="col-sm-1">
			                   	   <label class="control-label mtop">发放优惠券数量:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input name="actNum" value="${activity.actNum }" type="text" class="form-control m-b" required>
	                               </div>
	                               
	                               <label class="col-sm-1 control-label mtop">活动城市:</label>
		                           <div class="col-sm-2">
		                               <div id="allCheckBox" class="checkbox i-checks">
	                                        <label>
	                                            <input id="allCityCheckBox" name="allCityCode" type="checkbox" value="0"> <i></i>全部
	                                        </label>
	                                   </div>
	                                   <div id="cityCheckBox">    
			                             <c:forEach items="${cityList }" var="obj" varStatus="status">
		                                  <div class="checkbox i-checks">
		                                     <label>
		                                          <input type="checkbox" name="cityCodeArray" value="${obj.code }"> <i></i>${obj.name }
		                                      </label>
		                                   </div>
		                                  </c:forEach>
	                                   </div>
			                           <span class="help-block m-b-none"></span>
								   </div>
                               </div>
		                   </div>
		                   <div class="hr-line-dashed"></div>
			               <div class="form-group">
			                   <div class="row">
	                               <div class="col-sm-1">
			                   	       <label class="control-label mtop">优惠券名称:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input name="couponName" value="${activity.couponName }" type="text" class="form-control m-b" required>
	                               </div>
	                               <!-- <label class="col-sm-1 control-label mtop">优惠券使用对象:</label>
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
		                           <div class="col-sm-1">
		                               <label class="control-label mtop">优惠券使用开始日期:</label>
		                           </div>
		                           <div class="col-sm-2">
		                           	   <input id="couponStartTime" name="sCouponStartTime" value="" class="laydate-icon form-control layer-date" required>
	                               </div>
		                           <label class="col-sm-1 control-label mtop">优惠券使用结束日期:</label>
		                           <div class="col-sm-2">
		                           	   <input id="couponEndTime" name="sCouponEndTime"  value="" class="laydate-icon form-control layer-date" required>
	                               </div>
                               </div> 
                               <div class="hr-line-dashed"></div>
                               <div class="row">
                                   <div class="col-sm-1">
		                              <label class="control-label mtop">订单入住时间限制:</label>
		                           </div>
                                   <div class="col-sm-2">
	                                   <select id="isCheckTimeSelect" class="form-control m-b m-b"  name="isCheckTime" class="{required:true}">
	                                   		<option value="">请选择</option>
	                                   		<option value="0">不限制</option>
	                                   		<option value="1">限制</option>
	                                   </select>
	                               </div>
	                               <div id="checkInLimitDiv" style="display: none" >
			                           <label class="col-sm-1 control-label mtop">入住日期:</label>
			                           <div class="col-sm-2">
			                                <input id="checkInTime" name="sCheckInTime" value="" class="laydate-icon form-control layer-date" required>
			                           </div>
			                           <label class="col-sm-1 control-label mtop">离开日期:</label>
			                           <div class="col-sm-2">
			                                <input id="checkOutTime" name="sCheckOutTime" value="" class="laydate-icon form-control layer-date" required>
	                                   </div>
	                               </div> 
                               </div>
                           </div>
                           <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="submit" >保存内容</button>
                                    <button id="goBack" class="btn btn-primary"  >返回列表</button>
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
    
    <script>
        $(function (){
        	//初始化日期
        	CommonUtils.datePicker('actStartTime');
        	CommonUtils.datePicker('actEndTime');
            CommonUtils.datePicker('couponStartTime');
        	CommonUtils.datePicker('couponEndTime'); 
        	CommonUtils.datePicker('checkInTime');
        	CommonUtils.datePicker('checkOutTime');
        	
       		$("#couponStartTime").val(CommonUtils.formateDate('${activity.couponStartTime }'));
           	$("#couponEndTime").val(CommonUtils.formateDate('${activity.couponEndTime }'));
           	$("#actStartTime").val(CommonUtils.formateDate('${activity.actStartTime }'));
           	$("#actEndTime").val(CommonUtils.formateDate('${activity.actEndTime }'));
           	$("#checkInTime").val(CommonUtils.formateDate('${activity.checkInTime }'));
           	$("#checkOutTime").val(CommonUtils.formateDate('${activity.checkOutTime }'));
           	
        	CommonUtils.selectedDefault('cityCodeSelect','${activity.cityCode}','');
        	//===========================================//
        	var activityTypeOper = function(type){
        		
        		if(typeof(type) == ""||typeof(type) == undefined){
        			$("#showDiv").children().remove();
        			return ;
        		}
        		
        		var html = '';
        		if(type == 1){
        			html +='<label class="col-sm-1 control-label mtop">满:</label>';
        			html +='<div class="col-sm-2">';
        			html +='<input name="actLimit" value="${activity.actLimit }" type="text" class="form-control m-b" required>';
        			html +='</div>'
        			html +='<label class="col-sm-1 control-label mtop">减:</label>';
        			html +='<div class="col-sm-2">';
        			html +='<input name="actCut" value="${activity.actCut }" type="text" class="form-control m-b" required>';
        			html +='</div>';
        		}else if(type == 4){
        			html += '<label class="col-sm-1 control-label mtop">免几天房租(单位:天):</label>';
        			html +='<div class="col-sm-2">';
        			html +='<input name="actCut" value="${activity.actCut }" type="text" class="form-control m-b" required>';
        			html +='</div>';
        		}
        		$("#showDiv").children().remove();
    			$("#showDiv").append(html);
        	}
        	
        	//select设置默认选中 
        	CommonUtils.selectedDefault('actTypeSelect','${activity.actType}',activityTypeOper);
        	
        	$("#actTypeSelect").change(function(){
        		activityTypeOper($(this).val());
        	});
        	//===========================================//
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
        	
        	//select设置默认选中 
        	CommonUtils.selectedDefault('isCheckTimeSelect','${activity.isCheckTime}',activityLimitOper);
        	
        	$("#isCheckTimeSelect").change(function(){
        		activityLimitOper($(this).val());
        	});
        	//===========================================//
        	
        	/**返回按钮*/
        	$("#goBack").click(function(){
        		window.location.href = "cms/toActivityList";
        	});
        	
        	/** 设置优惠券默认选中 */
        	var cityCodeArray = "{activity.cityCode}".split(",");
        	for(var cityCode in cityCodeArray){
        		$("input:checkbox[value="+cityCodeArray[cityCode]+"]").attr('checked','true');	
        	}
        	
        	/**复选框点击事件*/
        	$("#allCheckBox").click(function(){
        		if($('#allCityCheckBox').is(':checked')) {
         			$("#cityCheckBox").find("input[name='cityCodeArray']").attr("disabled","disabled");
         		}else{
         			$("#cityCheckBox").find("input[name='cityCodeArray']").removeAttr("disabled");
         		}
        	});
        	
        	/**校验活动码*/
        	$("#checkCode").click(function(){
        		var actSn = $("#actSn").val();
        		 var callBack = function(result){
        			if(result.status == "success"){
        				layer.alert("验证成功", {icon: 6,time: 2000, title:'提示'});
        			}else{
        				layer.alert(result.message, {icon: 6,time: 3000, title:'提示'});
        			}
        		} 
        	CommonUtils.ajaxPostSubmit("cms/checkActivityCode",{'code':actSn},callBack); 
        	});
        });
        
    </script>
</body>
</html>
