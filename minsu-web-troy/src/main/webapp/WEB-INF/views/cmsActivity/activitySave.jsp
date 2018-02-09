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
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/jsTree/style.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
</head>
 <body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
			           <form id="activityForm" action="" class="form-horizontal" onsubmit="document.charset='utf-8'" method="post" >
			           		<input type="hidden" name="editFlag" value="${editFlag}" type="text" class="form-control m-b">
							<div class="form-group">
								<div class="row">
									<label class="col-sm-1 control-label mtop">活动名称:</label>
									<div class="col-sm-2">
										<input id="activityName" name="activityName" value="" type="text" class="form-control m-b" maxlength="20">
									</div>
									<label class="col-sm-1 control-label mtop">活动城市:</label>
									<div class="col-sm-2">
										<select id="cityCode" name="cityCode" class="form-control m-b">
											<option value="0">全部</option>
											<c:forEach items="${cityMap}" var="obj">
												<option value="${obj.key }">${obj.value }</option>
											</c:forEach>
										</select> <span class="help-block m-b-none"></span>
									</div>
								</div>
								<div class="hr-line-dashed"></div>
								<div class="row">
									<label class="col-sm-1 control-label mtop">活动开始日期:</label>
									<div class="col-sm-2">
										<input id="startTime" name="startTime" value="" class="laydate-icon form-control layer-date">
									</div>
									<label class="col-sm-1 control-label mtop">到:</label>
									<div class="col-sm-2">
										<input id="endTime" name="endTime" value="" class="laydate-icon form-control layer-date">
									</div>
								</div>
								<div class="hr-line-dashed"></div>
								<div class="row">
									<label class="col-sm-1 control-label mtop">活动形式:</label>
									<div class="col-sm-2">
										<select id="activityType" name="activityType" class="form-control m-b m-b" class="{required:true}">
											<option value="1">免佣金</option>
										</select>
									</div>
									<label class="col-sm-1 control-label mtop">活动对象:</label>
									<div class="col-sm-2">
										<select id="userRole" name="userRole" class="form-control">
											<option value="0">全部</option>
											<option value="1">种子房东</option>
										</select>
									</div>

                                    <label class="col-sm-1 control-label mtop">免佣天数:</label>
                                    <div class="col-sm-2">
                                        <input id="activityCut" name="activityCut" value="" type="text" class="form-control m-b" maxlength="20">
                                    </div>

								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button id="saveBtn" class="btn btn-primary" type="button">保存内容</button>
									<button id="goBackBtn" class="btn btn-primary">返回列表</button>
								</div>
							</div>
						</form>
			        </div>
                </div>
            </div>
        </div>
    </div>
	

    <!-- 全局js -->
    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>

    <!-- jsTree plugin javascript -->
    <script src="${staticResourceUrl}/js/plugins/jsTree/jstree.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
    <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
    <script src="${basePath}js/minsu/common/commonUtils.js${VERSION}010" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
    
    <script>
        $(function (){
        	//初始化日期
        	CommonUtils.datePickerFormat('startTime');
        	CommonUtils.datePickerFormat('endTime');
        });
        
        $("#saveBtn").click(function(){
        	if($("#activityName").val() == ''){
        		layer.alert("活动名称不能为空", {icon: 6,time: 2000, title:'提示'});
        		return;
        	}
        	if($("#startTime").val() == ''){
        		layer.alert("开始时间不能为空", {icon: 6,time: 2000, title:'提示'});
        		return;
        	}
        	if($("#endTime").val() == ''){
        		layer.alert("结束时间不能为空", {icon: 6,time: 2000, title:'提示'});
        		return;
        	}

            if($("#activityCut").val() == ''){
                layer.alert("请填写免佣天数", {icon: 6,time: 2000, title:'提示'});
                return;
            }

            if($("#activityCut").val() > 0){

            }else{
                layer.alert("请填写正确的免佣天数", {icon: 6,time: 2000, title:'提示'});
                return;
            }


        	
        	var array = $("#activityForm").serializeArray();
        	var activity = toJsonString(array);
	    	$.ajax({
				url:"cmsactivity/saveActivity",
				data:{
					activity : activity
				},
				dataType:"json",
				type:"post",
				async: false,
				success:function(data) {
					if(data.code === 0){
						layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
						setTimeout(function(){
							window.location.href='cmsactivity/toActivityList';
						},1500);
					}else{
						layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
					}
				},
				error:function(result){
					layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
				}
			});
		})
		
		
		$("#goBackBtn").click(function(){
			window.location.href='cmsactivity/toActivityList';
			return false;
		})
		
		function toJsonString(array) {
			var json = {};
			$.each(array, function(i, n) {
				var key = n.name;
				var value = n.value;
				if(key == "startTime" && $.trim(value).length != 0){
					var date = new Date(value);
					json[key] = date;
				}else if(key == "endTime" && $.trim(value).length != 0){
					var date = new Date(value);
					json[key] = date;
				}else if ($.trim(value).length != 0) {
					json[key] = value;
				}
			});
			return JSON.stringify(json);
		}
	</script>
</body>
</html>
