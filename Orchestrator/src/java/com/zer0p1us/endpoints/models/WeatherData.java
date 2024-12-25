package com.zer0p1us.endpoints.models;

/**
 *
 * @author zer0p1us
 */
public class WeatherData {
    public float currentTempCelsius;
    public float sevenDayAverageTempCelsius;
    
    @Override
    public String toString() {
        return "currentTime:" + this.currentTempCelsius + ",sevenDayAverageTemp:" + this.sevenDayAverageTempCelsius;
    }
}