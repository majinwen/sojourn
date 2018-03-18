<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE >
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<base href="${basePath }>">
<title>自如民宿后台管理系统</title>
<meta name="keywords" content="自如民宿后台管理系统">
<meta name="description" content="自如民宿后台管理系统">
<link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico">
<link href="${staticResourceUrl}/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
<link href="css/index.css?008" rel="stylesheet" type="text/css">
<script src="js/echarts.js"></script>
<script src="js/dark.js"></script>
<script src="js/jquery.min.js"></script>
<script src="js/china.js"></script>
</head>
</head>
<html>
<body>
<div style="width: 100%;height:775px; background-color: #404A59;">
	<div id="main" style="width: 70%;height:775px;float: right;"></div>
	
	<!-- -------------各城市订单展示---------------  -->
	<div class="Top_Record">
		<div class="record_Top"><p>各城市订单展示</p></div>
	    <div class="topRec_List">
	  		<dl>
	        	<dd>城市</dd>
	        	<dd>总单量</dd>
	        	<dd>日增订单量</dd>
	        </dl>
	        <div class="maquee">
	            <ul id="orderNum">
	            </ul>
	        </div>
	    </div>
	</div> 
</div>
<script type="text/javascript">
	//生成地图
	var data;
	$.ajax(  
		  {  
	            url:'cityMapCoordinates',  
	            type:"post",  
	            async:false,  
	            dataType:"json",  
	            timeout:"1000",  
	            error:function(){
					console.log("服务加载出错");
				},
	            success:function(cityCoordinates){
	         	   data=cityCoordinates;
	            }
	     }  
	 );
	option = {
	    backgroundColor: '#404a59',
	    title: {
	        text: '自如民宿全国单量分布',
	        left: 'center',
	        textStyle: {
	            color: '#fff'
	        }
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: function (params) {
	            return params.name + ' : ' + params.value[2]+"单";
	        }
	    },
	    geo: {
	        map: 'china',
	        label: {
	            emphasis: {
	                show: false
	            }
	        },
	        roam: true,
	        itemStyle: {
	            normal: {
	                areaColor: '#323c48',
	                borderColor: '#111'
	            },
	            emphasis: {
	                areaColor: '#2a333d'
	            }
	        }
	    },
	    series : [
	        {
	            name: '城市单量',
	            type: 'scatter',
	            coordinateSystem: 'geo',
	            data: data,
	            symbolSize: 12,
	            label: {
	                normal: {
	        	        formatter: function (params) {
	        	            return params.name;
	        	        },
	                    position: 'right',
	                    show: false
	                },
	                emphasis: {
	                    show: true
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: '#ddb926'
	                }
	            }
	        },
            {
                name: '订单提醒',
                type: 'effectScatter',
                coordinateSystem: 'geo',
                data: [],
                symbolSize: 15,
                showEffectOn: 'render',
                rippleEffect: {
                    brushType: 'stroke'
                },
                hoverAnimation: true,
                label: {
	                normal: {
	        	        formatter: function (params) {
	        	            return params.name;
	        	        },
	                    position: 'right',
	                    show: false
	                },
                },
                itemStyle: {
                    normal: {
                        color: '#f4e925',
                        shadowBlur: 10,
                        shadowColor: '#333'
                    }
                },
                zlevel: 1
            }
	    ]
	};
    var myChart = echarts.init(document.getElementById('main'));
    myChart.setOption(option);
    //闪烁数据数组
    var glintD=[];
    //定时刷新图表
	function refreshData(){
	     if(!myChart){
	          return;
	     }
	      //更新数据
	      var option = myChart.getOption();
	      var d;
     	  $.ajax(  
	    		{  
	    	            url:'cityMapCoordinates',  
	    	            type:"post",  
	    	            async:false,  
	    	            dataType:"json",  
	    	            timeout:"1000",  
	    	            error:function(){
							console.log("服务加载出错");
	    	            },
	    	            success:function(cityCoordinates){
	    	         	   d=cityCoordinates;
	    	            }
	    	     }  
	      );
	      //模拟数据改变
	      /**d=[{"name":"北京市","value":[116.46,39.92,242.0]},{"name":"南京市","value":[118.78,32.04,0.0]},{"name":"无锡市"
	    	  ,"value":[120.29,31.59,0.0]},{"name":"苏州市","value":[120.62,31.32,10.0]},{"name":"乌鲁木齐市","value":[87.68,43.77,1.0]}]**/
	      var op=myChart.getOption();
	      var oldD=op.series[0].data;
	      //是否有订单增加的城市
	      for(var i=0;i<oldD.length;i++){
	    	  var v;
	    	  var vboolean=false;
	    	  for(var j=0;j<d.length;j++){
	    		  if(oldD[i].name==d[j].name){
	    			  v=d[j];
	    			  vboolean=true;
	    			  break;
	    		  }
	    	  }
	    	  if(vboolean&&v.value[2]>oldD[i].value[2]){
	    		  if(dropDistinct(glintD,v.name)==-1){
		    		  oldD[i].value[2]=v.value[2];
		    		  glintD.push(v);
	    		  }else{
	    			  glintD[dropDistinct(glintD,v.name)].value[2]=v.value[2];
	    		  }
	    	  }
	      }
	      //是否有增加的城市
	      for (var i=0;i<d.length;i++){
	    	  var isHave=false;
	    	  for(var j=0;j<oldD.length;j++){
	    		  if(d[i].name==oldD[j].name){
	    			  isHave=true;
	    			  break;
	    		  }
	    	  }
	    	  if(!isHave&&d[i].value[2]!=0){
	    		  if(dropDistinct(glintD,d[i].name)==-1){
	    			  glintD.push(d[i]);
	    		  } else {
	    			  glintD[dropDistinct(glintD,d[i].name)].value[2]=d[i].value[2];
	    		  }
	    	  }
	      }
	      op.series[0].data = d;
	      op.series[1].data = glintD; 
	      console.log(glintD);
	      myChart.setOption(op);    
	      //刷新订单列表
	      refreshOrderData();
	}
    //鼠标移开事件
    myChart.on('mouseout', function (params) {
    	if (params.componentType === 'series') { 
    		if(params.seriesType=='effectScatter'){
    			for(var i=0;i<glintD.length;i++){
    				if(params.data.name==glintD[i].name){
    					glintD.splice(i,1);
    					break;
    				}
    			}
    			var op=myChart.getOption();
    			op.series[1].data = glintD;   
    		    myChart.setOption(op);    
    		}
    	}
    });
	setInterval("refreshData()",1000*200);
	//查询城市是否存在
	function dropDistinct(glintD,cityName){
		var index=-1;
		for(var i=0;i<glintD.length;i++){
			if(glintD[i].name==cityName){
				index=i;
				break;
			}
		}
		return index;
	}
</script>
<script type="text/javascript">
	 function autoScroll(obj){
		$(obj).find("ul").animate({  
			marginTop : "-39px"  
		},500,function(){  
			$(this).css({marginTop : "0px"}).find("li:first").appendTo(this);  
		})  
	}
	//刷新订单数据
	function refreshOrderData(){
   	  $.ajax(  
	    		{  
	    	            url:'cityOrderNumList',  
	    	            type:"post",  
	    	            async:false,  
	    	            dataType:"json",  
	    	            timeout:"1000",  
	    	            error:function(){
							console.log("服务加载出错");
	    	            },
	    	            success:function(cityOrderNum){
	    	            	var orderHtml='';
	    	            	for(var i=0;i<cityOrderNum.length;i++){
	    	            		orderHtml=orderHtml+"<li><div>"+cityOrderNum[i].cityName+"</div><div>"+cityOrderNum[i].orderTotal+"</div><div>"+cityOrderNum[i].orderDayNum+"</div></li>"
	    	            	}
	    	            	$("#orderNum").html(orderHtml);
	    	            }
	    	     }  
	   );
	}
	$(function(){
		refreshOrderData();
		setInterval('autoScroll(".maquee")',3000);
	}) 	
</script> 
</body>
</html>
