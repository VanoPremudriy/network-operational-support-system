package ru.mirea.network.operational.support.system.back.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import ru.mirea.network.operational.support.system.back.service.RouteService;
import ru.mirea.network.operational.support.system.route.api.route.kafka.Route;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteListener {
    private final RouteService routeService;

    @KafkaListener(
            topics = "${listener.topic}"
    )
    public void listen(ConsumerRecord<String, Route> record, Acknowledgment ack) {
        if (record == null || record.value() == null) {
            log.warn("Не удалось получить сообщение: {}", record);
            ack.acknowledge();
            return;
        }

        try {
            routeService.createRoute(record.value());
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Не удалось обработать сообщение: {}", record, e);
        }
    }
}
