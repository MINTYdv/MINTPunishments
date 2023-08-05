package xyz.mintydev.punishment.util;

public enum TimeUnit {
    YEAR("time-units.year", "time-units.years", 86400000*30*12),
    MONTH("time-units.month", "time-units.months", 86400000*30),
    DAY("time-units.day", "time-units.days", 86400000),
    HOUR("time-units.hour","time-units.hours", 3600000),
    MINUTE("time-units.minute", "time-units.minutes", 60000),
    SECOND("time-units.second", "time-units.seconds", 1000);
    
	private String singular, plural;
    private long milliseconds;
    
    TimeUnit(String singular, String plural, long milliseconds) {
    	this.singular = singular;
    	this.plural = plural;
        this.milliseconds=milliseconds;
    }

//    public String getFormat(String timeleft){
//        return replace(Main.config.getString(format),timeleft);
//    }

    public String replace(String s, String timeleft) {
        return s.replaceAll("%"+name().toUpperCase()+"%",(timeleft)+"");
    }
    public long FormatMS(Long time){
        return ((time==0?1:time)/milliseconds);
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