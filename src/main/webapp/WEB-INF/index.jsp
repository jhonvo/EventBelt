<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>
<body>

	<h1>Register!</h1>
    
    <p><form:errors path="userRegister.*"/></p>
    <p><c:out value="${registerErrors}"/></p>
    
    <form:form method="POST" action="/register" modelAttribute="userRegister">
        <p>
            <form:label path="firstname">First Name:</form:label>
            <form:input path="firstname"/>
            <p><c:out value="${registerFN}"/></p>
        </p>
        <p>
            <form:label path="lastname">Last Name:</form:label>
            <form:input path="lastname"/>
            <p><c:out value="${registerLN}"/></p>
        </p>
       
        <p>
            <form:label path="email">Email:</form:label>
            <form:input type="email" path="email"/>
        </p>
        
        <p>
            <form:label path="location">Location:</form:label>
            <form:input path="location"/>
        </p>
        
        <p>
            <form:label path="state">State:</form:label>
            <form:select path="state">
            	<c:forEach var="state" items="${states}">
            		<form:option value="${state}"><c:out value="${state}"/></form:option>
            	</c:forEach>
            </form:select>
        </p>
        
        <p>
            <form:label path="password">Password:</form:label>
            <form:password path="password"/>
        </p>
        <p>
            <form:label path="passwordConfirmation">Password Confirmation:</form:label>
            <form:password path="passwordConfirmation"/>
            <p><c:out value="${passwordError}"/></p>
        </p>
        <input type="submit" value="Register!"/>
    </form:form>
    
    <h1>Login</h1>
    <p><c:out value="${error}" /></p>
    <form method="post" action="/login">
        <p>
            <label for="email">Email</label>
            <input type="text" id="email" name="email"/>
        </p>
        <p>
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
        </p>
        <input type="submit" value="Login!"/>
    </form>    
       <div>
    	<c:out value="${loginError}" />
    </div>
</body>
</html>