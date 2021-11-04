<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<%@ page isErrorPage="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>
<body>

	<h1><c:out value="${event.name}"/>!</h1>
    
     
     <h1>Edit the event</h1>
    
    <p><form:errors path="event.*"/></p>
    <p><c:out value="${eventErrors}"/></p>
    
    <form:form method="POST" action="/events/${event.id}/edit" modelAttribute="event">
	<input type="hidden" name="_method" value="put">
        <p>
            <form:label path="name">Name:</form:label>
            <form:input path="name"/>
        </p>
        <p>
            <form:label path="date">Date:</form:label>
            <form:input path="date" type="date"/>
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
        <form:hidden path="user" value="${event.user.id}"/> 
        
        <input type="submit" value="Save Event"/>
    </form:form>

    
</body>
</html>