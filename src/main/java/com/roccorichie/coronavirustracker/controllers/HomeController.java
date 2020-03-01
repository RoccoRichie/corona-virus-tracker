package com.roccorichie.coronavirustracker.controllers;

import com.roccorichie.coronavirustracker.models.LocationStats;
import com.roccorichie.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    // Use autowire to bring the service into this controller
    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    // Return the home template - thymeleaf dependency
    @GetMapping("/")
    public String home(Model model){
//        model.addAttribute("testName", "TEST");
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
//        model.addAttribute("locationStats", coronaVirusDataService.getAllStats());
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        return "home"; // map to a html file called home.html
    }

}
