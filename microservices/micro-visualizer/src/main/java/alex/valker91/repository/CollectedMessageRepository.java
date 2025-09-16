package alex.valker91.repository;

import alex.valker91.model.CollectedMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectedMessageRepository extends JpaRepository<CollectedMessage, Long> {
}
