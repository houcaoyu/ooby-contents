package ooby.contents.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonView;

import ooby.contents.view.UserView;

@Entity
public class User implements UserDetails{
	
	private static final long serialVersionUID = 2978229835321921816L;
	
	@Id
	@GeneratedValue
	@JsonView({  UserView.Base.class})
	private Long id;
	@JsonView({  UserView.Base.class})
	private String nickname;

	@JsonView({  UserView.Base.class})
	private String description;
	
	@OneToOne
	private ResourceRepository image;
	

	

	public ResourceRepository getImage() {
		return image;
	}

	public void setImage(ResourceRepository image) {
		this.image = image;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String loginName;
	private String password;
	@OneToMany(mappedBy = "user")
	@JsonView({UserView.Detail.class})
	private List<ContentGroup> contentGroups;

	@ManyToMany
	private List<Content> likes;

	@ManyToMany
	private List<Content> collections;

	@ManyToMany
	private List<ContentGroup> subscriptions;

	@ManyToMany
	private List<Content> histories;

	@ManyToMany
	private List<User> followees;
	
	@JsonView({UserView.Detail.class})
	public int getFolloweeSize(){
		return followees==null?0:followees.size();
	}

	@ManyToMany(mappedBy = "followees")
	private List<User> followers;

	@OneToMany(mappedBy = "fromUser")
	private List<Moment> myMoments;

	@OneToMany(mappedBy = "toUser")
	private List<Moment> relatedMoments;
	
	@JsonView({  UserView.Base.class})
	@Transient
	private boolean followed;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isFollowed() {
		return followed;
	}

	public void setFollowed(boolean followed) {
		this.followed = followed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ContentGroup> getContentGroups() {
		return contentGroups;
	}

	public void setContentGroups(List<ContentGroup> contentGroups) {
		this.contentGroups = contentGroups;
	}

	public List<Content> getLikes() {
		return likes;
	}

	public void setLikes(List<Content> likes) {
		this.likes = likes;
	}

	public List<Content> getCollections() {
		return collections;
	}

	public void setCollections(List<Content> collections) {
		this.collections = collections;
	}

	public List<ContentGroup> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<ContentGroup> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public List<Content> getHistories() {
		return histories;
	}

	public void setHistories(List<Content> histories) {
		this.histories = histories;
	}

	public List<User> getFollowees() {
		return followees;
	}

	public void setFollowees(List<User> followees) {
		this.followees = followees;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	@JsonView({ UserView.Base.class })
	public int getFollowerSize() {
		return followers == null ? 0 : followers.size();
	}

	public List<Moment> getMyMoments() {
		return myMoments;
	}

	public void setMyMoments(List<Moment> myMoments) {
		this.myMoments = myMoments;
	}

	public List<Moment> getRelatedMoments() {
		return relatedMoments;
	}

	public void setRelatedMoments(List<Moment> relatedMoments) {
		this.relatedMoments = relatedMoments;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		
		return loginName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	

}
