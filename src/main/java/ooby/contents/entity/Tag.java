package ooby.contents.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonView;

import ooby.contents.view.ContentGroupView;

@Entity
public class Tag {
	@Id
	@GeneratedValue
	@JsonView({ContentGroupView.Detail.class})
	private Long id;
	
	@JsonView({ContentGroupView.Detail.class})
	private String name;
	@ManyToMany(mappedBy="tags")
	private List<ContentGroup> contentGroups;
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
	public List<ContentGroup> getContentGroups() {
		return contentGroups;
	}
	public void setContentGroups(List<ContentGroup> contentGroups) {
		this.contentGroups = contentGroups;
	}
	
	
	
}
