package jjwilliams.trafficscotland.adapters;

import static jjwilliams.trafficscotland.R.layout.fragment_lookup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;

public class LookupListAdapter extends ArrayAdapter<TrafficScotlandItem> {

  private Context context;
  private ArrayList<TrafficScotlandItem> trafficScotlandItems;
  private TrafficScotlandItem item;

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

    title.setText(trafficScotlandItems.get(position).getTitle());



    // Onclick
    seeMore.setOnClickListener(view -> {

    });

    return trafficScotlandItemCard;
  }

}
