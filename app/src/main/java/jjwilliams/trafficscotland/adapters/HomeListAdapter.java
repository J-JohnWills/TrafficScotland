package jjwilliams.trafficscotland.adapters;

// Jamie Williams : S2029548

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.helpers.DateHelper;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;
import jjwilliams.trafficscotland.models.TrafficScotlandType;

public class HomeListAdapter extends ArrayAdapter<TrafficScotlandItem> {

  // TODO: remove the date stuff to DateHelper

  private Context context;
  private ArrayList<TrafficScotlandItem> trafficScotlandItems;
  private TrafficScotlandItem item;

  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
  DateHelper dateHelper = new DateHelper();

  public HomeListAdapter(@NonNull Context context,
                         @NonNull ArrayList<TrafficScotlandItem> trafficScotlandItems) {
    super(context, R.layout.traffic_scotland_item_card, trafficScotlandItems);
    this.context = context;
    this.trafficScotlandItems = trafficScotlandItems;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getApplicationContext()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View trafficScotlandItemCard = inflater.inflate(R.layout.traffic_scotland_item_card, parent,
            false);
    ImageView currentImage = trafficScotlandItemCard.findViewById(R.id.image);
    TextView currentTitle = trafficScotlandItemCard.findViewById(R.id.text_view_main_title);
    TextView currentType = trafficScotlandItemCard.findViewById(R.id.home_card_type_text);
    TextView currentStartDate = trafficScotlandItemCard.findViewById(R.id.text_view_start_date);
    TextView currentEndDate = trafficScotlandItemCard.findViewById(R.id.text_view_end_date);
    TextView duration = trafficScotlandItemCard.findViewById(R.id.text_view_duration);


    TextView seeMore = trafficScotlandItemCard.findViewById(R.id.textViewSeeMore);

    if (trafficScotlandItems.get(position).getType() == TrafficScotlandType.ROADWORKS) {
      currentImage.setImageResource(R.drawable.outline_route_2_24);
      currentType.setText("Roadwork");
    } else if (trafficScotlandItems.get(position).getType() == TrafficScotlandType.PLANNED_ROADWORKS) {
      currentImage.setImageResource(R.drawable.ic_baseline_date_range_24);
      currentType.setText("Planned");
    } else if (trafficScotlandItems.get(position).getType() == TrafficScotlandType.CURRENT_INCIDENT) {
      currentImage.setImageResource(R.drawable.ic_baseline_car_crash_24);
      currentType.setText("Incident");
    }


    currentTitle.setText(trafficScotlandItems.get(position).getTitle());

    if (trafficScotlandItems.get(position).getType() != TrafficScotlandType.CURRENT_INCIDENT) {
      currentStartDate.append(dateFormat.format(trafficScotlandItems.get(position).getStartDate()));
      currentEndDate.append(dateFormat.format(trafficScotlandItems.get(position).getEndDate()));
      duration.append(trafficScotlandItems.get(position).getDuration() + " Days");
    } else {
      currentStartDate.setText("Happening now!");
      currentEndDate.setText("");
      duration.setText("");
    }


    item = trafficScotlandItems.get(position);

    seeMore.setOnClickListener(view -> {
      onClick(view);
    });

    return trafficScotlandItemCard;
  }

  private void onClick(View view) {
    AlertDialog alertDialog = new AlertDialog.Builder(this.getContext()).create();
    alertDialog.setTitle("Detailed Information");
    String temp = dateFormat.format(item.getDatePublished());
    alertDialog.setMessage(item.getDescription());
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Back", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
      }

    });
    alertDialog.show();
  }
}
