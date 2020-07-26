package ucd.travelagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ucd.travelagent.service.TravelBrokerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TravelAgentController {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private TravelBrokerService travelBrokerService;

    @Autowired
    public TravelAgentController(TravelBrokerService travelBrokerService) {
        this.travelBrokerService = travelBrokerService;
    }

    @RequestMapping("/request-new-plan")
    public ResponseEntity<String> requestNewPlan(@RequestParam("departure") String departure, @RequestParam("arrival") String arrival,
                                                 @RequestParam("from") String from, @RequestParam("to") String to) throws InterruptedException, ParseException {
        Optional<String> plan = travelBrokerService.requestNewPlan(departure, arrival, dateFormat.parse(from).getTime(), dateFormat.parse(to).getTime());
        return plan.map(s -> ResponseEntity.ok(Objects.requireNonNull(s))).orElseGet(() -> ResponseEntity.notFound().build());
    }



}
