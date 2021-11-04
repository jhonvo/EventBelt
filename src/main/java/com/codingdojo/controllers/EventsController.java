package com.codingdojo.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.models.Comment;
import com.codingdojo.models.Event;
import com.codingdojo.models.User;
import com.codingdojo.services.CommentService;
import com.codingdojo.services.EventService;
import com.codingdojo.services.UserService;

@Controller

public class EventsController {
	
	@Autowired
	private UserService uServ;
	
	@Autowired
	private EventService eServ;
	
	@Autowired
	private CommentService cServ;
	
	private static String[] stateList = {"FL","CA","NY"};
	
	@RequestMapping ("/")
	public String index(HttpSession session,
						RedirectAttributes redirectAttributes,
						Model model,
						@ModelAttribute("userRegister") User user) {
		if (session.getAttribute("user_id") ==  null) {
			model.addAttribute("states", stateList);
			return "index.jsp";
		} else {
			return "redirect:/events";
		}
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("userRegister") User user, 
			 					BindingResult result, 
			 					HttpSession session,
			 					RedirectAttributes redirectAttributes) {
		 if (result.hasErrors()) {
			 if (user.getFirstname().length() < 5 ) {
				 redirectAttributes.addFlashAttribute("registerFN", "Please add a valid First Name.");
			 } 
			 if (user.getLastname().length() < 5 ){
				 redirectAttributes.addFlashAttribute("registerLN", "Please add a valid Last Name.");
			 }
			 if (! user.getPassword().equals(user.getPasswordConfirmation())) {
				 redirectAttributes.addFlashAttribute("passwordError", "Password and password confirmation do not match");
			 }
			 
//			 redirectAttributes.addFlashAttribute("registerErrors", "Please review the information of the form.");
				return "redirect:/";
//				return "index.jsp";
			}
		 else if (user.getPassword().equals(user.getPasswordConfirmation())) {			 
			 User newUser = uServ.registerUser(user);
			 session.setAttribute("user_id", newUser.getId());
			 return "redirect:/events";
		 }
		 else {
			 redirectAttributes.addFlashAttribute("passwordError", "Password and password confirmation do not match");
			 return "redirect:/register";
		 }
		 
		 // if result has errors, return the registration page (don't worry about validations just now)
	     // else, save the user in the database, save the user id in session, and redirect them to the /home route
	 }
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginUser(@RequestParam("email") String email, 
			 				@RequestParam("password") String password, 
			 				RedirectAttributes redirectAttributes, 
			 				Model model, 
			 				HttpSession session) {
	     User user = uServ.findByEmail(email);
	     if( user == null ) {
				redirectAttributes.addFlashAttribute("loginError", "Wrong credentials");
				return "redirect:/";
			}
	     else if (uServ.authenticateUser(email, password)) {
	    	 session.setAttribute("user_id", user.getId());
	    	 return "redirect:/events";
	     }
	     else {
	    	 redirectAttributes.addFlashAttribute("loginError", "Incorrect password");
	    	 return "redirect:/";
	     }
		 // if the user is authenticated, save their user id in session
	     // else, add error messages and return the login page
	 }
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
	     session.removeAttribute("user_id");
		 return "redirect:/";
		 // invalidate session
	     // redirect to login page
	 }
	
	@RequestMapping("/events")
	public String showEvents(Model model,
							HttpSession session,
							@ModelAttribute("event") Event event,
							RedirectAttributes redirectAttributes) {
		if (session.getAttribute("user_id") ==  null) {
			return "redirect:/";
		} else {
			Long id = (Long) session.getAttribute("user_id");
			User current = uServ.findUserById(id);
			String state = current.getState();
			model.addAttribute("eventsState", eServ.getEventsUserState(state));
			model.addAttribute("eventsOther", eServ.getEventsOtherState(state));
			model.addAttribute("user", current);
			model.addAttribute("states", stateList);
			return "events.jsp";
		}		
	}
	
	@RequestMapping(value="/events", method=RequestMethod.POST)
	public String createEvent(@Valid @ModelAttribute("event") Event event,
							BindingResult result,
							RedirectAttributes redirectAttributes){

		if (result.hasErrors()) {
			
			if (event.getDate().compareTo(new Date()) < 0 ) {
				redirectAttributes.addFlashAttribute("dateError", "Please select a future date.");
			}
			redirectAttributes.addFlashAttribute("eventErrors", "Please review the information of the form,");
			return "redirect:/events";
//			return "events.jsp";
		} else {
			System.out.println("2");
			eServ.saveEvent(event);
			return "redirect:/events";
		}		
	}
	
	@RequestMapping("/events/{id}")
	public String showEvent(Model model,
							HttpSession session,
							@PathVariable ("id") Long id,
							@ModelAttribute("newcomment") Comment comment,
							RedirectAttributes redirectAttributes) {
		if (session.getAttribute("user_id") ==  null) {
			return "redirect:/";
		} else {
		Event current = eServ.getEvent(id);
		List<Comment> comments = cServ.getCommentsOfEvent(current);
		model.addAttribute("event", current);
		model.addAttribute("comments", comments);
		model.addAttribute("user_id", session.getAttribute("user_id"));
		return "eventsShow.jsp";
		}
	}
	
	@RequestMapping(value="/events/comment", method=RequestMethod.POST)
	public String saveNewComment(@Valid @ModelAttribute("newcomment") Comment comment,
							BindingResult result,
							RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("eventErrors", "Please review the information of the form,");
			return "redirect:/events/"+comment.getEvent().getId();
		} else {
			cServ.saveComment(comment);
			return "redirect:/events/"+comment.getEvent().getId();
		}
	}
	
	
	@RequestMapping("/events/{id}/edit")
	public String editEvent(Model model,
							HttpSession session,
							@PathVariable ("id") Long id,
							@ModelAttribute("event") Event event) {
		Event current = eServ.getEvent(id);
		if (session.getAttribute("user_id").equals(current.getUser().getId())) {
			model.addAttribute("event", current);
			model.addAttribute("states", stateList);
			return "eventsedit.jsp";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/events/{id}/edit", method=RequestMethod.PUT)
	public String updateEvent(@PathVariable ("id") Long id,
							@Valid @ModelAttribute("event") Event event,
							BindingResult result) {
		if (result.hasErrors()) {
			return "eventsedit.jsp";
		} else {
			eServ.saveEvent(event);
			return "redirect:/events/"+id;
		}
	}
	
	@RequestMapping("/events/{id}/delete")
	public String deleteEvent(@PathVariable ("id") Long id) {
		eServ.deleteEvent(id);
		return "redirect:/events";
	}
	
	@RequestMapping("/events/{id}/join")
	public String joinEvent(@PathVariable ("id") Long id,
							HttpSession session) {
		Event current = eServ.getEvent(id);
		User target = uServ.findUserById((Long) session.getAttribute("user_id"));
		List<Event> attending = target.getEventsAttending();
		attending.add(current);
		target.setEventsAttending(attending);
		uServ.saveUser(target);
		return "redirect:/events";
	}
	
	@RequestMapping("/events/{id}/cancel")
	public String canceJoiningEvent(@PathVariable ("id") Long id,
							HttpSession session) {
		Event current = eServ.getEvent(id);
		User target = uServ.findUserById((Long) session.getAttribute("user_id"));
		List<Event> attending = target.getEventsAttending();
		attending.remove(current);
		target.setEventsAttending(attending);
		uServ.saveUser(target);
		return "redirect:/events";
	}
	
	
}
