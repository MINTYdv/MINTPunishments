package xyz.mintydev.punishment.util;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import xyz.mintydev.punishment.managers.ConfigManager;
import xyz.mintydev.punishment.managers.LangManager;

public class CalendarUtil {

	public static String getFormatted(Date date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat();
        sdf1.applyPattern(ConfigManager.get().getDateFormat());
        String res = sdf1.format(date);
        return res;
	}
	
	public static long convertTextToMS(String text) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(
                "[d'd'][ ][h'h'][ ][m'm'][ ][s's']").appendPattern("[d'd'][ ][hh'h'][ ][mm'm'][ ][ss's']").toFormatter();

		TemporalAccessor temporalAccessor = null;
		try {
			temporalAccessor = formatter.parse(text);
		}catch(Exception e) {
			return -1;
		}

        return temporalAccessor.getLong(null);
	}
	
    /** 
     * Format the duration of the punishment
     * 
     * @return String formatted duration like : "1 day, 12 hours and 1 second"
     * */
    public static String getDurationFormatted(long duration) {
    	if(duration <= 0) return "N/A";
//    	long duration = endDate.getTime() - startDate.getTime();
    	
    	int seconds = (int) (duration / 1000) % 60 ;
    	int minutes = (int) ((duration / (1000*60)) % 60);
    	int hours   = (int) ((duration / (1000*60*60)) % 24);
    	int days   = (int) ((duration / (1000*60*60*24)) % 24);
    	int months   = (int) ((duration / (1000*60*60*24*30)) % 24);
    	int years   = (int) ((duration / (1000*60*60*24*30*365)) % 24);
    	
    	String res = "";
    	if(years > 0) res += " " + years + " " + (years > 1 ? LangManager.getMessage("time-units.years") : LangManager.getMessage("time-units.year"));
    	if(months > 0) res += " " + months + " " + (months > 1 ? LangManager.getMessage("time-units.months") : LangManager.getMessage("time-units.month"));
    	if(days > 0) res += " " + days + " " + (days > 1 ? LangManager.getMessage("time-units.days") : LangManager.getMessage("time-units.day"));
    	if(hours > 0) res += " " + hours + " " + (hours > 1 ? LangManager.getMessage("time-units.hours") : LangManager.getMessage("time-units.day"));
    	if(minutes > 0) res += " " + minutes + " " + (minutes > 1 ? LangManager.getMessage("time-units.minutes") : LangManager.getMessage("time-units.minute"));
    	if(seconds > 0) res += " " + seconds + " " + (seconds > 1 ? LangManager.getMessage("time-units.seconds") : LangManager.getMessage("time-units.second"));

    	return res.substring(1);
    }
	
    public static TimeUnit getUnit(String s) {
        if(s.toLowerCase().indexOf("y")==s.length()-1){return TimeUnit.YEAR;}
        if(s.toLowerCase().indexOf("mm")==s.length()-1){return TimeUnit.MONTH;}
        if(s.toLowerCase().indexOf("d")==s.length()-1){return TimeUnit.DAY;}
        if(s.toLowerCase().indexOf("h")==s.length()-1){return TimeUnit.HOUR;}
        if(s.toLowerCase().indexOf("m")==s.length()-1){return TimeUnit.MINUTE;}
        if(s.toLowerCase().indexOf("s")==s.length()-1){return TimeUnit.SECOND;}
        return null;
    }
    
    public static long getNumberfromUnit(String s, TimeUnit unit){
        switch (unit){
        	case YEAR:
            	return Integer.parseInt(s.toLowerCase().replaceAll("y",""))*TimeUnit.YEAR.getMilliseconds();
        	case MONTH:
        		return Integer.parseInt(s.toLowerCase().replaceAll("mm",""))*TimeUnit.MONTH.getMilliseconds();
            case DAY:
                return Integer.parseInt(s.toLowerCase().replaceAll("d",""))*TimeUnit.DAY.getMilliseconds();
            case HOUR:
                return Integer.parseInt(s.toLowerCase().replaceAll("h",""))*TimeUnit.HOUR.getMilliseconds();
            case MINUTE:
                return Integer.parseInt(s.toLowerCase().replaceAll("m",""))*TimeUnit.MINUTE.getMilliseconds();
            case SECOND:
                return Integer.parseInt(s.toLowerCase().replaceAll("s",""))*TimeUnit.SECOND.getMilliseconds();
        }
        return 0;
    }
	
}
