package ooby.contents.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

import ooby.contents.view.ContentGroupCategoryView;

@Entity
public class ContentGroupCategory {
	@Id
	@GeneratedValue
	@JsonView({ContentGroupCategoryView.Base.class})
	private Long id;
	@JsonView({ContentGroupCategoryView.Base.class})
	private String name;
	@OneToMany(mappedBy="category")
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
