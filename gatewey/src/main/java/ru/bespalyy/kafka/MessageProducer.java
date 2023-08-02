//package ru.bespalyy.kafka;
//
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Component;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.util.concurrent.ListenableFutureCallback;
//import ru.bespalyy.dto.DocumentDtoRequest;
//
//@Slf4j
//@NoArgsConstructor
//@Component
//public class MessageProducer {
//
//    @Autowired
//    private KafkaTemplate<String, DocumentDtoRequest> kafkaTemplate;
//    @Value(value = "${kafka.topic.name}")
//    private String topicName;
//
//
//    public void sendMessage(DocumentDtoRequest documentDtoRequest) {
//        ListenableFuture<SendResult<String, DocumentDtoRequest>> future = kafkaTemplate.send(topicName, documentDtoRequest);
//
//        future.addCallback(new ListenableFutureCallback<SendResult<String, DocumentDtoRequest>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                log.error("Unable to send message = {} dut to: {}", documentDtoRequest, throwable.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, DocumentDtoRequest> stringDataSendResult) {
//                log.info("Sent Message = {} with offset = {}", documentDtoRequest, stringDataSendResult.getRecordMetadata().offset());
//            }
//        });
//    }
//}