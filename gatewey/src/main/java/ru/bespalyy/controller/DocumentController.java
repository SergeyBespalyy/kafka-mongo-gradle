package ru.bespalyy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.bespalyy.client.DocumentClient;
import ru.bespalyy.common.dto.DocumentDtoRequest;
import ru.bespalyy.common.dto.DocumentDtoResponse;
import ru.bespalyy.kafka.DocumentProducer;

import java.util.List;

@RestController
@RequestMapping("/documents")
@Slf4j
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentProducer documentProducer;
    private final DocumentClient documentClient;


    @PostMapping
    public String createDocument(@RequestBody DocumentDtoRequest documentDtoRequest) {
        log.info("CLIENT Create document {}", documentDtoRequest);
        documentProducer.sendMessage(documentDtoRequest);
        return "createDocument successfully";
    }

    @GetMapping("/{id}")
    public DocumentDtoResponse getDocument(@PathVariable String id) {
        log.info("Get document , id={}", id);
        return documentClient.getById(id);
    }

    @GetMapping
    public List<DocumentDtoResponse> getDocumentAll() {
        log.info("Get All document");
        return documentClient.getAll();
    }

    @GetMapping("/author/search")
    public List<DocumentDtoResponse> getDocumentByAuthor(@RequestParam String text) {
        log.info("Get document , поиск по автору text={}", text);
        return documentClient.getByAuthor(text);
    }

    @GetMapping("title/search")
    public List<DocumentDtoResponse> getDocumentByTitle(@RequestParam String text) {
        log.info("Get document , поиск по описанию text={}", text);
        return documentClient.getByTitle(text);
    }

    @PutMapping("/{id}")
    public DocumentDtoResponse updateDocument(@PathVariable String id, @RequestBody DocumentDtoRequest documentDtoRequest) {
        log.info("Update document c ID = {}", id);
        return documentClient.update(id, documentDtoRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable String id) {
        log.info("Delete document, c ID= {}", id);
        documentClient.delete(id);
    }
}