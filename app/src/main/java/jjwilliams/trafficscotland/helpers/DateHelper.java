package jjwilliams.trafficscotland.helpers;

// Jamie Williams : S2029548

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateHelper {
  private final SimpleDateFormat formatFromDescription = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
  private final SimpleDateFormat formatFromPubDate = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH);
  private final SimpleDateFormat formatFromDatePicker = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
  private final SimpleDateFormat formatOut = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

  // parse start date from the description string of traffic item
  public Date parseStartDate(String description) {
    Date startDate = new Date();
    String startDateString;

    if (description.startsWith("Start Date")) {
      try {
        startDateString = description.substring(description.indexOf(",") + 2,
                description.indexOf("-") - 1);
        startDate = formatFromDescription.parse(startDateString);
        if (startDate != null) {
          startDateString = formatOut.format(startDate);
        }
        startDate = formatOut.parse(startDateString);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return startDate;
  }

  // parse end date from the description string of traffic item
  public Date parseEndDate(String description) {
    Date endDate = new Date();
    String endDateString;

    if (description.startsWith("Start Date")) {
      try {
        endDateString = description.substring(
                description.indexOf(",", description.indexOf(",") + 1) + 2,
                description.indexOf("-", description.indexOf("-") + 1) - 1);
        endDate = formatFromDescription.parse(endDateString);
        if (endDate != null) {
          endDateString = formatOut.format(endDate);
        }
        endDate = formatOut.parse(endDateString);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return endDate;
  }

  // parse the pubDate from the <pubDate> of traffic item
  public Date parsePubDate(String dateToParse) {
    Date pubDate = new Date();
    String pubDateString = "";
    try {
      pubDate = formatFromPubDate.parse(dateToParse);
      if (pubDate != null) {
        pubDateString = formatOut.format(pubDate);
      }
      pubDate = formatOut.parse(pubDateString);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return pubDate;
  }

  // parse the date from the Datepicker
  public String parseDateFromPicker(String dateToParse) {
    Date date;
    String returnDate = "";
    try {
      date = formatFromDatePicker.parse(dateToParse);
      if (date != null) {
        returnDate = formatOut.format(date);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnDate;
  }

  // find duration between two dates
  public long parseDuration(Date startDateIn, Date endDateIn) {

    long diffInMills = Math.abs(endDateIn.getTime() - startDateIn.getTime());

    return TimeUnit.DAYS.convert(diffInMills, TimeUnit.MILLISECONDS);
  }
}
