package jjwilliams.trafficscotland.controllers;

// Jamie Williams : S2029548

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jjwilliams.trafficscotland.adapters.HomeListAdapter;
import jjwilliams.trafficscotland.data.TrafficScotlandController;
import jjwilliams.trafficscotland.databinding.FragmentJourneyPlannerBinding;
import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.models.TrafficScotlandCoordinates;
import jjwilliams.trafficscotland.models.TrafficScotlandFeed;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;

public class JourneyPlannerController extends Fragment implements OnMapReadyCallback {

  private GoogleMap googleMap;
  private MapView mapView;
  private FragmentJourneyPlannerBinding binding;
  private Button view1Button, view2Button;
  private MarkerOptions markerOptions;
  private ArrayList<LatLng> latLngs = new ArrayList<>();

  ExecutorService executor = Executors.newSingleThreadExecutor();
  Handler handler = new Handler(Looper.getMainLooper());

  TrafficScotlandFeed trafficScotlandFeed = new TrafficScotlandFeed();
  ArrayList<TrafficScotlandItem> trafficScotlandItems = new ArrayList<>();


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    View root = inflater.inflate(R.layout.fragment_journey_planner, container, false);


    // Google Map
    MapsInitializer.initialize(this.getActivity());
    mapView = root.findViewById(R.id.journey_planner_map_view);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(this);

    connectorTest();

    return root;
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
    googleMap.addMarker(new MarkerOptions().position(latLngs.get(0)));

    for (LatLng item: latLngs) {
      googleMap.addMarker(new MarkerOptions().position(item));
    }
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

  public void connectorTest() {
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