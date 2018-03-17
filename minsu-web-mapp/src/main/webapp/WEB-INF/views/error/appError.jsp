<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
  <form action="https://www.baidu.com/" method="post" name="appForm" id="appForm">
     <input type="hidden" name="aa" value="test">
  </form>
<script>
	$(function(){
	    $("#appForm").submit();
	})
</script>
</body>
</html>