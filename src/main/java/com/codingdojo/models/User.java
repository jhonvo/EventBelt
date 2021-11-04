package com.codingdojo.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table (name="users")
public class User {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size (min=3, max=45, message="First Name should be more than 3 characters.")
	private String firstname;
	
	@NotNull
	@Size (min=3, max=45, message="Last Name should be more than 3 characters.")
	private String lastname;
	
	@NotEmpty(message="Email is required!")
	@Email (message="Please provide a valid email address.")
	private String email;
	
	@NotNull
	@Size (min=3, max=45, message="Location should be more than 3 characters.")
	private String location;
	
	@NotNull
	private String state;
	
	@NotEmpty(message="Password is required!")
	@Size (min=6, message="Password should be more than 6 characters.")
	private String password;
	
	@Transient
    private String passwordConfirmation;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
	private List<Event> events;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
	private List<Comment> comments;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "event_users", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "event_id")
	)
	private List<Event> eventsAttending;
	
	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    @PrePersist // Before the item is created.
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate // Before the item is updated.
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    
	// Constructor	
	
	public User () {}
	
	// Getters and Setters	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Event> getEventsAttending() {
		return eventsAttending;
	}
	public void setEventsAttending(List<Event> eventsAttending) {
		this.eventsAttending = eventsAttending;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
	
	
}
