package com.dms.etf.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entry {
	@JsonProperty("isFile")
	private Boolean isFile;
	@JsonProperty("createdByUser")
	private CreatedByUser createdByUser;
	@JsonProperty("content")
	private Content content;
	@JsonProperty("createdAt")
	private String createdAt;
	@JsonProperty("isFolder")
	private Boolean isFolder;
	@JsonProperty("modifiedByUser")
	private ModifiedByUser modifiedByUser;
	@JsonProperty("name")
	private String name;
	@JsonProperty("id")
	private String id;
	@JsonProperty("nodeType")
	private String nodeType;
	@JsonProperty("properties")
	private Properties properties;

	@JsonProperty("isFile")
	public Boolean getIsFile() {
		return isFile;
	}

	@JsonProperty("isFile")
	public void setIsFile(Boolean isFile) {
		this.isFile = isFile;
	}

	@JsonProperty("createdByUser")
	public CreatedByUser getCreatedByUser() {
		return createdByUser;
	}

	@JsonProperty("createdByUser")
	public void setCreatedByUser(CreatedByUser createdByUser) {
		this.createdByUser = createdByUser;
	}

	@JsonProperty("content")
	public Content getContent() {
		return content;
	}

	@JsonProperty("content")
	public void setContent(Content content) {
		this.content = content;
	}

	@JsonProperty("createdAt")
	public String getCreatedAt() {
		return createdAt;
	}

	@JsonProperty("createdAt")
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty("isFolder")
	public Boolean getIsFolder() {
		return isFolder;
	}

	@JsonProperty("isFolder")
	public void setIsFolder(Boolean isFolder) {
		this.isFolder = isFolder;
	}

	@JsonProperty("modifiedByUser")
	public ModifiedByUser getModifiedByUser() {
		return modifiedByUser;
	}

	@JsonProperty("modifiedByUser")
	public void setModifiedByUser(ModifiedByUser modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("nodeType")
	public String getNodeType() {
		return nodeType;
	}

	@JsonProperty("nodeType")
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	@JsonProperty("properties")
	public Properties getProperties() {
		return properties;
	}

	@JsonProperty("properties")
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}