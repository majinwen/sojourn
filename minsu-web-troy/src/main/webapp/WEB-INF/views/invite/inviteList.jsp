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
										<label class="col-sm-3 control-label left2">邀请码：</label>
										<div class="col-sm-3 left2">
											<input id="inviteCode" name="inviteCode" type="text" class="form-control">
										</div>
										<label class="col-sm-3 control-label left2">用户名：</label>
										<div class="col-sm-3 left2">
											<input id="name" name="name" type="text" class="form-control">
										</div>
									</div>
									<div class="col-sm-6">
										<label class="col-sm-3 control-label left2">联系方式：</label>
										<div class="col-sm-3 left2">
											<input id="tel" name="tel" type="text" class="form-control">
										</div>
										<label class="col-sm-3 control-label left2">邀请人姓名：</label>
										<div class="col-sm-3 left2">
											<input id="invoteName" name="invoteName" type="text" class="form-control">
										</div>
									</div>
									<div class="col-sm-6">
										<label class="col-sm-3 control-label left2">邀请人电话：</label>
										<div class="col-sm-3 left2">
											<input id="invoteTel" name="invoteTel" type="text" class="form-control">
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
					                    data-url="${basePath}invite/inviteDataList">
					                    <thead>
					                        <tr class="trbg">
					                            <th data-field="id" data-visible="false"></th>
												<th data-field="inviteCode"  data-width="10%" data-align="center" >邀请码</th>
					                            <th data-field="uidName"  data-width="10%" data-align="center" >用户名</th>
												<th data-field="uidTel"  data-width="10%" data-align="center" >联系方式</th>
												<th data-field="inviteUidName"  data-width="10%" data-align="center" >邀请人姓名</th>
												<th data-field="inviteUidTel"  data-width="10%" data-align="center" >邀请人联系方式</th>
												<th data-field="inviteStatus" data-width="10%" data-align="center" data-formatter="formateInviteStatus">优惠券状态状态</th>

					                            <th data-field="createTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">创建时间</th>
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
			name : $.trim($('#name').val()),
			tel : $.trim($('#tel').val()),
			invoteName : $('#invoteName').val(),
			invoteTel : $('#invoteTel').val(),
			inviteCode:$('#inviteCode').val()
		};
	}

	function formateInviteStatus(value, row, index){
		if(value == 0){
			return "初始化";
		}else if(value == 1){
			return "已被邀请且给被邀请人送券";
		}else if(value == 2){
			return "已给邀请人送券";
		}else{
			return value;
		}
	}

</script>
</body>

</html>
