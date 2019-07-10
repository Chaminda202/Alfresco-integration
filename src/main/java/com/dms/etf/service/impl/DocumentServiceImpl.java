package com.dms.etf.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.dms.etf.entity.DocumentDetail;
import com.dms.etf.exception.EtfApiException;
import com.dms.etf.repository.DocumentDetailRepository;
import com.dms.etf.repository.DocumentExtensionRepository;
import com.dms.etf.repository.DocumentTypeRepository;
import com.dms.etf.request.CreateFolderRequest;
import com.dms.etf.request.DocumentRequest;
import com.dms.etf.response.CreateFolderResponse;
import com.dms.etf.response.DocumentResponse;
import com.dms.etf.response.DocumentVersionHistoryResponse;
import com.dms.etf.service.DocumentService;
import com.dms.etf.util.CommonMessage;
import com.dms.etf.util.JacksonUtil;
import com.dms.etf.util.Util;

@Service
public class DocumentServiceImpl implements DocumentService {
	@Value("${alfresco.login.username}")
	private String username;
	@Value("${alfresco.login.password}")
	private String password;
	@Value("${alfresco.config.node-type}")
	private String nodeType;
	@Value("${alfresco.config.folder-name}")
	private String folderName;
	@Value("${alfresco.config.temp-location}")
	private String tempLocation;

	@Value("${alfresco.context-path}")
	private String contextPath;
	@Value("${alfresco.endpoint.auth-url}")
	private String authUrl;
	@Value("${alfresco.endpoint.upload-url}")
	private String uploadUrl;
	@Value("${alfresco.endpoint.create-folder-url}")
	private String createFolderUrl;
	@Value("${alfresco.endpoint.retrieve-url}")
	private String retrieveUrl;
	@Value("${alfresco.endpoint.update-url}")
	private String updateUrl;
	@Value("${alfresco.endpoint.version-history-url}")
	private String versionHistoryUrl;
	@Value("${alfresco.endpoint.version-content-url}")
	private String versionContentUrl;

	private Logger logger;
	private RestTemplate restTemplate;
	private DocumentDetailRepository documentDetailRepository;
	private DocumentTypeRepository documentTypeRepository;
	private DocumentExtensionRepository documentExtensionRepository;

	public DocumentServiceImpl(RestTemplate restTemplate, DocumentDetailRepository documentDetailRepository,
			DocumentTypeRepository documentTypeRepository, DocumentExtensionRepository documentExtensionRepository) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.restTemplate = restTemplate;
		this.documentDetailRepository = documentDetailRepository;
		this.documentTypeRepository = documentTypeRepository;
		this.documentExtensionRepository = documentExtensionRepository;
	}

	@Override
	public String getAuthTicket() {
		logger.info("Start Request ticket service");
		String url = MessageFormat.format(Util.urlBuilder(contextPath, authUrl), username.trim(), password.trim());
		HttpEntity<?> entity = new HttpEntity<>(Util.createCommonHttpHeader());
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		final String responseBody = responseEntity.getBody();
		final int startIndex = responseBody.indexOf("TICKET");
		final int endIndex = responseBody.indexOf("</");
		logger.info("End Request ticket service {} -> {} ", CommonMessage.RESPONSE, responseEntity.getBody());
		return responseBody.substring(startIndex, endIndex);
	}

	@Override
	public DocumentResponse uploadDocument(DocumentRequest request) throws IOException {
		logger.info("Start Upload document service {}", request.getUserReference());
		String url = MessageFormat.format(Util.urlBuilder(contextPath, uploadUrl), getAuthTicket());
		// check folder should be create or not
		if (documentDetailRepository.countByDocumentType(request.getType()) == 0) {
			createFolder(request.getType(), nodeType);
		}

		// Original file name
		String originalFileName = StringUtils.cleanPath(request.getFile().getOriginalFilename());
		String fileExtension = StringUtils.getFilenameExtension(originalFileName);
		request.setExtension(fileExtension);

		File directory = new File(tempLocation);
		if (!directory.exists()) {
			logger.info("Create directory ");
			directory.mkdirs();
		}
		byte[] buf = new byte[1024];
		File files = new File(tempLocation + originalFileName);
		try (InputStream inputStream = request.getFile().getInputStream();
				FileOutputStream fileOutputStream = new FileOutputStream(files)) {
			int numRead = 0;
			while ((numRead = inputStream.read(buf)) >= 0) {
				fileOutputStream.write(buf, 0, numRead);
			}
		}
		String changeDocName = changeDocName(request.getFile());
		logger.info("Changed file name {}", changeDocName);
		MultiValueMap<String, Object> requestContent = new LinkedMultiValueMap<>();
		requestContent.add("filedata", new FileSystemResource(createFilePath(tempLocation, originalFileName)));
		requestContent.add("relativePath", request.getType());
		requestContent.add("name", changeDocName);

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(requestContent,
				Util.createMultiHttpHeader());

		ResponseEntity<DocumentResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity,
				DocumentResponse.class);
		final DocumentResponse response = responseEntity.getBody();
		saveDocumentDetail(request, originalFileName, response);
		logger.info("Delete tempory file {}", deleteFile(originalFileName));
		logger.info("End Upload document service {} -> {}", CommonMessage.RESPONSE, JacksonUtil.getToString(response));
		return response;
	}

	@Override
	public CreateFolderResponse createFolder(String name, String nodeType) {
		logger.info("Start create folder service {} -> {} ", name, nodeType);
		String url = MessageFormat.format(Util.urlBuilder(contextPath, createFolderUrl), getAuthTicket());
		CreateFolderRequest req = new CreateFolderRequest(name, nodeType);

		HttpEntity<CreateFolderRequest> entity = new HttpEntity<>(req, Util.createJsonHttpHeader());

		ResponseEntity<CreateFolderResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity,
				CreateFolderResponse.class);
		CreateFolderResponse response = responseEntity.getBody();
		logger.info("End create folder service {} -> {} ", CommonMessage.RESPONSE, JacksonUtil.getToString(response));
		return response;
	}

	@Override
	public ResponseEntity<Resource> getDocument(String docId, String docType) throws EtfApiException {
		logger.info("Start Get document service {} -> {}", CommonMessage.REQUEST, docId);

		Optional<DocumentDetail> docOptional = documentDetailRepository
				.findByDocumentReferenceAndDocumentTypeType(docId, docType);
		if (docOptional.isPresent()) {
			String url = MessageFormat.format(Util.urlBuilder(contextPath, retrieveUrl), docId, getAuthTicket());
			HttpEntity<?> entity = new HttpEntity<>(Util.createCommonHttpHeader());
			ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
					Resource.class);
			logger.info("Get document service {}", CommonMessage.STATUS_SUCCESS);
			return responseEntity;
		}
		throw new EtfApiException(CommonMessage.DOCUMENT_NOT_EXIST_MESSAGE);
	}

	@Override
	public DocumentResponse updateDocument(DocumentRequest request) throws IOException, EtfApiException {
		logger.info("Start Update document service {}", request.getUserReference());

		Optional<DocumentDetail> docOptional = documentDetailRepository
				.findByDocumentReferenceAndDocumentTypeType(request.getDocReference(), request.getType());
		if (docOptional.isPresent()) {
			// Original file name
			String originalFileName = StringUtils.cleanPath(request.getFile().getOriginalFilename());
			String fileExtension = StringUtils.getFilenameExtension(originalFileName);
			request.setExtension(fileExtension);

			String url = MessageFormat.format(Util.urlBuilder(contextPath, updateUrl), request.getDocReference(),
					getAuthTicket());

			HttpHeaders header = new HttpHeaders();
			HttpEntity<byte[]> entity = new HttpEntity<>(request.getFile().getBytes(), header);

			ResponseEntity<DocumentResponse> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity,
					DocumentResponse.class);
			final DocumentResponse response = responseEntity.getBody();
			updateDocumentDetail(request, originalFileName, response);
			logger.info("End Update document service {} -> {}", CommonMessage.RESPONSE,
					JacksonUtil.getToString(response));
			return response;
		}
		throw new EtfApiException(CommonMessage.DOCUMENT_NOT_EXIST_MESSAGE);
	}

	@Override
	public DocumentVersionHistoryResponse documentVersionHistory(String docId) throws IOException, EtfApiException {
		logger.info("Start Document version history service {}", docId);

		Optional<DocumentDetail> docOptional = documentDetailRepository.findByDocumentReference(docId);
		if (docOptional.isPresent()) {
			String url = MessageFormat.format(Util.urlBuilder(contextPath, versionHistoryUrl), docId, getAuthTicket());
			HttpEntity<?> entity = new HttpEntity<>(Util.createCommonHttpHeader());
			ResponseEntity<DocumentVersionHistoryResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					entity, DocumentVersionHistoryResponse.class);
			
			final DocumentVersionHistoryResponse response = responseEntity.getBody();
			logger.info("End Document version history service {} -> {} ", CommonMessage.RESPONSE, JacksonUtil.getToString(response));
			return response;
		}
		throw new EtfApiException(CommonMessage.DOCUMENT_NOT_EXIST_MESSAGE);
	}
	
	@Override
	public ResponseEntity<Resource> documentVersionContent(String docId, double version) throws EtfApiException {
		logger.info("Start Document version content service {} -> {} -> {}", CommonMessage.REQUEST, docId, version);
		
		Optional<DocumentDetail> docOptional = documentDetailRepository
				.findByDocumentReference(docId);
		if (docOptional.isPresent()) {
			String url = MessageFormat.format(Util.urlBuilder(contextPath, versionContentUrl), docId, version, getAuthTicket());
			HttpEntity<?> entity = new HttpEntity<>(Util.createCommonHttpHeader());
			ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
					Resource.class);
			logger.info("Document version content service {}", CommonMessage.STATUS_SUCCESS);
			return responseEntity;
		}
		throw new EtfApiException(CommonMessage.DOCUMENT_NOT_EXIST_MESSAGE);
	}

	private void saveDocumentDetail(DocumentRequest request, String originalFileName, DocumentResponse response) {
		DocumentDetail detail = new DocumentDetail();
		detail.setDocumentExtension(documentExtensionRepository.getOne(request.getExtension()));
		detail.setDocumentType(documentTypeRepository.getOne(request.getType()));
		detail.setOriginalFileName(originalFileName);
		detail.setDocumentReference(response.getEntry().getId());
		detail.setUserReference(request.getUserReference());
		documentDetailRepository.save(detail);
	}
	
	private void updateDocumentDetail(DocumentRequest request, String originalFileName, DocumentResponse response){
		Optional<DocumentDetail> docOptional = documentDetailRepository
				.findByDocumentReferenceAndDocumentTypeType(request.getDocReference(), request.getType());
		DocumentDetail detail = docOptional.get();
		detail.setDocumentExtension(documentExtensionRepository.getOne(request.getExtension()));
		detail.setDocumentType(documentTypeRepository.getOne(request.getType()));
		detail.setOriginalFileName(originalFileName);
		detail.setDocumentReference(response.getEntry().getId());
		detail.setUserReference(request.getUserReference());
		documentDetailRepository.save(detail);
	}

	private String createFilePath(String filePath, String fileName) {
		StringBuilder builder = new StringBuilder();
		builder.append(filePath);
		builder.append(fileName);
		return builder.toString();
	}

	private boolean deleteFile(String fileName) {
		File file = new File(createFilePath(tempLocation, fileName));
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	private String changeDocName(MultipartFile file) {
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.stripFilenameExtension(file.getOriginalFilename()));
		builder.append("_");
		builder.append(Long.toString(System.currentTimeMillis()));
		builder.append(".");
		builder.append(StringUtils.getFilenameExtension(file.getOriginalFilename()));
		return builder.toString();
	}
}
