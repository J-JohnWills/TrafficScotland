package jjwilliams.trafficscotland.controllers;

// Jamie Williams : S2029548

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.helpers.DateHelpers;
import jjwilliams.trafficscotland.helpers.DatePickerHelper;

public class LookupController extends Fragment {

  DateHelpers dateHelpers = new DateHelpers();

  // Layout
  private ViewSwitcher viewSwitcher;
  // View 1
  private Button searchButton;
  private TextInputEditText dateInput;
  // View 2
  private Button returnbutton;
  private ListView listView;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    View root = inflater.inflate(R.layout.fragment_lookup, container, false);
    // Layout
    viewSwitcher = root.findViewById(R.id.lookup_view_switch);
    // View 1
    searchButton = root.findViewById(R.id.lookup_button_search);
    dateInput = root.findViewById(R.id.lookup_date_field);


    // View 2
    returnbutton = root.findViewById(R.id.lookup_button_return);
    listView = root.findViewById(R.id.lookup_list_view);

    // DatePicker
    DatePickerHelper pickerHelper = new DatePickerHelper(dateInput);
    MaterialDatePicker<Long> datePicker = pickerHelper.buildPicker(dateInput);
    dateInput.setText(pickerHelper.today());

    // OnClicks
    searchButton.setOnClickListener(view -> viewSwitcher.showNext());

    returnbutton.setOnClickListener(view -> viewSwitcher.showPrevious());

    dateInput.setOnClickListener(view -> datePicker.show(getParentFragmentManager(), "Date Picker"));

    datePicker.addOnPositiveButtonClickListener(selection -> {
      if (pickerHelper.validate(Long.parseLong(selection.toString()))) {
        String dateString = datePicker.getHeaderText();
        dateInput.setText(dateHelpers.pickerThing(dateString));
        // TODO: do stuff

      } else {
        Toast toast = Toast.makeText(getContext(),
                "Select a date from today onwards",
                Toast.LENGTH_LONG);
        toast.show();
      }
    });

    return root;
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}