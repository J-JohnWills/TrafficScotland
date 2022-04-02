package jjwilliams.trafficscotland.controllers;

// Jamie Williams : S2029548

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import jjwilliams.trafficscotland.R;

public class LookupController extends Fragment {

  private Button pickDateButton;
  private TextView showSelectedDateText;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    View root = inflater.inflate(R.layout.fragment_lookup, container, false);

    // Register the textview and button
    pickDateButton = root.findViewById(R.id.pick_date_button);
    showSelectedDateText = root.findViewById(R.id.show_selected_date);

    // Create instance of material date picker builder
    MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();

    // Define the properties of the materialDateBuilder
    materialDateBuilder.setTitleText("SELECT A DATE");

    // create instance of the datePicker
    final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

    // handle select date button which opens the date picker
    pickDateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // getSupportFragmentManager()
        materialDatePicker.show(getParentFragmentManager(), "Date picker");
      }
    });

    materialDatePicker.addOnPositiveButtonClickListener(selection -> showSelectedDateText.setText("Selected date is : " + materialDatePicker.getHeaderText()));
    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }
}