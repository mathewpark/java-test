package timeTest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeOffTestMain {
	private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
	private static DateFormat sdf_date_only = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
	
	public static void main(String[] args) throws ParseException {
		System.out.println(TimeOffTestMain.assertSMSShouldBeSent());
	}
	
	public static boolean assertSMSShouldBeSent() throws ParseException {
	    Calendar calendar = Calendar.getInstance();
	    
	    int weekday = calendar.get(Calendar.DAY_OF_WEEK);
	    /*Calendar.SUNDAY 1
	    Calendar.MONDAY 2
	    Calendar.TUESDAY 3
	    Calendar.WEDNESDAY 4
	    Calendar.THURSDAY 5
	    Calendar.FRIDAY 6
	    Calendar.SATURDAY 7*/
	    
	    System.out.println(String.format("weekday : %d", weekday));
	    
	    switch (weekday) {
	    case 2:
	    case 3:
	    case 4:
	    case 5:
	    case 6:
	    	Date timeOff = sdf.parse(String.format("%s %s", sdf_date_only.format(new Date()), "18:00:00"));
//	    	Date now = new Date();
	    	Date now = sdf.parse(String.format("%s %s", sdf_date_only.format(new Date()), "10:00:00"));
	    	
	    	System.out.println(String.format("timeOff format: %s", sdf.format(timeOff)));
	    	System.out.println(String.format("now format: %s", sdf.format(now)));
	    	
	    	long diff = now.getTime() - timeOff.getTime();
	    	
	    	System.out.println(String.format("diff: %d", diff));
	    	
	    	if (diff > 0) {
	    		System.out.println(String.format("timeOff..."));
	    		
	    		return true;
	    	}
	    	else if (diff < -32400000) {
	    		System.out.println(String.format("timeOff..."));
	    		
	    		return true;
	    	}
	    	else {
	    		System.out.println(String.format("timeOn..."));
	    		
	    		return false;
	    	}
	    case 7:
	    case 1:
	    	return true;
	    }
	    
	    return true;
	}
}