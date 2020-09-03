package hive.hive;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
	public static String GetCurrentDateTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");  
	    LocalDateTime now = LocalDateTime.now();  
	    return dtf.format(now);
	}
}
