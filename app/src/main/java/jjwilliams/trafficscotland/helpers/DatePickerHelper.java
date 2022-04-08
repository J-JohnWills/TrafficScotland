package jjwilliams.trafficscotland.helpers;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DatePickerHelper {

  private TextInputEditText dateInput;
  private long startDate;
  private long endDate;
  private Calendar calendar;

  public DatePickerHelper(TextInputEditText dateInput) {
    this.dateInput = dateInput;
  }

  public MaterialDatePicker<Long> buildPicker(TextInputEditText pDateInput) {
    TextInputEditText dateInput = pDateInput;
    dateInput.setKeyListener(null);

    // Calender
    calendar = Calendar.getInstance(Locale.ENGLISH);
    startDate = calendar.getTimeInMillis();
    calendar.roll(Calendar.MONTH, 1);
    endDate = calendar.getTimeInMillis();

    CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
    constraintsBuilder.setStart(startDate);
    constraintsBuilder.setEnd(endDate);

    // Datepicker builder
    MaterialDatePicker.Builder<Long> dateBuilder = MaterialDatePicker.Builder.datePicker();
    dateBuilder.setTitleText("Choose a date");
    dateBuilder.setCalendarConstraints(constraintsBuilder.build());
    dateBuilder.setSelection(startDate);

    // Build and return the datepicker
    return dateBuilder.build();
  }

  public String today() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    Date today = Calendar.getInstance().getTime();
    return dateFormat.format(today);
  }

//  public String selectedDate() {
//    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//    Date selectedDate =
//  }

  public boolean validate(long dateToValidate) {
    return dateToValidate > startDate;
  }
}
