package xyz.mintydev.punishment.util;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class CalendarUtil {

	public static String getFormatted(Date date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat();
        sdf1.applyPattern("dd/MM/yyyy HH:mm:ss.SS");
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
	
}
