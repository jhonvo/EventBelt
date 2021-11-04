package com.codingdojo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.models.Comment;
import com.codingdojo.models.Event;
import com.codingdojo.repositories.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository comRepo;
	
	public List <Comment> getCommentsOfEvent (Event event){
		return comRepo.getCommentsOfEvent(event);
	}
	
	public Comment saveComment(Comment comment) {
		return comRepo.save(comment);
	}
	
}
