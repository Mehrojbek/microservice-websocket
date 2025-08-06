package uz.dtc.websocket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import uz.dtc.websocket.model.NotificationMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final SimpMessagingTemplate messagingTemplate;

    // Umumiy e'lonlar uchun manzil (topic)
    private static final String BROADCAST_DESTINATION = "/topic/public";

    @RabbitListener(queues = "${app.rabbitmq.queue-name}")
    public void handleMessage(NotificationMessage message) {

        log.info("Received message: {}. Broadcasting to all clients on {}", message, BROADCAST_DESTINATION);

        // Xabarni `/topic/public` manziliga yuboramiz.
        // Bu topic'ga obuna bo'lgan barcha client'lar xabarni oladi.
        messagingTemplate.convertAndSend(BROADCAST_DESTINATION, message);

        log.info("Message successfully broadcasted.");
    }
}