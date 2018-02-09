<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<base href="${basePath }>">
<title>自如民宿后台管理系统</title>
<meta name="keywords" content="自如民宿后台管理系统">
<meta name="description" content="自如民宿后台管理系统">
<link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico">
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}"rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}"rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/star-rating.css${VERSION}001"  rel="stylesheet" type="text/css"/>

<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
<style type="text/css">
.left2{
    margin-top: 10px;
}
.row{margin:0;}
</style>
</head>

<body>
<div class="" id="myModal"aria-hidden="false" >
  <div class="modal-dialog">
    <div class="modal-content">
       <div class="modal-header">
          </button>
          <h4 class="modal-title">添加评价</h4>
       </div>
       <div class="modal-body">
	        <div class="wrapper wrapper-content animated fadeInRight">
		        <div class="row">
		          <div class="col-sm-14">
		               <div class="ibox float-e-margins">
		                   <div class="ibox-content">
		                     <input id="evaType"  value="${evaType }"  type="hidden"  >
				           <form id="evaluateForm" action="" method="post">
				               <div class="form-group">
									<label class="col-sm5 control-label">订单编号：</label>
									<input id="orderSn" name="orderSn" value="" type="text"  >
						        </div>
						        
						       <c:if test="${evaType == 2 }">
						     	 <div class="form-group">
			                       <label class="col-sm8 control-label" style="margin-left: 165px;">房东评价</label>
			                   </div>
						        <div class="form-group">
									<label class="col-sm5 control-label"   >房东对房客的满意度：</label>
									<input id="input-21f"  value="" id="landlordSatisfied" name="landlordSatisfied" type="number" class="rating" min=0 max=5 step=1 data-size="md" >
						        </div>
						         <div class="form-group">
									<label class="col-sm5 control-label">房东评价内容：</label>
									<div  class="col-sm5">
								        <textarea rows="5" cols="70" name="content"></textarea>
									</div>
						        </div>
						     	<div class="hr-line-dashed"></div>
						     	
						     	</c:if>
			                  
						     	
						     	<c:if test="${evaType == 1 }">
						     	 <div class="form-group">
			                       <label class="col-sm8 control-label" style="margin-left: 165px;">房客评价</label>
			                     </div>
				                  <div class="form-group">
									<label class="col-sm5 control-label" >清洁度：</label>
									<input id="input-21f"  value="" type="number" id="houseClean"  name="houseClean" class="rating" min=0 max=5 step=1 data-size="md" >
								</div>
								  <div class="form-group">
									<label class="col-sm5 control-label" >描述相符：</label>
									<input id="input-21f"  value=""  id="descriptionMatch"   name="descriptionMatch" type="number" class="rating" min=0 max=5 step=1 data-size="md" >
								
								</div>
								  <div class="form-group">
								    <label class="col-sm5 control-label" >房东印象：</label>
								    <input id="input-21f"  value=""  id="safetyDegree" name="safetyDegree" type="number" class="rating" min=0 max=5 step=1 data-size="md" >
								</div>
								<div class="form-group">
									<label class="col-sm5 control-label" >周边环境：</label>
									<input id="input-21f"  value="" id="trafficPosition" name="trafficPosition" type="number" class="rating" min=0 max=5 step=1 data-size="md" >
								</div>
								<div class="form-group">
									<label class="col-sm5 control-label" >性价比：</label>
									<input id="input-21f"  value="" id="costPerformance"  name="costPerformance"  type="number" class="rating" min=0 max=5 step=1 data-size="md" >
								</div>
								<div class="form-group">
									<label class="col-sm5 control-label">评价内容：</label>
									<div class="col-sm5">
									  <textarea rows="5" cols="70"  name="content" ></textarea>
									</div>
								</div>
						     	
						     	</c:if>
						      
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
         <button type="button" id="saveEvaluate" class="btn btn-primary" >保存</button>
      </div>
     </div>
   </div>
</div>

	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	<!-- 自定义js -->
	<script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	<!-- Bootstrap table -->
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
	<!-- layer javascript -->
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
	<!-- <script src="js/demo/layer-demo.js"></script> -->
	<!-- layerDate plugin javascript -->
	<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/star-rating.js" type="text/javascript"></script>
	
   <!-- blueimp gallery -->
   <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
   <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
   
     <!-- 自定义js -->
	   <script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
   
   
	<script type="text/javascript">
	
   	$("#saveEvaluate").click(function(){
		 
		 var orderSn = $("#orderSn").val();
		 var evaType = $("#evaType").val();
		 //房客评价
		 if(evaType == 1){
			$.ajax({
	            type: "POST",
	            url: "evaluate/saveTenantEva",
	            dataType:"json",
	            data: $("#evaluateForm").serialize(),
	            success: function (result) {
	            	if(result.code==0){
	            		layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
	            		window.location.href = "evaluate/queryListEvaluate";
	            	}else{
	            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
	            	}
	            },
	            error: function(result) {
	            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
	            }
	      });
		 }
		 //房东评价
        if(evaType == 2){
        	$.ajax({
	            type: "POST",
	            url: "evaluate/saveLanlordEva",
	            dataType:"json",
	            data: $("#evaluateForm").serialize(),
	            success: function (result) {
	            	if(result.code==0){
	            		layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
	            		window.location.href = "evaluate/queryListEvaluate";
	            	}else{
	            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
	            	}
	            },
	            error: function(result) {
	            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
	            }
	      });
		 }
 	});
   	
	 
	</script>
</body>
</html>