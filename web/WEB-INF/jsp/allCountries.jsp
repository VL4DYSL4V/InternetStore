<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    <%@include file="../css/background.css"%>
    <%@include file="../css/tableStyle.css"%>
    <%@include file="../css/commonStyle.css"%>
</style>

<html class="blackBcg">
<head>
    <title><spring:message code="countries.title" text="Countries"/></title>
</head>
<body>
<div>
    <table class="centered whiteFont">
        <tr>
            <th>Name</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${countries}" var="country">
            <tr>
                <td>${country.countryName}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/countries/${country.id}/edit"
                          method="get">
                        <input type="submit" value="Edit" class="standardButton"/>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/countries/${country.id}/delete"
                          method="post">
                        <input type="submit" value="Delete" class="standardButton"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <form action="${pageContext.request.contextPath}/countries/new" method="get" class="centered">
        <input type="submit" value="Add country" class="standardButton"/>
    </form>
</div>
</body>
</html>