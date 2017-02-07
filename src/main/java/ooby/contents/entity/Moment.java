package ooby.contents.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Moment {
	@Id
	@GeneratedValue
	private Long id;
	
	private String type;
	private Date dateTime=new Date();
	@ManyToOne
	private User fromUser;
	@ManyToOne
	private User toUser;
	@ManyToOne
	private ContentGroup contentGroup;
	@ManyToOne
	private Content content;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public User getToUser() {
		return toUser;
	}
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
	public ContentGroup getContentGroup() {
		return contentGroup;
	}
	public void setContentGroup(ContentGroup contentGroup) {
		this.contentGroup = contentGroup;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
}
