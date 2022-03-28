package jjwilliams.trafficscotland.controllers;

// Jamie Williams : S2029548

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jjwilliams.trafficscotland.R;

public class HomeController extends Fragment {

  ExecutorService executor = Executors.newSingleThreadExecutor();
  Handler handler = new Handler(Looper.getMainLooper());


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {


    View root = inflater.inflate(R.layout.fragment_home, container, false);
    return root;
  }

  public void startProgress() {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        // Background work here

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