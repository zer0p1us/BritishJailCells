package com.zer0p1us.endpoints.models;

/**
 *
 * @author zer0p1us
 */
public class Coordinates {
    public double latitude;
    public double longitude;
    
    public Coordinates(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    @Override
    public String toString() {
        return "latitude:" + this.latitude + ",longitude:" + this.longitude;
    }
}
