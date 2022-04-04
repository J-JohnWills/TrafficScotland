package jjwilliams.trafficscotland.controllers;

// Jamie Williams : S2029548

import android.os.Bundle;
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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import jjwilliams.trafficscotland.databinding.FragmentJourneyPlannerBinding;
import jjwilliams.trafficscotland.R;

public class JourneyPlannerController extends Fragment implements OnMapReadyCallback{

  private GoogleMap googleMap;
  private FragmentJourneyPlannerBinding binding;
  private ViewSwitcher viewSwitcher;
  private Button view1Button, view2Button;
  private RadioGroup mapTypeGroup;
  private RadioButton normalButton, terrainButton, hybridButton, satelliteButton;
  private CheckBox panZoomCheck;


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    View root = inflater.inflate(R.layout.fragment_journey_planner, container, false);

    viewSwitcher = root.findViewById(R.id.journey_planner_view_switcher);
    view1Button = root.findViewById(R.id.view_1_button_map_options);
    view2Button = root.findViewById(R.id.view_2_button_go_to_map);

    view1Button.setOnClickListener(view -> viewSwitcher.showNext());

    view2Button.setOnClickListener(view -> viewSwitcher.showPrevious());

    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
            .findFragmentById(R.id.view_1_map_fragment);
    mapFragment.getMapAsync(this);

    return root;
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }

  @Override
  public void onMapReady(@NonNull GoogleMap googleMap) {

  }

}