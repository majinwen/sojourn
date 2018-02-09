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
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<div class="form-group">
							<div class="row">
								<label class="col-sm-1 control-label mtop">房源编号:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control m-b" id="houseSn" name="houseSn">
								</div>
								<label class="col-sm-1 control-label mtop">房东姓名:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control m-b" id="landlordName" name="landlordName"/>
								</div>
								<label class="col-sm-1 control-label mtop">房东手机:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control m-b" id="landlordMobile" name="landlordMobile">
								</div>
								<label class="col-sm-1 control-label mtop">实勘编号:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control m-b" id="surveySn" name="surveySn">
								</div>
							</div>
							<div class="row">
                                <label class="col-sm-1 control-label mtop">维护管家姓名:</label>	
                                <div class="col-sm-2">
                                    <input type="text" class="form-control m-b" id="empGuardName" name="empGuardName"/>
                                </div>
                                <label class="col-sm-1 control-label mtop">维护管家系统号:</label>	
                                <div class="col-sm-2">
                                    <input type="text" class="form-control m-b" id="empGuardCode" name="empGuardCode"/>
                                </div>
                                <label class="col-sm-1 control-label mtop">维护管家手机:</label>
	                            <div class="col-sm-2">
                                 		<input type="text" class="form-control m-b" id="empGuardMobile" name="empGuardMobile">
                                </div>
                                <label class="col-sm-1 control-label mtop">实勘结果:</label>
	                            <div class="col-sm-2">
                                 	<select class="form-control m-b" id="surveyResult" name="surveyResult">
	                                    <option value="">请选择</option>
	                                    <c:forEach items="${surveyResultMap }" var="obj">
	                                    	<option value="${obj.key }">${obj.value }</option>
	                                    </c:forEach>
                                    </select>
                                </div>
	                        </div>
							<div class="row">
                                <label class="col-sm-1 control-label mtop">地推管家姓名:</label>	
                                <div class="col-sm-2">
                                    <input type="text" class="form-control m-b" id="empPushName" name="empPushName"/>
                                </div>
                                <label class="col-sm-1 control-label mtop">地推管家系统号:</label>	
                                <div class="col-sm-2">
                                    <input type="text" class="form-control m-b" id="empPushCode" name="empPushCode"/>
                                </div>
                                <label class="col-sm-1 control-label mtop">地推管家手机:</label>
	                            <div class="col-sm-2">
                                 		<input type="text" class="form-control m-b" id="empPushMobile" name="empPushMobile">
                                </div>
                                <label class="col-sm-1 control-label mtop">是否审阅:</label>
	                            <div class="col-sm-2">
                                 	<select class="form-control m-b" id="isAudit" name="isAudit">
	                                    <option value="">请选择</option>
	                                    <option value="0">否</option>
	                                    <option value="1">是</option>
                                    </select>
                                </div>
	                        </div>
							<div class="row">
	                            <label class="col-sm-1 control-label mtop">国家:</label>
		                        <div class="col-sm-2">
	                                <select class="form-control m-b m-b" id="nationCode">
	                                </select>
	                            </div>
	                            <label class="col-sm-1 control-label mtop">省份:</label>
		                        <div class="col-sm-2">
	                                <select class="form-control m-b " id="provinceCode">
	                                </select>
	                            </div>
	                            <label class="col-sm-1 control-label mtop">城市:</label>
		                        <div class="col-sm-2">
	                                <select class="form-control m-b" id="cityCode">
	                                </select>
	                            </div>
	                            <label class="col-sm-1 control-label mtop">区域:</label>
		                        <div class="col-sm-2">
	                                <select class="form-control m-b" id="areaCode">
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
		</div>
		<!-- Panel Other -->
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<div class="row row-lg">
					<div class="col-sm-12">
						<div class="example-wrap">
							<div class="example">
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
				                    data-url="house/houseSurvey/showSurveyHouse">                    
				                    <thead>
				                        <tr>
				                            <th data-field="fid" data-visible="false"></th>
				                            <th data-field="id" data-width="2%" data-radio="true"></th>
				                            <th data-field="surveySn" data-width="100px" data-align="center" data-formatter="formateSurveySn">实勘编号</th>
				                            <th data-field="houseSn" data-width="100px" data-align="center">房源编号</th>
				                            <th data-field="roomsSn" data-width="100px" data-align="center">房间编号</th>
				                            <th data-field="houseName" data-width="200px" data-align="center">房源名称</th>
				                            <th data-field="houseAddr" data-width="200px" data-align="center">房源地址</th>
				                            <th data-field="surveyEmpName" data-width="100px" data-align="center">实勘专员</th>
				                            <th data-field="surveyDate" data-width="100px" data-align="center" data-formatter="formateDate">实勘日期</th>
				                            <th data-field="surveyResult" data-width="100px" data-align="center" data-formatter="formateSurveyResult">实勘结果</th>
				                            <th data-field="auditEmpName" data-width="100px" data-align="center">审阅人</th>
				                            <th data-field="auditDate" data-width="100px" data-align="center" data-formatter="formateDate">审阅日期</th>
				                            <th data-field="operate" data-width="100px" data-align="center" data-formatter="formateOperate">操作</th>
				                        </tr>
				                    </thead>
				                    </table>             
	                            </div>
							</div>
						</div>
						<!-- End Example Pagination -->
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>

	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}001"></script>

	<!-- Page-Level Scripts -->
	<script>
		$(function(){
			// 多级联动
			var areaJson = ${cityTreeList};
			var defaults = {
				geoJson	: areaJson
			};
			geoCascade(defaults);
		});
		
	    function paginationParam(params) {
		    return {
		    	limit: params.limit,
		        page:$('#listTable').bootstrapTable('getOptions').pageNumber,
		        empGuardName:$.trim($('#empGuardName').val()),
		        empGuardCode:$.trim($('#empGuardCode').val()),
		        empGuardMobile:$.trim($('#empGuardMobile').val()),
		        surveyResult:$('#surveyResult option:selected').val(),
		        empPushName:$.trim($('#empPushName').val()),
		        empPushCode:$.trim($('#empPushCode').val()),
		        empPushMobile:$.trim($('#empPushMobile').val()),
		        isAudit:$('#isAudit option:selected').val(),
		        landlordName:$.trim($('#landlordName').val()),
		        landlordMobile:$.trim($('#landlordMobile').val()),
		        houseSn:$.trim($('#houseSn').val()),
		        surveySn:$.trim($('#surveySn').val()),
		        nationCode:$('#nationCode option:selected').val(),
		        provinceCode:$('#provinceCode option:selected').val(),
		        cityCode:$('#cityCode option:selected').val(),
		       	areaCode:$('#areaCode option:selected').val()
	    	};
		}
	    
	    function query(){
	    	$('#listTable').bootstrapTable('selectPage', 1);
	    }
		
		// 实勘编号
		function formateSurveySn(value, row, index){
		    if(!checkNullOrBlank(value)){
			    return "<a href='javascript:;' class='oneline line-width' title=\""+value+"\" <customtag:authtag authUrl='house/houseSurvey/surveyDetail'>onclick='showSurveyDetail(\""+row.houseBaseFid+"\");'</customtag:authtag>>"+value+"</a>";
		    }
			return "";
		}
		
		// 格式化时间
		function formateDate(value, row, index) {
			if (value != null) {
				var vDate = new Date(value);
				return vDate.format("yyyy-MM-dd HH:mm:ss");
			} else {
				return "";
			}
		}
		
		// 实勘结果
		function formateSurveyResult(value, row, index){
			var surveyResultJson = ${surveyResultJson };
			var result = '';
			$.each(surveyResultJson,function(i,n){
				if(value == i){
					result = n;
					return false;
				}
			});
			return result;
		}
		
		//操作
		function formateOperate(value, row, index){
			if(checkNullOrBlank(row.recordStatus) || (row.isAudit == 0 && row.recordStatus == 0)){
			    return "<a href='javascript:;' class='oneline line-width' <customtag:authtag authUrl='house/houseSurvey/uploadHouseSurvey'>onclick='uploadHouseSurvey(\""+row.houseFid+"\");'</customtag:authtag>><span>上传实勘</span></a>";
			} else if(row.isAudit == 0 && row.recordStatus == 1){
			    return "<a href='javascript:;' class='oneline line-width' <customtag:authtag authUrl='house/houseSurvey/editHouseSurvey'>onclick='toEditHouseSurvey(\""
			    		+ row.houseFid+"\");'</customtag:authtag>><span>修改</span></a>&nbsp;&nbsp;"
			    		+ "<a href='javascript:;' class='oneline line-width' <customtag:authtag authUrl='house/houseSurvey/toAuditHouseSurvey'>onclick='toAuditHouseSurvey(\""
	    				+ row.houseFid+"\");'</customtag:authtag>><span>审阅</span></a>";
			}
		}
		
	    function checkNullOrBlank(obj){
	    	return typeof obj == "undefined" || obj == null || $.trim(obj).length == 0;
	    }
		

		//展示实勘详情
		function showSurveyDetail(houseBaseFid){
			$.openNewTab(new Date().getTime(),'house/houseSurvey/surveyDetail?houseBaseFid='+houseBaseFid, "实勘详情");
		}
		
		function uploadHouseSurvey(houseBaseFid){
			$.openNewTab(new Date().getTime(),'house/houseSurvey/uploadHouseSurvey?houseBaseFid='+houseBaseFid, "上传实勘");
		}
		
		function toEditHouseSurvey(houseBaseFid){
			$.openNewTab(new Date().getTime(),'house/houseSurvey/editHouseSurvey?houseBaseFid='+houseBaseFid, "修改实勘");
		}
		
		function toAuditHouseSurvey(houseBaseFid){
			$.openNewTab(new Date().getTime(),'house/houseSurvey/toAuditHouseSurvey?houseBaseFid='+houseBaseFid, "审阅实勘");
		}
	</script>
</body>
</html>