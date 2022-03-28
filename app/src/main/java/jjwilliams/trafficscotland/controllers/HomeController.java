package jjwilliams.trafficscotland.controllers;

// Jamie Williams : S2029548

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.data.TrafficScotlandPullParser;
import jjwilliams.trafficscotland.models.TrafficScotlandFeed;

public class HomeController extends Fragment {

  ExecutorService executor = Executors.newSingleThreadExecutor();
  Handler handler = new Handler(Looper.getMainLooper());

  TrafficScotlandFeed trafficScotlandFeed = new TrafficScotlandFeed();
  TrafficScotlandPullParser pullParser = new TrafficScotlandPullParser();


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {


    startProgress();
    View root = inflater.inflate(R.layout.fragment_home, container, false);
    return root;
  }

  public void startProgress() {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        // Background work here
        URL aurl;
        URLConnection yc;
        BufferedReader in = null;

        try {
          aurl = new URL("https://trafficscotland.org/rss/feeds/currentincidents.aspx");
          yc = aurl.openConnection();
          in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
          pullParser.setInputStream(yc.getInputStream());
          trafficScotlandFeed = pullParser.parseTrafficScotlandFeed();
          Log.i("Traffic Scotland feed item", trafficScotlandFeed.toString());
        } catch (Exception e) {
          e.printStackTrace();
        }

        handler.post(new Runnable() {
          @Override
          public void run() {
            // UI thread here
          }
        });
      }
    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}