package jjwilliams.trafficscotland.helpers;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelpers {

  public Date parsePubDate(String dateToParse) {
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    try {
      date = dateFormat.parse(dateToParse);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

  public void parseStartAndEndDate(String description) {

    try {
      String startDateSubString;
      String endDateSubString;

      int startStart = description.indexOf(",");
      int startEnd = description.indexOf("-");
      startDateSubString = description.substring(startStart + 2, startEnd);
      Log.i("Inside dateHelpers", "startDateString is: " + startDateSubString);

      int endStart = description.indexOf(",", description.indexOf(",") + 1);
      int endEnd = description.indexOf("-", description.indexOf("-") + 1);
      endDateSubString = description.substring(endStart + 2, endEnd);
      Log.i("Inside dateHelpers", "endDateString is: " + endDateSubString);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
