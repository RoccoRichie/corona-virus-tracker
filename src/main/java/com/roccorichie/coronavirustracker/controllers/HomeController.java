package com.roccorichie.coronavirustracker.controllers;

import com.roccorichie.coronavirustracker.models.LocationStats;
import com.roccorichie.coronavirustracker.services.CoronaVirusDataService;
import com.roccorichie.coronavirustracker.services.CoronaVirusDeathService;
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

    @Autowired
    CoronaVirusDeathService coronaVirusDeathService;

    // Return the home template - thymeleaf dependency
    @GetMapping("/")
    public String home(Model model) {
        // Cases Reported
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPreviousDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        // Deaths Reported
        List<LocationStats> deathStats = coronaVirusDeathService.getAllDeathStats();
        int totalDeathCases = deathStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewDeathCases = deathStats.stream().mapToInt(stat -> stat.getDiffFromPreviousDay()).sum();
        model.addAttribute("deathStats", deathStats);
        model.addAttribute("totalDeathCases", totalDeathCases);
        model.addAttribute("totalNewDeathCases", totalNewDeathCases);

        return "home"; // map to a html file called home.html
    }

}
