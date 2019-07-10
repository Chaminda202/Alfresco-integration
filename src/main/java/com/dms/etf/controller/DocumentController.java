package com.dms.etf.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dms.etf.exception.EtfApiException;
import com.dms.etf.request.DocumentRequest;
import com.dms.etf.response.DocumentResponse;
import com.dms.etf.response.DocumentVersionHistoryResponse;
import com.dms.etf.service.DocumentService;
import com.dms.etf.util.CommonMessage;
import com.dms.etf.validator.DocumentExtensionValidator;
import com.dms.etf.validator.DocumentTypeValidator;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
	private Logger logger;
	private DocumentTypeValidator documentTypeValidator;
	private DocumentExtensionValidator documentExtensionValidator;
	private DocumentService documentService;
	
	public DocumentController(DocumentTypeValidator documentTypeValidator, DocumentExtensionValidator documentExtensionValidator,
			DocumentService documentService){
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.documentTypeValidator = documentTypeValidator;
		this.documentExtensionValidator = documentExtensionValidator;
		this.documentService = documentService;
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> uploadDocument(@RequestParam("file") MultipartFile file,
			@RequestParam("docType") String docType, @RequestParam("userReference") String userReference) {
		logger.info("Start Upload document controller {} -> {}", docType, userReference);
		Map<String, Object> response = new HashMap<>();
		try{
			if(documentTypeValidator.validateDocumentType(docType)){
				if(documentExtensionValidator.validateDocumentExtension(file)){
					DocumentRequest request = new DocumentRequest
							.Builder()
							.file(file)
							.type(docType)
							.userReference(userReference)
							.build();
					DocumentResponse res = documentService.uploadDocument(request);
					response.put("FILE_ID", res.getEntry().getId());
					response.put(CommonMessage.STATUS, CommonMessage.STATUS_SUCCESS);
					response.put(CommonMessage.MESSAGE, CommonMessage.DOCUMENT_UPLOAD_SUCCESS_MESSAGE);
				}else{
					throw new EtfApiException(CommonMessage.INVALID_EXTENSION_MESSAGE);
				}
			}else{
				throw new EtfApiException(CommonMessage.INVALID_DOCUMENT_TYPE_MESSAGE);
			}
		}catch (EtfApiException e) {
			logger.error("Upload document controller {} -> {} -> {}", userReference, CommonMessage.STATUS_FAILED, e.getMessage());
			response.put(CommonMessage.STATUS, CommonMessage.STATUS_FAILED);
			response.put(CommonMessage.MESSAGE, e.getMessage());
		}catch (Exception ex) {
			logger.error("Upload document controller {} -> {} -> {}", userReference, CommonMessage.STATUS_FAILED, ex.getMessage());
			response.put(CommonMessage.STATUS, CommonMessage.STATUS_FAILED);
			response.put(CommonMessage.MESSAGE, CommonMessage.DOCUMENT_UPLOAD_FAILED_MESSAGE);
		}
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value="/{docId}/{docType}")
	public ResponseEntity<Resource> getDocumentType(@PathVariable String docId, @PathVariable String docType){
		logger.info("Start Upload document controller {} -> {}", docId, docType);
		Map<String, Object> response = new HashMap<>();
		try{
			return documentService.getDocument(docId, docType);
		}catch (EtfApiException e) {
			logger.error("Upload document controller {} -> {}", CommonMessage.STATUS_FAILED, e.getMessage());
			response.put(CommonMessage.STATUS, CommonMessage.STATUS_FAILED);
			response.put(CommonMessage.MESSAGE, e.getMessage());
		}catch (Exception ex) {
			logger.error("Upload document controller {} -> {}", CommonMessage.STATUS_FAILED, ex.getMessage());
			response.put(CommonMessage.STATUS, CommonMessage.STATUS_FAILED);
			response.put(CommonMessage.MESSAGE, CommonMessage.DOCUMENT_RETRIEVE_FAILED_MESSAGE);
		}
		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
	}
	
	@PutMapping(value="/{docId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> updateDocument(@PathVariable String docId, @RequestParam("file") MultipartFile file,
			@RequestParam("docType") String docType, @RequestParam("userReference") String userReference) {
		logger.info("Start Upload document controller {} -> {} -> {}", docType, docId, userReference);
		Map<String, Object> response = new HashMap<>();
		try{
			if(documentTypeValidator.validateDocumentType(docType)){
				if(documentExtensionValidator.validateDocumentExtension(file)){
					DocumentRequest request = new DocumentRequest
							.Builder()
							.file(file)
							.type(docType)
							.userReference(userReference)
							.docReference(docId)
							.build();
					
					DocumentResponse res = documentService.updateDocument(request);
					response.put("FILE_ID", res.getEntry().getId());
					response.put(CommonMessage.STATUS, CommonMessage.STATUS_SUCCESS);
					response.put(CommonMessage.MESSAGE, CommonMessage.DOCUMENT_UPLOAD_SUCCESS_MESSAGE);
				}else{
					throw new EtfApiException(CommonMessage.INVALID_EXTENSION_MESSAGE);
				}
			}else{
				throw new EtfApiException(CommonMessage.INVALID_DOCUMENT_TYPE_MESSAGE);
			}
		}catch (EtfApiException e) {
			logger.error("Upload document controller {} -> {} -> {}", userReference, CommonMessage.STATUS_FAILED, e.getMessage());
			response.put(CommonMessage.STATUS, CommonMessage.STATUS_FAILED);
			response.put(CommonMessage.MESSAGE, e.getMessage());
		}catch (Exception ex) {
			logger.error("Upload document controller {} -> {} -> {}", userReference, CommonMessage.STATUS_FAILED, ex.getMessage());
			response.put(CommonMessage.STATUS, CommonMessage.STATUS_FAILED);
			response.put(CommonMessage.MESSAGE, CommonMessage.DOCUMENT_UPDATE_FAILED_MESSAGE);
		}
		return response;
	}
	
	@GetMapping(value="/{docId}")
	public Map<String, Object> documentVersionHistory(@PathVariable String docId) {
		logger.info("Start Document version history controller {}", docId);
		Map<String, Object> response = new HashMap<>();
		try{
			DocumentVersionHistoryResponse res = documentService.documentVersionHistory(docId);
			response.put(CommonMessage.DATA, res);
			response.put(CommonMessage.STATUS, CommonMessage.STATUS_SUCCESS);
			response.put(CommonMessage.MESSAGE, CommonMessage.DOCUMENT_VERSION_HISTORY_RETRIEVE_SUCCESS_MESSAGE);
		}catch (EtfApiException e) {
			logger.error("Document version history controller {} -> {} -> {}", docId, CommonMessage.STATUS_FAILED, e.getMessage());
			response.put(CommonMessage.STATUS, CommonMessage.STATUS_FAILED);
			response.put(CommonMessage.MESSAGE, e.getMessage());
		}catch (Exception ex) {
			logger.error("Document version history controller {} -> {} -> {}", docId, CommonMessage.STATUS_FAILED, ex.getMessage());
			response.put(CommonMessage.STATUS, CommonMessage.STATUS_FAILED);
			response.put(CommonMessage.MESSAGE, CommonMessage.DOCUMENT_VERSION_HISTORY_RETRIEVE_FAILED_MESSAGE);
		}
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value="/version/{docId}/{versionId}")
	public ResponseEntity<Resource> documentVersionContent(@PathVariable String docId, @PathVariable double versionId){
		logger.info("Start Document version content controller {} -> {}", docId, versionId);
		Map<String, Object> response = new HashMap<>();
		try{
			return documentService.documentVersionContent(docId, versionId);
		}catch (EtfApiException e) {
			logger.error("Upload document controller {} -> {}", CommonMessage.STATUS_FAILED, e.getMessage());
			response.put(CommonMessage.STATUS, CommonMessage.STATUS_FAILED);
			response.put(CommonMessage.MESSAGE, e.getMessage());
		}catch (Exception ex) {
			logger.error("Upload document controller {} -> {}", CommonMessage.STATUS_FAILED, ex.getMessage());
			response.put(CommonMessage.STATUS, CommonMessage.STATUS_FAILED);
			response.put(CommonMessage.MESSAGE, CommonMessage.DOCUMENT_VERSION_CONTENT_RETRIEVE_FAILED_MESSAGE);
		}
		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
	}
}
