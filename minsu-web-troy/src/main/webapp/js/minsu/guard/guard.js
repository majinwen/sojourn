(function ($) {

	var guard = {};
	 
	/**
	 * 初始化
	 */
	guard.init = function(){
		$("#guardForm").validate();
		$("#saveGuardArea").click(function(){
			$("#saveGuardArea").show();
			$("#updateGuardArea").hide();
			CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"guard/saveGuardArea", $("#guardForm").serialize(), function(data){
				if(data.code == 0){
					CommonUtils.query();
					guard.closeTwoWindow();
				}else{
					alert(data.msg);
				}
			})
		});

		
		
		//修改
		$("#updateGuardArea").click(function(dada){
			
			CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"guard/updateGuardArea?flag=2", $("#guardForm").serialize(), function(data){
				if(data.code == 0){
					CommonUtils.query();
					$('#myModal1').modal('hide');
					return false;
				}else{
					alert(data.msg);
				}
			})
		});
		CommonUtils.getNationInfo('nationCode');
		guard.getNationInfo('nationCodeNew');


	}

	guard.updateGuardArea =  function(flag,param){
		CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"guard/updateGuardArea?flag="+flag,param, function(data){
			if(data.code == 0){
				window.location.href = SERVER_CONTEXT+"guard/guardAreaIndex";
			}else{
				alert(data.msg);
			}
		})
	}
	guard.guardParam = function (params) {
		return {
			limit: params.limit,
			page: $('#listTable').bootstrapTable('getOptions').pageNumber,
			empCode:$.trim($('#empCode').val()),
			empPhone:$.trim($('#empPhone').val()),
			empName:$.trim($('#empName').val()),
			cityCode:$.trim($('#cityCode').val()),
			provinceCode:$.trim($('#provinceCode').val()),
			nationCode:$.trim($('#nationCode').val())
		};
	}

	/* 列表 操作 值设置 */
	guard.menuOperData = function (value, row, index){
		var _html = "";
		if(row.isDel == 0){
			_html +="<button id='addMenuButton"+index+"' onclick='Guard.showGuardArea(\""+row.fid+"\",2)' style='height: 26px;background-color: #1ab394;line-height: normal;border:1 solid #FFCC00;color: #FFFFFF;font-size: 9pt;font-style: normal;font-variant: normal;font-weight: normal;' type='button' class='btn btn-primary' data-toggle='modal' >修改</button>";
			_html +="<button id='addMenuButton"+index+"' onclick='Guard.showGuardArea(\""+row.fid+"\",1,\""+row.empCode+"\")'  style='height: 26px;background-color: #1ab394;line-height: normal;border:1 solid #FFCC00;color: #FFFFFF;font-size: 9pt;font-style: normal;font-variant: normal;font-weight: normal;' type='button' class='btn btn-primary' data-toggle='modal'>删除</button>";
		}
		return _html;
	}
	
	guard.saveGuardArea = function(){
		
		$("#guardAreaTitle").empty();
		$("#guardAreaTitle").html("新增区域管家");
		$("#guardSelect").show();
		guard.getNationInfo('nationCodeNew');
		$("#provinceCodeNew").val("");
		$("#cityCodeNew").val("");
		$("#areaCodeNew").val("");
		$("#guardHistory").empty();
		$("#saveGuardArea").show();
		$("#updateGuardArea").hide();
		$("#guardAreaDid").val("");
		$("input[name='reset']").click(); 
	}
	/**
	 * 修改区域管家
	 */
	guard.showGuardArea  = function(fid,flag,empCode){
		
		if(flag==1){
			guard.updateGuardArea(flag, {"fid":fid,"empCode":empCode});
			return false;
		}
		$("#guardAreaTitle").empty();
		$("#guardAreaTitle").html("修改区域管家");
		$("#guardSelect").hide();
		CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"guard/findGaurdAreaByFid", {"fid":fid}, function(data){
			if(data){
				if(data.code == 0){
					$("#guardAreaDid").val(fid);
					var guardArea = data.data.guardArea;
					$("#empNameOne").val(guardArea.empName);
					$("#empPhoneOne").val(guardArea.empPhone);
					$("#empCodeOne").val(guardArea.empCode);
					guard.queryGuardHistory(guardArea.empCode);
					//国家选中
					$("#saveGuardArea").hide();
					$("#updateGuardArea").show();
					guard.setNationInfo('nationCodeNew',null,guardArea.nationCode);
					guard.setNationInfo('provinceCodeNew',guardArea.nationCode,guardArea.provinceCode);
					guard.setNationInfo('cityCodeNew',guardArea.provinceCode,guardArea.cityCode);
					guard.setNationInfo('areaCodeNew',guardArea.cityCode,guardArea.areaCode);
					$('#myModal1').modal('show');
				}else{
					alert(data.msg);
				}
			}
		})
	}
	guard.query = function (){
		$('#listTable').bootstrapTable('selectPage', 1);
	}

	/**
	 * 获取国家 省 市  区的下拉列表
	 * 条件：国家id必须为： nationCodeNew  省id必须为：provinceCodeNew 市id必须为cityCodeNew  区id必须为areaCodeNew
	 */
	guard.getNationInfo = function(id,obj){

		var pcode = $(obj).val();
		if(id === 'nationCodeNew'){
			$("#provinceCodeNew").empty();
			$("#cityCodeNew").empty();
			$("#areaCodeNew").empty();
		}else if(id === 'provinceCodeNew'){
			$("#cityCodeNew").empty();
			$("#areaCodeNew").empty();
		}else if(id === 'cityCodeNew'){
			$("#areaCodeNew").empty();
		}
		if(id!=undefined&&id!=null&&id!=null){
			var _html="";
			$("#"+id).empty();
			CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"config/city/getNationInfo", {"pcode":pcode}, function(nation){
				if(nation){
					_html +="<option value=''>请选择</option>";
					for (var i = 0; i < nation.length; i++) {
						_html +="<option value='"+nation[i].code+"'>"+nation[i].showName+"</option>";
					}
					$("#"+id).html(_html);
				}
			})
		}

	}

	guard.setNationInfo = function(id,pcode,code){

		if(code!=null&&code!=""&&code!=undefined){
			if(id === 'nationCodeNew'){
				$("#provinceCodeNew").empty();
				$("#cityCodeNew").empty();
				$("#areaCodeNew").empty();
			}else if(id === 'provinceCodeNew'){
				$("#cityCodeNew").empty();
				$("#areaCodeNew").empty();
			}else if(id === 'cityCodeNew'){
				$("#areaCodeNew").empty();
			}
			if(id!=undefined&&id!=null&&id!=null){
				var _html="";
				$("#"+id).empty();
				CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"config/city/getNationInfo", {"pcode":pcode}, function(nation){
					if(nation){
						_html +="<option value=''>请选择</option>";
						for (var i = 0; i < nation.length; i++) {
							if(code == nation[i].code){
								_html +="<option value='"+nation[i].code+"' selected >"+nation[i].showName+"</option>";
							}else{
								_html +="<option value='"+nation[i].code+"'>"+nation[i].showName+"</option>";
							}

						}
						$("#"+id).html(_html);
					}
				})
			}
		}

	}

	guard.getIsDel = function(value, row, index){

		if(value==0){
			return "正常";
		}else{
			return "已删除";
		}

	}
	
	//搜索员工
	guard.queryEmp =  function (){
    	$('#empListTable').bootstrapTable('selectPage', 1);
    }

	/*选择员工*/
	guard.getSelectEmployee = function(){
		var selectVar=$('#empListTable').bootstrapTable('getSelections');
		$("#guardHistory").empty();
		if(selectVar&&selectVar.length>0){
			$("#empNameOne").val(selectVar[0].empName);
			$("#empCodeOne").val(selectVar[0].empCode);
			$("#empPhoneOne").val(selectVar[0].empMobile);
			guard.queryGuardHistory(selectVar[0].empCode);
		}
		$("#myModal5").modal("hide");
	}
	//加载管家历史
	guard.queryGuardHistory = function(empCode){

		if(empCode!=null&&empCode!=""&&empCode!=undefined){
			CommonUtils.ajaxPostSubmit(SERVER_CONTEXT+"guard/findGaurdAreaLog", {"empCode":empCode}, function(data){
				$("#guardHistory").empty();
				var _html="";
				if(data.code == 0){
					var list = data.data.listGuardArea;
					if(list){
						var n = list.length;
						for (var i = 0; i < list.length; i++,n--) {
							_html +="<label class='col-sm8 control-label' style='margin-left: 110px;'>"
							_html +=n+".&nbsp;&nbsp;&nbsp;"+CommonUtils.formateDate(list[i].createDate)+"&nbsp;&nbsp;"+list[i].oldNationCode;
							if(list[i].oldProvinceCode != null&&list[i].oldProvinceCode!=""&&list[i].oldProvinceCode!=undefined){
								_html +="&nbsp;&nbsp;"+list[i].oldProvinceCode
							}
							if(list[i].oldCityCode != null&&list[i].oldCityCode!=""&&list[i].oldCityCode!=undefined){
								_html +="&nbsp;&nbsp;"+list[i].oldCityCode
							}
							if(list[i].oldAreaCode != null&&list[i].oldAreaCode!=""&&list[i].oldAreaCode!=undefined){
								_html +="&nbsp;&nbsp;"+list[i].oldAreaCode
							}
						
							_html +="</label></br>";
						}
					}
					
				}
				$("#guardHistory").html(_html);
				$("#guardHistory").show();
			})
		}

	}

	guard.getAddress = function(value, row, index){

		var str =  row.nationCode+"  ";
		if(row.provinceCode!=null&&row.provinceCode!=""&&row.provinceCode!=undefined){
			str =str+row.provinceCode+" ";
		}
		if(row.cityCode!=null&&row.cityCode!=""&&row.cityCode!=undefined){
			str =str+row.cityCode+" ";
		}
		if(row.areaCode!=null&&row.areaCode!=""&&row.areaCode!=undefined){
			str =str+row.areaCode;
		}
		return str;
	}
	
	/**
	 * 打开员工选择窗口
	 */
	guard.showEmpWindow = function(){
		$("#myModal5").modal({backdrop:false})
		
	}
	
	/**
	 * 关闭窗口
	 */
	guard.closeTwoWindow = function(){
		$('#myModal1').modal('hide');
		$('#myModal5').modal('hide');
	}
	guard.init();
	window.Guard = guard;
}(jQuery));