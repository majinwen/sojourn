<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="applicable-device" content="mobile">
    <meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <title>收款信息</title>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">

</head>
<body>

<form id="customerBaseInfo" accept="" method="">
    <div class="main myCenterListNoneA">
        <header class="header">
            <a href="javascript:;" id="goback" class="goback" onclick="history.go(-1)"></a>
            <h1>收款信息</h1>
        </header>

        <ul class="myCenterList">
            <li class="clearfix bor_b no_icon">
                开户人姓名
                <input id="realName"  name="realName" readonly="readonly" value="${bankcardHolder}" placeholder="请与证件姓名保持一致" class="ipt"  />
            </li>

            <li class="clearfix bor_b no_icon">
                开户银行
                <input type="text" id="bankName" name="bankName" placeholder="请选择" value="${bankName}"  readonly="readonly">
                <input type="hidden" id="cardName" name="cardName"  value="${cardName}"  readonly="readonly">
                <select id="bankSelect"  name="" onchange="setValToInputAndShowName(this,'cardName','bankName')">
                    <option value="">请选择</option>
                    <c:forEach var="item" items="${cardList}">
                        <option value="${item.bankCode}"
                        <c:if test="${item.falg == 1}">
                            selected="selected"
                        </c:if>
                                >${item.bankName}</option>
                    </c:forEach>
                </select>
            </li>


            <li class="clearfix bor_b no_icon">
                开户城市
                <input type="text" id="cardCity" name="cardCity" placeholder="请选择" value="${province}"  readonly="readonly">
                <select id="cardCitySelect"  name="" onchange="setValToInput(this,'cardCity')">
                    <option value="">请选择</option>
                    <c:forEach var="city" items="${cityList}">
                        <option value="${city.name}">${city.name}</option>
                    </c:forEach>
                </select>


            </li>

            <li class="clearfix bor_b no_icon">
                银行卡号
                <input id="bankcardNo"  name="bankcardNo" value="${bankcardNo}" placeholder="" class="ipt"  />
            </li>
        </ul>

    </div><!--/main-->
    <div class="boxP075 mt85 mb85">
        <input type="button" value="确定" class="org_btn " id="submitBtn">
    </div>
</form>
<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/layer/layer.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>


<script type="text/javascript">
    function setValToInputAndShowName(obj,id,name){
        var val=$(obj).val();
        var txt =  $(obj).find("option:selected").text();
        $('#'+name).val(txt);
        $('#'+id).val(val);
    }

    $(function(){
        $("#submitBtn").click(function(){
            if($(this).hasClass("disabled_btn")){
                return false;
            }

            if($("#cardName").val() == "" || $("#cardName").val() == undefined || $("#cardName").val() == null){
                showShadedowTips("请填写开户银行",1);
                return false;
            }

            if($("#cardCity").val() == "" || $("#cardCity").val() == undefined || $("#cardCity").val() == null  ||  $("#cardCity").val() == "请选择"){
                showShadedowTips("请填写开户城市",1);
                return false;
            }

            if($("#bankcardNo").val() == "" || $("#bankcardNo").val() == undefined || $("#bankcardNo").val() == null){
                showShadedowTips("请填写银行卡号",1);
                return false;
            }

            if(isNaN($("#bankcardNo").val())){
                showShadedowTips("银行卡号不正确",1);
                return false;
            }

            /* if(!luhm($("#bankcardNo").val())){
                showShadedowTips("银行卡号不正确",1);
                return false;
            } */

            /** 保存后回调函数 */
            var saveCusInfoCallBack = function(result){
                if(result.code == 0 ){
                    showShadedowTips("操作成功",1);
<<<<<<< HEAD
                    window.location.href='${basePath }personal/${loginUnauth }/toMyBankAcount';
=======
                    window.location.href='${basePathHttps }personal/${loginUnauth }/toMyBankAcount';
>>>>>>> refs/remotes/origin/test
                }else{
                    showShadedowTips(result.msg,3);
                }
            };

            /** 保存后路径  */
            var saveCusInfoUrl= '${basePathHttps }personal/${loginUnauth }/saveBank';
            /** 保存后参数  */
            var params = $("#customerBaseInfo").serialize();
            /** 保存方法 */
            CommonUtils.ajaxPostSubmit(saveCusInfoUrl,params,saveCusInfoCallBack);
        })
        
        /**
         * 银行卡号luhm校验
         *1、从卡号最后一位数字开始,偶数位乘以2,如果乘以2的结果是两位数，将结果减去9。
         *2、把所有数字相加,得到总和。
         *3、如果信用卡号码是合法的，总和可以被10整除。
         */
        function luhm(bankCardNo){
        	var arrays = bankCardNo.split("");
        	arrays.reverse();
     		var total = 0;
     		for (var i = 0; i < arrays.length; i++) {
    			if ((i + 1) % 2 == 0) {
     				var temp = parseInt(arrays[i]) << 1;
     				total += temp > 9 ? temp - 9 : temp;
    			} else {
    				total += parseInt(arrays[i]);
    			}
    		}
    		return total % 10 == 0;
        }
        

    })
</script>
</body>
</html>
