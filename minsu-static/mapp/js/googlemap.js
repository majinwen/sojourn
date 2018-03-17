var map;

/**
 * 测试 google地图加载地图的js调用次数
 */
function googleMapTest(){
	var map1;
	var j=0;
	try {
		for (var i = 0; i <3000; i++) {
			j++;
			var myLatlng = new google.maps.LatLng(42.8112654,141.4533427);
		    var mapOptions = {
		        zoom: 18,
		        center: myLatlng,
		        panControl: true,
		        zoomControl: true,
		        mapTypeControl: false,  //右上角的地图形式
		        scaleControl: true,
		        streetViewControl: false,
		        overviewMapControl: true,
		        disableDragging: false
		    }
		    map1 = new google.maps.Map(document.getElementById('allmap'), mapOptions);
		    map1.setOptions({draggable: true});
		}
		alert(j);
	} catch (e) {
		alert(j);
		console.log(e);
	}
	
}


function getPoint(){
	if($.trim($("#locationName").val()).length != 0 || $.trim($("#communityName").val()).length != 0 || $.trim($("#houseStreet").val()).length != 0){
		canNext();
		try {
			var keyWStr = $("#locationName").val()+$("#houseStreet").val()+$("#communityName").val();
			getGoogleLocationByKeyword(keyWStr);		
		} catch (e) {
			console.log(e);
		}
	}
}

//前段通过关键词查询 地名
function getGoogleLocationByKeyword(keyWStr){
 	if(keyWStr){
        var geocoder = new google.maps.Geocoder();
        if (geocoder) {
             geocoder.geocode({'address': keyWStr}, function (results, status) {
                 if (status == google.maps.GeocoderStatus.OK) {
                     if (results[0]) {
                         try {
                             var addr = '';
                             if(results[0].geometry){
                                 var location = results[0].geometry.location;
                                 if(location){
                              	     var longitude = location.lng();
                                       var latitude = location.lat();
                                       setMap(latitude,longitude,18)
                                 }
                              }
                             if(results[0].address_components){
                                 var nameLength = results[0].address_components.length;
                                 for(var i = nameLength - 1; i >= 0 ; i --){
                                     if(results[0].address_components[i].types[0] != 'country' && results[0].address_components[i].types[0] != 'postal_code'){
                                         if(i == 0){
                                             addr += results[0].address_components[i]['long_name'];
                                         }
                                         else {
                                             addr += results[0].address_components[i]['long_name']+',';
                                         }
                                     }
                                     else {
                                     }
                                 }
                             }

                         }
                         catch (e){
                             console.log(e);
                         }
                         displayAddressTag(addr);
                     }
                 }
                 else {

                 }
             });
         }
    }
 }

/*function canNext(){
	if($.trim($("#locationName").val()).length != 0 && $.trim($("#houseStreet").val()).length != 0 ){
		
		if($.trim($("#houseStreet").val()).length >50){
			showShadedowTips("街道名称过长",1);
			return;
		}
		if($.trim($("#communityName").val()).length >50){
			showShadedowTips("小区名称过长",1);
			return;
		}
		$("#nextBtn").removeClass("disabled_btn").addClass("org_btn");
		return true;
	}else{
        $("#nextBtn").removeClass("org_btn").addClass("disabled_btn");
        return false;
    }
}*/
//初始化地图,加上拖动结束监听事件
function setMap(latitude,longitude,zoom){
    var myLatlng = new google.maps.LatLng(latitude, longitude);
    var mapOptions = {
        zoom: zoom,
        center: myLatlng,
        panControl: true,
        zoomControl: true,
        mapTypeControl: false,  //右上角的地图形式
        scaleControl: true,
        streetViewControl: false,
        overviewMapControl: true,
        disableDragging: false
    }
    map = new google.maps.Map(document.getElementById('allmap'), mapOptions);
    map.setOptions({draggable: true});
    
    $("[name='longitude']").val(longitude);
    $("[name='latitude']").val(latitude);

    //拖动地图结束后触发
    var timeoutflag=null;
    //idle 第一次进入和拖动后触发
    //为了防止idle第一次触发
    var firstIdle = 0;
    var idleSwich = true;
    google.maps.event.addListener(map,'idle',function() {

        if(firstIdle == 0){
            firstIdle ++;
            return ;
        }
        else {

        }
        //将地图中心点保存到父页面的元素里,再调用父元素方法保存
        var longitude = map.getCenter().lng();
        var latitude = map.getCenter().lat();
        //经度超过-180,180时候的处理
        if(longitude>180 || longitude<-180){
            if(longitude>180){
                longitude = longitude - Math.floor((longitude+180) / 360) * 360;
            }
            else if(longitude<-180){
                longitude = longitude - Math.ceil((longitude-180) / 360) * 360;
            }
        }
        $("[name='longitude']").val(longitude);
        $("[name='latitude']").val(latitude);

        //后端查
//    getAddressByLatAndLng(latitude,longitude,displayAddressTag);

        //原方法
        var geocoder = new google.maps.Geocoder();
        if (geocoder) {
            geocoder.geocode({'location': map.getCenter()}, function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (results[0]) {
                        try {
                            var address = '';
                            if(results[0].geometry){
                                var location = results[0].geometry.location;
                                if(location){
                             	     var longitude = location.lng();
                                     var latitude = location.lat();
                                     $("[name='longitude']").val(longitude);
                                     $("[name='latitude']").val(latitude);
                                   //  setMap(latitude,longitude,15);
                                }
                             }
                            if(results[0].address_components){
                                var nameLength = results[0].address_components.length;
                                for(var i = nameLength - 1; i >= 0 ; i --){
                                    if(results[0].address_components[i].types[0] != 'country' && results[0].address_components[i].types[0] != 'postal_code'){
                                        if(i == 0){
                                            address += results[0].address_components[i]['long_name'];
                                        }
                                        else {
                                            address += results[0].address_components[i]['long_name']+',';
                                        }
                                    }
                                    else {
                                    }
                                }
                            }


                        }
                        catch (e){
                            console.log(e);
                        }

                        displayAddressTag(address);
                        
                    }
                    else {
                        console.log('status != google.maps.GeocoderStatus.OK');
                    }
                } else {
                }

            });
        }

    });
    //去掉底部的跳转谷歌的链接
  //  waitChangeGoogleInfor();
}

//显示名称标签
function displayAddressTag(Str) {
    $('.addressTag').css('margin-left','-170px');
    $('.addressArrow').show();
    $('.addressTag').html(Str);
    $('.addressTag').css('margin-left','-'+($('.addressTag').width()/2+20)+'px');
}


//更改谷歌地图信息
function waitChangeGoogleInfor(){
    setTimeout(function(){
        changeInforFromGoogle();
    },500);
    setTimeout(function(){
        changeInforFromGoogle();
    },1000);
    setTimeout(function(){
        changeInforFromGoogle();
    },2000);
    setTimeout(function(){
        changeInforFromGoogle();
    },5000);
    setTimeout(function(){
        changeInforFromGoogle();
    },10000);
    setTimeout(function(){
        changeInforFromGoogle();
    },30000);
}
//更改地图信息
function changeInforFromGoogle(){
    var ahr = document.getElementsByTagName('a');
    ahr[1].href = 'javascript:void(0);';
    //ahr[2].href = 'javascript:void(0);';
    var cc = document.getElementsByClassName('gm-style-cc');
    var par = cc[0].parentNode;
    par.style.display = 'none';
    var div = ahr[0].childNodes;
   /* var img = div[0].childNodes;
    img[0].src = '/images/googlemark.png';
    img[0].style.width = '120px';*/
}
//googleMapTest();