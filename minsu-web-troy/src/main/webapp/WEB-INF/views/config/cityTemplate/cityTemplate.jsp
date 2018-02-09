<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<%=basePath%>">
	<title>自如民宿后台管理系统</title>
	<meta name="keywords" content="自如民宿后台管理系统">
	<meta name="description" content="自如民宿后台管理系统">
	<link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico">
	<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001 rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/treeview/bootstrap-treeview.css${VERSION}001" rel="stylesheet">


</head>
<body class="gray-bg">
	<div class="ibox float-e-margins">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content clearfix">
					<!-- 左侧树div -->
					<div class="col-sm-3">
						<div id="treeview" class="test"></div>
					</div>
					<!-- 右侧table div -->
					<div class="col-sm-9">

						<!-- 显示头信息 -->
						<div class="col-sm-10">
							<h5 > <div style="font-size: large" id="cityNameShow"> 已选城市:<span id="cityNameShowId"></span> <span  id="templateNameShowId" style="font-size: large" class="label label-info pull-right"></span>
						</div>

						<div class="hr-line-dashed"></div>

							<button id="addMenuButton" type="button" class="btn btn-primary" >新增模板</button>
							<!-- 菜单列表 -->
							<div class="example-wrap">
								<div class="example">
									<table id="listTable" class="table table-hover table-bordered" data-click-to-select="true"
										   data-toggle="table"
										   data-side-pagination="server"
										   data-page-list="[10,20,50,100]"
										   data-pagination="false"
										   data-page-size="10"
										   data-pagination-first-text="首页"
										   data-pagination-pre-text="上一页"
										   data-pagination-next-text="下一页"
										   data-pagination-last-text="末页"
										   data-content-type="application/x-www-form-urlencoded"
										   data-query-params="paginationParam"
										   data-method="post"
										   data-row-style="rowStyle"
										   data-height="600"
										   data-url="config/cityTemplate/templateList">
										<thead>

										<tr>
											<th data-field="fid" data-align="center" data-formatter="copyFormat" >复制模板</th>
											<th data-field="templateLine" data-visible="false">业务线类型</th>
											<%--<th data-field="id" data-align="center" data-checkbox="false"></th>--%>
											<th data-field="templateName" data-align="center" data-sortable="true">模板名称</th>
											<th data-field="pName" data-align="center" data-sortable="true">模板来自</th>
											<th data-field="cityList" data-align="center" >已关联城市</th>
											<th data-field="templateStatus" data-align="center" data-formatter="stateFormat">状态</th>
											<th data-field="templateStatus" data-align="center"  data-formatter="menuOperData">操作</th>
										</tr>
										</thead>
									</table>
								</div>
							</div>
							<!-- 菜单列表结束 -->
						</div>
					</div>
					<div class="clearfix">
					</div>
				</div>
			</div>
		</div>
	</div>


<div id="dataDiv">
	<input id="selectedCityCode" type="hidden" name="pcode" value="">
	<input id="selectedCityLevel" type="hidden" name="level" value="">
</div>

<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content animated bounceInRight">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
				</button>
				<h4 class="modal-title">添加模板</h4>
			</div>
			<div class="modal-body">
				<div class="wrapper wrapper-content animated fadeInRight">
					<div class="row">
						<div class="col-sm-12">
							<div class="ibox float-e-margins">
								<div class="ibox-content">
									<form id="menuEditForm" action="/config/addTemplate" class="form-horizontal m-t" >

										<div id="parentDiv" class="form-group" style="text-align: center">
											<span id="cpoyNameId"></span>
										</div>

										<div class="form-group">
											<input id="pfid" name="pfid" class="form-control" type="hidden" value="">
											<label class="col-sm-3 control-label">模板名称：</label>
											<div class="col-sm-8">
												<input id="templateName" name="templateName" class="form-control" type="text" value="">
												<span class="help-block m-b-none"></span>
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
				<button type="button" id="saveMenuRes" class="btn btn-primary" >保存</button>
			</div>
		</div>
	</div>
</div>


<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>

<!-- 自定义js -->
<script src="${staticResourceUrl}/js/content.js${VERSION}001"></script>

<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>



<!-- Bootstrap-Treeview plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/treeview/bootstrap-treeview.js${VERSION}001"></script>

<!-- jQuery Validation plugin javascript-->
<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>

<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}001"></script>

<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>


<script type="text/javascript">
	var contentPath = "${path}"; //系统路径
	var bindUrl = "config/cityTemplate/bindInfo";//绑定模板
	var saveTemplateUrl = "config/cityTemplate/addTemplate";//增加模板
	var refreshListUrl = "config/cityTemplate/templateList"; //刷新右侧列表路径
	var refreshCityUrl = "config/cityTemplate/getCityInfo"; //刷新城市模板信息

	/* 校验值不为空 */
	function checkNotNull(obj){
		if(obj == null || obj == "" ||obj == 'undefined'){
			return false;
		}else{
			return true;
		}
	}

	/* 状态 值设置 */
	function stateFormat(value, row, index){
		if(value == 0){
			return'未上线';
		}else if(value == 1){
			return '上线';
		}else{
			return '异常状态';
		}
	}



	/* 列表 操作 值设置 */
	function copyFormat(value, row, index){

		//var buttonInfo ="<button type='button'  class='btn btn-danger btn-sm'><span onclick='alert(\""+row.fid+"\");'></span>拷贝</button>";
		var buttonInfo ="<button type='button'  class='btn btn-info btn-sm' onclick='doCopy(\""+row.fid+'\",\"'+row.templateName+"\");'><span>复制</span></button>";
		return buttonInfo;
	}


	/* 列表 操作 值设置 */
	function menuOperData(value, row, index){
        console.log(row);
		var buttonInfo ="";
		var selectedCityLevel =  $('#selectedCityLevel').val();
		if(value == 1){
			if(selectedCityLevel == 3){
				buttonInfo =  "<button type='button'  class='btn btn-danger btn-sm' onclick='bindInfo(\""+row.fid+'\",\"'+row.templateName+"\");'><span> 绑定</span></button>"
			}
		}else{
            buttonInfo = "<button type='button'  class='btn btn-info btn-sm' onclick='doEdit(\"" + row.fid + "\",\"" + row.templateLine + "\");'><span>编辑</span></button>";
		}
		return buttonInfo;
	}


	/* 左侧树 初始化设置 */
	function initTreeView(Obj){
		//加载左侧树
		$('#treeview').treeview({
			color: " inherit",
			data: Obj,
			onNodeSelected: function (event, node) {
				//选择城市
				var fidText = node.text;//默认菜单名称
				var level = node.level;
				var code = node.code;
				if(level == 3){
					$("#cityNameShowId").html(fidText);
					refreshCityInfo(code);
				}else{
					$("#cityNameShowId").html("");
					$("#templateNameShowId").html("");
				}
				$("#selectedCityCode").val(code);
				$("#selectedCityLevel").val(level);
				refreshList(node.code);
			}
		})
	}

	//刷新列表
	function  refreshList(){
		$.ajax({
			url:refreshListUrl,
			type:"post",
			dataType:"json",
			data:{},
			success:function(data){
				//刷新table
				$('#listTable').bootstrapTable('load', data)
			}
		});
	}

	//刷新列表
	function  refreshCityInfo(cityCode){
		$.ajax({
			url:refreshCityUrl,
			type:"get",
			dataType:"json",
			data:{"cityCode":cityCode},
			success:function(data){
				if(data.code == 0){
					$("#templateNameShowId").html(data.data.info.templateName);
				}

			}
		});
	}
    /* 编辑  */
	function doCopy(fid,templateName){

		$("#cpoyNameId").html("当前模板继承自"+templateName);
		$('#pfid').val(fid);
		$('#myModal').modal('toggle');
	}

	/* 编辑  */
	function doAdd(){
		$("#cpoyNameId").html("");
		$('#pfid').val("");
		$('#myModal').modal('toggle');
	}

	/* 编辑  */
    function doEdit(fid, line) {
        window.location.href = "config/dicItem/itemTree?templateId=" + fid + "&templateLine=" + line;
	}

	/* 绑定模板  */
	function bindInfo(fid,templateName){
		var selectedCityCode = $("#selectedCityCode").val();
		var mes = '确认要把当前城市和当前模板绑定？';// 提示信息
		var iconNum = 6; //显示icon层设置 6：笑脸  5：沮丧
		//确认是否改变
		layer.confirm(mes, {icon: iconNum, title:'提示'},function(index){
			$.ajax({
				type: "POST",
				url: bindUrl,
				dataType:"json",
				traditional: true,
				data: {'templateFid':fid,'cityCode':selectedCityCode},
				success: function (result) {
					if(result.code == 0){
						$("#templateNameShowId").html(templateName);
						refreshList();
					}else{
						alert(result.msg);
					}
				},
				error: function(result) {
					alert("error:"+result);
				}
			});
			layer.close(index);
		});


	}

	//初始化
	$(document).ready(function () {
		/* 初始化左侧树  */
		initTreeView('${treeview}');

		/*保存菜单*/
		$("#saveMenuRes").click(function(){
			$.ajax({
				type: "POST",
				url: saveTemplateUrl,
				dataType:"json",
				data: $("#menuEditForm").serialize(),
				success: function (result) {
					refreshList();
					$('#myModal').modal('hide')
				},
				error: function(result) {
					alert("error:"+result);
				}
			});
		})
		$("#addMenuButton").click(function(){
			doAdd();
		});
	});

</script>


</body>

</html>
