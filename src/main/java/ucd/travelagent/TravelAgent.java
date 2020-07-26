package ucd.travelagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ucd")
public class TravelAgent {

    public static void main(String[] args) {
        SpringApplication.run(TravelAgent.class, args);
    }

}
