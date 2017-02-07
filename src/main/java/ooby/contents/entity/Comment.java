package ooby.contents.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;

import ooby.contents.view.CommentView;

@Entity
public class Comment {
	@Id
	@GeneratedValue
	@JsonView({CommentView.Base.class})
	private Long id;
	@ManyToOne
	@JsonView({CommentView.Base.class})
	private User user;
	@ManyToOne
	private Content content;
	@JsonView({CommentView.Base.class})
	private String text;
	@JsonView({CommentView.Base.class})
	private Date dateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
}
