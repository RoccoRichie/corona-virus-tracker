package com.roccorichie.coronavirustracker.services;

import com.roccorichie.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

// Make this a spring service
@Service
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";


    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    //Execute this at start - When you construct an instance of this class, execute this method
    // Scheduler runs as a cron say every day
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {

        // Once this list is complied we will add it to the allStats list
        // => Avoids throwing exceptions when user requests allStats
        List<LocationStats> newStats = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        // Print to console
        //System.out.println(httpResponse.body());

        // Convert the string into a String Reader and parse it
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        // header Province/State,Country/Region,Lat,Long,1/22/20,1/23/20,
        // sample data: Beijing,Mainland China,40.1824,116.4142,14,
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));

            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int previousCases = Integer.parseInt(record.get(record.size() - 2));

            locationStats.setLatestTotalCases(latestCases);
            locationStats.setDiffFromPreviousDay(latestCases - previousCases);

            newStats.add(locationStats);
        }
        this.allStats = newStats; // Attempting to be concurrent capable
    }
}
