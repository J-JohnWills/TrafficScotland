package jjwilliams.trafficscotland.controllers;

// Jamie Williams : S2029548

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.adapters.HomeListAdapter;
import jjwilliams.trafficscotland.data.Connector;
import jjwilliams.trafficscotland.models.TrafficScotlandFeed;

public class HomeController extends Fragment {

  ExecutorService executor = Executors.newSingleThreadExecutor();
  Handler handler = new Handler(Looper.getMainLooper());

  TrafficScotlandFeed trafficScotlandFeed = new TrafficScotlandFeed();

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
        Connector connector = new Connector("https://trafficscotland.org/rss/feeds/roadworks.aspx");
        trafficScotlandFeed = connector.getTrafficScotlandFeed();
      } catch (Exception e) {
        e.printStackTrace();
      }

      handler.post(() ->{
        HomeListAdapter adapter = new HomeListAdapter(this.getContext(), trafficScotlandFeed.getTrafficScotlandItems());
        listView.setAdapter(adapter);
      });
    });
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}