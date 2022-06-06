package com.example.ettalebweatherapp;

public class Weather {
    private Long date;
    private  String timezone;
    private double temp;
    private String icon;
    private double maxTemp;
    private double minTemp;
    private Long sunsetTime;
    private Long sunriseTime;

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public Long getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(Long sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public Long getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(Long sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public Weather(Long date, String timezone, double temp, String icon, double maxTemp, double minTemp, Long sunsetTime, Long sunriseTime) {
        this.date = date;
        this.timezone = timezone;
        this.temp = temp;
        this.icon = icon;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.sunsetTime = sunsetTime;
        this.sunriseTime = sunriseTime;
    }
}
