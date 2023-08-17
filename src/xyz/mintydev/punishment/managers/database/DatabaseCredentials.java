package xyz.mintydev.punishment.managers.database;

/**
 * Class responsible of holding the databse credentials
 */
public class DatabaseCredentials {

	private final String ip, database, user, password;
	private final int port;

	public DatabaseCredentials(String ip, String database, String user, String password, int port) {
		this.ip = ip;
		this.database = database;
		this.user = user;
		this.password = password;
		this.port = port;
	}

	public boolean isFilled() {
		return port != 0 && ip != null && ip.length() > 5 && database != null && database.length() > 0 && user != null
				&& user.length() > 0 && password != null && password.length() > 0;
	}

	/**
	 * Function called to transform the database credentials to an URI.
	 */
	public String toURI() {
		final StringBuilder sb = new StringBuilder();

		sb.append("jdbc:mysql://").append(ip).append(":").append(port).append("/").append(database);

		return sb.toString();
	}

	/*
	 * Getters & Setters
	 */

	public int getPort() {
		return port;
	}

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
