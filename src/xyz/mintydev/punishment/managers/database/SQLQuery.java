package xyz.mintydev.punishment.managers.database;

public enum SQLQuery {
	
	CREATE_TABLE_ACTIVE(
        "CREATE TABLE IF NOT EXISTS `mp_active` ("+
		"`id` int NOT NULL AUTO_INCREMENT," +
        "`type` VARCHAR(255) NULL DEFAULT NULL," +
        "`uuid` VARCHAR(255) NULL DEFAULT NULL," +
        "`name` VARCHAR(255) NULL DEFAULT NULL," +
        "`operator` VARCHAR(255) NULL DEFAULT NULL," +
        "`operatorName` VARCHAR(255) NULL DEFAULT NULL," +
        "`start` BIGINT DEFAULT NULL," +
        "`end` BIGINT DEFAULT NULL," +
        "`reason` VARCHAR(255) NULL DEFAULT NULL," +
        "PRIMARY KEY (`id`))"
	),
	CREATE_TABLE_EXPIRED(
        "CREATE TABLE IF NOT EXISTS `mp_expired` ("+
		"`id` int NOT NULL AUTO_INCREMENT," +
        "`type` VARCHAR(255) NULL DEFAULT NULL," +
        "`uuid` VARCHAR(255) NULL DEFAULT NULL," +
        "`name` VARCHAR(255) NULL DEFAULT NULL," +
        "`operator` VARCHAR(255) NULL DEFAULT NULL," +
        "`operatorName` VARCHAR(255) NULL DEFAULT NULL," +
        "`start` BIGINT DEFAULT NULL," +
        "`end` BIGINT DEFAULT NULL," +
        "`reason` VARCHAR(255) NULL DEFAULT NULL," +
        "PRIMARY KEY (`id`))"
	),
    INSERT_PUNISHMENT(
            "INSERT INTO `mp_active` " +
                    "(`type`, `uuid`, `name`, `operator`, `operatorName`, `start`, `end`, `reason`) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    ),
    INSERT_PUNISHMENT_HISTORY(
            "INSERT INTO `mp_expired` " +
            "(`type`, `uuid`, `name`, `operator`, `operatorName`, `start`, `end`, `reason`) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    ),
	SELECT_USER_PUNISHMENTS_UUID("SELECT * FROM `mp_active` WHERE `uuid` = ?"),
	SELECT_EXACT_PUNISHMENT("SELECT * FROM `mp_active` WHERE `uuid` = ? AND `start` = ? AND `type` = ?"),
	SELECT_USER_PUNISHMENTS_HISTORY_UUID("SELECT * FROM `mp_expired` WHERE `uuid` = ?"),
	SELECT_ALL_PUNISHMENTS("SELECT * FROM `mp_active`"),
	DELETE_PUNISHMENT("DELETE FROM `mp_active` WHERE `id` = ?"),
	SELECT_PUNISHMENTS_ID("SELECT * FROM `mp_active` WHERE `id` = ?"),
	DELETE_OLD_PUNISHMENTS("DELETE FROM `mp_active` WHERE `end` <= ?");
	
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
