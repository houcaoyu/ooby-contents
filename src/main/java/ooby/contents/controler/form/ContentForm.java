package ooby.contents.controler.form;

public class ContentForm {
	private Long id;
	
	private String title;
	private String text;
	private String coverId;
	private Long contentGroupId;
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
	public String getCoverId() {
		return coverId;
	}
	public void setCoverId(String coverId) {
		this.coverId = coverId;
	}
	public Long getContentGroupId() {
		return contentGroupId;
	}
	public void setContentGroupId(Long contentGroupId) {
		this.contentGroupId = contentGroupId;
	}
	

	
}
