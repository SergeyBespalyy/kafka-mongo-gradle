//package ru.bespalyy.kafka;
//
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.ser.std.StringSerializer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import ru.bespalyy.dto.DocumentDtoRequest;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaProducerConfig {
//    @Value("${kafka.bootstrapAddress}")
//    private String bootstrapAddress;
//
//    @Bean
//    public ProducerFactory<String, DocumentDtoRequest> producerFactory() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//
//        return new DefaultKafkaProducerFactory<>(config);
//    }
//
//    @Bean
//    public KafkaTemplate<String, DocumentDtoRequest> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//}
