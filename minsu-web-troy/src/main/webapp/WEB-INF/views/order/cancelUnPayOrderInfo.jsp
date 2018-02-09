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
	    <!-- 隐藏域 -->
	    <input id="orderSn" name="orderSn" value="${orderSn }"  type="hidden" >
	    
	         <h2 class="title-font text-center">取消订单操作</h2>
	          <div class="row"> 
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				               <div class="form-group">
		                          <!-- 订单未支付协商取消  -->
	                               <div class="row">
		                               <label class="col-sm-2 control-label text-center">备注:</label>
			                           <div class="col-sm-8">
			                               <textarea name="cancelReason"  id="cancelReason" placeholder="(必填)" class="form-control m-b"></textarea>
		                               </div>
	                               </div>
		                       </div>
			               </div>
			           </div>
	               </div>
	           </div>
	       </div>
	               <div class="row"> 
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				          <div class="form-group">
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
			                       <label class="col-sm8 control-label" style="margin-left: 105px;font-size: 16px">取消成功后,系统将释放此订单优惠券</label>
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
	   <script type="text/javascript">
		   var cancleOrderParam={
					orderSn:$("#orderSn").val(),
		   };
		   $("#cancleOrder").click(function(){
			   var cancelReason = $("#cancelReason").val();
			   if(cancelReason == null||cancelReason==""||cancelReason==undefined){
					alert("请输入备注");
					return ;
			   }
			   //原因及取消类型赋值
			   cancleOrderParam.cancelReason = cancelReason;
			   cancleOrderParam.cancelType = 37;
			   $('#cancleOrder').attr("disabled","disabled");
			   CommonUtils.ajaxPostSubmit("order/cancelOrderNegotiate", cancleOrderParam, function(data){
					if(data.code == 0){
						window.location.href = "order/toCancelOrderList";
					}else{
						$("#cancleOrder").removeAttr("disabled");
						alert(data.msg);
					}
				})
		   });
	   </script>
	</body>
</html>