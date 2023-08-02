package ru.bespalyy.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bespalyy.common.dto.DocumentDtoRequest;
import ru.bespalyy.common.dto.DocumentDtoResponse;

import java.util.List;

@Service
public class DocumentClient {

    private static final String API_PREFIX = "/documents";
    private final RestTemplate rest;
    private final String serverUrl;

    public DocumentClient(@Value("${server.url}") String serverUrl) {
        this.rest = new RestTemplate();
        this.serverUrl = serverUrl;
    }

    public DocumentDtoResponse create(DocumentDtoRequest documentDtoRequest) {
        HttpEntity<DocumentDtoRequest> requestEntity = new HttpEntity<>(documentDtoRequest);
        ResponseEntity<DocumentDtoResponse> documentDtoResponse = rest.exchange(serverUrl + API_PREFIX,
                HttpMethod.POST, requestEntity, DocumentDtoResponse.class);
        return documentDtoResponse.getBody();
    }

    public DocumentDtoResponse getById(String id) {

        String path = serverUrl + API_PREFIX + "/" + id;

        ResponseEntity<DocumentDtoResponse> serverResponse = rest.exchange(path, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        DocumentDtoResponse response = serverResponse.getBody();
        return response;
    }

    public List<DocumentDtoResponse> getAll() {

        String path = serverUrl + API_PREFIX;

        ResponseEntity<List<DocumentDtoResponse>> serverResponse = rest.exchange(path, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        List<DocumentDtoResponse> responseList = serverResponse.getBody();
        return responseList;
    }

    public DocumentDtoResponse update(String id, DocumentDtoRequest documentDtoRequest) {
        HttpEntity<DocumentDtoRequest> requestEntity = new HttpEntity<>(documentDtoRequest);
        ResponseEntity<DocumentDtoResponse> documentDtoResponse = rest.exchange(serverUrl + API_PREFIX + "/" + id,
                HttpMethod.PUT, requestEntity, DocumentDtoResponse.class);
        return documentDtoResponse.getBody();
    }

    public HttpStatus delete(String id) {
        String path = serverUrl + API_PREFIX + "/" + id;

        ResponseEntity<List<DocumentDtoResponse>> serverResponse = rest.exchange(path, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<>() {
                });
        return HttpStatus.OK;
    }

    public List<DocumentDtoResponse> getByAuthor(String text) {

        String path = serverUrl + API_PREFIX + "/author/search?text=" + text;

        ResponseEntity<List<DocumentDtoResponse>> serverResponse = rest.exchange(path, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        List<DocumentDtoResponse> response = serverResponse.getBody();
        return response;
    }

    public List<DocumentDtoResponse> getByTitle(String text) {

        String path = serverUrl + API_PREFIX + "/title/search?text=" + text;

        ResponseEntity<List<DocumentDtoResponse>> serverResponse = rest.exchange(path, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        List<DocumentDtoResponse> response = serverResponse.getBody();
        return response;
    }

}
