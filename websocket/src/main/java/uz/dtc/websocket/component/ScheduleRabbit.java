package uz.dtc.websocket.component;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.dtc.websocket.model.NotificationMessage;

import java.util.Random;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class ScheduleRabbit {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange-name}")
    private String exchangeName;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    @Scheduled(fixedRate = 2000)
    public void sendData(){

        Random random = new Random();
        int userId = random.nextInt(20);

        NotificationMessage payload = new NotificationMessage(String.valueOf(userId), userId + "-message");

        // Xabarni RabbitMQ'ga yuborish
        rabbitTemplate.convertAndSend(exchangeName, routingKey, payload);
    }

}
