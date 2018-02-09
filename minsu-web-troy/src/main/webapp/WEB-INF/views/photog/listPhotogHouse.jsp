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
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}"rel="stylesheet">
	<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}"rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
	<link href="${staticResourceUrl}/css/star-rating.css${VERSION}001"  rel="stylesheet" type="text/css"/>
	
	<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
	
	<style type="text/css">
		.line-width{
			width: 200px;;
		}
		/* td{
			white-space: nowrap;text-overflow: ellipsis;overflow: hidden;
		} */
	</style>
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
					                        	<label class="col-sm-1 control-label mtop">房东姓名:</label>
					                            <div class="col-sm-2">
				                                    <input id="landlordName" name="landlordName" type="text" class="form-control m-b">
				                                </div>
					                        	<label class="col-sm-1 control-label mtop">房东手机:</label>
					                            <div class="col-sm-2">
				                                    <input id="landlordMobile" name="landlordMobile" type="text" class="form-control m-b">
				                                </div>
				                                <label class="col-sm-1 control-label mtop">房源编号:</label>
				                                <div class="col-sm-2">
				                                   <input type="text" class="form-control" id="houseSn" name="houseSn"/>
				                                </div>
					                        	<label class="col-sm-1 control-label mtop">房源名称:</label>
					                            <div class="col-sm-2">
				                                    <input id="houseName" name="houseName" type="text" class="form-control m-b">
				                                </div>
						                    </div>
											<div class="row">
											     <label class="col-sm-1 control-label mtop">房间编号:</label>
				                                <div class="col-sm-2">
				                                   <input type="text" class="form-control" id="roomSn" name="roomSn"/>
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
					                    <div class="col-sm-14">
					                        <div class="example-wrap">
					                            <div class="example">
					                                <table id="listTable" class="table table-bordered"  data-click-to-select="true"
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
								                    data-single-select="true"
								                    data-height="496"
								                    data-classes="table table-hover table-condensed"
								                    data-url="photog/queryPhotographerHouse">                    
								                    <thead>
								                    <!-- 房源编号   房源名称  房源地址   房东真实姓名  房东手机号   房源渠道  是否预约摄影师  分租：多个房间名称 逗号隔开-->
								                        <tr>
								                            <!-- <th data-field="houseFid" data-width="100px" data-align="center" >房源fid</th> -->
								                            <th data-field="houseSn" data-width="100px" data-align="center" >房源编号</th>
								                            <th data-field="roomSn" data-width="100px" data-align="center" data-formatter="formateRoomSn">房间编号(分租展示)</th>
								                            <th data-field="houseName" data-width="200px" data-align="center" data-formatter="formateHouseName">房源名称(分租：房间名称)</th>
								                            <th data-field="houseAddr" data-width="200px" data-align="center">房源地址</th>
								                            <th data-field="rentWay" data-width="200px" data-align="center" data-formatter="formateRentWay">出租方式</th>
								                            <th data-field="landlordName" data-width="100px" data-align="center">房东真实姓名</th>
								                            <th data-field="landlordMobile" data-width="100px" data-align="center">房东手机</th>
								                            <th data-field="isPhotography" data-width="100px" data-align="center"  data-formatter="formateIsPhotography">是否预约摄影师</th>
								                            <th data-field="isPhotography" data-width="100px" data-align="center" data-formatter="getOperation">操作</th>
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
        				
        				
 <!-- 添加 菜单  弹出框 -->
<div class="modal inmodal" id="myModal" tabindex="-1"  role="dialog" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content animated bounceInRight">
       <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
          </button>
          <h4 class="modal-title">申请预约</h4>
       </div>
       <div class="modal-body">
	        <div class="wrapper wrapper-content animated fadeInRight">
		        <div class="row">
		          <div class="col-sm-14">
		               <div class="ibox float-e-margins">
		                   <div class="ibox-content">
				           <form id="bookPhotogForm" action="" method="post">
				                <input type="hidden" id="houseFidG" name = "houseFid" value=""/>
			                    <input type="hidden" id="houseSnG" name = "houseSn" value=""/>
			                    <input type="hidden" id="houseNameG" name ="houseName" value=""/>
			                    <input type="hidden" id="houseAddrG" name = "shotAddr" value=""/>
			                    <input type="hidden" id="landlordUidG" name = "landlordUid" value=""/>
			                     <div class="row">
			                          <div class="form-group">
										<label class="col-sm-3 control-label mtop">预约开始时间：</label>
			                           	<div class="col-sm-7">
			                                <input id="bookStartTime"  value=""  name="bookStartTimeStr" class="laydate-icon form-control layer-date">
			                            </div>
			                       </div>
						        </div>
						        
						         <div class="row">	
						            <div class="form-group">
									    <label class="col-sm-3 control-label mtop">预约结束时间：</label>
			                           	<div class="col-sm-7">
			                                <input id="bookEndTime"  value=""  name="bookEndTimeStr" class="laydate-icon form-control layer-date">
			                            </div>
						            </div>
						         </div>
						     	
		                  <!-- 用于 将表单缓存清空 -->
                           <!--用来清空表单数据-->
                              <input type="reset" name="reset" style="display: none;" />
                           </form>	           
                      </div>
			       </div>
		       </div>
	         </div>
         </div>
     </div>
     <div class="modal-footer" style="text-align: center;">
         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
         <button type="button" class="btn btn-primary" onclick="javascript:bookHousePhotographer();">保存</button>
      </div>
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
	 <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
	 
	 <script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}005" type="text/javascript"></script>
	
	   <!-- Page-Level Scripts -->
	   <script>
		   $(function(){
		        
		        var start={elem:"#bookStartTime",format: 'YYYY-MM-DD hh:mm:ss',istime: true,min:laydate.now(),choose:function(datas){end.min=datas;end.start=datas}};
		        var end={elem:"#bookEndTime",format: 'YYYY-MM-DD hh:mm:ss',istime: true,min:laydate.now(),choose:function(datas){start.max=datas}};
		        laydate(start);
		        laydate(end);
	  			
	  		});
		   
		    function isNullOrBlank(obj){
		    	return obj == undefined || obj == null || $.trim(obj).length == 0;
		    }
		   
	   		//整租
		    function paginationParam(params) {
		    	
			    return {
			        limit: params.limit,
			        page:$('#listTable').bootstrapTable('getOptions').pageNumber,
			        landlordName:$.trim($('#landlordName').val()),
			        landlordMobile:$.trim($('#landlordMobile').val()),
			        houseName:$.trim($('#houseName').val()),
			        houseSn:$.trim($('#houseSn').val()),
			        roomSn:$.trim($('#roomSn').val())
		    	};
			}
	   		
		    
		  	//整租
		    function query(){
				console.log($("#houseQualityGradeZ").val());
		    	$('#listTable').bootstrapTable('selectPage', 1);
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
			
			function formateIsPhotography(value, row, index) {
				if(value == 0){
					return "未预约";
				}else if(value == 1){
					return "已经预约";
				}else{
					return "";
				}
			}
			
			function formateRentWay(value, row, index) {
				if(value == 0){
					return "整租";
				}else if(value == 1){
					return "分租";
				}else{
					return "";
				}
			}
			
			function getOperation(value, row, index){
				
				var _html=""
				if(row.isPhotography == 0){
		   			_html +="<button id='addMenuButton"+index+"' style='height: 26px;background-color: #1ab394;line-height: normal;border:1 solid #FFCC00;color: #FFFFFF;font-size: 9pt;font-style: normal;font-variant: normal;font-weight: normal;'  type='button' class='btn btn-primary'onclick='bookPhotographer(\""+row.houseFid+"\",\""+row.houseSn+"\",\""+row.houseName+"\",\""+row.houseAddr+"\",\""+row.landlordUid+"\");' data-toggle='modal' data-target='#myModal'>预约</button>";
				}
				return _html;
			}
			
			function formateHouseName(value, row, index){
				
				var _html=""
				if(row.rentWay == 0){
					return row.houseName;
		   		}
				return row.roomName;
			}
			
			function formateRoomSn(value, row, index){
				
				if(row.rentWay == 0){
					return "-";
		   		}
				return row.roomSn;
			}
			
			//预约  打开时间框
			function bookPhotographer(houseFid,houseSn,houseName,houseAddr,landlordUid){
                
                $("#houseFidG").val(houseFid);
                $("#houseSnG").val(houseSn);
                $("#houseNameG").val(houseName);
                $("#houseAddrG").val(houseAddr);
                $("#landlordUidG").val(landlordUid);
				
			}
			
		
			//申请预约表单提交
			function bookHousePhotographer(){
				CommonUtils.ajaxPostSubmit("${basePath}photog/bookHousePhotographer", $('#bookPhotogForm').serialize(), function(data){
					
					//$('#bookPhotogForm').reset(); 
					if(data.code == 0){
						 window.location.href = "${basePath}photog/goToPhotographerHouse"
					}else{
						   alert(data.msg);
					}
				})
			}
			
			
		</script>
	</body>
</html>
