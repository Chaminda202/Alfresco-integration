package com.dms.etf.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Properties {

	@JsonProperty("cm:versionType")
	private String cmVersionType;
	@JsonProperty("cm:versionLabel")
	private String cmVersionLabel;

	@JsonProperty("cm:versionType")
	public String getCmVersionType() {
		return cmVersionType;
	}

	@JsonProperty("cm:versionType")
	public void setCmVersionType(String cmVersionType) {
		this.cmVersionType = cmVersionType;
	}

	@JsonProperty("cm:versionLabel")
	public String getCmVersionLabel() {
		return cmVersionLabel;
	}

	@JsonProperty("cm:versionLabel")
	public void setCmVersionLabel(String cmVersionLabel) {
		this.cmVersionLabel = cmVersionLabel;
	}
}