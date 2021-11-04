package com.codingdojo.models;

import java.text.SimpleDateFormat;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table (name="events")
public class Event {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size (min=6, max=45, message="Name should be more than 6 characters.")
	private String name;
	
	@NotNull
	@Size (min=3, max=45, message="Location should be more than 3 characters.")
	private String location;
	
	@NotNull
	private String state;
	
	@NotNull
	@Future (message="Date should be in the future")
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date; 
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="host_id")
	private User user;
	
	@OneToMany(mappedBy="event", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Comment> comments;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "event_users", 
        joinColumns = @JoinColumn(name = "event_id"), 
        inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<User> eventParticipants;
	
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
    
    public Event () {}
    
    // Getters and Setters
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<User> getEventParticipants() {
		return eventParticipants;
	}
	public void setEventParticipants(List<User> eventParticipants) {
		this.eventParticipants = eventParticipants;
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
    
	public String getDateFormatted() {
		SimpleDateFormat fm = new SimpleDateFormat("MMMM dd, YYYY");
		return fm.format(this.date);
	}
		
    
}
