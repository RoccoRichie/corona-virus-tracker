package com.roccorichie.coronavirustracker.models;

public class LocationDeathStats {

    private String state;
    private String country;
    private int latestTotalDeathCases;
    private int diffFromPreviousDayDeaths;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalDeathCases() {
        return latestTotalDeathCases;
    }

    public void setLatestTotalDeathCases(int latestTotalDeathCases) {
        this.latestTotalDeathCases = latestTotalDeathCases;
    }

    @Override
    public String toString() {
        return "LocationDeathStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalDeathCases +
                '}';
    }

    public int getDiffFromPreviousDayDeaths() {
        return diffFromPreviousDayDeaths;
    }

    public void setDiffFromPreviousDayDeaths(int diffFromPreviousDayDeaths) {
        this.diffFromPreviousDayDeaths = diffFromPreviousDayDeaths;
    }
}
