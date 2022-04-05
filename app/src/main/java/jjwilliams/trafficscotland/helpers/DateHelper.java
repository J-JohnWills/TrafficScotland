package jjwilliams.trafficscotland.helpers;

// Jamie Williams : S2029548

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

  SimpleDateFormat startEndDate = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
  SimpleDateFormat formatOut = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
  SimpleDateFormat formatIn = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH);

  public Date parseStartDate(String description) {
    String startDateString;
    Date startDate = new Date();

    if (description.startsWith("Start Date")) {
      try {
        int start = description.indexOf(",");
        int end = description.indexOf("-");

        startDateString = description.substring(start + 2, end - 1);

        startDate = startEndDate.parse(startDateString);

        String startDateTemp = formatOut.format(startDate);
        startDate = formatOut.parse(startDateTemp);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return startDate;
  }
}
