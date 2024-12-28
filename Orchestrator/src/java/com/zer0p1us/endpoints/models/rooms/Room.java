package com.zer0p1us.endpoints.models.rooms;

import com.zer0p1us.endpoints.models.Coordinates;
import com.zer0p1us.endpoints.models.WeatherData;
import com.zer0p1us.service.GeoCodingService;
import com.zer0p1us.service.WeatherService;

/**
 *
 * @author zer0p1us
 */
public class Room{
    public int id;
    public String name;
    public Location location;
    public Details details;
    public int price_per_month_gbp;
    public String availability_date;
    public String[] spoken_languages;
    public Coordinates coordinates;
    public WeatherData weather_data;

    public Room fetchCoordinates() {
        GeoCodingService geoService = GeoCodingService.getInstance();
        this.coordinates = geoService.getCoordinatesFromPostcode(this.location.postcode.replaceAll(" ", ""));
        return this;
    }
    
    public Room fetchWeatherData() {
        WeatherService weatherService = WeatherService.getInstance();
        this.weather_data = weatherService.getWeather(this.coordinates);
        return this;
    }
}