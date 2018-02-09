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
				                    <label class="col-sm-1 control-label left2">运营专员姓名：</label>
				                    <div class="col-sm-2 left2">
				                        <input id="empName" name="empName" minlength="1" type="text" class="form-control" required="true" aria-required="true">
				                    </div>
				                    <label class="col-sm-1 control-label left2">运营专员系统号：</label>
				                    <div class="col-sm-2 left2">
				                        <input id="empCode" name="empCode" minlength="1" type="text" class="form-control" required="true" aria-required="true">
				                    </div>
				                    <label class="col-sm-1 control-label left2">运营专员手机号：</label>
				                    <div class="col-sm-2 left2">
				                        <input id="empPhone" name="empPhone" minlength="1" type="text" class="form-control" required="true" aria-required="true">
				                    </div>
				               	</div>
				            </div>
			           		<div class="form-group">
				           		<div class="row">
				           			<label class="col-sm-1 control-label left2">国家：</label>
			                        <div class="col-sm-2 left2">
		                                <select class="form-control" id="nationCode">
		                                </select>
		                            </div>
		                            <label class="col-sm-1 control-label left2">省：</label>
			                        <div class="col-sm-2 left2">
		                                <select class="form-control" id="provinceCode">
		                                </select>
		                            </div>
		                            <label class="col-sm-1 control-label left2">房源城市：</label>
			                        <div class="col-sm-2 left2">
		                                <select class="form-control" id="cityCode">
		                                </select>
		                            </div>
		                            <!-- 适配新增区域管家城市级联 start -->
			                        <div class="col-sm-2" style="display: none;">
		                                <select class="form-control m-b" id="areaCode">
		                                </select>
		                            </div>
		                            <!-- 适配新增区域管家城市级联 end -->
	                                <div class="col-sm-2 left2">
				                        <button class="btn btn-primary" type="button" onclick="CommonUtils.query();"><i class="fa fa-search"></i>&nbsp;查询</button>
				                    </div>
			           			</div>
			                </div>
			           </form>
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
				  <button class="btn btn-primary" type="button"  onclick='Guard.saveGuardArea();' data-toggle='modal' data-target='#myModal1'>
						<i class="fa fa-save"></i>&nbsp;新增运营专员
					</button>
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
		                    data-single-select="true" 
		                    data-height="475"
		                    data-url="${basePath }guard/queryGaurdAreaByPage">                    
		                    <thead>
		                           <tr>
										<th data-field="empName"  data-width="15%" >运营专员</th>
										<th data-field="empCode" data-width="10%"  >运营专员系统号</th>
										<th data-field="empPhone" data-width="10%" >运营专员手机号</th>
										<th data-field="nationCode" data-width="8%" data-align="center"   data-formatter="Guard.getAddress">维护区域</th>
										<th data-field="isDel" data-width="8%" data-align="center"   data-formatter="Guard.getIsDel">状态</th>
										<th data-field="createDate" data-width="12%"data-formatter="CommonUtils.formateDate" >创建日期</th>
										<th data-field="test" data-align="center"  data-width="20%" data-formatter="Guard.menuOperData">操作</th>
									</tr>
		                    </thead>             
                           </table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 添加 菜单  弹出框 -->
<div class="modal inmodal fade" id="myModal1" tabindex="-1"  role="dialog" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content animated bounceInRight">
       <div class="modal-header">
          <button type="button" class="close" onclick="Guard.closeTwoWindow();"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
          </button>
          <h4 class="modal-title" id="guardAreaTitle">新增区域运营专员</h4>
       </div>
       <div class="modal-body">
	        <div class="wrapper wrapper-content animated fadeInRight">
		        <div class="row">
		          <div class="col-sm-14">
		               <div class="ibox float-e-margins">
		                   <div class="ibox-content">
				           <form id="guardForm" action="" method="post">
			                   <div class="form-group">
			                       <label class="col-sm8 control-label" style="margin-left: 165px;">运营专员行政区域</label>
			                   </div>
			                   <div class="form-group">
				                   <div class="row">
									   <label class="col-sm-1 control-label left2" style="width: 50px;">国家：</label>
					                   <div class="col-sm-2 left2"  style="margin-left: 20px;width: 130px;">
					                       <select class="form-control m-b m-b required" id="nationCodeNew" name="nationCode">
			                               </select>
					                   </div>
					                   <label class="col-sm-1 control-label left2" style="margin-left: 30px;">省：</label>
					                   <div class="col-sm-2 left2" style="margin-left: 120px;width: 130px;margin-top: -25px;">
					                       <select class="form-control m-b m-b" id="provinceCodeNew"   name="provinceCode">
		                                   </select>
					                   </div>
							        </div>
			                   </div>
						       <div class="form-group">
						           <div class="row">
									   <label class="col-sm-1 control-label left2" style="width: 50px;">房源城市：</label>
			                           <div class="col-sm-2 left2" style="margin-left: 20px;width: 130px;">
				                           <select class="form-control m-b m-b" id="cityCodeNew"   name="cityCode"> 
				                           </select>
	                                   </div>
	                                   <label class="col-sm-1 control-label left2" style="margin-left: 30px;">房源行政区：</label>
			                           <div class="col-sm-2 left2" style="margin-left: 50px;width: 130px;margin-top: 13px;">
				                           <select class="form-control m-b m-b" id="areaCodeNew"  name="areaCode" > 
				                           </select>
	                                   </div>
					               </div>
			                   </div>
						       <div class="hr-line-dashed"></div>
						       <div class="form-group">
			                       <label class="col-sm8 control-label" style="margin-left: 190px;">运营专员信息</label>
			                   </div>
			                     <div id="guardSelect">
				                     <button type="button" class="btn btn-white"   data-toggle="modal"   style="margin-left: 180px;" data-target="#myModal5">
		                                  	员工选择
		                             </button>
	                             </div>
	                              <div class="row" style="margin-top: 20px;">
		                               	<div class="form-group">
										<label class="col-sm-2 control-label">运营专员姓名:</label>
										<div class="col-sm-2" style="width: 110px;margin-left: 0px;margin-top: -7px;">
										  <input type="text" class="form-control" readonly="readonly" name="empName" id="empNameOne" value="">
										</div>
										<label class="col-sm-2 control-label" style="margin-left: 45px;">运营专员手机号:</label>
										<div class="col-sm-2" style="width: 130px;margin-left: 150px;margin-top: -30px;">
										  <input type="text" class="form-control" readonly="readonly" name="empPhone" id="empPhoneOne"  value="">
										</div>
								       </div>
	                               </div>
	                               
	                                <div class="row" style="margin-top: 30px;">
		                               	<div class="form-group">
										<label class="col-sm-2 control-label">运营专员系统号:</label>
										<div class="col-sm-2" style="width: 110px;margin-left: 0px;margin-top: -7px;">
										  <input type="text" class="form-control"  readonly="readonly" name="empCode" id="empCodeOne" value="">
										</div>
								       </div>
	                               </div>
			                 
			                 
			                   <div class="hr-line-dashed"></div>
							  
							   <div class="form-group">
			                       <label class="col-sm8 control-label" style="margin-left: 190px;">运营专员历史记录</label>
			                   </div>
			                   
			                       <div class="row" >
		                               	<div class="form-group" id="guardHistory" style="display: none;">
										<label class="col-sm8 control-label" style="margin-left: 110px;">中国   &nbsp;&nbsp;北京   &nbsp;&nbsp; 北京市  &nbsp;&nbsp;朝阳区</label></br>
										<label class="col-sm8 control-label" style="margin-left: 110px;">中国   &nbsp;&nbsp;北京   &nbsp;&nbsp; 北京市  &nbsp;&nbsp;朝阳区</label></br>
										<label class="col-sm8 control-label" style="margin-left: 110px;">中国   &nbsp;&nbsp;北京   &nbsp;&nbsp; 北京市  &nbsp;&nbsp;朝阳区</label></br>
										<label class="col-sm8 control-label" style="margin-left: 110px;">中国   &nbsp;&nbsp;北京   &nbsp;&nbsp; 北京市  &nbsp;&nbsp;朝阳区</label></br>
								       </div>
	                               </div>
		                  <!-- 用于 将表单缓存清空 -->
                           <!--用来清空表单数据-->
                              <input type="hidden" id="guardAreaDid" name="fid" value=""  />
                              <input type="reset" name="reset" style="display: none;" />
                           </form>	           
				        </div>
                      </div>
			       </div>
		       </div>
	         </div>
         </div>
          <div class="modal-footer" style="text-align: center;">
          <button type="button"  class="btn btn-white" id="saveGuardArea" class="btn btn-primary" >保存</button>
          <button type="button"   class="btn btn-white" id="updateGuardArea" class="btn btn-primary" style="display: none;" >保存</button>
      </div>
     </div>
    
     </div>
   </div>
   
   
     <!-- 弹出框员工 -->
     <div class="modal inmodal fade" id="myModal5" tabindex="-2" role="dialog"  aria-hidden="true">
         <div class="modal-dialog modal-lg">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close"  onclick="Guard.getSelectEmployee();"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                     <h4 class="modal-title">员工列表</h4>
                 </div>
                 <div class="ibox-content">
		             <div class="row">
		                 <label class="col-sm-1 control-label mtop">运营专员姓名:</label>	
	                     <div class="col-sm-2">
	                         <input type="text" class="form-control m-b" name="empName" id="empNames"/>
	                     </div>
	                     <label class="col-sm-1 control-label mtop">运营专员系统号:</label>	
	                     <div class="col-sm-2">
	                         <input type="text" class="form-control m-b"  name="empCode" id="empCodes" />
	                     </div>
	                     <label class="col-sm-1 control-label mtop">运营专员手机:</label>
	                     <div class="col-sm-2">
	                    	 <input type="text" class="form-control m-b"  name="empMobile" id="empMobiles" />
	                     </div>
		                 <div class="col-sm-1">
		                 	<button class="btn btn-primary" type="button" onclick="Guard.queryEmp();"><i class="fa fa-search"></i>&nbsp;查询</button>
		           		 </div>
		            </div>
	             </div>
                 <div class="ibox-content">
	                 <div class="row row-lg">
	                    <div class="col-sm-12">
	                        <div class="example-wrap">
	                            <div class="example">
				                      <!-- 弹出框列表 -->
				                      <table id="empListTable" data-click-to-select="true"
					                    data-toggle="table"
					                    data-side-pagination="server"
					                    data-pagination="true"
					                    data-page-list="[10,20,50]"
					                    data-pagination="true"
					                    data-page-size="10"
					                    data-show-refresh="true"
					                    data-single-select="true"
					                    data-pagination-first-text="首页"
					                    data-pagination-pre-text="上一页"
					                    data-pagination-next-text="下一页"
					                    data-pagination-last-text="末页"
					                    data-query-params="paginationParamEmp"
					                    data-content-type="application/x-www-form-urlencoded"
					                    data-method="post" 
					                    data-classes="table table-hover table-condensed"
					                    data-height="500"
					                    data-url="${basePath }system/permission/employeeList">                    
					                    <thead>
					                        <tr>
					                        	<th data-field="id" data-checkbox="true"></th>
					                            <th data-field="fid" data-visible="false"></th>
					                            <th data-field="empCode">人员编号</th>
					                            <th data-field="empName">人员姓名</th>
					                            <!-- <th data-field="empSex">性别</th> -->
					                            <th data-field="empMobile">移动电话</th>
					                            <th data-field="empMail">电子邮箱</th>
					                            <th data-field="postName">部门</th>
					                        </tr>
					                    </thead>
					                    </table>
					              </div>
					          </div>
					       </div>
					  </div>
			     </div>      
                 <div class="modal-footer">
                     <button type="button" class="btn btn-primary" onclick="Guard.getSelectEmployee();">保存</button>
                 </div>
                 <input id="empCodeId" value=""  type="hidden">
                 <input id="empNameId" value=""  type="hidden">
             </div>
         </div>
	</div>
    
	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	<!-- 自定义js -->
	<script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	<!-- Bootstrap table -->
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
	<!-- layer javascript -->
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
	<!-- layerDate plugin javascript -->
	<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
	
	  <!--  validate -->
	   <script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/Math.uuid.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/validate.ext.js${VERSION}"></script>
	   
	   
	<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/minsu/guard/guard.js${VERSION}001" type="text/javascript"></script>
	<script type="text/javascript">
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
		        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
		        empCode:$.trim($('#empCode').val()),
		        empPhone:$.trim($('#empPhone').val()),
		        empName:$.trim($('#empName').val()),
		        cityCode:$.trim($('#cityCode').val()),
		        provinceCode:$.trim($('#provinceCode').val()),
		        nationCode:$.trim($('#nationCode').val())
	    	};
	 }
	 
	 function paginationParamEmp(params) {
			return {
				limit : params.limit,
				page : $('#empListTable').bootstrapTable('getOptions').pageNumber,
				empName : $.trim($('#empNames').val()),
				empCode : $.trim($('#empCodes').val()),
				empMobile : $.trim($('#empMobiles').val())
			};
		}

	</script>
</body>

</html>
