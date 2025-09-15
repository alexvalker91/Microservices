package alex.valker91.service.impl;

import alex.valker91.model.CollectedMessage;
import alex.valker91.repository.CollectedMessageRepository;
import alex.valker91.service.CollectedMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectedMessageServiceImpl implements CollectedMessageService {

    private final CollectedMessageRepository collectedMessageRepository;

    public CollectedMessageServiceImpl(CollectedMessageRepository collectedMessageRepository) {
        this.collectedMessageRepository = collectedMessageRepository;
    }

    @Transactional
    @Override
    public int saveAllMessages(List<String> messages) {
        if (messages == null || messages.isEmpty()) {
            return 0;
        }

        List<CollectedMessage> collectedMessage = new ArrayList<>();

        for (String message : messages) {
            if (message == null) {
                continue;
            }
            collectedMessage.add(new CollectedMessage(null, message));
        }
        collectedMessageRepository.saveAll(collectedMessage);
        return collectedMessage.size();
    }
}
