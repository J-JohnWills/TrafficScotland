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

import java.util.ArrayList;
import java.util.List;

import jjwilliams.trafficscotland.R;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;
import jjwilliams.trafficscotland.models.TrafficScotlandType;

public class HomeListAdapter extends ArrayAdapter<TrafficScotlandItem> {

  private Context context;
  private ArrayList<TrafficScotlandItem> trafficScotlandItems;
  private TrafficScotlandItem item;

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
    currentDescription.setText(trafficScotlandItems.get(position).getDescription());

    item = trafficScotlandItems.get(position);

    seeMore.setOnClickListener(view -> {
      onClick(view);
    });

    return trafficScotlandItemCard;
  }

  private void onClick(View view) {
    AlertDialog alertDialog = new AlertDialog.Builder(this.getContext()).create();
    alertDialog.setTitle("Detailed Information");
    alertDialog.setMessage("Publish date : " + item.getDatePublished());
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Back", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
      }

    });
    alertDialog.show();
  }
}
