<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<head lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta name="format-detection" content="telephone=no, email=no">
    <meta name="HandheldFriendly" content="true">
    <meta name="MobileOptimized" content="320">
    <meta http-equiv="Cache-Control" content="no-siteapp">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-title" content="">
    <meta name="apple-mobile-web-app-status-bar-style" content="white">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>天使房东</title>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/stylesSeed.css${VERSION}004">
</head>
<body class="bg_ebf1fa">
    <form id="form">
    <div class="S_form_box">
        <ul>
            <li class="clearfix">
                <p>姓名</p>
                <input type="text" name="customerName" />
            </li>
            <li class="clearfix">
                <p>联系方式</p>
                <input type="text" name="customerMoblie" />
            </li>


            <li class="clearfix">
                <p>房源所在城市</p>
                <select name="cityCode" id="cityCode" onchange="setAreaList(this)">
                </select>
                <i class="S_item_xiala"></i>
            </li>
            <li class="clearfix">
                <p>行政区域</p>
                <select name="areaCode" id="areaCode">
                </select>
                <i class="S_item_xiala"></i>
            </li>

            <li class="clearfix">
                <p>房源平均评分</p>
                <input type="text" name="houseScore" placeholder="5分为满分哦"/>
            </li>
            <li class="clearfix">
                <p>房源数量</p>
                <input type="text" name="houseNum" />
            </li>
            <li class="clearfix">
                <p>是否已是自如民宿房东</p>
                <div class="S_isOrNot clearfix">
                    <span>是</span>
                    <i class="S_item_is on" id="J_isOrNot"></i>
                    <span>否</span>
                    <input type="hidden" name="isZlan" value="1">
                </div>
            </li>
            <li class="clearfix">
                <p>房源链接</p>
                <i class="S_item_add" id="J_addLi"></i>
            </li>
            <li class="clearfix" id="J_beizhu">
                <p>备注</p>
                <input type="text" name="remark" />
            </li>
        </ul>
    </div>

    <div class="S_accept">
        <i class="S_item_chose on" id="J_chose"></i>
        <a href="hetong">已阅读并同意协议</a>
    </div>
    <input type="button" value="提交申请" class="S_submit" id="submit"/>
</form>
</body>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/zepto.min.js"></script>
<script src="${staticResourceUrl }/js/jquery.min.js"></script>
<script src="${staticResourceUrl }/js/layer/layer.js"></script>
<script>

    var data=[
        {
            text:'北京',
            code:'110000',
            list:[
                {text:'东城区',code:'110101'},
                {text:'西城区',code:'110102'},
                {text:'朝阳区',code:'110105'},
                {text:'丰台区',code:'110106'},
                {text:'石景山区',code:'110107'},
                {text:'海淀区',code:'110108'},
                {text:'门头沟区',code:'110109'},
                {text:'房山区',code:'110111'},
                {text:'通州区',code:'110112'},
                {text:'顺义区',code:'110113'},
                {text:'昌平区',code:'110114'}
            ]
        },
        {
            text:'上海',
            code:'310000',
            list:[
                {text:'黄浦区',code:'310101'},
                {text:'徐汇区',code:'310104'},
                {text:'长宁区',code:'310105'},
                {text:'静安区',code:'310106'},
                {text:'普陀区',code:'310107'},
                {text:'闸北区',code:'310108'},
                {text:'虹口区',code:'310109'},
                {text:'杨浦区',code:'310110'},
                {text:'闵行区',code:'310112'},
                {text:'宝山区',code:'310113'},
                {text:'嘉定区',code:'310114'},
                {text:'浦东新区',code:'310115'},
                {text:'金山区',code:'310116'},
                {text:'松江区',code:'310117'},
                {text:'青浦区',code:'310118'},
                {text:'奉贤区',code:'310120'},
                {text:'崇明县',code:'310230'}
            ]
        },
        {
            text:'深圳',
            code:'440000',
            list:[
                {text:'罗湖区',code:'440303'},
                {text:'福田区',code:'440304'},
                {text:'南山区',code:'440305'},
                {text:'宝安区',code:'440306'},
                {text:'龙岗区',code:'440307'},
                {text:'盐田区',code:'440308'},
                {text:'光明新区',code:'440309'},
                {text:'坪山新区',code:'440310'},
                {text:'大鹏新区',code:'440311'},
                {text:'龙华新区',code:'440312'}
            ]
        },
        {
        	text:'广州',
            code:'440000',
            list:[
                {text:'荔湾区',code:'440103'},
                {text:'越秀区',code:'440104'},
                {text:'海珠区',code:'440105'},
                {text:'天河区',code:'440106'},
                {text:'白云区',code:'440111'},
                {text:'黄埔区',code:'440112'},
                {text:'番禺区',code:'440113'},
                {text:'花都区',code:'440114'},
                {text:'南沙区',code:'440115'},
                {text:'从化区',code:'440117'},
                {text:'增城区',code:'440118'}
            ]
        },
        {
        	text:'成都',
            code:'510000',
            list:[
                {text:'锦江区',code:'510104'},
                {text:'青羊区',code:'510105'},
                {text:'金牛区',code:'510106'},
                {text:'武侯区',code:'510107'},
                {text:'成华区',code:'510108'},
                {text:'龙泉驿区',code:'510112'},
                {text:'青白江区',code:'510113'},
                {text:'新都区',code:'510114'},
                {text:'温江区',code:'510115'},
                {text:'金堂县',code:'510121'},
                {text:'双流县',code:'510122'},
                {text:'郫县',code:'510124'},
                {text:'大邑县',code:'510129'},
                {text:'蒲江县',code:'510131'},
                {text:'新津县',code:'510132'},
                {text:'都江堰市',code:'510181'},
                {text:'彭州市',code:'510182'},
                {text:'邛崃市',code:'510183'},
                {text:'崇州市',code:'510184'}
            ]
        },
        {
        	text:'青岛',
            code:'370000',
            list:[
                {text:'市南区',code:'370202'},
                {text:'市北区',code:'370203'},
                {text:'黄岛区',code:'370211'},
                {text:'崂山区',code:'370212'},
                {text:'李沧区',code:'370213'},
                {text:'城阳区',code:'370214'},
                {text:'胶州市',code:'370281'},
                {text:'即墨市',code:'370282'},
                {text:'平度市',code:'370283'},
                {text:'莱西市',code:'370285'},
                {text:'西海岸新区',code:'370286'}
            ]
        },
        {
        	text:'苏州',
            code:'320000',
            list:[
                {text:'虎丘区',code:'320505'},
                {text:'吴中区',code:'320506'},
                {text:'相城区',code:'320507'},
                {text:'姑苏区',code:'320508'},
                {text:'吴江区',code:'320509'},
                {text:'常熟市',code:'320581'},
                {text:'张家港市',code:'320582'},
                {text:'昆山市',code:'320583'},
                {text:'太仓市',code:'320585'}
            ]
        },
        {
        	text:'厦门',
            code:'350000',
            list:[
                {text:'思明区',code:'350203'},
                {text:'海沧区',code:'350205'},
                {text:'湖里区',code:'350206'},
                {text:'集美区',code:'350211'},
                {text:'同安区',code:'350212'},
                {text:'翔安区',code:'350213'}
            ]
        },
        {
        	text:'西安',
            code:'610000',
            list:[
                {text:'新城区',code:'610102'},
                {text:'碑林区',code:'610103'},
                {text:'莲湖区',code:'610104'},
                {text:'灞桥区',code:'610111'},
                {text:'未央区',code:'610112'},
                {text:'雁塔区',code:'610113'},
                {text:'阎良区',code:'610114'},
                {text:'临潼区',code:'610115'},
                {text:'长安区',code:'610116'},
                {text:'蓝田县',code:'610122'},
                {text:'周至县',code:'610124'},
                {text:'户县',code:'610125'},
                {text:'高陵区',code:'610126'}
            ]
        },
        {
        	text:'丽江',
            code:'530000',
            list:[
                {text:'古城区',code:'530702'},
                {text:'玉龙纳西族自治县',code:'530721'},
                {text:'永胜县',code:'530722'},
                {text:'华坪县',code:'530723'},
                {text:'宁蒗彝族自治县',code:'530724'}
            ]
        }];
    var oData=data;
    var cityNameList='';
    var code='';
    var index=0;
    var cityList='';

    for(var i=0; i<oData.length; i++){
        cityNameList +='<option value="'+oData[i].code+'" data-index="'+i+'">'+oData[i].text+'</option>'
    }
    $('#cityCode').append(cityNameList);


    function setAreaList(obj){
        index = obj.selectedIndex;
        setList(index);
    }


    function setList(index){
        cityList='';
        var oDataList=oData[index].list;
        for(var i=0; i<oDataList.length; i++){
            cityList +='<option value="'+oDataList[i].code+'" data-index="'+i+'">'+oDataList[i].text+'</option>';
        }
        $('#areaCode').html('').append(cityList);
    }
    setList(0);

var oCustomerName = $('input[name=customerName]'); //姓名
var oCustomerMoblie = $('input[name=customerMoblie]'); //电话
var oCityCode = $('select[name=cityCode]'); //城市
var oAreaCode = $('select[name=areaCode]'); //区域
var oHouseScore = $('input[name=houseScore]'); //房源平均评分
var oHouseNum = $('input[name=houseNum]');     //数量
var oRemark = $('input[name=remark]');     //备注

function checkForm(){
	
    if(oCustomerName.val()==''){
        showTips('请输入姓名！');
        return false;
    }
    
    if(oCustomerMoblie.val()==''){
        showTips('请输入联系方式！');
        return false;
    }

    if(!checkMobile(oCustomerMoblie.val())){
        showTips('请输入正确的手机号！');
        return false;
    }

    if(oCityCode.val()==''){
        showTips('请选择城市！');
        return false;
    }

    if(oAreaCode.val()==''){
        showTips('请选择区域！');
        return false;
    }
    if(oHouseScore.val()==''){
       showTips('请填写房源平均评分！');
       return false;
    }
    var re1 = /^\d+(\.\d+)?$/;
    if (!re1.test(oHouseScore.val())){ 
        showTips('房源平均评分必须为数字！');
        return false;
    }
    if(oHouseScore.val()>5){
        showTips('评分满分为5分！');
        return false;
     }
    
    if(oHouseNum.val()==''){
        showTips('请填写房源数量！');
        return false;
    }
    
    var re = /^[1-9]+[0-9]*]*$/;
    if (!re.test(oHouseNum.val())){ 
        showTips('房源数量必须为数字！');
        return false;
    }
    
    if($('.linksInp').length==0){
    	showTips('请添加房源链接！');
        return false;
    }
    
    var flag = true;
    $('.linksInp').each(function(){
        if($.trim($(this).val())===''){
            flag =  false;
        }
    });
    if(!flag){
        showTips('还有未填写的房源链接！');
        return false;
    }

    return true;
}

//是否切换
$('#J_isOrNot').click(function(){
    if($(this).hasClass('on')){
        $(this).removeClass('on');
        $('input[name="isZlan"]').val(0);
    }
    else{
        $(this).addClass('on');
        $('input[name="isZlan"]').val(1);
    }
});

//房源链接
$('#J_addLi').click(function(){
    $('<li><input type="text" class="linksInp" name="houseUrlList" value="" placeholder=""/><a href="javascript:;" class="del" onclick="removeLi(this)"></a></li>').insertBefore('#J_beizhu');
});

function removeLi(obj){
    $(obj).parent().remove();
}

//是否同意
$('#J_chose').click(function(){
    if($(this).hasClass('on')){
        $(this).removeClass('on');
    }
    else{
        $(this).addClass('on');
    }
});

function showTips(str){
    layer.open({
    content: str,
    style: 'background-color:#09C1FF; color:#fff; border:none;',
    time: 1
});
}

/*手机正则验证*/
function checkMobile(tel){
    var telReg;
    var pattern = /^1[34578]\d{9}$/;
    if (pattern.test(tel)) {
        telReg = true;
    }else{
        telReg = false;
    }
    return telReg;
}



$("#submit").click(function(){
	if(!checkForm()){
		return;
	}
	
	var array = $("#form").serializeArray();
	var apply = toJsonString(array);
	$.ajax({
		url:SERVER_CONTEXT+"activityApply/saveSeedApply",
		data:{
			apply : apply
		},
		dataType:"json",
		type:"post",
		async: false,
		success:function(data) {
			if(data.code == 0){
				setTimeout(function(){
					window.location.href=SERVER_CONTEXT+"activityApply/toSuccess";
				},1500);
			}else{
				showTips(data.msg);
			}
		},
		error:function(result){
			showTips("未知错误");
		}
	});
})

function toJsonString(array) {
	var json = {};
	var links = [];
	$.each(array, function(i, n) {
		var key = n.name;
		var value = n.value;
		if(key == "houseUrlList" && $.trim(value).length != 0){
			links.push(value)
			json[key] = links;
		}else if ($.trim(value).length != 0) {
			json[key] = value;
		}
	});
	return JSON.stringify(json);
}
</script>
</html>