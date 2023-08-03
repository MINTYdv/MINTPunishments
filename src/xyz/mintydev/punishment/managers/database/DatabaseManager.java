package xyz.mintydev.punishment.managers.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

import com.zaxxer.hikari.HikariDataSource;

import xyz.mintydev.punishment.MINTPunishment;

public class DatabaseManager {
	
	private final MINTPunishment main;
	private static DatabaseManager instance;
	
	private HikariDataSource dataSource;
	
	public DatabaseManager(MINTPunishment main) {
		this.main = main;
		instance = this;
		setup();
	}
	
	/** 
	 * Function called to setup the database/hikari database connection
	 * */
	private void setup() {
		dataSource = new DBDataSource().getNewDataSource();
		
		/* In case we can't connect to the MySQL Database, stop the plugin. */
		if(!isConnectionValid()) {
			main.getLogger().log(Level.SEVERE, "Could not establish a connection to the MySQL Database. Stopping the plugin.");
			main.getPluginLoader().disablePlugin(main);
			return;
		}
	}

	public void initPool() {
		setup();
	}
	
	public void closePool() {
		this.dataSource.close();
	}
	
	public Connection getConnection() {
		if(this.dataSource == null) {
			setup();
		}
		
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			main.getLogger().warning("ERROR : Could not connect to database.");
			e.printStackTrace();
			return null;
		}
	}
	
    /**
     * Check whether there is a valid connection to the database.
     *
     * @return whether there is a valid connection
     */
    public boolean isConnectionValid() {
    	if(dataSource == null) return false;
        return dataSource.isRunning();
    }

	/* 
	 * Getters & Setters
	 * */
	
	public static DatabaseManager get() {
		return instance;
	}
	
	
}
