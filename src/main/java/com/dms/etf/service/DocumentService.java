package com.dms.etf.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.dms.etf.exception.EtfApiException;
import com.dms.etf.request.DocumentRequest;
import com.dms.etf.response.CreateFolderResponse;
import com.dms.etf.response.DocumentResponse;
import com.dms.etf.response.DocumentVersionHistoryResponse;

public interface DocumentService {
	public String getAuthTicket();
	public DocumentResponse uploadDocument(DocumentRequest request) throws IOException;
	public CreateFolderResponse createFolder(String name, String nodeType);
	public ResponseEntity<Resource> getDocument(String docId, String docType) throws EtfApiException;
	public DocumentResponse updateDocument(DocumentRequest request) throws IOException, EtfApiException;
	public DocumentVersionHistoryResponse documentVersionHistory(String docId) throws IOException, EtfApiException;
	public ResponseEntity<Resource> documentVersionContent(String docId, double version) throws EtfApiException;
}
