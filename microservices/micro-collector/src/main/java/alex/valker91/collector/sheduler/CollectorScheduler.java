package alex.valker91.collector.sheduler;

import alex.valker91.collector.service.RecipientClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectorScheduler {

    private static final Logger log = LoggerFactory.getLogger(CollectorScheduler.class);

    private final RecipientClient recipientClient;

    public CollectorScheduler(RecipientClient recipientClient) {
        this.recipientClient = recipientClient;
    }

    @Scheduled(fixedDelayString = "${collector.fetch-interval-ms:10000}")
    public void fetchAndLog() {
        log.info("Collector scheduler triggered - fetching messages from recipient...");
        try {
            List<String> messages = recipientClient.fetchMessages();
            log.info("Fetched {} messages: {}", messages == null ? 0 : messages.size(), messages);
        } catch (Exception e) {
            log.warn("Failed to fetch messages from recipient: {}", e.getMessage());
        }
    }
}
