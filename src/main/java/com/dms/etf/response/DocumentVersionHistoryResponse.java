package com.dms.etf.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentVersionHistoryResponse {
	@JsonProperty("list")
	private List list;

	@JsonProperty("list")
	public List getList() {
		return list;
	}

	@JsonProperty("list")
	public void setList(List list) {
		this.list = list;
	}
}