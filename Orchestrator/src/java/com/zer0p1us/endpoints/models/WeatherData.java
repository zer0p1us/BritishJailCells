package com.zer0p1us.endpoints.models;

/**
 *
 * @author zer0p1us
 */
public class WeatherData {
    public float currentTemp;
    public float sevenDayAverageTemp;
    
    @Override
    public String toString() {
        return "currentTime:" + this.currentTemp + ",sevenDayAverageTemp:" + this.sevenDayAverageTemp;
    }
}