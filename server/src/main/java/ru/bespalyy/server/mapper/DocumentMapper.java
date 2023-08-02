package ru.bespalyy.server.mapper;

import lombok.experimental.UtilityClass;
import ru.bespalyy.common.dto.DocumentDtoRequest;
import ru.bespalyy.common.dto.DocumentDtoResponse;
import ru.bespalyy.server.model.DocumentModel;

@UtilityClass
public class DocumentMapper {
    public static DocumentModel toDocumentModel(DocumentDtoRequest documentDtoRequest) {
        return DocumentModel.builder()
                .title(documentDtoRequest.getTitle())
                .author(documentDtoRequest.getAuthor())
                .build();
    }

    public static DocumentDtoResponse toDocumentDtoResponse(DocumentModel documentModel) {
        return DocumentDtoResponse.builder()
                .id(documentModel.getId())
                .author(documentModel.getAuthor())
                .title(documentModel.getTitle())
                .build();
    }
}
