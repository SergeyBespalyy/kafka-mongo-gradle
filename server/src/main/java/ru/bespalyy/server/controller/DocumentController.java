package ru.bespalyy.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.bespalyy.common.dto.DocumentDtoRequest;
import ru.bespalyy.common.dto.DocumentDtoResponse;
import ru.bespalyy.server.service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/{id}")
    public DocumentDtoResponse getDocument(@PathVariable String id) {
        log.info("Get document , id={}", id);
        return documentService.getDocumentById(id);
    }

    @GetMapping
    public List<DocumentDtoResponse> getDocumentAll() {
        log.info("Get All document");
        return documentService.findAll();
    }

    @GetMapping("/author/search")
    public List<DocumentDtoResponse> getDocumentByAuthor(@RequestParam String text) {
        log.info("Get document , поиск по автору text={}", text);
        return documentService.getDocumentByAuthor(text);
    }

    @GetMapping("title/search")
    public List<DocumentDtoResponse> getDocumentByTitle(@RequestParam String text) {
        log.info("Get document , поиск по описанию text={}", text);
        return documentService.getDocumentByTitle(text);
    }

    @PutMapping("/{id}")
    public DocumentDtoResponse updateDocument(@PathVariable String id, @RequestBody DocumentDtoRequest documentDtoRequest) {
        log.info("Update document c ID = {}", id);
        return documentService.updateDocument(id, documentDtoRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable String id) {
        log.info("Delete document, c ID= {}", id);
        documentService.deleteDocument(id);
    }
}