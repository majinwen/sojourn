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

<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">

<style type="text/css">
.left2{
    margin-top: 10px;
}
.left3{padding-left:62px;}
.btn1
{
    border-right: #7b9ebd 1px solid;
    padding-right: 2px;
    border-top: #7b9ebd 1px solid;
    padding-left: 2px;
    font-size: 12px;
    FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0,  StartColorStr=#ffffff,  EndColorStr=#cecfde);
    border-left: #7b9ebd 1px solid;
    cursor: hand;
    padding-top: 2px;
    border-bottom: #7b9ebd 1px solid;
}
.row{margin:0;}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
	<!-- Panel Other -->
	<div class="row row-lg">
       	<div class="col-sm-12">
               <div class="ibox float-e-margins">
                   <div class="ibox-content">
	                   	<div class="row">
	                   		<label class="col-sm-1 control-label mtop">姓名:</label>
                            <div class="col-sm-2">
                                   <input id="customerNameS" name="customerName" type="text" class="form-control m-b">
                            </div>
                        	<label class="col-sm-1 control-label mtop">手机:</label>
                            <div class="col-sm-2">
                                   <input id="customerMoblieS" name="customerMoblie" type="text" class="form-control m-b">
                            </div>
                            <label class="col-sm-1 control-label mtop">申请时间：</label>
                           	<div class="col-sm-2">
                                <input id="createTimeStart"  value=""  name="createTimeStart" class="laydate-icon form-control layer-date">
                           	</div>
                           	<label class="col-sm-1 control-label mtop">到</label>
                            <div class="col-sm-2">
                                <input id="createTimeEnd" value="" name="createTimeEnd" class="laydate-icon form-control layer-date">
                           	</div>	                   	
	                   	</div>
	                   	<div class="row">
	                   		<div class="col-sm-1">
			                    <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
			                </div>
	                   	</div>
                   
                   </div>
               </div>
         </div>
	</div>
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
			   <div class="col-sm-12">
					<div class="example-wrap">
						<div class="example">
						 <table class="table table-bordered table-hover" id="listTable"
								data-toggle="table"
					                    data-side-pagination="server"
					                    data-pagination="true"
					                    data-page-list="[10,20,50]"
					                    data-pagination="true"
					                    data-page-size="10"
					                    data-pagination-first-text="首页"
					                    data-pagination-pre-text="上一页"
					                    data-pagination-next-text="下一页"
					                    data-pagination-last-text="末页"
			                            data-content-type="application/x-www-form-urlencoded"
					                    data-query-params="paginationParam"
					                    data-method="post"
					                    data-single-select="true" 
					                    data-height="470"
					                    data-url="apply/seedApplyList">
								<thead>
									<tr class="trbg">									
										<th data-field="customerName" data-width="13%" data-align="center">姓名</th>
										<th data-field="customerMoblie" data-width="13%" data-align="center" data-formatter="customerMoblieFormatter" >手机号</th>
										<th data-field="isZlan" data-width="12%" data-align="center" data-formatter="isZlanFormatter">是否房东</th>
										<th data-field="roleCodeName" data-width="11%" data-align="center" >角色</th>
										<th data-field="applyStatusName" data-width="11%" data-align="center" >申请状态</th>
                                        <th data-field="cityName" data-width="11%" data-align="center">城市</th>
                                        <th data-field="areaName" data-width="11%" data-align="center">区域</th>
                                        <!-- 
										<th data-field="houseScore" data-width="10%" data-align="center" >房源平均评分</th>
										<th data-field="houseNum" data-width="10%" data-align="center" >房源数量</th>
                                         -->
										<!-- <th data-field="extType" data-width="10%" data-align="center" data-formatter="extTypeFormatter">类型</th>
										<th data-field="content" data-width="10%" data-align="center" >内容</th>
										 --><th data-field="createTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">申请时间</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
 <div class="modal inmodal fade" id="payMentDialog" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lm">
		<div class="modal-content">
			<div class="ibox-content">
				<div class="row row-lg">
					<div class="col-sm-12">
						<div class="example-wrap">
							<div class="example">
								<form action="#">
									<div id="extContent" class="row">
										
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="modalClose" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

<!-- 申请详情 -->
<div class="modal inmodal" id="applyDetailModal" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog" style="width:50%">
	    <div class="modal-content animated bounceInRight">
	       <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
	          <h4 class="modal-title">种子房东申请详情</h4>
	       </div>
	       <div class="modal-body">
	         	<div class="wrapper wrapper-content animated fadeInRight">
					<div class="row">
						<div class="col-sm-12">
							<div class="ibox float-e-margins">
								<div class="ibox-content">
									<div class="row" >
										<div class="form-group">
											<div class="row" style="margin-left: 10px;">
												<label class="col-sm-2 control-label mtop">逻辑编码:</label>
												<div class="col-sm-10">
													<input id="fid" name="fid" type="text"  class="form-control">
												</div> 												 
											</div>
											<div class="row" style="margin-left: 10px;">
												<label class="col-sm-2 control-label mtop">姓名:</label>
												<div class="col-sm-10">
													<input id="customerName" name="customerName" type="text"  class="form-control">
												</div> 												 
											</div>
											<div class="row" style="margin-left: 10px;">
												<label class="col-sm-2 control-label mtop">电话:</label>
												<div class="col-sm-10">
													<input id="customerMoblie" name="customerMoblie" type="text"   class="form-control">
												</div> 												 
											</div>
											<div class="row" style="margin-left: 10px;">
												<label class="col-sm-2 control-label mtop">房源所在城市:</label>
												<div class="col-sm-10">
													<input id="city" name="city" type="text"  class="form-control">
												</div> 												 
											</div>
											<div class="row" style="margin-left: 10px;">
												<label class="col-sm-2 control-label mtop">行政区域:</label>
												<div class="col-sm-10">
													<input id="area" name="area" type="text"   class="form-control">
												</div> 												 
											</div>
											<div class="row" style="margin-left: 10px;">
												<label class="col-sm-2 control-label mtop">房东个人介绍:</label>
												<div class="col-sm-10">
													<textarea id="customerIntroduce" name="customerIntroduce" rows="8" cols="" class="form-control"  ></textarea>
												</div> 												 
											</div>
											<div class="row" style="margin-left: 10px;">
												<label class="col-sm-2 control-label mtop">房源故事:</label>
												<div class="col-sm-10">
													<textarea id="houseStory" name="houseStory" rows="8" cols="" class="form-control" ></textarea>
												</div> 												 
											</div>
											<div class="row" style="margin-left: 10px;">
												<label class="col-sm-2 control-label mtop">房源链接:</label>
												<div class="col-sm-10" id="linkList" >
												
												</div> 												 
											</div>
											<div class="row" style="margin-left: 10px;">
												<label class="col-sm-2 control-label mtop">礼包收货地址:</label>
												<div class="col-sm-10">
													<input id="giftAddress" name="giftAddress" type="text"   class="form-control">
												</div> 												 
											</div>
											<div class="row" style="margin-left: 10px;">
												<label class="col-sm-2 control-label mtop">备注:</label>
												<div class="col-sm-10">
													<input id="remark" name="remark" type="text"   class="form-control">
												</div> 												 
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div> 
				</div>	         
		         
	       </div>
	     <div class="modal-footer">
	         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
	      </div>
	     </div>
	   </div>
	</div>


<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>
<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}001"></script>
<script src="${basePath}js/minsu/common/commonUtils.js${VERSION}004" type="text/javascript"></script>

<script type="text/javascript">
 /* 页面设置 */
 
function paginationParam(params) {
	var createTimeStart  = $.trim($('#createTimeStart').val());
    if(!isNullOrBlank(createTimeStart)){
    	createTimeStart = createTimeStart+" 00:00:00";
    }
    
    var createTimeEnd =$.trim($('#createTimeEnd').val());
    if(!isNullOrBlank(createTimeEnd)){
    	createTimeEnd = createTimeEnd+" 23:59:59";
    }
	 
    return {
        limit: params.limit,
        page:$('#listTable').bootstrapTable('getOptions').pageNumber,
        customerMoblie:$.trim($('#customerMoblieS').val()),
        customerName:$.trim($('#customerNameS').val()), 
        createTimeStart:createTimeStart,
        createTimeEnd:createTimeEnd,
	};
}
 
$(function(){
	   
    //外部js调用
    laydate({
        elem: '#createTimeStart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus' //响应事件。如果没有传入event，则按照默认的click
    });
    laydate({
        elem: '#createTimeEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus' //响应事件。如果没有传入event，则按照默认的click
    });
    
});

function query(){
	$('#listTable').bootstrapTable('selectPage', 1);
}

function isNullOrBlank(obj){
	return obj == undefined || obj == null || $.trim(obj).length == 0;
}

function customerMoblieFormatter(value, row, index){
	return "<a href='javascript:;' class='oneline line-width' title=\""+value+"\" onclick='showApplyDetail(\""+row.fid+"\");'>"+value+"</a>";
}

function showApplyDetail(fid){
	$("#applyDetailModal").modal("toggle");
	$.getJSON("apply/getApplyDetail",{applyFid:fid}, function(data) {
		if (data.code === 0) { 
			var apply = data.data.result;
			if(apply==null){
				return;
			}
			
			$("#fid").val(apply.fid).attr("readonly","readonly");
			$("#customerName").val(apply.customerName).attr("readonly","readonly");
			$("#customerMoblie").val(apply.customerMoblie).attr("readonly","readonly");
			$("#city").val(apply.cityName).attr("readonly","readonly");
			$("#area").val(apply.areaName).attr("readonly","readonly");
			$("#customerIntroduce").val(apply.customerIntroduce).attr("readonly","readonly");
			$("#houseStory").val(apply.houseStory).attr("readonly","readonly");
			$("#giftAddress").val(apply.giftAddress).attr("readonly","readonly");
			$("#remark").val(apply.remark).attr("readonly","readonly");
			
			if(apply.extList==null){
				return;
			}
			var linkStr='';
			$.each(apply.extList, function(i, n) {
				linkStr+='<a target="_blank"  href="'+n.content+'" >'+n.content+'</a></br>';
			});
			
			$("#linkList").html("").append(linkStr);
			
		} else {
			layer.alert("查询失败", {icon: 5,time: 2000, title:'提示'});
		}
	});
}
	
/**操作列*/
function formateOperate(value, row, index){
	if (row.isCoupon == 0) {
		return "<button type='button'  class='btn btn-primary btn-sm'><span onclick='createCoupon(\""+ row.actSn + "\");'> 生成优惠券</span></button>";
	}else if(row.isCoupon == 1){
		return "<button type='button'  class='btn btn-primary btn-sm'><span onclick='couponList(\""+ row.actSn + "\");'> 优惠券列表</span></button>";
	}
}

function isZlanFormatter(value, row, index){
	if(value == 0){
		return "否";
	}else{
		return "是";
	}
}

function roleCodeFormatter(value, row, index){
	if(value == 0){
		return "否";
	}else{
		return "是";
	}
}

function extTypeFormatter(value, row, index){
	if(value == 1){
		return "房源连接";
	}else{
		return "-";
	}
}

function applyStatusFormatter(value, row, index){
	if(value == 1){
		return "提交申请";
	}else{
		return "-";
	}
}

/**显示收款单详情 方法*/
function showDetail(field, value, row){
	
/* 	var data = row.extList;
	var html = "";
	for(var i in data){
		html += " <div class='col-sm-2'>";
		html += " <input value='"+i.content+'" type="text" class="form-control m-b" required>";
		html += " </div>";
	}
		    
	$("#extContent").empty();
	$("#extContent").append(html);
	$("#payMentDialog").modal("toggle"); */
}
</script>
</body>

</html>
