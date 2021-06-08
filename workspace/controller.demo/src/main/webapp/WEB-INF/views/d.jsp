<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>This is D</h1>
<%
	int num = 10;
%>
<h3>num 의 값은 <%= num %></h3> <!-- 동적인 파일 응답이 가능한 페이지 - jsp -->
${10 + 1}
${num}
</body>
</html>