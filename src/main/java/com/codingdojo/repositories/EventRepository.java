package com.codingdojo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.models.Event;

@Repository
public interface EventRepository extends CrudRepository <Event, Long> {
	
	@Query ("SELECT e FROM Event e WHERE state = ?1")
	List <Event> getEventsUserState (String state);
	
	@Query ("SELECT e FROM Event e WHERE state != ?1")
	List <Event> getEventsOtherState (String state);
	
	@Query ("SELECT e, u FROM Event e JOIN e.user u WHERE e.state = ?1")
	List <Object[]> joinEventUsersState(String state);
	
	@Query ("SELECT e, u FROM Event e JOIN e.user u WHERE e.state != ?1")
	List <Object[]> joinEventOtherState(String state);

}
