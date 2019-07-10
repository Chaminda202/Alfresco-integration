package com.dms.etf.validator;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dms.etf.entity.DocumentType;
import com.dms.etf.repository.DocumentTypeRepository;
import com.dms.etf.util.CommonMessage;

@Component
public class DocumentTypeValidator {
	private Logger logger;
	private DocumentTypeRepository documentTypeRepository;

	public DocumentTypeValidator(DocumentTypeRepository documentTypeRepository) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.documentTypeRepository = documentTypeRepository;
	}

	public boolean validateDocumentType(String docType) {
		logger.info("Start Validate document type {}", docType);
		List<DocumentType> docTypeList = documentTypeRepository.findAll();
		List<String> docList = docTypeList.stream().map(t -> t.getType()).collect(Collectors.toList());
		logger.info("End Validate document type {} -> {}", docType, CommonMessage.STATUS_SUCCESS);
		return docList.contains(docType);
	}
}
