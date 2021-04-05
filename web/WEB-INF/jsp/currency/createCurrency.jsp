<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<style>
    <%@include file="../../css/background.css"%>
    <%@include file="../../css/tableStyle.css"%>
    <%@include file="../../css/commonStyle.css"%>
</style>

<html class="blackBcg whiteFont">
<head>
    <title><spring:message code="currency.view.create.title" text="New currency"/></title>
</head>
<body>
<center>
    <form:form method="post" modelAttribute="creationCurrencyForm" cssClass="form">
        <div class="formRow">
            <div class="formCell">
                <spring:message code="currency.name" text="Name"/>
            </div>
            <div class="formCell">
                <form:input path="currencyName"/>
            </div>
            <div class="formCell">
                <form:errors path="currencyName" cssClass="error"/>
            </div>
        </div>
        <div class="formRow">
            <div class="formCell">
                <input type="submit"
                       value="<spring:message code="currency.view.create.createButton" text="Add currency"/>"
                       class="standardButton"/>
            </div>
        </div>
    </form:form>
</center>
</div>
</body>
</html>