<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    <%@include file="../../css/background.css"%>
    <%@include file="../../css/tableStyle.css"%>
    <%@include file="../../css/commonStyle.css"%>
</style>

<html class="blackBcg">
<head>
    <title><spring:message code="country.view.countries.title" text="Countries"/></title>
</head>
<body>
<div>
    <table class="centered whiteFont">
        <tr>
            <th><spring:message code="country.name" text="Name"/></th>
        </tr>
        <c:forEach items="${countries}" var="country">
            <tr>
                <td>
                    <form action="${pageContext.request.contextPath}/countries/${country.id}"
                          method="get" class="centered">
                        <input type="submit" value="${country.countryName}" class="standardButton"
                               style="width: 100%"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <form action="${pageContext.request.contextPath}/countries/new" method="get" class="centered">
        <input type="submit"
               value="<spring:message code="country.view.create.createButton" text="Add country"/>"
               class="standardButton"/>
    </form>
</div>
</body>
</html>