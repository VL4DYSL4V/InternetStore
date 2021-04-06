<%@ page import="entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
Hello, <%=((User)request.getSession().getAttribute("user")).getName()%>
</body>
</html>