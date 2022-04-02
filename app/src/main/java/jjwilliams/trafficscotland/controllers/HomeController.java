package jjwilliams.trafficscotland.controllers;

// Jamie Williams : S2029548

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.adapters.HomeListAdapter;
import jjwilliams.trafficscotland.data.TrafficScotlandController;
import jjwilliams.trafficscotland.models.TrafficScotlandFeed;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;

public class HomeController extends Fragment {

  ExecutorService executor = Executors.newSingleThreadExecutor();
  Handler handler = new Handler(Looper.getMainLooper());

  TrafficScotlandFeed trafficScotlandFeed = new TrafficScotlandFeed();
  ArrayList<TrafficScotlandItem> trafficScotlandItems = new ArrayList<>();


  private ListView listView;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {


    connectorTest();
    View root = inflater.inflate(R.layout.fragment_home, container, false);
    listView = root.findViewById(R.id.home_list_view);
    return root;
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

      } catch (Exception e) {
        e.printStackTrace();
      }

      handler.post(() -> {
        HomeListAdapter adapter = new HomeListAdapter(this.getContext(), trafficScotlandItems);
        listView.setAdapter(adapter);
      });
    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}