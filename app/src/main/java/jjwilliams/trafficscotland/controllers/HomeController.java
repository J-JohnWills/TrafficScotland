package jjwilliams.trafficscotland.controllers;

// Jamie Williams : S2029548

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.adapters.HomeListAdapter;
import jjwilliams.trafficscotland.data.TrafficScotlandController;
import jjwilliams.trafficscotland.models.TrafficScotlandFeed;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;
import jjwilliams.trafficscotland.models.TrafficScotlandType;

public class HomeController extends Fragment {

  ExecutorService executor = Executors.newSingleThreadExecutor();
  Handler handler = new Handler(Looper.getMainLooper());

  TrafficScotlandFeed trafficScotlandFeed = new TrafficScotlandFeed();
  ArrayList<TrafficScotlandItem> trafficScotlandItems = new ArrayList<>();

  private ListView listView;
  MaterialButtonToggleGroup materialButtonToggleGroup;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    Context context = this.getContext();

    if (isInternetConnected()) {
      getFeeds();
    } else {
      Toast toast = Toast.makeText(getContext(),
              "Not connected, Please try again",
              Toast.LENGTH_LONG);
      toast.show();
    }

    View root = inflater.inflate(R.layout.fragment_home, container, false);
    listView = root.findViewById(R.id.home_list_view);
    materialButtonToggleGroup = root.findViewById(R.id.home_toggle_button_group);

    materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
      @Override
      public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        if (isChecked) {
          if (checkedId == R.id.btnIncidents) {
            // filter by incidents
            ArrayList<TrafficScotlandItem> incidents = new ArrayList<>();
            for (TrafficScotlandItem item : trafficScotlandItems) {
              if (item.getType().equals(TrafficScotlandType.CURRENT_INCIDENT)) {
                incidents.add(item);
              }
            }
            HomeListAdapter adapter = new HomeListAdapter(context, incidents);
            listView.setAdapter(adapter);

          } else if (checkedId == R.id.btnPlanned) {
            ArrayList<TrafficScotlandItem> planned = new ArrayList<>();
            for (TrafficScotlandItem item : trafficScotlandItems) {
              if (item.getType().equals(TrafficScotlandType.PLANNED_ROADWORKS)) {
                planned.add(item);
              }
            }
            HomeListAdapter adapter = new HomeListAdapter(context, planned);
            listView.setAdapter(adapter);

          } else if (checkedId == R.id.btnRoadworks) {
            ArrayList<TrafficScotlandItem> roadworks = new ArrayList<>();
            for (TrafficScotlandItem item : trafficScotlandItems) {
              if (item.getType().equals(TrafficScotlandType.ROADWORKS)) {
                roadworks.add(item);
              }
            }
            HomeListAdapter adapter = new HomeListAdapter(context, roadworks);
            listView.setAdapter(adapter);
          }
        } else {
          HomeListAdapter adapter = new HomeListAdapter(context, trafficScotlandItems);
          listView.setAdapter(adapter);
        }
      }
    });
    return root;
  }

  public void getFeeds() {
    executor.execute(() -> {
      // working out thread

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

      handler.post(() -> {
        HomeListAdapter adapter = new HomeListAdapter(this.getContext(), trafficScotlandItems);
        listView.setAdapter(adapter);
      });
    });
  }

  public boolean isInternetConnected() {
    boolean isConnected = true;

    ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    @SuppressLint("MissingPermission") NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
      return false;
    }

    return isConnected;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}