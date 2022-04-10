package jjwilliams.trafficscotland.adapters;

import static jjwilliams.trafficscotland.R.layout.fragment_lookup;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;
import jjwilliams.trafficscotland.models.TrafficScotlandType;

public class LookupListAdapter extends ArrayAdapter<TrafficScotlandItem> {

  private Context context;
  private ArrayList<TrafficScotlandItem> trafficScotlandItems;
  private TrafficScotlandItem item;

  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

  public LookupListAdapter(@NonNull Context context,
                           @NonNull ArrayList<TrafficScotlandItem> trafficScotlandItems) {
    super(context, R.layout.list_view_item, trafficScotlandItems);
    this.context = context;
    this.trafficScotlandItems = trafficScotlandItems;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getApplicationContext()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View trafficScotlandItemCard = inflater.inflate(R.layout.list_view_item, parent, false);
    TextView title = trafficScotlandItemCard.findViewById(R.id.list_view_item_title);
    TextView startDate = trafficScotlandItemCard.findViewById(R.id.list_view_item_start_date);
    TextView endDate = trafficScotlandItemCard.findViewById(R.id.list_view_item_end_date);
    Button seeMore = trafficScotlandItemCard.findViewById(R.id.list_view_item_button_see_more);
    ImageView typeIcon = trafficScotlandItemCard.findViewById(R.id.list_view_item_image_view_type);
    TextView typeText = trafficScotlandItemCard.findViewById(R.id.list_view_item_text_view_type);
    TextView duration = trafficScotlandItemCard.findViewById(R.id.list_view_item_duration);

    title.setText(trafficScotlandItems.get(position).getTitle());

    // set date
    if (trafficScotlandItems.get(position).getType() != TrafficScotlandType.CURRENT_INCIDENT) {
      startDate.append(dateFormat.format(trafficScotlandItems.get(position).getStartDate()));
      endDate.append(dateFormat.format(trafficScotlandItems.get(position).getEndDate()));

      // TODO: make elegant and sexy
      long longDuration = trafficScotlandItems.get(position).getDuration();
      if (longDuration < 7) {
        duration.setTextColor(Color.GREEN);
        duration.append(trafficScotlandItems.get(position).getDuration() + " Days");
      } else if (longDuration >= 7 && longDuration<=14) {
        duration.setTextColor(0xffFF6600);
        duration.append(trafficScotlandItems.get(position).getDuration() + " Days");
      } else if (longDuration > 14) {
        duration.setTextColor(Color.RED);
        duration.append(trafficScotlandItems.get(position).getDuration() + " Days");
      }
    } else {
      startDate.setText("Happening now!");
      endDate.setText("");
    }

    // Set icon and type text
    if (trafficScotlandItems.get(position).getType() == TrafficScotlandType.ROADWORKS) {
      typeIcon.setImageResource(R.drawable.ic_baseline_remove_road_24);
      typeText.setText("Roadwork");
    } else if (trafficScotlandItems.get(position).getType() == TrafficScotlandType.PLANNED_ROADWORKS) {
      typeIcon.setImageResource(R.drawable.ic_baseline_date_range_24);
      typeText.setText("Planned ");
    } else if (trafficScotlandItems.get(position).getType() == TrafficScotlandType.CURRENT_INCIDENT) {
      typeIcon.setImageResource(R.drawable.ic_baseline_car_crash_24);
      typeText.setText("Incident");
    }
    // Onclick
    seeMore.setOnClickListener(view -> {

    });

    return trafficScotlandItemCard;
  }

  public String durationColor(String stringToColor) {
    String durationColored = "";


    return durationColored;
  }

}
