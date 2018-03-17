
var auth = {};
auth.msg = {
	picNum:0,
	picBaseAddrMona:$("#picBaseAddrMona").val(),
	list_small_pic:$("#list_small_pic").val(),
	picSuffix:$("#picSuffix").val(),
	authType:$("#authType").val(),
	sourceType:$("#sourceType").val()
};
function changePic(obj,id){
    var f = $(obj).val();
    if (!/\.(jpg|jpeg|png|JPG|PNG)$/.test(f)) {
        showShadedowTips('图片格式不正确',3);
    } else {
        $("#loading_"+id).show();
        var options = {
            dataType: 'json',
            success: function(txt) {
                if(txt.code == 0){
                    //上传文件成功以后，如果以前数据不为空，则删除旧图片数据
                    var uuid = $("#upload_uuid_"+id).val();
                    //uuid不为空的话 删除旧图片
                    if(uuid != ""){
                        $.post(SERVER_CONTEXT+"personal/43e881/delCustomerPic",{"picServerUuid":uuid},function(data){
                        },'json');
                    }

                    var fileInfo = txt.data.fileInfo;
                    //var URL = picBaseAddrMona + fileInfo.url_base + fileInfo.url_ext + list_small_pic + picSuffix;
                    var URL = auth.msg.picBaseAddrMona + fileInfo.url_base + fileInfo.url_ext + auth.msg.list_small_pic + auth.msg.picSuffix;
                    //上传到服务器参数数据
                    var urldata = fileInfo.url_base+":"+fileInfo.uuid+":"+fileInfo.url_ext+":"+fileInfo.original_filename;
                    $("#img_"+id).css({'background-image':"url("+URL+")"});
                    $("#upload_data_"+id).val(urldata);
                    $("#upload_uuid_"+id).val(fileInfo.uuid);
                    $("#loading_"+id).hide();
                    auth.msg.picNum++;

                    $(".checkFormChild").linghtBtn();
                    if(auth.msg.picNum>=3 && $('#saveBtn').hasClass('disabled_btn2')){
                        $('#saveBtn').removeAttr('disabled').removeClass('disabled_btn disabled_btn2');
                    }
                } else {
                    showShadedowTips('图片上传失败请重新上传',3);
                }
            }
        };
        $("#form"+id).ajaxSubmit(options);
        return false;
    }
}


//调用android图片上传方法
function upoladPicAndroid(type){
    //来自android
    if(auth.msg.sourceType == "1"){
        window.WebViewFunc.uploadUserPic(type);
        return;
    }
}


//android图片回调
function handleAndroidPic(response){
    if(response == null || response == ""){
        showShadedowTips('图片上传失败请重新上传',3);
        return;
    }
    var resJson = $.parseJSON(response);

    if(resJson.status == 0){
        var type = resJson.data.picType;
        var picInfo = resJson.data;
        var URL = auth.msg.picBaseAddrMona + picInfo.urlBase + picInfo.urlExt + auth.msg.list_small_pic +  auth.msg.picSuffix;
        var urldata = picInfo.urlBase+":"+picInfo.uuid+":"+picInfo.urlExt+":"+picInfo.originalFilename;
        $("#img_"+type).css({'background-image':"url("+URL+")"});
        $("#upload_data_"+type).val(urldata);
        $("#upload_uuid_"+type).val(picInfo.uuid);
        //$("#loading_"+type).hide();
        auth.msg.picNum++;
        //$("#loading_"+type).show();
        if(auth.msg.picNum>=3 && $('#saveBtn').hasClass('disabled_btn2')){
            $('#saveBtn').removeAttr('disabled').removeClass('disabled_btn disabled_btn2');
        }
    }else{
        showShadedowTips('图片上传失败请重新上传',3);
    }
}


/* 提交操作 */
$('#saveBtn').click(function(){
    if($(this).hasClass('disabled_btn')){
        return;
    }
    
    var realName= $.trim($("input[name='realName']").val());

    var idType = $("select[name='idType']").val();
    var idValue = $("input[name='idNo']").val();
    
    var re = /^[0-9]*$/;
    if(re.test(realName)){
    	showShadedowTips("姓名不能全为数字",3);
        return;
    }    

    if(idType == 1){
        //如果身份证的话校验格式
        if(!isCardNo(idValue)){
            showShadedowTips("身份证号码格式不正确",3);
            return;
        }
    }else if(idType == 2){
        if(!isPassport(idValue)){
            showShadedowTips("护照号码格式不正确",3);
            return;
        }
    }else if(idType == 6){
        if(!isTaiwan(idValue)){
            showShadedowTips("台湾居民来往通行证格式不正确",3);
            return;
        }
    }else if(idType == 13){
        if(!isHKMacao(idValue)){
            showShadedowTips("港澳居民来往通行证格式不正确",3);
            return;
        }
    }


    var pic0 = $("input[name='upload_data_0']").val();
    var pic1 = $("input[name='upload_data_1']").val();
    var pic2 = $("input[name='upload_data_2']").val();

    if(pic0 == ""){
        showShadedowTips("请上传身份证正面照",3);
        return;
    }
    if(pic1 == ""){
        showShadedowTips("请上传身份证反面照",3);
        return;
    }
    if(pic2 == ""){
        showShadedowTips("请上传身份证手持照",3);
        return;
    }

    $.ajax({
        url : SERVER_CONTEXT+"personal/43e881/saveAuthDetail",
        data : $('#authForm').serialize(),
        dataType : "json",
        type : "post",
        async : false,
        success : function(result) {
            if (result.code === 0) {
                showShadedowTips("操作成功",3);
                if(auth.msg.authType == 1){
                	window.location.href = SERVER_CONTEXT+"personal/43e881/initPersonalCenter";
                }else if(auth.msg.authType == 2){
                	window.location.href = SERVER_CONTEXT+"auth/43e881/init?checkFlag=1";
                }else if(auth.msg.authType == 3){
                    window.location.href = SERVER_CONTEXT+ "personal/43e881/addBank";
                }else{
                	window.location.href = SERVER_CONTEXT+"personal/43e881/initPersonalCenter";
                }
            } else {
                showShadedowTips(result.msg,3);
            }
        },
        error : function(result) {
            showShadedowTips("未知错误",3);
        }
    });

});