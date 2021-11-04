package com.codingdojo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.models.Event;
import com.codingdojo.repositories.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepo;
	
	public List <Event> getEventsUserState (String state){
		return eventRepo.getEventsUserState(state);
	}
	
	public List <Event> getEventsOtherState (String state){
		return eventRepo.getEventsOtherState(state);
	}
	
	public List <Object[]> joinEventUsersState(String state){
		return eventRepo.joinEventUsersState(state);
	}
	
	public List <Object[]> joinEventOtherState(String state){
		return eventRepo.joinEventOtherState(state);
	}
	
	
	public Event saveEvent(Event event) {
		return eventRepo.save(event);
	}
	
	public Event updateEvent(Event event) {
		return eventRepo.save(event);
	}
	
	public Event getEvent(Long id) {
		Optional<Event> event = eventRepo.findById(id);
		if(event.isPresent()) {
			return event.get();
		} else {
			return null;
		}
	}
	
	public void deleteEvent(Long id) {
		eventRepo.deleteById(id);
		
	}


}
