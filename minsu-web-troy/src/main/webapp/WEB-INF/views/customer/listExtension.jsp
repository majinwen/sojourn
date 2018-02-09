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

<link href="${staticResourceUrl}/favicon.ico${VERSION}"
	rel="shortcut icon">
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.min.css${VERSION}"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/iCheck/custom.css${VERSION}" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${staticResourceUrl}/css/plugins/webuploader/webuploader.css${VERSION}">
<link rel="stylesheet" type="text/css"
	href="${staticResourceUrl}/css/demo/webuploader-demo.css${VERSION}">
<link
	href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css"
	rel="stylesheet">
<link
	href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}"
	rel="stylesheet">

</head>

<body class="gray-bg">
	<!-- 图片显示 -->
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
									style="height: 35px; line-height: 35px;">房东姓名:</label>
							</div>
							<div class="col-sm-2">
								<input id="S_landName" name="landName" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">UID:</label>
							</div>
							<div class="col-sm-2">
								<input id="S_uid" name="uid" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">房东电话:</label>
							</div>
							<div class="col-sm-3">
								<input id="S_landPhone" name="landPhone" type="text"
									class="form-control">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">400分机号:</label>
							</div>
							<div class="col-sm-2">
                                <input id="S_ziroomPhone" name="ziroomPhone" type="text"
                                       class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">状态:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" id="S_extStatus"
									name="extStatus">
                                    <option value="" selected >全部</option>
									<option value="0">未绑定</option>
									<option value="1">已经绑定</option>
									<option value="2">绑定失败</option>
                                    <option value="3">已解绑</option>
								</select>
							</div>
							<customtag:authtag authUrl="customer/extensionDataList" >
							<button class="btn btn-primary" style="margin-left: 77px;" type="button" onclick="CommonUtils.query();">
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
								data-single-select="true" data-url="customer/extensionDataList">
								<thead>
									<tr>
										<%--<th data-field="uid" data-visible="false"></th>--%>
										<th data-field="landName" data-width="13%" data-align="center">房东姓名</th>
										<th data-field="uid" data-width="13%" data-align="center">UID</th>
										<th data-field="landPhone" data-width="12%" data-align="center">房东电话</th>
										<th data-field="ziroomPhone" data-width="11%"
											data-align="center" >400分机号</th>

										<th data-field="extStatus" data-width="10%"
											data-align="center"
											data-formatter="extensionDetail.extStatusShow">状态</th>
                                        <th data-field="extStatus" data-width="11%"
                                            data-align="center" data-formatter="extensionDetail.buttonDetail">操作</th>
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



	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}001"
		type="text/javascript"></script>
	<!-- Bootstrap table -->
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js?${VERSION}"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?${VERSION}"></script>
	<script
		src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js${VERSION}"></script>

	<script src="${staticResourceUrl}/js/minsu/common/custom.js?${VERSION}"></script>
	<script
		src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	<!-- 照片显示插件 -->
	<script
		src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	<!-- 图片上传 -->
	<script
		src="${staticResourceUrl}/js/plugins/webuploader/webuploader.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/customerUploader.js?${VERSION}"></script>
	<!-- Page-Level Scripts -->
	<script>

		//客户对象
		var extensionDetail = {

			//审核状态
			extStatusShow : function(value) {
                //0:未绑定 1：已经绑定 2：绑定失败 3：已解绑
                if (value == "0") {
					return "未绑定";
				} else if (value == "1") {
					return "已经绑定";
				} else if (value == "2") {
					return "绑定失败";
				}else if (value == "3") {
                    return "已解绑";
                }else{
                    return "未绑定";
                }
			},

            buttonDetail:function (value, row, index){
                var _html = "";
                value = parseInt(value);
                if(value == 0 || value == 2 || value == 3 ){
                    _html +="<customtag:authtag authUrl='customer/bindTel'><button id='bindTel"+index+"' style='padding:6px 10px;'  type='button' class='btn btn-primary btn1'onclick='bindTel(\""+row.uid+"\");' data-toggle='modal' data-target='#myModal'>绑定</button></customtag:authtag>";
                }else if(value == 1){
                    _html +="<customtag:authtag authUrl='customer/breakBindTel'><button id='bindTel"+index+"' style='padding:6px 10px;'  type='button' class='btn btn-red btn1'onclick='breakTel(\""+row.uid+"\");' data-toggle='modal' data-target='#myModal'>解绑</button></customtag:authtag>";
                }else{
                    _html +="<customtag:authtag authUrl='customer/bindTel'><button id='bindTel"+index+"' style='padding:6px 10px;'  type='button' class='btn btn-primary btn1'onclick='bindTel(\""+row.uid+"\");' data-toggle='modal' data-target='#myModal'>绑定</button></customtag:authtag>";
                }
                return _html;
            }
		}


        function bindTel(uid){
            var mes = '确认绑定？';// 提示信息
            var iconNum = 6; //显示icon层设置 6：笑脸  5：沮丧
            //确认是否改变
            layer.confirm(mes, {icon: iconNum, title:'提示'},function(index){
                $.post("customer/bindTel", {
                    uid : uid
                }, function(data, status) {
                    console.log(data);
                    if(data.code == 0){
                        CommonUtils.query();
                    }else{
                        alert(data.msg);
                    }
                }, "json");
                layer.close(index);
            });
        }


        function breakTel(uid){
            var mes = '确认解绑？';// 提示信息
            var iconNum = 5; //显示icon层设置 6：笑脸  5：沮丧
            //确认是否改变
            layer.confirm(mes, {icon: iconNum, title:'提示'},function(index){
                $.post("customer/breakBindTel", {
                    uid : uid
                }, function(data, status) {
                    console.log(data);
                    if(data.code == 0){
                        CommonUtils.query();
                    }else{
                        alert(data.msg);
                    }
                }, "json");
                layer.close(index);
            });
        }
		//分页查询参数
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#listTable').bootstrapTable('getOptions').pageNumber,
                landName : $.trim($('#S_landName').val()),
				uid : $.trim($('#S_uid').val()),
                landPhone : $('#S_landPhone').val(),
                ziroomPhone : $('#S_ziroomPhone').val(),
                extStatus : $('#S_extStatus option:selected').val()
			};
		}
	</script>
</body>
</html>
