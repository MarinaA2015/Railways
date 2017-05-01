package example.project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Inka on 30-Apr-17.
 */
public class DateUtil {
        public static Date addDays(Date date, int days)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, days); //minus number would decrement the days
            return cal.getTime();
        }
        public static String currentDatePlusDays(int num){
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date curentDate = new Date();
            return dateFormat.format(DateUtil.addDays(curentDate, num));

        }

}
