package jjwilliams.trafficscotland.controllers;

// Jamie Williams : S2029548

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.adapters.LookupListAdapter;
import jjwilliams.trafficscotland.data.TrafficScotlandController;
import jjwilliams.trafficscotland.helpers.DateHelper;
import jjwilliams.trafficscotland.helpers.DatePickerHelper;
import jjwilliams.trafficscotland.models.TrafficScotlandFeed;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;

public class LookupController extends Fragment {

  // Threading
  ExecutorService executor = Executors.newSingleThreadExecutor();
  Handler handler = new Handler(Looper.getMainLooper());

  TrafficScotlandFeed trafficScotlandFeed = new TrafficScotlandFeed();
  DateHelper dateHelper = new DateHelper();

  // View 1
  private Button searchButton, clearButton;
  private TextInputEditText dateInput, roadInput;
  private ListView listView1;

  // View 2
  private Button returnButton;
  private ListView listView;

  // variables
  private String dateSelected;
  private String searchTerm;
  private final ArrayList<TrafficScotlandItem> trafficScotlandItems = new ArrayList<>();
  private ArrayList<TrafficScotlandItem> filteredItems = new ArrayList<>();


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    getFeeds();

    // Layout
    View root = inflater.inflate(R.layout.fragment_lookup, container, false);

    // View 1
    searchButton = root.findViewById(R.id.lookup_button_search);
    clearButton = root.findViewById(R.id.lookup_button_clear);
    dateInput = root.findViewById(R.id.lookup_date_field);
    roadInput = root.findViewById(R.id.lookup_search_field);
    listView1 = root.findViewById(R.id.lookup_list_view1);

    // View 2

    // DatePicker
    DatePickerHelper pickerHelper = new DatePickerHelper(dateInput);
    MaterialDatePicker<Long> datePicker = pickerHelper.buildPicker(dateInput);
    dateInput.setText(pickerHelper.today());

    // OnClicks
    searchButton.setOnClickListener(view -> {
      if (roadInput.getText().length() == 0) {
        Toast toast = Toast.makeText(getContext(),
                "Please enter a road to search by",
                Toast.LENGTH_LONG);
        toast.show();
      } else {
        try {
          InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(dateInput.getWindowToken(), 0);
        } catch (Exception e) {
          e.printStackTrace();
        }
        searchTerm = roadInput.getText().toString();

        executor.execute(() -> {
          filterCurrentIncidentsAndRoadworks(dateSelected, searchTerm);

          handler.post(() -> {
//            HomeListAdapter adapter = new HomeListAdapter(this.getContext(), filteredItems);
//            listView1.setAdapter(adapter);
            LookupListAdapter adapter1 = new LookupListAdapter(this.getContext(), filteredItems);
            listView1.setAdapter(adapter1);
          });
        });
      }
    });

    dateInput.setOnClickListener(view -> datePicker.show(getParentFragmentManager(), "Date Picker"));

    datePicker.addOnPositiveButtonClickListener(selection -> {
      if (pickerHelper.validate(Long.parseLong(selection.toString()))) {
        String dateString = datePicker.getHeaderText();
        dateInput.setText(dateHelper.parseDateFromPicker(dateString));
        // Saving the date for use in search
        dateSelected = dateHelper.parseDateFromPicker(dateString);


      } else {
        Toast toast = Toast.makeText(getContext(),
                "Select a date from today onwards",
                Toast.LENGTH_LONG);
        toast.show();
      }
    });

    // Reset the inputs on click
    clearButton.setOnClickListener(view -> {
      dateInput.setText(pickerHelper.today());
      roadInput.setText("");
    });
    return root;
  }

  private void filterCurrentIncidentsAndRoadworks(String date, String searchTerm) {
    Log.i("road is", searchTerm);
    // Empty filtered arrayList
    filteredItems.clear();
    Date dateToFilter = new Date();
    // TODO: move the date
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    try {
      dateToFilter = format.parse(String.valueOf(dateInput.getText()));

    } catch (ParseException e) {
      e.printStackTrace();
    }


    for (TrafficScotlandItem item : trafficScotlandItems) {
      boolean isInsideDate = item.getStartDate().before(dateToFilter) &&
              item.getEndDate().after(dateToFilter);

      if (item.getStartDate().compareTo(dateToFilter) == 0 ||
              item.getEndDate().compareTo(dateToFilter) == 0 ||
              isInsideDate) {
        if (item.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
          filteredItems.add(item);
        }
      }
    }
  }

  public void getFeeds() {
    executor.execute(() -> {
      try {
        TrafficScotlandController controller = new TrafficScotlandController();
        trafficScotlandFeed = controller.getCurrentIncidents();
        trafficScotlandItems.addAll(trafficScotlandFeed.getTrafficScotlandItems());

        trafficScotlandFeed = controller.getRoadworks();
        trafficScotlandItems.addAll(trafficScotlandFeed.getTrafficScotlandItems());

        trafficScotlandFeed = controller.getPlannedRoadworks();
        trafficScotlandItems.addAll(trafficScotlandFeed.getTrafficScotlandItems());

      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}