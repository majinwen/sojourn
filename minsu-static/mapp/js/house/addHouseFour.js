(function ($) {
	scrollThis('main');
	function scrollThis(id){
		var myScroll = new IScroll('#'+id, { scrollbars: true, probeType: 3, mouseWheel: true });
		var conHeight = $(window).height()-$('.header').height()-$('.box_bottom').height();

		$('#'+id).css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$('#'+id).find('.box').css({'position':'absolute','width':'100%'});
		myScroll.refresh();
	}
	
	
	function showConfirm(str,calback){
	    //信息框-例2
	    layer.open({
	        content: '<div class="loginTips" style="line-height: 1.1rem">'+str+'</div>',
	        btn:['确定'],
	        yes: function(){
	              //直接跳转到手机注册
	        	calback();
	            layer.closeAll(); 
	        }, no: function(){
	            
	        }
	                        
	    });
	}
	
	function compareDate(_id, id) {
		var beginDate = $('#'+_id).attr('mins');
		var endDate = $('#'+id).val();
		var maxDate = $('#'+_id).attr("max");
		var d1 = new Date(beginDate.replace(/\-/g, "\/"));
		var d2 = new Date(endDate.replace(/\-/g, "\/"));
		var max = new Date(maxDate.replace(/\-/g, "\/"));

		if (beginDate != "" && endDate != "" && d1 >= d2) {
			showShadedowTips("只能选择今天以后的日期！", 1);
			$('#'+_id).val(beginDate);
			$('#' + id).val(beginDate);
			return false;
		}
		
		if(endDate != "" && d2 >= max ){
			showShadedowTips("最大时间为2037年！", 1);
			$('#'+_id).val(maxDate);
			$('#' + id).val(maxDate);
			return false;
		}
		return true;
	}
	
	
	
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	//添加房源第四步
	var addHouseFour = {};

	//保存房源相关信息步骤  1=保存房源名称 2=保存房源户型 3=保存房源面积 4=保存配套设施  5=保存房源描述 6=发布房源
	var stepFlag = 0;

	/**
	 * 房源保存相关信息
	 * @param houseName 房源名称
	 * @param houseArea  房源名称
	 * @param tillDate 房源截止日期
	 * @param checkInLimit 最小入住天数
	 * @param leasePrice 房源日价格（分）
	 * @param houseDesc  房源描述
	 * @param isTogetherLandlord 是否是发布房源 默认0 只是更新，不是发布房源
	 */
	var houseParams={
			houseName:"",
			houseArea:"",
			tillDateStr:"",
			checkInLimit:0,
			leasePrice:0,
			houseDesc:"",
			isTogetherLandlord:0,
			isIssue:0,
			houseBaseFid:$("#houseBaseFid").val(),
			rentWay:$("#rentWay").val()
	}
	//配置对象
	var houseConfMsgEntity = {
			houseBaseFid:$("#houseBaseFid").val(),
			dicCode:"",
			dicVal:""
	}
	//配置对象数组
	var houseConfMsgArray = {};
	/**
	 * 定义map结构
	 */
	function HashMap(){
		this.map = {};
	}
	HashMap.prototype = {
			put : function(key, value){
				this.map[key] = value;
			},
			get : function(key){
				if(this.map.hasOwnProperty(key)){
					return this.map[key];
				}
				return null;
			},
			remove : function(key){
				if(this.map.hasOwnProperty(key)){
					return delete this.map[key];
				}
				return false;
			},
			removeAll : function(){
				this.map = {};
			},
			keySet : function(){
				var _keys = [];
				for(var i in this.map){
					_keys.push(i);
				}
				return _keys;
			}
	};
	HashMap.prototype.constructor = HashMap;

	var supportingListMap = new HashMap();

	/**
	 * 初始化houseParams
	 */
	addHouseFour.setHouseParams =  function(){
		houseParams.houseName = "";
		houseParams.houseArea = "";
		houseParams.tillDateStr = "";
		houseParams.checkInLimit = 0;
		houseParams.leasePrice = 0;
		houseParams.houseDesc = "";
		houseParams.isIssue = 0;
		houseParams.isTogetherLandlord =0;
		houseParams.houseBaseFid = $("#houseBaseFid").val();
		houseParams.rentWay = $("#rentWay").val();
	}

	/**
	 * 初始化
	 */
	addHouseFour.init = function(){

		supportingListMap.removeAll();
		addHouseFour.setSupport();
		 
		$("#addhouseName").keyup(function(){
			if($.trim($(this).val()) != ""){
				$("#submitBtn").addClass("org_btn").removeClass("disabled_btn");
			}else{
				$("#submitBtn").removeClass("org_btn").addClass("disabled_btn");
			}
		})
		
		var flag = $("#flag").val();
		if(flag==1){
			var houseStatus = $("#houseStatus").val();
			if(houseStatus==11 || houseStatus==40 || houseStatus==20){
				$("#houseArea").attr("readonly","readonly");
				$("#houseArea").removeClass("j_ipt");
				$("#houseArea").click(function(){
					showShadedowTips("此状态下的房源面积不能修改",1);
					$(this).blur();
				});
			}
		}
		
		/*$("#houseArea").keyup(function(){
			addHouseFour.canNext();
		})
		
		$("#rentDate").change(function(){
			addHouseFour.canNext();
		})
		
		var rentWay = $("#rentWay").val();
		if(rentWay==0){
			
			$("#personList").change(function(){
				addHouseFour.canNext();
			});
			
			$("#housePrice").keyup(function(){
				addHouseFour.canNext();
			})
			
		}
		if(rentWay==1){
			$("#liveTogetherList").change(function(){
				addHouseFour.canNext();
			});
		}
		
		
		var rentWay = $("#rentWay").val();
		if(rentWay==0){
			$("#houseArea").keyup(function(){
				var reg = /^[0-9]+(.[0-9]{2})?$/ ;
				var v = $("#houseArea").val();
				if (!reg.test(v)) {
					showShadedowTips("请填写正确的房源面积",1);
					$("#houseArea").val("");
					return false;
				}
			})
			
			$("#housePrice").keyup(function(){
				var reg = /^[0-9]+(.[0-9]{2})?$/ ;
				var v = $("#housePrice").val();
				if (!reg.test(v)) {
					showShadedowTips("请填写正确的房间价格",1);
					$("#housePrice").val("");
					return false;
				}
			})
			
		}
		*/

		/**
		 * 添加房源名称
		 */
		$("#submitBtn").click(function(e){
			if($(this).hasClass("disabled_btn")){
				return false;
			}else{
				var houseName = $("#addhouseName").val();
				if(houseName == null||houseName==""||houseName==undefined){
					showShadedowTips("请输入房源名称",1);
					return false;
				}
				if(houseName.length < 10 || houseName.length>30){
					showShadedowTips("请填写10-30字的房源名称",1);
					return false;
				}
				addHouseFour.setHouseParams();
				houseParams.houseName = houseName;
				stepFlag = 1;
				var postParam={
						houseName:houseName,
						isIssue:0,
						houseBaseFid:$("#houseBaseFid").val(),
						rentWay:$("#rentWay").val()
				}
				addHouseFour.saveHouse(postParam);
			}
		})

		/**
		 * 保存配套设施
		 */
		$("#supportingListBut").click(function(){
			addHouseFour.saveConMsg();
		});
		$("#arround").keyup(function(){
			/*if($.trim($("#arround").val()).length != 0){
				$("#submitBtnEnvironmen").removeClass("disabled_btn").addClass("org_btn");
			}else{
				$("#submitBtnEnvironmen").addClass("disabled_btn").removeClass("org_btn");
			}*/
		});
		$("#arroundInfoArea").keyup(function(){
			/*if($.trim($("#arroundInfoArea").val()).length != 0){
				$("#submitBtnarroundInfo").removeClass("disabled_btn").addClass("org_btn");
			}else{
				$("#submitBtnarroundInfo").addClass("disabled_btn").removeClass("org_btn");
			}*/
		});
		
		scrollThis('main');
		
		addHouseFour.canNext();
		
		/**
		 * 下一步
		 */
		$("#nextBtn").click(function(obj){
			if($(this).hasClass('disabled_btn')){
				return;
			}
			addHouseFour.sentHouseSource(obj);
		});
	}
	/**
	 * 房源清洁费
	 */
	addHouseFour.cleanFeesOk = function (){
		
		var rentWay = $("#rentWay").val();
		if(rentWay==0){
			var reg = /^\d+(\.\d+)?$/ ;
			var cleaningFees = $("#cleaningFees").val();
			if($.trim(cleaningFees).length>0){
				cleaningFees = cleaningFees.substring(0,cleaningFees.length-1);
			}
			if($.trim(cleaningFees).length==0){
				showShadedowTips("请填写清洁费用",1);
				return false;
			}
			if (!reg.test(cleaningFees)) {
				showShadedowTips("请填写正确的清洁费用",1); 
				return false;
			}
			var cleanPer =$("#cleanPer").val();
			//$.trim(cleanPer).length == 0?0:new Number($.trim(cleanPer));			 
			var housePrice =$("#housePrice").val();
			/*if($.trim(cleaningFees).length>0){
				housePrice = housePrice.substring(0,housePrice.length-1);
			}
			$.trim(housePrice).length == 0?0:new Number($.trim(housePrice));*/
			var cleaningFeesLimit=Math.round(housePrice*cleanPer);
			if(cleaningFees >cleaningFeesLimit){
//				showShadedowTips("清洁费用超过上限",1);
				showConfirm("您的清洁费太高了，最高可设置为"+cleaningFeesLimit+"元",function(){
					$("#cleaningFees").val("");
				});

				
				return false;
			}
			
		}
		 
		return true;
	}
	
	
	
	
	/**
	 * 判断是否可以调到下一步
	 */
	addHouseFour.canNext = function (){
		
		if($.trim($("#rentTime").val()).length != 0 && $.trim($("#houseMessage").val()).length != 0
				&& $.trim($("#arroundInfo").val()).length != 0){
			
			var rentWay = $("#rentWay").val();
			
			/*if(rentWay==0 && ($.trim($("#personList option:selected").val()).length == 0 || $.trim($("#housePrice").val()).length == 0)
					||$.trim($("#houseName").val()).length != 0 || $.trim($("#houseArea").val()).length != 0 ){
					return false;
			}*/
			if(rentWay==0 && ($.trim($("#personList option:selected").val()).length == 0 )
				|| $.trim($("#houseName").val()).length != 0 || $.trim($("#houseArea").val()).length != 0 ){
				return false;
			}
			if(rentWay==1 && ($.trim($("#liveTogetherList option:selected").val()).length == 0 ) ){
					return false;		
			}
			
			$("#nextBtn").removeClass("disabled_btn").addClass("org_btn");
			return true;
		}
		return false;
	}

	/**
	 * 初始化配置设施的值
	 */
	addHouseFour.setSupport =  function(){
		var support = $("#support").val();
		if(support == null||support == "" ||support==undefined ){
			support = "1";
		}
		support = parseInt(support);
		if(support == 2){
			$("#supportList").val("已完成");
			$("#supportList").parent().parent().removeClass("c_ipt_li_none");
		}
		$("#addHouseFourForm").show();
	}

	/**
	 * 提交房源描述
	 */
	addHouseFour.submitChose = function(){
		/*if($("#submitBtnEnvironmen").hasClass("disabled_btn")){
			return false;
		}*/
		var houseDesc = $.trim($("#arround").val())
		if(houseDesc.length == 0){
			showShadedowTips("请填写房源描述",2);
			return false;
		}
		if(houseDesc.length < 100){
			showShadedowTips("请至少填写100字的房源描述",2);
			return false;
		}
		if(houseDesc.length > 1000){
			showShadedowTips("房源描述不能超过1000字",2);
			return false;
		}
		stepFlag = 5;
		var postParam={
				complateShowId:'houseDescCmplShow',
				houseDesc:houseDesc,
				isIssue:0,
				houseBaseFid:$("#houseBaseFid").val(),
				rentWay:$("#rentWay").val()
		}
		addHouseFour.saveHouse(postParam);
	}
	
	/**
	 * 提交周边情况
	 */
	addHouseFour.submitArroundInfo = function(){
		/*if($("#submitBtnarroundInfo").hasClass("disabled_btn")){
			return false;
		}*/
		var houseDesc = $.trim($("#arroundInfoArea").val());
		if(houseDesc.length == 0){
			showShadedowTips("请填写周边情况",2);
			return false;
		}
		if(houseDesc.length < 100){
			showShadedowTips("请至少填写100字的周边情况",2);
			return false;
		}
		if(houseDesc.length > 1000){
			showShadedowTips("周边情况不能超过1000字",2);
			return false;
		}
		stepFlag = 6;
		var postParam={
				complateShowId:'arroundInfo',
				houseAroundDesc:houseDesc,
				isIssue:0,
				houseBaseFid:$("#houseBaseFid").val(),
				rentWay:$("#rentWay").val()
		}
		addHouseFour.saveHouse(postParam);
	}

	/**
	 * 初始化页面
	 */
	addHouseFour.returnInitPage = function(){

		supportingListMap.removeAll();
		if(stepFlag == 1){
			$("#houseName").val(houseParams.houseName);
			$("#addhouseName").val("");
			$("#addSupporting").hide();
			$("#toAddHouseNameForm").hide();
			$("#peripheryEnvironmen").hide();
			$("#arroundInfoForm").hide();
			$("#addHouseFourForm").show();
		}
		if(stepFlag == 4){
			$("#supportList").val("已完成");
			$("#supportList").parent().parent().removeClass("c_ipt_li_none");
			$("#addSupporting").hide();
			$("#toAddHouseNameForm").hide();
			$("#peripheryEnvironmen").hide();
			$("#arroundInfoForm").hide();
			$("#addHouseFourForm").show();
			scrollThis('main');
		}
		if(stepFlag == 5){
			$("#houseMessage").val($("#arround").val());
			$("#houseMessageShow").val("已完成");
			$("#houseMessage").parent().parent().removeClass("c_ipt_li_none");
			$("#addSupporting").hide();
			$("#toAddHouseNameForm").hide();
			$("#peripheryEnvironmen").hide();
			$("#arroundInfoForm").hide();
			$("#addHouseFourForm").show(); 	
		}
		
		if(stepFlag == 6){
			$("#arroundInfoShow").val("已完成");
			$("#arroundInfo").parent().parent().removeClass("c_ipt_li_none");
			$("#arroundInfoShow").addClass("span_r_done");
			$("#arroundInfoShow").text("已完成");
			$("#arroundInfo").val($("#arroundInfoArea").val());
			$("#addSupporting").hide();
			$("#toAddHouseNameForm").hide();
			$("#peripheryEnvironmen").hide();
			$("#arroundInfoForm").hide();
			$("#addHouseFourForm").show(); 
		}
		
		//总算发布房源结束，太不容易了
//		if(stepFlag == 6){
//			var houseBaseFid = $("#houseBaseFid").val();
//			var rentWay=$("#rentWay").val();
//			window.location.href = SERVER_CONTEXT +"houseInput/"+LOGIN_UNAUTH+"/goToFinished?houseBaseFid="+houseBaseFid+"&rentWay="+rentWay;
//		}
		addHouseFour.canNext();
		scrollThis('main');
	}
	/**
	 * 打开添加房源名称页面
	 */
	addHouseFour.toAddHouseName = function(){
		$("#addHouseFourForm").hide();
		$("#addSupporting").hide();
		$("#peripheryEnvironmen").hide();
		$("#addhouseName").val($("#houseName").val());
		$("#toAddHouseNameForm").show();
	}
	/**
	 * 到房源描述
	 */
	addHouseFour.toEnvironmen = function(){
		$("#addHouseFourForm").hide();
		$("#addSupporting").hide();
		$("#toAddHouseNameForm").hide();
		$("#arroundInfoForm").hide();
		$("#arround").val($("#houseMessage").val());
		numThis();
		$("#peripheryEnvironmen").show();
		$("#submitBtnEnvironmen").removeClass("disabled_btn").addClass("org_btn");
	}
	
	/**
	 * 到周边情况
	 */
	addHouseFour.toArroundInfo = function(){
		$("#addHouseFourForm").hide();
		$("#addSupporting").hide();
		$("#toAddHouseNameForm").hide();
		$("#peripheryEnvironmen").hide();
		$("#arroundInfoArea").val($("#arroundInfo").val());
		numThis();
		$("#arroundInfoForm").show();
		$("#submitBtnarroundInfo").removeClass("disabled_btn").addClass("org_btn");
	}
	
	function numThis(){
		$(".area_txt").each(function(){
        	if($(this).val() != ''){
        		$(this).parents(".area_box").find(".num").html($(this).val().length);
        	}else{
        		$(this).parents(".area_box").find(".num").html(0);
        	}
        	$(this).on('input pasteafter',function(){
        		if($(this).val().length >= 1000){
        			$(this).val($(this).val().slice(0,1000));
        			$(this).parents(".area_box").find(".num").html(1000);
        		}else{
        			$(this).parents(".area_box").find(".num").html($(this).val().length);
        		}
        	})
        })
	}
	/**
	 * 打开添加配套设施
	 */
	addHouseFour.toSupporting = function(){

		var houseBaseFid = $("#houseBaseFid").val();
		var rentWay = $("#rentWay").val();
		
		$("#addHouseFourForm").hide();
		$("#toAddHouseNameForm").hide();
		$("#peripheryEnvironmen").hide();
		CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"houseInput/"+LOGIN_UNAUTH+"/findFacilityList", {"houseBaseFid":houseBaseFid,"rentWay":rentWay}, function(data){
			if(data){
				if(data.code == 0){
					$("#supportingList").empty();
					var _html="";
					_html +="<header id='supportHeader' class='header'>";
					_html +="<a href='javascript:AddHouseFour.refresPage();'  class='goback' ></a>";
					_html +="<h1>便利设施</h1>";
					_html +="</header>";
					$("#supportHeader").remove();
					$("#supportingList").before(_html);
					_html="";
					
					
					var facilityList = data.data.facilityList;
					var serviceList = data.data.serviceList;
					var houseFacilityList =data.data.houseFacilityList;
					var houseServeListList = data.data.houseServeListList;
					_html +="<div id='main1' class='main'><div class='box'>";
					for (var i = 0; i < facilityList.length; i++) {
						var listEnumVo = facilityList[i].subEnumVals;
						_html +="<div class='title_classify'>"+facilityList[i].text+"</div>";
						_html +="<ul class='myCenterList myCenterList_no'>";
						for (var j = 0; j <listEnumVo.length; j++) {
							_html +="<li class='clearfix bor_b houseTypeLi' onclick='AddHouseFour.cleckedSupporting(this)'>"+listEnumVo[j].text+"<span class='icon_r icon icon_check' id='"+facilityList[i].key+"_"+listEnumVo[j].key+"'value='"+listEnumVo[j].key+"'  key='"+facilityList[i].key+"_"+listEnumVo[j].key+"'value='"+listEnumVo[j].key+"' ></span>";
							_html +="</li>";
						}
						_html +="</ul>";
					}
					if(serviceList.length>0){
						_html +="<div class='title_classify'>服务</div>";
						_html +="<ul class='myCenterList myCenterList_no'>";
						for (var k = 0; k < serviceList.length; k++) {
							_html +="<li class='clearfix bor_b houseTypeLi ' onclick='AddHouseFour.cleckedSupporting(this)'>"+serviceList[k].text+"<span class='icon_r icon icon_check' id='ProductRulesEnum0015_"+serviceList[k].key+"' key='ProductRulesEnum0015_"+serviceList[k].key+"' value='"+serviceList[k].key+"' ></span>";
							_html +="</li>";
						}
						_html +="</ul>";
					}
					_html +="</div ></div>";
					$("#supportingList").html(_html);
					$("#addSupporting").show();
					if(houseFacilityList.length>0){
						for (var m = 0; m < houseFacilityList.length; m++) {
							var key = houseFacilityList[m].dicCode+"_"+houseFacilityList[m].dicValue;
							$("#"+key).addClass("active");

						} 
						$("#support").val(2)
					}
					if(houseServeListList.length>0){
						for (var n = 0; n < houseServeListList.length; n++) {
							var key = houseServeListList[n].dicCode+"_"+houseServeListList[n].dicValue;
							$("#"+key).addClass("active");
						} 
						$("#support").val(2)
					}
					scrollThis('main1');
				}else{
					showShadedowTips(data.msg,1);
				}

			}
		})
	}

	/**
	 * 保存第四步
	 */
	addHouseFour.sentHouseSource = function(obj){
		if($(obj).hasClass("disabled_btn")){
			return false;
		}
		
		stepFlag = 6;
		var houseName = $("#houseName").val();//房源名称
		var houseArea = $("#houseArea").val();//房源面积
		var rentTime = $("#rentTime").val();//出租期限
		var person = $("#personList option:selected").val();//入住人数限制
		var housePrice = $("#housePrice").val();//出租价格
		//var houseStyle = $("#houseStyle").val();//房源户型
		var supportList = $("#supportList").val();//配套设施
		var rentWay = $("#rentWay").val();
		var houseMessage = $("#houseMessage").val();//房源描述
		var arroundInfo = $("#arroundInfo").val();//周边情况
		var priceLow=$("#priceLow").val();
		var priceHigh=$("#priceHigh").val();
		var cleaningFees = $("#cleaningFees").val();
		if(rentWay == 0){
			
			if(houseName == ""||houseName==null||houseName==undefined){
				showShadedowTips("请填写房源名称",1);
				return false;
			}
			
			if(houseName.length < 10 || houseName.length>30){
				showShadedowTips("请填写10-30字的房源名称",1);
				return false;
			}
			
			if($.trim(houseArea).length>0){
				houseArea = houseArea.substring(0,houseArea.length-2);
			}
			if(houseArea == ""||houseArea==null||houseArea==undefined){
				showShadedowTips("请填写整套房源面积",1);
				return false;
			} 
			
			var reg = /^\d+(\.\d+)?$/ ; 
			if (!reg.test(houseArea) || houseArea <=0) {
				showShadedowTips("请填写正确的房源面积",1);
				$("#houseArea").val("");
				return false;
			}			
			
		
			/*if(houseStyle == ""||houseStyle==null||houseStyle==undefined){
				showShadedowTips("请选择整套房屋的户型",1);
				return false;
			}*/
			
			if($.trim(housePrice) == ""||housePrice==null||housePrice==undefined){
				showShadedowTips("请填写整套房屋价格",1);
				return false;
			}
			
			if (!reg.test(housePrice)) {
				showShadedowTips("请填写正确的房间价格",1);
				$("#housePrice").val("");
				return false;
			}
			
			if(parseInt(housePrice)<parseInt(priceLow)){
				showShadedowTips("房源价格不能低于"+priceLow+"元",1);
				return false;
			}
			
			if(parseInt(housePrice)>parseInt(priceHigh)){
				showShadedowTips("房源价格不能高于"+priceHigh+"元",1);
				return false;
			}
			housePrice = Math.round(parseFloat(housePrice)*100);
			
			
			if(!addHouseFour.cleanFeesOk()){
				return false;
			}
			if($.trim(cleaningFees).length>0){
				cleaningFees = cleaningFees.substring(0,cleaningFees.length-1);
			}
			cleaningFees = Math.round(parseFloat(cleaningFees)*100);
			
		}
		var isTogetherLandlord;
		if(rentWay == 1){
			isTogetherLandlord = $("#liveTogether").val();
			if(isTogetherLandlord == null||isTogetherLandlord==""||isTogetherLandlord==undefined){
				showShadedowTips("请选择是否与房东同住",1);
				return false;
			}
			isTogetherLandlord = $("#liveTogetherList option:selected").val();
		}
		
		
		if(rentTime == ""||rentTime==null||rentTime==undefined){
			showShadedowTips("请选择出租期限",1);
			return false;
		}
		if(!compareDate('rentDate','rentTime')){
			return false;
		}
		
		
		if(rentWay == 0){
			if(person == ""||person==null||person==undefined){
				showShadedowTips("请选择入住人数限制",1);
				return false;
			}
		}
		
		if(supportList == ""||supportList==null||supportList==undefined){
			showShadedowTips("请选择配套设施",1);
			return false;
		}
		if(houseMessage == ""||houseMessage==null||houseMessage==undefined){
			showShadedowTips("请填写房源描述",1);
			return false;
		}
		if(arroundInfo == ""||arroundInfo==null||arroundInfo==undefined){
			showShadedowTips("请填写周边情况",1);
			return false;
		}
		
		
		var postParam ={};
		
		if(rentWay == 0){
			postParam = {
					nextStep:true,
					houseName:houseName,
					houseArea:houseArea,
					tillDateStr:rentTime,
					checkInLimit:person,
					leasePrice:housePrice,
					houseCleaningFees:cleaningFees,
					isIssue:2,
					houseBaseFid:$("#houseBaseFid").val(),
					rentWay:rentWay
			}
		}
		if(rentWay == 1){
			postParam = {
					nextStep:true, 
					tillDateStr:rentTime, 
					isIssue:2,
					isTogetherLandlord:isTogetherLandlord,
					houseBaseFid:$("#houseBaseFid").val(),
					rentWay:rentWay
			}
		}
		
		console.log(postParam);
		addHouseFour.saveHouse(postParam);
		
	}
	/**
	 * 保存房租基本信息
	 */
	addHouseFour.saveHouse = function(postParam){
		
		
		CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"houseInput/"+LOGIN_UNAUTH+"/issueHouse",postParam, function(data){
			if(data){
				if(data.code == 0){
					
					if(postParam.nextStep){
						var flag = $("#flag").val();
						if(flag ==1){
							window.location.href=SERVER_CONTEXT+"houseInput/"+LOGIN_UNAUTH+"/goToHouseUpdate?houseBaseFid="+$("#houseBaseFid").val()+"&flag="+flag+"&rentWay="+$("#rentWay").val()+"&houseRoomFid="+$("#houseRoomFid").val();
						}else{
							window.location.href=SERVER_CONTEXT+"roomMgt/"+LOGIN_UNAUTH+"/houseApartment?houseBaseFid="+$("#houseBaseFid").val()+"&flag="+flag+"&houseRoomFid="+$("#houseRoomFid").val();
						}
						
						//addHouseFour.returnInitPage();
					}else{
						//$("#"+postParam.complateShowId).addClass("span_r_done");
						//$("#"+postParam.complateShowId).html(postParam.houseAroundDesc);
						//每次点了确认都走这个方法，初始化页面,回显也去那个位置调
						addHouseFour.returnInitPage();
					}
				}else{
					showShadedowTips(data.msg,1);
				}
			}
		})
	}

	//校验配套设施 说明 1.选中 添加到supportingListMap中 2.删除从supportingListMap删除
	addHouseFour.cleckedSupporting = function(o){
		if($(o).find(".icon_check").hasClass("active")){
			$(o).find(".icon_check").removeClass("active");
		}else{
			$(o).find(".icon_check").addClass("active");
		}
	}
	/**
	 * 校验
	 */
	addHouseFour.clecked = function(e){
		if($(e).find(".icon_check").hasClass("active")){
			$(e).find(".icon_check").removeClass("active");
			$("#sentHouse").removeClass("org_btn").addClass("disabled_btn");
		}else{
			$(e).find(".icon_check").addClass("active");
			$("#sentHouse").addClass("org_btn").removeClass("disabled_btn");

		}
	}

	/**
	 * 保存房源的配置信息
	 */
	addHouseFour.saveConMsg = function(){

		var houseBaseFid = $("#houseBaseFid").val();

		$(".icon_check").each(function(o){
			var key = $(this).attr("key");
			var value = $(this).attr("value");
			if($(this).hasClass("active")){
				supportingListMap.put(key, value);
			}
			
		});
		var keys = supportingListMap.keySet();
		if(keys.length<=0){
			showShadedowTips("请选择配置信息",1);
			return false;
		}
		keys = keys.toString();
		CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"houseInput/"+LOGIN_UNAUTH+"/saveConMsg",{"houseBaseFid":houseBaseFid,"supportArray":keys}, function(data){
			if(data){
				if(data.code == 0){
					stepFlag = 4;
					addHouseFour.returnInitPage();
				}else{
					showShadedowTips(data.msg,1);
				}
			}
		})

	}
	
	/**
	 * 刷新页面
	 */
	addHouseFour.refresPage = function(){
		$("#addSupporting").hide();
		$("#toAddHouseNameForm").hide();
		$("#peripheryEnvironmen").hide();
		$("#addHouseFourForm").show();
	}
	addHouseFour.init();
	window.AddHouseFour = addHouseFour;
	
}(jQuery));
