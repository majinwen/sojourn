<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8"%>
<span style="color:red">错误码	： </span><br/><%=request.getAttribute("javax.servlet.error.status_code")%><br/>
<span style="color:red">信息		： </span><br/><%=request.getAttribute("javax.servlet.error.message")%><br/>
<span style="color:red">异常		： </span><br/><%=request.getAttribute("javax.servlet.error.exception_type")%><br/>

<span>具体信息请查看日志</span>