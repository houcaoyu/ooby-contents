package ooby.contents.entity;

import java.sql.Blob;
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

import ooby.contents.view.ContentGroupView;
import ooby.contents.view.ContentView;

@Entity
public class ContentGroup {
	@Id
	@GeneratedValue
	@JsonView({ContentGroupView.Base.class})
	private Long id;
	@JsonView({ContentGroupView.Base.class})
	private String name;
	
	@OneToOne
	private ResourceRepository image;
	@JsonView({ContentGroupView.Base.class})
	private String description;
	@JsonView({ContentGroupView.List.class,ContentGroupView.Detail.class,ContentView.Detail.class})
	@ManyToOne
	private User user;
	
	@JsonView({ContentGroupView.Detail.class})
	@Transient
	private boolean subscribed;
	
	@JsonView({ContentGroupView.Detail.class})
	@OneToMany(mappedBy="contentGroup")
	private List<Content> contents;
		
	@JsonView({ContentGroupView.Detail.class})
	@ManyToMany
	private List<Tag> tags;
	
	@ManyToMany(mappedBy="subscriptions")
	private List<User> subscribers;
	
	@JsonView({ContentGroupView.Detail.class})
	@ManyToOne
	private ContentGroupCategory category;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ContentGroupCategory getCategory() {
		return category;
	}

	public void setCategory(ContentGroupCategory category) {
		this.category = category;
	}

	public List<Content> getContents() {
		return contents;
	}
	
	@JsonView(ContentGroupView.Base.class)
	public int getContentSize(){
		return contents==null?0:contents.size();
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<User> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<User> subscribers) {
		this.subscribers = subscribers;
	}

	@JsonView({ContentGroupView.Base.class})
	public Long getPv(){
		if(contents==null)
			return 0l;
		long pv=0;
		for(Content content:contents){
			pv+=content.getPv();
		}
		return pv;
	}
	@JsonView({ContentGroupView.Base.class})
	public int getSubscriberSize(){
		if(subscribers==null)
			return 0;
		return subscribers.size();
	}
	
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	public ResourceRepository getImage() {
		return image;
	}

	public void setImage(ResourceRepository image) {
		this.image = image;
	}


	
	
}
