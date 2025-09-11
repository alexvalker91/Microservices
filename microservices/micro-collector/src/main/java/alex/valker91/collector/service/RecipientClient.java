package alex.valker91.collector.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class RecipientClient {

    private final RestClient restClient;

    public RecipientClient(@Value("${recipient.base-url:http://localhost:8082}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public List<String> fetchMessages() {
        ResponseEntity<List> response = restClient.get().uri("/message").retrieve().toEntity(List.class);
        return response.getBody();
    }
}
