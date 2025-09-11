package alex.valker91.recipient.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RecipientMetrics {

    private final AtomicInteger randomQueueDepth;
    private final Counter dummyProcessedCounter;
    private final Timer processingTimer;
    private final Random random;

    public RecipientMetrics(MeterRegistry meterRegistry) {
        this.randomQueueDepth = new AtomicInteger(0);
        this.random = new Random();

        Gauge.builder("recipient_random_queue_depth", randomQueueDepth, AtomicInteger::get)
                .description("Randomized gauge representing a queue depth sample")
                .baseUnit("messages")
                .register(meterRegistry);

        this.dummyProcessedCounter = Counter.builder("recipient_dummy_processed_total")
                .description("Randomly increments to simulate processed messages")
                .baseUnit("messages")
                .register(meterRegistry);

        this.processingTimer = Timer.builder("recipient_processing_time")
                .description("Simulated processing time for a message")
                .publishPercentiles(0.5, 0.9, 0.99)
                .maximumExpectedValue(Duration.ofSeconds(2))
                .register(meterRegistry);
    }

    @Scheduled(fixedDelayString = "${recipient.poll-interval-ms:5000}")
    public void updateRandomMetrics() {
        int nextDepth = random.nextInt(101); // 0 - 100
        randomQueueDepth.set(nextDepth);

        int increments = 1 + random.nextInt(5); // 1 - 5
        for (int i = 0; i < increments; i++) {
            dummyProcessedCounter.increment();
        }

        long simulatedMs = 50L + random.nextInt(200); // 50 - 249
        processingTimer.record(simulatedMs, TimeUnit.MILLISECONDS);
    }
}

