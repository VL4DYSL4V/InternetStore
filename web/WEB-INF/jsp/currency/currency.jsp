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
    <title><spring:message code="currency.view.currency.title" text="Currency"/></title>
</head>
<body>
<center>
    <table>
        <tr>
            <td colspan="2">
                <c:out value="${currencyForm.currencyName}"/>
            </td>
        </tr>
        <tr>
            <td>
                <form action="${pageContext.request.contextPath}/currencies/${currencyForm.id}/edit"
                      method="get">
                    <input type="submit"
                           value="<spring:message code="currency.view.edit.editButton" text="Update"/>"
                           class="standardButton"/>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/currencies/${currencyForm.id}/delete"
                      method="post">
                    <input type="submit"
                           value="<spring:message code="currency.view.delete.deleteButton" text="Delete"/>"
                           class="standardButton"/>
                </form>
            </td>
        </tr>
    </table>
</center>
</body>
</html>