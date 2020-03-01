package com.roccorichie.coronavirustracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Return the home template - thymeleaf dependency
    @GetMapping("/")
    public String home(){
        return "home"; // map to a html file called home.html
    }

}
