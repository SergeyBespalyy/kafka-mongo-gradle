package ru.bespalyy.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.bespalyy.common.dto.DocumentDtoRequest;

@Service
public class DocumentProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentProducer.class);
    private final NewTopic topic;

    private final KafkaTemplate<String, DocumentDtoRequest> kafkaTemplate;

    public DocumentProducer(NewTopic topic, KafkaTemplate<String, DocumentDtoRequest> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(DocumentDtoRequest dtoRequest){
        LOGGER.info(String.format("Document event => %s", dtoRequest.toString()));

        Message<DocumentDtoRequest> message = MessageBuilder
                .withPayload(dtoRequest)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        kafkaTemplate.send(message);
    }
}
