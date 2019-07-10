package com.dms.etf.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Content {
	@JsonProperty("mimeType")
	private String mimeType;
	@JsonProperty("mimeTypeName")
	private String mimeTypeName;
	@JsonProperty("sizeInBytes")
	private Long sizeInBytes;

	@JsonProperty("mimeType")
	public String getMimeType() {
		return mimeType;
	}

	@JsonProperty("mimeType")
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@JsonProperty("mimeTypeName")
	public String getMimeTypeName() {
		return mimeTypeName;
	}

	@JsonProperty("mimeTypeName")
	public void setMimeTypeName(String mimeTypeName) {
		this.mimeTypeName = mimeTypeName;
	}

	@JsonProperty("sizeInBytes")
	public Long getSizeInBytes() {
		return sizeInBytes;
	}

	@JsonProperty("sizeInBytes")
	public void setSizeInBytes(Long sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}
}