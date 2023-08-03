package xyz.mintydev.punishment.managers.database;

public enum SQLQuery {
	
	CREATE_TABLE_ACTIVE(
        "CREATE TABLE IF NOT EXISTS `mp_active` ("+
		"`id` int NOT NULL AUTO_INCREMENT," +
        "`type` VARCHAR(16) NULL DEFAULT NULL," +
        "`uuid` VARCHAR(35) NULL DEFAULT NULL," +
        "`name` VARCHAR(16) NULL DEFAULT NULL," +
        "`operator` VARCHAR(16) NULL DEFAULT NULL," +
        "`start` BIGINT DEFAULT NULL," +
        "`end` BIGINT DEFAULT NULL," +
        "`reason` VARCHAR(255) NULL DEFAULT NULL," +
        "PRIMARY KEY (`id`))"
	),
	CREATE_TABLE_EXPIRED(
        "CREATE TABLE IF NOT EXISTS `mp_expired` ("+
		"`id` int NOT NULL AUTO_INCREMENT," +
        "`type` VARCHAR(16) NULL DEFAULT NULL," +
        "`uuid` VARCHAR(35) NULL DEFAULT NULL," +
        "`name` VARCHAR(16) NULL DEFAULT NULL," +
        "`operator` VARCHAR(16) NULL DEFAULT NULL," +
        "`start` BIGINT DEFAULT NULL," +
        "`end` BIGINT DEFAULT NULL," +
        "`reason` VARCHAR(255) NULL DEFAULT NULL," +
        "PRIMARY KEY (`id`))"
	),
	SELECT_USER_PUNISHMENTS_UUID("SELECT * FROM `mp_active` WHERE `uuid` = ?"),
	SELECT_USER_PUNISHMENTS_HISTORY_UUID("SELECT * FROM `mp_expired` WHERE `uuid` = ?"),
	SELECT_ALL_PUNISHMENTS("SELECT * FROM `mp_active`"),
	DELETE_PUNISHMENT("DELETE FROM `mp_active` WHERE `id` = ?"),
	SELECT_PUNISHMENTS_ID("SELECT * FROM `mp_active` WHERE `id` = ?");
	
	private final String mySqlQuery;
	
	SQLQuery(String mySqlQuery){
		this.mySqlQuery = mySqlQuery;
	}
	
	/*
	 * Getters & Setters
	 * */
	
	@Override
	public String toString() {
		return mySqlQuery;
	}
	
}
