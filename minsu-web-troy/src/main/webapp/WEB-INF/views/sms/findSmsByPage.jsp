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
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row row-lg">
		
			 <div class="col-sm-12">
               <div class="ibox float-e-margins">
                    <div class="ibox-content">
			           <form class="form-horizontal m-t" id="commentForm">
			           		<div class="form-group">
			                   <label class="col-sm-1 control-label">消息编码：</label>
			                   <div class="col-sm-2">
			                       <input id="smsCode" name="smsCode" minlength="1" type="text" class="form-control" required="true" aria-required="true">
			                   </div>
			                   <label class="col-sm-1 control-label">消息名称：</label>
			                   <div class="col-sm-2">
			                       <input id="smsName" name="smsName" minlength="1" type="text" class="form-control" required="true" aria-required="true">
			                   </div>
			                   <label class="col-sm-1 control-label">消息类型：</label>
                               <div class="col-sm-2">
			                       <select class="form-control m-b" id="smsType" name="smsType" >
			                           <option value="">全部</option>
                                       <option value="1">短信消息</option>
                                       <option value="2">邮箱消息 </option>
                                       <option value="3">消息</option>
                                       <option value="4">其他消息</option>
                                   </select>
			                   </div>
                               
                               <div class="col-sm-1">
			                       <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
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
					<button class="btn btn-primary" type="button" onclick="toAddSms(0);">
						<i class="fa fa-save"></i>&nbsp;添加
					</button>
					<button class="btn btn-info " type="button" onclick="toAddSms(1);">
						<i class="fa fa-edit"></i>&nbsp;编辑
					</button>
					<button class="btn btn-default " type="button"
						onclick="enabledSms();">
						<i class="fa fa-delete"></i>&nbsp;启用
					</button>
					<div class="example-wrap">
						<div class="example">
							<table id="listTable" class="table table-hover table-bordered" data-click-to-select="true"
								data-toggle="table" data-side-pagination="server"
								data-pagination="true" data-page-list="[10,20,50]"
								data-pagination="true" data-page-size="10"
								data-pagination-first-text="首页" data-pagination-pre-text="上一页"
								data-pagination-next-text="下一页" data-pagination-last-text="末页"
								data-content-type="application/x-www-form-urlencoded"
								data-query-params="paginationParam" data-method="get"
								data-single-select="true"
								data-height="520" data-url="${basePath }sms/querySmsByPage">
								<thead>
									<tr>
										<th data-width="5%"  data-checkbox="true" ></th>
										<th data-width="10%" data-field="smsCode">消息编码</th>
										<th data-width="10%" data-field="smsName">消息名称</th>
										<th data-width="50%" data-field="smsComment" data-formatter="getComment">消息内容</th>
										<th data-width="15%" data-field="smsType"  data-align="center" data-formatter="getSmsType">消息类型</th>
										<th data-width="15%" data-field="smsEnabled"  data-align="center" data-formatter="getSmsEnabled">是否启用</th>
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
	<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	<!-- 自定义js -->
	<script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	<!-- Bootstrap table -->
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
	<!-- layer javascript -->
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
	<!-- <script src="js/demo/layer-demo.js"></script> -->
	<!-- layerDate plugin javascript -->
	<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>

	<script type="text/javascript">

    	function paginationParam(params) {
		    return {
		        limit: params.limit,
		        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
		        fid:$.trim($('#fid').val()),
		        smsCode:$.trim($('#smsCode').val()),
		        smsName:$.trim($('#smsName').val()),
		        smsType:$('#smsType').val()
	    	};
		}
	    function query(){
	    	$('#listTable').bootstrapTable('selectPage', 1);
	    }
	    // 格式化时间
		function formateDate(value, row, index) {
			return value;
		}
	    /**
	    *添加短信模板
	    */
		function toAddSms(flag){
	    	
	    	var url= "sms/addSmsTeplate?editFlag="+flag;
	    	
	    	if(flag==1||flag=="1"){
	    		var selectVar=$('#listTable').bootstrapTable('getSelections');
				if(selectVar.length == 0){
					alert("请选择一条记录");
					return;
				}
				url = url +"&smsFid="+selectVar[0].fid;
	    	}
            $.openNewTab(new Date().getTime(),url, "添加修改模板");
			//window.location.href=url;
		}
	    //启用短信模板
	    function enabledSms(){
	    	var selectVar=$('#listTable').bootstrapTable('getSelections');
			if(selectVar.length == 0){
				alert("请选择一条记录");
				return;
			}
			var smsEnabled =  selectVar[0].smsEnabled;
			if(smsEnabled == 1 ){
				alert("模板已启用");
				return;
			}
			window.location.href="sms/enabledSms?smsFid="+selectVar[0].fid;
            //$('#listTable').bootstrapTable('selectPage', 1);
	    }
	    //状态选择
		function getSmsEnabled(value, row, index){
			if(value==0){
				return "否";
			}else{
				return "是";
			}
		}
		   //消息类型
		function getSmsType(value, row, index){
			if(value==1){
				return "短信消息";
			}else if(value==2){
				return "邮箱消息";
			}else if(value==3){
				return "消息";
			}else{
				return "其他";
			}
		}
	    
	    function getComment(value,row,index){
	    	if(row.smsType==2){
	    		return "";
	    	} else {
	    		return value;
	    	}
	    }
	</script>

</body>

</html>
