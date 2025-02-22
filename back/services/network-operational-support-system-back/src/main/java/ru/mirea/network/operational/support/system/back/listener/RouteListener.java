package ru.mirea.network.operational.support.system.back.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RouteListener {
    @KafkaListener(
            topics = "routes"
    )
    public void listen(ConsumerRecord<String, String> record, Acknowledgment ack) {
        if (record == null || record.value() == null) {
            log.warn("Не удалось получить сообщение: {}", record);
            ack.acknowledge();
            return;
        }

        // do something
        ack.acknowledge();
    }
}
