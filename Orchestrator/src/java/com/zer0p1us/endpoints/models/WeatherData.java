package com.zer0p1us.endpoints.models;

/**
 *
 * @author zer0p1us
 */
public class WeatherData {
    public float current_temp_celsius;
    public float seven_day_average_temp_celsius;
    
    @Override
    public String toString() {
        return "currentTime:" + this.current_temp_celsius + ",sevenDayAverageTemp:" + this.seven_day_average_temp_celsius;
    }
}