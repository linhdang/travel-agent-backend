package ucd.travelagent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TravelBrokerService {

    @Value("${agent.name}")
    private String agentName;

    @Value("${travelBroker.url}")
    private String travelBrokerUrl;

    public void register() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>("" +
                "{\n" +
                "\"name\": \"" + agentName + "\",\n" +
                "\"chosenFlightProviders\": [\"Ryanair\", \"AerLingus\",\"Flybee\"],\n" +
                "\"chosenAccommodationProviders\": [\"Sheraton\", \"Carlton\"]\n" +
                "}");
        ResponseEntity<String> response
                = restTemplate.postForEntity(travelBrokerUrl + "/planners", request, String.class);
    }

    @EventListener
    public void registerAgentName(ContextRefreshedEvent event) {
        register();
    }

}
