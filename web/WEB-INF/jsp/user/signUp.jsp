<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<style>
    <%@include file="../../css/background.css"%>
    <%@include file="../../css/commonStyle.css"%>
</style>

<html class="blackBcg whiteFont">
<head>
    <title><spring:message code="user.view.signUp.title" text="Sign up"/></title>
</head>
<body>
<center>
    <form:form method="post" modelAttribute="createUserForm" cssClass="form">
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
                <spring:message code="user.phoneNumber" text="Phone number"/>
            </div>
            <div class="formCell">
                <form:input path="phoneNumber"/>
            </div>
            <div class="formCell">
                <form:errors path="phoneNumber" cssClass="error"/>
            </div>
        </div>
        <div class="formRow">
            <div class="formCell">
                <spring:message code="user.email" text="Email"/>
            </div>
            <div class="formCell">
                <form:input path="email"/>
            </div>
            <div class="formCell">
                <form:errors path="email" cssClass="error"/>
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
            </div>
        </div>
        <div class="formRow">
            <div class="formCell">
                <spring:message code="user.passwordMatch" text="Repeat password"/>
            </div>
            <div class="formCell">
                <form:password path="passwordMatch"/>
            </div>
            <div class="formCell">
                <form:errors path="passwordMatch" cssClass="error"/>
            </div>
        </div>
        <div class="formRow">
            <div class="formCell">
                <form action="${pageContext.request.contextPath}/users/sign-up" method="post">
                    <input type="submit"
                           value="<spring:message code="user.view.signUp.signUpButton" text="Sign up!"/>"
                           class="standardButton"/>
                </form>
            </div>
        </div>
    </form:form>
    <div class="formCell" style="margin-top: 1vw;">
        <form action="${pageContext.request.contextPath}/users/sign-in" method="get">
            <input type="submit"
                   value="<spring:message code="user.view.signUp.toSignInButton" text="Sign in"/>"
                   class="standardButton"/>
        </form>
    </div>
</center>
</body>
</html>