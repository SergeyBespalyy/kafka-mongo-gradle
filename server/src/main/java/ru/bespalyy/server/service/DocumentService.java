package ru.bespalyy.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bespalyy.common.dto.DocumentDtoRequest;
import ru.bespalyy.common.dto.DocumentDtoResponse;
import ru.bespalyy.server.exceptions.ValidationIdException;
import ru.bespalyy.server.mapper.DocumentMapper;
import ru.bespalyy.server.model.DocumentModel;
import ru.bespalyy.server.repository.DocumentRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;


    public DocumentDtoResponse createDocument(DocumentDtoRequest documentDtoRequest) {
        DocumentModel documentModel = DocumentMapper.toDocumentModel(documentDtoRequest);
        documentRepository.save(documentModel);
        return DocumentMapper.toDocumentDtoResponse(documentModel);
    }

    public DocumentDtoResponse getDocumentById(String id) {
        Optional<DocumentModel> optionalBook = documentRepository.findById(id);
        return DocumentMapper.toDocumentDtoResponse(optionalBook.orElseThrow(()-> new ValidationIdException("Пользователь не найден")));
    }

    public DocumentDtoResponse updateDocument(String id, DocumentDtoRequest documentDtoRequest) {
        DocumentModel documentModel = DocumentMapper.toDocumentModel(documentDtoRequest);
        documentModel.setId(id);
        documentRepository.save(documentModel);
        return DocumentMapper.toDocumentDtoResponse(documentModel);
    }

    public void deleteDocument(String id) {
        documentRepository.deleteById(id);
    }

    public List<DocumentDtoResponse> findAll(){
        List<DocumentModel> documentModels = documentRepository.findAll();
        return documentModels.stream().map(DocumentMapper::toDocumentDtoResponse).collect(Collectors.toList());
    }

    public List<DocumentDtoResponse> getDocumentByAuthor(String text) {
        List<DocumentModel> documentModels = documentRepository.findAllByAuthorContaining(text);
        return documentModels.stream().map(DocumentMapper::toDocumentDtoResponse).collect(Collectors.toList());
    }

    public List<DocumentDtoResponse> getDocumentByTitle(String text) {
        List<DocumentModel> documentModels = documentRepository.findAllByTitleContaining(text);
        return documentModels.stream().map(DocumentMapper::toDocumentDtoResponse).collect(Collectors.toList());
    }
}
