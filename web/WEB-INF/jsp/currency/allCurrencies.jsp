<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    <%@include file="../../css/background.css"%>
    <%@include file="../../css/tableStyle.css"%>
    <%@include file="../../css/commonStyle.css"%>
</style>

<html class="blackBcg whiteFont">
<head>
    <title><spring:message code="currency.view.currencies.title" text="Currencies"/></title>
</head>
<body>
<table class="centered whiteFont">
    <tr>
        <th><spring:message code="currency.view.currencies.title" text="Currencies"/></th>
    </tr>
    <c:forEach items="${currencies}" var="currency">
        <tr>
            <td>
                <form action="${pageContext.request.contextPath}/currencies/${currency.id}"
                      method="get" class="centered">
                    <input type="submit" value="${currency.currencyName}"
                           class="standardButton"
                           style="width: 100%"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/currencies/new" method="get" class="centered">
    <input type="submit"
           value="<spring:message code="currency.view.create.createButton" text="Add currency"/>"
           class="standardButton"/>
</form>
</body>
</html>