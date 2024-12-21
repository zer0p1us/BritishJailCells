package com.zer0p1us.endpoints.models.rooms;

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
}