package alex.valker91.controller;

import alex.valker91.model.CollectedMessage;
import alex.valker91.repository.CollectedMessageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class VisualizerController {

    private final CollectedMessageRepository collectedMessageRepository;

    public VisualizerController(CollectedMessageRepository collectedMessageRepository) {
        this.collectedMessageRepository = collectedMessageRepository;
    }

    @GetMapping("/saved-messages")
    public List<CollectedMessage> getSavedMessages() {
        return collectedMessageRepository.findAll();
    }
}
