<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<%=basePath%>">
	<title>自如民宿后台管理系统</title>
	<meta name="keywords" content="自如民宿后台管理系统">
	<meta name="description" content="自如民宿后台管理系统">
	<link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico">
	<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/treeview/bootstrap-treeview.css${VERSION}001" rel="stylesheet">




</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox float-e-margins">
	</div>
	<div class="ibox float-e-margins">
		<div class="col-sm-16">
			<div class="ibox float-e-margins">
				<div class="ibox-content">

					<!-- 右侧table div -->
					<div class="col-sm-10">

						<div >

							<form id="complate" class="form-horizontal m-t" >
								<div class="form-group">
									<label class="col-sm-3 control-label">房东的联想词</label>
									<div class="col-sm-8">
										<input id="complateName" name="complateName"  type="text" value="">
										<button id="addMenuButton" type="button" class="btn btn-primary" onclick="doCom()" >补全信息</button>
									</div>
								</div>
							</form>

                        </div>

                        <div >

                            <form id="suggest" class="form-horizontal m-t" >
                                <div class="form-group">
                                    <div class="col-sm-8">
                                        <label class="col-sm-3 control-label">用户的补全</label>
                                        <input id="suggestName" name="suggestName"  type="text" value="">
                                        <%--<input id="cityCode" name="cityCode"  type="hidden" value="">--%>
                                        <button id="suggestButton" type="button" class="btn btn-primary" onclick="doSuggest()" >补全信息</button>
                                    </div>

                                </div>
                            </form>

                        </div>

						<div class="col-sm-11"  >
							<div class="form-group">
								这里将会有返回的结果
								<textarea id="valueInfo" class="form-control" rows="3"></textarea>
							</div>
						</div>


						<div class="col-sm-12">

							<!-- 菜单列表结束 -->
						</div>
					</div>
					</div>
					<div class="clearfix">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>

<script src="${staticResourceUrl}/js/plugins/suggest/bootstrap-suggest.min.js${VERSION}001"></script>

<!-- 自定义js -->
<script src="${staticResourceUrl}/js/content.js${VERSION}001"></script>

<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>

<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>



<!-- Bootstrap-Treeview plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/treeview/bootstrap-treeview.js${VERSION}001"></script>

<!-- jQuery Validation plugin javascript-->
<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>

<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}001"></script>

<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>


<script type="text/javascript">





	var contentPath = "${path}"; //系统路径
	var complateUrl = "search/query/complate";

	var suggestUrl = "search/query/suggestInfo";







	function doCom(){
		$.ajax({
			type: "POST",
			url: complateUrl,
			dataType:"json",
			data: $("#complate").serialize(),
			success: function (result) {
				$("#valueInfo").val(result.data.comSet);
			},
			error: function(result) {
				alert("error:"+result);
			}
		});
	}



    function doSuggest(){
        $.ajax({
            type: "POST",
            url: suggestUrl,
            dataType:"json",
            data: $("#suggest").serialize(),
            success: function (result) {
                var rst="";
                console.log(result.data.list);
                for(var ele in result.data.list){
                    rst = rst + result.data.list[ele].suggestName + "----" + result.data.list[ele].areaName + "</br>;";
                }
                console.log(result);
                $("#valueInfo").val(rst);
            },
            error: function(result) {
                alert("error:"+result);
            }
        });
    }


</script>


</body>

</html>
