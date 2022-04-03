package jjwilliams.trafficscotland.data;

// Jamie Williams : S2029548

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import jjwilliams.trafficscotland.helpers.DateHelpers;
import jjwilliams.trafficscotland.models.TrafficScotlandFeed;
import jjwilliams.trafficscotland.models.TrafficScotlandItem;

public class TrafficScotlandPullParser {
  private TrafficScotlandFeed trafficScotlandFeed = new TrafficScotlandFeed();
  private TrafficScotlandItem trafficScotlandItem = new TrafficScotlandItem();
  private Scope scope;
  private InputStream inputStream;
  DateHelpers dateHelpers = new DateHelpers();

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
              Log.i("START_TAG", xpp.getName());
            }

            switch (xpp.getName().toLowerCase()) {
              case "channel":
                scope = Scope.FEED;
                break;
              case "item":
                scope = Scope.ITEM;
                break;
              case "point":
                scope = Scope.COORDINATES;
                // TODO: coordinated stuff
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
                  Date date = dateHelpers.parseStartDate(description);
                  trafficScotlandItem.setStartDate(date);
                  date = dateHelpers.parseEndDate(description);
                  trafficScotlandItem.setEndDate(date);
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
                  trafficScotlandItem.setDatePublished(dateHelpers.parseDate(xpp.nextText()));
                } catch (Exception e) {
                  Log.e("Inside case 'pubdate'", "Error setting new date " + e.toString());
                }
                break;

              default:
                break;
            }
            break;

          case XmlPullParser.END_TAG:
            if (xpp.getName() != null) {
              Log.i("END_TAG", xpp.getName());
            }
            if (xpp.getName().equalsIgnoreCase("item") && scope == Scope.ITEM) {
              trafficScotlandFeed.addItems(trafficScotlandItem);
              trafficScotlandItem = new TrafficScotlandItem();
              scope = Scope.FEED;
            }
            break;
          default:
            if (xpp.getName() != null) {
              Log.i("???", xpp.getName());
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
