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

<link href="${staticResourceUrl}/favicon.ico${VERSION}" rel="shortcut icon">
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
<style>
.modal-lm{min-width:880px;} 
.lightBoxGallery img {
	margin: 5px;
	width: 160px;
}
.row{margin:0;}
#operList td{font-size:12px;}
</style>
</head>
<body class="gray-bg">
	<!-- 图片弹出层 -->
	<div id="blueimp-gallery" class="blueimp-gallery">
		<div class="slides"></div>
		<h3 class="title"></h3>
		<a class="prev">‹</a> <a class="next">›</a> <a class="close">×</a> <a
			class="play-pause"></a>
		<ol class="indicator"></ol>
	</div>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<form class="form-group">
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">姓名:</label>
							</div>
							<div class="col-sm-2">
								<input id="S_realName" name="realName" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">用户名:</label>
							</div>
							<div class="col-sm-2">
								<input id="S_nickName" name="nickName" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">手机号:</label>
							</div>
							<div class="col-sm-3">
								<input id="S_customerMobile" name="customerMobile" type="text"
									class="form-control">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">角色:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="isLandlord" id="S_isLandlord">
									<option value="">全部</option>
									<option value="1">房东</option>
									<option value="0">房客</option>
								</select>
							</div>

                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">资质:</label>
                            </div>
                            <div class="col-sm-2">
                                <select class="form-control" name="roleCode" id="S_roleCode">
                                    <option value="">全部</option>
                                    <option value="1">种子房东</option>
                                </select>
                            </div>

							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">审核状态:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" id="S_auditStatus" name="auditStatus">
									<option value="">全部</option>
									<option value="0">未审核</option>
									<option value="1">审核通过</option>
									<option value="2">审核未通过</option>
								</select>
							</div>

							<customtag:authtag authUrl="customer/listCustomerMsg" >
								<button class="btn btn-primary" type="button" style="margin-left: 77px;"
									onclick="CommonUtils.query();">
									<i class="fa fa-search"></i>&nbsp;查询
								</button>
							</customtag:authtag>
						</div>
					</form>
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
							<table class="table table-bordered" id="listTable"
								data-click-to-select="true" data-toggle="table"
								data-side-pagination="server" data-pagination="true"
								data-page-list="[10,20,50]" data-pagination="true"
								data-page-size="10" data-pagination-first-text="首页"
								data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
								data-pagination-last-text="末页"
								data-content-type="application/x-www-form-urlencoded"
								data-query-params="paginationParam" data-method="post"
								data-height="520"
								data-single-select="true" data-url="customer/customerDataList">
								<thead>
									<tr>
										<th data-field="uid" data-visible="false"></th>
										<th data-field="realName" data-width="13%" data-align="center" data-formatter="customerDetail.nameshow">姓名</th>
										<th data-field="customerMobile" data-width="13%" data-align="center">手机号</th>
										<th data-field="nickName" data-width="12%" data-align="center">用户名</th>
										<th data-field="isLandlord" data-width="11%" data-align="center" data-formatter="customerDetail.role">角色</th>
										<th data-field="customerJob" data-width="11%" data-align="center">职业</th>
                                        <th data-field="roleNames" data-width="11%" data-align="center">资质</th>
										<th data-field="auditStatus" data-width="10%" data-align="center" data-formatter="customerDetail.auditStatus">审核状态</th>
										<customtag:authtag authUrl="customer/updataCustomerIntroduce" ><th data-field="operate" data-width="10%" data-align="center" data-formatter="formateOperate">操作</th></customtag:authtag>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<!-- End Example Pagination -->
				</div>
			</div>
		</div>
	</div>
	<!--弹出框 -->
	<div class="modal inmodal fade" id="customerDetailInfoModel"
		tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lm">
			<div class="modal-content">
				<div style="padding:5px 10px 0;">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					
				</div>
				<input type="hidden" id="uid" value="">
				<div class="ibox-content" style="border:none;padding-bottom:0;">
					<div class="row">
						<div class="col-sm-12">
							<div class="example-wrap">
								<div class="example">
									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">姓名:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="realName" readonly="readonly" />
										</div>

										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">用户名:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="nickName" readonly="readonly" />
										</div>
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">性别:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="customerSex"
												readonly="readonly" />
										</div>
									</div>

									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">出生日期:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="customerBirthday"
												readonly="readonly" />
										</div>
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">电话:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="customerMobile"
												readonly="readonly" />
										</div>
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">邮箱:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="customerEmail"
												readonly="readonly" />
										</div>
									</div>

									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">证件类别:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="idType" readonly="readonly" />
										</div>

										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">证件号码:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="idNo" readonly="readonly" />
										</div>
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">居住城市:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="cityCode" readonly="readonly" />
										</div>
									</div>
									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">角色:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="isLandlord"
												readonly="readonly" />
										</div>
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">是否审核:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="auditStatus"
												readonly="readonly" />
										</div>
									</div>
									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">个人介绍:</label>
										</div>
										<div class="col-sm-11" style="position: relative;">
											<textarea id="introduce" rows="6" style="width: 100%;" onkeydown="calNum()" onkeyup="calNum()" onchange="calNum()"></textarea>
											<p style="position:relative;left:1px;bottom: 1px;height: 20px;line-height: 20px;text-align:right;">字数：<span id="introduceNum">0</span></p>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="row">
										<div class="col-sm-2 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">身份信息:</label>
										</div>
										<div class="col-sm-9">
											<div class="lightBoxGallery" id="customerPicList"></div>
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-2 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">头像:</label>
										</div>
										<div class="col-sm-9">
											<div class="lightBoxGallery" id="customerPicHeader"></div>
										</div>
									</div>

									<div class="hr-line-dashed"></div>
									<div class="row">
										<div class="col-sm-2 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">审核历史:</label>
										</div>
										<div class="col-sm-10">
											<table id="operList" class="table table-hover">
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="row" style="margin-bottom:10px;">
					<div class="col-sm-6 text-right">
						<customtag:authtag authUrl="customer/updataCustomerIntroduce" ><button type="button" class="btn btn-success btn-sm"
							onclick="upCustomerIntroduce();">保存</button></customtag:authtag>
					</div>
					<div class="col-sm-6">
						<button type="button" class="btn btn-primary btn-sm" id="modalClose"
								data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js" type="text/javascript"></script>
	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/custom.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	<!-- 照片显示插件 -->
	<script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	<!-- Page-Level Scripts -->
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	
	<script>
	
	var picBaseAddrMona = '${picBaseAddrMona}';
	var picSize = '${picSize}';
	
		var customerDetail = {
			idtype : function(value) {
				if (value == "1") {
					return "身份证";
				}else if(value == "2"){
					return "护照";
				}else if(value == "0"){
					return "其他";
				}else if(value == "6"){
					return "台湾居民来往通行证";
				}else if(value == "13"){
					return "港澳居民来往通行证";
				}else if(value == "14"){
					return "港澳台居民身份证";
				}
				
			},
			//审核状态
			audit : function(value) {
				if (value == "0") {
					return "未审核";
				} else if (value == "1") {
					return "审核通过";
				} else {
					return "审核未通过";
				}
			},
			sex : function(value) {
				if (value == "1") {
					return "女";
				} else if(value == "2"){
					return "男";
				}else{
					return "";
				}
			},
			role : function(value, row, index) {
				if (value == "1") {
					return "房东";
				} else if (value == "0") {
					return "房客";
				}
			},
			auditStatus : function(value, row, index) {
				if (value == "0") {
					return "未审核";
				} else if (value == "1") {
					return "审核通过";
				} else if (value == "2") {
					return "审核未通过";
				}
			},
            nameshow:function(value, row, index) {
                return "<a href='javascript:showCustomerDetail(\"" + row.uid + "\");'>" + value + "</a>";
            },
			showDetail : function(uid) {
				$.getJSON("customer/customerDetailInfo",{uid:uid},
					function(data) {
							$.each(data,function(i, n) {
								if (i == "idType") {
									$("#idType").val(customerDetail.idtype(n));
											}if (i == "customerSex") {
												$("#customerSex").val(customerDetail.sex(n));
											} else if (i == "isLandlord") {
												$("#isLandlord").val(customerDetail.role(n));
											} else if (i == "auditStatus") {
												$("#auditStatus").val(customerDetail.audit(n));
											} else if(i == "customerBirthday"){
												$("#customerBirthday").val(new Date(n).format("yyyy-MM-dd"));
											}else if(i == "idType"){
												$("#idType").val(customerDetail.idtype(n));
											} else if (i == "customerPicList") {
												$("#customerPicHeader").empty();
												$("#customerPicList").empty();
												$.each(n,function(i,n) {
													var picStr = "";
													if (n.picBaseUrl){
														if (n.picBaseUrl.indexOf("http") == -1){
															picStr = picBaseAddrMona + n.picBaseUrl + n.picSuffix + picSize + n.picSuffix.toLowerCase();
														}else{
															picStr = n.picBaseUrl;
														}
													}

													var picStr = "<a href='"+picStr+"' data-gallery='blueimp-gallery'><img src='"+picStr+"' style='height:135px;width:165px;' /></a>";
													if (n.picType == "3") {
														$("#customerPicHeader").append(picStr);
													} else {
														$("#customerPicList").append(picStr);
													}
													
												});
											} else if (i == "customerOperHistoryList") {
												$("#operList").empty();
												$.each(n,function(i,n) {
													var tr = "<tr><td>"
															+ (i+1)
															+ "</td><td>"
															+ customerDetail.audit(n.auditAfterStatus)
															+ "</td><td>"
															+ n.operRemark
															+ "</td><td>"
															+ (new Date(n.createTime).format("yyyy-MM-dd"))
															+ "</td>";
													$("#operList").append(tr);
												});
											} else if(i == "customerBaseMsgExtEntity"){
												if(n){
													$("#introduce").val(n.customerIntroduce);
													$("#introduceNum").text(n.customerIntroduce.length);
												} else {
													$("#introduce").val("");
													$("#introduceNum").text("0");
												}
											}else {
												$("#" + i).val(n);
											}
								});
						});

			}
		}

		//分页查询参数
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#listTable').bootstrapTable('getOptions').pageNumber,
				customerMobile : $.trim($('#S_customerMobile').val()),
				realName : $.trim($('#S_realName').val()),
				nickName : $('#S_nickName').val(),
				isLandlord : $('#S_isLandlord option:selected').val(),
                roleCode : $('#S_roleCode option:selected').val(),
				auditStatus : $('#S_auditStatus option:selected').val(),
			};
		}

		function showCustomerDetail(uid){
			//$("#uid").val(uid);
			//$("#customerDetailInfoModel").modal("toggle");
			//customerDetail.showDetail(uid);
            $.openNewTab(new Date().getTime(),'customer/customerDetailMsg?uid='+uid, "客户信息详情"); 
        }

		$("#modalClose").click(function() {
			$('#customerDetailInfoModel').modal('hide')
		})
		
		//操作
		function formateOperate(value, row, index){
			return "<a href='javascript:upCustomerMsg(\""+row.uid+"\");'>修改信息</a>";
		}
		//计算数量
		function calNum() {
			var num = $("#introduce").val().length;
			$("#introduceNum").text(num);
		}
		
		//点击修改
		function upCustomerMsg(uid){
			$("#uid").val(uid);
			$("#customerDetailInfoModel").modal("toggle");
			customerDetail.showDetail(uid);
		}
		
		//更新个人描述
		function upCustomerIntroduce(){
			
			var introduce = $("#introduce").val();
			if(introduce.length<70){
				layer.alert("字数不能少于70", {icon: 5,time: 2000, title:'提示'});
				return;
			}
			if(introduce.length>500){
				layer.alert("字数不能超过500", {icon: 5,time: 2000, title:'提示'});
				return;
			}
			
			$.ajax({
				url:"${basePath}customer/updataCustomerIntroduce",
				data:{"uid":$("#uid").val(),"introduce":introduce},
				dataType:"json",
				type:"post",
				async: true,
				success:function(result) {
					if(result.code === 0){
						$('#customerDetailInfoModel').modal('hide');
					}else{
						layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
					}
				},
				error:function(result){
					layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
				}
			});
		}
	</script>
</body>
</html>
