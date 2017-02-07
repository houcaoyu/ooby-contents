package ooby.contents.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ooby.contents.entity.Comment;

public interface CommentDao extends CrudRepository<Comment, Long> {
	List<Comment> findByContentContentGroupUserIdAndDateTimeAfter(Long userId,Date dateTime);
}
