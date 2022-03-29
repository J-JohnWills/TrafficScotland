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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.adapters.HomeListAdapter;
import jjwilliams.trafficscotland.data.TrafficScotlandPullParser;
import jjwilliams.trafficscotland.models.TrafficScotlandFeed;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;

public class HomeController extends Fragment {

  ExecutorService executor = Executors.newSingleThreadExecutor();
  Handler handler = new Handler(Looper.getMainLooper());

  TrafficScotlandFeed trafficScotlandFeed = new TrafficScotlandFeed();
  TrafficScotlandPullParser pullParser = new TrafficScotlandPullParser();

  private ListView listView;



  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {


    populateCards();
    View root = inflater.inflate(R.layout.fragment_home, container, false);
    listView = root.findViewById(R.id.home_list_view);
    return root;
  }

  public void populateCards() {
    executor.execute(() -> {
      URL aurl;
      URLConnection yc;
      BufferedReader in = null;

      try {
        aurl = new URL("https://trafficscotland.org/rss/feeds/roadworks.aspx");
        yc = aurl.openConnection();
        in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        pullParser.setInputStream(yc.getInputStream());
        trafficScotlandFeed = pullParser.parseTrafficScotlandFeed();
        Log.i("Traffic Scotland feed item", trafficScotlandFeed.toString());

      } catch (Exception e) {
        e.printStackTrace();
      }

      handler.post(() -> {
//        ListAdapter adapter = new ListAdapter(this.getContext(), trafficScotlandFeed.getTrafficScotlandItems());
//        listView.setAdapter(adapter);
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