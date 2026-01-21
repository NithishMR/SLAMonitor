package com.nithish.SLAMonitor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {
    @RequestMapping("/demo")
    public String welcomeMessage(){
        return "This endpoint works";
    }
}
