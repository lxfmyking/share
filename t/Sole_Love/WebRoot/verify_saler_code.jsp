<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'verify_saler_code.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="jquery-1.7.2.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#btn_login").click(function() {
			var url = 'verify_saler_code.action';
			var params = {
				verify : $("#verify").val(),
			}
			$.post(url, //服务器要接受的url  
			params,
			function cbf(data) { //服务器返回后执行的函数 参数 data保存的就是服务器发送到客户端的数据  
				data = data.replace("[", "").replace("]", "");
				data = data.replace("[", "").replace("]", "");
				alert(data);
				var member = eval("(" + data + ")"); //包数据解析为json 格式    
				$('#result').html(":"+member.message);
			}, 'json' //数据传递的类型  json  
			);
		});
	});
</script>
  </head>
  
  <body>
  优惠代码：<input type="text" id="params" name="params"><br />
   <input type="button" id="btn_login" value="测试" />
	<span id="result"></span>
  </body>
</html>
