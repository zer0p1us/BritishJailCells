package com.zer0p1us.core;

import com.zer0p1us.core.misc.ConfigReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author zer0p1us
 */
public class Database {
    private Connection connection;
    private String CONNECTION_STRING;
    
    public Database() {
        loadProperties();
        RunQuery();
    }
    
    private void loadProperties() {
        ConfigReader reader = new ConfigReader();
        this.CONNECTION_STRING = reader.getDatabaseUrl()
                + "user=" + reader.getDatabaseUser() +';'
                + "password=" + reader.getDatabasePassword() +';'
                + "loginTimeout=" + reader.getDatabaseTimeout();
        
    }
    
    private void RunQuery() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.connection = DriverManager.getConnection(this.CONNECTION_STRING);
            Statement statement = this.connection.createStatement();
            ResultSet query_result = statement.executeQuery("SELECT TOP (1000) * FROM [dbo].[rooms]");
            while (query_result.next()){
                for (int i = 1; i < 6 + 1; i++){
                    System.out.println(query_result.getString(i));
                }
            }
        } catch (SQLException ex) {
            System.err.print(ex);
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.err.print(ex);
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
