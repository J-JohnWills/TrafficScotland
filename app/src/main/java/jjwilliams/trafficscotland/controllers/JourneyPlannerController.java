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
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jjwilliams.trafficscotland.data.TrafficScotlandController;
import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.helpers.DateHelper;
import jjwilliams.trafficscotland.helpers.DatePickerHelper;
import jjwilliams.trafficscotland.models.TrafficScotlandFeed;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;

public class JourneyPlannerController extends Fragment implements OnMapReadyCallback {

  // Threading
  ExecutorService executor = Executors.newSingleThreadExecutor();
  Handler handler = new Handler(Looper.getMainLooper());

  // Layout
  private GoogleMap googleMap;
  private MapView mapView;
  private TextInputEditText editTextChooseDate, editTextSearchRoad;
  private Button btnSearch;

  // Classes
  private DateHelper dateHelper = new DateHelper();
  // Variables
  private TrafficScotlandFeed trafficScotlandFeed = new TrafficScotlandFeed();
  private ArrayList<TrafficScotlandItem> trafficScotlandItems = new ArrayList<>();
  private ArrayList<TrafficScotlandItem> filteredItems = new ArrayList<>();
  private ArrayList<LatLng> latLngs = new ArrayList<>();
  private String dateSelected;
  private String searchTerm;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    // Layout
    View root = inflater.inflate(R.layout.fragment_journey_planner, container, false);
    // Edit texts
    editTextChooseDate = root.findViewById(R.id.journey_planner_edit_text_choose_date);
    editTextSearchRoad = root.findViewById(R.id.journey_planner_edit_text_search_road);
    // Button/s
    btnSearch = root.findViewById(R.id.journey_planner_button_search);
    // Google Map
    MapsInitializer.initialize(this.getActivity());
    mapView = root.findViewById(R.id.journey_planner_map_view);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(this);

    getFeeds();

    // DatePicker
    DatePickerHelper pickerHelper = new DatePickerHelper(editTextChooseDate);
    MaterialDatePicker<Long> datePicker = pickerHelper.buildPicker(editTextChooseDate);
    editTextChooseDate.setText(pickerHelper.today());


    editTextChooseDate.setOnClickListener(view -> {
      // Statement to catch double click error
      // TODO: write about this in testing
      if (!datePicker.isAdded()) {
        datePicker.show(getParentFragmentManager(), "Date Picker");
      }
    });

    datePicker.addOnPositiveButtonClickListener(selection -> {
      if (pickerHelper.validate(Long.parseLong(selection.toString()))) {
        String dateString = datePicker.getHeaderText();
        editTextChooseDate.setText(dateHelper.parseDateFromPicker(dateString));
        dateSelected = dateHelper.parseDateFromPicker(dateString);
      } else {
        Toast toast = Toast.makeText(getContext(),
                "Select a date from today onwards",
                Toast.LENGTH_LONG);
        toast.show();
      }
    });

    // Search button
    btnSearch.setOnClickListener(view -> {
      if (editTextSearchRoad.getText().length() == 0) {
        Toast toast = Toast.makeText(getContext(),
                "Please enter a road to search by",
                Toast.LENGTH_LONG);
        toast.show();
      } else {
        try {
          // Hide the keyboard
          InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(editTextSearchRoad.getWindowToken(), 0);
        } catch (Exception e) {
          e.printStackTrace();
        }
        searchTerm = editTextSearchRoad.getText().toString();

        executor.execute(() -> {
          filterTrafficList(dateSelected, searchTerm);

          handler.post(() -> {
            addFilteredMarkers(filteredItems);

          });
        });
      }
    });

    return root;
  }


  private void filterTrafficList(String dateSelected, String searchTerm) {
    filteredItems.clear();
    Date dateToFilter = new Date();
    // TODO: move the date
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    try {
      dateToFilter = format.parse(String.valueOf(editTextChooseDate.getText()));
    } catch (ParseException e) {
      e.printStackTrace();
    }

    for (TrafficScotlandItem item : trafficScotlandItems) {
      boolean isInsideDate = item.getStartDate().before(dateToFilter)
              && item.getEndDate().after(dateToFilter);

      if (item.getStartDate().compareTo(dateToFilter) == 0 ||
              item.getEndDate().compareTo(dateToFilter) == 0 ||
              isInsideDate) {
        if (item.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
          filteredItems.add(item);
        }
      }
    }
    Log.i("filtered List", filteredItems.toString());
  }


  // Maps methods
  @Override
  public void onMapReady(@NonNull GoogleMap googleMap) {
    this.googleMap = googleMap;

    // Add a marker in New Zealand
    LatLng uk = new LatLng(54, -3);

    googleMap.moveCamera(CameraUpdateFactory.newLatLng(uk));
  }

  public void addMarkers(ArrayList<LatLng> latLngs) {
    googleMap.clear();
    ArrayList<Marker> markers = new ArrayList<>();

    for (LatLng item : latLngs) {
      googleMap.addMarker(new MarkerOptions().position(item));
      markers.add(googleMap.addMarker(new MarkerOptions().position(item)));
    }

    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    for (Marker item: markers) {
      builder.include(item.getPosition());
    }
    LatLngBounds bounds = builder.build();

    int padding = 50;
    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

    googleMap.animateCamera(cu);
  }

  // TODO: add catch for no included points
  private void addFilteredMarkers(ArrayList<TrafficScotlandItem> filteredItems) {
    googleMap.clear();
    ArrayList<Marker> markers = new ArrayList<>();

    for (TrafficScotlandItem item: filteredItems) {
      LatLng latLng = item.getCoordinates();
      googleMap.addMarker(new MarkerOptions().position(latLng).title(item.getTitle()));
      markers.add(googleMap.addMarker(new MarkerOptions().position(item.getCoordinates())));
    }
    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    for (Marker item: markers) {
      builder.include(item.getPosition());
    }
    LatLngBounds bounds = builder.build();

    int padding = 75;
    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

    googleMap.animateCamera(cu);

  }

  @Override
  public void onResume() {
    mapView.onResume();
    super.onResume();
  }

  @Override
  public void onPause() {
    mapView.onPause();
    super.onPause();
  }

  @Override
  public void onDestroy() {
    mapView.onDestroy();
    super.onDestroy();
  }

  @Override
  public void onLowMemory() {
    mapView.onLowMemory();
    super.onLowMemory();
  }

  @Override
  public void onSaveInstanceState(Bundle state) {
    super.onSaveInstanceState(state);
    mapView.onSaveInstanceState(state);
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

        for (TrafficScotlandItem item : trafficScotlandItems) {
          latLngs.add(item.getCoordinates());
        }
        Log.e("latLngs", latLngs.toString());
      } catch (Exception e) {
        e.printStackTrace();
      }

      handler.post(() -> {
        addMarkers(latLngs);
      });
    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}