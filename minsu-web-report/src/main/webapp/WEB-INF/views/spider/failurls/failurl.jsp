<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<base href="${basePath }">
<title>自如民宿后台管理系统</title>
<meta name="keywords" content="自如民宿后台爬虫系统">
<meta name="description" content="自如民宿后台爬虫系统">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">   
<link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico">
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}"rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}"rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">

<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">

</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<div class="col-sm-12">
			  <div class="tabs-container">
                    <div class="tab-content">

                    	<div id="tab-1" class="tab-pane active">
                    		<div class="row row-lg">
					        	<div class="col-sm-12">
					                <div class="ibox float-e-margins">
					                    <div class="ibox-content">
					                    
					                    	<div class="row">
					                    		<label class="col-sm-1 control-label mtop">url类型:</label>
						                        <div class="col-sm-2">
					                               <select class="form-control m-b" id="urlType" name="urlType">
					                                    <option value="">请选择</option>
					                                    <c:forEach items="${failUrlType }" var="obj">
						                                    <option value="${obj.key }">${obj.value }</option>
					                                    </c:forEach>
				                                    </select>
					                            </div>
					                    	 	<div class="col-sm-1">
								                    <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
								                </div> 					                    	 
					                    	 </div>
					                    	 <div class="row">
					                    	 	<!-- 
					                    	 	<div class="col-sm-1" style="margin-left: 20px;">
								                    <button class="btn btn-primary" type="button" onclick="tryAll(this);">尝试所有</button>
								                </div>
					                    	 	 -->
					                    	  	<div class="col-sm-1" style="margin-left: 20px;">
								                    <button class="btn btn-primary" type="button" onclick="trySelect(this);">尝试所选编号</button>
								                </div>
								                <div class="col-sm-1" style="margin-left: 50px;">
								                    <button class="btn btn-primary" type="button" onclick="tryType(this);">尝试所选类型</button>
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
					                            <div class="example">
					                                <table id="listTable" class="table table-bordered" style="word-break:break-all; word-wrap:break-all;"
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
								                    data-unique-id="id"
								                    data-classes="table table-hover table-condensed"
								                    data-url="/failurl/list">                    
								                    <thead>
								                        <tr>
				                            				<th data-field="idS" data-width="2%" data-checkbox="true"></th>
								                        	<th data-field="id" data-width="150px" data-align="center" >ids</th>
								                            <th data-field="url" data-width="150px" data-align="center" >url</th>
								                            <th data-field="urlType" data-width="50px" data-align="center">url类型</th>
								                            <th data-field="createDate" data-width="100px" data-align="center" data-formatter="formateDate">创建时间</th>
								                            <th data-field="tryCount" data-width="50px" data-align="center">尝试次数</th>
								                            <th data-field="lastModifyDate" data-width="100px" data-align="center" data-formatter="formateDate">最近尝试时间</th>
								                            <th data-field="failReason" data-width="200px" data-align="left">失败原因</th>
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
<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}0018"></script>


<script type="text/javascript">

    
function paginationParamS(params) {
     
    return {
    	types:$('#urlType option:selected').val(),
    	limit:params.limit,
        page:$('#listTable').bootstrapTable('getOptions').pageNumber
	};
}


function query(){
	$('#listTable').bootstrapTable('selectPage', 1);
}

function tryAll(btnObj){
	if($(btnObj).hasClass("btn-disabled")){
		   return;
	   }
	
	$(btnObj).removeClass("btn-primary").addClass("btn-disabled");
	ajaxGetSubmit("${basePath}/failurl/tryAll",{},function(result){
		   layer.alert(result.msg, {icon: 1,time: 2000, title:'提示'});	
		   $(btnObj).removeClass("btn-disabled").addClass("btn-primary");  
	   },function(result){
		   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});	
		   $(btnObj).removeClass("btn-disabled").addClass("btn-primary");  
	   });
	
}

function tryType(btnObj){
	
	var urlType = $('#urlType option:selected').val();
	if(urlType.length==0){
		 layer.alert("请选择URL类型", {icon: 1,time: 2000, title:'提示'});	
		 return;
	}
	
	if($(btnObj).hasClass("btn-disabled")){
		   return;
	   }
	
	$(btnObj).removeClass("btn-primary").addClass("btn-disabled");
 	ajaxGetSubmit("${basePath}/failurl/tryTypes?types[0]="+urlType,{},function(result){
		   layer.alert(result.msg, {icon: 1,time: 2000, title:'提示'});	
		   $(btnObj).removeClass("btn-disabled").addClass("btn-primary");  
	   },function(result){
		   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});	
		   $(btnObj).removeClass("btn-disabled").addClass("btn-primary");  
	   });
	
	
}

function trySelect(btnObj){
	var rows = $('#listTable').bootstrapTable('getSelections');
	if (rows.length == 0) {
		layer.alert("至少选择一条记录进行操作", {
			icon : 6,
			time : 2000,
			title : '提示'
		});
		return;
	}
	
	var url = "failurl/trySelectIDs";
	$.each(rows,function(i,row){
		if(i == 0){
			url += "?ids[0]="+row.id;
		}else{
			url += "&ids["+i+"]="+row.id;
		}
	});
	
	if($(btnObj).hasClass("btn-disabled")){
		   return;
	   }
	
	$(btnObj).removeClass("btn-primary").addClass("btn-disabled");
    ajaxGetSubmit("${basePath}/"+url,{},function(result){
		   layer.alert(result.msg, {icon: 1,time: 2000, title:'提示'});	
		   $(btnObj).removeClass("btn-disabled").addClass("btn-primary");  
	   },function(result){
		   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});	
		   $(btnObj).removeClass("btn-disabled").addClass("btn-primary");  
	   });
	   
	
}


function ajaxGetSubmit(url,data,successCallback,failCallback){
	$.ajax({
    type: "GET",
    url: url,
    dataType:"json",
    data: data,
    success: function (result) {
    	successCallback(result);
    },
    error: function(result) {
    	failCallback(result);
    }
 });
}
 

//格式化时间
function formateDate(value, row, index) {
	if (value != null) {
		var vDate = new Date(value);
		//return vDate.format("yyyy-MM-dd HH:mm:ss");
		return value;
	} else {
		return "";
	}
}
	
</script>
</body>

</html>
