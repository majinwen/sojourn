<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/iCheck/custom.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/treeview/bootstrap-treeview.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css?${VERSION}" rel="stylesheet">
    <!-- 下拉框搜索插件-->
    <link href="${staticResourceUrl}/css/combo.select.css${VERSION}" rel="stylesheet">
      <style>
          .row {
              display: -webkit-box;
              display: -webkit-flex;
              display: -ms-flexbox;
              display: flex;
              flex-wrap: wrap;
          }
          .row > [class*='col-'] {
              display: flex;
              flex-direction: column;
          }
      </style>
      <!-- 全局js -->
      <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
      <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
      <script type="text/JavaScript" src="${staticResourceUrl}/ckeditor/ckeditor.js"></script>
      <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
      <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
      <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>

      <!-- blueimp gallery -->
      <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
      <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
      <script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
      <!-- 下拉框搜索插件-->
      <script src="${staticResourceUrl}/js/minsu/common/jquery.combo.select.js${VERSION}"></script>
      <script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
      <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
      <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
      <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
</head>
  <body class="gray-bg">
  <%--<div class="wrapper wrapper-content animated fadeInRight">--%>
  <div class="wrapper wrapper-content">
      <div class="row">
          <div class="col-sm-12">
              <div class="ibox float-e-margins">
                  <div id="regionItemAll" class="ibox-content">

                      <div class="row show-grid">
                          <div class="col-md-1"><h2>${showName}：</h2></div>
                          <div class="col-md-7">
                              <input id="fid" name="fid" type="hidden" readonly="true" value="${fileCity.fid}"/>
                              <input id="cityCode" name="cityCode" type="hidden" readonly="true" value="${cityCode}"/>
                                <textarea name="cityFileContent" id="cityFileContent" rows="100" cols="100">
                                  ${fileCity.cityFileContent}
                                </textarea>
                              <script>
                                  CKEDITOR.replace( 'cityFileContent' ,{
                                      //图片上传发送路径，returnURL为参数,到服务器后跳转回来的页面
                                      filebrowserImageUploadUrl : '${basePath}cityFile/uploadCkeditorPic',
                                      filebrowserBrowseUrl : '${basePath}cityFile/uploadCkeditorPic'
                                  });
                              </script>
                              <br><br>
                              <div class="row">
                                  &nbsp;&nbsp;&nbsp;&nbsp;
                                  <button type="button" id="submit" class="btn btn-primary" >保存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  <button type="button" id="cancel" class="btn btn-white" >取消</button>
                              </div>
                          </div>
                      </div>

                      <div class="row show-grid">
                          <div class="col-md-1"><h4>商圈/景点档案:</h4></div>
                          <div class="col-md-3">
                              <button id="addRegion" name="addRegion" type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal">添加商圈景点</button>
                          </div>
                      </div>
                      <div class="row show-grid">
                          <div class="col-md-1"><h4>推荐项目:</h4></div>
                          <div class="col-md-3">
                              <button id="addRegionItem" name="addRegionItem" type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal2">添加推荐项目</button>
                          </div>
                      </div>
                      <c:forEach items="${existRegionList}" var="exist">
                      <div class="row show-grid">
                          <div class="col-md-9">
                              <button id="reg" type="button" value="${exist.hotRegionFid}" onclick="upRegion('${exist.hotRegionFid}','${exist.regionName}')" data-toggle="modal" data-target="#myModal4" class="btn btn-inverse">${exist.regionName}</button>
                              <div id="allItem${exist.hotRegionFid}" class="row show-grid">
                                  <c:forEach items="${itemList}" var="items" varStatus="no">
                                      <c:if test="${exist.hotRegionFid == items.hotRegionFid && !empty items.itemName}">
                                          <div  class="col-md-2">
                                              <button id="item${items.itemFid}" name="reg" type="button" value="${items.itemFid}" onclick="upItem('${items.itemFid}','${exist.regionName}')" class="btn btn-warning" data-toggle="modal" data-target="#myModal3">${items.itemName}</button>
                                          </div>
                                      </c:if>
                                  </c:forEach>
                              </div>
                          </div>
                      </div>
                      </c:forEach>
                  </div>
              </div>
          </div>
      </div>
  </div>

  <!-- 添加 菜单  景点商圈弹出框 -->
  <div class="modal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true" data-keyboard="false" data-backdrop="static">
      <div class="modal-dialog" style="width:1000px;">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                  </button>
                  <h4 class="modal-title">添加商圈景点内容</h4>
              </div>
              <div class="modal-body">
                  <div class="wrapper wrapper-content animated fadeInRight">
                      <div class="row">
                          <div class="col-sm-14">
                              <div class="ibox float-e-margins">
                                  <div class="ibox-content">
                                      <form id="region" class="form-horizontal m-t">
                                          <!-- 传递隐藏域参数cityCode-->
                                          <input id="code" type="hidden" name="cityCode" value="${cityCode}">
                                          <div class="form-group">
                                              <label class="col-sm-3 control-label">商圈/景点</label>
                                              <div class="col-sm-8 jq22">
                                                  <select id="regionsSelect" class="form-control m-b" onchange="backContent(this)" >
                                                      <option value="-1">请选择</option>
                                                      <%--<c:forEach items="${regionList}" var="region">
                                                          <option id="hotRegionFid" value="${region.hotRegionFid}">${region.regionName}</option>
                                                      </c:forEach>--%>
                                                  </select>
                                              </div>
                                          </div>
                                          <div class="form-group">
                                              <label class="col-sm-3 control-label">商圈描述</label>
                                              <div class="col-sm-8">
                                                  <input id="hotRegionBrief" name="brief" class="form-control" type="text"  value="" aria-required="true" aria-invalid="true" class="error">
                                              </div>
                                          </div>
                                          <div class="form-group">
                                              <label class="col-sm-3 control-label">商圈/景点档案</label>
                                          </div>
                                          <div class="form-group" align="center">
                        <textarea name="hotRegionContent" id="hotRegionContent">
                          <pre></pre>
                        </textarea>
                                              <script>
                                                  //CKEDITOR.replace( 'hotRegionContent' );
                                                  CKEDITOR.replace( 'hotRegionContent' ,{
                                                      //图片上传发送路径，returnURL为参数,到服务器后跳转回来的页面
                                                      filebrowserImageUploadUrl : '${basePath}cityFile/uploadCkeditorPic',
                                                      filebrowserBrowseUrl : '${basePath}cityFile/uploadCkeditorPic'
                                                  });
                                              </script>
                                          </div>
                                          <!-- 用于 将表单缓存清空 -->
                                          <input type="reset" style="display:none;" />
                                      </form>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="modal-footer">
                  <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                  <button type="button" id="saveRegion" class="btn btn-primary" >保存</button>
              </div>
          </div>
      </div>
  </div>
  <!-- 修改  景点商圈弹出框 -->
  <div class="modal" id="myModal4" tabindex="-1" role="dialog" aria-hidden="false" data-keyboard="false" data-backdrop="static">
      <div class="modal-dialog" style="width:1000px;">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                  </button>
                  <h4 class="modal-title">添加商圈景点内容</h4>
              </div>
              <div class="modal-body">
                  <div class="wrapper wrapper-content animated fadeInRight">
                      <div class="row">
                          <div class="col-sm-14">
                              <div class="ibox float-e-margins">
                                  <div class="ibox-content">
                                      <form id="region4" class="form-horizontal m-t">
                                          <!-- 传递隐藏域参数cityCode-->
                                          <input id="code4" type="hidden" name="cityCode" value="${cityCode}">
                                          <input id="regionFid" type="hidden" name="regionFid" value="">
                                          <div class="form-group">
                                              <label class="col-sm-3 control-label">商圈/景点</label>
                                              <div class="col-sm-8">
                                                  <%--<select id="regionsSelect" class="form-control m-b" onchange="backContent(this)" >
                                                      <option value="-1">请选择</option>
                                                      <c:forEach items="${regionList}" var="region">
                                                          <option id="hotRegionFid" value="${region.hotRegionFid}">${region.regionName}</option>
                                                      </c:forEach>
                                                  </select>--%>
                                                  <input id="hotRegionName" hotRegionName="brief" readonly="true" class="form-control" type="text"  value="" aria-required="true" aria-invalid="true" class="error">
                                              </div>
                                          </div>
                                          <div class="form-group">
                                              <label class="col-sm-3 control-label">商圈描述</label>
                                              <div class="col-sm-8">
                                                  <input id="hotRegionBrief4" name="brief" class="form-control" type="text"  value="" aria-required="true" aria-invalid="true" class="error">
                                              </div>
                                          </div>
                                          <div class="form-group">
                                              <label class="col-sm-3 control-label">商圈/景点档案</label>
                                          </div>
                                          <div class="form-group" align="center">
                                            <textarea name="hotRegionContent4" id="hotRegionContent4">
                                              <pre></pre>
                                            </textarea>
                                              <script>
                                                  //CKEDITOR.replace( 'hotRegionContent' );
                                                  CKEDITOR.replace( 'hotRegionContent4' ,{
                                                      //图片上传发送路径，returnURL为参数,到服务器后跳转回来的页面
                                                      filebrowserImageUploadUrl : '${basePath}cityFile/uploadCkeditorPic',
                                                      filebrowserBrowseUrl : '${basePath}cityFile/uploadCkeditorPic'
                                                  });
                                              </script>
                                          </div>
                                          <!-- 用于 将表单缓存清空 -->
                                          <input type="reset" style="display:none;" />
                                      </form>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="modal-footer">
                  <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                  <button type="button" id="updateRegion" class="btn btn-primary" >修改</button>
              </div>
          </div>
      </div>
  </div>

  <!-- 添加 菜单  推荐项目 弹出框 -->
  <div class="modal" id="myModal2" tabindex="-1" role="dialog" aria-hidden="false" data-keyboard="false" data-backdrop="static">
      <div class="modal-dialog" style="width:1000px;">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                  </button>
                  <h4 class="modal-title">添加推荐项目</h4>
              </div>
              <div class="modal-body">
                  <div class="wrapper wrapper-content animated fadeInRight">
                      <div class="row">
                          <div class="col-sm-14">
                              <div class="ibox float-e-margins">
                                  <div class="ibox-content">
                                      <form id="itemForn" class="form-horizontal m-t" >
                                          <!-- 传递隐藏域参数cityCode-->
                                          <input id="itemFid" type="hidden" name="cityCode" value="">
                                          <div class="form-group">
                                              <label class="col-sm-1 control-label">商圈/景点</label>
                                              <div class="col-sm-2">
                                                  <input id="regions" name="resType"  type="hidden" class="form-control" type="text"  value="${menu.resType }" aria-required="true" aria-invalid="false" class="valid">
                                                  <select id="itemSelect" class="form-control m-b">
                                                      <option value="-1">请选择</option>
                                                      <c:forEach items="${existRegionList}" var="region">
                                                          <option  value="${region.hotRegionFid}">${region.regionName}</option>
                                                      </c:forEach>
                                                  </select>
                                              </div>
                                          </div>
                                          <div class="form-group">
                                              <label class="col-sm-1 control-label">推荐项目</label>
                                              <div class="col-sm-2">
                                                  <input id="itemName" name="itemName" class="form-control" type="text"  value="" aria-required="true" aria-invalid="true" class="error">
                                              </div>
                                              <label class="col-sm-1 control-label">推荐描述</label>
                                              <div class="col-sm-2">
                                                  <input id="itemBrief" name="itemBrief" class="form-control" type="text"  value="" aria-required="true" aria-invalid="true" class="error">
                                              </div>
                                          </div>
                                          <div class="form-group">
                                              <label class="col-sm-1 control-label">摘要</label>
                                              <div class="col-sm-2">
                                                  <textarea name="itemAbstract" id="itemAbstract" cols="45" rows="6">

                                                    </textarea>
                                              </div>
                                          </div>
                                          <div class="form-group">
                                              <label class="col-sm-3 control-label">项目内容</label>
                                          </div>
                                          <div class="form-group" align="center">
                                            <textarea name="itemContent" id="itemContent">
                                              <pre></pre>
                                            </textarea>
                                              <script>
                                                  //CKEDITOR.replace( 'itemContent' );
                                                  CKEDITOR.replace( 'itemContent' ,{
                                                      //图片上传发送路径，returnURL为参数,到服务器后跳转回来的页面
                                                      filebrowserImageUploadUrl : '${basePath}cityFile/uploadCkeditorPic',
                                                      filebrowserBrowseUrl : '${basePath}cityFile/uploadCkeditorPic'
                                                  });
                                              </script>
                                          </div>
                                          <!-- 用于 将表单缓存清空 -->
                                          <input type="reset" style="display:none;" />
                                      </form>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="modal-footer">
                  <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                  <button type="button" id="saveItem" class="btn btn-primary" >保存</button>
              </div>
          </div>
      </div>
  </div>
<!-- 推荐项的修改弹层-->
  <div class="modal" id="myModal3" tabindex="-1" role="dialog" aria-hidden="false" data-keyboard="false" data-backdrop="static">
      <div class="modal-dialog" style="width:1000px;">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                  </button>
                  <h4 class="modal-title">添加推荐项目</h4>
              </div>
              <div class="modal-body">
                  <div class="wrapper wrapper-content animated fadeInRight">
                      <div class="row">
                          <div class="col-sm-14">
                              <div class="ibox float-e-margins">
                                  <div class="ibox-content">
                                      <form id="itemFormUp"  class="form-horizontal m-t" >
                                          <!-- 传递隐藏域参数cityCode-->
                                          <input id="itemFidUp" type="hidden" name="itemFidUp" value="">
                                          <div class="form-group">
                                              <label class="col-sm-1 control-label">商圈/景点</label>
                                              <div class="col-sm-2">
                                                  <input id="regionItmes" name="resType"  type="hidden" class="form-control" type="text"  value="${menu.resType }" aria-required="true" aria-invalid="false" class="valid">
                                                  <input id="regionName" name="regionName" class="form-control" type="text" readonly="true" value="" aria-required="true" aria-invalid="true" class="error">
                                                  <input id="regionFidUp" name="regionFidUp" class="form-control" type="hidden" readonly="true" value="" aria-required="true" aria-invalid="true" class="error">
                                              </div>
                                          </div>
                                          <div class="form-group">
                                              <label class="col-sm-1 control-label">推荐项目</label>
                                              <div class="col-sm-2">
                                                  <input id="itemNameUp" name="itemName" class="form-control" type="text"  value="" aria-required="true" aria-invalid="true" class="error">
                                              </div>
                                              <label class="col-sm-1 control-label">推荐描述</label>
                                              <div class="col-sm-2">
                                                  <input id="itemBriefUp" name="itemBrief" class="form-control" type="text"  value="" aria-required="true" aria-invalid="true" class="error">
                                              </div>
                                          </div>
                                          <div class="form-group">
                                              <label class="col-sm-1 control-label">摘要</label>
                                              <div class="col-sm-2">
                                                  <textarea name="itemAbstract" id="itemAbstractUp" cols="45" rows="6">

                                                    </textarea>
                                              </div>
                                          </div>
                                          <div class="form-group">
                                              <label class="col-sm-3 control-label">项目内容</label>
                                          </div>
                                          <div class="form-group" align="center">
                                            <textarea name="itemContentUp" id="itemContentUp">
                                              <pre></pre>
                                            </textarea>
                                              <script>
                                                  CKEDITOR.replace( 'itemContentUp' ,{
                                                      //图片上传发送路径，returnURL为参数,到服务器后跳转回来的页面
                                                      filebrowserImageUploadUrl : '${basePath}cityFile/uploadCkeditorPic',
                                                      filebrowserBrowseUrl : '${basePath}cityFile/uploadCkeditorPic'
                                                  });
                                              </script>
                                          </div>
                                          <!-- 用于 将表单缓存清空 -->
                                          <input type="reset" style="display:none;" />
                                      </form>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="modal-footer">
                  <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                  <button type="button" id="updateItem" class="btn btn-primary" >修改</button>
              </div>
          </div>
      </div>
  </div>
  <script type="text/javascript">

      /** 点击添加景点商圈去实时查询景点商圈列表*/
      $("#addRegion").click(function () {
          var cityCode = '${cityCode}';
          if(isNullOrBlank(cityCode)){
              return;
          }
          //实时查询景点商圈
          $.ajax({
              type: "POST",
              url: "archive/archiveMgt/searchRegionList",
              dataType:"json",
              data: {"cityCode":cityCode},
              success: function (result) {
                var len = result.data.list.length;
                var list = result.data.list;
                for(var i=0;i<len;i++){
                    $("#regionsSelect").append("<option id='hotRegionFid' value='"+list[i].hotRegionFid+"''>"+list[i].regionName+"</option>");
                }
              $('#regionsSelect').comboSelect();
              },
              error: function(result) {
                  alert(result.msg);
              }
          });

      })
      function isNullOrBlank(obj){
          return obj == undefined || obj == null || $.trim(obj).length == 0;
      }
      function backContent(val) {
          $("#hotRegionBrief").val("");
          CKEDITOR.instances['hotRegionContent'].setData('');
      }
      function upRegion(hotRegionFid,regionName){
          //var hotRegionFid = $(val).val();
//          console.log(hotRegionFid);
          $.ajax({
              type: "POST",
              url: "archive/archiveMgt/searchRegionOne",
              dataType:"json",
              data: {"hotRegionFid":hotRegionFid},
              success: function (result) {
                  $('#myModal4').modal('show');
                  if(!isNullOrBlank(result.data.region)){
                      $("#regionFid").val(hotRegionFid);
                      $("#hotRegionName").val(regionName);
                      $("#hotRegionBrief4").val(result.data.region.hotRegionBrief);
                      CKEDITOR.instances['hotRegionContent4'].setData(result.data.region.hotRegionContent);
//                      $("#hotRegionContent").html(result.data.region.hotRegionContent);
                  }else{
                      $("#hotRegionBrief4").val("");
                      CKEDITOR.instances['hotRegionContent4'].setData('');
                  }
              },
              error: function(result) {
                  alert(result.msg);
              }
          });
      }
  </script>
  <script type="text/javascript">
      /**保存景点商圈内容*/
      $("#saveRegion").click(function(){
          var hotRegionFid = $.trim($("#regionsSelect").val());
          var regionName=$("#regionsSelect").find("option:selected").text();
          $("#regionsSelect option[value = "+hotRegionFid+"]").remove();
          var txt = $('#regionsSelect  option:selected').text();
          var cityCode = $.trim($("#code").val());
          var hotRegionBrief = $.trim($("#hotRegionBrief").val());
          var hotRegionContent = CKEDITOR.instances['hotRegionContent'].getData();
//          console.log(hotRegionFid+cityCode+hotRegionBrief+hotRegionContent);
          if(isNullOrBlank(cityCode) || isNullOrBlank(hotRegionFid) || (hotRegionFid == '-1') ){
              alert("请选择景点商圈");
              $('#myModal').modal('hide')
              $("input[type=reset]").trigger("click");
              return;
          }
         //校验通过  则提交
          $.ajax({
              type: "POST",
              url: "archive/archiveMgt/saveHotRegion",
              dataType:"json",
              data: {"hotRegionFid":hotRegionFid,"cityCode":cityCode,"hotRegionBrief":hotRegionBrief,"hotRegionContent":hotRegionContent},
              success: function (result) {
//                  $('#myModal').modal('hide')
//                  $("input[type=reset]").trigger("click");
                  $("#allRegion").append("<button  class='btn btn-inverse'>"+regionName+"</button>");
                  $("#regionItemAll").append("<div class='row show-grid'> <div class='col-md-9'> <button id='reg' type='button'  onclick='upRegion(\""+hotRegionFid+"\",\""+regionName+"\")' data-toggle='modal' data-target='#myModal4' class='btn btn-inverse'>"+regionName+"</button> <div id='allItem"+hotRegionFid+"' class='row show-grid'> </div> </div> </div>");
                  $("#itemSelect").append("<option  value='"+hotRegionFid+"'>"+regionName+"</option>");
                  /*$('#myModal').modal('hide');
                  $("input[type=reset]").trigger("click");*/
                  layer.alert('保存成功,点击关闭或者X号退出弹层', {icon: 6,time: 2000, title:'提示'});
              },
              error: function(result) {
                  alert(result.msg);
              }
          });

      })
      /** 修改景点商圈*/
      $("#updateRegion").click(function(){
//          alert("保存被单击了");
          var hotRegionFid = $.trim($("#regionFid").val());
          //var regionName=$("#regionsSelect").find("option:selected").text();
          //var txt = $('#regionsSelect  option:selected').text();
          var cityCode = $.trim($("#code4").val());
          var hotRegionBrief = $.trim($("#hotRegionBrief4").val());
          var hotRegionContent = CKEDITOR.instances['hotRegionContent4'].getData();
//          console.log(hotRegionFid+cityCode+hotRegionBrief+hotRegionContent);
          if(isNullOrBlank(cityCode) || isNullOrBlank(hotRegionFid)){
              alert("请选择景点商圈");
              $('#myModal4').modal('hide')
              $("input[type=reset]").trigger("click");
              return;
          }
          //校验通过  则提交
          $.ajax({
              type: "POST",
              url: "archive/archiveMgt/saveHotRegion",
              dataType:"json",
              data: {"hotRegionFid":hotRegionFid,"cityCode":cityCode,"hotRegionBrief":hotRegionBrief,"hotRegionContent":hotRegionContent},
              success: function (result) {
                  /*$('#myModal4').modal('hide');
                  $("input[type=reset]").trigger("click");*/
                  layer.alert('修改成功,点击关闭或者X号退出弹层', {icon: 6,time: 2000, title:'提示'});
              },
              error: function(result) {
                  alert(result.msg);
              }
          });

      })
      /*保存推荐项内容*/
      $("#saveItem").click(function(){
          var hotRegionFid = $.trim($("#itemSelect").val());
          var hotRegionName = $("#itemSelect").find("option:selected").text();
          var itemName = $.trim($("#itemName").val());
          var itemBrief = $.trim($("#itemBrief").val());
          var itemAbstract = $.trim($("#itemAbstract").val());
          var itemContent = CKEDITOR.instances['itemContent'].getData();
          console.log(hotRegionFid+itemName+itemBrief+itemAbstract+itemContent);
          if(isNullOrBlank(hotRegionFid) || (hotRegionFid == '-1') ){
              alert("请选择景点商圈");
              /*$('#myModal2').modal('hide')
              $("input[type=reset]").trigger("click");*/
              return;
          }else if(isNullOrBlank(itemName)){
              alert("请填写推荐项名称");
              /*$('#myModal2').modal('hide')
              $("input[type=reset]").trigger("click");*/
              return;
          }
          //校验通过  则提交
          $.ajax({
              type: "POST",
              url: "archive/archiveMgt/saveRegionItem",
              dataType:"json",
              data: {"hotRegionFid":hotRegionFid,"itemName":itemName,"itemBrief":itemBrief,"itemAbstract":itemAbstract,"itemContent":itemContent},
              success: function (result) {
                  var itemFid = result.data.itemFid;
                  /*$('#myModal').modal('hide')
                  $("input[type=reset]").trigger("click");*/
//                  window.location.reload();
                  //$("#allItem"+hotRegionFid).append("&nbsp;&nbsp;&nbsp;&nbsp;<button id='item"+itemFid+"' class='btn btn-warning' onclick='upItem(\""+itemFid+"\",\""+hotRegionName+"\")' class='btn btn-warning' data-toggle='modal' data-target='#myModal3' >"+itemName+"</button>");
                  $("#allItem"+hotRegionFid).append("<div  class='col-md-2'> <button id='item"+itemFid+"' name='reg' type='button' onclick='upItem(\""+itemFid+"\",\""+hotRegionName+"\")' class='btn btn-warning' data-toggle='modal' data-target='#myModal3'>"+itemName+"</button> </div>");
                  /*$('#myModal2').modal('hide')
                  $("input[type=reset]").trigger("click");*/
                  layer.alert('保存成功,点击关闭或者X号退出弹层', {icon: 6,time: 2000, title:'提示'});
              },
              error: function(result) {
                  alert(result.msg);
              }
          });

      })
      
      /** 修改推荐项内容*/
      $("#updateItem").click(function () {
          var itemFid = $.trim($("#itemFidUp").val());
          var hotRegionFid = $.trim($("#regionFidUp").val());
          var itemName = $.trim($("#itemNameUp").val());
          var itemBrief = $.trim($("#itemBriefUp").val());
          var itemAbstract = $.trim($("#itemAbstractUp").val());
          var itemContent = CKEDITOR.instances['itemContentUp'].getData();
          $("#item"+itemFid).text(itemName);
          console.log(hotRegionFid+itemName+itemBrief+itemAbstract+itemContent);
          if(isNullOrBlank(hotRegionFid) || (hotRegionFid == '-1') ){
              alert("请选择景点商圈");
              $('#myModal3').modal('hide')
              $("input[type=reset]").trigger("click");
              return;
          }else if(isNullOrBlank(itemName)){
              alert("请填写推荐项名称");
              $('#myModal3').modal('hide')
              $("input[type=reset]").trigger("click");
              return;
          }
          //校验通过  则提交
          $.ajax({
              type: "POST",
              url: "archive/archiveMgt/saveRegionItem",
              dataType:"json",
              data: {"itemFid":itemFid,"hotRegionFid":hotRegionFid,"itemName":itemName,"itemBrief":itemBrief,"itemAbstract":itemAbstract,"itemContent":itemContent},
              success: function (result) {
                 /* $('#myModal').modal('hide')
                   $("input[type=reset]").trigger("click");*/
//                  window.location.reload();
                  /*$('#myModal3').modal('hide')
                  $("input[type=reset]").trigger("click");*/
                  layer.alert('修改成功,点击关闭或者X号退出弹层', {icon: 6,time: 2000, title:'提示'});
              },
              error: function(result) {
                  alert(result.msg);
              }
          });
      })

      /**
       * 取消按钮
       * @Author lunan【lun14@ziroom.com】
       * @Date 2016/11/16 14:26
       */
      $("#cancel").click(function () {
          $.callBackParent("archive/archiveMgt/listCityArchive",true,saveColumnCityCallback);
      });
    /** 保存城市档案*/
      $("#submit").click(function(){
          var fid = $("#fid").val();
          var cityCode = $("#cityCode").val();
          var cityFileContent = CKEDITOR.instances['cityFileContent'].getData();
          if(isNullOrBlank(cityCode)){
              alert("请选择城市");
              return;
          }
          $.ajax({
              type: "POST",
              url: "archive/archiveMgt/saveCityArchive",
              dataType:"json",
              data: {"fid":fid,"cityCode":cityCode,"cityFileContent":cityFileContent},
              success: function (result) {
                  if(result.code==0){
                      $.callBackParent("archive/archiveMgt/listCityArchive",true,saveColumnCityCallback);
                  }else{
                      layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
                  }
              },
              error: function(result) {
                  layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
              }
          });

      });
      //保存商机刷新父页列表
      function saveColumnCityCallback(panrent){
          panrent.refreshData("listTableS");
      }

    /** 修改推荐项事件*/
      function upItem(fid,regionName) {
//        alert("修改事件被触发");
        if(isNullOrBlank(fid) || isNullOrBlank(regionName)){
            alert("请选择正确的推荐项");
            return;
        }
        $.ajax({
            type: "POST",
            url: "archive/archiveMgt/getRegionItem",
            dataType:"json",
            data: {"itemFid":fid},
            success: function (result) {
                $("#itemFidUp").val(fid);
                $("#regionName").val(regionName);
                $("#regionFidUp").val(result.data.item.hotRegionFid);
                $("#itemNameUp").val(result.data.item.itemName);
                $("#itemBriefUp").val(result.data.item.itemBrief);
                $("#itemAbstractUp").val(result.data.item.itemAbstract);
                CKEDITOR.instances['itemContentUp'].setData(result.data.item.itemContent);
            },
            error: function(result) {
                $("#regionName").val('');
                $("#regionFidUp").val('');
                $("#itemNameUp").val('');
                $("#itemBriefUp").val('');
                $("#itemAbstractUp").val('');
                CKEDITOR.instances['itemContentUp'].setData('');
                $('#myModal3').modal('hide')
                $("input[type=reset]").trigger("click");
                alert(result.msg);
            }
        });
    }

      /** 添加推荐项，清空表单缓存*/
      $("#addRegionItem").click(function () {
          $("#itemName").val("");
          $("#itemBrief").val("");
          $("#itemAbstract").val("");
          CKEDITOR.instances['itemContent'].setData('');
      })
  </script>
	</body>
</html>
