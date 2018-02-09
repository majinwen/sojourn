<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
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
		<div class="tabs-container">
			<div class="panel-heading">
				<h4 class="panel-title">筛选器</h4>
				<input id="groupFid" value="${groupFid}" hidden="hidden"/>
			</div>

			<div class="tab-content">
				<div class="col-sm-12">
					<div class="ibox float-e-margins">
						<div class="ibox-content">
							<form class="form-horizontal m-t" id="commentForm_house">
								<div class="form-group">
									<div class="row">
										<div class="col-sm-6">
											<label class="col-sm-3 control-label left2">城市：</label>
											<div class="col-sm-3 left2">
												<select id="city" name="city" class="form-control m-b m-b" onchange="htmlProject()">
													<option value="" >请选择</option>
													<c:forEach items="${cityList}" var="obj">
														<option value="${obj.fid }">${obj.cityname }</option>
													</c:forEach>
												</select>
											</div>
											<label class="col-sm-3 control-label left2">项目名称：</label>
											<div class="col-sm-3 left2">
												<select id="projectName" name="projectName" class="form-control m-b m-b">
												</select>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="col-sm-3 left2">
												<button class="btn btn-primary" type="button" onclick="queryLayoutList();">
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

				<div class="panel-heading">
					<h4 class="panel-title">项目列表</h4>
				</div>

				<!-- Panel Other -->
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<div class="row row-lg">
							<div class="col-sm-12">
								<customtag:authtag authUrl="activityGroup/addGroupHouseRel">
									<button id="addGroupHouseRelBtn" class="btn btn-primary" type="button" onclick="addGroupHouseRel()">
										<i class="fa fa-save"></i> 批量添加 </button>
								</customtag:authtag>

								<div class="example-wrap">
									<div class="example">
										<table id="listTable" class="table table-bordered table-hover" data-click-to-select="true"
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
											   data-height="500"
											   data-url="activityGroup/layoutList">
											<thead>
											<tr class="trbg">
												<th data-field="id" data-width="5%" data-checkbox="true"></th>
												<th data-field="projectname" data-width="10%" data-align="center">项目名称</th>
												<th data-field="housetypename" data-width="10%" data-align="center">户型</th>
												<th data-field="housetypeid" data-visible="false"></th>
												<th data-field="projectid" data-visible="false"></th>
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
		</div>
	</div>

<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>
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

<script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>


<script type="text/javascript">

    function paginationParam(params) {
	    return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
            cityid : $('#city option:selected').val(),
            projectid : $('#projectName option:selected').val()
    	};
	}

	function queryLayoutList() {
        $('#listTable').bootstrapTable('selectPage', 1);
    }

    function htmlProject() {
        var cityid = $('#city option:selected').val();
        if(cityid == '' || cityid == null) {
            $('#projectName').empty();
        }else{
            $.ajax({
                url:"${basePath}activityGroup/projectList",
                data:{
                    limit: 9999,
                    page: 1,
                    cityid : $('#city option:selected').val()
                },
                dataType:"json",
                type:"post",
                success:function (result) {
                    var projectList = result.rows;
                    temp_html = "<option value=''>请选择</option>";
                    $.each(projectList,function(i,project){
                        temp_html+="<option value='" + project.fid + "'>" + project.fname + "</option>";
                    });
                    $('#projectName').html(temp_html);
                },
                error:function(result){
                    layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
                }
            });
        }
    }

    function addGroupHouseRel() {
        var selectVar=$('#listTable').bootstrapTable('getSelections');
        if(selectVar.length == 0){
            layer.alert("请至少选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
            return;
        }
        var param = new Object();
        var groupHouseRelList = new Array();
        selectVar.forEach(function(v,index){
            var groupHouseRel = {};
            groupHouseRel.projectId = v.projectid;
            groupHouseRel.layoutId = v.housetypeid;
            groupHouseRel.groupFid = $('#groupFid').val();
            groupHouseRel.houseType = 2;
            groupHouseRelList.push(groupHouseRel);
        });
        param.groupHouseRelEntities = groupHouseRelList;

        $.ajax({
            url:"${basePath}activityGroup/addGroupHouseRel",
            data:{param:JSON.stringify(param)
            },
            dataType:"json",
            type:"post",
            success:function (result) {
                if(result.code === 0){
                    layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
                    $.callBackParent('activityGroup/toGroupHouseList?groupFid='+$('#groupFid').val(),true,callBack);
                }else{
                    layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
                }
            },
            error:function(result){
                layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
            }
        });
    }

    function callBack(parent){
        parent.refreshData();
    }
</script>
</body>

</html>
