package jjwilliams.trafficscotland.controllers;

// Jamie Williams : S2029548

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import jjwilliams.trafficscotland.R;

public class NotificationsController extends Fragment {


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    View root = inflater.inflate(R.layout.fragment_notifications, container, false);
    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}