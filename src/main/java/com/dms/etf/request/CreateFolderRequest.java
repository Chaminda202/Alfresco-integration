package com.dms.etf.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateFolderRequest {
	@JsonProperty("name")
	private String name;
	@JsonProperty("nodeType")
	private String nodeType;
	
	public CreateFolderRequest(String name, String nodeType) {
		super();
		this.name = name;
		this.nodeType = nodeType;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("nodeType")
	public String getNodeType() {
		return nodeType;
	}

	@JsonProperty("nodeType")
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
}