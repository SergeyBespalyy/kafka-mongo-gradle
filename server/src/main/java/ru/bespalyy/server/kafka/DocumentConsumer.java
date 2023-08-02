package ru.bespalyy.server.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.bespalyy.common.dto.DocumentDtoRequest;
import ru.bespalyy.common.dto.DocumentDtoResponse;
import ru.bespalyy.server.service.DocumentService;

@Service
public class DocumentConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentConsumer.class);
    private final DocumentService documentService;
    private final KafkaTemplate<String, DocumentDtoResponse> kafkaTemplate;
    private final NewTopic topic;

    public DocumentConsumer(DocumentService documentService, KafkaTemplate<String, DocumentDtoResponse> kafkaTemplate, NewTopic topic) {
        this.documentService = documentService;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void consume(DocumentDtoRequest dtoRequest, @Header String  requestId) {
        LOGGER.info(String.format("Document event received in server service => %s", dtoRequest.toString()));

        // Сохраняем в базу данных с помощью DocumentService
        DocumentDtoResponse response = documentService.createDocument(dtoRequest);

        // Отправляем обратно в Kafka объект DocumentDtoResponse
        sendMessageToKafka(response, requestId);
    }

    private void sendMessageToKafka(DocumentDtoResponse dtoResponse, String requestId ) {
        LOGGER.info(String.format("Sending DocumentDtoResponse => %s", dtoResponse.toString()));

        Message<DocumentDtoResponse> message = MessageBuilder
                .withPayload(dtoResponse)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .setHeader("requestId", requestId) // Добавляем уникальный идентификатор в заголовок сообщения
                .build();


        kafkaTemplate.send(message);
    }
}