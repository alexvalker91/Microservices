package alex.valker91.model;

import jakarta.persistence.*;

@Entity
@Table(schema = "public", name = "collected_messages_table")
public class CollectedMessage {

    public CollectedMessage() {}

    public CollectedMessage(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "collected_message")
    private String message;
}
