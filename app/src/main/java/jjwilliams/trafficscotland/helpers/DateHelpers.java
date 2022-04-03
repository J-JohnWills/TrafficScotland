package jjwilliams.trafficscotland.helpers;

// Jamie Williams : S2029548

import android.util.Log;

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

  public ArrayList<Date> parseStartAndEndDate(String description) {

    ArrayList<Date> dates = new ArrayList<>();
    try {
      String startDateSubString;
      String endDateSubString;

      // Get start date string
      int startStart = description.indexOf(",");
      int startEnd = description.indexOf("-");
      startDateSubString = description.substring(startStart + 2, startEnd - 1);


      // Get end date string
      int endStart = description.indexOf(",", description.indexOf(",") + 1);
      int endEnd = description.indexOf("-", description.indexOf("-") + 1);
      endDateSubString = description.substring(endStart + 2, endEnd - 1);


    } catch (Exception e) {
      e.printStackTrace();
    }
    return dates;
  }

  public Date parseStartDate(String description) {
    String startDateString;
    Date startDate = new Date();

    try {
      int startOfDate = description.indexOf(",");
      int endOfDate = description.indexOf("-");

      startDateString = description.substring(startOfDate + 2, endOfDate - 1);

      startDate = startEndDate.parse(startDateString);

      String startDateTemp = formatOut.format(startDate);
      startDate = formatOut.parse(startDateTemp);

      Log.i("DateLogic", "Start Date: " + startDateTemp);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return startDate;
  }

  public Date parseEndDate(String description) {
    String endDateString;
    Date endDate = new Date();

    try {
      int startOfDate = description.indexOf(",", description.indexOf(",") +1);
      int endOfDate = description.indexOf("-", description.indexOf("-") +1);

      endDateString = description.substring(startOfDate + 2, endOfDate - 1);

      endDate = startEndDate.parse(endDateString);

      String startDateTemp = formatOut.format(endDate);
      endDate = formatOut.parse(startDateTemp);

      Log.i("DateLogic", "Start Date: " + startDateTemp);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return endDate;
  }
}
