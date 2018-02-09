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
            <div class="col-sm-22">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
			           <form id="activityForm" action="activity/addActivityInfo" class="form-horizontal" onsubmit="document.charset='utf-8'" method="post" >
			           		<input type="hidden" name="editFlag" value="${editFlag}" type="text" class="form-control m-b">
			           		<div class="form-group">
			                   <div class="row">
	                               <div class="col-sm-1 control-label mtop">
			                   	       <label class="control-label mtop">活动码:</label>
	                               </div>
	                               <c:choose>
	                               <c:when test="${editFlag eq 'new'}">
		                               <div class="col-sm-2 col-sm-2">
			                               <input id="actSn" name="actSn" value="${activity.actSn }" type="text" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" onblur="value=value.replace(/[^\w\.\/]/ig,'')" class="form-control m-b"  required maxlength="6">
			                               <span>(数字、字母、下划线,最长6位)</span></br>
			                              
			                               <button id="checkCode" class="btn btn-primary" type="button">校验活动码</button>
		                               </div>
	                               </c:when>
	                               <c:otherwise>
	                                   <div class="col-sm-2">
			                               <input id="actSn" name="actSn" value="${activity.actSn }" type="text" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" onblur="value=value.replace(/[^\w\.\/]/ig,'')" class="form-control m-b"  required maxlength="6" readonly="readonly" />
		                                   <span>(数字、字母、下划线,最长6位)</span>
		                               </div>
	                               </c:otherwise>
	                               </c:choose>

	                               
	                               <label class="col-sm-2 control-label mtop">活动名称:</label>
		                           <div class="col-sm-2">
	                                  <input name="actName" value="${activity.actName }" type="text" class="form-control m-b" required>
	                               </div>
	                           </div>
		                       <div class="hr-line-dashed"></div>
		                       <div id="actTypeRow" class="row actTypeRow">

								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">活动形式:</label>
								   </div>
		                           <div class="col-sm-2">
	                                   <select id="actTypeSelect" class="form-control m-b m-b" title="请选择活动形式"  name="actType" required>
	                                   		<option value="">请选择</option>
	                                   		<option value="1">优惠券抵现金</option>
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
									   <label class="control-label mtop">活动开始日期:</label>
								   </div>

		                           <div class="col-sm-2">
		                           	   <input id="actStartTime" name="sActStartTime" value="" class="laydate-icon form-control layer-date" required>
	                               </div>
		                      
		                           <label class="col-sm-2 control-label mtop">活动结束日期:</label>
		                           <div class="col-sm-2">
		                           	   <input id="actEndTime" name="sActEndTime" value=""  class="laydate-icon form-control layer-date" required>
	                               </div>
	                           </div>
	                           <div class="hr-line-dashed"></div>
	                           <div class="row">
								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">发放优惠券数量:</label>
								   </div>

		                           <div class="col-sm-2">
	                                   <input name="couponNum" value="${activity.couponNum }" placeholder="友好提示:请输入100的倍数(特殊除外)" type="text" class="form-control m-b " >
	                               </div>
	                               
	                               <label class="col-sm-2 control-label mtop">活动城市:</label>
		                           <div class="col-sm-2">
		                               <div id="allCheckBox" class="checkbox i-checks">
	                                        <label>
	                                            <input id="allCityCheckBox" name="allCityCode" type="checkbox" value="0" > <i></i>全部
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
		                                  <input type="hidden" name="check_city">
		                               <span class="help-block m-b-none"></span>
								       </div>
                               </div>
		                   </div>
		                   <div class="hr-line-dashed"></div>
			               <div class="form-group">
			                   <div class="row">


								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">优惠券名称:</label>
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
								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">是否限制领取次数:</label>
								   </div>
								   <div class="col-sm-2">
									   <select id="isTime" class="form-control m-b m-b"  title="是否限制领取次数" name="isTime" required>
										   <option value="0">不限制</option>
										   <option value="1">限制</option>
									   </select>
								   </div>
								   <div class="col-sm-2">
									   <input name="times" value="" type="text" class="form-control m-b" required>
								   </div>
								   <div class="col-sm-1 ">
									   <label class="control-label mtop">次</label>
								   </div>
							   </div>

							   <div class="hr-line-dashed"></div>

							   <div class="row">
								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">优惠券使用开始日期:</label>
								   </div>
								   <div class="col-sm-2">
									   <input id="couponStartTime" name="sCouponStartTime" value="" class="laydate-icon form-control layer-date" required>
								   </div>
								   <label class="col-sm-2 control-label mtop">优惠券使用结束日期:</label>
								   <div class="col-sm-2">
									   <input id="couponEndTime" name="sCouponEndTime"  value="" class="laydate-icon form-control layer-date" required>
								   </div>
							   </div>
							   <div class="hr-line-dashed"></div>



                               <div class="row">
								   <div class="col-sm-1 control-label mtop">
									   <label class="control-label mtop">订单入住时间限制:</label>
								   </div>
                                   <div class="col-sm-2">
	                                   <select id="isCheckTimeSelect" class="form-control m-b m-b"  title="请选择是否限制入住时间" name="isCheckTime" required>
	                                   		<option value="">请选择</option>
	                                   		<option value="0">不限制</option>
	                                   		<option value="1">限制</option>
	                                   </select>
	                               </div>
	                               <div id="checkInLimitDiv" style="display: none" >
			                           <label class="col-sm-2 control-label mtop">入住日期:</label>
			                           <div class="col-sm-2">
			                                <input id="checkInTime" name="sCheckInTime" value="" class="laydate-icon form-control layer-date" required>
			                           </div>
			                           <label class="col-sm-2 control-label mtop">离开日期:</label>
			                           <div class="col-sm-2">
			                                <input id="checkOutTime" name="sCheckOutTime" value="" class="laydate-icon form-control layer-date" required>
	                                   </div>
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
        	
       		$("#couponStartTime").val(CommonUtils.formatCSTDate('${activity.couponStartTime }'));
           	$("#couponEndTime").val(CommonUtils.formatCSTDate('${activity.couponEndTime }'));
           	$("#actStartTime").val(CommonUtils.formatCSTDate('${activity.actStartTime }'));
           	$("#actEndTime").val(CommonUtils.formatCSTDate('${activity.actEndTime }'));
           	$("#checkInTime").val(CommonUtils.formatCSTDate('${activity.checkInTime }'));
           	$("#checkOutTime").val(CommonUtils.formatCSTDate('${activity.checkOutTime }'));
           	
        	
        	//select设置默认选中 
        	CommonUtils.selectedDefault('actTypeSelect','${activity.actType}',activityTypeOper);
        	
        	$("#actTypeSelect").change(function(){
        		activityTypeOper($(this).val());
        	});
        	
        	//select设置默认选中 
        	CommonUtils.selectedDefault('isCheckTimeSelect','${activity.isCheckTime}',activityLimitOper);
        	
        	$("#isCheckTimeSelect").change(function(){
        		activityLimitOper($(this).val());
        	});
        	
        	/**返回按钮*/
        	$("#goBack").click(function(){

        		history.back();

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
                 var callBack = function(result){
        			if(result.status == "success"){
        				layer.alert("这个码可以使用！", {icon: 6,time: 2000, title:'提示'});
        			}else{
        				layer.alert(result.message, {icon: 5,time: 3000, title:'提示'});
        			}
        		} 
        		CommonUtils.ajaxPostSubmit("activity/checkActivityCode",{'code':actSn},callBack);
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
        
        /** 初始化 checkBox*/
        var initCheckBox= function(){
        	var cityCodeArray = "${cityCodeStr}".split(",");
        	for(var cityCode in cityCodeArray){
        		if(!!cityCodeArray[cityCode]){
        			$("input:checkbox[value="+cityCodeArray[cityCode]+"]").attr('checked','true');
        		}
        		
        	} 
        	setCheckBoxSelected('allCityCheckBox','cityCheckBox','cityCodeArray');
        }
        
        /** 设置checkBox使用情况*/
        var setCheckBoxSelected = function(selectedCheckBox,setCheckBox,checkBoxName){
        	if($('#'+selectedCheckBox).is(':checked')) {
     			$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").attr("disabled","disabled");
     		}else{
     			$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").removeAttr("disabled");
     		}
        }
        
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
    			html +='<input name="actLimit" value="${activity.actLimit }" type="text" class="form-control m-b" required>';
    			html +='</div>'
    			html +='<label class="col-sm-2 control-label mtop">减:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actCut" value="${activity.actCut }" type="text" class="form-control m-b" required>';
    			html +='</div>';
    		}else if(type == 4){
    			/* html +='<label class="col-sm-2 control-label mtop">限制消费金额最小值:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actLimit" value="${activity.actLimit }" type="text" class="form-control m-b" required>';
    			html +='</div>' */
    			html +='<label class="col-sm-2 control-label mtop">限制消费金额最大值:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actMax" value="${activity.actMax }" type="text" class="form-control m-b" required>';
    			html +='</div>';
    			html += '<label class="col-sm-2 control-label mtop">免几天房租(单位:天):</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actCut" value="${activity.actCut }" type="text" class="form-control m-b" required>';
    			html +='</div>';
    		}else if(type == 5){
    			$("#addRow").show();
    			$("#newRow").show();
    			html +='<label class="col-sm-2 control-label mtop">满:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actLimit" value="${activity.actLimit }" type="text" class="form-control m-b" required>';
    			html +='</div>'
    			html +='<label class="col-sm-2 control-label mtop">减:</label>';
    			html +='<div class="col-sm-2">';
    			html +='<input name="actCut" value="${activity.actCut }" type="text" class="form-control m-b" required>';
    			html +='</div>';
    			html +='<label class="col-sm-2 control-label mtop">占比:</label>';
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
        	+'<label class="col-sm-2 control-label mtop">满:</label>'
        	+'<div class="col-sm-2"><input name="actLimit" value="" type="text" class="form-control m-b" required=""></div>'
        	+'<label class="col-sm-2 control-label mtop">减:</label>'
        	+'<div class="col-sm-2"><input name="actCut" value="" type="text" class="form-control m-b" required=""></div>'
        	+'<label class="col-sm-2 control-label mtop">占比:</label>'
        	+'<div class="col-sm-2"><input name="ratio" value="" type="text" class="form-control m-b" required=""></div>'
        	+'</div>'
        	$("#newRow").append(str);
        }
    </script>
</body>
</html>