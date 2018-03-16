'use strict';

/* 所有的ajax调用  可以使用此方法提交 url:要提交的路径 data:提交的数据 binary:提交成功的回调函数*/
function ajaxPostSubmit(url,data,binary){
	$.ajax({
    type: "POST",
    url: url,
    dataType:"json",
    data: data,
    success: function (result) {
    	binary(result);
    },
    error: function(result) {
       alert("error:"+result);
        }
     });
}
	

/* 加载树  公共方法封装  domId:id信息  obj:树的数据  binary：点击回调函数*/
function treeViewCommon(domId,obj,binary){
	//加载左侧树	
    $('#'+domId).treeview({
        color: " inherit",
        data: obj,
        onNodeSelected: function (event, node) {
        binary(event, node);
     }
  })
}


/* layer方法confirm  mes:展示的信息 iconNum：layer的icon binary：confirm方法回调  */
function layerConfirm(mes,iconNum,binary){
	layer.confirm(mes, {icon: iconNum, title:'提示'},function(index){
		binary(index);
	});
}

/* 笑脸图标，显示2000ms 自动消失，不需要用户确认*/
function msgLayer(mesg){
	layer.msg(mesg, {icon: 6,time: 2000, title:'提示'});
}