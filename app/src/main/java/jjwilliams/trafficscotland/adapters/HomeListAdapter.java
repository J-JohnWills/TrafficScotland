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
import java.util.List;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;

public class HomeListAdapter extends ArrayAdapter<TrafficScotlandItem> {

  private Context context;
  private ArrayList<TrafficScotlandItem> trafficScotlandItems;

  public HomeListAdapter(@NonNull Context context,
                         @NonNull ArrayList<TrafficScotlandItem> trafficScotlandItems) {
    super(context, R.layout.traffic_scotland_item_card,trafficScotlandItems);
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
    TextView currentTitle = trafficScotlandItemCard.findViewById(R.id.textView1);
    TextView currentDescription = trafficScotlandItemCard.findViewById(R.id.textView2);
    TextView currentType = trafficScotlandItemCard.findViewById(R.id.home_card_type_text);

    currentTitle.setText(trafficScotlandItems.get(position).getTitle());
    currentDescription.setText(trafficScotlandItems.get(position).getDescription());

    return trafficScotlandItemCard;
  }
}
