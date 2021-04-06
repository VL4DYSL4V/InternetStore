<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<style>
    <%@include file="../../css/background.css"%>
    <%@include file="../../css/commonStyle.css"%>
</style>

<html class="blackBcg whiteFont">
<head>
    <title><spring:message code="user.view.signIn.title" text="Sign in!"/></title>
</head>
<%
    LocalTime timeBeforeRetry = (LocalTime) request.getSession().getAttribute("timeBeforeRetry");
    String timeString = "";
    if (timeBeforeRetry != null) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeString = dateTimeFormatter.format(timeBeforeRetry);
    }
%>
<body>
<center>
    <form:form method="post" modelAttribute="signInForm" cssClass="form">
        <div class="formRow">
            <div class="formCell">
                <spring:message code="user.name" text="Name"/>
            </div>
            <div class="formCell">
                <form:input path="name"/>
            </div>
            <div class="formCell">
                <form:errors path="name" cssClass="error"/>
            </div>
        </div>
        <div class="formRow">
            <div class="formCell">
                <spring:message code="user.password" text="Password"/>
            </div>
            <div class="formCell">
                <form:password path="password"/>
            </div>
            <div class="formCell">
                <form:errors path="password" cssClass="error"/>
                <div class="error"><%=timeString%>
                </div>
            </div>
        </div>
        <div class="formRow">
            <div class="formCell">
                <form action="${pageContext.request.contextPath}/users/sign-in" method="post">
                    <input type="submit"
                           value="<spring:message code="user.view.signIn.signInButton" text="Sign in!"/>"
                           class="standardButton"/>
                </form>
            </div>
        </div>
    </form:form>
    <div class="formCell" style="margin-top: 1vw;">
        <form action="${pageContext.request.contextPath}/users/sign-up" method="get">
            <input type="submit"
                   value="<spring:message code="user.view.signIn.toSignUpButton" text="Sign up"/>"
                   class="standardButton"/>
        </form>
    </div>
</center>
</body>
</html>