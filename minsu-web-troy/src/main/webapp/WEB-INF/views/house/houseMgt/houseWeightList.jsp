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
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">

	<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
	
	<style type="text/css">
		td{
			
		}
	</style>
</head>
  
  <body class="gray-bg">
	   <div class="wrapper wrapper-content animated fadeInRight">
	       <div class="row">
	           <div class="col-sm-12">
               	  <div class="tabs-container">
                    <ul class="nav nav-tabs">
                        <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true"> 整租</a></li>
                        <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">合租</a></li>
                    </ul>
	                <div class="tab-content">
                        <div id="tab-1" class="tab-pane active">
                            <div class="row row-lg">
					        	<div class="col-sm-12">
					                <div class="ibox float-e-margins">
					                    <div class="ibox-content">
					                       	<div class="row">
				                                <label class="col-sm-1 control-label mtop">房源名称:</label>	
				                                <div class="col-sm-2">
					                                 <input id="houseName" name="houseName" type="text" class="form-control m-b">
				                                </div>
				                                <label class="col-sm-1 control-label mtop">房源类型:</label>	
				                                <div class="col-sm-2">
				                                     <select class="form-control m-b" id="houseType">
						                                   <option value="">请选择</option>
						                                   <c:forEach items="${houseTypeList }" var="obj">
							                                   <option value="${obj.key }">${obj.text }</option>
						                                   </c:forEach>
					                                   </select>
				                                </div>
				                                <label class="col-sm-1 control-label mtop">管家姓名:</label>
					                            <div class="col-sm-2">
													<input id="zoName" name="zoName" type="text" class="form-control m-b">
				                                </div>
					                        </div>
					                        <div class="row">
				                                <label class="col-sm-1 control-label mtop">管理权重分值:</label>
					                            <div class="col-sm-2">
				                                    <select class="form-control m-b" id="isWeight">
						                                   <option value="">请选择</option>
						                                   <option value="1">有</option>
						                                   <option value="0">无</option>
					                                </select>
				                                </div>
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
						                        <customtag:authtag authUrl="house/houseMgt/editHouseWeight">
						                        	<button class="btn btn-primary " type="button" onclick="showSaveModal(0);"><i class="fa fa-plus"></i>&nbsp;设置房源权重分值</button>
				                       				<button class="btn btn-info " type="button" onclick="showDelModal(0);"><i class="fa fa-edit"></i>&nbsp;取消房源权重分值</button>
					                            </customtag:authtag>
					                            <div class="example">
					                                <table id="listTable" class="table table-bordered"  
					                                data-click-to-select="true"
								                    data-toggle="table"
								                    data-side-pagination="server"
								                    data-pagination="true"
								                    data-striped="true"
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
								                    data-single-select="false"
								                    data-height="496"
								                    data-classes="table table-hover table-condensed"
								                    data-url="house/houseMgt/showHouseWeight">                    
								                    <thead>
								                        <tr>
								                            <th data-field="fid" data-visible="false"></th>
								                            <th data-field="id" data-width="50px" data-checkbox="true"></th>
								                            <th data-field="houseSn" data-width="100px" data-align="center">房源编号</th>
								                            <th data-field="houseName" data-width="100px" data-align="center">房源名称</th>
								                            <th data-field="houseAddr" data-width="200px" data-align="center">房源地址</th>
								                            <th data-field="weight" data-width="50px" data-align="center">管理权重分值</th>
								                            <th data-field="houseType" data-width="100px" data-align="center" data-formatter="formateHouseType">房屋类型</th>
								                            <th data-field="landlordName" data-width="100px" data-align="center">房东</th>
								                            <th data-field="landlordMobile" data-width="100px" data-align="center">房东手机</th>
								                            <th data-field="rentWay" data-width="100px" data-align="center" data-formatter="formateRentWay">出租方式</th>
								                            <!-- <th data-field="empPushName" data-width="100px" data-align="center">地推管家</th> -->
								                            <!-- <th data-field="empPushMobile" data-width="100px" data-align="center">管家手机</th> -->
								                            <th data-field="empGuardName" data-width="100px" data-align="center">运营专员</th>
								                            <!-- <th data-field="empGuardMobile" data-width="100px" data-align="center">管家手机</th> -->
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
						<div id="tab-2" class="tab-pane">
                            <div class="row row-lg">
					        	<div class="col-sm-12">
					                <div class="ibox float-e-margins">
					                    <div class="ibox-content">
					                       	<div class="row">
				                                <label class="col-sm-1 control-label mtop">房源名称:</label>	
				                                <div class="col-sm-2">
					                                 <input id="houseNameS" name="houseNameS" type="text" class="form-control m-b">
				                                </div>
				                                <label class="col-sm-1 control-label mtop">房源类型:</label>	
				                                <div class="col-sm-2">
				                                     <select class="form-control m-b" id="houseTypeS">
						                                   <option value="">请选择</option>
						                                   <c:forEach items="${houseTypeList }" var="obj">
							                                   <option value="${obj.key }">${obj.text }</option>
						                                   </c:forEach>
					                                   </select>
				                                </div>
				                                <label class="col-sm-1 control-label mtop">房间类型:</label>
						                        <div class="col-sm-2">
					                                <select class="form-control m-b m-b" id="roomType">
					                                     <option value="">请选择</option>
					                                     <option value="0">房间</option>
					                                     <option value="1">客厅</option>
					                                </select>
					                            </div>
				                                <label class="col-sm-1 control-label mtop">运营专员姓名:</label>
					                            <div class="col-sm-2">
													<input id="zoNameS" name="zoNameS" type="text" class="form-control m-b">
				                                </div>
					                        </div>
					                        <div class="row">
				                                <label class="col-sm-1 control-label mtop">管理权重分值:</label>
					                            <div class="col-sm-2">
				                                    <select class="form-control m-b" id="isWeightS">
						                                   <option value="">请选择</option>
						                                   <option value="1">有</option>
						                                   <option value="0">无</option>
					                                </select>
				                                </div>
				                                <div class="col-sm-1">
								                       <button class="btn btn-primary" type="button" onclick="queryS();"><i class="fa fa-search"></i>&nbsp;查询</button>
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
						                        <customtag:authtag authUrl="house/houseMgt/editHouseWeight">
						                        	<button class="btn btn-primary " type="button" onclick="showSaveModal(1);"><i class="fa fa-plus"></i>&nbsp;设置房源权重分值</button>
				                       				<button class="btn btn-info " type="button" onclick="showDelModal(1);"><i class="fa fa-edit"></i>&nbsp;取消房源权重分值</button>
						                        </customtag:authtag>
					                            <div class="example">
					                                <table id="listTableS" class="table table-bordered"  
					                                data-click-to-select="true"
								                    data-toggle="table"
								                    data-side-pagination="server"
								                    data-pagination="true"
								                    data-striped="true"
								                    data-page-list="[10,20,50]"
								                    data-pagination="true"
								                    data-page-size="10"
								                    data-pagination-first-text="首页"
								                    data-pagination-pre-text="上一页"
								                    data-pagination-next-text="下一页"
								                    data-pagination-last-text="末页"
								                    data-content-type="application/x-www-form-urlencoded"
								                    data-query-params="paginationParamS"
								                    data-method="post" 
								                    data-single-select="false"
								                    data-height="496"
								                    data-classes="table table-hover table-condensed"
								                    data-url="house/houseMgt/showHouseWeight">                    
								                    <thead>
								                        <tr>
								                            <th data-field="fid" data-visible="false"></th>
								                            <th data-field="id" data-width="50px" data-checkbox="true"></th>
								                            <th data-field="roomSn" data-width="100px" data-align="center">房间编号</th>
								                            <th data-field="roomTypeStr" data-width="100px" data-align="center">房间类型</th>
								                            <th data-field="houseName" data-width="100px" data-align="center">房间名称</th>
								                            <th data-field="houseAddr" data-width="200px" data-align="center">房源地址</th>
								                            <th data-field="weight" data-width="50px" data-align="center">管理权重分值</th>
								                            <th data-field="houseType" data-width="100px" data-align="center" data-formatter="formateHouseType">房屋类型</th>
								                            <th data-field="landlordName" data-width="100px" data-align="center">房东</th>
								                            <th data-field="landlordMobile" data-width="100px" data-align="center">房东手机</th>
								                            <th data-field="rentWay" data-width="100px" data-align="center" data-formatter="formateRentWay">出租方式</th>
								                            <!-- <th data-field="empPushName" data-width="100px" data-align="center">地推管家</th> -->
								                            <!-- <th data-field="empPushMobile" data-width="100px" data-align="center">管家手机</th> -->
								                            <th data-field="empGuardName" data-width="100px" data-align="center">运营专员</th>
								                            <!-- <th data-field="empGuardMobile" data-width="100px" data-align="center">管家手机</th> -->
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
	           </div>
	       </div>
	   </div>
	   
	   <!-- 增加管理权重分值弹出框 -->
		<div class="modal inmodal fade" id="saveModel" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">增加权重分值</h4>
					</div>
					<div class="modal-body">
						<div class="wrapper wrapper-content animated fadeInRight">
							<div class="row">
								<div class="col-sm-12">
									<div class="ibox float-e-margins">
										<div class="ibox-content">
											<form id="addForm" class="form-horizontal m-t">
												<input id="rentWay_A" type="hidden">
												<div class="form-group">
													<label class="col-sm-3 control-label">管理权重分值：</label>
													<div class="col-sm-4">
														<input id="weight" name="weight" class="form-control"></input>
														<span class="help-block m-b-none"></span>
													</div>
												</div>
												<div class="form-group">
													<label id="saveMsg" class="col-sm-3 control-label" style="color:red"></label>
												</div>
												<!-- 用于 将表单缓存清空 -->
												<input type="reset" style="display: none;" />
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<form class="form-group">
							<div class="row">
								<div class="form-group">
									<div class="col-sm-4 col-sm-offset-3">
										<button type="button" id="saveBtn" class="btn btn-primary">确定</button>&nbsp;&nbsp;
										<button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
	   <!-- 取消管理权重分值弹出框 -->
		<div class="modal inmodal fade" id="delModel" tabindex="-2"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">取消权重分值</h4>
					</div>
					<div class="modal-body">
						<div class="wrapper wrapper-content animated fadeInRight">
							<div class="row">
								<div class="col-sm-12">
									<div class="ibox float-e-margins">
										<div class="ibox-content">
											<form id="delForm" class="form-horizontal m-t">
												<input id="rentWay_D" type="hidden">
												<div class="form-group">
													<label class="col-sm-8 control-label">确定删除所选房源的管理权重分值?</label>
												</div>
												<div class="form-group">
													<label id="delMsg" class="col-sm-3 control-label" style="color:red"></label>
												</div>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<form class="form-group">
							<div class="row">
								<div class="form-group">
									<div class="col-sm-4 col-sm-offset-3">
										<button type="button" id="delBtn" class="btn btn-primary">确定</button>&nbsp;&nbsp;
										<button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	
	   <!-- 全局js -->
	    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	    
	    <!-- iCheck -->
    	<script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js${VERSION}"></script>
	
	    <!-- Bootstrap table -->
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
		<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
		<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>
		
	    <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/minsu/common/validate.ext.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	
	
	   <!-- Page-Level Scripts -->
	   <script>
	   		$(function(){
	   			$('.i-checks').iCheck({
	                checkboxClass: 'icheckbox_square-green',
	                radioClass: 'iradio_square-green',
	            });
	   			
		 		var icon = "<i class='fa fa-times-circle'></i> ";
		 		var houseManagerMax = Number(${houseManagerMax});
	            $("#addForm").validate({
	                rules: {
	                    weight: {
	                        required: true,
							min:-houseManagerMax,
	                        max: houseManagerMax
	                    }
	                }
	            });
	   		});
	   
	   		//整租
		    function paginationParam(params) {
			    return {
			        limit: params.limit,
			        page:$('#listTable').bootstrapTable('getOptions').pageNumber,
			        houseName:$.trim($('#houseName').val()),
			        houseType:$('#houseType option:selected').val(),
			        zoName:$.trim($('#zoName').val()),
			        isWeight:$('#isWeight option:selected').val(),
			        rentWay:0
		    	};
			}
	   		
		    //合租
		    function paginationParamS(params) {
			    return {
			        limit: params.limit,
			        page:$('#listTable').bootstrapTable('getOptions').pageNumber,
			        houseNameS:$.trim($('#houseNameS').val()),
			        houseTypeS:$('#houseTypeS option:selected').val(),
			        zoNameS:$.trim($('#zoNameS').val()),
			        isWeightS:$('#isWeightS option:selected').val(),
			        rentWay:1,
			        roomType:$('#roomType option:selected').val()
		    	};
			}
		    
		  	//整租
		    function query(){
		    	$('#listTable').bootstrapTable('selectPage', 1);
		    }
		  	
	   		//合租
		    function queryS(){
		    	$('#listTableS').bootstrapTable('selectPage', 1);
		    }
		   
			// 房源类型
			function formateHouseType(value, row, index){
				var houseTypeJson = ${houseTypeJson };
				var result = '';
				$.each(houseTypeJson.data.selectEnum,function(i,n){
					if(value == i){
						result = n.text;
						return false;
					}
				});
				return result;
			}
			
			// 出租方式
			function formateRentWay(value, row, index){
				var rentWayJson = ${rentWayJson };
				var result = '';
				$.each(rentWayJson,function(i,n){
					if(value == i){
						result = n;
						return false;
					}
				});
				return result;
			}
			
			function showSaveModal(rentWay){
				var $table = bindTable(rentWay);
				var rows = $table.bootstrapTable('getSelections');
				if(rows.length == 0){
					layer.alert("至少选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
					return;
				}
				$("#rentWay_A").val(rentWay);
				$("#weight").val('');
				$("#saveMsg").text("共选择了"+rows.length+"个房源");
				$("#saveModel").modal('toggle');
			}
			
			function showDelModal(rentWay){
				var $table = bindTable(rentWay);
				var rows = $table.bootstrapTable('getSelections');
				if(rows.length == 0){
					layer.alert("至少选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
					return;
				}
				$("#rentWay_D").val(rentWay);
				$("#delMsg").text("共选择了"+rows.length+"个房源");
				$("#delModel").modal('toggle');
			}
			
			function bindTable(rentWay){
				if(rentWay == 0){
					return $('#listTable');
				}else if(rentWay == 1){
					return $('#listTableS');
				}
			}
			
			$("#saveBtn").click(function (){
				// 禁用按钮
				$(this).attr("disabled","disabled");
				
				var valid =  $("#addForm").valid();
				if(!valid){
					$(this).removeAttr("disabled");
					return;
				}
				
				var rentWay = $("#rentWay_A").val();
				var $table = bindTable(rentWay);
				var rows = $table.bootstrapTable('getSelections');
				
				var data = buildParams(rows);
				data["rentWay"] = parseInt(rentWay);
				data["weight"] = parseInt($("#weight").val());
				
				toEdit(data,"save",$table);
				
				// 启用按钮
				$(this).removeAttr("disabled");
			});
			
			$("#delBtn").click(function (){
				// 禁用按钮
				$(this).attr("disabled","disabled");
				
				var rentWay = $("#rentWay_D").val();
				var $table = bindTable(rentWay);
				var rows = $table.bootstrapTable('getSelections');
				
				var data = buildParams(rows);
				data["rentWay"] = rentWay;
				data["weight"] = 0;
				
				toEdit(data,"delete",$table);
				
				// 启用按钮
				$(this).removeAttr("disabled");
			});
			
			function buildParams(rows){
				var params = {};
				$.each(rows,function(index,row){
					params["houseFidList["+index+"]"] = row.fid;
				});
				return params;
			}
			
			function toEdit(data,operateType,$table){
				$.ajax({
					url:"house/houseMgt/editHouseWeight",
					data:data,
					dataType:"json",
					type:"post",
					success:function(result) {
						if(result.code === 0){
							//关闭弹窗
							if(operateType === "save"){
								$("#saveModel").modal('hide');
							}else if(operateType === "delete"){
								$("#delModel").modal('hide');
							}
							//刷新页面
							var pageNum = ($table.bootstrapTable('getOptions').pageNumber);
							$table.bootstrapTable('selectPage', pageNum);
							$("input[type=reset]").trigger("click");
							layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
						}else{
							layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
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
