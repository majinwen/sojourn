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
<meta name="keywords" content="自如民宿后台管理系统">
<meta name="description" content="自如民宿后台管理系统">
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
	<div class="row row-lg">
	    <div class="tabs-container">
             <ul class="nav nav-tabs">
				<li class="active">
					<a href="javascript:void(0)">整租列表</a>
				</li>
			</ul>
          </div>
		 <div class="col-sm-12">
		       <div class="ibox float-e-margins">
               <div class="ibox-content">
                      <div class="row">
	                		<div class="col-sm-12">
		                		<label class="col-sm-1 control-label mtop">从:</label>
	                            <div class="col-sm-2 ">
	                                 <input id="beginTime"  value=""  name="beginTime" class="laydate-icon form-control layer-date">
	                            </div>
	                            
	                            <label class="col-sm-1 control-label mtop">到:</label>
	                            <div class="col-sm-2">
	                                 <input id="endTime" value="" name="endTime" class="laydate-icon form-control layer-date">
	                            </div>
	                            
	                            <label class="col-sm-1 control-label mtop">城市:</label>
	                             <div class="col-sm-2">
		                               <select class="form-control m-b" id="cityCode" name="cityCode" >
		                               <option value="">请选择</option>
		                               <c:forEach items="${cityList}" var="obj">
											<option value="${obj.code }">${obj.name }</option>
										</c:forEach>
		                              </select>
	                             </div>
                            </div>
                            <div class="col-sm-12">
	                            <div class="ibox float-e-margins">
				                     <div class="ibox-content">
							           <form id="activityForm" action="activity/addActivityInfo" class="form-horizontal" onsubmit="document.charset='utf-8'" method="post" >
								           	<div class="form-group">
									           	<div class="row">
										           	<div class="col-sm-4 control-label mtop">
						                              <span>
						                              <customtag:authtag authUrl="houseDetail/houseDetailExcel">
						                              <a href="javascript:void(0)" onclick="CommonUtils.jumpPageOrExportFile(new CommonUtils.Param('url','houseDetail/houseDetailExcel'),new CommonUtils.Param('rentWay','0'),new CommonUtils.Param('beginTime',$('#beginTime').val()),new CommonUtils.Param('endTime',$('#endTime').val()),new CommonUtils.Param('methodName','getHouseFilterInfo'))">房源订单漏斗表，整租部分</a>
						                              </customtag:authtag>
						                              </span>
					                                </div>
					                                
					                                <div class="col-sm-4 control-label mtop">
						                              <span>
						                              <customtag:authtag authUrl="houseDetail/houseDetailExcel">
						                              <a href="javascript:void(0)" onclick="CommonUtils.jumpPageOrExportFile(new CommonUtils.Param('url','houseDetail/houseDetailExcel'),new CommonUtils.Param('rentWay','1'),new CommonUtils.Param('beginTime',$('#beginTime').val()),new CommonUtils.Param('endTime',$('#endTime').val()),new CommonUtils.Param('methodName','getHouseFilterInfo'))">房源订单漏斗表，分租部分</a>
						                              </customtag:authtag>
						                              </span>
					                                </div>
					                            </div>
									           	<div class="hr-line-dashed"></div>
									           	<div class="row">
										           	<div class="col-sm-4 control-label mtop">
						                              <span>
						                              <customtag:authtag authUrl="houseDetail/houseDetailExcel">
						                              <a href="javascript:void(0)" onclick="CommonUtils.jumpPageOrExportFile(new CommonUtils.Param('url','houseDetail/houseDetailExcel'),new CommonUtils.Param('rentWay','0'),new CommonUtils.Param('beginTime',$('#beginTime').val()),new CommonUtils.Param('endTime',$('#endTime').val()),new CommonUtils.Param('methodName','getHouseEvaInfo'))">房源评价率，整租部分</a>
						                              </customtag:authtag>
						                              </span>
					                                </div>
					                                
					                                <div class="col-sm-4 control-label mtop">
						                              <span>
						                              <customtag:authtag authUrl="houseDetail/houseDetailExcel">
						                              <a href="javascript:void(0)" onclick="CommonUtils.jumpPageOrExportFile(new CommonUtils.Param('url','houseDetail/houseDetailExcel'),new CommonUtils.Param('rentWay','1'),new CommonUtils.Param('beginTime',$('#beginTime').val()),new CommonUtils.Param('endTime',$('#endTime').val()),new CommonUtils.Param('methodName','getHouseEvaInfo'))">房源评价率，分租部分</a>
						                              </customtag:authtag>
						                              </span>
					                                </div>
					                            </div>
									           	<div class="hr-line-dashed"></div>
									           	
									           	<div class="row">
										           	<div class="col-sm-4 control-label mtop">
						                              <span>
						                              <customtag:authtag authUrl="houseDetail/houseDetailExcel">
						                              <a href="javascript:void(0)" onclick="CommonUtils.jumpPageOrExportFile(new CommonUtils.Param('url','houseDetail/houseDetailExcel'),new CommonUtils.Param('rentWay','0'),new CommonUtils.Param('beginTime',$('#beginTime').val()),new CommonUtils.Param('endTime',$('#endTime').val()),new CommonUtils.Param('methodName','getHouseAuditInfo'))">房源审核，整租部分</a>
						                              </customtag:authtag>
						                              </span>
					                                </div>
					                                
					                                <div class="col-sm-4 control-label mtop">
						                              <span>
						                              <customtag:authtag authUrl="houseDetail/houseDetailExcel">
						                              <a href="javascript:void(0)" onclick="CommonUtils.jumpPageOrExportFile(new CommonUtils.Param('url','houseDetail/houseDetailExcel'),new CommonUtils.Param('rentWay','1'),new CommonUtils.Param('beginTime',$('#beginTime').val()),new CommonUtils.Param('endTime',$('#endTime').val()),new CommonUtils.Param('methodName','getHouseAuditInfo'))">房源审核，分租部分</a>
						                              </customtag:authtag>
						                              </span>
					                                </div>
					                            </div>
									           	<div class="hr-line-dashed"></div>
									           	
									           	
									           	<div class="row">
										           	<div class="col-sm-4 control-label mtop">
						                              <span>
						                              <customtag:authtag authUrl="houseDetail/houseDetailExcel">
						                              <a href="javascript:void(0)" onclick="CommonUtils.jumpPageOrExportFile(new CommonUtils.Param('url','houseDetail/houseDetailExcel'),new CommonUtils.Param('rentWay','0'),new CommonUtils.Param('beginTime',$('#beginTime').val()),new CommonUtils.Param('endTime',$('#endTime').val()),new CommonUtils.Param('methodName','getHouseChannelInfo'))">房源渠道，整租部分</a>
						                              </customtag:authtag>
						                              </span>
					                                </div>
					                                
					                                <div class="col-sm-4 control-label mtop">
						                              <span>
						                              <customtag:authtag authUrl="houseDetail/houseDetailExcel">
						                              <a href="javascript:void(0)" onclick="CommonUtils.jumpPageOrExportFile(new CommonUtils.Param('url','houseDetail/houseDetailExcel'),new CommonUtils.Param('rentWay','1'),new CommonUtils.Param('beginTime',$('#beginTime').val()),new CommonUtils.Param('endTime',$('#endTime').val()),new CommonUtils.Param('methodName','getHouseChannelInfo'))">房源渠道，分租部分</a>
						                              </customtag:authtag>
						                              </span>
					                                </div>
					                            </div>
									           	<div class="hr-line-dashed"></div>
									           	<div class="row">
										           	<div class="col-sm-4 control-label mtop">
						                              <span>
						                              <customtag:authtag authUrl="orderDetail/orderDetailExcel">
						                              <a href="javascript:void(0)" onclick="CommonUtils.jumpPageOrExportFile(new CommonUtils.Param('url','orderDetail/orderDetailExcel'),new CommonUtils.Param('beginTime',$('#beginTime').val()),new CommonUtils.Param('endTime',$('#endTime').val()),new CommonUtils.Param('methodName','getOrderStaticsInfo'))">订单明细表</a>
						                              </customtag:authtag>
						                              </span>
					                                </div>
					                            </div>
									           	<div class="hr-line-dashed"></div>
									           	<div class="row">
										           	<div class="col-sm-4 control-label mtop">
						                              <span>
						                              <customtag:authtag authUrl="houseDetail/houseDetailExcel">
						                              <a href="javascript:void(0)" onclick="CommonUtils.jumpPageOrExportFile(new CommonUtils.Param('url','houseDetail/houseDetailExcel'),new CommonUtils.Param('rentWay','0'),new CommonUtils.Param('beginTime',$('#beginTime').val()),new CommonUtils.Param('endTime',$('#endTime').val()),new CommonUtils.Param('methodName','getHousePhotoInfo'))">房源拍摄需求，整租部分</a>
						                              </customtag:authtag>
						                              </span>
					                                </div>
					                                
					                                <div class="col-sm-4 control-label mtop">
						                              <span>
						                              <customtag:authtag authUrl="houseDetail/houseDetailExcel">
						                              <a href="javascript:void(0)" onclick="CommonUtils.jumpPageOrExportFile(new CommonUtils.Param('url','houseDetail/houseDetailExcel'),new CommonUtils.Param('rentWay','1'),new CommonUtils.Param('beginTime',$('#beginTime').val()),new CommonUtils.Param('endTime',$('#endTime').val()),new CommonUtils.Param('methodName','getHousePhotoInfo'))">房源拍摄需求，分租部分</a>
						                              </customtag:authtag>
						                              </span>
					                                </div>
									           	</div>
									           	<div class="hr-line-dashed"></div>
									           		<div class="row">
										           	<div class="col-sm-4 control-label mtop">
						                              <span>
						                              <customtag:authtag authUrl="houseDetail/userCusInfoExcel">
						                              <a href="javascript:void(0)" onclick="CommonUtils.jumpPageOrExportFile(new CommonUtils.Param('url','houseDetail/userCusInfoExcel'),new CommonUtils.Param('beginTime',$('#beginTime').val()),new CommonUtils.Param('endTime',$('#endTime').val()),new CommonUtils.Param('methodName','getUserCusInfo'))">客户信息导出</a>
						                              </customtag:authtag>
						                              </span>
					                                </div>
					                               </div>
								           	</div>
							           	</form>
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
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}002"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}005" type="text/javascript"></script>
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>


<script type="text/javascript">
   
   function ajaxGetSubmit(url,data,callback){
		$.ajax({
	    type: "GET",
	    url: url,
	    dataType:"json",
	    data: data,
	    success: function (result) {
	    	callback(result);
	    },
	    error: function(result) {
	       alert("error:"+result);
	    }
	 });
	}
   
    $(function (){
		//初始化日期
		CommonUtils.datePickerFormat('beginTime');
		CommonUtils.datePickerFormat('endTime');
	});
	
</script>
</body>

</html>
