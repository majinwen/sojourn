<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal inmodal fade" id="myModal5" tabindex="-1" role="dialog"  aria-hidden="true">
         <div class="modal-dialog modal-lg">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                     <h4 class="modal-title">员工列表</h4>
                 </div>
                 <div class="ibox-content">
		             <div class="row">
		                 <label class="col-sm-1 control-label mtop">管家姓名:</label>	
	                     <div class="col-sm-2">
	                         <input type="text" class="form-control m-b" id="empName" name="empName"/>
	                     </div>
	                     <label class="col-sm-1 control-label mtop">管家系统号:</label>	
	                     <div class="col-sm-2">
	                         <input type="text" class="form-control m-b" id="empCode" name="empCode"/>
	                     </div>
	                     <label class="col-sm-1 control-label mtop">管家手机:</label>
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
				                      <table id="empListTable" data-click-to-select="true"
					                    data-toggle="table"
					                    data-side-pagination="server"
					                    data-pagination="true"
					                    data-page-list="[1,20,50]"
					                    data-pagination="true"
					                    data-page-size="10"
					                    data-show-refresh="true"
					                    data-single-select="true"
					                    data-pagination-first-text="首页"
					                    data-pagination-pre-text="上一页"
					                    data-pagination-next-text="下一页"
					                    data-pagination-last-text="末页"
					                    data-query-params="paginationParam"
					                    data-content-type="application/x-www-form-urlencoded"
					                    data-method="post" 
					                    data-classes="table table-hover table-condensed"
					                    data-height="500"
					                    data-url="system/permission/employeeList">                    
					                    <thead>
					                        <tr>
					                        	<th data-field="id" data-checkbox="true"></th>
					                            <th data-field="fid" data-visible="false"></th>
					                            <th data-field="empCode">人员编号</th>
					                            <th data-field="empName">人员姓名</th>
					                            <th data-field="empSex">性别</th>
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
                     <button type="button" class="btn btn-primary" onclick="getSelectEmployee()" data-dismiss="modal">保存</button>
                 </div>
                 <input id="empCodeId" value=""  type="hidden">
                 <input id="empNameId" value=""  type="hidden">
             </div>
         </div>
</div>
	<script type="text/javascript">
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#empListTable').bootstrapTable('getOptions').pageNumber,
				empName : $.trim($('#empName').val()),
				empCode : $.trim($('#empCode').val()),
				empMobile : $.trim($('#empMobile').val())
			};
		}
		//搜索员工
	    function query(){
	    	$('#empListTable').bootstrapTable('selectPage', 1);
	    }
		
	    /*选择员工*/
		function getSelectEmployee(){
			var selectVar=$('#empListTable').bootstrapTable('getSelections');
			var empName=$("#empNameId").val();
			var empCode=$("#empCodeId").val();
			$("#"+empName).val(selectVar[0].empName);
			$("#"+empCode).val(selectVar[0].empCode);
		}
	</script>
