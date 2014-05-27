package com.model;

/**
 * Html entity. @author MyEclipse Persistence Tools
 */

public class Html implements java.io.Serializable {

	// Fields

	private Integer id;
	private String title;
	private String content;

	// Constructors

	/** default constructor */
	public Html() {
	}

	/** full constructor */
	public Html(String title, String content) {
		this.title = title;
		this.content = content;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}