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
     * 
     * @param conditions where close for data
     * @return Rooms initialised with all the data
     */
    public Rooms GetRooms(String conditions) {
        Rooms rooms = new Rooms();
        rooms.rooms = new ArrayList<Room>();
        ResultSet resultSet = RunQuery("SELECT * FROM [dbo].[vw_room_details]");
        
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
