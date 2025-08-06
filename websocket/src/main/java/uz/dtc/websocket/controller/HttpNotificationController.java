package uz.dtc.websocket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uz.dtc.websocket.model.NotificationMessage;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HttpNotificationController {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange-name}")
    private String exchangeName;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    /**
     * Bu endpoint endi faqat xabarni RabbitMQ ga yuboradi va darhol javob qaytaradi.
     */
    @GetMapping("/send/{userId}/{message}")
    public ResponseEntity<String> sendNotification(@PathVariable String userId, @PathVariable String message) {

        log.info("Received request to send message to user '{}'. Forwarding to RabbitMQ...", userId);
        NotificationMessage payload = new NotificationMessage(userId, message);

        // Xabarni RabbitMQ'ga yuborish
        rabbitTemplate.convertAndSend(exchangeName, routingKey, payload);

        log.info("Message for user '{}' has been sent to RabbitMQ.", userId);

        // Darhol javob qaytaramiz
        return ResponseEntity.accepted().body("Notification request for user '" + userId + "' has been accepted.");
    }
}