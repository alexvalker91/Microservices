package alex.valker91.recipient.service;

import alex.valker91.recipient.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageCollectorService {

    private static final Logger log = LoggerFactory.getLogger(MessageCollectorService.class);

    private final RabbitTemplate rabbitTemplate;
    private final List<String> messagesBuffer = Collections.synchronizedList(new ArrayList<>());

    public MessageCollectorService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<String> drainMessages() {
        synchronized (messagesBuffer) {
            List<String> copy = new ArrayList<>(messagesBuffer);
            messagesBuffer.clear();
            return copy;
        }
    }

    @Scheduled(fixedDelayString = "${recipient.poll-interval-ms:5000}")
    public void pollQueue() {
        log.info("Polling RabbitMQ for messages...");
        while (true) {
            Object msg = rabbitTemplate.receiveAndConvert(RabbitConfig.NOTIFICATIONS_QUEUE);
            if (msg == null) {
                break;
            }
            String content = String.valueOf(msg);
            log.info("Received message: {}", content);
            messagesBuffer.add(content);
        }
    }
}
