package alex.valker91.sender.api;

import alex.valker91.config.RabbitConfig;
import alex.valker91.dto.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    private final RabbitTemplate rabbitTemplate;

    public NotificationController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<Void> publishNotification(@RequestBody NotificationRequest request) {
        if (request == null || request.getMessage() == null) {
            return ResponseEntity.badRequest().build();
        }
        log.info("Received notification from user='{}'", request.getUser());
        rabbitTemplate.convertAndSend(RabbitConfig.NOTIFICATIONS_QUEUE, request.getMessage());
        log.info("Published message to queue '{}': {}", RabbitConfig.NOTIFICATIONS_QUEUE, request.getMessage());
        return ResponseEntity.accepted().build();
    }
}
