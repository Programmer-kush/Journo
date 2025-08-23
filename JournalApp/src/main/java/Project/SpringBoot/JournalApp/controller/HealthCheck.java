package Project.SpringBoot.JournalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//automatically converts any output(return) to json
@RestController
public class HealthCheck {

    @GetMapping("/health-check")
    public String healthcheck() {
        return "ok";
    }

}
