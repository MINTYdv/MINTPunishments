package xyz.mintydev.punishment.managers.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import xyz.mintydev.punishment.managers.ConfigManager;

public class DBDataSource {
	
	private HikariConfig hikariConfig = new HikariConfig();
	
	public DBDataSource() {
		final DatabaseCredentials credentials = ConfigManager.get().loadCredentials();
		
		hikariConfig.setJdbcUrl(credentials.toURI());
		
		hikariConfig.setPassword(credentials.getPassword());
		hikariConfig.setUsername(credentials.getUser());
		
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMaxLifetime(10*60*1000);
		hikariConfig.setIdleTimeout(5*60*1000);
		hikariConfig.setLeakDetectionThreshold(5*60*1000);
		hikariConfig.setConnectionTimeout(60*1000);
	}
	
	public HikariDataSource getNewDataSource() {
		return new HikariDataSource(hikariConfig);
	}
	
}
