package rdison.Begining.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Secondone {
    @GetMapping("/trySome")
    String saydemo(){
        return "Do What Did U Love from the Bottom of the heart";
    }
}
