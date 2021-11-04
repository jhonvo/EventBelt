package com.codingdojo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.models.Comment;
import com.codingdojo.models.Event;

@Repository
public interface CommentRepository extends CrudRepository <Comment, Long> {
	
	@Query ("SELECT c FROM Comment c WHERE event = ?1")
	List <Comment> getCommentsOfEvent (Event event);

}
