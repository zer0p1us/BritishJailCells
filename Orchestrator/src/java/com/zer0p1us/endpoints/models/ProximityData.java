package com.zer0p1us.endpoints.models;

/**
 *
 * @author zer0p1us
 */
public class ProximityData {
    public double durationSeconds;
    public double distanceMeters;

    @Override
    public String toString() {
        return "durationSeconds:" + this.durationSeconds + ",distanceMeters:" + this.distanceMeters;
    }
}
