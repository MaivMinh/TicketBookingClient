package utils;

import java.util.Calendar;
import java.util.Date;

public class ConverDateToString {
  public static String convertDateToString(Date selectedDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(selectedDate);
    return  calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
  }
}
