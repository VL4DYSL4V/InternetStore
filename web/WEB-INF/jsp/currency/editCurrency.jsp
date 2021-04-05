<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<style>
    <%@include file="../../css/commonStyle.css"%>
    <%@include file="../../css/background.css"%>
</style>

<html class="blackBcg whiteFont">
<head>
    <title><spring:message code="currency.view.edit.title" text="Edit currency"/></title>
</head>
<body>
<center>
    <form:form method="post" modelAttribute="currencyForm" cssClass="form centered">
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
                       value="<spring:message code="currency.view.edit.editButton" text="Update"/>"
                       class="standardButton">
            </div>
        </div>
    </form:form>
</center>
</body>
</html>