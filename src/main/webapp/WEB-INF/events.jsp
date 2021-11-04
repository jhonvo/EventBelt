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

	<h1>Welcome <c:out value="${user.firstname}"/>!</h1>
    <a href="/logout">Logout</a>
    <p>Here are some of the events in your state:</p>
    <table>
    	<thead>
    		<tr>
    			<th>Name</th>
    			<th>Date</th>
    			<th>Location</th>
    			<th>Host</th>
    			<th>Action/Status</th>
    		</tr>
    	</thead>
    	<tbody>
    		<c:forEach var="event" items="${eventsState}">
    			<tr>
    				<td><a href="/events/${event.id}"><c:out value="${event.name}"/></a></td>
    				<td><c:out value="${event.getDateFormatted()}"/></td>
    				<td><c:out value="${event.location}"/></td>
    				<td><c:out value="${event.user.firstname}"/></td>
    				<td>
    					<c:if test="${event.user == user}">
    						<a href="/events/${event.id}/edit">Edit</a> | <a href="/events/${event.id}/delete">Delete</a>
    					</c:if>
    					<c:if test="${event.user != user}">
    						<c:if test="${! user.getEventsAttending().contains(event)}">
    							<a href="/events/${event.id}/join">Join</a>
    						</c:if>
    						<c:if test="${user.getEventsAttending().contains(event)}">
    							<a href="/events/${event.id}/cancel">Cancel Joining</a> 
    						</c:if>		 
    						
    						
    						<c:if test="${session != null}"> <a>Logout</a></c:if>
    					</c:if>
    				</td>
    			<tr>
    		</c:forEach>
    	</tbody>
    	
    </table>
    
     <p>Here are some of the events in your state:</p>
     
     <table>
    	<thead>
    		<tr>
    			<th>Name</th>
    			<th>Date</th>
    			<th>Location</th>
    			<th>State</th>
    			<th>Host</th>
    			<th>Action/Status</th>
    		</tr>
    	</thead>
    	<tbody>
    		<c:forEach var="event" items="${eventsOther}">
    			<tr>
    				<td><a href="/events/${event.id}"><c:out value="${event.name}"/></a></td>
    				<td><c:out value="${event.getDateFormatted()}"/></td>
    				<td><c:out value="${event.location}"/></td>
    				<td><c:out value="${event.state}"/></td>
    				<td><c:out value="${event.user.firstname}"/></td>
    				<td>
    					<c:if test="${event.user == user}">
    						<a href="/events/${event.id}/edit">Edit</a> | <a href="/events/${event.id}/delete">Delete</a>
    					</c:if>
    					<c:if test="${event.user != user}">
    						<c:if test="${! user.getEventsAttending().contains(event)}">
    							<a href="/events/${event.id}/join">Join</a>
    						</c:if>
    						<c:if test="${user.getEventsAttending().contains(event)}">
    							<a href="/events/${event.id}/cancel">Cancel Joining</a> 
    						</c:if>		 
    					</c:if>
    				</td>
    			<tr>
    		</c:forEach>
    	</tbody>
    	
    </table>
     
     <h1>Create a new event</h1>
    
    <p><form:errors path="event.*"/></p>
    <p><c:out value="${eventErrors}"/></p>
    
    <form:form method="POST" action="/events" modelAttribute="event">

        <p>
            <form:label path="name">Name:</form:label>
            <form:input path="name"/>
        </p>
        <p>
            <form:label path="date">Date:</form:label>
            <form:input path="date" type="date"/>
            <p><c:out value="${dateError}"/></p>
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
        <form:hidden path="user" value="${user.id}"/>
        
        <input type="submit" value="Save Event"/>
    </form:form>

    
</body>
</html>