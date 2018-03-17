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
                <input id="realName"  name="realName" readonly="readonly" value="${bankcardHolder}" class="ipt"  />
            </li>

            <li class="clearfix bor_b no_icon">
                开户银行
                <input id="bankName"  name="bankName" readonly="readonly" value="${bankName}" class="ipt"  />
                <input type="hidden" id="cardName" name="cardName"  value="${cardName}"  readonly="readonly">
            </li>


            <li class="clearfix bor_b no_icon">
                开户城市
                <input id="cardCity"  name="cardCity" readonly="readonly" value="${province}" class="ipt"  />
            </li>

            <li class="clearfix bor_b no_icon">
                银行卡号
                <input id="bankcardNo"  name="bankcardNo" readonly="readonly" value="${bankcardNo}" placeholder="" class="ipt"  />
            </li>
        </ul>

    </div><!--/main-->
    <div class="boxP075 mt85 mb85">
        <input type="button" value="解绑" class="org_btn " id="submitBtn">
    </div>
</form>
<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/layer/layer.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>


<script type="text/javascript">
    $(function(){
        $("#submitBtn").click(function(){
            if($(this).hasClass("disabled_btn")){
                return false;
            }

            /** 保存后回调函数 */
            var saveCusInfoCallBack = function(result){
                if(result.code == 0 ){
                    showShadedowTips("操作成功",1);
<<<<<<< HEAD
                    window.location.href='${basePath }personal/${loginUnauth }/toMyBankAcount';
                }else{
                    showShadedowTips("操作失败",1);
                }
            };

            /** 保存后路径  */
            var saveCusInfoUrl= '${basePath }personal/${loginUnauth }/delBank';
=======
                    window.location.href='${basePathHttps }personal/${loginUnauth }/toMyBankAcount';
                }else{
                    showShadedowTips("操作失败",1);
                }
            };

            /** 保存后路径  */
            var saveCusInfoUrl= '${basePathHttps }personal/${loginUnauth }/delBank';
>>>>>>> refs/remotes/origin/test
            /** 保存后参数  */
            var params = $("#customerBaseInfo").serialize();
            /** 保存方法 */
            CommonUtils.ajaxPostSubmit(saveCusInfoUrl,params,saveCusInfoCallBack);
        })
    })
</script>
</body>
</html>
