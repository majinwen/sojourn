<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/commonUtils.js${VERSION}"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  CommonUtils.ajaxPostSubmit("../message/listMsgBaseByTime", "", listMsgBase);
	  
     $("#tenant").click(function(){
		  
		  var params={
				  msgContent:$("#msgContent").val(),
				  msgSentType:$(this).attr("msgSentType"),
				  msgHouseFid: $("#msgHouseFid").val()
		  }
		  CommonUtils.ajaxPostSubmit("../message/saveMsgBase", params, tenantSave);
		  
	  });
     $("#landlord").click(function(){
    	  var params={
				  msgContent:$("#msgContent").val(),
				  msgSentType:$(this).attr("msgSentType"),
				  msgHouseFid: $("#msgHouseFid").val()
		  }
		  CommonUtils.ajaxPostSubmit("../message/saveMsgBase", params, landlordSave);
	  });
  })
  //封装数据
  function listMsgBase(data){
	  if(data){
		  var lisBaseEntities = data;
		  $("#comment").empty();
		  var _html="";
		  for (var i = 0; i < lisBaseEntities.length; i++) {
			  if(lisBaseEntities[i].msgSenderType == 1){
				  _html +="<div style='width: 300px;height: 30px;'>房东："+lisBaseEntities[i].msgContent+"</div>";
			  }else{
				  _html +="<div style='width: 300px;height: 30px;'>房客："+lisBaseEntities[i].msgContent+"</div>";
			  }
		}
		  $("#comment").html(_html);
	  }
  }
  function myrefresh() 
  { 
	 CommonUtils.ajaxPostSubmit("../message/listMsgBaseByTime", "", listMsgBase);
  } 
  setInterval(myrefresh,10000);
  
  function tenantSave(data){
  }
  
  function landlordSave(data){
  }
</script>

<style>
.center{ 
MARGIN-RIGHT: auto;
MARGIN-LEFT: auto;
height:500px;
background:#F00;
width:500px;
vertical-align:middle;
line-height:200px;
}
.center1{
  margin-top: 100px;
}
.button{width:100px;height:18px;line-height:18px;border:0;margin:4px 0 0 10px;} 
</style>
</head>
<body>
   <div id="center" class="center" >
     <div id="comment">
     </div>
     
   </div>
   <div id="center1"  class="center center1">
     <form action="">
        <textarea rows="10" cols="70"  id="msgContent">
        
        </textarea>
        <input type="hidden" id ="msgHouseFid" value="8a9e9c8b541e32c001541e32c0150000"/>
         <button id="tenant"  name=""  msgSentType ="2" class="button" value="">房客提交</button>
         <button id="landlord"  name=""  msgSentType ="1" class="button" value="">房东提交</button>
     </form>
   </div>
</body>
</html>