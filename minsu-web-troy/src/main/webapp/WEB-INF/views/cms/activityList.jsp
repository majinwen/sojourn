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
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
			
				<div class="col-sm-12">
					<customtag:authtag authUrl="cms/toEditActivity/new">
						<button id="toAddActivityBtn" class="btn btn-primary" type="button">
						<i class="fa fa-save"></i>&nbsp;新增活动</button>
				    </customtag:authtag>
				    
				    <customtag:authtag authUrl="cms/toEditActivity">
						<button id="updateActivityBtn" class="btn btn-primary" type="button">
						<i class="fa fa-save"></i>&nbsp;编辑活动</button>
				    </customtag:authtag>
				    
					<div class="example-wrap">
						<div class="example">
						 <table id="activityListTable" class="table table-bordered table-hover"  data-click-to-select="true"
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
					                    data-url="cms/activityDataList">                    
					                    <thead>
					                        <tr class="trbg">
					                            <th data-field="id" data-width="5%" data-radio="true"></th>
					                            <th data-field="fid" data-visible="false"></th>
					                            <th data-field="actSn"  data-width="10%" data-align="center" data-formatter="showDetail">活动码</th>
					                            <th data-field="actName" data-width="10%" data-align="center">活动名称</th>
					                            <th data-field="cityName" data-width="5%" data-align="center" >活动城市</th>
					                            <!-- <th data-field="couponName" data-width="5%" data-align="center">优惠券名称</th>
					                            <th data-field="couponSource" data-width="5%" data-align="center">优惠券来源</th> -->
					                            <th data-field="actStatus" data-width="10%" data-align="center" data-formatter="formateActStatus">活动状态</th>
					                            <!-- <th data-field="actUser" data-width="5%" data-align="center">活动对象</th> -->
					                            <th data-field="actNum" data-width="10%" data-align="center">优惠券数量</th>
					                            <th data-field="actType" data-width="10%" data-align="center" data-formatter="formateActType">活动类型</th>
					                            <th data-field="isCoupon" data-width="5%" data-align="center" data-formatter="formateIsCoupon" >是否已经生成优惠券</th>
					                            <th data-field="haveCouponNum" data-width="5%" data-align="center" >已生成优惠券数量</th>
					                            <th data-field="useCouponNum" data-width="5%" data-align="center" >已使用优惠券数量</th>
					                            <th data-field="frozenCouponNum" data-width="5%" data-align="center" >已冻结优惠券数量</th>
					                            <!-- <th data-field="actLimit" data-width="2%" data-align="center" >最少使用金额</th>
					                            <th data-field="actCut" data-width="5%" data-align="center" >优惠券金额\折扣百分比</th> -->
					                            <th data-field="actStartTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">活动开始时间</th>
					                            <th data-field="actEndTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">活动结束时间</th>
					                            <!-- <th data-field="couponStartTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">优惠券开始时间</th>
					                            <th data-field="couponEndTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">优惠券结束时间</th>
					                            <th data-field="isCheckTime" data-width="5%" data-align="center" >是否限制入住时间</th>
					                            <th data-field="checkInTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">入住开始时间</th>
					                            <th data-field="checkOutTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">入住结束时间</th> -->
					                            
					                            <th data-field="createTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">创建时间</th>
					                            <th data-field="handle" data-width="15%" data-align="center" data-formatter="formateOperate">操作</th>
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
					<div class="col-sm-20">
						<div class="example-wrap">
							<div class="example">
								<form action="#">
									<div class="row">
										<div class="col-sm-3 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">活动名称:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="D_actName" readonly="readonly" />
										</div>
										
									</div>
									
									<div class="row">
										<div class="col-sm-3 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">活动码:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="D_actSn" readonly="readonly" />
										</div>
									</div>
									
									<div class="row">
									   <div class="col-sm-3 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">活动形式:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="D_actType" readonly="readonly" />
										</div>
										
									</div>
									
									<div class="row">
									   <div id="showDiv">
										</div>
									</div>
	
									<div class="row">
										<div class="col-sm-3 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">活动开始时间:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="D_actStartTime" readonly="readonly" />
										</div>
										
									</div>
									
									<div class="row">
										<div class="col-sm-3 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">活动结束时间:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="D_actEndTime" readonly="readonly" />
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-3 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">优惠券开始时间:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="D_couponStartTime" readonly="readonly" />
										</div>
										
									</div>
									
									<div class="row">
										
										<div class="col-sm-3 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">优惠券结束时间:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="D_couponEndTime" readonly="readonly" />
										</div>
									</div>
									
									
									
									<div class="row">
										<div class="col-sm-3 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">优惠券数量:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="D_actNum" readonly="readonly" />
										</div>
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
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
<script src="${basePath}js/minsu/common/commonUtils.js${VERSION}005" type="text/javascript"></script>

<script type="text/javascript">
 /* 页面设置 */
 function paginationParam(params) {
	return CommonUtils.paginationCommon('activityListTable',{},params);
}; 

$("#toAddActivityBtn").click(function(){
	window.location.href = "cms/toEditActivity?actSn=new";
});

/** 编辑活动*/
$("#updateActivityBtn").click(function(){
	
	var selectVar=$('#activityListTable').bootstrapTable('getSelections');
	if(selectVar.length == 0){
		layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
	}
	
	if(selectVar[0].isCoupon == 1){
		layer.alert("已生成优惠券，活动不可再编辑！", {icon: 6,time: 2000, title:'提示'});
		return ;
	}
	
	window.location.href = "cms/toEditActivity?actSn="+selectVar[0].actSn;
});

/* function formateCityCode(value, row, index){
	if(value == 0){
		return "全部"
	}else{
		return value;
	}
}; */

function formateActStatus(value, row, index){
	if(value == 1){
		return "未启用";
	}else if(value == 2){
		return "进行中";
	}else if(value == 3){
		return "已结束";
	}
}

function formateActType(value, row, index){
	if(value == 1){
		return "现金券";
	}else if(value == 4){
		return "免天";
	}
}

function formateIsCoupon(value, row, index){
	if(value == 0){
		return "否";
	}else{
		return "是";
	}
}

/**操作列*/
function formateOperate(value, row, index){
	if (row.isCoupon == 0) {
		return "<button type='button'  class='btn btn-primary btn-sm'><span onclick='createCoupon(\""+ row.actSn + "\");'> 生成优惠券</span></button>";
	}else if(row.isCoupon == 1){
		return "<button type='button'  class='btn btn-primary btn-sm'><span onclick='couponList(\""+ row.actSn + "\");'> 优惠券列表</span></button>";
	}
}

/**显示 详情信息 动作*/
function showDetail(field, value, row){
	return "<a onclick='showDetailMess(\""+value.actSn+"\")' href='javascript:void(0);'>"+value.actSn+"</a>";
}

/**显示收款单详情 方法*/
function showDetailMess(value){
	//获取详情信息
	$.getJSON("cms/getActivityDetail",{actSn:value},function(data){
		    $("#D_actName").val(data.actName);
			$("#D_actSn").val(data.actSn);
			if(data.actType == 1){
				$("#D_actType").val("现金券");	
			}else if(data.actType == 4){
				$("#D_actType").val("免几天房租");
			}
			activityTypeOper(data.actType,data);
			$("#D_actStartTime").val(CommonUtils.formateDate(data.actStartTime));
			$("#D_actEndTime").val(CommonUtils.formateDate(data.actEndTime));
			$("#D_couponStartTime").val(CommonUtils.formateDate(data.couponStartTime));
			$("#D_couponEndTime").val(CommonUtils.formateDate(data.couponEndTime));
			$("#D_actNum").val(data.actNum);
			$("#payMentDialog").modal("toggle");
	});
	
}

/** 优惠券活动类型 */
var activityTypeOper = function(type,data){
	
	if(typeof(type) == ""||typeof(type) == undefined){
		$("#showDiv").children().remove();
		return ;
	}
	
	var html = '';
	if(type == 1){
		html += '<div class="col-sm-3 text-right">';
		html +='<label class="control-label mtop">满:</label>';
		html += '</div>';
		html +='<div class="col-sm-2">';
		html +='<input name="actLimit" value="'+data.actLimit+'" type="text" class="form-control m-b" readonly="readonly">';
		html +='</div>'
		html += '<div class="col-sm-1 text-right">';
		html +='<label class="control-label mtop">减:</label>';
		html +='</div>'
		html +='<div class="col-sm-2">';
		html +='<input name="actCut" value="'+data.actCut+'" type="text" class="form-control m-b" readonly="readonly">';
		html +='</div>';
	}else if(type == 4){
		html += '<div class="col-sm-3 text-right">';
		html += '<label class="control-label mtop">免房租(单位:天):</label>';
		html += '</div>';
		html +='<div class="col-sm-4">';
		html +='<input name="actCut" value="'+data.actCut+'" type="text" class="form-control m-b" readonly="readonly">';
		html +='</div>';
	}
	$("#showDiv").children().remove();
	$("#showDiv").append(html);
}

/**创建优惠券*/
function createCoupon(actSn){
	var callBack = function(result){
		layer.alert(result.message, {icon: 6,time: 2000, title:'提示'});
		$('#activityListTable').bootstrapTable('refresh', {query: {limit:$('#activityListTable').bootstrapTable('getOptions').pageSize}});
	}
	CommonUtils.ajaxPostSubmit("cms/createCoupon",{'actSn':actSn},callBack);
}
/**查看优惠券列表*/
function couponList(actSn){
	$.openNewTab(new Date().getTime(),"cms/toCouponList?actSn="+actSn, "优惠券列表");
}
</script>
</body>

</html>
