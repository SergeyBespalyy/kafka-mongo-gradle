package ru.bespalyy.kafka;

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

import java.util.UUID;
import java.util.concurrent.*;

@Service
public class DocumentProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentProducer.class);
    private final NewTopic topic;
    private final KafkaTemplate<String, DocumentDtoRequest> kafkaTemplate;
    private final ConcurrentHashMap<String, CompletableFuture<DocumentDtoResponse>> pendingRequests;

    public DocumentProducer(NewTopic topic, KafkaTemplate<String, DocumentDtoRequest> kafkaTemplate) {
        this.topic = topic;

        this.kafkaTemplate = kafkaTemplate;
        this.pendingRequests = new ConcurrentHashMap<>();
    }

    public DocumentDtoResponse sendAndReceiveMessage(DocumentDtoRequest dtoRequest) throws InterruptedException, ExecutionException, TimeoutException {
        LOGGER.info(String.format("Document event => %s", dtoRequest.toString()));

        // Уникальный идентификатор для запроса
        String requestId = UUID.randomUUID().toString();

        CompletableFuture<DocumentDtoResponse> completableFuture = new CompletableFuture<>();
        pendingRequests.put(requestId, completableFuture);

        Message<DocumentDtoRequest> message = MessageBuilder
                .withPayload(dtoRequest)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .setHeader("requestId", requestId) // Добавляем уникальный идентификатор в заголовок сообщения
                .build();

        kafkaTemplate.send(message);

        // Ожидаем ответа с таймаутом в 10 секунд
        DocumentDtoResponse dtoResponse = completableFuture.get(100, TimeUnit.SECONDS);

        // Удаляем CompletableFuture из pendingRequests после получения ответа
        pendingRequests.remove(requestId);

        return dtoResponse;
    }

    // Kafka-листенер для получения ответов
    @KafkaListener(topics = "${kafka.response.topic}")
    public void listenForResponse(DocumentDtoResponse dtoResponse, @Header String requestId) {
        CompletableFuture<DocumentDtoResponse> completableFuture = pendingRequests.get(requestId);
        if (completableFuture != null) {
            completableFuture.complete(dtoResponse);
        } else {
            LOGGER.warn("Received response for unknown requestId: " + requestId);
        }
    }
}
