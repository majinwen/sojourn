<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/jqgrid/ui.jqgrid.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
    <style type="text/css">
        .mbottom{
            margin-bottom: 5px;
        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row row-lg">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="col-sm-1 text-right mtop">
                                <label class="control-label">大区：</label>
                            </div>
                            <div class="col-sm-1 ">
                                <select class="form-control m-b cc-region" id="selectTargetFid" name="">
                                    <option value="">请选择</option>
                                    <c:forEach items="${regionList}" var="obj">
                                        <option value="${obj.fid }">${obj.regionName }</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-sm-1 text-right mtop">
                                <label class="control-label">城市：</label>
                            </div>
                            <div class="col-sm-1">
                                <select class="form-control m-b" id="selectCityCode" name="cityCode">
                                    <option value="">请选择</option>
                                    <c:forEach items="${cityList}" var="obj">
                                        <option value="${obj.code }">${obj.name }</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-sm-1 text-right mtop">
                                <label class="control-label">月份：</label>
                            </div>
                            <div class="col-sm-1">
                                <input name="endTime" id="selectMonth" class="laydate-icon form-control layer-date">
                            </div>

                            <div class="col-sm-1 text-right mtop">
                                <label class="control-label">限制每页大区：</label>
                            </div>
                            <div class="col-sm-1">
                                <select class="form-control m-b" id="selectLimit">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>
                            </div>

                            <div class="col-sm-1">
                                <button class="btn btn-primary" type="button" onclick="queryList();">
                                    <i class="fa fa-search"></i>&nbsp;查询
                                </button>
                            </div>
                            <div class="col-sm-1">
                                <button class="btn btn-primary" type="button" id="exportFile">
                                    <i class="fa fa-search"></i>&nbsp;导出文件
                                </button>
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
                    <!--添加大区-->
                    <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#addModal"><i
                            class="fa fa-plus"></i>&nbsp;大区设置
                    </button>
                    <div class="example-wrap">
                        <div class="example">
                            <div style="width: 100%;">
                                <table id="jqGrid" style="width: 100%;"></table>
                                <div id="jqGridPager"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--设置大区弹框-->
<div class="modal inmodal" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight" style="width: 700px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span></button>
                <h4 class="modal-title">设置大区</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="ibox float-e-margins">
                        <form id="addForm" class="form-horizontal m-t">
                            <div class="form-group">
                                <div class="row">
                                    <label class="col-sm-2 control-label">大区名字：</label>
                                    <div class="col-sm-4">
                                        <input class="form-control" type="text" name="regionName" id="regionName">
                                    </div>
                                    <div class="col-sm-4">
                                        <button type="button" id="btn-addRegion" class="btn btn-primary">增加</button>
                                    </div>
                                </div>
                                <div class="row">
                                    <label class="col-sm-2 control-label">选择大区：</label>
                                    <div class="col-sm-4">
                                        <select class="form-control m-b cc-region" name="cc-region-del" id="cc-region-del">
                                            <option value="">请选择</option>
                                            <c:forEach items="${regionList}" var="obj">
                                                <option value="${obj.fid }">${obj.regionName }</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-4">
                                        <button type="button" id="btn-delRegion" class="btn btn-danger">删除</button>
                                    </div>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
                <div class="row" style="border-color:#e7eaec;border-style:solid solid none;border-width:1px 0px;"></div>
                <div class="row" style="margin-top: 10px;">
                    <div class="col-sm-2" style="margin-top: 10px;">
                        <label class="control-label">选择大区：</label>
                    </div>
                    <div class="col-sm-4">
                        <select class="form-control m-b cc-region" name="cc-region-get" id="cc-region-get">
                            <option value="">请选择</option>
                            <c:forEach items="${regionList}" var="obj">
                                <option value="${obj.fid }">${obj.regionName }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4">
                        <h4>省份</h4>
                        <div id="treeview-pro" class="" style="height: 300px;overflow-y: scroll;"></div>
                    </div>
                    <div class="col-sm-4 text-center">

                        <div class="row" style="margin-top: 50px;">
                            <button type="button" id="btn-code-add" class="btn btn-primary btn-sm">增加 >></button>
                        </div>

                        <div class="row" style="margin-top: 10px;">
                            <button type="button" id="btn-code-del" class="btn btn-danger btn-sm"><< 删除</button>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <h4>所属省份</h4>
                        <div id="treeview-check" style="height: 300px;overflow-y: scroll;"></div>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>


<!--设置城市目标 弹层-->
<div class="modal inmodal" id="cityTargetModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight" style="width: 900px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span></button>
                <h4 class="modal-title">设置城市目标</h4>
            </div>
            <div class="modal-body">
                    <div class="ibox float-e-margins">
                        <form role="form" class="form-horizontal m-t" id="target_form">
                                <div class="row mbottom">
                                    <div class="col-sm-2">
                                        <label class="control-label">城市：</label>
                                    </div>
                                    <div class="col-sm-3">
                                        <input class="form-control" type="text" name="cityName" readonly id="target_city_name">
                                        <input type="hidden" name="cityCode" id="target_city_code">
                                        <input type="hidden" name="fid" id="target_fid">
                                    </div>
                                    <div class="col-sm-2">
                                        <label class="control-label">目标月份：</label>
                                    </div>
                                    <div class="col-sm-3">
                                        <input name="targetMonth" id="target_month" class="laydate-icon form-control layer-date">
                                    </div>
                                </div>
                                <div class="row mbottom">
                                    <div class="col-sm-2">
                                        <label class="control-label">房源发布目标：</label>
                                    </div>
                                    <div class="col-sm-3">
                                        <input class="form-control" type="text" id="target_house_num" name="targetHouseNum">
                                    </div>
                                    <div class="col-sm-2">
                                        <label class="control-label">地推上架目标：</label>
                                    </div>
                                    <div class="col-sm-3">
                                        <input class="form-control" id="target_pushHouse_num" name="targetPushHouseNum" >
                                    </div>
                                </div>
                                <div class="row mbottom">
                                    <div class="col-sm-2">
                                        <label class="control-label">自主上架目标：</label>
                                    </div>
                                    <div class="col-sm-3">
                                        <input class="form-control" type="text" name="targetSelfHouseNum" id="target_selfHouse_num">
                                    </div>
                                    <div class="col-sm-2">
                                        <label class="control-label">订单目标：</label>
                                    </div>
                                    <div class="col-sm-3">
                                        <input name="targetOrderNum" id="target_order_num" class="form-control">
                                    </div>
                                </div>
                                <div class="row mbottom">
                                    <div class="col-sm-2">
                                        <label class="control-label">出租间夜目标：</label>
                                    </div>
                                    <div class="col-sm-3">
                                        <input class="form-control" type="text" name="targetRentNum" id="target_rent_num">
                                    </div>
                                </div>
                        </form>
                    </div>
                    <!--历史记录-->
                    <div class="row">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>历史记录</h5>
                            </div>
                            <div class="ibox-content">
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>房源发布目标</th>
                                            <th>地推上架目标</th>
                                            <th>自主上架目标</th>
                                            <th>订单目标</th>
                                            <th>出租间夜目标</th>
                                            <th>操作人</th>
                                            <th>操作日期</th>
                                        </tr>
                                    </thead>
                                    <tbody id="log_hs">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="btn-savect">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>
<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}005" type="text/javascript"></script>
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
<!--jqgrid-->
<script src="${staticResourceUrl}/js/plugins/jqgrid/i18n/grid.locale-cn.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/jqgrid/jquery.jqGrid.min.js${VERSION}001"></script>

<!--treeview-->
<script src="${staticResourceUrl}/js/plugins/treeview/bootstrap-treeview.js${VERSION}001"></script>

<script type="text/javascript">

    //列表中显示城市设置按钮
    function targetSet(value, index, row) {
        if (value == 1){
            return '<button class="btn btn-primary btn-xs" type="button" style="margin: 0;" data-toggle="modal" ' +
                    'data-target="#cityTargetModal"  onclick="showTargetModal(\''+row.cityName+'\',\''+row.cityCode+'\')">设置</button>';
        }else{
            return '';
        }
    }

    //展示目标详情
    function showTargetModal(name,code) {
        $("#target_city_name").val(name);
        $("#target_city_code").val(code);
        $("#target_fid").val("");
        $("#target_month").val("");
        $("#target_house_num").val("");
        $("#target_pushHouse_num").val("");
        $("#target_selfHouse_num").val("");
        $("#target_order_num").val("");
        $("#target_rent_num").val("");
        $("#log_hs").html("");
    }
    //设置城市目标
    function setData(mydata) {
        $("#target_house_num").val(mydata.targetHouseNum);
        $("#target_pushHouse_num").val(mydata.targetPushHouseNum);
        $("#target_selfHouse_num").val(mydata.targetSelfHouseNum);
        $("#target_order_num").val(mydata.targetOrderNum);
        $("#target_rent_num").val(mydata.targetRentNum);
        $("#target_fid").val(mydata.fid);
    }

    function queryList() {
        $("#jqGrid").jqGrid('setGridParam',{
            url:"cityTarget/dataList",
            postData:{
                limit : $('#selectLimit option:selected').val(),
                regionFid : $.trim($('#selectTargetFid').val()),
                cityCode : $.trim($('#selectCityCode').val()),
                targetMonth : $('#selectMonth').val()
            }, //发送数据
            page:1
        }).trigger("reloadGrid"); //重新载入
    }

    $(function () {
        //初始化日期
        laydate({
            elem: '#selectMonth',
            format: 'YYYY-MM',
            istime: true,
            istoday: true
        });
        //初始化日期
        laydate({
            elem: '#target_month',
            format: 'YYYY-MM',
            istime: true,
            istoday: true,
            min: laydate.now(),
            max:laydate.now(+60),
            choose: function(dates){ //选择好日期的回调
                var month = dates;
                var cityCode = $("#target_city_code").val();
                if (cityCode && month){
                    $.getJSON("cityTarget/showCityTargetDetail",{"cityCode":cityCode,"targetMonth":month},function (data) {
                        if (data.code == 0){
                            var mydata = data.data.target;
                            var logs = data.data.log;
                            if (mydata){
                                setData(mydata);
                            }else{
                                setData("");
                            }

                            $("#log_hs").empty();
                            var temp = "";
                            $.each(logs,function (i,n) {
                                var tr = "<tr>"
                                        +"<td>"+n.targetHouseNum+"</td>"
                                        +"<td>"+n.targetPushHouseNum+"</td>"
                                        +"<td>"+n.targetSelfHouseNum+"</td>"
                                        +"<td>"+n.targetOrderNum+"</td>"
                                        +"<td>"+n.targetRentNum+"</td>"
                                        +"<td>"+n.createEmpName+"</td>"
                                        +"<td>"+CommonUtils.formateDate(n.createDate)+"</td>";
                                temp += tr;
                            });

                            $("#log_hs").append(temp);
                        }else{
                            layer.alert(data.msg, {
                                icon: 5,
                                skin: 'layer-ext-moon'
                            });
                        }
                    });
                }
            }
        });

        //保存城市目标
        $("#btn-savect").on("click",function () {
            if ($("#target_month").val()){
                $.post("cityTarget/saveOrUpdateCityTarget",$("#target_form").serialize(),function (data) {
                    if (data.code == 0){
                        $("#cityTargetModal").modal("toggle");
                        layer.alert("保存成功", {
                            icon: 6,
                            skin: 'layer-ext-moon'
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    }else{
                        layer.alert(data.msg, {
                            icon: 5,
                            skin: 'layer-ext-moon'
                        });
                    }
                });
            }
        });



        //保存大区
        $("#btn-addRegion").on("click", function () {
            var _self = $(this);
            _self.attr("disabled", "disabled");
            var regionName = $("#regionName").val();
            if ($.trim(regionName)) {
                $.post("cityRegion/saveCityRegion", {"regionName": regionName}, function (data) {
                    if (data.code == 0) {
                        $(".cc-region").append("<option value='"+data.data.region.fid+"'>"+regionName+"</option>");
                        layer.alert('保存成功', {
                            icon: 6,
                            skin: 'layer-ext-moon'
                        });
                    } else {
                        layer.alert(data.msg, {
                            icon: 5,
                            skin: 'layer-ext-moon'
                        });
                    }
                    _self.removeAttr("disabled");
                }, 'json');
            }
        });

    });
    //初始化列表数据
    $(document).ready(function () {
        $("#jqGrid").jqGrid({
            url: 'cityTarget/dataList',
            mtype: "GET",
            datatype: "json",
            colModel: [
                {label: '大区Fid', name: 'regionFid', hidden: true},
                {label: '大区', name: 'regionName', key: true, width: 75},
                {label: '城市code', name: 'cityCode',hidden: true},
                {label: '城市', name: 'cityName', width: 150},
                {label: '目标月份', name: 'targetMonth', width: 150},
                {label: '房源上架目标', name: 'targetHouseNum', width: 150},
                {label: '房源地推上架目标', name: 'targetPushHouseNum', width: 150},
                {label: '房源自主上架目标', name: 'targetSelfHouseNum', width: 150},
                {label: '订单目标', name: 'targetOrderNum', width: 150},
                {label: '出租间夜目标', name: 'targetRentNum', width: 150},
                {label: '操作', name: 'isSet',align:"center", width: 150,formatter:targetSet}
            ],
            //loadonce: true, // just for demo purpose
            jsonReader: {
                root:"dataList", page:"currPage", total:"totalpages",
                records:"totalCount", repeatitems:false, id : "id"
            },
            postData: {
                limit: $('#selectLimit option:selected').val(),
            },
            height: 400,
            rowNum: 1000,
            sortname: 'OrderDate',
            pager: "#jqGridPager",
            viewrecords: true,
            grouping: true,
            groupingView: {
                groupField: ["regionName"],
                groupColumnShow: [true],
                groupText: ["<b>{0}</b>"],
                groupOrder: ["asc"],
                groupSummary: [false],
                groupCollapse: false
            }
        });
    });
</script>

<script>
    $.jgrid.defaults.width = 1600;
    $.jgrid.defaults.responsive = true;
    $.jgrid.defaults.styleUI = 'Bootstrap';
</script>

<!--树状图-->
<script type="text/javascript">
    //树形列表初始化数值
    var defaultData = [
        {
            text: '中国',
            href: 'sdfdsf',
            nodes: []
        }];
    //获取大区关联省列表
    function getCityRegionRel(regionFid) {
        if (regionFid){
            $.getJSON("cityRegion/cityRegionRel", {"regionFid": regionFid}, function (data) {
                var getData = [];
                if (data.code == 0) {
                    getData = data.data.array;
                } else {
                    getData = defaultData;
                }
                $('#treeview-check').treeview({data: getData});
            })
        }else{
            $('#treeview-check').treeview({data: defaultData});
        }
    }
    $(function () {
        //初始化省份所有
        $.getJSON("cityRegion/provinceList", function (data) {
            var proData = [];
            if (data.code == 0) {
                proData = data.data.array;
            } else {
                proData = defaultData;
            }
            var treeviewPro = $('#treeview-pro').treeview({
                data: proData,
                showIcon: false,
                showCheckbox: false
            });
        });

        //初始化设置列表
        var treeviewCheck = $("#treeview-check").treeview({
            data: defaultData,
            showIcon: false,
            showCheckbox: false
        });


        //更改选择大区后刷新对应的省分
        $("#cc-region-get").on("change", function () {
            var regionFid = $(this).val();
            if (regionFid) {
                getCityRegionRel(regionFid);
            }
        });


        //增加大区对应省份code
        $("#btn-code-add").on("click", function () {
            var _self = $(this);
            _self.attr("disabled", "disabled");
            var regionFid = $("#cc-region-get").val();
            if (!regionFid) {
                layer.alert("请选择大区", {
                    icon: 5,
                    skin: 'layer-ext-moon'
                });
                return;
            }
            var arr = $('#treeview-pro').treeview('getSelected');
            //数组为空直接返回
            if (arr.length == 0) {
                return;
            }
            var code = arr[0].href;
            var nodeId = arr[0].nodeId;
            //选最顶级  直接返回
            if (nodeId == 0) {
                return;
            }
            //请求更新
            $.post("cityRegion/bindRegionRel", {"regionFid": regionFid, "code": code}, function (data) {
                if (data.code == 0) {
                    //添加成功之后刷新列表
                    getCityRegionRel(regionFid);
                } else {
                    layer.alert(data.msg, {
                        icon: 5,
                        skin: 'layer-ext-moon'
                    });
                }
                _self.removeAttr("disabled");
            }, 'json');

        });

        //删除选中的省
        $("#btn-code-del").on("click", function () {
            var regionFid = $("#cc-region-get").val();
            if (!regionFid) {
                layer.alert("请选择大区", {
                    icon: 5,
                    skin: 'layer-ext-moon'
                });
                return;
            }
            var arr = $('#treeview-check').treeview('getSelected');
            //数组为空直接返回
            if (arr.length == 0) {
                return;
            }
            var code = arr[0].href;
            var nodeId = arr[0].nodeId;
            //选最顶级  直接返回
            if (nodeId == 0) {
                return;
            }
            $.post("cityRegion/delRegionRel", {"regionFid": regionFid, "code": code}, function (data) {
                if (data.code == 0){
                    getCityRegionRel(regionFid);
                }else{
                    layer.alert(data.msg, {
                        icon: 5,
                        skin: 'layer-ext-moon'
                    });
                }
            });
        });

        //导出数据
        $("#exportFile").click(function () {
            var page =$("#jqGrid").jqGrid("getGridParam","page");
            var limit = $('#selectLimit option:selected').val();
            window.location.href="cityTarget/allDataToExcel?page="+page+"&limit="+limit;
        })

        //删除大区 同时更新各种列表 并刷新树图
        $("#btn-delRegion").on("click",function () {
            var regionFid_del = $("#cc-region-del").val();
            var regionFid_get = $("#cc-region-get").val();
            if (regionFid_del){
                $.post("cityRegion/delCityRegion",{"fid":regionFid_del},function (data) {
                    if (data.code == 0){
                        $(".cc-region").val("");
                        $(".cc-region option[value='"+regionFid_del+"']").remove();
                        if (regionFid_del == regionFid_get){
                            //刷新列表
                            getCityRegionRel("");
                        }
                    }else{
                        layer.alert(data.msg, {
                            icon: 5,
                            skin: 'layer-ext-moon'
                        });
                    }
                })
            }
        });


    })


</script>

</body>
</html>
