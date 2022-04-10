package jjwilliams.trafficscotland.data;

// Jamie Williams : S2029548

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import jjwilliams.trafficscotland.helpers.DateHelper;
import jjwilliams.trafficscotland.models.TrafficScotlandFeed;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;

public class TrafficScotlandPullParser {
  private TrafficScotlandFeed trafficScotlandFeed = new TrafficScotlandFeed();
  private TrafficScotlandItem trafficScotlandItem = new TrafficScotlandItem();
  private Scope scope;
  private InputStream inputStream;
  private DateHelper dateHelper = new DateHelper();

  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public TrafficScotlandFeed parseTrafficScotlandFeed() {

    trafficScotlandFeed = new TrafficScotlandFeed();

    try {
      // XML parsing initialisation
      XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
      factory.setNamespaceAware(true);
      XmlPullParser xpp = factory.newPullParser();
      xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
      xpp.setInput(inputStream, null);

      int eventType = xpp.getEventType();
      while (eventType != XmlPullParser.END_DOCUMENT) {
        switch (eventType) {
          case XmlPullParser.START_TAG:
            if (xpp.getName() != null) {
            }

            switch (xpp.getName().toLowerCase()) {
              case "channel":
                scope = Scope.FEED;
                break;
              case "item":
                scope = Scope.ITEM;
                break;
              case "georss:point":
                scope = Scope.COORDINATES;
                // TODO: coordinated stuff
                String latLng = xpp.nextText();
                if (!latLng.isEmpty()) {
                  String[] latLngs = latLng.split(" ");
                  try {
                    double lat = Double.parseDouble(latLngs[0]);
                    double lng = Double.parseDouble(latLngs[1]);
                    trafficScotlandItem.setCoordinates(lat, lng);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
                scope = Scope.ITEM;
                break;

              case "title":
                if (scope.equals(Scope.FEED)) {
                  trafficScotlandFeed.setTitle(xpp.nextText());
                } else {
                  trafficScotlandItem.setTitle(xpp.nextText());
                }
                break;
              case "description":
                String description = xpp.nextText().replaceAll("<br />", "\\\n");
                if (scope.equals(Scope.FEED)) {
                  trafficScotlandFeed.setDescription(description);
                } else {
                  trafficScotlandItem.setDescription(description);
                  Date date = dateHelper.parseStartDate(description);
                  trafficScotlandItem.setStartDate(date);
                  date = dateHelper.parseEndDate(description);
                  trafficScotlandItem.setEndDate(date);
                  trafficScotlandItem.setDuration(dateHelper.parseDuration
                          (trafficScotlandItem.getStartDate(), trafficScotlandItem.getEndDate()));
                }
                break;
              case "link":
                if (scope.equals(Scope.FEED)) {
                  trafficScotlandFeed.setLink(xpp.nextText());
                } else {
                  trafficScotlandItem.setLink(xpp.nextText());
                }
                break;
              case "ttl":
                trafficScotlandFeed.setTtl(Integer.parseInt(xpp.nextText()));
                break;
              case "pubdate":
                try {
                  trafficScotlandItem.setDatePublished(dateHelper.parsePubDate(xpp.nextText()));
                } catch (Exception e) {
                }
                break;

              default:
                break;
            }
            break;

          case XmlPullParser.END_TAG:
            if (xpp.getName() != null) {
            }
            if (xpp.getName().equalsIgnoreCase("item") && scope == Scope.ITEM) {
              trafficScotlandFeed.addItems(trafficScotlandItem);
              trafficScotlandItem = new TrafficScotlandItem();
              scope = Scope.FEED;
            }
            break;
          default:
            if (xpp.getName() != null) {
            }
            break;
        }
        eventType = xpp.next();
      }
    } catch (XmlPullParserException e) {
      Log.e("In TrafficScotlandPullParser", "Error during parsing" + e.toString());
    } catch (IOException e) {
      Log.e("In TrafficScotlandPullParser", "IO error during parsing" + e.toString());
    }
    return trafficScotlandFeed;
  }
}

enum Scope {
  FEED,
  ITEM,
  COORDINATES
}
