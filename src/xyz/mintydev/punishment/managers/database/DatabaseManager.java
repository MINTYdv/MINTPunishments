package xyz.mintydev.punishment.managers.database;

import xyz.mintydev.punishment.MINTPunishment;

public class DatabaseManager {
	
	private final MINTPunishment main;
	private static DatabaseManager instance;
	
	public DatabaseManager(MINTPunishment main) {
		this.main = main;
		instance = this;
	}

	/* 
	 * Getters & Setters
	 * */
	
	public static DatabaseManager get() {
		return instance;
	}
	
	
}
