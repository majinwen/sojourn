(function ($) {

    var editFlag = "${editFlag}";

    var order = {};

    var currOrderSn="";

    order.init = function(){
        //外部js调用
        laydate({
            elem: '#createTimeStart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });
        laydate({
            elem: '#createTimeEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });

        //点击单元格 查看订单详情
        $('#listTable').on('click-cell.bs.table',
            function(field, value, row, $element) {
                if(value == "fid"){
                    var orderSn = $element.orderSn;
                    order.getOrderDetail(orderSn);
                }
            });
    }

    order.getAccountStatu = function(value){
    	if(value == 0){
    		return "未结算";
    	}else if(value == 1){
    		return "结算中";
    	}else if(value == 2){
    		return "结算完成";
    	}else{
    		return "未知";
    	}
    	
    }
    
    /**
     * 订单表查询参数
     */
    order.paginationParam = function (params) {
    	return {
            limit: params.limit,
            page: $('#listTable').bootstrapTable('getOptions').pageNumber,
            orderSn:$.trim($('#orderSn').val()),
            houseName:$.trim($('#houseName').val()),
            nationCode:$.trim($('#nationCode').val()),
            provinceCode:$.trim($('#provinceCode').val()),
            cityCode:$.trim($('#cityCode').val()),
            userName:$.trim($('#userName').val()),
            userTel:$.trim($('#userTel').val()),
            landlordName:$.trim($('#landlordName').val()),
            landlordTel:$.trim($('#landlordTel').val()),
            orderStatus:$("#orderStatus").val(),
            createTimeStart:$('#createTimeStart').val(),
            createTimeEnd:$("#createTimeEnd").val(),
            orderOprationType:$("#orderOprationType").val(),
            payStatus:$("#payStatus").val(),
            accountsStatus:$("#accountsStatus").val(),
            empPushName:$.trim($("#empPushName").val()),
            empGuardName:$.trim($("#empGuardName").val()),
            houseSn:$.trim($("#houseSn").val()),
            orderType:$("#orderType").val(),
            orderActityType:$("#orderActityType").val(),
        };
    }

    /**
     * 差额审核查询列表
     */
    order.orderReParam = function (params) {

        var createTimeStart  = $.trim($('#createTimeStart').val());
        var createTimeEnd =$.trim($('#createTimeEnd').val());

        if(createTimeStart!=null&&createTimeStart!=""&&createTimeStart!=undefined){
            createTimeStart = createTimeStart+" 00:00:00"
        }
        if(createTimeEnd!=null&&createTimeEnd!=""&&createTimeEnd!=undefined){
            createTimeEnd = createTimeEnd+" 23:59:59"
        }
        return {
            limit: params.limit,
            page: $('#listTable').bootstrapTable('getOptions').pageNumber,
            newUserName:$.trim($('#newUserName').val()),
            newUserTel:$.trim($('#newUserTel').val()),
            nationCode:$.trim($('#nationCode').val()),
            provinceCode:$.trim($('#provinceCode').val()),
            checkedStatus:$.trim($('#checkedStatus').val()),
            cityCode:$.trim($('#cityCode').val()),
            oldOrderSn:$.trim($('#oldOrderSn').val()),
            createTimeStart:$('#createTimeStart').val(),
            createTimeEnd:$("#createTimeEnd").val()
        };
    }
    /* 列表 操作 值设置 */
    order.menuOperData = function (value, row, index){
        var _html = "";
        _html +="<button id='addMenuButton"+index+"' style='padding:6px 10px;' orderSn='\""+row.orderSn+"\"' type='button' class='btn btn-primary btn1'onclick='Order.getOrderDetail(\""+row.orderSn+"\");' data-toggle='modal' data-target='#myModal'>详情</button>";
        value = parseInt(value);
        //强制取消申请中的操作
        if(value == 34 && editFlag != 1){
            _html = "";
            _html +="&nbsp;<button id='addMenuButton"+index+"' style='padding:6px 10px;' orderSn='\""+row.orderSn+"\"' type='button' class='btn btn-primary btn1'onclick='Order.recoveryCancelOrder(\""+row.orderSn+"\");' data-toggle='modal' data-target='#myModal'>恢复</button>";
            _html +="&nbsp;<button id='addMenuButton"+index+"' style='padding:6px 10px;' orderSn='\""+row.orderSn+"\"' type='button' class='btn btn-primary btn1'onclick='Order.toAgreeCancelOrder(\""+row.orderSn+"\");' data-toggle='modal' data-target='#myModal'>同意</button>";
        }
        return _html;
    }

    /**
     * 差额列表的操作
     */
    order.orderReOper = function (value, row, index){
        var _html = "";
        value = parseInt(value);
        if(value==0){
            _html +="<button id='addMenuButton"+index+"' style='padding:6px 10px;' orderSn='\""+row.orderSn+"\"' type='button' class='btn btn-primary btn1'onclick='Order.toReNewOrder(\""+row.oldOrderSn+"\");' data-toggle='modal' data-target='#myModal'>关联旧订单</button>";
        }
        if(value==1){
            _html +="<button id='addMenuButton"+index+"' style='padding:6px 10px;' orderSn='\""+row.orderSn+"\"' type='button' class='btn btn-primary btn1'onclick='Order.auditOrderRelation(\""+row.oldOrderSn+"\",\""+row.newOrderSn+"\",2);' data-toggle='modal' data-target='#myModal'>通过</button>";
            _html +="<button id='addMenuButton"+index+"' style='padding:6px 10px;' orderSn='\""+row.orderSn+"\"' type='button' class='btn btn-primary btn1'onclick='Order.auditOrderRelation(\""+row.oldOrderSn+"\",\""+row.newOrderSn+"\",4);' data-toggle='modal' data-target='#myModal'>不通过</button>";
        }

        return _html;
    }

    /**
     * 关联新订单
     */
    order.toReNewOrder = function(oldOrderSn){
        $("#oldOrderSnM").val(oldOrderSn);
        $("#mymodal").modal("show");
    }

    /**
     * 获取两个订单的差额
     */
    order.getTwoOrderBlanace = function(){

        var newOrderSn = $("#newOrderSnM").val();
        var oldOrderSn =  $("#oldOrderSnM").val();

        if(newOrderSn == null||newOrderSn==""||newOrderSn==undefined){
            alert("新订单号不存在");
            return false;
        }
        if(oldOrderSn == null||oldOrderSn==""||oldOrderSn==undefined){
            alert("老订单号不存在");
            return false;
        }

        CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"/order/getMoneyLast", {"newOrderSn":newOrderSn,"oldOrderSn":oldOrderSn}, function(data){
            if(data){
                if(data.code == 1){
                    alert(data.msg);
                    $("#twoOrderBlanace").val("");
                    $("#twoOrderBlanaceDiv").hide();
                    return false;
                }
                $("#twoOrderBlanace").val(data.data.twoOrderBalance);
                $("#twoOrderBlanaceDiv").show();
            }
        })

    }

    /**
     * 新旧订单关联保存
     */
    order.saveOrderRelation = function(){
        var newOrderSn = $("#newOrderSnM").val();
        var oldOrderSn =  $("#oldOrderSnM").val();
        var twoOrderBlanace = $("#twoOrderBlanace").val();
        if(newOrderSn == null||newOrderSn==""||newOrderSn==undefined){
            alert("新订单号不存在");
            return false;
        }
        if(oldOrderSn == null||oldOrderSn==""||oldOrderSn==undefined){
            alert("老订单号不存在");
            return false;
        }

        if(twoOrderBlanace == null||twoOrderBlanace==""||twoOrderBlanace==undefined){
            alert("新旧订单差额错误，请联系客服");
            return false;
        }
        CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"/order/saveOrderRelation", {"newOrderSn":newOrderSn,"oldOrderSn":oldOrderSn}, function(data){
            if(data){
                if(data.code == 1){
                    alert(data.msg);
                    return false;
                }
                $("#newOrderSnM").val("");
                $("#oldOrderSnM").val("");
                $("#twoOrderBlanace").val("");
                $("#twoOrderBlanaceDiv").hide();
                $('#mymodal').modal('hide')
                CommonUtils.query();
            }
        })
    }

    /**
     * 强制取消订单差额审核
     */
    order.auditOrderRelation = function(oldOrderSn,newOrderSn,checkedStatu){

        if(oldOrderSn==null||oldOrderSn==""||oldOrderSn==undefined){
            alert("老订单不存在");
            return false;
        }
        if(newOrderSn==null||newOrderSn==""||newOrderSn==undefined){
            alert("新订单不存在");
            return false;
        }
        if(checkedStatu==null||checkedStatu==""||checkedStatu==undefined){
            alert("待更新状态错误");
            return false;
        }
        CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"/order/auditOrderRelation", {"oldOrderSn":oldOrderSn,"newOrderSn":newOrderSn,"checkedStatu":checkedStatu}, function(data){
                if(data){
                    if(data.code == 1){
                        alert(data.msg);
                        return false;
                    }
                    CommonUtils.query();
                }
            }
        );
    }
    /**
     * 恢复订单
     */
    order.recoveryCancelOrder = function(orderSn){
        CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"/order/recoveryCancelOrder", {"orderSn":orderSn}, function(data){
                if(data){
                    if(data.code == 1){
                        alert(data.msg);
                        return false;
                    }
                    CommonUtils.query();
                }
            }
        );

    }
    /**
     * 同意
     */
    order.toAgreeCancelOrder = function(orderSn){
        currOrderSn = orderSn
        $("#mymodal").modal("toggle");
    }
    order.agreeCancelOrder = function(){
        var orderSn = currOrderSn;
        var orderCancelType = $("#orderCancelType").val();

        if(orderCancelType == null||orderCancelType==""||orderCancelType==undefined){
            alert("请选择同意方式");
            return false;
        }
        if(orderSn!=null&&orderSn!=""&&orderSn!=undefined){
            CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"/order/agreeCancelOrder", {"orderSn":orderSn,"orderCancelType":orderCancelType}, function(data){
                if(data){
                    if(data.code == 1){
                        alert(data.msg);
                        return false;
                    }
                    $('#mymodal').modal('hide')
                    CommonUtils.query();
                }
                currOrderSn = "";
            })
        }
    }


    /**
     * 获取订单详情
     * 说明：订单详情数据需要具体完善
     * TODO
     */
    order.getOrderDetail =  function(orderSn){
        if(orderSn == null||orderSn=="" ||orderSn == undefined){
            alert("订单号不存在");
            return false;
        }

        $.openNewTab(new Date().getTime(),'order/getOrderDetail?orderSn='+orderSn, "订单详情");

    }

    order.init();
    CommonUtils.getNationInfo('nationCode');
    window.Order = order;
    //window.OrderRe = orderRe;
}(jQuery));
