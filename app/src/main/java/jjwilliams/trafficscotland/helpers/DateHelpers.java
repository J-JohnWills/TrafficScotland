package jjwilliams.trafficscotland.helpers;

// Jamie Williams : S2029548

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DateHelpers {

  SimpleDateFormat startEndDate = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
  SimpleDateFormat formatOut = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
  SimpleDateFormat formatIn = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH);

  public Date parseDate(String dateToParse) {

    Date date = new Date();
    try {
      date = formatIn.parse(dateToParse);
      String stringFormatOut = formatOut.format(date);
      date = formatOut.parse(stringFormatOut);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

  public Date parseStartDate(String description) {
    String startDateString;
    Date startDate = new Date();

    if (description.startsWith("Start Date")) {
      try {
        int startOfDate = description.indexOf(",");
        int endOfDate = description.indexOf("-");

        startDateString = description.substring(startOfDate + 2, endOfDate - 1);

        startDate = startEndDate.parse(startDateString);

        String startDateTemp = formatOut.format(startDate);
        startDate = formatOut.parse(startDateTemp);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return startDate;

  }

  public Date parseEndDate(String description) {
    String endDateString;
    Date endDate = new Date();

    if (description.startsWith("Start Date")) {
      try {
        int startOfDate = description.indexOf(",", description.indexOf(",") + 1);
        int endOfDate = description.indexOf("-", description.indexOf("-") + 1);

        endDateString = description.substring(startOfDate + 2, endOfDate - 1);

        endDate = startEndDate.parse(endDateString);

        String startDateTemp = formatOut.format(endDate);
        endDate = formatOut.parse(startDateTemp);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return endDate;
  }

}
