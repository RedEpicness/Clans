package net.mcmortals.clans.mysql;

import net.md_5.bungee.api.plugin.Plugin;

import java.sql.*;
import java.util.logging.Level;

/**
 * Connects to and uses a MySQL database
 * 
 * @author -_Husky_-
 * @author tips48
 */
public class MySQL extends Database {
    private Connection connection;

    /**
     * Creates a new MySQL instance
     * 
     * @param plugin
     *            Plugin instance
     */
    public MySQL(Plugin plugin) {
        super(plugin);
        this.connection = null;
    }

    @Override
    public Connection openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //connection = DriverManager.getConnection("jdbc:mysql://localhost/mcmortals?user=root&password=****");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mysql?user=root&password=");
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not connect to MySQL server! because: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "JDBC Driver not found!");
        }
        return connection;
    }

    @Override
    public boolean checkConnection() {
        return connection != null;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Error closing the MySQL Connection!");
                e.printStackTrace();
            }
        }
    }
}
