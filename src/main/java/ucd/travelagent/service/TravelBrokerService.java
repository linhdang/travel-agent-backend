package ucd.travelagent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TravelBrokerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TravelBrokerService.class);

    @Value("${agent.name}")
    private String agentName;

    @Value("${travelBroker.url}")
    private String travelBrokerUrl;

    public void register() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> checkIfAgentRegistered
                    = restTemplate.getForEntity(travelBrokerUrl + "/travel-broker/planners/"+agentName,String.class);
            if (checkIfAgentRegistered.getStatusCode() == HttpStatus.OK) {
                LOGGER.info("Agent {} is registered", agentName);
                return; //
            }
        } catch (HttpClientErrorException.NotFound e){

            HttpEntity<String> request = new HttpEntity<String>("" +
                    "{\n" +
                    "\"name\": \"" + agentName + "\",\n" +
                    "\"chosenFlightProviders\": [\"Ryanair\", \"AerLingus\",\"Flybee\"],\n" +
                    "\"chosenAccommodationProviders\": [\"Sheraton\", \"Carlton\"]\n" +
                    "}");
            ResponseEntity<String> response
                    = restTemplate.postForEntity(travelBrokerUrl + "/travel-broker/planners", request, String.class);
        }
    }

    public Optional<String> requestNewPlan(String departure, String arrival, long from, long to) throws InterruptedException {
        LOGGER.info("request plan for departure [{}], arrival [{}], from [{}], to [{}]", departure, arrival, from, to);
        RestTemplate restTemplate = new RestTemplate();
        String customerId = agentName + "_" + System.currentTimeMillis();
        HttpEntity<String> request = new HttpEntity<>("{\n" +
                "\"from\": " + from + ", \n" +
                "\"to\": " + to + ", \n" +
                "\"departure\": \"" + departure + "\",\n" +
                "\"arrival\": \"" + arrival + "\",\n" +
                "\"accomodationType\": \"ONE_BED\",\n" +
                "\"name\": \"" + customerId + "\",\n" +
                "\"plannerId\": \"" + agentName + "\"\n" +
                "}");
        ResponseEntity<String> response
                = restTemplate.postForEntity(travelBrokerUrl + "/travel-broker/plans", request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Thread.sleep(5000); // sleep 1s
            try {
                ResponseEntity<String> plan
                        = restTemplate.getForEntity(travelBrokerUrl + "/" + agentName + "/plans/" + customerId, String.class);
                return Optional.ofNullable(plan.getBody());
            } catch (HttpClientErrorException.NotFound e) {
                LOGGER.info("cannot find plan");
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    @EventListener
    public void registerAgentName(ContextRefreshedEvent event) {
        register();
    }

}
