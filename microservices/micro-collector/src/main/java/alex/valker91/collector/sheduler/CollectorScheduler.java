package alex.valker91.collector.sheduler;

import alex.valker91.collector.service.RecipientClient;
import alex.valker91.service.CollectedMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectorScheduler {

    private static final Logger log = LoggerFactory.getLogger(CollectorScheduler.class);

    private final RecipientClient recipientClient;
    private final CollectedMessageService collectedMessageService;

    public CollectorScheduler(RecipientClient recipientClient, CollectedMessageService collectedMessageService) {
        this.recipientClient = recipientClient;
        this.collectedMessageService = collectedMessageService;
    }

    @Scheduled(fixedDelayString = "${collector.fetch-interval-ms:10000}")
    public void fetchAndLog() {
        log.info("Collector scheduler triggered - retrieving messages from recipient");
        try {
            List<String> messages = recipientClient.fetchMessages();
            int saved = collectedMessageService.saveAllMessages(messages);
            log.info("Fetched " + (messages == null ? 0 : messages.size()) + " messages: " + messages);
            log.info("Saved " + saved + " messages");
        } catch (Exception e) {
            log.warn("Failed to fetch messages from recipient: " + e.getMessage());
        }
    }
}
