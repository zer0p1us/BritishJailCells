package com.zer0p1us.endpoints.models;

/**
 *
 * @author zer0p1us
 */
public class ProximityData {
    public double duration_seconds;
    public double distance_meters;

    @Override
    public String toString() {
        return "durationSeconds:" + this.duration_seconds + ",distanceMeters:" + this.distance_meters;
    }
}
