package jjwilliams.trafficscotland.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;
import jjwilliams.trafficscotland.models.TrafficScotlandType;

public class TestAdapter extends ArrayAdapter<TrafficScotlandItem> {

  private Context context;
  private ArrayList<TrafficScotlandItem> trafficScotlandItems;
  private TrafficScotlandItem item;

  public TestAdapter(@NonNull Context context,
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

    View listViewItem = inflater.inflate(R.layout.list_view_item, parent, false);
    ImageView icon = listViewItem.findViewById(R.id.list_view_item_icon);
    TextView title = listViewItem.findViewById(R.id.list_view_item_title);

    if (trafficScotlandItems.get(position).getType() == TrafficScotlandType.ROADWORKS) {
      icon.setImageResource(R.drawable.outline_route_2_24);
    } else if (trafficScotlandItems.get(position).getType() == TrafficScotlandType.PLANNED_ROADWORKS) {
      icon.setImageResource(R.drawable.ic_baseline_date_range_24);
    } else if (trafficScotlandItems.get(position).getType() == TrafficScotlandType.CURRENT_INCIDENT) {
      icon.setImageResource(R.drawable.ic_baseline_car_crash_24);
    }

    title.setText(trafficScotlandItems.get(position).getTitle());

    return listViewItem;
  }
}
