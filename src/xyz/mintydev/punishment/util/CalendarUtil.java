package xyz.mintydev.punishment.util;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import xyz.mintydev.punishment.managers.ConfigManager;

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
