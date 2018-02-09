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
										<label class="col-sm-3 control-label left2">imei：</label>
										<div class="col-sm-3 left2">
											<input id="imei" name="imei" type="text" class="form-control">
										</div>
										<label class="col-sm-3 control-label left2">uid：</label>
										<div class="col-sm-3 left2">
											<input id="uid" name="uid" type="text" class="form-control">
										</div>

									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<label class="col-sm-3 control-label left2">电话：</label>
										<div class="col-sm-3 left2">
											<input id="customerMobile" customerMobile="name" type="text" class="form-control">
										</div>
										<label class="col-sm-3 control-label left2">真实姓名：</label>
										<div class="col-sm-3 left2">
											<input id="realName" name="realName" type="text" class="form-control">
										</div>
									</div>
									<div class="col-sm-6">
										<div class="col-sm-1 left2 left3">
											<button class="btn btn-primary" type="button" onclick="query();">
												<i class="fa fa-search"></i>&nbsp;查询
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
				<div class="col-sm-12">


					<!-- Example Pagination -->
					<customtag:authtag authUrl="black/addBlack">
						<button class="btn btn-primary" type="button" data-toggle="modal" data-target="#addModal"><i class="fa fa-plus"></i>&nbsp;添加</button>
					</customtag:authtag>
					<customtag:authtag authUrl="black/editBlack">
						<button class="btn btn-info" type="button" onclick="toEditBlack();"><i class="fa fa-edit"></i>&nbsp;编辑</button>
					</customtag:authtag>
					<customtag:authtag authUrl="black/delBlack">
						<button class="btn btn-warning" type="button" onclick="delBlack();"><i class="fa fa-times"></i>&nbsp;删除</button>
					</customtag:authtag>



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
					                    data-url="${basePath}black/blackDataList">
					                    <thead>
					                        <tr class="trbg">
					                            <th data-field="id" data-width="5%"  data-checkbox="true"></th>
												<th data-field="fid"  data-align="center" data-visible="false"></th>
												<th data-field="imei"  data-width="10%" data-align="center" >imei</th>
												<th data-field="uid"  data-width="10%" data-align="center" >uid</th>
					                            <th data-field="realName"  data-width="10%" data-align="center" >真实姓名</th>
												<th data-field="customerMobile"  data-width="10%" data-align="center" >联系方式</th>
												<th data-field="remark"  data-width="30%" data-align="center" >备注</th>
												<th data-field="followUser"  data-width="10%" data-align="center" >跟进人</th>
												<th data-field="followStatus" data-width="10%" data-align="center" data-formatter="formateFollowStatus">跟进状态</th>
					                            <th data-field="createDate" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">创建时间</th>
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


	<!-- 添加 黑名单  弹出框 -->
	<div class="modal inmodal" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
					<h4 class="modal-title">添加黑名单</h4>
				</div>
				<div class="modal-body">
					<div class="wrapper wrapper-content animated fadeInRight">
						<div class="row">
							<div class="col-sm-14">
								<div class="ibox float-e-margins">
									<div class="ibox-content">
										<form id="addForm" class="form-horizontal m-t" >
											<div class="form-group">
												<label class="col-sm-3 control-label">uid：</label>
												<div class="col-sm-8">
													<input class="form-control" id="addUid" type="text" name="uid">
													<label id="message" class="control-label" style="color:#F00"></label>
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-3 control-label">跟进人：</label>
												<div class="col-sm-8">
													<input class="form-control" id="addFollowUser" type="text" name="followUser">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-3 control-label">跟进状态：</label>
												<div class="col-sm-8">
													<select class="form-control" id="addFollowStatus"  name="followStatus" >
														<option value="1">进行中</option>
														<option value="2">已经完成</option>
													</select>
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-3 control-label">备注</label>
												<div class="col-sm-8">
													<textarea rows="3" name="remark" id="addRemark" class="form-control m-b"></textarea>
												</div>
											</div>
											<div class="form-group">
												<div class="col-sm-8">
													<label class="col-sm-12 control-label m-b"><input id="selectImei" type="checkbox"/>同时禁止该账号使用的设备号（imei）</label>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
					<button type="button" id="addBlack" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改黑名单  弹出框 -->
	<div class="modal inmodal" id="editModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
					<h4 class="modal-title">修改黑名单</h4>
				</div>
				<div class="modal-body">
					<div class="wrapper wrapper-content animated fadeInRight">
						<div class="row">
							<div class="col-sm-14">
								<div class="ibox float-e-margins">
									<div class="ibox-content">

										<form id="editForm" class="form-horizontal m-t" >
											<div class="form-group">
												<input class="form-control" type="hidden" id="updateFid"  disabled="disabled">
												<label class="col-sm-3 control-label">uid：</label>
												<div class="col-sm-8">
													<input id="updateUid" class="form-control" type="text"  disabled="disabled">
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-3 control-label">真实姓名：</label>
												<div class="col-sm-8">
													<input id="upRealName" class="form-control" type="text" disabled="disabled">
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-3 control-label">联系方式：</label>
												<div class="col-sm-8">
													<input class="form-control" id="upMobile" type="text" disabled="disabled">
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-3 control-label">跟进人：</label>
												<div class="col-sm-8">
													<input class="form-control" id="upFollowUser" type="text" name="followUser">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-3 control-label">跟进状态：</label>
												<div class="col-sm-8">
													<select class="form-control" id="upFollowStatus"  name="followStatus" >
														<option value="1">进行中</option>
														<option value="2">已经完成</option>
													</select>
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-3 control-label">备注</label>
												<div class="col-sm-8">
													<textarea rows="3" id="upRemark" name="remark" class="form-control m-b"></textarea>
												</div>
											</div>

										</form>

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
					<button type="button" id="editBlack" class="btn btn-primary">修改</button>
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

<script type="text/javascript">


	function query(){
		$('#listTable').bootstrapTable('selectPage', 1);
	}

	//分页查询参数
	function paginationParam(params) {
		return {
			limit:params.limit,
			page : $('#listTable').bootstrapTable('getOptions').pageNumber,
			imei : $.trim($('#imei').val()),
			uid : $.trim($('#uid').val()),
			realName : $.trim($('#realName').val()),
			customerMobile : $('#customerMobile').val()
		};
	}

	function formateFollowStatus(value, row, index){
		if(value == 1){
			return "跟进中";
		}else if(value == 2){
			return "处理完成";
		}else{
			return value;
		}
	}
	//添加黑名单
	$("#addBlack").click(function(){
		var uid = $("#addUid").val();
		var followuser = $("#addFollowUser").val();
		var followStatus = $("#addFollowStatus").find("option:selected").val();
		var remark = $("#addRemark").val();
        var selectImei = $("#selectImei").is(':checked');
		if(isNullOrBlank(uid)){
			alert("参数错误");
			return;
		}
//		alert(uid+","+followuser+","+followStatus+","+remark);
		$.ajax({
			type: "post",
			url: "black/saveBlack",
			dataType:"json",
			data: {
				"uid" : uid,
				"followUser" : followuser,
				"followStatus" : followStatus,
				"remark" : remark,
				"selectImei":selectImei
			},
			success: function (result) {
				if(result.code == 0){
					alert("保存成功");
					$('#addModal').modal('hide');
					window.location.reload();
				}else{
					alert(result.msg);
				}
			},
			error: function(result) {
				alert("未知错误");
				$('#addModal').modal('hide');
				window.location.reload();
			}
		});
	})
	function isNullOrBlank(obj){
		return obj == undefined || obj == null || $.trim(obj).length == 0;
	}
	//修改黑名单
    $("#editBlack").click(function () {
		var fid = $("#updateFid").val();
		var followUser = $("#upFollowUser").val();
		var followStatus = $("#upFollowStatus").find("option:selected").val();
		var remark = $("#upRemark").val();
//		alert(fid+","+followUser+","+followStatus+","+remark);
		if(isNullOrBlank(fid)){
			alert("参数错误");
			return;
		}
		$.ajax({
			type: "post",
			url: "black/upBlack",
			dataType:"json",
			data: {
				"fid" : fid,
				"followUser" : followUser,
				"followStatus" : followStatus,
				"remark" : remark
			},
			success: function (result) {
				if(result.code == 0){
					alert("修改成功");
					$('#editModal').modal('hide');
					window.location.reload();
				}else{
					alert(result.msg);
				}
			},
			error: function(result) {
				alert("未知错误");
			}
		});

	})
	// 到修改黑名单弹层
	function toEditBlack() {
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请选择一条记录进行操作", {
				icon : 6,
				time : 2000,
				title : '提示'
			});
			return;
		}
//		alert(rows[0].fid);
		var uid = rows[0].uid;
		var realName = rows[0].realName;
		var mobile = rows[0].customerMobile;
		$.ajax({
			type: "post",
			url: "black/getBlack",
			dataType:"json",
			data: {
				"fid" : uid
			},
			success: function (result) {
				if(result.code == 0){
					$("#updateFid").val(result.data.obj.fid);
					$("#updateUid").val(result.data.obj.uid);
					$("#upRealName").val(realName);
					$("#upMobile").val(mobile);
					$("#upFollowUser").val(result.data.obj.followUser);
					$("#upFollowStatus option[value='"+result.data.obj.followStatus+"']").attr("selected","selected");
					$("#upRemark").val(result.data.obj.remark);

					$('#editModal').modal('show');
				}
			},
			error: function(result) {
				$('#editModal').modal('show');
			}
		});
	}
	//删除黑名单
	function delBlack(){
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请选择一条记录进行操作", {
				icon : 6,
				time : 2000,
				title : '提示'
			});
			return;
		}
		var fid = rows[0].fid;
		var uid = rows[0].uid;
		$.ajax({
			type: "post",
			url: "black/upBlack",
			dataType:"json",
			data: {
				"fid" : fid,
				"uid" :uid,
				"isDel" : 1
			},
			success: function (result) {
				if(result.code == 0){
					alert("删除成功");
					window.location.reload();
				}
			},
			error: function(result) {
				alert("删除失败,未知错误导致");
			}
		});
	}





</script>
</body>

</html>
