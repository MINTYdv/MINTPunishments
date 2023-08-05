package xyz.mintydev.punishment.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtil {

	public static String getFormatted(Date date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat();
        sdf1.applyPattern("dd/MM/yyyy HH:mm:ss.SS");
        String res = sdf1.format(date);
        return res;
	}
	
}
