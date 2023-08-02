package ru.bespalyy.server.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.bespalyy.common.dto.DocumentDtoRequest;
import ru.bespalyy.server.service.DocumentService;

@Service@RequiredArgsConstructor
public class DocumentConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentConsumer.class);
    private final DocumentService documentService;

    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void consume(DocumentDtoRequest dtoRequest) {
        LOGGER.info(String.format("Document event received in server service => %s", dtoRequest.toString()));

        documentService.createDocument(dtoRequest);
    }
}
