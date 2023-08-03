package xyz.mintydev.punishment.managers.database;

/** 
 * Class responsible of holding the databse credentials
 * */
public class DatabaseCredentials {

	private final String ip,database,user,password;

	public DatabaseCredentials(String ip, String database, String user, String password) {
		this.ip = ip;
		this.database = database;
		this.user = user;
		this.password = password;
	}

	public boolean isFilled() {
		return ip != null && ip.length() > 5 && database != null && database.length() > 0 && user != null && user.length() > 0 && password != null && password.length() > 0;
	}
	
	/* 
	 * Getters & Setters
	 * */
	
	public String getIp() {
		return ip;
	}

	public String getDatabase() {
		return database;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
	
	
	
}
