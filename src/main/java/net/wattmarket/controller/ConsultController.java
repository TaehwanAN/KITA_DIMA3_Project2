package net.wattmarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsultController {

    @GetMapping("/consult")
    public String consult(){
        return "consult";
    }
}
