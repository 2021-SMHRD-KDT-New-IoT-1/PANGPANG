<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<style>
	video {	
		align-items : center;
		width: 100%;	
		max-width: 640px;
		height: auto;	
	}
	
	body {
		background-image: url("images/main13.jpg");
	}
	h2{
		text-align : center;
		color: white;
	}
	
</style>
</head>
<body>
	<%-- <video src="/app_server_test/Videos/2021-12-27-15-47-30.mp4" controls></video>
	<video src="/app_server_test/Videos/2021-12-27-15-48-17.mp4" controls></video>--%>
	<%-- http://{서버주소}/app_server_test/videos/{파일명} --%>
	
	<%
		ArrayList<String> video_list = (ArrayList<String>)request.getAttribute("video");
		for(int i = 0; i< video_list.size();i++ ){%>
			<video src="/app_server_test/Videos/<%=video_list.get(i)%>" controls></video>
				<h2><%=video_list.get(i)%></h2>
			<hr style="border: solid 1px grey;">
		<%} %>
	
</body>
</html>
