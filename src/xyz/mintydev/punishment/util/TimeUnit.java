package xyz.mintydev.punishment.util;

public enum TimeUnit {
    YEAR("time-units.year", "time-units.years", 31104000000L),
    MONTH("time-units.month", "time-units.months", 2592000000L),
    DAY("time-units.day", "time-units.days", 86400000),
    HOUR("time-units.hour","time-units.hours", 3600000),
    MINUTE("time-units.minute", "time-units.minutes", 60000),
    SECOND("time-units.second", "time-units.seconds", 1000);
    
	private String singular, plural;
    private long milliseconds;
    
    TimeUnit(String singular, String plural, long milliseconds) {
    	this.singular = singular;
    	this.plural = plural;
        this.milliseconds = milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }
    
    public String getSingular() {
		return singular;
	}
    
    public String getPlural() {
		return plural;
	}
    
}