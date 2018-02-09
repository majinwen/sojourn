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
		<!-- Panel Other -->
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<div class="row row-lg">
					<div class="col-sm-12">
						<div class="example-wrap">
							<div class="example">
								<div class="example">
	                                <table id="listTable" 
	                                class="table table-bordered"  
				                    data-toggle="table"
				                    data-pagination="false"
				                    data-striped="true"
				                    data-single-select="true"
				                    data-minimum-count-columns="1"
				                    data-height="496"
				                    data-classes="table table-hover table-condensed">                    
				                    <thead>
				                        <tr>
				                            <th data-field="fid" data-visible="false"></th>
				                            <th data-field="houseSn" data-width="100px" data-align="center">房源编号</th>
				                            <th data-field="houseName" data-width="200px" data-align="center">房源名称</th>
				                            <th data-field="landlordName" data-width="100px" data-align="center">房东姓名</th>
				                            <!-- <th data-field="empPushCode" data-width="100px" data-align="center">地推管家编号</th>
				                            <th data-field="empPushName" data-width="100px" data-align="center">地推管家</th> -->
				                            <th data-field="empGuardCode" data-width="100px" data-align="center">专员系统号</th>
				                            <th data-field="empGuardName" data-width="100px" data-align="center">运营专员</th>
				                        </tr>
				                    </thead>
				                    </table>             
	                            </div>
							</div>
						</div>
						<!-- End Example Pagination -->
					</div>
				</div>
		  	    <div class="ibox-content">
		  	    	<form id="form">
		  	    		<c:forEach items="${data }" var="obj" varStatus="no">
	                        <input type="hidden" class="form-control" name="listGuard[${no.index }].fid" value="${obj.fid }">
	                        <input type="hidden" class="form-control" name="listGuard[${no.index }].houseFid" value="${obj.houseFid }">
		  	    		</c:forEach>
		  	    		 <input type="hidden" class="form-control" name="houseChannel" value="" id="houseChannel">
			            <!-- <div class="form-group">
			          	    <div class="hr-line-dashed"></div>
			          	    <h2 class="title-font">地推管家选择</h2>
	                        <div class="row">
                                <input type="hidden" class="form-control" name="empPushFid"  id="empPushFid">
                                <label class="col-sm-1 control-label">管家姓名</label>
                                <div class="col-sm-2">
                                    <div class="input-group">
                                        <input type="text" class="form-control" value="" name="empPushName" readonly="readonly" id="empPushName">
                                    </div>
                                </div>  
                                <label class="col-sm-1 control-label">管家系统号</label>
                                <div class="col-sm-2">
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="empPushCode" readonly="readonly" id="empPushCode">
                                    </div>
                                </div>  
                                <label class="col-sm-1 control-label">管家手机</label>
                                <div class="col-sm-2">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="empPushMobile" readonly="readonly">
                                    </div>
                                </div>  
                                <div class="col-sm-1">
                                    <button type="button" class="btn btn-white" data-toggle="modal" data-target="#empPushModel">选择管家</button>
	                            </div> 
	                        </div>
		                </div> -->
		                
		                  <div class="form-group" id="hosueChaGroup" style="display: none;">
			          	    <div class="hr-line-dashed"></div>
			          	    <h2 class="title-font">房源渠道</h2>
	                        <div class="row">
                                <label class="col-sm-1 control-label">房源渠道</label>
                                <div class="col-sm-2">
                                    <div class="input-group">
                                        <input type="text" class="form-control" value="" id="hosueChaStr" readonly="readonly" >
                                    </div>
                                </div>  
	                        </div>
		                </div>
		                
		                
			            <div class="form-group">
			          	    <div class="hr-line-dashed"></div>
			          	    <h2 class="title-font">选择运营专员</h2>
	                        <div class="row">
                                <input type="hidden" class="form-control" name="empGuardFid"  id="empGuardFid">
                                <label class="col-sm-1 control-label">专员姓名</label>
                                <div class="col-sm-2">
	                                <div class="input-group">
	                                    <input type="text" class="form-control" value="" name="empGuardName" readonly="readonly" id="empGuardName">
	                                </div>
	                            </div>  
	                            <label class="col-sm-1 control-label">专员系统号</label>
	                            <div class="col-sm-2">
		                            <div class="input-group">
		                                <input type="text" class="form-control" name="empGuardCode" readonly="readonly" id="empGuardCode">
		                            </div>
	                            </div>  
	                            <label class="col-sm-1 control-label">专员手机号</label>
	                            <div class="col-sm-2">
		                            <div class="input-group">
		                                <input type="text" class="form-control" id="empGuardMobile" readonly="readonly">
		                            </div>
	                            </div>  
	                            <div class="col-sm-1">
                                    <button type="button" class="btn btn-white" data-toggle="modal" data-target="#empGuardModel">选择运营专员</button>
		                        </div> 
	                        </div>
		                </div>
			            <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-2">
                                <button class="btn btn-primary" type="button" onclick="updateHouseGuard();">保存</button>
                            </div>
                        </div>
		  	    	</form>
	           </div>
			</div>
		</div>
	</div>
	
	<!-- 弹出框员工 -->
    <%-- <div class="modal inmodal fade" id="empPushModel" tabindex="-1" role="dialog"  aria-hidden="true">
         <div class="modal-dialog modal-lg">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                     <h4 class="modal-title">员工列表</h4>
                 </div>
	             <div class="ibox-content">
		             <div class="row">
		                 <label class="col-sm-1 control-label mtop">员工姓名:</label>	
	                     <div class="col-sm-2">
	                         <input type="text" class="form-control m-b" id="empName" name="empName"/>
	                     </div>
	                     <label class="col-sm-1 control-label mtop">员工系统号:</label>	
	                     <div class="col-sm-2">
	                         <input type="text" class="form-control m-b" id="empCode" name="empCode"/>
	                     </div>
	                     <label class="col-sm-1 control-label mtop">员工手机号:</label>
	                     <div class="col-sm-2">
	                    	 <input type="text" class="form-control m-b" id="empMobile" name="empMobile">
	                     </div>
		                 <div class="col-sm-1">
		                 	<button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
		           		 </div>
		             </div>
	             </div>
                 <div class="ibox-content">
	                 <div class="row row-lg">
	                    <div class="col-sm-12">
	                        <div class="example-wrap">
	                            <div class="example">
				                      <!-- 弹出框列表 -->
				                      <table id="empPushTable" 
				                        data-click-to-select="true"
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
					                    data-query-params="paginationParam"
					                    data-response-handler="pretreatment"
					                    data-method="get" 
					                    data-classes="table table-hover table-condensed"
					                    data-height="500"
					                    data-url="system/permission/employeeList">                    
					                    <thead>
					                        <tr>
					                        	<th data-field="id" data-radio="true"></th>
					                            <th data-field="fid" data-visible="false"></th>
					                            <th data-field="empCode">员工系统号</th>
					                            <th data-field="empName">员工姓名</th>
					                            <th data-field="empMobile">员工手机号</th>
					                        </tr>
					                    </thead>
					                    </table>
					              </div>
					          </div>
					       </div>
					  </div>
			     </div>      
                 <div class="modal-footer">
                     <button type="button" class="btn btn-primary" onclick="selectEmpPush()" data-dismiss="modal">保存</button>
                 </div>
             </div>
         </div>
    </div> --%>
    
	<!-- 弹出框员工 -->
    <div class="modal inmodal fade" id="empGuardModel" tabindex="-2" role="dialog"  aria-hidden="true">
         <div class="modal-dialog modal-lg">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                     <h4 class="modal-title">员工列表</h4>
                 </div>
	             <div class="ibox-content">
	             	 <div class="row">
		                 <label class="col-sm-1 control-label mtop">国家:</label>	
	                     <div class="col-sm-2">
	                         <select class="form-control m-b" id="nationCodeS">
	                             <option value="${data[0].nationCode }" selected="selected">${nationCode }</option>
	                         </select>
	                     </div>
	                     <label class="col-sm-1 control-label mtop">省份:</label>	
	                     <div class="col-sm-2">
	                         <select class="form-control m-b" id="provinceCodeS">
	                             <option value="">请选择</option>
	                             <option value="${data[0].provinceCode }" selected="selected">${provinceCode }</option>
	                         </select>
	                     </div>
		                 <label class="col-sm-1 control-label mtop">城市:</label>	
	                     <div class="col-sm-2">
	                         <select class="form-control m-b" id="cityCodeS">
	                             <option value="">请选择</option>
	                             <option value="${data[0].cityCode }" selected="selected">${cityCode }</option>
	                         </select>
	                     </div>
	                     <label class="col-sm-1 control-label mtop">区域:</label>	
	                     <div class="col-sm-2">
	                         <select class="form-control m-b" id="areaCodeS">
	                             <option value="">请选择</option>
	                             <option value="${data[0].areaCode }" selected="selected">${areaCode }</option>
	                         </select>
	                     </div>
		             </div>
		             <div class="row">
		                 <label class="col-sm-1 control-label mtop">员工姓名:</label>	
	                     <div class="col-sm-2">
	                         <input type="text" class="form-control m-b" id="empNameS" name="empName"/>
	                     </div>
	                     <label class="col-sm-1 control-label mtop">员工系统号:</label>	
	                     <div class="col-sm-2">
	                         <input type="text" class="form-control m-b" id="empCodeS" name="empCode"/>
	                     </div>
	                     <label class="col-sm-1 control-label mtop">员工手机号:</label>
	                     <div class="col-sm-2">
	                    	 <input type="text" class="form-control m-b" id="empPhone" name="empPhone">
	                     </div>
		                 <div class="col-sm-1">
		               	 	 <button class="btn btn-primary" type="button" onclick="queryS();"><i class="fa fa-search"></i>&nbsp;查询</button>
		           		 </div>
		            </div>
	             </div>
                 <div class="ibox-content">
	                 <div class="row row-lg">
	                    <div class="col-sm-12">
	                        <div class="example-wrap">
	                            <div class="example">
				                      <!-- 弹出框列表 -->
				                      <table id="empGuardTable" 
				                        data-click-to-select="true"
					                    data-toggle="table"
					                    data-side-pagination="server"
					                    data-cache="false"
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
					                    data-query-params="paginationParamS"
					                    data-response-handler="pretreatmentS"
					                    data-method="get" 
					                    data-classes="table table-hover table-condensed"
					                    data-height="500"
					                    data-url="guard/queryGaurdAreaByPage">                    
					                    <thead>
					                        <tr>
					                        	<th data-field="id" data-radio="true"></th>
					                            <th data-field="fid" data-visible="false"></th>
					                            <th data-field="empCode">员工系统号</th>
					                            <th data-field="empName">员工姓名</th>
					                            <th data-field="empPhone">员工手机号</th>
					                        </tr>
					                    </thead>
					                    </table>
					              </div>
					          </div>
					       </div>
					  </div>
			     </div>      
                 <div class="modal-footer">
                     <button type="button" class="btn btn-primary" onclick="selectEmpGuard()" data-dismiss="modal">保存</button>
                 </div>
             </div>
         </div>
    </div>


	<!-- 添加 菜单  弹出框 -->
<div class="modal inmodal" id="houseChannelModal" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content animated bounceInRight">
       <div class="modal-header">
          <button type="button" class="close" data-dismiss="houseChannelModal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
          </button>
          <h4 class="modal-title">房源渠道</h4>
       </div>
       <div class="modal-body">
	        <div class="wrapper wrapper-content animated fadeInRight">
		        <div class="row">
		          <div class="col-sm-14">
		               <div class="ibox float-e-margins">
		                   <div class="ibox-content">
				           <form id="houseChaForm" action="" method="post">
		                       <!-- 用于 将表单缓存清空 -->
                              <input type="reset" name="reset" style="display: none;" />
                                <select class="form-control m-b"  name="houseCha" id="houseCha">
                                      <option value="1">直营</option>
                                      <option value="2">房东</option>
                                  </select>
                           </form>	           
                      </div>
			       </div>
		       </div>
	         </div>
         </div>
     </div>
     <div class="modal-footer" style="text-align: center;">
         <button type="button" id="saveHouseChannel" class="btn btn-primary" >保存</button>
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
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>

	<!-- Page-Level Scripts -->
	<script>
		$(function(){
			var data = ${jsonData };
			$('#listTable').bootstrapTable("load", data);
			$("#houseChannel").val("");
			$("#saveHouseChannel").click(function(){
				
				var houseCha = $("#houseCha").val();
				$("#houseChannel").val(houseCha);
				$("#houseChannelModal").hide();
				if(houseCha == 1){
					$("#hosueChaStr").val("直营");
				}
				if(houseCha == 2){
					$("#hosueChaStr").val("房东");
				}
				$("#hosueChaGroup").show();
				//updateHouseGuard();
			});
		});
		
		// 分页参数
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#empPushTable').bootstrapTable('getOptions').pageNumber,
				empName : $.trim($('#empName').val()),
				empCode : $.trim($('#empCode').val()),
				empMobile : $.trim($('#empMobile').val())
			};
		}
		
		// 结果集预处理
		function pretreatment(result) {
			var pageNumber = $('#empPushTable').bootstrapTable('getOptions').pageNumber;
			if(pageNumber == 1){
				result.rows.unshift({empCode:"空",empName:"空",empMobile:"空"});
			}
			return result;
		}
		
		// 分页参数
		function paginationParamS(params) {
			return {
				limit : params.limit,
				page : $('#empGuardTable').bootstrapTable('getOptions').pageNumber,
				nationCode : $('#nationCodeS').val(),
				provinceCode : $('#provinceCodeS option:selected').val(),
				cityCode : $('#cityCodeS option:selected').val(),
				areaCode : $('#areaCodeS option:selected').val(),
				empName : $.trim($('#empNameS').val()),
				empCode : $.trim($('#empCodeS').val()),
				empPhone : $.trim($('#empPhone').val())
			};
		}
		
		// 结果集预处理
		function pretreatmentS(result) {
			var pageNumber = $('#empGuardTable').bootstrapTable('getOptions').pageNumber;
			if(pageNumber == 1){
				result.rows.unshift({empCode:"空",empName:"空",empPhone:"空"});
			}
			return result;
		}
		
	    function query(){
	    	$('#empPushTable').bootstrapTable('selectPage', 1);
	    }
		
		function selectEmpPush(){
			var rows = $('#empPushTable').bootstrapTable('getSelections');
			if (rows.length == 0) {
				layer.alert("请选择一条记录进行操作", {
					icon : 6,
					time : 2000,
					title : '提示'
				});
				return;
			}
			$("#empPushFid").val(rows[0].fid);
			$("#empPushName").val(rows[0].empName);
			$("#empPushCode").val(rows[0].empCode);
			$("#empPushMobile").val(rows[0].empMobile);
			$("#houseChannelModal").hide();
			$("#houseChannel").val('');
			$("#hosueChaGroup").hide();
		}
	  	
	    function queryS(){
	    	$('#empGuardTable').bootstrapTable('refresh');
	    }
		
		function selectEmpGuard(){
			var rows = $('#empGuardTable').bootstrapTable('getSelections');
			if (rows.length == 0) {
				layer.alert("请选择一条记录进行操作", {
					icon : 6,
					time : 2000,
					title : '提示'
				});
				return;
			}
			$("#empGuardFid").val(rows[0].fid);
			$("#empGuardName").val(rows[0].empName);
			$("#empGuardCode").val(rows[0].empCode);
			$("#empGuardMobile").val(rows[0].empPhone);
			
		}
		
		function updateHouseGuard(){			
			if(/* $("#empPushCode").val() === '' &&  */$("#empGuardCode").val() === ''){
				layer.alert("请先选择运营专员", {
					icon : 6,
					time : 2000,
					title : '提示'
				});
				return;
			}
			
			var houseChannel = $("#houseChannel").val();
			if($("#empPushCode").val() == '空'&&(houseChannel==null || houseChannel== "")){
				$("#houseChannelModal").show();
				return false;
			}
			$.ajax({
				url:"house/houseGuard/updateHouseGuard",
				data:toJson($('#form').serializeArray()),
				dataType:"json",
				type:"post",
				async: true,
				success:function(result) {
					if(result.code === 0){
						$.callBackParent("house/houseGuard/listHouseGuard",true,callback);
					}else{
						layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
					}
				},
				error:function(result){
					layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
				}
			});
		}
		
		function toJson(array){
			var json = {};
			$.each(array, function(i, n){
				var key = n.name;
				var value = n.value;
				if($.trim(value).length != 0){
					if(value != "空"){
						json[key] = value;
					} else {
						json[key] = "";
					}
				}
			});
			return json;
		}	
		
		function callback(parent){
			parent.refreshData("listTable");
		}
	</script>
</body>
</html>