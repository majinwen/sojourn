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



		<div class="row row-lg">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="commentForm">
							<div class="form-group">
								<div class="row">
									<div class="col-sm-6">
										<label class="col-sm-3 control-label left2">调查码：</label>
										<div class="col-sm-2 left2">
											<input id="npsCode" name="npsCode" type="text" class="form-control">
										</div>
										<label class="col-sm-3 control-label left2">调查名称：</label>
										<div class="col-sm-2 left2">
											<input id="npsName" name="npsName" type="text" class="form-control">
										</div>
										<%--<label class="col-sm-2 control-label left2">角色：</label>
										<div class="col-sm-3 left2">
											<input id="userType" name="userType" type="text" class="form-control">
										</div>--%>
									</div>
									<div class="col-sm-6">
										<label class="col-sm-3 control-label left2">用户UID：</label>
										<div class="col-sm-3 left2">
											<input id="uid" name="uid" type="text" class="form-control">
										</div>
										<label class="col-sm-3 control-label left2">用户姓名：</label>
										<div class="col-sm-3 left2">
											<input id="userName" name="userName" type="text" class="form-control">
										</div>
									</div>
									<div class="col-sm-6">
										<label class="col-sm-3 control-label mtop">创建时间：</label>
										<div class="col-sm-3 mtop" >
											<input id="npsStartTime"  value=""  name="npsStartTime" class="laydate-icon form-control layer-date">
										</div>
										<label class="col-sm-1 control-label mtop">到</label>
										<div class="col-sm-3 mtop">
											<input id="npsEndTime" value="" name="npsEndTime" class="laydate-icon form-control layer-date">
										</div>
									</div>
									<div class="col-sm-6">
										<label class="col-sm-3 control-label left2">联系电话：</label>
										<div class="col-sm-3 left2">
											<input id="mobile" name="mobile" type="text" class="form-control">
										</div>
										<label class="col-sm-3 control-label left2">评分：</label>
										<div class="col-sm-3 left2">
											<select class="form-control" name="score" id="score">
												<option value="">请选择</option>
												<option value="0">0</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
												<option value="8">8</option>
												<option value="9">9</option>
												<option value="10">10</option>
											</select>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="col-sm-3 left2 left3">
											<button class="btn btn-primary" type="button" onclick="query();">
												<i class="fa fa-search"></i>&nbsp;查询
											</button>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="col-sm-3 left2 left3">
											<button class="btn btn-primary" type="button" onclick="openWindow();">
												<i class="fa fa-search"></i>&nbsp;分时间段查询计算
											</button>
										</div>
									</div>
								</div>

							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		
	<!-- Panel Other -->
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">

				<div class="row" id="displayBar" style="display:none;">
					<div class="col-sm-12">
						<label class="col-sm-12 control-label mtop">NPS值：<span id="npsValueSpan"></span>  &nbsp;&nbsp;(其中：推荐者：<span id="npsCommendSpan"></span>    &nbsp;&nbsp;批评者：<span id="npsCriticismSpan"></span>)</label>
					</div>
				</div>

				<div class="col-sm-12">
					<div class="example-wrap">
						<div class="example">
						 <table id="listTable" class="table table-bordered table-hover"  data-click-to-select="true"
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
					                    data-url="${basePath}nps/queryRecommend">
					                    <thead>
					                        <tr class="trbg">
					                            <th data-field="id" data-visible="false"></th>
												<th data-field="npsCode" data-visible="false" ></th>
					                            <th data-field="npsName" data-align="center" >调查名称</th>
					                            <th data-field="uid" data-align="center" >uid</th>
												<th data-field="userType" data-align="center" data-formatter="formateUserType">角色</th>
												<th data-field="orderSn" data-align="center" >订单号</th>
												<th data-field="score" data-align="center" >评分</th>
					                            <th data-field="createTime"data-align="center" data-formatter="CommonUtils.formateDate">提交时间</th>
												<th data-field="remark" data-align="center" >备注</th>
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

	<!--弹出框 -->
	<div class="modal inmodal fade" id="calculateInfoPage"
		 tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lm">
			<div class="modal-content">
				<div style="padding:5px 10px 0;">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
				</div>

				<div class="ibox-content" style="border:none;padding-bottom:0;">
					<div class="row row-lg">
						<div class="col-sm-12">
							<div class="example-wrap">
								<div class="example">

									<div class="row">
										<div class="col-sm-12">
											<label class="col-sm-2 control-label left2">调查名称：</label>
											<div class="col-sm-6 left2">
												<select class="form-control m-b" id="innerNpsName" name="innerNpsName">
												</select>
											</div>
										</div>
									</div>

									<br/>

									<div class="row">
										<div class="col-sm-12">
											<label class="col-sm-2 control-label mtop">创建时间：</label>
											<div class="col-sm-3 mtop" >
												<input id="innerNpsStartTime"  value=""  name="innerNpsStartTime" class="laydate-icon form-control layer-date">
											</div>
											<label class="col-sm-1 control-label mtop">到</label>
											<div class="col-sm-3 mtop">
												<input id="innerNpsEndTime" value="" name="innerNpsEndTime" class="laydate-icon form-control layer-date">
											</div>
										</div>
									</div>

									<br/>

									<div class="row" style="margin-bottom:10px;">
										<div class="col-sm-6 text-right">
											<customtag:authtag authUrl="customer/auditCustomer" ><button type="button" class="btn btn-success btn-sm"
																										 onclick="calculateNPS()">计算</button></customtag:authtag>
										</div>
										<div class="col-sm-6">
											<customtag:authtag authUrl="customer/auditCustomer" ><button type="button" class="btn btn-danger btn-sm"
																										 onclick="closeWindow()">取消</button></customtag:authtag>
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
<script src="${basePath}js/minsu/common/commonUtils.js${VERSION}0013" type="text/javascript"></script>
	<%--<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}001"></script>--%>
	<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}001"></script>

<script>

	function isNullOrBlank(obj){
		return obj == undefined || obj == null || $.trim(obj).length == 0;
	}


	//分页查询参数
	function paginationParam(params) {
		var npsStartTime  = $.trim($('#npsStartTime').val());
		if(!isNullOrBlank(npsStartTime)){
			npsStartTime = npsStartTime+" 00:00:00";
		}

		var npsEndTime =$.trim($('#npsEndTime').val());
		if(!isNullOrBlank(npsEndTime)){
			npsEndTime = npsEndTime+" 23:59:59";
		}

		return {
			limit:params.limit,
			page : $('#listTable').bootstrapTable('getOptions').pageNumber,
			npsCode : $.trim($('#npsCode').val()),
			npsName : $.trim($('#npsName').val()),
			userType : $.trim($('#userType').val()),
			uid : $('#uid').val(),
			userName : $('#userName').val(),
			mobile:$('#mobile').val(),
			npsStartTime:npsStartTime,
			npsEndTime:npsEndTime,
			score:$('#score').val()
		};
	}
	var start={elem:"#npsStartTime",istime: true,max:laydate.now(),choose:function(datas){end.min=datas;end.start=datas}};
    var end={elem:"#npsEndTime",istime: true,max:laydate.now(),choose:function(datas){start.max=datas}};
    var innerStart={elem:"#innerNpsStartTime",istime: true,max:laydate.now(),choose:function(datas){innerEnd.min=datas;innerEnd.start=datas}};
    var innerEnd={elem:"#innerNpsEndTime",istime: true,max:laydate.now(),choose:function(datas){innerStart.max=datas}};
	laydate(start);
	laydate(end);
    laydate(innerStart);
    laydate(innerEnd);

	function query(){
		$('#listTable').bootstrapTable('selectPage', 1);
        $('#displayBar').hide();
	}



	function formateUserType(value, row, index){
		if(value == 1){
			return "房东";
		}else if(value == 2){
			return "房客";
		}else{
			return value;
		}
	}

	function openWindow() {
        $("#calculateInfoPage").modal("toggle");
        $.ajax({
            type: "post",
            url: "${basePath}nps/npsNameList",
            dataType:"json",
            success: function (result) {
                if(result.code == 0){
                    var npsNameList = result.data.npsNameList;
                    var innerNpsName = $("#innerNpsName");
                    innerNpsName.html("<option value='"+"'>"+"请选择NPS名称"+"</option>");
                    for(var i = 0; i< npsNameList.length;i++){
                        innerNpsName.append("<option value='"+npsNameList[i].npsCode+"'>"+npsNameList[i].npsName+"</option>");
                    }
                }else{
                    alert(result.msg);
                }
            },
            error: function(result) {
                alert(result.msg);
                window.location.reload()
            }
        });
    }

    function closeWindow() {
        $("#calculateInfoPage").modal("hide");
    }

    function calculateNPS() {
        var npsStartTime  = $.trim($('#innerNpsStartTime').val());
        var npsEndTime =$.trim($('#innerNpsEndTime').val());
        var npsCode = $.trim($('#innerNpsName option:selected').val());
        if(isNullOrBlank(npsStartTime)){
            alert("开始时间不能为空");
            return;
		}
		if(isNullOrBlank(npsEndTime)){
            alert("结束时间不能为空");
            return;
		}
        if(isNullOrBlank(npsCode)){
            alert("调查名称不能为空");
            return;
        }

        if(!isNullOrBlank(npsStartTime)){
            npsStartTime = npsStartTime+" 00:00:00";
        }
        if(!isNullOrBlank(npsEndTime)){
            npsEndTime = npsEndTime+" 23:59:59";
        }
        $.ajax({
            type: "post",
            url: "${basePath}nps/calculateNPS",
            dataType:"json",
            data: {
                "npsCode" : npsCode,
                "npsStartTime" : npsStartTime,
				"npsEndTime" : npsEndTime
            },
            success: function (result) {
                if(result.code == 0){
                    var npsQuantumVo = result.data.npsQuantumVo;
                    if(npsQuantumVo == null || npsQuantumVo == ""){
                        alert("该事间段内没有调研数据,请确认后重新输入！");
                        return;
                    }

                    $('#displayBar').show();

                    $('#npsValueSpan').text((npsQuantumVo.npsValue * 100).toFixed(2) + "%");
                    $('#npsCommendSpan').text((npsQuantumVo.commendPercent * 100).toFixed(2) + "%");
                    $('#npsCriticismSpan').text((npsQuantumVo.criticismPercent * 100).toFixed(2) + "%");

                    $('#npsStartTime').val(npsStartTime);
                    $('#npsEndTime').val(npsEndTime);
                    $('#npsName').val($.trim($('#innerNpsName option:selected').text()));

                    closeWindow();
                }else{
                    alert(result.msg);
                }
            },
            error: function(result) {
                alert(result.msg);
                window.location.reload()
            }
        });
    }

</script>
</body>

</html>
