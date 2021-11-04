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

	<h1> <c:out value="${event.name}"/>!</h1>
    <a href="/events">Events</a>
    <p>Host: <c:out value="${event.user.firstname}"/> <c:out value="${event.user.lastname}"/></p>
    <p>Date: <c:out value="${event.getDateFormatted()}"/></p>
    <p>Location: <c:out value="${event.location}"/>, <c:out value="${event.state}"/></p>
    <p>People who are attending this event: <c:out value="${event.eventParticipants.size()}"/></p>
    
    
   <table>
    	<thead>
    		<tr>
    			<th>Name</th>
    			<th>Location</th>
    		</tr>
    	</thead>
    	<tbody>
    		<c:forEach var="user" items="${event.eventParticipants}">
    			<tr>
    				<td><c:out value="${user.firstname}"/> <c:out value="${user.lastname}"/></td>
    				<td><c:out value="${user.location}"/></td>
    			<tr>
    		</c:forEach>
    	</tbody>
    	
    </table>
    
       <h1>Message Wall:</h1>
      
      <div style="boder">
      	<c:forEach var="comm" items="${comments}">
      	<p><c:out value="${comm.user.firstname}"/> says: <c:out value="${comm.comment}"/></p>
      	</c:forEach>
      </div>
     
     
    
    <p><form:errors path="newcomment.*"/></p>
    <p><c:out value="${eventErrors}"/></p>
    
    <form:form method="POST" action="/events/comment" modelAttribute="newcomment">
		<form:hidden path="user" value="${user_id}"/>
		<form:hidden path="event" value="${event.id}"/>
        <p>
            <form:label path="comment">Add a comment:</form:label>
            <form:textarea path="comment"/>
        </p>     
        <input type="submit" value="Save comment"/>
    </form:form> 

    
</body>
</html>