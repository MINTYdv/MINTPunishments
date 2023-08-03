package xyz.mintydev.punishment.managers.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.configuration.ConfigurationSection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import xyz.mintydev.punishment.MINTPunishment;

public class DatabaseManager {
	
	private final MINTPunishment main;
	private static DatabaseManager instance;
	
	private DatabaseCredentials credentials;
	private HikariDataSource hikariDataSource;
	private HikariConfig hikariConfig;
	
	public DatabaseManager(MINTPunishment main) {
		this.main = main;
		instance = this;
		
		credentials = loadCredentials();
		setup();
	}
	
	/** 
	 * Function called to setup the database/hikari database connection
	 * */
	private void setup() {
		if(hikariConfig != null) return;
		
		hikariConfig = new HikariConfig();
		
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setJdbcUrl(credentials.toURI());
		
		hikariConfig.setPassword(credentials.getPassword());
		hikariConfig.setUsername(credentials.getUser());
		
		hikariConfig.setMaxLifetime(10*60*1000);
		hikariConfig.setIdleTimeout(5*60*1000);
		hikariConfig.setLeakDetectionThreshold(5*60*1000);
		hikariConfig.setConnectionTimeout(60*1000);
		
		this.hikariDataSource = new HikariDataSource(hikariConfig);
	}
	
	public void initPool() {
		setup();
	}
	
	public void closePool() {
		this.hikariDataSource.close();
	}
	
	public Connection getConnection() {
		if(this.hikariDataSource == null) {
			setup();
		}
		
		try {
			return this.hikariDataSource.getConnection();
		} catch (SQLException e) {
			main.getLogger().warning("ERROR : Could not connect to database.");
			e.printStackTrace();
			return null;
		}
	}
	
	/** 
	 * Function called to load database credentials from the config.yml file
	 * */
	private DatabaseCredentials loadCredentials() {
		final ConfigurationSection sec = main.getConfig().getConfigurationSection("mysql");
		final String ip = sec.getString("ip"); 
		final String database = sec.getString("database"); 
		final String user = sec.getString("user"); 
		final String password = sec.getString("password"); 
		
		final int port = sec.getInt("port");
		
		return new DatabaseCredentials(ip, database, user, password, port);
	}

	/* 
	 * Getters & Setters
	 * */
	
	public DatabaseCredentials getCredentials() {
		return credentials;
	}
	
	public static DatabaseManager get() {
		return instance;
	}
	
	
}
