package com.dms.etf.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pagination {
	@JsonProperty("count")
	private Long count;
	@JsonProperty("hasMoreItems")
	private Boolean hasMoreItems;
	@JsonProperty("totalItems")
	private Long totalItems;
	@JsonProperty("skipCount")
	private Long skipCount;

	@JsonProperty("count")
	public Long getCount() {
		return count;
	}

	@JsonProperty("count")
	public void setCount(Long count) {
		this.count = count;
	}

	@JsonProperty("hasMoreItems")
	public Boolean getHasMoreItems() {
		return hasMoreItems;
	}

	@JsonProperty("hasMoreItems")
	public void setHasMoreItems(Boolean hasMoreItems) {
		this.hasMoreItems = hasMoreItems;
	}

	@JsonProperty("totalItems")
	public Long getTotalItems() {
		return totalItems;
	}

	@JsonProperty("totalItems")
	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	@JsonProperty("skipCount")
	public Long getSkipCount() {
		return skipCount;
	}

	@JsonProperty("skipCount")
	public void setSkipCount(Long skipCount) {
		this.skipCount = skipCount;
	}
}