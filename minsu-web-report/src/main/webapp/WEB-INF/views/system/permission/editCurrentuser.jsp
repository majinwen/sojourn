<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<base href="${basePath}">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">

    <link rel="${staticResourceUrl}/shortcut icon" href="${staticResourceUrl}/favicon.ico"> 
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/iCheck/custom.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">
 </head>
  
  <body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>添加用户</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="form_basic.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="form_basic.html#">选项1</a>
                                </li>
                                <li><a href="form_basic.html#">选项2</a>
                                </li>
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form action="system/permission/editCurrentuserSucceed" class="form-horizontal" method="post">
                        <input type="hidden" name="fid" value="${userInfo.fid }">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">账户名称</label>

                                <div class="col-sm-2">
                                    <input type="text" class="form-control"  name="userAccount" value="${userInfo.userAccount }">
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">密码</label>

                                <div class="col-sm-2">
                                    <input type="password" class="form-control" name="userPassword" value="${userInfo.userPassword }">
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">员工选择</label>

                                <div class="col-sm-2">
                                <div class="input-group">
                                     <input type="hidden" class="form-control" name="employeeFid"  id="empId" value="${userInfo.employeeFid}">
                                     <input type="text" class="form-control" name="empName"  id="empName" value="${userInfo.fullName }">
                                     <span class="input-group-btn">
                                     <button type="button" class="btn btn-white" data-toggle="modal" data-target="#myModal5">
                                    	员工选择
                                	</button>
                                	</span>
                                </div>
                                </div>  
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                               <label class="col-sm-2 control-label">账号行政区域选择</label>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" name="nationCode"  id="nationCode">
                                       <option>请选择</option>
                                   </select>
                               </div>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" name="provinceCode"  id="provinceCode">
                                       <option>请选择</option>
                                   </select>
                               </div>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" name="cityCode"  id="cityCode">
                                       <option>请选择</option>
                                   </select>
                               </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">
                                    	是否管理员
                                </label>
                                <div class="radio i-checks">
                                        <label>
                                            <input type="radio" value="0" name="isAdmin" ${userInfo.isAdmin==0 ?'checked':''}> <i></i>否</label>
                                        <label>
                                            <input type="radio" value="1" name="isAdmin" ${userInfo.isAdmin==1 ?'checked':''}> <i></i>是</label>   
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">
                                	<button type="button" class="btn btn-white" data-toggle="modal" data-target="#roleModel">角色选择</button>
								</label>

                                <div class="col-sm-10" id="roleDiv">
                                    <div name="selectedDiv" class="col-sm-2">
		                                 <c:forEach items="${userInfo.roles}" var="role" varStatus="rowIndex">
			                               <div class="input-group"><input type="hidden" class="form-control" name="roleFidList" value="${role.fid }" />
			                               <input type="text" class="form-control" name="empName" value="${role.roleName}" />
			                               <span class="input-group-btn"><button type="button" class="btn btn-white" onclick="deleteRoleDiv(this)" ><font style="color:red">删除</font></button></span>
			                               </div>
		                             </c:forEach>
	                               </div>
                               </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="submit">保存内容</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 弹出框员工 -->
    <div class="modal inmodal fade" id="myModal5" tabindex="-1" role="dialog"  aria-hidden="true">
         <div class="modal-dialog modal-lg">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                     <h4 class="modal-title">员工列表</h4>
                 </div>
                 <div class="ibox-content">
	                 <div class="row row-lg">
	                    <div class="col-sm-12">
	                        <div class="example-wrap">
	                            <div class="example">
				                      <!-- 弹出框列表 -->
				                      <table id="listTable" data-click-to-select="true"
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
					                    data-method="get" 
					                    data-classes="table table-hover table-condensed"
					                    data-height="500"
					                    data-url="system/permission/employeeList">                    
					                    <thead>
					                        <tr>
					                        	<th data-field="id" data-radio="true"></th>
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
             </div>
         </div>
     </div>
     
    <!-- 弹出框角色 -->
    <div class="modal inmodal fade" id="roleModel" tabindex="-1" role="dialog"  aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                     <h4 class="modal-title">角色列表</h4>
                 </div>
                 <div class="ibox-content">
	                 <div class="row row-lg">
	                    <div class="col-sm-12">
	                        <div class="example-wrap">
	                            <div class="example">
				                      <!-- 弹出框列表 -->
				                      <table id="roleTable" class="table table-hover table-bordered" data-click-to-select="true"
					                    data-toggle="table"
					                    data-side-pagination="server"
					                    data-pagination="true"
					                    data-page-list="[1,20,50]"
					                    data-pagination="true"
					                    data-page-size="5"
					                    data-show-refresh="true"
					                    data-pagination-first-text="首页"
					                    data-pagination-pre-text="上一页"
					                    data-pagination-next-text="下一页"
					                    data-pagination-last-text="末页"
					                    data-query-params="rolePaginationParam"
					                    data-method="get" 
					                    data-classes="table table-hover table-condensed"
					                    data-height="300"
					                    data-url="system/permission/showRoles">                    
					                    <thead>
					                        <tr>
					                        	<th data-field="id" data-checkbox="true"></th>
					                            <th data-field="fid" data-visible="false"></th>
						                        <th data-field="roleName" data-align="center">名称</th>
					                        </tr>
					                    </thead>
					                    </table>
					              </div>
					          </div>
					       </div>
					  </div>
			     </div>      
                 <div class="modal-footer">
                     <button type="button" class="btn btn-primary" onclick="getSelectRole()"  data-dismiss="modal">保存</button>
                 </div>
             </div>
         </div>
     </div>
    

    <!-- 全局js -->
    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>

    <!-- 自定义js -->
    <script src="${staticResourceUrl}/js/content.js${VERSION}"></script>

    <!-- iCheck -->
    <script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js${VERSION}"></script>
    
    <!-- Bootstrap table -->

    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>

    <script type="text/javascript">
    	 $(document).ready(function () {
			// 多级联动
			var areaJson = ${cityTreeList};
			var defaults = {
				geoJson	: areaJson,
				nationCode: '${userInfo.nationCode}',
				provinceCode: '${userInfo.provinceCode}',
				cityCode: '${userInfo.cityCode}'
			};
			geoCascade(defaults);
			
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
    	 
    	 
        /*员工列表参数*/
    	function paginationParam(params) {
		    return {
		        limit: params.limit,
		        offset: params.offset,
		        page: $('#listTable').bootstrapTable('getOptions').pageNumber
	    	};
		}
		/*选择员工*/
		function getSelectEmployee(){
			var selectVar=$('#listTable').bootstrapTable('getSelections');
			$("#empName").val(selectVar[0].empName);
			$("#empId").val(selectVar[0].fid);
		}
		/*角色列表参数*/
	   function rolePaginationParam(params) {
		    return {
		        limit: params.limit,
		        page: $('#roleTable').bootstrapTable('getOptions').pageNumber,
		        roleName:$('#roleName').val(),
		        createrName:$('#createrName').val()
	    	};
		}
		/*选择角色*/
		function getSelectRole(){
			var html = '<div name="selectedDiv" class="col-sm-2">';
			var selectVar=$('#roleTable').bootstrapTable('getSelections');
			var selectedIdArray = $("#roleDiv").find("input[name='roleFidList']");
			var selectedIdStr = $(selectedIdArray).map(function(){return $(this).val();}).get().join(", "); 
			
			$(selectVar).each(function(index,obj){
				if(!selectedIdStr || (!!selectedIdStr && selectedIdStr.indexOf(obj.fid) == -1)){
					html += '<div class="input-group"><input type="hidden" class="form-control" name="roleFidList" value="'+obj.fid+'" />';
					html += '<input type="text" class="form-control" name="empName" value="'+obj.roleName+'" />';
					html += '<span class="input-group-btn"><button type="button" class="btn btn-white" onclick="deleteRoleDiv(this)" ><font style="color:red">删除</font></button></span>'
					html += '</div>'
				}
			});
			
			html += '</div>';
			$("#roleDiv").append(html);
		}
		/*删除选择角色*/
		function deleteRoleDiv(obj){
			//删除选中人的div
			$(obj).parent().parent().remove();
			//删除 <div name="selectedDiv" class="col-sm-2">子节点为空的div
			var divArry = $("#roleDiv").find("div[name='selectedDiv']");
			$(divArry).each(function(index,obj){
				if($(obj).children().length<=0){
					$(obj).remove();
				}
			})
		}
    </script>
</body>
</html>
