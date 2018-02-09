<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

    <link href="${staticResourceUrl}/favicon.ico${VERSION}" rel="shortcut icon">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/iCheck/custom.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
	<style>
        .lightBoxGallery img {margin: 5px;width: 160px;}
        label{font-weight:500;margin-top:8px;}
        .room-pic{float:left;}
        .room-pic p{text-align:center;}
        .blueimp-gallery>.title{left:0;bottom:45px;top:auto;width:100%;text-align:center;}
        .house-desc-color{position:absolute;right:20px; bottom:6px;color:cc3366;font-size:6px}
        .checkbox{
    	height:50px;
    }
    </style>
</head>
  <body class="gray-bg">
	   <div id="blueimp-gallery" class="blueimp-gallery">
	       <div class="slides"></div>
	       <h3 class="title"></h3>
	       <a class="prev">‹</a>
	       <a class="next">›</a>
	       <a class="close">×</a>
	       <a class="play-pause"></a>
	       <ol class="indicator"></ol>
	   </div>
	   <div class="wrapper wrapper-content animated fadeInRight">
	      <input id="orderSn" name="orderSn" value="${orderSn }"  type="hidden" >
	      <input id="switchLanMoneyCur" name="switchLanMoneyCur" value=""  type="hidden" >
          <input id="switchTenCurDayCur" name="switchTenCurDayCur" value=""  type="hidden" >
          <input id="switchCleanFeeCur" name="switchCleanFeeCur" value=""  type="hidden" >
          <input id="curDay" name="curDay" value=""  type="hidden" >
          <input id="commissionRateLandlord" name="commissionRateLandlord" value=""  type="hidden" >
           <input id="curDayMoney" name="curDayMoney" value=""  type="hidden" >
           <input id="isHascurDayMoney" name="isHascurDayMoney" value=""  type="hidden" >
           <input id="realPayMoney" name="realPayMoney" value=""  type="hidden" >
           
          <!--  房东申请取消订单 -->
           <input id="switchFirstNightMoney" name="switchFirstNightMoney" value=""  type="hidden" >
           <input id="switchOneHundred" name="switchOneHundred" value=""  type="hidden" >
           <input id="switchCancelAngel" name="switchCancelAngel" value=""  type="hidden" >
           <input id="switchAddSystemEval" name="switchAddSystemEval" value=""  type="hidden" >
           <input id="switchUpdateRankFactor" name="switchUpdateRankFactor" value=""  type="hidden" >
           <input id="switchShieldCalendar" name="switchShieldCalendar" value=""  type="hidden" >
           <input id="switchGiveCoupon" name="switchGiveCoupon" value=""  type="hidden" >
           
           
           
           
	     <h2 class="title-font text-center">订单金额明细</h2>
	       <div class="row"> 
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				          <div class="form-group">
				           	<h2 class="title-font text-righ">订单支付金额</h2>
				           	
		                       <div class="row">
	                               <label class="col-sm-1 control-label text-center">房租(折扣之前):</label>
		                           <div class="col-sm-1">
	                                  <input id="rentalMoneyAll" name="rentalMoneyAll" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
	                              
	                               
	                               <label class="col-sm-1 control-label text-center">押金:</label>
		                           <div class="col-sm-1 text-center">
	                                    <input id="depositMoneyAll" name="depositMoneyAll" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
	                               <label class="col-sm-1 control-label text-center">房客服务费:</label>
		                           <div class="col-sm-1 text-center">
	                                    <input id="userCommMoney" name="userCommMoney" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
	                               <label class="col-sm-1 control-label text-center">清洁费:</label>
		                           <div class="col-sm-1">
	                                   <input id="cleanMoney" name="cleanMoney" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
	                                <label class="col-sm-1 control-label text-center">优惠券:</label>
		                           <div class="col-sm-1">
	                                   <input id="couponMoneyAll" name="couponMoneyAll" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>

								   <label class="col-sm-1 control-label text-center">活动（首单立减）:</label>
								   <div class="col-sm-1">
									   <input id="actMoneyAll" name="actMoneyAll" value="" readonly="readonly" type="text" class="form-control m-b">
								   </div>
		                       </div>
		                       
	                           <div class="hr-line-dashed"></div>
	                           <h2 class="title-font">订单已消费金额</h2>
	                            
		                       <div class="row">
		                           <label class="col-sm-1 control-label text-center">房租(折扣之后):</label>
		                           <div class="col-sm-1">
	                                   <input id="rentalMoneyConsume" name="rentalMoneyConsume" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
				                   <label class="col-sm-1 control-label text-center">房客服务费:</label>
		                           <div class="col-sm-1">
	                                   <input id="userCommMoneyConsume" name="userCommMoneyConsume" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
	                               <label class="col-sm-1 control-label text-center">清洁费:</label>
		                           <div class="col-sm-1">
	                                   <input id="cleanMoneyConsume" name="cleanMoneyConsume" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
	                                <label class="col-sm-1 control-label text-center">折扣金额:</label>
		                           <div class="col-sm-1">
	                                   <input id="discountMoney" name="discountMoney" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
	                                
	                             
		                       </div>
		                     
		                       <div class="hr-line-dashed"></div>
	                           <h2 class="title-font">剩余金额明细</h2>
                            
                              <div class="row">
		                            <label class="col-sm-1 control-label text-center">剩余金额:</label>
		                           <div class="col-sm-1">
	                                   <input id="realLastMoney" name="realLastMoney" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
	                                 <label class="col-sm-1 control-label text-center">客服可支配金额:</label>
		                           <div class="col-sm-1">
	                                   <input  value="" id="lastMoney" name="lastMoney" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
				                   <%--<label class="col-sm-1 control-label text-center">现金金额:</label>
		                           <div class="col-sm-1">
	                                   <input id="payMoney" name="payMoney" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
	                               <label class="col-sm-1 control-label text-center">优惠劵金额:</label>
		                           <div class="col-sm-1">
	                                   <input id="couponMoneyConsume"   name="couponMoneyConsume" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>--%>
	                               <!--  <label class="col-sm-1 control-label text-center">当天房租:</label>
		                           <div class="col-sm-1 text-center">
	                                    <input id="curDayMoneyV" name="curDayMoneyV" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
	                               
	                               <label class="col-sm-1 control-label text-center">剩余金额是否包含当天房租:</label>
		                           <div class="col-sm-1 text-center">
	                                    <input id="isHascurDayMoneyV" name="isHascurDayMoneyV" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div> -->
		                       </div>
			               </div>
			           </div>
	               </div>
	           </div>
	       </div>
	       <!-- 第二个 开始 -->
	         <h2 class="title-font text-center">取消订单操作</h2>
	          <div class="row"> 
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				          <div class="form-group">
				           	
		                       <div class="row">
	                               <label class="col-sm-1 control-label text-center">取消类型:</label>
		                           <div class="col-sm-2">
		                                <select id="cancelType"  name="cancelType" class="form-control m-b">
		                                    <option value="37">协商取消</option>
		                                    <option value="38">房东申请取消</option>
										</select> <span class="help-block m-b-none"></span>
	                               </div>
	                               
	                                <label class="col-sm-1 control-label text-center">取消原因:</label>
		                           <div class="col-sm-2">
		                                <select id="cancelReasonCode" name="cancelReasonCode" class="form-control m-b">
											<option value="10">房屋维修升级</option>
											<option value="20">日历冲突</option>
											<option value="30">无法抗拒的因素</option>
											<option value="0">其他</option>
										</select> <span class="help-block m-b-none"></span>
	                               </div>
		                       </div>
		                       
		                       <div class="row">
	                               <label class="col-sm-1 control-label text-center">协商后房东违约金:</label>
		                           <div class="col-sm-1">
	                                  <input id="penaltyMoney" name="penaltyMoney" value=""  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" placeholder="元" class="form-control m-b">
	                               </div>
		                       </div>
		                         <div class="row"  id="refundSelect" style="display: none;">
		                         </div>
		                         
		                          <div class="row"  id="jiSuanBut" style="display: none;text-align: center;"> 
		                             <button type="button"  class="btn btn-white  text-center" >计算</button> 
		                          </div>
		                          
		                          <!--  房东申请取消开始 -->
		                          <div class="row">
			                           <label class="col-sm-1 control-label text-center">入住时间:</label>
			                           <div class="col-sm-2">
		                                   <input id="checkInTime"   name="checkInTime" value="" readonly="readonly" type="text" class="form-control m-b">
		                               </div>
	                               </div>
	                               <div class="row">
		                               <label class="col-sm-1 control-label text-center">备注:</label>
			                           <div class="col-sm-5">
			                               <textarea name="cancelReason"  id="cancelReason" placeholder="(必填)" class="form-control m-b"></textarea>
		                               </div>
	                               </div>
	                                  <!-- 房东处罚，退费选项 -->
	                                <div class="row"  id="landlordPunishment" style="display: none;">
	                                
		                            </div>
		                            
		                              <!-- 其他五项惩罚 -->
	                                <div class="row" id="five_div" style="display: none;">
		                                <div class="col-sm-1">
		                                     <customtag:authtag authUrl="cancleOrder/cancleOrderEidt">
		                                		<button type="button"  style="margin-left:30px;" class="btn btn-white  text-center " id='edit_btn'>编辑</button>
		                                     </customtag:authtag>
		                                </div>
	                                	<div class="col-sm-11" id="otherFivePunish"></div>
		                            </div>
		                       </div>
		                          
			               </div>
			           </div>
	               </div>
	           </div>
	       </div>
	      <!-- 第二个 结束 -->
	        <!-- 第三个 开始-->
	           <h2 class="title-font text-center">退款明细</h2>
	               <div class="row"> 
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				          <div class="form-group">
				           	
		                       <div class="row">
	                               <label class="col-sm-1 control-label text-center">给房东打款:</label>
		                           <div class="col-sm-1">
		                                <input id="toLandlordMoney" name="toLandlordMoney" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
	                                <label class="col-sm-1 control-label text-center">平台佣金(房东违约金):</label>
		                           <div class="col-sm-1">
		                                <input id="toPlatform" name="toPlatform" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       
		                       <div class="row">
	                               <label class="col-sm-1 control-label text-center">给房客打款:</label>
		                           <div class="col-sm-1">
	                                  <input id="toTenlantMoney" name="toTenlantMoney" value="" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       
		                       <div class="row" style="text-align: center;"> 
		                        <button type="button" data-toggle="modal" data-target="#myModal" class="btn btn-white  text-center" data-dismiss="modal">提交申请</button> 
		                       </div>
		                      
			               </div>
			           </div>
	               </div>
	           </div>
	       </div>
	        <!-- 第三个 结束 -->
	       
	   </div>
	   
	   <!-- 取消申请弹出框 -->
	   	<!-- 添加 菜单  弹出框 -->
<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content animated bounceInRight">
       <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
          </button>
          <h4 class="modal-title">协商取消订单</h4>
       </div>
       <div class="modal-body">
	        <div class="wrapper wrapper-content animated fadeInRight">
		        <div class="row">
		          <div class="col-sm-14">
		               <div class="ibox float-e-margins">
		                   <div class="ibox-content">
				           <form id="evaluateForm" action="" method="post">
			                   <div class="form-group">
			                       <label class="col-sm8 control-label" style="margin-left: 105px;font-size: 16px">取消成功后,系统将会根据您填写的金额进行打款</label>
			                   </div>
		                  <!-- 用于 将表单缓存清空 -->
                           <!--用来清空表单数据-->
                              <input type="reset" name="reset" style="display: none;" />
                           </form>	           
                      </div>
			       </div>
		       </div>
	         </div>
         </div>
     </div>
     <div class="modal-footer" style="text-align: center;">
         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
         <button type="button" id="cancleOrder" class="btn btn-primary" >保存</button>
      </div>
     </div>
   </div>
</div>
   
	   <!-- 全局js -->
	   <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	   
	   <!-- 自定义js -->
	   <script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	   
	   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
 	   
	   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	   <!-- blueimp gallery -->
	   <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}" type="text/javascript"></script>
	    <script src="${staticResourceUrl}/js/minsu/order/cancleOrder.js${VERSION}"></script>
	</body>
</html>