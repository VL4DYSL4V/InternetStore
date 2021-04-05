<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    <%@include file="../../css/background.css"%>
    <%@include file="../../css/tableStyle.css"%>
    <%@include file="../../css/commonStyle.css"%>
</style>
<html class="whiteFont blackBcg">
<head>
    <title><spring:message code="country.view.country.title" text="Country"/></title>
</head>
<body>
<center>
    <table>
        <tr>
            <td colspan="2">
                <c:out value="${countryForm.countryName}"/>
            </td>
        </tr>
        <tr>
            <td>
                <form action="${pageContext.request.contextPath}/countries/${countryForm.id}/edit"
                      method="get">
                    <input type="submit"
                           value="<spring:message code="country.view.edit.editButton" text="Update"/>"
                           class="standardButton"/>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/countries/${countryForm.id}/delete"
                      method="post">
                    <input type="submit"
                           value="<spring:message code="country.view.delete.deleteButton" text="Delete"/>"
                           class="standardButton"/>
                </form>
            </td>
        </tr>
    </table>
</center>
</div>
</body>
</html>