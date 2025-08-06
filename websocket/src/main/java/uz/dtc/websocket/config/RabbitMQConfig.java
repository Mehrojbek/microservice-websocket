package uz.dtc.websocket.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // application.yml'dagi qiymatlarni o'qiymiz
    @Value("${app.rabbitmq.exchange-name}")
    private String exchangeName;

    @Value("${app.rabbitmq.queue-name}")
    private String queueName;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    // 1. Navbat (Queue) yaratadigan Bean
    @Bean
    public Queue queue() {
        // 'true' - bu navbat "durable" ekanligini bildiradi, ya'ni RabbitMQ
        // qayta ishga tushganda ham navbat o'chib ketmaydi.
        return new Queue(queueName, true);
    }

    // 2. Topic Exchange yaratadigan Bean
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    // 3. Navbatni Exchange'ga routing key orqali bog'laydigan Bean
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        // Bu bog'lanish "user.notification" bilan boshlangan barcha xabarlarni
        // (masalan, "user.notification.order", "user.notification.message")
        // bizning "notifications.queue" navbatimizga yo'naltiradi.
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}