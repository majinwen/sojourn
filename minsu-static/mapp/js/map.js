	function getPoint(){
		// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,12);
	map.addControl(new BMap.ZoomControl());          
	// 创建地址解析器实例
	var myGeo = new BMap.Geocoder();
	var geolocation = new BMap.Geolocation();
	for(var i = 0 ; i < $(".ipt_lg").length ; i++){
		if($.trim($(".ipt_lg").eq(i).val().length) != 0){
			myGeo.getPoint($("#city").val()+$("#district").val()+$("#street").val(), function(point){
				if (point) {
					map.centerAndZoom(point, 16);
					map.addOverlay(new BMap.Marker(point));
				}
			}, $("#city").val());
			$("#nextBtn").removeClass("disabled_btn").addClass("org_btn");
		}else{
			geolocation.getCurrentPosition(function(r){
				if(this.getStatus() == BMAP_STATUS_SUCCESS){
					var mk = new BMap.Marker(r.point);
					map.addOverlay(mk);
					map.panTo(r.point);
					$("#nextBtn").addClass("disabled_btn").removeClass("org_btn");
			// alert('您的位置：'+r.point.lng+','+r.point.lat);
			// var pt = r.point;
			// myGeo.getLocation(pt, function(rs){
			// 	var addComp = rs.addressComponents;
			// 		// alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
			// 		$("#city").val(addComp.city);
			// 		$("#district").val(addComp.district );
			// 		$("#street").val(addComp.street+ " "+addComp.streetNumber);
			// 		$("#nextBtn").removeClass("disabled_btn").addClass("org_btn");
			// 	});    
		}
		else {
			alert('failed'+this.getStatus());
		}        
	},{enableHighAccuracy: true})
		}
	}
	}
