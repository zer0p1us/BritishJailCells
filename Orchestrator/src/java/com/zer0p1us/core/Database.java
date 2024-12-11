package com.zer0p1us.core;

import com.zer0p1us.core.misc.ConfigReader;
import com.zer0p1us.endpoints.models.rooms.Details;
import com.zer0p1us.endpoints.models.rooms.Location;
import com.zer0p1us.endpoints.models.rooms.Room;
import com.zer0p1us.endpoints.models.rooms.Rooms;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zer0p1us
 */
public class Database {
    private Connection connection;
    private String CONNECTION_STRING;
    
    public Database() {
        loadProperties();
    }
    
    private void loadProperties() {
        ConfigReader reader = new ConfigReader();
        this.CONNECTION_STRING = reader.getDatabaseUrl()
                + "user=" + reader.getDatabaseUser() +';'
                + "password=" + reader.getDatabasePassword() +';'
                + "loginTimeout=" + reader.getDatabaseTimeout();
        
    }

    private ResultSet RunQuery(String sqlQuery) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.connection = DriverManager.getConnection(this.CONNECTION_STRING);
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);
            return result;
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * All the params are nullable types, if they are they aren't part of the query
     * @param searchTerms search terms
     * @param maxMonthlyRent max monthly rent
     * @param furnished furnished
     * @param liveInLandlord live in landlord
     * @param maxSharedWith max shared with
     * @param billsIncluded bills included
     * @param bathroomShared bathroom shared
     * @return 
     */
    public Rooms GetRooms(String searchTerms,
                          Integer maxMonthlyRent,
                          Boolean furnished,
                          Boolean liveInLandlord,
                          Integer maxSharedWith,
                          Boolean billsIncluded,
                          Boolean bathroomShared) {
        Rooms rooms = new Rooms();
        rooms.rooms = new ArrayList<Room>();
        
        StringBuilder statement = new StringBuilder("EXEC SearchRoomDetails");
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("@SearchTerms", searchTerms);
        parameters.put("@Furnished", furnished);
        parameters.put("@LiveInLandlord", liveInLandlord);
        parameters.put("@MaxSharedWith", maxSharedWith);
        parameters.put("@BillsIncluded", billsIncluded);
        parameters.put("@BathroomShared", bathroomShared);
        parameters.put("@MaxMonthlyRent", maxMonthlyRent);
        
        parameters.forEach((key, value) -> {
            if (value != null) {
                statement.append(" ").append(key).append(" = ");
                if (value instanceof String) {
                    statement.append("'").append(value).append("',");
                } else { 
                    statement.append(value).append(",");
                }
            }
        });
        
        // Remove the trailing comma if parameters were added
        if (statement.charAt(statement.length() - 1) == ',') {
            statement.deleteCharAt(statement.length() - 1);
        }
        System.out.println(statement.toString());
        ResultSet resultSet = RunQuery(statement.toString());
        
        try {            
            while (resultSet.next()) {
                Room room = new Room();
                
                room.id = resultSet.getInt("room_id");
                room.name = resultSet.getString("name");
                room.availability_date = resultSet.getString("availability_date");
                room.price_per_month_gbp = resultSet.getInt("price_per_month_gbp");
                
                Location location = new Location();
                location.city = resultSet.getString("city");
                location.county = resultSet.getString("county");
                location.postcode = resultSet.getString("postcode");
                room.location = location;
                
                Details details = new Details();
                details.furnished = resultSet.getBoolean("furnished");
                details.bathroom_shared = resultSet.getBoolean("bathroom_shared");
                details.bills_included = resultSet.getBoolean("bills_included");
                details.live_in_landlord = resultSet.getBoolean("live_in_landlord");
                details.shared_with = resultSet.getInt("shared_with");
                details.amenities = resultSet.getString("amenities").split(", ");
                room.details = details;
                
                room.spoken_languages = resultSet.getString("languages").split(", ");
                
                rooms.rooms.add(room);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        return rooms;
    }
}
