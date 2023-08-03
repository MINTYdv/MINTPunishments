package xyz.mintydev.punishment.managers.database;

public enum SQLQuery {

	CREATE_TABLE_PUNISHMENT(
        "CREATE TABLE IF NOT EXISTS `mpunishments_active` ("+
		"`id` int NOT NULL AUTO_INCREMENT," +
        "`type` VARCHAR(16) NULL DEFAULT NULL," +
        "`uuid` VARCHAR(35) NULL DEFAULT NULL," +
        "`name` VARCHAR(16) NULL DEFAULT NULL," +
        "`operator` VARCHAR(16) NULL DEFAULT NULL," +
        "`start` LONG DEFAULT NULL," +
        "`end` LONG DEFAULT NULL," +
        "`reason` VARCHAR(255) NULL DEFAULT NULL," +
        "PRIMARY KEY (`id`))"
	),
	CREATE_TABLE_EXPIRED(
        "CREATE TABLE IF NOT EXISTS `mpunishments_expired` ("+
		"`id` int NOT NULL AUTO_INCREMENT," +
        "`type` VARCHAR(16) NULL DEFAULT NULL," +
        "`uuid` VARCHAR(35) NULL DEFAULT NULL," +
        "`name` VARCHAR(16) NULL DEFAULT NULL," +
        "`operator` VARCHAR(16) NULL DEFAULT NULL," +
        "`start` LONG DEFAULT NULL," +
        "`end` LONG DEFAULT NULL," +
        "`reason` VARCHAR(255) NULL DEFAULT NULL," +
        "PRIMARY KEY (`id`))"
	);
	
	private final String mySqlQuery;
	
	SQLQuery(String mySqlQuery){
		this.mySqlQuery = mySqlQuery;
	}
	
	/*
	 * Getters & Setters
	 * */
	
	public String getMySQLQuery() {
		return mySqlQuery;
	}
	
}
