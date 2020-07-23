package ucd.travelagent.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
public class TravelAgentController {
    @Value("agent.name")
    private String agentName;

    @Value("travelBroker.url")
    private String travelBrokerUrl;

    @RequestMapping("/request-new-plan")
    public ResponseEntity<String> home(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("departure") String departure, @RequestParam("arrival") String arrival) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        String customerId = agentName + "_" + System.currentTimeMillis();
        HttpEntity<String> request = new HttpEntity<>("{\n" +
                "\"from\": 123, \n" +
                "\"to\": 124, \n" +
                "\"departure\": \"" + departure + "\",\n" +
                "\"arrival\": \"" + arrival + "\",\n" +
                "\"accomodationType\": \"ONE_BED\",\n" +
                "\"name\": \"" + customerId + "\",\n" +
                "\"plannerId\": \"" + agentName + "\"\n" +
                "}");
        ResponseEntity<String> response
                = restTemplate.postForEntity(travelBrokerUrl + "/plans", request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Thread.sleep(1000); // sleep 1s

            ResponseEntity<String> plan
                    = restTemplate.postForEntity(travelBrokerUrl + "/" + agentName + "/plans/" + customerId, request, String.class);
            return ResponseEntity.ok(Objects.requireNonNull(plan.getBody()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
