package com.dms.etf.request;

import org.springframework.web.multipart.MultipartFile;

public class DocumentRequest {
	private String userReference;
	private String type;
	private String extension;
	private MultipartFile file;
	private String docReference;

	public static class Builder {
		private String userReference;
		private String type;
		private String extension;
		private MultipartFile file;
		private String docReference;

		public Builder userReference(String userReference) {
			this.userReference = userReference;
			return this;
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public Builder extension(String extension) {
			this.extension = extension;
			return this;
		}

		public Builder file(MultipartFile file) {
			this.file = file;
			return this;
		}
		
		public Builder docReference(String docReference) {
			this.docReference = docReference;
			return this;
		}

		public DocumentRequest build() {
			DocumentRequest request = new DocumentRequest();
			request.userReference = this.userReference;
			request.type = this.type;
			request.extension = this.extension;
			request.file = this.file;
			request.docReference = this.docReference;
			return request;
		}
	}

	public String getUserReference() {
		return userReference;
	}

	public void setUserReference(String userReference) {
		this.userReference = userReference;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getDocReference() {
		return docReference;
	}

	public void setDocReference(String docReference) {
		this.docReference = docReference;
	}
}
