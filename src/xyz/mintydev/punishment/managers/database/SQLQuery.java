package xyz.mintydev.punishment.managers.database;

public enum SQLQuery {

	CREATE_TABLE_PUNISHMENT("");
	
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
