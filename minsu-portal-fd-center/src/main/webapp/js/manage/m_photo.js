var roomType ;//房间类型
var roomCode;//房间号码
var roomfid;
var housefid;//房源id
var isdefault;
var isSave = true; //图片是否保存
var uploadImageUrl = "";//上传图片url
var domain = "";
var roomName ='';


$(function(){
	housefid = $("#houseFid").val()
    // 加载房源相册
    addHousePhotos();

    //设置弹层高度
    $(window).resize(function() {
      countModalHeight();
    });
    //关闭弹层
    $('.modal-content .close').click(function() {
      closeModalBox();
    });
    $("#modal").on('click', function() {
      //$(this).hide();
      closeModalBox();
    })
    $('.modal-content').on('click', function(event) {
      event.stopPropagation();
    })
    $(".btn-group").delegate(".btn-right","click",function(){
      if($(this).hasClass("disabled")){
        return;
      }else{
        $(this).addClass("disabled");
      }
    })
    $('#sentHouseBtn').click(function(){
        //发布房源
        var rentWay = $("#rentWay").val();
        if(rentWay == 0){
        	$.post("/houseIssue/release",{houseFid:housefid},function(data){
        		if(data.code == 0){
        			window.location.href="/houseIssue/success/"+housefid;
        		}else{
        			showConfirm(data.msg,"确定");
        		}
        	},'json');
        }
        //如果是合租获取合租房间列表，选择合租的房间发布
        if(rentWay == 1){
        	$.getJSON("/house/getFRooms",{houseFid:housefid,type:"fabu"},function(data){
        		if(data.code == 0){
        			var list = data.data.roomList;
        			//var list = [{'roomName':'十里堡温馨小屋','roomFid':'123','statusName':'已发布'},{'roomName':'十里堡温馨小屋','roomFid':'12124','statusName':'已发布'}]
        			var str = '';
        			for(var i = 0 ; i < list.length ; i ++){
        				str+=' <li>'
               +  '<span>'+list[i].roomName+'（'+list[i].statusName+'）</span>'
               +  '<input type="hidden" class="house-ipt" value="'+list[i].roomFid+'">'
               +'</li>'
             }
             $("#sentHouseList").html(str);
             showModalBox("sentHouse");
           }else{
             showConfirm(data.msg,"确定");
           }
         });
        }
      });
$("#sentHouseList").delegate('li','click',function(){
  if($(this).hasClass("active")){
    $(this).removeClass("active");
  }else{
    $(this).addClass("active");
  }
})

    //合租确定发布房间
    $("#sureSentHouseBtn").click(function(){
        $("#sureSentHouseBtn").addClass("disabled");
        var roomData = [];
        for (var i = 0; i < $("#sentHouseList").find("li.active").length; i++) {
            roomData.push($("#sentHouseList li.active:eq(" + i + ")").find(".house-ipt").val())
        }
        var houseFid = $("#houseFid").val();
        //如果没有房间，则选择房间
        if(roomData.length == 0){
          $("#sureSentHouseBtn").removeClass("disabled");
          return false;
        }

        var params = {"houseFid":houseFid,"roomList":roomData}
        //合租发布房源
        $.post("/rooms/release",{param:JSON.stringify(params)},function(data){
        	var fids = "";
        	for(var i = 0;i<roomData.length;i++){
        		if(i == roomData.length -1){
        			fids += roomData[i];
        		}else{
        			fids += roomData[i]+"-";
        		}
        	}
        	if(data.code == 0){
        		window.location.href="/houseIssue/success/"+housefid+"?rfids="+fids;
        	}else{
                $("#sureSentHouseBtn").removeClass("disabled");
                showConfirm(data.msg, "确定");
            }
        },'json');
      })
    //合租确定预览房间
    $("#sureLookHouseBtn").click(function(){
      var roomFid = $("#lookHouseList li.active").find(".house-ipt").val();
      if(!roomFid){
        $("#sureLookHouseBtn").removeClass("disabled");
        return false;
      }
      closeModalBox();
      var data = {"houseFid":housefid,"roomFid":roomFid}
      window.open("/preview/1/"+roomFid);
    })
    
    //预览房源
    $('#lookHouseBtn').click(function(){
    	var rentWay = $("#rentWay").val();
    	if(rentWay == 0){
    		window.open("/preview/0/"+housefid);
    	}
    	if(rentWay == 1){
    		$.getJSON("/house/getFRooms",{houseFid:housefid,type:"preview"},function(data){
    			if(data.code == 0){
    				//var list = [{'roomName':'十里堡温馨小屋','roomFid':'','statusName':'已发布'},{'roomName':'十里堡温馨小屋','roomFid':'','statusName':'已发布'}]
    				var list = data.data.roomList;
    				var str = '';
    				for(var i = 0 ; i < list.length ; i ++){
    					str+=' <li>'
              +  '<span>'+list[i].roomName+'（'+list[i].statusName+'）</span>'
              +  '<input type="hidden" class="house-ipt" value="'+list[i].roomFid+'">'
              +'</li>'
            }
            $("#lookHouseList").html(str);
            showModalBox("lookHouse");
          }else{
            showConfirm(data.msg,"确定");
          }
        });
    	}
    	
    });

    $("#lookHouseList").delegate('li','click',function(){
      if($(this).hasClass("active")){
        $(this).removeClass("active")
      }else{
        $(this).addClass("active").siblings().removeClass("active");
      }
    })


    $("#photosPart").delegate('.img_box','click',function(){
        //    	$("#file_upload").val("");
        uploadImgArr = [];
        roomType = $(this).parents("li").attr("id").split("_")[1];
        if(roomType != 0){
         $(".p_txt").hide();
       }else{
         $(".p_txt").show();
       }
       switch(roomType){
            case "0"://卧室
              roomName = $(this).parents("li").attr("id").split("_")[2]+"卧室";
            break;
            case "1"://客厅
              roomName = "客厅";
            break;
            case "2"://厨房
              roomName = "厨房";
            break;
            case "3"://卫生间
              roomName = "卫生间";
            break;
            case "4"://室外
              roomName = "室外";
            break;
        }
       
       roomfid = $(this).parents("li").attr("data-roomfid");
       if($(this).parents("li").attr("id").split("_").length >= 3){
        roomCode = $(this).parents("li").attr("id").split("_")[2];
      }else{
        roomCode = '';
      }
           // TODO:加载房间照片
           if (window.File && window.FileList && window.FileReader && window.Blob) {
            if($(this).hasClass("have_img")){
             addPhotoList();
           }
         }

       })
    // photoListPart

    $("#sava_pic").click(function(){
      if($(this).hasClass("disabled")){
        return;
      }
      subimage();
    })

    // 拖拽
    $('body').on('mouseover','#imageBox',function(e){


      $("#imageBox" ).sortable({
        cursor: "move",
            items :".pho_bg",                        //只是li可以拖动
            tolerance : 'pointer',            //拖动元素越过其它元素多少时便进行重新排序
            opacity: 0.6,                       //拖动时，透明度为0.6
            revert: false,                       //释放时，增加动画
            scroll: true,                      //拖动页面边缘 页面滚动
            placeholder:"drag_end",             //设置当排序动作发生时，空白占位符的CSS样式。
            update : function(event, ui){       //更新排序之后
              changeFlag = true;
              var paixu = $(this).sortable("toArray");
            }
          });
      $("#imageBox").disableSelection();
    });
    /*********************************设置其他相册照片为封面**********************************/
    $("#setFaceBtn").click(function(){
    	$.getJSON("/houseIssue/getOtherPic/"+housefid,function(data){
    		if(data.code == 0){
    			var dataUrlList = data.data.list;
          if(dataUrlList.length == 0){
            showConfirm("请在其他区域上传照片后进行设置","确定");
            return;
          }
          var _str = '';
          for(var i = 0 ; i < dataUrlList.length ; i ++){
    				if(dataUrlList[i].isdefault == 0){//不是封面
    					_str+='<li class="control-li" data-isdefault="'+dataUrlList[i].isdefault+'"  data-picfid="'+dataUrlList[i].picfid+'" >';
    				}
    				if(dataUrlList[i].isdefault == 1){//是封面
    					_str+='<li class="control-li face_li" data-isdefault="'+dataUrlList[i].isdefault+'" data-picfid="'+dataUrlList[i].picfid+'" >';
    				}
    				_str+='<div class="control-img"><img src="'+dataUrlList[i].url+'"><div class="face_img"></div></div></li>'
    			}
    			if(dataUrlList.length > 8){
    				$("#setFace").css({'width':'620px'});
    				$("#setFace").find('.control-list').css({'overflow-y':'auto','height':'280px'});
    			}else{
    				$("#setFace").css({'width':'600px'});
    				$("#setFace").find('.control-list').css({'overflow':'hidden','height':'auto'});
    			}
    			$("#faceImageList").html(_str);
    			showModalBox('setFace');
    		}else{
    			
    		}
    	});

})

//设置封面照片
$("#sureSetFaceBtn").click(function(){
  if($("#faceImageList").find(".face_li").length !=0){
        // var _roomfid = $("#faceImageList").find(".face_li").attr("data-roomfid");
        var _picfid = $("#faceImageList").find(".face_li").attr("data-picfid");

        $.ajax({
          url:'/houseIssue/setDefaultPic',
          type:"post",
          dataType:"json",
          data:{"picFid":_picfid,"roomFid":roomfid,"houseFid":housefid},
          success:function(data){
            if(data.code == 0){
              if($("#imageBox").find('li.face_li').length != 0){
                $("#imageBox").find('li.face_li').attr("data-isdefault",0);
                $("#imageBox").find('li.face_li').removeClass('face_li');
              }
            }else{
              $("#sureSetFaceBtn").removeClass("disabled");
              showConfirm(data.errorMsg,"确定");
            }
          }
        })
        closeModalBox();
      }else{
        alert();
      }

    })

$("#faceImageList").delegate('li','click',function(){
  $(this).addClass('face_li').siblings().removeClass('face_li');
  $(this).attr("data-isdefault",1)
  $(this).siblings().attr("data-isdefault",0);

})
/********************************* 设置其他相册照片为封面 end**********************************/
/********************************* 设置其他相册照片为封面 end**********************************/

    // 离开当前页面

    window.onbeforeunload = function() {
      if(!isSave){
        var html =  "------------------------------------\n";
        html += " 提示：未保存的信息将会丢失！确定要离开此页吗？     \n";
        html += "------------------------------------\n";
        event.returnValue = html;
      }
    }; 
    /********************************* 设置其他相册照片为封面 end**********************************/


    $("#imageBox").delegate('.pho_bg','click',function(){
      addOutsideImage();
      var index = $(this).index();
      $('.outslide').slide({
        mainCell:".bd",
        titCell:".hd li",
        switchLoad:"_src",
        prevCell:".prev",
        nextCell:".next",
        trigger:"click",
        defaultIndex:index,
        pnLoop:false,
        startFun:function(i,c){
          var w = c*150;

          $('.outslide .hd').css({"width":w+"px"})
          
            if(i > 5){
              var _w = 0 - (c-5)*150;
              $('.outslide .hd').stop().animate({"left":+_w+"px"},200);
              
            }else if(i > 1 && i < (c-2)){
                var _w = 0 - (i-2)*150;
                $('.outslide .hd').stop().animate({"left":+_w+"px"},200);
            }else{
                $('.outslide .hd').stop().animate({"left":"0px"},200);
            }
            $('.outslide .hd:eq('+index+')').addClass("on").siblings().removeClass("on");
        },
        endFun:function(i,c){
          var _w = 0 - (i-2)*150;
          if(i > 1 && i < (c-2)){
            $('.outslide .hd').stop().animate({"left":+_w+"px"},200)
          }
        }
      });
      showModalBox("outSlideBox");
    })
    $("#imageBox").delegate('.set_img','click',function(e){
      e.stopPropagation();
    })
    $(".outslide .hd").delegate('.slide_item','click',function(){
      var index = $(this).index();
      var len = $(".outslide .hd").find("li").length;
      $(this).addClass("on").siblings().removeClass("on");
      var l = 0 - (index-2)*150;
      if(index > 2 && index < (len-2)){
        $('.outslide .hd').stop().animate({"left":+l+"px"},200)
      }
    })

  });
/*打开弹层*/
function showModalBox(id) {
  $('body').addClass('hidden');
  $('#modal').show();
  $('#' + id).show().siblings().hide();
  $('#'+id).find(".btn-right").removeClass("disabled");
  countModalHeight(id);
}

/*关闭弹层*/
function closeModalBox() {
  $('body').removeClass('hidden');
  $('#modal').hide();
}

/*计算弹层的高度*/
function countModalHeight(id) {
  var t = ($(window).height() - $('#'+id).height()) / 2 ;
  t = t < 0 ? 0 : t;
  $('.modal-content').css({
    'margin-top': t + 'px'
  });
}

// 页面初始化时 加载房源相册列表
function addHousePhotos(){
  $(".part_r_tt").text("真实照片");
	housefid = $("#houseFid").val();
	//加载房源图片列表
	var photo_str = '';
	$.getJSON("/houseIssue/housePic/"+housefid,function(data){
		var dataList = [];
		if(data.code == 0){
			dataList = data.data.picList;
		}else{
			dataList = [{"picType":1,"roomfid":"","url":""},{"roomType":2,"roomfid":"","url":""},{"picType":3,"roomfid":"","url":""},{"picType":4,"roomfid":"","url":""}];
		}
		var roomNo  = 0;
		for(var ii = 0 ; ii < dataList.length ; ii ++){
			var img_str = '';
			switch (dataList[ii].picType){ 
			case 0 : //卧室
      roomNo ++ ;
      roomNo = roomNo<10?'0'+roomNo:roomNo;
      photo_str+= '<li id="li_0_'+roomNo+'" data-roomfid="'+dataList[ii].roomfid+'">';
      if(dataList[ii].url == ''){
       img_str += '<a class="img_box set_img">'
       +    '<div class="txt">'
       +      '<dt>添加卧室'+roomNo+'照片</dt>'
       +      '<dd>按住Ctrl可多选</dd>'
       +   '</div>'
       +    '<input type="file" name="file_upload" class="file_upload" id="file_upload_'+roomNo+'" multiple/>'
       + ' </a>'
     }else{
       img_str += '<a class="img_box have_img">'
       +        '<img src="'+dataList[ii].url+'">'
       +        '<div class="name_bg">卧室'+roomNo+'</div>'
       +   '</a>'
     }
     photo_str+=img_str+'</li>';
     break; 
			case 1 : //客厅
      photo_str+= '<li id="li_1" data-roomfid="'+dataList[ii].roomfid+'">';
      if(dataList[ii].url == ''){
       img_str += '<a class="img_box set_img">'
       +    '<div class="txt">'
       +      '<dt>添加客厅照片</dt>'
       +      '<dd>按住Ctrl可多选</dd>'
       +   '</div>'
       +    '<input type="file" name="file_upload" class="file_upload" id="file_upload_sittingRoom" multiple/>'
       + ' </a>'
     }else{
       img_str += '<a class="img_box have_img">'
       +        '<img src="'+dataList[ii].url+'">'
       +        '<div class="name_bg">客厅</div>'
       +   '</a>'
     }
     photo_str+=img_str+'</li>';
     break; 
			case 2 : //厨房
      photo_str+= '<li id="li_2" data-roomfid="'+dataList[ii].roomfid+'">';
      if(dataList[ii].url == ''){
       img_str += '<a class="img_box set_img">'
       +    '<div class="txt">'
       +      '<dt>添加厨房照片</dt>'
       +      '<dd>按住Ctrl可多选</dd>'
       +   '</div>'
       +    '<input type="file" name="file_upload" class="file_upload" id="file_upload_kitchen" multiple/>'
       + ' </a>'
     }else{
       img_str += '<a class="img_box have_img">'
       +        '<img src="'+dataList[ii].url+'">'
       +        '<div class="name_bg">厨房</div>'
       +   '</a>'
     }
     photo_str+=img_str+'</li>';
     break; 
			case 3 : //卫生间

      photo_str+= '<li id="li_3" data-roomfid="'+dataList[ii].roomfid+'">';
      if(dataList[ii].url == ''){
       img_str += '<a class="img_box set_img">'
       +    '<div class="txt">'
       +      '<dt>添加卫生间照片</dt>'
       +      '<dd>按住Ctrl可多选</dd>'
       +   '</div>'
       +    '<input type="file" name="file_upload" class="file_upload" id="file_upload_toilet" multiple/>'
       + ' </a>'
     }else{
       img_str += '<a class="img_box have_img">'
       +        '<img src="'+dataList[ii].url+'">'
       +        '<div class="name_bg">卫生间</div>'
       +   '</a>'
     }
     photo_str+=img_str+'</li>';
     break; 
			case 4 : //室外

      photo_str+= '<li id="li_4" data-roomfid="'+dataList[ii].roomfid+'">';
      if(dataList[ii].url == ''){
       img_str += '<a class="img_box set_img">'
       +    '<div class="txt">'
       +      '<dt>添加室外照片</dt>'
       +      '<dd>按住Ctrl可多选</dd>'
       +   '</div>'
       +    '<input type="file" name="file_upload" class="file_upload" id="file_upload_outside" multiple/>'
       + ' </a>'
     }else{
       img_str += '<a class="img_box have_img">'
       +        '<img src="'+dataList[ii].url+'">'
       +        '<div class="name_bg">室外</div>'
       +   '</a>'
     }
     photo_str+=img_str+'</li>';
     break; 
     default : break; 
   } 
 }
 $("#photos").html(photo_str);
 if (window.File && window.FileList && window.FileReader && window.Blob){}else{
  var fileLen = $("#photosPart").find(".img_box .file_upload").length;

  for(var i = 0 ; i < fileLen; i ++){
   $("#photosPart").find(".img_box .file_upload:eq(0)").remove();
 }

}
});
}

// 根据传入参数加载房间列表
function addPhotoList(){
  var picBigSize = $("#picBigSize").val();
  var picBaseAddrMona = $("#picBaseAddrMona").val();

  $(".part_r_tt").text($(".part_r_tt").text()+'-'+roomName);
  $("html,body").stop().animate({"scrollTop":0},10);
  housefid = $("#houseFid").val();
  var len = $("#imageBox").find(".pho_bg").length;
  for(var i = 0 ; i < len ;i ++){
    $("#imageBox").find(".pho_bg:eq(0)").remove();
  }
  $.getJSON("/houseIssue/typepic",{houseFid:housefid,roomFid:roomfid,picType:roomType},function(data){
    if(data.code == 0){

     var photoList = data.data.picList;
     var photoList_str = '';
     for(var i = 0 ; i < photoList.length ; i ++){
      if(photoList[i].isdefault == 0){
       photoList_str += '<li class="pho_bg" id="Success_'+roomType+'_'+i+'" data-suffix="'+photoList[i].suffix+'" data-picfid="'+photoList[i].picfid+'" data-isdefault="'+photoList[i].isdefault+'" data-picserveruuid="">'
     }
				if(photoList[i].isdefault == 1){//封面
					photoList_str += '<li class="pho_bg face_li" id="Success_'+roomType+'_'+i+'" data-suffix="'+photoList[i].suffix+'"  data-picfid="'+photoList[i].picfid+'"  data-isdefault="'+photoList[i].isdefault+'" data-picserveruuid="">'
				}
         var big_pic = picBaseAddrMona+photoList[i].picbaseurl+photoList[i].suffix+picBigSize+'.jpg';
				photoList_str += 
       ' <a class="img_box upload_img">'
       +   '<img src="'+photoList[i].url+'" _src="'+photoList[i].picbaseurl+'" data-bigpic="'+big_pic+'">'
       +   '<ul class="set_img clearfix">'
       var rentWay = $("#rentWay").val();
       if(rentWay == 0){
        if(roomType ==2 || roomType ==3){
         photoList_str += '<li class="face"></li>';
       }else{
        photoList_str += '<li class="face"  onclick="setImageFace(\'Success_'+roomType+'_'+i+'\')">设为封面</li>';
      }
    }else if(rentWay == 1){
      if(roomType != 0){
         photoList_str += '<li class="face"></li>';
       }else{
        photoList_str += '<li class="face"  onclick="setImageFace(\'Success_'+roomType+'_'+i+'\')">设为封面</li>';
      }
    }
      photoList_str +=   ' <li class="rotate" onclick="rotateImage(\'Degress_'+roomType+'_'+i+'\')"></li>'
      +   ' <li class="del" onclick="removeImage(\'Success_'+roomType+'_'+i+'\')"></li>'
      + ' </ul>'
      + ' <input id="Degress_'+roomType+'_'+i+'" value="0" class="degreesIpt" type="hidden">'
      + ' <div class="face_img"></div>'
      +' </a>'
      + ' </li>'
    }

    $("#photosPart").hide();
    $("#photoListPart").show();
    $("#thumbnails").before(photoList_str);
    d = $("#imageBox").find(".pho_bg").length;
  }else{
   showConfirm("获取类型图片异常","确定");
 }
});
}






var flag_up_down = 0;
var moveSpeed = 10;
var queueSizeLimit = 1000;
var imageWidth = 180;
var imageHeight = 180;
var queueNum =0;
var filesErrored = 0;
var imgErr = false;

if (window.File && window.FileList && window.FileReader && window.Blob) {

  $(document).ready(function(){
    $("body").delegate('.file_upload','change',function(e){
      if($(this).parents(".part_r_box").attr("id") == 'photosPart'){
        $("#photosPart").hide();
        $("#photoListPart").show();
        addPhotoList();

      }
      getFiles(e);
    })

  })
} else {
	 // IE

  $(function(){

        // 删除file
        var _successid ='';
        var _degreesid ='';
        var _progressId = '';
        var idArr = [];
        var idNum = 0;
        var n = 0;
        $('#thumbnails').html(' <a class="img_box"><div class="txt"><dt>添加更多照片</dt><dd>按住Ctrl可多选</dd></div><div class="h_add_pic" id="file_upload" name="file_upload"></div> </a>');
        uploadifyImg(); 
        $('.add_pic_btn').removeClass('uploadify-button');
        $("#photosPart").delegate('.img_box','click',function(){
          addPhotoList();
        })
      });
 
 
}


//定义存放图片对象的数组
var uploadImgArr = [];
//防止图片上传完成后，再点击上传按钮的时候重复上传图片
var isUpload = false;
var xhrstatus = true;
//定义获取图片信息的函数
function getFiles(e) {
  isUpload = false;
  e = e || window.event;
    //获取file input中的图片信息列表
    var files = e.target.files;
    var lenLi = $("#imageBox .pho_bg").length;
    var len ;
    if(lenLi == 8){
     showConfirm('每个区域最多可上传8张照片','确定');
     return;
   }else if((files.length+lenLi) > 8){
        // showConfirm('每个区域最多可上传8张照片','确定');
        len = 8-lenLi;
        $(".photoMessage").show();
        setTimeout(function(){
      	  $(".photoMessage").hide();
        },3000);
      }else{
        len = files.length;
      }
        //验证是否是图片文件的正则
        var reg = /image\/.*/i;
        for (var i = 0; i < len; i++) {
          var f = files[i]
          if (!reg.test(f.type)) {
            showConfirm('只支持JPG、PNG格式文件，请重新选择','确定');
            return;
          }
          if(files[i].size/1024 < 20480){
            uploadImgArr.push(f);
            var reader = new FileReader();
            //类似于原生JS实现tab一样（闭包的方法），
            reader.onload = (function(file) {
                //获取图片相关的信息
                return function(e) {
                  var img = new Image();
                  img.addEventListener("load", imgLoaded, false);
                  img.src = e.target.result;
                  function imgLoaded() {
                    var innerHTML = '<li id="" class="pho_bg new"  data-suffix="" data-picserveruuid="" data-picfid="" data-isdefault="0"><a class="img_box upload_img"><div class="progress">0%</div></a></li>';
                    $('#thumbnails').before(innerHTML);
                    if(!xhrstatus){
                      showConfirm("上传失败","确定");
                      isUpload = true;
                    }
                  }
                }
              })(f);
                //读取文件内容
                reader.readAsDataURL(f);
              }else{
                showConfirm("照片大小不能超过2M，请重新选择","确定");
              }
            }
            uploadFun();
          }

          var j = 0;
          var d = 0;
//开始上传照片
function uploadFun() {
  changeFlag = true;
  var width = 180;
  var height = 180;
  var successid ='';
  var degreesid ='';
  var progressId='';
  var num = $('#imageBox .new').length;

  j = num;
  xhrUploadfun();
}
function xhrUploadfun() {
  var picSmallSize = $("#picSmallSize").val();
  var picBigSize = $("#picBigSize").val();
  var picBaseAddrMona = $("#picBaseAddrMona").val();
  if (uploadImgArr.length > 0 && !isUpload) {
    var singleImg = uploadImgArr[j];
    if(singleImg == ""){
     if (j < uploadImgArr.length - 1) {
      j++;
      ingleImg = uploadImgArr[j];
    }
  }
  var xhr = new XMLHttpRequest();
  var divId = j+d+suid;
  if (xhr.upload) {
            //进度条
            xhr.upload.addEventListener("progress",
              function(e) {
                if (e.lengthComputable) {
                  progressId = 'Progress'+roomType+'_'+divId;
                  $('.progress').attr("id",progressId);
                  $('#'+progressId).html(Math.round(e.loaded * 100 / e.total) + "%").css({'height':Math.round(e.loaded * 100 / e.total) + "%"}); 
                  $("#sava_pic").addClass("disabled")
                } else {
                  var innerHTMLSize = "无法计算文件大小";
                }
              },false);
            //文件上传成功或是失败
            xhr.onreadystatechange = function(e) {
              if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                  var result = eval("(" + xhr.responseText + ")");
                  if(result.code == 0){
                    var imgDesc = result.data.file;
                    successid = 'Success_'+roomType+'_'+divId;
                    degreesid = 'Degrees_'+roomType+'_'+divId;

                    var small_pic = picBaseAddrMona+imgDesc.urlBase+imgDesc.urlExt+picSmallSize+'.jpg';
                    var big_pic = picBaseAddrMona+imgDesc.urlBase+imgDesc.urlExt+picBigSize+'.jpg';

                    $('.progress').parents(".pho_bg").attr('id',successid);
                    $('#'+successid).attr("data-picserveruuid",imgDesc.uuid);
                    $('#'+successid).attr("data-picfid",imgDesc.picfid);

                    $('#'+successid).attr("data-suffix",imgDesc.urlExt);
                    $('#'+successid).children(".process").remove();
                    $("#sava_pic").removeClass("disabled");
                    var img_str = '';
                    img_str = '<img src="'+small_pic+'" _src="'+imgDesc.urlBase+'" data-bigpic="'+big_pic+'"><ul class="set_img clearfix">'
                   var rentWay = $("#rentWay").val();
                  if(rentWay == 0){//整
                    if(roomType ==2 || roomType ==3){
                      img_str+='<li class="face"></li>'
                    }else{
                      img_str+='<li class="face" onclick="setImageFace(\''+successid+'\')">设为封面</li>'
                    }
                  }else if(rentWay == 1){//分
                    if(roomType != 0){
                      img_str+='<li class="face"></li>'
                    }else{
                      img_str+='<li class="face" onclick="setImageFace(\''+successid+'\')">设为封面</li>'
                    }
                  }

                   img_str+='<li class="rotate" onclick="rotateImage(\''+degreesid+'\')"></li><li class="del" onclick="removeImage(\''+successid+'\')"></li></ul><input type="hidden" id="'+degreesid+'" class="degreesIpt" value="0"><div class="face_img"></div>'
                   $('#'+successid).children('.upload_img').html(img_str);
                   isSave = false;
                   isUpload = true;
                   xhrstatus = true;
                 }else{
                  showConfirm("上传失败","确定");
                }
              } else {
                        // $('#'+successid).remove();
                        xhrstatus = false;
                      }
                    //上传成功（或者失败）一张后，再次调用xhrUploadfun函数，模拟循环
                    if (j < uploadImgArr.length - 1) {
                      j++;
                      isUpload = false;
                      xhrUploadfun();
                    }
                  }
                };
                var formdata = new FormData();
                formdata.append("file", singleImg);
            //开始上传
            xhr.open("POST", "/houseIssue/uploadPic", true);
            xhr.send(formdata);
          }
        }else{
         showConfirm("您有照片不符合要求","确定");
       }
     }
// 设置封面
function setImageFace(id){
  $("#"+id).addClass("face_li").siblings().removeClass("face_li");
  $("#"+id).attr("data-isdefault",1);
  $("#"+id).siblings().attr("data-isdefault",0);

}
// 图片旋转
function rotateImage(id){
  var _id = 'Success_'+id.split('_')[1]+'_'+id.split('_')[2];
  var degreesVal = parseInt($('#'+id).val(),10);
  degreesVal += 90;
  if(degreesVal == 360){
    degreesVal = 0;
  } 
  if($('#'+_id).hasClass("face_li")){
    showConfirm("封面照片不允许旋转！","确定")
    return ;
  }
  rotateImg(degreesVal,_id);
  $('#'+id).val(degreesVal);;
}
function rotateImg(degreesVal,id){
    //{{{
      changeFlag = true;
      var curBigPhoto = $('#'+id).find('img');
      var rotateIE = degreesVal/90%4;
      rotateIE = rotateIE < 0 ? rotateIE+4 : rotateIE; 
      if(navigator.userAgent.indexOf("MSIE") != -1){ 
        curBigPhoto.css({'filter':'progid:DXImageTransform.Microsoft.BasicImage(rotation='+rotateIE+')'});
        //curBigPhoto.css({'margin-left':marginLeft,'margin-top':marginTop});
      }else{
        curBigPhoto.css('transform','rotate('+degreesVal+'deg)');
        //curBigPhoto.css({'margin-left':marginLeft,'margin-top':marginTop});
      }
    //}}}
  }
  var suid = 0;
  var removeId = '';
//删除图片
function removeImage(id){
	housefid = $("#houseFid").val();
  showModalBox('delPhoto');
  $("#delPhotoTxt").html('你确定要删除这张照片么？');
  removeId = id;
}
$(function(){
  $("#sureRemoveImageBtn").click(function(){
    var status = $("#status").val();//0 未上架 1 上架
    var len = $('#imageBox').find('.pho_bg').length;
    if($('#'+removeId).hasClass("face_li")){
        isdefault = 1;//封面
        // showConfirm("封面照片不允许删除！","确定");
        $("#delPhotoTxt").html('封面照片不允许删除！');
        return;
      }else{
        isdefault = 0;
      }
    // 上架状态限制
    if(status == 1){
      if(roomType == 0 || roomType == 1){
        if(len <= 2){
          $("#delPhotoTxt").html('无法删除，至少要保留2张图片');
          return;
        }
      }else{
       if(len <=1){
        $("#delPhotoTxt").html('无法删除，至少要保留1张图片');
        return;
      }
    }
  }

  var delpicfid = $('#'+removeId).attr("data-picfid");
  var delpicserveruuid = $('#'+removeId).attr("data-picserveruuid")
  var param = {
    houseBaseFid:housefid,
    houseRoomFid:roomfid,
    picType:roomType,
    housePicFid:delpicfid,
    picserveruuid:delpicserveruuid
  }
  $.ajax({
    url:"/houseIssue/delHousePic",
    type:"post",
    dataType:"json",
    data:param,
    success:function(data){
     if(data.code == 0){
      if($('#'+removeId).hasClass("new")){
        uploadImgArr.shift();
        suid++;
      }
      $('#'+removeId).remove();
      closeModalBox();
    }else{
      $("#sureRemoveImageBtn").removeClass("disabled");
      showConfirm(data.msg,"确定");

    }
  },
  error:function(){

  }
})
})
})
// 加载弹层图片
function addOutsideImage(){
  var outslide_bd = '';
  var outslide_hd = '';
  for(var i = 0 ; i < $("#imageBox").find(".pho_bg").length ; i ++){
    outslide_bd +='<li class="slide_item"><img _src="'+$("#imageBox .pho_bg:eq("+i+")").find("img").attr("data-bigpic")+'"></li>';
  }
  for(var i = 0 ; i < $("#imageBox").find(".pho_bg").length ; i ++){
    outslide_hd +='<li class="slide_item"><div class="img_box"><img src="'+$("#imageBox .pho_bg:eq("+i+")").find("img").attr("src")+'"></div><div class="bg"></div></li>';
  }
  $("#outSlideBox").find(".bd").html(outslide_bd);
  $("#outSlideBox").find(".hd").html(outslide_hd);
}

// 保存图片
function subimage(){
	var images = $(".upload_img").length;
  var status = $("#status").val();//0 未上架 1 上架
  if(images > 8){
    showConfirm("每个区域最多可上传8张照片","确定")
  }
  if(status == 1){
    if(roomType == 0 || roomType == 1){
      if(images < 2){
        showConfirm("每个区域至少上传2张照片","确定");
      }else{
        toSubImage();
      }
    }else{
     if(images < 1){
      showConfirm("每个区域至少上传1张照片","确定")
    }else{
      toSubImage();
    }
  }

}else{
  toSubImage();
}

}
function toSubImage(){
  housefid = $("#houseFid").val();
  var images = $(".upload_img").length;
  var new_images = $(".new").length;
  var imgData = '';
    var imgList = [];//图片地址列表
    // roomfid
    // roomType 0 - 卧室 1 － 客厅 2 - 厨房 3 － 卫生间
    // roomType房间类型
    // 开始拼接文件数据
    isSave = true;
    for(var i = 0 ; i < images ; i ++){
      var _str = '';
      _str += '{';
      _str += "isdefault:"
      + $(".upload_img:eq("+i+")").parents("li").attr("data-isdefault")
      + ","
      + "picbaseurl:'"
      + $(".upload_img:eq("+i+")").find("img").attr("_src")
      + "',"
      + "picsuffix:'"
      + $(".upload_img:eq("+i+")").parents("li").attr("data-suffix")
      + "',"
      + "picfid:'"
      + $(".upload_img:eq("+i+")").parents("li").attr("data-picfid")
      + "',"
      + "picserveruuid:'"
      + $(".upload_img:eq("+i+")").parents("li").attr("data-picserveruuid")
      + "',"
      + "degrees:"
      + $(".upload_img:eq("+i+")").find(".degreesIpt").val();

      _str += '}';
      imgList.push(_str);
    }
    imgData += "{"
    +"picType:"
    +roomType 
    +",roomfid:'"
    + roomfid
    +"',housefid:'"
    +housefid
    +"',imgList:"
    +"["+imgList+"]"
    +"}"
    $.ajax({
      url:"/houseIssue/saveHousePic",
      type:"post",
      dataType:"json",
      data:{param:imgData},
      success:function(data){
        addHousePhotos();
      }

    })
//        addHousePhotos();
$("#photosPart").show();
$("#photoListPart").hide();

}


