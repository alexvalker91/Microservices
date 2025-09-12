package alex.valker91.recipient.api;

import alex.valker91.recipient.service.MessageCollectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    private final MessageCollectorService collectorService;

    public MessageController(MessageCollectorService collectorService) {
        this.collectorService = collectorService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getAndClear() {
        log.info("GET /message called");
        List<String> messages = collectorService.drainMessages();
        log.info("Returning " + messages.size() + " messages and clearing buffer");
        return ResponseEntity.ok(messages);
    }
}
