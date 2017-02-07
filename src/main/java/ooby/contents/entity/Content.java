package ooby.contents.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

import ooby.contents.view.ContentView;

@Entity
public class Content {
	@Id
	@GeneratedValue
	@JsonView({ ContentView.Base.class })
	private Long id;
	@JsonView({ ContentView.Base.class })
	private String title;
	@JsonView({ ContentView.Detail.class })
	private String text;
	@JsonView({ ContentView.Base.class })
	private Long pv=0l;
	
	@OneToOne
	private ResourceRepository image;
	

	
	public ResourceRepository getImage() {
		return image;
	}

	public void setImage(ResourceRepository image) {
		this.image = image;
	}
	@JsonView({ ContentView.Detail.class })
	@ManyToOne
	private ContentGroup contentGroup;

	@ManyToMany(mappedBy = "likes")
	private List<User> fans;
	@JsonView({ ContentView.Base.class })
	public int getFanSize(){
		return fans==null?0:fans.size();
	}
	@ManyToMany(mappedBy = "collections")
	private List<User> collectedUsers;
	@ManyToMany(mappedBy = "histories")
	private List<User> readers;
	
	@JsonView({ ContentView.Detail.class })
	@OneToMany(mappedBy = "content")
	private List<Comment> comments;

	@JsonView({ ContentView.Base.class })
	public int getCommentSize() {
		return comments == null ? 0 : comments.size();
	}
	
	@JsonView({ ContentView.Base.class })
	@Transient
	private boolean liked;
	
	@JsonView({ ContentView.Base.class })
	@Transient
	private boolean collected;
	
	

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ContentGroup getContentGroup() {
		return contentGroup;
	}

	public void setContentGroup(ContentGroup contentGroup) {
		this.contentGroup = contentGroup;
	}

	public List<User> getCollectedUsers() {
		return collectedUsers;
	}

	public void setCollectedUsers(List<User> collectedUsers) {
		this.collectedUsers = collectedUsers;
	}

	public List<User> getFans() {
		return fans;
	}

	public void setFans(List<User> fans) {
		this.fans = fans;
	}

	public List<User> getReaders() {
		return readers;
	}

	public void setReaders(List<User> readers) {
		this.readers = readers;
	}

	public Long getPv() {
		return pv;
	}

	public void setPv(Long pv) {
		this.pv = pv;
	}

}
